package defpackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/* renamed from: u reason: default package */
/* compiled from: SessionPool */
final class u {
    final Map<v, List<p>> a = new HashMap();
    final WriteLock b = this.c.writeLock();
    private final ReentrantReadWriteLock c = new ReentrantReadWriteLock();
    private final ReadLock d = this.c.readLock();

    u() {
    }

    public final void a(v vVar, p pVar) {
        this.b.lock();
        try {
            List list = this.a.get(vVar);
            if (list != null) {
                list.remove(pVar);
                if (list.size() == 0) {
                    this.a.remove(vVar);
                }
                this.b.unlock();
            }
        } finally {
            this.b.unlock();
        }
    }

    public final List<p> a(v vVar) {
        this.d.lock();
        try {
            List list = this.a.get(vVar);
            if (list != null) {
                ArrayList arrayList = new ArrayList(list);
                return arrayList;
            }
            List<p> list2 = Collections.EMPTY_LIST;
            this.d.unlock();
            return list2;
        } finally {
            this.d.unlock();
        }
    }

    public final p a(v vVar, int i) {
        this.d.lock();
        try {
            List list = this.a.get(vVar);
            p pVar = null;
            if (list != null) {
                if (!list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        p pVar2 = (p) it.next();
                        if (pVar2 != null && pVar2.e()) {
                            if (i == ai.c || pVar2.j.d() == i) {
                                pVar = pVar2;
                            }
                        }
                    }
                    this.d.unlock();
                    return pVar;
                }
            }
            return null;
        } finally {
            this.d.unlock();
        }
    }

    public final List<v> a() {
        List<v> list = Collections.EMPTY_LIST;
        this.d.lock();
        try {
            if (this.a.isEmpty()) {
                return list;
            }
            ArrayList arrayList = new ArrayList(this.a.keySet());
            this.d.unlock();
            return arrayList;
        } finally {
            this.d.unlock();
        }
    }

    public final boolean b(v vVar, p pVar) {
        this.d.lock();
        try {
            List list = this.a.get(vVar);
            boolean z = false;
            if (list != null) {
                if (list.indexOf(pVar) != -1) {
                    z = true;
                }
            }
            return z;
        } finally {
            this.d.unlock();
        }
    }
}
