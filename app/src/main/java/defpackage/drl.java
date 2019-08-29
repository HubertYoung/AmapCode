package defpackage;

import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amap.bundle.logs.AMapLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* renamed from: drl reason: default package */
/* compiled from: ActivityLifeCycleManager */
public final class drl implements drn {
    private static volatile drl g;
    private final List<WeakReference<b>> a = new ArrayList();
    private final List<WeakReference<f>> b = new ArrayList();
    private final List<WeakReference<e>> c = new ArrayList();
    private final List<WeakReference<c>> d = new ArrayList();
    private final List<d> e = new ArrayList();
    private boolean f = false;

    public static drl a() {
        if (g == null) {
            synchronized (drl.class) {
                try {
                    if (g == null) {
                        g = new drl();
                    }
                }
            }
        }
        return g;
    }

    public final void a(@NonNull final a aVar) {
        if (c()) {
            c(aVar);
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    drl.this.c(aVar);
                }
            });
        }
    }

    public final void b(@NonNull final a aVar) {
        if (c()) {
            d(aVar);
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    drl.this.d(aVar);
                }
            });
        }
    }

    public final boolean b() {
        new StringBuilder("ActivityLifeCycleManager:isForeground: ").append(this.f);
        return this.f;
    }

    public final void a(@Nullable Class<?> cls) {
        AMapLog.d("ActivityLifeHook", "--ActivityLifeCycleManager.onCreated :activity: ".concat(String.valueOf(cls)));
        if (cls != null && this.a.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.a)) {
                weakReference.get();
            }
        }
    }

    public final void b(@Nullable Class<?> cls) {
        AMapLog.d("ActivityLifeHook", "--ActivityLifeCycleManager.onStarted :activity: ".concat(String.valueOf(cls)));
        if (cls != null && this.b.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.b)) {
                weakReference.get();
            }
        }
    }

    public final void c(@Nullable Class<?> cls) {
        AMapLog.d("ActivityLifeHook", "--ActivityLifeCycleManager.onResumed :activity: ".concat(String.valueOf(cls)));
        if (cls != null && this.c.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.c)) {
                e eVar = (e) weakReference.get();
                if (eVar != null) {
                    eVar.a();
                }
            }
        }
    }

    public final void d(@Nullable Class<?> cls) {
        AMapLog.d("ActivityLifeHook", "--ActivityLifeCycleManager.onPaused :activity: ".concat(String.valueOf(cls)));
        if (cls != null && this.c.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.c)) {
                e eVar = (e) weakReference.get();
                if (eVar != null) {
                    eVar.b();
                }
            }
        }
    }

    public final void e(@Nullable Class<?> cls) {
        AMapLog.d("ActivityLifeHook", "--ActivityLifeCycleManager.onStopped :activity: ".concat(String.valueOf(cls)));
        if (cls != null && this.b.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.b)) {
                weakReference.get();
            }
        }
    }

    public final void f(@Nullable Class<?> cls) {
        AMapLog.d("ActivityLifeHook", "--ActivityLifeCycleManager.onDestroyed :activity: ".concat(String.valueOf(cls)));
        if (cls != null && this.a.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.a)) {
                weakReference.get();
            }
        }
    }

    public final void g(@Nullable Class<?> cls) {
        this.f = true;
        StringBuilder sb = new StringBuilder("--ActivityLifeCycleManager.onForeground :activity: ");
        sb.append(cls);
        sb.append(" isForeground:");
        sb.append(this.f);
        AMapLog.d("ActivityLifeHook", sb.toString());
        if (cls != null && this.d.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.d)) {
                c cVar = (c) weakReference.get();
                if (cVar != null) {
                    cVar.a();
                }
            }
            drp.b().a = true;
        }
    }

    public final void h(@NonNull Class<?> cls) {
        this.f = false;
        StringBuilder sb = new StringBuilder("--ActivityLifeCycleManager.onBackground :activity: ");
        sb.append(cls);
        sb.append(" isForeground:");
        sb.append(this.f);
        AMapLog.d("ActivityLifeHook", sb.toString());
        if (cls != null && this.d.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.d)) {
                c cVar = (c) weakReference.get();
                if (cVar != null) {
                    cVar.b();
                }
            }
            drp.b().a = false;
        }
    }

    public final void i(@Nullable Class<?> cls) {
        AMapLog.d("ActivityLifeHook", "--ActivityLifeCycleManager.onExit :activity: ".concat(String.valueOf(cls)));
        if (cls != null && this.d.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.d)) {
                c cVar = (c) weakReference.get();
                if (cVar != null) {
                    cVar.c();
                }
            }
        }
    }

    public final void a(@Nullable Class<?> cls, int i, int i2, Intent intent) {
        AMapLog.d("ActivityLifeHook", "--ActivityLifeCycleManager.onActivityResult :activity: ".concat(String.valueOf(cls)));
        if (cls != null && this.e.size() > 0) {
            for (d dVar : new ArrayList(this.e)) {
                if (dVar != null) {
                    dVar.onActivityResult(cls, i, i2, intent);
                }
            }
        }
    }

    private static boolean c() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /* access modifiers changed from: private */
    public void c(@NonNull a aVar) {
        if (aVar instanceof b) {
            a((b) aVar);
        } else if (aVar instanceof e) {
            a((e) aVar);
        } else if (aVar instanceof f) {
            a((f) aVar);
        } else if (aVar instanceof c) {
            a((c) aVar);
        } else {
            if (aVar instanceof d) {
                a((d) aVar);
            }
        }
    }

    /* access modifiers changed from: private */
    public void d(@NonNull a aVar) {
        if (aVar instanceof b) {
            b((b) aVar);
        } else if (aVar instanceof e) {
            b((e) aVar);
        } else if (aVar instanceof f) {
            b((f) aVar);
        } else if (aVar instanceof c) {
            b((c) aVar);
        } else {
            if (aVar instanceof d) {
                b((d) aVar);
            }
        }
    }

    private void a(@NonNull b bVar) {
        for (WeakReference<b> weakReference : this.a) {
            if (bVar == ((b) weakReference.get())) {
                return;
            }
        }
        this.a.add(new WeakReference(bVar));
    }

    private void b(@NonNull b bVar) {
        for (WeakReference next : this.a) {
            if (bVar == ((b) next.get())) {
                this.a.remove(next);
                return;
            }
        }
    }

    private void a(@NonNull f fVar) {
        for (WeakReference<f> weakReference : this.b) {
            if (fVar == ((f) weakReference.get())) {
                return;
            }
        }
        this.b.add(new WeakReference(fVar));
    }

    private void b(@NonNull f fVar) {
        for (WeakReference next : this.b) {
            if (fVar == ((f) next.get())) {
                this.b.remove(next);
                return;
            }
        }
    }

    private void a(@NonNull e eVar) {
        for (WeakReference<e> weakReference : this.c) {
            if (eVar == ((e) weakReference.get())) {
                return;
            }
        }
        this.c.add(new WeakReference(eVar));
    }

    private void b(@NonNull e eVar) {
        for (WeakReference next : this.c) {
            if (eVar == ((e) next.get())) {
                this.c.remove(next);
                return;
            }
        }
    }

    private void a(@NonNull c cVar) {
        for (WeakReference<c> weakReference : this.d) {
            if (cVar == ((c) weakReference.get())) {
                return;
            }
        }
        this.d.add(new WeakReference(cVar));
    }

    private void b(@NonNull c cVar) {
        for (WeakReference next : this.d) {
            if (cVar == ((c) next.get())) {
                this.d.remove(next);
                return;
            }
        }
    }

    private void a(d dVar) {
        if (dVar != null && !this.e.contains(dVar)) {
            this.e.add(dVar);
        }
    }

    private void b(d dVar) {
        this.e.remove(dVar);
    }
}
