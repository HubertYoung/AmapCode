package com.alipay.mobile.antui.status;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.theme.AUAbsTheme;

public class AUResultView extends AUFrameLayout implements AntUI {
    private View buttonLayout;
    private AUEmptyGoneTextView mMainButton;
    private TextView mMainTitleText;
    private AUEmptyGoneTextView mSubButton;
    private TextView mSubTitleText;
    private TextView mThirdTitleText;

    public AUResultView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AUResultView);
        init(context, attrs, ta);
        initContent(context, attrs, ta);
        initStyleByTheme(context);
        initAttrStyle(context, attrs, ta);
        ta.recycle();
    }

    public AUResultView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUResultView(Context context) {
        this(context, null, 0);
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
        LayoutInflater.from(context).inflate(R.layout.au_result_view, this, true);
        this.mMainTitleText = (TextView) findViewById(R.id.main_title_text);
        this.mSubTitleText = (TextView) findViewById(R.id.sub_title_text);
        this.mThirdTitleText = (TextView) findViewById(R.id.third_title_text);
        this.buttonLayout = findViewById(R.id.result_button_layout);
        this.mMainButton = (AUEmptyGoneTextView) findViewById(R.id.result_main_button);
        this.mSubButton = (AUEmptyGoneTextView) findViewById(R.id.result_sub_button);
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void initStyleByTheme(Context context) {
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
        Drawable iconDrawable = typedArray.getDrawable(0);
        if (iconDrawable != null) {
            this.mMainTitleText.setCompoundDrawablesWithIntrinsicBounds(null, iconDrawable, null, null);
        }
        String value = typedArray.getString(1);
        if (!TextUtils.isEmpty(value)) {
            setMainTitleText(value);
        }
        String value2 = typedArray.getString(2);
        if (!TextUtils.isEmpty(value2)) {
            setSubTitleText(value2);
        }
        String value3 = typedArray.getString(3);
        if (!TextUtils.isEmpty(value3)) {
            setThirdTitleText(value3);
        }
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    public void setIcon(@DrawableRes int iconRes) {
        this.mMainTitleText.setCompoundDrawablesWithIntrinsicBounds(0, iconRes, 0, 0);
    }

    public void setIcon(Drawable drawable) {
        this.mMainTitleText.setCompoundDrawables(null, drawable, null, null);
    }

    public void setMainTitleText(CharSequence text) {
        this.mMainTitleText.setText(text);
        this.mMainTitleText.setVisibility(0);
    }

    public void setSubTitleText(CharSequence text) {
        this.mSubTitleText.setText(text);
        this.mSubTitleText.setVisibility(0);
    }

    public void setThirdTitleText(CharSequence text) {
        setThirdTitleText(text, false);
    }

    public void setThirdTitleText(CharSequence text, boolean strikeThrough) {
        this.mThirdTitleText.setText(text);
        this.mThirdTitleText.setVisibility(0);
        if (strikeThrough) {
            this.mThirdTitleText.setPaintFlags(this.mThirdTitleText.getPaintFlags() | 16);
        }
    }

    public TextView getMainTitleText() {
        return this.mMainTitleText;
    }

    public TextView getSubTitleText() {
        return this.mSubTitleText;
    }

    public TextView getThirdTitleText() {
        return this.mThirdTitleText;
    }

    public void setButtonLayoutVisibility(int visibility) {
        this.buttonLayout.setVisibility(visibility);
    }

    public void setButtonText(CharSequence main, CharSequence sub) {
        if (!TextUtils.isEmpty(main) || !TextUtils.isEmpty(sub)) {
            if (this.buttonLayout.getVisibility() != 0) {
                this.buttonLayout.setVisibility(0);
            }
            this.mMainButton.setText(main);
            this.mSubButton.setText(sub);
            return;
        }
        this.buttonLayout.setVisibility(8);
    }
}
