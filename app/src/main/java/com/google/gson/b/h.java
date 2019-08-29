package com.google.gson.b;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: LinkedTreeMap */
public final class h<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean f = true;
    private static final Comparator<Comparable> g = new Comparator<Comparable>() {
        /* renamed from: a */
        public final int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    };
    Comparator<? super K> a;
    d<K, V> b;
    int c;
    int d;
    final d<K, V> e;
    private a h;
    private b i;

    /* compiled from: LinkedTreeMap */
    class a extends AbstractSet<Entry<K, V>> {
        a() {
        }

        public int size() {
            return h.this.c;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new c<Entry<K, V>>() {
                {
                    h hVar = h.this;
                }

                /* renamed from: a */
                public Entry<K, V> next() {
                    return b();
                }
            };
        }

        public boolean contains(Object obj) {
            return (obj instanceof Entry) && h.this.a((Entry) obj) != null;
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            d a2 = h.this.a((Entry) obj);
            if (a2 == null) {
                return false;
            }
            h.this.a(a2, true);
            return true;
        }

        public void clear() {
            h.this.clear();
        }
    }

    /* compiled from: LinkedTreeMap */
    final class b extends AbstractSet<K> {
        b() {
        }

        public final int size() {
            return h.this.c;
        }

        public final Iterator<K> iterator() {
            return new c<K>() {
                {
                    h hVar = h.this;
                }

                public K next() {
                    return b().f;
                }
            };
        }

        public final boolean contains(Object obj) {
            return h.this.containsKey(obj);
        }

        public final boolean remove(Object obj) {
            return h.this.b(obj) != null;
        }

        public final void clear() {
            h.this.clear();
        }
    }

    /* compiled from: LinkedTreeMap */
    abstract class c<T> implements Iterator<T> {
        d<K, V> b = h.this.e.d;
        d<K, V> c = null;
        int d = h.this.d;

        c() {
        }

        public final boolean hasNext() {
            return this.b != h.this.e;
        }

        /* access modifiers changed from: 0000 */
        public final d<K, V> b() {
            d<K, V> dVar = this.b;
            if (dVar == h.this.e) {
                throw new NoSuchElementException();
            } else if (h.this.d != this.d) {
                throw new ConcurrentModificationException();
            } else {
                this.b = dVar.d;
                this.c = dVar;
                return dVar;
            }
        }

        public final void remove() {
            if (this.c == null) {
                throw new IllegalStateException();
            }
            h.this.a(this.c, true);
            this.c = null;
            this.d = h.this.d;
        }
    }

    /* compiled from: LinkedTreeMap */
    static final class d<K, V> implements Entry<K, V> {
        d<K, V> a;
        d<K, V> b;
        d<K, V> c;
        d<K, V> d;
        d<K, V> e;
        final K f;
        V g;
        int h;

        d() {
            this.f = null;
            this.e = this;
            this.d = this;
        }

        d(d<K, V> dVar, K k, d<K, V> dVar2, d<K, V> dVar3) {
            this.a = dVar;
            this.f = k;
            this.h = 1;
            this.d = dVar2;
            this.e = dVar3;
            dVar3.d = this;
            dVar2.e = this;
        }

        public final K getKey() {
            return this.f;
        }

        public final V getValue() {
            return this.g;
        }

        public final V setValue(V v) {
            V v2 = this.g;
            this.g = v;
            return v2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (this.f != null ? this.f.equals(entry.getKey()) : entry.getKey() == null) {
                if (this.g != null ? this.g.equals(entry.getValue()) : entry.getValue() == null) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = this.f == null ? 0 : this.f.hashCode();
            if (this.g != null) {
                i = this.g.hashCode();
            }
            return hashCode ^ i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.f);
            sb.append("=");
            sb.append(this.g);
            return sb.toString();
        }

        public final d<K, V> a() {
            d dVar = this;
            for (d dVar2 = this.b; dVar2 != null; dVar2 = dVar2.b) {
                dVar = dVar2;
            }
            return dVar;
        }

        public final d<K, V> b() {
            d dVar = this;
            for (d dVar2 = this.c; dVar2 != null; dVar2 = dVar2.c) {
                dVar = dVar2;
            }
            return dVar;
        }
    }

    public h() {
        this(g);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Comparator<? super K>, code=java.util.Comparator, for r2v0, types: [java.util.Comparator<? super K>, java.util.Comparator] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public h(java.util.Comparator r2) {
        /*
            r1 = this;
            r1.<init>()
            r0 = 0
            r1.c = r0
            r1.d = r0
            com.google.gson.b.h$d r0 = new com.google.gson.b.h$d
            r0.<init>()
            r1.e = r0
            if (r2 == 0) goto L_0x0012
            goto L_0x0014
        L_0x0012:
            java.util.Comparator<java.lang.Comparable> r2 = g
        L_0x0014:
            r1.a = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.b.h.<init>(java.util.Comparator):void");
    }

    public final int size() {
        return this.c;
    }

    public final V get(Object obj) {
        d a2 = a(obj);
        if (a2 != null) {
            return a2.g;
        }
        return null;
    }

    public final boolean containsKey(Object obj) {
        return a(obj) != null;
    }

    public final V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        d a2 = a(k, true);
        V v2 = a2.g;
        a2.g = v;
        return v2;
    }

    public final void clear() {
        this.b = null;
        this.c = 0;
        this.d++;
        d<K, V> dVar = this.e;
        dVar.e = dVar;
        dVar.d = dVar;
    }

    public final V remove(Object obj) {
        d b2 = b(obj);
        if (b2 != null) {
            return b2.g;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final d<K, V> a(K k, boolean z) {
        int i2;
        d<K, V> dVar;
        d<K, V> dVar2;
        Comparator<? super K> comparator = this.a;
        d<K, V> dVar3 = this.b;
        if (dVar3 != null) {
            Comparable comparable = comparator == g ? (Comparable) k : null;
            while (true) {
                if (comparable != null) {
                    i2 = comparable.compareTo(dVar3.f);
                } else {
                    i2 = comparator.compare(k, dVar3.f);
                }
                if (i2 != 0) {
                    if (i2 < 0) {
                        dVar2 = dVar3.b;
                    } else {
                        dVar2 = dVar3.c;
                    }
                    if (dVar2 == null) {
                        break;
                    }
                    dVar3 = dVar2;
                } else {
                    return dVar3;
                }
            }
        } else {
            i2 = 0;
        }
        if (!z) {
            return null;
        }
        d<K, V> dVar4 = this.e;
        if (dVar3 != null) {
            dVar = new d<>(dVar3, k, dVar4, dVar4.e);
            if (i2 < 0) {
                dVar3.b = dVar;
            } else {
                dVar3.c = dVar;
            }
            b(dVar3, true);
        } else if (comparator != g || (k instanceof Comparable)) {
            dVar = new d<>(dVar3, k, dVar4, dVar4.e);
            this.b = dVar;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(k.getClass().getName());
            sb.append(" is not Comparable");
            throw new ClassCastException(sb.toString());
        }
        this.c++;
        this.d++;
        return dVar;
    }

    /* access modifiers changed from: 0000 */
    public final d<K, V> a(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return a((K) obj, false);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final d<K, V> a(Entry<?, ?> entry) {
        d<K, V> a2 = a((Object) entry.getKey());
        if (a2 != null && a((Object) a2.g, (Object) entry.getValue())) {
            return a2;
        }
        return null;
    }

    private boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    /* access modifiers changed from: 0000 */
    public final void a(d<K, V> dVar, boolean z) {
        int i2;
        if (z) {
            dVar.e.d = dVar.d;
            dVar.d.e = dVar.e;
        }
        d<K, V> dVar2 = dVar.b;
        d<K, V> dVar3 = dVar.c;
        d<K, V> dVar4 = dVar.a;
        int i3 = 0;
        if (dVar2 == null || dVar3 == null) {
            if (dVar2 != null) {
                a(dVar, dVar2);
                dVar.b = null;
            } else if (dVar3 != null) {
                a(dVar, dVar3);
                dVar.c = null;
            } else {
                a(dVar, (d<K, V>) null);
            }
            b(dVar4, false);
            this.c--;
            this.d++;
            return;
        }
        d<K, V> b2 = dVar2.h > dVar3.h ? dVar2.b() : dVar3.a();
        a(b2, false);
        d<K, V> dVar5 = dVar.b;
        if (dVar5 != null) {
            i2 = dVar5.h;
            b2.b = dVar5;
            dVar5.a = b2;
            dVar.b = null;
        } else {
            i2 = 0;
        }
        d<K, V> dVar6 = dVar.c;
        if (dVar6 != null) {
            i3 = dVar6.h;
            b2.c = dVar6;
            dVar6.a = b2;
            dVar.c = null;
        }
        b2.h = Math.max(i2, i3) + 1;
        a(dVar, b2);
    }

    /* access modifiers changed from: 0000 */
    public final d<K, V> b(Object obj) {
        d<K, V> a2 = a(obj);
        if (a2 != null) {
            a(a2, true);
        }
        return a2;
    }

    private void a(d<K, V> dVar, d<K, V> dVar2) {
        d<K, V> dVar3 = dVar.a;
        dVar.a = null;
        if (dVar2 != null) {
            dVar2.a = dVar3;
        }
        if (dVar3 == null) {
            this.b = dVar2;
        } else if (dVar3.b == dVar) {
            dVar3.b = dVar2;
        } else if (f || dVar3.c == dVar) {
            dVar3.c = dVar2;
        } else {
            throw new AssertionError();
        }
    }

    private void b(d<K, V> dVar, boolean z) {
        while (dVar != null) {
            d<K, V> dVar2 = dVar.b;
            d<K, V> dVar3 = dVar.c;
            int i2 = 0;
            int i3 = dVar2 != null ? dVar2.h : 0;
            int i4 = dVar3 != null ? dVar3.h : 0;
            int i5 = i3 - i4;
            if (i5 == -2) {
                d<K, V> dVar4 = dVar3.b;
                d<K, V> dVar5 = dVar3.c;
                int i6 = dVar5 != null ? dVar5.h : 0;
                if (dVar4 != null) {
                    i2 = dVar4.h;
                }
                int i7 = i2 - i6;
                if (i7 == -1 || (i7 == 0 && !z)) {
                    a(dVar);
                } else if (f || i7 == 1) {
                    b(dVar3);
                    a(dVar);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    break;
                }
            } else if (i5 == 2) {
                d<K, V> dVar6 = dVar2.b;
                d<K, V> dVar7 = dVar2.c;
                int i8 = dVar7 != null ? dVar7.h : 0;
                if (dVar6 != null) {
                    i2 = dVar6.h;
                }
                int i9 = i2 - i8;
                if (i9 == 1 || (i9 == 0 && !z)) {
                    b(dVar);
                } else if (f || i9 == -1) {
                    a(dVar2);
                    b(dVar);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    break;
                }
            } else if (i5 == 0) {
                dVar.h = i3 + 1;
                if (z) {
                    return;
                }
            } else if (f || i5 == -1 || i5 == 1) {
                dVar.h = Math.max(i3, i4) + 1;
                if (!z) {
                    break;
                }
            } else {
                throw new AssertionError();
            }
            dVar = dVar.a;
        }
    }

    private void a(d<K, V> dVar) {
        d<K, V> dVar2 = dVar.b;
        d<K, V> dVar3 = dVar.c;
        d<K, V> dVar4 = dVar3.b;
        d<K, V> dVar5 = dVar3.c;
        dVar.c = dVar4;
        if (dVar4 != null) {
            dVar4.a = dVar;
        }
        a(dVar, dVar3);
        dVar3.b = dVar;
        dVar.a = dVar3;
        int i2 = 0;
        dVar.h = Math.max(dVar2 != null ? dVar2.h : 0, dVar4 != null ? dVar4.h : 0) + 1;
        int i3 = dVar.h;
        if (dVar5 != null) {
            i2 = dVar5.h;
        }
        dVar3.h = Math.max(i3, i2) + 1;
    }

    private void b(d<K, V> dVar) {
        d<K, V> dVar2 = dVar.b;
        d<K, V> dVar3 = dVar.c;
        d<K, V> dVar4 = dVar2.b;
        d<K, V> dVar5 = dVar2.c;
        dVar.b = dVar5;
        if (dVar5 != null) {
            dVar5.a = dVar;
        }
        a(dVar, dVar2);
        dVar2.c = dVar;
        dVar.a = dVar2;
        int i2 = 0;
        dVar.h = Math.max(dVar3 != null ? dVar3.h : 0, dVar5 != null ? dVar5.h : 0) + 1;
        int i3 = dVar.h;
        if (dVar4 != null) {
            i2 = dVar4.h;
        }
        dVar2.h = Math.max(i3, i2) + 1;
    }

    public final Set<Entry<K, V>> entrySet() {
        a aVar = this.h;
        if (aVar != null) {
            return aVar;
        }
        a aVar2 = new a<>();
        this.h = aVar2;
        return aVar2;
    }

    public final Set<K> keySet() {
        b bVar = this.i;
        if (bVar != null) {
            return bVar;
        }
        b bVar2 = new b<>();
        this.i = bVar2;
        return bVar2;
    }
}
