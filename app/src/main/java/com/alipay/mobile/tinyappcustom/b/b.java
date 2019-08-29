package com.alipay.mobile.tinyappcustom.b;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.common.logging.api.monitor.Performance;
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
import java.util.Map;

/* compiled from: H5RpcRequest */
public final class b implements Runnable {
    H5Event a = null;
    H5BridgeContext b;
    boolean c = false;
    private H5PreRpcProvider d;
    private String e;
    private String f;
    private int g = 0;
    private boolean h = false;
    private String i = "";

    public b(H5Event event, H5BridgeContext bridgeContext, H5PreRpcProvider h5PreRpcProvider) {
        this.a = event;
        this.b = bridgeContext;
        this.d = h5PreRpcProvider;
        this.c = false;
    }

    public final void run() {
        String requestData;
        JSONObject joResponse;
        JSONObject rpcParam = this.a.getParam();
        H5CoreNode coreNode = this.a.getTarget();
        if (!(coreNode instanceof H5Page)) {
            this.b.sendError(this.a, Error.INVALID_PARAM);
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
        H5Utils.getBoolean(rpcParam, (String) "httpGet", false);
        this.f = operationType;
        String type = H5Utils.getString(rpcParam, (String) "type");
        boolean isPbFormat = c.b(type);
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
            appKey = c.a(gateway);
        }
        JSONObject headers = H5Utils.getJSONObject(rpcParam, "headers", null);
        boolean compress = H5Utils.getBoolean(rpcParam, (String) MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, true);
        boolean disableLimitView = H5Utils.getBoolean(rpcParam, (String) "disableLimitView", false);
        H5Utils.getBoolean(rpcParam, (String) "retryable", false);
        boolean getResponse = H5Utils.getBoolean(rpcParam, (String) "getResponse", false);
        if (!(!this.c || h5Page == null || headers == null)) {
            headers.put((String) "OPEN_RPC_REQUEST_URL", (Object) h5Page.getUrl());
        }
        int timeout = H5Utils.getInt(rpcParam, (String) "timeout", 0);
        if (TextUtils.isEmpty(operationType)) {
            this.b.sendBridgeResult("error", Integer.valueOf(2));
            return;
        }
        Bundle startParams = h5Page.getParams();
        rpcParam.remove("funcName");
        String md5Key = H5SecurityUtil.getMD5(rpcParam.toJSONString());
        if (this.d != null && this.d.getPreFlag(md5Key)) {
            if (!md5Key.equals(this.e)) {
                this.e = md5Key;
                H5Log.d("H5RpcRequest", "in H5RpcPlugin preRpcStr is " + rpcParam.toJSONString() + ", md5 is " + md5Key);
                JSONObject jsonObject = this.d.getResult(md5Key, this.b);
                if (jsonObject != null) {
                    this.b.sendBridgeResult(jsonObject);
                    this.d.clearPreState(md5Key);
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
            H5Utils.getInt(rpcParam, (String) "signType", -1);
            a h5Response = c.a(operationType, requestData, gateway, compress, headers, appKey, h5Page, timeout, type);
            String response = h5Response.b();
            JSONObject tempResponse = H5Utils.parseObject(response);
            if (isPbFormat || tempResponse == null) {
                joResponse = new JSONObject();
                if (!TextUtils.isEmpty(response) && response.startsWith("\"") && response.endsWith("\"")) {
                    response = response.substring(1, response.length() - 1).replaceAll("\\\\", "");
                }
                joResponse.put((String) "resData", (Object) response);
                if (getResponse) {
                    joResponse.put((String) Performance.KEY_LOG_HEADER, (Object) a(h5Response.a()));
                }
            } else if (getResponse) {
                joResponse = new JSONObject();
                joResponse.put((String) Performance.KEY_LOG_HEADER, (Object) a(h5Response.a()));
                joResponse.put((String) "resData", (Object) tempResponse);
            } else {
                joResponse = tempResponse;
            }
            this.b.sendBridgeResult(joResponse);
            if (this.c && joResponse != null && !joResponse.isEmpty()) {
                String toast = H5Utils.getString(joResponse, (String) "toastMemo");
                if (!TextUtils.isEmpty(toast)) {
                    final String str = toast;
                    AnonymousClass1 r0 = new Runnable() {
                        public final void run() {
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
            this.g = 2;
            this.h = true;
            String message = exception.getMessage();
            if (code < 1000) {
                message = "网络繁忙或未连接";
            }
            JSONObject object = new JSONObject();
            if (code != 1002 || disableLimitView) {
                object.put((String) "error", (Object) Integer.valueOf(code));
                object.put((String) "errorMessage", (Object) message);
                this.b.sendBridgeResult(object);
            } else {
                object.put((String) "error", (Object) Integer.valueOf(100201));
                object.put((String) "errorMessage", (Object) message);
                this.b.sendBridgeResult(object);
                throw exception;
            }
        } catch (InterruptedException e2) {
            H5Log.e("H5RpcRequest", "exception detail", e2);
            JSONObject object2 = new JSONObject();
            object2.put((String) "error", (Object) "11");
            object2.put((String) "errorMessage", (Object) e2.toString());
            this.b.sendBridgeResult(object2);
            this.g = 2;
            this.h = true;
            this.i = "11";
        } catch (Throwable e3) {
            H5Log.e("H5RpcRequest", "exception detail", e3);
            JSONObject object3 = new JSONObject();
            object3.put((String) "error", (Object) "10");
            object3.put((String) "errorMessage", (Object) e3.toString());
            this.b.sendBridgeResult(object3);
            this.g = 2;
            this.h = true;
            this.i = "10";
        }
        param.put((String) "failCode", (Object) Integer.valueOf(this.g));
        param.put((String) "errorCode", (Object) this.i);
        param.put((String) TransportConstants.KEY_OPERATION_TYPE, (Object) this.f);
        if (h5Page != null && this.h) {
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
