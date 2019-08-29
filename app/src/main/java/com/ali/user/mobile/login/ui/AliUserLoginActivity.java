package com.ali.user.mobile.login.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.view.LoginView;
import com.ali.user.mobile.login.view.SmsLoginJuniorView;
import com.ali.user.mobile.login.view.SmsLoginView;
import com.ali.user.mobile.login.view.UsedLoginView;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.ali.user.mobile.ui.widget.EditTextHasNullChecker;
import com.ali.user.mobile.util.ResizeScrollView;
import com.ali.user.mobile.util.ShowRegionHelper;
import com.ali.user.mobile.util.ShowRegionHelper.RegionChangeListener;
import com.ali.user.mobile.utils.CommonUtil;
import com.ali.user.mobile.utils.DensityUtil;
import com.alipay.mobile.common.share.widget.ResUtils;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class AliUserLoginActivity extends BaseLoginActivity {
    public static final int STATE_FACE_LOGIN = 2;
    public static final int STATE_IDLE = -1;
    public static final int STATE_PASSWORD_LOGIN = 1;
    public static final int STATE_SMS_LOGIN = 0;
    public static final int STATE_SMS_LOGIN_JUNIOR = 4;
    private static final String TAG = "AliUserLoginActivity";
    private static Map<Integer, Class<?>> mState2LoginViewMap;
    protected OnClickListener accountInputListener = new OnClickListener() {
        public void onClick(DialogInterface dialogInterface, int i) {
            AliUserLoginActivity.this.clearPassword();
            AliUserLoginActivity.this.requestAccountFocus();
        }
    };
    protected EditTextHasNullChecker mHasNullChecker;
    protected Map<Integer, LoginView> mLoginViewMap;
    private boolean mPasswordVisible;
    protected LinearLayout mRightCornerView;
    protected ResizeScrollView mScrollContainers;
    protected ShowRegionHelper mShowRegionHelper;
    protected Map<String, String> mSmsLoginMap = new HashMap();
    private boolean mSoftKeyboardShowed;
    protected int mState = -1;
    protected APTitleBar mTitleBar;
    protected OnClickListener reInputPasswordListener = new OnClickListener() {
        public void onClick(DialogInterface dialogInterface, int i) {
            AliUserLoginActivity.this.clearPassword();
            AliUserLoginActivity.this.requestPasswordFocus();
        }
    };

    static {
        HashMap hashMap = new HashMap();
        mState2LoginViewMap = hashMap;
        hashMap.put(Integer.valueOf(0), SmsLoginView.class);
        mState2LoginViewMap.put(Integer.valueOf(1), UsedLoginView.class);
        mState2LoginViewMap.put(Integer.valueOf(4), SmsLoginJuniorView.class);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AliUserLog.c(TAG, "onCreate:".concat(String.valueOf(bundle)));
        this.mHasNullChecker = new EditTextHasNullChecker();
        this.mLoginViewMap = new HashMap();
        setContentView(R.layout.h);
        initViews();
        initLoginState();
        if (UIConfigManager.j() != null) {
            getCurrentLoginView();
        }
        LoggerUtils.a("", TAG, "login", "");
        LogAgent.a("YWUC-JTTYZH-C38", "", "alipayLoginView", getLoginAccount(), this.mToken);
    }

    public ResizeScrollView getResizeScrollView() {
        return this.mScrollContainers;
    }

    public void setScrollBound(View view, View view2, boolean z) {
        this.mShowRegionHelper.a(view, view2, z);
    }

    public void initViews() {
        this.mScrollContainers = (ResizeScrollView) findViewById(R.id.cJ);
        this.mShowRegionHelper = new ShowRegionHelper(this.mScrollContainers);
        this.mShowRegionHelper.a((RegionChangeListener) new RegionChangeListener() {
            public final void a(int i, int i2) {
                StringBuilder sb = new StringBuilder("onRegionChange h:");
                sb.append(i);
                sb.append(" oldH:");
                sb.append(i2);
                AliUserLog.c(AliUserLoginActivity.TAG, sb.toString());
                if (i < i2 && i2 - i > DensityUtil.a((Context) AliUserLoginActivity.this, 50.0f)) {
                    AliUserLoginActivity.this.onSoftKeyboardShow(true);
                } else {
                    AliUserLoginActivity.this.onSoftKeyboardShow(false);
                }
            }
        });
        this.mRightCornerView = (LinearLayout) findViewById(R.id.cb);
        configRightCornerView(this.mRightCornerView);
        this.mTitleBar = (APTitleBar) findViewById(R.id.ca);
        if (this.mTitleBar.getTitlebarBg() != null) {
            this.mTitleBar.getTitlebarBg().setBackgroundColor(0);
        }
        this.mTitleBar.setBackButtonListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AliUserLoginActivity.this.mState == 4) {
                    AliUserLoginActivity.this.enterState(1);
                    AliUserLoginActivity.this.mTitleBar.setVisibility(AliuserLoginContext.a() ? 0 : 8);
                    return;
                }
                AliUserLoginActivity.this.finishAndNotify();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.getBoolean("come_back")) {
            AliUserLog.c(TAG, "can not come back, hide titlebar");
            AliuserLoginContext.a(false);
            this.mTitleBar.setVisibility(8);
            return;
        }
        AliUserLog.c(TAG, "can come back, show titlebar");
        AliuserLoginContext.a(true);
        this.mTitleBar.setVisibility(0);
        UIConfigManager.a(this.mTitleBar);
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = getIntent().getExtras();
        if (extras != null && "sms".equals(extras.getString(ResUtils.STYLE))) {
            AliUserLog.c(TAG, String.format("当前外部跳转短信登录", new Object[0]));
            enterState(0);
        } else if (getLoginParamFromExtra() != null && this.mIsFromRegist) {
            AliUserLog.c(TAG, "onNewIntent-注册过来的信任登录在账密页进行");
            if ("true".equals(getIntent().getExtras().get("noQueryPwdUser"))) {
                enterState(0);
            } else {
                enterState(1);
            }
        }
        getCurrentLoginView().onNewIntent(intent);
    }

    private LoginParam getLoginParamFromExtra() {
        Bundle extras = getIntent().getExtras();
        LoginParam loginParam = extras != null ? (LoginParam) extras.get("login_param") : null;
        AliUserLog.c(TAG, String.format("loginParam from extra:%s", new Object[]{loginParam}));
        return loginParam;
    }

    public void onResume() {
        super.onResume();
        AliUserLog.c(TAG, "onResume");
        getCurrentLoginView().onResume();
    }

    public void onPause() {
        super.onPause();
        AliUserLog.c(TAG, "onPause");
        getCurrentLoginView().onPause();
    }

    public boolean getIsFromRegist() {
        return this.mIsFromRegist;
    }

    public APTitleBar getTitleBar() {
        return this.mTitleBar;
    }

    public void setRightCornerViewEnable(boolean z) {
        if (z) {
            this.mRightCornerView.setVisibility(this.mRightCornerDefaultVisible);
        } else {
            this.mRightCornerView.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    public void onKeyBack() {
        if (this.mState == 4) {
            enterState(1);
            this.mTitleBar.setVisibility(AliuserLoginContext.a() ? 0 : 8);
            return;
        }
        super.onKeyBack();
    }

    public String getLoginAccount() {
        LoginView currentLoginView = getCurrentLoginView();
        if (currentLoginView == null) {
            return "";
        }
        return currentLoginView.getLoginAccount();
    }

    /* access modifiers changed from: protected */
    public String getShownAccount() {
        LoginView currentLoginView = getCurrentLoginView();
        if (currentLoginView == null) {
            return "";
        }
        return currentLoginView.getShownAccount();
    }

    public void clearAccount() {
        LoginView currentLoginView = getCurrentLoginView();
        if (currentLoginView != null) {
            currentLoginView.clearAccount();
        }
    }

    public void requestAccountFocus() {
        LoginView currentLoginView = getCurrentLoginView();
        if (currentLoginView != null) {
            currentLoginView.requestAccountFocus();
        }
    }

    /* access modifiers changed from: protected */
    public String getLoginPassword() {
        LoginView currentLoginView = getCurrentLoginView();
        if (currentLoginView == null) {
            return "";
        }
        return currentLoginView.getLoginPassword();
    }

    /* access modifiers changed from: protected */
    public void clearPassword() {
        LoginView currentLoginView = getCurrentLoginView();
        if (currentLoginView != null) {
            currentLoginView.clearPassword();
        }
    }

    public void showInputPassword() {
        LoginView currentLoginView = getCurrentLoginView();
        if (currentLoginView != null) {
            currentLoginView.showInputPassword();
        }
    }

    public void requestPasswordFocus() {
        LoginView currentLoginView = getCurrentLoginView();
        if (currentLoginView != null) {
            currentLoginView.requestPasswordFocus();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        StringBuilder sb = new StringBuilder("onActivityResult, requestCode:");
        sb.append(i);
        sb.append(",resultCode:");
        sb.append(i2);
        sb.append(",data:");
        sb.append(intent);
        AliUserLog.c(TAG, sb.toString());
        if (i != 45056) {
            super.onActivityResult(i, i2, intent);
        }
    }

    /* access modifiers changed from: protected */
    public void onNewAccount(String str, int i) {
        getCurrentLoginView().onNewAccount(str, i);
    }

    public void afterLogin(LoginParam loginParam, UnifyLoginRes unifyLoginRes) {
        "withonekeytoken".equals(loginParam.validateTpye);
        super.afterLogin(loginParam, unifyLoginRes);
    }

    /* access modifiers changed from: protected */
    public void onLoginResponseSuccess(UnifyLoginRes unifyLoginRes) {
        if (unifyLoginRes.extMap == null) {
            unifyLoginRes.extMap = new HashMap();
        }
        unifyLoginRes.extMap.put("validateTpye", "withpwd");
        unifyLoginRes.extMap.put("lp", getCurrentLoginView().getLoginPassword());
        unifyLoginRes.extMap.put("loginType", getLoginType());
        unifyLoginRes.extMap.put("from_register", String.valueOf(this.mIsFromRegist));
        if (this.mLoginParam != null) {
            "withface".equalsIgnoreCase(this.mLoginParam.validateTpye);
        }
        super.onLoginResponseSuccess(unifyLoginRes);
    }

    /* access modifiers changed from: protected */
    public void onLoginResponseError(UnifyLoginRes unifyLoginRes) {
        checkLoginResponseFail(unifyLoginRes);
        String str = unifyLoginRes.code;
        if ("6304".equals(str)) {
            alert(null, unifyLoginRes.msg, getString(R.string.co), this.reInputPasswordListener, getString(R.string.bs), this.forgetPasswordListener);
        } else if ("6325".equals(str)) {
            alert("", unifyLoginRes.msg, getString(R.string.cz), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    AliUserLoginActivity.this.enterState(4);
                }
            }, null, null);
        } else if ("6303".equals(str)) {
            dismissProgress();
            alert(null, unifyLoginRes.msg, getString(R.string.co), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    AliUserLoginActivity.this.clearPassword();
                    AliUserLoginActivity.this.showInputPassword();
                    AliUserLoginActivity.this.requestPasswordFocus();
                }
            }, getString(R.string.bs), this.forgetPasswordListener);
        } else if ("5039".equals(str)) {
            dismissProgress();
            alert(null, unifyLoginRes.msg, getString(R.string.co), this.accountInputListener, getString(R.string.cj), this.registerWithAccountListener);
        } else if ("5133".equals(str)) {
            dismissProgress();
            alert(null, unifyLoginRes.msg, getString(R.string.bt), null, null, null);
        } else if ("5037".equals(str)) {
            dismissProgress();
            alert("", unifyLoginRes.msg, this.confirm, this.accountInputListener, null, null);
        } else if ("6324".equals(str)) {
            dismissProgress();
            enterState(1);
            toast(unifyLoginRes.msg, 0);
        } else {
            super.onLoginResponseError(unifyLoginRes);
        }
    }

    /* access modifiers changed from: protected */
    public void checkLoginResponseFail(UnifyLoginRes unifyLoginRes) {
        enableBackButton();
        checkSupportSmsLogin(unifyLoginRes);
    }

    public void doUnifyLogin(LoginParam loginParam) {
        if (this.mState != 2 && !"withface".equalsIgnoreCase(loginParam.validateTpye)) {
            addFaceLoginSyncConfig(loginParam);
        }
        disableBackButton();
        super.doUnifyLogin(loginParam);
    }

    public void onDestroy() {
        super.onDestroy();
        dismissProgress();
        for (LoginView onDestroy : this.mLoginViewMap.values()) {
            onDestroy.onDestroy();
        }
        this.mLoginViewMap.clear();
    }

    /* access modifiers changed from: protected */
    public void checkSupportSmsLogin(UnifyLoginRes unifyLoginRes) {
        this.mSmsLoginMap.put(getLoginAccount(), getStringFromExtResAttrs(unifyLoginRes, "isAllowSMS"));
    }

    public void enableBackButton() {
        this.mTitleBar.getBackButton().setEnabled(true);
    }

    public void disableBackButton() {
        this.mHandler.post(new Runnable() {
            public void run() {
                AliUserLoginActivity.this.mTitleBar.getBackButton().setEnabled(false);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initLoginState() {
        boolean z;
        Bundle extras = getIntent().getExtras();
        if (CommonUtil.a(extras)) {
            AliUserLog.c(TAG, String.format("当前外部跳转短信登录", new Object[0]));
            enterState(0);
            return;
        }
        if (hasLoginHistory()) {
            AliUserLog.c(TAG, String.format("当前有历史账户", new Object[0]));
            LoginParam loginParam = null;
            if (extras != null) {
                loginParam = (LoginParam) extras.get("login_param");
                z = extras.getBoolean("source_accountSelectAccount");
            } else {
                z = false;
            }
            AliUserLog.c(TAG, String.format("initLoginState-loginParam:%s", new Object[]{loginParam}));
            if (loginParam == null) {
                if (!z) {
                    enterStateWithLoginHistory((UserInfo) this.mLoginHistorys.get(0));
                    return;
                }
            } else if (!loginParam.trustLoginEnable() || !this.mIsFromRegist) {
                UserInfo loginHistoryFromAccount = getLoginHistoryFromAccount(loginParam.loginAccount);
                AliUserLog.c(TAG, String.format("是否找到匹配的历史账户:%s", new Object[]{loginHistoryFromAccount}));
                enterStateWithLoginHistory(loginHistoryFromAccount);
                return;
            } else {
                AliUserLog.c(TAG, "initLoginState-注册过来的信任登录在账密页进行");
                if ("true".equals(extras.get("noQueryPwdUser"))) {
                    enterState(0);
                    return;
                } else {
                    enterState(1);
                    return;
                }
            }
        } else {
            AliUserLog.c(TAG, String.format("当前是首次登录", new Object[0]));
            if (getLoginParamFromExtra() != null && this.mIsFromRegist) {
                AliUserLog.c(TAG, "onNewIntent-注册过来的信任登录在账密页进行");
                if ("true".equals(getIntent().getExtras().get("noQueryPwdUser"))) {
                    enterState(0);
                    return;
                } else {
                    enterState(1);
                    return;
                }
            }
        }
        enterState(1);
    }

    private void enterStateWithLoginHistory(UserInfo userInfo) {
        if (isNoQueryPwdUser(userInfo)) {
            enterState(0);
        } else {
            enterState(1);
        }
    }

    public UserInfo getLoginHistoryFromAccount(String str) {
        for (UserInfo userInfo : this.mLoginHistorys) {
            if (str != null && userInfo != null && str.equals(userInfo.getLogonId())) {
                return userInfo;
            }
        }
        return null;
    }

    public int getState() {
        return this.mState;
    }

    public String getFrom() {
        LoginView currentLoginView = getCurrentLoginView();
        if (currentLoginView == null) {
            return "fromlogout";
        }
        return currentLoginView.myName();
    }

    public LoginView getCurrentLoginView() {
        return this.mLoginViewMap.get(Integer.valueOf(this.mState));
    }

    private LoginView switchLoginView(int i) {
        LoginView loginView;
        if (this.mLoginViewMap.containsKey(Integer.valueOf(i))) {
            loginView = this.mLoginViewMap.get(Integer.valueOf(i));
            loginView.onViewRestart();
        } else {
            LoginView loginView2 = null;
            try {
                Constructor constructor = mState2LoginViewMap.get(Integer.valueOf(i)).getConstructor(new Class[]{AliUserLoginActivity.class});
                constructor.setAccessible(true);
                LoginView loginView3 = (LoginView) constructor.newInstance(new Object[]{this});
                try {
                    this.mLoginViewMap.put(Integer.valueOf(i), loginView3);
                    loginView3.onViewStart();
                    loginView3.setTag(Integer.valueOf(i));
                    loginView = loginView3;
                } catch (Exception e) {
                    e = e;
                    loginView2 = loginView3;
                    AliUserLog.a((String) TAG, (Throwable) e);
                    loginView = loginView2;
                    this.mScrollContainers.removeAllViews();
                    this.mScrollContainers.addView(loginView);
                    return loginView;
                }
            } catch (Exception e2) {
                e = e2;
                AliUserLog.a((String) TAG, (Throwable) e);
                loginView = loginView2;
                this.mScrollContainers.removeAllViews();
                this.mScrollContainers.addView(loginView);
                return loginView;
            }
        }
        this.mScrollContainers.removeAllViews();
        this.mScrollContainers.addView(loginView);
        return loginView;
    }

    public void enterState(int i) {
        AliUserLog.c(TAG, "enterState:".concat(String.valueOf(i)));
        if (this.mState != i) {
            LoginView currentLoginView = getCurrentLoginView();
            if (currentLoginView != null) {
                currentLoginView.onViewStop();
            }
            switchLoginView(i);
            this.mState = i;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isNoQueryPwdUser(UserInfo userInfo) {
        return userInfo != null && userInfo.isNoQueryPwdUser();
    }

    /* access modifiers changed from: protected */
    public boolean isPwdState() {
        return 1 == this.mState;
    }

    public void onSoftKeyboardShow(boolean z) {
        this.mSoftKeyboardShowed = z;
        checkChangeScreenshotState();
    }

    public void onPasswordVisibleChange(boolean z) {
        this.mPasswordVisible = z;
        checkChangeScreenshotState();
    }

    private void checkChangeScreenshotState() {
        if (isPwdState()) {
            changeScreenshotState(this.mPasswordVisible || this.mSoftKeyboardShowed);
        }
    }

    public void changeScreenshotState(boolean z) {
        try {
            AliUserLog.a((String) TAG, "changeScreenshotState enable : ".concat(String.valueOf(z)));
            if (z) {
                getWindow().addFlags(8192);
            } else {
                getWindow().clearFlags(8192);
            }
        } catch (Throwable th) {
            AliUserLog.b((String) TAG, "changeScreenshotState:".concat(String.valueOf(th)));
        }
    }
}
