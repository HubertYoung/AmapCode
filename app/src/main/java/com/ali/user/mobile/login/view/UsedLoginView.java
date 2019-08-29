package com.ali.user.mobile.login.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.ui.AliUserLoginActivity;
import com.ali.user.mobile.register.model.State;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.util.StringUtil;

public class UsedLoginView extends PasswordLoginView {
    protected TextView mForgetPasswordCenter;
    protected TextView mForgetPasswordRight;
    protected Button mLoginButton;
    protected TextView mReg;
    protected View mSmsAndForgetPasswordView;
    protected TextView mSmsLoginView;

    public String myName() {
        return "fromloginpw";
    }

    public UsedLoginView(AliUserLoginActivity aliUserLoginActivity) {
        super(aliUserLoginActivity);
    }

    /* access modifiers changed from: protected */
    public void doInflate(Context context) {
        super.doInflate(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.B, this, true);
        this.mLoginButton = (Button) inflate.findViewById(R.id.ax);
        this.mSmsAndForgetPasswordView = findViewById(R.id.bD);
        this.mSmsLoginView = (TextView) inflate.findViewById(R.id.bE);
        this.mForgetPasswordRight = (TextView) inflate.findViewById(R.id.Y);
        this.mForgetPasswordCenter = (TextView) inflate.findViewById(R.id.X);
        this.mReg = (TextView) inflate.findViewById(R.id.aX);
        this.mLoginButton.setOnClickListener(this);
        this.mSmsLoginView.setOnClickListener(this);
        this.mForgetPasswordRight.setOnClickListener(this);
        this.mForgetPasswordCenter.setOnClickListener(this);
        this.mReg.setOnClickListener(this);
        addNullCheckButton(this.mLoginButton);
        initInputBoxIme(this.mLoginButton);
    }

    /* access modifiers changed from: protected */
    public void uiCustomize() {
        super.uiCustomize();
        UIConfigManager.a(this.mLoginButton);
        int e = UIConfigManager.e();
        if (e != Integer.MAX_VALUE) {
            this.mSmsLoginView.setTextColor(e);
            this.mForgetPasswordRight.setTextColor(e);
            this.mForgetPasswordCenter.setTextColor(e);
        }
    }

    /* access modifiers changed from: protected */
    public void initRds() {
        super.initRds();
        this.mAttatchActivity.initRdsFocusChange(this.mForgetPasswordCenter, "GetPwdBtn");
        this.mAttatchActivity.initRdsFocusChange(this.mLoginButton, "LoginBtn");
    }

    /* access modifiers changed from: protected */
    public void setScrollBound() {
        if (isOpenSmsLoginNative()) {
            this.mForgetPasswordCenter.setVisibility(8);
            this.mSmsAndForgetPasswordView.setVisibility(0);
            this.mAttatchActivity.setScrollBound(this.mAccountInputBox, this.mSmsAndForgetPasswordView, true);
            return;
        }
        this.mForgetPasswordCenter.setVisibility(0);
        this.mSmsAndForgetPasswordView.setVisibility(8);
        this.mAttatchActivity.setScrollBound(this.mAccountInputBox, this.mForgetPasswordCenter, true);
    }

    /* access modifiers changed from: protected */
    public void whereAmIFrom() {
        if (this.mAttatchActivity.getState() != -1) {
            super.whereAmIFrom();
        } else if (this.mAttatchActivity.isFromAccountManager()) {
            this.mFrom = "fromaccountmanager";
        } else if (!hasLoginHistory()) {
            this.mFrom = "firstpage";
        } else {
            this.mFrom = "fromloginpw";
        }
    }

    public void onViewStart() {
        super.onViewStart();
        LogAgent.e("UC-LOG-161225-01", "loginpage", this.mFrom, State.a, null);
        this.mAttatchActivity.setRightCornerViewEnable(true);
        doInitData();
        initInputTrace();
        setScrollBound();
    }

    public void onViewRestart() {
        super.onViewRestart();
        LogAgent.e("UC-LOG-161225-01", "loginpage", this.mFrom, State.a, null);
        this.mAttatchActivity.setRightCornerViewEnable(true);
        String str = "";
        try {
            str = this.mAttatchActivity.getIntent().getStringExtra("accountBetweenView");
        } catch (Throwable th) {
            AliUserLog.b("UsedLoginView", "get intent", th);
        }
        if (!TextUtils.isEmpty(str) && StringUtil.d(str)) {
            UserInfo loginHistoryFromAccount = getLoginHistoryFromAccount(str);
            if (loginHistoryFromAccount == null) {
                if (!TextUtils.isEmpty(this.mAttatchActivity.mInsideAccount)) {
                    setAccount(str, str.equals(this.mAttatchActivity.mInsideAccount));
                } else {
                    setAccount(str, false);
                }
                setPortraitImage(false, null);
            } else {
                setLoginHistoryAccount(loginHistoryFromAccount);
            }
        }
        setScrollBound();
        this.mIsAccountInputted = false;
        this.mIsPasswordInputted = false;
    }

    public void onViewStop() {
        super.onViewStop();
        closeInputMethod(this);
        this.mAttatchActivity.getIntent().putExtra("accountBetweenView", getLoginAccount());
        this.mAttatchActivity.getIntent().putExtra("facadeBetweenView", getShownAccount());
        this.mAttatchActivity.changeScreenshotState(false);
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        doInitData();
    }

    /* access modifiers changed from: protected */
    public void doInitData() {
        if (!trustLoginWithExtLoginParam()) {
            if (this.mParams == null) {
                setLoginHistoryAccount(this.mCurrentSelectedHistory);
                return;
            }
            boolean isFromRegist = this.mAttatchActivity.getIsFromRegist();
            boolean z = this.mParams.getBoolean("source_accountSelectAccount");
            LoginParam loginParam = (LoginParam) this.mParams.get("login_param");
            AliUserLog.c("UsedLoginView", String.format("login init, FromRegist:%s, accountSelect:%s, fromForgotPayPwd:%s, extLoginParam:%s", new Object[]{Boolean.valueOf(isFromRegist), Boolean.valueOf(z), Boolean.valueOf(this.mParams.getBoolean("source_forgotPayPwd")), loginParam}));
            if (loginParam != null) {
                UserInfo loginHistoryFromAccount = getLoginHistoryFromAccount(loginParam.loginAccount);
                if (loginHistoryFromAccount == null) {
                    setPortraitImage(false, null);
                    setAccount(loginParam.loginAccount, !this.mAttatchActivity.getIsFromRegist());
                } else {
                    setLoginHistoryAccount(loginHistoryFromAccount);
                }
            } else if (z) {
                AliUserLog.c("UsedLoginView", "添加账户，不在输入框中设置已登录账户");
            } else {
                setLoginHistoryAccount(this.mCurrentSelectedHistory);
            }
            checkToForgetPassword();
        }
    }

    /* access modifiers changed from: protected */
    public boolean isSupportFaceLogin() {
        AliUserLog.c("UsedLoginView", "isSupportFaceLogin:false");
        return false;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ax) {
            onLoginClicked(this.mLoginButton);
        } else if (view.getId() == R.id.X || view.getId() == R.id.Y) {
            doForgotPasswordAction();
        } else if (view.getId() == R.id.bE) {
            LogAgent.a("UC-LOG-161225-06", "messagelogintwo");
            this.mAttatchActivity.enterState(4);
        } else if (view.getId() == R.id.aX) {
            performDialogAction(this.mAttatchActivity.getString(R.string.ci));
        } else {
            super.onClick(view);
        }
    }

    /* access modifiers changed from: protected */
    public void performDialogAction(String str) {
        super.performDialogAction(str, true);
    }
}
