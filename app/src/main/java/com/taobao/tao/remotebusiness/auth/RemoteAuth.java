package com.taobao.tao.remotebusiness.auth;

import android.support.annotation.NonNull;
import com.taobao.tao.remotebusiness.RequestPool;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.intf.Mtop;

public class RemoteAuth {
    private static final String TAG = "mtopsdk.RemoteAuth";
    private static Map<String, IRemoteAuth> mtopAuthMap = new ConcurrentHashMap();

    static class AuthHandler implements AuthListener {
        @NonNull
        private AuthParam authParam;
        @NonNull
        private Mtop mtopInstance;

        public AuthHandler(@NonNull Mtop mtop, @NonNull AuthParam authParam2) {
            this.mtopInstance = mtop;
            this.authParam = authParam2;
        }

        public void onAuthSuccess() {
            String str = this.authParam.openAppKey != null ? this.authParam.openAppKey : "DEFAULT_AUTH";
            String a = fdd.a(this.mtopInstance.b, str);
            String authToken = RemoteAuth.getAuthToken(this.mtopInstance, this.authParam);
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder("auth success.authToken=");
                sb.append(authToken);
                sb.append(",key=");
                sb.append(a);
                TBSdkLog.b(RemoteAuth.TAG, sb.toString());
            }
            fgy.a(a, "accessToken", authToken);
            RequestPool.retryAllRequest(this.mtopInstance, str);
        }

        public void onAuthFail(String str, String str2) {
            String str3 = this.authParam.openAppKey != null ? this.authParam.openAppKey : "DEFAULT_AUTH";
            if (TBSdkLog.a(LogEnable.ErrorEnable)) {
                StringBuilder sb = new StringBuilder(64);
                sb.append("[onAuthFail] auth fail,key=");
                sb.append(fdd.a(this.mtopInstance.b, str3));
                sb.append(",code=");
                sb.append(str);
                sb.append(",msg=");
                sb.append(str2);
                TBSdkLog.d(RemoteAuth.TAG, sb.toString());
            }
            RequestPool.failAllRequest(this.mtopInstance, str3, str, str2);
        }

        public void onAuthCancel(String str, String str2) {
            String str3 = this.authParam.openAppKey != null ? this.authParam.openAppKey : "DEFAULT_AUTH";
            if (TBSdkLog.a(LogEnable.ErrorEnable)) {
                StringBuilder sb = new StringBuilder(64);
                sb.append("[onAuthCancel] auth cancel,key=");
                sb.append(fdd.a(this.mtopInstance.b, str3));
                sb.append(",code=");
                sb.append(str);
                sb.append(",msg=");
                sb.append(str2);
                TBSdkLog.d(RemoteAuth.TAG, sb.toString());
            }
            RequestPool.failAllRequest(this.mtopInstance, str3, str, str2);
        }
    }

    @Deprecated
    public static void setAuthImpl(IRemoteAuth iRemoteAuth) {
        setAuthImpl(null, iRemoteAuth);
    }

    public static void authorize(@NonNull Mtop mtop, AuthParam authParam) {
        if (authParam == null) {
            TBSdkLog.d(TAG, "[authorize] authParam is null");
            return;
        }
        IRemoteAuth auth = getAuth(mtop);
        if (auth == null) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b(TAG, "didn't set IRemoteAuth implement. remoteAuth=null");
            }
            return;
        }
        IMtopRemoteAuth iMtopRemoteAuth = null;
        if (auth instanceof IMtopRemoteAuth) {
            iMtopRemoteAuth = (IMtopRemoteAuth) auth;
        }
        if (!(iMtopRemoteAuth != null ? iMtopRemoteAuth.isAuthorizing(authParam) : auth.isAuthorizing())) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b(TAG, "call authorize. ".concat(String.valueOf(authParam)));
            }
            AuthHandler authHandler = new AuthHandler(mtop, authParam);
            if (iMtopRemoteAuth != null) {
                iMtopRemoteAuth.authorize(authParam, authHandler);
            } else {
                auth.authorize(authParam.bizParam, authParam.apiInfo, authParam.failInfo, authParam.showAuthUI, authHandler);
            }
        }
    }

    public static boolean isAuthInfoValid(@NonNull Mtop mtop, AuthParam authParam) {
        if (authParam == null) {
            TBSdkLog.d(TAG, "[isAuthInfoValid] authParam is null");
            return true;
        }
        IRemoteAuth auth = getAuth(mtop);
        if (auth == null) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b(TAG, "didn't set IRemoteAuth implement. remoteAuth=null");
            }
            return true;
        }
        IMtopRemoteAuth iMtopRemoteAuth = null;
        if (auth instanceof IMtopRemoteAuth) {
            iMtopRemoteAuth = (IMtopRemoteAuth) auth;
        }
        if (iMtopRemoteAuth != null ? iMtopRemoteAuth.isAuthorizing(authParam) : auth.isAuthorizing()) {
            return false;
        }
        return iMtopRemoteAuth != null ? iMtopRemoteAuth.isAuthInfoValid(authParam) : auth.isAuthInfoValid();
    }

    public static String getAuthToken(@NonNull Mtop mtop, AuthParam authParam) {
        IMtopRemoteAuth iMtopRemoteAuth = null;
        if (authParam == null) {
            TBSdkLog.d(TAG, "[getAuthToken] authParam is null");
            return null;
        }
        IRemoteAuth auth = getAuth(mtop);
        if (auth == null) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b(TAG, "didn't set IRemoteAuth implement. remoteAuth=null");
            }
            return null;
        }
        if (auth instanceof IMtopRemoteAuth) {
            iMtopRemoteAuth = (IMtopRemoteAuth) auth;
        }
        return iMtopRemoteAuth != null ? iMtopRemoteAuth.getAuthToken(authParam) : auth.getAuthToken();
    }

    public static void setAuthImpl(@NonNull Mtop mtop, @NonNull IRemoteAuth iRemoteAuth) {
        if (iRemoteAuth != null) {
            String str = mtop == null ? "OPEN" : mtop.b;
            mtopAuthMap.put(str, iRemoteAuth);
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" [setAuthImpl] set remoteAuthImpl=");
                sb.append(iRemoteAuth);
                TBSdkLog.b(TAG, sb.toString());
            }
        }
    }

    private static IRemoteAuth getAuth(@NonNull Mtop mtop) {
        String str = mtop == null ? "OPEN" : mtop.b;
        IRemoteAuth iRemoteAuth = mtopAuthMap.get(str);
        if (iRemoteAuth == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" [getAuth]remoteAuthImpl is null");
            TBSdkLog.d(TAG, sb.toString());
        }
        return iRemoteAuth;
    }
}
