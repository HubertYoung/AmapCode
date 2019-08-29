package com.autonavi.map.search.comment.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.minimap.R;

public class RatingBar extends LinearLayout {
    private int mCurrentRating;
    private int mItemHeight;
    private int mItemWidth;
    private int mMargin;
    private int mMaxRating;
    private a mOnRatingChangedListener;
    private Drawable mRatedDrawable;
    private int mTouchDownX;
    private int mTouchSpace;
    private Drawable mUnRatedDrawable;

    public interface a {
        void a(int i);
    }

    public int getMaxRating() {
        return this.mMaxRating;
    }

    public void setMaxRating(int i) {
        this.mMaxRating = i;
    }

    public void setCurrentRating(int i) {
        this.mCurrentRating = i;
    }

    public void setMargin(int i) {
        this.mMargin = i;
    }

    public int getItemWidth() {
        return this.mItemWidth;
    }

    public void setItemWidth(int i) {
        this.mItemWidth = i;
    }

    public int getItemHeight() {
        return this.mItemHeight;
    }

    public void setItemHeight(int i) {
        this.mItemHeight = i;
    }

    public Drawable getRatedDrawable() {
        return this.mRatedDrawable;
    }

    public void setRatedDrawable(Drawable drawable) {
        this.mRatedDrawable = drawable;
    }

    public Drawable getUnRatedDrawable() {
        return this.mUnRatedDrawable;
    }

    public void setUnRatedDrawable(Drawable drawable) {
        this.mUnRatedDrawable = drawable;
    }

    public int getTouchSpace() {
        return this.mTouchSpace;
    }

    public void setTouchSpace(int i) {
        this.mTouchSpace = i;
    }

    public void setOnRatingChangedListener(a aVar) {
        this.mOnRatingChangedListener = aVar;
    }

    public RatingBar(Context context) {
        this(context, null, 0);
    }

    public RatingBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RatingBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxRating = 5;
        this.mCurrentRating = 0;
        this.mMargin = 44;
        this.mItemWidth = -2;
        this.mItemHeight = -2;
        this.mTouchDownX = 0;
        this.mTouchSpace = 20;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RatingBar, 0, i);
            this.mMaxRating = obtainStyledAttributes.getInt(R.styleable.RatingBar_maxRating, 5);
            this.mMargin = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RatingBar_margin, 44);
            this.mRatedDrawable = obtainStyledAttributes.getDrawable(R.styleable.RatingBar_ratedDrawable);
            this.mUnRatedDrawable = obtainStyledAttributes.getDrawable(R.styleable.RatingBar_unratedDrawable);
            this.mItemWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RatingBar_ratingWidth, -2);
            this.mItemHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RatingBar_ratingHeight, -2);
            obtainStyledAttributes.recycle();
        }
        init(context);
    }

    public int getRating() {
        return this.mCurrentRating;
    }

    public void setRating(int i) {
        this.mCurrentRating = i;
        setRating();
    }

    private void init(Context context) {
        this.mTouchSpace = context.getResources().getDimensionPixelSize(R.dimen.touchspace);
        for (int i = 0; i < this.mMaxRating; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ScaleType.CENTER);
            setBackground(imageView, this.mUnRatedDrawable);
            LayoutParams layoutParams = new LayoutParams(this.mItemWidth, this.mItemHeight);
            layoutParams.gravity = 17;
            layoutParams.rightMargin = this.mMargin;
            if (i == this.mMaxRating - 1) {
                layoutParams.rightMargin = 0;
            }
            addView(imageView, layoutParams);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (getChildCount() > 0) {
            this.mItemWidth = getChildAt(0).getWidth();
            this.mItemHeight = getChildAt(0).getHeight();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.mTouchDownX = x;
                break;
            case 1:
                this.mCurrentRating = findScore((float) x, (float) y);
                setRating();
                break;
            case 2:
                if (Math.abs(x - this.mTouchDownX) > this.mTouchSpace) {
                    this.mTouchDownX = x;
                    int findScore = findScore((float) x, (float) y);
                    if (findScore != this.mCurrentRating) {
                        this.mCurrentRating = findScore;
                        setRating();
                        break;
                    }
                }
                break;
        }
        return true;
    }

    private int findScore(float f, float f2) {
        if (f >= ((float) (((this.mMargin + this.mItemWidth) * 4) - (this.mMargin / 2)))) {
            return 5;
        }
        if (f >= ((float) (((this.mMargin + this.mItemWidth) * 3) - (this.mMargin / 2)))) {
            return 4;
        }
        if (f >= ((float) (((this.mMargin + this.mItemWidth) * 2) - (this.mMargin / 2)))) {
            return 3;
        }
        if (f >= ((float) ((this.mMargin / 2) + this.mItemWidth))) {
            return 2;
        }
        return 1;
    }

    private void setRating() {
        for (int i = 0; i < this.mMaxRating; i++) {
            setBackground(getChildAt(i), this.mUnRatedDrawable);
        }
        for (int i2 = 0; i2 < this.mCurrentRating; i2++) {
            setBackground(getChildAt(i2), this.mRatedDrawable);
        }
        if (this.mOnRatingChangedListener != null) {
            this.mOnRatingChangedListener.a(this.mCurrentRating);
        }
    }

    private void setBackground(View view, Drawable drawable) {
        if (VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }
}
