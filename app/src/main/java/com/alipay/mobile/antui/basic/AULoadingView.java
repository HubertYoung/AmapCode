package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.theme.AUAbsTheme;

public class AULoadingView extends AULinearLayout implements AntUI {
    private AUTextView mCurrentProgressTextView;
    private AUEmptyGoneTextView mLoadingText;

    public AULoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public AULoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AULoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context, null, null);
        if (attrs != null) {
            initContent(context, null, null);
            initStyleByTheme(context);
            initAttrStyle(context, null, null);
            return;
        }
        initStyleByTheme(context);
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
        LayoutInflater.from(context).inflate(R.layout.au_loading_view, this, true);
        this.mCurrentProgressTextView = (AUTextView) findViewById(R.id.progress_current_text);
        this.mLoadingText = (AUEmptyGoneTextView) findViewById(R.id.progress_loading_text);
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void initStyleByTheme(Context context) {
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    public void setCurrentProgress(int curentProgress) {
        SpannableString spannableString = new SpannableString(curentProgress + "%");
        spannableString.setSpan(new AbsoluteSizeSpan(6, true), spannableString.length() - 1, spannableString.length(), 33);
        this.mCurrentProgressTextView.setText(spannableString);
    }

    public void setLoadingText(String text) {
        this.mLoadingText.setText(text);
    }
}
