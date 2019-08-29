package com.ali.user.mobile.login.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.MsgLoginParam;
import com.ali.user.mobile.login.ui.AliUserLoginActivity;
import com.ali.user.mobile.login.ui.LoginManualSmsActivity;
import com.ali.user.mobile.register.Account;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.rpc.vo.mobilegw.login.LoginSendMSGResPb;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.AUPhoneInputBox;
import com.ali.user.mobile.util.StringUtil;
import com.ali.user.mobile.utils.CommonUtil;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;

public class SmsLoginView extends LoginView {
    private String a = "logout";
    protected String mCountryCode;
    protected String mCountryName;
    protected String mLoginAccount = "";
    protected Button mLoginButton;
    protected TextWatcher mPhoneChangedListener;
    protected AUPhoneInputBox mPhoneInputBox;
    protected TextView mSmsRegView;

    public void clearPassword() {
    }

    public String getLoginPassword() {
        return "";
    }

    public String myName() {
        return "fromsmslogin";
    }

    public void onNewAccount(String str, int i) {
    }

    public void requestPasswordFocus() {
    }

    public void showInputPassword() {
    }

    public SmsLoginView(AliUserLoginActivity aliUserLoginActivity) {
        super(aliUserLoginActivity);
    }

    /* access modifiers changed from: protected */
    public void doInflate(Context context) {
        super.doInflate(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.A, this, true);
        this.mLoginButton = (Button) inflate.findViewById(R.id.ax);
        this.mSmsRegView = (TextView) inflate.findViewById(R.id.bF);
        this.mPhoneInputBox = (AUPhoneInputBox) findViewById(R.id.aV);
        this.mPhoneInputBox.attatchActivity(this.mAttatchActivity);
        this.mPhoneInputBox.setRdsWrapper(this.mAttatchActivity.getRdsWraper());
        this.mPhoneInputBox.addNeedEnabledButton(this.mLoginButton);
        this.mPhoneInputBox.setPageName("fromsmslogin");
        this.mMainTip.setVisibility(8);
        this.mAttatchActivity.setRightCornerViewEnable(true);
        this.mAccountImageView.setVisibility(0);
        this.mSmsRegView.setVisibility(0);
        this.mLoginButton.setOnClickListener(this);
        this.mSmsRegView.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void uiCustomize() {
        super.uiCustomize();
        UIConfigManager.a(this.mLoginButton);
        UIConfigManager.e();
    }

    /* access modifiers changed from: protected */
    public void initRds() {
        super.initRds();
        this.mAttatchActivity.initRdsTextChange(this.mPhoneInputBox.getPhoneInput(), "PhoneET");
        this.mAttatchActivity.initRdsFocusChange(this.mPhoneInputBox.getPhoneInput(), "PhoneET");
        this.mAttatchActivity.initRdsFocusChange(this.mLoginButton, "smsLoginBtn");
    }

    public void onViewStart() {
        super.onViewStart();
        doInit();
    }

    public void onViewRestart() {
        super.onViewRestart();
        doInit();
    }

    /* access modifiers changed from: protected */
    public void doInit() {
        if (!trustLoginWithExtLoginParam()) {
            initPhoneAccount();
        }
        this.mPhoneInputBox.getPhoneInput().addTextChangedListener(getPhoneChangedListener());
        setScrollBound();
        requestAccountFocus();
        LogAgent.a((String) "UC-LOG-161225-12", (String) "smsloginonepage", this.a, (String) "");
    }

    /* access modifiers changed from: protected */
    public void whereAmIFrom() {
        if (CommonUtil.a(this.mParams)) {
            this.a = "fromscheme";
        } else if (this.mAttatchActivity.getState() == -1) {
            this.a = "fromlogout";
        } else {
            super.whereAmIFrom();
        }
    }

    public void onViewStop() {
        super.onViewStop();
        this.mPhoneInputBox.getPhoneInput().removeTextChangedListener(this.mPhoneChangedListener);
        closeInputMethod(this.mPhoneInputBox.getPhoneInput());
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.mPhoneInputBox.getPhoneInput().removeTextChangedListener(this.mPhoneChangedListener);
        if (!trustLoginWithExtLoginParam()) {
            initPhoneAccount();
        }
        this.mPhoneInputBox.getPhoneInput().addTextChangedListener(getPhoneChangedListener());
    }

    /* access modifiers changed from: protected */
    public void setScrollBound() {
        this.mAttatchActivity.setScrollBound(this.mPhoneInputBox, this.mLoginButton, true);
    }

    /* access modifiers changed from: protected */
    public boolean trustLoginWithExtLoginParam() {
        if (this.mParams != null) {
            LoginParam loginParam = (LoginParam) this.mParams.get("login_param");
            if (loginParam != null) {
                AliUserLog.c("SmsLoginView", String.format("smslogin init check trust login, extLoginParam:%s", new Object[]{loginParam}));
                this.mLoginAccount = loginParam.loginAccount;
                setFacade(this.mLoginAccount);
                if (loginParam.trustLoginEnable()) {
                    this.mAttatchActivity.getLoginParams(loginParam);
                    if ("withonekeytoken".equals(loginParam.validateTpye)) {
                        UserInfo loginHistoryFromAccount = getLoginHistoryFromAccount(loginParam.loginAccount);
                        if (loginHistoryFromAccount != null) {
                            loginParam.addExternalParam("userId", loginHistoryFromAccount.getUserId());
                        }
                    }
                    this.mAttatchActivity.doUnifyLogin(loginParam);
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initPhoneAccount() {
        /*
            r6 = this;
            com.ali.user.mobile.login.ui.AliUserLoginActivity r0 = r6.mAttatchActivity
            boolean r0 = r0.getIsFromRegist()
            r1 = 1
            r0 = r0 ^ r1
            android.os.Bundle r2 = r6.mParams
            r3 = 0
            if (r2 != 0) goto L_0x0011
            r6.a()
            goto L_0x004f
        L_0x0011:
            android.os.Bundle r2 = r6.mParams
            java.lang.String r4 = "login_param"
            java.lang.Object r2 = r2.get(r4)
            com.ali.user.mobile.login.LoginParam r2 = (com.ali.user.mobile.login.LoginParam) r2
            java.lang.String r4 = "SmsLoginView"
            java.lang.String r5 = "smslogin init, extLoginParam:%s"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r1[r3] = r2
            java.lang.String r1 = java.lang.String.format(r5, r1)
            com.ali.user.mobile.log.AliUserLog.c(r4, r1)
            if (r2 != 0) goto L_0x0031
            r6.a()
            goto L_0x004f
        L_0x0031:
            java.lang.String r1 = r2.loginAccount
            boolean r1 = com.ali.user.mobile.util.StringUtil.d(r1)
            if (r1 == 0) goto L_0x0051
            java.lang.String r1 = r2.loginAccount
            com.ali.user.mobile.account.bean.UserInfo r1 = r6.getLoginHistoryFromAccount(r1)
            if (r1 != 0) goto L_0x004a
            java.lang.String r0 = r2.loginAccount
            r6.mLoginAccount = r0
            r0 = 0
            r6.setPortraitImage(r3, r0)
            goto L_0x0055
        L_0x004a:
            r6.mCurrentSelectedHistory = r1
            r6.a()
        L_0x004f:
            r3 = r0
            goto L_0x0055
        L_0x0051:
            java.lang.String r0 = ""
            r6.mLoginAccount = r0
        L_0x0055:
            if (r3 == 0) goto L_0x005e
            java.lang.String r0 = r6.mLoginAccount
            java.lang.String r0 = com.ali.user.mobile.util.StringUtil.b(r0)
            goto L_0x0060
        L_0x005e:
            java.lang.String r0 = r6.mLoginAccount
        L_0x0060:
            r6.setFacade(r0)
            r6.checkToForgetPassword()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.login.view.SmsLoginView.initPhoneAccount():void");
    }

    private void a() {
        if (this.mCurrentSelectedHistory != null && StringUtil.a(this.mCurrentSelectedHistory.getLogonId())) {
            this.mLoginAccount = this.mCurrentSelectedHistory.getLogonId();
            setPortraitImage(true, this.mCurrentSelectedHistory);
        }
    }

    /* access modifiers changed from: protected */
    public void setFacade(String str) {
        String str2 = "+86";
        int indexOf = str.indexOf(45);
        if (indexOf > 0 && indexOf < str.length() - 1) {
            String[] split = str.split("-");
            StringBuilder sb = new StringBuilder("+");
            sb.append(split[0]);
            str2 = sb.toString();
            str = split[1];
        }
        this.mPhoneInputBox.setContent(str2, null, str);
    }

    /* access modifiers changed from: protected */
    public TextWatcher getPhoneChangedListener() {
        if (this.mPhoneChangedListener == null) {
            this.mPhoneChangedListener = new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void afterTextChanged(Editable editable) {
                    if (editable.toString().contains("*")) {
                        editable.clear();
                    }
                    SmsLoginView.this.onAccountChanged(editable.toString());
                }
            };
        }
        return this.mPhoneChangedListener;
    }

    /* access modifiers changed from: protected */
    public void onAccountChanged(String str) {
        if (TextUtils.isEmpty(str)) {
            setPortraitImage(false, null);
            return;
        }
        this.mCountryCode = this.mPhoneInputBox.getInputAreaCode();
        if (!"86".equals(this.mCountryCode)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mCountryCode);
            sb.append("-");
            sb.append(str);
            str = sb.toString();
        }
        UserInfo loginHistoryFromAccount = getLoginHistoryFromAccount(str.replace(Token.SEPARATOR, ""));
        if (loginHistoryFromAccount == null) {
            setPortraitImage(false, null);
        } else {
            setPortraitImage(true, loginHistoryFromAccount);
        }
    }

    public void onClick(View view) {
        if (R.id.ax == view.getId()) {
            LogAgent.a((String) "UC-LOG-161225-13", (String) "smsloginone", this.a, (String) "");
            this.mAttatchActivity.onRdsControlClick("smsLoginBtn");
            initMsgLogin(getLoginAccount(), null, null);
        } else if (R.id.aR == view.getId()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.mAttatchActivity.getString(R.string.A));
            arrayList.add(this.mAttatchActivity.getString(R.string.ci));
            arrayList.add(this.mAttatchActivity.getString(R.string.bA));
            showListDialog(arrayList);
        } else if (R.id.bF == view.getId()) {
            performDialogAction(this.mAttatchActivity.getString(R.string.ci));
        } else {
            super.onClick(view);
        }
    }

    /* access modifiers changed from: protected */
    public void performDialogAction(String str) {
        if (this.mAttatchActivity.getString(R.string.A).equals(str)) {
            this.mAttatchActivity.enterState(1);
            this.mAttatchActivity.clearAccount();
            return;
        }
        super.performDialogAction(str);
    }

    /* access modifiers changed from: protected */
    public void initMsgLogin(final String str, final String str2, final String str3) {
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                String str = str2;
                if (TextUtils.isEmpty(str)) {
                    str = SmsLoginView.this.mAttatchActivity.getRdsWraper().getRdsData(SmsLoginView.this.mApplicationContext, str);
                }
                SmsLoginView.access$000(SmsLoginView.this, str, str, str3);
            }
        });
    }

    public String getLoginAccount() {
        this.mCountryCode = this.mPhoneInputBox.getInputAreaCode();
        this.mCountryName = this.mPhoneInputBox.getCountryName();
        String inputPhoneNo = this.mPhoneInputBox.getInputPhoneNo();
        if (!inputPhoneNo.contains("*")) {
            if (!"86".equals(this.mCountryCode)) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.mCountryCode);
                sb.append("-");
                sb.append(inputPhoneNo);
                inputPhoneNo = sb.toString();
            }
            this.mLoginAccount = inputPhoneNo;
        }
        return this.mLoginAccount;
    }

    public String getShownAccount() {
        return this.mPhoneInputBox.getInputPhoneNo();
    }

    public void requestAccountFocus() {
        this.mPhoneInputBox.getPhoneInput().requestFocus();
        showInputMethodPannel(this.mPhoneInputBox.getPhoneInput());
    }

    public void clearAccount() {
        this.mPhoneInputBox.getPhoneInput().setText("");
        requestAccountFocus();
    }

    static /* synthetic */ void access$000(SmsLoginView smsLoginView, final String str, String str2, String str3) {
        smsLoginView.mAttatchActivity.showProgress("");
        try {
            MsgLoginParam msgLoginParam = new MsgLoginParam();
            msgLoginParam.loginId = str;
            msgLoginParam.envJson = str2;
            msgLoginParam.verifyId = str3;
            LoginSendMSGResPb a2 = AliuserLoginContext.d().a(msgLoginParam);
            AliUserLog.c("SmsLoginView", String.format("initMsgLogin result, code:%s, memo:%s", new Object[]{a2.resultCode, a2.memo}));
            smsLoginView.mAttatchActivity.dismissProgress();
            AliUserLog.c("SmsLoginView", "onInitMsgLoginResponse > ".concat(String.valueOf(str2)));
            if (a2 == null) {
                AliUserLog.d("SmsLoginView", "LoginSendMSGResPb=null");
            } else if ("1000".equals(a2.resultCode)) {
                AliUserLog.c("SmsLoginView", "sms without permission");
                Intent intent = new Intent(smsLoginView.mAttatchActivity, LoginManualSmsActivity.class);
                intent.putExtra("loginId", str);
                intent.putExtra("showAccount", smsLoginView.getShownAccount());
                intent.putExtra("countryCodeBetweenView", smsLoginView.mCountryCode);
                intent.putExtra("token", a2.token);
                if (smsLoginView.mCurrentSelectedHistory != null) {
                    intent.putExtra("userId", smsLoginView.mCurrentSelectedHistory.getUserId());
                }
                smsLoginView.mAttatchActivity.startActivityForResult(intent, 8192);
            } else if ("6401".equals(a2.resultCode)) {
                smsLoginView.mAttatchActivity.alert("", smsLoginView.mAttatchActivity.getString(R.string.cA), smsLoginView.mAttatchActivity.getString(R.string.bt), null, null, null);
            } else {
                if (!"6415".equals(a2.resultCode) && !"6414".equals(a2.resultCode) && !"6410".equals(a2.resultCode) && !"6413".equals(a2.resultCode) && !"6402".equals(a2.resultCode)) {
                    if (!"6403".equals(a2.resultCode)) {
                        if (!"6407".equals(a2.resultCode)) {
                            if (!"6405".equals(a2.resultCode)) {
                                if ("6406".equals(a2.resultCode)) {
                                    smsLoginView.mAttatchActivity.alert("", a2.memo, smsLoginView.mAttatchActivity.getString(R.string.ck), new OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            RegContext.a().a(SmsLoginView.this.mAttatchActivity, null, new Account(SmsLoginView.this.mCountryCode, str, SmsLoginView.this.mCountryName));
                                        }
                                    }, smsLoginView.mAttatchActivity.getString(R.string.J), null);
                                    return;
                                } else {
                                    smsLoginView.mAttatchActivity.toast(a2.memo, 0);
                                    return;
                                }
                            }
                        }
                        smsLoginView.mAttatchActivity.alert("", a2.memo, smsLoginView.mAttatchActivity.getString(R.string.bL), new OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SmsLoginView.this.mAttatchActivity.enterState(1);
                            }
                        }, null, null);
                        return;
                    }
                }
                smsLoginView.mAttatchActivity.alert("", a2.memo, smsLoginView.mAttatchActivity.getString(R.string.bt), null, null, null);
            }
        } catch (RpcException e) {
            smsLoginView.mAttatchActivity.dismissProgress();
            throw e;
        }
    }
}
