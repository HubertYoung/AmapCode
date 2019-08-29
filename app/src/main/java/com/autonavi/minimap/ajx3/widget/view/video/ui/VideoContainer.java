package com.autonavi.minimap.ajx3.widget.view.video.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class VideoContainer extends RelativeLayout implements OnClickListener, OnTouchListener, FullScreenGestureStateListener, FullScreenToggleListener, UIStateChangeListener, VideoTouchListener {
    boolean inFirstFrameRendered;
    protected boolean isVideoThumbEnable;
    protected int mAutoDismissTime;
    protected ScheduledExecutorService mDismissControllerViewTimer;
    protected DismissControllerViewTimerTask mDismissControllerViewTimerTask;
    protected FullScreenGestureView mFullScreenGestureView;
    private int mFullScreenGestureViewRes;
    protected boolean mFullScreenLocked;
    protected boolean mHideControllerView;
    protected VideoControllerView mVideoControllerView;
    private int mVideoControllerViewRes;
    protected View mVideoErrorView;
    private int mVideoErrorViewRes;
    protected ImageView mVideoFullScreenLockView;
    protected VideoHeaderView mVideoHeaderView;
    private int mVideoHeaderViewRes;
    protected ProgressBar mVideoLoadingBar;
    protected ImageView mVideoPlayButton;
    protected ImageView mVideoSmallWindowBackView;
    protected FrameLayout mVideoTextureViewContainer;
    protected String mVideoThumbUrl;
    protected ImageView mVideoThumbView;
    private int mVideoThumbViewRes;
    protected CharSequence mVideoTitle;
    protected String mVideoUrl;

    public class DismissControllerViewTimerTask implements Runnable {
        public DismissControllerViewTimerTask() {
        }

        public void run() {
            if (VideoContainer.this.getCurrentState() == 2 && VideoContainer.this.getContext() != null && (VideoContainer.this.getContext() instanceof Activity)) {
                ((Activity) VideoContainer.this.getContext()).runOnUiThread(new Runnable() {
                    public void run() {
                        VideoContainer.this.hideAllPlayStateView();
                    }
                });
            }
        }
    }

    public abstract int getCurrentScreenState();

    public abstract int getCurrentState();

    /* access modifiers changed from: protected */
    public int getViewHash() {
        return 0;
    }

    public VideoContainer(Context context) {
        this(context, null);
    }

    public VideoContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mVideoControllerViewRes = -1;
        this.mVideoThumbViewRes = -1;
        this.mFullScreenGestureViewRes = -1;
        this.mVideoErrorViewRes = -1;
        this.mVideoHeaderViewRes = -1;
        this.mAutoDismissTime = 2000;
        this.mFullScreenLocked = false;
        this.mHideControllerView = false;
        this.inFirstFrameRendered = false;
        initView(context, attributeSet);
    }

    public VideoContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVideoControllerViewRes = -1;
        this.mVideoThumbViewRes = -1;
        this.mFullScreenGestureViewRes = -1;
        this.mVideoErrorViewRes = -1;
        this.mVideoHeaderViewRes = -1;
        this.mAutoDismissTime = 2000;
        this.mFullScreenLocked = false;
        this.mHideControllerView = false;
        this.inFirstFrameRendered = false;
        initView(context, attributeSet);
    }

    @TargetApi(21)
    public VideoContainer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mVideoControllerViewRes = -1;
        this.mVideoThumbViewRes = -1;
        this.mFullScreenGestureViewRes = -1;
        this.mVideoErrorViewRes = -1;
        this.mVideoHeaderViewRes = -1;
        this.mAutoDismissTime = 2000;
        this.mFullScreenLocked = false;
        this.mHideControllerView = false;
        this.inFirstFrameRendered = false;
        initView(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void initView(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            initCustomViewAttributes(context, attributeSet);
        }
        inflate(context, getPlayerLayoutId(), this);
        setDescendantFocusability(393216);
        findAndBindView();
        initVideoThumbView();
        initVideoControllerView();
        initVideoPlayErrorView();
        initVideoHeaderView();
        bindViewListener();
    }

    /* access modifiers changed from: protected */
    public int getPlayerLayoutId() {
        return R.layout.vp_layout;
    }

    private void initCustomViewAttributes(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VideoPlayerView, 0, 0);
        this.mFullScreenGestureViewRes = obtainStyledAttributes.getResourceId(R.styleable.VideoPlayerView_vpFullScreenGestureViewLayoutRes, -1);
        this.mVideoThumbViewRes = obtainStyledAttributes.getResourceId(R.styleable.VideoPlayerView_vpVideoThumbViewLayoutRes, -1);
        this.mVideoControllerViewRes = obtainStyledAttributes.getResourceId(R.styleable.VideoPlayerView_vpVideoControllerViewLayoutRes, -1);
        this.mVideoErrorViewRes = obtainStyledAttributes.getResourceId(R.styleable.VideoPlayerView_vpVideoErrorViewLayoutRes, -1);
        this.mVideoHeaderViewRes = obtainStyledAttributes.getResourceId(R.styleable.VideoPlayerView_vpVideoHeaderViewLayoutRes, -1);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void findAndBindView() {
        this.mVideoTextureViewContainer = (FrameLayout) findViewById(R.id.vp_video_surface_container);
        this.mVideoLoadingBar = (ProgressBar) findViewById(R.id.vp_video_loading);
        this.mVideoPlayButton = (ImageView) findViewById(R.id.vp_video_play);
        this.mVideoSmallWindowBackView = (ImageView) findViewById(R.id.vp_video_small_window_back);
        this.mVideoFullScreenLockView = (ImageView) findViewById(R.id.vp_fullscreen_lock);
    }

    /* access modifiers changed from: protected */
    public void bindViewListener() {
        this.mVideoPlayButton.setOnClickListener(this);
        this.mVideoThumbView.setOnClickListener(this);
        this.mVideoTextureViewContainer.setOnClickListener(this);
        this.mVideoTextureViewContainer.setOnTouchListener(this);
        this.mVideoErrorView.setOnClickListener(this);
        this.mVideoControllerView.setOnTouchListener(this);
        this.mVideoSmallWindowBackView.setOnClickListener(this);
        this.mVideoFullScreenLockView.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void initVideoThumbView() {
        if (this.mVideoThumbView == null) {
            if (this.mVideoThumbViewRes == -1) {
                this.mVideoThumbView = new ImageView(getContext());
                this.mVideoThumbView.setBackgroundResource(17170444);
                this.mVideoThumbView.setScaleType(ScaleType.FIT_CENTER);
            } else {
                this.mVideoThumbView = (ImageView) inflate(getContext(), this.mVideoThumbViewRes, null);
            }
            addView(this.mVideoThumbView, 1, new LayoutParams(-1, -1));
        }
    }

    public void hideControllerView() {
        if (!this.mHideControllerView) {
            Utils.hideViewIfNeed(this.mVideoControllerView);
            this.mHideControllerView = true;
        }
    }

    public void showControllerView() {
        if (this.mHideControllerView) {
            this.mHideControllerView = false;
        }
    }

    public void setThumbDrawable(Drawable drawable) {
        if (this.mVideoThumbView != null) {
            this.mVideoThumbView.setImageDrawable(drawable);
            this.mVideoThumbView.setBackgroundDrawable(null);
            this.isVideoThumbEnable = drawable != null;
        }
    }

    public void setThumbBitmap(Bitmap bitmap) {
        if (this.mVideoThumbView != null) {
            this.mVideoThumbView.setImageBitmap(bitmap);
            this.mVideoThumbView.setBackgroundDrawable(null);
            this.isVideoThumbEnable = bitmap != null;
        }
    }

    public void setThumbColor(@ColorInt int i) {
        if (this.mVideoThumbView != null) {
            this.mVideoThumbView.setBackgroundColor(i);
            this.isVideoThumbEnable = i != 0;
        }
    }

    public void setThumbUrl(String str, Drawable drawable) {
        if (this.mVideoThumbView != null) {
            this.mVideoThumbUrl = str;
            if (drawable != null) {
                this.mVideoThumbView.setImageDrawable(drawable);
            }
            boolean z = true;
            Picasso.with(getContext()).load(this.mVideoThumbUrl).fastMode(true).into(this.mVideoThumbView);
            this.mVideoThumbView.setBackgroundDrawable(null);
            if (this.mVideoThumbUrl == null) {
                z = false;
            }
            this.isVideoThumbEnable = z;
        }
    }

    public void setThumbUrl(String str, Bitmap bitmap) {
        if (this.mVideoThumbView != null) {
            this.mVideoThumbUrl = str;
            if (bitmap != null) {
                this.mVideoThumbView.setImageBitmap(bitmap);
            }
            Picasso.with(getContext()).load(this.mVideoThumbUrl).into(this.mVideoThumbView);
            this.mVideoThumbView.setBackgroundDrawable(null);
            this.isVideoThumbEnable = this.mVideoThumbUrl != str;
        }
    }

    /* access modifiers changed from: protected */
    public boolean needHideThumbBeforeFirstFrame() {
        return this.inFirstFrameRendered || !this.isVideoThumbEnable;
    }

    /* access modifiers changed from: protected */
    public void initVideoControllerView() {
        if (this.mVideoControllerView == null) {
            if (this.mVideoControllerViewRes == -1) {
                this.mVideoControllerView = new VideoControllerView(getContext());
            } else {
                this.mVideoControllerView = (VideoControllerView) inflate(getContext(), this.mVideoControllerViewRes, null);
            }
            this.mVideoControllerView.setFullScreenToggleListener(this);
            this.mVideoControllerView.setCurrentScreenState(getCurrentScreenState());
            addView(this.mVideoControllerView, 3, new LayoutParams(-1, -2));
        }
    }

    /* access modifiers changed from: protected */
    public void initFullScreenGestureView() {
        if (this.mFullScreenGestureView == null) {
            if (this.mFullScreenGestureViewRes != -1) {
                this.mFullScreenGestureView = (FullScreenGestureView) inflate(getContext(), this.mFullScreenGestureViewRes, null);
            } else {
                this.mFullScreenGestureView = new FullScreenGestureView(getContext());
            }
        }
        addView(this.mFullScreenGestureView, 2, new LayoutParams(-1, -1));
    }

    /* access modifiers changed from: protected */
    public void clearFullScreenGestureView() {
        if (this.mFullScreenGestureView != null) {
            ViewGroup viewGroup = (ViewGroup) this.mFullScreenGestureView.getParent();
            if (!(viewGroup == null || viewGroup.indexOfChild(this.mFullScreenGestureView) == -1)) {
                viewGroup.removeView(this.mFullScreenGestureView);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initVideoPlayErrorView() {
        if (this.mVideoErrorView == null) {
            if (this.mVideoErrorViewRes == -1) {
                this.mVideoErrorViewRes = R.layout.vp_layout_play_error;
            }
            this.mVideoErrorView = inflate(getContext(), this.mVideoErrorViewRes, null);
            this.mVideoErrorView.setOnClickListener(this);
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.addRule(13);
            addView(this.mVideoErrorView, indexOfChild(this.mVideoPlayButton) + 1, layoutParams);
        }
    }

    /* access modifiers changed from: protected */
    public void initVideoHeaderView() {
        if (this.mVideoHeaderView == null) {
            if (this.mVideoHeaderViewRes == -1) {
                this.mVideoHeaderView = new VideoHeaderView(getContext());
            } else {
                this.mVideoHeaderView = (VideoHeaderView) inflate(getContext(), this.mVideoHeaderViewRes, null);
            }
            this.mVideoHeaderView.setFullScreenToggleListener(this);
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            layoutParams.addRule(10);
            addView(this.mVideoHeaderView, indexOfChild(this.mVideoSmallWindowBackView) + 1, layoutParams);
        }
    }

    public void bind(String str, CharSequence charSequence, boolean z) {
        this.mVideoHeaderView.mNormalStateShowTitle = z;
        this.mVideoTitle = charSequence;
        this.mVideoUrl = str;
        this.mVideoHeaderView.setTitle(this.mVideoTitle);
    }

    public void bind(String str, CharSequence charSequence) {
        bind(str, charSequence, this.mVideoHeaderView.mNormalStateShowTitle);
    }

    public void bind(String str) {
        bind(str, null);
    }

    public TextureView createTextureView() {
        TextureView newTextureView = newTextureView();
        newTextureView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
        return newTextureView;
    }

    /* access modifiers changed from: protected */
    public TextureView newTextureView() {
        return new TextureView(getContext());
    }

    public ImageView getThumbImageView() {
        return this.mVideoThumbView;
    }

    /* access modifiers changed from: protected */
    public void onToggleFullScreenLockState(boolean z) {
        this.mFullScreenLocked = z;
        if (this.mFullScreenLocked) {
            this.mVideoFullScreenLockView.setImageResource(R.drawable.vp_fullscreen_lock);
            hideAllPlayStateViewExcludeLockView();
            startDismissControllerViewTimer();
            return;
        }
        this.mVideoFullScreenLockView.setImageResource(R.drawable.vp_fullscreen_unlocked);
        showAllPlayStateView();
        startDismissControllerViewTimer();
    }

    public void hideAllPlayStateView() {
        Utils.hideViewIfNeed(this.mVideoFullScreenLockView);
        hideAllPlayStateViewExcludeLockView();
    }

    public void showAllPlayStateView() {
        if (!this.mHideControllerView) {
            startDismissControllerViewTimer();
            if (ScreenState.isFullScreen(getCurrentScreenState())) {
                Utils.showViewIfNeed(this.mVideoFullScreenLockView);
            } else {
                Utils.hideViewIfNeed(this.mVideoFullScreenLockView);
            }
            Utils.showViewIfNeed(this.mVideoPlayButton);
            this.mVideoControllerView.showAllPlayStateView();
            onChangeVideoHeaderViewState(true);
        }
    }

    public void hideAllPlayStateViewExcludeLockView() {
        Utils.hideViewIfNeed(this.mVideoPlayButton);
        if (!this.mHideControllerView) {
            this.mVideoControllerView.hideAllPlayStateView();
            onChangeVideoHeaderViewState(false);
        }
        cancelDismissControllerViewTimer();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (ScreenState.isSmallWindow(getCurrentScreenState()) || this.mHideControllerView) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                cancelDismissControllerViewTimer();
                break;
            case 1:
                onChangeUIWhenTouchVideoView();
                break;
        }
        if (!this.mFullScreenLocked && this.mFullScreenGestureView != null) {
            this.mFullScreenGestureView.onTouch(motionEvent, this, this.mVideoControllerView.getDuration(), getCurrentState());
        }
        return false;
    }

    public void onFullScreenGestureStart() {
        hideAllPlayStateView();
    }

    public void onFullScreenGestureFinish() {
        showAllPlayStateView();
    }

    /* access modifiers changed from: protected */
    public void onChangeVideoHeaderViewState(boolean z) {
        this.mVideoHeaderView.onChangeVideoHeaderViewState(getCurrentScreenState(), z);
        if (z && ScreenState.isFullScreen(getCurrentScreenState())) {
            Utils.showViewIfNeed(this.mVideoFullScreenLockView);
        }
    }

    public void onChangeUINormalState(int i) {
        this.inFirstFrameRendered = false;
        Utils.hideViewIfNeed(this.mVideoLoadingBar);
        Utils.hideViewIfNeed(this.mVideoErrorView);
        if (ScreenState.isSmallWindow(i)) {
            Utils.showViewIfNeed(this.mVideoSmallWindowBackView);
        } else if (ScreenState.isFullScreen(i)) {
            Utils.hideViewIfNeed(this.mVideoSmallWindowBackView);
        }
        Utils.hideViewIfNeed(this.mVideoPlayButton);
        Utils.showViewIfNeed(this.mVideoThumbView);
        if (!this.mHideControllerView) {
            this.mVideoPlayButton.setImageResource(R.drawable.vp_play_selector);
            Utils.showViewIfNeed(this.mVideoPlayButton);
            this.mVideoControllerView.onChangeUINormalState(i);
            onChangeVideoHeaderViewState(true);
        }
    }

    public void onChangeUILoadingState(int i) {
        Utils.showViewIfNeed(this.mVideoLoadingBar);
        Utils.hideViewIfNeed(this.mVideoErrorView);
        if (ScreenState.isSmallWindow(i)) {
            Utils.showViewIfNeed(this.mVideoSmallWindowBackView);
        } else {
            Utils.hideViewIfNeed(this.mVideoSmallWindowBackView);
        }
        Utils.showViewIfNeed(this.mVideoThumbView);
        if (!this.mHideControllerView) {
            Utils.hideViewIfNeed(this.mVideoPlayButton);
            this.mVideoControllerView.onChangeUILoadingState(i);
            onChangeVideoHeaderViewState(false);
        }
    }

    public void onChangeUIFirstFrameRendering(int i) {
        Utils.hideViewIfNeed(this.mVideoLoadingBar);
        Utils.hideViewIfNeed(this.mVideoErrorView);
        Utils.hideViewIfNeed(this.mVideoThumbView);
        this.inFirstFrameRendered = true;
        if (!this.mHideControllerView) {
            if (ScreenState.isSmallWindow(i)) {
                cancelDismissControllerViewTimer();
                Utils.hideViewIfNeed(this.mVideoPlayButton);
                Utils.showViewIfNeed(this.mVideoSmallWindowBackView);
            } else {
                this.mVideoPlayButton.setImageResource(R.drawable.vp_pause_selector);
                Utils.hideViewIfNeed(this.mVideoPlayButton);
                Utils.hideViewIfNeed(this.mVideoSmallWindowBackView);
            }
            this.mVideoControllerView.onChangeUIFirstFrameRendering(i);
            onChangeVideoHeaderViewState(true);
        }
    }

    public void onChangeUIPlayingState(int i) {
        Utils.hideViewIfNeed(this.mVideoLoadingBar);
        Utils.hideViewIfNeed(this.mVideoErrorView);
        if (needHideThumbBeforeFirstFrame()) {
            Utils.hideViewIfNeed(this.mVideoThumbView);
        } else {
            Utils.showViewIfNeed(this.mVideoThumbView);
        }
        if (!this.mHideControllerView) {
            if (ScreenState.isSmallWindow(i)) {
                cancelDismissControllerViewTimer();
                Utils.hideViewIfNeed(this.mVideoPlayButton);
                Utils.showViewIfNeed(this.mVideoSmallWindowBackView);
            } else {
                startDismissControllerViewTimer();
                this.mVideoPlayButton.setImageResource(R.drawable.vp_pause_selector);
                Utils.hideViewIfNeed(this.mVideoPlayButton);
                Utils.hideViewIfNeed(this.mVideoSmallWindowBackView);
            }
            this.mVideoControllerView.onChangeUIPlayingState(i);
            onChangeVideoHeaderViewState(true);
        }
    }

    public void onChangeUIPauseState(int i) {
        Utils.hideViewIfNeed(this.mVideoLoadingBar);
        Utils.hideViewIfNeed(this.mVideoErrorView);
        cancelDismissControllerViewTimer();
        Utils.hideViewIfNeed(this.mVideoThumbView);
        if (!this.mHideControllerView) {
            if (ScreenState.isSmallWindow(i)) {
                Utils.showViewIfNeed(this.mVideoSmallWindowBackView);
                Utils.hideViewIfNeed(this.mVideoPlayButton);
            } else {
                this.mVideoPlayButton.setImageResource(R.drawable.vp_play_selector);
                Utils.showViewIfNeed(this.mVideoPlayButton);
                Utils.hideViewIfNeed(this.mVideoSmallWindowBackView);
            }
            this.mVideoControllerView.onChangeUIPauseState(i);
            onChangeVideoHeaderViewState(true);
        }
    }

    public void onChangeUIBufferingState(int i) {
        Utils.showViewIfNeed(this.mVideoLoadingBar);
        Utils.hideViewIfNeed(this.mVideoPlayButton);
        Utils.hideViewIfNeed(this.mVideoErrorView);
        if (needHideThumbBeforeFirstFrame()) {
            Utils.hideViewIfNeed(this.mVideoThumbView);
        } else {
            Utils.showViewIfNeed(this.mVideoThumbView);
        }
        if (!this.mHideControllerView) {
            if (ScreenState.isSmallWindow(i)) {
                cancelDismissControllerViewTimer();
                Utils.showViewIfNeed(this.mVideoSmallWindowBackView);
            } else {
                cancelDismissControllerViewTimer();
                Utils.hideViewIfNeed(this.mVideoSmallWindowBackView);
            }
            this.mVideoControllerView.onChangeUIBufferingState(i);
            onChangeVideoHeaderViewState(false);
        }
    }

    public void onChangeUIBufferCompletedState(int i) {
        Utils.hideViewIfNeed(this.mVideoLoadingBar);
        Utils.hideViewIfNeed(this.mVideoPlayButton);
        Utils.hideViewIfNeed(this.mVideoErrorView);
        Utils.hideViewIfNeed(this.mVideoThumbView);
        if (!this.mHideControllerView) {
            if (ScreenState.isSmallWindow(i)) {
                cancelDismissControllerViewTimer();
                Utils.showViewIfNeed(this.mVideoSmallWindowBackView);
            } else {
                cancelDismissControllerViewTimer();
                Utils.hideViewIfNeed(this.mVideoSmallWindowBackView);
            }
            this.mVideoControllerView.onChangeUIBufferingState(i);
            onChangeVideoHeaderViewState(false);
        }
    }

    public void onChangeUICompleteState(int i) {
        Utils.hideViewIfNeed(this.mVideoLoadingBar);
        Utils.hideViewIfNeed(this.mVideoErrorView);
        Utils.showViewIfNeed(this.mVideoThumbView);
        if (!this.mHideControllerView) {
            this.mVideoPlayButton.setImageResource(R.drawable.vp_replay_selector);
            Utils.showViewIfNeed(this.mVideoPlayButton);
            cancelDismissControllerViewTimer();
            if (ScreenState.isSmallWindow(i)) {
                Utils.showViewIfNeed(this.mVideoSmallWindowBackView);
            } else {
                Utils.hideViewIfNeed(this.mVideoSmallWindowBackView);
            }
            this.mVideoControllerView.onChangeUICompleteState(i);
            onChangeVideoHeaderViewState(true);
        }
        Utils.hideViewIfNeed(this.mVideoFullScreenLockView);
    }

    public void onChangeUIErrorState(int i) {
        this.inFirstFrameRendered = false;
        Utils.hideViewIfNeed(this.mVideoLoadingBar);
        Utils.showViewIfNeed(this.mVideoErrorView);
        Utils.hideViewIfNeed(this.mVideoThumbView);
        if (!this.mHideControllerView) {
            Utils.hideViewIfNeed(this.mVideoPlayButton);
            cancelDismissControllerViewTimer();
            if (ScreenState.isSmallWindow(i)) {
                Utils.showViewIfNeed(this.mVideoSmallWindowBackView);
            } else {
                Utils.hideViewIfNeed(this.mVideoSmallWindowBackView);
            }
            this.mVideoControllerView.onChangeUIErrorState(i);
            onChangeVideoHeaderViewState(false);
        }
    }

    public void onChangeUIWhenTouchVideoView() {
        if (getCurrentState() == 2) {
            if (!this.mFullScreenLocked) {
                if (Utils.isViewShown(this.mVideoPlayButton) && Utils.isViewShown(this.mVideoControllerView)) {
                    hideAllPlayStateView();
                } else {
                    showAllPlayStateView();
                }
            } else if (Utils.isViewShown(this.mVideoFullScreenLockView)) {
                cancelDismissControllerViewTimer();
                Utils.hideViewIfNeed(this.mVideoFullScreenLockView);
            } else {
                Utils.showViewIfNeed(this.mVideoFullScreenLockView);
                startDismissControllerViewTimer();
            }
        }
    }

    public void startDismissControllerViewTimer() {
        cancelDismissControllerViewTimer();
        this.mDismissControllerViewTimer = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            public Thread newThread(@NonNull Runnable runnable) {
                return new Thread("Thread-Dismiss-Controller");
            }
        });
        this.mDismissControllerViewTimerTask = new DismissControllerViewTimerTask();
        this.mDismissControllerViewTimer.schedule(this.mDismissControllerViewTimerTask, (long) this.mAutoDismissTime, TimeUnit.MICROSECONDS);
    }

    public void cancelDismissControllerViewTimer() {
        if (this.mDismissControllerViewTimer != null) {
            this.mDismissControllerViewTimer.shutdown();
        }
    }
}
