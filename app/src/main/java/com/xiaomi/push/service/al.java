package com.xiaomi.push.service;

import com.xiaomi.stats.h;
import java.util.Iterator;
import java.util.List;

final class al implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ boolean b;

    al(List list, boolean z) {
        this.a = list;
        this.b = z;
    }

    public final void run() {
        int i;
        boolean a2 = ak.b("www.baidu.com:80");
        Iterator it = this.a.iterator();
        while (true) {
            i = 1;
            if (!it.hasNext()) {
                break;
            }
            a2 = a2 || ak.b((String) it.next());
            if (a2 && !this.b) {
                break;
            }
        }
        if (!a2) {
            i = 2;
        }
        h.a(i);
    }
}
