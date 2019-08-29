package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.service.PhotoInfo;

public class CustomImageView extends ImageView {
    private float heightPercentage2Width = 0.0f;
    private a mPendingCheckForTap = null;
    private b mUnsetPressedState;
    private String picture;
    private String video;

    private final class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(CustomImageView x0, byte b) {
            this();
        }

        public final void run() {
            Drawable drawable = CustomImageView.this.getDrawable();
            if (drawable != null) {
                drawable.mutate().setColorFilter(-7829368, Mode.MULTIPLY);
            }
        }
    }

    private final class b implements Runnable {
        private b() {
        }

        /* synthetic */ b(CustomImageView x0, byte b) {
            this();
        }

        public final void run() {
            Drawable drawableUp = CustomImageView.this.getDrawable();
            if (drawableUp != null) {
                drawableUp.mutate().clearColorFilter();
            }
        }
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomImageView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.picture = context.getString(R.string.talkback_picture);
        this.video = context.getString(R.string.talkback_video);
        setContentDescription(this.picture);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (Float.compare(this.heightPercentage2Width, 0.0f) > 0) {
            setMeasuredDimension(measuredWidth, (int) (((float) measuredWidth) * this.heightPercentage2Width));
        }
    }

    public void setHeightPercentage2Width(float heightPercentage2Width2) {
        this.heightPercentage2Width = heightPercentage2Width2;
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean bool = super.onTouchEvent(event);
        switch (event.getAction()) {
            case 0:
                if (this.mPendingCheckForTap == null) {
                    this.mPendingCheckForTap = new a(this, 0);
                }
                postDelayed(this.mPendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
                break;
            case 1:
            case 3:
                removeTapCallback();
                if (this.mUnsetPressedState == null) {
                    this.mUnsetPressedState = new b(this, 0);
                }
                if (!isPressed()) {
                    this.mUnsetPressedState.run();
                    break;
                } else {
                    Drawable drawable = getDrawable();
                    if (drawable != null) {
                        drawable.mutate().setColorFilter(-7829368, Mode.MULTIPLY);
                    }
                    postDelayed(this.mUnsetPressedState, (long) ViewConfiguration.getPressedStateDuration());
                    break;
                }
            case 2:
                removeTapCallback();
                break;
        }
        return bool;
    }

    private void removeTapCallback() {
        if (this.mPendingCheckForTap != null) {
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    public void setCustomTalkback(PhotoInfo photoInfo) {
        switch (photoInfo.getMediaType()) {
            case 0:
                setContentDescription(this.picture);
                return;
            case 1:
                setContentDescription(this.video);
                return;
            default:
                setContentDescription(this.picture);
                return;
        }
    }
}
