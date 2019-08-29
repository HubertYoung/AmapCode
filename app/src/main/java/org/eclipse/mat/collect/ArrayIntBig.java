package org.eclipse.mat.collect;

import com.taobao.accs.data.Message;
import java.io.Serializable;
import java.util.ArrayList;

public final class ArrayIntBig implements Serializable {
    private static final long serialVersionUID = 1;
    private int length = 0;
    private int[] page;
    private ArrayList<int[]> pages = new ArrayList<>();

    public final void add(int i) {
        int i2 = this.length;
        this.length = i2 + 1;
        int i3 = i2 & Message.EXT_HEADER_VALUE_MAX_LEN;
        if (i3 == 0) {
            ArrayList<int[]> arrayList = this.pages;
            int[] iArr = new int[1024];
            this.page = iArr;
            arrayList.add(iArr);
        }
        this.page[i3] = i;
    }

    public final void addAll(int[] iArr) {
        int i;
        int i2 = this.length & Message.EXT_HEADER_VALUE_MAX_LEN;
        if (i2 == 0) {
            i = 0;
        } else {
            i = Math.min(iArr.length, 1024 - i2);
        }
        if (i > 0) {
            System.arraycopy(iArr, 0, this.pages.get(this.length >> 10), this.length & Message.EXT_HEADER_VALUE_MAX_LEN, i);
            this.length += i;
        }
        while (i < iArr.length) {
            ArrayList<int[]> arrayList = this.pages;
            int[] iArr2 = new int[1024];
            this.page = iArr2;
            arrayList.add(iArr2);
            int min = Math.min(iArr.length - i, 1024);
            System.arraycopy(iArr, i, this.page, 0, min);
            i += min;
            this.length += min;
        }
    }

    public final int get(int i) throws IndexOutOfBoundsException {
        if (i < this.length) {
            return this.pages.get(i >> 10)[i & Message.EXT_HEADER_VALUE_MAX_LEN];
        }
        throw new IndexOutOfBoundsException();
    }

    public final int length() {
        return this.length;
    }

    public final boolean isEmpty() {
        return this.length == 0;
    }

    public final long consumption() {
        return ((long) this.pages.size()) << 12;
    }

    public final int[] toArray() {
        int[] iArr = new int[this.length];
        int i = 0;
        while (i < this.length) {
            int min = Math.min(this.length - i, 1024);
            System.arraycopy(this.pages.get(i >> 10), 0, iArr, i, min);
            i += min;
        }
        return iArr;
    }
}
