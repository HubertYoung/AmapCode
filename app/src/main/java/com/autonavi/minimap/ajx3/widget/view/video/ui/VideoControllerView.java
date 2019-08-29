package com.autonavi.minimap.ajx3.widget.view.video.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.widget.view.video.player.PlayerManager;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;

public class VideoControllerView extends RelativeLayout implements OnClickListener, OnSeekBarChangeListener, UIStateChangeListener, VideoControllerViewListener, VideoTouchListener {
    private static final int PROGRESS_UPDATE_INITIAL_INTERVAL = 100;
    private static final int PROGRESS_UPDATE_INTERNAL = 300;
    protected ProgressBar mBottomProgressBar;
    protected int mCurrentScreenState = 1;
    private long mDuration = 0;
    private int mFullScreenButtonRes = R.drawable.vp_fullscreen;
    private FullScreenToggleListener mFullScreenToggleListener;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private OnProcessUpdatedListener mProcessUpdatedListener;
    private final Runnable mUpdateProgressTask = new Runnable() {
        public void run() {
            if (PlayerManager.getInstance().isPlaying()) {
                VideoControllerView.this.updateProgress(PlayerManager.getInstance().getCurrentPosition());
                VideoControllerView.this.mHandler.postDelayed(this, 300);
            }
        }
    };
    protected View mVideoControllerInternalView;
    protected ImageView mVideoFullScreenButton;
    protected SeekBar mVideoPlaySeekBar;
    protected TextView mVideoPlayTimeView;
    protected TextView mVideoTotalTimeView;

    public interface OnProcessUpdatedListener {
        void onProcessUpdated(long j);
    }

    public void destroy() {
    }

    public void onChangeUIBufferCompletedState(int i) {
    }

    public void onChangeUIFirstFrameRendering(int i) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public VideoControllerView(Context context) {
        super(context);
        loadLayoutRes(context);
        initHandlerThread();
    }

    public VideoControllerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        loadLayoutRes(context);
        initHandlerThread();
    }

    public VideoControllerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        loadLayoutRes(context);
        initHandlerThread();
    }

    @TargetApi(21)
    public VideoControllerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        loadLayoutRes(context);
        initHandlerThread();
    }

    private void loadLayoutRes(Context context) {
        inflate(context, getControllerViewLayoutResId(), this);
        setVisibility(8);
        initWidgetView();
    }

    private void initHandlerThread() {
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public void cloneState(VideoControllerView videoControllerView) {
        this.mCurrentScreenState = videoControllerView.mCurrentScreenState;
        this.mDuration = videoControllerView.mDuration;
    }

    /* access modifiers changed from: protected */
    public void initWidgetView() {
        this.mVideoControllerInternalView = findViewById(R.id.vp_video_bottom_controller_view);
        this.mVideoPlayTimeView = (TextView) findViewById(R.id.vp_video_play_time);
        this.mVideoTotalTimeView = (TextView) findViewById(R.id.vp_video_total_time);
        this.mVideoPlaySeekBar = (SeekBar) findViewById(R.id.vp_video_seek_progress);
        this.mVideoFullScreenButton = (ImageView) findViewById(R.id.vp_video_fullscreen);
        this.mBottomProgressBar = (ProgressBar) findViewById(R.id.vp_video_bottom_progress);
        this.mVideoFullScreenButton.setOnClickListener(this);
        this.mVideoPlaySeekBar.setOnSeekBarChangeListener(this);
    }

    /* access modifiers changed from: protected */
    public int getControllerViewLayoutResId() {
        return R.layout.vp_layout_bottom_controller;
    }

    public void onClick(View view) {
        if (this.mFullScreenToggleListener != null) {
            if (ScreenState.isFullScreen(this.mCurrentScreenState)) {
                this.mFullScreenToggleListener.onExitFullScreen();
                this.mVideoFullScreenButton.setImageResource(R.drawable.vp_fullscreen);
                this.mFullScreenButtonRes = R.drawable.vp_fullscreen;
            } else if (ScreenState.isNormal(this.mCurrentScreenState)) {
                this.mFullScreenToggleListener.onStartFullScreen();
                this.mVideoFullScreenButton.setImageResource(R.drawable.vp_minimize);
                this.mFullScreenButtonRes = R.drawable.vp_minimize;
            } else {
                StringBuilder sb = new StringBuilder("the screen state is error, state=");
                sb.append(this.mCurrentScreenState);
                throw new IllegalStateException(sb.toString());
            }
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            PlayerManager.getInstance().seekTo((long) ((int) ((((long) seekBar.getProgress()) * this.mDuration) / 100)));
        }
    }

    /* access modifiers changed from: protected */
    public void changeFullScreenButtonIfNeeded(int i) {
        if (ScreenState.isFullScreen(i)) {
            if (this.mFullScreenButtonRes != R.drawable.vp_minimize) {
                this.mVideoFullScreenButton.setImageResource(R.drawable.vp_minimize);
                this.mFullScreenButtonRes = R.drawable.vp_minimize;
            }
        } else if (this.mFullScreenButtonRes != R.drawable.vp_fullscreen) {
            this.mVideoFullScreenButton.setImageResource(R.drawable.vp_fullscreen);
            this.mFullScreenButtonRes = R.drawable.vp_fullscreen;
        }
    }

    public void onChangeUINormalState(int i) {
        Utils.hideViewIfNeed(this);
        Utils.hideViewIfNeed(this.mVideoControllerInternalView);
        Utils.hideViewIfNeed(this.mBottomProgressBar);
    }

    public void onChangeUILoadingState(int i) {
        Utils.hideViewIfNeed(this);
        Utils.hideViewIfNeed(this.mVideoControllerInternalView);
        Utils.hideViewIfNeed(this.mBottomProgressBar);
    }

    public void onChangeUIPlayingState(int i) {
        Utils.showViewIfNeed(this);
        if (ScreenState.isSmallWindow(i)) {
            Utils.hideViewIfNeed(this.mVideoControllerInternalView);
            Utils.showViewIfNeed(this.mBottomProgressBar);
            return;
        }
        Utils.showViewIfNeed(this.mVideoControllerInternalView);
        Utils.hideViewIfNeed(this.mBottomProgressBar);
    }

    public void onChangeUIPauseState(int i) {
        Utils.showViewIfNeed(this);
        Utils.showViewIfNeed(this.mVideoControllerInternalView);
        Utils.hideViewIfNeed(this.mBottomProgressBar);
    }

    public void onChangeUIBufferingState(int i) {
        Utils.showViewIfNeed(this);
        if (ScreenState.isSmallWindow(i)) {
            Utils.hideViewIfNeed(this.mVideoControllerInternalView);
            Utils.showViewIfNeed(this.mBottomProgressBar);
            return;
        }
        Utils.showViewIfNeed(this.mVideoControllerInternalView);
        Utils.hideViewIfNeed(this.mBottomProgressBar);
    }

    public void onChangeUICompleteState(int i) {
        Utils.hideViewIfNeed(this);
        Utils.hideViewIfNeed(this.mVideoControllerInternalView);
        Utils.hideViewIfNeed(this.mBottomProgressBar);
        updateProgress(this.mDuration);
    }

    public void onChangeUIErrorState(int i) {
        Utils.hideViewIfNeed(this);
        Utils.hideViewIfNeed(this.mVideoControllerInternalView);
        Utils.hideViewIfNeed(this.mBottomProgressBar);
    }

    public void showAllPlayStateView() {
        Utils.showViewIfNeed(this);
        Utils.showViewIfNeed(this.mVideoControllerInternalView);
        Utils.hideViewIfNeed(this.mBottomProgressBar);
    }

    public void hideAllPlayStateView() {
        Utils.showViewIfNeed(this);
        Utils.hideViewIfNeed(this.mVideoControllerInternalView);
        Utils.showViewIfNeed(this.mBottomProgressBar);
    }

    public void onVideoDurationChanged(long j) {
        this.mDuration = j;
        this.mVideoTotalTimeView.setText(Utils.formatVideoTimeLength(j));
    }

    public void startVideoProgressUpdate() {
        stopVideoProgressUpdate();
        this.mHandler.postDelayed(this.mUpdateProgressTask, 100);
    }

    public void stopVideoProgressUpdate() {
        this.mHandler.removeCallbacks(this.mUpdateProgressTask);
    }

    public void setProcessUpdatedListener(OnProcessUpdatedListener onProcessUpdatedListener) {
        this.mProcessUpdatedListener = onProcessUpdatedListener;
    }

    /* access modifiers changed from: private */
    public void updateProgress(long j) {
        int i = (int) ((100 * j) / (this.mDuration == 0 ? 1 : this.mDuration));
        this.mVideoPlayTimeView.setText(Utils.formatVideoTimeLength(j));
        this.mVideoPlaySeekBar.setProgress(i);
        this.mBottomProgressBar.setProgress(i);
        if (this.mProcessUpdatedListener != null) {
            this.mProcessUpdatedListener.onProcessUpdated(j);
        }
    }

    public void setFullScreenToggleListener(FullScreenToggleListener fullScreenToggleListener) {
        this.mFullScreenToggleListener = fullScreenToggleListener;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public void setCurrentScreenState(int i) {
        this.mCurrentScreenState = i;
    }

    public void toggleFullScreenButtonVisibility(boolean z) {
        if (z) {
            Utils.showViewIfNeed(this.mVideoFullScreenButton);
        } else {
            Utils.hideViewIfNeed(this.mVideoFullScreenButton);
        }
    }
}
