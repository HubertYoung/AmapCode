package org.eclipse.mat.parser.internal.util;

public class IntStack {
    private int[] data;
    private int size;

    public IntStack() {
        this(16);
    }

    public IntStack(int i) {
        this.data = new int[i];
    }

    public final int pop() {
        int[] iArr = this.data;
        int i = this.size - 1;
        this.size = i;
        return iArr[i];
    }

    public final void push(int i) {
        if (this.size == this.data.length) {
            int[] iArr = new int[(this.data.length << 1)];
            System.arraycopy(this.data, 0, iArr, 0, this.data.length);
            this.data = iArr;
        }
        int[] iArr2 = this.data;
        int i2 = this.size;
        this.size = i2 + 1;
        iArr2[i2] = i;
    }

    public final int peek() {
        return this.data[this.size - 1];
    }

    public final int size() {
        return this.size;
    }

    public final int capacity() {
        return this.data.length;
    }
}
