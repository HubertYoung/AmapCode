package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean;

public class AudioRecordState {
    public static final int STATE_CANCEL = 4;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FINISHED = 202;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PAUSED = 5;
    public static final int STATE_READY = 1;
    public static final int STATE_RECORD_PROGRESS_CHANGED = 201;
    public static final int STATE_RESUME = 6;
    public static final int STATE_START = 2;
    public static final int STATE_STOP = 3;
    public static final int STATE_UNKNOWN = -1;
    public static final int STATE_VOLUME_CHANGED = 200;
    private volatile int a = -1;

    public void setState(int state) {
        this.a = state;
    }

    public int getState() {
        return this.a;
    }

    public boolean isIdel() {
        return this.a == 0;
    }

    public boolean isReady() {
        return 1 == this.a;
    }

    public boolean isStarted() {
        return 2 == this.a;
    }

    public boolean isPaused() {
        return 5 == this.a;
    }

    public boolean isResumed() {
        return 6 == this.a;
    }

    public boolean isCanceled() {
        return 4 == this.a;
    }

    public boolean isStoped() {
        return 3 == this.a;
    }

    public boolean isErrored() {
        return 7 == this.a;
    }

    public static String printState(int state) {
        switch (state) {
            case 0:
                return "STATE_IDLE";
            case 1:
                return "STATE_READY";
            case 2:
                return "STATE_START";
            case 3:
                return "STATE_STOP";
            case 4:
                return "STATE_CANCEL";
            case 5:
                return "STATE_PAUSED";
            case 6:
                return "STATE_RESUME";
            case 7:
                return "STATE_ERROR";
            case 200:
                return "STATE_VOLUME_CHANGED";
            case 201:
                return "STATE_RECORD_PROGRESS_CHANGED";
            case 202:
                return "STATE_FINISHED";
            default:
                return "STATE_UNKNOWN";
        }
    }
}
