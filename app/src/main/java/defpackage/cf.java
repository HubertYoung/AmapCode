package defpackage;

import android.text.TextUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: cf reason: default package */
/* compiled from: HttpDispatcher */
public final class cf {
    public ca a;
    public volatile boolean b;
    public Set<String> c;
    public Set<String> d;
    public AtomicBoolean e;
    private CopyOnWriteArraySet<a> f;

    /* renamed from: cf$a */
    /* compiled from: HttpDispatcher */
    public interface a {
        void onEvent(cd cdVar);
    }

    /* renamed from: cf$b */
    /* compiled from: HttpDispatcher */
    public static class b {
        public static cf a = new cf(0);
    }

    /* synthetic */ cf(byte b2) {
        this();
    }

    private cf() {
        this.f = new CopyOnWriteArraySet<>();
        this.a = new ca();
        this.b = true;
        this.c = Collections.newSetFromMap(new ConcurrentHashMap());
        this.d = new TreeSet();
        this.e = new AtomicBoolean();
        b();
    }

    public final void a(a aVar) {
        this.f.add(aVar);
    }

    /* access modifiers changed from: 0000 */
    public final void a(cd cdVar) {
        Iterator<a> it = this.f.iterator();
        while (it.hasNext()) {
            try {
                it.next().onEvent(cdVar);
            } catch (Exception unused) {
            }
        }
    }

    public final synchronized Set<String> a() {
        try {
            b();
        }
        return new HashSet(this.d);
    }

    private void b() {
        if (!this.e.get() && m.a() != null && this.e.compareAndSet(false, true)) {
            this.d.add(cb.a());
            if (m.b()) {
                this.d.addAll(Arrays.asList(cb.a));
            }
        }
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean contains = this.c.contains(str);
        if (!contains) {
            this.c.add(str);
        }
        if (!contains) {
            return true;
        }
        return false;
    }
}
