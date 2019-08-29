package org.eclipse.mat.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class HashMapIntObject<E> implements Serializable {
    private static final long serialVersionUID = 2;
    private int capacity;
    /* access modifiers changed from: private */
    public transient int[] keys;
    private int limit;
    /* access modifiers changed from: private */
    public int size;
    private int step;
    /* access modifiers changed from: private */
    public transient boolean[] used;
    /* access modifiers changed from: private */
    public transient E[] values;

    public interface Entry<E> {
        int getKey();

        E getValue();
    }

    public HashMapIntObject() {
        this(10);
    }

    public HashMapIntObject(int i) {
        init(i);
    }

    public final E put(int i, E e) {
        if (this.size == this.limit) {
            resize(this.capacity << 1);
        }
        int i2 = Integer.MAX_VALUE & i;
        int i3 = this.capacity;
        while (true) {
            int i4 = i2 % i3;
            if (!this.used[i4]) {
                this.used[i4] = true;
                this.keys[i4] = i;
                this.values[i4] = e;
                this.size++;
                return null;
            } else if (this.keys[i4] == i) {
                E e2 = this.values[i4];
                this.values[i4] = e;
                return e2;
            } else {
                i2 = i4 + this.step;
                i3 = this.capacity;
            }
        }
    }

    public final E remove(int i) {
        int i2;
        int i3 = i & Integer.MAX_VALUE;
        int i4 = this.capacity;
        while (true) {
            int i5 = i3 % i4;
            if (!this.used[i5]) {
                return null;
            }
            if (this.keys[i5] == i) {
                E e = this.values[i5];
                this.used[i5] = false;
                this.size--;
                int i6 = i5 + this.step;
                int i7 = this.capacity;
                while (true) {
                    int i8 = i6 % i7;
                    if (!this.used[i8]) {
                        return e;
                    }
                    int i9 = this.keys[i8];
                    this.used[i8] = false;
                    int i10 = i9 & Integer.MAX_VALUE;
                    int i11 = this.capacity;
                    while (true) {
                        i2 = i10 % i11;
                        if (!this.used[i2]) {
                            break;
                        }
                        i10 = i2 + this.step;
                        i11 = this.capacity;
                    }
                    this.used[i2] = true;
                    this.keys[i2] = i9;
                    E[] eArr = this.values;
                    eArr[i2] = eArr[i8];
                    i6 = i8 + this.step;
                    i7 = this.capacity;
                }
            } else {
                i3 = i5 + this.step;
                i4 = this.capacity;
            }
        }
    }

    public final boolean containsKey(int i) {
        int i2 = Integer.MAX_VALUE & i;
        int i3 = this.capacity;
        while (true) {
            int i4 = i2 % i3;
            if (!this.used[i4]) {
                return false;
            }
            if (this.keys[i4] == i) {
                return true;
            }
            i2 = i4 + this.step;
            i3 = this.capacity;
        }
    }

    public final E get(int i) {
        int i2 = Integer.MAX_VALUE & i;
        int i3 = this.capacity;
        while (true) {
            int i4 = i2 % i3;
            if (!this.used[i4]) {
                return null;
            }
            if (this.keys[i4] == i) {
                return this.values[i4];
            }
            i2 = i4 + this.step;
            i3 = this.capacity;
        }
    }

    public final int[] getAllKeys() {
        int[] iArr = new int[this.size];
        int i = 0;
        for (int i2 = 0; i2 < this.used.length; i2++) {
            if (this.used[i2]) {
                iArr[i] = this.keys[i2];
                i++;
            }
        }
        return iArr;
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
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.collect.HashMapIntObject.getAllValues(java.lang.Object[]):java.lang.Object[]");
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

    public final IteratorInt keys() {
        return new IteratorInt() {
            int i = -1;
            int n = 0;

            public boolean hasNext() {
                return this.n < HashMapIntObject.this.size;
            }

            public int next() throws NoSuchElementException {
                do {
                    int i2 = this.i + 1;
                    this.i = i2;
                    if (i2 >= HashMapIntObject.this.used.length) {
                        throw new NoSuchElementException();
                    }
                } while (!HashMapIntObject.this.used[this.i]);
                this.n++;
                return HashMapIntObject.this.keys[this.i];
            }
        };
    }

    public final Iterator<E> values() {
        return new Iterator<E>() {
            int i = -1;
            int n = 0;

            public boolean hasNext() {
                return this.n < HashMapIntObject.this.size;
            }

            public E next() throws NoSuchElementException {
                do {
                    int i2 = this.i + 1;
                    this.i = i2;
                    if (i2 >= HashMapIntObject.this.used.length) {
                        throw new NoSuchElementException();
                    }
                } while (!HashMapIntObject.this.used[this.i]);
                this.n++;
                return HashMapIntObject.this.values[this.i];
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
                return this.n < HashMapIntObject.this.size;
            }

            public Entry<E> next() throws NoSuchElementException {
                do {
                    int i2 = this.i + 1;
                    this.i = i2;
                    if (i2 >= HashMapIntObject.this.used.length) {
                        throw new NoSuchElementException();
                    }
                } while (!HashMapIntObject.this.used[this.i]);
                this.n++;
                return new Entry<E>() {
                    public int getKey() {
                        return HashMapIntObject.this.keys[AnonymousClass3.this.i];
                    }

                    public E getValue() {
                        return HashMapIntObject.this.values[AnonymousClass3.this.i];
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
        this.keys = new int[this.capacity];
        this.values = (Object[]) new Object[this.capacity];
    }

    private void resize(int i) {
        int i2;
        int i3 = this.size;
        boolean[] zArr = this.used;
        int[] iArr = this.keys;
        E[] eArr = this.values;
        init(i);
        for (int i4 = 0; i4 < zArr.length; i4++) {
            if (zArr[i4]) {
                int i5 = iArr[i4];
                int i6 = Integer.MAX_VALUE & i5;
                int i7 = this.capacity;
                while (true) {
                    i2 = i6 % i7;
                    if (!this.used[i2]) {
                        break;
                    }
                    i6 = i2 + this.step;
                    i7 = this.capacity;
                }
                this.used[i2] = true;
                this.keys[i2] = i5;
                this.values[i2] = eArr[i4];
            }
        }
        this.size = i3;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.used.length; i++) {
            if (this.used[i]) {
                objectOutputStream.writeInt(this.keys[i]);
                objectOutputStream.writeObject(this.values[i]);
            }
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.step = Math.max(1, PrimeFinder.findPrevPrime(this.capacity / 3));
        this.used = new boolean[this.capacity];
        this.keys = new int[this.capacity];
        this.values = (Object[]) new Object[this.capacity];
        for (int i = 0; i < this.size; i++) {
            putQuick(objectInputStream.readInt(), objectInputStream.readObject());
        }
    }

    private void putQuick(int i, E e) {
        int i2 = Integer.MAX_VALUE & i;
        int i3 = this.capacity;
        while (true) {
            int i4 = i2 % i3;
            if (!this.used[i4]) {
                this.used[i4] = true;
                this.keys[i4] = i;
                this.values[i4] = e;
                return;
            } else if (this.keys[i4] == i) {
                this.values[i4] = e;
                return;
            } else {
                i2 = i4 + this.step;
                i3 = this.capacity;
            }
        }
    }
}
