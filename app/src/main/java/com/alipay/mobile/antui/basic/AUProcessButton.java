package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.lottie.AULottieLayout;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;
import com.alipay.mobile.antui.utils.AULottieFileUtils;

public class AUProcessButton extends AULinearLayout {
    public static final int MAIN_PROCESS_STYLE = 1;
    public static final int SUB_PROCESS_STYLE = 2;
    private AULottieLayout mButtonProgress;
    private AUTextView mButtonText;

    public AUProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public AUProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUProcessButton(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.au_process_button_view, this, true);
        this.mButtonText = (AUTextView) findViewById(R.id.button_text);
        this.mButtonProgress = (AULottieLayout) findViewById(R.id.button_process);
        setOrientation(0);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ButtonAttr);
            int style = array.getInt(2, 2);
            array.recycle();
            setProcessStyle(style);
            return;
        }
        setProcessStyle(2);
    }

    public void setText(CharSequence text) {
        this.mButtonText.setText(text);
    }

    public void setTextSize(float size) {
        this.mButtonText.setTextSize(size);
    }

    public void setTextSize(int unit, float size) {
        this.mButtonText.setTextSize(unit, size);
    }

    public void setTextColor(@ColorInt int color) {
        this.mButtonText.setTextColor(color);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.mButtonText.setTextColor(colorStateList);
    }

    public void setMinWidth(int minpixels) {
        setMinimumWidth(minpixels);
    }

    public AUTextView getButtonText() {
        return this.mButtonText;
    }

    public void setProcessStyle(int style) {
        switch (style) {
            case 1:
                applyStyleByTheme(AUThemeKey.MAIN_BTN_BACKGROUND, AUThemeKey.MAIN_BTN_TEXTSIZE, AUThemeKey.MAIN_BTN_TEXTCOLOR);
                setProcessColor(-1);
                return;
            case 2:
                applyStyleByTheme(AUThemeKey.SUB_BTN_BACKGROUND, AUThemeKey.SUB_BTN_TEXTSIZE, AUThemeKey.SUB_BTN_TEXTCOLOR);
                setProcessColor(-15692055);
                return;
            default:
                return;
        }
    }

    private void applyStyleByTheme(String backgroundKey, String textsizeKey, String textcolorKey) {
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        Integer backgroundResid = theme.getResId(backgroundKey);
        if (backgroundResid != null) {
            setBackgroundResource(backgroundResid.intValue());
        }
        Float textsize = theme.getDimension(getContext(), textsizeKey);
        if (textsize != null) {
            float textsizeFloat = textsize.floatValue();
            this.mButtonText.setTextSize(0, textsize.floatValue());
            LayoutParams layoutParams = this.mButtonProgress.getLayoutParams();
            layoutParams.height = (int) (((double) textsizeFloat) * 1.5d);
            layoutParams.width = (int) (((double) textsizeFloat) * 1.5d);
            this.mButtonProgress.setLayoutParams(layoutParams);
        }
        Integer textColor = theme.getResId(textcolorKey);
        if (textColor != null) {
            this.mButtonText.setTextColor(getResources().getColorStateList(textColor.intValue()));
        }
    }

    public void startProcess() {
        this.mButtonProgress.playAnimation();
    }

    public void startProcess(String pro_str) {
        this.mButtonText.setText(pro_str);
        this.mButtonProgress.playAnimation();
    }

    public void stopProcess() {
        this.mButtonProgress.cancelAnimation();
        this.mButtonProgress.setVisibility(8);
    }

    public void setProcessColor(@ColorInt int color) {
        this.mButtonProgress.setAnimationSource(AULottieFileUtils.getLoadingAnimation(getContext(), color));
        this.mButtonProgress.loop(true);
    }
}
