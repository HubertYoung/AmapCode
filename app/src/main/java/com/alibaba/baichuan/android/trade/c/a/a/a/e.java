package com.alibaba.baichuan.android.trade.c.a.a.a;

import com.alibaba.baichuan.android.trade.c.a.a.a.d.a;
import com.alibaba.baichuan.android.trade.c.a.a.b.f;
import java.util.Map;

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

    public e a(String str, String str2, Map map) {
        a aVar = new a();
        aVar.a = str;
        aVar.b = str2;
        aVar.c = map;
        this.a.j.put(aVar.a, aVar);
        return this;
    }

    public e a(String str, String str2, String[] strArr, String[] strArr2) {
        f fVar = new f();
        fVar.c = str;
        fVar.d = str2;
        fVar.e = strArr;
        fVar.f = strArr2;
        this.a.m.put(fVar.c, fVar);
        return this;
    }

    public e a(int[] iArr) {
        this.a.g = iArr;
        return this;
    }
}
