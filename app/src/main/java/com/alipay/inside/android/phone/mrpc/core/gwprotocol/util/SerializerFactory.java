package com.alipay.inside.android.phone.mrpc.core.gwprotocol.util;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.InnerRpcInvokeContext;
import com.alipay.inside.android.phone.mrpc.core.Response;
import com.alipay.inside.android.phone.mrpc.core.RpcFactory;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.Deserializer;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.Serializer;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.json.JsonDeserializer;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.json.JsonDeserializerV2;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.json.JsonSerializerV2;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.json.SimpleRpcJsonSerializerV2;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.PBDeserializer;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.PBSerializer;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.SimpleRpcPBDeserializer;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.SimpleRpcPBSerializer;
import com.alipay.inside.android.phone.mrpc.core.utils.RpcInvokerUtil;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class SerializerFactory {
    private RpcFactory rpcFactory;

    public SerializerFactory(RpcFactory rpcFactory2) {
        this.rpcFactory = rpcFactory2;
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("SerializerFactory::(x) >");
        sb.append(this.rpcFactory);
        f.b((String) "rpc", sb.toString());
    }

    public Serializer getSerializer(int i, String str, Method method, Object[] objArr, InnerRpcInvokeContext innerRpcInvokeContext, RPCProtoDesc rPCProtoDesc) {
        if (isPBInParam(objArr, method)) {
            rPCProtoDesc.protoType = 3;
            return new PBSerializer(i, str, objArr);
        } else if (RpcInvokerUtil.isSimpleRpcAnnotation(((OperationType) method.getAnnotation(OperationType.class)).value())) {
            rPCProtoDesc.protoType = 2;
            return new SimpleRpcJsonSerializerV2(i, str, objArr);
        } else if (RpcInvokerUtil.isSimpleRpcBytesAnnotation(((OperationType) method.getAnnotation(OperationType.class)).value())) {
            rPCProtoDesc.protoType = 4;
            return new SimpleRpcPBSerializer(i, str, objArr);
        } else {
            rPCProtoDesc.protoType = 2;
            return new JsonSerializerV2(i, str, objArr);
        }
    }

    public Deserializer getDeserializer(Type type, Response response, RPCProtoDesc rPCProtoDesc) {
        if (rPCProtoDesc.isJsonV2()) {
            return new JsonDeserializerV2(type, response);
        }
        if (rPCProtoDesc.isPBV1()) {
            return new PBDeserializer(type, response);
        }
        if (rPCProtoDesc.isSimplePBV1()) {
            return new SimpleRpcPBDeserializer(type, response);
        }
        return new JsonDeserializer(type, response.getResData());
    }

    private boolean isPBInParam(Object[] objArr, Method method) {
        if (objArr != null) {
            try {
                if (objArr.length == 1 && ProtobufCodecUtil.isPBBean(objArr[0].getClass())) {
                    return true;
                }
            } catch (Exception e) {
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("SerializerFactory ex:");
                sb.append(e.toString());
                f.d("SerializerFactory", sb.toString());
                return false;
            }
        }
        return ProtobufCodecUtil.isPBBean(method.getReturnType());
    }

    public String getContentType(RPCProtoDesc rPCProtoDesc) {
        if (rPCProtoDesc.isJsonV2()) {
            return "application/json";
        }
        return rPCProtoDesc.isPBV1() ? "application/protobuf" : "application/x-www-form-urlencoded";
    }
}
