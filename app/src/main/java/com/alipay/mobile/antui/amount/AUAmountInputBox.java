package com.alipay.mobile.antui.amount;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUEditText;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;

public class AUAmountInputBox extends AURelativeLayout {
    private int footStyle = 16;
    private AUAmountEditText mEditText;
    private AUAmountFootView mFootView;
    private AUTextView mTitleView;

    public AUAmountInputBox(Context context) {
        super(context);
        init(context, null);
    }

    public AUAmountInputBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUAmountInputBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.au_amount_input_box, this, true);
        this.mFootView = (AUAmountFootView) findViewById(R.id.amount_foot);
        this.mTitleView = (AUTextView) findViewById(R.id.amount_title);
        this.mEditText = (AUAmountEditText) findViewById(R.id.amount_edit_text);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AmountInputBox);
            initContent(array);
            initAttrStyle(array);
            array.recycle();
        }
        setFootStyle(this.footStyle);
    }

    private void initContent(TypedArray typedArray) {
        if (typedArray.hasValue(3)) {
            setViewInfo(this.mTitleView, typedArray.getString(3));
        }
        if (typedArray.hasValue(5)) {
            this.mEditText.setHint(typedArray.getString(5));
        }
    }

    private void initAttrStyle(TypedArray typedArray) {
        if (typedArray.hasValue(1)) {
            this.footStyle = typedArray.getInt(1, this.footStyle);
        }
    }

    private void setViewInfo(AUTextView textView, String info) {
        if (!TextUtils.isEmpty(info)) {
            textView.setText(info);
        }
    }

    public AUEditText getEditText() {
        return this.mEditText.getEditText();
    }

    public AUAmountEditText getEditLayout() {
        return this.mEditText;
    }

    public AUAmountFootView getFootView() {
        return this.mFootView;
    }

    public AUTextView getTitleView() {
        return this.mTitleView;
    }

    public void setFootStyle(int style) {
        if (style != -1) {
            this.mFootView.setStyle(style);
            this.mFootView.setVisibility(0);
            return;
        }
        this.mFootView.setVisibility(8);
    }

    public void setFootHint(String hint) {
        this.mFootView.setEditHint(hint);
    }

    public void setFootText(String text) {
        this.mFootView.setText(text);
    }
}
