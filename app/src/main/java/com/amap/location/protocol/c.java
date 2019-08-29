package com.amap.location.protocol;

import android.content.Context;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.amap.location.protocol.c.a;
import com.autonavi.core.network.inter.response.ResponseException;

/* compiled from: BaseLocator */
public abstract class c {
    protected Context a;
    protected boy b;
    private a c;

    /* access modifiers changed from: protected */
    public abstract String a(LocationRequest locationRequest, boolean z, boolean z2);

    /* access modifiers changed from: protected */
    public abstract byte[] a(LocationRequest locationRequest) throws Exception;

    public c(Context context, boy boy) {
        this.a = context;
        this.b = boy;
        if (com.amap.location.protocol.b.a.h) {
            this.c = new a(this.a);
        }
    }

    public boolean a(LocationRequest locationRequest, f fVar, boolean z, boolean z2) {
        try {
            a(a(locationRequest, z, z2), a(locationRequest), locationRequest, fVar);
            return true;
        } catch (Exception e) {
            com.amap.location.common.d.a.b("baseloc", "get location error", e);
            ResponseException responseException = new ResponseException(e.toString());
            responseException.errorCode = -99;
            responseException.exception = e;
            fVar.onFailure(locationRequest, responseException);
            return false;
        }
    }

    public void a() {
        if (this.c != null) {
            this.c.a();
        }
    }

    /* access modifiers changed from: protected */
    public void a(String str, byte[] bArr, LocationRequest locationRequest, f fVar) {
        locationRequest.addHeader("Content-Type", FilePart.DEFAULT_CONTENT_TYPE);
        locationRequest.addHeader("Accept-Encoding", "gzip");
        locationRequest.addHeader("gzipped", "1");
        locationRequest.setBody(bArr);
        locationRequest.setUrl(str);
        locationRequest.setRetryTimes(1);
        this.b.a((bph) locationRequest, (bpl<T>) fVar);
    }

    /* access modifiers changed from: protected */
    public void a(byte[] bArr) {
        if (com.amap.location.protocol.b.a.h && this.c != null) {
            this.c.a(bArr);
        }
    }
}
