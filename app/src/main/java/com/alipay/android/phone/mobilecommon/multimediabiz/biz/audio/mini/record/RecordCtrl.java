package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.record;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.RecordMarkTime;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioRecordState;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IRecordCtrl;

public class RecordCtrl implements IRecordCtrl {
    private AudioRecordState a = new AudioRecordState();
    private RecordMarkTime b;

    public RecordCtrl(RecordMarkTime time) {
        a(time);
        this.b = time;
    }

    private static void a(RecordMarkTime time) {
        if (time == null) {
            throw new NullPointerException("please check constructor params ,fail to init Record ctrl ");
        }
    }

    public void refreshRecordState(int state) {
        synchronized (this.b) {
            this.a.setState(state);
            if (this.a.isPaused()) {
                this.b.markPause();
            } else if (this.a.isResumed()) {
                this.b.markResume();
            } else {
                this.b.markClearPause();
            }
            if (!this.a.isPaused()) {
                excuteRecordNotifyAll();
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean excuteRecordWait() {
        /*
            r2 = this;
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.RecordMarkTime r1 = r2.b
            monitor-enter(r1)
        L_0x0003:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioRecordState r0 = r2.a     // Catch:{ all -> 0x0018 }
            boolean r0 = r0.isPaused()     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x0015
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.RecordMarkTime r0 = r2.b     // Catch:{ InterruptedException -> 0x0011 }
            r0.wait()     // Catch:{ InterruptedException -> 0x0011 }
            goto L_0x0003
        L_0x0011:
            r0 = move-exception
            r0 = 0
            monitor-exit(r1)     // Catch:{ all -> 0x0018 }
        L_0x0014:
            return r0
        L_0x0015:
            monitor-exit(r1)     // Catch:{ all -> 0x0018 }
            r0 = 1
            goto L_0x0014
        L_0x0018:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0018 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.record.RecordCtrl.excuteRecordWait():boolean");
    }

    public long getPauseDuration() {
        return this.b.getTotalPauseTime();
    }

    public AudioRecordState getRecordState() {
        AudioRecordState audioRecordState;
        synchronized (this.b) {
            audioRecordState = this.a;
        }
        return audioRecordState;
    }

    public void excuteRecordNotifyAll() {
        synchronized (this.b) {
            if (!this.a.isPaused()) {
                this.b.notifyAll();
            }
        }
    }
}
