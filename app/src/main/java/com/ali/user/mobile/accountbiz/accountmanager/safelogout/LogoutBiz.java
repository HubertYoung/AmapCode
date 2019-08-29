package com.ali.user.mobile.accountbiz.accountmanager.safelogout;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.account.bean.DeviceInfoBean;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.account.domain.MspDeviceInfoBean;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.accountbiz.extservice.AUMemCacheService;
import com.ali.user.mobile.accountbiz.extservice.AuthService;
import com.ali.user.mobile.accountbiz.extservice.impl.LoginServiceImpl;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.rpc.facade.UserUnifyLoginFacade;
import com.ali.user.mobile.rpc.vo.mobilegw.logout.UserLogoutGWReq;
import com.ali.user.mobile.rpc.vo.mobilegw.logout.UserLogoutGWResult;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class LogoutBiz {
    private UserInfo a;

    public final void a(String str, UserInfo userInfo) {
        UserUnifyLoginFacade userUnifyLoginFacade;
        UserLogoutGWReq userLogoutGWReq;
        AliUserLog.c("LogoutServiceImpl", "logout params ".concat(String.valueOf(str)));
        AuthService authService = AntExtServiceManager.getAuthService(AliUserInit.b());
        if (userInfo == null) {
            userInfo = authService.getUserInfo();
        }
        AliUserLog.c("LogoutServiceImpl", "修改当前登录用户免登状态为不可免登 new");
        this.a = userInfo;
        try {
            if (this.a != null) {
                AliUserLog.c("LogoutServiceImpl", "更改用户状态");
                if (this.a != null) {
                    this.a.setAutoLogin(false);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss:SSS", Locale.getDefault());
                    StringBuilder sb = new StringBuilder();
                    sb.append("false###LogoutServiceImpl");
                    sb.append("###");
                    sb.append(simpleDateFormat.format(new Date()));
                    String sb2 = sb.toString();
                    AUMemCacheService memCacheService = AntExtServiceManager.getMemCacheService();
                    if (memCacheService != null) {
                        memCacheService.put("securityapp", "security", "setAutoLogin", sb2);
                    }
                    this.a.setSessionId(null);
                    this.a.setLoginToken(null);
                    this.a.setTaobaoSid(null);
                    this.a.setExternToken(null);
                    boolean addUserInfo = AntExtServiceManager.getAccountService(AliUserInit.b()).addUserInfo(this.a);
                    if (addUserInfo) {
                        AliUserLog.c("LogoutServiceImpl", "更新本地数据成功");
                    } else {
                        AliUserLog.c("LogoutServiceImpl", "更新本地数据失败");
                    }
                    LoginServiceImpl.writeLoginLog("setAutoLogin=false###LogoutServiceImpl###ret=".concat(String.valueOf(addUserInfo)));
                }
                AliUserLog.c("LogoutServiceImpl", "修改当前登录用户为未登录状态");
                AntExtServiceManager.getAccountService(AliUserInit.b()).setCurrentLoginState("false");
                authService.clearLoginUserInfo();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public void run() {
                        try {
                            SecurityUtil.a();
                        } catch (Throwable th) {
                            AliUserLog.a("LogoutServiceImpl", "resetCookie error", th);
                        }
                    }
                }, 500);
                a();
                AliUserLog.c("LogoutServiceImpl", "logout userinfo not null");
                String logonId = this.a.getLogonId();
                try {
                    AliUserLog.c("LogoutServiceImpl", "更改用户状态开始");
                    AliUserLog.c("LogoutServiceImpl", "请求服务器安全退出");
                    userUnifyLoginFacade = (UserUnifyLoginFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(UserUnifyLoginFacade.class);
                    userLogoutGWReq = new UserLogoutGWReq();
                    userLogoutGWReq.logonId = logonId;
                    userLogoutGWReq.logoutType = str;
                    userLogoutGWReq.productId = AppInfo.getInstance().getProductId();
                    userLogoutGWReq.productVersion = AppInfo.getInstance().getProductVersion();
                    userLogoutGWReq.externParams = new HashMap();
                    userLogoutGWReq.externParams.put("userId", this.a.getUserId());
                    userLogoutGWReq.externParams.put("currentOperateMobile", OutsideConfig.h());
                    userLogoutGWReq.externParams.put("isPrisonBreak", String.valueOf(OutsideConfig.i()));
                    userLogoutGWReq.externParams.put("isTrojan", String.valueOf(OutsideConfig.j()));
                    userLogoutGWReq.externParams.put("phoneModel", DeviceInfo.a().j());
                    userLogoutGWReq.externParams.put("insideModel", "insidePreview");
                    userLogoutGWReq.externParams.put("mspType", "insideSdk");
                    APSecuritySdk instance = APSecuritySdk.getInstance(AliUserInit.b());
                    String str2 = "";
                    if (instance != null) {
                        TokenResult tokenResult = instance.getTokenResult();
                        if (tokenResult != null) {
                            str2 = tokenResult.apdid;
                        }
                    }
                    userLogoutGWReq.externParams.put("apdId", str2);
                    DeviceInfoBean queryDeviceInfo = AntExtServiceManager.getDeviceService(AliUserInit.b()).queryDeviceInfo();
                    if (queryDeviceInfo != null) {
                        AliUserLog.c("LogoutServiceImpl", "获取设备信息成功");
                        String walletTid = queryDeviceInfo.getWalletTid();
                        if (walletTid != null) {
                            userLogoutGWReq.walletTid = walletTid;
                        }
                        AliUserLog.c("LogoutServiceImpl", "获取设备信息成功 walletTid=".concat(String.valueOf(walletTid)));
                        userLogoutGWReq.walletClientKey = DeviceInfo.a().t();
                        AliUserLog.c("LogoutServiceImpl", "获取设备信息成功");
                        userLogoutGWReq.clientId = DeviceInfo.a().r();
                    }
                } catch (Exception e) {
                    AliUserLog.b((String) "LogoutServiceImpl", (Throwable) e);
                } catch (Throwable th) {
                    AliUserLog.a("LogoutServiceImpl", "doLogout error", th);
                }
                MspDeviceInfoBean queryCertification = AntExtServiceManager.getDeviceService(AliUserInit.b()).queryCertification();
                if (!(queryCertification == null || queryCertification.getTid() == null)) {
                    userLogoutGWReq.mspTid = queryCertification.getTid();
                    userLogoutGWReq.mspClientKey = queryCertification.getMspkey();
                    userLogoutGWReq.mspImei = queryCertification.getImei();
                    userLogoutGWReq.mspImsi = queryCertification.getImsi();
                    StringBuilder sb3 = new StringBuilder("从移动快捷获取tid=");
                    sb3.append(null);
                    AliUserLog.c("LogoutServiceImpl", sb3.toString());
                }
                UserLogoutGWResult logout = userUnifyLoginFacade.logout(userLogoutGWReq);
                StringBuilder sb4 = new StringBuilder("got token ");
                sb4.append(logout.token);
                AliUserLog.c("LogoutServiceImpl", sb4.toString());
            }
        } catch (Throwable th2) {
            AliUserLog.a("LogoutServiceImpl", "logout error", th2);
        }
    }

    public final void a() {
        AliUserLog.c("LogoutServiceImpl", "发送安全退出消息");
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(AliUserInit.b());
        try {
            Intent intent = new Intent("com.alipay.security.logout");
            if (this.a != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.a.getUserId());
                intent.putExtra("logoutUserId", sb.toString());
            }
            instance.sendBroadcast(intent);
        } catch (Exception e) {
            AliUserLog.a((String) "StackTrace", (Throwable) e);
        }
    }
}
