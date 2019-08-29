package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;

public class AUSearchInputBox extends AURelativeLayout implements AntUI {
    private Drawable mBackgroundDrawable;
    private AUSearchView searchView;

    public AUSearchInputBox(Context context) {
        super(context);
        init(context, null);
    }

    public AUSearchInputBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUSearchInputBox(Context context, AttributeSet attrs, int defStyle) {
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
        setBackgroundDrawable(this.mBackgroundDrawable);
        setGravity(15);
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
        LayoutInflater.from(context).inflate(R.layout.au_search_input_box, this, true);
        this.searchView = (AUSearchView) findViewById(R.id.search_view);
        this.mBackgroundDrawable = new ColorDrawable(-1);
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
        this.searchView.initContent(context, attrs, typedArray);
    }

    public void initStyleByTheme(Context context) {
        this.mBackgroundDrawable = AUThemeManager.getCurrentTheme().getDrawable(context, AUThemeKey.SERACHBAR_INPUT_BACKGROUND_COLOR, this.mBackgroundDrawable);
        this.searchView.initStyleByTheme(context);
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
        this.searchView.initAttrStyle(context, attrs, typedArray);
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    public void setInputMaxLength() {
        this.searchView.setInputMaxLength();
    }

    public AUIconView getClearButton() {
        return this.searchView.getClearButton();
    }

    public AUEditText getSearchEditView() {
        return this.searchView.getSearchEditView();
    }

    public AUIconView getVoiceButton() {
        return this.searchView.getVoiceButton();
    }

    public CharSequence getContentDescription() {
        return this.searchView.getSearchEditView().getContentDescription();
    }
}
