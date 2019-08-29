package com.alipay.android.phone.mobilecommon.multimedia.widget;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APTakePictureOption;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APDetectionResult;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APTakePicRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.VideoRecordParams;

public class SightCameraView extends FrameLayout {
    public static final int MODE_PHOTO = 1;
    public static final int MODE_VIDEO = 0;

    public interface APTakePictureListener extends TakePictureListener {
        void onPictureProcessFinish(APTakePicRsp aPTakePicRsp);
    }

    public interface FaceDetectionListener {
        void onFaceDetection(APDetectionResult aPDetectionResult);
    }

    public interface FramePreprocessor {
        int onProcess(int i, float[] fArr, int i2);

        void onRelease();
    }

    public interface OnRecordListener {
        void onCancel();

        void onError(APVideoRecordRsp aPVideoRecordRsp);

        void onFinish(APVideoRecordRsp aPVideoRecordRsp);

        void onInfo(int i, Bundle bundle);

        void onPrepared(APVideoRecordRsp aPVideoRecordRsp);

        void onStart();
    }

    public interface OnScrollListener {
        boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

        boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);
    }

    public interface TakePictureListener {
        public static final int ERROR_DECODE_TAKEN_PICTURE = 2;
        public static final int ERROR_RENDER_NOT_EXISTS = 101;
        public static final int ERROR_RENDER_PROCESS = 102;
        public static final int ERROR_SAVE_PROCESS_PICTURE = 103;
        public static final int ERROR_TAKEN_PICTURE = 1;

        void onPictureProcessError(int i, byte[] bArr);

        void onPictureProcessFinish(String str, int i, int i2, int i3);

        void onPictureProcessStart();

        void onPictureTaken(byte[] bArr, Camera camera);

        void onPictureTakenError(int i, Camera camera);
    }

    public interface TakePictureMPListener extends TakePictureListener {
        void onPictureProcessFinish(String str, int i, int i2, int i3, Bundle bundle);
    }

    public SightCameraView(Context context) {
        super(context);
    }

    public void startRecord() {
        startRecord(APConstants.DEFAULT_BUSINESS);
    }

    public int startRecord(String business) {
        return 0;
    }

    public void stopRecord() {
    }

    public void pauseLiveRecord() {
    }

    public void retryLiveRecord() {
    }

    public void cancelRecord() {
        cancelRecord(false);
    }

    public void cancelRecord(boolean releaseCamera) {
    }

    public Camera switchCamera() {
        return null;
    }

    public Camera reopenCamera(int mode) {
        return null;
    }

    public int getCameraId() {
        return -1;
    }

    public void setOnRecordListener(OnRecordListener listener) {
        Log.d("ylf", "shell setOnRecordListener");
    }

    public void setOnScrollListener(OnScrollListener listener) {
        Log.d("ylf", "shell setOnScrollListener");
    }

    public void setBeautyValue(int value) {
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    }

    public void switchMute() {
    }

    public boolean isLive() {
        return false;
    }

    public boolean isSupportLiveBeauty() {
        return false;
    }

    public void setLive(String channelId, String publishUrl) {
    }

    public long getCurTime() {
        return 0;
    }

    public void setRetryParam(long interval, int count) {
    }

    public void setExposureCompensation(int percent) {
    }

    public void setRecordParamas(VideoRecordParams params) {
    }

    public void setSelectedMaterial(String materialId) {
    }

    public void setSelectedFilter(int filterId) {
    }

    public void setFaceDetectionListener(FaceDetectionListener listener) {
    }

    public void takePicture(TakePictureListener listener, Looper looper) {
    }

    public void takePicture(TakePictureListener listener, Looper looper, APTakePictureOption option) {
    }

    public void releaseCamera() {
    }

    public void setFramePreprocessor(FramePreprocessor preprocessor) {
    }

    public Camera reopenCamera(int mode, CameraParams params) {
        return null;
    }

    public Camera getCamera() {
        return null;
    }
}
