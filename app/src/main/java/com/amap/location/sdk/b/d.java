package com.amap.location.sdk.b;

import com.amap.location.common.e.a;
import com.amap.location.common.e.b;
import com.amap.location.common.e.c;
import com.autonavi.core.network.inter.response.ByteResponse;
import java.util.Map.Entry;

/* compiled from: NetworkWrapper */
public class d implements c {
    private static volatile d a;
    private static Object b = new Object();
    private boy c = new boy();

    public static d a() {
        if (a == null) {
            synchronized (b) {
                try {
                    if (a == null) {
                        a = new d();
                    }
                }
            }
        }
        return a;
    }

    private d() {
        this.c.a(5);
    }

    public b a(a aVar) {
        if (!(this.c == null || aVar == null || aVar.a == null || aVar.c == null)) {
            bpj bpj = new bpj();
            if (aVar.b != null) {
                for (Entry next : aVar.b.entrySet()) {
                    if (next != null) {
                        bpj.addHeader((String) next.getKey(), (String) next.getValue());
                    }
                }
            }
            bpj.setBody(aVar.c);
            bpj.setUrl(aVar.a);
            bpj.setTimeout(aVar.d);
            ByteResponse byteResponse = (ByteResponse) this.c.a((bph) bpj, ByteResponse.class);
            if (byteResponse != null) {
                b bVar = new b();
                bVar.a = byteResponse.getStatusCode();
                bVar.b = byteResponse.getHeaders();
                bVar.c = (byte[]) byteResponse.getResult();
                return bVar;
            }
        }
        return null;
    }
}
