package com.squareup.wire;

import com.alipay.sdk.util.h;
import com.squareup.wire.ExtendableMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class ExtensionMap<T extends ExtendableMessage<?>> {
    private static final int INITIAL_SIZE = 1;
    private Object[] data;
    private int size;

    public <E> ExtensionMap(Extension<T, E> extension, E e) {
        this.data = new Object[2];
        this.data[0] = extension;
        this.data[1] = e;
        this.size = 1;
    }

    public ExtensionMap(ExtensionMap<T> extensionMap) {
        this.data = (Object[]) extensionMap.data.clone();
        this.size = extensionMap.size;
    }

    public final int size() {
        return this.size;
    }

    public final Extension<T, ?> getExtension(int i) {
        if (i >= 0 && i < this.size) {
            return (Extension) this.data[i];
        }
        throw new IndexOutOfBoundsException(String.valueOf(i));
    }

    public final Object getExtensionValue(int i) {
        if (i >= 0 && i < this.size) {
            return this.data[this.size + i];
        }
        throw new IndexOutOfBoundsException(String.valueOf(i));
    }

    public final List<Extension<T, ?>> getExtensions() {
        ArrayList arrayList = new ArrayList(this.size);
        for (int i = 0; i < this.size; i++) {
            arrayList.add((Extension) this.data[i]);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public final <E> E get(Extension<T, E> extension) {
        int binarySearch = Arrays.binarySearch(this.data, 0, this.size, extension);
        if (binarySearch < 0) {
            return null;
        }
        return this.data[this.size + binarySearch];
    }

    public final <E> void put(Extension<T, E> extension, E e) {
        int binarySearch = Arrays.binarySearch(this.data, 0, this.size, extension);
        if (binarySearch >= 0) {
            this.data[this.size + binarySearch] = e;
        } else {
            insert(extension, e, -(binarySearch + 1));
        }
    }

    private <E> void insert(Extension<T, E> extension, E e, int i) {
        Object[] objArr = this.data;
        if (this.data.length < (this.size + 1) * 2) {
            objArr = new Object[(this.data.length * 2)];
            System.arraycopy(this.data, 0, objArr, 0, i);
        }
        if (i < this.size) {
            System.arraycopy(this.data, this.size + i, objArr, this.size + i + 2, this.size - i);
            System.arraycopy(this.data, i, objArr, i + 1, this.size);
        } else {
            System.arraycopy(this.data, this.size, objArr, this.size + 1, this.size);
        }
        this.size++;
        this.data = objArr;
        this.data[i] = extension;
        this.data[this.size + i] = e;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ExtensionMap)) {
            return false;
        }
        ExtensionMap extensionMap = (ExtensionMap) obj;
        if (this.size != extensionMap.size) {
            return false;
        }
        for (int i = 0; i < this.size * 2; i++) {
            if (!this.data[i].equals(extensionMap.data[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < this.size * 2; i2++) {
            i = (i * 37) + this.data[i2].hashCode();
        }
        return i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        String str = "";
        for (int i = 0; i < this.size; i++) {
            sb.append(str);
            sb.append(((Extension) this.data[i]).getTag());
            sb.append("=");
            sb.append(this.data[this.size + i]);
            str = ", ";
        }
        sb.append(h.d);
        return sb.toString();
    }
}
