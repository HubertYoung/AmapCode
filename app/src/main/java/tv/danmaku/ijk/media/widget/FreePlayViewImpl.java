package tv.danmaku.ijk.media.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.alibaba.wireless.security.SecExceptionCode;
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
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CacheUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ReflectUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark;
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
import com.alipay.multimedia.gles.GlFrameBuffer;
import com.alipay.multimedia.gles.GlTexture;
import com.alipay.multimedia.gles.GlUtil;
import com.alipay.multimedia.gles.WindowSurface10;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.sdk.sys.a;
import com.alipay.streammedia.encode.FFmpegCameraEncoderJni;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.video.VideoInfo;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
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
public class FreePlayViewImpl extends SightPlayView implements OnFrameAvailableListener, SurfaceTextureListener, OnBufferingUpdateListener, OnCompletionListener, OnDownloadStatusListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener {
    private static final int MSG_BITMAP = 7;
    private static final int MSG_CACHE = 19;
    private static final int MSG_CHECK_PROGRESS = 16;
    private static final int MSG_CURR_FRAME = 15;
    private static final int MSG_FRAME_AVAILABLE = 9;
    private static final int MSG_INIT_GL = 13;
    private static final int MSG_MUTE = 18;
    private static final int MSG_PARSE_PARAM = 12;
    private static final int MSG_PAUSE = 11;
    private static final int MSG_PREPARE = 0;
    private static final int MSG_PRO_QUIT = 17;
    private static final int MSG_QUIT = 8;
    private static final int MSG_RELEASE_WINDOW = 14;
    private static final int MSG_RESET = 3;
    private static final int MSG_RESUME = 1;
    private static final int MSG_SEEK = 10;
    private static final int MSG_THUMB = 5;
    private static final String TAG = "FreePlayViewImpl";
    private boolean VERBOSE = false;
    private boolean bLog = true;
    final int buffer_num = 1;
    boolean firstCreated = false;
    private Boolean hardDecodeSwitch;
    private long logTime = 0;
    private AtomicBoolean mAudioPaused = new AtomicBoolean(false);
    private boolean mAutoFitCenter = false;
    private String mBizId;
    private SightVideoPlayView.OnBufferingUpdateListener mBufferingListener;
    private boolean mCacheDone = false;
    private String mCachePath;
    private boolean mCenterCropFixed = false;
    private boolean mCenterCropped = false;
    private boolean mCheckProgress = false;
    private String mCloudId = null;
    private SightVideoPlayView.OnCompletionListener mCompletionListener;
    private Bitmap mCurFrame;
    private long mCurPlayTime = 0;
    private int mDegree = 0;
    private int mDisplayHeight;
    private WindowSurface10 mDisplaySurface;
    private int mDisplayWidth;
    private int mEffect = VideoPlayParams.EFFECT_DEFAULT;
    private EglCore10 mEglCore;
    private boolean mEnableAudio = true;
    private boolean mEnableCache = false;
    private int mErrCode = 0;
    private OnPlayErrorListener mErrorListener;
    private GlFrameBuffer mFbo;
    private long mFirstFrameTime = 0;
    private int mFrameIndex = 0;
    private Object mFrameLock = new Object();
    private FullFrameRect mFullFrameBlit;
    private FullFrameRect mFullThumbBlit;
    private int mH;
    /* access modifiers changed from: private */
    public PlayHandler mHandler;
    /* access modifiers changed from: private */
    public final Object mHandlerLock = new Object();
    private ImageView mImageView;
    private int mImgTexId;
    private boolean mIsLoop = false;
    private boolean mKeepScreenOn = false;
    private IjkMediaPlayer mMediaPlayer;
    private boolean mNeedReport = false;
    private boolean mNoFrameCome = true;
    private String mObjectId = toString();
    private SightVideoPlayView.OnInfoListener mOnInfoListener;
    final int[] mPBO_id = new int[1];
    private Paint mPaint;
    private boolean mPaused = false;
    private boolean mPboCreated = false;
    private ParcelFileDescriptor mPfd = null;
    private String mPlayUrl;
    private SurfaceTexture mPreSurfaceTexture = null;
    private boolean mPreparExp = false;
    private SightVideoPlayView.OnPreparedListener mPrepareListener;
    private boolean mPreparing = false;
    /* access modifiers changed from: private */
    public ProgressHandler mProHandler;
    /* access modifiers changed from: private */
    public HandlerThread mProThread;
    private int mProgressInterval = 500;
    private OnProgressUpdateListener mProgressListener;
    /* access modifiers changed from: private */
    public final Object mQuitLock = new Object();
    private AtomicBoolean mReported = new AtomicBoolean(false);
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
    private GlTexture mTexture;
    private int mTextureId;
    /* access modifiers changed from: private */
    public HandlerThread mThread;
    private Bitmap mThumbnail;
    private final float[] mTmpMatrix = new float[16];
    private boolean mUsePBO = false;
    /* access modifiers changed from: private */
    public int mVideoHeight = 0;
    private String mVideoId;
    private SurfaceTexture mVideoTexture;
    /* access modifiers changed from: private */
    public int mVideoWidth = 0;
    private int mW;
    private volatile boolean mWindowAvailable = false;
    private Object mWindowLock = new Object();
    private boolean mWriteCache = false;
    private int mX;
    private int mY;

    private static class PlayHandler extends Handler {
        private Looper mLooper;
        private WeakReference<FreePlayViewImpl> mReference;

        PlayHandler(FreePlayViewImpl view, Looper looper) {
            super(looper);
            this.mLooper = looper;
            this.mReference = new WeakReference<>(view);
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void checkSurface() {
            /*
                r6 = this;
                java.lang.ref.WeakReference<tv.danmaku.ijk.media.widget.FreePlayViewImpl> r2 = r6.mReference
                java.lang.Object r1 = r2.get()
                tv.danmaku.ijk.media.widget.FreePlayViewImpl r1 = (tv.danmaku.ijk.media.widget.FreePlayViewImpl) r1
                if (r1 == 0) goto L_0x003e
                java.lang.Object r3 = r1.mSurfaceLock
                monitor-enter(r3)
                android.graphics.SurfaceTexture r2 = r1.mSurfaceTexture     // Catch:{ all -> 0x004b }
                if (r2 == 0) goto L_0x001b
                boolean r2 = r1.isAvailable()     // Catch:{ all -> 0x004b }
                if (r2 != 0) goto L_0x003d
            L_0x001b:
                java.lang.String r2 = "FreePlayViewImpl"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x004b }
                r4.<init>()     // Catch:{ all -> 0x004b }
                java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ all -> 0x004b }
                java.lang.String r5 = "checkSurface and surface not ready"
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x004b }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x004b }
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x004b }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I(r2, r4, r5)     // Catch:{ all -> 0x004b }
                java.lang.Object r2 = r1.mSurfaceLock     // Catch:{ InterruptedException -> 0x003f }
                r2.wait()     // Catch:{ InterruptedException -> 0x003f }
            L_0x003d:
                monitor-exit(r3)     // Catch:{ all -> 0x004b }
            L_0x003e:
                return
            L_0x003f:
                r0 = move-exception
                java.lang.String r2 = "FreePlayViewImpl"
                java.lang.String r4 = ""
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x004b }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r2, r4, r0, r5)     // Catch:{ all -> 0x004b }
                goto L_0x003d
            L_0x004b:
                r2 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x004b }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.widget.FreePlayViewImpl.PlayHandler.checkSurface():void");
        }

        public void handleMessage(Message msg) {
            boolean z = true;
            FreePlayViewImpl playTextureView = (FreePlayViewImpl) this.mReference.get();
            if (playTextureView == null) {
                Logger.I(FreePlayViewImpl.TAG, "outter class is null", new Object[0]);
                return;
            }
            if (msg.what != 9) {
                Logger.I(FreePlayViewImpl.TAG, playTextureView + " handle msg: " + msg.what, new Object[0]);
            }
            switch (msg.what) {
                case 0:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mSurfaceTexture, false);
                    playTextureView.handlePrepare();
                    return;
                case 1:
                    playTextureView.handleResume();
                    return;
                case 3:
                    playTextureView.handleReset();
                    return;
                case 5:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mSurfaceTexture, false);
                    playTextureView.handleDrawBitmap(playTextureView.getThumbnail(), true);
                    return;
                case 7:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mSurfaceTexture, false);
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if (msg.arg1 == 0) {
                        z = false;
                    }
                    playTextureView.handleDrawBitmap(bitmap, z);
                    return;
                case 8:
                    try {
                        this.mLooper.quit();
                        synchronized (playTextureView.mHandlerLock) {
                            playTextureView.mHandler = null;
                            playTextureView.mThread = null;
                        }
                    } catch (Exception e) {
                        Logger.E((String) FreePlayViewImpl.TAG, (String) "", (Throwable) e, new Object[0]);
                    }
                    playTextureView.releaseGl();
                    synchronized (playTextureView.mQuitLock) {
                        playTextureView.mQuitLock.notifyAll();
                        Logger.I(FreePlayViewImpl.TAG, "mQuitLock notifyAll", new Object[0]);
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
                    playTextureView.handleSetSurfaceTexture(playTextureView.mSurfaceTexture, true);
                    return;
                case 14:
                    playTextureView.handleReleaseWindow();
                    return;
                case 15:
                    playTextureView.handleCurrentFrame(playTextureView.mVideoWidth, playTextureView.mVideoHeight);
                    return;
                case 18:
                    playTextureView.handleMute(msg.arg1 != 0);
                    return;
                case 19:
                    playTextureView.handleCache();
                    return;
                default:
                    return;
            }
        }
    }

    private static class ProgressHandler extends Handler {
        private Looper mLooper;
        private WeakReference<FreePlayViewImpl> mReference;

        ProgressHandler(FreePlayViewImpl view, Looper looper) {
            super(looper);
            this.mLooper = looper;
            this.mReference = new WeakReference<>(view);
        }

        public void handleMessage(Message msg) {
            FreePlayViewImpl playTextureView = (FreePlayViewImpl) this.mReference.get();
            if (playTextureView == null) {
                Logger.D(FreePlayViewImpl.TAG, "outter class is null", new Object[0]);
                return;
            }
            switch (msg.what) {
                case 16:
                    try {
                        playTextureView.handleCheckProgress();
                        return;
                    } catch (Exception e) {
                        Logger.E((String) FreePlayViewImpl.TAG, (String) "handleCheckProgress exp:", (Throwable) e, new Object[0]);
                        return;
                    }
                case 17:
                    try {
                        playTextureView.stopCheckProgress();
                        this.mLooper.quit();
                        playTextureView.mProHandler = null;
                        playTextureView.mProThread = null;
                    } catch (Exception e2) {
                        Logger.E((String) FreePlayViewImpl.TAG, (String) "", (Throwable) e2, new Object[0]);
                    }
                    Logger.D(FreePlayViewImpl.TAG, "MSG_PRO_QUIT handle end.", new Object[0]);
                    return;
                default:
                    return;
            }
        }
    }

    public FreePlayViewImpl(Context context) {
        super(context);
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).clear();
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_VIEW_CREATE, System.nanoTime());
        init();
        this.mUsePBO = VideoUtils.supportGles30(context);
        if (this.mUsePBO) {
            try {
                AppUtils.loadLibrary("pbo_jni");
            } catch (Throwable th) {
                Logger.E(TAG, "loadLibrary pbo_jni failed.", new Object[0]);
                this.mUsePBO = false;
            }
        }
    }

    private void init() {
        Logger.I(TAG, "free video play view init", new Object[0]);
        setSurfaceTextureListener(this);
        setOpaque(false);
        setScaleX(1.00001f);
        setScaleY(1.00001f);
        this.mPaint = new Paint();
        this.mPaint.setFilterBitmap(true);
        generateMVPMatrix();
    }

    private void addPlaceholder() {
        if (this.mImageView == null) {
            ViewGroup parent = (ViewGroup) getParent();
            if (parent == null) {
                Logger.E(TAG, "This is impossible!", new Object[0]);
                return;
            }
            Logger.I(TAG, "addPlaceholder", new Object[0]);
            LayoutParams layoutParam = new LayoutParams(-1, -1);
            this.mImageView = new ImageView(getContext());
            parent.addView(this.mImageView, layoutParam);
            this.mImageView.setVisibility(4);
            this.mImageView.bringToFront();
            this.mImageView.requestLayout();
        }
    }

    private void showPlaceholder() {
        if (this.mImageView != null) {
            Logger.I(TAG, "showPlaceholder", new Object[0]);
            Bitmap image = getCurrentFrame();
            if (image == null) {
                Logger.E(TAG, "PBO get image failed.", new Object[0]);
                return;
            }
            this.mImageView.setImageBitmap(image);
            this.mImageView.setVisibility(0);
            Logger.I(TAG, "showPlaceholder end", new Object[0]);
        }
    }

    private void hidePlaceholder() {
        if (this.mImageView != null) {
            Logger.E(TAG, "hidePlaceholder", new Object[0]);
            this.mImageView.setImageBitmap(null);
            this.mImageView.setVisibility(4);
        }
    }

    public void setCenterCropped() {
        LogHelper.i(TAG, "setCenterCropped and mAutoFitCenter is " + this.mAutoFitCenter);
        if (!this.mAutoFitCenter) {
            this.mCenterCropped = true;
        }
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

    public void setAutoFitCenter(boolean fit) {
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
        LogHelper.i(TAG, "generateViewport view size, w:" + getWidth() + ",h:" + getHeight());
        if (!this.mAutoFitCenter) {
            this.mY = 0;
            this.mX = 0;
            this.mW = getWidth();
            this.mH = getHeight();
            LogHelper.i(TAG, this + "generateViewport finished, \tmX:" + this.mX + "\tmY:" + this.mY + "\tmW:" + this.mW + "mH:" + this.mH);
        } else if (!(this.mVideoHeight == 0 || this.mVideoWidth == 0)) {
            LogHelper.i(TAG, this + "generateViewport, \tmVideoWidth:" + this.mVideoWidth + "\tmVideoHeight:" + this.mVideoHeight);
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
            LogHelper.i(TAG, this + "generateViewport done, \tmX:" + this.mX + "\tmY:" + this.mY + "\tmW:" + this.mW + "mH:" + this.mH);
        }
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (this.VERBOSE) {
            Logger.I(TAG, "onFrameAvailable sendEmptyMessage", new Object[0]);
        }
        this.mHandler.sendEmptyMessage(9);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e6, code lost:
        if (r6.mDisplaySurface == null) goto L_0x00e8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleSetSurfaceTexture(android.graphics.SurfaceTexture r7, boolean r8) {
        /*
            r6 = this;
            r4 = 0
            java.lang.String r1 = "FreePlayViewImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.String r3 = "\thandleSetSurfaceTexture"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r4]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I(r1, r2, r3)
            r6.setExceptionHandler()
            com.alipay.multimedia.gles.EglCore10 r1 = r6.mEglCore     // Catch:{ Exception -> 0x00bc }
            if (r1 != 0) goto L_0x00e2
            java.lang.String r1 = "FreePlayViewImpl"
            java.lang.String r2 = "handleSetSurfaceTexture mEglCore"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x00bc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I(r1, r2, r3)     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.EglCore10 r1 = new com.alipay.multimedia.gles.EglCore10     // Catch:{ Exception -> 0x00bc }
            r1.<init>()     // Catch:{ Exception -> 0x00bc }
            r6.mEglCore = r1     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.WindowSurface10 r1 = new com.alipay.multimedia.gles.WindowSurface10     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.EglCore10 r2 = r6.mEglCore     // Catch:{ Exception -> 0x00bc }
            r1.<init>(r2, r7)     // Catch:{ Exception -> 0x00bc }
            r6.mDisplaySurface = r1     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.WindowSurface10 r1 = r6.mDisplaySurface     // Catch:{ Exception -> 0x00bc }
            r1.makeCurrent()     // Catch:{ Exception -> 0x00bc }
            int r1 = r6.mEffect     // Catch:{ Exception -> 0x00bc }
            int r2 = com.alipay.android.phone.mobilecommon.multimedia.video.data.VideoPlayParams.EFFECT_TRANSPARENT     // Catch:{ Exception -> 0x00bc }
            if (r1 != r2) goto L_0x00ad
            com.alipay.multimedia.gles.FullFrameRect r1 = new com.alipay.multimedia.gles.FullFrameRect     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.Texture2dProgram r2 = new com.alipay.multimedia.gles.Texture2dProgram     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.Texture2dProgram$ProgramType r3 = com.alipay.multimedia.gles.Texture2dProgram.ProgramType.TEXTURE_EXT_TRANSPARENT     // Catch:{ Exception -> 0x00bc }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00bc }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00bc }
            r6.mFullFrameBlit = r1     // Catch:{ Exception -> 0x00bc }
        L_0x0055:
            com.alipay.multimedia.gles.FullFrameRect r1 = r6.mFullFrameBlit     // Catch:{ Exception -> 0x00bc }
            int r1 = r1.createTextureObject()     // Catch:{ Exception -> 0x00bc }
            r6.mTextureId = r1     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.FullFrameRect r1 = new com.alipay.multimedia.gles.FullFrameRect     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.Texture2dProgram r2 = new com.alipay.multimedia.gles.Texture2dProgram     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.Texture2dProgram$ProgramType r3 = com.alipay.multimedia.gles.Texture2dProgram.ProgramType.TEXTURE_2D     // Catch:{ Exception -> 0x00bc }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00bc }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00bc }
            r6.mFullThumbBlit = r1     // Catch:{ Exception -> 0x00bc }
            android.graphics.SurfaceTexture r1 = new android.graphics.SurfaceTexture     // Catch:{ Exception -> 0x00bc }
            int r2 = r6.mTextureId     // Catch:{ Exception -> 0x00bc }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00bc }
            r6.mVideoTexture = r1     // Catch:{ Exception -> 0x00bc }
            android.view.Surface r1 = new android.view.Surface     // Catch:{ Exception -> 0x00bc }
            android.graphics.SurfaceTexture r2 = r6.mVideoTexture     // Catch:{ Exception -> 0x00bc }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00bc }
            r6.mSurface = r1     // Catch:{ Exception -> 0x00bc }
            android.graphics.SurfaceTexture r1 = r6.mVideoTexture     // Catch:{ Exception -> 0x00bc }
            r1.setOnFrameAvailableListener(r6)     // Catch:{ Exception -> 0x00bc }
            r1 = 1
            r6.mWindowAvailable = r1     // Catch:{ Exception -> 0x00bc }
        L_0x0085:
            java.lang.Object r2 = r6.mWindowLock
            monitor-enter(r2)
            java.lang.Object r1 = r6.mWindowLock     // Catch:{ all -> 0x014e }
            r1.notifyAll()     // Catch:{ all -> 0x014e }
            monitor-exit(r2)     // Catch:{ all -> 0x014e }
        L_0x008e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "video_play_"
            r1.<init>(r2)
            java.lang.String r2 = r6.mObjectId
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.os.Bundle r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark.getBundle(r1)
            java.lang.String r2 = "gl_prepared"
            long r4 = java.lang.System.nanoTime()
            r1.putLong(r2, r4)
        L_0x00ac:
            return
        L_0x00ad:
            com.alipay.multimedia.gles.FullFrameRect r1 = new com.alipay.multimedia.gles.FullFrameRect     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.Texture2dProgram r2 = new com.alipay.multimedia.gles.Texture2dProgram     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.Texture2dProgram$ProgramType r3 = com.alipay.multimedia.gles.Texture2dProgram.ProgramType.TEXTURE_EXT     // Catch:{ Exception -> 0x00bc }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00bc }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00bc }
            r6.mFullFrameBlit = r1     // Catch:{ Exception -> 0x00bc }
            goto L_0x0055
        L_0x00bc:
            r0 = move-exception
            com.alipay.multimedia.gles.EglCore10 r1 = r6.mEglCore     // Catch:{ all -> 0x0143 }
            if (r1 == 0) goto L_0x00c9
            com.alipay.multimedia.gles.EglCore10 r1 = r6.mEglCore     // Catch:{ all -> 0x0143 }
            r1.release()     // Catch:{ all -> 0x0143 }
            r1 = 0
            r6.mEglCore = r1     // Catch:{ all -> 0x0143 }
        L_0x00c9:
            java.lang.String r1 = "FreePlayViewImpl"
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x0143 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0143 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r1, r2, r3)     // Catch:{ all -> 0x0143 }
            java.lang.Object r2 = r6.mWindowLock
            monitor-enter(r2)
            java.lang.Object r1 = r6.mWindowLock     // Catch:{ all -> 0x00df }
            r1.notifyAll()     // Catch:{ all -> 0x00df }
            monitor-exit(r2)     // Catch:{ all -> 0x00df }
            goto L_0x008e
        L_0x00df:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00df }
            throw r1
        L_0x00e2:
            if (r8 != 0) goto L_0x00e8
            com.alipay.multimedia.gles.WindowSurface10 r1 = r6.mDisplaySurface     // Catch:{ Exception -> 0x00bc }
            if (r1 != 0) goto L_0x0085
        L_0x00e8:
            boolean r1 = r6.mWindowAvailable     // Catch:{ Exception -> 0x00bc }
            if (r1 == 0) goto L_0x0103
            java.lang.String r1 = "FreePlayViewImpl"
            java.lang.String r2 = "native_window already connected, just skip."
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x00bc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I(r1, r2, r3)     // Catch:{ Exception -> 0x00bc }
            java.lang.Object r2 = r6.mWindowLock
            monitor-enter(r2)
            java.lang.Object r1 = r6.mWindowLock     // Catch:{ all -> 0x0100 }
            r1.notifyAll()     // Catch:{ all -> 0x0100 }
            monitor-exit(r2)     // Catch:{ all -> 0x0100 }
            goto L_0x00ac
        L_0x0100:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0100 }
            throw r1
        L_0x0103:
            r6.handleReleaseWindow()     // Catch:{ Exception -> 0x00bc }
            java.lang.String r1 = "FreePlayViewImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00bc }
            java.lang.String r3 = "recreate windowsurface and surface id:"
            r2.<init>(r3)     // Catch:{ Exception -> 0x00bc }
            java.lang.StringBuilder r2 = r2.append(r7)     // Catch:{ Exception -> 0x00bc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00bc }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x00bc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I(r1, r2, r3)     // Catch:{ Exception -> 0x00bc }
            android.graphics.SurfaceTexture r1 = r6.mVideoTexture     // Catch:{ Exception -> 0x00bc }
            if (r1 == 0) goto L_0x0126
            android.graphics.SurfaceTexture r1 = r6.mVideoTexture     // Catch:{ Exception -> 0x00bc }
            r1.setOnFrameAvailableListener(r6)     // Catch:{ Exception -> 0x00bc }
        L_0x0126:
            java.lang.String r1 = "FreePlayViewImpl"
            java.lang.String r2 = "handleSetSurfaceTexture mDisplaySurface"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x00bc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I(r1, r2, r3)     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.WindowSurface10 r1 = new com.alipay.multimedia.gles.WindowSurface10     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.EglCore10 r2 = r6.mEglCore     // Catch:{ Exception -> 0x00bc }
            r1.<init>(r2, r7)     // Catch:{ Exception -> 0x00bc }
            r6.mDisplaySurface = r1     // Catch:{ Exception -> 0x00bc }
            com.alipay.multimedia.gles.WindowSurface10 r1 = r6.mDisplaySurface     // Catch:{ Exception -> 0x00bc }
            r1.makeCurrent()     // Catch:{ Exception -> 0x00bc }
            r1 = 1
            r6.mWindowAvailable = r1     // Catch:{ Exception -> 0x00bc }
            goto L_0x0085
        L_0x0143:
            r1 = move-exception
            java.lang.Object r2 = r6.mWindowLock
            monitor-enter(r2)
            java.lang.Object r3 = r6.mWindowLock     // Catch:{ all -> 0x0151 }
            r3.notifyAll()     // Catch:{ all -> 0x0151 }
            monitor-exit(r2)     // Catch:{ all -> 0x0151 }
            throw r1
        L_0x014e:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x014e }
            throw r1
        L_0x0151:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0151 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.widget.FreePlayViewImpl.handleSetSurfaceTexture(android.graphics.SurfaceTexture, boolean):void");
    }

    /* access modifiers changed from: private */
    public void handleFrameAvailable() {
        if (this.mEglCore == null) {
            Logger.I(TAG, "Skipping drawFrame if no egl context", new Object[0]);
            return;
        }
        this.mVideoTexture.updateTexImage();
        if (!this.mWindowAvailable || !isAvailable()) {
            Logger.I(TAG, "Skipping drawFrame when no surface", new Object[0]);
            return;
        }
        this.mFrameIndex++;
        if (this.mReported.compareAndSet(false, true)) {
            VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_FIRST_FRAME_SHOW, System.nanoTime());
            VideoBenchmark.reportPlaying(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString());
        }
        if (this.mFrameIndex % 30 == 0) {
            Logger.I(TAG, "handleFrameAvailable", new Object[0]);
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
        if (!this.mDisplaySurface.swapBuffers() && this.mFrameIndex >= 3) {
            this.mErrCode = -109;
            stop();
            if (this.mErrorListener != null) {
                this.mErrorListener.onError(-109, this.mVideoId);
            }
        }
        if (this.mOnInfoListener != null && this.mNoFrameCome && !this.mPaused && this.mStarted) {
            this.mOnInfoListener.onInfo(3, null);
            this.mNoFrameCome = false;
            Logger.D(TAG, this + "video go to playing state.", new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void handleDrawBitmap(Bitmap bitmap, boolean needRotate) {
        LogHelper.i(TAG, "handleDrawBitmap, needRotate:" + needRotate);
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_THUMB_RENDER_BEGIN, System.nanoTime());
        long ts = System.currentTimeMillis();
        generateMVPMatrix();
        if (!(bitmap == null || this.mFullThumbBlit == null || !this.mWindowAvailable)) {
            Logger.I(TAG, this + "\tdo draw bitmap", new Object[0]);
            this.mVideoWidth = bitmap.getWidth();
            this.mVideoHeight = bitmap.getHeight();
            if (needRotate) {
                adjustVideoSize();
            }
            generateViewport();
            try {
                this.mDisplaySurface.makeCurrent();
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES20.glClear(16384);
                GLES20.glViewport(this.mX, this.mY, this.mW, this.mH);
                this.mImgTexId = this.mFullThumbBlit.createImageTexture(bitmap, this.mEglCore.getGl10());
                if (!this.mCenterCropped) {
                    this.mFullThumbBlit.drawFrame(this.mImgTexId, this.mTmpMatrix, needRotate ? GlUtil.getRotateMatrix(this.mDegree) : GlUtil.IDENTITY_MATRIX);
                } else {
                    this.mFullThumbBlit.drawCroppedFrame(this.mImgTexId, this.mTmpMatrix, this.mVideoWidth, this.mVideoHeight, this.mDisplayWidth, this.mDisplayHeight);
                }
                this.mDisplaySurface.swapBuffers();
                this.mFullThumbBlit.freeImageTexture(this.mImgTexId);
            } catch (Exception e) {
                Logger.E(TAG, e.getMessage(), new Object[0]);
            }
        }
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_THUMB_RENDER_END, System.nanoTime());
        Logger.I(TAG, this + "\tdraw bitmap took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
    }

    public void setVideoParams(VideoPlayParams params) {
        Logger.I(TAG, this + "\tsetVideoParams" + params, new Object[0]);
        Message msg = Message.obtain();
        msg.what = 12;
        msg.obj = params;
        checkHandler();
        this.mHandler.removeMessages(12);
        this.mHandler.sendMessage(msg);
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
        Logger.I(TAG, "setOnCompletionListener: " + listener, new Object[0]);
        this.mCompletionListener = listener;
    }

    public void setOnInfoListener(SightVideoPlayView.OnInfoListener listener) {
        this.mOnInfoListener = listener;
    }

    public void setOnErrorListener(OnPlayErrorListener listener) {
        Logger.I(TAG, "setOnErrorListener: " + listener, new Object[0]);
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
        Logger.I(TAG, this + " prepare done Url: " + this.mPlayUrl + "\tcurrent time: " + this.mCurPlayTime, new Object[0]);
        UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_PLAY_VD, "0");
        prepareDone();
    }

    public void setVideoRotation(int degree) {
        LogHelper.i(TAG, "setVideoRotation:" + degree);
        if (degree % 90 != 0) {
            throw new RuntimeException("degree is invalid.");
        }
        this.mDegree = degree;
    }

    private synchronized void adjustVideoSize() {
        LogHelper.i(TAG, "adjustVideoSize mDegree:" + this.mDegree + ", mVideoWidth:" + this.mVideoWidth + ", mVideoHeight:" + this.mVideoHeight);
        if (this.mDegree == 270 || this.mDegree == 90) {
            int tmp = this.mVideoHeight;
            this.mVideoHeight = this.mVideoWidth;
            this.mVideoWidth = tmp;
        }
    }

    private void prepareDone() {
        if (!this.mStarted) {
            VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_VIDEO_PREPARED, System.nanoTime());
            this.mVideoWidth = this.mMediaPlayer.getVideoWidth();
            this.mVideoHeight = this.mMediaPlayer.getVideoHeight();
            if (this.mEffect == VideoPlayParams.EFFECT_TRANSPARENT) {
                this.mVideoWidth /= 2;
            }
            adjustVideoSize();
            generateViewport();
            this.mMediaPlayer.start();
            if (this.mCurPlayTime > 0) {
                this.mMediaPlayer.seekTo((long) ((int) this.mCurPlayTime));
            }
            this.mStarted = true;
            if (this.mEnableAudio && this.mAudioPaused.compareAndSet(false, true)) {
                AudioUtils.pauseSystemAudio();
            }
            this.mPreparing = false;
            this.mPreparExp = false;
            this.mFrameIndex = 0;
            startCheckProgress();
            if (this.mPrepareListener != null) {
                Bundle bundle = new Bundle();
                Long dur = Long.valueOf(this.mMediaPlayer.getDuration());
                Logger.I(TAG, "prepareDone duration:" + dur, new Object[0]);
                bundle.putLong("duration", dur.longValue());
                this.mPrepareListener.onPrepared(bundle);
            }
        }
    }

    public void onCompletion(IMediaPlayer mp) {
        Logger.I(TAG, this + "\tonCompletion,cb is" + this.mCompletionListener, new Object[0]);
        if (this.mCompletionListener != null && this.mStarted) {
            this.mCompletionListener.onCompletion(null);
        }
        if (this.mSurfaceTexture != null && this.mIsLoop && this.mStarted) {
            this.mHandler.sendEmptyMessage(3);
            this.mHandler.sendEmptyMessage(0);
        } else if (this.mAudioPaused.compareAndSet(true, false) && this.mEnableAudio) {
            AudioUtils.resumeSystemAudio();
        }
    }

    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
        if (this.mBufferingListener != null) {
            this.mBufferingListener.onBufferingUpdate(percent, null);
        }
    }

    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        Logger.I(TAG, this + " onInfo, what: " + what, new Object[0]);
        if (this.mOnInfoListener != null && what == 701) {
            new Bundle().putInt("extra", extra);
        }
        if (what == 10002 || what == 3) {
            prepareDone();
            Logger.I(TAG, this + "onInfo MEDIA_INFO_VIDEO_RENDERING_START", new Object[0]);
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
        Logger.I(TAG, this + " onSeekComplete", new Object[0]);
        if (this.mSeekListener != null) {
            this.mSeekListener.onSeekComplete(null);
        }
        startCheckProgress();
    }

    public boolean onError(IMediaPlayer mp, int what, int extra) {
        Logger.I(TAG, this + " onError:" + what + "," + extra + " file: " + this.mPlayUrl + ", mVideoId: " + this.mVideoId, new Object[0]);
        this.mStarted = false;
        this.mPreparing = false;
        this.mPreparExp = false;
        this.mErrCode = what;
        if (this.mErrorListener != null) {
            this.mErrorListener.onError(what, this.mVideoId);
        }
        FileCacheModel videoInfo = VideoFileManager.getInstance().getVideoInfo(this.mVideoId);
        if (videoInfo == null || (videoInfo.tag & 4096) == 0) {
            UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_PLAY_VD, String.valueOf(what));
        }
        if (PathUtils.extractFile(this.mPlayUrl) != null) {
            Logger.I(TAG, "delete broken file:" + this.mPlayUrl + "ret:" + new File(this.mPlayUrl).delete(), new Object[0]);
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
        this.mHandler.removeMessages(19);
        this.mHandler.sendEmptyMessage(19);
    }

    public void pause() {
        if (!this.mStarted || this.mPreparing) {
            Logger.I(TAG, "pause before started", new Object[0]);
            return;
        }
        this.mHandler.removeMessages(11);
        this.mHandler.sendEmptyMessage(11);
    }

    public void resume() {
        if (!this.mStarted || this.mPreparing) {
            Logger.I(TAG, "resume before started", new Object[0]);
            return;
        }
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessage(1);
    }

    public void seekTo(long msec) {
        if (!this.mStarted || this.mPreparing) {
            Logger.I(TAG, "seek before started", new Object[0]);
            return;
        }
        this.mHandler.removeMessages(10);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(10, (int) msec, 0));
    }

    public void start() {
        Logger.I(TAG, this + "\tstart###", new Object[0]);
        checkHandler();
        startInternal();
    }

    public void start(int msec) {
        this.mCurPlayTime = (long) msec;
        start();
    }

    public void start(String id, int msec) {
        Logger.I(TAG, this + "\tstart###", new Object[0]);
        setVideoId(id);
        checkHandler();
        this.mCurPlayTime = (long) msec;
        startInternal();
    }

    public void start(String id) {
        Logger.I(TAG, this + "\tstart###", new Object[0]);
        setVideoId(id);
        checkHandler();
        startInternal();
    }

    private void startInternal() {
        if (!this.mPreparing || this.mPreparExp) {
            this.mErrCode = 0;
            if (this.mMediaPlayer == null || !this.mMediaPlayer.isPlaying()) {
                drawThumbnail();
            }
            this.mHandler.removeMessages(0);
            this.mHandler.sendEmptyMessage(0);
            return;
        }
        Logger.I(TAG, "preparing, skip start", new Object[0]);
    }

    public long getCurrentPosition() {
        Logger.I(TAG, this + "\tgetCurrentPosition###", new Object[0]);
        if (this.mMediaPlayer != null && this.mStarted) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        Logger.I(TAG, this + "\tgetCurrentPosition = -1", new Object[0]);
        return -1;
    }

    public long getDuration() {
        if (TextUtils.isEmpty(this.mPlayUrl)) {
            Logger.I(TAG, "getDuration mPlayUrl is null", new Object[0]);
            return 0;
        } else if (this.mMediaPlayer != null && this.mStarted) {
            return this.mMediaPlayer.getDuration();
        } else {
            Logger.W(TAG, "getDuration called before started, get from IO", new Object[0]);
            int dur = getVideoInfo().duration;
            if (dur <= 0) {
                Logger.I(TAG, "getDuration dur=" + dur, new Object[0]);
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
        this.mHandler.removeMessages(18);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(18, mute ? 1 : 0, 0));
    }

    public void setScreenOnWhilePlaying(boolean screenOn) {
        this.mKeepScreenOn = screenOn;
    }

    public void stop() {
        boolean flag;
        if (this.mStarted || this.mPreparing) {
            Logger.I(TAG, this + "\tstop###", new Object[0]);
            this.mCurPlayTime = 0;
            this.mPreparing = false;
            this.mPreparExp = false;
            if (this.mHandler != null) {
                this.mHandler.removeMessages(3);
                this.mHandler.sendEmptyMessage(3);
            }
            synchronized (this.mSurfaceLock) {
                this.mSurfaceLock.notifyAll();
            }
            if (this.mHandler != null) {
                this.mHandler.sendEmptyMessage(8);
            }
            if (this.mProHandler != null) {
                this.mProHandler.sendEmptyMessage(17);
            }
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
            if (this.mAudioPaused.compareAndSet(true, false) && this.mEnableAudio) {
                AudioUtils.resumeSystemAudio();
                return;
            }
            return;
        }
        Logger.I(TAG, "invalid stop, skip###", new Object[0]);
    }

    public void drawThumbnail() {
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_PLAY_START, System.nanoTime());
        checkHandler();
        this.mHandler.removeMessages(5);
        this.mHandler.sendEmptyMessage(5);
    }

    public void drawBitmap(Bitmap bitmap) {
        Logger.I(TAG, this + "\tdrawBitmap bitmap: " + bitmap, new Object[0]);
        drawBitmap(bitmap, true);
    }

    public void drawBitmap(Bitmap bitmap, boolean needRotate) {
        int i = 0;
        Logger.I(TAG, this + "\tdrawBitmap bitmap: " + bitmap, new Object[0]);
        try {
            VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_PLAY_START, System.nanoTime());
        } catch (Throwable t) {
            Logger.I(TAG, "drawBitmap skip exception, t: " + t, new Object[0]);
        }
        checkHandler();
        Message msg = Message.obtain();
        if (needRotate) {
            i = 1;
        }
        msg.arg1 = i;
        msg.obj = bitmap;
        msg.what = 7;
        if (this.mHandler != null) {
            this.mHandler.sendMessage(msg);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        Logger.I(TAG, this + "\tonAttachedToWindow", new Object[0]);
        super.onAttachedToWindow();
        addPlaceholder();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        Logger.I(TAG, this + "\tonDetachedFromWindow mMediaPlayer: " + this.mMediaPlayer, new Object[0]);
        super.onDetachedFromWindow();
        this.mWindowAvailable = false;
    }

    /* access modifiers changed from: private */
    public void handlePrepare() {
        this.mPreparExp = false;
        initBehavior();
        try {
            if (!this.mStarted || this.mMediaPlayer == null) {
                this.mStarted = false;
                this.mPreparing = true;
                Logger.I(TAG, this + " handlePrepare begin, path:" + this.mPlayUrl + ", mMediaPlayer: " + this.mMediaPlayer, new Object[0]);
                if (this.mMediaPlayer != null) {
                    Logger.I(TAG, "clear previous mediaplayer", new Object[0]);
                    this.mMediaPlayer.reset();
                    this.mMediaPlayer.release();
                    this.mMediaPlayer = null;
                }
                closeParcelFD();
                this.mMediaPlayer = new IjkMediaPlayer();
                this.mMediaPlayer.setOnCompletionListener(this);
                this.mMediaPlayer.setOnErrorListener(this);
                this.mMediaPlayer.setOnBufferingUpdateListener(this);
                this.mMediaPlayer.setOnPreparedListener(this);
                this.mMediaPlayer.setOnSeekCompleteListener(this);
                this.mMediaPlayer.setOnInfoListener(this);
                this.mMediaPlayer.setOnDownloadStatusListener(this);
                this.mNoFrameCome = true;
                this.mCachePath = null;
                if (this.mEnableCache && FileUtils.isSDcardAvailableSpace(ConfigManager.getInstance().diskConf().urlVideoNeedSpace)) {
                    if (this.mWriteCache) {
                        this.mCachePath = VideoFileManager.getInstance().getDiskCache().genPathByKey(this.mPlayUrl);
                        this.mMediaPlayer.setOption(4, (String) "data-copy", this.mCachePath);
                        Logger.D(TAG, "###enable cache and path is:" + this.mCachePath, new Object[0]);
                    }
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
                this.mMediaPlayer.setLooping(this.mIsLoop);
                Logger.I(TAG, "after setDataSource", new Object[0]);
                this.mMediaPlayer.setSurface(this.mSurface);
                Logger.I(TAG, "after setDisplay", new Object[0]);
                if (this.mKeepScreenOn) {
                    Logger.I(TAG, this + "setWakeMode", new Object[0]);
                    this.mMediaPlayer.setWakeMode(getContext(), 536870922);
                }
                Logger.I(TAG, "after setScreenOnWhilePlaying", new Object[0]);
                this.mMediaPlayer.prepareAsync();
                Logger.I(TAG, "after prepare", new Object[0]);
                this.mSeekWhenResume = -1;
                return;
            }
            this.mMediaPlayer.start();
            Logger.I(TAG, this + "resume play", new Object[0]);
        } catch (Exception e) {
            Logger.E((String) TAG, this + " prepare exception:" + e.getMessage(), (Throwable) e, new Object[0]);
            this.mStarted = false;
            if (TextUtils.isEmpty(this.mPlayUrl)) {
                this.mPreparExp = true;
            }
            this.mErrCode = -1;
            if (this.mErrorListener != null) {
                Logger.E(TAG, "onError callback", new Object[0]);
                this.mErrorListener.onError(this.mErrCode, this.mVideoId);
            }
            if (this.mMediaPlayer != null) {
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
        Logger.I(TAG, "handleResume", new Object[0]);
        this.mPaused = false;
        if (this.mMediaPlayer != null) {
            Logger.I(TAG, "handleResume enter if", new Object[0]);
            this.mMediaPlayer.start();
            if (this.mSeekWhenResume >= 0) {
                this.mMediaPlayer.seekTo((long) this.mSeekWhenResume);
                this.mSeekWhenResume = -1;
            }
            if (this.mNoFrameCome && this.mOnInfoListener != null) {
                this.mOnInfoListener.onInfo(701, null);
            }
        }
        if (this.mProHandler != null) {
            this.mProHandler.sendEmptyMessage(16);
        }
    }

    /* access modifiers changed from: private */
    public void handlePause() {
        Logger.I(TAG, "handlePause", new Object[0]);
        this.mPaused = true;
        this.mNoFrameCome = true;
        if (this.mMediaPlayer != null) {
            Logger.I(TAG, "handlePause enter if", new Object[0]);
            this.mMediaPlayer.pause();
        }
        if (this.mProHandler != null) {
            this.mProHandler.removeMessages(16);
        }
    }

    /* access modifiers changed from: private */
    public void handleSeek(int msec) {
        Logger.I(TAG, "handleSeek: " + msec, new Object[0]);
        if (isPlaying()) {
            long ts = System.currentTimeMillis();
            this.mMediaPlayer.seekTo((long) msec);
            Logger.I(TAG, "seekTo took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
            this.mSeekWhenResume = -1;
            return;
        }
        this.mSeekWhenResume = msec;
    }

    /* access modifiers changed from: private */
    public void handleMute(boolean mute) {
        Logger.D(TAG, "handleMute: " + mute, new Object[0]);
        if (this.mStarted && this.mMediaPlayer != null) {
            if (mute) {
                this.mMediaPlayer.setVolume(0.0f, 0.0f);
            } else {
                this.mMediaPlayer.setVolume(1.0f, 1.0f);
            }
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
    public void handleReleaseWindow() {
        Logger.I(TAG, "handleReleaseWindow", new Object[0]);
        if (this.mDisplaySurface != null) {
            this.mDisplaySurface.release();
            this.mDisplaySurface = null;
        }
    }

    /* access modifiers changed from: private */
    public void handleReset() {
        Logger.I(TAG, "handleReset(), mMediaPlayer: " + this.mMediaPlayer, new Object[0]);
        this.mPaused = false;
        this.mStopTime = System.currentTimeMillis();
        if (this.mStarted) {
            reportEvent();
        }
        this.mStarted = false;
        this.mNoFrameCome = true;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            closeParcelFD();
            if (this.mAudioPaused.compareAndSet(true, false) && this.mEnableAudio) {
                AudioUtils.resumeSystemAudio();
            }
            try {
                clearIncompleteCache();
            } catch (Exception e) {
                Logger.E((String) TAG, (String) "clearIncompleteCache", (Throwable) e, new Object[0]);
            }
            Logger.I(TAG, this + "mediaplayer handleReset done.", new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void handleParseParams(VideoPlayParams params) {
        this.mWriteCache = params.mEnableCache;
        String id = params.mVideoId;
        this.mBizId = params.mBizId;
        this.mEnableAudio = params.mEnableAudio;
        this.mProgressInterval = params.mProgressInterval;
        this.mEffect = params.mEffect;
        Logger.I(TAG, this + "begin handleParseParams: " + id, new Object[0]);
        Logger.D(TAG, this + "effect " + this.mEffect, new Object[0]);
        if (PathUtils.extractFile(id) != null) {
            String id2 = PathUtils.extractFile(id).getAbsolutePath();
            this.mPlayUrl = id2;
            this.mVideoId = id2;
            Logger.I(TAG, this + "setVideoId: " + id2, new Object[0]);
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
            this.mEnableCache = true;
            VideoFileManager.getInstance().getDiskCache().remove(id);
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
            } else if (SandboxWrapper.isContentUriPath(id)) {
                this.mVideoId = id;
                this.mPlayUrl = this.mVideoId;
                if (SandboxWrapper.checkFileExist(this.mPlayUrl)) {
                    this.mEnableCache = false;
                }
            } else {
                Logger.D(TAG, this + "handleParseParams invalid input param: " + id, new Object[0]);
            }
        } else {
            this.mPlayUrl = path;
            this.mVideoId = id;
            this.mEnableCache = false;
        }
        Logger.I(TAG, this + "end handleParseParams: " + this.mPlayUrl, new Object[0]);
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
        if (this.mPboCreated) {
            this.mPboCreated = false;
            GLES30.glDeleteBuffers(1, this.mPBO_id, 0);
        }
        if (this.mTexture != null) {
            this.mTexture.release();
            this.mTexture = null;
        }
        if (this.mFbo != null) {
            this.mFbo.release();
            this.mFbo = null;
        }
        if (this.mCurFrame != null) {
            this.mCurFrame.recycle();
            this.mCurFrame = null;
        }
        if (this.mThumbnail != null) {
            this.mThumbnail.recycle();
            this.mThumbnail = null;
        }
        Logger.I(TAG, this + "\treleaseGl end", new Object[0]);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int w, int h) {
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_BUFFER_PREPARED, System.nanoTime());
        Logger.I(TAG, this + " onSurfaceTextureAvailable and notify######, w = " + getWidth() + ", h = " + getHeight(), new Object[0]);
        if (isPlaying()) {
            generateViewport();
        }
        this.mSurfaceTexture = surfaceTexture;
        synchronized (this.mSurfaceLock) {
            this.mSurfaceLock.notifyAll();
            Logger.I(TAG, this + "\tafter onSurfaceTextureAvailable and notify######", new Object[0]);
        }
        if (this.mPreSurfaceTexture != null) {
            Logger.I(TAG, "surface recreate.", new Object[0]);
            if (this.mHandler != null) {
                this.mHandler.removeMessages(13);
                this.mHandler.sendEmptyMessage(13);
            }
            Logger.I(TAG, this + "\twaiting surface window creating", new Object[0]);
            synchronized (this.mWindowLock) {
                try {
                    this.mWindowLock.wait(1000);
                } catch (Exception e) {
                    Logger.I(TAG, "onSurfaceTextureAvailable exp" + e.toString(), new Object[0]);
                }
            }
            Logger.I(TAG, this + "\twaiting surface window end", new Object[0]);
        }
        this.mPreSurfaceTexture = this.mSurfaceTexture;
        hidePlaceholder();
        if (!this.mCenterCropFixed) {
            this.mDisplayWidth = w;
            this.mDisplayHeight = h;
        }
        if (this.mPaused) {
            drawBitmap(this.mCurFrame, false);
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int w, int h) {
        Logger.I(TAG, this + " onSurfaceTextureSizeChanged w:" + w + ", h:" + h, new Object[0]);
        if (!this.mCenterCropFixed) {
            this.mDisplayHeight = h;
            this.mDisplayWidth = w;
        }
        generateViewport();
        if (this.mPaused) {
            drawBitmap(this.mCurFrame, false);
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Logger.I(TAG, this + " onSurfaceTextureDestroyed", new Object[0]);
        Logger.I(TAG, this + "\tonSurfaceTextureDestroyed done", new Object[0]);
        this.mWindowAvailable = false;
        if (this.mEffect != VideoPlayParams.EFFECT_TRANSPARENT) {
            showPlaceholder();
        }
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (this.mSurfaceTexture != surfaceTexture) {
            Logger.I(TAG, "###surfacetexture error###", new Object[0]);
        }
    }

    private void checkHandler() {
        synchronized (this.mHandlerLock) {
            if (this.mHandler == null || this.mThread == null || !this.mThread.isAlive() || this.mHandler.getLooper() == null) {
                Logger.I(TAG, this + "\trender thread not ready, create...", new Object[0]);
                releaseGl();
                this.mThread = new HandlerThread("reputation_sight_play");
                this.mThread.start();
                this.mHandler = new PlayHandler(this, this.mThread.getLooper());
                this.mStarted = false;
                this.mPreparing = false;
                this.mPreparExp = false;
            }
        }
        if (this.mProHandler != null && this.mProThread != null && this.mProThread.isAlive() && this.mProHandler.getLooper() != null) {
            return;
        }
        if (this.mProgressListener == null) {
            Logger.D(TAG, this + "\tthere is no need to create progress thread.", new Object[0]);
            return;
        }
        Logger.D(TAG, this + "\tprogress thread not ready, create...", new Object[0]);
        this.mProThread = new HandlerThread("url_sight_play_pro");
        this.mProThread.start();
        this.mProHandler = new ProgressHandler(this, this.mProThread.getLooper());
    }

    private void setExceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                Logger.E(FreePlayViewImpl.TAG, "uncaughtException###, thread name:" + thread.getName() + ", thread id:" + thread.getId() + ",ex:" + ex.getMessage(), new Object[0]);
                StackTraceElement[] elements = ex.getStackTrace();
                StringBuilder sb = new StringBuilder();
                for (StackTraceElement stackTraceElement : elements) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
                Logger.E(FreePlayViewImpl.TAG, "exception stack:\n" + sb.toString(), new Object[0]);
                FreePlayViewImpl.this.releaseGl();
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
            File f = PathUtils.extractFile(this.mPlayUrl);
            if (f != null) {
                filesize = f.length();
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
            ext4.put(DictionaryKeys.EVENT_TYPE_FOCUS, "0");
            ext4.put(H5Param.SAFEPAY_CONTEXT, "0");
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
            long t = System.nanoTime();
            if (!TextUtils.isEmpty(path)) {
                bitmap = ImageUtils.decodeBitmapByFalcon(new File(path));
            }
            VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_THUMB_DECODE_TIME, System.nanoTime() - t);
            CacheContext cacheContext2 = CacheContext.get();
            if (model != null) {
                str2 = model.businessId;
            }
            cacheContext2.getMemCache(str2).put(cacheKey, bitmap);
        }
        Logger.I(TAG, "operation getThumbnail took " + (System.currentTimeMillis() - ts) + "ms, bitmap id:" + bitmap, new Object[0]);
        this.mThumbnail = bitmap;
        return bitmap;
    }

    private Bitmap getCurrentFrame() {
        Logger.I(TAG, "getCurrentFrame", new Object[0]);
        if (!this.mStarted) {
            Logger.I(TAG, "getCurrentFrame but not start, return the thumbnail", new Object[0]);
            return this.mThumbnail;
        } else if (!this.mUsePBO) {
            Logger.I(TAG, "pbo not support.", new Object[0]);
            return null;
        } else {
            if (this.mHandler != null) {
                this.mHandler.removeMessages(15);
                this.mHandler.sendEmptyMessage(15);
            }
            synchronized (this.mFrameLock) {
                try {
                    this.mFrameLock.wait(1000);
                } catch (Exception e) {
                    Logger.E((String) TAG, (String) "mFrameLock.wait exp", (Throwable) e, new Object[0]);
                }
            }
            return this.mCurFrame;
        }
    }

    private void bindFBO(int fbo, int w, int h) {
        GLES20.glBindFramebuffer(36160, fbo);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16384);
        GLES20.glViewport(0, 0, w, h);
    }

    /* access modifiers changed from: private */
    public void handleCurrentFrame(int w, int h) {
        if (this.mEglCore == null) {
            Logger.I(TAG, "handleCurrentFrame but no egl context, skip...", new Object[0]);
            synchronized (this.mFrameLock) {
                this.mFrameLock.notifyAll();
            }
            return;
        }
        if (this.mStarted) {
            this.firstCreated = false;
            if (this.mTexture == null) {
                this.mTexture = new GlTexture(3553, w, h);
            }
            if (this.mFbo == null) {
                this.mFbo = new GlFrameBuffer(this.mTexture.getID());
                this.firstCreated = true;
            }
            bindFBO(this.mFbo.getID(), w, h);
            float[] mat = new float[16];
            Matrix.setIdentityM(mat, 0);
            Matrix.rotateM(mat, 0, 180.0f, 1.0f, 0.0f, 0.0f);
            float[] result = new float[16];
            Matrix.multiplyMM(result, 0, mat, 0, GlUtil.getRotateMatrix(this.mDegree), 0);
            if (!this.mCenterCropped) {
                this.mFullFrameBlit.drawFrame(this.mTextureId, this.mTmpMatrix, result);
                if (this.firstCreated) {
                    this.mFullFrameBlit.drawFrame(this.mTextureId, this.mTmpMatrix, result);
                }
            } else {
                this.mFullFrameBlit.drawCroppedFrame(this.mTextureId, this.mTmpMatrix, this.mVideoWidth, this.mVideoHeight, this.mDisplayWidth, this.mDisplayHeight);
                if (this.firstCreated) {
                    this.mFullFrameBlit.drawCroppedFrame(this.mTextureId, this.mTmpMatrix, this.mVideoWidth, this.mVideoHeight, this.mDisplayWidth, this.mDisplayHeight);
                }
            }
            long start = System.currentTimeMillis();
            getPixelFromPBO(w, h, false);
            Logger.I(TAG, "*****handleCurrentFrame took " + (System.currentTimeMillis() - start) + RPCDataParser.TIME_MS, new Object[0]);
            GLES20.glBindFramebuffer(36160, 0);
        } else {
            Logger.I(TAG, "handlecurrentframe but not start, return the thumbnail", new Object[0]);
        }
        synchronized (this.mFrameLock) {
            this.mFrameLock.notifyAll();
        }
    }

    private void getPixelFromPBO(int width, int height, boolean isDefaultFb) {
        int pbo_size = width * height * 4;
        try {
            if (!this.mPboCreated) {
                GLES30.glGenBuffers(1, this.mPBO_id, 0);
                Logger.I(TAG, "glGenBuffers pbo_id[0]:" + this.mPBO_id[0], new Object[0]);
                GLES30.glBindBuffer(35051, this.mPBO_id[0]);
                GLES30.glBufferData(35051, pbo_size, null, 35049);
                GLES30.glBindBuffer(35051, 0);
                this.mPboCreated = true;
            }
            GLES30.glPixelStorei(3333, 1);
            GlUtil.checkGlError("glPixelStorei");
            if (isDefaultFb) {
                GLES30.glReadBuffer(1029);
            } else {
                GLES30.glReadBuffer(36064);
            }
            GlUtil.checkGlError("glReadBuffer");
            GLES30.glBindBuffer(35051, this.mPBO_id[0]);
            GlUtil.checkGlError("glBindBuffer 1 ");
            long ts = System.currentTimeMillis();
            FFmpegCameraEncoderJni.glReadPixelsPBOJNI(0, 0, width, height, 6408, 5121, 0);
            Logger.I(TAG, "glReadPixelsPBOJNI took " + (System.currentTimeMillis() - ts) + "ms\n\n\n", new Object[0]);
            GlUtil.checkGlError("glReadPixels");
            long ts2 = System.currentTimeMillis();
            ByteBuffer buf = (ByteBuffer) GLES30.glMapBufferRange(35051, 0, pbo_size, 1);
            GlUtil.checkGlError("glMapBufferRange");
            Logger.I(TAG, "*****glMapBufferRange took " + (System.currentTimeMillis() - ts2) + RPCDataParser.TIME_MS, new Object[0]);
            if (this.mCurFrame == null) {
                this.mCurFrame = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            }
            this.mCurFrame.copyPixelsFromBuffer(buf);
            GLES30.glUnmapBuffer(35051);
            GlUtil.checkGlError("glUnmapBuffer");
            GLES30.glBindBuffer(35051, 0);
            GlUtil.checkGlError("glBindBuffer 0 ");
        } catch (Exception e) {
            Logger.E((String) TAG, (String) "DO PBO  exp", (Throwable) e, new Object[0]);
        }
    }

    private void superMMDestroyHardwareResources() {
        Logger.D(TAG, "call superMMDestroyHardwareResources", new Object[0]);
        String[] strArr = {"resetDisplayList", "destroySurface", "invalidateParentCaches"};
        for (int i = 0; i < 3; i++) {
            Method method = ReflectUtils.findMethod((Class) getClass(), strArr[i], (Class<?>[]) new Class[0]);
            if (method != null) {
                method.setAccessible(true);
                ReflectUtils.invoke(this, method, new Object[0]);
            }
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void destroyHardwareResources() {
        Logger.W(TAG, "destroyHardwareResources", new Object[0]);
        if (isReputation() && isXiaomiMarshmallow() && ConfigManager.getInstance().getCommonConfigItem().videoConf.retainsurface == 0) {
            Logger.W(TAG, "destroy surface for xiaomi 6.0 phones", new Object[0]);
            superMMDestroyHardwareResources();
        }
    }

    private boolean isXiaomiMarshmallow() {
        return "Xiaomi".equalsIgnoreCase(Build.MANUFACTURER) && VERSION.SDK_INT == 23;
    }

    private boolean isReputation() {
        return this.mBizId != null && this.mBizId.toLowerCase().contains("o2o_home");
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
        if (this.bLog) {
            Logger.D(TAG, this + "handleCheckProgress, mSarted:" + this.mStarted + "mProgressListener:" + this.mProgressListener + ";mMediaPlayer.isPlaying():" + (this.mMediaPlayer != null ? Boolean.valueOf(this.mMediaPlayer.isPlaying()) : "FALSE"), new Object[0]);
        }
        if (this.mMediaPlayer != null && this.mStarted && this.mMediaPlayer.isPlaying() && this.mProgressListener != null) {
            long curPostion = this.mMediaPlayer.getCurrentPosition();
            this.mProgressListener.onProgressUpdate(curPostion);
            if (this.bLog) {
                Logger.D(TAG, this + "onProgressUpdate callback pos:" + curPostion, new Object[0]);
            }
        }
        if (this.mCheckProgress && !this.mPaused) {
            this.mProHandler.sendEmptyMessageDelayed(16, (long) this.mProgressInterval);
        }
    }

    private void startCheckProgress() {
        Logger.D(TAG, this + "startCheckProgress...", new Object[0]);
        if (this.mProgressListener != null) {
            this.mCheckProgress = true;
            this.mProHandler.removeMessages(16);
            this.mProHandler.obtainMessage(16).sendToTarget();
        }
    }

    /* access modifiers changed from: private */
    public void stopCheckProgress() {
        Logger.D(TAG, this + "stopCheckProgress...", new Object[0]);
        if (this.mProgressListener != null) {
            this.mProHandler.removeMessages(16);
            this.mCheckProgress = false;
        }
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
