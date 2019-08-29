package com.alipay.mobile.antui.tablelist;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUTextView;

public class AUParallelTitleListItem extends AUBaseListItem {
    private String leftSubText;
    private String leftText;
    private AUTextView mLeftSubTextView;
    private AUTextView mLeftTextView;
    private AUTextView mRightSubTextView;
    private AUTextView mRightTextView;
    private String rightSubText;
    private String rightText;

    public AUParallelTitleListItem(Context context) {
        super(context);
        init(context, null);
    }

    public AUParallelTitleListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUParallelTitleListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initView();
        initContent(context, attrs);
        setParallelText(this.leftText, this.leftSubText, this.rightText, this.rightSubText);
    }

    /* access modifiers changed from: protected */
    public void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_parallel_title_item, this.mListLayout, true);
    }

    private void initView() {
        this.mLeftTextView = (AUTextView) findViewById(R.id.parallel_left_text);
        this.mLeftSubTextView = (AUTextView) findViewById(R.id.parallel_left_sub_text);
        this.mRightTextView = (AUTextView) findViewById(R.id.parallel_right_text);
        this.mRightSubTextView = (AUTextView) findViewById(R.id.parallel_right_sub_text);
    }

    private void initContent(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.listItem);
        this.leftText = typedArray.getString(2);
        this.leftSubText = typedArray.getString(3);
        this.rightText = typedArray.getString(13);
        this.rightSubText = typedArray.getString(19);
        typedArray.recycle();
    }

    public void setParallelText(String leftText2, String leftSubText2, String rightText2, String rightSubText2) {
        setLeftText(leftText2);
        setLeftSubText(leftSubText2);
        setRightText(rightText2);
        setRightSubText(rightSubText2);
    }

    public void setLeftText(String leftText2) {
        setTextViewString(this.mLeftTextView, leftText2);
    }

    public void setRightText(String rightText2) {
        setTextViewString(this.mRightTextView, rightText2);
    }

    public void setLeftSubText(String leftSubText2) {
        setTextViewString(this.mLeftSubTextView, leftSubText2);
    }

    public void setRightSubText(String rightSubText2) {
        setTextViewString(this.mRightSubTextView, rightSubText2);
    }

    private void setTextViewString(AUTextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
        textView.setText(text);
    }
}
