package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* renamed from: drp reason: default package */
/* compiled from: PageLifeCycleManager */
public final class drp implements dro {
    public static HashMap<bid, IPageStateListener> b = new HashMap<>();
    public static Handler c = new Handler(Looper.getMainLooper());
    private static volatile drp i;
    public boolean a = false;
    private final List<WeakReference<a>> d = new ArrayList();
    private final List<WeakReference<e>> e = new ArrayList();
    private final List<WeakReference<d>> f = new ArrayList();
    private final List<WeakReference<b>> g = new ArrayList();
    private final List<WeakReference<f>> h = new ArrayList();

    private drp() {
    }

    public static drp b() {
        if (i == null) {
            synchronized (drp.class) {
                try {
                    if (i == null) {
                        i = new drp();
                    }
                }
            }
        }
        return i;
    }

    public final void a(@NonNull final c cVar) {
        if (d()) {
            c(cVar);
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    drp.this.c(cVar);
                }
            });
        }
    }

    public final void b(@NonNull final c cVar) {
        if (d()) {
            d(cVar);
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    drp.this.d(cVar);
                }
            });
        }
    }

    public final void a(@Nullable WeakReference<AbstractBasePage> weakReference) {
        if (this.d.size() > 0) {
            AMapLog.d("PageLifeHook", "--PageLifeCycleManager.onCreated :page: ");
            for (WeakReference weakReference2 : new ArrayList(this.d)) {
                a aVar = (a) weakReference2.get();
                if (aVar != null) {
                    aVar.a(weakReference);
                }
            }
        }
    }

    public final void b(@Nullable WeakReference<AbstractBasePage> weakReference) {
        AMapLog.d("PageLifeHook", "--PageLifeCycleManager.onStarted :page: ");
        if (this.e.size() > 0) {
            for (WeakReference weakReference2 : new ArrayList(this.e)) {
                e eVar = (e) weakReference2.get();
                if (eVar != null) {
                    eVar.a(weakReference);
                }
            }
        }
    }

    public final void c(@Nullable WeakReference<AbstractBasePage> weakReference) {
        AMapLog.d("PageLifeHook", "--PageLifeCycleManager.onResumed :page: ");
        if (this.f.size() > 0) {
            for (WeakReference weakReference2 : new ArrayList(this.f)) {
                d dVar = (d) weakReference2.get();
                if (dVar != null) {
                    dVar.onPageLifeResumed(weakReference);
                }
            }
        }
    }

    public final void d(WeakReference<AbstractBasePage> weakReference) {
        AMapLog.d("PageLifeHook", "--PageLifeCycleManager.onDidResume :page: ");
        if (this.g.size() > 0) {
            for (WeakReference weakReference2 : new ArrayList(this.g)) {
                b bVar = (b) weakReference2.get();
                if (bVar != null) {
                    bVar.a(weakReference);
                }
            }
        }
    }

    public final void e(@Nullable WeakReference<AbstractBasePage> weakReference) {
        AMapLog.d("PageLifeHook", "--PageLifeCycleManager.onPaused :page: ");
        if (this.f.size() > 0) {
            for (WeakReference weakReference2 : new ArrayList(this.f)) {
                d dVar = (d) weakReference2.get();
                if (dVar != null) {
                    dVar.onPageLifePaused(weakReference);
                }
            }
        }
    }

    public final void a() {
        AMapLog.d("PageLifeHook", "--PageLifeCycleManager.onDidPause :page: ");
        if (this.g.size() > 0) {
            for (WeakReference weakReference : new ArrayList(this.g)) {
                weakReference.get();
            }
        }
    }

    public final void f(@Nullable WeakReference<AbstractBasePage> weakReference) {
        AMapLog.d("PageLifeHook", "--PageLifeCycleManager.onStopped :page: ");
        if (this.e.size() > 0) {
            for (WeakReference weakReference2 : new ArrayList(this.e)) {
                e eVar = (e) weakReference2.get();
                if (eVar != null) {
                    eVar.b(weakReference);
                }
            }
        }
    }

    public final void g(@Nullable WeakReference<AbstractBasePage> weakReference) {
        AMapLog.d("PageLifeHook", "--PageLifeCycleManager.onDestroyed :page: ");
        if (this.d.size() > 0) {
            for (WeakReference weakReference2 : new ArrayList(this.d)) {
                a aVar = (a) weakReference2.get();
                if (aVar != null) {
                    aVar.b(weakReference);
                }
            }
        }
    }

    public final void a(@NonNull Class<?> cls) {
        AMapLog.d("PageLifeHook", "--PageLifeCycleManager.onStartPage :page: ");
        for (WeakReference weakReference : new ArrayList(this.h)) {
            f fVar = (f) weakReference.get();
            if (fVar != null) {
                fVar.a(cls);
            }
        }
    }

    private static boolean d() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /* access modifiers changed from: private */
    public void c(@NonNull c cVar) {
        if (cVar instanceof a) {
            a((a) cVar);
        } else if (cVar instanceof d) {
            a((d) cVar);
        } else if (cVar instanceof e) {
            a((e) cVar);
        } else if (cVar instanceof b) {
            a((b) cVar);
        } else {
            if (cVar instanceof f) {
                a((f) cVar);
            }
        }
    }

    /* access modifiers changed from: private */
    public void d(@NonNull c cVar) {
        if (cVar instanceof a) {
            b((a) cVar);
        } else if (cVar instanceof d) {
            b((d) cVar);
        } else if (cVar instanceof e) {
            b((e) cVar);
        } else if (cVar instanceof b) {
            b((b) cVar);
        } else {
            if (cVar instanceof f) {
                b((f) cVar);
            }
        }
    }

    private void a(@NonNull a aVar) {
        for (WeakReference<a> weakReference : this.d) {
            if (aVar == ((a) weakReference.get())) {
                return;
            }
        }
        this.d.add(new WeakReference(aVar));
    }

    private void b(@NonNull a aVar) {
        for (WeakReference next : this.d) {
            if (aVar == ((a) next.get())) {
                this.d.remove(next);
                return;
            }
        }
    }

    private void a(@NonNull e eVar) {
        for (WeakReference<e> weakReference : this.e) {
            if (eVar == ((e) weakReference.get())) {
                return;
            }
        }
        this.e.add(new WeakReference(eVar));
    }

    private void b(@NonNull e eVar) {
        for (WeakReference next : this.e) {
            if (eVar == ((e) next.get())) {
                this.e.remove(next);
                return;
            }
        }
    }

    private void a(@NonNull d dVar) {
        for (WeakReference<d> weakReference : this.f) {
            if (dVar == ((d) weakReference.get())) {
                return;
            }
        }
        this.f.add(new WeakReference(dVar));
    }

    private void b(@NonNull d dVar) {
        for (WeakReference next : this.f) {
            if (dVar == ((d) next.get())) {
                this.f.remove(next);
                return;
            }
        }
    }

    private void a(@NonNull b bVar) {
        for (WeakReference<b> weakReference : this.g) {
            if (bVar == ((b) weakReference.get())) {
                return;
            }
        }
        this.g.add(new WeakReference(bVar));
    }

    private void b(@NonNull b bVar) {
        for (WeakReference next : this.g) {
            if (bVar == ((b) next.get())) {
                this.g.remove(next);
            }
        }
    }

    private void a(f fVar) {
        for (WeakReference<f> weakReference : this.h) {
            if (fVar == ((f) weakReference.get())) {
                return;
            }
        }
        this.h.add(new WeakReference(fVar));
    }

    private void b(f fVar) {
        for (WeakReference next : this.h) {
            if (fVar == ((f) next.get())) {
                this.h.remove(next);
            }
        }
    }

    @Deprecated
    public final void b(final bid bid) {
        if (bid != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                b.remove(bid);
            } else {
                c.post(new Runnable() {
                    public final void run() {
                        drp.b.remove(bid);
                    }
                });
            }
        }
    }

    @Nullable
    public final IPageStateListener a(bid bid) {
        if (bid == null) {
            return null;
        }
        return b.get(bid);
    }
}
