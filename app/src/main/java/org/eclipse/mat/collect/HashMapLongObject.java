package org.eclipse.mat.collect;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class HashMapLongObject<E> implements Serializable {
    private static final long serialVersionUID = 1;
    private int capacity;
    /* access modifiers changed from: private */
    public long[] keys;
    private int limit;
    /* access modifiers changed from: private */
    public int size;
    private int step;
    /* access modifiers changed from: private */
    public boolean[] used;
    /* access modifiers changed from: private */
    public E[] values;

    public interface Entry<E> {
        long getKey();

        E getValue();
    }

    private int hash(long j) {
        return (int) (j & 2147483647L);
    }

    public HashMapLongObject() {
        this(10);
    }

    public HashMapLongObject(int i) {
        init(i);
    }

    public final E put(long j, E e) {
        if (this.size == this.limit) {
            resize(this.capacity << 1);
        }
        int hash = hash(j);
        int i = this.capacity;
        while (true) {
            int i2 = hash % i;
            if (!this.used[i2]) {
                this.used[i2] = true;
                this.keys[i2] = j;
                this.values[i2] = e;
                this.size++;
                return null;
            } else if (this.keys[i2] == j) {
                E e2 = this.values[i2];
                this.values[i2] = e;
                return e2;
            } else {
                hash = i2 + this.step;
                i = this.capacity;
            }
        }
    }

    public final E remove(long j) {
        int i;
        int hash = hash(j);
        int i2 = this.capacity;
        while (true) {
            int i3 = hash % i2;
            if (!this.used[i3]) {
                return null;
            }
            if (this.keys[i3] == j) {
                E e = this.values[i3];
                this.used[i3] = false;
                this.size--;
                int i4 = i3 + this.step;
                int i5 = this.capacity;
                while (true) {
                    int i6 = i4 % i5;
                    if (!this.used[i6]) {
                        return e;
                    }
                    long j2 = this.keys[i6];
                    this.used[i6] = false;
                    int hash2 = hash(j2);
                    int i7 = this.capacity;
                    while (true) {
                        i = hash2 % i7;
                        if (!this.used[i]) {
                            break;
                        }
                        hash2 = i + this.step;
                        i7 = this.capacity;
                    }
                    this.used[i] = true;
                    this.keys[i] = j2;
                    E[] eArr = this.values;
                    eArr[i] = eArr[i6];
                    i4 = i6 + this.step;
                    i5 = this.capacity;
                }
            } else {
                hash = i3 + this.step;
                i2 = this.capacity;
            }
        }
    }

    public final boolean containsKey(long j) {
        int hash = hash(j);
        int i = this.capacity;
        while (true) {
            int i2 = hash % i;
            if (!this.used[i2]) {
                return false;
            }
            if (this.keys[i2] == j) {
                return true;
            }
            hash = i2 + this.step;
            i = this.capacity;
        }
    }

    public final E get(long j) {
        int hash = hash(j);
        int i = this.capacity;
        while (true) {
            int i2 = hash % i;
            if (!this.used[i2]) {
                return null;
            }
            if (this.keys[i2] == j) {
                return this.values[i2];
            }
            hash = i2 + this.step;
            i = this.capacity;
        }
    }

    public final long[] getAllKeys() {
        long[] jArr = new long[this.size];
        int i = 0;
        for (int i2 = 0; i2 < this.used.length; i2++) {
            if (this.used[i2]) {
                jArr[i] = this.keys[i2];
                i++;
            }
        }
        return jArr;
    }

    public final Object[] getAllValues() {
        Object[] objArr = new Object[this.size];
        int i = 0;
        for (int i2 = 0; i2 < this.used.length; i2++) {
            if (this.used[i2]) {
                objArr[i] = this.values[i2];
                i++;
            }
        }
        return objArr;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=T[], code=java.lang.Object[], for r5v0, types: [T[], java.lang.Object[], java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> T[] getAllValues(java.lang.Object[] r5) {
        /*
            r4 = this;
            int r0 = r5.length
            int r1 = r4.size
            if (r0 >= r1) goto L_0x0015
            java.lang.Class r5 = r5.getClass()
            java.lang.Class r5 = r5.getComponentType()
            int r0 = r4.size
            java.lang.Object r5 = java.lang.reflect.Array.newInstance(r5, r0)
            java.lang.Object[] r5 = (java.lang.Object[]) r5
        L_0x0015:
            r0 = 0
            r1 = 0
        L_0x0017:
            boolean[] r2 = r4.used
            int r2 = r2.length
            if (r0 >= r2) goto L_0x002e
            boolean[] r2 = r4.used
            boolean r2 = r2[r0]
            if (r2 == 0) goto L_0x002b
            int r2 = r1 + 1
            E[] r3 = r4.values
            r3 = r3[r0]
            r5[r1] = r3
            r1 = r2
        L_0x002b:
            int r0 = r0 + 1
            goto L_0x0017
        L_0x002e:
            int r0 = r5.length
            int r1 = r4.size
            if (r0 <= r1) goto L_0x0038
            int r0 = r4.size
            r1 = 0
            r5[r0] = r1
        L_0x0038:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.collect.HashMapLongObject.getAllValues(java.lang.Object[]):java.lang.Object[]");
    }

    public final int size() {
        return this.size;
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    public final void clear() {
        this.size = 0;
        this.used = new boolean[this.capacity];
    }

    public final IteratorLong keys() {
        return new IteratorLong() {
            int i = -1;
            int n = 0;

            public boolean hasNext() {
                return this.n < HashMapLongObject.this.size;
            }

            public long next() throws NoSuchElementException {
                do {
                    int i2 = this.i + 1;
                    this.i = i2;
                    if (i2 >= HashMapLongObject.this.used.length) {
                        throw new NoSuchElementException();
                    }
                } while (!HashMapLongObject.this.used[this.i]);
                this.n++;
                return HashMapLongObject.this.keys[this.i];
            }
        };
    }

    public final Iterator<E> values() {
        return new Iterator<E>() {
            int i = -1;
            int n = 0;

            public boolean hasNext() {
                return this.n < HashMapLongObject.this.size;
            }

            public E next() throws NoSuchElementException {
                do {
                    int i2 = this.i + 1;
                    this.i = i2;
                    if (i2 >= HashMapLongObject.this.used.length) {
                        throw new NoSuchElementException();
                    }
                } while (!HashMapLongObject.this.used[this.i]);
                this.n++;
                return HashMapLongObject.this.values[this.i];
            }

            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException();
            }
        };
    }

    public final Iterator<Entry<E>> entries() {
        return new Iterator<Entry<E>>() {
            int i = -1;
            int n = 0;

            public boolean hasNext() {
                return this.n < HashMapLongObject.this.size;
            }

            public Entry<E> next() throws NoSuchElementException {
                do {
                    int i2 = this.i + 1;
                    this.i = i2;
                    if (i2 >= HashMapLongObject.this.used.length) {
                        throw new NoSuchElementException();
                    }
                } while (!HashMapLongObject.this.used[this.i]);
                this.n++;
                return new Entry<E>() {
                    public long getKey() {
                        return HashMapLongObject.this.keys[AnonymousClass3.this.i];
                    }

                    public E getValue() {
                        return HashMapLongObject.this.values[AnonymousClass3.this.i];
                    }
                };
            }

            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void init(int i) {
        this.capacity = PrimeFinder.findNextPrime(i);
        this.step = Math.max(1, PrimeFinder.findPrevPrime(i / 3));
        this.limit = (int) (((double) this.capacity) * 0.75d);
        clear();
        this.keys = new long[this.capacity];
        this.values = (Object[]) new Object[this.capacity];
    }

    private void resize(int i) {
        int i2;
        int i3 = this.size;
        boolean[] zArr = this.used;
        long[] jArr = this.keys;
        E[] eArr = this.values;
        init(i);
        for (int i4 = 0; i4 < zArr.length; i4++) {
            if (zArr[i4]) {
                long j = jArr[i4];
                int hash = hash(j);
                int i5 = this.capacity;
                while (true) {
                    i2 = hash % i5;
                    if (!this.used[i2]) {
                        break;
                    }
                    hash = i2 + this.step;
                    i5 = this.capacity;
                }
                this.used[i2] = true;
                this.keys[i2] = j;
                this.values[i2] = eArr[i4];
            }
        }
        this.size = i3;
    }
}
