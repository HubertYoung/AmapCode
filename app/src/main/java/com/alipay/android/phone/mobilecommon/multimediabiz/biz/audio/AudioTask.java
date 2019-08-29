package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;

public class AudioTask {
    public static final int STATE_CANCEL = 3;
    public static final int STATE_FINISH = 4;
    public static final int STATE_INIT = 0;
    public static final int STATE_RUNNING = 2;
    public static final int STATE_SUBMIT = 1;
    public static final int STATE_UNKNOWN = -1;
    private int a = -1;
    private APRequestParam b;

    public int getState() {
        return this.a;
    }

    public void setState(int state) {
        if (state < -1 || state > 4) {
            throw new IllegalArgumentException("state code error, please check your code!");
        }
        this.a = state;
    }

    public APRequestParam getRequestParam() {
        return this.b;
    }

    public void setRequestParam(APRequestParam reqParam) {
        this.b = reqParam;
    }

    public boolean isRunning() {
        return 2 == this.a;
    }

    public boolean hasInit() {
        return this.a >= 0 && this.a <= 2;
    }

    public boolean isCanceled() {
        return 3 == this.a;
    }

    public boolean isFinished() {
        return 4 == this.a;
    }
}
