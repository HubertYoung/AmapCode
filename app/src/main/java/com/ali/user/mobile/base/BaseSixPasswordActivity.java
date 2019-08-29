package com.ali.user.mobile.base;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.register.TaoUrlSpan;
import com.ali.user.mobile.rpc.vo.mobilegw.RSAPKeyResult;
import com.ali.user.mobile.rsa.Rsa;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APSixNumberPwdInputBox;
import com.ali.user.mobile.ui.widget.APSixNumberPwdInputBox.PWDInputListener;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.ali.user.mobile.ui.widget.AUCheckboxWithLinkText;
import com.alipay.inside.android.phone.mrpc.core.RpcException;

public class BaseSixPasswordActivity extends BackgroundLoginActivity implements OnClickListener, PWDInputListener {
    private static final String TAG = "BaseSixPasswordActivity";
    protected String agreementURL;
    protected String insuranceInfo;
    protected Button mConfirmSettingBtn;
    protected TextView mInsuranceSureText;
    protected AUCheckboxWithLinkText mInsuranceText;
    protected APSixNumberPwdInputBox mSixNumberInput;
    protected APTitleBar mTitleBar;
    protected boolean optionStatus = false;

    /* access modifiers changed from: protected */
    public void onSupplement(String str) {
    }

    /* access modifiers changed from: protected */
    public void setAppId() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                this.insuranceInfo = extras.getString("showOptionalInfo");
                this.agreementURL = extras.getString("agreementURL");
                this.optionStatus = extras.getBoolean("optionStatus");
            }
        }
        initViews();
        afterViews();
    }

    /* access modifiers changed from: protected */
    public void initViews() {
        setContentView(R.layout.n);
        this.mTitleBar = (APTitleBar) findViewById(R.id.ca);
        this.mSixNumberInput = (APSixNumberPwdInputBox) findViewById(R.id.bB);
        this.mConfirmSettingBtn = (Button) findViewById(R.id.K);
        this.mInsuranceText = (AUCheckboxWithLinkText) findViewById(R.id.ag);
        this.mInsuranceSureText = (TextView) findViewById(R.id.ah);
    }

    /* access modifiers changed from: protected */
    public void afterViews() {
        this.mTitleBar.setTitleText(getString(R.string.cw));
        this.mConfirmSettingBtn.setEnabled(false);
        this.mConfirmSettingBtn.setOnClickListener(this);
        this.mSixNumberInput.setPwdInputListener((PWDInputListener) this);
        this.mSixNumberInput.getSafeEditText().setAutoShowSafeKeyboard(true);
        this.mSixNumberInput.getSafeEditText().requestFocus();
        if (TextUtils.isEmpty(this.insuranceInfo)) {
            this.mInsuranceText.setVisibility(8);
        } else {
            this.mInsuranceText.setVisibility(0);
            this.mInsuranceText.getLinkTextView().setTextColor(-7763575);
            this.mInsuranceText.getLinkTextView().setText(this.insuranceInfo);
            this.mInsuranceText.getCheckBox().setChecked(this.optionStatus);
            this.mInsuranceText.getCheckBox().setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    BaseSixPasswordActivity.this.optionStatus = z;
                }
            });
            if (TextUtils.isEmpty(this.agreementURL)) {
                this.mInsuranceSureText.setVisibility(8);
            } else {
                this.mInsuranceSureText.setVisibility(0);
                initSure(this.agreementURL);
            }
        }
        showInputMethodPannel(this.mSixNumberInput.getSafeEditText());
        UIConfigManager.a(this.mConfirmSettingBtn);
        UIConfigManager.a(this.mTitleBar);
    }

    /* access modifiers changed from: protected */
    public void initSure(String str) {
        String string = getResources().getString(R.string.h);
        String string2 = getResources().getString(R.string.bu);
        String replace = string.replace("$insure", string2);
        int indexOf = replace.indexOf(string2);
        if (indexOf >= 0) {
            int length = string2.length() + indexOf;
            SpannableString spannableString = new SpannableString(replace);
            spannableString.setSpan(new TaoUrlSpan(str).setContext(this), indexOf, length, 33);
            int e = UIConfigManager.e();
            if (e == Integer.MAX_VALUE) {
                e = getResources().getColor(R.color.k);
            }
            spannableString.setSpan(new ForegroundColorSpan(e), indexOf, length, 33);
            this.mInsuranceSureText.setText(spannableString);
            this.mInsuranceSureText.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.K) {
            closeKeyboard();
            doSupplement();
        }
    }

    /* access modifiers changed from: protected */
    public void doSupplement() {
        AnonymousClass2 r1 = new Runnable() {
            public void run() {
                try {
                    BaseSixPasswordActivity.this.showProgress("");
                    RSAPKeyResult a2 = Rsa.a(BaseSixPasswordActivity.this.getApplicationContext());
                    if (a2 == null) {
                        BaseSixPasswordActivity.this.dismissProgress();
                        throw new IllegalArgumentException("get rsa from server failed!!!");
                    }
                    BaseSixPasswordActivity.this.onSupplement(Rsa.a(BaseSixPasswordActivity.this.getOriginalKey(), a2.rsaPK));
                } catch (RpcException e) {
                    BaseSixPasswordActivity.this.dismissProgress();
                    throw e;
                }
            }
        };
        StringBuilder sb = new StringBuilder("Aliuser-SupplySixPassword-");
        sb.append(getClass().getSimpleName());
        new Thread(r1, sb.toString()).start();
    }

    public void pwdInputed(int i, Editable editable) {
        StringBuilder sb = new StringBuilder("pwdInputed, len:");
        sb.append(editable == null ? "null" : Integer.valueOf(editable.length()));
        AliUserLog.c(TAG, sb.toString());
        if (editable == null || editable.length() != 6) {
            this.mConfirmSettingBtn.setEnabled(false);
        } else {
            this.mConfirmSettingBtn.setEnabled(true);
        }
    }

    /* access modifiers changed from: protected */
    public void closeKeyboard() {
        this.mSixNumberInput.hideInputMethod();
    }

    /* access modifiers changed from: protected */
    public String getOriginalKey() {
        return this.mSixNumberInput.getInputValue();
    }
}
