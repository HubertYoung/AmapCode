package org.eclipse.mat.collect;

import org.eclipse.mat.hprof.Messages;

public class QueueInt {
    int capacity;
    int[] data;
    int headIdx;
    int size;
    int tailIdx;

    public QueueInt(int i) {
        this.capacity = i;
        this.data = new int[i];
    }

    public final int get() {
        if (this.size == 0) {
            throw new ArrayIndexOutOfBoundsException(Messages.QueueInt_ZeroSizeQueue.pattern);
        }
        int i = this.data[this.headIdx];
        this.headIdx++;
        this.size--;
        if (this.headIdx == this.capacity) {
            this.headIdx = 0;
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void put(int i) {
        if (this.tailIdx == this.capacity) {
            this.tailIdx = 0;
        }
        if (this.size == this.capacity) {
            this.capacity <<= 1;
            int[] iArr = new int[this.capacity];
            int length = this.data.length - this.headIdx;
            System.arraycopy(this.data, this.headIdx, iArr, 0, length);
            if (this.tailIdx > 0) {
                System.arraycopy(this.data, 0, iArr, length, this.tailIdx);
            }
            this.headIdx = 0;
            this.tailIdx = this.data.length;
            this.data = iArr;
        }
        this.data[this.tailIdx] = i;
        this.size++;
        this.tailIdx++;
    }
}
