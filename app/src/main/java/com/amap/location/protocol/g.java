package com.amap.location.protocol;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.location.protocol.b.a;
import com.autonavi.core.network.inter.response.ResponseException;
import java.util.LinkedList;
import java.util.List;

/* compiled from: OnlineManager */
public class g {
    private static boy b;
    private Context a;
    private c c;
    private e d;
    private List<LocationRequest> e = new LinkedList();
    private Object f = new Object();

    public g(Context context, e eVar) {
        this.a = context;
        this.d = eVar;
        a(eVar);
        synchronized (g.class) {
            if (b == null) {
                boy boy = new boy();
                b = boy;
                boy.a(3);
            }
        }
    }

    private boolean a(@NonNull e eVar) {
        if (eVar == null) {
            return false;
        }
        String a2 = eVar.a();
        a.b = a2;
        if (TextUtils.isEmpty(a2)) {
            com.amap.location.common.d.a.c("olmgr", "src is empty");
            return false;
        }
        String b2 = eVar.b();
        a.c = b2;
        if (TextUtils.isEmpty(b2)) {
            com.amap.location.common.d.a.c("olmgr", "license is empty");
            return false;
        }
        String c2 = eVar.c();
        a.d = c2;
        if (TextUtils.isEmpty(c2)) {
            com.amap.location.common.d.a.c("olmgr", "version is empty");
            return false;
        }
        a.e = eVar.d();
        a.f = eVar.e();
        a.g = eVar.f();
        return true;
    }

    public void a(@NonNull LocationRequest locationRequest, @NonNull f fVar) {
        fVar.a(this);
        if (this.c == null) {
            this.c = new b(this.a, b);
        }
        boolean g = this.d == null ? false : this.d.g();
        if (this.d != null) {
            this.d.h();
        }
        if (this.c.a(locationRequest, fVar, g, false)) {
            synchronized (this.f) {
                this.e.add(locationRequest);
            }
        }
    }

    public void b(@NonNull LocationRequest locationRequest, @NonNull f fVar) {
        fVar.a(this);
        if (this.c == null) {
            this.c = new a(this.a, b);
        }
        boolean g = this.d == null ? false : this.d.g();
        if (this.d != null) {
            this.d.h();
        }
        if (this.c.a(locationRequest, fVar, g, false)) {
            synchronized (this.f) {
                this.e.add(locationRequest);
            }
        }
    }

    public void a(LocationRequest locationRequest) {
        if (locationRequest != null) {
            b.a((bph) locationRequest);
            b(locationRequest);
        }
    }

    public void a() {
        synchronized (this.f) {
            for (LocationRequest a2 : this.e) {
                b.a((bph) a2);
            }
            this.e.clear();
        }
        if (this.c != null) {
            this.c.a();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(f fVar, com.amap.location.protocol.d.a aVar) {
        LocationRequest locationRequest = (LocationRequest) aVar.getRequest();
        b(locationRequest);
        if (locationRequest != null && !TextUtils.isEmpty(locationRequest.j())) {
            aVar.a(locationRequest.j());
        }
        fVar.a(aVar);
    }

    /* access modifiers changed from: 0000 */
    public void a(f fVar, bph bph, ResponseException responseException) {
        LocationRequest locationRequest = (LocationRequest) bph;
        b(locationRequest);
        fVar.a(locationRequest, responseException);
    }

    private void b(LocationRequest locationRequest) {
        synchronized (this.f) {
            this.e.remove(locationRequest);
        }
    }
}
