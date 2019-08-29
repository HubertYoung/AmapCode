package com.autonavi.minimap.component;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.util.Map;

public class SplashVideoView extends TextureView implements MediaPlayerControl {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    /* access modifiers changed from: private */
    public String TAG = "TextureVideoView";
    private int fixedHeight;
    private int fixedWidth;
    private int mAudioSession;
    private OnBufferingUpdateListener mBufferingUpdateListener = new OnBufferingUpdateListener() {
        public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            SplashVideoView.this.mCurrentBufferPercentage = i;
        }
    };
    /* access modifiers changed from: private */
    public boolean mCanPause;
    /* access modifiers changed from: private */
    public boolean mCanSeekBack;
    /* access modifiers changed from: private */
    public boolean mCanSeekForward;
    private OnCompletionListener mCompletionListener = new OnCompletionListener() {
        public final void onCompletion(MediaPlayer mediaPlayer) {
            SplashVideoView.this.mCurrentState = 5;
            SplashVideoView.this.mTargetState = 5;
            if (SplashVideoView.this.mMediaController != null) {
                SplashVideoView.this.mMediaController.hide();
            }
            if (SplashVideoView.this.mOnCompletionListener != null) {
                SplashVideoView.this.mOnCompletionListener.onCompletion(SplashVideoView.this.mMediaPlayer);
            }
        }
    };
    private Context mContext;
    /* access modifiers changed from: private */
    public int mCurrentBufferPercentage;
    /* access modifiers changed from: private */
    public int mCurrentState = 0;
    private OnErrorListener mErrorListener = new OnErrorListener() {
        public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            SplashVideoView.this.TAG;
            StringBuilder sb = new StringBuilder("Error: ");
            sb.append(i);
            sb.append(",");
            sb.append(i2);
            SplashVideoView.this.mCurrentState = -1;
            SplashVideoView.this.mTargetState = -1;
            if (SplashVideoView.this.mMediaController != null) {
                SplashVideoView.this.mMediaController.hide();
            }
            return (SplashVideoView.this.mOnErrorListener == null || SplashVideoView.this.mOnErrorListener.onError(SplashVideoView.this.mMediaPlayer, i, i2)) ? true : true;
        }
    };
    private Map<String, String> mHeaders;
    private OnInfoListener mInfoListener = new OnInfoListener() {
        public final boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
            if (SplashVideoView.this.mOnInfoListener != null) {
                SplashVideoView.this.mOnInfoListener.onInfo(mediaPlayer, i, i2);
            }
            return true;
        }
    };
    /* access modifiers changed from: private */
    public MediaController mMediaController;
    /* access modifiers changed from: private */
    public MediaPlayer mMediaPlayer = null;
    /* access modifiers changed from: private */
    public OnCompletionListener mOnCompletionListener;
    /* access modifiers changed from: private */
    public OnErrorListener mOnErrorListener;
    /* access modifiers changed from: private */
    public OnInfoListener mOnInfoListener;
    /* access modifiers changed from: private */
    public OnPreparedListener mOnPreparedListener;
    OnPreparedListener mPreparedListener = new OnPreparedListener() {
        public final void onPrepared(MediaPlayer mediaPlayer) {
            SplashVideoView.this.mCurrentState = 2;
            SplashVideoView.this.mCanPause = SplashVideoView.this.mCanSeekBack = SplashVideoView.this.mCanSeekForward = true;
            if (SplashVideoView.this.mOnPreparedListener != null) {
                SplashVideoView.this.mOnPreparedListener.onPrepared(SplashVideoView.this.mMediaPlayer);
            }
            if (SplashVideoView.this.mMediaController != null) {
                SplashVideoView.this.mMediaController.setEnabled(true);
            }
            SplashVideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
            SplashVideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
            int access$1100 = SplashVideoView.this.mSeekWhenPrepared;
            if (access$1100 != 0) {
                SplashVideoView.this.seekTo(access$1100);
            }
            if (SplashVideoView.this.mVideoWidth != 0 && SplashVideoView.this.mVideoHeight != 0) {
                SplashVideoView.this.getSurfaceTexture().setDefaultBufferSize(SplashVideoView.this.mVideoWidth, SplashVideoView.this.mVideoHeight);
                if (SplashVideoView.this.mTargetState == 3) {
                    SplashVideoView.this.start();
                    if (SplashVideoView.this.mMediaController != null) {
                        SplashVideoView.this.mMediaController.show();
                    }
                } else if (!SplashVideoView.this.isPlaying() && ((access$1100 != 0 || SplashVideoView.this.getCurrentPosition() > 0) && SplashVideoView.this.mMediaController != null)) {
                    SplashVideoView.this.mMediaController.show(0);
                }
            } else if (SplashVideoView.this.mTargetState == 3) {
                SplashVideoView.this.start();
            }
        }
    };
    /* access modifiers changed from: private */
    public int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener = new OnVideoSizeChangedListener() {
        public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
            SplashVideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
            SplashVideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
            if (SplashVideoView.this.mVideoWidth != 0 && SplashVideoView.this.mVideoHeight != 0) {
                SplashVideoView.this.getSurfaceTexture().setDefaultBufferSize(SplashVideoView.this.mVideoWidth, SplashVideoView.this.mVideoHeight);
                SplashVideoView.this.requestLayout();
                SplashVideoView.this.transformVideo(SplashVideoView.this.mVideoWidth, SplashVideoView.this.mVideoHeight);
                SplashVideoView.this.TAG;
                String.format("OnVideoSizeChangedListener, mVideoWidth=%d,mVideoHeight=%d", new Object[]{Integer.valueOf(SplashVideoView.this.mVideoWidth), Integer.valueOf(SplashVideoView.this.mVideoHeight)});
            }
        }
    };
    /* access modifiers changed from: private */
    public Surface mSurface = null;
    SurfaceTextureListener mSurfaceTextureListener = new SurfaceTextureListener() {
        public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            boolean z = false;
            boolean z2 = SplashVideoView.this.mTargetState == 3;
            if (i > 0 && i2 > 0) {
                z = true;
            }
            if (SplashVideoView.this.mMediaPlayer != null && z2 && z) {
                if (SplashVideoView.this.mSeekWhenPrepared != 0) {
                    SplashVideoView.this.seekTo(SplashVideoView.this.mSeekWhenPrepared);
                }
                SplashVideoView.this.start();
            }
        }

        public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            SplashVideoView.this.mSurface = new Surface(surfaceTexture);
            SplashVideoView.this.openVideo();
        }

        public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (SplashVideoView.this.mSurface != null) {
                SplashVideoView.this.mSurface.release();
                SplashVideoView.this.mSurface = null;
            }
            if (SplashVideoView.this.mMediaController != null) {
                SplashVideoView.this.mMediaController.hide();
            }
            SplashVideoView.this.release(true);
            return true;
        }
    };
    /* access modifiers changed from: private */
    public int mTargetState = 0;
    private Uri mUri;
    /* access modifiers changed from: private */
    public int mVideoHeight;
    /* access modifiers changed from: private */
    public int mVideoWidth;
    private Matrix matrix;
    private float volume;

    public SplashVideoView(Context context, drr drr, String str, boolean z) {
        super(context);
        this.mContext = context;
        this.volume = z ? 1.0f : 0.0f;
        setLayoutParams(new LayoutParams(-1, -1));
        setOnCompletionListener(drr);
        setOnPreparedListener(drr);
        setOnErrorListener(drr);
        setOnInfoListener(drr);
        initVideoView();
        setVideoPath(str);
    }

    public void setFixedSize(int i, int i2) {
        this.fixedHeight = i2;
        this.fixedWidth = i;
        StringBuilder sb = new StringBuilder("setFixedSize,width=");
        sb.append(i);
        sb.append("height=");
        sb.append(i2);
        requestLayout();
    }

    public int getVideoHeight() {
        return this.mVideoHeight;
    }

    public int getVideoWidth() {
        return this.mVideoWidth;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.fixedWidth = getDefaultSize(0, i);
        this.fixedHeight = getDefaultSize(0, i2);
        if (this.fixedWidth == 0 || this.fixedHeight == 0) {
            defaultMeasure(this.fixedWidth, this.fixedHeight);
        } else {
            setMeasuredDimension(this.fixedWidth, this.fixedHeight);
        }
        String.format("onMeasure, fixedWidth=%d,fixedHeight=%d", new Object[]{Integer.valueOf(this.fixedWidth), Integer.valueOf(this.fixedHeight)});
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006e, code lost:
        if (r1 > r6) goto L_0x0094;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void defaultMeasure(int r6, int r7) {
        /*
            r5 = this;
            int r0 = r5.mVideoWidth
            int r0 = getDefaultSize(r0, r6)
            int r1 = r5.mVideoHeight
            int r1 = getDefaultSize(r1, r7)
            int r2 = r5.mVideoWidth
            if (r2 <= 0) goto L_0x0092
            int r2 = r5.mVideoHeight
            if (r2 <= 0) goto L_0x0092
            int r0 = android.view.View.MeasureSpec.getMode(r6)
            int r6 = android.view.View.MeasureSpec.getSize(r6)
            int r1 = android.view.View.MeasureSpec.getMode(r7)
            int r7 = android.view.View.MeasureSpec.getSize(r7)
            r2 = 1073741824(0x40000000, float:2.0)
            if (r0 != r2) goto L_0x0051
            if (r1 != r2) goto L_0x0051
            int r0 = r5.mVideoWidth
            int r0 = r0 * r7
            int r1 = r5.mVideoHeight
            int r1 = r1 * r6
            if (r0 >= r1) goto L_0x003e
            int r6 = r5.mVideoWidth
            int r6 = r6 * r7
            int r0 = r5.mVideoHeight
            int r0 = r6 / r0
            r6 = r0
            goto L_0x0094
        L_0x003e:
            int r0 = r5.mVideoWidth
            int r0 = r0 * r7
            int r1 = r5.mVideoHeight
            int r1 = r1 * r6
            if (r0 <= r1) goto L_0x0094
            int r7 = r5.mVideoHeight
            int r7 = r7 * r6
            int r0 = r5.mVideoWidth
            int r1 = r7 / r0
            goto L_0x0093
        L_0x0051:
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 != r2) goto L_0x0063
            int r0 = r5.mVideoHeight
            int r0 = r0 * r6
            int r2 = r5.mVideoWidth
            int r0 = r0 / r2
            if (r1 != r3) goto L_0x0061
            if (r0 <= r7) goto L_0x0061
            goto L_0x0094
        L_0x0061:
            r7 = r0
            goto L_0x0094
        L_0x0063:
            if (r1 != r2) goto L_0x0073
            int r1 = r5.mVideoWidth
            int r1 = r1 * r7
            int r2 = r5.mVideoHeight
            int r1 = r1 / r2
            if (r0 != r3) goto L_0x0071
            if (r1 <= r6) goto L_0x0071
            goto L_0x0094
        L_0x0071:
            r6 = r1
            goto L_0x0094
        L_0x0073:
            int r2 = r5.mVideoWidth
            int r4 = r5.mVideoHeight
            if (r1 != r3) goto L_0x0083
            if (r4 <= r7) goto L_0x0083
            int r1 = r5.mVideoWidth
            int r1 = r1 * r7
            int r2 = r5.mVideoHeight
            int r1 = r1 / r2
            goto L_0x0085
        L_0x0083:
            r1 = r2
            r7 = r4
        L_0x0085:
            if (r0 != r3) goto L_0x0071
            if (r1 <= r6) goto L_0x0071
            int r7 = r5.mVideoHeight
            int r7 = r7 * r6
            int r0 = r5.mVideoWidth
            int r1 = r7 / r0
            goto L_0x0093
        L_0x0092:
            r6 = r0
        L_0x0093:
            r7 = r1
        L_0x0094:
            r5.setMeasuredDimension(r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.component.SplashVideoView.defaultMeasure(int, int):void");
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(SplashVideoView.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(SplashVideoView.class.getName());
    }

    public int resolveAdjustedSize(int i, int i2) {
        return getDefaultSize(i, i2);
    }

    private void initVideoView() {
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        setSurfaceTextureListener(this.mSurfaceTextureListener);
        this.mCurrentState = 0;
        this.mTargetState = 0;
    }

    public void setVideoPath(String str) {
        setVideoURI(Uri.parse(str));
    }

    public void setVideoURI(Uri uri) {
        setVideoURI(uri, null);
    }

    public void setVideoURI(Uri uri, Map<String, String> map) {
        this.mUri = uri;
        this.mHeaders = map;
        this.mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    public void stopPlayback() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            this.mTargetState = 0;
            bnz.b(this.mContext);
        }
    }

    /* access modifiers changed from: private */
    public void openVideo() {
        if (this.mUri != null && this.mSurface != null) {
            release(false);
            bnz.a(this.mContext, true);
            try {
                this.mMediaPlayer = new MediaPlayer();
                if (this.mAudioSession != 0) {
                    this.mMediaPlayer.setAudioSessionId(this.mAudioSession);
                } else {
                    this.mAudioSession = this.mMediaPlayer.getAudioSessionId();
                }
                this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
                this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
                this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
                this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
                this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
                this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
                this.mCurrentBufferPercentage = 0;
                this.mMediaPlayer.setDataSource(getContext().getApplicationContext(), this.mUri, this.mHeaders);
                this.mMediaPlayer.setSurface(this.mSurface);
                this.mMediaPlayer.setAudioStreamType(3);
                this.mMediaPlayer.setScreenOnWhilePlaying(true);
                this.mMediaPlayer.setVolume(this.volume, this.volume);
                this.mMediaPlayer.prepareAsync();
                this.mCurrentState = 1;
                attachMediaController();
            } catch (IOException unused) {
                new StringBuilder("Unable to open content: ").append(this.mUri);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            } catch (IllegalArgumentException unused2) {
                new StringBuilder("Unable to open content: ").append(this.mUri);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            }
        }
    }

    public void setMediaController(MediaController mediaController) {
        if (this.mMediaController != null) {
            this.mMediaController.hide();
        }
        this.mMediaController = mediaController;
        attachMediaController();
    }

    private void attachMediaController() {
        if (this.mMediaPlayer != null && this.mMediaController != null) {
            this.mMediaController.setMediaPlayer(this);
            this.mMediaController.setAnchorView(getParent() instanceof View ? (View) getParent() : this);
            this.mMediaController.setEnabled(isInPlaybackState());
        }
    }

    /* access modifiers changed from: private */
    @SuppressFBWarnings({"ICAST_IDIV_CAST_TO_DOUBLE"})
    public void transformVideo(int i, int i2) {
        if (getResizedHeight() == 0 || getResizedWidth() == 0) {
            StringBuilder sb = new StringBuilder("transformVideo, getResizedHeight=");
            sb.append(getResizedHeight());
            sb.append(",getResizedWidth=");
            sb.append(getResizedWidth());
            return;
        }
        float f = (float) i;
        float f2 = (float) i2;
        float max = Math.max(((float) getResizedWidth()) / f, ((float) getResizedHeight()) / f2);
        if (this.matrix == null) {
            this.matrix = new Matrix();
        } else {
            this.matrix.reset();
        }
        StringBuilder sb2 = new StringBuilder("transformVideo, getResizedHeight=");
        sb2.append(getResizedHeight());
        sb2.append(",getResizedWidth=");
        sb2.append(getResizedWidth());
        this.matrix.preTranslate((float) ((getResizedWidth() - i) / 2), (float) ((getResizedHeight() - i2) / 2));
        this.matrix.preScale(f / ((float) getResizedWidth()), f2 / ((float) getResizedHeight()));
        this.matrix.postScale(max, max, (float) (getResizedWidth() / 2), (float) (getResizedHeight() / 2));
        this.matrix.postTranslate(0.0f, (((float) getResizedHeight()) - (f2 * max)) / 2.0f);
        setTransform(this.matrix);
        postInvalidate();
        StringBuilder sb3 = new StringBuilder("transformVideo, videoWidth=");
        sb3.append(i);
        sb3.append(",videoHeight=");
        sb3.append(i2);
    }

    public int getResizedWidth() {
        if (this.fixedWidth == 0) {
            return getWidth();
        }
        return this.fixedWidth;
    }

    public int getResizedHeight() {
        if (this.fixedHeight == 0) {
            return getHeight();
        }
        return this.fixedHeight;
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    /* access modifiers changed from: private */
    public void release(boolean z) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            if (z) {
                this.mTargetState = 0;
            }
            bnz.b(this.mContext);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return super.onTrackballEvent(motionEvent);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = (i == 4 || i == 24 || i == 25 || i == 164 || i == 82 || i == 5 || i == 6) ? false : true;
        if (isInPlaybackState() && z && this.mMediaController != null) {
            if (i == 79 || i == 85) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                } else {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            } else if (i == 126) {
                if (!this.mMediaPlayer.isPlaying()) {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            } else if (i == 86 || i == 127) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                }
                return true;
            } else {
                toggleMediaControlsVisiblity();
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    public void start() {
        if (isInPlaybackState()) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
        }
        this.mTargetState = 3;
    }

    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
        }
        this.mTargetState = 4;
    }

    public void suspend() {
        release(false);
    }

    public void resume() {
        openVideo();
    }

    public int getDuration() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getDuration();
        }
        return -1;
    }

    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void seekTo(int i) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(i);
            this.mSeekWhenPrepared = 0;
            return;
        }
        this.mSeekWhenPrepared = i;
    }

    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    private boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
    }

    public boolean canPause() {
        return this.mCanPause;
    }

    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    public int getAudioSessionId() {
        if (this.mAudioSession == 0) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mAudioSession = mediaPlayer.getAudioSessionId();
            mediaPlayer.release();
        }
        return this.mAudioSession;
    }
}
