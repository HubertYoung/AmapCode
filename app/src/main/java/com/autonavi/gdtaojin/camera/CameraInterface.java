package com.autonavi.gdtaojin.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class CameraInterface {
    public static final String CAMERA_PARAMETER = "parameter";

    public interface onCaptureButtonClickListener {
        void onCapture();

        void onCaptureEnd();
    }

    public static void setCameraPictureSize(int i) {
        CameraConst.MAX_PICTURE_SIZE = i;
    }

    public static void setCameraFloder(String str) {
        CameraConst.FOLDER_NAME = str;
    }

    public static void setPictureCompressQuality(int i) {
        CameraConst.PICTURE_QUALITY = i;
    }

    public static void setPictrueCompressSize(int i) {
        CameraConst.MAX_PICTURE_COMPRESS_SIZE_VALUE = i;
    }

    public static void setIsPhotoCompress(boolean z) {
        CameraConst.IS_PICTURE_COMPRESSED = z;
    }

    public static void setIsDevelopPictureSizeFunction(boolean z) {
        CameraConst.IS_DEVELOP_PICTURESIZE = z;
    }

    public static void setIsHasTouchCaptureFunction(boolean z) {
        CameraConst.IS_HAS_TOUCH_CAPTURE = z;
    }

    public static void setIsHasVolumeKeyFunction(boolean z) {
        CameraConst.IS_HAS_VOLUME_CAPTURE = z;
    }

    public static void setCameraCapturePermisson(boolean z, String str) {
        CameraConst.IS_HAS_CAPTURE_PERMISSION = z;
        CameraConst.PERMISSION_REASON = str;
    }

    public static void setOnCaptureButtonClickListener(onCaptureButtonClickListener oncapturebuttonclicklistener) {
        CameraConst.CAPTURE_LISTENER = oncapturebuttonclicklistener;
    }

    public static String getPicturePathByURI(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString().substring(uri.toString().indexOf("///") + 2);
    }

    public static void showCameraActivityForResult(Activity activity, int i) {
        activity.startActivityForResult(new Intent(activity, CameraActivity.class), i);
    }

    public static void showCameraActivityForResultWithParameter(Activity activity, int i, String str) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(CAMERA_PARAMETER, str);
        activity.startActivityForResult(intent, i);
    }

    public static float getShootedOrientation(Intent intent) {
        if (intent == null) {
            return -1.0f;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return -1.0f;
        }
        return extras.getFloat(CameraControllerManager.MSG_XDirection);
    }
}
