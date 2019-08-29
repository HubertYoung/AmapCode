package com.mpaas.nebula.provider;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.provider.H5PreRpcProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.nebula.rpc.H5RpcUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WalletPreRpcProvider implements H5PreRpcProvider {
    private static Map<String, JSONObject> a = new HashMap();
    private static Map<String, H5BridgeContext> b = new HashMap();
    private static Map<String, Boolean> c = new HashMap();
    private static Object d = new Object();
    private Bundle e;
    private RpcRequest f;

    private class RpcRequest implements Runnable {
        private JSONObject a;
        private String b;

        public RpcRequest(JSONObject preRpcJo, String md5Key) {
            this.a = preRpcJo;
            this.b = md5Key;
        }

        public void run() {
            String operationType = H5Utils.getString(this.a, (String) TransportConstants.KEY_OPERATION_TYPE);
            String requestData = H5Utils.getJSONArray(this.a, "requestData", null).toJSONString();
            String gateway = H5Utils.getString(this.a, (String) "gateway");
            String appKey = null;
            if (!TextUtils.isEmpty(gateway)) {
                appKey = H5RpcUtil.getAppKey(gateway);
            }
            try {
                String response = H5RpcUtil.rpcCall(operationType, requestData, gateway, H5Utils.getBoolean(this.a, (String) MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, true), H5Utils.getJSONObject(this.a, "headers", null), appKey, H5Utils.getBoolean(this.a, (String) "retryable", false), null, 0);
                JSONObject joResponse = H5Utils.parseObject(response);
                if (joResponse == null) {
                    joResponse = new JSONObject();
                    if (!TextUtils.isEmpty(response) && response.startsWith("\"") && response.endsWith("\"")) {
                        response = response.substring(1, response.length() - 1).replaceAll("\\\\", "");
                    }
                    joResponse.put((String) "resData", (Object) response);
                }
                H5Log.d("WalletPreRpcProvider", "joResponse is " + joResponse.toJSONString());
                WalletPreRpcProvider.this.handleResultPool(this.b, joResponse);
            } catch (ExecutionException e) {
                H5Log.e("WalletPreRpcProvider", "exception detail", e);
                WalletPreRpcProvider.this.handleResultPool(this.b, 10);
            } catch (InterruptedException e2) {
                H5Log.e("WalletPreRpcProvider", "exception detail", e2);
                WalletPreRpcProvider.this.handleResultPool(this.b, 11);
            } catch (Exception e3) {
                H5Log.e("WalletPreRpcProvider", "exception detail", e3);
                WalletPreRpcProvider.this.handleResultPool(this.b, 10);
            }
        }
    }

    public void setStartParams(Bundle startParams) {
        this.e = startParams;
    }

    public JSONObject getResult(String md5Key, H5BridgeContext bridgeContext) {
        JSONObject rpcResultJo;
        synchronized (d) {
            rpcResultJo = a.get(md5Key);
            if (rpcResultJo != null) {
                H5Log.d("WalletPreRpcProvider", "getResult has prerpc");
            } else {
                if (c.get(md5Key).booleanValue()) {
                    H5Log.d("WalletPreRpcProvider", "get Result no prerpc");
                    if (!b.containsKey(md5Key)) {
                        b.put(md5Key, bridgeContext);
                    }
                }
                rpcResultJo = null;
            }
        }
        return rpcResultJo;
    }

    public boolean getPreFlag(String md5Key) {
        if (c.get(md5Key) == null) {
            return false;
        }
        return c.get(md5Key).booleanValue();
    }

    public void clearPreState(String md5Key) {
        a.remove(md5Key);
        b.remove(md5Key);
        c.remove(md5Key);
    }

    public void clearPreAll() {
        a.clear();
        b.clear();
        c.clear();
    }

    public boolean enableUsePreRpc(Bundle bundle) {
        try {
            if (!TextUtils.isEmpty(URLDecoder.decode(H5Utils.getString(this.e, (String) "preRpc"), "UTF-8"))) {
                H5Log.d("WalletPreRpcProvider", "preRpcStr is null return");
                return true;
            }
        } catch (Exception e2) {
            H5Log.e("WalletPreRpcProvider", "urldecoder exception", e2);
        }
        return false;
    }

    public void preRpc() {
        String preRpcStr = null;
        try {
            preRpcStr = URLDecoder.decode(H5Utils.getString(this.e, (String) "preRpc"), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            H5Log.e("WalletPreRpcProvider", "urldecoder exception", e2);
        }
        if (TextUtils.isEmpty(preRpcStr)) {
            H5Log.d("WalletPreRpcProvider", "preRpcStr is null return");
            return;
        }
        JSONObject preRpcJo = H5Utils.parseObject(preRpcStr);
        if (preRpcJo == null) {
            H5Log.d("WalletPreRpcProvider", "preRpcJo is null return");
        } else if (TextUtils.isEmpty(H5Utils.getString(preRpcJo, (String) TransportConstants.KEY_OPERATION_TYPE))) {
            H5Log.d("WalletPreRpcProvider", "operationType is null return");
        } else {
            String md5Key = H5SecurityUtil.getMD5(preRpcStr);
            H5Log.d("WalletPreRpcProvider", "in WalletPreRpcProvider preRpcStr is " + preRpcStr + ", md5 is " + md5Key);
            c.put(md5Key, Boolean.valueOf(true));
            this.f = new RpcRequest(preRpcJo, md5Key);
            H5Utils.getExecutor("RPC").execute(this.f);
            H5LogProvider walletLogProvider = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
            if (walletLogProvider != null) {
                walletLogProvider.log("H5_PRERPC_SEND", preRpcStr, String.format("appId=%s^version=%s^publicId=%s^url=%s", new Object[]{H5Utils.getString(this.e, (String) "appId"), H5Utils.getString(this.e, (String) "appVersion"), H5Utils.getString(this.e, (String) H5Param.PUBLIC_ID), H5Utils.getString(this.e, (String) "url")}), null, null);
            }
        }
    }

    public void handleResultPool(String md5Key, JSONObject response) {
        synchronized (d) {
            H5BridgeContext h5BridgeContext = b.get(md5Key);
            if (h5BridgeContext == null) {
                H5Log.d("WalletPreRpcProvider", "handleResultPool resultPool put response");
                a.put(md5Key, response);
            } else {
                H5Log.d("WalletPreRpcProvider", "handleResultPool h5BridgeContext sendBridgeResult");
                h5BridgeContext.sendBridgeResult(response);
                clearPreState(md5Key);
            }
        }
    }

    public void handleResultPool(String md5Key, int error) {
        synchronized (d) {
            H5BridgeContext h5BridgeContext = b.get(md5Key);
            if (h5BridgeContext == null) {
                H5Log.d("WalletPreRpcProvider", "handleResultPool resultPool put response error");
                JSONObject param = new JSONObject();
                param.put((String) "error", (Object) Integer.valueOf(error));
                a.put(md5Key, param);
            } else {
                H5Log.d("WalletPreRpcProvider", "handleResultPool h5BridgeContext sendBridgeResult error");
                h5BridgeContext.sendBridgeResult("error", Integer.valueOf(error));
                clearPreState(md5Key);
            }
        }
    }
}
