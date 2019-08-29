package com.alipay.inside.android.phone.mrpc.core.gwprotocol.json;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.AbstractDeserializer;
import com.alipay.inside.jsoncodec.JSONCodec;
import com.alipay.sdk.util.j;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import java.lang.reflect.Type;
import org.json.JSONObject;

public class JsonDeserializer extends AbstractDeserializer {
    public JsonDeserializer(Type type, byte[] bArr) {
        super(type, bArr);
    }

    public Object parser() throws RpcException {
        String str;
        try {
            String str2 = new String(this.mData);
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("threadid = ");
            sb.append(Thread.currentThread().getId());
            sb.append("; rpc response:  ");
            sb.append(str2);
            f.a((String) "HttpCaller", sb.toString());
            JSONObject jSONObject = new JSONObject(str2);
            int i = jSONObject.getInt(j.a);
            if (i != 1000) {
                throw new RpcException(Integer.valueOf(i), jSONObject.optString(ModulePoi.TIPS));
            } else if (this.mType == String.class) {
                return jSONObject.optString("result");
            } else {
                return JSONCodec.parseObject(jSONObject.optString("result"), this.mType);
            }
        } catch (Exception e) {
            Integer valueOf = Integer.valueOf(10);
            StringBuilder sb2 = new StringBuilder("response  =");
            sb2.append(new String(this.mData));
            sb2.append(":");
            sb2.append(e);
            if (sb2.toString() == null) {
                str = "";
            } else {
                str = e.getMessage();
            }
            throw new RpcException(valueOf, str);
        }
    }
}
