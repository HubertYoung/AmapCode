package org.eclipse.mat.collect;

import java.io.Serializable;
import java.util.NoSuchElementException;

public final class SetInt implements Serializable {
    private static final long serialVersionUID = 1;
    private int capacity;
    /* access modifiers changed from: private */
    public int[] keys;
    private int limit;
    /* access modifiers changed from: private */
    public int size;
    private int step;
    /* access modifiers changed from: private */
    public boolean[] used;

    public SetInt() {
        this(10);
    }

    public SetInt(int i) {
        init(i);
    }

    public final boolean add(int i) {
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
                this.size++;
                return true;
            } else if (this.keys[i4] == i) {
                return false;
            } else {
                i2 = i4 + this.step;
                i3 = this.capacity;
            }
        }
    }

    public final boolean remove(int i) {
        int i2;
        int i3 = i & Integer.MAX_VALUE;
        int i4 = this.capacity;
        while (true) {
            int i5 = i3 % i4;
            if (!this.used[i5]) {
                return false;
            }
            if (this.keys[i5] == i) {
                this.used[i5] = false;
                this.size--;
                int i6 = i5 + this.step;
                int i7 = this.capacity;
                while (true) {
                    int i8 = i6 % i7;
                    if (!this.used[i8]) {
                        return true;
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
                    i6 = i8 + this.step;
                    i7 = this.capacity;
                }
            } else {
                i3 = i5 + this.step;
                i4 = this.capacity;
            }
        }
    }

    public final boolean contains(int i) {
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

    public final IteratorInt iterator() {
        return new IteratorInt() {
            int i = -1;
            int n = 0;

            public boolean hasNext() {
                return this.n < SetInt.this.size;
            }

            public int next() throws NoSuchElementException {
                do {
                    int i2 = this.i + 1;
                    this.i = i2;
                    if (i2 >= SetInt.this.used.length) {
                        throw new NoSuchElementException();
                    }
                } while (!SetInt.this.used[this.i]);
                this.n++;
                return SetInt.this.keys[this.i];
            }
        };
    }

    public final int[] toArray() {
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

    private void init(int i) {
        this.capacity = PrimeFinder.findNextPrime(i);
        this.step = Math.max(1, PrimeFinder.findPrevPrime(i / 3));
        this.limit = (int) (((double) this.capacity) * 0.75d);
        clear();
        this.keys = new int[this.capacity];
    }

    private void resize(int i) {
        int i2;
        int i3 = this.size;
        boolean[] zArr = this.used;
        int[] iArr = this.keys;
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
            }
        }
        this.size = i3;
    }
}
