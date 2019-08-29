package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.api.ScrollTitleChangeListener;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.antui.screenadpt.AUScreenUtils;
import com.alipay.mobile.antui.segement.AUSegment;
import com.alipay.mobile.antui.segement.AUSegment.TabSwitchListener;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.antui.utils.StateListUtils;
import com.alipay.mobile.antui.utils.ToolUtils;

public class AUTitleBar extends AURelativeLayout implements AntUI, AUViewInterface {
    private static int[] OPACITY_ARRAY = {0, 0, 30, 107, 191, 255};
    private AttributeSet attrs;
    private boolean isTextLeft;
    private boolean isTextRight;
    private int mBackBtnColor;
    private AUIconView mBackButton;
    private Drawable mBackgroundDrawable;
    public int mDefaultScrollHeight = 0;
    private AUIconView mLeftButton;
    private int mLeftIconColor;
    private int mLeftIconSize;
    private int mLeftTextColor;
    private int mLeftTextSize;
    /* access modifiers changed from: private */
    public AUProgressBar mProgressBar;
    private AUIconView mRightButton;
    private AURelativeLayout mRightContainer_Left;
    private AURelativeLayout mRightContainer_Right;
    private int mRightIconColor;
    private int mRightIconSize;
    private int mRightTextColor;
    private int mRightTextSize;
    private AULinearLayout mTitleBarHorizon;
    private AURelativeLayout mTitleBarRelative;
    private AURelativeLayout mTitleContainer;
    private AUTextView mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private AULinearLayout mTitleViewContainer;
    private AUSearchView searchView;
    private AUSegment segment;

    public AUTitleBar(Context context) {
        super(context);
        init(context, null);
    }

    public AUTitleBar(Context context, AttributeSet attrs2) {
        super(context, attrs2);
        init(context, attrs2);
    }

    public AUTitleBar(Context context, AttributeSet attrs2, int defStyle) {
        super(context, attrs2, defStyle);
        init(context, attrs2);
    }

    private void init(Context context, AttributeSet attrs2) {
        init(context, null, null);
        if (attrs2 != null) {
            TypedArray array = context.obtainStyledAttributes(attrs2, R.styleable.titleBar);
            initContent(context, null, array);
            initStyleByTheme(context);
            initAttrStyle(context, null, array);
            array.recycle();
        } else {
            initStyleByTheme(context);
        }
        setFont();
    }

    public void init(Context context, AttributeSet attrs2, TypedArray typedArray) {
        this.attrs = attrs2;
        LayoutInflater.from(context).inflate(R.layout.au_title_bar, this, true);
        this.mTitleBarRelative = (AURelativeLayout) findViewById(R.id.title_bar_kernel);
        this.mTitleBarHorizon = (AULinearLayout) findViewById(R.id.title_bar_horizon);
        this.mBackButton = (AUIconView) findViewById(R.id.back_button);
        this.mTitleText = (AUTextView) findViewById(R.id.title_text);
        this.mLeftButton = (AUIconView) findViewById(R.id.left_text);
        this.mRightButton = (AUIconView) findViewById(R.id.right_text);
        this.mProgressBar = (AUProgressBar) findViewById(R.id.title_bar_progress);
        this.mTitleContainer = (AURelativeLayout) findViewById(R.id.title_container);
        this.mRightContainer_Left = (AURelativeLayout) findViewById(R.id.right_container_1);
        this.mRightContainer_Right = (AURelativeLayout) findViewById(R.id.right_container_2);
        initBackButton();
        this.mBackgroundDrawable = getResources().getDrawable(R.drawable.drawable_titlebar_bg);
        this.mTitleTextColor = getResources().getColor(R.color.AU_COLOR_TITLE);
        if (AUScreenUtils.checkApFlag(context, attrs2, this)) {
            this.mTitleTextSize = AUScreenAdaptTool.getApFromDimen(getContext(), R.dimen.title_text_size);
            this.mLeftIconSize = AUScreenAdaptTool.getApFromDimen(getContext(), R.dimen.button_icon_size);
            this.mLeftTextSize = AUScreenAdaptTool.getApFromDimen(getContext(), R.dimen.button_text_size);
            this.mRightIconSize = AUScreenAdaptTool.getApFromDimen(getContext(), R.dimen.button_icon_size);
            this.mRightTextSize = AUScreenAdaptTool.getApFromDimen(getContext(), R.dimen.button_text_size);
            this.mDefaultScrollHeight = (int) (AUScreenAdaptTool.getAPDensity(context) * 148.0f);
        } else {
            this.mTitleTextSize = getResources().getDimensionPixelSize(R.dimen.title_text_size);
            this.mLeftIconSize = getResources().getDimensionPixelOffset(R.dimen.button_icon_size);
            this.mLeftTextSize = getResources().getDimensionPixelOffset(R.dimen.button_text_size);
            this.mRightIconSize = getResources().getDimensionPixelOffset(R.dimen.button_icon_size);
            this.mRightTextSize = getResources().getDimensionPixelOffset(R.dimen.button_text_size);
            this.mDefaultScrollHeight = (int) (getResources().getDisplayMetrics().density * 148.0f);
        }
        this.mBackBtnColor = getResources().getColor(R.color.AU_COLOR_LINK);
        this.mLeftIconColor = getResources().getColor(R.color.AU_COLOR_LINK);
        this.mLeftTextColor = getResources().getColor(R.color.AU_COLOR_LINK);
        this.mRightIconColor = getResources().getColor(R.color.AU_COLOR_LINK);
        this.mRightTextColor = getResources().getColor(R.color.AU_COLOR_LINK);
        this.isTextLeft = false;
        this.isTextRight = false;
    }

    public void initContent(Context context, AttributeSet attrs2, TypedArray typedArray) {
        if (typedArray.hasValue(1)) {
            this.mTitleText.setText(typedArray.getString(1));
        }
        if (typedArray.hasValue(5)) {
            setLeftButtonUnicode(typedArray.getString(5));
        } else if (typedArray.hasValue(4)) {
            setBtnImage(this.mLeftButton, typedArray.getResourceId(4, 0));
        } else if (typedArray.hasValue(8)) {
            setLeftButtonUnicode(typedArray.getString(8));
            this.isTextLeft = true;
        } else {
            this.mRightContainer_Left.setVisibility(8);
        }
        if (typedArray.hasValue(12)) {
            setRightButtonUnicode(typedArray.getString(12));
        } else if (typedArray.hasValue(11)) {
            setBtnImage(this.mRightButton, typedArray.getResourceId(11, 0));
        } else if (typedArray.hasValue(15)) {
            setRightButtonUnicode(typedArray.getString(15));
            this.isTextRight = true;
        } else {
            this.mRightContainer_Right.setVisibility(8);
        }
    }

    public void initStyleByTheme(Context context) {
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        this.mBackgroundDrawable = theme.getDrawable(context, AUThemeKey.TITLEBAR_BACKGROUND_COLOR, this.mBackgroundDrawable);
        this.mBackBtnColor = theme.getColor(context, AUThemeKey.TITLEBAR_ICON_COLOR, Integer.valueOf(this.mBackBtnColor)).intValue();
        this.mTitleTextSize = theme.getColor(context, AUThemeKey.TITLEBAR_TITLE_TEXTSIZE, Integer.valueOf(this.mTitleTextSize)).intValue();
        this.mTitleTextColor = theme.getColor(context, AUThemeKey.TITLEBAR_TITLE_TEXTCOLOR, Integer.valueOf(this.mTitleTextColor)).intValue();
        this.mLeftIconColor = theme.getColor(context, AUThemeKey.TITLEBAR_ICON_COLOR, Integer.valueOf(this.mLeftIconColor)).intValue();
        this.mLeftIconSize = theme.getDimensionPixelOffset(context, AUThemeKey.TITLEBAR_ICON_SIZE, Integer.valueOf(this.mLeftIconSize)).intValue();
        this.mLeftTextColor = theme.getColor(context, AUThemeKey.TITLEBAR_TEXTCOLOR, Integer.valueOf(this.mLeftTextColor)).intValue();
        this.mLeftTextSize = theme.getDimensionPixelOffset(context, AUThemeKey.TITLEBAR_TEXTSIZE, Integer.valueOf(this.mLeftTextSize)).intValue();
        this.mRightIconColor = theme.getColor(context, AUThemeKey.TITLEBAR_ICON_COLOR, Integer.valueOf(this.mRightIconColor)).intValue();
        this.mRightIconSize = theme.getDimensionPixelOffset(context, AUThemeKey.TITLEBAR_ICON_SIZE, Integer.valueOf(this.mRightIconSize)).intValue();
        this.mRightTextColor = theme.getColor(context, AUThemeKey.TITLEBAR_TEXTCOLOR, Integer.valueOf(this.mRightTextColor)).intValue();
        this.mRightTextSize = theme.getDimensionPixelOffset(context, AUThemeKey.TITLEBAR_TEXTSIZE, Integer.valueOf(this.mRightTextSize)).intValue();
    }

    public void initAttrStyle(Context context, AttributeSet attrs2, TypedArray typedArray) {
        if (typedArray.hasValue(0)) {
            this.mBackgroundDrawable = typedArray.getDrawable(0);
        }
        if (typedArray.hasValue(18)) {
            this.mBackBtnColor = typedArray.getColor(18, this.mBackBtnColor);
        }
        this.mTitleTextSize = typedArray.getDimensionPixelOffset(2, this.mTitleTextSize);
        this.mTitleTextColor = typedArray.getColor(3, this.mTitleTextColor);
        if (typedArray.hasValue(6)) {
            this.mLeftIconColor = typedArray.getColor(6, this.mLeftIconColor);
        }
        this.mLeftIconSize = typedArray.getDimensionPixelOffset(7, this.mLeftIconSize);
        if (typedArray.hasValue(9)) {
            this.mLeftTextColor = typedArray.getColor(9, this.mLeftTextColor);
        }
        this.mLeftTextSize = typedArray.getDimensionPixelOffset(10, this.mLeftTextSize);
        if (typedArray.hasValue(13)) {
            this.mRightIconColor = typedArray.getColor(13, this.mRightIconColor);
        }
        this.mRightIconSize = typedArray.getDimensionPixelOffset(14, this.mRightIconSize);
        if (typedArray.hasValue(16)) {
            this.mRightTextColor = typedArray.getColor(16, this.mRightTextColor);
        }
        this.mRightTextSize = typedArray.getDimensionPixelOffset(17, this.mRightTextSize);
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
        if (theme != null) {
            this.mBackgroundDrawable = theme.getDrawable(context, AUThemeKey.TITLEBAR_BACKGROUND_COLOR, this.mBackgroundDrawable);
            this.mBackBtnColor = theme.getColor(context, AUThemeKey.TITLEBAR_ICON_COLOR, Integer.valueOf(this.mBackBtnColor)).intValue();
            this.mTitleTextSize = theme.getColor(context, AUThemeKey.TITLEBAR_TITLE_TEXTSIZE, Integer.valueOf(this.mTitleTextSize)).intValue();
            this.mTitleTextColor = theme.getColor(context, AUThemeKey.TITLEBAR_TITLE_TEXTCOLOR, Integer.valueOf(this.mTitleTextColor)).intValue();
            this.mLeftIconColor = theme.getColor(context, AUThemeKey.TITLEBAR_ICON_COLOR, Integer.valueOf(this.mLeftIconColor)).intValue();
            this.mLeftIconSize = theme.getDimensionPixelOffset(context, AUThemeKey.TITLEBAR_ICON_SIZE, Integer.valueOf(this.mLeftIconSize)).intValue();
            this.mLeftTextColor = theme.getColor(context, AUThemeKey.TITLEBAR_TEXTCOLOR, Integer.valueOf(this.mLeftTextColor)).intValue();
            this.mLeftTextSize = theme.getDimensionPixelOffset(context, AUThemeKey.TITLEBAR_TEXTSIZE, Integer.valueOf(this.mLeftTextSize)).intValue();
            this.mRightIconColor = theme.getColor(context, AUThemeKey.TITLEBAR_ICON_COLOR, Integer.valueOf(this.mRightIconColor)).intValue();
            this.mRightIconSize = theme.getDimensionPixelOffset(context, AUThemeKey.TITLEBAR_ICON_SIZE, Integer.valueOf(this.mRightIconSize)).intValue();
            this.mRightTextColor = theme.getColor(context, AUThemeKey.TITLEBAR_TEXTCOLOR, Integer.valueOf(this.mRightTextColor)).intValue();
            this.mRightTextSize = theme.getDimensionPixelOffset(context, AUThemeKey.TITLEBAR_TEXTSIZE, Integer.valueOf(this.mRightTextSize)).intValue();
            setFont();
        }
    }

    private void initBackButton() {
        this.mBackButton.setOnClickListener(new ap(this));
    }

    private void setFont() {
        setBackgroundDrawable(this.mBackgroundDrawable);
        this.mTitleText.setTextColor(this.mTitleTextColor);
        this.mTitleText.setTextSize(0, (float) this.mTitleTextSize);
        this.mBackButton.setIconfontColorStates(getStateColor(this.mBackBtnColor));
        setLeftBtnFont();
        setRightBtnFont();
    }

    private boolean checkSpecialUnicode(String unicode) {
        return TextUtils.equals(unicode, getResources().getString(R.string.iconfont_user_setting)) || TextUtils.equals(unicode, getResources().getString(R.string.iconfont_add_user));
    }

    private void setLeftButtonUnicode(String unicode) {
        if (checkSpecialUnicode(unicode)) {
            this.mLeftIconSize = getResources().getDimensionPixelOffset(R.dimen.titlebar_icon_special_size);
            this.mLeftButton.setIconfontSize((float) this.mLeftIconSize);
        }
        this.mLeftButton.setIconfontUnicode(unicode);
    }

    private void setRightButtonUnicode(String unicode) {
        if (checkSpecialUnicode(unicode)) {
            if (AUScreenUtils.checkApFlag(getContext(), this.attrs, this)) {
                this.mRightIconSize = AUScreenAdaptTool.getApFromDimen(getContext(), R.dimen.titlebar_icon_special_size);
            } else {
                this.mRightIconSize = getResources().getDimensionPixelSize(R.dimen.titlebar_icon_special_size);
            }
            this.mRightButton.setIconfontSize((float) this.mRightIconSize);
        }
        this.mRightButton.setIconfontUnicode(unicode);
    }

    private void setLeftButtonStyle(boolean isText) {
        this.isTextLeft = isText;
        setLeftBtnFont();
    }

    private void setRightButtonStyle(boolean isText) {
        this.isTextRight = isText;
        setRightBtnFont();
    }

    public void setBtnImage(AUIconView iconView, int resId) {
        iconView.setImageResource(resId);
    }

    public void setIconFont(AUIconView iconView, int size, int color) {
        if (size != 0) {
            iconView.setIconfontSize((float) size);
            iconView.setIconTextMinHeight((int) (((double) size) * 1.4d));
        }
        if (color != 0) {
            iconView.setIconfontColorStates(getStateColor(color));
        }
    }

    private void setLeftBtnFont() {
        if (this.isTextLeft) {
            setIconFont(this.mLeftButton, this.mLeftTextSize, this.mLeftTextColor);
        } else {
            setIconFont(this.mLeftButton, this.mLeftIconSize, this.mLeftIconColor);
        }
    }

    private void setRightBtnFont() {
        if (this.isTextRight) {
            setIconFont(this.mRightButton, this.mRightTextSize, this.mRightTextColor);
        } else {
            setIconFont(this.mRightButton, this.mRightIconSize, this.mRightIconColor);
        }
    }

    public AUIconView getBackButton() {
        return this.mBackButton;
    }

    public AURelativeLayout getLeftButton() {
        return this.mRightContainer_Left;
    }

    public AUIconView getLeftButtonIconView() {
        return this.mLeftButton;
    }

    public AURelativeLayout getRightButton() {
        return this.mRightContainer_Right;
    }

    public AUIconView getRightButtonIconView() {
        return this.mRightButton;
    }

    public AUTextView getTitleText() {
        return this.mTitleText;
    }

    public AURelativeLayout getTitleContainer() {
        return this.mTitleContainer;
    }

    public AURelativeLayout getTitleBarRelative() {
        return this.mTitleBarRelative;
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        if (VERSION.SDK_INT >= 16) {
            this.mTitleBarRelative.setBackground(backgroundDrawable);
        } else {
            this.mTitleBarRelative.setBackgroundDrawable(backgroundDrawable);
        }
    }

    public void setProgressBarDrawable(Drawable progressDrawable) {
        this.mProgressBar.setIndeterminateDrawable(progressDrawable);
    }

    public void setTitleText(String text, int textSize, int textColor) {
        if (textSize != 0) {
            this.mTitleText.setTextSize(0, (float) textSize);
        }
        if (textColor != 0) {
            this.mTitleText.setTextColor(textColor);
        }
        setTitleText(text);
    }

    public void setTitleText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mTitleText.setText(text);
        }
    }

    public void addSubTitleView(View view) {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(3, R.id.title_text);
        layoutParams.addRule(5, R.id.title_text);
        this.mTitleContainer.addView(view, (ViewGroup.LayoutParams) layoutParams);
    }

    public void setBackBtnInfo(Object drawable, int size, int color) {
        setBackBtnInfo(drawable);
        if (color != 0) {
            this.mBackBtnColor = color;
        }
        setIconFont(this.mBackButton, size, color);
    }

    public void setBackBtnInfo(Object drawable) {
        if (drawable instanceof String) {
            this.mBackButton.setIconfontUnicode((String) drawable);
        } else if (drawable instanceof Drawable) {
            this.mBackButton.setImageDrawable((Drawable) drawable);
        }
    }

    public void setLeftBtnInfo(Object drawable, int size, int color, boolean isText) {
        if (drawable instanceof Drawable) {
            setLeftButtonIcon((Drawable) drawable);
        } else if (drawable instanceof String) {
            if (isText) {
                setLeftButtonText((String) drawable);
            } else {
                setLeftButtonIcon((String) drawable);
            }
        }
        setLeftButtonFont(size, color, isText);
    }

    public void setLeftButtonIcon(Drawable drawable) {
        if (drawable == null) {
            this.mRightContainer_Left.setVisibility(8);
            return;
        }
        this.mLeftButton.setImageDrawable(drawable);
        setLeftButtonStyle(false);
        this.mRightContainer_Left.setVisibility(0);
    }

    public void setLeftButtonIcon(String unicode) {
        if (TextUtils.isEmpty(unicode)) {
            this.mRightContainer_Left.setVisibility(8);
            return;
        }
        setLeftButtonUnicode(unicode);
        setLeftButtonStyle(false);
        this.mRightContainer_Left.setVisibility(0);
    }

    public void setLeftButtonText(String text) {
        if (TextUtils.isEmpty(text)) {
            this.mRightContainer_Left.setVisibility(8);
            return;
        }
        this.mLeftButton.setIconfontUnicode(text);
        setLeftButtonStyle(true);
        this.mRightContainer_Left.setVisibility(0);
    }

    public void setLeftButtonEnabled(boolean enabled) {
        this.mLeftButton.setEnabled(enabled);
        this.mRightContainer_Left.setEnabled(enabled);
    }

    public void setLeftButtonFont(int size, int color, boolean isText) {
        if (isText) {
            if (color == 0) {
                color = this.mLeftTextColor;
            }
            this.mLeftTextColor = color;
            if (size == 0) {
                size = this.mLeftTextSize;
            }
            this.mLeftTextSize = size;
        } else {
            if (color == 0) {
                color = this.mLeftIconColor;
            }
            this.mLeftIconColor = color;
            if (size == 0) {
                size = this.mLeftIconSize;
            }
            this.mLeftIconSize = size;
        }
        setLeftButtonStyle(isText);
    }

    public void setRightBtnInfo(Object drawable, int size, int color, boolean isText) {
        if (drawable instanceof Drawable) {
            setRightButtonIcon((Drawable) drawable);
        } else if (drawable instanceof String) {
            if (isText) {
                setRightButtonText((String) drawable);
            } else {
                setRightButtonIcon((String) drawable);
            }
        }
        setRightButtonFont(size, color, isText);
    }

    public void setRightButtonIcon(Drawable drawable) {
        if (drawable == null) {
            this.mRightContainer_Right.setVisibility(8);
            return;
        }
        this.mRightButton.setImageDrawable(drawable);
        setRightButtonStyle(false);
        this.mRightContainer_Right.setVisibility(0);
    }

    public void setRightButtonIcon(String unicode) {
        if (TextUtils.isEmpty(unicode)) {
            this.mRightContainer_Right.setVisibility(8);
            return;
        }
        setRightButtonUnicode(unicode);
        setRightButtonStyle(false);
        this.mRightContainer_Right.setVisibility(0);
    }

    public void setRightButtonText(String text) {
        if (TextUtils.isEmpty(text)) {
            this.mRightContainer_Right.setVisibility(8);
            return;
        }
        this.mRightButton.setIconfontUnicode(text);
        setRightButtonStyle(true);
        this.mRightContainer_Right.setVisibility(0);
    }

    public void setRightButtonEnabled(boolean enabled) {
        this.mRightButton.setEnabled(enabled);
        this.mRightContainer_Right.setEnabled(enabled);
    }

    public void setRightButtonFont(int size, int color, boolean isText) {
        if (isText) {
            if (color == 0) {
                color = this.mRightTextColor;
            }
            this.mRightTextColor = color;
            if (size == 0) {
                size = this.mRightTextSize;
            }
            this.mRightTextSize = size;
        } else {
            if (color == 0) {
                color = this.mRightIconColor;
            }
            this.mRightIconColor = color;
            if (size == 0) {
                size = this.mRightIconSize;
            }
            this.mRightIconSize = size;
        }
        setRightButtonStyle(isText);
    }

    public void startProgressBar() {
        this.mProgressBar.post(new aq(this));
    }

    public void stopProgressBar() {
        this.mProgressBar.post(new ar(this));
    }

    public void handleScrollChange(int currentHeight) {
        handleScrollChange(this.mDefaultScrollHeight, currentHeight);
    }

    public void handleScrollChange(int totalHeight, int currentHeight) {
        handleScrollChange(totalHeight, currentHeight, null);
    }

    public void handleScrollChange(int totalHeight, int currentHeight, ScrollTitleChangeListener listener) {
        if (totalHeight <= 0) {
            totalHeight = this.mDefaultScrollHeight;
        }
        if (currentHeight >= 0) {
            boolean isChange = false;
            int percent = (currentHeight * 100) / totalHeight;
            if (percent < 80) {
                setColorWhiteStyle();
            } else {
                isChange = true;
                setColorOriginalStyle();
            }
            int index = percent / 20 >= 5 ? 5 : percent / 20;
            int unit = percent % 20;
            if (index > 0 && index < 5) {
                this.mTitleBarRelative.getBackground().setAlpha(OPACITY_ARRAY[index] + (((OPACITY_ARRAY[index + 1] - OPACITY_ARRAY[index]) * unit) / 20));
            } else if (index == 0 || index == 5) {
                this.mTitleBarRelative.getBackground().setAlpha(OPACITY_ARRAY[index]);
            }
            if (listener != null) {
                listener.onChange(isChange);
            }
        }
    }

    public void setColorWhiteStyle() {
        int whiteColor = getContext().getResources().getColor(R.color.AU_COLOR_UNIVERSAL_BG);
        this.mTitleText.setTextColor(whiteColor);
        ColorStateList whiteSelector = getStateColor(whiteColor);
        this.mBackButton.setIconfontColorStates(whiteSelector);
        this.mLeftButton.setIconfontColorStates(whiteSelector);
        this.mRightButton.setIconfontColorStates(whiteSelector);
    }

    public void setColorOriginalStyle() {
        this.mTitleText.setTextColor(this.mTitleTextColor);
        this.mBackButton.setIconfontColorStates(getStateColor(this.mBackBtnColor));
        setLeftBtnFont();
        setRightBtnFont();
    }

    public void setBackButtonGone() {
        this.mBackButton.setVisibility(8);
    }

    public void setTitle2Search(String search2) {
        this.searchView = new AUSearchView(getContext());
        this.searchView.getSearchEditView().setText(search2);
        this.searchView.getSearchEditView().setFocusable(false);
        this.searchView.getClearButton().setVisibility(8);
        this.mTitleText.setVisibility(8);
        this.mTitleContainer.addView(this.searchView);
    }

    public void setSearch2Title() {
        if (this.searchView != null) {
            this.searchView.setVisibility(8);
        }
        this.mTitleText.setVisibility(0);
    }

    public void setSearchColorOriginalStyle() {
        if (this.searchView != null) {
            this.searchView.setColorOriginalStyle();
        }
    }

    public void setSearchColorTransStyle() {
        if (this.searchView != null) {
            this.searchView.setTransparentStyle();
        }
    }

    public void attachFlagToLeftBtn(View flagView) {
        attachFlagView(this.mRightContainer_Left, this.mLeftButton, flagView);
    }

    public void attachFlagToRightBtn(View flagView) {
        attachFlagView(this.mRightContainer_Right, this.mRightButton, flagView);
    }

    public void attachFlagView(AURelativeLayout container, View targetView, View flagView) {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(1, targetView.getId());
        layoutParams.addRule(6, targetView.getId());
        flagView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.flag_top_margin) - (flagView.getMeasuredHeight() / 2);
        layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.flag_left_margin);
        flagView.setLayoutParams(layoutParams);
        container.addView(flagView);
    }

    private ColorStateList getStateColor(int normalColor) {
        int pressed = normalColor & 1728053247;
        return StateListUtils.getColorStateList(normalColor, pressed, pressed);
    }

    public void setSegment(String[] titles, TabSwitchListener tabSwitchListener) {
        this.mTitleContainer.removeAllViews();
        this.segment = new AUSegment(getContext());
        this.segment.resetTabView(titles);
        this.segment.setTabSwitchListener(tabSwitchListener);
        LayoutParams params = new LayoutParams(getResources().getDimensionPixelSize(R.dimen.titlebar_segment_width), getResources().getDimensionPixelSize(R.dimen.AU_SPACE12));
        params.addRule(14);
        params.addRule(3, R.id.title_bar_status_bar);
        this.mTitleBarRelative.addView((View) this.segment, (ViewGroup.LayoutParams) params);
    }

    public void toIOSStyle(String title) {
        if (!ToolUtils.isAlipayApp(getContext())) {
            this.mBackButton.setIconfontSize((float) this.mLeftTextSize);
            this.mBackButton.setIconfontUnicode(getResources().getString(R.string.iconfont_back) + getResources().getString(R.string.back));
            this.mTitleContainer.removeAllViews();
            LayoutParams layoutParams = new LayoutParams(-2, getResources().getDimensionPixelSize(R.dimen.AU_SPACE12));
            layoutParams.addRule(14);
            layoutParams.addRule(3, R.id.title_bar_status_bar);
            AUTextView centerTitle = new AUTextView(getContext());
            centerTitle.setGravity(17);
            centerTitle.setTextSize(0, (float) this.mTitleTextSize);
            centerTitle.setTextColor(this.mTitleTextColor);
            centerTitle.setText(title);
            this.mTitleBarRelative.addView((View) centerTitle, (ViewGroup.LayoutParams) layoutParams);
        }
    }

    public AUSegment getSegment() {
        return this.segment;
    }

    @Deprecated
    public void addButtonView(View view) {
        addButtonView(view, -1, new ViewGroup.LayoutParams(-2, -1));
    }

    public void addButtonViewToRight(View view) {
        addButtonView(view, this.mTitleBarHorizon.indexOfChild(this.mRightContainer_Right) + 1, new ViewGroup.LayoutParams(-2, -1));
    }

    public void addButtonViewToLeft(View view) {
        addButtonView(view, this.mTitleBarHorizon.indexOfChild(this.mRightContainer_Left), new ViewGroup.LayoutParams(-2, -1));
    }

    public void addButtonView(View view, int index, ViewGroup.LayoutParams layoutParams) {
        this.mTitleBarHorizon.addView(view, index, layoutParams);
    }

    public void setTitleView(View view) {
        if (view != null) {
            if (this.mTitleViewContainer == null) {
                this.mTitleViewContainer = new AULinearLayout(getContext());
                int index = this.mTitleBarHorizon.indexOfChild(this.mTitleContainer);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, getResources().getDimensionPixelOffset(R.dimen.AU_SPACE12));
                layoutParams.weight = 1.0f;
                layoutParams.rightMargin = DensityUtil.dip2px(getContext(), 13.0f);
                layoutParams.gravity = 19;
                this.mTitleViewContainer.setGravity(19);
                this.mTitleBarHorizon.addView((View) this.mTitleViewContainer, index, (ViewGroup.LayoutParams) layoutParams);
            } else {
                this.mTitleViewContainer.removeAllViews();
            }
            ViewGroup.LayoutParams viewParams = view.getLayoutParams();
            if (viewParams == null) {
                viewParams = new LinearLayout.LayoutParams(-2, -2);
            }
            this.mTitleViewContainer.addView(view, viewParams);
            this.mTitleContainer.setVisibility(8);
        } else if (this.mTitleViewContainer != null) {
            this.mTitleBarHorizon.removeView(this.mTitleViewContainer);
            this.mTitleContainer.setVisibility(0);
            this.mTitleViewContainer.removeAllViews();
            this.mTitleViewContainer = null;
        }
    }

    public View getTitleView_SetBefore() {
        if (this.mTitleViewContainer == null || this.mTitleViewContainer.getChildCount() <= 0) {
            return null;
        }
        return this.mTitleViewContainer.getChildAt(0);
    }

    public Boolean isAP() {
        return Boolean.FALSE;
    }

    public void setAP(Boolean isAP) {
        AuiLogger.info("AUTitleBar", "setAP:" + isAP);
    }
}
