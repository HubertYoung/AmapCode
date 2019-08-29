package com.alipay.mobile.nebulauc.webar;

import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.webar.FaceDetectorImpl;
import com.uc.webview.export.extension.IARDetector;
import com.uc.webview.export.extension.IARDetector.ARSessionFrame;
import com.uc.webview.export.extension.IARDetector.ResultListener;

public class FaceDetector implements IARDetector {
    private static final String TAG = "FaceDetector";
    FaceDetectorImpl mFaceDetectorImpl = new FaceDetectorImpl();

    public void setARSessionFrame(ARSessionFrame arSessionFrame) {
        H5Log.d(TAG, "setARSessionFrame");
        if (this.mFaceDetectorImpl != null) {
            this.mFaceDetectorImpl.setARSessionFrame(arSessionFrame);
        }
    }

    public String setARSessionFrameFilter(ARSessionFrame arSessionFrame) {
        return null;
    }

    public void setResultListener(ResultListener resultListener) {
        H5Log.d(TAG, "setResultListener");
        if (this.mFaceDetectorImpl != null) {
            this.mFaceDetectorImpl.setResultListener(resultListener);
        }
    }

    public void init(int width, int height, int rotation, int format, int mId) {
        H5Log.d(TAG, "init");
        if (this.mFaceDetectorImpl != null) {
            this.mFaceDetectorImpl.init(width, height, rotation, format, mId);
        }
    }

    public void stop() {
        H5Log.d(TAG, AudioUtils.CMDSTOP);
        if (this.mFaceDetectorImpl != null) {
            this.mFaceDetectorImpl.stop();
            this.mFaceDetectorImpl = null;
        }
    }

    public void pause() {
        H5Log.d(TAG, AudioUtils.CMDPAUSE);
        if (this.mFaceDetectorImpl != null) {
            this.mFaceDetectorImpl.pause();
        }
    }

    public void resume() {
        H5Log.d(TAG, "resume");
        if (this.mFaceDetectorImpl != null) {
            this.mFaceDetectorImpl.resume();
        }
    }

    public void setMarkers(String[] markers) {
        H5Log.d(TAG, "setMarkers");
        if (this.mFaceDetectorImpl != null) {
            this.mFaceDetectorImpl.setMarkers(markers);
        }
    }

    public void removeMarkers() {
        H5Log.d(TAG, "removeMarkers");
        if (this.mFaceDetectorImpl != null) {
            this.mFaceDetectorImpl.removeMarkers();
        }
    }

    public void setOption(String option) {
        H5Log.d(TAG, "setOption");
        if (this.mFaceDetectorImpl != null) {
            this.mFaceDetectorImpl.setOption(option);
        }
    }

    public boolean isDetector() {
        H5Log.d(TAG, "isDetector");
        return true;
    }

    public String getVersion() {
        H5Log.d(TAG, "getVersion");
        return "1";
    }
}
