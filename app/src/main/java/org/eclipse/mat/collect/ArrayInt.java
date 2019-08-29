package org.eclipse.mat.collect;

import java.util.Arrays;

public final class ArrayInt {
    int[] elements;
    int size;

    public ArrayInt() {
        this(10);
    }

    public ArrayInt(int i) {
        this.elements = new int[i];
        this.size = 0;
    }

    public ArrayInt(int[] iArr) {
        this(iArr.length);
        System.arraycopy(iArr, 0, this.elements, 0, iArr.length);
        this.size = iArr.length;
    }

    public ArrayInt(ArrayInt arrayInt) {
        this(arrayInt.size);
        System.arraycopy(arrayInt.elements, 0, this.elements, 0, arrayInt.size);
        this.size = arrayInt.size;
    }

    public final void add(int i) {
        ensureCapacity(this.size + 1);
        int[] iArr = this.elements;
        int i2 = this.size;
        this.size = i2 + 1;
        iArr[i2] = i;
    }

    public final void addAll(int[] iArr) {
        ensureCapacity(this.size + iArr.length);
        System.arraycopy(iArr, 0, this.elements, this.size, iArr.length);
        this.size += iArr.length;
    }

    public final void addAll(ArrayInt arrayInt) {
        ensureCapacity(this.size + arrayInt.size);
        System.arraycopy(arrayInt.elements, 0, this.elements, this.size, arrayInt.size);
        this.size += arrayInt.size;
    }

    public final int set(int i, int i2) {
        if (i < 0 || i >= this.size) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        int i3 = this.elements[i];
        this.elements[i] = i2;
        return i3;
    }

    public final int get(int i) {
        if (i >= 0 && i < this.size) {
            return this.elements[i];
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    public final int size() {
        return this.size;
    }

    public final int[] toArray() {
        int[] iArr = new int[this.size];
        System.arraycopy(this.elements, 0, iArr, 0, this.size);
        return iArr;
    }

    public final boolean isEmpty() {
        return this.size == 0;
    }

    public final IteratorInt iterator() {
        return new IteratorInt() {
            int index = 0;

            public boolean hasNext() {
                return this.index < ArrayInt.this.size;
            }

            public int next() {
                int[] iArr = ArrayInt.this.elements;
                int i = this.index;
                this.index = i + 1;
                return iArr[i];
            }
        };
    }

    public final void clear() {
        this.size = 0;
    }

    public final long lastElement() {
        return (long) this.elements[this.size - 1];
    }

    public final long firstElement() {
        if (this.size != 0) {
            return (long) this.elements[0];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public final void sort() {
        Arrays.sort(this.elements, 0, this.size);
    }

    private void ensureCapacity(int i) {
        int length = this.elements.length;
        if (i > length) {
            int[] iArr = this.elements;
            int i2 = ((length * 3) / 2) + 1;
            if (i2 >= i) {
                i = i2;
            }
            this.elements = new int[i];
            System.arraycopy(iArr, 0, this.elements, 0, this.size);
        }
    }
}
