package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;

/* renamed from: ebq reason: default package */
/* compiled from: SimpleTimer */
public abstract class ebq implements eaw, Runnable {
    private String a;
    private long b;
    private long c;
    private long d;
    private Handler e;
    private long f;
    private long g;
    private long h;

    public ebq(String str, long j, long j2, long j3) {
        this.a = str;
        if (TextUtils.isEmpty(this.a)) {
            this.a = "default";
        }
        j2 = j2 < 0 ? 0 : j2;
        j3 = 1000 > j2 ? j2 : j3;
        this.b = j;
        this.c = j2;
        this.d = j3;
    }

    public void b() {
        if (this.e == null) {
            this.e = new Handler(Looper.getMainLooper());
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(": new timer start, delay = ");
            sb.append(this.b);
            sb.append(", period = ");
            sb.append(this.c);
            sb.append(", interval = ");
            sb.append(this.d);
            eao.a((String) "SimpleTimer", sb.toString());
            this.f = SystemClock.elapsedRealtime() + this.b;
            this.g = this.f + this.c;
            if (this.g < 0) {
                throw new ArithmeticException("定时周期(period)设置过大，导致结束时间溢出");
            }
            if (this.b <= 0) {
                d();
                if (this.c == 0) {
                    c();
                    return;
                }
                e();
            } else if (this.e != null) {
                this.e.postDelayed(this, this.b);
            }
        }
    }

    public void c() {
        if (this.e != null) {
            this.f = 0;
            this.g = 0;
            this.h = 0;
            this.e.removeCallbacksAndMessages(null);
            this.e = null;
            a();
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(": timer stop");
            eao.a((String) "SimpleTimer", sb.toString());
        }
    }

    public void run() {
        if (d()) {
            c();
        } else {
            e();
        }
    }

    private boolean d() {
        long j;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i = 0;
        boolean z = elapsedRealtime - this.g >= 0;
        if (z) {
            long j2 = (this.g - this.f) / this.d;
            if ((this.g - this.f) % this.d > 0) {
                i = 1;
            }
            j = j2 + ((long) i);
        } else {
            j = (elapsedRealtime - this.f) / this.d;
        }
        Math.max(0, this.g - elapsedRealtime);
        a(j);
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(": onTick...   tickTimes = ");
        sb.append(j);
        sb.append("， spendTime = ");
        sb.append(elapsedRealtime - this.f);
        sb.append(", leftTime = ");
        sb.append(Math.max(0, this.g - elapsedRealtime));
        sb.append(", isLastCallback = ");
        sb.append(z);
        eao.c("SimpleTimer", sb.toString());
        return z;
    }

    private void e() {
        if (this.e == null) {
            c();
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.f + (((elapsedRealtime - this.f) / this.d) * this.d) + this.d;
        if (j >= this.g) {
            j = this.g;
        }
        long j2 = j - elapsedRealtime;
        long j3 = 0;
        if (j2 > 0) {
            j3 = j2;
        }
        this.e.postDelayed(this, j3);
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(": postDelayed ");
        sb.append(j3);
        eao.c("SimpleTimer", sb.toString());
    }
}
