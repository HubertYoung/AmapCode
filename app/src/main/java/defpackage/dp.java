package defpackage;

import anetwork.channel.cache.Cache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/* renamed from: dp reason: default package */
/* compiled from: CacheManager */
public final class dp {
    private static List<a> a = new ArrayList();
    private static final ReentrantReadWriteLock b;
    private static final ReadLock c;
    private static final WriteLock d = b.writeLock();

    /* renamed from: dp$a */
    /* compiled from: CacheManager */
    static class a implements Comparable<a> {
        final Cache a;
        final dq b;
        final int c = 1;

        public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return this.c - ((a) obj).c;
        }

        a(Cache cache, dq dqVar) {
            this.a = cache;
            this.b = dqVar;
        }
    }

    static {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        b = reentrantReadWriteLock;
        c = reentrantReadWriteLock.readLock();
    }

    public static void a(Cache cache, dq dqVar) {
        try {
            d.lock();
            a.add(new a(cache, dqVar));
            Collections.sort(a);
        } finally {
            d.unlock();
        }
    }

    public static Cache a(Map<String, String> map) {
        try {
            c.lock();
            for (a next : a) {
                if (next.b.a(map)) {
                    return next.a;
                }
            }
            c.unlock();
            return null;
        } finally {
            c.unlock();
        }
    }
}
