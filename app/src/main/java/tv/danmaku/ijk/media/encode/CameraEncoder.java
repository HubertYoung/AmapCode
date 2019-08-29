package tv.danmaku.ijk.media.encode;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoFileManager;
import com.alipay.multimedia.gles.EglCore;
import com.alipay.multimedia.gles.FullFrameRect;
import com.alipay.multimedia.gles.Texture2dProgram;
import com.alipay.multimedia.gles.Texture2dProgram.ProgramType;
import com.alipay.multimedia.gles.WindowSurface;
import com.alipay.streammedia.mmengine.video.VideoInfo;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.widget.CameraView;

@TargetApi(14)
public class CameraEncoder implements OnFrameAvailableListener {
    private static final int MSG_FRAME_AVAILABLE = 1;
    private static final int MSG_INIT = 5;
    private static final int MSG_RELEASE = 3;
    private static final int MSG_RELEASE_GL = 4;
    private static final int MSG_SET_SURFACE_TEXTURE = 2;
    protected static final String TAG = "CameraEncoder";
    /* access modifiers changed from: private */
    public static final Object mClassLock = new Object();
    protected int mBufferHeight;
    protected int mBufferWidth;
    private Camera mCamera;
    private Object mCameraLock = new Object();
    private SurfaceTexture mCameraTexture;
    protected SessionConfig mConfig;
    private WindowSurface mDisplaySurface;
    protected CameraView mDisplayView;
    private EglCore mEglCore;
    private WindowSurface mEncoderSurface;
    private int mEncodingCount = 0;
    private boolean mEosRequested = false;
    private AtomicBoolean mFirstFrame = new AtomicBoolean(true);
    private long mFirstTs = 0;
    private long mFrameCount = 0;
    private FullFrameRect mFullFrameBlit;
    private Handler mHandler;
    private volatile boolean mIsRecording;
    private long mLastTs = 0;
    private Object mLock = new Object();
    protected Size mPreviewSize;
    private long mRenderCount = 0;
    private long mStartPreviewEnd;
    private int mTextureId;
    private HandlerThread mThread;
    private volatile boolean mThumbRequest = true;
    private final float[] mTmpMatrix = new float[16];
    private long mTotalEncodingTime = 0;
    public boolean mUseVideoEncoderNative = false;
    private AndroidEncoder mVideoEncoder;

    private class EncoderHandler extends Handler {
        private WeakReference<CameraEncoder> mWeakEncoder;

        public EncoderHandler(CameraEncoder encoder, Looper looper) {
            super(looper);
            this.mWeakEncoder = new WeakReference<>(encoder);
        }

        public void handleMessage(Message inputMessage) {
            boolean display = true;
            int what = inputMessage.what;
            Object obj = inputMessage.obj;
            CameraEncoder encoder = (CameraEncoder) this.mWeakEncoder.get();
            if (encoder == null) {
                Logger.D(CameraEncoder.TAG, "EncoderHandler.handleMessage: encoder is null", new Object[0]);
                return;
            }
            if (what != 1) {
                Logger.D(CameraEncoder.TAG, "handleMessage handle msg:" + what, new Object[0]);
            }
            switch (what) {
                case 1:
                    if (inputMessage.obj != null) {
                        display = ((Boolean) inputMessage.obj).booleanValue();
                    }
                    encoder.handleFrameAvailable(display);
                    return;
                case 2:
                    synchronized (CameraEncoder.mClassLock) {
                        encoder.handleSetSurfaceTexture((SurfaceTexture) obj);
                    }
                    return;
                case 3:
                    synchronized (CameraEncoder.mClassLock) {
                        encoder.handleRelease(true);
                    }
                    return;
                case 4:
                    synchronized (CameraEncoder.mClassLock) {
                        encoder.handleRelease(false);
                    }
                    return;
                case 5:
                    encoder.handleInit();
                    return;
                default:
                    try {
                        CameraEncoder.this.handleGLMessage(inputMessage);
                        return;
                    } catch (IOException e) {
                        Logger.E((String) CameraEncoder.TAG, (Throwable) e, (String) "handleMessage error", new Object[0]);
                    }
            }
            Logger.E((String) CameraEncoder.TAG, (Throwable) e, (String) "handleMessage error", new Object[0]);
        }
    }

    public CameraEncoder(SessionConfig config) {
        this.mConfig = config;
        getHandler();
        sendMsg(5);
    }

    public void setPreviewDisplay(CameraView display) {
        this.mDisplayView = display;
    }

    public void setCamera(Camera camera) {
        this.mCamera = camera;
        if (camera != null) {
            try {
                this.mPreviewSize = camera.getParameters().getPreviewSize();
            } catch (RuntimeException e) {
                Logger.E((String) TAG, (Throwable) e, (String) "getParameters excepiton:", new Object[0]);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Camera getCamera() {
        return this.mCamera;
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (this.mFrameCount % 30 == 0) {
            Logger.D(TAG, "Camera Time onFrameAvailable.cost=" + (System.currentTimeMillis() - this.mStartPreviewEnd), new Object[0]);
        }
        this.mFrameCount++;
        sendMsg(1);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture) {
        Message msg = Message.obtain();
        msg.what = 2;
        msg.obj = surfaceTexture;
        if (sendMsg(msg)) {
            synchronized (this.mCameraLock) {
                try {
                    this.mCameraLock.wait(2000);
                } catch (InterruptedException e) {
                    Logger.E(TAG, "InterruptedException:" + e.getMessage(), new Object[0]);
                }
            }
        }
        Logger.D(TAG, "MSG_SET_SURFACE_TEXTURE process done", new Object[0]);
    }

    /* access modifiers changed from: protected */
    public synchronized Handler getHandler() {
        if (this.mHandler == null) {
            this.mThread = new HandlerThread(TAG);
            this.mThread.setPriority(10);
            this.mThread.start();
            this.mHandler = new EncoderHandler(this, this.mThread.getLooper());
        }
        return this.mHandler;
    }

    private boolean sendMsg(int what) {
        if (this.mHandler == null || this.mThread == null || !this.mThread.isAlive() || this.mHandler.getLooper() == null) {
            return false;
        }
        return this.mHandler.sendEmptyMessage(what);
    }

    private boolean sendMsg(Message msg) {
        if (this.mHandler == null || this.mThread == null || !this.mThread.isAlive() || this.mHandler.getLooper() == null) {
            return false;
        }
        return this.mHandler.sendMessage(msg);
    }

    public void release() {
        synchronized (this.mLock) {
            this.mLock.notifyAll();
        }
        Logger.D(TAG, "handleFrameAvailable release notifyAll.~~~~", new Object[0]);
        sendMsg(3);
    }

    public void releaseGL() {
        synchronized (this.mLock) {
            this.mLock.notifyAll();
        }
        Logger.D(TAG, "handleFrameAvailable releaseGL notifyAll.~~~~", new Object[0]);
        sendMsg(4);
    }

    /* access modifiers changed from: private */
    @TargetApi(15)
    public void handleInit() {
        Logger.D(TAG, "handleInit...", new Object[0]);
        setExceptionHandler();
        if (this.mEglCore == null) {
            this.mEglCore = new EglCore(null, 1);
        }
        try {
            prepareEncoder(this.mConfig.getVideoWidth(), this.mConfig.getVideoHeight(), this.mConfig.getVideoBitrate(), this.mConfig);
        } catch (Exception e) {
            Logger.E((String) TAG, (Throwable) e, (String) "handleInit.error", new Object[0]);
        }
    }

    private void prepareEncoder(int width, int height, int bitRate, SessionConfig config) {
        if (this.mVideoEncoder == null) {
            this.mVideoEncoder = this.mUseVideoEncoderNative ? new VideoEncoderNative(config) : new VideoEncoderCore(width, height, bitRate, config);
        }
        if (this.mEncoderSurface == null) {
            this.mEncoderSurface = new WindowSurface(this.mEglCore, this.mVideoEncoder.getInputSurface(), false);
        }
    }

    /* access modifiers changed from: private */
    @TargetApi(15)
    public void handleSetSurfaceTexture(SurfaceTexture surfaceTexture) {
        try {
            this.mBufferWidth = this.mPreviewSize.height;
            this.mBufferHeight = this.mPreviewSize.width;
            Logger.D(TAG, "mBufferWidth:" + this.mBufferWidth + ", mBufferHeight:" + this.mBufferHeight, new Object[0]);
            surfaceTexture.setDefaultBufferSize(this.mBufferWidth, this.mBufferHeight);
        } catch (Exception e) {
            Logger.E(TAG, "handleSetSurfaceTexture, getParameters exception:" + e.getMessage(), new Object[0]);
            this.mBufferWidth = this.mDisplayView.getWidth();
            this.mBufferHeight = this.mDisplayView.getHeight();
            if (ConfigManager.getInstance().getCommonConfigItem().videoConf.enableSetTexSize == 1) {
                surfaceTexture.setDefaultBufferSize(this.mBufferWidth, this.mBufferHeight);
            }
        }
        try {
            if (this.mDisplaySurface == null) {
                try {
                    this.mDisplaySurface = new WindowSurface(this.mEglCore, new Surface(surfaceTexture), false);
                } catch (Exception e2) {
                    Logger.D(TAG, "Surface not support, try SurfaceTexture.", new Object[0]);
                    this.mDisplaySurface = new WindowSurface(this.mEglCore, surfaceTexture);
                }
            }
            this.mDisplaySurface.makeCurrent();
            if (this.mCameraTexture == null) {
                this.mCameraTexture = createCameraTexture();
                this.mCameraTexture.setOnFrameAvailableListener(this);
            }
            if (VideoUtils.previewRunning(this.mCamera)) {
                Logger.D(TAG, "preview is running, stop it.", new Object[0]);
                this.mCamera.stopPreview();
            }
            this.mCamera.setPreviewTexture(this.mCameraTexture);
            this.mDisplayView.startPreview();
            this.mStartPreviewEnd = System.currentTimeMillis();
            Logger.D(TAG, "startPreview end", new Object[0]);
            synchronized (this.mCameraLock) {
                this.mCameraLock.notifyAll();
            }
        } catch (Exception e3) {
            Logger.E((String) TAG, (Throwable) e3, (String) "handleSetSurfaceTexture error", new Object[0]);
            synchronized (this.mCameraLock) {
                this.mCameraLock.notifyAll();
            }
        } catch (Throwable th) {
            synchronized (this.mCameraLock) {
                this.mCameraLock.notifyAll();
                throw th;
            }
        }
    }

    /* access modifiers changed from: protected */
    public SurfaceTexture createCameraTexture() {
        this.mFullFrameBlit = new FullFrameRect(new Texture2dProgram(ProgramType.TEXTURE_EXT));
        this.mTextureId = this.mFullFrameBlit.createTextureObject();
        return new SurfaceTexture(this.mTextureId);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleFrameAvailable(boolean r11) {
        /*
            r10 = this;
            r9 = 0
            boolean r6 = r10.isNeedLog()
            if (r6 == 0) goto L_0x0029
            java.lang.String r6 = "CameraEncoder"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "handleFrameAvailable display: "
            r7.<init>(r8)
            java.lang.StringBuilder r7 = r7.append(r11)
            java.lang.String r8 = ";mIsRecording="
            java.lang.StringBuilder r7 = r7.append(r8)
            boolean r8 = r10.mIsRecording
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            java.lang.Object[] r8 = new java.lang.Object[r9]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r6, r7, r8)
        L_0x0029:
            com.alipay.multimedia.gles.EglCore r6 = r10.mEglCore     // Catch:{ Exception -> 0x01fa }
            if (r6 != 0) goto L_0x008e
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "Skipping drawFrame after shutdown"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x01fa }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r6, r7, r8)     // Catch:{ Exception -> 0x01fa }
            boolean r6 = r10.mIsRecording
            if (r6 == 0) goto L_0x0064
            boolean r6 = r10.mEosRequested
            if (r6 == 0) goto L_0x0064
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "Sending last video frame. Draining encoder"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0068 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r6, r7, r8)     // Catch:{ Exception -> 0x0068 }
            tv.danmaku.ijk.media.encode.AndroidEncoder r6 = r10.mVideoEncoder     // Catch:{ Exception -> 0x0068 }
            r6.signalEndOfStream()     // Catch:{ Exception -> 0x0068 }
            tv.danmaku.ijk.media.encode.AndroidEncoder r6 = r10.mVideoEncoder     // Catch:{ Exception -> 0x0068 }
            r7 = 1
            r8 = 1
            r6.drainEncoder(r7, r8)     // Catch:{ Exception -> 0x0068 }
            r6 = 0
            r10.mIsRecording = r6     // Catch:{ Exception -> 0x0068 }
            r10.release()     // Catch:{ Exception -> 0x0068 }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r6 = r10.mLock     // Catch:{ all -> 0x0065 }
            r6.notifyAll()     // Catch:{ all -> 0x0065 }
            monitor-exit(r7)     // Catch:{ all -> 0x0065 }
        L_0x0064:
            return
        L_0x0065:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0065 }
            throw r6
        L_0x0068:
            r1 = move-exception
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "signalEndOfStream error"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x0080 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r6, r1, r7, r8)     // Catch:{ all -> 0x0080 }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r6 = r10.mLock     // Catch:{ all -> 0x007d }
            r6.notifyAll()     // Catch:{ all -> 0x007d }
            monitor-exit(r7)     // Catch:{ all -> 0x007d }
            goto L_0x0064
        L_0x007d:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x007d }
            throw r6
        L_0x0080:
            r6 = move-exception
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r8 = r10.mLock     // Catch:{ all -> 0x008b }
            r8.notifyAll()     // Catch:{ all -> 0x008b }
            monitor-exit(r7)     // Catch:{ all -> 0x008b }
            throw r6
        L_0x008b:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x008b }
            throw r6
        L_0x008e:
            if (r11 == 0) goto L_0x00d9
            com.alipay.multimedia.gles.WindowSurface r6 = r10.mDisplaySurface     // Catch:{ Exception -> 0x01fa }
            r6.makeCurrent()     // Catch:{ Exception -> 0x01fa }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            android.opengl.GLES20.glClearColor(r6, r7, r8, r9)     // Catch:{ Exception -> 0x01fa }
            r6 = 16384(0x4000, float:2.2959E-41)
            android.opengl.GLES20.glClear(r6)     // Catch:{ Exception -> 0x01fa }
            android.graphics.SurfaceTexture r6 = r10.mCameraTexture     // Catch:{ Exception -> 0x01fa }
            r6.updateTexImage()     // Catch:{ Exception -> 0x01fa }
            android.graphics.SurfaceTexture r6 = r10.mCameraTexture     // Catch:{ Exception -> 0x01fa }
            float[] r7 = r10.mTmpMatrix     // Catch:{ Exception -> 0x01fa }
            r6.getTransformMatrix(r7)     // Catch:{ Exception -> 0x01fa }
            r6 = 0
            r7 = 0
            int r8 = r10.mBufferWidth     // Catch:{ Exception -> 0x01fa }
            int r9 = r10.mBufferHeight     // Catch:{ Exception -> 0x01fa }
            android.opengl.GLES20.glViewport(r6, r7, r8, r9)     // Catch:{ Exception -> 0x01fa }
            float[] r6 = r10.mTmpMatrix     // Catch:{ Exception -> 0x01fa }
            r10.drawOnScreen(r6)     // Catch:{ Exception -> 0x01fa }
            com.alipay.multimedia.gles.WindowSurface r6 = r10.mDisplaySurface     // Catch:{ Exception -> 0x01fa }
            r6.swapBuffers()     // Catch:{ Exception -> 0x01fa }
            java.util.concurrent.atomic.AtomicBoolean r6 = r10.mFirstFrame     // Catch:{ Exception -> 0x01fa }
            r7 = 1
            r8 = 0
            boolean r6 = r6.compareAndSet(r7, r8)     // Catch:{ Exception -> 0x01fa }
            if (r6 == 0) goto L_0x00d9
            java.lang.String r6 = "video_rec_"
            android.os.Bundle r6 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark.getBundle(r6)     // Catch:{ Exception -> 0x01fa }
            java.lang.String r7 = "first_frame_show"
            long r8 = java.lang.System.nanoTime()     // Catch:{ Exception -> 0x01fa }
            r6.putLong(r7, r8)     // Catch:{ Exception -> 0x01fa }
        L_0x00d9:
            boolean r6 = r10.mIsRecording     // Catch:{ Exception -> 0x01fa }
            if (r6 == 0) goto L_0x01b7
            if (r11 == 0) goto L_0x01b7
            tv.danmaku.ijk.media.widget.CameraView r6 = r10.mDisplayView     // Catch:{ Exception -> 0x01fa }
            if (r6 == 0) goto L_0x01b7
            tv.danmaku.ijk.media.widget.CameraView r6 = r10.mDisplayView     // Catch:{ Exception -> 0x01fa }
            boolean r6 = r6.isLive()     // Catch:{ Exception -> 0x01fa }
            if (r6 == 0) goto L_0x00f3
            tv.danmaku.ijk.media.widget.CameraView r6 = r10.mDisplayView     // Catch:{ Exception -> 0x01fa }
            boolean r6 = r6.isAudioStart()     // Catch:{ Exception -> 0x01fa }
            if (r6 == 0) goto L_0x01b7
        L_0x00f3:
            android.graphics.SurfaceTexture r6 = r10.mCameraTexture     // Catch:{ Exception -> 0x01fa }
            long r6 = r6.getTimestamp()     // Catch:{ Exception -> 0x01fa }
            r8 = 1000(0x3e8, double:4.94E-321)
            long r2 = r6 / r8
            boolean r6 = r10.isNeedLostFrames(r2)     // Catch:{ Exception -> 0x01fa }
            if (r6 == 0) goto L_0x015c
            boolean r6 = r10.mIsRecording
            if (r6 == 0) goto L_0x0064
            boolean r6 = r10.mEosRequested
            if (r6 == 0) goto L_0x0064
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "Sending last video frame. Draining encoder"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0135 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r6, r7, r8)     // Catch:{ Exception -> 0x0135 }
            tv.danmaku.ijk.media.encode.AndroidEncoder r6 = r10.mVideoEncoder     // Catch:{ Exception -> 0x0135 }
            r6.signalEndOfStream()     // Catch:{ Exception -> 0x0135 }
            tv.danmaku.ijk.media.encode.AndroidEncoder r6 = r10.mVideoEncoder     // Catch:{ Exception -> 0x0135 }
            r7 = 1
            r8 = 1
            r6.drainEncoder(r7, r8)     // Catch:{ Exception -> 0x0135 }
            r6 = 0
            r10.mIsRecording = r6     // Catch:{ Exception -> 0x0135 }
            r10.release()     // Catch:{ Exception -> 0x0135 }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r6 = r10.mLock     // Catch:{ all -> 0x0132 }
            r6.notifyAll()     // Catch:{ all -> 0x0132 }
            monitor-exit(r7)     // Catch:{ all -> 0x0132 }
            goto L_0x0064
        L_0x0132:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0132 }
            throw r6
        L_0x0135:
            r1 = move-exception
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "signalEndOfStream error"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x014e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r6, r1, r7, r8)     // Catch:{ all -> 0x014e }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r6 = r10.mLock     // Catch:{ all -> 0x014b }
            r6.notifyAll()     // Catch:{ all -> 0x014b }
            monitor-exit(r7)     // Catch:{ all -> 0x014b }
            goto L_0x0064
        L_0x014b:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x014b }
            throw r6
        L_0x014e:
            r6 = move-exception
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r8 = r10.mLock     // Catch:{ all -> 0x0159 }
            r8.notifyAll()     // Catch:{ all -> 0x0159 }
            monitor-exit(r7)     // Catch:{ all -> 0x0159 }
            throw r6
        L_0x0159:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0159 }
            throw r6
        L_0x015c:
            long r4 = java.lang.System.nanoTime()     // Catch:{ Exception -> 0x01fa }
            tv.danmaku.ijk.media.encode.AndroidEncoder r6 = r10.mVideoEncoder     // Catch:{ Exception -> 0x01fa }
            r7 = 0
            r8 = 1
            r6.drainEncoder(r7, r8)     // Catch:{ Exception -> 0x01fa }
            com.alipay.multimedia.gles.WindowSurface r6 = r10.mEncoderSurface     // Catch:{ Exception -> 0x01fa }
            r6.makeCurrent()     // Catch:{ Exception -> 0x01fa }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            android.opengl.GLES20.glClearColor(r6, r7, r8, r9)     // Catch:{ Exception -> 0x01fa }
            r6 = 16384(0x4000, float:2.2959E-41)
            android.opengl.GLES20.glClear(r6)     // Catch:{ Exception -> 0x01fa }
            r6 = 0
            r7 = 0
            tv.danmaku.ijk.media.encode.SessionConfig r8 = r10.mConfig     // Catch:{ Exception -> 0x01fa }
            int r8 = r8.getVideoWidth()     // Catch:{ Exception -> 0x01fa }
            tv.danmaku.ijk.media.encode.SessionConfig r9 = r10.mConfig     // Catch:{ Exception -> 0x01fa }
            int r9 = r9.getVideoHeight()     // Catch:{ Exception -> 0x01fa }
            android.opengl.GLES20.glViewport(r6, r7, r8, r9)     // Catch:{ Exception -> 0x01fa }
            float[] r6 = r10.mTmpMatrix     // Catch:{ Exception -> 0x01fa }
            r10.drawOnEncoder(r6)     // Catch:{ Exception -> 0x01fa }
            boolean r6 = r10.mThumbRequest     // Catch:{ Exception -> 0x01fa }
            if (r6 == 0) goto L_0x01e9
            r6 = 0
            r10.mThumbRequest = r6     // Catch:{ Exception -> 0x01fa }
            java.lang.String r6 = "video_rec_"
            android.os.Bundle r6 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark.getBundle(r6)     // Catch:{ Exception -> 0x01fa }
            java.lang.String r7 = "encode_begin"
            long r8 = java.lang.System.nanoTime()     // Catch:{ Exception -> 0x01fa }
            r6.putLong(r7, r8)     // Catch:{ Exception -> 0x01fa }
            r10.saveFrameAsImage()     // Catch:{ Exception -> 0x01fa }
        L_0x01a7:
            com.alipay.multimedia.gles.WindowSurface r6 = r10.mEncoderSurface     // Catch:{ Exception -> 0x01fa }
            android.graphics.SurfaceTexture r7 = r10.mCameraTexture     // Catch:{ Exception -> 0x01fa }
            long r8 = r7.getTimestamp()     // Catch:{ Exception -> 0x01fa }
            r6.setPresentationTime(r8)     // Catch:{ Exception -> 0x01fa }
            com.alipay.multimedia.gles.WindowSurface r6 = r10.mEncoderSurface     // Catch:{ Exception -> 0x01fa }
            r6.swapBuffers()     // Catch:{ Exception -> 0x01fa }
        L_0x01b7:
            boolean r6 = r10.mIsRecording
            if (r6 == 0) goto L_0x0064
            boolean r6 = r10.mEosRequested
            if (r6 == 0) goto L_0x0064
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "Sending last video frame. Draining encoder"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0237 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r6, r7, r8)     // Catch:{ Exception -> 0x0237 }
            tv.danmaku.ijk.media.encode.AndroidEncoder r6 = r10.mVideoEncoder     // Catch:{ Exception -> 0x0237 }
            r6.signalEndOfStream()     // Catch:{ Exception -> 0x0237 }
            tv.danmaku.ijk.media.encode.AndroidEncoder r6 = r10.mVideoEncoder     // Catch:{ Exception -> 0x0237 }
            r7 = 1
            r8 = 1
            r6.drainEncoder(r7, r8)     // Catch:{ Exception -> 0x0237 }
            r6 = 0
            r10.mIsRecording = r6     // Catch:{ Exception -> 0x0237 }
            r10.release()     // Catch:{ Exception -> 0x0237 }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r6 = r10.mLock     // Catch:{ all -> 0x01e6 }
            r6.notifyAll()     // Catch:{ all -> 0x01e6 }
            monitor-exit(r7)     // Catch:{ all -> 0x01e6 }
            goto L_0x0064
        L_0x01e6:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x01e6 }
            throw r6
        L_0x01e9:
            long r6 = r10.mTotalEncodingTime     // Catch:{ Exception -> 0x01fa }
            long r8 = java.lang.System.nanoTime()     // Catch:{ Exception -> 0x01fa }
            long r8 = r8 - r4
            long r6 = r6 + r8
            r10.mTotalEncodingTime = r6     // Catch:{ Exception -> 0x01fa }
            int r6 = r10.mEncodingCount     // Catch:{ Exception -> 0x01fa }
            int r6 = r6 + 1
            r10.mEncodingCount = r6     // Catch:{ Exception -> 0x01fa }
            goto L_0x01a7
        L_0x01fa:
            r0 = move-exception
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "handleFrameAvailable error"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x0285 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r6, r0, r7, r8)     // Catch:{ all -> 0x0285 }
            boolean r6 = r10.mIsRecording
            if (r6 == 0) goto L_0x0064
            boolean r6 = r10.mEosRequested
            if (r6 == 0) goto L_0x0064
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "Sending last video frame. Draining encoder"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x025e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r6, r7, r8)     // Catch:{ Exception -> 0x025e }
            tv.danmaku.ijk.media.encode.AndroidEncoder r6 = r10.mVideoEncoder     // Catch:{ Exception -> 0x025e }
            r6.signalEndOfStream()     // Catch:{ Exception -> 0x025e }
            tv.danmaku.ijk.media.encode.AndroidEncoder r6 = r10.mVideoEncoder     // Catch:{ Exception -> 0x025e }
            r7 = 1
            r8 = 1
            r6.drainEncoder(r7, r8)     // Catch:{ Exception -> 0x025e }
            r6 = 0
            r10.mIsRecording = r6     // Catch:{ Exception -> 0x025e }
            r10.release()     // Catch:{ Exception -> 0x025e }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r6 = r10.mLock     // Catch:{ all -> 0x0234 }
            r6.notifyAll()     // Catch:{ all -> 0x0234 }
            monitor-exit(r7)     // Catch:{ all -> 0x0234 }
            goto L_0x0064
        L_0x0234:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0234 }
            throw r6
        L_0x0237:
            r1 = move-exception
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "signalEndOfStream error"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x0250 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r6, r1, r7, r8)     // Catch:{ all -> 0x0250 }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r6 = r10.mLock     // Catch:{ all -> 0x024d }
            r6.notifyAll()     // Catch:{ all -> 0x024d }
            monitor-exit(r7)     // Catch:{ all -> 0x024d }
            goto L_0x0064
        L_0x024d:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x024d }
            throw r6
        L_0x0250:
            r6 = move-exception
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r8 = r10.mLock     // Catch:{ all -> 0x025b }
            r8.notifyAll()     // Catch:{ all -> 0x025b }
            monitor-exit(r7)     // Catch:{ all -> 0x025b }
            throw r6
        L_0x025b:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x025b }
            throw r6
        L_0x025e:
            r1 = move-exception
            java.lang.String r6 = "CameraEncoder"
            java.lang.String r7 = "signalEndOfStream error"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x0277 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r6, r1, r7, r8)     // Catch:{ all -> 0x0277 }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r6 = r10.mLock     // Catch:{ all -> 0x0274 }
            r6.notifyAll()     // Catch:{ all -> 0x0274 }
            monitor-exit(r7)     // Catch:{ all -> 0x0274 }
            goto L_0x0064
        L_0x0274:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0274 }
            throw r6
        L_0x0277:
            r6 = move-exception
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r8 = r10.mLock     // Catch:{ all -> 0x0282 }
            r8.notifyAll()     // Catch:{ all -> 0x0282 }
            monitor-exit(r7)     // Catch:{ all -> 0x0282 }
            throw r6
        L_0x0282:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0282 }
            throw r6
        L_0x0285:
            r6 = move-exception
            boolean r7 = r10.mIsRecording
            if (r7 == 0) goto L_0x02b3
            boolean r7 = r10.mEosRequested
            if (r7 == 0) goto L_0x02b3
            java.lang.String r7 = "CameraEncoder"
            java.lang.String r8 = "Sending last video frame. Draining encoder"
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x02b7 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r7, r8, r9)     // Catch:{ Exception -> 0x02b7 }
            tv.danmaku.ijk.media.encode.AndroidEncoder r7 = r10.mVideoEncoder     // Catch:{ Exception -> 0x02b7 }
            r7.signalEndOfStream()     // Catch:{ Exception -> 0x02b7 }
            tv.danmaku.ijk.media.encode.AndroidEncoder r7 = r10.mVideoEncoder     // Catch:{ Exception -> 0x02b7 }
            r8 = 1
            r9 = 1
            r7.drainEncoder(r8, r9)     // Catch:{ Exception -> 0x02b7 }
            r7 = 0
            r10.mIsRecording = r7     // Catch:{ Exception -> 0x02b7 }
            r10.release()     // Catch:{ Exception -> 0x02b7 }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r8 = r10.mLock     // Catch:{ all -> 0x02b4 }
            r8.notifyAll()     // Catch:{ all -> 0x02b4 }
            monitor-exit(r7)     // Catch:{ all -> 0x02b4 }
        L_0x02b3:
            throw r6
        L_0x02b4:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x02b4 }
            throw r6
        L_0x02b7:
            r1 = move-exception
            java.lang.String r7 = "CameraEncoder"
            java.lang.String r8 = "signalEndOfStream error"
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x02cf }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r7, r1, r8, r9)     // Catch:{ all -> 0x02cf }
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r8 = r10.mLock     // Catch:{ all -> 0x02cc }
            r8.notifyAll()     // Catch:{ all -> 0x02cc }
            monitor-exit(r7)     // Catch:{ all -> 0x02cc }
            goto L_0x02b3
        L_0x02cc:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x02cc }
            throw r6
        L_0x02cf:
            r6 = move-exception
            java.lang.Object r7 = r10.mLock
            monitor-enter(r7)
            java.lang.Object r8 = r10.mLock     // Catch:{ all -> 0x02da }
            r8.notifyAll()     // Catch:{ all -> 0x02da }
            monitor-exit(r7)     // Catch:{ all -> 0x02da }
            throw r6
        L_0x02da:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x02da }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.encode.CameraEncoder.handleFrameAvailable(boolean):void");
    }

    /* access modifiers changed from: protected */
    public void drawOnEncoder(float[] transformMatrix) {
        this.mFullFrameBlit.drawCroppedFrame(this.mTextureId, transformMatrix, this.mPreviewSize);
    }

    /* access modifiers changed from: protected */
    public void drawOnScreen(float[] transformMatrix) {
        this.mFullFrameBlit.drawFrame(this.mTextureId, this.mTmpMatrix);
    }

    private void saveFrameAsImage() {
        this.mEncoderSurface.saveFrame(new File(VideoFileManager.getInstance().generateThumbPath(this.mConfig.getVideoId() + "_thumb")), this.mConfig.getOrientation());
    }

    private void releaseEncoder() {
        Logger.I(TAG, "releaseEncoder", new Object[0]);
        if (this.mVideoEncoder != null) {
            this.mVideoEncoder.release();
            this.mVideoEncoder = null;
        }
        if (this.mConfig.isLiveConfig()) {
            if (this.mConfig.getmFFmpegMuxer() != null) {
                this.mConfig.getmFFmpegMuxer().uninit();
            }
        } else if (!this.mConfig.isOMXVideo() && this.mConfig.getMuxer() != null) {
            this.mConfig.getMuxer().clean();
        }
        Logger.D(TAG, "releaseEncoder finish#######", new Object[0]);
    }

    /* access modifiers changed from: private */
    public void handleRelease(boolean iReleaseEncode) {
        Logger.D(TAG, "handleRelease iReleaseEncode=" + iReleaseEncode, new Object[0]);
        if (iReleaseEncode) {
            releaseEncoder();
        }
        if (this.mCameraTexture != null) {
            this.mCameraTexture.release();
            this.mCameraTexture = null;
        }
        if (this.mDisplaySurface != null) {
            this.mDisplaySurface.release();
            this.mDisplaySurface = null;
        }
        if (this.mEncoderSurface != null) {
            this.mEncoderSurface.release();
            this.mEncoderSurface = null;
        }
        releaseRender();
        if (this.mEglCore != null) {
            this.mEglCore.release();
            this.mEglCore = null;
        }
        synchronized (this.mLock) {
            this.mLock.notifyAll();
            Logger.D(TAG, "mLock.notifyAll()", new Object[0]);
        }
        try {
            this.mThread.getLooper().quit();
            this.mHandler = null;
            this.mThread = null;
        } catch (Exception e) {
            Logger.E((String) TAG, (Throwable) e, (String) "looper quit", new Object[0]);
        }
        Logger.D(TAG, "handle release end here", new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void releaseRender() {
        if (this.mFullFrameBlit != null) {
            this.mFullFrameBlit.release(true);
            this.mFullFrameBlit = null;
        }
    }

    public void startRecording() {
        if (this.mIsRecording) {
            Logger.D(TAG, "already started, skip...", new Object[0]);
            return;
        }
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong("record_start", System.nanoTime());
        this.mThumbRequest = true;
        this.mTotalEncodingTime = 0;
        this.mEncodingCount = 0;
        this.mIsRecording = true;
    }

    public boolean checkSurface() {
        return this.mEncoderSurface != null;
    }

    public void stopRecording() {
        int method = 1;
        if (this.mEosRequested) {
            Logger.D(TAG, "already stopped, skip...", new Object[0]);
            return;
        }
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_RECORD_STOP, System.nanoTime());
        this.mEosRequested = true;
        Logger.D(TAG, "stopRecording and current mIsRecording is:" + this.mIsRecording, new Object[0]);
        if (!this.mIsRecording || (this.mThread != null && (this.mThread.getLooper() == null || !this.mThread.isAlive()))) {
            release();
        } else {
            stopRecordForWait();
        }
        if (this.mEncodingCount > 0) {
            VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong("encode_avg_time", this.mTotalEncodingTime / ((long) this.mEncodingCount));
        }
        String path = this.mConfig.getOutputFile().getAbsolutePath();
        VideoInfo info = VideoUtils.getVideoInfo(path);
        if (info != null) {
            VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_VIDEO_DURATION, (long) info.duration);
            VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong("file_size", new File(path).length());
            VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putInt(VideoBenchmark.KEY_VIDEO_FPS, (int) info.fps);
            if (this.mDisplayView.isOMX()) {
                method = 2;
            }
            VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putInt(VideoBenchmark.KEY_ENCODE_METHOD, method);
            VideoBenchmark.reportRecording(VideoBenchmark.KEY_REC);
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void stopRecordForWait() {
        /*
            r6 = this;
            r4 = 0
            android.os.Handler r2 = r6.getHandler()
            r3 = 1
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            android.os.Message r1 = r2.obtainMessage(r3, r4)
            android.os.Handler r2 = r6.getHandler()
            boolean r2 = r2.sendMessage(r1)
            if (r2 == 0) goto L_0x0039
            java.lang.Object r3 = r6.mLock
            monitor-enter(r3)
            java.lang.String r2 = "CameraEncoder"
            java.lang.String r4 = "waiting lock~~~~~~~"
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InterruptedException -> 0x003a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r2, r4, r5)     // Catch:{ InterruptedException -> 0x003a }
            boolean r2 = r6.mEosRequested     // Catch:{ InterruptedException -> 0x003a }
            if (r2 == 0) goto L_0x002e
            java.lang.Object r2 = r6.mLock     // Catch:{ InterruptedException -> 0x003a }
            r2.wait()     // Catch:{ InterruptedException -> 0x003a }
        L_0x002e:
            java.lang.String r2 = "CameraEncoder"
            java.lang.String r4 = "waiting lock~~~~~~~ooooooooooo"
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InterruptedException -> 0x003a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r2, r4, r5)     // Catch:{ InterruptedException -> 0x003a }
        L_0x0038:
            monitor-exit(r3)     // Catch:{ all -> 0x0046 }
        L_0x0039:
            return
        L_0x003a:
            r0 = move-exception
            java.lang.String r2 = "CameraEncoder"
            java.lang.String r4 = ""
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0046 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r2, r0, r4, r5)     // Catch:{ all -> 0x0046 }
            goto L_0x0038
        L_0x0046:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0046 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.encode.CameraEncoder.stopRecordForWait():void");
    }

    /* access modifiers changed from: protected */
    public void handleGLMessage(Message msg) {
        throw new RuntimeException("Unexpected msg what=" + msg.what);
    }

    private void setExceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                Logger.E(CameraEncoder.TAG, "uncaughtException###, thread name:" + thread.getName() + ", thread id:" + thread.getId() + ",ex:" + ex.getMessage(), new Object[0]);
                StackTraceElement[] elements = ex.getStackTrace();
                StringBuilder sb = new StringBuilder();
                for (StackTraceElement stackTraceElement : elements) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
                Logger.E(CameraEncoder.TAG, "exception stack:\n" + sb.toString(), new Object[0]);
                CameraEncoder.this.handleRelease(true);
            }
        });
    }

    private boolean isNeedLostFrames(long pts) {
        int interval = 40000;
        if (this.mConfig.mType == 1) {
            interval = 50000;
        }
        if (this.mFirstTs == 0) {
            this.mFirstTs = pts;
        } else if ((pts - this.mFirstTs) - this.mLastTs < ((long) interval)) {
            return true;
        } else {
            this.mLastTs += (long) interval;
        }
        return false;
    }

    private boolean isNeedLog() {
        if (this.mRenderCount % 30 != 0) {
            this.mRenderCount++;
            return false;
        }
        this.mRenderCount = 0;
        this.mRenderCount++;
        return true;
    }

    public boolean isBeautyCameraEncoder() {
        return false;
    }

    public void setBeautyValue(int value) {
    }
}
