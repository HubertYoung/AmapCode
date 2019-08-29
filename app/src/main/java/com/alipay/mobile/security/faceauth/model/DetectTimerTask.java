package com.alipay.mobile.security.faceauth.model;

import java.util.Timer;

public class DetectTimerTask {
    Timer a = null;
    int b = 30000;
    int c = 1000;
    int d = 1000;
    TimerListener e;
    private int f = 30000;

    public interface TimerListener {
        void countdown(int i);
    }

    public DetectTimerTask(int i) {
        this.f = i;
        this.b = i;
    }

    public DetectTimerTask(int i, int i2, int i3) {
        this.f = i;
        this.b = i;
        this.c = i2;
        this.d = i3;
    }

    public void setTimerTaskListener(TimerListener timerListener) {
        this.e = timerListener;
    }

    public int getLeftTime() {
        return this.b;
    }

    public boolean isTimeOut() {
        if (this.b == 0) {
            return true;
        }
        return false;
    }

    public void reset() {
        this.b = this.f;
    }

    public void start() {
        this.b = this.f;
        if (this.e != null) {
            this.e.countdown(this.b);
        }
        stop();
        this.a = new Timer();
        this.a.schedule(new a(this), (long) this.c, (long) this.d);
    }

    public void stop() {
        this.b = this.f;
        if (this.a != null) {
            this.a.cancel();
            this.a = null;
        }
    }
}
