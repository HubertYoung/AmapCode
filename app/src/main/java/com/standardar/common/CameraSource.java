package com.standardar.common;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Range;
import android.util.Size;
import android.util.SizeF;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.zoloz.toyger.bean.Config;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class CameraSource {
    public static final int ARSERVICE_JAVA = 41;
    public static final int AUTO_SELECT_ENGINE = 0;
    public static final int CAMERA_DIRECTION_BACK = 1;
    public static final int CAMERA_DIRECTION_DEFAULT = 0;
    public static final int CAMERA_DIRECTION_DOUBLE = 3;
    public static final int CAMERA_DIRECTION_FRONT = 2;
    private static final int CAMERA_SOURCE_ANR = 0;
    private static final int CAMERA_SOURCE_ANR_TIME = 1000;
    public static final int HAL_CAMERA_ENGINE = 4;
    public static final int JAVA_CAMERA2_ENGINE = 1;
    public static final int MULT_STREAM = 16;
    public static final int NDK_CAMERA2_ENGINE = 2;
    public static final int SINGLE_STREAM = 8;
    private static final int STATE_CLOSE = 2;
    private static final int STATE_IDLE = 3;
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAITING_LOCK = 1;
    private static byte[] mImageBuf;
    private static CameraSource mInstance;
    private static Object mInstanceLock = new Object();
    private boolean isCamerOpened = false;
    /* access modifiers changed from: private */
    public boolean isPreviewing = false;
    /* access modifiers changed from: private */
    public Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    /* access modifiers changed from: private */
    public Object mCallbackLock = new Object();
    /* access modifiers changed from: private */
    public CameraDevice mCameraDevice;
    public int mCameraDirection;
    private CameraManager mCameraManager;
    /* access modifiers changed from: private */
    public Set<ICameraNotifyCallback> mCameraNotifiers = new HashSet();
    /* access modifiers changed from: private */
    public Semaphore mCameraOpenCloseLock = new Semaphore(1);
    private int mCameraOrientation = 0;
    CameraSourceHandle mCameraSourceHandler;
    private HandlerThread mCameraSourceThread;
    /* access modifiers changed from: private */
    public CaptureCallback mCaptureCallback = new CaptureCallback() {
        private void process(CaptureResult captureResult) {
            if (CameraSource.this.mState == 0) {
                CameraSource.this.mCurExposureTime = (Long) captureResult.get(CaptureResult.SENSOR_EXPOSURE_TIME);
                StringBuilder sb = new StringBuilder("image exposure time ");
                sb.append(CameraSource.this.mCurExposureTime);
                Util.LOGD(sb.toString());
            }
        }

        public void onCaptureProgressed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureResult captureResult) {
            super.onCaptureProgressed(cameraCaptureSession, captureRequest, captureResult);
        }

        public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
            super.onCaptureCompleted(cameraCaptureSession, captureRequest, totalCaptureResult);
            process(totalCaptureResult);
        }
    };
    /* access modifiers changed from: private */
    public CameraCaptureSession mCaptureSession;
    private Context mContext;
    private int mCount = 0;
    /* access modifiers changed from: private */
    public Long mCurExposureTime = Long.valueOf(20000000);
    private double mElapsetime = 0.0d;
    private long mEnginePtr = 0;
    private int mEngineType = 0;
    private double mFPS = 0.0d;
    /* access modifiers changed from: private */
    public Range<Integer> mFPSRange = new Range<>(Integer.valueOf(30), Integer.valueOf(30));
    private Long mFixExposureTime = Long.valueOf(10000000);
    private float mFovH = 0.0f;
    private float mFovV = 0.0f;
    /* access modifiers changed from: private */
    public boolean mFrameAvail = false;
    /* access modifiers changed from: private */
    public Object mFrameAvailLock = new Object();
    private ImageReader mImageReader;
    public AtomicBoolean mImageReaderActive = new AtomicBoolean(false);
    private Long mMaxExposureTime = Long.valueOf(0);
    private float mMaxFocalLength;
    private Integer mMaxSensitivity = Integer.valueOf(0);
    private Long mMinExposureTime = Long.valueOf(0);
    private Integer mMinSensitivity = Integer.valueOf(0);
    private OnFrameAvailableListener mOnFrameLinstener = new OnFrameAvailableListener() {
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            synchronized (CameraSource.this.mFrameAvailLock) {
                CameraSource.this.mFrameAvail = true;
            }
        }
    };
    private OnImageAvailableListener mOnImageAvailableListenerClient = new OnImageAvailableListener() {
        public void onImageAvailable(ImageReader imageReader) {
            if (!CameraSource.this.isPreviewing || !CameraSource.this.mImageReaderActive.get()) {
                consumeImage(imageReader);
                return;
            }
            Image acquireLatestImage = imageReader.acquireLatestImage();
            if (acquireLatestImage != null) {
                byte[] access$1000 = CameraSource.YUV420toNV21(acquireLatestImage);
                synchronized (CameraSource.this.mCallbackLock) {
                    StringBuilder sb = new StringBuilder(" image timestamp ");
                    sb.append(acquireLatestImage.getTimestamp());
                    Util.LOGD(sb.toString());
                    for (ICameraNotifyCallback onCameraNotify : CameraSource.this.mCameraNotifiers) {
                        onCameraNotify.onCameraNotify(access$1000, CameraSource.this.mCurExposureTime != null ? CameraSource.this.mCurExposureTime.longValue() : 0, CameraSource.this.mUseSensorTimestamp ? acquireLatestImage.getTimestamp() : SystemClock.elapsedRealtimeNanos());
                    }
                }
                acquireLatestImage.close();
            }
        }

        private void consumeImage(ImageReader imageReader) {
            Image acquireLatestImage = imageReader.acquireLatestImage();
            if (acquireLatestImage != null) {
                acquireLatestImage.close();
            }
        }
    };
    private OnImageAvailableListener mOnImageAvailableListenerMul = new OnImageAvailableListener() {
        public void onImageAvailable(ImageReader imageReader) {
            if (CameraSource.this.isPreviewing) {
                Image acquireLatestImage = imageReader.acquireLatestImage();
                if (acquireLatestImage != null) {
                    CameraSource.this.arUpdate(CameraSource.this.mSessionPtr);
                    CameraSource.this.onPreviewFrame(CameraSource.this.getImageGrayByte(acquireLatestImage), CameraSource.this.mUseSensorTimestamp ? acquireLatestImage.getTimestamp() : SystemClock.elapsedRealtimeNanos());
                    acquireLatestImage.close();
                }
            }
        }
    };
    private OnImageAvailableListener mOnImageAvailableListenerSingle = new OnImageAvailableListener() {
        public void onImageAvailable(ImageReader imageReader) {
            if (CameraSource.this.isPreviewing) {
                Image acquireLatestImage = imageReader.acquireLatestImage();
                if (acquireLatestImage != null) {
                    StringBuilder sb = new StringBuilder(" image timestamp ");
                    sb.append(acquireLatestImage.getTimestamp());
                    Util.LOGD(sb.toString());
                    CameraSource.this.onPreviewFrame(CameraSource.YUV420toNV21(acquireLatestImage), CameraSource.this.mUseSensorTimestamp ? acquireLatestImage.getTimestamp() : SystemClock.elapsedRealtimeNanos());
                    acquireLatestImage.close();
                }
            }
        }
    };
    private int mPreHeight = Config.HQ_IMAGE_WIDTH;
    private int mPreWidth = 640;
    /* access modifiers changed from: private */
    public CaptureRequest mPreviewRequest;
    /* access modifiers changed from: private */
    public Builder mPreviewRequestBuilder;
    private int mSLAMHeight = 720;
    private int mSLAMWidth = 1280;
    /* access modifiers changed from: private */
    public long mSessionPtr = 0;
    private long mStarttime = 0;
    /* access modifiers changed from: private */
    public int mState = -1;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private int mTextureId = 0;
    /* access modifiers changed from: private */
    public boolean mUseSensorTimestamp = true;

    public enum CameraOrientation {
        ST_CLOCKWISE_ROTATE_0,
        ST_CLOCKWISE_ROTATE_90,
        ST_CLOCKWISE_ROTATE_180,
        ST_CLOCKWISE_ROTATE_270
    }

    class CameraSourceHandle extends Handler {
        public CameraSourceHandle(Looper looper) {
            super(looper);
        }

        public void dispatchMessage(Message message) {
            super.dispatchMessage(message);
            if (message.what == 0) {
                Util.LOGW("ARService response time exceed 1000 ms");
                CameraSource.this.mImageReaderActive.set(true);
            }
        }
    }

    public interface ICameraNotifyCallback {
        void onCameraNotify(byte[] bArr, long j, long j2);
    }

    private double adjustTime(long j) {
        return ((double) j) * 1.0E-9d;
    }

    private native void arProcessFrameNoImu(long j, byte[] bArr, int i, double d, double d2);

    private native void arSetDisplaySize(long j, int i, int i2);

    private native void arSetFov(long j, float f, float f2);

    private native void arSetSLAMSize(long j, int i, int i2);

    private native void arSetSupportPreviewSize(long j, String str);

    /* access modifiers changed from: private */
    public native void arUpdate(long j);

    public void setImageReaderActive(boolean z) {
        if (this.mCameraSourceHandler != null) {
            this.mImageReaderActive.set(z);
            if (!z) {
                this.mCameraSourceHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                this.mCameraSourceHandler.removeMessages(0);
            }
        }
    }

    public void registerCallback(ICameraNotifyCallback iCameraNotifyCallback) {
        synchronized (this.mCallbackLock) {
            if (iCameraNotifyCallback != null) {
                try {
                    this.mCameraNotifiers.add(iCameraNotifyCallback);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void unregisterCallback(ICameraNotifyCallback iCameraNotifyCallback) {
        synchronized (this.mCallbackLock) {
            if (iCameraNotifyCallback != null) {
                try {
                    this.mCameraNotifiers.remove(iCameraNotifyCallback);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static CameraSource getInstance(Context context, long j, long j2) {
        CameraSource cameraSource;
        synchronized (mInstanceLock) {
            if (mInstance == null) {
                CameraSource cameraSource2 = new CameraSource(context, j, j2);
                mInstance = cameraSource2;
            } else {
                mInstance.setContext(context);
                mInstance.setSessionPtr(j);
                mInstance.setEnginePtr(j2);
            }
            cameraSource = mInstance;
        }
        return cameraSource;
    }

    private void startCameraSourceThread() {
        if (this.mCameraSourceThread == null) {
            this.mCameraSourceThread = new HandlerThread("camerasource");
            this.mCameraSourceThread.start();
            this.mCameraSourceThread.setPriority(10);
            this.mCameraSourceHandler = new CameraSourceHandle(this.mCameraSourceThread.getLooper());
        }
    }

    private void stopCameraSourceThread() {
        if (this.mCameraSourceThread != null) {
            this.mCameraSourceThread.quitSafely();
            try {
                this.mCameraSourceThread.join();
                this.mCameraSourceThread = null;
                this.mCameraSourceHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startBackgroudThread() {
        if (this.mBackgroundThread == null) {
            this.mBackgroundThread = new HandlerThread("camerabackgroud");
            this.mBackgroundThread.start();
            this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
        }
    }

    private void stopBackgroudThread() {
        if (this.mBackgroundThread != null) {
            this.mBackgroundThread.quitSafely();
            try {
                this.mBackgroundThread.join();
                this.mBackgroundThread = null;
                this.mBackgroundHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void exposureManual() {
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(0));
        this.mPreviewRequestBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, this.mFixExposureTime);
        this.mPreviewRequestBuilder.set(CaptureRequest.SENSOR_SENSITIVITY, Integer.valueOf(this.mMinSensitivity.intValue() + (((this.mMaxSensitivity.intValue() - this.mMinSensitivity.intValue()) * 10) / 100)));
    }

    public CameraSource(Context context, long j, long j2) {
        this.mContext = context;
        this.mSessionPtr = j;
        this.mEnginePtr = j2;
    }

    public void setSessionPtr(long j) {
        this.mSessionPtr = j;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setEnginePtr(long j) {
        this.mEnginePtr = j;
    }

    public boolean isOpen() {
        return this.isCamerOpened;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0049, code lost:
        r8 = (float[]) r5.get(android.hardware.camera2.CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        r0 = new java.lang.StringBuilder("available focal lengths: ");
        r0.append(java.util.Arrays.toString(r8));
        com.standardar.common.Util.LOGI(r0.toString());
        r7.mMaxFocalLength = 0.0f;
        r0 = r8.length;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006a, code lost:
        if (r2 >= r0) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x006c, code lost:
        r1 = r8[r2];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0072, code lost:
        if (r1 <= r7.mMaxFocalLength) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0074, code lost:
        r7.mMaxFocalLength = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0076, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0079, code lost:
        r8 = (android.util.Range) r5.get(android.hardware.camera2.CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0081, code lost:
        if (r8 == null) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0083, code lost:
        r7.mMaxExposureTime = (java.lang.Long) r8.getUpper();
        r7.mMinExposureTime = (java.lang.Long) r8.getLower();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0093, code lost:
        r8 = (android.util.Range) r5.get(android.hardware.camera2.CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009b, code lost:
        if (r8 == null) goto L_0x00ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x009d, code lost:
        r7.mMaxSensitivity = (java.lang.Integer) r8.getUpper();
        r7.mMinSensitivity = (java.lang.Integer) r8.getLower();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b7, code lost:
        if (r7.mCameraOpenCloseLock.tryAcquire(2500, java.util.concurrent.TimeUnit.MILLISECONDS) != false) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c0, code lost:
        throw new java.lang.RuntimeException("time out waiting to lock camera opening");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c1, code lost:
        r7.mCameraManager.openCamera(r4, new com.standardar.common.CameraSource.AnonymousClass2(r7), r7.mBackgroundHandler);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ce, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r8.printStackTrace();
     */
    @android.annotation.SuppressLint({"MissingPermission"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void openCamera2(int r8) {
        /*
            r7 = this;
            android.hardware.camera2.CameraManager r0 = r7.mCameraManager
            if (r0 != 0) goto L_0x001e
            android.content.Context r0 = r7.mContext
            if (r0 == 0) goto L_0x001e
            android.content.Context r0 = r7.mContext
            java.lang.String r1 = "camera"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.hardware.camera2.CameraManager r0 = (android.hardware.camera2.CameraManager) r0
            r7.mCameraManager = r0
            android.hardware.camera2.CameraManager r0 = r7.mCameraManager
            if (r0 != 0) goto L_0x001e
            java.lang.String r8 = "can not get camera service"
            com.standardar.common.Util.LOGE(r8)
            return
        L_0x001e:
            android.hardware.camera2.CameraManager r0 = r7.mCameraManager     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.String[] r0 = r0.getCameraIdList()     // Catch:{ CameraAccessException -> 0x00d7 }
            int r1 = r0.length     // Catch:{ CameraAccessException -> 0x00d7 }
            r2 = 0
            r3 = 0
        L_0x0027:
            if (r3 >= r1) goto L_0x00dc
            r4 = r0[r3]     // Catch:{ CameraAccessException -> 0x00d7 }
            android.hardware.camera2.CameraManager r5 = r7.mCameraManager     // Catch:{ CameraAccessException -> 0x00d7 }
            android.hardware.camera2.CameraCharacteristics r5 = r5.getCameraCharacteristics(r4)     // Catch:{ CameraAccessException -> 0x00d7 }
            android.hardware.camera2.CameraCharacteristics$Key r6 = android.hardware.camera2.CameraCharacteristics.LENS_FACING     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Object r6 = r5.get(r6)     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Integer r6 = (java.lang.Integer) r6     // Catch:{ CameraAccessException -> 0x00d7 }
            if (r6 == 0) goto L_0x00d3
            android.hardware.camera2.CameraCharacteristics$Key r6 = android.hardware.camera2.CameraCharacteristics.LENS_FACING     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Object r6 = r5.get(r6)     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Integer r6 = (java.lang.Integer) r6     // Catch:{ CameraAccessException -> 0x00d7 }
            int r6 = r6.intValue()     // Catch:{ CameraAccessException -> 0x00d7 }
            if (r6 != r8) goto L_0x00d3
            android.hardware.camera2.CameraCharacteristics$Key r8 = android.hardware.camera2.CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Object r8 = r5.get(r8)     // Catch:{ CameraAccessException -> 0x00d7 }
            float[] r8 = (float[]) r8     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.String r1 = "available focal lengths: "
            r0.<init>(r1)     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.String r1 = java.util.Arrays.toString(r8)     // Catch:{ CameraAccessException -> 0x00d7 }
            r0.append(r1)     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.String r0 = r0.toString()     // Catch:{ CameraAccessException -> 0x00d7 }
            com.standardar.common.Util.LOGI(r0)     // Catch:{ CameraAccessException -> 0x00d7 }
            r0 = 0
            r7.mMaxFocalLength = r0     // Catch:{ CameraAccessException -> 0x00d7 }
            int r0 = r8.length     // Catch:{ CameraAccessException -> 0x00d7 }
        L_0x006a:
            if (r2 >= r0) goto L_0x0079
            r1 = r8[r2]     // Catch:{ CameraAccessException -> 0x00d7 }
            float r3 = r7.mMaxFocalLength     // Catch:{ CameraAccessException -> 0x00d7 }
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x0076
            r7.mMaxFocalLength = r1     // Catch:{ CameraAccessException -> 0x00d7 }
        L_0x0076:
            int r2 = r2 + 1
            goto L_0x006a
        L_0x0079:
            android.hardware.camera2.CameraCharacteristics$Key r8 = android.hardware.camera2.CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Object r8 = r5.get(r8)     // Catch:{ CameraAccessException -> 0x00d7 }
            android.util.Range r8 = (android.util.Range) r8     // Catch:{ CameraAccessException -> 0x00d7 }
            if (r8 == 0) goto L_0x0093
            java.lang.Comparable r0 = r8.getUpper()     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ CameraAccessException -> 0x00d7 }
            r7.mMaxExposureTime = r0     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Comparable r8 = r8.getLower()     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Long r8 = (java.lang.Long) r8     // Catch:{ CameraAccessException -> 0x00d7 }
            r7.mMinExposureTime = r8     // Catch:{ CameraAccessException -> 0x00d7 }
        L_0x0093:
            android.hardware.camera2.CameraCharacteristics$Key r8 = android.hardware.camera2.CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Object r8 = r5.get(r8)     // Catch:{ CameraAccessException -> 0x00d7 }
            android.util.Range r8 = (android.util.Range) r8     // Catch:{ CameraAccessException -> 0x00d7 }
            if (r8 == 0) goto L_0x00ad
            java.lang.Comparable r0 = r8.getUpper()     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ CameraAccessException -> 0x00d7 }
            r7.mMaxSensitivity = r0     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Comparable r8 = r8.getLower()     // Catch:{ CameraAccessException -> 0x00d7 }
            java.lang.Integer r8 = (java.lang.Integer) r8     // Catch:{ CameraAccessException -> 0x00d7 }
            r7.mMinSensitivity = r8     // Catch:{ CameraAccessException -> 0x00d7 }
        L_0x00ad:
            java.util.concurrent.Semaphore r8 = r7.mCameraOpenCloseLock     // Catch:{ InterruptedException -> 0x00ce }
            r0 = 2500(0x9c4, double:1.235E-320)
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x00ce }
            boolean r8 = r8.tryAcquire(r0, r2)     // Catch:{ InterruptedException -> 0x00ce }
            if (r8 != 0) goto L_0x00c1
            java.lang.RuntimeException r8 = new java.lang.RuntimeException     // Catch:{ InterruptedException -> 0x00ce }
            java.lang.String r0 = "time out waiting to lock camera opening"
            r8.<init>(r0)     // Catch:{ InterruptedException -> 0x00ce }
            throw r8     // Catch:{ InterruptedException -> 0x00ce }
        L_0x00c1:
            android.hardware.camera2.CameraManager r8 = r7.mCameraManager     // Catch:{ InterruptedException -> 0x00ce }
            com.standardar.common.CameraSource$2 r0 = new com.standardar.common.CameraSource$2     // Catch:{ InterruptedException -> 0x00ce }
            r0.<init>()     // Catch:{ InterruptedException -> 0x00ce }
            android.os.Handler r1 = r7.mBackgroundHandler     // Catch:{ InterruptedException -> 0x00ce }
            r8.openCamera(r4, r0, r1)     // Catch:{ InterruptedException -> 0x00ce }
            goto L_0x00dc
        L_0x00ce:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ CameraAccessException -> 0x00d7 }
            goto L_0x00dc
        L_0x00d3:
            int r3 = r3 + 1
            goto L_0x0027
        L_0x00d7:
            java.lang.String r8 = "open camera failed Can not access camera"
            com.standardar.common.Util.LOGE(r8)
        L_0x00dc:
            r8 = 1
            r7.isCamerOpened = r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.standardar.common.CameraSource.openCamera2(int):void");
    }

    public void setTextureId(int i) {
        this.mTextureId = i;
        StringBuilder sb = new StringBuilder("set texture id ");
        sb.append(this.mTextureId);
        Util.LOGI(sb.toString());
    }

    public int openCamera(int i) {
        closeCamera();
        this.mEngineType = i;
        startBackgroudThread();
        startCameraSourceThread();
        if (this.mCameraManager == null && this.mContext != null) {
            this.mCameraManager = (CameraManager) this.mContext.getSystemService(WalletTinyappUtils.CONST_SCOPE_CAMERA);
            if (this.mCameraManager == null) {
                Util.LOGE("can not get camera service");
                return -1;
            }
        }
        openCamera2(1);
        this.mCameraDirection = 1;
        arSetSLAMSize(this.mEnginePtr, this.mSLAMWidth, this.mSLAMHeight);
        setSupportSizeStr();
        setDisplaySize();
        return 0;
    }

    private void setDisplaySize() {
        Display defaultDisplay = ((WindowManager) this.mContext.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        if (point.x > point.y) {
            arSetDisplaySize(this.mEnginePtr, point.x, point.y);
        } else {
            arSetDisplaySize(this.mEnginePtr, point.y, point.x);
        }
    }

    private void setSupportSizeStr() {
        List<Size> list = (this.mEngineType & 16) != 0 ? getSupportsSizes(34) : (this.mEngineType & 8) != 0 ? getSupportsSizes(35) : null;
        if (list == null) {
            StringBuilder sb = new StringBuilder("engine type is unknown ");
            sb.append(this.mEngineType);
            Util.LOGW(sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        for (Size next : list) {
            sb2.append(next.getWidth());
            sb2.append(DictionaryKeys.CTRLXY_X);
            sb2.append(next.getHeight());
            sb2.append(",");
        }
        arSetSupportPreviewSize(this.mEnginePtr, sb2.deleteCharAt(sb2.length() - 1).toString());
    }

    public CameraOrientation getCameraOrientation() {
        CameraOrientation cameraOrientation = CameraOrientation.ST_CLOCKWISE_ROTATE_90;
        if (!isOpen()) {
            return cameraOrientation;
        }
        int i = this.mCameraOrientation;
        if (i == 0) {
            cameraOrientation = CameraOrientation.ST_CLOCKWISE_ROTATE_0;
        } else if (i == 90) {
            cameraOrientation = CameraOrientation.ST_CLOCKWISE_ROTATE_90;
        } else if (i == 180) {
            cameraOrientation = CameraOrientation.ST_CLOCKWISE_ROTATE_180;
        } else if (i == 270) {
            cameraOrientation = CameraOrientation.ST_CLOCKWISE_ROTATE_270;
        }
        return cameraOrientation;
    }

    public List<Size> getSupportsSizes(int i) {
        try {
            this.mCameraOpenCloseLock.acquire();
            if (!(this.mCameraDevice == null || this.mCameraManager == null)) {
                try {
                    List<Size> asList = Arrays.asList(((StreamConfigurationMap) this.mCameraManager.getCameraCharacteristics(this.mCameraDevice.getId()).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(i));
                    this.mCameraOpenCloseLock.release();
                    return asList;
                } catch (CameraAccessException unused) {
                    Util.LOGE("get supported failed can not access camera");
                    this.mCameraOpenCloseLock.release();
                    return null;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            this.mCameraOpenCloseLock.release();
            throw th;
        }
        this.mCameraOpenCloseLock.release();
        return null;
    }

    public void setPreviewSize(int i, int i2) {
        this.mPreWidth = i;
        this.mPreHeight = i2;
        calcFov();
        arSetFov(this.mEnginePtr, getFovH(), getFovV());
    }

    /* access modifiers changed from: private */
    public static byte[] YUV420toNV21(Image image) {
        if (image == null || image.getFormat() != 35) {
            Util.LOGE("yuv420ToNv21, only support YUV_420_888");
            return null;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        Rect cropRect = image.getCropRect();
        int format = image.getFormat();
        int width2 = cropRect.width();
        int height2 = cropRect.height();
        Plane[] planes = image.getPlanes();
        int bitsPerPixel = ((width2 * height2) * ImageFormat.getBitsPerPixel(format)) / 8;
        if (mImageBuf == null || mImageBuf.length != bitsPerPixel) {
            mImageBuf = new byte[bitsPerPixel];
        }
        int i = 0;
        ByteBuffer buffer = planes[0].getBuffer();
        ByteBuffer buffer2 = planes[2].getBuffer();
        if (image.getPlanes()[0].getRowStride() != image.getWidth()) {
            int rowStride = image.getPlanes()[0].getRowStride();
            int i2 = 0;
            int i3 = 0;
            while (i2 < height) {
                buffer.get(mImageBuf, i3, width);
                if (i2 != height - 1) {
                    buffer.position(buffer.position() + (rowStride - width));
                }
                i2++;
                i3 += width;
            }
            while (true) {
                int i4 = height / 2;
                if (i >= i4) {
                    break;
                }
                if (i != i4 - 1) {
                    buffer2.get(mImageBuf, i3, width);
                    buffer2.position(buffer2.position() + (rowStride - width));
                } else {
                    buffer2.get(mImageBuf, i3, width - 1);
                }
                i++;
                i3 += width;
            }
        } else {
            buffer.get(mImageBuf, 0, buffer.remaining());
            buffer2.get(mImageBuf, buffer.position(), buffer2.remaining());
        }
        return mImageBuf;
    }

    /* access modifiers changed from: private */
    public byte[] getImageGrayByte(Image image) {
        Rect cropRect = image.getCropRect();
        int width = cropRect.width();
        int height = cropRect.height();
        Plane[] planes = image.getPlanes();
        byte[] bArr = new byte[(width * height)];
        ByteBuffer buffer = planes[0].getBuffer();
        int rowStride = planes[0].getRowStride();
        buffer.position((cropRect.top * rowStride) + (planes[0].getPixelStride() * cropRect.left));
        int i = 0;
        for (int i2 = 0; i2 < height; i2++) {
            buffer.get(bArr, i, width);
            i += width;
            if (i2 < height - 1) {
                buffer.position((buffer.position() + rowStride) - width);
            }
        }
        return bArr;
    }

    public void startPreview(int i) {
        if (i == 41) {
            startPreviewClient();
        } else if ((i & 8) != 0) {
            startPreviewSingle();
        } else {
            if ((i & 16) != 0) {
                startPreviewMul();
            }
        }
    }

    private void startPreviewSingle() {
        Util.LOGI("start preview single");
        try {
            this.mCameraOpenCloseLock.acquire();
            if (this.mCameraDevice == null) {
                this.mCameraOpenCloseLock.release();
            } else if (this.isPreviewing) {
                this.mCameraOpenCloseLock.release();
            } else {
                this.mImageReader = ImageReader.newInstance(this.mPreWidth, this.mPreHeight, 35, 5);
                this.mImageReader.setOnImageAvailableListener(this.mOnImageAvailableListenerSingle, this.mBackgroundHandler);
                cameraReadStart(Arrays.asList(new Surface[]{this.mImageReader.getSurface()}));
                this.isPreviewing = true;
                this.mCameraOpenCloseLock.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            this.mCameraOpenCloseLock.release();
            throw th;
        }
    }

    private void startPreviewMul() {
        Util.LOGI("start preview mul");
        try {
            this.mCameraOpenCloseLock.acquire();
            if (this.mCameraDevice != null) {
                if (this.mTextureId != 0) {
                    if (this.isPreviewing) {
                        this.mCameraOpenCloseLock.release();
                        return;
                    }
                    this.mImageReader = ImageReader.newInstance(this.mSLAMWidth, this.mSLAMHeight, 35, 5);
                    this.mImageReader.setOnImageAvailableListener(this.mOnImageAvailableListenerMul, this.mBackgroundHandler);
                    this.mSurfaceTexture = new SurfaceTexture(this.mTextureId);
                    this.mSurfaceTexture.setOnFrameAvailableListener(this.mOnFrameLinstener);
                    this.mSurfaceTexture.setDefaultBufferSize(this.mPreWidth, this.mPreHeight);
                    this.mSurface = new Surface(this.mSurfaceTexture);
                    cameraReadStart(Arrays.asList(new Surface[]{this.mSurface, this.mImageReader.getSurface()}));
                    this.isPreviewing = true;
                    this.mCameraOpenCloseLock.release();
                    return;
                }
            }
            StringBuilder sb = new StringBuilder("camera device ");
            sb.append(this.mCameraDevice == null ? "null" : "not null");
            sb.append(",texid:");
            sb.append(this.mTextureId);
            Util.LOGW(sb.toString());
            this.mCameraOpenCloseLock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            this.mCameraOpenCloseLock.release();
            throw th;
        }
    }

    private void startPreviewClient() {
        Util.LOGI("start preview for remote service");
        try {
            this.mCameraOpenCloseLock.acquire();
            if (this.mCameraDevice == null) {
                StringBuilder sb = new StringBuilder("camera device ");
                sb.append(this.mCameraDevice == null ? "null" : "not null");
                Util.LOGW(sb.toString());
                this.mCameraOpenCloseLock.release();
            } else if (this.isPreviewing) {
                this.mCameraOpenCloseLock.release();
            } else {
                this.mImageReader = ImageReader.newInstance(this.mPreWidth, this.mPreHeight, 35, 5);
                this.mImageReader.setOnImageAvailableListener(this.mOnImageAvailableListenerClient, this.mBackgroundHandler);
                cameraReadStart(Arrays.asList(new Surface[]{this.mImageReader.getSurface()}));
                this.isPreviewing = true;
                this.mCameraOpenCloseLock.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            this.mCameraOpenCloseLock.release();
            throw th;
        }
    }

    public void update() {
        synchronized (this.mFrameAvailLock) {
            if (this.mFrameAvail && this.mSurfaceTexture != null) {
                this.mSurfaceTexture.updateTexImage();
                this.mFrameAvail = false;
            }
        }
    }

    private void countFPS() {
        if (this.mCount == 0) {
            this.mStarttime = SystemClock.elapsedRealtimeNanos();
        } else if (this.mCount == 50) {
            this.mCount = 0;
            this.mElapsetime = (double) (SystemClock.elapsedRealtimeNanos() - this.mStarttime);
            this.mFPS = 50.0d / (this.mElapsetime / 1.0E9d);
            return;
        }
        this.mCount++;
    }

    public void stop() {
        stopPreview();
        closeCamera();
    }

    private void cameraReadStart(List<Surface> list) {
        if (list.isEmpty()) {
            Util.LOGW("Empty surface list");
        } else if (this.isPreviewing) {
            Util.LOGW("Camera is already previewing");
        } else {
            try {
                this.mPreviewRequestBuilder = this.mCameraDevice.createCaptureRequest(1);
                for (Surface addTarget : list) {
                    this.mPreviewRequestBuilder.addTarget(addTarget);
                }
                this.mCameraDevice.createCaptureSession(list, new StateCallback() {
                    public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                        CameraSource.this.mCaptureSession = cameraCaptureSession;
                        try {
                            CameraSource.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(0));
                            CameraSource.this.mPreviewRequestBuilder.set(CaptureRequest.LENS_FOCUS_DISTANCE, Float.valueOf(0.0f));
                            CameraSource.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, CameraSource.this.mFPSRange);
                            CameraSource.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
                            CameraSource.this.mState = 0;
                            CameraSource.this.mPreviewRequestBuilder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, Integer.valueOf(0));
                            CameraSource.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, Integer.valueOf(0));
                            CameraSource.this.mPreviewRequestBuilder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE, Integer.valueOf(0));
                            CameraSource.this.mPreviewRequest = CameraSource.this.mPreviewRequestBuilder.build();
                            CameraSource.this.mCaptureSession.setRepeatingRequest(CameraSource.this.mPreviewRequest, CameraSource.this.mCaptureCallback, CameraSource.this.mBackgroundHandler);
                        } catch (CameraAccessException unused) {
                            Util.LOGW("setRepeatingRequest failed");
                        }
                    }

                    public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                        cameraCaptureSession.close();
                        Util.LOGE("configure failed");
                    }

                    public void onClosed(CameraCaptureSession cameraCaptureSession) {
                        CameraSource.this.mCaptureSession = null;
                    }
                }, this.mBackgroundHandler);
            } catch (CameraAccessException unused) {
                Util.LOGW("Create session failed can not access camera.");
            }
        }
    }

    public int stopPreview() {
        this.isPreviewing = false;
        return 0;
    }

    public int closeCamera() {
        Util.LOGI("close camera");
        if (this.isPreviewing) {
            stopPreview();
        }
        try {
            this.mCameraOpenCloseLock.acquire();
            this.mState = 2;
            if (this.mCaptureSession != null) {
                this.mCaptureSession.stopRepeating();
                this.mCaptureSession.abortCaptures();
                this.mCaptureSession.close();
                this.mCaptureSession = null;
            }
            if (this.mCameraDevice != null) {
                this.mCameraDevice.close();
                this.mCameraDevice = null;
            }
            if (this.mImageReader != null) {
                this.mImageReader.close();
                this.mImageReader = null;
            }
            if (this.mSurfaceTexture != null) {
                this.mSurfaceTexture.setOnFrameAvailableListener(null);
                this.mSurfaceTexture.release();
                this.mSurfaceTexture = null;
            }
            if (this.mSurface != null) {
                this.mSurface.release();
                this.mSurface = null;
            }
        } catch (InterruptedException unused) {
            Util.LOGE("lock acquire interrupt");
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            this.mCameraOpenCloseLock.release();
            throw th;
        }
        this.mCameraOpenCloseLock.release();
        stopBackgroudThread();
        stopCameraSourceThread();
        this.isCamerOpened = false;
        return 0;
    }

    /* access modifiers changed from: private */
    public void onPreviewFrame(byte[] bArr, long j) {
        arProcessFrameNoImu(this.mEnginePtr, bArr, bArr.length, adjustTime(this.mCurExposureTime != null ? this.mCurExposureTime.longValue() : 0), adjustTime(j));
    }

    private void calcFov() {
        if (this.mContext != null) {
            try {
                CameraManager cameraManager = (CameraManager) this.mContext.getSystemService(WalletTinyappUtils.CONST_SCOPE_CAMERA);
                this.mCameraOpenCloseLock.acquire();
                if (this.mCameraDevice == null) {
                    Util.LOGE("calc fov failed because camera device is null");
                    this.mCameraOpenCloseLock.release();
                    return;
                }
                SizeF sizeF = (SizeF) cameraManager.getCameraCharacteristics(this.mCameraDevice.getId()).get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
                this.mFovV = (float) (((Math.atan((((double) sizeF.getHeight()) / 2.0d) / ((double) this.mMaxFocalLength)) * 2.0d) * 180.0d) / 3.141592653589793d);
                this.mFovH = (float) (((Math.atan((((double) sizeF.getWidth()) / 2.0d) / ((double) this.mMaxFocalLength)) * 2.0d) * 180.0d) / 3.141592653589793d);
                StringBuilder sb = new StringBuilder("phsical width:");
                sb.append(sizeF.getWidth());
                sb.append(",height:");
                sb.append(sizeF.getHeight());
                sb.append(",fov horizontal:");
                sb.append(this.mFovH);
                sb.append(",fov vertical:");
                sb.append(this.mFovV);
                this.mCameraOpenCloseLock.release();
            } catch (CameraAccessException unused) {
                Util.LOGE("Calc fov failed can not access camera.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                this.mCameraOpenCloseLock.release();
                throw th;
            }
        }
    }

    public float getFovH() {
        return this.mFovH;
    }

    public float getFovV() {
        return this.mFovV;
    }

    public static int getDisplayWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels > displayMetrics.heightPixels ? displayMetrics.widthPixels : displayMetrics.heightPixels;
    }

    public static int getDisplayHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels < displayMetrics.heightPixels ? displayMetrics.widthPixels : displayMetrics.heightPixels;
    }

    public int getSLAMWidth() {
        return this.mSLAMWidth;
    }

    public int getSLAMHeight() {
        return this.mSLAMHeight;
    }

    public int getPreviewWidth() {
        return this.mPreWidth;
    }

    public int getPreviewHeight() {
        return this.mPreHeight;
    }
}
