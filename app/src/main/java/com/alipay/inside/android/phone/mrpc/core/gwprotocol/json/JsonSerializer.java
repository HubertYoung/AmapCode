package com.alipay.inside.android.phone.mrpc.core.gwprotocol.json;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.AbstractSerializer;
import com.alipay.inside.jsoncodec.JSONCodec;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.util.ArrayList;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class JsonSerializer extends AbstractSerializer {
    public static final String TAG = "JsonSerializer";
    public static final String VERSION = "1.0.0";
    private Object mExtParam;
    private int mId;
    protected String mRequestDataJson;

    public JsonSerializer(int i, String str, Object obj) {
        super(str, obj);
        this.mId = i;
    }

    public void setExtParam(Object obj) {
        this.mExtParam = obj;
    }

    public byte[] packet() throws RpcException {
        String str;
        String str2;
        try {
            ArrayList arrayList = new ArrayList();
            if (this.mExtParam != null) {
                arrayList.add(new BasicNameValuePair("extParam", JSONCodec.toJSONString(this.mExtParam)));
            }
            arrayList.add(new BasicNameValuePair(TransportConstants.KEY_OPERATION_TYPE, this.mOperationType));
            StringBuilder sb = new StringBuilder();
            sb.append(this.mId);
            arrayList.add(new BasicNameValuePair("id", sb.toString()));
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb2 = new StringBuilder("mParams is:");
            sb2.append(this.mParams);
            f.a((String) "JsonSerializer", sb2.toString());
            if (this.mParams == null) {
                str2 = "[]";
            } else {
                str2 = JSONCodec.toJSONString(this.mParams);
            }
            arrayList.add(new BasicNameValuePair("requestData", str2));
            String format = URLEncodedUtils.format(arrayList, "utf-8");
            LoggerFactory.f().a((String) "JsonSerializer", "request = ".concat(String.valueOf(format)));
            return format.getBytes();
        } catch (Exception e) {
            Integer valueOf = Integer.valueOf(9);
            StringBuilder sb3 = new StringBuilder("request  =");
            sb3.append(this.mParams);
            sb3.append(":");
            sb3.append(e);
            if (sb3.toString() == null) {
                str = "";
            } else {
                str = e.getMessage();
            }
            throw new RpcException(valueOf, str, e);
        }
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public String getRequestDataJson() {
        if (!TextUtils.isEmpty(this.mRequestDataJson)) {
            return this.mRequestDataJson;
        }
        try {
            this.mRequestDataJson = this.mParams == null ? "[]" : JSONCodec.toJSONString(this.mParams);
        } catch (Throwable th) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("getRequestDataJson ex");
            sb.append(th.toString());
            f.d("JsonSerializer", sb.toString());
        }
        return this.mRequestDataJson;
    }
}
