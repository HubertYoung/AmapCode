package com.ali.user.mobile.external.InSideService;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.accountbiz.extservice.AccountService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.login.sso.impl.SsoServiceImpl;
import com.ali.user.mobile.login.sso.info.SsoLoginInfo;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.sdk.util.e;

public class CheckLoginConsistencyService implements IInsideService<Void, String> {
    /* access modifiers changed from: private */
    public static final String TAG = "CheckLoginConsistencyService";

    public void start(Void voidR) throws Exception {
        throw new UnsupportedOperationException();
    }

    public void start(final IInsideServiceCallback<String> iInsideServiceCallback, Void voidR) throws Exception {
        if (iInsideServiceCallback != null) {
            new Thread(new Runnable() {
                public void run() {
                    final String access$000 = CheckLoginConsistencyService.this.checkLoginConsistency();
                    AliUserLog.c(CheckLoginConsistencyService.TAG, "checklogin consistency state : ".concat(String.valueOf(access$000)));
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            iInsideServiceCallback.onComplted(access$000);
                        }
                    });
                }
            }).start();
        }
    }

    public String startForResult(Void voidR) throws Exception {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: private */
    public String checkLoginConsistency() {
        if (!AntExtServiceManager.getAuthService(LauncherApplication.a()).isLogin()) {
            AliUserLog.c(TAG, "check consistency ,inside has not login");
            return e.b;
        }
        SsoLoginInfo a = SsoServiceImpl.a(LauncherApplication.a().getApplicationContext()).a();
        if (a == null) {
            AliUserLog.c(TAG, "check consistency ,ssoLoginInfo is null,callback failed");
            return e.b;
        }
        AccountService accountService = AntExtServiceManager.getAccountService(LauncherApplication.a().getApplicationContext());
        String currentLoginUserId = accountService.getCurrentLoginUserId();
        String str = a.userId;
        String str2 = TAG;
        StringBuilder sb = new StringBuilder("userId:");
        sb.append(str);
        sb.append(",currentUid:");
        sb.append(currentLoginUserId);
        AliUserLog.c(str2, sb.toString());
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(currentLoginUserId)) {
            return TextUtils.equals(str, currentLoginUserId) ? "consistency" : "notConsistentcy";
        }
        String str3 = a.loginId;
        if (TextUtils.isEmpty(str3)) {
            AliUserLog.c(TAG, "check consistency ,alipay loginId is null,callback failed");
            return e.b;
        }
        UserInfo queryAccountDetailInfoByUserId = accountService.queryAccountDetailInfoByUserId(currentLoginUserId);
        if (queryAccountDetailInfoByUserId == null) {
            AliUserLog.c(TAG, "inside login userinfo is null,normal is not null,because checklogin event invoke after hasLogin");
            return e.b;
        }
        String logonId = queryAccountDetailInfoByUserId.getLogonId();
        String otherLoginId = queryAccountDetailInfoByUserId.getOtherLoginId();
        String str4 = TAG;
        StringBuilder sb2 = new StringBuilder("alipayLoginId = ");
        sb2.append(str3);
        sb2.append(",cLoginId=");
        sb2.append(logonId);
        sb2.append(",insideOtherLoginId=");
        sb2.append(otherLoginId);
        AliUserLog.c(str4, sb2.toString());
        if (str3.equals(logonId) || str3.equals(otherLoginId)) {
            AliUserLog.c(TAG, "check consistency ,loginId is consistency,callback consistency");
            return "consistency";
        }
        AliUserLog.c(TAG, "check consistency ,loginId is null,callback not_consistency");
        return "notConsistentcy";
    }
}
