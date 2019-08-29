package com.alipay.mobile.bqcscanservice.monitor;

import android.text.TextUtils;

public class ScanExceptionHandler {
    public static final String PREVIEW_RECONNECT_CAMERA = "reconnect_camera";
    public static final String PREVIEW_START_CAMERA = "start_camera";

    public class TorchException extends Exception {
        public static final int FOCUS_FAILED = 4002;
        public static final int THREAD_FAILED = 4003;
        public static final int TORCH_FAILED = 4001;
        public int errorCode;
        public boolean state;

        public TorchException(boolean switchState, int errorCode2, String msg) {
            super(msg);
            this.state = switchState;
            this.errorCode = errorCode2;
        }
    }

    public static int getCameraErrorCode(String errMsg) {
        if (TextUtils.isEmpty(errMsg)) {
            return 2004;
        }
        String errMsgLower = errMsg.toLowerCase();
        if (errMsgLower.contains("service")) {
            return 2001;
        }
        if (errMsgLower.contains("initial")) {
            return 2002;
        }
        if (errMsgLower.contains("unknown")) {
            return 2003;
        }
        return 2004;
    }

    public static int getPreviewErrorCode(String methodName) {
        if (TextUtils.equals(methodName, PREVIEW_START_CAMERA)) {
            return 3001;
        }
        if (TextUtils.equals(methodName, PREVIEW_RECONNECT_CAMERA)) {
            return 3002;
        }
        return 0;
    }
}
