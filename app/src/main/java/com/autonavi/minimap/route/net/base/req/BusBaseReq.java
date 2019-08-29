package com.autonavi.minimap.route.net.base.req;

import com.amap.bundle.aosservice.request.AosPostRequest;

public class BusBaseReq extends AosPostRequest {
    public BusBaseReq(ede ede) {
        if (!ede.f) {
            ede.d = aai.a(ede.a == null ? "" : ede.a, ede.b);
            ede.f = true;
        }
        setUrl(ede.d);
        addReqParams(ede.e);
        addSignParams(ede.c);
    }

    public void setUrl(String str) {
        super.setUrl(str);
        try {
            String str2 = new String(this.mBody, "UTF-8");
            StringBuilder sb = new StringBuilder("[POST] url = ");
            sb.append(str);
            sb.append("\nbody = ");
            sb.append(str2);
            eao.a((String) "request", sb.toString());
        } catch (Exception unused) {
            StringBuilder sb2 = new StringBuilder("[POST] url = ");
            sb2.append(str);
            sb2.append("\nbody = null");
            eao.a((String) "request", sb2.toString());
        }
    }
}
