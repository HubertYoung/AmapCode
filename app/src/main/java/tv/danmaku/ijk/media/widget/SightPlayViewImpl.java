package tv.danmaku.ijk.media.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.Matrix;
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
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.BubbleEffectParams;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.VideoPlayParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnPlayErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnProgressUpdateListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory.Request;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CacheUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ReflectUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoFileManager;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.multimedia.gles.EglCore10;
import com.alipay.multimedia.gles.FullFrameRect;
import com.alipay.multimedia.gles.GlUtil;
import com.alipay.multimedia.gles.Texture2dProgram;
import com.alipay.multimedia.gles.Texture2dProgram.ProgramType;
import com.alipay.multimedia.gles.WindowSurface10;
import com.alipay.sdk.sys.a;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.video.VideoInfo;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import tv.danmaku.ijk.media.gles.RoundFrameRect;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnDownloadStatusListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnSeekCompleteListener;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@TargetApi(15)
public class SightPlayViewImpl extends SightPlayView implements OnFrameAvailableListener, SurfaceTextureListener, OnBufferingUpdateListener, OnCompletionListener, OnDownloadStatusListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener {
    private static final int ANDROID_N = 24;
    private static final int MSG_BITMAP = 7;
    private static final int MSG_CACHE = 15;
    private static final int MSG_CHECK_PROGRESS = 17;
    private static final int MSG_FLASH = 6;
    private static final int MSG_FRAME_AVAILABLE = 10;
    private static final int MSG_PARSE_PARAM = 16;
    private static final int MSG_PAUSE = 13;
    private static final int MSG_PREPARE = 0;
    private static final int MSG_QUIT = 8;
    private static final int MSG_RELEASE = 4;
    private static final int MSG_RESET = 3;
    private static final int MSG_RESUME = 1;
    private static final int MSG_SEEK = 14;
    private static final int MSG_STOP = 12;
    private static final int MSG_THUMB = 5;
    private static final int SKIP_FRAME_COUNT = 1;
    private static final String TAG = "SightPlayViewImpl";
    private static Bitmap mFlash;
    private int curFrameIndex = 0;
    /* access modifiers changed from: private */
    public int mActualHeight = 0;
    /* access modifiers changed from: private */
    public int mActualWidth = 0;
    private boolean mAutoFitCenter = false;
    private String mBizId;
    private NinePatch mBubbleNinePatch;
    private Paint mBubblePaint;
    private BubbleEffectParams mBubbleParams = null;
    private Rect mBubbleRect;
    private SightVideoPlayView.OnBufferingUpdateListener mBufferingListener;
    private String mCachePath;
    private boolean mCenterCropped;
    private boolean mCheckProgress = false;
    private SightVideoPlayView.OnCompletionListener mCompletionListener;
    private long mCurPlayTime = 0;
    private String mCurrentPlayUrl;
    private boolean mDetachedFlag = false;
    private int mDisplayHeight;
    private WindowSurface10 mDisplaySurface;
    private int mDisplayWidth;
    private EglCore10 mEglCore;
    private boolean mEnableAudio = false;
    private boolean mEnableCache = false;
    private OnPlayErrorListener mErrorListener;
    private int mFastPlay = 0;
    private FullFrameRect mFullFrameBlit;
    private FullFrameRect mFullThumbBlit;
    private int mH;
    private PlayHandler mHandler;
    private final Object mHandlerLock = new Object();
    private int mImgTexId = -1;
    /* access modifiers changed from: private */
    public boolean mIsAvailable = false;
    private boolean mIsLoop = true;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private IjkMediaPlayer mMediaPlayer;
    private SightVideoPlayView.OnInfoListener mOnInfoListener;
    private Paint mPaint;
    private ParcelFileDescriptor mPfd = null;
    private String mPlayUrl;
    private SightVideoPlayView.OnPreparedListener mPrepareListener;
    private OnProgressUpdateListener mProgressListener;
    /* access modifiers changed from: private */
    public RoundFrameRect mRoundFrameBlit;
    private RoundFrameRect mRoundThumbBlit;
    private SightVideoPlayView.OnSeekCompleteListener mSeekListener;
    private int mSeekWhenResume = -1;
    private long mStartPosition = 0;
    private int mStreamHeight;
    private int mStreamWidth;
    private Surface mSurface;
    /* access modifiers changed from: private */
    public SurfaceTexture mSurfaceTexture;
    private int mTextureId;
    private HandlerThread mThread;
    private final float[] mTmpMatrix = new float[16];
    private int mVideoHeight;
    private String mVideoId;
    private float[] mVideoRotationMatrix = GlUtil.IDENTITY_MATRIX;
    private SurfaceTexture mVideoTexture;
    private int mVideoWidth;
    private int mW;
    private int mX;
    private int mY;
    private boolean notNeedPreload;

    private static class PlayHandler extends Handler {
        private Looper mLooper;
        private WeakReference<SightPlayViewImpl> mReference;

        PlayHandler(SightPlayViewImpl view, Looper looper) {
            super(looper);
            this.mLooper = looper;
            this.mReference = new WeakReference<>(view);
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void checkSurface() {
            /*
                r6 = this;
                java.lang.ref.WeakReference<tv.danmaku.ijk.media.widget.SightPlayViewImpl> r2 = r6.mReference
                java.lang.Object r1 = r2.get()
                tv.danmaku.ijk.media.widget.SightPlayViewImpl r1 = (tv.danmaku.ijk.media.widget.SightPlayViewImpl) r1
                if (r1 == 0) goto L_0x0038
                java.lang.Object r3 = r1.mLock
                monitor-enter(r3)
                boolean r2 = r1.mIsAvailable     // Catch:{ all -> 0x0045 }
                if (r2 != 0) goto L_0x0037
                java.lang.String r2 = "SightPlayViewImpl"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0045 }
                r4.<init>()     // Catch:{ all -> 0x0045 }
                java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ all -> 0x0045 }
                java.lang.String r5 = "checkSurface and surface not ready"
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0045 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0045 }
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0045 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r2, r4, r5)     // Catch:{ all -> 0x0045 }
                java.lang.Object r2 = r1.mLock     // Catch:{ InterruptedException -> 0x0039 }
                r2.wait()     // Catch:{ InterruptedException -> 0x0039 }
            L_0x0037:
                monitor-exit(r3)     // Catch:{ all -> 0x0045 }
            L_0x0038:
                return
            L_0x0039:
                r0 = move-exception
                java.lang.String r2 = "SightPlayViewImpl"
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
            throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.widget.SightPlayViewImpl.PlayHandler.checkSurface():void");
        }

        public void handleMessage(Message msg) {
            SightPlayViewImpl playTextureView = (SightPlayViewImpl) this.mReference.get();
            if (playTextureView == null) {
                Logger.D(SightPlayViewImpl.TAG, "outter class is null", new Object[0]);
                return;
            }
            if (msg.what != 10) {
                Logger.D(SightPlayViewImpl.TAG, playTextureView + " play handler handle msg: " + msg.what, new Object[0]);
            }
            switch (msg.what) {
                case 0:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mSurfaceTexture);
                    if (playTextureView.useBubbleEffect()) {
                        playTextureView.mRoundFrameBlit.setupData(playTextureView.mActualWidth, playTextureView.mActualHeight);
                    }
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
                    playTextureView.releaseGl();
                    try {
                        this.mLooper.quit();
                        return;
                    } catch (Exception e) {
                        Logger.E((String) SightPlayViewImpl.TAG, "quit ex" + e.getMessage(), (Throwable) e, new Object[0]);
                        return;
                    } finally {
                        playTextureView.resetPlayHandler();
                    }
                case 10:
                    try {
                        playTextureView.handleFrameAvailable();
                        return;
                    } catch (Exception e2) {
                        Logger.E((String) SightPlayViewImpl.TAG, "handleFrameAvailable err:" + e2.getMessage(), (Throwable) e2, new Object[0]);
                        return;
                    }
                case 12:
                    playTextureView.handleStop();
                    return;
                case 13:
                    playTextureView.handlePause();
                    return;
                case 14:
                    playTextureView.handleSeek(msg.arg1);
                    return;
                case 15:
                    playTextureView.handleCache();
                    return;
                case 16:
                    playTextureView.handleParseParams((VideoPlayParams) msg.obj);
                    return;
                case 17:
                    playTextureView.handleCheckProgress();
                    return;
                default:
                    return;
            }
        }
    }

    public void setOnErrorListener(OnPlayErrorListener listener) {
        this.mErrorListener = listener;
    }

    public void setOnSeekCompleteListener(SightVideoPlayView.OnSeekCompleteListener listener) {
        this.mSeekListener = listener;
    }

    public void setOnCompletionListener(SightVideoPlayView.OnCompletionListener listener) {
        this.mCompletionListener = listener;
    }

    public void setOnBufferingUpdateListener(SightVideoPlayView.OnBufferingUpdateListener listener) {
        this.mBufferingListener = listener;
    }

    public void setOnPreparedListener(SightVideoPlayView.OnPreparedListener listener) {
        this.mPrepareListener = listener;
    }

    public void setOnInfoListener(SightVideoPlayView.OnInfoListener listener) {
        this.mOnInfoListener = listener;
    }

    /* access modifiers changed from: protected */
    public void setOnProgressUpdateListener(OnProgressUpdateListener listener) {
        this.mProgressListener = listener;
    }

    public void setBubbleEffect(BubbleEffectParams params) {
        Logger.D(TAG, this + "setBubbleEffect " + params, new Object[0]);
        this.mBubbleParams = params;
        if (!(this.mBubbleParams == null || this.mBubbleParams.mBubbleShape == null || this.mBubbleParams.mBubbleShape.isRecycled())) {
            this.mBubbleNinePatch = new NinePatch(this.mBubbleParams.mBubbleShape, this.mBubbleParams.mBubbleShape.getNinePatchChunk(), null);
        }
        this.mBubblePaint = new Paint(3);
    }

    public void setVideoRotation(int degree) {
        this.mVideoRotationMatrix = GlUtil.getRotateMatrix(degree);
    }

    /* access modifiers changed from: protected */
    public void drawBubblePreload(Canvas canvas) {
        Logger.D(TAG, this + "drawBubblePreload", new Object[0]);
        if (!this.notNeedPreload) {
            if (getWidth() == 0 || getHeight() == 0) {
                Logger.D(TAG, "drawBubblePreload getWidth: " + getWidth() + ", getHeight: " + getHeight(), new Object[0]);
                return;
            }
            this.mBubbleRect = new Rect(0, 0, getWidth(), getHeight());
            Logger.D(TAG, "setBubbleEffect " + this.mBubbleRect, new Object[0]);
            Bitmap bitmap1 = getThumbnail();
            if (bitmap1 == null || bitmap1.isRecycled()) {
                Logger.D(TAG, "bitmap1: " + bitmap1, new Object[0]);
                return;
            }
            canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
            Logger.D(TAG, "drawBubblePreload, mBubbleRect:" + this.mBubbleRect.toString(), new Object[0]);
            if (this.mBubbleNinePatch == null) {
                Logger.D(TAG, "mBubbleNinePatch is null", new Object[0]);
                canvas.drawBitmap(bitmap1, null, this.mBubbleRect, this.mBubblePaint);
            } else {
                this.mBubbleNinePatch.draw(canvas, this.mBubbleRect, this.mBubblePaint);
                this.mBubblePaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
                canvas.drawBitmap(bitmap1, null, this.mBubbleRect, this.mBubblePaint);
                this.mBubblePaint.setXfermode(null);
            }
            canvas.restore();
        }
    }

    /* access modifiers changed from: private */
    public void hideBubblePreload() {
        Logger.D(TAG, this + "hideBubblePreload: ", new Object[0]);
        this.notNeedPreload = true;
        ViewParent parent = getParent();
        if (parent instanceof FrameLayout) {
            FrameLayout layout = (FrameLayout) parent;
            layout.setBackgroundDrawable(null);
            layout.invalidate();
        }
    }

    public SightPlayViewImpl(Context context) {
        super(context);
        if (VERSION.SDK_INT < 24) {
            init();
        }
    }

    private void init() {
        Logger.D(TAG, this + "\tsightplay view init.", new Object[0]);
        setSurfaceTextureListener(this);
        setOpaque(false);
        try {
            this.mMediaPlayer = new IjkMediaPlayer();
            this.mMediaPlayer.setOnCompletionListener(this);
            this.mMediaPlayer.setOnErrorListener(this);
            this.mMediaPlayer.setOnBufferingUpdateListener(this);
            this.mMediaPlayer.setOnPreparedListener(this);
            this.mMediaPlayer.setOnSeekCompleteListener(this);
            this.mMediaPlayer.setOnInfoListener(this);
            this.mMediaPlayer.setOnDownloadStatusListener(this);
            this.mMediaPlayer.setOption(4, (String) "fast-play", (long) this.mFastPlay);
        } catch (Throwable e) {
            Logger.E((String) TAG, e, this + "\tsightplay view init exp", new Object[0]);
        }
        getPlayHandler();
        this.mPaint = new Paint();
        this.mPaint.setFilterBitmap(true);
        generateMVPMatrix();
    }

    private Handler getPlayHandler() {
        PlayHandler playHandler;
        synchronized (this.mHandlerLock) {
            if (this.mHandler == null || this.mThread == null || !this.mThread.isAlive() || this.mHandler.getLooper() == null) {
                this.mThread = new HandlerThread("sight_play");
                this.mThread.start();
                this.mHandler = new PlayHandler(this, this.mThread.getLooper());
            }
            playHandler = this.mHandler;
        }
        return playHandler;
    }

    /* access modifiers changed from: private */
    public void resetPlayHandler() {
        synchronized (this.mHandlerLock) {
            if (this.mHandler != null) {
                this.mHandler = null;
            }
            if (this.mThread != null) {
                this.mThread = null;
            }
        }
    }

    private void generateMVPMatrix() {
        Matrix.setIdentityM(this.mTmpMatrix, 0);
        this.mTmpMatrix[5] = -this.mTmpMatrix[5];
        this.mTmpMatrix[13] = 1.0f - this.mTmpMatrix[13];
    }

    public void updateViewSize(int w, int h) {
        this.mActualWidth = w;
        this.mActualHeight = h;
    }

    /* access modifiers changed from: private */
    public void handleDrawBitmap(Bitmap bitmap) {
        Logger.D(TAG, this + "handleDrawBitmap begin", new Object[0]);
        long ts = System.currentTimeMillis();
        if (!(bitmap == null || this.mFullThumbBlit == null)) {
            this.mDisplaySurface.makeCurrent();
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES20.glClear(16384);
            GLES20.glViewport(0, 0, this.mDisplaySurface.getWidth(), this.mDisplaySurface.getHeight());
            this.mImgTexId = this.mFullThumbBlit.createImageTexture(bitmap, this.mEglCore.getGl10());
            if (useBubbleEffect()) {
                Logger.D(TAG, this + "mRoundThumbBlit setupData, w:" + getWidth() + ",h:" + getHeight(), new Object[0]);
                this.mRoundThumbBlit.setupData(this.mActualWidth, this.mActualHeight);
                this.mRoundThumbBlit.drawFrame(this.mImgTexId, this.mTmpMatrix, this.mVideoRotationMatrix);
            } else if (!this.mCenterCropped) {
                this.mFullThumbBlit.drawFrame(this.mImgTexId, this.mTmpMatrix, this.mVideoRotationMatrix);
            } else {
                this.mFullThumbBlit.drawCroppedFrame(this.mImgTexId, this.mTmpMatrix, this.mVideoWidth, this.mVideoHeight, this.mDisplayWidth, this.mDisplayHeight);
            }
            this.mDisplaySurface.swapBuffers();
            this.mFullThumbBlit.freeImageTexture(this.mImgTexId);
        }
        Logger.D(TAG, this + "\tdraw bitmap took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
        Logger.D(TAG, this + "handleDrawBitmap end", new Object[0]);
        if (!this.notNeedPreload) {
            post(new Runnable() {
                public void run() {
                    SightPlayViewImpl.this.hideBubblePreload();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public Bitmap getThumbnail() {
        long ts = System.currentTimeMillis();
        FileCacheModel model = VideoFileManager.getInstance().getVideoThumbCacheInfo(this.mVideoId);
        String jpath = model == null ? "" : model.path;
        if (TextUtils.isEmpty(jpath)) {
            File file = PathUtils.extractFile(this.mVideoId);
            if (file != null) {
                jpath = file.getAbsolutePath();
            } else if (!TextUtils.isEmpty(this.mVideoId)) {
                jpath = this.mVideoId.substring(this.mVideoId.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1);
            }
        }
        String cacheKey = CacheUtils.makeVideoThumbCacheKey(jpath, 640, 640);
        Bitmap bitmap = CacheContext.get().getMemCache(model != null ? model.businessId : null).get(cacheKey);
        if (!ImageUtils.checkBitmap(bitmap)) {
            String path = CacheContext.get().getDiskCache().getPath(cacheKey);
            if (!TextUtils.isEmpty(path)) {
                bitmap = ImageUtils.decodeBitmapByFalcon(new File(path));
            }
        }
        Logger.D(TAG, "operation getThumbnail took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
        return bitmap;
    }

    /* access modifiers changed from: private */
    public void drawEndFlash() {
        handleDrawBitmap(mFlash);
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            Logger.E((String) TAG, (String) "", (Throwable) e, new Object[0]);
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int w, int h) {
        synchronized (this.mLock) {
            this.mIsAvailable = true;
            Logger.D(TAG, this + "###onSurfaceTextureAvailable, w = " + w + ", h = " + h, new Object[0]);
            this.mSurfaceTexture = surfaceTexture;
            Logger.D(TAG, this + "isAvailable and notify######" + this.mSurfaceTexture, new Object[0]);
            this.mLock.notifyAll();
            this.mActualWidth = getWidth();
            this.mActualHeight = getHeight();
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
            if (LocalIdTool.isLocalIdRes(id)) {
                id = LocalIdTool.get().decodeToPath(id);
            }
            this.mVideoId = id;
            this.mPlayUrl = VideoFileManager.getInstance().getVideoPathById(id);
            if (SandboxWrapper.isContentUriPath(this.mVideoId)) {
                this.mPlayUrl = this.mVideoId;
            } else {
                this.mPlayUrl = VideoFileManager.getInstance().getVideoPathById(id);
            }
            Logger.D(TAG, this + "setVideoId: " + id, new Object[0]);
        }
    }

    public void setVideoParams(VideoPlayParams params) {
        Logger.D(TAG, "setVideoParams", new Object[0]);
        Message msg = Message.obtain();
        msg.what = 16;
        msg.obj = params;
        getPlayHandler().removeMessages(16);
        getPlayHandler().sendMessage(msg);
    }

    private void addCache() {
        getPlayHandler().removeMessages(15);
        getPlayHandler().sendEmptyMessage(15);
    }

    public String getVideoId() {
        return this.mVideoId;
    }

    public boolean isPlaying() {
        return this.mMediaPlayer != null && this.mMediaPlayer.isPlaying();
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int w, int h) {
        Logger.D(TAG, this + "\t###onSurfaceTextureSizeChanged, w = " + w + ", h = " + h, new Object[0]);
        generateViewport();
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mIsAvailable = false;
        Logger.D(TAG, this + "###onSurfaceTextureDestroyed\t" + surfaceTexture, new Object[0]);
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (this.mSurfaceTexture != surfaceTexture) {
            Logger.D(TAG, "###surfacetexture error###", new Object[0]);
        }
    }

    public void onPrepared(IMediaPlayer mp) {
        Logger.D(TAG, this + " prepare done Url: " + this.mPlayUrl + "\tcurrent time: " + this.mCurPlayTime, new Object[0]);
        if (this.mMediaPlayer != null) {
            if (this.mPrepareListener != null) {
                this.mPrepareListener.onPrepared(null);
            }
            this.mStreamWidth = this.mMediaPlayer.getVideoWidth();
            this.mStreamHeight = this.mMediaPlayer.getVideoHeight();
            generateViewport();
            UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_PLAY_VD, "0");
            startCheckProgress();
        }
    }

    public void onCompletion(IMediaPlayer mp) {
        Logger.D(TAG, this + " onCompletion and loop:" + this.mIsLoop, new Object[0]);
        if (this.mCompletionListener != null) {
            this.mCompletionListener.onCompletion(null);
        }
        if (!this.mIsLoop) {
            stopCheckProgress();
        }
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
                f.delete();
            }
        }
    }

    public boolean onError(IMediaPlayer mp, int what, int extra) {
        Logger.W(TAG, this + " onError:" + what + "," + extra + " file: " + this.mPlayUrl + ", id: " + this.mVideoId, new Object[0]);
        if (this.mErrorListener != null) {
            this.mErrorListener.onError(what, this.mVideoId);
        }
        FileCacheModel videoInfo = VideoFileManager.getInstance().getVideoInfo(this.mVideoId);
        if (videoInfo == null || (videoInfo.tag & 4096) == 0) {
            UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_PLAY_VD, String.valueOf(what));
        }
        return false;
    }

    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
        Logger.D(TAG, this + " onBufferingUpdate, percent: " + percent, new Object[0]);
        if (this.mBufferingListener != null) {
            this.mBufferingListener.onBufferingUpdate(percent, null);
        }
    }

    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        Logger.D(TAG, this + " onInfo, what: " + what, new Object[0]);
        if (this.mOnInfoListener != null && this.mEnableCache) {
            Bundle bundle = new Bundle();
            bundle.putInt("extra", extra);
            this.mOnInfoListener.onInfo(what, bundle);
        }
        return false;
    }

    public void onSeekComplete(IMediaPlayer mp) {
        Logger.D(TAG, this + " onSeekComplete", new Object[0]);
        if (this.mSeekListener != null) {
            this.mSeekListener.onSeekComplete(null);
        }
        startCheckProgress();
    }

    public void start() {
        if (this.mMediaPlayer == null || !this.mMediaPlayer.isPlaying()) {
            drawThumbnail();
        }
        getPlayHandler().removeMessages(0);
        getPlayHandler().sendEmptyMessage(0);
    }

    public void start(String id) {
        start(id, false);
    }

    public void start(String id, boolean enableAudio) {
        Logger.D(TAG, this + "\tstart###", new Object[0]);
        this.mEnableAudio = enableAudio;
        if (!TextUtils.isEmpty(id)) {
            setVideoId(id);
        }
        if (this.mMediaPlayer == null || !this.mMediaPlayer.isPlaying()) {
            drawThumbnail();
        }
        getPlayHandler().removeMessages(0);
        getPlayHandler().sendEmptyMessage(0);
    }

    /* access modifiers changed from: protected */
    public void start(String path, long startPosition) {
        this.mStartPosition = startPosition;
        start(path, true);
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.mStartPosition = 0;
        getPlayHandler().removeMessages(3);
        getPlayHandler().sendEmptyMessage(3);
    }

    public void setLooping(boolean loop) {
        this.mIsLoop = loop;
    }

    public void stop() {
        Logger.D(TAG, this + "\tstop###", new Object[0]);
        getPlayHandler().removeMessages(12);
        getPlayHandler().sendEmptyMessage(12);
        this.mCurPlayTime = 0;
    }

    public void setCenterCropped(int videoWidth, int videoHeight, int displayWidth, int displayHeight) {
        if (!this.mAutoFitCenter) {
            this.mCenterCropped = true;
            this.mVideoHeight = videoHeight;
            this.mVideoWidth = videoWidth;
            this.mDisplayHeight = displayHeight;
            this.mDisplayWidth = displayWidth;
        }
    }

    public void drawThumbnail() {
        getPlayHandler().sendEmptyMessage(5);
        Logger.D(TAG, this + "\tdrawThumbnail###", new Object[0]);
    }

    public void drawBitmap(Bitmap bitmap) {
        Message msg = Message.obtain();
        msg.obj = bitmap;
        msg.what = 7;
        getPlayHandler().sendMessage(msg);
        Logger.D(TAG, this + "\tdrawBitmap###", new Object[0]);
    }

    public void resume() {
        getPlayHandler().removeMessages(1);
        getPlayHandler().sendEmptyMessage(1);
    }

    public void pause() {
        getPlayHandler().removeMessages(13);
        getPlayHandler().sendEmptyMessage(13);
    }

    public void seekTo(long msec) {
        getPlayHandler().removeMessages(14);
        getPlayHandler().sendMessage(getPlayHandler().obtainMessage(14, (int) msec, 0));
    }

    public long getDuration() {
        int dur = getVideoInfo().duration;
        if (dur <= 0) {
            Logger.D(TAG, "getDuration dur=" + dur, new Object[0]);
        }
        return (long) dur;
    }

    public long getCurrentPosition() {
        Logger.D(TAG, this + "\tgetCurrentPosition###", new Object[0]);
        if (this.mMediaPlayer != null) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        Logger.D(TAG, this + "\tgetCurrentPosition = -1", new Object[0]);
        return -1;
    }

    public void setAutoFitCenter(boolean fit) {
        this.mAutoFitCenter = fit;
        this.mCenterCropped = !this.mAutoFitCenter;
    }

    public boolean isAutoFitCenter() {
        return this.mAutoFitCenter;
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

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.D(TAG, this + "\tonSizeChanged, w:" + w + ", h:" + h + ",oldw:" + oldw + ",oldh:" + oldh, new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        this.mDetachedFlag = false;
        super.onAttachedToWindow();
        Logger.D(TAG, this + "\tonAttachedToWindow", new Object[0]);
        if (VERSION.SDK_INT >= 24) {
            init();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        synchronized (this.mLock) {
            this.mLock.notifyAll();
        }
        this.mDetachedFlag = true;
        Logger.D(TAG, this + "onDetachedFromWindow", new Object[0]);
        Logger.D(TAG, "sendEmptyMessage result:" + getPlayHandler().sendEmptyMessage(4), new Object[0]);
        getPlayHandler().sendEmptyMessage(8);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: private */
    public void handleRelease() {
        Logger.D(TAG, this + "mediaplayer release begin.", new Object[0]);
        stopCheckProgress();
        this.mCurrentPlayUrl = null;
        if (this.mMediaPlayer != null) {
            try {
                this.mMediaPlayer.reset();
                this.mMediaPlayer.release();
            } catch (Throwable e) {
                Logger.E((String) TAG, (String) "handleRelease error", e, new Object[0]);
            } finally {
                this.mMediaPlayer = null;
            }
        }
        closeParcelFD();
        clearIncompleteCache();
        Logger.D(TAG, this + "mediaplayer release done.", new Object[0]);
    }

    /* access modifiers changed from: private */
    public void handlePrepare() {
        try {
            if (this.mMediaPlayer != null) {
                if (isSamePlaying()) {
                    Logger.D(TAG, this + " is playing, just skip...", new Object[0]);
                    return;
                }
                this.mMediaPlayer.reset();
            } else if (VERSION.SDK_INT >= 24 && ConfigManager.getInstance().getCommonConfigItem().videoConf.checkVideoPlayHandlePrepare()) {
                Logger.D(TAG, this + "\thandlePrepare  mMediaPlayer is null", new Object[0]);
                try {
                    this.mMediaPlayer = new IjkMediaPlayer();
                    this.mMediaPlayer.setDebugLog();
                    this.mMediaPlayer.setOnCompletionListener(this);
                    this.mMediaPlayer.setOnErrorListener(this);
                    this.mMediaPlayer.setOnBufferingUpdateListener(this);
                    this.mMediaPlayer.setOnPreparedListener(this);
                    this.mMediaPlayer.setOnSeekCompleteListener(this);
                    this.mMediaPlayer.setOnInfoListener(this);
                    this.mMediaPlayer.setOnDownloadStatusListener(this);
                    this.mMediaPlayer.setOption(4, (String) "fast-play", (long) this.mFastPlay);
                } catch (Throwable e) {
                    Logger.E((String) TAG, e, this + "\tsightplay view init exp", new Object[0]);
                }
                getPlayHandler();
            }
            closeParcelFD();
            Logger.D(TAG, this + " handlePrepare mplayurl:" + this.mPlayUrl, new Object[0]);
            this.curFrameIndex = 0;
            Logger.D(TAG, this + " mEnableAudio:" + this.mEnableAudio, new Object[0]);
            if (!this.mEnableAudio) {
                this.mMediaPlayer.setOption(4, (String) a.i, 1);
            }
            if (this.mEnableCache && FileUtils.isSDcardAvailableSpace(ConfigManager.getInstance().diskConf().urlVideoNeedSpace)) {
                this.mCachePath = VideoFileManager.getInstance().getDiskCache().genPathByKey(this.mPlayUrl);
                this.mMediaPlayer.setOption(4, (String) "data-copy", this.mCachePath);
                Logger.D(TAG, "###enable cache and path is:" + this.mCachePath, new Object[0]);
            }
            setDataSource();
            this.mMediaPlayer.setLooping(this.mIsLoop);
            this.mMediaPlayer.setSurface(this.mSurface);
            this.mMediaPlayer.setOption(4, (String) "seek-at-start", this.mStartPosition);
            if (this.mStartPosition > 0) {
                this.mMediaPlayer.setOption(4, (String) "fast-play", 1);
            } else {
                this.mMediaPlayer.setOption(4, (String) "fast-play", (long) this.mFastPlay);
            }
            this.mMediaPlayer.prepareAsync();
            Logger.D(TAG, this + "handlePrepare pre: " + this.mCurrentPlayUrl + ", cur: " + this.mPlayUrl, new Object[0]);
            this.mCurrentPlayUrl = this.mPlayUrl;
            this.mSeekWhenResume = -1;
        } catch (Exception e2) {
            Logger.E((String) TAG, this + " prepare exception:", (Throwable) e2, new Object[0]);
            closeParcelFD();
        } finally {
            this.mSeekWhenResume = -1;
        }
    }

    /* access modifiers changed from: private */
    public void handleResume() {
        Logger.D(TAG, "handleResume", new Object[0]);
        if (!isPlaying() && this.mMediaPlayer != null) {
            this.mMediaPlayer.start();
            if (this.mSeekWhenResume >= 0) {
                this.mMediaPlayer.seekTo((long) this.mSeekWhenResume);
                this.mSeekWhenResume = -1;
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleSeek(int msec) {
        Logger.D(TAG, "handleSeek: " + msec, new Object[0]);
        if (isPlaying()) {
            long ts = System.currentTimeMillis();
            this.mMediaPlayer.seekTo((long) msec);
            Logger.D(TAG, "seekTo took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
            this.mSeekWhenResume = -1;
            return;
        }
        this.mSeekWhenResume = msec;
    }

    /* access modifiers changed from: private */
    public void handlePause() {
        Logger.D(TAG, "handlePause", new Object[0]);
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.pause();
        }
    }

    /* access modifiers changed from: private */
    public void handleReset() {
        Logger.D(TAG, this + " handleReset", new Object[0]);
        this.curFrameIndex = 0;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
        }
        closeParcelFD();
        this.mCurrentPlayUrl = null;
    }

    /* access modifiers changed from: private */
    public void handleStop() {
        Logger.D(TAG, this + " handleStop", new Object[0]);
        this.curFrameIndex = 0;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
        }
        this.mCurrentPlayUrl = null;
        clearIncompleteCache();
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
            }
        } catch (Exception e2) {
            Logger.E((String) TAG, (String) "addCache exp:", (Throwable) e2, new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void handleParseParams(VideoPlayParams params) {
        String id = params.mVideoId;
        this.mBizId = params.mBizId;
        this.mEnableAudio = params.mEnableAudio;
        Logger.D(TAG, this + "begin handleParseParams: " + id, new Object[0]);
        String path = VideoFileManager.getInstance().getVideoPathById(id);
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
            this.mEnableCache = false;
        }
        Logger.D(TAG, this + "end handleParseParams: " + this.mPlayUrl, new Object[0]);
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Logger.D(TAG, "onWindowFocusChanged hasWindowFocus: " + hasWindowFocus, new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void destroyHardwareResources() {
        if (this.mDetachedFlag && VERSION.SDK_INT < 27) {
            superDestroyHardwareResources();
        }
    }

    /* access modifiers changed from: protected */
    public void superDestroyHardwareResources() {
        Logger.D(TAG, "call superDestroyHardwareResources", new Object[0]);
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

    /* access modifiers changed from: private */
    public void handleSetSurfaceTexture(SurfaceTexture surfaceTexture) {
        if (this.mEglCore == null) {
            setExceptionHandler();
            Logger.D(TAG, this + "handleSetSurfaceTexture begin", new Object[0]);
            this.mEglCore = new EglCore10();
            this.mDisplaySurface = new WindowSurface10(this.mEglCore, surfaceTexture);
            this.mDisplaySurface.makeCurrent();
            if (!useBubbleEffect()) {
                this.mFullFrameBlit = new FullFrameRect(new Texture2dProgram(ProgramType.TEXTURE_EXT));
                this.mTextureId = this.mFullFrameBlit.createTextureObject();
            } else {
                this.mRoundFrameBlit = new RoundFrameRect(false);
                this.mRoundFrameBlit.setBubbleEffect(this.mBubbleParams.mBubbleType);
                int r = this.mBubbleParams.mRoundRadius;
                this.mRoundFrameBlit.setCornerRadius((float) r, (float) r, (float) r, (float) r);
                this.mRoundFrameBlit.setTriangleOffset(this.mBubbleParams.mTriangleOffset);
                this.mRoundFrameBlit.setupData(getWidth(), getHeight());
                this.mTextureId = this.mRoundFrameBlit.createTextureObject();
                this.mRoundThumbBlit = new RoundFrameRect(true);
                this.mRoundThumbBlit.setBubbleEffect(this.mBubbleParams.mBubbleType);
                this.mRoundThumbBlit.setCornerRadius((float) r, (float) r, (float) r, (float) r);
                this.mRoundThumbBlit.setTriangleOffset(this.mBubbleParams.mTriangleOffset);
                this.mRoundThumbBlit.setupData(getWidth(), getHeight());
            }
            this.mFullThumbBlit = new FullFrameRect(new Texture2dProgram(ProgramType.TEXTURE_2D));
            this.mVideoTexture = new SurfaceTexture(this.mTextureId);
            this.mSurface = new Surface(this.mVideoTexture);
            this.mVideoTexture.setOnFrameAvailableListener(this);
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
    }

    /* access modifiers changed from: private */
    public void handleFrameAvailable() {
        if (this.mEglCore == null) {
            Logger.D(TAG, "Skipping drawFrame after shutdown", new Object[0]);
            return;
        }
        this.mVideoTexture.updateTexImage();
        if (this.mIsAvailable && !this.mDetachedFlag && hasWindowFocus() && isShown() && getGlobalVisibleRect(new Rect())) {
            this.curFrameIndex++;
            if (this.curFrameIndex <= 1) {
                handleDrawBitmap(getThumbnail());
                return;
            }
            this.mVideoTexture.getTransformMatrix(this.mTmpMatrix);
            this.mDisplaySurface.makeCurrent();
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES20.glClear(16384);
            GLES20.glViewport(this.mX, this.mY, this.mW, this.mH);
            if (useBubbleEffect()) {
                this.mRoundFrameBlit.drawFrame(this.mTextureId, this.mTmpMatrix, this.mVideoRotationMatrix);
            } else if (!this.mCenterCropped) {
                this.mFullFrameBlit.drawFrame(this.mTextureId, this.mTmpMatrix, this.mVideoRotationMatrix);
            } else {
                this.mFullFrameBlit.drawCroppedFrame(this.mTextureId, this.mTmpMatrix, this.mVideoWidth, this.mVideoHeight, this.mDisplayWidth, this.mDisplayHeight);
            }
            this.mDisplaySurface.swapBuffers();
        }
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        getPlayHandler().sendEmptyMessage(10);
    }

    /* access modifiers changed from: private */
    public boolean useBubbleEffect() {
        return (this.mBubbleParams == null || this.mBubbleParams.mBubbleType == 0) ? false : true;
    }

    private void setExceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                Logger.D(SightPlayViewImpl.TAG, "uncaughtException###, thread name:" + thread.getName() + ", thread id:" + thread.getId() + ",ex:" + ex.getMessage(), new Object[0]);
                StackTraceElement[] elements = ex.getStackTrace();
                StringBuilder sb = new StringBuilder();
                for (StackTraceElement stackTraceElement : elements) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
                Logger.D(SightPlayViewImpl.TAG, "exception stack:\n" + sb.toString(), new Object[0]);
                SightPlayViewImpl.this.releaseGl();
            }
        });
    }

    private boolean isSamePlaying() {
        Logger.D(TAG, "isSamePlaying mPlayUrl: " + this.mPlayUrl + ", current: " + this.mCurrentPlayUrl, new Object[0]);
        if (this.mMediaPlayer == null || !this.mMediaPlayer.isPlaying() || !this.mPlayUrl.equals(this.mCurrentPlayUrl)) {
            return false;
        }
        return true;
    }

    private void clearIncompleteCache() {
        if (this.mEnableCache && TextUtils.isEmpty(VideoFileManager.getInstance().getVideoPathById(this.mPlayUrl))) {
            Logger.D(TAG, "player cache not success", new Object[0]);
            File f = new File(this.mCachePath);
            if (f.exists()) {
                Logger.D(TAG, "Incomplete video cache exists, delete it", new Object[0]);
                f.delete();
            }
        }
    }

    private synchronized void generateViewport() {
        if (!this.mAutoFitCenter) {
            this.mY = 0;
            this.mX = 0;
            this.mW = getWidth();
            this.mH = getHeight();
            Logger.D(TAG, this + "\tmW:" + this.mW + "mH:" + this.mH, new Object[0]);
        } else if (!(this.mStreamWidth == 0 || this.mStreamHeight == 0)) {
            if (this.mStreamHeight * getWidth() <= this.mStreamWidth * getHeight()) {
                this.mW = getWidth();
                this.mH = (this.mW * this.mStreamHeight) / this.mStreamWidth;
                this.mX = 0;
                this.mY = (getHeight() - this.mH) / 2;
            } else {
                this.mH = getHeight();
                this.mW = (this.mH * this.mStreamWidth) / this.mStreamHeight;
                this.mY = 0;
                this.mX = (getWidth() - this.mW) / 2;
            }
        }
    }

    private void startCheckProgress() {
        Logger.D(TAG, this + "startCheckProgress...", new Object[0]);
        if (this.mProgressListener != null) {
            this.mCheckProgress = true;
            getPlayHandler().removeMessages(17);
            getPlayHandler().obtainMessage(17).sendToTarget();
        }
    }

    private void stopCheckProgress() {
        Logger.D(TAG, this + "stopCheckProgress...", new Object[0]);
        getPlayHandler().removeMessages(17);
        this.mCheckProgress = false;
    }

    /* access modifiers changed from: private */
    public void handleCheckProgress() {
        if (isPlaying() && this.mProgressListener != null) {
            this.mProgressListener.onProgressUpdate(this.mMediaPlayer.getCurrentPosition());
        }
        if (this.mCheckProgress) {
            getPlayHandler().sendEmptyMessageDelayed(17, 100);
        }
    }

    /* access modifiers changed from: protected */
    public void setFastPlay(int fastPlay) {
        this.mFastPlay = fastPlay;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setOption(4, (String) "fast-play", (long) this.mFastPlay);
        }
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
