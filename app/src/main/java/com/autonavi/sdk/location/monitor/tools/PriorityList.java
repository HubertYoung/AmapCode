package com.autonavi.sdk.location.monitor.tools;

import java.util.Iterator;
import java.util.LinkedList;

public class PriorityList<E> extends LinkedList<E> {
    private int mMaxSize;

    public PriorityList(int i) {
        this.mMaxSize = i;
    }

    public boolean add(E e) {
        if (size() >= this.mMaxSize) {
            poll();
        }
        if (e != null) {
            return super.add(e);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = iterator();
        while (it.hasNext()) {
            Object next = it.next();
            StringBuilder sb2 = new StringBuilder("\n");
            sb2.append(next.toString());
            sb.append(sb2.toString());
        }
        return sb.toString();
    }
}
