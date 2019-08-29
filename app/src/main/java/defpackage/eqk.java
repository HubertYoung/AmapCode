package defpackage;

import com.autonavi.vcs.recorder.Recorder;
import com.autonavi.vcs.recorder.Task;

/* renamed from: eqk reason: default package */
/* compiled from: PauseTask */
public final class eqk extends Task {
    public final String a() {
        return "PauseTask";
    }

    public eqk(int i, a aVar, Recorder recorder) {
        super(i, aVar, recorder);
    }

    public final void b() {
        Recorder recorder = this.c;
        boolean z = false;
        if (!(!Recorder.b() || recorder.a == 4 || recorder.a == 6)) {
            recorder.b.set(false);
            recorder.a = 3;
            if (recorder.d != null) {
                recorder.d.a();
                z = true;
            }
        }
        if (!z) {
            d();
        }
    }
}
