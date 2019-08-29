package com.alipay.inside.android.phone.mrpc.core.gwprotocol.json;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.HttpUrlHeader;
import com.alipay.inside.android.phone.mrpc.core.HttpUrlResponse;
import com.alipay.inside.android.phone.mrpc.core.Response;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.AbstractDeserializer;
import java.lang.reflect.Type;
import java.net.URLDecoder;

public class JsonDeserializerV2 extends AbstractDeserializer {
    private static final String TAG = "JsonDeserializerV2";
    private Response response;

    public JsonDeserializerV2(Type type, byte[] bArr) {
        super(type, bArr);
    }

    public JsonDeserializerV2(Type type, Response response2) {
        super(type, response2.getResData());
        this.response = response2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object parser() throws com.alipay.inside.android.phone.mrpc.core.RpcException {
        /*
            r7 = this;
            java.lang.String r0 = ""
            com.alipay.inside.android.phone.mrpc.core.Response r1 = r7.response     // Catch:{ Throwable -> 0x0023 }
            r7.preProcessResponse(r1)     // Catch:{ Throwable -> 0x0023 }
            java.lang.reflect.Type r1 = r7.mType     // Catch:{ Throwable -> 0x0023 }
            java.lang.Class r2 = java.lang.Void.TYPE     // Catch:{ Throwable -> 0x0023 }
            if (r1 != r2) goto L_0x000f
            r0 = 0
            return r0
        L_0x000f:
            byte[] r1 = r7.mData     // Catch:{ Throwable -> 0x0023 }
            java.lang.String r2 = "UTF-8"
            java.lang.String r1 = org.apache.http.util.EncodingUtils.getString(r1, r2)     // Catch:{ Throwable -> 0x0023 }
            java.lang.reflect.Type r0 = r7.mType     // Catch:{ Throwable -> 0x001e }
            java.lang.Object r0 = com.alipay.inside.jsoncodec.JSONCodec.parseObject(r1, r0)     // Catch:{ Throwable -> 0x001e }
            return r0
        L_0x001e:
            r0 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x0024
        L_0x0023:
            r1 = move-exception
        L_0x0024:
            boolean r2 = r1 instanceof com.alipay.inside.android.phone.mrpc.core.RpcException
            if (r2 == 0) goto L_0x002b
            com.alipay.inside.android.phone.mrpc.core.RpcException r1 = (com.alipay.inside.android.phone.mrpc.core.RpcException) r1
            throw r1
        L_0x002b:
            com.alipay.inside.android.phone.mrpc.core.RpcException r2 = new com.alipay.inside.android.phone.mrpc.core.RpcException
            r3 = 10
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "response  ="
            r4.<init>(r5)
            r4.append(r0)
            java.lang.String r5 = ":"
            r4.append(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            if (r4 != 0) goto L_0x004f
            java.lang.String r4 = ""
            goto L_0x0053
        L_0x004f:
            java.lang.String r4 = r1.getMessage()
        L_0x0053:
            r2.<init>(r3, r4)
            r2.initCause(r1)
            r7.logResult(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.inside.android.phone.mrpc.core.gwprotocol.json.JsonDeserializerV2.parser():java.lang.Object");
    }

    private void logResult(String str) {
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("threadid = ");
        sb.append(Thread.currentThread().getId());
        sb.append("; rpc response:  ");
        sb.append(str);
        sb.append(" mType=");
        sb.append(this.mType != null ? this.mType.getClass().getSimpleName() : " is null ");
        f.a((String) TAG, sb.toString());
    }

    public void preProcessResponse(Response response2) {
        HttpUrlHeader header = ((HttpUrlResponse) response2).getHeader();
        int intValue = Integer.valueOf(header.getHead("Result-Status")).intValue();
        String head = header.getHead("Tips");
        if (intValue != 1000 && intValue != 8001) {
            RpcException rpcException = new RpcException(Integer.valueOf(intValue), decodeMemo(intValue, head));
            StringBuilder sb = new StringBuilder("preProcessResponserpcException hashcode: ");
            sb.append(rpcException.hashCode());
            sb.append(", errcode: ");
            sb.append(rpcException.getCode());
            sb.append(", errmsg: ");
            sb.append(rpcException.getMsg());
            throw rpcException;
        }
    }

    private String decodeMemo(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (Exception e) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("memo=[");
            sb.append(str);
            sb.append("]");
            f.b(TAG, sb.toString(), e);
            StringBuilder sb2 = new StringBuilder("很抱歉，系统错误 [");
            sb2.append(i);
            sb2.append("]。");
            return sb2.toString();
        }
    }
}
