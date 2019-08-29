package com.xiaomi.network;

import java.util.ArrayList;
import java.util.Iterator;

class b extends Fallback {
    Fallback i;
    final /* synthetic */ Fallback j;
    final /* synthetic */ HostManager k;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    b(HostManager hostManager, String str, Fallback fallback) {
        // this.k = hostManager;
        // this.j = fallback;
        super(str);
        this.i = this.j;
        this.b = this.b;
        if (this.j != null) {
            this.f = this.j.f;
        }
    }

    public synchronized ArrayList<String> a(boolean z) {
        ArrayList<String> arrayList;
        arrayList = new ArrayList<>();
        if (this.i != null) {
            arrayList.addAll(this.i.a(true));
        }
        synchronized (HostManager.sReservedHosts) {
            Fallback fallback = HostManager.sReservedHosts.get(this.b);
            if (fallback != null) {
                Iterator<String> it = fallback.a(true).iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (arrayList.indexOf(next) == -1) {
                        arrayList.add(next);
                    }
                }
                arrayList.remove(this.b);
                arrayList.add(this.b);
            }
        }
        return arrayList;
    }

    public synchronized void a(String str, AccessHistory accessHistory) {
        if (this.i != null) {
            this.i.a(str, accessHistory);
        }
    }

    public boolean b() {
        return false;
    }
}
