package com.taobao.tao.remotebusiness.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.intf.Mtop;

public class RemoteLogin {
    private static final String DEFAULT_USERINFO = "DEFAULT";
    public static final String TAG = "mtopsdk.RemoteLogin";
    private static Map<String, IRemoteLogin> mtopLoginMap = new ConcurrentHashMap();

    public static void login(@NonNull Mtop mtop, @Nullable String str, boolean z, Object obj) {
        IRemoteLogin login = getLogin(mtop);
        String a = fdd.a(mtop == null ? "INNER" : mtop.b, fdd.b(str) ? DEFAULT_USERINFO : str);
        String str2 = null;
        MultiAccountRemoteLogin multiAccountRemoteLogin = login instanceof MultiAccountRemoteLogin ? (MultiAccountRemoteLogin) login : null;
        if (!DEFAULT_USERINFO.equals(str)) {
            str2 = str;
        }
        if (multiAccountRemoteLogin != null ? multiAccountRemoteLogin.isLogining(str2) : login.isLogining()) {
            if (TBSdkLog.a(LogEnable.WarnEnable)) {
                StringBuilder sb = new StringBuilder();
                sb.append(a);
                sb.append(" [login] loginsdk is logining");
                TBSdkLog.c(TAG, sb.toString());
            }
            return;
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append(" [login]call login");
            TBSdkLog.b(TAG, sb2.toString());
        }
        if (obj != null && (login instanceof DefaultLoginImpl)) {
            ((DefaultLoginImpl) login).setSessionInvalid(obj);
        }
        LoginHandler instance = LoginHandler.instance(mtop, str);
        if (multiAccountRemoteLogin != null) {
            multiAccountRemoteLogin.login(str2, instance, z);
        } else {
            login.login(instance, z);
        }
        instance.sendEmptyMessageDelayed(LoginHandler.LOGIN_TIMEOUT, 20000);
    }

    public static void setSessionInvalid(@NonNull Mtop mtop, Bundle bundle) {
        IRemoteLogin login = getLogin(mtop);
        if (login instanceof IRemoteLoginAdapter) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                String str = mtop == null ? "INNER" : mtop.b;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" [setSessionInvalid] bundle=");
                sb.append(bundle);
                TBSdkLog.b(TAG, sb.toString());
            }
            ((IRemoteLoginAdapter) login).setSessionInvalid(bundle);
        }
    }

    public static boolean isSessionValid(@NonNull Mtop mtop, @Nullable String str) {
        IRemoteLogin login = getLogin(mtop);
        MultiAccountRemoteLogin multiAccountRemoteLogin = login instanceof MultiAccountRemoteLogin ? (MultiAccountRemoteLogin) login : null;
        if (DEFAULT_USERINFO.equals(str)) {
            str = null;
        }
        if (multiAccountRemoteLogin != null ? multiAccountRemoteLogin.isLogining(str) : login.isLogining()) {
            return false;
        }
        return multiAccountRemoteLogin != null ? multiAccountRemoteLogin.isSessionValid(str) : login.isSessionValid();
    }

    public static LoginContext getLoginContext(@NonNull Mtop mtop, @Nullable String str) {
        IRemoteLogin login = getLogin(mtop);
        if (!(login instanceof MultiAccountRemoteLogin)) {
            return login.getLoginContext();
        }
        if (DEFAULT_USERINFO.equals(str)) {
            str = null;
        }
        return ((MultiAccountRemoteLogin) login).getLoginContext(str);
    }

    @Deprecated
    public static IRemoteLogin getLogin() {
        return getLogin(null);
    }

    @Deprecated
    public static void setLoginImpl(IRemoteLogin iRemoteLogin) {
        setLoginImpl(null, iRemoteLogin);
    }

    @Deprecated
    public static void login(boolean z) {
        login(null, null, z, null);
    }

    @Deprecated
    public static void login(boolean z, Object obj) {
        login(null, null, z, obj);
    }

    @Deprecated
    public static boolean isSessionValid() {
        return isSessionValid(null, null);
    }

    @Deprecated
    public static LoginContext getLoginContext() {
        return getLoginContext(null, null);
    }

    public static IRemoteLogin getLogin(Mtop mtop) {
        Context context;
        String str = mtop == null ? "INNER" : mtop.b;
        IRemoteLogin iRemoteLogin = mtopLoginMap.get(str);
        if (iRemoteLogin == null) {
            synchronized (RemoteLogin.class) {
                iRemoteLogin = mtopLoginMap.get(str);
                if (iRemoteLogin == null) {
                    if (mtop == null) {
                        context = null;
                    } else {
                        context = mtop.c.e;
                    }
                    IRemoteLogin defaultLoginImpl = DefaultLoginImpl.getDefaultLoginImpl(context);
                    if (defaultLoginImpl == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(" [getLogin]loginImpl is null");
                        TBSdkLog.d(TAG, sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(" [getLogin] Login Not Implement!");
                        throw new LoginNotImplementException(sb2.toString());
                    }
                    mtopLoginMap.put(str, defaultLoginImpl);
                    iRemoteLogin = defaultLoginImpl;
                }
            }
        }
        return iRemoteLogin;
    }

    public static void setLoginImpl(@NonNull Mtop mtop, @NonNull IRemoteLogin iRemoteLogin) {
        if (iRemoteLogin != null) {
            String str = mtop == null ? "INNER" : mtop.b;
            mtopLoginMap.put(str, iRemoteLogin);
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" [setLoginImpl] set loginImpl=");
                sb.append(iRemoteLogin);
                TBSdkLog.b(TAG, sb.toString());
            }
        }
    }
}
