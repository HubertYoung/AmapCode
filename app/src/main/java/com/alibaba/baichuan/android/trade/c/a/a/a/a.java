package com.alibaba.baichuan.android.trade.c.a.a.a;

import com.alibaba.baichuan.android.trade.c.a.a.b.f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class a {
    private d a;
    private List b;

    public a(d dVar) {
        this.a = dVar;
        if (dVar.j == null || dVar.j.size() == 0) {
            this.b = Collections.emptyList();
            return;
        }
        this.b = new ArrayList(dVar.j.size());
        for (com.alibaba.baichuan.android.trade.c.a.a.a.d.a a2 : dVar.j.values()) {
            b a3 = a2.a();
            if (a3 != null) {
                this.b.add(a3);
            }
        }
    }

    public d a() {
        return this.a;
    }

    public boolean a(c cVar) {
        boolean z = false;
        if (this.a.m != null) {
            if (this.b.size() != 0) {
                Iterator it = this.a.m.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (((f) it.next()).a(cVar)) {
                        for (b a2 : this.b) {
                            if (a2.a(cVar)) {
                                z = true;
                            }
                        }
                    }
                }
            } else {
                return false;
            }
        }
        return z;
    }
}
