package defpackage;

import android.os.Looper;
import com.autonavi.vcs.recorder.Recorder;

/* renamed from: eql reason: default package */
/* compiled from: RecordManager */
public final class eql {
    public Recorder a = new Recorder();
    eqm b = new eqm(this.a);

    public final void a(final int i, String str) {
        StringBuilder sb = new StringBuilder("addTask/taskType:");
        sb.append(i);
        sb.append("/caller:");
        sb.append(str);
        bfh.a("Recorder-RecordManager", sb.toString());
        if (b()) {
            this.b.a(i);
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    eql.this.b.a(i);
                }
            });
        }
    }

    public final void a() {
        a(1);
        a(2);
        a(3);
    }

    private void a(final int i) {
        if (b()) {
            this.b.b(i);
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    eql.this.b.b(i);
                }
            });
        }
    }

    private static boolean b() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
