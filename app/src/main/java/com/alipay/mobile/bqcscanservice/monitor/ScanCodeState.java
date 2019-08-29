package com.alipay.mobile.bqcscanservice.monitor;

import com.alipay.mobile.bqcscanservice.Logger;

public class ScanCodeState {
    public static final String TAG = "ScanCodeState";
    private int a = Error.OK;
    private int b = 0;
    private int c;
    private long d;
    private float e;
    private boolean f;

    public class Error {
        public static int CameraHasNoFrames = 1002;
        public static int CameraOpenError = 1000;
        public static int CanNotOpenTorch = 1004;
        public static int CanNotRecognized = 1003;
        public static int OK = 0;
        public static int PreviewError = 1001;
    }

    public void reset() {
        this.a = Error.OK;
        this.b = Error.OK;
        this.c = 0;
        this.d = 0;
        this.e = 0.0f;
        this.f = false;
    }

    public void setCameraErrorCode(int code) {
        this.a = Error.CameraOpenError;
        this.b = code;
    }

    public void setPreviewFailed(int errorCode) {
        this.a = Error.PreviewError;
        this.b = errorCode;
    }

    public void setTorchFailed(boolean state, int errorCode) {
        this.a = Error.CanNotOpenTorch;
        this.b = errorCode;
        this.f = state;
    }

    public void setRecognizeFailed(ScanResultMonitor scanResultMonitor) {
        if (scanResultMonitor == null || scanResultMonitor.succeed) {
            return;
        }
        if (scanResultMonitor.scanFrameNum == 0 && scanResultMonitor.scanDuration > 0) {
            this.a = Error.CameraHasNoFrames;
            this.d = scanResultMonitor.scanDuration;
        } else if (scanResultMonitor.size > 0.0f && scanResultMonitor.scanFrameNum > 0 && scanResultMonitor.scanDuration > 0) {
            this.a = Error.CanNotRecognized;
            this.c = scanResultMonitor.scanFrameNum;
            this.d = scanResultMonitor.scanDuration;
            this.e = scanResultMonitor.size;
        }
    }

    public boolean needUploadBuryInfo() {
        return this.a != Error.OK;
    }

    public String dumpBuryInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("errorCode=").append(this.a).append("^");
        sb.append("subErrorCode=").append(this.b).append("^");
        sb.append("frameNumRound=").append(this.c).append("^");
        sb.append("recognizeSpent=").append(this.d).append("^");
        sb.append("codeSize=").append(this.e).append("^");
        sb.append("torchState=").append(this.f);
        Logger.d(TAG, "dumpBuryInfo(" + sb.toString() + ")");
        return sb.toString();
    }
}
