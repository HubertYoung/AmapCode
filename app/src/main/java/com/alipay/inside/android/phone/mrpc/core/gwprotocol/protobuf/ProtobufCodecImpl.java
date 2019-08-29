package com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf;

import android.text.TextUtils;
import android.util.Base64;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Wire;
import java.lang.reflect.Type;

public class ProtobufCodecImpl implements ProtobufCodec {
    private static final String TAG = "ProtobufCodec";

    public String toString(Object obj) {
        return "";
    }

    public Object deserialize(Type type, byte[] bArr) {
        String str;
        if (type == null) {
            throw new IllegalArgumentException("type maybe null.");
        } else if (!(type instanceof Class)) {
            throw new IllegalArgumentException("type is not Class Type.");
        } else {
            try {
                return new Wire((Class<?>[]) new Class[0]).a(bArr, (Class) type);
            } catch (Throwable th) {
                String exportBase64RawResp = exportBase64RawResp(bArr);
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("deserialize fail. type is ");
                sb.append(type.toString());
                sb.append(", ");
                if (!TextUtils.isEmpty(exportBase64RawResp)) {
                    StringBuilder sb2 = new StringBuilder("pb data:[");
                    sb2.append(exportBase64RawResp);
                    sb2.append("]");
                    str = sb2.toString();
                } else {
                    str = "";
                }
                sb.append(str);
                f.b(TAG, sb.toString(), th);
                throw new RuntimeException(th);
            }
        }
    }

    public byte[] serialize(Object obj) {
        if (obj instanceof Message) {
            return ((Message) obj).toByteArray();
        }
        return null;
    }

    public boolean isPBBean(Object obj) {
        return obj instanceof Message;
    }

    public boolean isPBBean(Class cls) {
        try {
            return Message.class.isAssignableFrom(cls);
        } catch (Throwable th) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("isPBBean class ");
            sb.append(th.toString());
            f.d(TAG, sb.toString());
            return false;
        }
    }

    private String exportBase64RawResp(byte[] bArr) {
        String str;
        try {
            if (bArr.length > 2048) {
                return "";
            }
            str = Base64.encodeToString(bArr, 11);
            return str;
        } catch (Throwable th) {
            LoggerFactory.f().b(TAG, "exportBase64RawResp fail", th);
            str = "";
        }
    }
}
