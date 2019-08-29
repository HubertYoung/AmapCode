package com.alipay.mobile.nebula.provider;

public interface H5BugMeRpcAuthProvider {

    public interface AuthRpcCallback {
        void onResponse(boolean z, boolean z2, String[] strArr);
    }

    void rpcAuth(String str, String str2, String str3, String str4, AuthRpcCallback authRpcCallback);
}
