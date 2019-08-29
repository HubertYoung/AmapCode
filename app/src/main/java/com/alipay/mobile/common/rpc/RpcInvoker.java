package com.alipay.mobile.common.rpc;

import android.annotation.TargetApi;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.netsdkextdependapi.monitorinfo.MonitorInfoUtil;
import com.alipay.mobile.common.netsdkextdependapi.monitorinfo.MonitorLoggerModel;
import com.alipay.mobile.common.rpc.protocol.Serializer;
import com.alipay.mobile.common.rpc.protocol.json.JsonSerializer;
import com.alipay.mobile.common.rpc.protocol.json.JsonSerializerV2;
import com.alipay.mobile.common.rpc.protocol.json.SignJsonSerializer;
import com.alipay.mobile.common.rpc.protocol.protobuf.PBSerializer;
import com.alipay.mobile.common.rpc.protocol.protobuf.SimpleRpcPBSerializer;
import com.alipay.mobile.common.rpc.protocol.util.RPCProtoDesc;
import com.alipay.mobile.common.rpc.protocol.util.SerializerFactory;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;
import com.alipay.mobile.common.rpc.transport.RpcCaller;
import com.alipay.mobile.common.rpc.transport.http.HttpCaller;
import com.alipay.mobile.common.rpc.util.RpcInvokerUtil;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.http.CookieAccessHelper;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;
import com.alipay.mobile.common.transport.utils.GtsUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.RpcSignUtil;
import com.alipay.mobile.common.transport.utils.RpcSignUtil.SignData;
import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobile.framework.service.ext.annotation.CheckLogin;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class RpcInvoker {
    protected static final ThreadLocal<Map<String, Object>> EXT_PARAM = new ThreadLocal<>();
    private static final byte MODE_BATCH = 1;
    private static final byte MODE_DEFAULT = 0;
    /* access modifiers changed from: private */
    public static final ThreadLocal<Object> RETURN_VALUE = new ThreadLocal<>();
    private static final String TAG = "RpcInvoker";
    private static AtomicReference<String> mScene = new AtomicReference<>();
    private byte mMode = 0;
    /* access modifiers changed from: private */
    public RpcFactory mRpcFactory;
    protected AtomicInteger rpcSequence;
    protected SerializerFactory serializerFactory;

    interface Handle {
        boolean handle(RpcInterceptor rpcInterceptor, Annotation annotation);
    }

    public RpcInvoker(RpcFactory rpcFactory) {
        this.mRpcFactory = rpcFactory;
        this.rpcSequence = new AtomicInteger();
        this.serializerFactory = new SerializerFactory(this.mRpcFactory);
    }

    public RpcFactory getRpcFactory() {
        return this.mRpcFactory;
    }

    public Object invoke(Object proxy, Class<?> clazz, Method method, Object[] args, InnerRpcInvokeContext invokeContext) {
        LogCatUtil.info(TAG, "ThreadId=[" + Thread.currentThread().getId() + "]  invoke. object=[" + method.getDeclaringClass().getName() + "]  methodName=[" + method.getName() + "]");
        if (checkMainThread()) {
            throw new IllegalThreadStateException("can't call rpc in main thread.");
        }
        long startTime = System.currentTimeMillis();
        RETURN_VALUE.set(null);
        EXT_PARAM.set(null);
        int id = this.rpcSequence.incrementAndGet();
        Response response = null;
        Object returnObj = null;
        try {
            preHandle(proxy, clazz, method, args, method.getAnnotations(), invokeContext);
            if (this.mMode == 0) {
                RPCProtoDesc protoDesc = new RPCProtoDesc();
                response = singleCall(method, args, RpcInvokerUtil.getOperationTypeValue(method, args), id, invokeContext, protoDesc);
                returnObj = processResponse(method, response, protoDesc);
            }
        } catch (RpcException exception) {
            perfLog(exception, RpcInvokerUtil.getOperationTypeValue(method, args));
            exception.setOperationType(RpcInvokerUtil.getOperationTypeValue(method, args));
            exceptionHandle(proxy, response != null ? response.getResData() : null, clazz, method, args, method.getAnnotations(), exception, invokeContext, startTime);
        }
        postHandle(proxy, response != null ? response.getResData() : null, clazz, method, args, method.getAnnotations(), invokeContext);
        printAllTimeLog(method, RpcInvokerUtil.getOperationTypeValue(method, args), startTime);
        if (RETURN_VALUE.get() != null) {
            returnObj = RETURN_VALUE.get();
        }
        asyncNotifyRpcHeaderUpdateEvent(method, args, (HttpUrlResponse) response);
        printReturnObjLog(method, returnObj);
        return returnObj;
    }

    /* access modifiers changed from: protected */
    public void printAllTimeLog(Method method, String ot, long startTime) {
        LogCatUtil.debug(TAG, "ThreadId=[" + Thread.currentThread().getId() + "] methodName=[" + method.getName() + "] API=[" + ot + "] invokeTiming=[" + (System.currentTimeMillis() - startTime) + "]");
    }

    /* access modifiers changed from: protected */
    public void asyncNotifyRpcHeaderUpdateEvent(final Method method, final Object[] args, final HttpUrlResponse response) {
        NetworkAsyncTaskExecutor.execute(new Runnable() {
            public void run() {
                RpcHeader rpcHeader = new RpcHeader();
                rpcHeader.operationType = RpcInvokerUtil.getOperationTypeValue(method, args);
                rpcHeader.httpUrlHeader = response.getHeader();
                RpcInvoker.this.mRpcFactory.notifyRpcHeaderUpdateEvent(rpcHeader);
            }
        });
    }

    private void printReturnObjLog(Method method, Object returnObj) {
        if (!MiscUtils.isDebugger(getRpcFactory().getContext())) {
            return;
        }
        if (returnObj != null) {
            LogCatUtil.printInfo(TAG, "ThreadId=[" + Thread.currentThread().getId() + "] methodName=[" + method.getName() + "] returnObj=[" + JSON.toJSONString(returnObj) + "]");
        } else {
            LogCatUtil.printInfo(TAG, "ThreadId=[" + Thread.currentThread().getId() + "] methodName=[" + method.getName() + "] returnObj=[ is null ]");
        }
    }

    private Object processResponse(Method method, Response response, RPCProtoDesc protoDesc) {
        Type retType = method.getGenericReturnType();
        Object object = this.serializerFactory.getDeserializer(retType, response, protoDesc).parser();
        if (retType != Void.TYPE) {
            RETURN_VALUE.set(object);
        }
        return object;
    }

    public void setScene(String scene) {
        mScene.set(scene);
    }

    public String getScene() {
        return mScene.get();
    }

    private boolean handleAnnotations(Annotation[] annotations, Handle handle) {
        boolean ret = true;
        try {
            for (Annotation annotation : annotations) {
                RpcInterceptor rpcInterceptor = this.mRpcFactory.findRpcInterceptor(annotation.annotationType());
                if (rpcInterceptor != null) {
                    ret = handle.handle(rpcInterceptor, annotation);
                    if (!ret) {
                        break;
                    }
                }
            }
            return ret;
        } catch (Throwable e) {
            LogCatUtil.error((String) TAG, "handleAnnotations ex:" + e.toString());
            if (e instanceof RpcException) {
                throw ((RpcException) e);
            }
            throw new RpcException(Integer.valueOf(9), e);
        }
    }

    private void postHandle(Object proxy, byte[] rawResult, Class<?> clazz, Method method, Object[] args, Annotation[] annotations, InnerRpcInvokeContext invokeContext) {
        final Object obj = proxy;
        final byte[] bArr = rawResult;
        final Class<?> cls = clazz;
        final Method method2 = method;
        final Object[] objArr = args;
        handleAnnotations(annotations, new Handle() {
            public boolean handle(RpcInterceptor rpcInterceptor, Annotation annotation) {
                LogCatUtil.info(RpcInvoker.TAG, " Start execute postHandle. rpcInterceptor is " + rpcInterceptor.getClass().getName());
                if (rpcInterceptor.postHandle(obj, RpcInvoker.RETURN_VALUE, bArr, cls, method2, objArr, annotation)) {
                    return true;
                }
                throw new RpcException(Integer.valueOf(9), rpcInterceptor + "postHandle stop this call.");
            }
        });
        RpcInvokerUtil.postHandleForBizInterceptor(proxy, rawResult, clazz, method, args, annotations, invokeContext, RETURN_VALUE);
        RpcInvokerUtil.postHandleForPacketSize(method, args, invokeContext);
    }

    private void exceptionHandle(Object proxy, byte[] rawResult, Class<?> clazz, Method method, Object[] args, Annotation[] annotations, RpcException exception, InnerRpcInvokeContext invokeContext, long startTime) {
        final Object obj = proxy;
        final byte[] bArr = rawResult;
        final Class<?> cls = clazz;
        final Method method2 = method;
        final Object[] objArr = args;
        final RpcException rpcException = exception;
        if (handleAnnotations(annotations, new Handle() {
            public boolean handle(RpcInterceptor rpcInterceptor, Annotation annotation) {
                LogCatUtil.info(RpcInvoker.TAG, " Start execute exceptionHandle. rpcInterceptor is " + rpcInterceptor.getClass().getName());
                if (rpcInterceptor.exceptionHandle(obj, RpcInvoker.RETURN_VALUE, bArr, cls, method2, objArr, rpcException, annotation)) {
                    LogCatUtil.error((String) RpcInvoker.TAG, rpcException + " need process");
                    return true;
                }
                LogCatUtil.error((String) RpcInvoker.TAG, rpcException + " need not process");
                return false;
            }
        })) {
            printAllTimeLog(method, RpcInvokerUtil.getOperationTypeValue(method, args), startTime);
            throw exception;
        }
    }

    private void preHandle(Object proxy, Class<?> clazz, Method method, Object[] args, Annotation[] annotations, InnerRpcInvokeContext invokeContext) {
        final Object obj = proxy;
        final Class<?> cls = clazz;
        final Method method2 = method;
        final Object[] objArr = args;
        handleAnnotations(annotations, new Handle() {
            public boolean handle(RpcInterceptor rpcInterceptor, Annotation annotation) {
                LogCatUtil.info(RpcInvoker.TAG, " Start execute preHandle. rpcInterceptor is " + rpcInterceptor.getClass().getName());
                if (rpcInterceptor.preHandle(obj, RpcInvoker.RETURN_VALUE, new byte[0], cls, method2, objArr, annotation, RpcInvoker.EXT_PARAM)) {
                    return true;
                }
                throw new RpcException(Integer.valueOf(9), rpcInterceptor + "preHandle stop this call.");
            }
        });
        RpcInvokerUtil.preHandleForBizInterceptor(proxy, clazz, method, args, invokeContext, EXT_PARAM, RETURN_VALUE);
        RpcInvokerUtil.mockRpcLimit(this.mRpcFactory.getContext(), method, args);
    }

    private Response singleCall(Method method, Object[] args, String operationTypeValue, int id, InnerRpcInvokeContext invokeContext, RPCProtoDesc protoDesc) {
        checkLogin(method, invokeContext);
        Serializer serializer = getSerializer(method, args, operationTypeValue, id, invokeContext, protoDesc);
        LogCatUtil.verbose(TAG, "operationType=" + operationTypeValue + ",serializerClass=" + serializer.getClass().getName());
        if (EXT_PARAM.get() != null) {
            serializer.setExtParam(EXT_PARAM.get());
        }
        byte[] body = serializer.packet();
        HttpCaller caller = new HttpCaller(this.mRpcFactory.getConfig(), method, id, operationTypeValue, body, this.serializerFactory.getContentType(protoDesc), this.mRpcFactory.getContext(), invokeContext);
        addInfo2Caller(method, serializer, caller, operationTypeValue, body, invokeContext);
        Response response = (Response) caller.call();
        EXT_PARAM.set(null);
        LogCatUtil.verbose(TAG, " operationType = " + operationTypeValue);
        return response;
    }

    @TargetApi(8)
    private SignData getSignData(String operationType, byte[] body, String ts, InnerRpcInvokeContext invokeContext) {
        StringBuffer signPlain = new StringBuffer();
        signPlain.append("Operation-Type=").append(operationType).append("&");
        signPlain.append("Request-Data=").append(Base64.encodeToString(body, 2)).append("&");
        signPlain.append("Ts=").append(ts);
        String content = signPlain.toString();
        if (MiscUtils.isDebugger(getRpcFactory().getContext())) {
            LogCatUtil.debug(TAG, "sign content: " + content);
        }
        return RpcSignUtil.signature(this.mRpcFactory.getContext(), invokeContext.appKey, isReq2Online(invokeContext), content, MiscUtils.isAlipayGW(invokeContext.gwUrl));
    }

    private void checkLogin(Method method, InnerRpcInvokeContext invokeContext) {
        try {
            OperationType operTypeAnn = (OperationType) method.getAnnotation(OperationType.class);
            if (operTypeAnn != null && TransportStrategy.isAlipayUrl(invokeContext.gwUrl) && !invokeContext.bgRpc.booleanValue() && ((CheckLogin) method.getAnnotation(CheckLogin.class)) != null) {
                String cookie = CookieAccessHelper.getCookie(invokeContext.gwUrl, getRpcFactory().getContext());
                if (TextUtils.isEmpty(cookie)) {
                    LogCatUtil.warn((String) TAG, "CheckLogin_prejudge: cookie is empty  API=[" + operTypeAnn.value() + "]");
                    throw new RpcException(Integer.valueOf(2000), (String) "登录超时，请重新登录:登录超时，请重新登录");
                } else if (!cookie.contains("ALIPAYJSESSIONID")) {
                    LogCatUtil.warn((String) TAG, "CheckLogin_prejudge: cookie not contains ALIPAYJSESSIONID!  API=[" + operTypeAnn.value() + "]");
                    throw new RpcException(Integer.valueOf(2000), (String) "登录超时，请重新登录:登录超时，请重新登录");
                }
            }
        } catch (Exception e) {
        }
    }

    private void addInfo2Caller(Method method, Serializer serializer, RpcCaller caller, String operationType, byte[] body, InnerRpcInvokeContext invokeContext) {
        if (caller instanceof HttpCaller) {
            HttpCaller httpCaller = (HttpCaller) caller;
            if ((serializer instanceof SignJsonSerializer) || TextUtils.equals(serializer.getClass().getName(), JsonSerializer.class.getName())) {
                if (serializer instanceof SignJsonSerializer) {
                    SignJsonSerializer sser = (SignJsonSerializer) serializer;
                    httpCaller.setReqDataDigest(sser.getRequestDataDigest());
                    httpCaller.setSignData(sser.getSignData());
                } else {
                    httpCaller.setReqDataDigest(((JsonSerializer) serializer).getRequestDataDigest());
                }
                httpCaller.setContentType("application/x-www-form-urlencoded");
                httpCaller.setRpcVersion("1");
                return;
            }
            if (serializer instanceof JsonSerializerV2) {
                httpCaller.setReqDataDigest(((JsonSerializerV2) serializer).getRequestDataDigest());
                httpCaller.setContentType("application/json");
            } else if (serializer instanceof SimpleRpcPBSerializer) {
                httpCaller.setContentType("application/protobuf");
                httpCaller.setReqDataDigest(((SimpleRpcPBSerializer) serializer).getRequestDataDigest());
            } else if (serializer instanceof PBSerializer) {
                httpCaller.setContentType("application/protobuf");
                httpCaller.setReqDataDigest(((PBSerializer) serializer).getRequestDataDigest());
            }
            httpCaller.setExtObjectParam(EXT_PARAM.get());
            httpCaller.setRpcVersion("2");
            httpCaller.setScene(getScene());
            String timeStampStr = GtsUtils.get64HexCurrentTimeMillis();
            httpCaller.setTimeStamp(timeStampStr);
            if (isNeedSign(method)) {
                httpCaller.setSignData(getSignData(operationType, body, timeStampStr, invokeContext));
            }
        }
    }

    /* access modifiers changed from: protected */
    public Serializer getSerializer(Method method, Object[] args, String operationTypeValue, int id, InnerRpcInvokeContext invokeContext, RPCProtoDesc protoDesc) {
        return this.serializerFactory.getSerializer(id, operationTypeValue, method, args, getScene(), invokeContext, protoDesc);
    }

    public void batchBegin() {
        this.mMode = 1;
    }

    public FutureTask<?> batchCommit() {
        this.mMode = 0;
        return null;
    }

    public static void addProtocolArgs(String key, String value) {
        if (EXT_PARAM.get() == null) {
            EXT_PARAM.set(new HashMap());
        }
        EXT_PARAM.get().put(key, value);
    }

    public boolean isReq2Online(InnerRpcInvokeContext invokeContext) {
        if (TextUtils.isEmpty(invokeContext.gwUrl)) {
            LogCatUtil.warn((String) TAG, (String) "handler.getConfig().getUrl() is null");
            return false;
        }
        try {
            URL url = new URL(invokeContext.gwUrl);
            if (!url.getHost().contains("mobilegw") || !url.getHost().contains(BehavorReporter.PROVIDE_BY_ALIPAY) || url.getHost().lastIndexOf("alipay.com") == -1) {
                return false;
            }
            return true;
        } catch (MalformedURLException e) {
            LogCatUtil.warn((String) TAG, (Throwable) e);
            return false;
        }
    }

    public static boolean isNeedSign(Method method) {
        SignCheck signCheck = (SignCheck) method.getAnnotation(SignCheck.class);
        if (signCheck != null && TextUtils.equals(signCheck.value(), BQCCameraParam.VALUE_NO)) {
            return false;
        }
        return true;
    }

    public static boolean perfLog(RpcException rpcException, String operationType) {
        try {
            if (!isNeedPerfLog(rpcException)) {
                return false;
            }
            MonitorLoggerModel pf = new MonitorLoggerModel();
            pf.setSubType("RPC");
            pf.setParam1("RPC_ERROR");
            pf.setParam2("DEBUG");
            pf.setParam3("-");
            pf.getExtPramas().put("ERR_CODE", String.valueOf(rpcException.getCode()));
            pf.getExtPramas().put("ERR_MSG", MiscUtils.getRootCause(rpcException).toString());
            pf.getExtPramas().put("API", operationType);
            LogCatUtil.debug(TAG, pf.toString());
            MonitorInfoUtil.record(pf);
            return true;
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "[perfLog] Exception: " + ex.toString(), ex);
            return false;
        }
    }

    private static boolean isNeedPerfLog(RpcException rpcException) {
        if (rpcException.getCode() == 10 || rpcException.getCode() == 9 || rpcException.getCode() == 13 || rpcException.getCode() == 2 || rpcException.getCode() == 1 || rpcException.getCode() == 15 || rpcException.getCode() == 4 || rpcException.getCode() == 5 || rpcException.isServerError()) {
            return true;
        }
        return false;
    }

    public SerializerFactory getSerializerFactory() {
        return this.serializerFactory;
    }

    public static boolean checkMainThread() {
        return Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper();
    }
}
