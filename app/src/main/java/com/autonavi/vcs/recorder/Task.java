package com.autonavi.vcs.recorder;

import android.os.Message;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Task {
    public int a;
    public int b;
    protected Recorder c;
    protected a d;
    private eqi e = new eqi() {
        public final void a(boolean z) {
            StringBuilder sb = new StringBuilder("------onInitialized/task:");
            sb.append(Task.this.a());
            sb.append("/taskId:");
            sb.append(Task.this.hashCode());
            sb.append("/success:");
            sb.append(z);
            bfh.a("Recorder-Task", sb.toString());
            if (1 == Task.this.a) {
                Task.this.d();
            }
        }

        public final void b(boolean z) {
            StringBuilder sb = new StringBuilder("------onPaused/task:");
            sb.append(Task.this.a());
            sb.append("/taskId:");
            sb.append(Task.this.hashCode());
            sb.append("/success:");
            sb.append(z);
            bfh.a("Recorder-Task", sb.toString());
            if (2 == Task.this.a) {
                Task.this.d();
            }
        }

        public final void c(boolean z) {
            StringBuilder sb = new StringBuilder("-------onClosed/task:");
            sb.append(Task.this.a());
            sb.append("/taskId:");
            sb.append(Task.this.hashCode());
            sb.append("/success:");
            sb.append(z);
            bfh.a("Recorder-Task", sb.toString());
            if (3 == Task.this.a) {
                Task.this.d();
            }
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface TaskLifeConstraint {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TaskTypeConstraint {
    }

    /* access modifiers changed from: protected */
    public abstract String a();

    /* access modifiers changed from: protected */
    public abstract void b();

    public Task(int i, a aVar, Recorder recorder) {
        this.d = aVar;
        this.c = recorder;
        this.b = 1;
        this.a = i;
    }

    public final void c() {
        StringBuilder sb = new StringBuilder(">>> enter/taskName:");
        sb.append(a());
        sb.append("/taskId:");
        sb.append(hashCode());
        sb.append("/recordStatus:");
        sb.append(this.c.a);
        bfh.a("Recorder-Task", sb.toString());
        this.b = 2;
        this.c.e = this.e;
        a aVar = this.d;
        int hashCode = hashCode();
        aVar.sendMessageDelayed(Message.obtain(aVar, hashCode, Integer.valueOf(hashCode)), 1000);
        b();
    }

    public final void d() {
        StringBuilder sb = new StringBuilder("<<< exit/task:");
        sb.append(a());
        sb.append("/taskId:");
        sb.append(hashCode());
        sb.append("/recordStatus:");
        sb.append(this.c.a);
        bfh.a("Recorder-Task", sb.toString());
        this.b = 3;
        this.c.e = null;
        a aVar = this.d;
        int hashCode = hashCode();
        if (aVar.a.get() != null) {
            ((eqm) aVar.a.get()).c(hashCode);
            if (aVar.hasMessages(hashCode)) {
                aVar.removeMessages(hashCode);
            }
        }
    }

    public final void e() {
        StringBuilder sb = new StringBuilder("<<< recycle/task:");
        sb.append(a());
        sb.append("/taskId:");
        sb.append(hashCode());
        sb.append("/recordStatus:");
        sb.append(this.c.a);
        bfh.a("Recorder-Task", sb.toString());
        this.b = 3;
        this.c.e = null;
    }
}
