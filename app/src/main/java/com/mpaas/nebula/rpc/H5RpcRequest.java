package com.mpaas.nebula.rpc;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.provider.H5PreRpcProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.mpaas.nebula.NebulaBiz;
import com.mpaas.nebula.adapter.R;
import java.util.Map;

public class H5RpcRequest implements Runnable {
    public static final int H5_APP_ERROR_CODE = 2003;
    private H5PreRpcProvider a;
    private String b;
    H5BridgeContext bridgeContext;
    private String c;
    private int d = 0;
    private boolean e = false;
    H5Event event = null;
    private String f = "";
    boolean openRpc = false;

    public H5RpcRequest(H5Event event2, H5BridgeContext bridgeContext2, H5PreRpcProvider h5PreRpcProvider, boolean openRpc2) {
        this.event = event2;
        this.bridgeContext = bridgeContext2;
        this.a = h5PreRpcProvider;
        this.openRpc = openRpc2;
    }

    public void run() {
        String requestData;
        JSONObject joResponse;
        JSONObject rpcParam = this.event.getParam();
        H5CoreNode coreNode = this.event.getTarget();
        if (!(coreNode instanceof H5Page)) {
            this.bridgeContext.sendError(this.event, Error.INVALID_PARAM);
            return;
        }
        final H5Page h5Page = (H5Page) coreNode;
        String url = h5Page.getUrl();
        H5AvailablePageData availablePageData = h5Page.getAvailablePageData();
        if (availablePageData != null) {
            availablePageData.reportReqStart();
        }
        H5Log.d("H5RpcRequest", "rpc url " + url);
        long time = System.currentTimeMillis();
        String operationType = H5Utils.getString(rpcParam, (String) TransportConstants.KEY_OPERATION_TYPE);
        boolean isHttpGet = H5Utils.getBoolean(rpcParam, (String) "httpGet", false);
        this.c = operationType;
        String type = H5Utils.getString(rpcParam, (String) "type");
        boolean isPbFormat = H5RpcUtil.isPbFormat(type);
        if (isPbFormat) {
            requestData = H5Utils.getString(rpcParam, (String) "requestData", (String) null);
        } else {
            requestData = H5Utils.getJSONArray(rpcParam, "requestData", null).toJSONString();
            if (rpcParam != null && !rpcParam.containsKey("requestData")) {
                requestData = "";
            }
        }
        String gateway = H5Utils.getString(rpcParam, (String) "gateway");
        String appKey = null;
        if (!TextUtils.isEmpty(gateway)) {
            appKey = H5RpcUtil.getAppKey(gateway);
        }
        JSONObject headers = H5Utils.getJSONObject(rpcParam, "headers", null);
        boolean compress = H5Utils.getBoolean(rpcParam, (String) MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, true);
        boolean disableLimitView = H5Utils.getBoolean(rpcParam, (String) "disableLimitView", false);
        boolean retryable = H5Utils.getBoolean(rpcParam, (String) "retryable", false);
        boolean getResponse = H5Utils.getBoolean(rpcParam, (String) "getResponse", false);
        if (!(!this.openRpc || h5Page == null || headers == null)) {
            headers.put((String) "OPEN_RPC_REQUEST_URL", (Object) h5Page.getUrl());
        }
        int timeout = H5Utils.getInt(rpcParam, (String) "timeout", 0);
        if (TextUtils.isEmpty(operationType)) {
            this.bridgeContext.sendBridgeResult("error", Integer.valueOf(2));
            return;
        }
        Bundle startParams = h5Page.getParams();
        rpcParam.remove("funcName");
        String md5Key = H5SecurityUtil.getMD5(rpcParam.toJSONString());
        if (this.a != null && this.a.getPreFlag(md5Key)) {
            if (!md5Key.equals(this.b)) {
                this.b = md5Key;
                H5Log.d("H5RpcRequest", "in H5RpcPlugin preRpcStr is " + rpcParam.toJSONString() + ", md5 is " + md5Key);
                JSONObject jsonObject = this.a.getResult(md5Key, this.bridgeContext);
                if (jsonObject != null) {
                    this.bridgeContext.sendBridgeResult(jsonObject);
                    this.a.clearPreState(md5Key);
                    H5Log.d("WalletPreRpcProvider", "in H5RpcPlugin, has prerpc result, return");
                    H5LogProvider h5LogProvider = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
                    if (h5LogProvider != null) {
                        h5LogProvider.log("H5_PRERPC_USE", rpcParam.toJSONString(), String.format("appId=%s^version=%s^publicId=%s^url=%s", new Object[]{H5Utils.getString(startParams, (String) "appId"), H5Utils.getString(startParams, (String) "appVersion"), H5Utils.getString(startParams, (String) H5Param.PUBLIC_ID), H5Utils.getString(startParams, (String) "url")}), null, null);
                        return;
                    }
                    return;
                }
                H5Log.d("WalletPreRpcProvider", "in H5RpcPlugin, prerpc not finish, return");
                H5LogProvider h5LogProvider2 = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
                if (h5LogProvider2 != null) {
                    h5LogProvider2.log("H5_PRERPC_EXISTRPC", rpcParam.toJSONString(), String.format("appId=%s^version=%s^publicId=%s^url=%s", new Object[]{H5Utils.getString(startParams, (String) "appId"), H5Utils.getString(startParams, (String) "appVersion"), H5Utils.getString(startParams, (String) H5Param.PUBLIC_ID), H5Utils.getString(startParams, (String) "url")}), null, null);
                    return;
                }
                return;
            }
        }
        H5Log.d("WalletPreRpcProvider", "in H5RpcPlugin, not has prerpc result, rpc direct");
        JSONObject param = new JSONObject();
        try {
            H5Response h5Response = H5RpcUtil.rpcCall(operationType, requestData, gateway, compress, headers, appKey, retryable, h5Page, timeout, type, isHttpGet, H5Utils.getInt(rpcParam, (String) "signType", -1));
            String response = h5Response.getResponse();
            JSONObject tempResponse = H5Utils.parseObject(response);
            if (isPbFormat || tempResponse == null) {
                joResponse = new JSONObject();
                if (!TextUtils.isEmpty(response) && response.startsWith("\"") && response.endsWith("\"")) {
                    response = response.substring(1, response.length() - 1).replaceAll("\\\\", "");
                }
                joResponse.put((String) "resData", (Object) response);
                if (getResponse) {
                    joResponse.put((String) Performance.KEY_LOG_HEADER, (Object) a(h5Response.getHeaders()));
                }
            } else if (getResponse) {
                joResponse = new JSONObject();
                joResponse.put((String) Performance.KEY_LOG_HEADER, (Object) a(h5Response.getHeaders()));
                joResponse.put((String) "resData", (Object) tempResponse);
            } else {
                joResponse = tempResponse;
            }
            this.bridgeContext.sendBridgeResult(joResponse);
            if (this.openRpc && joResponse != null && !joResponse.isEmpty()) {
                String toast = H5Utils.getString(joResponse, (String) "toastMemo");
                if (!TextUtils.isEmpty(toast)) {
                    final String str = toast;
                    AnonymousClass1 r0 = new Runnable() {
                        public void run() {
                            NebulaBiz.showToast(str, 0, h5Page);
                        }
                    };
                    H5Utils.runOnMain(r0);
                }
            }
        } catch (RpcException exception) {
            int code = exception.getCode();
            String errorCode = String.valueOf(code);
            if ("2".equals(errorCode)) {
                errorCode = "10";
            }
            H5Log.d("H5RpcRequest", "error code:" + errorCode);
            H5Log.e("H5RpcRequest", "exception detail", exception);
            this.d = 2;
            this.e = true;
            String message = exception.getMessage();
            if (code < 1000) {
                message = NebulaBiz.getResources().getString(R.string.h5_error_message);
            }
            JSONObject object = new JSONObject();
            if (code != 1002 || disableLimitView) {
                object.put((String) "error", (Object) Integer.valueOf(code));
                object.put((String) "errorMessage", (Object) message);
                this.bridgeContext.sendBridgeResult(object);
            } else {
                object.put((String) "error", (Object) Integer.valueOf(100201));
                object.put((String) "errorMessage", (Object) message);
                this.bridgeContext.sendBridgeResult(object);
                throw exception;
            }
        } catch (InterruptedException e2) {
            H5Log.e("H5RpcRequest", "exception detail", e2);
            JSONObject object2 = new JSONObject();
            object2.put((String) "error", (Object) "11");
            object2.put((String) "errorMessage", (Object) e2.toString());
            this.bridgeContext.sendBridgeResult(object2);
            this.d = 2;
            this.e = true;
            this.f = "11";
        } catch (Throwable e3) {
            H5Log.e("H5RpcRequest", "exception detail", e3);
            JSONObject object3 = new JSONObject();
            object3.put((String) "error", (Object) "10");
            object3.put((String) "errorMessage", (Object) e3.toString());
            this.bridgeContext.sendBridgeResult(object3);
            this.d = 2;
            this.e = true;
            this.f = "10";
        }
        param.put((String) "failCode", (Object) Integer.valueOf(this.d));
        param.put((String) "errorCode", (Object) this.f);
        param.put((String) TransportConstants.KEY_OPERATION_TYPE, (Object) this.c);
        if (h5Page != null && this.e) {
            h5Page.sendEvent(CommonEvents.H5_RPC_FAILED, param);
        }
        if (availablePageData != null) {
            availablePageData.reportReqEnd();
        }
        H5Log.d("H5RpcRequest", "rpc request time " + (System.currentTimeMillis() - time));
    }

    private static JSONObject a(Map<String, String> headers) {
        if (headers == null || headers.size() == 0) {
            H5Log.d("H5RpcRequest", "response headers == null");
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (String key : headers.keySet()) {
            String value = headers.get(key);
            jsonObject.put(key, (Object) value);
            H5Log.d("H5RpcRequest", "response headers " + key + Token.SEPARATOR + value);
        }
        return jsonObject;
    }
}
