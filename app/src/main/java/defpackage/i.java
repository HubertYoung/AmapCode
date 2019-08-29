package defpackage;

import android.text.TextUtils;
import anet.channel.entity.ConnType.TypeLevel;
import anet.channel.status.NetworkStatusHelper;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/* renamed from: i reason: default package */
/* compiled from: AccsSessionManager */
public final class i {
    r a = null;
    Set<String> b = Collections.EMPTY_SET;

    i(r rVar) {
        this.a = rVar;
    }

    public final synchronized void a() {
        boolean z;
        Collection<t> values = this.a.g.b.values();
        Set<String> set = Collections.EMPTY_SET;
        if (!values.isEmpty()) {
            set = new TreeSet<>();
        }
        for (t next : values) {
            if (next.b) {
                set.add(cz.a(bu.a().a(next.a, next.c ? "https" : "http"), "://", next.a));
            }
        }
        for (String next2 : this.b) {
            if (!set.contains(next2)) {
                a(next2);
            }
        }
        if (!m.h() && NetworkStatusHelper.h()) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            for (String next3 : set) {
                try {
                    this.a.a(next3, TypeLevel.SPDY, 0);
                } catch (Exception unused) {
                    cl.d("start session failed", null, "host", next3);
                }
            }
            this.b = set;
        }
    }

    public final synchronized void a(boolean z) {
        if (cl.a(1)) {
            cl.a("awcn.AccsSessionManager", "forceCloseSession", this.a.c, "reCreate", Boolean.valueOf(z));
        }
        for (String a2 : this.b) {
            a(a2);
        }
        if (z) {
            a();
        }
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            cl.a("awcn.AccsSessionManager", "closeSessions", this.a.c, "host", str);
            this.a.c(str).b(false);
        }
    }
}
