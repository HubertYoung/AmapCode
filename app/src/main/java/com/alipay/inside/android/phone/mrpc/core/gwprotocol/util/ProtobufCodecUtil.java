package com.alipay.inside.android.phone.mrpc.core.gwprotocol.util;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.ProtobufCodec;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.ProtobufCodecImpl;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.ProtobufCodecImplDegrade;
import java.lang.reflect.Type;

public class ProtobufCodecUtil {
    private static boolean isPbReferenceDegrade(Type type) {
        Class<? super T> cls = (Class) type;
        int i = 0;
        while (cls != null && i < 10) {
            String name = cls.getName();
            LoggerFactory.f().b((String) "inside", "ProtobufCodecUtil::isPbReferenceDegrade > name:".concat(String.valueOf(name)));
            if (name.contains("com.squareup.wire.Message")) {
                LoggerFactory.d().a("rpc", BehaviorType.EVENT, "PbReferenceDegrade").a("match com.squareup.wire.Message!");
                return true;
            }
            cls = cls.getSuperclass();
            i++;
        }
        return false;
    }

    public static boolean isPBBean(Type type) {
        Class<? super T> cls = (Class) type;
        boolean z = false;
        int i = 0;
        while (true) {
            if (cls == null || i >= 10) {
                break;
            }
            String name = cls.getName();
            LoggerFactory.f().b((String) "inside", "ProtobufCodecUtil::isPBBean > name:".concat(String.valueOf(name)));
            if (name.contains("com.squareup.wire.Message") || name.contains("com.alipay.android.phone.inside.protobuf.wire.Message")) {
                z = true;
            } else {
                cls = cls.getSuperclass();
                i++;
            }
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("ProtobufCodecUtil::isPBBean > ");
        sb.append(type.toString());
        sb.append(",");
        sb.append(z);
        f.b((String) "inside", sb.toString());
        return z;
    }

    public static ProtobufCodec getProtobufCodec(Type type) {
        if (isPbReferenceDegrade(type)) {
            return new ProtobufCodecImplDegrade();
        }
        return new ProtobufCodecImpl();
    }
}
