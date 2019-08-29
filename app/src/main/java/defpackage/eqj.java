package defpackage;

import com.autonavi.vcs.recorder.Recorder;
import com.autonavi.vcs.recorder.Recorder.b;
import com.autonavi.vcs.recorder.Task;

/* renamed from: eqj reason: default package */
/* compiled from: InitTask */
public final class eqj extends Task {
    public final String a() {
        return "InitTask";
    }

    public eqj(int i, a aVar, Recorder recorder) {
        super(i, aVar, recorder);
    }

    public final void b() {
        Recorder recorder = this.c;
        boolean z = false;
        if (Recorder.b() && recorder.a != 2) {
            recorder.b.set(true);
            recorder.c.set(false);
            recorder.a = 1;
            if (recorder.d == null) {
                recorder.d = new b();
                b bVar = recorder.d;
                bVar.a.submit(bVar.b);
            } else {
                recorder.d.a();
            }
            z = true;
        }
        if (!z) {
            d();
        }
    }
}
