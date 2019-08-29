package com.autonavi.minimap.ajx3.widget.view.video.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.view.video.player.PlayerManager;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;

public class FullScreenGestureView extends FrameLayout implements FullScreenGestureListener {
    private static final float BRIGHTNESS_STEP = 20.0f;
    private static final int TOTAL_PERCENT = 100;
    private static final int VIDEO_SEEK_STEP = 2000;
    private static final int VOLUME_STEP = 1;
    private float mBrightnessDistance = 0.0f;
    private int mCurrentGestureState = 0;
    private long mDuration = 0;
    private long mGestureSeekToPosition = -1;
    private int mScreenHeight;
    private int mScreenWidth;
    private float mTouchDownX;
    private float mTouchDownY;
    private int mTouchSlop = 0;
    protected ProgressBar mVideoBrightnessProgress;
    protected LinearLayout mVideoBrightnessView;
    protected ProgressBar mVideoChangeProgressBar;
    protected TextView mVideoChangeProgressCurrPro;
    protected ImageView mVideoChangeProgressIcon;
    protected TextView mVideoChangeProgressTotal;
    protected View mVideoChangeProgressView;
    protected ProgressBar mVideoVolumeProgress;
    protected LinearLayout mVideoVolumeView;
    private float mVolumeDistance = 0.0f;

    public FullScreenGestureView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FullScreenGestureView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public FullScreenGestureView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    @TargetApi(21)
    public FullScreenGestureView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context);
    }

    private void init(Context context) {
        inflate(context, getFullScreenGestureViewLayoutResId(), this);
        this.mScreenWidth = DimensionUtils.getWidthPixels();
        this.mScreenHeight = DimensionUtils.getHeightPixels();
        initWidgetView();
        initFullScreenGestureParams();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mScreenWidth = DimensionUtils.getWidthPixels();
        this.mScreenHeight = DimensionUtils.getHeightPixels();
        int maxVolume = Utils.getMaxVolume(getContext());
        int maxBrightness = Utils.getMaxBrightness();
        this.mVolumeDistance = (((float) this.mScreenHeight) / 3.0f) / ((float) maxVolume);
        this.mBrightnessDistance = (((float) this.mScreenHeight) / 3.0f) / (((float) maxBrightness) / 20.0f);
    }

    /* access modifiers changed from: protected */
    public int getFullScreenGestureViewLayoutResId() {
        return R.layout.vp_fullscreen_gesture_view;
    }

    /* access modifiers changed from: protected */
    public void initWidgetView() {
        this.mVideoVolumeView = (LinearLayout) findViewById(R.id.vp_video_volume);
        this.mVideoVolumeProgress = (ProgressBar) findViewById(R.id.vp_video_volume_progressbar);
        this.mVideoBrightnessView = (LinearLayout) findViewById(R.id.vp_video_brightness);
        this.mVideoBrightnessProgress = (ProgressBar) findViewById(R.id.vp_video_brightness_progressbar);
        this.mVideoChangeProgressView = findViewById(R.id.vp_video_change_progress_view);
        this.mVideoChangeProgressIcon = (ImageView) findViewById(R.id.vp_video_change_progress_icon);
        this.mVideoChangeProgressCurrPro = (TextView) findViewById(R.id.vp_video_change_progress_current);
        this.mVideoChangeProgressTotal = (TextView) findViewById(R.id.vp_video_change_progress_total);
        this.mVideoChangeProgressBar = (ProgressBar) findViewById(R.id.vp_video_change_progress_bar);
    }

    private void initFullScreenGestureParams() {
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        int maxVolume = Utils.getMaxVolume(getContext());
        int maxBrightness = Utils.getMaxBrightness();
        float f = (float) maxVolume;
        this.mVolumeDistance = (((float) this.mScreenHeight) / 3.0f) / f;
        float f2 = (float) maxBrightness;
        this.mBrightnessDistance = (((float) this.mScreenHeight) / 3.0f) / (f2 / 20.0f);
        this.mVideoVolumeProgress.setProgress((int) ((((((float) Utils.getCurrentVolume(getContext())) * 1.0f) / f) * 100.0f) + 0.5f));
        int systemBrightness = Utils.getSystemBrightness(getContext());
        LayoutParams attributes = ((Activity) getContext()).getWindow().getAttributes();
        attributes.screenBrightness = (((float) systemBrightness) * 1.0f) / f2;
        this.mVideoBrightnessProgress.setProgress((int) (attributes.screenBrightness * 100.0f));
    }

    public void onTouch(MotionEvent motionEvent, FullScreenGestureStateListener fullScreenGestureStateListener, long j, int i) {
        this.mDuration = j;
        switch (motionEvent.getAction()) {
            case 0:
                this.mTouchDownX = motionEvent.getRawX();
                this.mTouchDownY = motionEvent.getRawY();
                return;
            case 1:
                switch (this.mCurrentGestureState) {
                    case 1:
                        if (this.mGestureSeekToPosition != -1) {
                            PlayerManager.getInstance().seekTo(this.mGestureSeekToPosition);
                            this.mGestureSeekToPosition = -1;
                            Utils.hideViewIfNeed(this.mVideoChangeProgressView);
                            fullScreenGestureStateListener.onFullScreenGestureFinish();
                            return;
                        }
                        break;
                    case 2:
                        Utils.hideViewIfNeed(this.mVideoVolumeView);
                        return;
                    case 3:
                        Utils.hideViewIfNeed(this.mVideoBrightnessView);
                        break;
                }
            case 2:
                if (i == 2 || i == 5) {
                    float abs = Math.abs(this.mTouchDownX - motionEvent.getRawX());
                    float abs2 = Math.abs(motionEvent.getRawY() - this.mTouchDownY);
                    StringBuilder sb = new StringBuilder("TouchSlop:");
                    sb.append(this.mTouchSlop);
                    sb.append(", xDis:");
                    sb.append(abs);
                    sb.append(", yDis:");
                    sb.append(abs2);
                    Utils.logTouch(sb.toString());
                    boolean z = false;
                    if (isFlingLeft(this.mTouchDownX, this.mTouchDownY, motionEvent)) {
                        fullScreenGestureStateListener.onFullScreenGestureStart();
                        Utils.logTouch("Fling Left");
                        videoSeek(false);
                        this.mTouchDownX = motionEvent.getRawX();
                        this.mTouchDownY = motionEvent.getRawY();
                        return;
                    } else if (isFlingRight(this.mTouchDownX, this.mTouchDownY, motionEvent)) {
                        fullScreenGestureStateListener.onFullScreenGestureStart();
                        Utils.logTouch("Fling Right");
                        videoSeek(true);
                        this.mTouchDownX = motionEvent.getRawX();
                        this.mTouchDownY = motionEvent.getRawY();
                        return;
                    } else if (isScrollVertical(this.mTouchDownX, this.mTouchDownY, motionEvent)) {
                        fullScreenGestureStateListener.onFullScreenGestureStart();
                        if (isScrollVerticalRight(this.mTouchDownX, motionEvent)) {
                            Utils.logTouch("isScrollVerticalRight");
                            if (Math.abs(motionEvent.getRawY() - this.mTouchDownY) >= this.mVolumeDistance) {
                                if (motionEvent.getRawY() < this.mTouchDownY) {
                                    z = true;
                                }
                                changeVideoVolume(z);
                                this.mTouchDownX = motionEvent.getRawX();
                                this.mTouchDownY = motionEvent.getRawY();
                                return;
                            }
                        } else if (isScrollVerticalLeft(this.mTouchDownX, motionEvent)) {
                            Utils.logTouch("isScrollVerticalLeft");
                            if (Math.abs(motionEvent.getRawY() - this.mTouchDownY) >= this.mBrightnessDistance) {
                                if (motionEvent.getRawY() < this.mTouchDownY) {
                                    z = true;
                                }
                                changeBrightness(z);
                                this.mTouchDownX = motionEvent.getRawX();
                                this.mTouchDownY = motionEvent.getRawY();
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    /* access modifiers changed from: protected */
    public void changeVideoVolume(boolean z) {
        this.mCurrentGestureState = 2;
        updateVolumeView((int) ((((((double) Utils.changeVolume(getContext(), z ? 1 : -1)) * 1.0d) / ((double) Utils.getMaxVolume(getContext()))) * 100.0d) + 0.5d));
    }

    /* access modifiers changed from: protected */
    public void updateVolumeView(int i) {
        Utils.showViewIfNeed(this.mVideoVolumeView);
        this.mVideoVolumeProgress.setProgress(i);
    }

    /* access modifiers changed from: protected */
    public void changeBrightness(boolean z) {
        this.mCurrentGestureState = 3;
        updateBrightnessView((int) (Utils.changeBrightness(((Activity) getContext()).getWindow(), z ? 20.0f : -20.0f) * 100.0f));
    }

    /* access modifiers changed from: protected */
    public void updateBrightnessView(int i) {
        Utils.showViewIfNeed(this.mVideoBrightnessView);
        this.mVideoBrightnessProgress.setProgress(i);
    }

    /* access modifiers changed from: protected */
    public void videoSeek(boolean z) {
        this.mCurrentGestureState = 1;
        if (this.mGestureSeekToPosition == -1) {
            this.mGestureSeekToPosition = PlayerManager.getInstance().getCurrentPosition();
        }
        if (z) {
            this.mVideoChangeProgressIcon.setImageResource(R.drawable.vp_fast_forward);
            this.mGestureSeekToPosition = this.mGestureSeekToPosition + 2000 >= this.mDuration ? this.mDuration : 2000 + this.mGestureSeekToPosition;
        } else {
            this.mVideoChangeProgressIcon.setImageResource(R.drawable.vp_fast_back);
            long j = 0;
            if (this.mGestureSeekToPosition - 2000 > 0) {
                j = this.mGestureSeekToPosition - 2000;
            }
            this.mGestureSeekToPosition = j;
        }
        String formatVideoTimeLength = Utils.formatVideoTimeLength(this.mGestureSeekToPosition);
        StringBuilder sb = new StringBuilder("/");
        sb.append(Utils.formatVideoTimeLength(this.mDuration));
        updateSeekView(formatVideoTimeLength, sb.toString(), (int) ((((((float) this.mGestureSeekToPosition) * 1.0f) / ((float) this.mDuration)) * 100.0f) + 0.5f));
    }

    /* access modifiers changed from: protected */
    public void updateSeekView(String str, String str2, int i) {
        Utils.showViewIfNeed(this.mVideoChangeProgressView);
        this.mVideoChangeProgressCurrPro.setText(str);
        this.mVideoChangeProgressTotal.setText(str2);
        this.mVideoChangeProgressBar.setProgress(i);
    }

    private boolean isFlingRight(float f, float f2, MotionEvent motionEvent) {
        return motionEvent.getRawX() - f > ((float) this.mTouchSlop) && Math.abs(motionEvent.getRawY() - f2) < ((float) this.mTouchSlop);
    }

    private boolean isFlingLeft(float f, float f2, MotionEvent motionEvent) {
        return f - motionEvent.getRawX() > ((float) this.mTouchSlop) && Math.abs(motionEvent.getRawY() - f2) < ((float) this.mTouchSlop);
    }

    private boolean isScrollVertical(float f, float f2, MotionEvent motionEvent) {
        return Math.abs(motionEvent.getRawX() - f) < ((float) this.mTouchSlop) && Math.abs(motionEvent.getRawY() - f2) > ((float) this.mTouchSlop);
    }

    private boolean isScrollVerticalRight(float f, MotionEvent motionEvent) {
        return f > ((float) (this.mScreenWidth / 2)) && motionEvent.getRawX() > ((float) (this.mScreenWidth / 2));
    }

    private boolean isScrollVerticalLeft(float f, MotionEvent motionEvent) {
        return f < ((float) (this.mScreenWidth / 2)) && motionEvent.getRawX() < ((float) (this.mScreenWidth / 2));
    }
}
