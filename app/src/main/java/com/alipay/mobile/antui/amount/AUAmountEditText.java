package com.alipay.mobile.antui.amount;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ScrollView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUEditText;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.constant.AUConstant;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.iconfont.manager.TypefaceCache;
import com.alipay.mobile.antui.keyboard.AUNumberKeyBoardUtil;
import com.alipay.mobile.antui.keyboard.AUNumberKeyboardView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AUAmountEditText extends AURelativeLayout implements OnFocusChangeListener {
    private AUAmountTextWatcher editChangedWatcher;
    private List<OnFocusChangeListener> focusChangeListeners = new ArrayList();
    /* access modifiers changed from: private */
    public AUNumberKeyBoardUtil keyBoardUtil;
    private AUIconView mClearIcon;
    private Context mContext;
    private View mDivider;
    /* access modifiers changed from: private */
    public AUEditText mEditText;
    private AUTextView mTvUnitIcon;

    public AUAmountEditText(Context context) {
        super(context);
        init(context, null);
    }

    public AUAmountEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUAmountEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.au_amount_edit_text, this, true);
        this.mEditText = (AUEditText) findViewById(R.id.amount_edit);
        this.mClearIcon = (AUIconView) findViewById(R.id.amount_clear_icon);
        this.mDivider = findViewById(R.id.edit_divider);
        this.mTvUnitIcon = (AUTextView) findViewById(R.id.amount_unit_icon);
        this.mContext = context;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AmountInputBox);
            initContent(array);
            array.recycle();
        }
        this.mTvUnitIcon.setTypeface(TypefaceCache.getTypeface(getContext(), AUConstant.RES_BUNDLE, getTTFFilePath()));
        this.editChangedWatcher = new AUAmountTextWatcher(context, this.mEditText, this.mClearIcon);
        this.mEditText.addTextChangedListener(this.editChangedWatcher);
        this.mEditText.setOnFocusChangeListener(this);
        this.mEditText.setInputType(8194);
        setClearBtnClick();
        setOnTouchListener(new a(this));
    }

    private String getTTFFilePath() {
        return new StringBuilder(AUConstant.RES_BUNDLE).append(File.separator).append("AlipayNumber.ttf").toString();
    }

    private void initContent(TypedArray typedArray) {
        if (typedArray.hasValue(5)) {
            this.mEditText.setHint(typedArray.getString(5));
        }
    }

    private void setClearBtnClick() {
        this.mClearIcon.setOnClickListener(new b(this));
    }

    public AUEditText getEditText() {
        return this.mEditText;
    }

    public Editable getEditTextEditable() {
        return this.mEditText.getText();
    }

    public void setDividerVisible(boolean visible) {
        this.mDivider.setVisibility(visible ? 0 : 8);
    }

    public void setHint(String hint) {
        this.mEditText.setHint(hint);
    }

    public void isShowClearIcon(boolean isShow) {
        this.editChangedWatcher.setShowClear(isShow);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        this.editChangedWatcher.onInputTextStatusChanged(TextUtils.isEmpty(getEditTextEditable()), hasFocus);
        if (hasFocus && this.keyBoardUtil != null) {
            this.keyBoardUtil.showKeyboard();
        } else if (this.keyBoardUtil != null) {
            this.keyBoardUtil.hideKeyboard();
        }
        for (OnFocusChangeListener onFocusChange : this.focusChangeListeners) {
            onFocusChange.onFocusChange(v, hasFocus);
        }
    }

    public void addOnFocusChangeListeners(OnFocusChangeListener listener) {
        if (listener != null) {
            this.focusChangeListeners.add(listener);
        }
    }

    public void setKeyBoardView(AUNumberKeyboardView keyboardView, ScrollView scrollView, int softInputAdjustMode) {
        this.keyBoardUtil = new AUNumberKeyBoardUtil(this.mContext, this.mEditText, keyboardView, softInputAdjustMode);
        if (scrollView != null) {
            this.keyBoardUtil.setScrollView(scrollView);
        }
    }

    public void setKeyBoardView(AUNumberKeyboardView keyboardView, ScrollView scrollView) {
        setKeyBoardView(keyboardView, scrollView, 0);
    }

    public void setKeyBoardView(AUNumberKeyboardView keyboardView) {
        setKeyBoardView(keyboardView, 0);
    }

    public void setKeyBoardView(AUNumberKeyboardView keyboardView, int softInputAdjustMode) {
        setKeyBoardView(keyboardView, null, softInputAdjustMode);
    }
}
