package com.alipay.android.phone.mobilecommon.multimedia.video.data;

import android.graphics.Rect;
import android.hardware.Camera.Size;
import java.util.Map;

public class CameraParams {
    public static final String FLASH_MODE_AUTO = "auto";
    public static final String FLASH_MODE_OFF = "off";
    public static final String FLASH_MODE_ON = "on";
    public static final int REC_TYPE_DEFAULT = 0;
    public static final int REC_TYPE_FALCON_LOOKS = 2;
    public static final int REC_TYPE_LIVE = 1;
    public static final int REC_TYPE_OMX_VIDEO = 3;
    public static final int REC_TYPE_XMEDIA = 4;
    public boolean autoFucus = true;
    public boolean autoRotateTakenPicture = true;
    public boolean bZoomEnable = true;
    public Map<String, String> exif;
    public int mActivityRotation = -1;
    private boolean mBeautyEnable = false;
    private boolean mConvertPicture = true;
    public Rect mCropRect = null;
    private boolean mDefaultCameraFront = false;
    private String mFlashMode = "";
    public boolean mIgnoreOrientation = false;
    public boolean mLandscapeVideo = false;
    public int mMode = 0;
    private Size mPictureSize;
    public Rect mSrcRect = null;
    public int recordType = 0;

    public String getFlashMode() {
        return this.mFlashMode;
    }

    public void setFlashMode(String mFlashMode2) {
        this.mFlashMode = mFlashMode2;
    }

    public void enableBeauty(boolean enable) {
        this.mBeautyEnable = enable;
    }

    public boolean isBeautyEnabled() {
        return this.mBeautyEnable;
    }

    public boolean isDefaultCameraFront() {
        return this.mDefaultCameraFront;
    }

    public void setDefaultCameraFront(boolean defaultCameraFront) {
        this.mDefaultCameraFront = defaultCameraFront;
    }

    public Size getPictureSize() {
        return this.mPictureSize;
    }

    public void setPictureSize(Size pictureSize) {
        this.mPictureSize = pictureSize;
    }

    public boolean isConvertPicture() {
        return this.mConvertPicture;
    }

    public void setConvertPicture(boolean convertPicture) {
        this.mConvertPicture = convertPicture;
    }

    public String toString() {
        return "CameraParams{recordType=" + this.recordType + ", mBeautyEnable=" + this.mBeautyEnable + ", bZoomEnable=" + this.bZoomEnable + ", mDefaultCameraFront=" + this.mDefaultCameraFront + ", mPictureSize=" + this.mPictureSize + ", mConvertPicture=" + this.mConvertPicture + ", mIgnoreOrientation=" + this.mIgnoreOrientation + ", mLandscapeVideo=" + this.mLandscapeVideo + ", exif=" + this.exif + ", autoRotateTakenPicture=" + this.autoRotateTakenPicture + ", mActivityRotation=" + this.mActivityRotation + '}';
    }
}
