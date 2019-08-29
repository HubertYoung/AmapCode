package com.alibaba.baichuan.android.trade.c.a.a.b;

public class e {
    private d a = new d();

    public static e a(String str) {
        e eVar = new e();
        eVar.a.a = str;
        return eVar;
    }

    public d a() {
        return this.a;
    }

    public e a(b bVar) {
        if (bVar != null) {
            this.a.j = bVar;
            this.a.i = bVar.getClass().getName();
        }
        return this;
    }

    public e a(String str, String str2, String[] strArr, String[] strArr2) {
        f fVar = new f();
        fVar.c = str;
        fVar.d = str2;
        fVar.e = strArr;
        fVar.f = strArr2;
        this.a.m.put(str, fVar);
        return this;
    }

    public e a(boolean z) {
        this.a.l = z;
        return this;
    }

    public e a(int[] iArr) {
        this.a.g = iArr;
        return this;
    }

    public e a(String[] strArr) {
        this.a.h = strArr;
        return this;
    }
}
