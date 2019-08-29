package com.ali.user.mobile.external;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.login.CommonNotifyCaller;
import com.ali.user.mobile.security.ui.R;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.mobile.h5container.api.H5PageData;

public class LogoutInsideActivity extends BaseActivity {
    private static final String TAG = "LogoutInsideActivity";

    public void setAppId() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AliUserLog.c(TAG, TAG);
        if (LauncherApplication.a() == null) {
            AliUserLog.c(TAG, "LauncherApplication.getInstance() == null finish");
            finish();
        }
    }

    public void onStart() {
        super.onStart();
        AliUserLog.c(TAG, H5PageData.KEY_UC_START);
        doLogOut();
    }

    private void doLogOut() {
        AliUserLog.c(TAG, "登出");
        showProgress(getString(R.string.bG));
        try {
            AntExtServiceManager.getLogoutService(LauncherApplication.a().getApplicationContext()).logout(new CommonNotifyCaller() {
                public void onError() {
                }

                public void onFinish() {
                    synchronized (LogoutInsideActivity.class) {
                        IInsideServiceCallback h = AliuserLoginContext.h();
                        if (h != null) {
                            h.onComplted("");
                            AliuserLoginContext.b(null);
                        }
                    }
                    LogoutInsideActivity.this.dismissProgress();
                    LogoutInsideActivity.this.delayFinishCurrentPage();
                }
            });
        } catch (Throwable th) {
            AliUserLog.b(TAG, "logout exception", th);
            IInsideServiceCallback h = AliuserLoginContext.h();
            if (h != null) {
                h.onException(th);
                AliuserLoginContext.b(null);
            }
            dismissProgress();
            delayFinishCurrentPage();
        }
    }

    /* access modifiers changed from: private */
    public void delayFinishCurrentPage() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                LogoutInsideActivity.this.finish();
            }
        }, 200);
    }
}
