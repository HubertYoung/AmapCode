package com.uc.webview.export.extension;

import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.interfaces.InvokeObject;

@Api
/* compiled from: ProGuard */
public interface IARDetector {

    @Api
    /* compiled from: ProGuard */
    public static class ARDetector implements IARDetector {
        protected ResultListener mListener = null;

        public String getVersion() {
            return "-1";
        }

        public void init(int i, int i2, int i3, int i4, int i5) {
        }

        public final boolean isDetector() {
            return true;
        }

        public void pause() {
        }

        public void removeMarkers() {
        }

        public void resume() {
        }

        public void setARSessionFrame(ARSessionFrame aRSessionFrame) {
        }

        public final String setARSessionFrameFilter(ARSessionFrame aRSessionFrame) {
            return null;
        }

        public void setMarkers(String[] strArr) {
        }

        public void setOption(String str) {
        }

        public void stop() {
        }

        public void setResultListener(ResultListener resultListener) {
            this.mListener = resultListener;
        }
    }

    @Api
    /* compiled from: ProGuard */
    public static class ARFilter implements IARDetector {
        protected ResultListener mListener = null;

        public String getVersion() {
            return "-1";
        }

        public void init(int i, int i2, int i3, int i4, int i5) {
        }

        public final boolean isDetector() {
            return false;
        }

        public void pause() {
        }

        public void removeMarkers() {
        }

        public void resume() {
        }

        public final void setARSessionFrame(ARSessionFrame aRSessionFrame) {
        }

        public String setARSessionFrameFilter(ARSessionFrame aRSessionFrame) {
            return "";
        }

        public void setMarkers(String[] strArr) {
        }

        public void setOption(String str) {
        }

        public void stop() {
        }

        public void setResultListener(ResultListener resultListener) {
            this.mListener = resultListener;
        }
    }

    @Api
    /* compiled from: ProGuard */
    public static class ARSessionFrame {
        public byte[] data;
        public int format;
        public int frameId;
        public int height;
        public int imageRotation;
        public int rotation;
        public Object userData;
        public int width;
    }

    @Api
    /* compiled from: ProGuard */
    public interface ResultListener extends InvokeObject {
        void onInit(int i);

        void onResult(String str);

        void onResult(String str, Object obj);
    }

    String getVersion();

    void init(int i, int i2, int i3, int i4, int i5);

    boolean isDetector();

    void pause();

    void removeMarkers();

    void resume();

    void setARSessionFrame(ARSessionFrame aRSessionFrame);

    String setARSessionFrameFilter(ARSessionFrame aRSessionFrame);

    void setMarkers(String[] strArr);

    void setOption(String str);

    void setResultListener(ResultListener resultListener);

    void stop();
}
