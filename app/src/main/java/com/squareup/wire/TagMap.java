package com.squareup.wire;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

abstract class TagMap<T> {
    private static final Comparator<? super Entry<Integer, ?>> COMPARATOR = new Comparator<Entry<Integer, ?>>() {
        public final int compare(Entry<Integer, ?> entry, Entry<Integer, ?> entry2) {
            return entry.getKey().compareTo(entry2.getKey());
        }
    };
    private static final float RATIO_THRESHOLD = 0.75f;
    private static final int SIZE_THRESHOLD = 64;
    List<T> values;

    static final class Compact<T> extends TagMap<T> {
        Object[] elementsByTag;
        int maxTag = -1;

        public static <T> Compact<T> compactTagMapOf(Map<Integer, T> map, int i) {
            return new Compact<>(map, i);
        }

        private Compact(Map<Integer, T> map, int i) {
            super(map);
            this.maxTag = i;
            this.elementsByTag = new Object[(i + 1)];
            for (Entry next : map.entrySet()) {
                Integer num = (Integer) next.getKey();
                if (num.intValue() <= 0) {
                    throw new IllegalArgumentException("Input map key is negative or zero");
                }
                this.elementsByTag[num.intValue()] = next.getValue();
            }
        }

        public final T get(int i) {
            if (i > this.maxTag) {
                return null;
            }
            return this.elementsByTag[i];
        }

        public final boolean containsKey(int i) {
            if (i <= this.maxTag && this.elementsByTag[i] != null) {
                return true;
            }
            return false;
        }
    }

    static final class Sparse<T> extends TagMap<T> {
        Map<Integer, T> map;

        public static <T> Sparse<T> sparseTagMapOf(Map<Integer, T> map2) {
            return new Sparse<>(map2);
        }

        private Sparse(Map<Integer, T> map2) {
            super(map2);
            this.map = map2;
        }

        public final T get(int i) {
            return this.map.get(Integer.valueOf(i));
        }

        public final boolean containsKey(int i) {
            return this.map.containsKey(Integer.valueOf(i));
        }
    }

    private static boolean isCompact(int i, int i2) {
        return i2 <= 64 || ((float) i) / ((float) i2) > RATIO_THRESHOLD;
    }

    public abstract boolean containsKey(int i);

    public abstract T get(int i);

    public static <T> TagMap<T> of(Map<Integer, T> map) {
        int maxTag = maxTag(map);
        if (isCompact(map.size(), maxTag)) {
            return Compact.compactTagMapOf(map, maxTag);
        }
        return Sparse.sparseTagMapOf(map);
    }

    private static <T> int maxTag(Map<Integer, T> map) {
        int i = -1;
        for (Integer intValue : map.keySet()) {
            int intValue2 = intValue.intValue();
            if (intValue2 > i) {
                i = intValue2;
            }
        }
        return i;
    }

    private static <T> List<T> sortedValues(Map<Integer, T> map) {
        TreeSet treeSet = new TreeSet(COMPARATOR);
        treeSet.addAll(map.entrySet());
        ArrayList arrayList = new ArrayList();
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            arrayList.add(((Entry) it.next()).getValue());
        }
        return arrayList;
    }

    protected TagMap(Map<Integer, T> map) {
        this.values = sortedValues(map);
    }

    public Collection<T> values() {
        return this.values;
    }
}
