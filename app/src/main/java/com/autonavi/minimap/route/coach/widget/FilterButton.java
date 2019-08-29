package com.autonavi.minimap.route.coach.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class FilterButton extends LinearLayout implements OnClickListener {
    private Drawable mIconDrawable;
    private ImageView mImageView;
    private a mOnClickListener;
    private boolean mSelected;
    private Drawable mSelectedIconDrawable;
    private int mSelectedTextColorResId;
    private int mTextColorResId;
    private TextView mTextView;

    public interface a {
        void onFilterButtonClick(View view);
    }

    public FilterButton(Context context) {
        this(context, null);
    }

    public FilterButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FilterButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.coach_filter_button, this, true).setOnClickListener(this);
        this.mImageView = (ImageView) findViewById(R.id.filter_icon);
        this.mTextView = (TextView) findViewById(R.id.filter_text);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FilterButton);
        if (obtainStyledAttributes != null) {
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.FilterButton_FilterText, 0);
            this.mSelected = obtainStyledAttributes.getBoolean(R.styleable.FilterButton_FilterSelected, false);
            this.mTextColorResId = obtainStyledAttributes.getColor(R.styleable.FilterButton_FilterTextColor, -16777216);
            this.mSelectedTextColorResId = obtainStyledAttributes.getColor(R.styleable.FilterButton_FilterSelectedTextColor, -16776961);
            this.mIconDrawable = obtainStyledAttributes.getDrawable(R.styleable.FilterButton_FilterIcon);
            this.mSelectedIconDrawable = obtainStyledAttributes.getDrawable(R.styleable.FilterButton_FilterSelectedIcon);
            this.mTextView.setText(resourceId);
            this.mTextView.setTextColor(this.mSelected ? this.mSelectedTextColorResId : this.mTextColorResId);
            this.mImageView.setBackgroundDrawable(this.mSelected ? this.mSelectedIconDrawable : this.mIconDrawable);
            obtainStyledAttributes.recycle();
        }
    }

    public void onClick(View view) {
        if (this.mOnClickListener != null) {
            this.mOnClickListener.onFilterButtonClick(view);
        }
    }

    public void setOnFilterButtonClickListener(a aVar) {
        this.mOnClickListener = aVar;
    }

    public void setText(int i) {
        if (this.mTextView != null) {
            this.mTextView.setText(i);
        }
    }

    public void setText(String str) {
        if (this.mTextView != null) {
            this.mTextView.setText(str);
        }
    }

    public String getText() {
        if (this.mTextView == null || this.mTextView.getText() == null) {
            return null;
        }
        return this.mTextView.getText().toString();
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public void setSelected(boolean z) {
        this.mSelected = z;
        if (this.mTextView != null) {
            this.mTextView.setTextColor(this.mSelected ? this.mSelectedTextColorResId : this.mTextColorResId);
        }
        if (this.mImageView != null) {
            this.mImageView.setBackgroundDrawable(this.mSelected ? this.mSelectedIconDrawable : this.mIconDrawable);
        }
    }

    public void setTextColorSelected(boolean z) {
        if (this.mTextView != null) {
            this.mTextView.setTextColor(z ? this.mSelectedTextColorResId : this.mTextColorResId);
        }
    }

    public void setBgColorSelected(boolean z) {
        if (this.mImageView != null) {
            this.mImageView.setBackgroundDrawable(z ? this.mSelectedIconDrawable : this.mIconDrawable);
        }
    }
}
