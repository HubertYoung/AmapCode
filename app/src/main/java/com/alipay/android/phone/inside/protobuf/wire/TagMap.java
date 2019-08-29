package com.alipay.android.phone.inside.protobuf.wire;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

abstract class TagMap<T> {
    private static final Comparator<? super Entry<Integer, ?>> b = new Comparator<Entry<Integer, ?>>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return ((Integer) ((Entry) obj).getKey()).compareTo((Integer) ((Entry) obj2).getKey());
        }
    };
    List<T> a;

    static final class Compact<T> extends TagMap<T> {
        Object[] b;
        int c = -1;

        public static <T> Compact<T> a(Map<Integer, T> map, int i) {
            return new Compact<>(map, i);
        }

        private Compact(Map<Integer, T> map, int i) {
            super(map);
            this.c = i;
            this.b = new Object[(i + 1)];
            for (Entry next : map.entrySet()) {
                Integer num = (Integer) next.getKey();
                if (num.intValue() <= 0) {
                    throw new IllegalArgumentException("Input map key is negative or zero");
                }
                this.b[num.intValue()] = next.getValue();
            }
        }

        public final T a(int i) {
            if (i > this.c) {
                return null;
            }
            return this.b[i];
        }

        public final boolean b(int i) {
            if (i <= this.c && this.b[i] != null) {
                return true;
            }
            return false;
        }
    }

    static final class Sparse<T> extends TagMap<T> {
        Map<Integer, T> b;

        public static <T> Sparse<T> b(Map<Integer, T> map) {
            return new Sparse<>(map);
        }

        private Sparse(Map<Integer, T> map) {
            super(map);
            this.b = map;
        }

        public final T a(int i) {
            return this.b.get(Integer.valueOf(i));
        }

        public final boolean b(int i) {
            return this.b.containsKey(Integer.valueOf(i));
        }
    }

    public abstract T a(int i);

    public abstract boolean b(int i);

    private static <T> List<T> b(Map<Integer, T> map) {
        TreeSet treeSet = new TreeSet(b);
        treeSet.addAll(map.entrySet());
        ArrayList arrayList = new ArrayList();
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            arrayList.add(((Entry) it.next()).getValue());
        }
        return arrayList;
    }

    protected TagMap(Map<Integer, T> map) {
        this.a = b(map);
    }

    public static <T> TagMap<T> a(Map<Integer, T> map) {
        int i = -1;
        for (Integer intValue : map.keySet()) {
            int intValue2 = intValue.intValue();
            if (intValue2 > i) {
                i = intValue2;
            }
        }
        if (i <= 64 || ((float) map.size()) / ((float) i) > 0.75f) {
            return Compact.a(map, i);
        }
        return Sparse.b(map);
    }
}
