package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.service.PhotoInfo;

public abstract class GridCustomView extends FrameLayout {
    private int mDis = 15;
    private a mPendingCheckForTap = null;
    private b mUnsetPressedState;
    private boolean needRemove = false;
    private String picture;
    private float start_X;
    private float start_Y;
    private String video;

    private final class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(GridCustomView x0, byte b) {
            this();
        }

        public final void run() {
            Drawable drawable = GridCustomView.this.getDrawable();
            if (drawable != null) {
                drawable.mutate().setColorFilter(-7829368, Mode.MULTIPLY);
            }
        }
    }

    private final class b implements Runnable {
        private b() {
        }

        /* synthetic */ b(GridCustomView x0, byte b) {
            this();
        }

        public final void run() {
            Drawable drawableUp = GridCustomView.this.getDrawable();
            if (drawableUp != null) {
                drawableUp.mutate().clearColorFilter();
            }
        }
    }

    public abstract void clear();

    public abstract Drawable getDrawable();

    public abstract void inflateView(Context context);

    public abstract void setScaleType(ScaleType scaleType);

    public GridCustomView(Context context) {
        super(context);
        init(context);
    }

    public GridCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GridCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /* access modifiers changed from: protected */
    public void init(Context context) {
        resetStartPoint();
        this.mDis = dip2px(context, (float) this.mDis);
        this.picture = context.getString(R.string.talkback_picture);
        this.video = context.getString(R.string.talkback_video);
        inflateView(context);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean bool = super.onTouchEvent(event);
        switch (event.getAction()) {
            case 0:
                if (this.mPendingCheckForTap == null) {
                    this.mPendingCheckForTap = new a(this, 0);
                }
                postDelayed(this.mPendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
                this.start_X = event.getX();
                this.start_Y = event.getY();
                this.needRemove = true;
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
                if (PointF.length(event.getX() - this.start_X, event.getY() - this.start_Y) > ((float) this.mDis)) {
                    removeTapCallback();
                    break;
                }
                break;
        }
        return bool;
    }

    private void removeTapCallback() {
        if (this.needRemove && this.mPendingCheckForTap != null) {
            removeCallbacks(this.mPendingCheckForTap);
            resetStartPoint();
            this.needRemove = false;
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

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(0, 0, right - left, bottom - top);
            LayoutParams params = (LayoutParams) getChildAt(i).getLayoutParams();
            params.width = right - left;
            params.height = bottom - top;
            getChildAt(i).setLayoutParams(params);
        }
    }

    private void resetStartPoint() {
        this.start_X = 0.0f;
        this.start_Y = 0.0f;
    }

    private int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
