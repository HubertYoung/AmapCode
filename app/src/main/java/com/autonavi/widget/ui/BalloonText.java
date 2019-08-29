package com.autonavi.widget.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BalloonText extends BalloonLayout {
    public static final int STYLE_DEFAULT = 0;
    public static final int STYLE_NIGHT = 1;
    public static final int TYPE_BOTTOM_CENTER = 7;
    public static final int TYPE_BOTTOM_LEFT = 6;
    public static final int TYPE_BOTTOM_RIGHT = 8;
    public static final int TYPE_CENTER_CENTER = 0;
    public static final int TYPE_LEFT_BOTTOM = 10;
    public static final int TYPE_LEFT_TOP = 9;
    public static final int TYPE_RIGHT_BOTTOM = 5;
    public static final int TYPE_RIGHT_TOP = 4;
    public static final int TYPE_TOP_CENTER = 2;
    public static final int TYPE_TOP_LEFT = 1;
    public static final int TYPE_TOP_RIGHT = 3;
    private int mStyle;
    private CharSequence mText;
    private int mTextColor;
    private int mTextSize;
    private TextView mTextView;
    private int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BalloonStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BalloonType {
    }

    public BalloonText(Context context) {
        this(context, null);
    }

    public BalloonText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BalloonText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStyle = 0;
        this.mType = 0;
    }

    /* access modifiers changed from: protected */
    public void init(Context context) {
        super.init(context);
        addTextView(context);
        setMinimumHeight(context.getResources().getDimensionPixelOffset(R.dimen.balloon_min_height));
        int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.balloon_padding_hor);
        int dimensionPixelOffset2 = context.getResources().getDimensionPixelOffset(R.dimen.balloon_padding_ver);
        setPadding(dimensionPixelOffset, dimensionPixelOffset2, dimensionPixelOffset, dimensionPixelOffset2);
    }

    /* access modifiers changed from: protected */
    public void initDefaultValues() {
        this.mMaxWidth = getResources().getDimensionPixelOffset(R.dimen.balloon_max_width);
        this.mArrowWidth = (float) getResources().getDimensionPixelOffset(R.dimen.balloon_arrow_width);
        this.mArrowHeight = (float) getResources().getDimensionPixelOffset(R.dimen.balloon_arrow_height);
        this.mStrokeWidth = getResources().getDimension(R.dimen.balloon_stroke_width);
        this.mCornersRadius = getResources().getDimension(R.dimen.balloon_conner_radius);
        this.mStrokeColor = -4924960;
        this.mBubbleColor = -1311489;
        this.mShadowRadius = getResources().getDimension(R.dimen.balloon_shadow_radius);
        this.mShadowY = getResources().getDimension(R.dimen.balloon_shadow_y);
        this.mShadowColor = 134217728;
    }

    /* access modifiers changed from: protected */
    public void initFromAttributes(Context context, AttributeSet attributeSet) {
        this.mTextSize = getResources().getDimensionPixelSize(R.dimen.f_s_13);
        this.mTextColor = getResources().getColor(R.color.f_c_2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BalloonText);
        this.mStyle = obtainStyledAttributes.getInt(R.styleable.BalloonText_balloon_style, 0);
        if (this.mStyle == 1) {
            this.mTextColor = getResources().getColor(R.color.f_c_17);
            this.mBubbleColor = getResources().getColor(R.color.c_16);
            this.mStrokeColor = getResources().getColor(R.color.c_16_b);
        }
        this.mTextColor = obtainStyledAttributes.getColor(R.styleable.BalloonText_balloon_textColor, this.mTextColor);
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.BalloonText_balloon_textSize, this.mTextSize);
        this.mText = obtainStyledAttributes.getString(R.styleable.BalloonText_balloon_text);
        this.mType = obtainStyledAttributes.getInt(R.styleable.BalloonText_balloon_type, 0);
        setType(this.mType);
        obtainStyledAttributes.recycle();
        super.initFromAttributes(context, attributeSet);
    }

    private void addTextView(Context context) {
        this.mTextView = new TextView(context);
        this.mTextView.setText(this.mText);
        this.mTextView.setTextSize(0, (float) this.mTextSize);
        this.mTextView.setTextColor(this.mTextColor);
        addView(this.mTextView);
    }

    public TextView getTextView() {
        return this.mTextView;
    }

    public void setTextSize(int i) {
        this.mTextSize = i;
        this.mTextView.setTextSize(1, (float) this.mTextSize);
    }

    public void setTextColor(int i) {
        this.mTextColor = i;
        this.mTextView.setText(this.mTextSize);
    }

    public void setText(CharSequence charSequence) {
        this.mText = charSequence;
        this.mTextView.setText(charSequence);
    }

    public void setStyle(int i) {
        this.mStyle = i;
        switch (this.mStyle) {
            case 0:
                if (this.mTextView != null) {
                    this.mTextView.setTextColor(getResources().getColor(R.color.f_c_2));
                }
                setStroke(this.mStrokeWidth, -4924960);
                setBubbleColor(-1311489);
                break;
            case 1:
                if (this.mTextView != null) {
                    this.mTextView.setTextColor(getResources().getColor(R.color.f_c_17));
                }
                setStroke(this.mStrokeWidth, getResources().getColor(R.color.c_16_b));
                setBubbleColor(getResources().getColor(R.color.c_16));
                return;
        }
    }

    public void setType(int i) {
        this.mType = i;
        switch (this.mType) {
            case 1:
                this.mArrowDirection = 2;
                this.mArrowOffset = getResources().getDimension(R.dimen.balloon_arrow_offset);
                this.mArrowOffsetReverse = false;
                break;
            case 2:
                this.mArrowDirection = 2;
                this.mArrowOffset = -1.0f;
                break;
            case 3:
                this.mArrowDirection = 2;
                this.mArrowOffset = getResources().getDimension(R.dimen.balloon_arrow_offset);
                this.mArrowOffsetReverse = true;
                break;
            case 4:
                this.mArrowDirection = 3;
                this.mArrowOffset = getResources().getDimension(R.dimen.balloon_arrow_offset_top);
                this.mArrowOffsetReverse = false;
                break;
            case 5:
                this.mArrowDirection = 3;
                this.mArrowOffset = getResources().getDimension(R.dimen.balloon_arrow_offset_bottom);
                this.mArrowOffsetReverse = true;
                break;
            case 6:
                this.mArrowDirection = 4;
                this.mArrowOffset = getResources().getDimension(R.dimen.balloon_arrow_offset);
                this.mArrowOffsetReverse = false;
                break;
            case 7:
                this.mArrowDirection = 4;
                this.mArrowOffset = -1.0f;
                this.mArrowOffsetReverse = false;
                break;
            case 8:
                this.mArrowDirection = 4;
                this.mArrowOffset = getResources().getDimension(R.dimen.balloon_arrow_offset);
                this.mArrowOffsetReverse = true;
                break;
            case 9:
                this.mArrowDirection = 1;
                this.mArrowOffset = getResources().getDimension(R.dimen.balloon_arrow_offset_top);
                this.mArrowOffsetReverse = false;
                break;
            case 10:
                this.mArrowDirection = 1;
                this.mArrowOffset = getResources().getDimension(R.dimen.balloon_arrow_offset_bottom);
                this.mArrowOffsetReverse = true;
                break;
            default:
                this.mArrowDirection = 0;
                break;
        }
        requestLayout();
    }
}
