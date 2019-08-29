package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/* renamed from: agl reason: default package */
/* compiled from: WeakListenerSet */
public final class agl<T> {
    public LinkedHashMap<Integer, WeakReference<T>> a = new LinkedHashMap<>();
    public final Object b = new Object();

    /* renamed from: agl$a */
    /* compiled from: WeakListenerSet */
    public interface a<T> {
        void onNotify(T t);
    }

    public final void a(@Nullable T t) {
        if (t != null) {
            int hashCode = t.hashCode();
            synchronized (this.b) {
                if (!this.a.containsKey(Integer.valueOf(hashCode))) {
                    this.a.put(Integer.valueOf(hashCode), new WeakReference(t));
                }
            }
        }
    }

    public final void b(@Nullable T t) {
        if (t != null && this.a.size() != 0) {
            int hashCode = t.hashCode();
            synchronized (this.b) {
                if (this.a.containsKey(Integer.valueOf(hashCode))) {
                    this.a.remove(Integer.valueOf(hashCode));
                }
            }
        }
    }

    @NonNull
    public final List<T> a() {
        LinkedHashMap linkedHashMap;
        ArrayList arrayList = new ArrayList();
        if (this.a.size() <= 0) {
            return arrayList;
        }
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        synchronized (this.b) {
            linkedHashMap = new LinkedHashMap(this.a);
        }
        for (Entry entry : linkedHashMap.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            Object obj = ((WeakReference) entry.getValue()).get();
            if (obj != null) {
                arrayList.add(obj);
            } else {
                arrayList2.add(Integer.valueOf(intValue));
            }
        }
        if (arrayList2.size() > 0) {
            synchronized (this.b) {
                for (Integer intValue2 : arrayList2) {
                    this.a.remove(Integer.valueOf(intValue2.intValue()));
                }
            }
        }
        return arrayList;
    }

    public final void a(@Nullable a<T> aVar) {
        LinkedHashMap linkedHashMap;
        if (this.a.size() != 0) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            synchronized (this.b) {
                linkedHashMap = new LinkedHashMap(this.a);
            }
            for (Entry entry : linkedHashMap.entrySet()) {
                int intValue = ((Integer) entry.getKey()).intValue();
                Object obj = ((WeakReference) entry.getValue()).get();
                if (obj != null) {
                    aVar.onNotify(obj);
                } else {
                    arrayList.add(Integer.valueOf(intValue));
                }
            }
            if (arrayList.size() > 0) {
                synchronized (this.b) {
                    for (Integer intValue2 : arrayList) {
                        this.a.remove(Integer.valueOf(intValue2.intValue()));
                    }
                }
            }
        }
    }

    @Nullable
    public final T b() {
        LinkedHashMap linkedHashMap;
        T t = null;
        if (this.a.size() <= 0) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        synchronized (this.b) {
            linkedHashMap = new LinkedHashMap(this.a);
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Entry entry = (Entry) it.next();
            int intValue = ((Integer) entry.getKey()).intValue();
            T t2 = ((WeakReference) entry.getValue()).get();
            if (t2 != null) {
                t = t2;
                break;
            }
            arrayList.add(Integer.valueOf(intValue));
        }
        if (arrayList.size() > 0) {
            synchronized (this.b) {
                for (Integer intValue2 : arrayList) {
                    this.a.remove(Integer.valueOf(intValue2.intValue()));
                }
            }
        }
        return t;
    }
}
