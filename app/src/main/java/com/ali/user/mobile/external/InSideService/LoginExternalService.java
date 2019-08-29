package com.ali.user.mobile.external.InSideService;

import android.content.Intent;
import android.os.Bundle;
import com.ali.user.mobile.accountbiz.sp.SecurityShareStore;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.external.LoginPreCheckActivity;
import com.ali.user.mobile.external.OpenAuthTokenLoginActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import java.util.concurrent.Semaphore;

public class LoginExternalService implements IInsideService<Bundle, String> {
    private static final String TAG = "LoginExternalService";
    private int TASK_TIME_OUT = 600000;
    private String mInsideLoginType = null;
    private Boolean mIsNewOpenAuthFlow;
    private final Object mLock = new Object();
    private String mMCUid;
    private String mOpenAuthToken;
    private String mOpenAuthUserId;
    private String mOpenMcAccount;
    private String mOpenMcMobileNumber;
    private String mOpenMobileNumber;
    private Semaphore mSemaphore = new Semaphore(0);

    public LoginExternalService() {
        AliUserLog.c(TAG, "LoginExternalService service constructor");
    }

    public void start(Bundle bundle) {
        AliUserLog.c(TAG, "LoginExternalService start 1");
    }

    public void start(IInsideServiceCallback iInsideServiceCallback, Bundle bundle) {
        AliUserLog.c(TAG, "LoginExternalService start 2.0");
        LoggerUtils.a("event", "inside_login_init", "UC-INSIDE-LOGIN-INIT-170401-1", "");
        if (bundle != null) {
            this.mOpenAuthToken = bundle.getString("openAuthToken");
            this.mOpenAuthUserId = bundle.getString("openAuthUserId");
            this.mMCUid = bundle.getString("openMcUid");
            this.mIsNewOpenAuthFlow = Boolean.valueOf(bundle.getBoolean("isNewOpenAuthFlow", false));
            this.mOpenMobileNumber = bundle.getString("openMobileNumber");
            this.mOpenMcAccount = bundle.getString("openMcAccount");
            this.mOpenMcMobileNumber = bundle.getString("openMcMobileNumber");
            this.mInsideLoginType = bundle.getString("insideLoginType");
            SecurityShareStore.a(LauncherApplication.a().getApplicationContext(), (String) "insideLoginType", this.mInsideLoginType);
            StringBuilder sb = new StringBuilder("mInsideLoginType = ");
            sb.append(this.mInsideLoginType);
            sb.append(",mOpenAuthToken = ");
            sb.append(this.mOpenAuthToken);
            sb.append(",mOpenAuthUserId");
            sb.append(this.mOpenAuthUserId);
            AliUserLog.c(TAG, sb.toString());
        }
        LoggerUtils.a("event", "pre_no_token_no_session", "UC-INSIDE-LOG-170401-5", "");
        AliuserLoginContext.a(iInsideServiceCallback);
        judgeLoginType();
    }

    private void judgeLoginType() {
        if ("openAuthTokenLogin".equals(this.mInsideLoginType)) {
            goOpenAuthLoginPage();
        } else {
            goPreCheckPage();
        }
    }

    public String startForResult(Bundle bundle) {
        AliUserLog.c(TAG, "LoginExternalService start 3");
        return null;
    }

    private void goOpenAuthLoginPage() {
        Intent intent = new Intent(LauncherApplication.a(), OpenAuthTokenLoginActivity.class);
        intent.putExtra("openAuthToken", this.mOpenAuthToken);
        intent.putExtra("openAuthUserId", this.mOpenAuthUserId);
        intent.putExtra("openMcUid", this.mMCUid);
        intent.putExtra("isNewOpenAuthFlow", this.mIsNewOpenAuthFlow);
        intent.setFlags(268435456);
        intent.addFlags(65536);
        LauncherApplication.a().startActivity(intent);
    }

    private void goPreCheckPage() {
        Intent intent = new Intent(LauncherApplication.a(), LoginPreCheckActivity.class);
        intent.putExtra("openMobileNumber", this.mOpenMobileNumber);
        intent.putExtra("openMcMobileNumber", this.mOpenMcMobileNumber);
        intent.putExtra("openMcAccount", this.mOpenMcAccount);
        intent.putExtra("openAuthToken", this.mOpenAuthToken);
        intent.putExtra("openAuthUserId", this.mOpenAuthUserId);
        intent.putExtra("insideLoginType", this.mInsideLoginType);
        intent.setFlags(268435456);
        LauncherApplication.a().startActivity(intent);
    }
}
