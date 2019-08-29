package com.autonavi.minimap.ajx3.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.lang.ref.WeakReference;

public class NewRatingBar extends LinearLayout {
    private static final int RATING_IMG_EMPTY = 0;
    private static final int RATING_IMG_FILL = 2;
    private static final int RATING_IMG_HALF = 1;
    private boolean isImgDirty = false;
    private boolean isImgSizeDirty = false;
    private boolean isPaddingDirty = false;
    private boolean isRatingDirty = false;
    private AsyncAction mAction = new AsyncAction(this);
    private Drawable mEmptyImg;
    private int mEmptyImgResId;
    private Drawable mFillImg;
    private int mFillImgResId;
    private Drawable mHalfImg;
    private int mHalfImgResId;
    private int mImgHeight = 0;
    private int mImgPadding = 0;
    private int mImgWidth = 0;
    private int mMax = 5;
    private float mRating = 3.5f;
    private OnRatingBarChangeListener mRatingBarChangeListener;

    class AsyncAction implements Runnable {
        private WeakReference<NewRatingBar> mOuter;

        private AsyncAction(NewRatingBar newRatingBar) {
            this.mOuter = new WeakReference<>(newRatingBar);
        }

        public void run() {
            NewRatingBar newRatingBar = (NewRatingBar) this.mOuter.get();
            if (this.mOuter != null) {
                newRatingBar.updateLayoutImpl();
                newRatingBar.removeCallbacks(this);
            }
        }
    }

    public interface OnRatingBarChangeListener {
        void onRatingChanged(NewRatingBar newRatingBar, float f, boolean z);
    }

    public NewRatingBar(Context context) {
        super(context);
        setOrientation(0);
        this.mEmptyImgResId = R.drawable.star_null;
        this.mHalfImgResId = R.drawable.star_half;
        this.mFillImgResId = R.drawable.star_full;
        updateLayoutAsync();
    }

    public void setMax(int i) {
        if (this.mMax != i) {
            this.mMax = i;
            updateLayoutAsync();
        }
    }

    public void setRatingImages(Drawable drawable, Drawable drawable2, Drawable drawable3) {
        if (drawable != this.mFillImg || drawable2 != this.mHalfImg || drawable3 != this.mEmptyImg) {
            this.mFillImg = drawable;
            this.mHalfImg = drawable2;
            this.mEmptyImg = drawable3;
            this.isImgDirty = true;
            updateLayoutAsync();
        }
    }

    public void setRatingImages(int i, int i2, int i3) {
        if (i != this.mFillImgResId || i3 != this.mHalfImgResId || i2 != this.mEmptyImgResId) {
            this.mFillImgResId = i;
            this.mHalfImgResId = i3;
            this.mEmptyImgResId = i2;
            this.mFillImg = null;
            this.mHalfImg = null;
            this.mEmptyImg = null;
            this.isImgDirty = true;
            updateLayoutAsync();
        }
    }

    public Drawable getEmptyImage() {
        if (this.mEmptyImg != null) {
            return this.mEmptyImg;
        }
        if (this.mEmptyImgResId > 0) {
            this.mEmptyImg = getResources().getDrawable(this.mEmptyImgResId);
        }
        return this.mEmptyImg;
    }

    public Drawable getHalfImage() {
        int round = Math.round(this.mRating * 10.0f) % 10;
        boolean z = round == 5;
        Drawable realHalfImg = z ? getRealHalfImg() : null;
        if (realHalfImg == null) {
            realHalfImg = getClipImg(round);
        }
        if (realHalfImg != null && z) {
            this.mHalfImg = realHalfImg;
        }
        return realHalfImg;
    }

    private Drawable getClipImg(int i) {
        Drawable emptyImage = getEmptyImage();
        Drawable fillImage = getFillImage();
        if (!(fillImage instanceof BitmapDrawable) || !(emptyImage instanceof BitmapDrawable)) {
            return null;
        }
        Bitmap bitmap = ((BitmapDrawable) emptyImage).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable) fillImage).getBitmap();
        int ratingImgPadding = getRatingImgPadding();
        int intrinsicWidth = fillImage.getIntrinsicWidth();
        int intrinsicHeight = fillImage.getIntrinsicHeight();
        int i2 = ratingImgPadding + (((intrinsicWidth - (ratingImgPadding * 2)) * i) / 10);
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(bitmap2, new Rect(0, 0, i2, intrinsicHeight), new Rect(0, 0, i2, intrinsicHeight), null);
        canvas.save();
        canvas.restore();
        return new BitmapDrawable(getResources(), createBitmap);
    }

    private int getRatingImgPadding() {
        return DimensionUtils.dipToPixel(6.0f);
    }

    private Drawable getRealHalfImg() {
        if (this.mHalfImg != null) {
            return this.mHalfImg;
        }
        if (this.mHalfImgResId > 0) {
            this.mHalfImg = getResources().getDrawable(this.mHalfImgResId);
        }
        return this.mHalfImg;
    }

    public Drawable getFillImage() {
        if (this.mFillImg != null) {
            return this.mFillImg;
        }
        if (this.mFillImgResId > 0) {
            this.mFillImg = getResources().getDrawable(this.mFillImgResId);
        }
        return this.mFillImg;
    }

    public void setRating(float f) {
        if (((double) Math.abs(this.mRating - f)) > 0.01d) {
            this.mRating = f;
            this.isRatingDirty = true;
            updateLayoutAsync();
            if (this.mRatingBarChangeListener != null) {
                this.mRatingBarChangeListener.onRatingChanged(this, this.mRating, false);
            }
        }
    }

    public void setImagePadding(int i) {
        if (i != this.mImgPadding) {
            this.mImgPadding = i;
            this.isPaddingDirty = true;
            updateLayoutAsync();
        }
    }

    public void setImageWidth(int i) {
        if (i != this.mImgWidth) {
            this.mImgWidth = i;
            this.isImgSizeDirty = true;
            updateLayoutAsync();
        }
    }

    public void setImageHeight(int i) {
        if (i != this.mImgHeight) {
            this.mImgHeight = i;
            this.isImgSizeDirty = true;
            updateLayoutAsync();
        }
    }

    private void decorationRatingImageView(int i) {
        boolean z;
        LayoutParams layoutParams;
        ImageView imageView = (ImageView) getChildAt(i);
        if (imageView == null) {
            imageView = new ImageView(getContext());
            imageView.setTag(Integer.valueOf(i));
            imageView.setScaleType(ScaleType.FIT_XY);
            addView(imageView);
            z = true;
        } else {
            z = false;
        }
        if (z || this.isImgDirty || this.isRatingDirty) {
            switch (calculateRatingImage(i)) {
                case 0:
                    imageView.setImageDrawable(getEmptyImage());
                    break;
                case 1:
                    imageView.setImageDrawable(getHalfImage());
                    break;
                case 2:
                    imageView.setImageDrawable(getFillImage());
                    break;
            }
        }
        if (z || this.isImgSizeDirty || this.isPaddingDirty) {
            if (this.mImgHeight <= 0 || this.mImgWidth <= 0) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 19;
            } else {
                layoutParams = new LayoutParams(this.mImgWidth, this.mImgHeight);
                layoutParams.gravity = 19;
            }
            if (this.mImgPadding > 0 && i > 0) {
                layoutParams.leftMargin = this.mImgPadding;
            }
            imageView.setLayoutParams(layoutParams);
        }
    }

    private int calculateRatingImage(int i) {
        int i2 = (int) this.mRating;
        if (i < i2) {
            return 2;
        }
        return (Math.round(this.mRating * 10.0f) % 10 <= 0 || i != i2) ? 0 : 1;
    }

    private void updateLayoutAsync() {
        postDelayed(this.mAction, 16);
    }

    /* access modifiers changed from: private */
    public void updateLayoutImpl() {
        int childCount = getChildCount();
        if (this.mMax > childCount) {
            childCount = this.mMax;
        }
        for (int i = 0; i < childCount; i++) {
            if (i >= this.mMax) {
                removeViewAt(i);
            }
            decorationRatingImageView(i);
        }
        this.isPaddingDirty = false;
        this.isImgSizeDirty = false;
        this.isRatingDirty = false;
        this.isImgDirty = false;
    }

    public void setOnRatingBarChangeListener(OnRatingBarChangeListener onRatingBarChangeListener) {
        this.mRatingBarChangeListener = onRatingBarChangeListener;
    }

    private static void recycleBitmapDrawable(Drawable drawable) {
        if (drawable != null && (drawable instanceof BitmapDrawable)) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }
}
