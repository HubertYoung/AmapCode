package tv.danmaku.ijk.media.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.alipaylogger.Log;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.VideoPlayParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnPlayErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnProgressUpdateListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory.Request;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CacheUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoDeviceWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoFileManager;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.diskcache.utils.LogHelper;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.multimedia.gles.EglCore10;
import com.alipay.multimedia.gles.FullFrameRect;
import com.alipay.multimedia.gles.GlUtil;
import com.alipay.multimedia.gles.Texture2dProgram;
import com.alipay.multimedia.gles.Texture2dProgram.ProgramType;
import com.alipay.multimedia.gles.WindowSurface10;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.sdk.sys.a;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.video.VideoInfo;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnDownloadStatusListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnSeekCompleteListener;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@TargetApi(14)
public class UrlPlayViewImpl extends SightPlayView implements OnFrameAvailableListener, SurfaceTextureListener, OnBufferingUpdateListener, OnCompletionListener, OnDownloadStatusListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener {
    private static final int MSG_BITMAP = 7;
    private static final int MSG_CACHE = 13;
    private static final int MSG_CHECK_PROGRESS = 14;
    private static final int MSG_FLASH = 6;
    private static final int MSG_FRAME_AVAILABLE = 9;
    private static final int MSG_MUTE = 16;
    private static final int MSG_PARSE_PARAM = 12;
    private static final int MSG_PAUSE = 11;
    private static final int MSG_PREPARE = 0;
    private static final int MSG_PRO_QUIT = 15;
    private static final int MSG_QUIT = 8;
    private static final int MSG_RELEASE = 4;
    private static final int MSG_RESET = 3;
    private static final int MSG_RESUME = 1;
    private static final int MSG_SEEK = 10;
    private static final int MSG_THUMB = 5;
    private static final String TAG = "UrlPlayViewImpl";
    private static final AtomicBoolean adjustLock = new AtomicBoolean();
    private static AtomicBoolean mAudioPaused = new AtomicBoolean(false);
    private boolean VERBOSE = false;
    private boolean bLog = true;
    private Boolean hardDecodeSwitch;
    private long logTime = 0;
    private boolean mAutoFitCenter = false;
    private String mBizId;
    private long mBlockTime = 0;
    private SightVideoPlayView.OnBufferingUpdateListener mBufferingListener;
    private boolean mCacheDone = false;
    private String mCachePath;
    private boolean mCenterCropFixed = false;
    private boolean mCenterCropped = false;
    private boolean mCheckProgress = false;
    private String mCloudId = null;
    private SightVideoPlayView.OnCompletionListener mCompletionListener;
    private long mCurPlayTime = 0;
    private int mDegree = 0;
    private int mDisplayHeight;
    /* access modifiers changed from: private */
    public WindowSurface10 mDisplaySurface;
    private int mDisplayWidth;
    private int mEffect = VideoPlayParams.EFFECT_DEFAULT;
    private EglCore10 mEglCore;
    private boolean mEnableAudio = true;
    private boolean mEnableCache = false;
    private int mErrCode = 0;
    private OnPlayErrorListener mErrorListener;
    private long mFirstFrameTime = 0;
    private int mFrameIndex = 0;
    private FullFrameRect mFullFrameBlit;
    private FullFrameRect mFullThumbBlit;
    private int mH;
    /* access modifiers changed from: private */
    public PlayHandler mHandler;
    /* access modifiers changed from: private */
    public final Object mHandlerLock = new Object();
    private int mImgTexId;
    private boolean mIsLoop = false;
    private boolean mKeepScreenOn = false;
    private IjkMediaPlayer mMediaPlayer;
    private boolean mNeedReport = false;
    private boolean mNoFrameCome = true;
    private SightVideoPlayView.OnInfoListener mOnInfoListener;
    private Paint mPaint;
    private boolean mPaused = false;
    private ParcelFileDescriptor mPfd;
    private String mPlayUrl;
    private SightVideoPlayView.OnPreparedListener mPrepareListener;
    private Object mPrepareLock = new Object();
    private boolean mPreparing = false;
    /* access modifiers changed from: private */
    public ProgressHandler mProHandler;
    /* access modifiers changed from: private */
    public HandlerThread mProThread;
    private int mProgressInterval = 500;
    private OnProgressUpdateListener mProgressListener;
    /* access modifiers changed from: private */
    public final Object mQuitLock = new Object();
    private SightVideoPlayView.OnSeekCompleteListener mSeekListener;
    private int mSeekWhenResume = -1;
    private long mStartTime = 0;
    private boolean mStarted = false;
    private long mStopTime = 0;
    private Surface mSurface;
    /* access modifiers changed from: private */
    public final Object mSurfaceLock = new Object();
    /* access modifiers changed from: private */
    public SurfaceTexture mSurfaceTexture = null;
    private int mTextureId;
    /* access modifiers changed from: private */
    public HandlerThread mThread;
    private final float[] mTmpMatrix = new float[16];
    private int mVideoHeight = 0;
    private String mVideoId;
    private SurfaceTexture mVideoTexture;
    private int mVideoWidth = 0;
    private int mW;
    private int mX;
    private int mY;

    private static class PlayHandler extends Handler {
        private Looper mLooper;
        private WeakReference<UrlPlayViewImpl> mReference;

        PlayHandler(UrlPlayViewImpl view, Looper looper) {
            super(looper);
            this.mLooper = looper;
            this.mReference = new WeakReference<>(view);
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void checkSurface() {
            /*
                r6 = this;
                java.lang.ref.WeakReference<tv.danmaku.ijk.media.widget.UrlPlayViewImpl> r2 = r6.mReference
                java.lang.Object r1 = r2.get()
                tv.danmaku.ijk.media.widget.UrlPlayViewImpl r1 = (tv.danmaku.ijk.media.widget.UrlPlayViewImpl) r1
                if (r1 == 0) goto L_0x0038
                java.lang.Object r3 = r1.mSurfaceLock
                monitor-enter(r3)
                android.graphics.SurfaceTexture r2 = r1.mSurfaceTexture     // Catch:{ all -> 0x0045 }
                if (r2 != 0) goto L_0x0037
                java.lang.String r2 = "UrlPlayViewImpl"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0045 }
                r4.<init>()     // Catch:{ all -> 0x0045 }
                java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ all -> 0x0045 }
                java.lang.String r5 = "checkSurface and surface not ready"
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0045 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0045 }
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0045 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r2, r4, r5)     // Catch:{ all -> 0x0045 }
                java.lang.Object r2 = r1.mSurfaceLock     // Catch:{ InterruptedException -> 0x0039 }
                r2.wait()     // Catch:{ InterruptedException -> 0x0039 }
            L_0x0037:
                monitor-exit(r3)     // Catch:{ all -> 0x0045 }
            L_0x0038:
                return
            L_0x0039:
                r0 = move-exception
                java.lang.String r2 = "UrlPlayViewImpl"
                java.lang.String r4 = ""
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0045 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r2, r4, r0, r5)     // Catch:{ all -> 0x0045 }
                goto L_0x0037
            L_0x0045:
                r2 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0045 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.widget.UrlPlayViewImpl.PlayHandler.checkSurface():void");
        }

        public void handleMessage(Message msg) {
            boolean z = false;
            UrlPlayViewImpl playTextureView = (UrlPlayViewImpl) this.mReference.get();
            if (playTextureView == null) {
                Logger.D(UrlPlayViewImpl.TAG, "outter class is null", new Object[0]);
                return;
            }
            if (msg.what != 9) {
                Logger.D(UrlPlayViewImpl.TAG, playTextureView + " handle msg: " + msg.what, new Object[0]);
            }
            switch (msg.what) {
                case 0:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mSurfaceTexture);
                    playTextureView.handlePrepare();
                    return;
                case 1:
                    playTextureView.handleResume();
                    return;
                case 3:
                    playTextureView.handleReset();
                    return;
                case 4:
                    playTextureView.handleRelease();
                    return;
                case 5:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mSurfaceTexture);
                    playTextureView.handleDrawBitmap(playTextureView.getThumbnail());
                    return;
                case 6:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mSurfaceTexture);
                    playTextureView.drawEndFlash();
                    return;
                case 7:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mSurfaceTexture);
                    playTextureView.handleDrawBitmap((Bitmap) msg.obj);
                    return;
                case 8:
                    try {
                        this.mLooper.quit();
                        synchronized (playTextureView.mHandlerLock) {
                            playTextureView.mHandler = null;
                            playTextureView.mThread = null;
                        }
                    } catch (Exception e) {
                        Logger.E((String) UrlPlayViewImpl.TAG, (String) "", (Throwable) e, new Object[0]);
                    }
                    playTextureView.releaseGl();
                    synchronized (playTextureView.mQuitLock) {
                        playTextureView.mQuitLock.notifyAll();
                        Logger.D(UrlPlayViewImpl.TAG, "mQuitLock notifyAll", new Object[0]);
                    }
                    return;
                case 9:
                    playTextureView.handleFrameAvailable();
                    return;
                case 10:
                    playTextureView.handleSeek(msg.arg1);
                    return;
                case 11:
                    playTextureView.handlePause();
                    return;
                case 12:
                    playTextureView.handleParseParams((VideoPlayParams) msg.obj);
                    return;
                case 13:
                    playTextureView.handleCache();
                    return;
                case 16:
                    if (msg.arg1 != 0) {
                        z = true;
                    }
                    playTextureView.handleMute(z);
                    return;
                default:
                    return;
            }
        }
    }

    private static class ProgressHandler extends Handler {
        private Looper mLooper;
        private WeakReference<UrlPlayViewImpl> mReference;

        ProgressHandler(UrlPlayViewImpl view, Looper looper) {
            super(looper);
            this.mLooper = looper;
            this.mReference = new WeakReference<>(view);
        }

        public void handleMessage(Message msg) {
            UrlPlayViewImpl playTextureView = (UrlPlayViewImpl) this.mReference.get();
            if (playTextureView == null) {
                Logger.D(UrlPlayViewImpl.TAG, "outter class is null", new Object[0]);
                return;
            }
            switch (msg.what) {
                case 14:
                    try {
                        playTextureView.handleCheckProgress();
                        return;
                    } catch (Exception e) {
                        Logger.E((String) UrlPlayViewImpl.TAG, (String) "handleCheckProgress exp:", (Throwable) e, new Object[0]);
                        return;
                    }
                case 15:
                    try {
                        playTextureView.stopCheckProgress();
                        this.mLooper.quit();
                        playTextureView.mProHandler = null;
                        playTextureView.mProThread = null;
                    } catch (Exception e2) {
                        Logger.E((String) UrlPlayViewImpl.TAG, (String) "", (Throwable) e2, new Object[0]);
                    }
                    Logger.D(UrlPlayViewImpl.TAG, "MSG_PRO_QUIT handle end.", new Object[0]);
                    return;
                default:
                    return;
            }
        }
    }

    public void setVideoRotation(int degree) {
        if (degree % 90 != 0) {
            throw new RuntimeException("degree is invalid.");
        }
        this.mDegree = degree;
    }

    public UrlPlayViewImpl(Context context) {
        super(context);
        init();
    }

    private void init() {
        Logger.D(TAG, "url video play view init", new Object[0]);
        setSurfaceTextureListener(this);
        setOpaque(false);
        setScaleX(1.00001f);
        setScaleY(1.00001f);
        this.mPaint = new Paint();
        this.mPaint.setFilterBitmap(true);
        generateMVPMatrix();
    }

    public void setCenterCropped(int videoWidth, int videoHeight, int displayWidth, int displayHeight) {
        if (!this.mAutoFitCenter) {
            this.mCenterCropFixed = true;
            this.mCenterCropped = true;
            this.mVideoHeight = videoHeight;
            this.mVideoWidth = videoWidth;
            this.mDisplayHeight = displayHeight;
            this.mDisplayWidth = displayWidth;
        }
    }

    public void setCenterCropped() {
        LogHelper.i(TAG, "setCenterCropped and mAutoFitCenter is " + this.mAutoFitCenter);
        if (!this.mAutoFitCenter) {
            this.mCenterCropped = true;
        }
    }

    public void setAutoFitCenter(boolean fit) {
        LogHelper.i(TAG, "setAutoFitCenter " + fit);
        this.mAutoFitCenter = fit;
    }

    public boolean isAutoFitCenter() {
        return this.mAutoFitCenter;
    }

    private void generateMVPMatrix() {
        Matrix.setIdentityM(this.mTmpMatrix, 0);
        this.mTmpMatrix[5] = -this.mTmpMatrix[5];
        this.mTmpMatrix[13] = 1.0f - this.mTmpMatrix[13];
    }

    private synchronized void generateViewport() {
        if (!this.mAutoFitCenter) {
            this.mY = 0;
            this.mX = 0;
            this.mW = getWidth();
            this.mH = getHeight();
            Logger.D(TAG, this + "\tmW:" + this.mW + "mH:" + this.mH, new Object[0]);
        } else if (!(this.mVideoHeight == 0 || this.mVideoWidth == 0)) {
            if (this.mVideoHeight * getWidth() <= this.mVideoWidth * getHeight()) {
                this.mW = getWidth();
                this.mH = (this.mW * this.mVideoHeight) / this.mVideoWidth;
                this.mX = 0;
                this.mY = (getHeight() - this.mH) / 2;
            } else {
                this.mH = getHeight();
                this.mW = (this.mH * this.mVideoWidth) / this.mVideoHeight;
                this.mY = 0;
                this.mX = (getWidth() - this.mW) / 2;
            }
        }
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (this.VERBOSE) {
            Logger.D(TAG, "onFrameAvailable", new Object[0]);
        }
        this.mHandler.sendEmptyMessage(9);
    }

    /* access modifiers changed from: private */
    public void handleSetSurfaceTexture(SurfaceTexture surface) {
        Logger.D(TAG, this + "\thandleSetSurfaceTexture", new Object[0]);
        setExceptionHandler();
        try {
            if (this.mEglCore == null) {
                Logger.D(TAG, "handleSetSurfaceTexture mEglCore", new Object[0]);
                this.mEglCore = new EglCore10();
                this.mDisplaySurface = new WindowSurface10(this.mEglCore, surface);
                this.mDisplaySurface.makeCurrent();
                if (this.mEffect == VideoPlayParams.EFFECT_TRANSPARENT) {
                    this.mFullFrameBlit = new FullFrameRect(new Texture2dProgram(ProgramType.TEXTURE_EXT_TRANSPARENT));
                } else {
                    this.mFullFrameBlit = new FullFrameRect(new Texture2dProgram(ProgramType.TEXTURE_EXT));
                }
                this.mTextureId = this.mFullFrameBlit.createTextureObject();
                this.mFullThumbBlit = new FullFrameRect(new Texture2dProgram(ProgramType.TEXTURE_2D));
                this.mVideoTexture = new SurfaceTexture(this.mTextureId);
                this.mSurface = new Surface(this.mVideoTexture);
                this.mVideoTexture.setOnFrameAvailableListener(this);
            }
            if (this.mDisplaySurface == null) {
                Logger.D(TAG, "handleSetSurfaceTexture mDisplaySurface", new Object[0]);
                this.mDisplaySurface = new WindowSurface10(this.mEglCore, surface);
                this.mDisplaySurface.makeCurrent();
            }
        } catch (Exception e) {
            Logger.E(TAG, e.getMessage(), new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void handleFrameAvailable() {
        if (this.mEglCore == null) {
            Logger.D(TAG, "Skipping drawFrame after shutdown", new Object[0]);
            return;
        }
        this.mVideoTexture.updateTexImage();
        this.mFrameIndex++;
        if (this.mFrameIndex % 30 == 0) {
            Logger.D(TAG, "handleFrameAvailable", new Object[0]);
        }
        this.mVideoTexture.getTransformMatrix(this.mTmpMatrix);
        this.mDisplaySurface.makeCurrent();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16384);
        if (VideoPlayParams.EFFECT_TRANSPARENT == this.mEffect && !getHardDecodeConfigSwitch()) {
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(770, 771);
        }
        GLES20.glViewport(this.mX, this.mY, this.mW, this.mH);
        if (!this.mCenterCropped) {
            if (VideoPlayParams.EFFECT_TRANSPARENT == this.mEffect) {
                this.mFullFrameBlit.drawFrameTransparent(this.mTextureId, this.mTmpMatrix, GlUtil.IDENTITY_MATRIX);
            } else {
                this.mFullFrameBlit.drawFrame(this.mTextureId, this.mTmpMatrix, GlUtil.getRotateMatrix(this.mDegree));
            }
        } else if (VideoPlayParams.EFFECT_TRANSPARENT == this.mEffect) {
            this.mFullFrameBlit.drawFrameTransparent(this.mTextureId, this.mTmpMatrix, this.mVideoWidth, this.mVideoHeight, this.mDisplayWidth, this.mDisplayHeight);
        } else {
            this.mFullFrameBlit.drawCroppedFrame(this.mTextureId, this.mTmpMatrix, this.mVideoWidth, this.mVideoHeight, this.mDisplayWidth, this.mDisplayHeight);
        }
        this.mDisplaySurface.swapBuffers();
        if (this.mOnInfoListener != null && this.mNoFrameCome && !this.mPaused && this.mStarted) {
            this.mOnInfoListener.onInfo(3, null);
            this.mNoFrameCome = false;
            Logger.D(TAG, this + "video go to playing state.", new Object[0]);
        }
    }

    private void adjustVideoSize() {
        if (this.mDegree == 270 || this.mDegree == 90) {
            int tmp = this.mVideoHeight;
            this.mVideoHeight = this.mVideoWidth;
            this.mVideoWidth = tmp;
        }
    }

    /* access modifiers changed from: private */
    public void handleDrawBitmap(Bitmap bitmap) {
        long ts = System.currentTimeMillis();
        generateMVPMatrix();
        if (!(bitmap == null || this.mFullThumbBlit == null)) {
            this.mVideoWidth = bitmap.getWidth();
            this.mVideoHeight = bitmap.getHeight();
            adjustVideoSize();
            generateViewport();
            try {
                this.mDisplaySurface.makeCurrent();
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES20.glClear(16384);
                GLES20.glViewport(this.mX, this.mY, this.mW, this.mH);
                this.mImgTexId = this.mFullThumbBlit.createImageTexture(bitmap, this.mEglCore.getGl10());
                if (!this.mCenterCropped) {
                    this.mFullThumbBlit.drawFrame(this.mImgTexId, this.mTmpMatrix, GlUtil.getRotateMatrix(this.mDegree));
                } else {
                    this.mFullThumbBlit.drawCroppedFrame(this.mImgTexId, this.mTmpMatrix, this.mVideoWidth, this.mVideoHeight, this.mDisplayWidth, this.mDisplayHeight);
                }
                this.mDisplaySurface.swapBuffers();
                this.mFullThumbBlit.freeImageTexture(this.mImgTexId);
            } catch (Exception e) {
                Logger.E(TAG, e.getMessage(), new Object[0]);
            }
        }
        Logger.D(TAG, this + "\tdraw bitmap took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
    }

    /* access modifiers changed from: private */
    public void drawEndFlash() {
    }

    public void setVideoParams(VideoPlayParams params) {
        Logger.D(TAG, this + "\tsetVideoParams:" + params, new Object[0]);
        Message msg = Message.obtain();
        msg.what = 12;
        msg.obj = params;
        checkHandler();
        if (this.mHandler != null) {
            this.mHandler.removeMessages(12);
            this.mHandler.sendMessage(msg);
        }
    }

    public void setVideoId(String id) {
        if (PathUtils.extractFile(id) != null) {
            String id2 = PathUtils.extractFile(id).getAbsolutePath();
            this.mPlayUrl = id2;
            this.mVideoId = id2;
            Logger.D(TAG, this + "setVideoId: " + id2, new Object[0]);
        } else if (PathUtils.isHttp(id) || PathUtils.isRtmp(id)) {
            this.mPlayUrl = id;
            this.mVideoId = id;
            Logger.D(TAG, this + "setVideoId: " + id, new Object[0]);
        } else {
            adjustLock.set(false);
            this.mVideoId = id;
            if (SandboxWrapper.isContentUriPath(this.mVideoId)) {
                this.mPlayUrl = this.mVideoId;
            } else {
                this.mPlayUrl = VideoFileManager.getInstance().getVideoPathById(id);
            }
            Logger.D(TAG, this + "\tsetVideoId: " + id, new Object[0]);
        }
    }

    public String getVideoId() {
        return this.mVideoId;
    }

    public boolean isPlaying() {
        return this.mMediaPlayer != null && this.mStarted && this.mMediaPlayer.isPlaying();
    }

    public boolean isPlayingOK() {
        return this.mStarted;
    }

    public void setOnCompletionListener(SightVideoPlayView.OnCompletionListener listener) {
        Logger.D(TAG, "setOnCompletionListener: " + listener, new Object[0]);
        this.mCompletionListener = listener;
    }

    public void setOnInfoListener(SightVideoPlayView.OnInfoListener listener) {
        this.mOnInfoListener = listener;
    }

    public void setOnErrorListener(OnPlayErrorListener listener) {
        Logger.D(TAG, "setOnErrorListener: " + listener, new Object[0]);
        this.mErrorListener = listener;
    }

    public void setOnBufferingUpdateListener(SightVideoPlayView.OnBufferingUpdateListener listener) {
        this.mBufferingListener = listener;
    }

    public void setOnPreparedListener(SightVideoPlayView.OnPreparedListener listener) {
        this.mPrepareListener = listener;
    }

    public void setOnSeekCompleteListener(SightVideoPlayView.OnSeekCompleteListener listener) {
        this.mSeekListener = listener;
    }

    public void onPrepared(IMediaPlayer mp) {
        Logger.D(TAG, this + " prepare done Url: " + this.mPlayUrl + "\tcurrent time: " + this.mCurPlayTime, new Object[0]);
        UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_PLAY_VD, "0");
        prepareDone();
    }

    private void prepareDone() {
        if (!this.mStarted) {
            this.mVideoWidth = this.mMediaPlayer.getVideoWidth();
            this.mVideoHeight = this.mMediaPlayer.getVideoHeight();
            if (this.mEffect == VideoPlayParams.EFFECT_TRANSPARENT) {
                this.mVideoWidth /= 2;
            }
            adjustVideoSize();
            LogHelper.i(TAG, "prepareDone, w*h:" + this.mVideoWidth + "*" + this.mVideoHeight);
            generateViewport();
            this.mMediaPlayer.start();
            if (this.mCurPlayTime > 0) {
                this.mMediaPlayer.seekTo(this.mCurPlayTime);
            }
            this.mStarted = true;
            if (mAudioPaused.compareAndSet(false, true)) {
                AudioUtils.pauseSystemAudio();
            }
            this.mPreparing = false;
            startCheckProgress();
            if (this.mPrepareListener != null) {
                Bundle bundle = new Bundle();
                Long dur = Long.valueOf(this.mMediaPlayer.getDuration());
                Logger.D(TAG, "prepareDone duration:" + dur, new Object[0]);
                bundle.putLong("duration", dur.longValue());
                this.mPrepareListener.onPrepared(bundle);
            }
        }
    }

    public void onCompletion(IMediaPlayer mp) {
        Logger.D(TAG, this + "\tonCompletion,cb is" + this.mCompletionListener + ", mIsLoop:" + this.mIsLoop, new Object[0]);
        if (this.mCompletionListener != null && this.mStarted) {
            this.mCompletionListener.onCompletion(null);
        }
        if (this.mSurfaceTexture != null && this.mIsLoop && this.mStarted) {
            this.mHandler.sendEmptyMessage(3);
            this.mHandler.sendEmptyMessage(0);
        } else if (VideoPlayParams.EFFECT_TRANSPARENT != this.mEffect && mAudioPaused.compareAndSet(true, false)) {
            AudioUtils.resumeSystemAudio();
        }
    }

    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
        if (this.mBufferingListener != null) {
            this.mBufferingListener.onBufferingUpdate(percent, null);
        }
    }

    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        Logger.D(TAG, this + " onInfo, what: " + what, new Object[0]);
        if (this.mOnInfoListener != null && !TextUtils.isEmpty(this.mCachePath) && what == 701) {
            this.mNoFrameCome = true;
            Bundle bundle = new Bundle();
            bundle.putInt("extra", extra);
            this.mOnInfoListener.onInfo(what, bundle);
            if (this.mFrameIndex > 0) {
                this.mBlockTime++;
            }
        }
        if (what == 10002 || what == 3) {
            prepareDone();
            Logger.D(TAG, this + "onInfo MEDIA_INFO_VIDEO_RENDERING_START", new Object[0]);
            this.mFirstFrameTime = System.currentTimeMillis();
        }
        return false;
    }

    public void OnDownloadStatus(IMediaPlayer mp, int what) {
        Logger.D(TAG, this + " OnDownloadStatus, code:" + what, new Object[0]);
        if (what == 1) {
            if (this.mEnableCache) {
                addCache();
            }
        } else if (!TextUtils.isEmpty(this.mCachePath)) {
            File f = new File(this.mCachePath);
            if (f.exists()) {
                Logger.D(TAG, "delete file:" + f.getAbsolutePath() + "ret:" + f.delete(), new Object[0]);
            }
        }
    }

    public void onSeekComplete(IMediaPlayer mp) {
        Logger.D(TAG, this + " onSeekComplete", new Object[0]);
        if (this.mSeekListener != null) {
            this.mSeekListener.onSeekComplete(null);
        }
        startCheckProgress();
    }

    public boolean onError(IMediaPlayer mp, int what, int extra) {
        Logger.D(TAG, this + " onError:" + what + "," + extra + " file: " + this.mPlayUrl + ", mVideoId: " + this.mVideoId, new Object[0]);
        this.mStarted = false;
        this.mPreparing = false;
        this.mErrCode = what;
        if (this.mErrorListener != null) {
            this.mErrorListener.onError(what, this.mVideoId);
        }
        FileCacheModel videoInfo = VideoFileManager.getInstance().getVideoInfo(this.mVideoId);
        if (videoInfo == null || (videoInfo.tag & 4096) == 0) {
            UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_PLAY_VD, String.valueOf(what));
        }
        if (PathUtils.extractFile(this.mPlayUrl) != null) {
            Logger.D(TAG, "delete broken file:" + this.mPlayUrl + "ret:" + new File(this.mPlayUrl).delete(), new Object[0]);
        }
        try {
            clearIncompleteCache();
        } catch (Exception e) {
            Logger.E((String) TAG, (String) "onError and clearIncompleteCache", (Throwable) e, new Object[0]);
        }
        this.mStopTime = System.currentTimeMillis();
        reportEvent();
        return false;
    }

    private void addCache() {
        this.mHandler.removeMessages(13);
        this.mHandler.sendEmptyMessage(13);
    }

    public void pause() {
        if (!this.mStarted || this.mPreparing) {
            Logger.D(TAG, "pause before started", new Object[0]);
            return;
        }
        this.mHandler.removeMessages(11);
        this.mHandler.sendEmptyMessage(11);
    }

    public void resume() {
        if (!this.mStarted || this.mPreparing) {
            Logger.D(TAG, "resume before started", new Object[0]);
            return;
        }
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessage(1);
    }

    public void seekTo(long msec) {
        if (!this.mStarted || this.mPreparing) {
            Logger.D(TAG, "seek before started", new Object[0]);
            return;
        }
        this.mHandler.removeMessages(10);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(10, (int) msec, 0));
    }

    public void start() {
        Logger.D(TAG, this + "\tstart###", new Object[0]);
        checkHandler();
        startInternal();
    }

    public void start(int msec) {
        this.mCurPlayTime = (long) msec;
        start();
    }

    public void start(String id, int msec) {
        Logger.D(TAG, this + "\tstart###id=" + id + ", msec=" + msec, new Object[0]);
        setVideoId(id);
        checkHandler();
        this.mCurPlayTime = (long) msec;
        startInternal();
    }

    public void start(String id) {
        Logger.D(TAG, this + "\tstart###id=" + id, new Object[0]);
        setVideoId(id);
        checkHandler();
        startInternal();
    }

    private void startInternal() {
        if (this.mPreparing) {
            Logger.D(TAG, "preparing, skip start", new Object[0]);
            return;
        }
        this.mPreparing = true;
        this.mStarted = false;
        this.mErrCode = 0;
        if (this.mMediaPlayer == null || !this.mMediaPlayer.isPlaying()) {
            drawThumbnail();
        }
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessage(0);
    }

    public long getCurrentPosition() {
        Logger.D(TAG, this + "\tgetCurrentPosition###", new Object[0]);
        if (this.mMediaPlayer != null && this.mStarted) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        Logger.D(TAG, this + "\tgetCurrentPosition = -1", new Object[0]);
        return -1;
    }

    public long getDuration() {
        if (TextUtils.isEmpty(this.mPlayUrl)) {
            Logger.D(TAG, "getDuration mPlayUrl is null", new Object[0]);
            return 0;
        } else if (this.mMediaPlayer != null && this.mStarted) {
            return this.mMediaPlayer.getDuration();
        } else {
            Logger.W(TAG, "getDuration called before started, get from IO", new Object[0]);
            int dur = getVideoInfo().duration;
            if (dur <= 0) {
                Logger.D(TAG, "getDuration dur=" + dur, new Object[0]);
            }
            return (long) dur;
        }
    }

    private VideoInfo getVideoInfo() {
        VideoUtils.loadLibrariesOnce();
        try {
            return MMNativeEngineApi.getVideoInfo(this.mPlayUrl);
        } catch (MMNativeException e) {
            Logger.E((String) TAG, "getVideoInfo code=" + e.getCode(), (Throwable) e, new Object[0]);
            return new VideoInfo();
        }
    }

    public void setLooping(boolean loop) {
        this.mIsLoop = loop;
    }

    public void setMute(boolean mute) {
        if (!this.mStarted || this.mPreparing) {
            Logger.D(TAG, "setMute before started", new Object[0]);
            return;
        }
        this.mHandler.removeMessages(16);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(16, mute ? 1 : 0, 0));
    }

    /* access modifiers changed from: protected */
    public Point getTouchPoint(int x, int y) {
        if (!this.mStarted) {
            return null;
        }
        return VideoUtils.calculatePoint(x, y, this.mDisplayWidth, this.mDisplayHeight, this.mVideoWidth, this.mVideoHeight);
    }

    public void setScreenOnWhilePlaying(boolean screenOn) {
        this.mKeepScreenOn = screenOn;
    }

    public void stop() {
        if (this.mStarted || this.mPreparing) {
            Logger.D(TAG, this + "\tstop###", new Object[0]);
            this.mCurPlayTime = 0;
            this.mPreparing = false;
            if (this.mHandler != null) {
                this.mHandler.removeMessages(3);
                this.mHandler.sendEmptyMessage(3);
            }
            if (mAudioPaused.compareAndSet(true, false)) {
                AudioUtils.resumeSystemAudio();
                return;
            }
            return;
        }
        Logger.D(TAG, "invalid stop, skip###", new Object[0]);
    }

    public void drawThumbnail() {
        if (this.mCurPlayTime != 0) {
            Log.i(TAG, "skip draw thumb if not start from beginning.");
            return;
        }
        checkHandler();
        this.mHandler.removeMessages(5);
        this.mHandler.sendEmptyMessage(5);
    }

    public void drawBitmap(Bitmap bitmap) {
        Logger.D(TAG, this + "\tdrawBitmap bitmap: " + bitmap, new Object[0]);
        checkHandler();
        Message msg = Message.obtain();
        msg.obj = bitmap;
        msg.what = 7;
        if (this.mHandler != null) {
            this.mHandler.sendMessage(msg);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        Logger.D(TAG, this + "\tonAttachedToWindow", new Object[0]);
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        Logger.D(TAG, this + "\tonDetachedFromWindow mMediaPlayer: " + this.mMediaPlayer, new Object[0]);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: private */
    public void handleRelease() {
        this.mNoFrameCome = true;
        this.mPaused = false;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        closeParcelFD();
        if (mAudioPaused.compareAndSet(true, false)) {
            AudioUtils.resumeSystemAudio();
        }
        try {
            clearIncompleteCache();
        } catch (Exception e) {
            Logger.E((String) TAG, (String) "clearIncompleteCache", (Throwable) e, new Object[0]);
        }
        Logger.D(TAG, this + "mediaplayer release done.", new Object[0]);
    }

    /* access modifiers changed from: private */
    public void handlePrepare() {
        initBehavior();
        try {
            if (!this.mStarted || this.mMediaPlayer == null) {
                Logger.D(TAG, this + " handlePrepare begin, path:" + this.mPlayUrl + ", mMediaPlayer: " + this.mMediaPlayer, new Object[0]);
                if (this.mMediaPlayer != null) {
                    Logger.D(TAG, "clear previous mediaplayer", new Object[0]);
                    this.mMediaPlayer.reset();
                    this.mMediaPlayer.release();
                    this.mMediaPlayer = null;
                }
                closeParcelFD();
                this.mFrameIndex = 0;
                this.mMediaPlayer = new IjkMediaPlayer();
                this.mMediaPlayer.setOnCompletionListener(this);
                this.mMediaPlayer.setOnErrorListener(this);
                this.mMediaPlayer.setOnBufferingUpdateListener(this);
                this.mMediaPlayer.setOnPreparedListener(this);
                this.mMediaPlayer.setOnSeekCompleteListener(this);
                this.mMediaPlayer.setOnInfoListener(this);
                this.mMediaPlayer.setOnDownloadStatusListener(this);
                String path = VideoFileManager.getInstance().getVideoPathById(this.mPlayUrl);
                if (PathUtils.extractFile(this.mPlayUrl) != null) {
                    this.mVideoId = this.mPlayUrl;
                    this.mEnableCache = false;
                } else if (SandboxWrapper.checkFileExist(this.mPlayUrl)) {
                    this.mEnableCache = false;
                } else {
                    if (path != null) {
                        if (new File(path).exists()) {
                            this.mPlayUrl = path;
                            this.mVideoId = path;
                            this.mEnableCache = false;
                        }
                    }
                    this.mEnableCache = true;
                }
                this.mCachePath = null;
                this.mNoFrameCome = true;
                if (this.mEnableCache && FileUtils.isSDcardAvailableSpace(ConfigManager.getInstance().diskConf().urlVideoNeedSpace)) {
                    this.mCachePath = VideoFileManager.getInstance().getDiskCache().genPathByKey(this.mPlayUrl);
                    this.mMediaPlayer.setOption(4, (String) "data-copy", this.mCachePath);
                    Logger.D(TAG, "###enable cache and path is:" + this.mCachePath, new Object[0]);
                    if (this.mOnInfoListener != null) {
                        this.mOnInfoListener.onInfo(701, null);
                    }
                    this.mCacheDone = false;
                }
                if (!this.mEnableAudio) {
                    this.mMediaPlayer.setOption(4, (String) a.i, 1);
                }
                if (VideoPlayParams.EFFECT_TRANSPARENT == this.mEffect && getHardDecodeConfigSwitch()) {
                    this.mMediaPlayer.setOption(4, (String) "mediacodec", 1);
                    Logger.D(TAG, "open hard decode switch", new Object[0]);
                }
                setDataSource();
                Logger.D(TAG, "after setDataSource", new Object[0]);
                this.mMediaPlayer.setSurface(this.mSurface);
                Logger.D(TAG, "after setDisplay", new Object[0]);
                if (this.mKeepScreenOn) {
                    Logger.D(TAG, this + "setWakeMode", new Object[0]);
                    this.mMediaPlayer.setWakeMode(getContext(), 536870922);
                }
                Logger.D(TAG, "after setScreenOnWhilePlaying", new Object[0]);
                this.mMediaPlayer.prepareAsync();
                Logger.D(TAG, "after prepare", new Object[0]);
                this.mSeekWhenResume = -1;
                return;
            }
            this.mMediaPlayer.start();
            Logger.D(TAG, this + "resume play", new Object[0]);
        } catch (Exception e) {
            Logger.E((String) TAG, this + " prepare exception:" + e.getMessage(), (Throwable) e, new Object[0]);
            this.mStarted = false;
            this.mErrCode = -1;
            if (this.mErrorListener != null) {
                Logger.E(TAG, "onError callback", new Object[0]);
                this.mErrorListener.onError(this.mErrCode, this.mVideoId);
            }
            if (this.mMediaPlayer != null) {
                this.mMediaPlayer.reset();
                this.mMediaPlayer.release();
                this.mMediaPlayer = null;
            }
            closeParcelFD();
            this.mStopTime = System.currentTimeMillis();
            reportEvent();
        } finally {
            this.mSeekWhenResume = -1;
        }
    }

    /* access modifiers changed from: private */
    public void handleResume() {
        Logger.D(TAG, "handleResume.isPlaying=" + isPlaying() + ", mPaused=" + this.mPaused, new Object[0]);
        if ((!isPlaying() || this.mPaused) && this.mMediaPlayer != null) {
            this.mMediaPlayer.start();
            if (this.mSeekWhenResume >= 0) {
                this.mMediaPlayer.seekTo((long) this.mSeekWhenResume);
                this.mSeekWhenResume = -1;
            }
            if (this.mNoFrameCome) {
                this.mOnInfoListener.onInfo(701, null);
            }
        }
        this.mPaused = false;
        if (this.mProHandler != null) {
            this.mProHandler.sendEmptyMessage(14);
        }
    }

    /* access modifiers changed from: private */
    public void handlePause() {
        Logger.D(TAG, "handlePause", new Object[0]);
        this.mPaused = true;
        this.mNoFrameCome = true;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.pause();
        }
        if (this.mProHandler != null) {
            this.mProHandler.removeMessages(14);
        }
    }

    /* access modifiers changed from: private */
    public void handleSeek(int msec) {
        Logger.I(TAG, "handleSeek: " + msec, new Object[0]);
        if (isPlaying()) {
            long ts = System.currentTimeMillis();
            this.mMediaPlayer.seekTo((long) msec);
            Logger.D(TAG, "seekTo took " + (System.currentTimeMillis() - ts) + "ms, seekTo:" + msec + ", duration:" + getDuration(), new Object[0]);
            this.mSeekWhenResume = -1;
            return;
        }
        this.mSeekWhenResume = msec;
    }

    /* access modifiers changed from: private */
    public void handleMute(boolean mute) {
        Logger.D(TAG, "handleMute: " + mute, new Object[0]);
        if (!isPlaying()) {
            return;
        }
        if (mute) {
            this.mMediaPlayer.setVolume(0.0f, 0.0f);
        } else {
            this.mMediaPlayer.setVolume(1.0f, 1.0f);
        }
    }

    /* access modifiers changed from: private */
    public void handleCache() {
        Logger.D(TAG, this + " handleCache", new Object[0]);
        try {
            File f = new File(this.mCachePath);
            if (!f.exists() || f.length() > ConfigManager.getInstance().diskConf().maxVideoCacheSize) {
                Logger.D(TAG, "video cache too large, just drop it.", new Object[0]);
                try {
                    f.delete();
                } catch (Exception e) {
                    Logger.E((String) TAG, (String) "file delete ex", (Throwable) e, new Object[0]);
                }
            } else {
                VideoFileManager.getInstance().getDiskCache().save(this.mPlayUrl, 2, 1, this.mBizId, Long.MAX_VALUE);
                VideoFileManager.getInstance().getDiskCache().update(this.mPlayUrl, this.mVideoId);
                this.mCacheDone = true;
            }
        } catch (Exception e2) {
            Logger.E((String) TAG, (String) "addCache exp:", (Throwable) e2, new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void handleReset() {
        Logger.D(TAG, "handleReset(), mMediaPlayer: " + this.mMediaPlayer, new Object[0]);
        this.mStopTime = System.currentTimeMillis();
        if (this.mStarted) {
            reportEvent();
        }
        this.mFrameIndex = 0;
        this.mStarted = false;
        this.mNoFrameCome = true;
        this.mPaused = false;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            closeParcelFD();
            try {
                clearIncompleteCache();
            } catch (Exception e) {
                Logger.E((String) TAG, (String) "clearIncompleteCache", (Throwable) e, new Object[0]);
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleParseParams(VideoPlayParams params) {
        String id = params.mVideoId;
        this.mCloudId = params.mVideoId;
        this.mBizId = params.mBizId;
        this.mEnableAudio = params.mEnableAudio;
        this.mEffect = params.mEffect;
        this.mProgressInterval = params.mProgressInterval;
        Logger.D(TAG, this + "begin handleParseParams: " + id, new Object[0]);
        Logger.D(TAG, this + "effect " + this.mEffect, new Object[0]);
        if (PathUtils.extractFile(id) != null) {
            String id2 = PathUtils.extractFile(id).getAbsolutePath();
            this.mPlayUrl = id2;
            this.mVideoId = id2;
            Logger.D(TAG, this + "setVideoId: " + id2, new Object[0]);
            return;
        }
        String path = VideoFileManager.getInstance().getVideoPathById(id);
        this.mCloudId = id;
        if (!TextUtils.isEmpty(id) && id.contains(MergeUtil.SEPARATOR_KV)) {
            id = id.substring(0, id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA));
        }
        if (TextUtils.isEmpty(path)) {
            path = CacheContext.get().getDiskCache().genPathByKey(id);
            Logger.I(TAG, this + "genPathByKey: " + id + ";path=" + path, new Object[0]);
        }
        if (path == null || !new File(path).exists()) {
            Logger.D(TAG, this + "handleParseParams, no cache: " + id, new Object[0]);
            VideoFileManager.getInstance().getDiskCache().remove(id);
            this.mEnableCache = true;
            if (PathUtils.isRtmp(id) || PathUtils.isHttp(id)) {
                this.mPlayUrl = id;
                this.mVideoId = id;
            } else if (PathUtils.isDjangoPath(id)) {
                try {
                    this.mVideoId = id;
                    this.mPlayUrl = UriFactory.buildGetUrl(id, new Request(0));
                } catch (Exception e) {
                    Logger.E((String) TAG, (String) "buildGetUrl exp:", (Throwable) e, new Object[0]);
                }
            } else {
                Logger.D(TAG, this + "handleParseParams invalid input param: " + id, new Object[0]);
            }
        } else {
            this.mPlayUrl = path;
            this.mVideoId = id;
            this.mEnableCache = false;
        }
        Logger.D(TAG, this + "end handleParseParams: " + this.mPlayUrl, new Object[0]);
    }

    private void clearIncompleteCache() {
        Logger.D(TAG, "clearIncompleteCache", new Object[0]);
        if (this.mEnableCache && !this.mCacheDone && !TextUtils.isEmpty(this.mCachePath)) {
            Logger.D(TAG, "player cache not success, path:" + this.mCachePath, new Object[0]);
            File f = new File(this.mCachePath);
            if (f.exists()) {
                Logger.D(TAG, "Incomplete video cache exists, delete it, result:" + f.delete(), new Object[0]);
            }
        }
    }

    /* access modifiers changed from: private */
    public void releaseGl() {
        if (this.mVideoTexture != null) {
            this.mVideoTexture.release();
            this.mVideoTexture = null;
        }
        if (this.mSurface != null) {
            this.mSurface.release();
            this.mSurface = null;
        }
        if (this.mDisplaySurface != null) {
            this.mDisplaySurface.release();
            this.mDisplaySurface = null;
        }
        if (this.mFullFrameBlit != null) {
            this.mFullFrameBlit.release(true);
            this.mFullFrameBlit = null;
        }
        if (this.mEglCore != null) {
            this.mEglCore.makeNothingCurrent();
            this.mEglCore.release();
            this.mEglCore = null;
        }
        Logger.D(TAG, this + "\treleaseGl end", new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void setOnProgressUpdateListener(OnProgressUpdateListener listener) {
        this.mProgressListener = listener;
    }

    /* access modifiers changed from: private */
    public void handleCheckProgress() {
        if (System.currentTimeMillis() - this.logTime < 1000) {
            this.bLog = false;
        } else {
            this.bLog = true;
            this.logTime = System.currentTimeMillis();
        }
        if (this.mMediaPlayer != null && this.bLog) {
            Logger.D(TAG, this + "handleCheckProgress, mMediaPlayer.isPlaying():" + this.mMediaPlayer.isPlaying() + ",mStarted:" + this.mStarted + ",mProgressListener:" + this.mProgressListener, new Object[0]);
        }
        if (this.mMediaPlayer != null && this.mStarted && this.mMediaPlayer.isPlaying() && this.mProgressListener != null) {
            long curPostion = this.mMediaPlayer.getCurrentPosition();
            this.mProgressListener.onProgressUpdate(curPostion);
            if (this.bLog) {
                Logger.D(TAG, this + "onProgressUpdate callback:" + curPostion, new Object[0]);
            }
        }
        if (this.mCheckProgress && !this.mPaused) {
            this.mProHandler.sendEmptyMessageDelayed(14, (long) this.mProgressInterval);
        }
    }

    private void startCheckProgress() {
        Logger.D(TAG, this + "startCheckProgress...", new Object[0]);
        if (this.mProgressListener != null) {
            this.mCheckProgress = true;
            this.mProHandler.removeMessages(14);
            this.mProHandler.obtainMessage(14).sendToTarget();
        }
    }

    /* access modifiers changed from: private */
    public void stopCheckProgress() {
        Logger.D(TAG, this + "stopCheckProgress...", new Object[0]);
        if (this.mProgressListener != null) {
            this.mProHandler.removeMessages(14);
            this.mCheckProgress = false;
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int w, int h) {
        synchronized (this.mSurfaceLock) {
            this.mSurfaceTexture = surfaceTexture;
            Logger.D(TAG, this + " onSurfaceTextureAvailable and notify######, w = " + getWidth() + ", h = " + getHeight(), new Object[0]);
            this.mSurfaceLock.notifyAll();
            Logger.D(TAG, this + "\tafter onSurfaceTextureAvailable and notify######", new Object[0]);
        }
        if (!this.mCenterCropFixed) {
            this.mDisplayWidth = w;
            this.mDisplayHeight = h;
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int w, int h) {
        Logger.D(TAG, this + " onSurfaceTextureSizeChanged w:" + w + ", h:" + h, new Object[0]);
        generateViewport();
        if (!this.mCenterCropFixed) {
            this.mDisplayHeight = h;
            this.mDisplayWidth = w;
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        boolean flag;
        Logger.D(TAG, this + " onSurfaceTextureDestroyed", new Object[0]);
        synchronized (this.mSurfaceLock) {
            this.mSurfaceLock.notifyAll();
        }
        synchronized (this.mPrepareLock) {
            this.mPrepareLock.notifyAll();
        }
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(4);
            this.mHandler.sendEmptyMessage(8);
        }
        if (this.mProHandler != null) {
            this.mProHandler.sendEmptyMessage(15);
        }
        this.mStarted = false;
        this.mPreparing = false;
        synchronized (this.mHandlerLock) {
            flag = (this.mHandler == null || this.mThread == null || !this.mThread.isAlive() || this.mHandler.getLooper() == null) ? false : true;
        }
        if (flag) {
            synchronized (this.mQuitLock) {
                try {
                    this.mQuitLock.wait(2000);
                } catch (InterruptedException e) {
                    Logger.E((String) TAG, (String) "", (Throwable) e, new Object[0]);
                }
            }
        }
        this.mSurfaceTexture = null;
        if (this.mHandler != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (UrlPlayViewImpl.this.mDisplaySurface != null) {
                        UrlPlayViewImpl.this.mDisplaySurface.release();
                        UrlPlayViewImpl.this.mDisplaySurface = null;
                    }
                }
            });
        }
        Logger.D(TAG, this + "\tonSurfaceTextureDestroyed done", new Object[0]);
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (this.mSurfaceTexture != surfaceTexture) {
            Logger.D(TAG, "###surfacetexture error###", new Object[0]);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkHandler() {
        /*
            r4 = this;
            java.lang.Object r1 = r4.mHandlerLock
            monitor-enter(r1)
            tv.danmaku.ijk.media.widget.UrlPlayViewImpl$PlayHandler r0 = r4.mHandler     // Catch:{ all -> 0x00cb }
            if (r0 == 0) goto L_0x001b
            android.os.HandlerThread r0 = r4.mThread     // Catch:{ all -> 0x00cb }
            if (r0 == 0) goto L_0x001b
            android.os.HandlerThread r0 = r4.mThread     // Catch:{ all -> 0x00cb }
            boolean r0 = r0.isAlive()     // Catch:{ all -> 0x00cb }
            if (r0 == 0) goto L_0x001b
            tv.danmaku.ijk.media.widget.UrlPlayViewImpl$PlayHandler r0 = r4.mHandler     // Catch:{ all -> 0x00cb }
            android.os.Looper r0 = r0.getLooper()     // Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x005a
        L_0x001b:
            java.lang.String r0 = "UrlPlayViewImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00cb }
            r2.<init>()     // Catch:{ all -> 0x00cb }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x00cb }
            java.lang.String r3 = "\trender thread not ready, create..."
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00cb }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00cb }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00cb }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r0, r2, r3)     // Catch:{ all -> 0x00cb }
            r4.releaseGl()     // Catch:{ all -> 0x00cb }
            android.os.HandlerThread r0 = new android.os.HandlerThread     // Catch:{ all -> 0x00cb }
            java.lang.String r2 = "url_sight_play"
            r0.<init>(r2)     // Catch:{ all -> 0x00cb }
            r4.mThread = r0     // Catch:{ all -> 0x00cb }
            android.os.HandlerThread r0 = r4.mThread     // Catch:{ all -> 0x00cb }
            r0.start()     // Catch:{ all -> 0x00cb }
            tv.danmaku.ijk.media.widget.UrlPlayViewImpl$PlayHandler r0 = new tv.danmaku.ijk.media.widget.UrlPlayViewImpl$PlayHandler     // Catch:{ all -> 0x00cb }
            android.os.HandlerThread r2 = r4.mThread     // Catch:{ all -> 0x00cb }
            android.os.Looper r2 = r2.getLooper()     // Catch:{ all -> 0x00cb }
            r0.<init>(r4, r2)     // Catch:{ all -> 0x00cb }
            r4.mHandler = r0     // Catch:{ all -> 0x00cb }
            r0 = 0
            r4.mStarted = r0     // Catch:{ all -> 0x00cb }
            r0 = 0
            r4.mPreparing = r0     // Catch:{ all -> 0x00cb }
        L_0x005a:
            tv.danmaku.ijk.media.widget.UrlPlayViewImpl$ProgressHandler r0 = r4.mProHandler     // Catch:{ all -> 0x00cb }
            if (r0 == 0) goto L_0x0072
            android.os.HandlerThread r0 = r4.mProThread     // Catch:{ all -> 0x00cb }
            if (r0 == 0) goto L_0x0072
            android.os.HandlerThread r0 = r4.mProThread     // Catch:{ all -> 0x00cb }
            boolean r0 = r0.isAlive()     // Catch:{ all -> 0x00cb }
            if (r0 == 0) goto L_0x0072
            tv.danmaku.ijk.media.widget.UrlPlayViewImpl$ProgressHandler r0 = r4.mProHandler     // Catch:{ all -> 0x00cb }
            android.os.Looper r0 = r0.getLooper()     // Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c9
        L_0x0072:
            com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView$OnProgressUpdateListener r0 = r4.mProgressListener     // Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x0093
            java.lang.String r0 = "UrlPlayViewImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00cb }
            r2.<init>()     // Catch:{ all -> 0x00cb }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x00cb }
            java.lang.String r3 = "\tthere is no need to create progress thread."
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00cb }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00cb }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00cb }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r0, r2, r3)     // Catch:{ all -> 0x00cb }
            monitor-exit(r1)     // Catch:{ all -> 0x00cb }
        L_0x0092:
            return
        L_0x0093:
            java.lang.String r0 = "UrlPlayViewImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00cb }
            r2.<init>()     // Catch:{ all -> 0x00cb }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x00cb }
            java.lang.String r3 = "\tprogress thread not ready, create..."
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00cb }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00cb }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00cb }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r0, r2, r3)     // Catch:{ all -> 0x00cb }
            android.os.HandlerThread r0 = new android.os.HandlerThread     // Catch:{ all -> 0x00cb }
            java.lang.String r2 = "url_sight_play_pro"
            r0.<init>(r2)     // Catch:{ all -> 0x00cb }
            r4.mProThread = r0     // Catch:{ all -> 0x00cb }
            android.os.HandlerThread r0 = r4.mProThread     // Catch:{ all -> 0x00cb }
            r0.start()     // Catch:{ all -> 0x00cb }
            tv.danmaku.ijk.media.widget.UrlPlayViewImpl$ProgressHandler r0 = new tv.danmaku.ijk.media.widget.UrlPlayViewImpl$ProgressHandler     // Catch:{ all -> 0x00cb }
            android.os.HandlerThread r2 = r4.mProThread     // Catch:{ all -> 0x00cb }
            android.os.Looper r2 = r2.getLooper()     // Catch:{ all -> 0x00cb }
            r0.<init>(r4, r2)     // Catch:{ all -> 0x00cb }
            r4.mProHandler = r0     // Catch:{ all -> 0x00cb }
        L_0x00c9:
            monitor-exit(r1)     // Catch:{ all -> 0x00cb }
            goto L_0x0092
        L_0x00cb:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00cb }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.widget.UrlPlayViewImpl.checkHandler():void");
    }

    private void setExceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                Logger.E(UrlPlayViewImpl.TAG, "uncaughtException###, thread name:" + thread.getName() + ", thread id:" + thread.getId() + ",ex:" + ex.getMessage(), new Object[0]);
                StackTraceElement[] elements = ex.getStackTrace();
                StringBuilder sb = new StringBuilder();
                for (StackTraceElement stackTraceElement : elements) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
                Logger.E(UrlPlayViewImpl.TAG, "exception stack:\n" + sb.toString(), new Object[0]);
                UrlPlayViewImpl.this.releaseGl();
            }
        });
    }

    private void reportEvent() {
        if (this.mNeedReport) {
            this.mNeedReport = false;
            long watchtime = this.mStopTime - this.mStartTime;
            long loadingtime = this.mFirstFrameTime - this.mStartTime;
            long videodur = this.mMediaPlayer != null ? this.mMediaPlayer.getDuration() : -1;
            long filesize = -1;
            if (TextUtils.isEmpty(this.mCachePath)) {
                File f = PathUtils.extractFile(this.mPlayUrl);
                if (f != null) {
                    filesize = f.length();
                }
            } else if (this.mCacheDone) {
                File f2 = PathUtils.extractFile(this.mCachePath);
                if (f2 != null) {
                    filesize = f2.length();
                }
            }
            String error_code = String.valueOf(this.mErrCode);
            Map ext4 = new HashMap();
            ext4.put("bz", this.mBizId);
            ext4.put("id", this.mCloudId);
            ext4.put("wd", String.valueOf(watchtime));
            ext4.put("ld", String.valueOf(loadingtime));
            ext4.put("td", String.valueOf(videodur));
            ext4.put("er", error_code);
            ext4.put("nc", "");
            ext4.put(DictionaryKeys.EVENT_TYPE_FOCUS, this.mEnableCache ? "1" : "0");
            ext4.put(H5Param.SAFEPAY_CONTEXT, String.valueOf(this.mBlockTime));
            ext4.put("ter", "");
            ext4.put("tsr", "");
            ext4.put("tso", "");
            ext4.put("tfl", String.valueOf(filesize));
            ext4.put("tcl", "");
            ext4.put("tct", "");
            UCLogUtil.UC_MM_C50(error_code, String.valueOf(loadingtime), ext4);
            Logger.I(TAG, "report online playing ubc:" + this.mPlayUrl + "\tbizId:" + this.mBizId, new Object[0]);
            Logger.I(TAG, "report online playing ubc watchtime:" + watchtime + ", loadingtime:" + loadingtime + ", videodur:" + videodur + ", filesize:" + filesize + ", errorcode:" + error_code, new Object[0]);
        }
    }

    private void initBehavior() {
        this.mStartTime = System.currentTimeMillis();
        this.mBlockTime = 0;
        this.mNeedReport = true;
    }

    /* access modifiers changed from: private */
    public Bitmap getThumbnail() {
        String str;
        String str2 = null;
        Logger.I(TAG, "getThumbnail video id:" + this.mCloudId, new Object[0]);
        long ts = System.currentTimeMillis();
        if (TextUtils.isEmpty(this.mCloudId) || !this.mCloudId.contains(MergeUtil.SEPARATOR_KV)) {
            return null;
        }
        FileCacheModel model = VideoFileManager.getInstance().getVideoThumbCacheInfo(this.mCloudId);
        String key = model == null ? "" : model.cacheKey;
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        String cacheKey = CacheUtils.makeVideoThumbCacheKey(key, 640, 640);
        CacheContext cacheContext = CacheContext.get();
        if (model != null) {
            str = model.businessId;
        } else {
            str = null;
        }
        Bitmap bitmap = cacheContext.getMemCache(str).get(cacheKey);
        if (!ImageUtils.checkBitmap(bitmap)) {
            Logger.I(TAG, "mem cache missed.", new Object[0]);
            String path = model.path;
            if (!TextUtils.isEmpty(path)) {
                bitmap = ImageUtils.decodeBitmapByFalcon(new File(path));
            }
            CacheContext cacheContext2 = CacheContext.get();
            if (model != null) {
                str2 = model.businessId;
            }
            cacheContext2.getMemCache(str2).put(cacheKey, bitmap);
        }
        Logger.I(TAG, "operation getThumbnail took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
        Logger.I(TAG, "bitmap id:" + bitmap, new Object[0]);
        return bitmap;
    }

    /* access modifiers changed from: protected */
    public void destroyHardwareResources() {
    }

    private boolean getHardDecodeConfigSwitch() {
        if (this.hardDecodeSwitch != null) {
            return this.hardDecodeSwitch.booleanValue();
        }
        if (VideoDeviceWrapper.isLivePlayHardDecode()) {
            this.hardDecodeSwitch = Boolean.valueOf(true);
        } else {
            this.hardDecodeSwitch = Boolean.valueOf(false);
        }
        Logger.D(TAG, "getHardDecodeSwitch ret=" + this.hardDecodeSwitch, new Object[0]);
        return this.hardDecodeSwitch.booleanValue();
    }

    private void setDataSource() {
        if (this.mMediaPlayer != null) {
            String dataSource = this.mPlayUrl;
            if (SandboxWrapper.isContentUriPath(this.mPlayUrl)) {
                this.mPfd = SandboxWrapper.openParcelFileDescriptor(Uri.parse(this.mPlayUrl));
                if (this.mPfd != null) {
                    dataSource = PathUtils.genPipePath(this.mPfd.getFd());
                }
            }
            this.mMediaPlayer.setDataSource(dataSource);
            Logger.D(TAG, "setDataSource dataSource=" + dataSource, new Object[0]);
        }
    }

    private void closeParcelFD() {
        if (this.mPfd != null) {
            Logger.D(TAG, "closeParcelFD mPfd=" + this.mPfd.getFd(), new Object[0]);
        }
        IOUtils.closeQuietly(this.mPfd);
        this.mPfd = null;
    }
}
