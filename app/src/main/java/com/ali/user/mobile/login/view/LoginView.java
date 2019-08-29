package com.ali.user.mobile.login.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.common.api.LoginHistoryCallback;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.login.AccountResponsable;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.ui.AliUserLoginActivity;
import com.ali.user.mobile.register.model.State;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APImageView;
import com.ali.user.mobile.ui.widget.APListPopDialog;
import com.ali.user.mobile.ui.widget.APListPopDialog.OnItemClickListener;
import com.ali.user.mobile.ui.widget.ImageLoader;
import com.ali.user.mobile.ui.widget.keyboard.APSafeEditText;
import com.ali.user.mobile.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

public abstract class LoginView extends LinearLayout implements OnClickListener, AccountResponsable {
    protected APImageView mAccountImageView;
    protected Context mApplicationContext;
    protected AliUserLoginActivity mAttatchActivity;
    protected String mCurrentSelectedAccount;
    protected UserInfo mCurrentSelectedHistory;
    protected String mFrom = "fromnull";
    protected List<UserInfo> mLoginHistorys;
    protected TextView mMainTip;
    protected Bundle mParams;
    protected final DialogInterface.OnClickListener passwordLoginListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialogInterface, int i) {
            LogAgent.d("UC-RLSB-150901-09", "failtopassword", null, null, null);
            LoginView.this.mAttatchActivity.enterState(1);
            LoginView.this.mAttatchActivity.requestPasswordFocus();
        }
    };

    public abstract String myName();

    public void onClick(View view) {
    }

    public void onDestroy() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void toSecurityCenter() {
    }

    /* access modifiers changed from: protected */
    public void uiCustomize() {
    }

    public LoginView(AliUserLoginActivity aliUserLoginActivity) {
        super(aliUserLoginActivity);
        this.mApplicationContext = aliUserLoginActivity.getApplicationContext();
        this.mAttatchActivity = aliUserLoginActivity;
        this.mLoginHistorys = this.mAttatchActivity.getLoginHistoryList();
        if (hasLoginHistory()) {
            this.mCurrentSelectedHistory = this.mLoginHistorys.get(0);
        }
        updateParam(this.mAttatchActivity.getIntent());
        doInflate(aliUserLoginActivity);
        uiCustomize();
        initRds();
    }

    public void onViewStart() {
        whereAmIFrom();
    }

    public void onViewRestart() {
        updateParam(this.mAttatchActivity.getIntent());
        whereAmIFrom();
    }

    public void onViewStop() {
        clearTustLoginToken();
    }

    public void onNewIntent(Intent intent) {
        AliUserLog.c("LoginView", String.format("onNewIntent:%s", new Object[]{intent}));
        updateParam(intent);
    }

    /* access modifiers changed from: protected */
    public void doInflate(Context context) {
        setLayoutParams(new LayoutParams(-1, -1));
        setOrientation(1);
        View inflate = LayoutInflater.from(context).inflate(R.layout.x, this, true);
        this.mAccountImageView = (APImageView) inflate.findViewById(R.id.cG);
        this.mMainTip = (TextView) inflate.findViewById(R.id.aA);
    }

    /* access modifiers changed from: protected */
    public void initRds() {
        this.mAttatchActivity.initRdsPage(this.mCurrentSelectedAccount);
        this.mAttatchActivity.initRdsScreenTouch(this.mAttatchActivity.getResizeScrollView(), "LoginViewContainers");
    }

    /* access modifiers changed from: protected */
    public void whereAmIFrom() {
        this.mFrom = this.mAttatchActivity.getFrom();
    }

    /* access modifiers changed from: protected */
    public void updateParam(Intent intent) {
        this.mParams = intent.getExtras();
        AliUserLog.c("LoginView", String.format("param updated: %s", new Object[]{this.mParams}));
    }

    /* access modifiers changed from: protected */
    public boolean hasLoginHistory() {
        return this.mLoginHistorys != null && !this.mLoginHistorys.isEmpty();
    }

    /* access modifiers changed from: protected */
    public void setPortraitImage(boolean z, UserInfo userInfo) {
        if (z && userInfo != null) {
            LoginHistoryCallback i = UIConfigManager.i();
            if (i != null) {
                Drawable a = i.a();
                AliUserLog.c("LoginView", String.format("custom avatar of %s is: %s", new Object[]{userInfo.getLogonId(), a}));
                if (a != null) {
                    this.mAccountImageView.setImageDrawable(a);
                    return;
                }
            }
            if (!TextUtils.isEmpty(userInfo.getUserAvatar())) {
                ImageLoader.a(userInfo.getUserAvatar(), this.mAccountImageView, getResources().getDrawable(R.drawable.y));
                return;
            }
        }
        this.mAccountImageView.setImageResource(R.drawable.y);
    }

    /* access modifiers changed from: protected */
    public void checkToForgetPassword() {
        String str;
        if ("toForgetPwd".equalsIgnoreCase(this.mParams.getString("loginTargetBiz"))) {
            LoginParam loginParamFromExtra = getLoginParamFromExtra();
            String str2 = null;
            if (loginParamFromExtra != null) {
                AliUserLog.c("LoginView", String.format("toForgetPassword with extra account:%s", new Object[]{loginParamFromExtra.loginAccount}));
                str2 = loginParamFromExtra.loginAccount;
                str = StringUtil.b(str2);
            } else {
                str = null;
            }
            this.mAttatchActivity.toForgetPassword(str2, str);
            this.mAttatchActivity.getIntent().putExtra("loginTargetBiz", "");
        }
    }

    /* access modifiers changed from: protected */
    public void clearTustLoginToken() {
        LoginParam loginParamFromExtra = getLoginParamFromExtra();
        Intent intent = this.mAttatchActivity.getIntent();
        LoginParam loginParam = intent != null ? (LoginParam) intent.getSerializableExtra("login_param") : null;
        boolean z = false;
        if (loginParamFromExtra == loginParam) {
            AliUserLog.c("LoginView", "new loginParams == old loginParams");
        } else {
            if (loginParamFromExtra != null && loginParam != null && TextUtils.equals(loginParamFromExtra.loginAccount, loginParam.loginAccount) && TextUtils.equals(loginParamFromExtra.token, loginParam.token) && TextUtils.equals(loginParamFromExtra.validateTpye, loginParam.validateTpye)) {
                AliUserLog.c("LoginView", "new loginParams'param == old loginParams'param");
            }
            if (loginParamFromExtra != null && z) {
                loginParamFromExtra.disableTustLogin();
                this.mAttatchActivity.getIntent().putExtra("login_param", loginParamFromExtra);
                return;
            }
        }
        z = true;
        if (loginParamFromExtra != null) {
        }
    }

    /* access modifiers changed from: protected */
    public LoginParam getLoginParamFromExtra() {
        if (this.mParams != null) {
            return (LoginParam) this.mParams.getSerializable("login_param");
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void showInputMethodPannel(final View view) {
        this.mAttatchActivity.getWindow().getDecorView().post(new Runnable() {
            public void run() {
                view.requestFocus();
                if (view instanceof APSafeEditText) {
                    APSafeEditText aPSafeEditText = (APSafeEditText) view;
                    if (aPSafeEditText.isPasswordType()) {
                        aPSafeEditText.showSafeKeyboard();
                    } else {
                        ((InputMethodManager) LoginView.this.mApplicationContext.getSystemService("input_method")).showSoftInput(view, 0);
                    }
                } else {
                    ((InputMethodManager) LoginView.this.mApplicationContext.getSystemService("input_method")).showSoftInput(view, 0);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void closeInputMethod(View view) {
        try {
            ((InputMethodManager) this.mApplicationContext.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            String simpleName = getClass().getSimpleName();
            StringBuilder sb = new StringBuilder("closeInputMethod exception");
            sb.append(e.getMessage());
            AliUserLog.c(simpleName, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void showListDialog(final ArrayList<String> arrayList) {
        final APListPopDialog aPListPopDialog = new APListPopDialog(this.mAttatchActivity, arrayList);
        aPListPopDialog.a((OnItemClickListener) new OnItemClickListener() {
            public final void a(int i) {
                aPListPopDialog.dismiss();
                LoginView.this.performDialogAction((String) arrayList.get(i));
            }
        });
        aPListPopDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                LogAgent.d("UC-GY-161225-09", "loginmoreback", LoginView.this.myName(), null, null);
            }
        });
        aPListPopDialog.show();
        LogAgent.d("UC-GY-161225-03", "loginmore", myName(), null, null);
    }

    /* access modifiers changed from: protected */
    public void performDialogAction(String str) {
        performDialogAction(str, false);
    }

    /* access modifiers changed from: protected */
    public void performDialogAction(String str, boolean z) {
        if (!this.mAttatchActivity.getString(R.string.bn).equals(str)) {
            if (this.mAttatchActivity.getString(R.string.cP).equals(str)) {
                LogAgent.d("UC-RLSB-160619-02", "gotoTaoBaoLogin", myName(), null, null);
            } else if (this.mAttatchActivity.getString(R.string.ci).equals(str)) {
                if (!z) {
                    State.a = "";
                }
                LogAgent.d("UC-GY-161225-07", "loginmorezc", myName(), State.a, null);
                this.mAttatchActivity.onRdsControlClick("RegisterBtn");
                this.mAttatchActivity.toRegist(null);
            } else if (this.mAttatchActivity.getString(R.string.bA).equals(str)) {
                LogAgent.d("UC-GY-161225-08", "otherproblem", myName(), null, null);
                toSecurityCenter();
            }
        }
    }

    /* access modifiers changed from: protected */
    public UserInfo getLoginHistoryFromAccount(String str) {
        return this.mAttatchActivity.getLoginHistoryFromAccount(str);
    }

    /* access modifiers changed from: protected */
    public void writeClickLog(String str, String str2, String str3) {
        LogAgent.b(str, str2, str3, getLoginAccount(), "");
    }
}
