package com.ali.user.mobile.login.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.login.AbsNotifyFinishCaller;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.OnLoginCaller;
import com.ali.user.mobile.login.ui.AliUserLoginActivity;
import com.ali.user.mobile.login.ui.adapter.LoginHistoryAdapter;
import com.ali.user.mobile.resolver.ConfigResolver;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APListView;
import com.ali.user.mobile.ui.widget.AUAccessibilityDelegate;
import com.ali.user.mobile.ui.widget.AUInputBox;
import com.ali.user.mobile.ui.widget.EditTextHasNullChecker;
import com.ali.user.mobile.ui.widget.WidgetUtil;
import com.ali.user.mobile.ui.widget.keyboard.APSafeEditText;
import com.ali.user.mobile.ui.widget.keyboard.APSafeTextWatcher;
import com.ali.user.mobile.util.StringUtil;
import com.ali.user.mobile.utils.AccessibilityUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.drivecommon.inter.NetConstant;
import com.autonavi.common.SuperId;
import java.util.HashMap;
import java.util.Map;

public abstract class PasswordLoginView extends LoginView {
    protected boolean isDropdownAccount;
    protected APSafeEditText mAccountInput;
    protected AUInputBox mAccountInputBox;
    protected ImageButton mArrowDownView;
    protected int mInputLeftMargin = 0;
    protected boolean mIsAccountInputted = false;
    protected boolean mIsPasswordInputted = false;
    protected LoginHistoryAdapter<UserInfo> mLoginHistoryAdapter;
    protected APListView mLoginHistoryList;
    protected ImageButton mPasswordChangeView;
    protected APSafeEditText mPasswordInput;
    protected AUInputBox mPasswordInputBox;
    protected String mPasswordInputType = SuperId.BIT_1_MAIN_BUSSTATION;
    protected Map<String, String> mSmsLoginMap = new HashMap();

    public PasswordLoginView(AliUserLoginActivity aliUserLoginActivity) {
        super(aliUserLoginActivity);
    }

    /* access modifiers changed from: protected */
    public void doInflate(Context context) {
        super.doInflate(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.y, this, true);
        this.mAccountInputBox = (AUInputBox) inflate.findViewById(R.id.cH);
        this.mAccountInput = (APSafeEditText) this.mAccountInputBox.getEtContent();
        this.mArrowDownView = this.mAccountInputBox.getSpecialFuncImg();
        this.mLoginHistoryList = (APListView) inflate.findViewById(R.id.aa);
        this.mPasswordInputBox = (AUInputBox) inflate.findViewById(R.id.cI);
        this.mPasswordInput = (APSafeEditText) this.mPasswordInputBox.getEtContent();
        this.mPasswordChangeView = this.mPasswordInputBox.getSpecialFuncImg();
        this.isDropdownAccount = false;
        WidgetUtil.a((View) this.mAccountInputBox, (EditText) this.mAccountInput);
        WidgetUtil.a((View) this.mPasswordInputBox, (EditText) this.mPasswordInput);
        TextPaint paint = this.mPasswordInputBox.getInputName().getPaint();
        paint.setTextSize(this.mPasswordInputBox.getInputName().getTextSize());
        int max = Math.max(((int) paint.measureText(this.mAccountInputBox.getInputName().getText().toString())) + 1, ((int) paint.measureText(this.mPasswordInputBox.getInputName().getText().toString())) + 1);
        this.mInputLeftMargin = WidgetUtil.a(getResources(), this.mAccountInput, 0, max);
        WidgetUtil.a(getResources(), this.mPasswordInput, 0, max);
        setPortraitImage(false, null);
        if (((AccessibilityManager) this.mApplicationContext.getSystemService("accessibility")).isEnabled()) {
            this.mPasswordInput.setAccessibilityProtect(true);
        } else if (VERSION.SDK_INT >= 14) {
            this.mPasswordInput.setAccessibilityDelegate(new AUAccessibilityDelegate());
        }
        this.mAccountInput.addTextChangedListener(new APSafeTextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                PasswordLoginView.this.hideLoginHistory();
                if (PasswordLoginView.this.isDropdownAccount) {
                    PasswordLoginView.this.clearAccount();
                    editable.clear();
                }
            }
        });
        final OnFocusChangeListener onFocusChangeListener = this.mAccountInput.getOnFocusChangeListener();
        this.mAccountInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    PasswordLoginView.this.hideLoginHistory();
                } else {
                    PasswordLoginView.access$000(PasswordLoginView.this, PasswordLoginView.this.mAccountInput.getText().toString());
                }
                if (onFocusChangeListener != null) {
                    onFocusChangeListener.onFocusChange(view, z);
                }
            }
        });
        this.mPasswordChangeView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PasswordLoginView.this.mAttatchActivity.onRdsControlClick("SeePwdBtn");
                if (PasswordLoginView.this.mPasswordInput.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                    PasswordLoginView.access$100(PasswordLoginView.this);
                    return;
                }
                if (PasswordLoginView.this.mPasswordInput.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    PasswordLoginView.this.showInputPassword();
                }
            }
        });
        final OnFocusChangeListener onFocusChangeListener2 = this.mPasswordInput.getOnFocusChangeListener();
        this.mPasswordInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    PasswordLoginView.this.hideLoginHistory();
                }
                if (onFocusChangeListener2 != null) {
                    onFocusChangeListener2.onFocusChange(view, z);
                }
            }
        });
        this.mArrowDownView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PasswordLoginView.this.writeClickLog("YWUC-JTTYZH-C23", "dropdown", "loginAccountSelectView");
                PasswordLoginView.this.closeInputMethod(PasswordLoginView.this.mAccountInput);
                if (PasswordLoginView.this.mLoginHistoryList.getVisibility() == 8) {
                    PasswordLoginView.this.showLoginHistory();
                } else {
                    PasswordLoginView.this.hideLoginHistory();
                }
            }
        });
        setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View focusedChild = PasswordLoginView.this.getFocusedChild();
                if (focusedChild != null) {
                    focusedChild.clearFocus();
                }
                PasswordLoginView.this.hideLoginHistory();
                PasswordLoginView.this.closeInputMethod(view);
            }
        });
        initLoginHistoryList();
    }

    /* access modifiers changed from: protected */
    public void initInputTrace() {
        this.mAccountInput.addTextChangedListener(new APSafeTextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (!PasswordLoginView.this.mIsAccountInputted) {
                    PasswordLoginView.this.mIsAccountInputted = true;
                    LogAgent.f("UC-LOG-161225-02", "accountinput", PasswordLoginView.this.mFrom, null, null);
                }
            }
        });
        this.mPasswordInput.addTextChangedListener(new APSafeTextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (!PasswordLoginView.this.mIsPasswordInputted) {
                    PasswordLoginView.this.mIsPasswordInputted = true;
                    LogAgent.f("UC-LOG-161225-04", "loginpasswordinput", PasswordLoginView.this.mFrom, null, null);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void uiCustomize() {
        super.uiCustomize();
        Drawable d = UIConfigManager.d();
        if (d == null) {
            this.mPasswordInputBox.setSepciaFunBtn(R.drawable.u);
        } else {
            this.mPasswordInputBox.setSepciaFunBtn(d);
        }
        String b = UIConfigManager.b();
        if (b != null) {
            this.mAccountInput.setHint(b);
        }
    }

    /* access modifiers changed from: protected */
    public void initRds() {
        super.initRds();
        this.mAttatchActivity.initRdsTextChange(this.mAccountInput, "UsernameET");
        this.mAttatchActivity.initRdsFocusChange(this.mAccountInput, "UsernameET");
        this.mAttatchActivity.initRdsFocusChange(this.mPasswordInput, "PwdET");
    }

    /* access modifiers changed from: protected */
    public boolean isOpenSmsLoginNative() {
        String a = ConfigResolver.a(this.mAttatchActivity.getApplicationContext(), "sms_login_enable");
        AliUserLog.c("PasswordLoginView", "smsEnable = ".concat(String.valueOf(a)));
        return "YES".equalsIgnoreCase(a);
    }

    /* access modifiers changed from: protected */
    public boolean trustLoginWithExtLoginParam() {
        if (this.mParams != null) {
            LoginParam loginParam = (LoginParam) this.mParams.get("login_param");
            if (loginParam != null) {
                AliUserLog.c("PasswordLoginView", String.format("has extLoginParam, validateTpye:%s, token:%s, ssoToken:%s", new Object[]{loginParam.validateTpye, loginParam.token, loginParam.ssoToken}));
                setAccount(loginParam.loginAccount, !this.mAttatchActivity.getIsFromRegist());
                if (!TextUtils.isEmpty(loginParam.validateTpye)) {
                    AliUserLog.c("PasswordLoginView", "发起信任登录，后续流程全部忽略");
                    this.mAttatchActivity.getLoginParams(loginParam);
                    if ("withonekeytoken".equals(loginParam.validateTpye)) {
                        loginParam.addExternalParam("userId", this.mCurrentSelectedHistory.getUserId());
                    }
                    this.mAttatchActivity.doUnifyLogin(loginParam);
                    return true;
                }
            }
        }
        return false;
    }

    public String getLoginAccount() {
        if (this.isDropdownAccount) {
            return this.mCurrentSelectedAccount;
        }
        String obj = this.mAccountInput.getText().toString();
        if (obj.contains("－")) {
            obj = obj.replace("－", "-");
            this.mAccountInput.setText(obj);
        }
        return obj;
    }

    public String getShownAccount() {
        return this.mAccountInput.getText().toString();
    }

    public void onNewAccount(String str, int i) {
        setAccount(str, (i == 45057 || i == 53248) ? false : true);
    }

    public void clearAccount() {
        this.isDropdownAccount = false;
        this.mCurrentSelectedAccount = "";
        this.mCurrentSelectedHistory = null;
        this.mAccountInput.setText("");
        setPortraitImage(false, null);
        requestAccountFocus();
    }

    public String getLoginPassword() {
        return this.mPasswordInput.getSafeText().toString();
    }

    public void clearPassword() {
        this.mAttatchActivity.runOnUiThread(new Runnable() {
            public void run() {
                PasswordLoginView.this.mPasswordInput.setText("");
            }
        });
    }

    public void requestAccountFocus() {
        this.mAccountInput.requestFocus();
        showInputMethodPannel(this.mAccountInput);
    }

    public void requestPasswordFocus() {
        this.mPasswordInput.requestFocus();
        showInputMethodPannel(this.mPasswordInput);
    }

    /* access modifiers changed from: protected */
    public void initLoginHistoryList() {
        if (!hasLoginHistory() || !TextUtils.isEmpty(this.mAttatchActivity.mInsideAccount)) {
            if (!TextUtils.isEmpty(this.mAttatchActivity.mInsideAccount)) {
                setAccount(this.mAttatchActivity.mInsideAccount, true);
            }
            this.mArrowDownView.setVisibility(8);
            return;
        }
        this.mArrowDownView.setImageResource(R.drawable.c);
        this.mArrowDownView.setVisibility(0);
        this.mCurrentSelectedHistory = (UserInfo) this.mLoginHistorys.get(0);
        LoginHistoryAdapter loginHistoryAdapter = new LoginHistoryAdapter(this.mAttatchActivity, null, new OnClickListener() {
            public void onClick(View view) {
                LogAgent.f("UC-LOG-161225-03", "accountchoose", PasswordLoginView.this.mFrom, null, null);
                PasswordLoginView.this.hideLoginHistory();
                UserInfo userInfo = (UserInfo) view.getTag();
                if (TextUtils.isEmpty(PasswordLoginView.this.mCurrentSelectedAccount) || !PasswordLoginView.this.mCurrentSelectedAccount.equals(userInfo.getLogonId())) {
                    PasswordLoginView.this.mPasswordInput.setText(null);
                }
                PasswordLoginView.this.setLoginHistoryAccount(userInfo);
                PasswordLoginView.this.mPasswordInput.requestFocus();
            }
        }, this.mLoginHistorys, BehavorReporter.PROVIDE_BY_ALIPAY);
        this.mLoginHistoryAdapter = loginHistoryAdapter;
        this.mLoginHistoryAdapter.a(this.mInputLeftMargin);
        this.mLoginHistoryList.setDivider(null);
        this.mLoginHistoryList.setDividerHeight(0);
        this.mLoginHistoryList.setAdapter(this.mLoginHistoryAdapter);
        LayoutParams layoutParams = this.mLoginHistoryList.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.j) * this.mLoginHistorys.size();
        this.mLoginHistoryList.setLayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public void showLoginHistory() {
        this.mArrowDownView.setImageResource(R.drawable.e);
        this.mLoginHistoryList.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void hideLoginHistory() {
        this.mArrowDownView.setImageResource(R.drawable.c);
        this.mLoginHistoryList.setVisibility(8);
    }

    public void showInputPassword() {
        this.mPasswordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        Drawable c = UIConfigManager.c();
        if (c == null) {
            this.mPasswordInputBox.setSepciaFunBtn(R.drawable.v);
        } else {
            this.mPasswordInputBox.setSepciaFunBtn(c);
        }
        this.mPasswordInputType = DictionaryKeys.CTRLXY_Y;
        this.mPasswordInput.setSelection(this.mPasswordInput.getSafeText().length());
        if (this.mAttatchActivity != null) {
            this.mAttatchActivity.onPasswordVisibleChange(true);
        }
    }

    /* access modifiers changed from: protected */
    public void initInputBoxIme(final Button button) {
        this.mPasswordInput.setImeOptions(6);
        this.mPasswordInput.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                StringBuilder sb = new StringBuilder("actionId: ");
                sb.append(i);
                sb.append("loginButton status: ");
                sb.append(button.isEnabled());
                AliUserLog.a((String) "PasswordLoginView", sb.toString());
                if (6 != i || !button.isEnabled()) {
                    return false;
                }
                PasswordLoginView.this.mAttatchActivity.onRdsControlClick("LoginBtn");
                PasswordLoginView.this.onLoginClicked(button);
                return true;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setLoginHistoryAccount(UserInfo userInfo) {
        if (userInfo != null) {
            this.mCurrentSelectedHistory = userInfo;
            setPortraitImage(true, userInfo);
            setAccount(userInfo.getLogonId(), true);
        }
    }

    /* access modifiers changed from: protected */
    public void setAccount(String str, boolean z) {
        this.mCurrentSelectedAccount = str;
        if (z) {
            str = StringUtil.a(str, (String) BehavorReporter.PROVIDE_BY_ALIPAY);
        }
        this.isDropdownAccount = false;
        if (!TextUtils.isEmpty(str)) {
            this.mAccountInput.setText(str);
            this.mAccountInput.setSelection(str.length());
        }
        this.isDropdownAccount = true;
    }

    /* access modifiers changed from: protected */
    public void addNullCheckButton(Button button) {
        EditTextHasNullChecker editTextHasNullChecker = new EditTextHasNullChecker();
        editTextHasNullChecker.a(button);
        editTextHasNullChecker.a((EditText) this.mAccountInput);
        this.mAccountInput.addTextChangedListener(editTextHasNullChecker);
        editTextHasNullChecker.a((EditText) this.mPasswordInput);
        this.mPasswordInput.addTextChangedListener(editTextHasNullChecker);
    }

    /* access modifiers changed from: protected */
    public void performDialogAction(String str, boolean z) {
        if (this.mAttatchActivity.getString(R.string.cz).equals(str)) {
            clearPassword();
            this.mAttatchActivity.toSmsLogin();
            LogAgent.c("smsLogin_ClickSendMsgToH5", "UC-LOG-150512-06", "loginsms", null, H5PageData.KEY_UC_START);
            return;
        }
        super.performDialogAction(str, z);
    }

    /* access modifiers changed from: protected */
    public void doForgotPasswordAction() {
        LogAgent.a("UC-LOG-161225-07", "Forgotpassword");
        clearPassword();
        this.mAttatchActivity.toForgetPassword(this.mAttatchActivity.getLoginAccount(), null);
    }

    /* access modifiers changed from: protected */
    public void checkAccessibility() {
        if (AccessibilityUtils.a(this.mApplicationContext)) {
            ArrayMap arrayMap = new ArrayMap(4);
            arrayMap.put(NetConstant.KEY_MONEY_ACCOUNT, getLoginAccount());
            arrayMap.put(DictionaryKeys.DEV_APDIDTOKEN, AppInfo.getInstance().getApdid());
            arrayMap.put("accessibilityEnv", AccessibilityUtils.b(this.mApplicationContext));
            LogAgent.a((String) "UC-LOG-160414-01", (String) "dlmazt", (String) null, (String) null, (String) null, (Map<String, String>) arrayMap);
        }
    }

    public void onLoginClicked(Button button) {
        AliUserLog.c("PasswordLoginView", "onLoginClicked");
        LogAgent.b("YWUC-JTTYZH-C57", "", "loginAccountInputView", getLoginAccount(), this.mPasswordInputType);
        writeClickLog("YWUC-JTTYZH-C09", "login", "loginAccountInputView");
        OnLoginCaller i = AliuserLoginContext.i();
        if (i != null) {
            i.a(null, new AbsNotifyFinishCaller());
        }
        closeInputMethod(button);
        this.mAttatchActivity.onRdsControlClick("LoginBtn");
        this.mAttatchActivity.unifyLogin();
        checkAccessibility();
    }

    static /* synthetic */ void access$000(PasswordLoginView passwordLoginView, String str) {
        if (!TextUtils.isEmpty(str)) {
            for (int i = 0; i < passwordLoginView.mLoginHistorys.size(); i++) {
                UserInfo userInfo = (UserInfo) passwordLoginView.mLoginHistorys.get(i);
                if (userInfo != null && str.equals(userInfo.getLogonId())) {
                    if (TextUtils.isEmpty(passwordLoginView.mCurrentSelectedAccount) || !passwordLoginView.mCurrentSelectedAccount.equals(userInfo.getLogonId())) {
                        passwordLoginView.mCurrentSelectedAccount = userInfo.getLogonId();
                        passwordLoginView.mCurrentSelectedHistory = userInfo;
                    }
                    passwordLoginView.isDropdownAccount = false;
                    if (!TextUtils.isEmpty(userInfo.getLogonId())) {
                        passwordLoginView.mAccountInput.setText(userInfo.getLogonId());
                        passwordLoginView.mAccountInput.setSelection(userInfo.getLogonId().length());
                    }
                    passwordLoginView.setPortraitImage(true, userInfo);
                    passwordLoginView.isDropdownAccount = true;
                }
            }
        }
    }

    static /* synthetic */ void access$100(PasswordLoginView passwordLoginView) {
        passwordLoginView.mPasswordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Drawable d = UIConfigManager.d();
        if (d == null) {
            passwordLoginView.mPasswordInputBox.setSepciaFunBtn(R.drawable.u);
        } else {
            passwordLoginView.mPasswordInputBox.setSepciaFunBtn(d);
        }
        passwordLoginView.mPasswordInputType = SuperId.BIT_1_MAIN_BUSSTATION;
        passwordLoginView.mPasswordInput.setSelection(passwordLoginView.mPasswordInput.getSafeText().length());
        LogAgent.a("UC-LOG-161225-05", "loginpwdyc");
        if (passwordLoginView.mAttatchActivity != null) {
            passwordLoginView.mAttatchActivity.onPasswordVisibleChange(false);
        }
    }
}
