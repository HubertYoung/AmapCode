package com.ali.auth.third.core.registry.a;

import com.ali.auth.third.core.registry.ServiceRegistration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class a implements com.ali.auth.third.core.registry.a {
    private Map<Class<?>, List<b>> a = new HashMap();
    private Map<ServiceRegistration, b> b = new HashMap();
    private ReadWriteLock c = new ReentrantReadWriteLock();

    /* renamed from: com.ali.auth.third.core.registry.a.a$a reason: collision with other inner class name */
    static class C0000a implements ServiceRegistration {
        private final String a = UUID.randomUUID().toString();
        private com.ali.auth.third.core.registry.a b;
        private Map<String, String> c;

        public C0000a(com.ali.auth.third.core.registry.a aVar, Map<String, String> map) {
            this.b = aVar;
            this.c = map;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                return this.a.equals(((C0000a) obj).a);
            }
            return false;
        }

        public int hashCode() {
            return (this.a == null ? 0 : this.a.hashCode()) + 31;
        }

        public void setProperties(Map<String, String> map) {
            if (map != null) {
                for (Entry next : map.entrySet()) {
                    if (!(next.getKey() == null || next.getValue() == null)) {
                        this.c.put(next.getKey(), next.getValue());
                    }
                }
            }
        }

        public void unregister() {
            this.b.a(this);
        }
    }

    static class b {
        public Class<?>[] a;
        public Object b;
        public Map<String, String> c;

        b() {
        }
    }

    public ServiceRegistration a(Class<?>[] clsArr, Object obj, Map<String, String> map) {
        if (clsArr == null || clsArr.length == 0 || obj == null) {
            throw new IllegalArgumentException("service types and instance must not be null");
        }
        b bVar = new b();
        bVar.b = obj;
        bVar.a = clsArr;
        bVar.c = Collections.synchronizedMap(new HashMap());
        if (map != null) {
            for (Entry next : map.entrySet()) {
                if (!(next.getKey() == null || next.getValue() == null)) {
                    bVar.c.put(next.getKey(), next.getValue());
                }
            }
        }
        this.c.writeLock().lock();
        try {
            for (Class<?> cls : clsArr) {
                List list = this.a.get(cls);
                if (list == null) {
                    list = new ArrayList(2);
                    this.a.put(cls, list);
                }
                list.add(bVar);
            }
            C0000a aVar = new C0000a(this, bVar.c);
            this.b.put(aVar, bVar);
            return aVar;
        } finally {
            this.c.writeLock().unlock();
        }
    }

    public Object a(ServiceRegistration serviceRegistration) {
        Class<?>[] clsArr;
        if (serviceRegistration == null) {
            return null;
        }
        this.c.writeLock().lock();
        try {
            b remove = this.b.remove(serviceRegistration);
            if (remove == null) {
                return null;
            }
            if (remove.a != null) {
                for (Class<?> cls : remove.a) {
                    List list = this.a.get(cls);
                    Iterator it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next() == remove) {
                                it.remove();
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (list.size() == 0) {
                        this.a.remove(cls);
                    }
                }
            }
            Object obj = remove.b;
            this.c.writeLock().unlock();
            return obj;
        } finally {
            this.c.writeLock().unlock();
        }
    }

    public <T> T a(Class<T> cls, Map<String, String> map) {
        Object obj;
        this.c.readLock().lock();
        try {
            List<b> list = this.a.get(cls);
            if (list != null) {
                if (list.size() != 0) {
                    if (map != null) {
                        if (map.size() != 0) {
                            for (b bVar : list) {
                                boolean z = true;
                                Iterator<Entry<String, String>> it = map.entrySet().iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    Entry next = it.next();
                                    String str = bVar.c.get(next.getKey());
                                    if (str != null) {
                                        if (!str.equals(next.getValue())) {
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                z = false;
                                continue;
                                if (z) {
                                    obj = bVar.b;
                                    T cast = cls.cast(obj);
                                    this.c.readLock().unlock();
                                    return cast;
                                }
                            }
                        }
                    }
                    obj = ((b) list.get(0)).b;
                    T cast2 = cls.cast(obj);
                    this.c.readLock().unlock();
                    return cast2;
                }
            }
            return null;
        } finally {
            this.c.readLock().unlock();
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:28:0x0091=Splitter:B:28:0x0091, B:35:0x00bc=Splitter:B:35:0x00bc} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T[] b(java.lang.Class<T> r9, java.util.Map<java.lang.String, java.lang.String> r10) {
        /*
            r8 = this;
            java.util.concurrent.locks.ReadWriteLock r0 = r8.c
            java.util.concurrent.locks.Lock r0 = r0.readLock()
            r0.lock()
            java.util.Map<java.lang.Class<?>, java.util.List<com.ali.auth.third.core.registry.a.a$b>> r0 = r8.a     // Catch:{ all -> 0x00c3 }
            java.lang.Object r0 = r0.get(r9)     // Catch:{ all -> 0x00c3 }
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x00c3 }
            r1 = 0
            if (r0 == 0) goto L_0x00bc
            int r2 = r0.size()     // Catch:{ all -> 0x00c3 }
            if (r2 != 0) goto L_0x001c
            goto L_0x00bc
        L_0x001c:
            if (r10 == 0) goto L_0x0091
            int r2 = r10.size()     // Catch:{ all -> 0x00c3 }
            if (r2 != 0) goto L_0x0025
            goto L_0x0091
        L_0x0025:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x00c3 }
            int r3 = r0.size()     // Catch:{ all -> 0x00c3 }
            r2.<init>(r3)     // Catch:{ all -> 0x00c3 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x00c3 }
        L_0x0032:
            boolean r3 = r0.hasNext()     // Catch:{ all -> 0x00c3 }
            if (r3 == 0) goto L_0x0079
            java.lang.Object r3 = r0.next()     // Catch:{ all -> 0x00c3 }
            com.ali.auth.third.core.registry.a.a$b r3 = (com.ali.auth.third.core.registry.a.a.b) r3     // Catch:{ all -> 0x00c3 }
            java.util.Set r4 = r10.entrySet()     // Catch:{ all -> 0x00c3 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x00c3 }
        L_0x0046:
            boolean r5 = r4.hasNext()     // Catch:{ all -> 0x00c3 }
            if (r5 == 0) goto L_0x006c
            java.lang.Object r5 = r4.next()     // Catch:{ all -> 0x00c3 }
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5     // Catch:{ all -> 0x00c3 }
            java.util.Map<java.lang.String, java.lang.String> r6 = r3.c     // Catch:{ all -> 0x00c3 }
            java.lang.Object r7 = r5.getKey()     // Catch:{ all -> 0x00c3 }
            java.lang.Object r6 = r6.get(r7)     // Catch:{ all -> 0x00c3 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x00c3 }
            if (r6 == 0) goto L_0x006a
            java.lang.Object r5 = r5.getValue()     // Catch:{ all -> 0x00c3 }
            boolean r5 = r6.equals(r5)     // Catch:{ all -> 0x00c3 }
            if (r5 != 0) goto L_0x0046
        L_0x006a:
            r4 = 0
            goto L_0x006d
        L_0x006c:
            r4 = 1
        L_0x006d:
            if (r4 == 0) goto L_0x0032
            java.lang.Object r3 = r3.b     // Catch:{ all -> 0x00c3 }
            java.lang.Object r3 = r9.cast(r3)     // Catch:{ all -> 0x00c3 }
            r2.add(r3)     // Catch:{ all -> 0x00c3 }
            goto L_0x0032
        L_0x0079:
            int r10 = r2.size()     // Catch:{ all -> 0x00c3 }
            java.lang.Object r9 = java.lang.reflect.Array.newInstance(r9, r10)     // Catch:{ all -> 0x00c3 }
            java.lang.Object[] r9 = (java.lang.Object[]) r9     // Catch:{ all -> 0x00c3 }
            java.lang.Object[] r9 = r2.toArray(r9)     // Catch:{ all -> 0x00c3 }
        L_0x0087:
            java.util.concurrent.locks.ReadWriteLock r10 = r8.c
            java.util.concurrent.locks.Lock r10 = r10.readLock()
            r10.unlock()
            return r9
        L_0x0091:
            int r10 = r0.size()     // Catch:{ all -> 0x00c3 }
            java.lang.Object r10 = java.lang.reflect.Array.newInstance(r9, r10)     // Catch:{ all -> 0x00c3 }
            java.lang.Object[] r10 = (java.lang.Object[]) r10     // Catch:{ all -> 0x00c3 }
            int r2 = r0.size()     // Catch:{ all -> 0x00c3 }
        L_0x009f:
            if (r1 >= r2) goto L_0x00b2
            java.lang.Object r3 = r0.get(r1)     // Catch:{ all -> 0x00c3 }
            com.ali.auth.third.core.registry.a.a$b r3 = (com.ali.auth.third.core.registry.a.a.b) r3     // Catch:{ all -> 0x00c3 }
            java.lang.Object r3 = r3.b     // Catch:{ all -> 0x00c3 }
            java.lang.Object r3 = r9.cast(r3)     // Catch:{ all -> 0x00c3 }
            r10[r1] = r3     // Catch:{ all -> 0x00c3 }
            int r1 = r1 + 1
            goto L_0x009f
        L_0x00b2:
            java.util.concurrent.locks.ReadWriteLock r9 = r8.c
            java.util.concurrent.locks.Lock r9 = r9.readLock()
            r9.unlock()
            return r10
        L_0x00bc:
            java.lang.Object r9 = java.lang.reflect.Array.newInstance(r9, r1)     // Catch:{ all -> 0x00c3 }
            java.lang.Object[] r9 = (java.lang.Object[]) r9     // Catch:{ all -> 0x00c3 }
            goto L_0x0087
        L_0x00c3:
            r9 = move-exception
            java.util.concurrent.locks.ReadWriteLock r10 = r8.c
            java.util.concurrent.locks.Lock r10 = r10.readLock()
            r10.unlock()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.registry.a.a.b(java.lang.Class, java.util.Map):java.lang.Object[]");
    }
}
