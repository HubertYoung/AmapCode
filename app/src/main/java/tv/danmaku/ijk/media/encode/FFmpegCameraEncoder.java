package tv.danmaku.ijk.media.encode;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.OrientationDetector;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoDeviceWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoFileManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.beautify.OGJNIWrapper;
import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.streammedia.encode.FFmpegCameraEncoderJni;
import com.alipay.streammedia.encode.RecordVideoResult;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import tv.danmaku.ijk.media.widget.CameraView;

@TargetApi(9)
public class FFmpegCameraEncoder implements PreviewCallback {
    public static final int MAX_FPS_INTERVAL = 40000;
    public static final int MAX_FPS_INTERVAL_LIVE = 50000;
    private static final String TAG = "FFmpegCameraEncoder";
    private boolean bCameraFacingBack = true;
    private boolean hasCheckedPermission;
    private int mBufSize;
    private Camera mCamera;
    private int mCameraFacing = 0;
    private WeakReference<CameraView> mCameraView;
    private Object[] mEventBusArray;
    private boolean mFirstFrameRequest = true;
    private long mFirstTs = 0;
    private int mFormats = 17;
    private long mFrameCount = 0;
    private volatile boolean mIsRecording;
    private long mLastTs = 0;
    private boolean mLiveTsInited = false;
    private FFmpegMuxer mMuxer;
    private int mOrientation;
    private Size mPreviewSize;
    private long mRecordStartTimestamp;
    protected FFmpegSessionConfig mSessionConfig;
    private boolean mUseEventbus = false;
    private boolean mUseRtBeautify = false;
    private byte[] mYuvData;
    private long startTime;

    public FFmpegCameraEncoder(Camera camera, FFmpegSessionConfig sessionConfig, CameraView cameraView, int cameraFace, int rotation) {
        boolean z = false;
        this.mCamera = camera;
        this.mCameraView = new WeakReference<>(cameraView);
        this.mPreviewSize = this.mCamera.getParameters().getPreviewSize();
        setCallbackBuffer(camera);
        this.mSessionConfig = sessionConfig;
        this.mMuxer = this.mSessionConfig.getMuxer();
        this.mCameraFacing = cameraFace;
        this.bCameraFacingBack = this.mCameraFacing == 0 ? true : z;
        this.mOrientation = getOrientation(this.mCameraFacing, rotation);
        if (this.mSessionConfig.mType != 1 && this.mCameraView.get() != null) {
            ((CameraView) this.mCameraView.get()).onAudioStart();
        }
    }

    private int getOrientation(int facing, int rotation) {
        try {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(facing, info);
            Logger.D(TAG, "getOrientation orientation=" + info.orientation + ";facing=" + facing, new Object[0]);
            if (info.orientation > 0 && info.orientation <= 270) {
                return info.orientation;
            }
            if (facing == 1) {
                return 270;
            }
            return 90;
        } catch (Exception e) {
            Logger.E((String) TAG, (Throwable) e, "getOrientation exp rotation=" + rotation, new Object[0]);
            return rotation;
        }
    }

    public void switchCamera(Camera camera, int facing) {
        this.mCamera = camera;
        this.mCameraFacing = facing;
        this.bCameraFacingBack = this.mCameraFacing == 0;
        this.mPreviewSize = this.mCamera.getParameters().getPreviewSize();
        setCallbackBuffer(camera);
        this.mOrientation = getOrientation(facing, this.mOrientation);
    }

    public void setOrientation(int facing) {
        this.mCameraFacing = facing;
        this.bCameraFacingBack = this.mCameraFacing == 0;
        this.mOrientation = getOrientation(facing, this.mOrientation);
    }

    public void enableRtBeautify(boolean enable) {
        this.mUseRtBeautify = enable;
    }

    public void enableEventbus(boolean enable) {
        this.mUseEventbus = enable;
        if (enable && this.mEventBusArray == null) {
            this.mEventBusArray = new Object[4];
        }
    }

    private void setCallbackBuffer(Camera camera) {
        this.mBufSize = ((this.mPreviewSize.width * this.mPreviewSize.height) * ImageFormat.getBitsPerPixel(this.mFormats)) / 8;
        this.mYuvData = new byte[this.mBufSize];
        for (int i = 0; i < 3; i++) {
            camera.addCallbackBuffer(new byte[this.mBufSize]);
        }
        camera.setPreviewCallbackWithBuffer(this);
        this.startTime = System.currentTimeMillis();
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        if (!this.mUseRtBeautify) {
            if (System.currentTimeMillis() - this.startTime > 100 && !this.hasCheckedPermission && VideoDeviceWrapper.dynPermissionCheck()) {
                this.hasCheckedPermission = true;
                if (data != null && data.length > 1) {
                    byte pre = data[0];
                    int testLength = Math.min(10000, data.length - 1);
                    boolean allTheSame = false;
                    for (int i = 1; i < testLength; i += 50) {
                        allTheSame = pre == data[i];
                        if (!allTheSame) {
                            break;
                        }
                        pre = data[i];
                    }
                    if (!(!allTheSame || this.mCameraView == null || this.mCameraView.get() == null)) {
                        ((CameraView) this.mCameraView.get()).notifyOpenCameraError();
                        return;
                    }
                }
            }
            if (camera != this.mCamera) {
                Logger.D(TAG, "drop frame! camera " + camera + " mCamera " + this.mCamera, new Object[0]);
                return;
            }
            int width = this.mPreviewSize.width;
            int height = this.mPreviewSize.height;
            if (this.mUseEventbus) {
                long t = System.currentTimeMillis();
                this.mEventBusArray[0] = Integer.valueOf(width);
                this.mEventBusArray[1] = Integer.valueOf(height);
                this.mEventBusArray[2] = data;
                this.mEventBusArray[3] = Integer.valueOf((this.mCameraView == null || this.mCameraView.get() == null) ? 0 : ((CameraView) this.mCameraView.get()).getDisplayOrientation());
                EventBusManager.getInstance().post(this.mEventBusArray, "xmedia_yuvframe");
                Logger.I(TAG, "post data took " + (System.currentTimeMillis() - t) + RPCDataParser.TIME_MS, new Object[0]);
            }
            if (checkRecordStart()) {
                if (this.mPreviewSize == null) {
                    this.mPreviewSize = camera.getParameters().getPreviewSize();
                }
                handlePreviewFrame(data, width, height);
            }
            if (data.length == this.mBufSize) {
                camera.addCallbackBuffer(data);
            } else {
                camera.addCallbackBuffer(new byte[this.mBufSize]);
            }
        }
    }

    private void handlePreviewFrame(byte[] data, int width, int height) {
        if (this.mFormats == 17) {
            saveCommonFirstFrame(data, width, height);
        }
        long pts = System.nanoTime() / 1000;
        if (!needDropFrame(pts)) {
            long timestamp = pts - this.mRecordStartTimestamp;
            this.mFrameCount++;
            int extra = 0;
            if (this.mSessionConfig.isLandscape() && this.mCameraView.get() != null) {
                extra = OrientationDetector.getInstance(((CameraView) this.mCameraView.get()).getContext()).getDevOrientation() == 90 ? 90 : 270;
                if (!this.bCameraFacingBack) {
                    extra += 180;
                }
            }
            int ret = this.mMuxer.putVideo(data, data.length, timestamp, (this.mOrientation + extra) % 360, !this.bCameraFacingBack);
            notifyGetCount(timestamp, ret);
            if (ret != 0 && ret != 2) {
                Logger.D(TAG, "putVideo ret " + ret, new Object[0]);
                notifyCameraError(ret);
            } else if (this.mCameraView.get() != null) {
                ((CameraView) this.mCameraView.get()).setVideoCurTimeStamp(timestamp);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void saveCommonFirstFrame(byte[] data, int width, int height) {
        handleFirstFrame(data, width, height);
    }

    private void notifyGetCount(long time, int ret) {
        if (this.mCameraView != null && this.mCameraView.get() != null && ((CameraView) this.mCameraView.get()).isLive()) {
            ((CameraView) this.mCameraView.get()).onGetCount(this.mMuxer.getPublishCounter(), time, ret, false);
        }
    }

    public int start() {
        if (!this.mUseRtBeautify) {
            this.mSessionConfig.vPreviewWidth = this.mPreviewSize.width;
            this.mSessionConfig.vPreviewHeight = this.mPreviewSize.height;
        } else {
            this.mSessionConfig.vPreviewWidth = FFmpegSessionConfig.VIDEO_SOFTENCODE_W;
            this.mSessionConfig.vPreviewHeight = 960;
        }
        Logger.D(TAG, "FFmpegCameraEncoder start " + this.mSessionConfig, new Object[0]);
        int ret = this.mMuxer.init(this.mSessionConfig);
        if (ret != 0) {
            Logger.D(TAG, "start ret: " + ret, new Object[0]);
            return VideoUtils.convertMuxInitToRspCode(ret);
        }
        this.mLiveTsInited = false;
        this.mFirstFrameRequest = true;
        this.mIsRecording = true;
        return ret;
    }

    public boolean isRecording() {
        Logger.D(TAG, "isRecording " + this.mIsRecording, new Object[0]);
        return this.mIsRecording;
    }

    public void setIsRecording(boolean isRecording) {
        Logger.D(TAG, "setIsRecording " + isRecording, new Object[0]);
        this.mIsRecording = isRecording;
    }

    public RecordVideoResult stop() {
        RecordVideoResult res;
        if (isRecording()) {
            setIsRecording(false);
            synchronized (this.mMuxer) {
                try {
                    Logger.D(TAG, "total frames:" + this.mFrameCount, new Object[0]);
                    this.mFrameCount = 0;
                    res = this.mMuxer.uninit();
                    Logger.D(TAG, "muxing uninit " + res, new Object[0]);
                }
            }
            return res;
        }
        Logger.D(TAG, "stop when not recording", new Object[0]);
        return null;
    }

    /* access modifiers changed from: private */
    public void saveFrame(byte[] data, int width, int height) {
        long ts = System.currentTimeMillis();
        if (this.mOrientation == 90) {
            VideoHelper.rotateYUV420SPAntiClockwiseDegree90(data, this.mYuvData, width, height);
        } else {
            VideoHelper.rotateYUV420SPClockwiseDegree90(data, this.mYuvData, width, height);
        }
        YuvImage image = new YuvImage(this.mYuvData, 17, height, width, null);
        String jpgpath = VideoFileManager.getInstance().generateThumbPath(this.mSessionConfig.getVideoId() + "_thumb");
        FileOutputStream fos = null;
        ByteArrayOutputStream bos = null;
        try {
            int x = (height - ((width * 9) / 16)) / 2;
            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(jpgpath);
                try {
                    image.compressToJpeg(new Rect(x, 0, height - x, width), 70, bos2);
                    byte[] imageBytes = bos2.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    if (1 == this.mCameraFacing && this.mOrientation != 90) {
                        Matrix matrix = new Matrix();
                        matrix.postScale(-1.0f, 1.0f);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    }
                    if (this.mSessionConfig.rotate != 0) {
                        Matrix matrix2 = new Matrix();
                        matrix2.postRotate((float) this.mSessionConfig.rotate);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix2, false);
                    }
                    bitmap.compress(CompressFormat.JPEG, 80, fileOutputStream);
                    bitmap.recycle();
                    IOUtils.closeQuietly((OutputStream) fileOutputStream);
                    IOUtils.closeQuietly((OutputStream) bos2);
                    ByteArrayOutputStream byteArrayOutputStream = bos2;
                    FileOutputStream fileOutputStream2 = fileOutputStream;
                } catch (Exception e) {
                    e = e;
                    bos = bos2;
                    fos = fileOutputStream;
                    try {
                        Logger.E((String) TAG, (Throwable) e, (String) "saveFrame err", new Object[0]);
                        IOUtils.closeQuietly((OutputStream) fos);
                        IOUtils.closeQuietly((OutputStream) bos);
                        Logger.D(TAG, "saveFrame took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
                    } catch (Throwable th) {
                        th = th;
                        IOUtils.closeQuietly((OutputStream) fos);
                        IOUtils.closeQuietly((OutputStream) bos);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bos = bos2;
                    fos = fileOutputStream;
                    IOUtils.closeQuietly((OutputStream) fos);
                    IOUtils.closeQuietly((OutputStream) bos);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                bos = bos2;
                Logger.E((String) TAG, (Throwable) e, (String) "saveFrame err", new Object[0]);
                IOUtils.closeQuietly((OutputStream) fos);
                IOUtils.closeQuietly((OutputStream) bos);
                Logger.D(TAG, "saveFrame took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
            } catch (Throwable th3) {
                th = th3;
                bos = bos2;
                IOUtils.closeQuietly((OutputStream) fos);
                IOUtils.closeQuietly((OutputStream) bos);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            Logger.E((String) TAG, (Throwable) e, (String) "saveFrame err", new Object[0]);
            IOUtils.closeQuietly((OutputStream) fos);
            IOUtils.closeQuietly((OutputStream) bos);
            Logger.D(TAG, "saveFrame took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
        }
        Logger.D(TAG, "saveFrame took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
    }

    private void notifyCameraError(int ret) {
        if (this.mCameraView != null && this.mCameraView.get() != null) {
            ((CameraView) this.mCameraView.get()).notifyEncodeError(VideoUtils.convertMuxToRspCode(ret));
        }
    }

    private boolean needDropFrame(long pts) {
        int interval;
        if (this.mSessionConfig.mType == 1) {
            interval = 50000;
        } else {
            interval = 40000;
        }
        if (this.mFirstTs == 0) {
            this.mFirstTs = pts;
        } else if ((pts - this.mFirstTs) - this.mLastTs < ((long) interval)) {
            Logger.D(TAG, "drop the frame with pts:" + pts, new Object[0]);
            return true;
        } else {
            this.mLastTs += (long) interval;
        }
        return false;
    }

    public void handlePreviewFrameByteBuffer(int width, int height, int pbo) {
        if (checkRecordStart()) {
            setLiveStartTs();
            long pts = System.nanoTime() / 1000;
            if (!needDropFrame(pts)) {
                if (this.mFirstFrameRequest) {
                    this.mFirstFrameRequest = false;
                    savePBOFirstFrame(width, height, pbo);
                }
                long pts2 = pts - this.mRecordStartTimestamp;
                int ret = FFmpegCameraEncoderJni.glReadPBOJNI(0, 0, width, height, pbo, 0, 90, this.bCameraFacingBack ? 0 : 1, pts2);
                if (ret == 0 || ret == 2) {
                    if (this.mCameraView.get() != null) {
                        ((CameraView) this.mCameraView.get()).setVideoCurTimeStamp(pts2);
                    }
                    this.mFrameCount++;
                    return;
                }
                Logger.D(TAG, "putVideo ret " + ret, new Object[0]);
                if (isRecording()) {
                    notifyCameraError(ret);
                }
            }
        }
    }

    public void handlePreviewFrameEGL(OGJNIWrapper ogWrapper, int width, int height) {
        int i = 1;
        int i2 = 0;
        if (checkRecordStart()) {
            setLiveStartTs();
            long pts = System.nanoTime() / 1000;
            if (!needDropFrame(pts)) {
                long pts2 = pts - this.mRecordStartTimestamp;
                if (this.mFirstFrameRequest) {
                    this.mFirstFrameRequest = false;
                    if (!this.bCameraFacingBack) {
                        i2 = 1;
                    }
                    saveEglFirstFrame(width, height, ogWrapper.getOutputPixels2(90, i2, pts2));
                } else {
                    if (this.bCameraFacingBack) {
                        i = 0;
                    }
                    int ret = ogWrapper.getOutputPixels(90, i, pts2);
                    if (ret != 0 && ret != 2) {
                        Logger.D(TAG, "putVideo ret " + ret, new Object[0]);
                        notifyCameraError(ret);
                        return;
                    } else if (this.mCameraView.get() != null) {
                        ((CameraView) this.mCameraView.get()).setVideoCurTimeStamp(pts2);
                    }
                }
                this.mFrameCount++;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void saveEglFirstFrame(int width, int height, ByteBuffer byteBuffer) {
    }

    private boolean checkRecordStart() {
        return this.mIsRecording && this.mCameraView.get() != null && ((CameraView) this.mCameraView.get()).isAudioStart();
    }

    private void handleFirstFrame(final byte[] data, final int width, final int height) {
        if (this.mFirstFrameRequest) {
            this.mFirstFrameRequest = false;
            if (this.mSessionConfig.mType == 1) {
                this.mRecordStartTimestamp = (System.nanoTime() / 1000) - this.mSessionConfig.videoInitTimeStamp;
            } else {
                this.mRecordStartTimestamp = System.nanoTime() / 1000;
                BackgroundExecutor.execute((Runnable) new Runnable() {
                    public void run() {
                        FFmpegCameraEncoder.this.saveFrame(data, width, height);
                    }
                });
            }
            Logger.D(TAG, "mRecordStartTimestamp: " + this.mRecordStartTimestamp, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    @TargetApi(18)
    public void savePBOFirstFrame(int width, int height, int pboId) {
    }

    private void setLiveStartTs() {
        if (!this.mLiveTsInited && this.mSessionConfig.mType == 1 && checkRecordStart()) {
            this.mRecordStartTimestamp = (System.nanoTime() / 1000) - this.mSessionConfig.videoInitTimeStamp;
            this.mLiveTsInited = true;
            Logger.D(TAG, "mRecordStartTimestamp init:" + this.mRecordStartTimestamp, new Object[0]);
        }
    }
}
