package tv.danmaku.ijk.media.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimedia.widget.VideoPlayView.OnPlayCompletionListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.VideoPlayView.OnPlayErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.VideoPlayView.VideoSurfaceView;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CacheUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoFileManager;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.multimedia.gles.EglCore10;
import com.alipay.multimedia.gles.FullFrameRect;
import com.alipay.multimedia.gles.Texture2dProgram;
import com.alipay.multimedia.gles.Texture2dProgram.ProgramType;
import com.alipay.multimedia.gles.WindowSurface10;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.video.VideoInfo;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(14)
public class VideoSurfaceViewImpl extends VideoSurfaceView implements OnFrameAvailableListener, OnCompletionListener, OnErrorListener, OnPreparedListener, Callback {
    private static final int MSG_BITMAP = 7;
    private static final int MSG_FLASH = 6;
    private static final int MSG_FRAME_AVAILABLE = 9;
    private static final int MSG_PAUSE = 11;
    private static final int MSG_PREPARE = 0;
    private static final int MSG_QUIT = 8;
    private static final int MSG_RELEASE = 4;
    private static final int MSG_RESET = 3;
    private static final int MSG_RESUME = 1;
    private static final int MSG_SEEK = 10;
    private static final int MSG_THUMB = 5;
    private static final int SKIP_FRAME_COUNT = 1;
    private static final String TAG = "VideoSurfaceViewImpl";
    private static final AtomicBoolean adjustLock = new AtomicBoolean();
    private static Bitmap mFlash;
    private boolean VERBOSE = false;
    private AtomicBoolean mAudioPaused = new AtomicBoolean(false);
    private boolean mAutoFitCenter = false;
    private OnPlayCompletionListener mCompletionListener;
    private long mCurPlayTime = 0;
    private WindowSurface10 mDisplaySurface;
    private EglCore10 mEglCore;
    private OnPlayErrorListener mErrorListener;
    private int mFrameIndex = 0;
    private FullFrameRect mFullFrameBlit;
    private FullFrameRect mFullThumbBlit;
    private int mH;
    /* access modifiers changed from: private */
    public PlayHandler mHandler;
    /* access modifiers changed from: private */
    public final Object mHandlerLock = new Object();
    /* access modifiers changed from: private */
    public SurfaceHolder mHolder = null;
    private int mImgTexId;
    private boolean mIsLoop = false;
    private boolean mKeepScreenOn = false;
    private MediaPlayer mMediaPlayer;
    private String mObjectId = toString();
    private Paint mPaint;
    private String mPlayUrl;
    private boolean mPreparing = false;
    /* access modifiers changed from: private */
    public final Object mQuitLock = new Object();
    private AtomicBoolean mReported = new AtomicBoolean(false);
    private int mSeekWhenResume = 0;
    private boolean mStarted = false;
    private Surface mSurface;
    /* access modifiers changed from: private */
    public final Object mSurfaceLock = new Object();
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
        private WeakReference<VideoSurfaceViewImpl> mReference;

        PlayHandler(VideoSurfaceViewImpl view, Looper looper) {
            super(looper);
            this.mLooper = looper;
            this.mReference = new WeakReference<>(view);
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void checkSurface() {
            /*
                r6 = this;
                java.lang.ref.WeakReference<tv.danmaku.ijk.media.widget.VideoSurfaceViewImpl> r2 = r6.mReference
                java.lang.Object r1 = r2.get()
                tv.danmaku.ijk.media.widget.VideoSurfaceViewImpl r1 = (tv.danmaku.ijk.media.widget.VideoSurfaceViewImpl) r1
                if (r1 == 0) goto L_0x0038
                java.lang.Object r3 = r1.mSurfaceLock
                monitor-enter(r3)
                android.view.SurfaceHolder r2 = r1.mHolder     // Catch:{ all -> 0x0045 }
                if (r2 != 0) goto L_0x0037
                java.lang.String r2 = "VideoSurfaceViewImpl"
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
                java.lang.String r2 = "VideoSurfaceViewImpl"
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
            throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.widget.VideoSurfaceViewImpl.PlayHandler.checkSurface():void");
        }

        public void handleMessage(Message msg) {
            VideoSurfaceViewImpl playTextureView = (VideoSurfaceViewImpl) this.mReference.get();
            if (playTextureView == null) {
                Logger.D(VideoSurfaceViewImpl.TAG, "outter class is null", new Object[0]);
                return;
            }
            if (msg.what != 9) {
                Logger.D(VideoSurfaceViewImpl.TAG, playTextureView + " handle msg: " + msg.what, new Object[0]);
            }
            switch (msg.what) {
                case 0:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mHolder);
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
                    playTextureView.handleSetSurfaceTexture(playTextureView.mHolder);
                    playTextureView.handleDrawBitmap(playTextureView.getThumbnail());
                    return;
                case 6:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mHolder);
                    playTextureView.drawEndFlash();
                    return;
                case 7:
                    checkSurface();
                    playTextureView.handleSetSurfaceTexture(playTextureView.mHolder);
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
                        Logger.E((String) VideoSurfaceViewImpl.TAG, (String) "", (Throwable) e, new Object[0]);
                    }
                    playTextureView.releaseGl();
                    synchronized (playTextureView.mQuitLock) {
                        playTextureView.mQuitLock.notifyAll();
                        Logger.D(VideoSurfaceViewImpl.TAG, "mQuitLock notifyAll", new Object[0]);
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
                default:
                    return;
            }
        }
    }

    class VideoRect {
        int height;
        int width;
        int x;
        int y;

        VideoRect() {
        }

        public String toString() {
            return "VideoRect{x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + '}';
        }
    }

    public VideoSurfaceViewImpl(Context context) {
        super(context);
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).clear();
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_VIEW_CREATE, System.nanoTime());
        init();
    }

    private void init() {
        Logger.D(TAG, "full screen video surfaceview init", new Object[0]);
        getHolder().addCallback(this);
        this.mPaint = new Paint();
        this.mPaint.setFilterBitmap(true);
        generateMVPMatrix();
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
    public void handleSetSurfaceTexture(SurfaceHolder surface) {
        Logger.D(TAG, this + "\thandleSetSurfaceTexture", new Object[0]);
        setExceptionHandler();
        try {
            if (this.mEglCore == null) {
                Logger.D(TAG, "handleSetSurfaceTexture mEglCore", new Object[0]);
                this.mEglCore = new EglCore10();
                this.mDisplaySurface = new WindowSurface10(this.mEglCore, surface);
                this.mDisplaySurface.makeCurrent();
                this.mFullFrameBlit = new FullFrameRect(new Texture2dProgram(ProgramType.TEXTURE_EXT));
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
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_GL_PREPARED, System.nanoTime());
    }

    /* access modifiers changed from: private */
    public void handleFrameAvailable() {
        if (this.mEglCore == null) {
            Logger.D(TAG, "Skipping drawFrame after shutdown", new Object[0]);
            return;
        }
        this.mVideoTexture.updateTexImage();
        this.mFrameIndex++;
        if (this.mReported.compareAndSet(false, true)) {
            VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_FIRST_FRAME_SHOW, System.nanoTime());
            VideoBenchmark.reportPlaying(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString());
        }
        if (this.mFrameIndex <= 1) {
            handleDrawBitmap(getThumbnail());
            return;
        }
        if (this.mFrameIndex % 30 == 0) {
            Logger.D(TAG, "handleFrameAvailable", new Object[0]);
        }
        this.mVideoTexture.getTransformMatrix(this.mTmpMatrix);
        this.mDisplaySurface.makeCurrent();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16384);
        GLES20.glViewport(this.mX, this.mY, this.mW, this.mH);
        this.mFullFrameBlit.drawFrame(this.mTextureId, this.mTmpMatrix);
        this.mDisplaySurface.swapBuffers();
    }

    /* access modifiers changed from: private */
    public void handleDrawBitmap(Bitmap bitmap) {
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_THUMB_RENDER_BEGIN, System.nanoTime());
        long ts = System.currentTimeMillis();
        generateMVPMatrix();
        if (!(bitmap == null || this.mFullThumbBlit == null)) {
            this.mVideoWidth = bitmap.getWidth();
            this.mVideoHeight = bitmap.getHeight();
            generateViewport();
            try {
                this.mDisplaySurface.makeCurrent();
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES20.glClear(16384);
                GLES20.glViewport(this.mX, this.mY, this.mW, this.mH);
                this.mImgTexId = this.mFullThumbBlit.createImageTexture(bitmap, this.mEglCore.getGl10());
                this.mFullThumbBlit.drawFrame(this.mImgTexId, this.mTmpMatrix);
                this.mDisplaySurface.swapBuffers();
                this.mFullThumbBlit.freeImageTexture(this.mImgTexId);
            } catch (Exception e) {
                Logger.E(TAG, e.getMessage(), new Object[0]);
            }
        }
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_THUMB_RENDER_END, System.nanoTime());
        Logger.D(TAG, this + "\tdraw bitmap took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
    }

    /* access modifiers changed from: private */
    public Bitmap getThumbnail() {
        String key;
        Logger.D(TAG, this + "\tgetThumbnail()", new Object[0]);
        long ts = System.currentTimeMillis();
        long ts_n = System.nanoTime();
        FileCacheModel model = VideoFileManager.getInstance().getVideoThumbCacheInfo(this.mVideoId);
        VideoBenchmark.getBundle(VideoBenchmark.KEY_CACHE).putLong("cache_loading_time", System.nanoTime() - ts_n);
        String jpath = model == null ? "" : model.path;
        String businessId = model == null ? null : model.businessId;
        VideoBenchmark.getBundle(VideoBenchmark.KEY_CACHE).putInt(VideoBenchmark.KEY_CACHE_DETAIL, TextUtils.isEmpty(jpath) ? 0 : 2);
        VideoBenchmark.reportCacheLoading(VideoBenchmark.KEY_CACHE);
        if (TextUtils.isEmpty(jpath)) {
            Logger.D(TAG, "jpg path not in db", new Object[0]);
            File file = PathUtils.extractFile(this.mVideoId);
            if (file != null) {
                key = file.getAbsolutePath();
            } else {
                key = this.mVideoId.substring(this.mVideoId.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1);
            }
            String key2 = CacheUtils.makeVideoThumbCacheKey(key, 640, 640);
            Logger.D(TAG, this + "key: " + key2, new Object[0]);
            Bitmap bitmap = CacheContext.get().getMemCache(businessId).get(key2);
            if (bitmap != null) {
                return bitmap;
            }
            Logger.D(TAG, this + "\tmem cache missed###########", new Object[0]);
            String path = CacheContext.get().getDiskCache().getPath(key2);
            if (TextUtils.isEmpty(path)) {
                return bitmap;
            }
            long t = System.nanoTime();
            Bitmap bitmap2 = ImageUtils.decodeBitmapByFalcon(new File(path));
            VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_THUMB_DECODE_TIME, System.nanoTime() - t);
            return bitmap2;
        }
        String cacheKey = CacheUtils.makeVideoThumbCacheKey(jpath, 640, 640);
        long time = System.nanoTime();
        Bitmap bitmap3 = CacheContext.get().getMemCache(businessId).get(cacheKey);
        if (bitmap3 != null) {
            VideoBenchmark.getBundle(VideoBenchmark.KEY_CACHE).putLong("cache_loading_time", System.nanoTime() - time);
            VideoBenchmark.getBundle(VideoBenchmark.KEY_CACHE).putInt(VideoBenchmark.KEY_CACHE_DETAIL, 1);
            VideoBenchmark.reportCacheLoading(VideoBenchmark.KEY_CACHE);
        }
        if (bitmap3 == null) {
            String path2 = CacheContext.get().getDiskCache().getPath(cacheKey);
            if (!TextUtils.isEmpty(path2)) {
                long t2 = System.nanoTime();
                bitmap3 = ImageUtils.decodeBitmapByFalcon(new File(path2));
                VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_THUMB_DECODE_TIME, System.nanoTime() - t2);
            }
        }
        long cost = System.currentTimeMillis() - ts;
        if (cost > 100) {
            Logger.D(TAG, "operation getThumbnail took " + cost + RPCDataParser.TIME_MS, new Object[0]);
            return bitmap3;
        }
        Logger.D(TAG, "operation getThumbnail took " + cost + RPCDataParser.TIME_MS, new Object[0]);
        return bitmap3;
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
            long ts = System.nanoTime();
            this.mPlayUrl = VideoFileManager.getInstance().getVideoPathById(id);
            VideoBenchmark.getBundle(VideoBenchmark.KEY_CACHE).putLong("cache_loading_time", System.nanoTime() - ts);
            VideoBenchmark.getBundle(VideoBenchmark.KEY_CACHE).putInt(VideoBenchmark.KEY_CACHE_DETAIL, TextUtils.isEmpty(this.mPlayUrl) ? 3 : 5);
            VideoBenchmark.reportCacheLoading(VideoBenchmark.KEY_CACHE);
            Logger.D(TAG, this + "\tsetVideoId: " + id, new Object[0]);
        }
    }

    public String getVideoId() {
        return this.mVideoId;
    }

    public boolean isPlaying() {
        return this.mPreparing || (this.mMediaPlayer != null && this.mStarted && this.mMediaPlayer.isPlaying());
    }

    public boolean isPlayingOK() {
        return this.mStarted;
    }

    public void setOnCompletionListener(OnPlayCompletionListener listener) {
        Logger.D(TAG, "setOnCompletionListener: " + listener, new Object[0]);
        this.mCompletionListener = listener;
    }

    public void setOnErrorListener(OnPlayErrorListener listener) {
        Logger.D(TAG, "setOnErrorListener: " + listener, new Object[0]);
        this.mErrorListener = listener;
    }

    public void onPrepared(MediaPlayer mp) {
        Logger.D(TAG, this + " prepare done Url: " + this.mPlayUrl + "current time: " + this.mCurPlayTime, new Object[0]);
        UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_PLAY_VD, "0");
    }

    public void onCompletion(MediaPlayer mp) {
        Logger.D(TAG, this + "\tonCompletion,cb is" + this.mCompletionListener, new Object[0]);
        if (this.mCompletionListener != null) {
            this.mCompletionListener.onCompletion();
        }
        if (this.mHolder != null && this.mIsLoop) {
            this.mHandler.sendEmptyMessage(3);
            this.mHandler.sendEmptyMessage(0);
        } else if (this.mAudioPaused.compareAndSet(true, false)) {
            AudioUtils.resumeSystemAudio();
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        Logger.D(TAG, this + " onError:" + what + "," + extra + " file: " + this.mPlayUrl + ", mVideoId: " + this.mVideoId, new Object[0]);
        if (this.mErrorListener != null) {
            this.mErrorListener.onError(what, extra);
        }
        FileCacheModel videoInfo = VideoFileManager.getInstance().getVideoInfo(this.mVideoId);
        if (videoInfo == null || (videoInfo.tag & 4096) == 0) {
            UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_PLAY_VD, String.valueOf(what));
        }
        return false;
    }

    public void pause() {
        this.mHandler.removeMessages(11);
        this.mHandler.sendEmptyMessage(11);
    }

    public void resume() {
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessage(1);
    }

    public void seekTo(int msec) {
        this.mHandler.removeMessages(10);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(10, msec, 0));
    }

    public void start() {
        Logger.D(TAG, this + "\tstart###", new Object[0]);
        checkHandler();
        startInternal();
    }

    public void start(String id, int msec) {
        Logger.D(TAG, this + "\tstart###", new Object[0]);
        setVideoId(id);
        checkHandler();
        this.mCurPlayTime = (long) msec;
        startInternal();
    }

    public void start(String id) {
        Logger.D(TAG, this + "\tstart###", new Object[0]);
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
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessage(0);
    }

    public int getCurrentPosition() {
        Logger.D(TAG, this + "\tgetCurrentPosition###", new Object[0]);
        if (this.mMediaPlayer != null && this.mStarted) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        Logger.D(TAG, this + "\tgetCurrentPosition = -1", new Object[0]);
        return -1;
    }

    public int getDuration() {
        int dur = getVideoInfo().duration;
        if (dur <= 0) {
            Logger.D(TAG, "getDuration dur=" + dur, new Object[0]);
        }
        return dur;
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

    public void setScreenOnWhilePlaying(boolean screenOn) {
        this.mKeepScreenOn = screenOn;
    }

    public void stop() {
        if (this.mStarted || this.mPreparing) {
            Logger.D(TAG, this + "\tstop###", new Object[0]);
            this.mCurPlayTime = 0;
            this.mPreparing = false;
            this.mStarted = false;
            if (this.mHandler != null) {
                this.mHandler.removeMessages(3);
                this.mHandler.sendEmptyMessage(3);
            }
            if (this.mAudioPaused.compareAndSet(true, false)) {
                AudioUtils.resumeSystemAudio();
                return;
            }
            return;
        }
        Logger.D(TAG, "invalid stop, skip###", new Object[0]);
    }

    public void drawThumbnail() {
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_PLAY_START, System.nanoTime());
        checkHandler();
        this.mHandler.removeMessages(5);
        this.mHandler.sendEmptyMessage(5);
    }

    public void drawBitmap(Bitmap bitmap) {
        Logger.D(TAG, this + "\tdrawBitmap bitmap: " + bitmap, new Object[0]);
        try {
            VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_PLAY_START, System.nanoTime());
        } catch (Throwable t) {
            Logger.D(TAG, "drawBitmap skip exception, t: " + t, new Object[0]);
        }
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
        boolean flag;
        Logger.D(TAG, this + "\tonDetachedFromWindow mMediaPlayer: " + this.mMediaPlayer, new Object[0]);
        synchronized (this.mSurfaceLock) {
            this.mSurfaceLock.notifyAll();
        }
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(4);
            this.mHandler.sendEmptyMessage(8);
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
        Logger.D(TAG, "onDetachedFromWindow after wait", new Object[0]);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: private */
    public void handleRelease() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        if (this.mAudioPaused.compareAndSet(true, false)) {
            AudioUtils.resumeSystemAudio();
        }
        Logger.D(TAG, this + "mediaplayer release done.", new Object[0]);
    }

    /* access modifiers changed from: private */
    public void handlePrepare() {
        try {
            if (!this.mStarted || this.mMediaPlayer == null) {
                Logger.D(TAG, this + " handlePrepare begin, path:" + this.mPlayUrl + ", mMediaPlayer: " + this.mMediaPlayer, new Object[0]);
                if (this.mMediaPlayer != null) {
                    Logger.D(TAG, "clear previous mediaplayer", new Object[0]);
                    this.mMediaPlayer.reset();
                    this.mMediaPlayer.release();
                    this.mMediaPlayer = null;
                }
                this.mFrameIndex = 0;
                this.mMediaPlayer = new MediaPlayer();
                this.mMediaPlayer.setOnCompletionListener(this);
                this.mMediaPlayer.setOnErrorListener(this);
                this.mMediaPlayer.setDataSource(this.mPlayUrl);
                Logger.D(TAG, "after setDataSource", new Object[0]);
                this.mMediaPlayer.setSurface(this.mSurface);
                Logger.D(TAG, "after setDisplay", new Object[0]);
                if (this.mKeepScreenOn) {
                    Logger.D(TAG, this + "setWakeMode", new Object[0]);
                    this.mMediaPlayer.setWakeMode(getContext(), 536870922);
                }
                Logger.D(TAG, "after setScreenOnWhilePlaying", new Object[0]);
                this.mMediaPlayer.prepare();
                Logger.D(TAG, "after prepare", new Object[0]);
                VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_VIDEO_PREPARED, System.nanoTime());
                this.mVideoWidth = this.mMediaPlayer.getVideoWidth();
                this.mVideoHeight = this.mMediaPlayer.getVideoHeight();
                generateViewport();
                this.mMediaPlayer.start();
                if (this.mCurPlayTime > 0) {
                    this.mMediaPlayer.seekTo((int) this.mCurPlayTime);
                }
                if (this.mAudioPaused.compareAndSet(false, true)) {
                    AudioUtils.pauseSystemAudio();
                }
                this.mStarted = true;
                this.mPreparing = false;
                this.mSeekWhenResume = 0;
                return;
            }
            this.mMediaPlayer.start();
            Logger.D(TAG, this + "resume play", new Object[0]);
        } catch (Exception e) {
            Logger.E((String) TAG, this + " prepare exception:" + e.getMessage(), (Throwable) e, new Object[0]);
            this.mStarted = false;
            if (this.mErrorListener != null) {
                Logger.E(TAG, "onError callback", new Object[0]);
                this.mErrorListener.onError(-1, -1);
            }
            if (this.mMediaPlayer != null) {
                this.mMediaPlayer.release();
                this.mMediaPlayer = null;
            }
        } finally {
            this.mPreparing = false;
            this.mSeekWhenResume = 0;
        }
    }

    /* access modifiers changed from: private */
    public void handleResume() {
        Logger.D(TAG, "handleResume", new Object[0]);
        if (!isPlaying()) {
            this.mMediaPlayer.start();
            if (this.mSeekWhenResume > 0) {
                this.mMediaPlayer.seekTo(this.mSeekWhenResume);
            }
        }
    }

    /* access modifiers changed from: private */
    public void handlePause() {
        Logger.D(TAG, "handlePause", new Object[0]);
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.pause();
        }
    }

    /* access modifiers changed from: private */
    public void handleSeek(int msec) {
        Logger.D(TAG, "handleSeek: " + msec, new Object[0]);
        if (isPlaying()) {
            long ts = System.currentTimeMillis();
            this.mMediaPlayer.seekTo(this.mSeekWhenResume);
            Logger.D(TAG, "seekTo took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
            this.mSeekWhenResume = 0;
            return;
        }
        this.mSeekWhenResume = msec;
    }

    /* access modifiers changed from: private */
    public void handleReset() {
        Logger.D(TAG, "handleReset(), mMediaPlayer: " + this.mMediaPlayer, new Object[0]);
        this.mFrameIndex = 0;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
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

    public void surfaceCreated(SurfaceHolder holder) {
        VideoBenchmark.getBundle(new StringBuilder(VideoBenchmark.KEY_PLAY).append(this.mObjectId).toString()).putLong(VideoBenchmark.KEY_BUFFER_PREPARED, System.nanoTime());
        synchronized (this.mSurfaceLock) {
            this.mHolder = holder;
            Logger.D(TAG, this + " surfaceCreated and notify######, w = " + getWidth() + ", h = " + getHeight(), new Object[0]);
            this.mSurfaceLock.notifyAll();
            Logger.D(TAG, this + "after surfaceCreated and notify######", new Object[0]);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Logger.D(TAG, this + " surfaceChanged w:" + width + ", h:" + height, new Object[0]);
        generateViewport();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Logger.D(TAG, this + " surfaceDestroyed", new Object[0]);
        this.mHolder = null;
        if (this.mDisplaySurface != null) {
            this.mDisplaySurface.release();
            this.mDisplaySurface = null;
        }
        Logger.D(TAG, "surfaceDestroyed done", new Object[0]);
    }

    private void checkHandler() {
        synchronized (this.mHandlerLock) {
            if (this.mHandler == null || this.mThread == null || !this.mThread.isAlive() || this.mHandler.getLooper() == null) {
                Logger.D(TAG, this + "\tthread not ready, create...", new Object[0]);
                releaseGl();
                this.mThread = new HandlerThread("sur_play");
                this.mThread.start();
                this.mHandler = new PlayHandler(this, this.mThread.getLooper());
                this.mStarted = false;
                this.mPreparing = false;
            }
        }
    }

    private void setExceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                Logger.E(VideoSurfaceViewImpl.TAG, "uncaughtException###, thread name:" + thread.getName() + ", thread id:" + thread.getId() + ",ex:" + ex.getMessage(), new Object[0]);
                StackTraceElement[] elements = ex.getStackTrace();
                StringBuilder sb = new StringBuilder();
                for (StackTraceElement stackTraceElement : elements) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
                Logger.E(VideoSurfaceViewImpl.TAG, "exception stack:\n" + sb.toString(), new Object[0]);
                VideoSurfaceViewImpl.this.releaseGl();
            }
        });
    }
}
