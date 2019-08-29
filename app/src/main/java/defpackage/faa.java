package defpackage;

import java.lang.reflect.Method;

/* renamed from: faa reason: default package */
/* compiled from: CommandWorker */
final class faa implements Runnable {
    final /* synthetic */ Method a;
    final /* synthetic */ Object b;
    final /* synthetic */ Object[] c;
    final /* synthetic */ ezz d;

    faa(ezz ezz, Method method, Object obj, Object[] objArr) {
        this.d = ezz;
        this.a = method;
        this.b = obj;
        this.c = objArr;
    }

    public final void run() {
        try {
            this.a.invoke(this.b, this.c);
        } catch (Exception e) {
            fat.b("CommandWorker", "reflect e: ", e);
        }
    }
}
