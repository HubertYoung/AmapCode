package com.alipay.mobile.antui.amount;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUEditText;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;

public class AUAmountFootView extends AURelativeLayout {
    public static final int EDIT_STYLE = 16;
    public static final int TEXT_STYLE = 17;
    private int footStyle = -1;
    private AUIconView mClearIcon;
    private AUTextView mContentText;
    /* access modifiers changed from: private */
    public AUEditText mEditText;

    public AUAmountFootView(Context context) {
        super(context);
        init(context);
    }

    public AUAmountFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AUAmountFootView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_amount_foot_view, this, true);
        this.mEditText = (AUEditText) findViewById(R.id.foot_edit);
        this.mClearIcon = (AUIconView) findViewById(R.id.foot_clear_btn);
        this.mContentText = (AUTextView) findViewById(R.id.foot_content);
        setEditChangedListner();
        setClearBtnClick();
    }

    public void setStyle(int style) {
        this.footStyle = style;
        switch (style) {
            case 16:
                this.mEditText.setVisibility(0);
                this.mContentText.setVisibility(8);
                return;
            case 17:
                this.mEditText.setVisibility(8);
                this.mClearIcon.setVisibility(8);
                this.mContentText.setVisibility(0);
                return;
            default:
                return;
        }
    }

    public void setEditHint(String hint) {
        this.mEditText.setHint(hint);
    }

    public void setText(String contentText) {
        switch (this.footStyle) {
            case 16:
                this.mEditText.setText(contentText);
                return;
            case 17:
                this.mContentText.setText(contentText);
                return;
            default:
                return;
        }
    }

    public AUEditText getEditText() {
        return this.mEditText;
    }

    public Editable getEditTextEditable() {
        return this.mEditText.getText();
    }

    public AUTextView getContentText() {
        return this.mContentText;
    }

    private void setEditChangedListner() {
        this.mEditText.addTextChangedListener(new c(this));
        this.mEditText.setOnFocusChangeListener(new d(this));
    }

    private void setClearBtnClick() {
        this.mClearIcon.setOnClickListener(new e(this));
    }

    /* access modifiers changed from: protected */
    public void onInputTextStatusChanged(boolean isEmpty, boolean isFocused) {
        if (isEmpty || !isFocused) {
            this.mClearIcon.setVisibility(8);
        } else {
            this.mClearIcon.setVisibility(0);
        }
    }
}
