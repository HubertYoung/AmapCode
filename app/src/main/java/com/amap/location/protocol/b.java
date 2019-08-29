package com.amap.location.protocol;

import android.content.Context;
import com.amap.location.common.f.f;
import com.amap.location.common.model.FPS;
import com.amap.location.protocol.e.a;
import com.amap.location.protocol.e.c;
import com.amap.location.security.Core;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;

/* compiled from: ApsLocator */
public class b extends c {
    public b(Context context, boy boy) {
        super(context, boy);
    }

    /* access modifiers changed from: protected */
    public String a(LocationRequest locationRequest, boolean z, boolean z2) {
        FPS a = locationRequest.a();
        boolean a2 = (a.cellStatus == null || a.cellStatus.mainCell == null) ? false : a.a(a.cellStatus.mainCell.mcc);
        StringBuilder sb = new StringBuilder();
        if (a2) {
            sb.append(com.amap.location.protocol.b.a.o);
        } else if (z) {
            sb.append(com.amap.location.protocol.b.a.n);
        } else if (z2) {
            StringBuilder sb2 = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS);
            sb2.append(com.amap.location.protocol.b.a.m);
            sb.append(sb2.toString());
        } else {
            StringBuilder sb3 = new StringBuilder(AjxHttpLoader.DOMAIN_HTTP);
            sb3.append(com.amap.location.protocol.b.a.m);
            sb.append(sb3.toString());
        }
        sb.append("&csid=");
        sb.append(locationRequest.j());
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public byte[] a(LocationRequest locationRequest) throws Exception {
        byte[] bArr;
        locationRequest.addHeader("et", "111");
        boolean e = locationRequest.e();
        boolean f = locationRequest.f();
        boolean g = locationRequest.g();
        c cVar = new c();
        com.amap.location.protocol.b.b a = c.a(this.a, locationRequest.a(), locationRequest.b(), locationRequest.c(), locationRequest.k(), com.amap.location.protocol.b.a.f, com.amap.location.common.a.a(), e, f, g, locationRequest.h());
        locationRequest.a(a);
        byte[] a2 = cVar.a(a, false);
        if (a2 != null && a2.length > 0) {
            byte[] xxt = Core.xxt(a2, 1);
            if (xxt != null && xxt.length > 0) {
                bArr = f.a(xxt);
                a(bArr);
                return bArr;
            }
        }
        bArr = null;
        a(bArr);
        return bArr;
    }
}
