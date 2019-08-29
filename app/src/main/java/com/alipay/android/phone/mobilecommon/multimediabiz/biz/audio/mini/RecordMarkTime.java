package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini;

import java.util.ArrayList;
import java.util.List;

public class RecordMarkTime {
    private long a = 0;
    private long b = 0;
    private long c = 0;
    private List<Long> d = new ArrayList();

    public synchronized void markStart() {
        this.a = System.nanoTime();
    }

    public synchronized void markPause() {
        this.b = System.nanoTime();
    }

    public synchronized void markResume() {
        long pauseEndTime = System.nanoTime();
        if (this.b > 0 && pauseEndTime > this.b) {
            a(pauseEndTime - this.b);
            markClearPause();
        }
    }

    public synchronized void markClearPause() {
        this.b = 0;
    }

    private void a(long duration) {
        this.d.add(Long.valueOf(duration));
    }

    public synchronized void markFinish() {
        this.c = System.nanoTime();
    }

    public synchronized void clearMarkTime() {
        this.d.clear();
        this.a = 0;
        this.c = 0;
        this.b = 0;
    }

    public synchronized long getNsRecordDuration() {
        return b((this.c <= 0 || this.c <= this.a) ? System.nanoTime() : this.c);
    }

    private synchronized long b(long time) {
        return (time - this.a) - getTotalPauseTime();
    }

    public synchronized long getTotalPauseTime() {
        long sum;
        sum = 0;
        for (Long t : this.d) {
            sum += t.longValue();
        }
        return sum;
    }

    public synchronized long getMillisRecordDuration() {
        try {
        }
        return getNsRecordDuration() / 1000000;
    }

    public synchronized long getRemainRecordMaxDuration(long maxTime) {
        return maxTime - (b(System.nanoTime()) / 1000000);
    }
}
