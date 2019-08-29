package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;

public class AUSearchView extends AURelativeLayout implements AntUI {
    private boolean isShowVoiceSearch = false;
    private AUIconView mClearButton;
    private int mEditHintColor;
    private int mEditIconColor;
    private int mEditTextColor;
    private boolean mHasInput = false;
    private AUIconView mHintIconView;
    private int mInputMaxLength = -1;
    /* access modifiers changed from: private */
    public AUEditText mSearchEditView;
    private View mSearchInputBg;
    private AUIconView mVoiceButton;

    public AUSearchView(Context context) {
        super(context);
        init(context, null);
    }

    public AUSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUSearchView(Context context, AttributeSet attrs, int defStyle) {
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
        LayoutInflater.from(context).inflate(R.layout.au_search_view, this, true);
        this.mSearchEditView = (AUEditText) findViewById(R.id.search_input_box);
        this.mClearButton = (AUIconView) findViewById(R.id.search_clear_btn);
        this.mVoiceButton = (AUIconView) findViewById(R.id.search_voice_btn);
        this.mHintIconView = (AUIconView) findViewById(R.id.search_icon);
        this.mSearchInputBg = findViewById(R.id.search_bg);
        setTextChangedListner();
        setClearBtnClick();
        this.mEditHintColor = getResources().getColor(R.color.AU_COLOR_ASS_CONTENT);
        this.mEditIconColor = getResources().getColor(R.color.AU_COLOR_SEARCH_INPUT_ICON);
        this.mEditTextColor = getResources().getColor(R.color.AU_COLOR_MAIN_CONTENT);
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
        if (typedArray.hasValue(2)) {
            this.mSearchEditView.setText(typedArray.getString(2));
        }
        if (typedArray.hasValue(3)) {
            this.mSearchEditView.setHint(typedArray.getString(3));
        }
        if (typedArray.hasValue(6)) {
            this.mHintIconView.setIconfontUnicode(typedArray.getString(6));
        }
        if (typedArray.hasValue(7)) {
            this.mHintIconView.setImageDrawable(typedArray.getDrawable(7));
        }
    }

    public void initStyleByTheme(Context context) {
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        this.mEditHintColor = theme.getColor(context, AUThemeKey.SERACHBAR_HINT_COLOR, Integer.valueOf(this.mEditHintColor)).intValue();
        this.mEditTextColor = theme.getColor(context, AUThemeKey.SERACHBAR_INPUT_TEXTCOLOR, Integer.valueOf(this.mEditTextColor)).intValue();
        this.mEditIconColor = theme.getColor(context, AUThemeKey.SERACHBAR_ICON_COLOR, Integer.valueOf(this.mEditIconColor)).intValue();
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
        this.isShowVoiceSearch = typedArray.getBoolean(1, false);
        this.mInputMaxLength = typedArray.getInt(5, -1);
        this.mEditHintColor = typedArray.getColor(10, this.mEditHintColor);
        this.mEditTextColor = typedArray.getColor(11, this.mEditTextColor);
        this.mEditIconColor = typedArray.getColor(12, this.mEditIconColor);
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    private void initBtnStatus() {
        this.mHintIconView.setImportantForAccessibility(4);
        if (!this.isShowVoiceSearch || this.mHasInput) {
            this.mVoiceButton.setVisibility(8);
        } else {
            this.mVoiceButton.setVisibility(0);
        }
        initInputColor();
        setInputMaxLength();
    }

    private void initInputColor() {
        this.mSearchEditView.setTextColor(this.mEditTextColor);
        this.mSearchEditView.setHintTextColor(this.mEditHintColor);
        this.mSearchInputBg.setBackgroundResource(R.drawable.search_bar_input_bg);
        this.mHintIconView.setIconfontColor(this.mEditIconColor);
        this.mVoiceButton.setIconfontColor(this.mEditIconColor);
        this.mClearButton.setIconfontColor(this.mEditIconColor);
    }

    public void setInputMaxLength() {
        if (this.mInputMaxLength > 0) {
            this.mSearchEditView.setFilters(new InputFilter[]{new LengthFilter(this.mInputMaxLength)});
            return;
        }
        this.mSearchEditView.setFilters(new InputFilter[0]);
    }

    private void setClearBtnClick() {
        this.mClearButton.setOnClickListener(new am(this));
    }

    private void setTextChangedListner() {
        this.mSearchEditView.addTextChangedListener(new an(this));
    }

    /* access modifiers changed from: private */
    public void setButtonUnActivity() {
        if (this.mHasInput) {
            this.mClearButton.setVisibility(8);
            if (this.isShowVoiceSearch) {
                this.mVoiceButton.setVisibility(0);
            }
            this.mHasInput = false;
        }
    }

    /* access modifiers changed from: private */
    public void setButtonActivity() {
        if (!this.mHasInput) {
            this.mClearButton.setVisibility(0);
            this.mVoiceButton.setVisibility(8);
            this.mHasInput = true;
        }
    }

    public AUIconView getClearButton() {
        return this.mClearButton;
    }

    public AUIconView getHintIconView() {
        return this.mHintIconView;
    }

    public AUEditText getSearchEditView() {
        return this.mSearchEditView;
    }

    public AUIconView getVoiceButton() {
        return this.mVoiceButton;
    }

    public void setColorOriginalStyle() {
        initInputColor();
    }

    public void setTransparentStyle() {
        int whiteColor = getContext().getResources().getColor(R.color.AU_COLOR_UNIVERSAL_BG);
        this.mSearchInputBg.setBackgroundResource(R.drawable.au_search_input_bg_trans);
        this.mSearchEditView.setTextColor(whiteColor);
        this.mHintIconView.setIconfontColor(whiteColor);
        this.mVoiceButton.setIconfontColor(whiteColor);
        this.mClearButton.setIconfontColor(whiteColor);
    }
}
