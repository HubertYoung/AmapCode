package com.alipay.mobile.common.task.pipeline;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.ArrayDeque;
import java.util.List;

public abstract class Pool<T> {
    protected final ArrayDeque<T> freeObjects;
    public final int max;
    public int peak;

    public interface Poolable {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        void reset();
    }

    /* access modifiers changed from: protected */
    public abstract T newObject();

    public Pool() {
        this(16, Integer.MAX_VALUE);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public Pool(int initialCapacity) {
        this(initialCapacity, Integer.MAX_VALUE);
    }

    public Pool(int initialCapacity, int max2) {
        this.freeObjects = new ArrayDeque<>(initialCapacity);
        this.max = max2;
    }

    public T obtain() {
        return this.freeObjects.size() == 0 ? newObject() : this.freeObjects.pop();
    }

    public void free(T object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }
        if (this.freeObjects.size() < this.max) {
            this.freeObjects.add(object);
            this.peak = Math.max(this.peak, this.freeObjects.size());
        }
        if (object instanceof Poolable) {
            ((Poolable) object).reset();
        }
    }

    public void freeAll(List<T> objects) {
        if (objects == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }
        ArrayDeque freeObjects2 = this.freeObjects;
        int max2 = this.max;
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            if (object != null) {
                if (freeObjects2.size() < max2) {
                    freeObjects2.add(object);
                }
                if (object instanceof Poolable) {
                    ((Poolable) object).reset();
                }
            }
        }
        this.peak = Math.max(this.peak, freeObjects2.size());
    }

    public void clear() {
        this.freeObjects.clear();
    }

    public int getFree() {
        return this.freeObjects.size();
    }
}
