package com.alipay.deviceid.module.x;

import com.alipay.deviceid.module.rpc.mrpc.core.RpcException;
import com.alipay.sdk.util.j;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import java.lang.reflect.Type;
import org.json.JSONObject;

public final class ar extends ao {
    public ar(Type type, byte[] bArr) {
        super(type, bArr);
    }

    public final Object a() {
        try {
            String str = new String(this.b);
            StringBuilder sb = new StringBuilder("threadid = ");
            sb.append(Thread.currentThread().getId());
            sb.append("; rpc response:  ");
            sb.append(str);
            JSONObject jSONObject = new JSONObject(str);
            int i = jSONObject.getInt(j.a);
            if (i == 1000) {
                return this.a == String.class ? jSONObject.optString("result") : af.a(jSONObject.optString("result"), this.a);
            }
            throw new RpcException(Integer.valueOf(i), jSONObject.optString(ModulePoi.TIPS));
        } catch (Exception e) {
            Integer valueOf = Integer.valueOf(10);
            StringBuilder sb2 = new StringBuilder("response  =");
            sb2.append(new String(this.b));
            sb2.append(":");
            sb2.append(e);
            throw new RpcException(valueOf, sb2.toString() == null ? "" : e.getMessage());
        }
    }
}
