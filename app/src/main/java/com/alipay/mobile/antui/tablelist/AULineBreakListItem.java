package com.alipay.mobile.antui.tablelist;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class AULineBreakListItem extends AUBaseListItem {
    private LayoutParams leftParams;
    private AULinearLayout mLeftContainer;
    /* access modifiers changed from: private */
    public int mLeftLength;
    private AUTextView mLeftText;
    /* access modifiers changed from: private */
    public int mRightLength;
    /* access modifiers changed from: private */
    public AUTextView mRightText;
    private int middleScreenWidth;
    private LayoutParams rightParams;

    public AULineBreakListItem(Context context) {
        super(context);
        init(context, null);
    }

    public AULineBreakListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AULineBreakListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_line_break_list_item, this.mListLayout, true);
        int padding = getResources().getDimensionPixelSize(R.dimen.AU_SPACE18);
        this.mListLayout.setPadding(0, padding, 0, padding);
    }

    private void init(Context context, AttributeSet attrs) {
        this.middleScreenWidth = (getScreenWidth(context) / 2) - context.getResources().getDimensionPixelSize(R.dimen.AU_MARGIN_UNIVERSAL);
        initView();
        initStyle(context, attrs);
    }

    private void initView() {
        this.mLeftText = (AUTextView) findViewById(R.id.break_left_text);
        this.mRightText = (AUTextView) findViewById(R.id.break_right_text);
        this.mLeftContainer = (AULinearLayout) findViewById(R.id.break_left_container);
        this.leftParams = new LayoutParams(-1, -2);
        this.leftParams.gravity = 16;
        this.rightParams = new LayoutParams(-1, -2);
        this.rightParams.gravity = 16;
        this.rightParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.AU_SPACE17);
        this.mLeftText.addTextChangedListener(new d(this, new a(this)));
        this.mRightText.addTextChangedListener(new d(this, new b(this)));
    }

    private void initStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.listItem);
        String leftText = typedArray.getString(2);
        String rightText = typedArray.getString(13);
        typedArray.recycle();
        setText(leftText, rightText);
    }

    public void setText(String left, String right) {
        this.mLeftText.setText(left);
        this.mRightText.setText(right);
    }

    public AUTextView getLeftText() {
        return this.mLeftText;
    }

    public AUTextView getRightText() {
        return this.mRightText;
    }

    /* access modifiers changed from: private */
    public void adjustWeight() {
        if (this.mLeftLength > this.middleScreenWidth && this.mRightLength <= this.middleScreenWidth) {
            this.leftParams.weight = 1.0f;
            this.rightParams.weight = 0.0f;
            this.leftParams.width = -1;
            this.rightParams.width = -2;
        } else if (this.mLeftLength <= this.middleScreenWidth && this.mRightLength > this.middleScreenWidth) {
            this.leftParams.weight = 0.0f;
            this.rightParams.weight = 1.0f;
            this.leftParams.width = -2;
            this.rightParams.width = -1;
        } else if (this.mLeftLength > this.middleScreenWidth || this.mRightLength > this.middleScreenWidth) {
            this.leftParams.weight = 1.0f;
            this.rightParams.weight = 1.0f;
            this.leftParams.width = -1;
            this.rightParams.width = -1;
        } else {
            this.leftParams.weight = 1.0f;
            this.rightParams.weight = 0.0f;
            this.leftParams.width = -1;
            this.rightParams.width = -2;
        }
        this.mLeftContainer.setLayoutParams(this.leftParams);
        this.mRightText.setLayoutParams(this.rightParams);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mRightText.getLineCount() <= 1) {
            this.mRightText.setGravity(5);
        } else {
            this.mRightText.setGravity(3);
        }
    }

    private static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth();
    }
}
