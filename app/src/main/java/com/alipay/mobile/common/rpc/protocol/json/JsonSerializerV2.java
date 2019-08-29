package com.alipay.mobile.common.rpc.protocol.json;

import android.annotation.TargetApi;
import android.util.Base64;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import java.security.MessageDigest;

public class JsonSerializerV2 extends JsonSerializer {
    public JsonSerializerV2(int id, String operationType, Object params) {
        super(id, operationType, params);
    }

    public byte[] packet() {
        String message;
        String bodyStr = getRequestDataJson();
        try {
            byte[] result = bodyStr.getBytes("UTF-8");
            LogCatUtil.printInfo("JsonSerializer", "bodyStr=" + bodyStr);
            return result;
        } catch (Throwable e) {
            MonitorErrorLogHelper.log("JsonSerializer", e);
            Integer valueOf = Integer.valueOf(20);
            if (("response  =" + bodyStr + ":" + e) == null) {
                message = "";
            } else {
                message = e.getMessage();
            }
            throw new RpcException(valueOf, message);
        }
    }

    @TargetApi(8)
    public String getRequestDataDigest() {
        try {
            return new String(Base64.encode(MessageDigest.getInstance("MD5").digest(getRequestDataJson().getBytes()), 0));
        } catch (Exception e) {
            LogCatUtil.warn((String) "JsonSerializer", (Throwable) e);
            return "";
        }
    }
}
