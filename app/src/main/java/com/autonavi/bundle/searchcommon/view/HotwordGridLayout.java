package com.autonavi.bundle.searchcommon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class HotwordGridLayout extends RelativeLayout {
    private TextView[] mGridItemTextViews;
    private int mItemColor;
    /* access modifiers changed from: private */
    public OnClickListener mOnHotwordItemClickListener;
    private int[] mTextViewIds;
    private TextView mTitle;
    private int mTitleColor;
    private String mTitleText;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    public HotwordGridLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mGridItemTextViews = new TextView[8];
        this.mTextViewIds = new int[]{R.id.tv_hot_word_1, R.id.tv_hot_word_2, R.id.tv_hot_word_3, R.id.tv_hot_word_4, R.id.tv_hot_word_5, R.id.tv_hot_word_6, R.id.tv_hot_word_7, R.id.tv_hot_word_8};
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HotwordGridLayout);
        this.mTitleColor = obtainStyledAttributes.getColor(R.styleable.HotwordGridLayout_title_color, 0);
        this.mTitleText = obtainStyledAttributes.getString(R.styleable.HotwordGridLayout_title_text);
        this.mItemColor = obtainStyledAttributes.getColor(R.styleable.HotwordGridLayout_item_color, 0);
        obtainStyledAttributes.recycle();
        init();
    }

    public HotwordGridLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HotwordGridLayout(Context context) {
        this(context, null, 0);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.search_indoor_hotword_layout, this, true);
        this.mTitle = (TextView) findViewById(R.id.tv_hot_word_title);
        this.mTitle.setTextColor(this.mTitleColor);
        this.mTitle.setText(this.mTitleText);
        for (int i = 0; i < 8; i++) {
            this.mGridItemTextViews[i] = (TextView) findViewById(this.mTextViewIds[i]);
            if (this.mItemColor != 0) {
                this.mGridItemTextViews[i].setTextColor(this.mItemColor);
            }
            this.mGridItemTextViews[i].setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (HotwordGridLayout.this.mOnHotwordItemClickListener != null && (view instanceof TextView) && !TextUtils.isEmpty(((TextView) view).getText())) {
                        HotwordGridLayout.this.mOnHotwordItemClickListener.onClick(view);
                    }
                }
            });
        }
    }

    public void bindHotwordTitle(String str) {
        this.mTitleText = str;
        this.mTitle.setText(this.mTitleText);
    }

    public void setTitleVisibility(int i) {
        this.mTitle.setVisibility(i);
    }

    public void bindHotwords(String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            for (int i = 0; i < strArr.length; i++) {
                this.mGridItemTextViews[i].setText(strArr[i]);
            }
        }
    }

    public void setOnHotwordItemClickListener(OnClickListener onClickListener) {
        this.mOnHotwordItemClickListener = onClickListener;
    }

    public void ondestory() {
        if (this.mGridItemTextViews != null && this.mGridItemTextViews.length > 0) {
            for (int i = 0; i < this.mGridItemTextViews.length; i++) {
                this.mGridItemTextViews[i].setOnClickListener(null);
                this.mGridItemTextViews[i].setOnTouchListener(null);
                this.mGridItemTextViews[i] = null;
            }
        }
        if (this.mOnHotwordItemClickListener != null) {
            this.mOnHotwordItemClickListener = null;
        }
    }
}
