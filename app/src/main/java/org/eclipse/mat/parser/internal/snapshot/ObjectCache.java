package org.eclipse.mat.parser.internal.snapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.mat.collect.HashMapIntObject;

public abstract class ObjectCache<E> {
    private final List<LinkedList<Entry<E>>> lfus;
    private int lowestNonEmptyLfu = 0;
    private final HashMapIntObject<Entry<E>> map;
    private int maxLfuBuckets = 0;
    private int maxSize;

    static class Entry<E> {
        int key;
        int numUsages;
        E object;

        Entry() {
        }
    }

    /* access modifiers changed from: protected */
    public abstract E load(int i);

    public ObjectCache(int i) {
        this.maxSize = i;
        this.map = new HashMapIntObject<>(i);
        this.lfus = new ArrayList(5);
        this.maxLfuBuckets = i / 3;
    }

    public synchronized E get(int i) {
        Entry entry;
        try {
            entry = (Entry) this.map.get(i);
            if (entry != null) {
                revalueEntry(entry);
            } else {
                entry = new Entry();
                entry.object = load(i);
                entry.key = i;
                doInsert(entry);
                while (this.map.size() > this.maxSize) {
                    removeLeastValuableNode();
                }
            }
        }
        return entry.object;
    }

    public synchronized void clear() {
        this.map.clear();
        this.lfus.clear();
    }

    /* access modifiers changed from: protected */
    public synchronized void doInsert(Entry<E> entry) {
        lfu(entry.numUsages).addFirst(entry);
        Entry entry2 = (Entry) this.map.put(entry.key, entry);
        this.lowestNonEmptyLfu = 0;
        if (entry2 != null) {
            lfu(entry2.numUsages).remove(entry2);
        }
    }

    /* access modifiers changed from: protected */
    public final LinkedList<Entry<E>> lfu(int i) {
        int min = Math.min(this.maxLfuBuckets, i);
        if (min < this.lfus.size()) {
            return this.lfus.get(min);
        }
        LinkedList<Entry<E>> linkedList = new LinkedList<>();
        this.lfus.add(min, linkedList);
        return linkedList;
    }

    /* access modifiers changed from: protected */
    public void revalueEntry(Entry<E> entry) {
        LinkedList lfu = lfu(entry.numUsages);
        int i = entry.numUsages + 1;
        entry.numUsages = i;
        LinkedList lfu2 = lfu(i);
        lfu.remove(entry);
        lfu2.addFirst(entry);
    }

    /* access modifiers changed from: protected */
    public LinkedList<Entry<E>> getLowestNonEmptyLfu() {
        LinkedList<Entry<E>> linkedList = null;
        for (int i = this.lowestNonEmptyLfu; i < this.lfus.size(); i++) {
            linkedList = lfu(i);
            if (linkedList.size() != 0) {
                this.lowestNonEmptyLfu = i;
                return linkedList;
            }
        }
        return linkedList;
    }

    /* access modifiers changed from: protected */
    public void removeLeastValuableNode() {
        LinkedList lowestNonEmptyLfu2 = getLowestNonEmptyLfu();
        this.map.remove(((Entry) lowestNonEmptyLfu2.remove(lowestNonEmptyLfu2.size() - 1)).key);
    }
}
