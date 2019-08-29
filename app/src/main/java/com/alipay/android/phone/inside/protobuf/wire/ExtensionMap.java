package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.wire.ExtendableMessage;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class ExtensionMap<T extends ExtendableMessage<?>> {
    private Object[] a;
    private int b;

    public <E> ExtensionMap(Extension<T, E> extension, E e) {
        this.a = new Object[2];
        this.a[0] = extension;
        this.a[1] = e;
        this.b = 1;
    }

    public ExtensionMap(ExtensionMap<T> extensionMap) {
        this.a = (Object[]) extensionMap.a.clone();
        this.b = extensionMap.b;
    }

    public final int a() {
        return this.b;
    }

    public final Extension<T, ?> a(int i) {
        if (i >= 0 && i < this.b) {
            return (Extension) this.a[i];
        }
        throw new IndexOutOfBoundsException(String.valueOf(i));
    }

    public final Object b(int i) {
        if (i >= 0 && i < this.b) {
            return this.a[this.b + i];
        }
        throw new IndexOutOfBoundsException(String.valueOf(i));
    }

    public final List<Extension<T, ?>> b() {
        ArrayList arrayList = new ArrayList(this.b);
        for (int i = 0; i < this.b; i++) {
            arrayList.add((Extension) this.a[i]);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public final <E> E a(Extension<T, E> extension) {
        int binarySearch = Arrays.binarySearch(this.a, 0, this.b, extension);
        if (binarySearch < 0) {
            return null;
        }
        return this.a[this.b + binarySearch];
    }

    public final <E> void a(Extension<T, E> extension, E e) {
        int binarySearch = Arrays.binarySearch(this.a, 0, this.b, extension);
        if (binarySearch >= 0) {
            this.a[this.b + binarySearch] = e;
            return;
        }
        int i = -(binarySearch + 1);
        Object[] objArr = this.a;
        if (this.a.length < (this.b + 1) * 2) {
            objArr = new Object[(this.a.length * 2)];
            System.arraycopy(this.a, 0, objArr, 0, i);
        }
        if (i < this.b) {
            System.arraycopy(this.a, this.b + i, objArr, this.b + i + 2, this.b - i);
            System.arraycopy(this.a, i, objArr, i + 1, this.b);
        } else {
            System.arraycopy(this.a, this.b, objArr, this.b + 1, this.b);
        }
        this.b++;
        this.a = objArr;
        this.a[i] = extension;
        this.a[this.b + i] = e;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ExtensionMap)) {
            return false;
        }
        ExtensionMap extensionMap = (ExtensionMap) obj;
        if (this.b != extensionMap.b) {
            return false;
        }
        for (int i = 0; i < this.b * 2; i++) {
            if (!this.a[i].equals(extensionMap.a[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < this.b * 2; i2++) {
            i = (i * 37) + this.a[i2].hashCode();
        }
        return i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        String str = "";
        for (int i = 0; i < this.b; i++) {
            sb.append(str);
            sb.append(((Extension) this.a[i]).e());
            sb.append("=");
            sb.append(this.a[this.b + i]);
            str = ", ";
        }
        sb.append(h.d);
        return sb.toString();
    }
}
