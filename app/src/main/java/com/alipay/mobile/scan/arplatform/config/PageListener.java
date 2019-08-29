package com.alipay.mobile.scan.arplatform.config;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import java.util.Observer;

public interface PageListener {

    public interface CameraCallback {
        void onCameraClose();

        void onCameraReady(Camera camera);
    }

    public class InitParams {
        public Camera camera;
        public int cameraOrientation;
        public boolean directIn;
        public View finderView;
        public Bitmap lastFrameBmp;
        public Bundle params;
        public ViewGroup parentContainer;
        public TextureView previewView;
        public float transformScale;
    }

    public interface PageCallback {
        void clearSceneId();

        void closeCamera(Observer observer);

        void enableCameraOpenWatcher(boolean z);

        Camera getCamera();

        int getCameraOrientation();

        Bitmap getPreviewBitmap();

        void hideBottomView();

        void hideTitleBar();

        boolean isTorchOn();

        void onCameraPreviewShow();

        void openCamera(Observer observer);

        void postInCameraHandler(Runnable runnable);

        void quitApp();

        void reconnectCamera();

        void refocus();

        void removeCallbacks(Runnable runnable);

        void setCameraCallback(CameraCallback cameraCallback);

        void setPreviewCallback();

        void setScanEnable(boolean z);

        void setTabSwitchEnable(boolean z);

        void setTorch(boolean z);

        void showBottomView();

        void showTitleBar();

        void stopAutoFocus();
    }

    boolean interceptCameraOpen(boolean z);

    boolean interceptCameraPermission(boolean z);

    boolean onBackPressed();

    void onCreate(InitParams initParams);

    void onDestroy();

    void onParentAttachWindow();

    void onParentDetachWindow();

    void onPause();

    void onPreviewShow();

    void onResume();

    void onTabIn(String str, InitParams initParams);

    void onTabOut();

    void setPageCallback(PageCallback pageCallback);
}
