package com.alibaba.baichuan.android.trade.c.a.a.b;

public class a {
    private d a;
    private b b;

    public a(d dVar) {
        this.a = dVar;
        this.b = dVar.j;
    }

    public d a() {
        return this.a;
    }

    public boolean a(c cVar) {
        if (this.a.m == null || this.b == null) {
            return false;
        }
        for (f a2 : this.a.m.values()) {
            if (a2.a(cVar)) {
                return this.b.a(cVar);
            }
        }
        return false;
    }
}
