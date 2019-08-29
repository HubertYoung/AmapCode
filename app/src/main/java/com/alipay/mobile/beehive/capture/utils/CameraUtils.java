package com.alipay.mobile.beehive.capture.utils;

import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView;
import com.alipay.mobile.beehive.capture.activity.CaptureActivity;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.googlecode.androidannotations.api.BackgroundExecutor;

public class CameraUtils {

    public interface CameraHelperListener {
        void onCameraFacingChanged(Camera camera, int i, int i2);

        void onCaptureModeReached(Camera camera);

        void onVideoModeReached(Camera camera);
    }

    public static void cancelRecord(final SightCameraView cameraView) {
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public final void run() {
                cameraView.cancelRecord();
            }
        });
    }

    public static void stopRecord(final SightCameraView cameraView) {
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public final void run() {
                cameraView.stopRecord();
            }
        });
    }

    public static void switchCameraFacing(final SightCameraView cameraView, final int cameraFacing, final CameraHelperListener listener, final int cameraMode) {
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public final void run() {
                final Camera camera = cameraView.switchCamera();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public final void run() {
                        listener.onCameraFacingChanged(camera, Math.abs(cameraFacing - 1), cameraMode);
                    }
                });
            }
        });
    }

    public static void openRecordMode(final SightCameraView cameraView, final CameraHelperListener listener) {
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public final void run() {
                final Camera camera = cameraView.reopenCamera(0);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public final void run() {
                        listener.onVideoModeReached(camera);
                    }
                });
            }
        });
    }

    public static void openRecordMode(Camera camera, CameraHelperListener listener) {
        listener.onVideoModeReached(camera);
    }

    public static void openCaptureMode(Camera camera, CameraHelperListener listener) {
        listener.onCaptureModeReached(camera);
    }

    public static void reopenCameraIntoVideoMode(final SightCameraView cameraView, final CameraHelperListener listener) {
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public final void run() {
                final Camera camera = cameraView.reopenCamera(0);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public final void run() {
                        listener.onVideoModeReached(camera);
                    }
                });
            }
        });
    }

    public static boolean isZTEFrontCamera() {
        return "ZTE".equalsIgnoreCase(Build.MANUFACTURER) && "ZTE U970".equalsIgnoreCase(Build.MODEL);
    }

    public static boolean isMZPhone() {
        String model = Build.MODEL;
        String manufacturer = Build.MANUFACTURER;
        LoggerFactory.getTraceLogger().info(CaptureActivity.TAG, "model: " + model);
        LoggerFactory.getTraceLogger().info(CaptureActivity.TAG, "manufacturer: " + manufacturer);
        return "Meizu".equalsIgnoreCase(manufacturer) && !"M353".equalsIgnoreCase(model);
    }

    public static boolean isXiaoMi3() {
        String model = Build.MODEL;
        String manufacturer = Build.MANUFACTURER;
        LoggerFactory.getTraceLogger().info(CaptureActivity.TAG, "model: " + model);
        LoggerFactory.getTraceLogger().info(CaptureActivity.TAG, "manufacturer: " + manufacturer);
        return "Xiaomi".equalsIgnoreCase(manufacturer) && "MI 3".equalsIgnoreCase(model);
    }

    public static boolean isSupportCaptureFlush() {
        return !isXiaoMi3();
    }

    public static boolean checkPhoneModel(String manufacturer, String model) {
        return manufacturer.equalsIgnoreCase(Build.MANUFACTURER) && model.equalsIgnoreCase(Build.MODEL);
    }
}
