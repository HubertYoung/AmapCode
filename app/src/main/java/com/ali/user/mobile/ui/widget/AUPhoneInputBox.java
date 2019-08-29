package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.login.rds.RDSWraper;
import com.ali.user.mobile.register.LogUtils;
import com.ali.user.mobile.register.region.RegionChoice;
import com.ali.user.mobile.register.region.RegionChoice.RegionCallback;
import com.ali.user.mobile.register.region.RegionInfo;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.inputfomatter.APSplitTextFormatter;
import com.ali.user.mobile.utils.ResourceUtil;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class AUPhoneInputBox extends LinearLayout implements OnClickListener {
    /* access modifiers changed from: private */
    public static final char[] a = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-'};
    private TextView b;
    private AUInputBox c;
    private RDSWraper d;
    /* access modifiers changed from: private */
    public BaseActivity e;
    private EditTextHasNullChecker f;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public IPhoneChangeListener h;
    protected EditText mPhoneInput;

    public interface IPhoneChangeListener {
        void a(String str, String str2, String str3);
    }

    public AUPhoneInputBox(Context context) {
        super(context);
        a(context);
    }

    public AUPhoneInputBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public void attatchActivity(BaseActivity baseActivity) {
        this.e = baseActivity;
    }

    private void a(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.z, this, true);
        this.c = (AUInputBox) inflate.findViewById(R.id.aW);
        this.mPhoneInput = this.c.getEtContent();
        this.b = (TextView) inflate.findViewById(R.id.bo);
        ((RelativeLayout) inflate.findViewById(R.id.bn)).setOnClickListener(this);
        this.f = new EditTextHasNullChecker();
        this.f.a(this.mPhoneInput);
        this.mPhoneInput.addTextChangedListener(this.f);
        this.mPhoneInput.addTextChangedListener(new TextWatcher() {
            private boolean b = false;

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (!this.b) {
                    LogUtils.d("UC-ZC-161225-01", "phoneinput", AUPhoneInputBox.this.g, null);
                    this.b = true;
                }
                if (AUPhoneInputBox.this.h != null) {
                    AUPhoneInputBox.this.h.a(AUPhoneInputBox.this.getInputAreaCode(), AUPhoneInputBox.this.getInputPhoneNo(), AUPhoneInputBox.this.getCountryName());
                }
            }
        });
        this.mPhoneInput.setKeyListener(new NumberKeyListener() {
            /* access modifiers changed from: protected */
            public char[] getAcceptedChars() {
                return AUPhoneInputBox.a;
            }

            public int getInputType() {
                return AUPhoneInputBox.this.mPhoneInput.getInputType();
            }
        });
        WidgetUtil.a((View) this.c, this.mPhoneInput);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.h);
        WidgetUtil.a(getResources(), this.mPhoneInput, dimensionPixelOffset, ((int) this.c.getInputName().getPaint().measureText(this.c.getInputName().getText().toString())) + 1);
    }

    public final String getCountryName() {
        if (this.b != null) {
            return this.b.getText().toString();
        }
        return null;
    }

    public void addNeedEnabledButton(Button button) {
        this.f.a(button);
    }

    public EditText getPhoneInput() {
        return this.mPhoneInput;
    }

    public void setRdsWrapper(RDSWraper rDSWraper) {
        this.d = rDSWraper;
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.h != null) {
            this.h.a(getInputAreaCode(), getInputPhoneNo(), getCountryName());
        }
    }

    /* access modifiers changed from: private */
    public boolean a(String str, String str2) {
        String replace = str == null ? "" : str.replace("+", "");
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            str = getResources().getString(R.string.bM);
            str2 = getResources().getString(R.string.C);
            z = true;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = ResourceUtil.a("alipay_country_".concat(String.valueOf(replace)));
            if (TextUtils.isEmpty(str2)) {
                str = getResources().getString(R.string.bM);
                str2 = getResources().getString(R.string.C);
                z = true;
            }
        }
        if (getResources().getString(R.string.bM).equals(str)) {
            this.c.setTextFormatter(new APSplitTextFormatter("3,8"));
            if (this.c.getInputedText() != null) {
                this.c.setText(this.c.getInputedText());
            }
        } else {
            this.c.setTextFormatter(null);
            if (this.c.getInputedText() != null) {
                this.c.setText(getInputPhoneNo());
            }
        }
        this.c.setInputName(str);
        this.b.setText(str2);
        return z;
    }

    public void setContent(String str, String str2, String str3) {
        if (a(str, str2)) {
            a();
            return;
        }
        this.mPhoneInput.setText(str3);
        this.mPhoneInput.setSelection(this.mPhoneInput.getText().length());
    }

    public final String getInputPhoneNo() {
        return (this.c == null || this.c.getInputedText() == null) ? "" : this.c.getInputedText().replace(Token.SEPARATOR, "");
    }

    public final String getInputAreaCode() {
        return (this.c == null || TextUtils.isEmpty(this.c.getInputName().getText())) ? "" : this.c.getInputName().getText().toString().replace("+", "");
    }

    public void setAreaCode(String str) {
        if (this.c != null) {
            this.c.setInputName("+".concat(String.valueOf(str)));
        }
    }

    public void onClick(View view) {
        if (R.id.bn == view.getId()) {
            this.d.onControlClick("OverseaBtn");
            if (this.e != null) {
                this.e.closeInputMethod(this);
            }
            LogUtils.a("UC-ZC-150512-02", "zchaiwai", this.g, null);
            SecurityUtil.a((Runnable) new Runnable() {
                public void run() {
                    try {
                        RegionChoice.a().a(AUPhoneInputBox.this.e, new RegionCallback() {
                            public final void a() {
                                AUPhoneInputBox.this.e.showProgress("");
                            }

                            public final void b() {
                                AUPhoneInputBox.this.e.dismissProgress();
                            }

                            public final void a(RegionInfo regionInfo) {
                                if (regionInfo != null) {
                                    AUPhoneInputBox.this.a(regionInfo.mRegionNumber, regionInfo.mRegionName);
                                    LogUtils.d("UC-ZC-161209-01", "locationResult", AUPhoneInputBox.this.g, regionInfo.mRegionNumber);
                                    AUPhoneInputBox.this.a();
                                }
                            }

                            public final void a(String str) {
                                AUPhoneInputBox.this.e.toast(str, 3000);
                            }
                        });
                    } catch (RpcException e) {
                        AUPhoneInputBox.this.e.dismissProgress();
                        throw e;
                    }
                }
            });
        }
    }

    public void setPhoneChangeListener(IPhoneChangeListener iPhoneChangeListener) {
        this.h = iPhoneChangeListener;
    }

    public void setPageName(String str) {
        this.g = str;
    }
}
