package com.alipay.mobile.tinyappcustom.provider;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.apphandler.H5TinyAppDebugMode;
import com.alipay.mobile.nebula.provider.H5BugMeRpcAuthProvider;
import com.alipay.mobile.nebula.provider.H5BugMeRpcAuthProvider.AuthRpcCallback;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcustom.b.c;

public class H5BugMeRpcAuthProviderImpl implements H5BugMeRpcAuthProvider {
    public static final String TAG = "H5BugMeRpcAuthProviderImpl";
    private static final String VALID_MEMBER = "com.alipay.hpmweb.validMember";

    private class RpcAuthRunnable implements Runnable {
        private String mAppId;
        private AuthRpcCallback mCallback;
        private String mNbscene;
        private String mTarget;
        private String mToken;

        RpcAuthRunnable(AuthRpcCallback listen, String token, String target, String appId, String nbscene) {
            this.mCallback = listen;
            this.mToken = token;
            this.mTarget = target;
            this.mAppId = appId;
            this.mNbscene = nbscene;
        }

        public void run() {
            JSONArray requestData = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "method", (Object) "bugme");
            JSONObject tokenJson = new JSONObject();
            tokenJson.put((String) "token", (Object) this.mToken);
            if (!TextUtils.isEmpty(this.mTarget)) {
                tokenJson.put((String) "target", (Object) this.mTarget);
            }
            if (!TextUtils.isEmpty(this.mAppId)) {
                tokenJson.put((String) "appId", (Object) this.mAppId);
            }
            if (!TextUtils.isEmpty(this.mNbscene)) {
                tokenJson.put((String) "nbscene", (Object) this.mNbscene);
            }
            jsonObject.put((String) "params", (Object) tokenJson);
            requestData.add(jsonObject);
            try {
                H5Log.d(H5BugMeRpcAuthProviderImpl.TAG, "req:" + requestData.toJSONString());
                String authRpcName = H5BugMeRpcAuthProviderImpl.VALID_MEMBER;
                H5ApiManager h5ApiManager = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
                if (h5ApiManager != null) {
                    authRpcName = h5ApiManager.getDebugAuthRpcName();
                }
                JSONObject joResponse = H5Utils.parseObject(c.a(authRpcName, requestData.toJSONString(), "", true, new JSONObject(), null));
                H5Log.d(H5BugMeRpcAuthProviderImpl.TAG, "rep:" + joResponse);
                JSONObject result = H5Utils.getJSONObject(joResponse, "result", null);
                if (this.mCallback != null) {
                    if (result == null || result.isEmpty()) {
                        this.mCallback.onResponse(false, false, null);
                        return;
                    }
                    boolean pass = H5Utils.getBoolean(result, (String) "pass", false);
                    boolean isSuperUser = H5Utils.getBoolean(result, (String) "isSuperUser", false);
                    JSONArray domainWhiteList = H5Utils.getJSONArray(result, H5TinyAppDebugMode.KEY_WHITE_LIST, new JSONArray(0));
                    int size = domainWhiteList.size();
                    String[] dwArray = new String[size];
                    for (int i = 0; i < size; i++) {
                        dwArray[i] = (String) domainWhiteList.get(i);
                    }
                    this.mCallback.onResponse(pass, isSuperUser, dwArray);
                }
            } catch (Exception e) {
                this.mCallback.onResponse(false, false, null);
                H5Log.e((String) H5BugMeRpcAuthProviderImpl.TAG, (Throwable) e);
            }
        }
    }

    public void rpcAuth(String appId, String nbscene, String target, String token, AuthRpcCallback authRpcCallback) {
        if (authRpcCallback != null) {
            H5Utils.getExecutor("RPC").execute(new RpcAuthRunnable(authRpcCallback, token, target, appId, nbscene));
        }
    }
}
