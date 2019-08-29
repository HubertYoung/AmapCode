package com.alipay.mobile.antui.tablelist;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;

public class AUMultiListItem extends AUAbsListItem {
    private AUEmptyGoneTextView mLeftSubTextView;

    public AUMultiListItem(Context context) {
        super(context);
    }

    public AUMultiListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSelfDefAttrs(context, attrs);
    }

    public AUMultiListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSelfDefAttrs(context, attrs);
    }

    /* access modifiers changed from: protected */
    public int getImageVerticalMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.au_list_image_space_3);
    }

    /* access modifiers changed from: protected */
    public int getLeftImageSize(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.au_double_image_size_4);
    }

    private void initSelfDefAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.listItem);
        if (ta.hasValue(3)) {
            setLeftSubText(ta.getString(3));
        }
        ta.recycle();
        setArrowVisibility(false);
    }

    private void initLeftSubView() {
        this.mLeftSubTextView = new AUEmptyGoneTextView(getContext());
        this.mLeftSubTextView.setTextAppearance(getContext(), R.style.listContentTextStyle);
        this.mLeftSubTextView.setMaxLines(2);
        this.mLeftSubTextView.setEllipsize(TruncateAt.END);
        this.leftContainer.addView(this.mLeftSubTextView);
    }

    public void addLeftAssistantView(View view) {
        this.leftContainer.addView(view);
    }

    public void setLeftSubText(CharSequence text) {
        if (this.mLeftSubTextView == null) {
            initLeftSubView();
        }
        this.mLeftSubTextView.setText(text);
    }

    public AUEmptyGoneTextView getLeftSubTextView() {
        if (this.mLeftSubTextView == null) {
            initLeftSubView();
        }
        return this.mLeftSubTextView;
    }
}
