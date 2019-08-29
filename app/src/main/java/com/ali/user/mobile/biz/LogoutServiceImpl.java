package com.ali.user.mobile.biz;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.accountbiz.accountmanager.safelogout.LogoutBiz;
import com.ali.user.mobile.accountbiz.extservice.AuthService;
import com.ali.user.mobile.accountbiz.extservice.LogoutService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.CommonNotifyCaller;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APListPopDialog;
import com.ali.user.mobile.ui.widget.APListPopDialog.OnItemClickListener;
import com.ali.user.mobile.utils.ResourceUtil;
import java.util.ArrayList;

public class LogoutServiceImpl extends BaseExtService implements LogoutService {
    private static LogoutService mLogoutService;
    final String TAG = "LogoutServiceImpl";
    AuthService mAuthService = null;

    private LogoutServiceImpl(Context context) {
        super(context);
    }

    public static LogoutService getInstance(Context context) {
        if (mLogoutService == null) {
            synchronized (LogoutServiceImpl.class) {
                if (mLogoutService == null) {
                    mLogoutService = new LogoutServiceImpl(context);
                }
            }
        }
        return mLogoutService;
    }

    public void logout(final CommonNotifyCaller commonNotifyCaller) {
        AliUserLog.c("LogoutServiceImpl", "安全退出开始");
        this.mAuthService = AntExtServiceManager.getAuthService(this.mContext);
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                new LogoutBiz().a("LogoutNoToken", LogoutServiceImpl.this.fetchUserInfo());
                if (commonNotifyCaller != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            commonNotifyCaller.onFinish();
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public UserInfo fetchUserInfo() {
        if (this.mAuthService == null) {
            return null;
        }
        return this.mAuthService.getUserInfo();
    }

    public void showChangeAccountDialog(Activity activity) {
        if (activity == null) {
            AliUserLog.c("LogoutServiceImpl", "showChangeAccountDialog 传入的activity为null");
            return;
        }
        LoggerUtils.a("clicked", "logoutButton", "UC-LOGOUT-00", "");
        final ArrayList arrayList = new ArrayList();
        arrayList.add(ResourceUtil.a(R.string.B));
        arrayList.add(ResourceUtil.a(R.string.bF));
        final APListPopDialog aPListPopDialog = new APListPopDialog(activity, arrayList);
        aPListPopDialog.a((OnItemClickListener) new OnItemClickListener() {
            public final void a(int i) {
                aPListPopDialog.dismiss();
                LogoutServiceImpl.this.performChangeAccountAction((String) arrayList.get(i));
            }
        });
        aPListPopDialog.show();
    }

    /* access modifiers changed from: private */
    public void performChangeAccountAction(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.equals(ResourceUtil.a(R.string.B))) {
                LoggerUtils.a("clicked", "changelogin", "UC-LOGOUT-01", "");
                return;
            }
            if (str.equals(ResourceUtil.a(R.string.bF))) {
                SecurityUtil.a((Runnable) new Runnable() {
                    public void run() {
                        if (LogoutServiceImpl.this.fetchUserInfo() != null) {
                            LogoutServiceImpl.this.logout(null);
                        }
                    }
                });
            }
        }
    }
}
