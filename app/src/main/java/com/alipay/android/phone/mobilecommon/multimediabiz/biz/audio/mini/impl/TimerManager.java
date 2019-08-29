package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.RecordMarkTime;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.ITimerListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.ITimerManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class TimerManager implements ITimerManager {
    private static final Logger a = Logger.getLogger((String) "TimerManager");
    private HandlerThread b;
    private Handler c;
    /* access modifiers changed from: private */
    public ITimerListener d;
    /* access modifiers changed from: private */
    public APAudioInfo e;
    private RecordMarkTime f;

    private class TimerHandler extends Handler {
        public TimerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                case 4:
                    TimerManager.b((String) "[TimerManager]>>>refresh amplitude>>>>");
                    a();
                    return;
                case 2:
                case 5:
                    TimerManager.b((String) "[TimerManager]>>>refresh progress>>>>");
                    b();
                    return;
                case 3:
                    TimerManager.b((String) "[TimerManager]>>>max time callback>>>>");
                    if (TimerManager.this.d != null) {
                        TimerManager.this.d.onRecordMaxTimeFinished();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        private void a() {
            if (TimerManager.this.d != null) {
                TimerManager.this.d.onRecordAmplitudeChanged();
            }
            removeMessages(4);
            removeMessages(1);
            sendEmptyMessageDelayed(4, 300);
        }

        private void b() {
            if (TimerManager.this.d != null) {
                TimerManager.this.d.onRecordProgressChanged();
            }
            removeMessages(5);
            removeMessages(2);
            sendEmptyMessageDelayed(5, (long) TimerManager.this.e.getProgressUpdateInterval());
        }
    }

    public TimerManager(APAudioInfo audioInfo, RecordMarkTime recordMarkTime) {
        this.e = audioInfo;
        this.f = recordMarkTime;
    }

    public void setupTimer() {
        a.d("setupTimer, audioInfo: " + this.e, new Object[0]);
        cancelTimer();
        try {
            a();
            a((long) this.e.getRecordMaxTime());
        } catch (Exception e2) {
            if (this.d != null) {
                this.d.onErrorTimer();
            }
        }
    }

    private void a(long maxTime) {
        this.c.removeCallbacksAndMessages(null);
        this.c.sendEmptyMessageDelayed(1, 50);
        if (this.e.getProgressUpdateInterval() > 0) {
            this.c.sendEmptyMessageDelayed(2, 1);
        }
        if (this.e.getRecordMaxTime() > 0) {
            this.c.sendEmptyMessageDelayed(3, maxTime);
        }
    }

    private void a() {
        b("[TimerManager]>>>initThread mTimerHandlerThread == null?>>>>" + (this.b == null));
        if (this.b == null) {
            this.b = new HandlerThread("record-timer-handler-thread");
            this.b.start();
            this.c = new TimerHandler(this.b.getLooper());
        }
    }

    public void cancelTimer() {
        b("[TimerManager]>>>cancelTimer mTimerHandlerThread == null?>>>>" + (this.b == null));
        if (this.b != null) {
            this.c.removeCallbacksAndMessages(null);
            this.b.quit();
            this.b = null;
            this.c = null;
        }
    }

    public void pauseTimer() {
        b((String) "[TimerManager]>>> pause Timer");
        this.c.removeCallbacksAndMessages(null);
    }

    public void resumeTimer() {
        b((String) "[TimerManager]>>> resume Timer");
        a(this.f.getRemainRecordMaxDuration((long) this.e.getRecordMaxTime()));
    }

    public void setTimerListener(ITimerListener listener) {
        this.d = listener;
    }

    /* access modifiers changed from: private */
    public static void b(String msg) {
        a.p(msg, new Object[0]);
    }
}
