package com.alipay.mobile.antui.input;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUEditText;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;

public class AUParagraphInputBox extends AURelativeLayout {
    private static final int DEFAULT_MAX_LENGTH = 240;
    private String mInputHint;
    private String mInputText;
    /* access modifiers changed from: private */
    public int maxLength = 240;
    private AUEditText paraEditView;
    /* access modifiers changed from: private */
    public AUTextView paraTextView;
    private boolean supportEmoji = false;

    public AUParagraphInputBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public AUParagraphInputBox(Context context) {
        super(context);
        init(context, null);
    }

    public AUParagraphInputBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.au_paragrash_input_view, this, true);
        setBackgroundResource(R.drawable.drawable_bg_top_bottom_line);
        this.paraEditView = (AUEditText) findViewById(R.id.paragraph_edit);
        this.paraTextView = (AUTextView) findViewById(R.id.paragraph_text);
        infateStyle(context, attrs);
        this.paraEditView.setSupportEmoji(this.supportEmoji);
        this.paraEditView.setFilters(new InputFilter[]{new LengthFilter(this.maxLength)});
        this.paraEditView.addTextChangedListener(new d(this));
        if (!TextUtils.isEmpty(this.mInputHint)) {
            this.paraEditView.setHint(this.mInputHint);
        }
        if (!TextUtils.isEmpty(this.mInputText)) {
            this.paraEditView.setText(this.mInputHint);
        } else {
            this.paraTextView.setText("0/" + String.valueOf(this.maxLength));
        }
    }

    private void infateStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AUInputBox);
            this.mInputText = a.getString(1);
            this.maxLength = a.getInt(4, 240);
            this.mInputHint = a.getString(0);
            a.recycle();
            TypedArray emojia = context.obtainStyledAttributes(attrs, R.styleable.EmojiAttr);
            this.supportEmoji = emojia.getBoolean(0, false);
            emojia.recycle();
        }
    }

    public AUEditText getParaEditView() {
        return this.paraEditView;
    }

    public AUTextView getParaTextView() {
        return this.paraTextView;
    }
}
