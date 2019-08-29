package com.alipay.inside.android.phone.mrpc.core.gwprotocol.json;

import android.annotation.TargetApi;
import android.util.Base64;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import java.security.MessageDigest;

public class JsonSerializerV2 extends JsonSerializer {
    public JsonSerializerV2(int i, String str, Object obj) {
        super(i, str, obj);
    }

    public byte[] packet() throws RpcException {
        String str;
        String requestDataJson = getRequestDataJson();
        try {
            byte[] bytes = requestDataJson.getBytes("UTF-8");
            LoggerFactory.f().a((String) "JsonSerializer", "bodyStr=".concat(String.valueOf(requestDataJson)));
            return bytes;
        } catch (Throwable th) {
            Integer valueOf = Integer.valueOf(9);
            StringBuilder sb = new StringBuilder("response  =");
            sb.append(requestDataJson);
            sb.append(":");
            sb.append(th);
            if (sb.toString() == null) {
                str = "";
            } else {
                str = th.getMessage();
            }
            throw new RpcException(valueOf, str);
        }
    }

    @TargetApi(8)
    public String getRequestDataDigest() {
        try {
            return new String(Base64.encode(MessageDigest.getInstance("MD5").digest(getRequestDataJson().getBytes()), 0));
        } catch (Exception e) {
            LoggerFactory.f().a((String) "JsonSerializer", (Throwable) e);
            return "";
        }
    }
}
