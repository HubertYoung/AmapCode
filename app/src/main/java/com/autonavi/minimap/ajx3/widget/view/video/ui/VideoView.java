package com.autonavi.minimap.ajx3.widget.view.video.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.widget.view.video.player.DurationMessage;
import com.autonavi.minimap.ajx3.widget.view.video.player.Message;
import com.autonavi.minimap.ajx3.widget.view.video.player.PlayerManager;
import com.autonavi.minimap.ajx3.widget.view.video.player.UIStateMessage;
import com.autonavi.minimap.ajx3.widget.view.video.player.VideoSizeMessage;
import com.autonavi.minimap.ajx3.widget.view.video.ui.VideoControllerView.OnProcessUpdatedListener;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;
import java.lang.ref.WeakReference;
import java.util.Observable;
import java.util.Observer;

public class VideoView extends VideoContainer implements Observer {
    private static int sHashCodeId;
    private boolean isMuted;
    private AudioFocusChangeListener mAudioFocusChangeListener;
    private boolean mAutoPlay;
    private boolean mCanAudioResume;
    private boolean mCanResume;
    private OnClickListener mClickListener;
    private int mCurrentScreenState;
    private int mCurrentState;
    private int mFullScreenBackgroundColor;
    protected boolean mLoop;
    private int mOldIndex;
    private LayoutParams mOldParams;
    private ViewGroup mOldParent;
    private int mOldVolumeBeforeMuted;
    /* access modifiers changed from: private */
    public OnPlayStateChangedListener mPlayStateChangedListener;
    private int mScaleType;
    private int mScreenWidth;
    FullScreenOrientationChangedListener mSensorEventListener;
    SensorManager mSensorManager;
    private int mSmallWindowHeight;
    private int mSmallWindowWidth;
    private int mStateBeforeBuffering;
    private int mStateBeforeDetachedFromWindow;
    private VideoTextureView mTextureView;
    private boolean mToggleFullScreen;
    /* access modifiers changed from: private */
    public int mVideoHeight;
    /* access modifiers changed from: private */
    public int mVideoWidth;
    public int mViewHash;
    private VolumeChangeReceiver mVolumeChangeReceiver;

    static class AudioFocusChangeListener implements OnAudioFocusChangeListener {
        private WeakReference<VideoView> mVideoViewRef;

        public AudioFocusChangeListener(VideoView videoView) {
            this.mVideoViewRef = new WeakReference<>(videoView);
        }

        public void onAudioFocusChange(int i) {
            if (this.mVideoViewRef != null && this.mVideoViewRef.get() != null && PlayerManager.getInstance().isViewPlaying(((VideoView) this.mVideoViewRef.get()).mViewHash) && i != 1) {
                switch (i) {
                    case -2:
                        Utils.log("AudioManager.AUDIOFOCUS_LOSS_TRANSIENT");
                        break;
                    case -1:
                        Utils.log("AudioManager.AUDIOFOCUS_LOSS");
                        return;
                }
            }
        }
    }

    class FullScreenOrientationChangedListener implements SensorEventListener {
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        private FullScreenOrientationChangedListener() {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            float f = sensorEvent.values[0];
            if (f < -8.0f || f > 8.0f) {
                if (f > 0.0f) {
                    Utils.getActivity(VideoView.this.getContext()).setRequestedOrientation(0);
                    return;
                }
                Utils.getActivity(VideoView.this.getContext()).setRequestedOrientation(8);
            }
        }
    }

    public interface OnPlayStateChangedListener {
        void onBrightnessUpdated(float f);

        void onComplete();

        void onDetachedFromWindow();

        void onDurationUpdated(long j);

        void onError();

        void onLoading();

        void onMutedUpdate(boolean z);

        void onPause();

        void onPlaying();

        void onPositionUpdated(long j);

        void onScreenStateUpdated(int i);

        void onStop();

        void onVolumeUpdated(float f);
    }

    static class SettingsContentObserver extends ContentObserver {
        public SettingsContentObserver(Handler handler) {
            super(handler);
        }

        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        public void onChange(boolean z) {
            super.onChange(z);
        }

        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
        }
    }

    static class VolumeChangeReceiver extends BroadcastReceiver {
        private static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
        private static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
        private WeakReference<VideoView> mOuter;
        private boolean mRegistered = false;

        public VolumeChangeReceiver(VideoView videoView) {
            this.mOuter = new WeakReference<>(videoView);
        }

        public void onReceive(Context context, Intent intent) {
            if (VOLUME_CHANGED_ACTION.equals(intent.getAction()) && 3 == intent.getIntExtra(EXTRA_VOLUME_STREAM_TYPE, -1)) {
                int currentVolume = Utils.getCurrentVolume(context);
                if (currentVolume > 0 && this.mOuter.get() != null) {
                    ((VideoView) this.mOuter.get()).notifyVolumeChange(currentVolume);
                }
            }
        }

        public void registerReceiver(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(VOLUME_CHANGED_ACTION);
            context.registerReceiver(this, intentFilter);
            this.mRegistered = true;
        }

        public void unregisterReceiver(Context context) {
            if (this.mRegistered) {
                try {
                    context.unregisterReceiver(this);
                    this.mRegistered = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAudioFocusChangeListener = new AudioFocusChangeListener(this);
        this.mVolumeChangeReceiver = new VolumeChangeReceiver(this);
        this.mCurrentState = 0;
        this.mStateBeforeDetachedFromWindow = 0;
        this.mCurrentScreenState = 1;
        this.mAutoPlay = false;
        this.mLoop = false;
        this.mOldVolumeBeforeMuted = 0;
        this.isMuted = false;
        this.mFullScreenBackgroundColor = -1;
        this.mStateBeforeBuffering = -1;
        this.mCanResume = false;
        this.mCanAudioResume = false;
        this.mScaleType = 3;
        this.mToggleFullScreen = false;
        this.mOldIndex = 0;
        this.mOldParams = null;
        PlayerManager.getInstance().addObserver(this);
    }

    public VideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAudioFocusChangeListener = new AudioFocusChangeListener(this);
        this.mVolumeChangeReceiver = new VolumeChangeReceiver(this);
        this.mCurrentState = 0;
        this.mStateBeforeDetachedFromWindow = 0;
        this.mCurrentScreenState = 1;
        this.mAutoPlay = false;
        this.mLoop = false;
        this.mOldVolumeBeforeMuted = 0;
        this.isMuted = false;
        this.mFullScreenBackgroundColor = -1;
        this.mStateBeforeBuffering = -1;
        this.mCanResume = false;
        this.mCanAudioResume = false;
        this.mScaleType = 3;
        this.mToggleFullScreen = false;
        this.mOldIndex = 0;
        this.mOldParams = null;
        PlayerManager.getInstance().addObserver(this);
    }

    @TargetApi(21)
    public VideoView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAudioFocusChangeListener = new AudioFocusChangeListener(this);
        this.mVolumeChangeReceiver = new VolumeChangeReceiver(this);
        this.mCurrentState = 0;
        this.mStateBeforeDetachedFromWindow = 0;
        this.mCurrentScreenState = 1;
        this.mAutoPlay = false;
        this.mLoop = false;
        this.mOldVolumeBeforeMuted = 0;
        this.isMuted = false;
        this.mFullScreenBackgroundColor = -1;
        this.mStateBeforeBuffering = -1;
        this.mCanResume = false;
        this.mCanAudioResume = false;
        this.mScaleType = 3;
        this.mToggleFullScreen = false;
        this.mOldIndex = 0;
        this.mOldParams = null;
        PlayerManager.getInstance().addObserver(this);
    }

    private int getHashCodeId() {
        if (sHashCodeId == Integer.MAX_VALUE) {
            sHashCodeId = 0;
        }
        int i = sHashCodeId;
        sHashCodeId = i + 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void initView(Context context, AttributeSet attributeSet) {
        super.initView(context, attributeSet);
        this.mViewHash = toString().hashCode() + getHashCodeId();
        this.mScreenWidth = Utils.getWindowWidth(context);
        this.mSmallWindowWidth = this.mScreenWidth / 2;
        this.mSmallWindowHeight = (int) ((((((float) this.mSmallWindowWidth) * 1.0f) / 16.0f) * 9.0f) + 0.5f);
        setThumbColor(Color.parseColor("#f4f4f4"));
    }

    public void bind(String str, CharSequence charSequence, boolean z) {
        super.bind(str, charSequence, z);
        resetViewState();
        doAutoPlayIfNeeded();
    }

    public void setAutoPlay(boolean z) {
        this.mAutoPlay = z;
        doAutoPlayIfNeeded();
    }

    /* access modifiers changed from: protected */
    public int getViewHash() {
        return this.mViewHash;
    }

    private void doAutoPlayIfNeeded() {
        if (this.mAutoPlay && this.mVideoUrl != null && getCurrentState() == 0) {
            if (!PlayerManager.getInstance().isViewPlaying(this.mViewHash)) {
                PlayerManager.getInstance().stop();
            }
            startPlayVideo();
        }
    }

    public void setLoop(boolean z) {
        this.mLoop = z;
    }

    public void setVolume(float f) {
        Utils.setVolume(getContext(), (int) (f * ((float) Utils.getMaxVolume(getContext()))));
    }

    private void setCanResume(boolean z) {
        this.mCanResume = z;
    }

    private boolean canResume() {
        return this.mCanResume;
    }

    private void setAudioResume(boolean z) {
        this.mCanAudioResume = z;
    }

    private boolean canAudioResume() {
        return this.mCanAudioResume;
    }

    public void setBrightness(float f) {
        Utils.setBrightness(((Activity) getContext()).getWindow(), f);
    }

    public void setMuted(boolean z) {
        if (z && !this.isMuted) {
            this.isMuted = true;
            this.mOldVolumeBeforeMuted = Utils.getCurrentVolume(getContext());
            Utils.setVolume(getContext(), 0);
            if (this.mPlayStateChangedListener != null) {
                this.mPlayStateChangedListener.onMutedUpdate(true);
            }
        } else if (!z && this.isMuted) {
            this.isMuted = false;
            Utils.setVolume(getContext(), this.mOldVolumeBeforeMuted);
            if (this.mPlayStateChangedListener != null) {
                this.mPlayStateChangedListener.onMutedUpdate(false);
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyVolumeChange(int i) {
        if (PlayerManager.getInstance().isViewPlaying(this.mViewHash) && this.isMuted && i > 0) {
            setMuted(false);
        }
        if (this.mPlayStateChangedListener != null) {
            this.mPlayStateChangedListener.onVolumeUpdated((((float) i) * 1.0f) / ((float) Utils.getMaxVolume(getContext())));
        }
    }

    public void setFullScreenBackgroundColor(int i) {
        this.mFullScreenBackgroundColor = i;
    }

    private void resetViewState() {
        setCurrentScreenState(1);
        onPlayStateChanged(0);
    }

    /* access modifiers changed from: protected */
    public void setCurrentScreenState(int i) {
        this.mCurrentScreenState = i;
        this.mVideoControllerView.setCurrentScreenState(i);
    }

    /* access modifiers changed from: protected */
    public TextureView newTextureView() {
        this.mTextureView = new VideoTextureView(getContext());
        this.mTextureView.setScaleType(this.mScaleType);
        return this.mTextureView;
    }

    public void setScaleType(int i) {
        if (this.mTextureView != null) {
            this.mTextureView.setScaleType(i);
        }
        this.mScaleType = i;
    }

    public int getCurrentScreenState() {
        return this.mCurrentScreenState;
    }

    public int getCurrentState() {
        return this.mCurrentState;
    }

    public void update(Observable observable, Object obj) {
        if (getContext() != null && (obj instanceof Message)) {
            final Message message = (Message) obj;
            if (this.mViewHash == message.getHash() && this.mVideoUrl.equals(message.getVideoUrl())) {
                if (message instanceof VideoSizeMessage) {
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        public void run() {
                            View childAt = VideoView.this.mVideoTextureViewContainer.getChildAt(0);
                            if (childAt instanceof VideoSizeChangedListener) {
                                int width = ((VideoSizeMessage) message).getWidth();
                                int height = ((VideoSizeMessage) message).getHeight();
                                VideoView.this.mVideoWidth = width;
                                VideoView.this.mVideoHeight = height;
                                ((VideoSizeChangedListener) childAt).onVideoSizeChanged(width, height);
                            }
                        }
                    });
                } else if (message instanceof DurationMessage) {
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        public void run() {
                            long duration = ((DurationMessage) message).getDuration();
                            VideoView.this.mVideoControllerView.onVideoDurationChanged(duration);
                            if (VideoView.this.mPlayStateChangedListener != null) {
                                VideoView.this.mPlayStateChangedListener.onDurationUpdated(duration);
                            }
                        }
                    });
                } else {
                    if (obj instanceof UIStateMessage) {
                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            public void run() {
                                VideoView.this.onPlayStateChanged(((UIStateMessage) message).getState());
                            }
                        });
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        StringBuilder sb = new StringBuilder("attached to window, view hash:");
        sb.append(this.mViewHash);
        Utils.log(sb.toString());
        PlayerManager.getInstance().addObserver(this);
        this.mToggleFullScreen = false;
        if (ScreenState.isSmallWindow(getCurrentScreenState())) {
            toggleSmallWindow();
            return;
        }
        if (needResumeWhenAttachToWindow()) {
            if (this.mStateBeforeDetachedFromWindow == 1) {
                this.mCurrentState = 0;
            }
            resume();
            this.mStateBeforeDetachedFromWindow = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        StringBuilder sb = new StringBuilder("detached from window, view hash:");
        sb.append(this.mViewHash);
        Utils.log(sb.toString());
        PlayerManager.getInstance().removeObserver(this);
        if (!this.mToggleFullScreen) {
            if (PlayerManager.getInstance().getConfig().isSmallWindowPlayEnable()) {
                if (getId() != R.id.vp_small_window_view_id) {
                    toggleSmallWindow();
                }
            } else if (needRestoreWhenDetachFromWindow()) {
                this.mStateBeforeDetachedFromWindow = this.mCurrentState;
                setCanResume(true);
                if (this.mCurrentState == 1) {
                    PlayerManager.getInstance().stop();
                } else {
                    PlayerManager.getInstance().pause();
                }
            } else if (this.mCurrentState == 5) {
                this.mPlayStateChangedListener.onDetachedFromWindow();
            }
        }
    }

    public boolean onBackPressed() {
        if (!ScreenState.isFullScreen(getCurrentScreenState())) {
            return false;
        }
        if (!this.mFullScreenLocked) {
            onExitFullScreen();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPlayStateChanged(int i) {
        if (i == 3 && this.mCurrentState != 3) {
            this.mStateBeforeBuffering = this.mCurrentState;
        } else if (i == 4 && this.mCurrentState == 5) {
            this.mStateBeforeBuffering = -1;
            return;
        } else if (i == 4 && this.mStateBeforeBuffering != -1) {
            i = this.mStateBeforeBuffering;
            this.mStateBeforeBuffering = -1;
        }
        if (8 == i) {
            this.mCurrentState = 2;
        } else {
            this.mCurrentState = i;
        }
        onChangeUIState(i);
        switch (i) {
            case 0:
                Utils.log("state change to: STATE_NORMAL");
                this.mVideoControllerView.onVideoDurationChanged(0);
                this.mVideoControllerView.stopVideoProgressUpdate();
                abandonAudioFocus();
                this.mVolumeChangeReceiver.unregisterReceiver(getContext());
                ((Activity) getContext()).getWindow().clearFlags(128);
                if (this.mPlayStateChangedListener != null) {
                    this.mPlayStateChangedListener.onStop();
                    return;
                }
                break;
            case 1:
                Utils.log("state change to: STATE_LOADING");
                if (this.mPlayStateChangedListener != null) {
                    this.mPlayStateChangedListener.onLoading();
                    return;
                }
                break;
            case 2:
                Utils.log("state change to: STATE_PLAYING");
                this.mVideoControllerView.startVideoProgressUpdate();
                requestAudioFocus();
                return;
            case 3:
                Utils.log("state change to: STATE_PLAYING_BUFFERING_START");
                return;
            case 4:
                Utils.log("state change to: STATE_PLAYING_BUFFERING_END");
                return;
            case 5:
                Utils.log("state change to: STATE_PAUSE");
                this.mVideoControllerView.stopVideoProgressUpdate();
                abandonAudioFocus();
                if (this.mPlayStateChangedListener != null) {
                    this.mPlayStateChangedListener.onPause();
                    return;
                }
                break;
            case 6:
                Utils.log("state change to: STATE_AUTO_COMPLETE");
                this.mVideoControllerView.stopVideoProgressUpdate();
                if (this.mPlayStateChangedListener != null) {
                    this.mPlayStateChangedListener.onComplete();
                }
                if (!this.mLoop || !PlayerManager.getInstance().isViewPlaying(this.mViewHash)) {
                    onExitFullScreen();
                    onExitSmallWindowPlay(true);
                    return;
                }
                PlayerManager.getInstance().seekTo(0);
                PlayerManager.getInstance().play();
                return;
            case 7:
                Utils.log("state change to: STATE_ERROR");
                this.mVideoControllerView.onVideoDurationChanged(0);
                this.mVideoControllerView.stopVideoProgressUpdate();
                abandonAudioFocus();
                this.mVolumeChangeReceiver.unregisterReceiver(getContext());
                if (this.mPlayStateChangedListener != null) {
                    this.mPlayStateChangedListener.onError();
                    return;
                }
                break;
            case 8:
                Utils.log("state change to: STATE_FIRST_FRAME_RENDERING");
                if (this.mPlayStateChangedListener != null) {
                    this.mPlayStateChangedListener.onPlaying();
                }
                this.mVideoControllerView.startVideoProgressUpdate();
                return;
            default:
                throw new IllegalStateException("Illegal Play State:".concat(String.valueOf(i)));
        }
    }

    public void setPlayStateChangedListener(OnPlayStateChangedListener onPlayStateChangedListener) {
        this.mPlayStateChangedListener = onPlayStateChangedListener;
        if (onPlayStateChangedListener == null && this.mVideoControllerView != null) {
            this.mVideoControllerView.setProcessUpdatedListener(null);
        }
        if (onPlayStateChangedListener != null && this.mVideoControllerView != null) {
            this.mVideoControllerView.setProcessUpdatedListener(new OnProcessUpdatedListener() {
                public void onProcessUpdated(long j) {
                    if (VideoView.this.mPlayStateChangedListener != null) {
                        VideoView.this.mPlayStateChangedListener.onPositionUpdated(j);
                    }
                }
            });
        }
    }

    private void onChangeUIState(int i) {
        switch (i) {
            case 0:
                onChangeUINormalState(getCurrentScreenState());
                return;
            case 1:
                onChangeUILoadingState(getCurrentScreenState());
                return;
            case 2:
                onChangeUIPlayingState(getCurrentScreenState());
                return;
            case 3:
                onChangeUIBufferingState(getCurrentScreenState());
                return;
            case 4:
                onChangeUIBufferCompletedState(getCurrentScreenState());
                return;
            case 5:
                onChangeUIPauseState(getCurrentScreenState());
                return;
            case 6:
                onChangeUICompleteState(getCurrentScreenState());
                return;
            case 7:
                onChangeUIErrorState(getCurrentScreenState());
                return;
            case 8:
                onChangeUIFirstFrameRendering(getCurrentScreenState());
                return;
            default:
                throw new IllegalStateException("Illegal Play State:".concat(String.valueOf(i)));
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        Utils.log(" onKey keyCode = ".concat(String.valueOf(i)));
        if (i == 24 && this.isMuted) {
            setMuted(false);
        }
        return super.onKeyDown(i, keyEvent);
    }

    private void requestAudioFocus() {
        ((AudioManager) getContext().getSystemService("audio")).requestAudioFocus(this.mAudioFocusChangeListener, 3, 2);
    }

    private void abandonAudioFocus() {
        ((AudioManager) getContext().getSystemService("audio")).abandonAudioFocus(this.mAudioFocusChangeListener);
    }

    /* access modifiers changed from: protected */
    public void toggleSmallWindow() {
        if (this.mCurrentState != 0) {
            if (!PlayerManager.getInstance().hasViewPlaying()) {
                resetViewState();
            } else if (ScreenState.isNormal(getCurrentScreenState())) {
                onStartSmallWindowPlay();
            } else {
                onExitSmallWindowPlay(false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStartSmallWindowPlay() {
        this.mVideoControllerView.stopVideoProgressUpdate();
        setCurrentScreenState(3);
        PlayerManager.getInstance().setTextureView(null);
        this.mVideoTextureViewContainer.removeAllViews();
        VideoView videoView = new VideoView(getContext());
        videoView.setId(R.id.vp_small_window_view_id);
        videoView.mVideoControllerView.cloneState(this.mVideoControllerView);
        videoView.mVideoHeaderView.setTitle(this.mVideoTitle);
        videoView.mVideoUrl = this.mVideoUrl;
        videoView.mViewHash = this.mViewHash;
        TextureView createTextureView = videoView.createTextureView();
        videoView.mVideoTextureViewContainer.addView(createTextureView);
        PlayerManager.getInstance().setTextureView(createTextureView);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.mSmallWindowWidth, this.mSmallWindowHeight);
        layoutParams.gravity = 85;
        ((ViewGroup) Utils.getActivity(getContext()).findViewById(16908290)).addView(videoView, layoutParams);
        videoView.mCurrentScreenState = getCurrentScreenState();
        videoView.mCurrentState = this.mCurrentState;
        videoView.onPlayStateChanged(this.mCurrentState);
    }

    /* access modifiers changed from: protected */
    public void onExitSmallWindowPlay(boolean z) {
        if (ScreenState.isSmallWindow(getCurrentScreenState())) {
            ViewGroup viewGroup = (ViewGroup) Utils.getActivity(getContext()).findViewById(16908290);
            VideoView videoView = (VideoView) viewGroup.findViewById(R.id.vp_small_window_view_id);
            videoView.mVideoControllerView.stopVideoProgressUpdate();
            setCurrentScreenState(1);
            PlayerManager.getInstance().setTextureView(null);
            videoView.mVideoTextureViewContainer.removeAllViews();
            this.mVideoControllerView.cloneState(videoView.mVideoControllerView);
            this.mVideoUrl = videoView.mVideoUrl;
            this.mViewHash = videoView.mViewHash;
            this.mCurrentState = videoView.mCurrentState;
            if (z) {
                PlayerManager.getInstance().stop();
                viewGroup.removeView(videoView);
                return;
            }
            viewGroup.removeView(videoView);
            TextureView createTextureView = createTextureView();
            this.mVideoTextureViewContainer.addView(createTextureView);
            PlayerManager.getInstance().setTextureView(createTextureView);
            onPlayStateChanged(this.mCurrentState);
        }
    }

    private void updateViewStateIfNeeded() {
        this.mVideoControllerView.changeFullScreenButtonIfNeeded(this.mCurrentScreenState);
    }

    public void onStartFullScreen() {
        this.mToggleFullScreen = true;
        setCurrentScreenState(2);
        int i = this.mCurrentState;
        if (2 == i) {
            PlayerManager.getInstance().pause();
        }
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (this.mFullScreenBackgroundColor != -1) {
            setBackgroundColor(this.mFullScreenBackgroundColor);
        } else if (viewGroup != null) {
            setBackgroundDrawable(viewGroup.getBackground());
        }
        this.mOldParent = (ViewGroup) getParent();
        this.mOldParams = getLayoutParams();
        this.mOldIndex = this.mOldParent.indexOfChild(this);
        this.mOldParent.removeView(this);
        ((ViewGroup) Utils.getActivity(getContext()).findViewById(16908290)).addView(this, new LayoutParams(-1, -1));
        initFullScreenGestureView();
        Utils.getActivity(getContext()).getWindow().addFlags(1024);
        if (Utils.needLandscape(this.mVideoWidth, this.mVideoHeight)) {
            Utils.getActivity(getContext()).setRequestedOrientation(0);
        }
        if (2 == i) {
            PlayerManager.getInstance().play();
        }
        if (Utils.needLandscape(this.mVideoWidth, this.mVideoHeight)) {
            initFullScreenOrientationChangeListener();
        }
        if (!this.mHideControllerView) {
            onToggleFullScreenLockState(false);
        }
        updateViewStateIfNeeded();
        StringBuilder sb = new StringBuilder(" onStartFullScreen screenState = ");
        sb.append(getCurrentScreenState());
        Utils.log(sb.toString());
        if (this.mPlayStateChangedListener != null) {
            this.mPlayStateChangedListener.onScreenStateUpdated(2);
        }
    }

    public void onExitFullScreen() {
        if (ScreenState.isFullScreen(getCurrentScreenState())) {
            this.mToggleFullScreen = true;
            setCurrentScreenState(1);
            int i = this.mCurrentState;
            if (2 == i) {
                PlayerManager.getInstance().pause();
            }
            setBackgroundDrawable(null);
            clearFullScreenGestureView();
            ((ViewGroup) Utils.getActivity(getContext()).findViewById(16908290)).removeView(this);
            this.mOldParent.addView(this, this.mOldIndex, this.mOldParams);
            Utils.getActivity(getContext()).getWindow().clearFlags(1024);
            Utils.getActivity(getContext()).setRequestedOrientation(1);
            this.mOldParent = null;
            this.mOldIndex = 0;
            if (2 == i) {
                PlayerManager.getInstance().play();
            }
            if (Utils.needLandscape(this.mVideoWidth, this.mVideoHeight)) {
                clearFullScreenOrientationChangeListener();
            }
            if (this.mHideControllerView) {
                onToggleFullScreenLockState(false);
            }
            updateViewStateIfNeeded();
            StringBuilder sb = new StringBuilder(" onExitFullScreen screenState = ");
            sb.append(getCurrentScreenState());
            Utils.log(sb.toString());
            if (this.mPlayStateChangedListener != null) {
                this.mPlayStateChangedListener.onScreenStateUpdated(1);
            }
        }
    }

    private void initFullScreenOrientationChangeListener() {
        if (this.mSensorManager == null) {
            this.mSensorManager = (SensorManager) getContext().getSystemService("sensor");
        }
        if (this.mSensorEventListener == null) {
            this.mSensorEventListener = new FullScreenOrientationChangedListener();
        }
        this.mSensorManager.registerListener(this.mSensorEventListener, this.mSensorManager.getDefaultSensor(1), 3);
    }

    private void clearFullScreenOrientationChangeListener() {
        if (this.mSensorManager != null && this.mSensorEventListener != null) {
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            this.mSensorEventListener = null;
        }
    }

    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (R.id.vp_video_surface_container == id || view == this.mVideoThumbView) {
            if (this.mClickListener != null) {
                this.mClickListener.onClick(this);
            }
            return;
        }
        if (!PlayerManager.getInstance().isViewPlaying(this.mViewHash)) {
            PlayerManager.getInstance().stop();
        }
        int state = PlayerManager.getInstance().getState();
        if (view != this.mVideoErrorView) {
            if (R.id.vp_video_play == id) {
                if (!TextUtils.isEmpty(this.mVideoUrl)) {
                    if (state != 0) {
                        if (state != 2) {
                            switch (state) {
                                case 5:
                                    PlayerManager.getInstance().play();
                                    return;
                                case 6:
                                    PlayerManager.getInstance().seekTo(0);
                                    PlayerManager.getInstance().play();
                                    return;
                                case 7:
                                    break;
                                default:
                                    return;
                            }
                        } else {
                            PlayerManager.getInstance().pause();
                            return;
                        }
                    }
                    startPlayVideo();
                }
            } else if (R.id.vp_video_small_window_back == id) {
                onExitSmallWindowPlay(true);
            } else if (R.id.vp_fullscreen_lock == id) {
                onToggleFullScreenLockState(!this.mFullScreenLocked);
            }
        }
    }

    public void startPlayVideo() {
        if (canPlay()) {
            play();
        }
    }

    /* access modifiers changed from: protected */
    public void play() {
        ((Activity) getContext()).getWindow().addFlags(128);
        requestAudioFocus();
        this.mVolumeChangeReceiver.registerReceiver(getContext());
        PlayerManager.getInstance().removeTextureView();
        TextureView createTextureView = createTextureView();
        this.mVideoTextureViewContainer.addView(createTextureView);
        PlayerManager.getInstance().start(this.mVideoUrl, this.mViewHash);
        PlayerManager.getInstance().setTextureView(createTextureView);
        setCanResume(false);
        setAudioResume(false);
        if (this.isMuted) {
            setMuted(true);
        }
    }

    /* access modifiers changed from: protected */
    public boolean canPlay() {
        return Utils.isConnected(getContext()) || PlayerManager.getInstance().isCached(this.mVideoUrl) || PlayerManager.getInstance().isCaching(this.mVideoUrl);
    }

    public boolean requestPlay() {
        int currentState = getCurrentState();
        if (PlayerManager.getInstance().isViewPlaying(this.mViewHash)) {
            int state = PlayerManager.getInstance().getState();
            if (currentState != state) {
                switch (state) {
                    case 0:
                    case 5:
                    case 6:
                    case 7:
                        currentState = state;
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 8:
                        currentState = 5;
                        break;
                    default:
                        currentState = 0;
                        break;
                }
            }
        } else {
            PlayerManager.getInstance().stop();
            this.mCurrentState = 0;
            currentState = this.mCurrentState;
        }
        setCanResume(false);
        setAudioResume(false);
        if (currentState != 0) {
            switch (currentState) {
                case 5:
                    PlayerManager.getInstance().play();
                    return true;
                case 6:
                    PlayerManager.getInstance().seekTo(0);
                    PlayerManager.getInstance().play();
                    return true;
                case 7:
                    break;
                default:
                    return false;
            }
        }
        startPlayVideo();
        return true;
    }

    public boolean requestPause() {
        if (!PlayerManager.getInstance().isViewPlaying(this.mViewHash)) {
            return false;
        }
        if (getCurrentState() == 2 || getCurrentState() == 5 || getCurrentState() == 3 || getCurrentState() == 4) {
            PlayerManager.getInstance().pause();
        } else {
            PlayerManager.getInstance().stop();
        }
        setCanResume(false);
        setAudioResume(false);
        return true;
    }

    public boolean requestStop() {
        if (!PlayerManager.getInstance().isViewPlaying(this.mViewHash)) {
            return false;
        }
        PlayerManager.getInstance().stop();
        setCanResume(false);
        setAudioResume(false);
        return true;
    }

    public boolean updateScreenState(int i) {
        if (!ScreenState.isNormal(getCurrentScreenState()) && ScreenState.isNormal(i)) {
            onExitFullScreen();
            return true;
        } else if (ScreenState.isFullScreen(getCurrentScreenState()) || !ScreenState.isFullScreen(i)) {
            return false;
        } else {
            onStartFullScreen();
            return true;
        }
    }

    public boolean seekTo(long j) {
        if (PlayerManager.getInstance().isViewPlaying(this.mViewHash) && j >= 0) {
            int currentState = getCurrentState();
            if (2 == currentState || 5 == currentState) {
                PlayerManager.getInstance().seekTo(j);
                return true;
            }
        }
        return false;
    }

    public void destroy() {
        this.mVideoControllerView.destroy();
        setCanResume(false);
        setAudioResume(false);
    }

    private void resume() {
        if (canResume()) {
            if (!PlayerManager.getInstance().isViewPlaying(this.mViewHash) || PlayerManager.getInstance().getState() != 5) {
                requestPlay();
            } else {
                PlayerManager.getInstance().resume();
            }
            setCanResume(false);
            setAudioResume(false);
            if (this.isMuted) {
                setMuted(true);
            }
        }
    }

    private boolean needResumeWhenAttachToWindow() {
        return this.mStateBeforeDetachedFromWindow == 1 || this.mStateBeforeDetachedFromWindow == 2 || this.mStateBeforeDetachedFromWindow == 3 || this.mStateBeforeDetachedFromWindow == 4;
    }

    private boolean needRestoreWhenDetachFromWindow() {
        return this.mCurrentState == 1 || this.mCurrentState == 2 || this.mCurrentState == 3 || this.mCurrentState == 4;
    }

    public void handlePageResume() {
        if (getWindowToken() != null) {
            resume();
        }
    }

    public void handlePageStop() {
        if (PlayerManager.getInstance().isViewPlaying(this.mViewHash) && PlayerManager.getInstance().getState() == 2) {
            setCanResume(true);
            setAudioResume(false);
            PlayerManager.getInstance().pause();
        }
    }

    public void handlePageDestroy() {
        if (PlayerManager.getInstance().isViewPlaying(this.mViewHash)) {
            PlayerManager.getInstance().release();
        }
        destroy();
    }
}
