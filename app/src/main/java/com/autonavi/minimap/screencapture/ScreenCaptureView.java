package com.autonavi.minimap.screencapture;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.R;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScreenCaptureView extends LinearLayout {
    private String TAG = "---xing>ScreenCapture";
    private ImageView capture_btn;
    private LinearLayout capture_layout;
    private boolean isDrag;
    private int lastX;
    private int lastY;
    int mTouchSlop = 0;
    private AtomicBoolean mVideoRecording = new AtomicBoolean(false);
    private int parentHeight;
    private int parentWidth;

    public boolean isDrag() {
        return this.isDrag;
    }

    public ScreenCaptureView(Context context) {
        super(context);
        initView(context, null);
        AMapLog.i(this.TAG, "----->ScreenCaptureView(Context context)");
    }

    public ScreenCaptureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context, attributeSet);
        AMapLog.i(this.TAG, "----->ScreenCaptureView(Context context, AttributeSet attrs)");
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.screen_capture_layout, null);
        this.capture_layout = (LinearLayout) inflate.findViewById(R.id.capture_layout);
        this.capture_btn = (ImageView) inflate.findViewById(R.id.capture_btn);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        addView(inflate);
    }

    public void setScreenCapture(boolean z) {
        if (z) {
            this.mVideoRecording.set(true);
            this.capture_layout.setBackgroundResource(R.drawable.screen_capture_open_bg);
            this.capture_btn.setBackgroundResource(R.drawable.screen_capture_open_btn);
            setFlickerAnimation(this.capture_btn, 500);
            return;
        }
        this.mVideoRecording.set(false);
        this.capture_layout.setBackgroundResource(R.drawable.screen_capture_close_bg);
        this.capture_btn.setBackgroundResource(R.drawable.screen_capture_close_btn);
        removeFlickerAnimation(this.capture_btn);
    }

    public boolean isVideoRecording() {
        return this.mVideoRecording.get();
    }

    private void setFlickerAnimation(View view, long j) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(j);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setRepeatCount(-1);
        alphaAnimation.setRepeatMode(2);
        view.setAnimation(alphaAnimation);
    }

    private void removeFlickerAnimation(View view) {
        if (view != null) {
            view.clearAnimation();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        if (!isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction() & 255) {
            case 0:
                setPressed(true);
                this.isDrag = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                this.lastX = rawX;
                this.lastY = rawY;
                if (getParent() != null) {
                    ViewGroup viewGroup = (ViewGroup) getParent();
                    this.parentHeight = viewGroup.getHeight();
                    this.parentWidth = viewGroup.getWidth();
                    break;
                }
                break;
            case 1:
                setPressed(false);
                break;
            case 2:
                int i = rawX - this.lastX;
                int i2 = rawY - this.lastY;
                int sqrt = (int) Math.sqrt((double) ((i * i) + (i2 * i2)));
                if (!this.isDrag && this.parentHeight > 0 && this.parentWidth > 0 && sqrt > this.mTouchSlop) {
                    this.isDrag = true;
                }
                if (this.isDrag) {
                    float x = getX() + ((float) i);
                    float y = getY() + ((float) i2);
                    if (x < 0.0f) {
                        x = 0.0f;
                    } else if (x > ((float) (this.parentWidth - getWidth()))) {
                        x = (float) (this.parentWidth - getWidth());
                    }
                    if (getY() < 0.0f) {
                        y = 0.0f;
                    } else if (getY() + ((float) getHeight()) > ((float) this.parentHeight)) {
                        y = (float) (this.parentHeight - getHeight());
                    }
                    setX(x);
                    setY(y);
                    this.lastX = rawX;
                    this.lastY = rawY;
                    break;
                }
                break;
            case 3:
                setPressed(false);
                break;
        }
        return true;
    }

    private boolean isNotDrag() {
        return !this.isDrag && (getX() == 0.0f || getX() == ((float) (this.parentWidth - getWidth())));
    }
}
