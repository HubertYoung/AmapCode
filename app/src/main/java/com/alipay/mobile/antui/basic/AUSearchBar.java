package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;
import com.alipay.mobile.antui.utils.StateListUtils;

public class AUSearchBar extends AURelativeLayout implements AntUI {
    private al editChangedWatcher;
    private boolean isShowSearchButton = false;
    private boolean isShowVoiceSearch = false;
    private AUIconView mBackButton;
    private Drawable mBackgroundDrawable;
    private AUIconView mClearButton;
    private int mEditBoxHeight;
    private int mEditBoxWidth;
    private int mEditHintColor;
    private int mEditIconColor;
    private int mEditTextColor;
    private boolean mHasInput = false;
    private AUIconView mHintIconView;
    private int mInputMaxLength = -1;
    private View mSearchBackgroundView;
    private AURelativeLayout mSearchBarLayout;
    private AUIconView mSearchConfirmButton;
    /* access modifiers changed from: private */
    public AUEditText mSearchEditView;
    private AURelativeLayout mSearchRelativeLayout;
    private AURelativeLayout mTitleBarRelative;
    private AUIconView mVoiceButton;

    public AUSearchBar(Context context) {
        super(context);
        init(context, null);
    }

    public AUSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUSearchBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context, null, null);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchBar);
            initContent(context, null, array);
            initStyleByTheme(context);
            initAttrStyle(context, null, array);
            array.recycle();
        } else {
            initStyleByTheme(context);
        }
        initBtnStatus();
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
        LayoutInflater.from(context).inflate(R.layout.au_search_bar, this, true);
        this.mTitleBarRelative = (AURelativeLayout) findViewById(R.id.title_bar_kernel);
        this.mSearchBarLayout = (AURelativeLayout) findViewById(R.id.search_bar_layout);
        this.mBackButton = (AUIconView) findViewById(R.id.search_back_button);
        this.mSearchEditView = (AUEditText) findViewById(R.id.search_input_box);
        this.editChangedWatcher = new al(this, 0);
        this.mSearchEditView.addTextChangedListener(this.editChangedWatcher);
        this.mClearButton = (AUIconView) findViewById(R.id.search_clear_btn);
        this.mVoiceButton = (AUIconView) findViewById(R.id.search_voice_btn);
        this.mHintIconView = (AUIconView) findViewById(R.id.search_icon);
        this.mHintIconView.setContentDescription(getResources().getString(R.string.f13search));
        this.mHintIconView.setImportantForAccessibility(4);
        this.mSearchConfirmButton = (AUIconView) findViewById(R.id.search_confirm);
        this.mSearchConfirmButton.setEnabled(false);
        this.mSearchRelativeLayout = (AURelativeLayout) findViewById(R.id.search_layout);
        this.mSearchBackgroundView = findViewById(R.id.search_bg);
        initBackButton();
        setClearBtnClick();
        this.mBackgroundDrawable = getResources().getDrawable(R.drawable.drawable_titlebar_bg);
        this.mEditHintColor = getResources().getColor(R.color.AU_COLOR_SEARCH_INPUT_HINT);
        this.mEditIconColor = getResources().getColor(R.color.AU_COLOR_SEARCH_INPUT_ICON);
        this.mEditTextColor = getResources().getColor(R.color.AU_COLOR_MAIN_CONTENT);
        int color = getResources().getColor(R.color.AU_COLOR_LINK);
        setBackButtonColor(color);
        setSearchButtonColor(color);
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
        if (typedArray.hasValue(2)) {
            this.mSearchEditView.setText(typedArray.getString(2));
        }
        if (typedArray.hasValue(3)) {
            this.mSearchEditView.setHint(typedArray.getString(3));
        }
        if (typedArray.hasValue(4)) {
            this.mSearchConfirmButton.setIconfontUnicode(typedArray.getString(4));
        }
        if (typedArray.hasValue(6)) {
            this.mHintIconView.setIconfontUnicode(typedArray.getString(6));
        }
        if (typedArray.hasValue(7)) {
            this.mHintIconView.setImageDrawable(typedArray.getDrawable(7));
        }
        if (typedArray.hasValue(8)) {
            this.mBackButton.setIconfontUnicode(typedArray.getString(8));
        }
        if (typedArray.hasValue(9)) {
            this.mBackButton.setImageDrawable(typedArray.getDrawable(9));
        }
    }

    public void initStyleByTheme(Context context) {
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        this.mBackgroundDrawable = theme.getDrawable(context, AUThemeKey.SERACHBAR_INPUT_BACKGROUND_COLOR, this.mBackgroundDrawable);
        LayoutParams lp = this.mSearchRelativeLayout.getLayoutParams();
        this.mEditBoxHeight = theme.getDimensionPixelOffset(context, AUThemeKey.SERACHBAR_INPUT_HEIGHT, Integer.valueOf(lp.height)).intValue();
        this.mEditBoxWidth = theme.getDimensionPixelOffset(context, AUThemeKey.SERACHBAR_INPUT_WIDTH, Integer.valueOf(lp.width)).intValue();
        lp.height = this.mEditBoxHeight;
        lp.width = this.mEditBoxWidth;
        this.mSearchRelativeLayout.setLayoutParams(lp);
        this.mEditHintColor = theme.getColor(context, AUThemeKey.SERACHBAR_HINT_COLOR, Integer.valueOf(this.mEditHintColor)).intValue();
        this.mEditTextColor = theme.getColor(context, AUThemeKey.SERACHBAR_INPUT_TEXTCOLOR, Integer.valueOf(this.mEditTextColor)).intValue();
        this.mEditIconColor = theme.getColor(context, AUThemeKey.SERACHBAR_ICON_COLOR, Integer.valueOf(this.mEditIconColor)).intValue();
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
        this.isShowSearchButton = typedArray.getBoolean(0, false);
        this.isShowVoiceSearch = typedArray.getBoolean(1, false);
        this.mInputMaxLength = typedArray.getInt(5, -1);
        this.mEditHintColor = typedArray.getColor(10, this.mEditHintColor);
        this.mEditTextColor = typedArray.getColor(11, this.mEditTextColor);
        this.mEditIconColor = typedArray.getColor(12, this.mEditIconColor);
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    private void initBtnStatus() {
        setBackgroundDrawable(this.mBackgroundDrawable);
        if (this.isShowSearchButton) {
            setSearchConfirmButtonVisibility(0);
        } else {
            setSearchConfirmButtonVisibility(8);
        }
        if (!this.isShowVoiceSearch || this.mHasInput) {
            this.mVoiceButton.setVisibility(8);
        } else {
            this.mVoiceButton.setVisibility(0);
        }
        initInputColor();
        setInputMaxLength(this.mInputMaxLength);
    }

    private void initInputColor() {
        this.mSearchEditView.setTextColor(this.mEditTextColor);
        this.mSearchEditView.setHintTextColor(this.mEditHintColor);
        this.mHintIconView.setIconfontColor(this.mEditIconColor);
        this.mVoiceButton.setIconfontColor(this.mEditIconColor);
        this.mClearButton.setIconfontColor(this.mEditIconColor);
    }

    private void initBackButton() {
        this.mBackButton.setOnClickListener(new aj(this));
    }

    public void setInputMaxLength(int length) {
        if (length > 0) {
            this.mSearchEditView.setFilters(new InputFilter[]{new LengthFilter(length)});
            return;
        }
        this.mSearchEditView.setFilters(new InputFilter[0]);
    }

    private void setClearBtnClick() {
        this.mClearButton.setOnClickListener(new ak(this));
    }

    public void setTextChangedListner(TextWatcher watcher) {
        if (this.editChangedWatcher != null) {
            this.editChangedWatcher.a(watcher);
        }
    }

    public void setButtonUnActivity() {
        if (this.mHasInput) {
            this.mClearButton.setVisibility(8);
            this.mSearchConfirmButton.setEnabled(false);
            if (this.isShowVoiceSearch) {
                this.mVoiceButton.setVisibility(0);
            }
            this.mHasInput = false;
        }
    }

    public void setButtonActivity() {
        if (!this.mHasInput) {
            this.mClearButton.setVisibility(0);
            this.mSearchConfirmButton.setEnabled(true);
            this.mVoiceButton.setVisibility(8);
            this.mHasInput = true;
        }
    }

    public AUIconView getBackButton() {
        return this.mBackButton;
    }

    public AUIconView getClearButton() {
        return this.mClearButton;
    }

    public AUEditText getSearchEditView() {
        return this.mSearchEditView;
    }

    public AUIconView getSearchButton() {
        return this.mSearchConfirmButton;
    }

    public AURelativeLayout getSearchRelativeLayout() {
        return this.mSearchRelativeLayout;
    }

    public AUIconView getVoiceButton() {
        return this.mVoiceButton;
    }

    public View getSearchBackgroundView() {
        return this.mSearchBackgroundView;
    }

    public AUIconView getHintIconView() {
        return this.mHintIconView;
    }

    public AURelativeLayout getTitleBarRelative() {
        return this.mTitleBarRelative;
    }

    public void setBackgroundDrawable(Drawable background) {
        if (VERSION.SDK_INT >= 16) {
            this.mTitleBarRelative.setBackground(background);
        } else {
            this.mTitleBarRelative.setBackgroundDrawable(background);
        }
    }

    public void setSearchConfirmButtonVisibility(int visibility) {
        this.mSearchConfirmButton.setVisibility(visibility);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mSearchRelativeLayout.getLayoutParams();
        switch (visibility) {
            case 0:
                layoutParams.setMargins(0, 0, 0, 0);
                break;
            case 8:
                layoutParams.setMargins(0, 0, getResources().getDimensionPixelOffset(R.dimen.AU_MARGIN_UNIVERSAL), 0);
                break;
        }
        this.mSearchRelativeLayout.setLayoutParams(layoutParams);
    }

    public void setBackButtonColor(int color) {
        this.mBackButton.setIconfontColorStates(getStateColor(color));
    }

    public void setSearchButtonColor(int color) {
        this.mSearchConfirmButton.setIconfontColorStates(getStateColor(color));
    }

    private ColorStateList getStateColor(int normalColor) {
        return StateListUtils.getColorStateList(normalColor, normalColor & 1728053247, getResources().getColor(R.color.AU_COLOR_TEXT_DISABLE));
    }

    public void attachFlag2SearchBtn(View flagView) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(7, this.mSearchConfirmButton.getId());
        layoutParams.addRule(6, this.mSearchConfirmButton.getId());
        flagView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        int flagHeight = flagView.getMeasuredHeight();
        int flagWidth = flagView.getMeasuredWidth();
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.flag_top_margin) - (flagHeight / 2);
        layoutParams.rightMargin = (getResources().getDimensionPixelOffset(R.dimen.AU_MARGIN_UNIVERSAL) - (flagWidth / 2)) + getResources().getDimensionPixelOffset(R.dimen.flag_left_margin);
        flagView.setLayoutParams(layoutParams);
        this.mSearchBarLayout.addView(flagView);
    }
}
