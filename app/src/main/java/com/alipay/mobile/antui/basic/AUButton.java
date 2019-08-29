package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.api.AntUIHelper;
import com.alipay.mobile.antui.api.VisibilityChangeObserver;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.antui.screenadpt.AUScreenUtils;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.uc.webview.export.extension.UCCore;

public class AUButton extends Button implements AntUI, AUViewInterface {
    public static final String BIN_TYPE_NO_RECT = "no_rect";
    public static final String BTN_TYPE_ASS_TRANS = "ass_trans";
    public static final String BTN_TYPE_MAIN = "main";
    public static final String BTN_TYPE_MAIN_GROUP = "mainGroup";
    public static final String BTN_TYPE_SUB = "sub";
    public static final String BTN_TYPE_SUB_GROUP = "subGroup";
    public static final String BTN_TYPE_WARNING = "warning";
    private AttributeSet attrs;
    private String btnType;
    private float defalutTextSize;
    private boolean dynamicTextSize;
    private boolean dynamicThemeDisable;
    private Boolean isAP;
    private Integer mThemeHeight;
    private VisibilityChangeObserver visibilityChangeObserver;

    public AUButton(Context context) {
        super(context);
        this.dynamicTextSize = false;
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    public AUButton(Context context, AttributeSet attrs2) {
        super(context, attrs2, 0);
        this.dynamicTextSize = false;
        init(getContext(), attrs2, null);
    }

    public AUButton(Context context, AttributeSet attrs2, int defStyle) {
        super(context, attrs2, defStyle);
        this.dynamicTextSize = false;
    }

    public void init(Context context, AttributeSet attrs2, TypedArray typedArray) {
        this.attrs = attrs2;
        TypedArray typedArray2 = context.obtainStyledAttributes(attrs2, R.styleable.ButtonAttr);
        initContent(getContext(), attrs2, typedArray2);
        initStyleByTheme(getContext());
        initAttrStyle(getContext(), attrs2, typedArray2);
        typedArray2.recycle();
    }

    public void initContent(Context context, AttributeSet attrs2, TypedArray typedArray) {
        if (typedArray != null) {
            this.dynamicTextSize = typedArray.getBoolean(0, false);
            this.dynamicThemeDisable = typedArray.getBoolean(4, false);
        }
        this.btnType = String.valueOf(getTag());
        this.defalutTextSize = getTextSize();
    }

    public void initStyleByTheme(Context context) {
        if ("main".equals(this.btnType)) {
            applyStyleByTheme(AUThemeKey.MAIN_BTN_BACKGROUND, AUThemeKey.MAIN_BTN_TEXTSIZE, AUThemeKey.MAIN_BTN_TEXTCOLOR, AUThemeKey.MAIN_BTN_HEIGHT);
        } else if (BTN_TYPE_SUB.equals(this.btnType)) {
            applyStyleByTheme(AUThemeKey.SUB_BTN_BACKGROUND, AUThemeKey.SUB_BTN_TEXTSIZE, AUThemeKey.SUB_BTN_TEXTCOLOR, AUThemeKey.SUB_BTN_HEIGHT);
        } else if (BTN_TYPE_MAIN_GROUP.equals(this.btnType)) {
            applyStyleByTheme(AUThemeKey.MAIN_GROUP_BTN_BACKGROUND, AUThemeKey.MAIN_GROUP_BTN_TEXTSIZE, AUThemeKey.MAIN_GROUP_BTN_TEXTCOLOR, AUThemeKey.MAIN_GROUP_BTN_HEIGHT);
        } else if (BTN_TYPE_SUB_GROUP.equals(this.btnType)) {
            applyStyleByTheme(AUThemeKey.SUB_GROUP_BTN_BACKGROUND, AUThemeKey.SUB_GROUP_BTN_TEXTSIZE, AUThemeKey.SUB_BTN_TEXTCOLOR, AUThemeKey.SUB_BTN_HEIGHT);
        } else if (BTN_TYPE_WARNING.equals(this.btnType)) {
            applyStyleByTheme(AUThemeKey.WARN_BTN_BACKGROUND, AUThemeKey.WARN_BTN_TEXTSIZE, AUThemeKey.WARN_BTN_TEXTCOLOR, AUThemeKey.WARN_BTN_HEIGHT);
        } else if (BTN_TYPE_ASS_TRANS.equals(this.btnType)) {
            applyStyleByTheme(AUThemeKey.ASS_TRANS_BTN_BACKGROUND, AUThemeKey.ASS_TRANS_BTN_TEXTSIZE, AUThemeKey.ASS_TRANS_BTN_TEXTCOLOR, AUThemeKey.ASS_TRANS_BTN_HEIGHT);
        } else if (BIN_TYPE_NO_RECT.equals(this.btnType)) {
            applyStyleByTheme(AUThemeKey.NO_RECT_BTN_BACKGROUND, AUThemeKey.NO_RECT_BTN_TEXTSIZE, AUThemeKey.NO_RECT_BTN_TEXTCOLOR, AUThemeKey.NO_RECT_BTN_HEIGHT);
        }
    }

    public void setBtnType(String type) {
        this.btnType = type;
        initStyleByTheme(getContext());
    }

    private void applyStyleByTheme(String backgroundKey, String textsizeKey, String textcolorKey, String heightKey) {
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        Integer backgroundResid = theme.getResId(backgroundKey);
        if (backgroundResid != null) {
            setBackgroundResource(backgroundResid.intValue());
        }
        Float textsize = theme.getDimension(getContext(), textsizeKey);
        if (textsize != null) {
            setTextSize(0, textsize.floatValue());
        }
        Integer textColor = theme.getResId(textcolorKey);
        if (textColor != null) {
            setTextColor(getResources().getColorStateList(textColor.intValue()));
        }
        this.mThemeHeight = theme.getDimensionPixelOffset(getContext(), heightKey);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mThemeHeight == null || this.dynamicThemeDisable) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(this.mThemeHeight.intValue(), UCCore.VERIFY_POLICY_QUICK));
        }
    }

    public void initAttrStyle(Context context, AttributeSet attrs2, TypedArray typedArray) {
        setScaleSize();
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setScaleSize();
    }

    public void setTextSize(int unit, float size) {
        if (AUScreenUtils.checkApFlag(getContext(), this.attrs, this) && unit == 1) {
            size = (float) AUScreenAdaptTool.getApFromDp(getContext(), size);
            unit = 0;
        }
        super.setTextSize(unit, size);
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(AUViewEventHelper.wrapClickListener(l));
    }

    private void setScaleSize() {
        if (this.dynamicTextSize && AntUIHelper.getTextSizeGearGetter() != null) {
            float scaleSize = DensityUtil.getTextSize(DensityUtil.px2sp(getContext(), this.defalutTextSize), AntUIHelper.getTextSizeGearGetter().getCurrentGear());
            if (!DensityUtil.isValueEqule(DensityUtil.px2sp(getContext(), getTextSize()), scaleSize)) {
                super.setTextSize(2, scaleSize);
            }
        }
    }

    public void setVisibilityChangeObserver(VisibilityChangeObserver observer) {
        this.visibilityChangeObserver = observer;
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (this.visibilityChangeObserver != null) {
            this.visibilityChangeObserver.visibilityChanged(visibility);
        }
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
