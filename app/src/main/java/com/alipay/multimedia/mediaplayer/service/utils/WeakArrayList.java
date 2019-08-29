package com.alipay.multimedia.mediaplayer.service.utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeakArrayList<T> {
    private ArrayList<WeakReference<T>> mList = new ArrayList<>(10);

    public void add(T obj) {
        this.mList.add(new WeakReference(obj));
    }

    public void add(int index, T obj) {
        this.mList.add(index, new WeakReference(obj));
    }

    public void remove(T obj) {
        Iterator iterator = this.mList.iterator();
        while (iterator.hasNext()) {
            Object v = iterator.next().get();
            if (obj.equals(v)) {
                iterator.remove();
                return;
            } else if (v == null) {
                iterator.remove();
            }
        }
    }

    public void remove(int index) {
        this.mList.remove(index);
    }

    public boolean contains(T obj) {
        Iterator iterator = this.mList.iterator();
        while (iterator.hasNext()) {
            Object v = iterator.next().get();
            if (obj.equals(v)) {
                return true;
            }
            if (v == null) {
                iterator.remove();
            }
        }
        return false;
    }

    public List<T> values() {
        List values = new ArrayList();
        Iterator iterator = this.mList.iterator();
        while (iterator.hasNext()) {
            Object v = iterator.next().get();
            if (v != null) {
                values.add(v);
            } else {
                iterator.remove();
            }
        }
        return values;
    }

    public int size() {
        return this.mList.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.mList.size(); i++) {
            sb.append(i).append('-').append(this.mList.get(i).get()).append(',');
        }
        return sb.append(']').toString();
    }
}
