package com.alipay.mobile.nebulauc.webar;

import com.alipay.sdk.util.h;
import com.uc.webview.export.extension.IARDetector;
import com.uc.webview.export.extension.IARDetector.ARSessionFrame;
import com.uc.webview.export.extension.IARDetector.ResultListener;
import java.util.Date;

public class FakeDetector implements IARDetector {
    static final /* synthetic */ boolean $assertionsDisabled = (!FakeDetector.class.desiredAssertionStatus());
    private int i;
    private boolean mPaused;
    private ResultListener mResultListener;

    public String getVersion() {
        return getDetectorVersion();
    }

    public void init(int i2, int i1, int i22, int i3, int i4) {
        this.i = 1;
        this.mPaused = false;
        this.mResultListener.onInit(10086);
    }

    public void setResultListener(ResultListener resultListener) {
        this.mResultListener = resultListener;
    }

    public void setOption(String s) {
        if (s.indexOf("\"type\":\"echo\"") > -1) {
            this.mResultListener.onResult(s);
        }
    }

    public void setARSessionFrame(ARSessionFrame arSessionFrame) {
        if ($assertionsDisabled || !this.mPaused) {
            long now = new Date().getTime();
            StringBuilder append = new StringBuilder().append("{\"seq\":");
            int i2 = this.i;
            this.i = i2 + 1;
            this.mResultListener.onResult(append.append(i2).append(",\"now\":").append(now).append(h.d).toString());
            return;
        }
        throw new AssertionError();
    }

    public void pause() {
        if (!this.mPaused) {
            this.mPaused = true;
            this.mResultListener.onResult(String.format("{\"paused\":%s}", new Object[]{this.mPaused + ""}));
        }
    }

    public void resume() {
        if (this.mPaused) {
            this.mPaused = false;
            this.mResultListener.onResult(String.format("{\"paused\":%s}", new Object[]{this.mPaused + ""}));
        }
    }

    public void removeMarkers() {
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
    }

    public void setMarkers(String[] strings) {
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
    }

    public void stop() {
    }

    public boolean isDetector() {
        return true;
    }

    public String setARSessionFrameFilter(ARSessionFrame arSessionFrame) {
        if ($assertionsDisabled) {
            return null;
        }
        throw new AssertionError();
    }

    public static String getDetectorVersion() {
        return "beta";
    }
}
