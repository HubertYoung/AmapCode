package com.alipay.mobile.antui.profession;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView.BufferType;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.clickspan.AgreementClickableSpan;
import com.alipay.mobile.antui.clickspan.UrlClickableSpanListener;
import com.alipay.mobile.antui.common.AUCheckIcon;

public class AUAgreementView extends AULinearLayout {
    private AUTextView mAgreeTextView;
    private AUCheckIcon mCheckIcon;

    public class AgreeTextBuilder {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        public AgreeTextBuilder() {
        }

        public AgreeTextBuilder appendCommonText(CharSequence charSequence) {
            if (!TextUtils.isEmpty(charSequence)) {
                this.spannableStringBuilder.append(charSequence);
            }
            return this;
        }

        public AgreeTextBuilder appendClickText(CharSequence charSequence, String url, UrlClickableSpanListener listener) {
            if (!TextUtils.isEmpty(charSequence)) {
                this.spannableStringBuilder.append(charSequence);
                this.spannableStringBuilder.setSpan(new AgreementClickableSpan(AUAgreementView.this.getContext(), url, listener), this.spannableStringBuilder.length() - charSequence.length(), this.spannableStringBuilder.length(), 33);
            }
            return this;
        }

        public SpannableStringBuilder buildText() {
            return this.spannableStringBuilder;
        }
    }

    public AUAgreementView(Context context) {
        super(context);
        init(context);
    }

    public AUAgreementView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AUAgreementView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_agreement_view, this);
        setOrientation(0);
        int padding_v = getResources().getDimensionPixelOffset(R.dimen.au_agreement_padding_v);
        int padding_h = getResources().getDimensionPixelOffset(R.dimen.au_agreement_padding_h);
        setPadding(padding_h, padding_v, padding_h, padding_v);
        this.mCheckIcon = (AUCheckIcon) findViewById(R.id.agree_check);
        this.mAgreeTextView = (AUTextView) findViewById(R.id.agree_content);
        this.mAgreeTextView.setMovementMethod(LinkMovementMethod.getInstance());
        this.mAgreeTextView.setHighlightColor(0);
    }

    public boolean isChecked() {
        return this.mCheckIcon.isChecked();
    }

    public void setAgreeText(SpannableStringBuilder agreeText) {
        if (!TextUtils.isEmpty(agreeText)) {
            this.mAgreeTextView.setText(agreeText, BufferType.SPANNABLE);
        }
    }
}
