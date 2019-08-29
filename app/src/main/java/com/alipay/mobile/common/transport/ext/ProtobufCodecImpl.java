package com.alipay.mobile.common.transport.ext;

import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.squareup.wire.Message;
import com.squareup.wire.Wire;
import java.lang.reflect.Type;

public class ProtobufCodecImpl implements ProtobufCodec {
    public Object deserialize(Type type, byte[] data) {
        if (type == null) {
            throw new IllegalArgumentException("type maybe null.");
        } else if (!(type instanceof Class)) {
            throw new IllegalArgumentException("type is not Class Type.");
        } else {
            try {
                return new Wire((Class<?>[]) new Class[0]).parseFrom(data, (Class) type);
            } catch (Throwable e) {
                String base64RawResp = a(data);
                LogCatUtil.error("ProtobufCodec", "deserialize fail. type is " + type.toString() + ", " + (!TextUtils.isEmpty(base64RawResp) ? "pb data:[" + base64RawResp + "]" : ""), e);
                throw new RuntimeException(e);
            }
        }
    }

    public byte[] serialize(Object object) {
        if (object instanceof Message) {
            return ((Message) object).toByteArray();
        }
        return null;
    }

    public boolean isPBBean(Object object) {
        return object instanceof Message;
    }

    public boolean isPBBean(Class clazz) {
        try {
            return Message.class.isAssignableFrom(clazz);
        } catch (Throwable e) {
            LogCatUtil.warn((String) "ProtobufCodec", "isPBBean class " + e.toString());
            return false;
        }
    }

    public String toString(Object object) {
        return "";
    }

    private static String a(byte[] data) {
        String base64RawResp = "";
        try {
            if (data.length > TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.EXPORT_RES_DATA_LIMIT)) {
                return "";
            }
            base64RawResp = Base64.encodeToString(data, 11);
            return base64RawResp;
        } catch (Throwable e) {
            LogCatUtil.warn("ProtobufCodec", "exportBase64RawResp fail", e);
        }
    }
}
