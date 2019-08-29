package com.alipay.mobile.nebulauc.webar;

import com.alipay.mobile.webar.MarkerDetectorImpl;
import com.uc.webview.export.extension.IARDetector;
import com.uc.webview.export.extension.IARDetector.ARSessionFrame;
import com.uc.webview.export.extension.IARDetector.ResultListener;

public class MarkerDetector implements IARDetector {
    private MarkerDetectorImpl mMarkerDetectorImpl = new MarkerDetectorImpl();

    public void setARSessionFrame(ARSessionFrame arSessionFrame) {
        if (this.mMarkerDetectorImpl != null) {
            this.mMarkerDetectorImpl.setARSessionFrame(arSessionFrame);
        }
    }

    public void setResultListener(ResultListener resultListener) {
        if (this.mMarkerDetectorImpl != null) {
            this.mMarkerDetectorImpl.setResultListener(resultListener);
        }
    }

    public void init(int width, int height, int rotation, int format, int mId) {
        if (this.mMarkerDetectorImpl != null) {
            this.mMarkerDetectorImpl.init(width, height, rotation);
        }
    }

    public void stop() {
        if (this.mMarkerDetectorImpl != null) {
            this.mMarkerDetectorImpl.stop();
            this.mMarkerDetectorImpl = null;
        }
    }

    public void pause() {
        if (this.mMarkerDetectorImpl != null) {
            this.mMarkerDetectorImpl.pause();
        }
    }

    public void resume() {
        if (this.mMarkerDetectorImpl != null) {
            this.mMarkerDetectorImpl.resume();
        }
    }

    public void setMarkers(String[] markers) {
        if (this.mMarkerDetectorImpl != null) {
            this.mMarkerDetectorImpl.setMarkers(markers);
        }
    }

    public void removeMarkers() {
        if (this.mMarkerDetectorImpl != null) {
            this.mMarkerDetectorImpl.removeMarkers();
        }
    }

    public void setOption(String option) {
        if (this.mMarkerDetectorImpl != null) {
            this.mMarkerDetectorImpl.setOption(option);
        }
    }

    public String setARSessionFrameFilter(ARSessionFrame arSessionFrame) {
        return "";
    }

    public boolean isDetector() {
        return true;
    }

    public String getVersion() {
        return MarkerDetectorImpl.getVersion();
    }

    public static String getDetectorVersion() {
        return MarkerDetectorImpl.getVersion();
    }
}
