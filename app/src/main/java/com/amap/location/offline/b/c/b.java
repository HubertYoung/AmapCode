package com.amap.location.offline.b.c;

import android.content.Context;
import android.support.annotation.NonNull;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.amap.location.common.f.f;
import com.amap.location.offline.b.b.d;
import com.amap.location.offline.c;
import com.amap.location.security.Core;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;

/* compiled from: OfflineProtocol */
public class b {
    private Context a;
    private c b;
    private com.amap.location.offline.a c;
    private com.amap.location.offline.b.c.a.b d = new com.amap.location.offline.b.c.a.b();
    private d e;
    private boolean f;
    private a g;

    /* compiled from: OfflineProtocol */
    interface a {
        void a();
    }

    public b(Context context, c cVar, com.amap.location.offline.a aVar, a aVar2) {
        this.a = context;
        this.b = cVar;
        this.c = aVar;
        this.g = aVar2;
    }

    public void a(@NonNull c cVar) {
        this.b = cVar;
    }

    public void a(byte b2, int i) {
        this.f = true;
        try {
            c b3 = b(b2, i);
            if (b3 == null || this.b.n == null) {
                this.f = false;
                return;
            }
            com.amap.location.common.e.a aVar = new com.amap.location.common.e.a();
            aVar.a = c.a ? "http://aps.testing.amap.com/LoadOfflineData/repeatData" : "http://offline.aps.amap.com/LoadOfflineData/repeatData";
            aVar.b = b3.e;
            aVar.c = b3.f;
            a(b3, this.b.n.a(aVar));
        } catch (Throwable th) {
            this.f = false;
            com.amap.location.common.d.a.a(th);
        }
    }

    public boolean a() {
        return this.f;
    }

    private c b(byte b2, int i) throws Exception {
        List list;
        List list2;
        int i2;
        byte b3 = b2;
        if (this.e == null) {
            this.e = d.a(this.a);
        }
        if (b3 == 1) {
            int d2 = this.c.d();
            int i3 = this.c.i();
            List<Long> b4 = this.e.b(d2, i3);
            int size = b4.size();
            if (size < i3) {
                i2 = i3 - size;
            } else {
                i2 = (i3 * 2) / 10;
            }
            List<String> a2 = this.e.a(d2, i2);
            int size2 = a2.size();
            if (size2 > 0 && size == i3) {
                b4 = b4.subList(0, i3 - size2);
            }
            if (b4.size() + a2.size() < 5) {
                return null;
            }
            StringBuilder sb = new StringBuilder("dw size:(");
            sb.append(a2.size());
            sb.append(",");
            sb.append(b4.size());
            sb.append(")");
            com.amap.location.common.d.a.b("offpro", sb.toString());
            list2 = a2;
            list = b4;
        } else {
            com.amap.location.common.d.a.b("offpro", "start first dw");
            list2 = null;
            list = null;
        }
        c cVar = new c(b3, list, list2);
        cVar.b = i;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Content-Type", FilePart.DEFAULT_CONTENT_TYPE);
        hashMap.put("Accept-Encoding", "gzip");
        hashMap.put("gzipped", "1");
        hashMap.put("v", "1.4.0");
        hashMap.put("et", "110");
        cVar.e = hashMap;
        List list3 = list2;
        c cVar2 = cVar;
        byte[] a3 = this.d.a(b3, "1.4.0", this.b.b, this.b.c, this.b.d, (byte) com.amap.location.common.a.d(), this.b.f, this.b.g, this.b.h, com.amap.location.common.a.a(), com.amap.location.common.a.e(this.a), com.amap.location.common.a.c(), com.amap.location.common.a.b(), this.b.i, this.b.j, list, list3);
        if (a3 == null) {
            return null;
        }
        byte[] xxt = Core.xxt(a3, 1);
        if (xxt == null || xxt.length == 0) {
            return null;
        }
        byte[] a4 = f.a(xxt);
        if (a4 == null || a4.length == 0) {
            return null;
        }
        c cVar3 = cVar2;
        cVar3.f = a4;
        return cVar3;
    }

    private void a(c cVar, com.amap.location.common.e.b bVar) {
        if (bVar == null) {
            com.amap.location.common.d.a.c("offpro", "res is null");
            a(cVar);
            return;
        }
        String str = null;
        List list = bVar.b.get("code");
        if (list != null) {
            str = (String) list.get(list.size() - 1);
        }
        com.amap.location.common.d.a.b("offpro", "res code:".concat(String.valueOf(str)));
        if (cVar == null) {
            this.f = false;
            com.amap.location.common.d.a.c("offpro", "req is null");
        } else if (!"260".equals(str)) {
            a(cVar);
        } else {
            if (cVar.a == 1) {
                com.amap.location.offline.e.c.c(this.a);
                if (cVar.b == 0) {
                    com.amap.location.offline.e.c.d(this.a);
                }
            }
            if (cVar.a == 0) {
                com.amap.location.offline.e.c.b(this.a);
            }
            boolean b2 = b(cVar, bVar);
            this.f = false;
            if ((b2 || cVar.a == 0) && this.g != null) {
                this.g.a();
            }
        }
    }

    private boolean b(c cVar, com.amap.location.common.e.b bVar) {
        com.amap.location.offline.b.c.b.c a2 = a(bVar);
        if (a2 == null) {
            return false;
        }
        if (this.e == null) {
            this.e = d.a(this.a);
        }
        if (cVar.a == 0) {
            this.e.a(a2);
        } else {
            this.e.a(a2, cVar.c, cVar.d, this.a);
        }
        return true;
    }

    public com.amap.location.offline.b.c.b.c a(com.amap.location.common.e.b bVar) {
        try {
            List list = bVar.b.get(TransportConstants.KEY_X_CONTENT_ENCODING);
            Object obj = (list == null || list.size() <= 0) ? null : (String) list.get(0);
            byte[] bArr = bVar.c;
            if (bArr == null || bArr.length <= 0) {
                return null;
            }
            if ("gzip".equals(obj)) {
                bArr = f.b(bArr);
            }
            return com.amap.location.offline.b.c.b.c.a(ByteBuffer.wrap(bArr));
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
            return null;
        }
    }

    private void a(c cVar) {
        this.f = false;
        if (cVar != null) {
            if (cVar.a == 1) {
                com.amap.location.offline.e.c.c(this.a);
                return;
            }
            if (this.g != null) {
                this.g.a();
            }
        }
    }
}
