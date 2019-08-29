package com.alp.o.mzx.a;

import com.alipay.android.phone.wallet.minizxing.m;
import java.util.ArrayList;

public final class c {
    private ArrayList<f>[] a = new ArrayList[3];
    private int[] b = new int[3];

    public static c a(ArrayList<f>[] modes, int[] bitCount) {
        c ret = new c();
        ret.a = modes;
        ret.b = bitCount;
        return ret;
    }

    public static c a(m mode, int contentLength, int byteLength) {
        c ret = new c();
        for (int i = 0; i < 3; i++) {
            ret.a[i] = new ArrayList<>();
            ret.a[i].add(new f(0, contentLength, mode));
            ret.b[i] = e.a(byteLength, b.a(mode), i).a;
        }
        return ret;
    }

    private c() {
    }

    public final m a(int versionId) {
        return this.a[e.a(versionId)].get(0).c;
    }

    public final ArrayList<f> b(int versionId) {
        return this.a[e.a(versionId)];
    }

    public final int c(int versionId) {
        return this.b[e.a(versionId)];
    }
}
