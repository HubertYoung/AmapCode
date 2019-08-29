package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.vcs.recorder.Recorder;
import com.autonavi.vcs.recorder.Task;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: eqm reason: default package */
/* compiled from: TaskScheduler */
public final class eqm {
    CopyOnWriteArrayList<Task> a = new CopyOnWriteArrayList<>();
    private a b = new a(this);
    private Recorder c;

    /* renamed from: eqm$a */
    /* compiled from: TaskScheduler */
    public static class a extends Handler {
        public WeakReference<eqm> a;

        protected a(eqm eqm) {
            super(Looper.getMainLooper());
            this.a = new WeakReference<>(eqm);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            bfh.a("Recorder-TaskScheduler", "===> onTimeout/task: ".concat(String.valueOf(i)));
            if (this.a.get() != null) {
                CopyOnWriteArrayList<Task> copyOnWriteArrayList = ((eqm) this.a.get()).a;
                Task task = copyOnWriteArrayList.isEmpty() ? null : copyOnWriteArrayList.get(copyOnWriteArrayList.size() - 1);
                boolean z = false;
                if (task != null && i == task.hashCode()) {
                    z = true;
                }
                Iterator<Task> it = copyOnWriteArrayList.iterator();
                while (it.hasNext()) {
                    Task next = it.next();
                    if (next != null) {
                        if (task != null && next.hashCode() != task.hashCode()) {
                            next.d();
                        } else if (z) {
                            next.d();
                        }
                    }
                }
            }
        }
    }

    public eqm(Recorder recorder) {
        this.c = recorder;
    }

    public final void a(int i) {
        if (e(i)) {
            StringBuilder sb = new StringBuilder(">>> addTask: ");
            sb.append(i);
            sb.append("/existType");
            bfh.a("Recorder-TaskScheduler", sb.toString());
            return;
        }
        this.a.add(d(i));
        a();
    }

    public final void b(int i) {
        Iterator<Task> it = this.a.iterator();
        while (it.hasNext()) {
            Task next = it.next();
            if (next != null && i == next.a) {
                next.e();
                this.a.remove(next);
            }
        }
    }

    private Task d(int i) {
        if (1 == i) {
            return new eqj(i, this.b, this.c);
        }
        if (2 == i) {
            return new eqk(i, this.b, this.c);
        }
        if (3 == i) {
            return new eqh(i, this.b, this.c);
        }
        return null;
    }

    private void a() {
        if (!this.a.isEmpty()) {
            StringBuilder sb = new StringBuilder("===> Queue.size: ");
            sb.append(this.a.size());
            bfh.a("Recorder-TaskScheduler", sb.toString());
            Task task = this.a.get(0);
            if (task != null && 1 == task.b) {
                task.c();
            }
        }
    }

    public final void c(int i) {
        Iterator<Task> it = this.a.iterator();
        while (it.hasNext()) {
            Task next = it.next();
            if (next != null && i == next.hashCode() && 3 == next.b) {
                this.a.remove(next);
            }
        }
        a();
    }

    private boolean e(int i) {
        Iterator<Task> it = this.a.iterator();
        while (it.hasNext()) {
            Task next = it.next();
            if (next != null && next.a == i) {
                return true;
            }
        }
        return false;
    }
}
