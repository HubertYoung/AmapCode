package com.ali.user.mobile.common.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.register.LogUtils;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.ali.user.mobile.ui.widget.AUInputBox;
import com.ali.user.mobile.ui.widget.EditTextHasNullChecker;
import com.ali.user.mobile.ui.widget.WidgetUtil;
import com.ali.user.mobile.ui.widget.keyboard.APSafeEditText;
import com.ali.user.mobile.util.ResizeScrollView;
import com.ali.user.mobile.util.ShowRegionHelper;

public abstract class AbsSupplyLoginPwdActivity extends BaseActivity implements OnClickListener {
    private static final String sTag = "Reg_AbsSupplyLoginPwdActivity";
    protected boolean mCanComeBack;
    protected Button mConfirm;
    private TextView mErrorTip;
    protected EditTextHasNullChecker mHasNullChecker;
    private APSafeEditText mLoginPasswordInput;
    private AUInputBox mLoginPwdInputBox;
    private TextView mMainTip;
    protected APTitleBar mTitle;

    public abstract void doSupply(String str);

    /* access modifiers changed from: protected */
    public String getInputHint() {
        return "";
    }

    /* access modifiers changed from: protected */
    public abstract String getPageName();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.k);
        initContext();
        initView();
        LogUtils.b("UC-ZC-161225-07", "loginpasswordpage", getPageName(), null);
    }

    private void initContext() {
        this.mHasNullChecker = new EditTextHasNullChecker();
    }

    private void initView() {
        initTitleBar();
        initRegisterPasswordErrorTips();
        initPwdInput();
        initButton();
        initWrapper();
    }

    private void initWrapper() {
        new ShowRegionHelper((ResizeScrollView) findViewById(R.id.bz)).a(this.mLoginPwdInputBox, this.mConfirm, true);
        View findViewById = findViewById(R.id.cM);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    AbsSupplyLoginPwdActivity.this.closeInputMethod(view);
                }
            });
        }
    }

    private void initButton() {
        this.mConfirm = (Button) findViewById(R.id.aZ);
        this.mConfirm.setOnClickListener(this);
        UIConfigManager.a(this.mConfirm);
        this.mHasNullChecker.a(this.mConfirm);
    }

    public void onPanelClosed(int i, Menu menu) {
        super.onPanelClosed(i, menu);
    }

    private void initPwdInput() {
        this.mLoginPwdInputBox = (AUInputBox) findViewById(R.id.bm);
        this.mLoginPasswordInput = (APSafeEditText) this.mLoginPwdInputBox.getEtContent();
        String inputHint = getInputHint();
        if (!TextUtils.isEmpty(inputHint)) {
            this.mLoginPwdInputBox.setHint(inputHint);
        }
        this.mLoginPwdInputBox.getSpecialFuncImg().setOnClickListener(this);
        this.mLoginPwdInputBox.setInputName(null);
        WidgetUtil.a((View) this.mLoginPwdInputBox, (EditText) this.mLoginPasswordInput);
        initInputSpecImage();
        this.mHasNullChecker.a((EditText) this.mLoginPasswordInput);
        this.mLoginPasswordInput.addTextChangedListener(this.mHasNullChecker);
        this.mLoginPasswordInput.addTextChangedListener(new TextWatcher() {
            private boolean b = false;

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (!this.b) {
                    LogUtils.d("UC-ZC-161225-08", "inloginpassword", AbsSupplyLoginPwdActivity.this.getPageName(), null);
                    this.b = true;
                }
                if (editable != null && !TextUtils.isEmpty(editable.toString())) {
                    AbsSupplyLoginPwdActivity.this.hideErrorTip();
                }
            }
        });
    }

    private void initInputSpecImage() {
        ImageButton specialFuncImg = this.mLoginPwdInputBox.getSpecialFuncImg();
        LayoutParams layoutParams = (LayoutParams) specialFuncImg.getLayoutParams();
        layoutParams.setMargins(getResources().getDimensionPixelOffset(R.dimen.f), 0, 0, 0);
        specialFuncImg.setLayoutParams(layoutParams);
        this.mLoginPasswordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        Drawable c = UIConfigManager.c();
        if (c == null) {
            this.mLoginPwdInputBox.setSepciaFunBtn(R.drawable.v);
        } else {
            this.mLoginPwdInputBox.setSepciaFunBtn(c);
        }
        this.mLoginPasswordInput.setSelection(this.mLoginPasswordInput.getSafeText().length());
    }

    private void initTitleBar() {
        this.mTitle = (APTitleBar) findViewById(R.id.cE);
        if (this.mTitle.getTitlebarBg() != null) {
            this.mTitle.getTitlebarBg().setBackgroundColor(-1);
        }
        this.mTitle.setImageBackButtonIcon(R.drawable.L);
        int i = 8;
        Intent intent = getIntent();
        if (intent != null) {
            try {
                boolean booleanExtra = intent.getBooleanExtra("come_back", false);
                this.mCanComeBack = booleanExtra;
                if (booleanExtra) {
                    i = 0;
                }
            } catch (Throwable th) {
                AliUserLog.b(sTag, "get intent ", th);
            }
        }
        this.mTitle.setVisibility(i);
        this.mMainTip = (TextView) findViewById(R.id.aA);
    }

    private void initRegisterPasswordErrorTips() {
        this.mErrorTip = (TextView) findViewById(R.id.bl);
        String string = getResources().getString(R.string.cm);
        String string2 = getResources().getString(R.string.bm);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(string);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.A)), 0, string.length(), 33);
        spannableStringBuilder.append(string2);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.B)), string.length(), spannableStringBuilder.length(), 33);
        this.mErrorTip.setText(spannableStringBuilder);
    }

    /* access modifiers changed from: protected */
    public void setMainTip(CharSequence charSequence) {
        this.mMainTip.setText(charSequence);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.bG) {
            LogUtils.a("UC-ZC-150512-05", "zcpwdyc", getPageName(), null);
            switchTransformationMethod();
            return;
        }
        if (R.id.aZ == view.getId()) {
            LogUtils.a("UC-ZC-161225-10", "enterloginpassword", getPageName(), null);
            if (this.mLoginPasswordInput != null) {
                setButtonEnable(this.mConfirm, false);
                closeInputMethod(this.mLoginPasswordInput);
                doSupply(this.mLoginPasswordInput.getSafeText().toString());
            }
        }
    }

    private void switchTransformationMethod() {
        if (this.mLoginPasswordInput.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
            this.mLoginPasswordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            Drawable d = UIConfigManager.d();
            if (d == null) {
                this.mLoginPwdInputBox.setSepciaFunBtn(R.drawable.u);
            } else {
                this.mLoginPwdInputBox.setSepciaFunBtn(d);
            }
            this.mLoginPasswordInput.setSelection(this.mLoginPasswordInput.getSafeText().length());
            return;
        }
        if (this.mLoginPasswordInput.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            this.mLoginPasswordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            Drawable c = UIConfigManager.c();
            if (c == null) {
                this.mLoginPwdInputBox.setSepciaFunBtn(R.drawable.v);
            } else {
                this.mLoginPwdInputBox.setSepciaFunBtn(c);
            }
            this.mLoginPasswordInput.setSelection(this.mLoginPasswordInput.getSafeText().length());
        }
    }

    public void onBackPressed() {
        if (this.mCanComeBack) {
            super.onBackPressed();
            LogUtils.a("UC-ZC-161225-09", "loginpasswordback", getPageName(), null);
        }
    }

    /* access modifiers changed from: protected */
    public void showErrorTip(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            this.mErrorTip.setText(str);
        }
        this.mErrorTip.setVisibility(0);
        if (z) {
            this.mLoginPasswordInput.setText("");
        }
    }

    /* access modifiers changed from: protected */
    public void hideErrorTip() {
        if (this.mErrorTip != null && this.mErrorTip.getVisibility() == 0) {
            this.mErrorTip.setVisibility(4);
        }
    }
}
