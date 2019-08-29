package com.amap.location.protocol;

import com.amap.location.protocol.d.a;
import com.autonavi.core.network.inter.response.ResponseException;

/* compiled from: LocationCallback */
public abstract class f implements bpl<a> {
    private g a;

    public abstract void a(LocationRequest locationRequest, ResponseException responseException);

    public abstract void a(a aVar);

    /* access modifiers changed from: 0000 */
    public void a(g gVar) {
        this.a = gVar;
    }

    /* renamed from: b */
    public final void onSuccess(a aVar) {
        this.a.a(this, aVar);
    }

    public final void onFailure(bph bph, ResponseException responseException) {
        this.a.a(this, bph, responseException);
    }
}
