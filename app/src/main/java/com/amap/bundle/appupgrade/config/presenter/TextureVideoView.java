package com.amap.bundle.appupgrade.config.presenter;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.opengl.GLES20;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import java.io.IOException;
import java.util.Map;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class TextureVideoView extends TextureView implements MediaPlayerControl {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private static final String TAG = "TextureVideoView";
    private int mAudioSession;
    /* access modifiers changed from: private */
    public boolean mBIsPlaying;
    private OnBufferingUpdateListener mBufferingUpdateListener;
    /* access modifiers changed from: private */
    public boolean mCanPause;
    /* access modifiers changed from: private */
    public boolean mCanSeekBack;
    /* access modifiers changed from: private */
    public boolean mCanSeekForward;
    private OnCompletionListener mCompletionListener;
    /* access modifiers changed from: private */
    public int mCurrentBufferPercentage;
    /* access modifiers changed from: private */
    public int mCurrentState;
    private OnErrorListener mErrorListener;
    private Map<String, String> mHeaders;
    private OnInfoListener mInfoListener;
    /* access modifiers changed from: private */
    public MediaController mMediaController;
    /* access modifiers changed from: private */
    public MediaPlayer mMediaPlayer;
    /* access modifiers changed from: private */
    public OnCompletionListener mOnCompletionListener;
    /* access modifiers changed from: private */
    public OnErrorListener mOnErrorListener;
    /* access modifiers changed from: private */
    public OnInfoListener mOnInfoListener;
    /* access modifiers changed from: private */
    public OnPreparedListener mOnPreparedListener;
    OnPreparedListener mPreparedListener;
    /* access modifiers changed from: private */
    public int mSeekWhenPrepared;
    private boolean mShouldRequestAudioFocus;
    OnVideoSizeChangedListener mSizeChangedListener;
    /* access modifiers changed from: private */
    public Surface mSurface;
    SurfaceTextureListener mSurfaceTextureListener;
    /* access modifiers changed from: private */
    public int mTargetState;
    private Uri mUri;
    /* access modifiers changed from: private */
    public int mVideoHeight;
    /* access modifiers changed from: private */
    public int mVideoWidth;

    public TextureVideoView(Context context) {
        this(context, null);
    }

    public TextureVideoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextureVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurface = null;
        this.mMediaPlayer = null;
        this.mShouldRequestAudioFocus = true;
        this.mBIsPlaying = false;
        this.mSizeChangedListener = new OnVideoSizeChangedListener() {
            public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
                TextureVideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
                TextureVideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
                if (TextureVideoView.this.mVideoWidth != 0 && TextureVideoView.this.mVideoHeight != 0) {
                    TextureVideoView.this.getSurfaceTexture().setDefaultBufferSize(TextureVideoView.this.mVideoWidth, TextureVideoView.this.mVideoHeight);
                    TextureVideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new OnPreparedListener() {
            public final void onPrepared(MediaPlayer mediaPlayer) {
                if (VERSION.SDK_INT >= 17) {
                    mediaPlayer.setOnInfoListener(new OnInfoListener() {
                        public final boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                            if (i == 3) {
                                TextureVideoView.this.setAlpha(1.0f);
                            }
                            return false;
                        }
                    });
                } else {
                    TextureVideoView.this.setAlpha(1.0f);
                }
                TextureVideoView.this.mCurrentState = 2;
                TextureVideoView.this.mCanPause = TextureVideoView.this.mCanSeekBack = TextureVideoView.this.mCanSeekForward = true;
                if (TextureVideoView.this.mOnPreparedListener != null) {
                    TextureVideoView.this.mOnPreparedListener.onPrepared(TextureVideoView.this.mMediaPlayer);
                }
                if (TextureVideoView.this.mMediaController != null) {
                    TextureVideoView.this.mMediaController.setEnabled(true);
                }
                TextureVideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
                TextureVideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
                int access$900 = TextureVideoView.this.mSeekWhenPrepared;
                if (access$900 != 0) {
                    TextureVideoView.this.seekTo(access$900);
                }
                if (TextureVideoView.this.mVideoWidth != 0 && TextureVideoView.this.mVideoHeight != 0) {
                    TextureVideoView.this.getSurfaceTexture().setDefaultBufferSize(TextureVideoView.this.mVideoWidth, TextureVideoView.this.mVideoHeight);
                    if (TextureVideoView.this.mTargetState == 3) {
                        TextureVideoView.this.start();
                        if (TextureVideoView.this.mMediaController != null) {
                            TextureVideoView.this.mMediaController.show();
                        }
                    } else if (!TextureVideoView.this.isPlaying() && ((access$900 != 0 || TextureVideoView.this.getCurrentPosition() > 0) && TextureVideoView.this.mMediaController != null)) {
                        TextureVideoView.this.mMediaController.show(0);
                    }
                } else if (TextureVideoView.this.mTargetState == 3) {
                    TextureVideoView.this.start();
                }
            }
        };
        this.mCompletionListener = new OnCompletionListener() {
            public final void onCompletion(MediaPlayer mediaPlayer) {
                TextureVideoView.this.mCurrentState = 5;
                TextureVideoView.this.mTargetState = 5;
                if (TextureVideoView.this.mMediaController != null) {
                    TextureVideoView.this.mMediaController.hide();
                }
                if (TextureVideoView.this.mOnCompletionListener != null) {
                    TextureVideoView.this.mOnCompletionListener.onCompletion(TextureVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new OnInfoListener() {
            public final boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                if (TextureVideoView.this.mOnInfoListener != null) {
                    TextureVideoView.this.mOnInfoListener.onInfo(mediaPlayer, i, i2);
                }
                return true;
            }
        };
        this.mErrorListener = new OnErrorListener() {
            public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                TextureVideoView.this.mCurrentState = -1;
                TextureVideoView.this.mTargetState = -1;
                if (TextureVideoView.this.mMediaController != null) {
                    TextureVideoView.this.mMediaController.hide();
                }
                return (TextureVideoView.this.mOnErrorListener == null || TextureVideoView.this.mOnErrorListener.onError(TextureVideoView.this.mMediaPlayer, i, i2)) ? true : true;
            }
        };
        this.mBufferingUpdateListener = new OnBufferingUpdateListener() {
            public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                TextureVideoView.this.mCurrentBufferPercentage = i;
            }
        };
        this.mSurfaceTextureListener = new SurfaceTextureListener() {
            public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }

            public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                boolean z = false;
                boolean z2 = TextureVideoView.this.mTargetState == 3;
                if (i > 0 && i2 > 0) {
                    z = true;
                }
                if (TextureVideoView.this.mMediaPlayer != null && z2 && z) {
                    if (TextureVideoView.this.mSeekWhenPrepared != 0) {
                        TextureVideoView.this.seekTo(TextureVideoView.this.mSeekWhenPrepared);
                    }
                    TextureVideoView.this.start();
                }
            }

            public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                TextureVideoView.this.setAlpha(0.0f);
                TextureVideoView.this.mSurface = new Surface(surfaceTexture);
                TextureVideoView.this.openVideo();
                if (TextureVideoView.this.mBIsPlaying) {
                    TextureVideoView.this.mBIsPlaying = false;
                    TextureVideoView.this.start();
                }
            }

            public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                TextureVideoView.this.setAlpha(0.0f);
                if (TextureVideoView.this.mMediaPlayer != null) {
                    TextureVideoView.this.mBIsPlaying = TextureVideoView.this.mMediaPlayer.isPlaying();
                }
                if (TextureVideoView.this.mSurface != null) {
                    TextureVideoView.this.mSurface.release();
                    TextureVideoView.this.mSurface = null;
                }
                if (TextureVideoView.this.mMediaController != null) {
                    TextureVideoView.this.mMediaController.hide();
                }
                TextureVideoView.this.release(true);
                return true;
            }
        };
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        setSurfaceTextureListener(this.mSurfaceTextureListener);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = 0;
        this.mTargetState = 0;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006e, code lost:
        if (r1 > r6) goto L_0x0094;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r6, int r7) {
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
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.appupgrade.config.presenter.TextureVideoView.onMeasure(int, int):void");
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(TextureVideoView.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(TextureVideoView.class.getName());
    }

    public int resolveAdjustedSize(int i, int i2) {
        return getDefaultSize(i, i2);
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
            if (this.mShouldRequestAudioFocus) {
                AudioManager audioManager = (AudioManager) getContext().getApplicationContext().getSystemService("audio");
                if (audioManager != null) {
                    audioManager.abandonAudioFocus(null);
                }
            }
        }
        clearSurface();
    }

    private void clearSurface() {
        if (this.mSurface != null && VERSION.SDK_INT >= 16) {
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            egl10.eglInitialize(eglGetDisplay, null);
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            egl10.eglChooseConfig(eglGetDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344, 0, 12344}, eGLConfigArr, 1, new int[1]);
            EGLConfig eGLConfig = eGLConfigArr[0];
            EGLContext eglCreateContext = egl10.eglCreateContext(eglGetDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
            EGLSurface eglCreateWindowSurface = egl10.eglCreateWindowSurface(eglGetDisplay, eGLConfig, this.mSurface, new int[]{12344});
            egl10.eglMakeCurrent(eglGetDisplay, eglCreateWindowSurface, eglCreateWindowSurface, eglCreateContext);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            egl10.eglSwapBuffers(eglGetDisplay, eglCreateWindowSurface);
            egl10.eglDestroySurface(eglGetDisplay, eglCreateWindowSurface);
            EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
            egl10.eglMakeCurrent(eglGetDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
            egl10.eglDestroyContext(eglGetDisplay, eglCreateContext);
            egl10.eglTerminate(eglGetDisplay);
        }
    }

    /* access modifiers changed from: private */
    public void openVideo() {
        if (this.mUri != null && this.mSurface != null) {
            release(false);
            if (this.mShouldRequestAudioFocus) {
                AudioManager audioManager = (AudioManager) getContext().getApplicationContext().getSystemService("audio");
                if (audioManager != null) {
                    audioManager.requestAudioFocus(null, 3, 1);
                }
            }
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
            if (this.mShouldRequestAudioFocus) {
                AudioManager audioManager = (AudioManager) getContext().getApplicationContext().getSystemService("audio");
                if (audioManager != null) {
                    audioManager.abandonAudioFocus(null);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisibility();
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisibility();
        }
        return false;
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
                toggleMediaControlsVisibility();
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    private void toggleMediaControlsVisibility() {
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

    public void setShouldRequestAudioFocus(boolean z) {
        this.mShouldRequestAudioFocus = z;
    }

    public boolean shouldRequestAudioFocus() {
        return this.mShouldRequestAudioFocus;
    }
}
