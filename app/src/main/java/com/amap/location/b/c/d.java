package com.amap.location.b.c;

import android.support.annotation.NonNull;
import com.amap.location.common.a.a.C0014a;

/* compiled from: CollectDataItem */
public class d implements C0014a {
    private int a;
    private byte[] b;

    public d(int i, @NonNull byte[] bArr) {
        this.a = i;
        this.b = bArr;
    }

    public long a() {
        return (long) (this.b.length + 17);
    }

    public int b() {
        return this.a;
    }

    public byte[] c() {
        return this.b;
    }
}
