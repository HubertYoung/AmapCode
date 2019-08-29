package com.alipay.deviceid.module.x;

import com.alipay.deviceid.module.rpc.mrpc.core.RpcException;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.util.ArrayList;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public final class as extends ap {
    private int c;
    private Object d;

    public as(int i, String str, Object obj) {
        super(str, obj);
        this.c = i;
    }

    public final void a(Object obj) {
        this.d = obj;
    }

    public final byte[] a() {
        try {
            ArrayList arrayList = new ArrayList();
            if (this.d != null) {
                arrayList.add(new BasicNameValuePair("extParam", ag.a(this.d)));
            }
            arrayList.add(new BasicNameValuePair(TransportConstants.KEY_OPERATION_TYPE, this.a));
            StringBuilder sb = new StringBuilder();
            sb.append(this.c);
            arrayList.add(new BasicNameValuePair("id", sb.toString()));
            new StringBuilder("mParams is:").append(this.b);
            arrayList.add(new BasicNameValuePair("requestData", this.b == null ? "[]" : ag.a(this.b)));
            return URLEncodedUtils.format(arrayList, "utf-8").getBytes();
        } catch (Exception e) {
            Integer valueOf = Integer.valueOf(9);
            StringBuilder sb2 = new StringBuilder("request  =");
            sb2.append(this.b);
            sb2.append(":");
            sb2.append(e);
            throw new RpcException(valueOf, sb2.toString() == null ? "" : e.getMessage(), e);
        }
    }
}
