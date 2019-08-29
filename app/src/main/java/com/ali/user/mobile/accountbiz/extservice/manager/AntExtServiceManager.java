package com.ali.user.mobile.accountbiz.extservice.manager;

import android.content.Context;
import com.ali.user.mobile.accountbiz.extservice.AUMemCacheService;
import com.ali.user.mobile.accountbiz.extservice.AccountService;
import com.ali.user.mobile.accountbiz.extservice.AuthService;
import com.ali.user.mobile.accountbiz.extservice.DeviceService;
import com.ali.user.mobile.accountbiz.extservice.DexInfoService;
import com.ali.user.mobile.accountbiz.extservice.LoginService;
import com.ali.user.mobile.accountbiz.extservice.LogoutService;
import com.ali.user.mobile.accountbiz.extservice.RSAService;
import com.ali.user.mobile.accountbiz.extservice.RouterService;
import com.ali.user.mobile.accountbiz.extservice.SecurityInitService;
import com.ali.user.mobile.accountbiz.extservice.ServerConfigService;
import com.ali.user.mobile.accountbiz.extservice.UserInfoService;
import com.ali.user.mobile.accountbiz.extservice.impl.AccountServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.impl.AuthServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.impl.DeviceServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.impl.DexInfoServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.impl.LoginServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.impl.RSAServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.impl.SecurityInitServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.impl.ServerConfigServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.impl.UserInfoServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.impl.mem.AUMemCacheServiceImpl;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.util.ReflectUtils;

public class AntExtServiceManager {
    private static final String TAG = "AntExtServiceManager";

    public static AccountService getAccountService(Context context) {
        return AccountServiceImpl.getInstance(context);
    }

    public static AuthService getAuthService(Context context) {
        return AuthServiceImpl.getInstance(context);
    }

    public static DeviceService getDeviceService(Context context) {
        return DeviceServiceImpl.getInstance(context);
    }

    public static DexInfoService getDexInfoService(Context context) {
        return DexInfoServiceImpl.getInstance(context);
    }

    public static LoginService getLoginService(Context context) {
        return LoginServiceImpl.getInstance(context);
    }

    public static LogoutService getLogoutService(Context context) {
        try {
            Object a = ReflectUtils.a("com.ali.user.mobile.biz.LogoutServiceImpl", new Class[]{Context.class}, new Object[]{context});
            AliUserLog.c(TAG, "reflect get LogoutService success");
            return (LogoutService) a;
        } catch (Throwable th) {
            AliUserLog.a(TAG, "reflect get LogoutService error", th);
            return null;
        }
    }

    public static RouterService getRouterService(Context context) {
        try {
            Object a = ReflectUtils.a("com.ali.user.mobile.biz.SecurityStartIntercept", new Class[]{Context.class}, new Object[]{context});
            AliUserLog.c(TAG, "reflect get getRouterService success");
            return (RouterService) a;
        } catch (Throwable th) {
            AliUserLog.a(TAG, "reflect get RouterService error", th);
            return null;
        }
    }

    public static RSAService getRSAService(Context context) {
        return RSAServiceImpl.getInstance(context);
    }

    public static SecurityInitService getSecurityInitService(Context context) {
        return SecurityInitServiceImpl.getInstance(context);
    }

    public static UserInfoService getUserInfoService(Context context) {
        return UserInfoServiceImpl.getInstance(context);
    }

    public static ServerConfigService getConfigService(Context context) {
        return ServerConfigServiceImpl.getInstance(context);
    }

    public static AUMemCacheService getMemCacheService() {
        return AUMemCacheServiceImpl.getInstance();
    }
}
