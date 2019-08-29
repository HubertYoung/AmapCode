package com.amap.bundle.drivecommon.model;

import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class LongDistnceSceneData {
    public List<a> a;
    public int[] b;
    public ArrayList<GeoPoint> c = new ArrayList<>();
    private List<c> d;
    private b e = new b(0);
    private ArrayList<lj> f;

    static class HighWaySortByLength implements Serializable, Comparator {
        private HighWaySortByLength() {
        }

        public int compare(Object obj, Object obj2) {
            a aVar = (a) obj;
            a aVar2 = (a) obj2;
            if (aVar.c > aVar2.c) {
                return -1;
            }
            return aVar.c == aVar2.c ? 0 : 1;
        }
    }

    public static class a {
        public GeoPoint a;
        public String b;
        public int c;
    }

    static class b {
        Set<Integer> a;
        Set<Integer> b;

        private b() {
            this.a = new HashSet();
            this.b = new HashSet();
        }

        /* synthetic */ b(byte b2) {
            this();
        }
    }

    public static class c {
        public int a;
        public GeoPoint b;
        public String c;
    }

    public final List<c> a() {
        if (this.d == null || this.d.size() == 0) {
            d();
        }
        return this.d;
    }

    public final List<c> b() {
        List<c> a2 = a();
        if (a2 == null || a2.isEmpty()) {
            return a2;
        }
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        for (c next : a2) {
            if (next != null && !hashSet.contains(Integer.valueOf(next.a))) {
                arrayList.add(next);
                hashSet.add(Integer.valueOf(next.a));
            }
        }
        return arrayList;
    }

    private void d() {
        int[] iArr;
        this.d = new ArrayList();
        this.f = new ArrayList<>();
        if (this.b != null && this.b.length > 0) {
            for (int i : this.b) {
                lj a2 = li.a().a((int) ((long) i));
                if (a2 != null) {
                    c cVar = new c();
                    cVar.a = i;
                    cVar.b = new GeoPoint(a2.f, a2.g);
                    cVar.c = a2.a;
                    this.d.add(cVar);
                    this.f.add(a2);
                }
            }
        }
    }

    public final void a(int[] iArr) {
        this.b = iArr;
        for (int i : iArr) {
            b bVar = this.e;
            if (i > 100) {
                bVar.b.add(Integer.valueOf((i / 100) * 100));
                bVar.a.add(Integer.valueOf(i));
            }
        }
    }

    public final int c() {
        return this.e.b.size();
    }
}
