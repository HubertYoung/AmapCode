package com.alipay.mobile.common.rpc.util;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;
import com.alipay.mobile.common.transport.utils.HeaderConstant;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.ext.annotation.Retryable;
import com.autonavi.minimap.ajx3.util.Constants;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class RpcInvokerUtil {
    private static long NEXT_TIME_FOR_GLOBAL_NETWORK_LIMIT = -1;
    public static final String RPC_V1 = "V1";
    public static final String RPC_V2 = "V2";
    public static final String SIMPLE_RPC_OPERATION_TYPE = "alipay.client.executerpc";
    public static final String SIMPLE_RPC_OPERATION_TYPE_BYTES = "alipay.client.executerpc.bytes";
    private static final String TAG = "RpcInvokerUtil";
    private static List<String> rpcOpeationList = new ArrayList();

    public static final boolean isSimpleRpcAnnotation(String originOperationType) {
        return TextUtils.equals(originOperationType, "alipay.client.executerpc");
    }

    public static final boolean isSimpleRpcBytesAnnotation(String operationType) {
        return TextUtils.equals(operationType, "alipay.client.executerpc.bytes");
    }

    public static final String getOperationTypeValue(Method method, Object[] originArgs) {
        OperationType operationTypeAnnotation = (OperationType) method.getAnnotation(OperationType.class);
        if (operationTypeAnnotation == null) {
            throw new IllegalStateException("OperationType must be set.");
        }
        String value = operationTypeAnnotation.value();
        if (isSimpleRpcAnnotation(value) || isSimpleRpcBytesAnnotation(value)) {
            return String.valueOf(originArgs[0]);
        }
        return value;
    }

    public static boolean isRetryable(Method method) {
        return ((Retryable) method.getAnnotation(Retryable.class)) != null;
    }

    public static void preProcessResponse(Response response) {
        HttpUrlResponse httpUrlResponse = (HttpUrlResponse) response;
        HttpUrlHeader header = httpUrlResponse.getHeader();
        int resultStatus = Integer.valueOf(header.getHead("Result-Status")).intValue();
        String tips = header.getHead("Tips");
        if (resultStatus != 1000 && resultStatus != 8001) {
            RpcException rpcException = new RpcException(Integer.valueOf(resultStatus), decodeMemo(resultStatus, tips));
            String alertValue = httpUrlResponse.getHeader().getHead("alert");
            if (!TextUtils.isEmpty(alertValue)) {
                if (TextUtils.equals(alertValue, "0")) {
                    LogCatUtil.debug(TAG, "set alertValue NO_ALERT");
                    rpcException.setAlert(0);
                } else if (TextUtils.equals(alertValue, "1")) {
                    LogCatUtil.debug(TAG, "set alertValue TOAST_ALERT");
                    rpcException.setAlert(1);
                }
            }
            if (resultStatus == 1002) {
                String control = header.getHead("Control");
                if (!TextUtils.isEmpty(control)) {
                    rpcException.setControl(decodeControlStr(control));
                }
            }
            LogCatUtil.debug("HttpCaller", "preProcessResponse, alertValue:" + alertValue + ", rpcException hashcode: " + rpcException.hashCode() + ", errcode: " + rpcException.getCode() + ", errmsg: " + rpcException.getMsg() + ", alert: " + rpcException.getAlert() + ", control: " + rpcException.getControl());
            throw rpcException;
        }
    }

    private static String decodeControlStr(String control) {
        try {
            return URLDecoder.decode(control, "UTF-8");
        } catch (Exception e) {
            LogCatUtil.error(TAG, "control=[" + control + "]", e);
            return control;
        }
    }

    private static String decodeMemo(int resultStatus, String memo) {
        if (TextUtils.isEmpty(memo)) {
            return memo;
        }
        try {
            return URLDecoder.decode(memo, "utf-8");
        } catch (Exception e) {
            LogCatUtil.error(TAG, "memo=[" + memo + "]", e);
            return "很抱歉，系统错误 [" + resultStatus + "]。";
        }
    }

    public static String getRpcVersion() {
        try {
            Context context = TransportEnvUtil.getContext();
            String rpcVersionStr = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("mobilegw.rpcVersion");
            if (!TextUtils.isEmpty(rpcVersionStr)) {
                return rpcVersionStr;
            }
            return "V2";
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, (Throwable) e);
            return null;
        }
    }

    public static void mockRpcLimit(Context context, Method method, Object[] args) {
        try {
            if (MiscUtils.isDebugger(context)) {
                if (ReadSettingServerUrl.getInstance().isEnableGlobalNetworkLimit(context) && System.currentTimeMillis() > NEXT_TIME_FOR_GLOBAL_NETWORK_LIMIT) {
                    NEXT_TIME_FOR_GLOBAL_NETWORK_LIMIT = 7000;
                    RpcException rpcException = new RpcException(Integer.valueOf(1002), (String) "");
                    rpcException.setControl("{\"tag\":\"overflow\",\"title\":\"" + "福气正在路上" + "\",\"waittime\":\"4\"}");
                    throw rpcException;
                } else if (ReadSettingServerUrl.getInstance().isEnableRpcNetworkLimit(context)) {
                    String rpcNameOfRpcNetworkLimit = ReadSettingServerUrl.getInstance().getRpcNameOfRpcNetworkLimit(context);
                    if (TextUtils.isEmpty(rpcNameOfRpcNetworkLimit)) {
                        LogCatUtil.warn((String) TAG, (String) "Config rpc name is empty");
                        return;
                    }
                    String operationTypeValue = getOperationTypeValue(method, args);
                    if (rpcNameOfRpcNetworkLimit.indexOf(":") != -1 || TextUtils.indexOf(operationTypeValue, rpcNameOfRpcNetworkLimit) == -1) {
                        String[] rpcNameExpr = rpcNameOfRpcNetworkLimit.split(":");
                        if (rpcNameExpr.length != 2) {
                            LogCatUtil.warn((String) TAG, (String) "rpcNameExpr length != 2");
                        } else if (TextUtils.indexOf(operationTypeValue, rpcNameExpr[1]) == -1) {
                            LogCatUtil.warn((String) TAG, "Did not match. config rpc name：" + rpcNameExpr[1] + ", operationTypeValue:" + operationTypeValue);
                        } else {
                            RpcException rpcException2 = new RpcException(Integer.valueOf(1002), (String) "福气正在路上");
                            if ("toast".equalsIgnoreCase(rpcNameExpr[0])) {
                                rpcException2.setMsg("请稍等喔，马上出来");
                                rpcException2.setAlert(1);
                            } else if (Constants.ANIMATOR_NONE.equalsIgnoreCase(rpcNameExpr[0])) {
                                rpcException2.setAlert(0);
                            } else {
                                rpcException2.setAlert(-100);
                            }
                            throw rpcException2;
                        }
                    } else {
                        throw new RpcException(Integer.valueOf(1002), (String) "福气正在路上");
                    }
                }
            }
        } catch (RpcException e) {
            throw e;
        } catch (Throwable e2) {
            LogCatUtil.warn(TAG, "mockRpcLimit fail", e2);
        }
    }

    public static final void preHandleForBizInterceptor(Object proxy, Class<?> clazz, Method method, Object[] args, InnerRpcInvokeContext invokeContext, ThreadLocal<Map<String, Object>> EXT_PARAM, ThreadLocal<Object> RETURN_VALUE) {
        try {
            for (RpcInterceptor rpcInterceptor : invokeContext.getRpcInterceptorList()) {
                if (!rpcInterceptor.preHandle(proxy, RETURN_VALUE, new byte[0], clazz, method, args, null, EXT_PARAM)) {
                    throw new RpcException(Integer.valueOf(21), rpcInterceptor + " preHandle stop this call.");
                }
            }
        } catch (RpcException e) {
            throw e;
        } catch (Throwable e2) {
            RpcException rpcException = new RpcException(Integer.valueOf(21), e2.toString());
            rpcException.initCause(e2);
            throw rpcException;
        }
    }

    public static final void postHandleForBizInterceptor(Object proxy, byte[] rawResult, Class<?> clazz, Method method, Object[] args, Annotation[] annotations, InnerRpcInvokeContext invokeContext, ThreadLocal<Object> RETURN_VALUE) {
        try {
            for (RpcInterceptor rpcInterceptor : invokeContext.getRpcInterceptorList()) {
                if (!rpcInterceptor.postHandle(proxy, RETURN_VALUE, rawResult, clazz, method, args, null)) {
                    throw new RpcException(Integer.valueOf(21), rpcInterceptor + "postHandle stop this call.");
                }
            }
        } catch (RpcException e) {
            throw e;
        } catch (Throwable e2) {
            RpcException rpcException = new RpcException(Integer.valueOf(21), e2.toString());
            rpcException.initCause(e2);
            throw rpcException;
        }
    }

    public static void postHandleForPacketSize(Method method, Object[] args, InnerRpcInvokeContext invokeContext) {
        try {
            if (MiscUtils.isDebugger(TransportEnvUtil.getContext()) && MiscUtils.isInAlipayClient(TransportEnvUtil.getContext())) {
                Map responseHeader = invokeContext.responseHeader;
                String reqSize = responseHeader.get(HeaderConstant.HEADER_KEY_PARAM_REQ_SIZE);
                String resSize = responseHeader.get(HeaderConstant.HEADER_KEY_PARAM_RES_SIZE);
                TransportConfigureManager configMgr = TransportConfigureManager.getInstance();
                long reqSizeLimit = configMgr.getLongValue(TransportConfigureItem.RPC_REQSIZE_LIMIT);
                long resSizeLimit = configMgr.getLongValue(TransportConfigureItem.RPC_RESSIZE_LIMIT);
                String operationType = getOperationTypeValue(method, args);
                if (rpcOpeationList.contains(operationType)) {
                    LogCatUtil.debug(TAG, "opeType:" + operationType + " ,not first time,ignore");
                } else if (Long.parseLong(reqSize) > reqSizeLimit) {
                    String errMsg = "operationType:" + operationType + ",RPC Request size: " + reqSize + " more than " + reqSizeLimit + " byte,please optimize";
                    LogCatUtil.debug(TAG, errMsg);
                    rpcOpeationList.add(operationType);
                    throw new RpcException(Integer.valueOf(22), errMsg);
                } else if (Long.parseLong(resSize) > resSizeLimit) {
                    String errMsg2 = "operationType:" + operationType + ",RPC Response size: " + resSize + " more than " + resSizeLimit + " byte,please optimize";
                    LogCatUtil.debug(TAG, errMsg2);
                    rpcOpeationList.add(operationType);
                    throw new RpcException(Integer.valueOf(23), errMsg2);
                }
            }
        } catch (RpcException e) {
            throw e;
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "postHandleForPacketSize ex:" + ex.toString(), ex);
        }
    }
}
