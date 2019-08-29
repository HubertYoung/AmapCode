package com.alipay.mobile.common.rpc.protocol.util;

import android.text.TextUtils;
import com.alipay.mobile.common.rpc.Config;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.RpcFactory;
import com.alipay.mobile.common.rpc.protocol.Deserializer;
import com.alipay.mobile.common.rpc.protocol.Serializer;
import com.alipay.mobile.common.rpc.protocol.json.JsonDeserializer;
import com.alipay.mobile.common.rpc.protocol.json.JsonDeserializerV2;
import com.alipay.mobile.common.rpc.protocol.json.JsonSerializer;
import com.alipay.mobile.common.rpc.protocol.json.JsonSerializerV2;
import com.alipay.mobile.common.rpc.protocol.json.SignJsonSerializer;
import com.alipay.mobile.common.rpc.protocol.json.SimpleRpcJsonSerializer;
import com.alipay.mobile.common.rpc.protocol.json.SimpleRpcJsonSerializerV2;
import com.alipay.mobile.common.rpc.protocol.protobuf.PBDeserializer;
import com.alipay.mobile.common.rpc.protocol.protobuf.PBSerializer;
import com.alipay.mobile.common.rpc.protocol.protobuf.SimpleRpcPBDeserializer;
import com.alipay.mobile.common.rpc.protocol.protobuf.SimpleRpcPBSerializer;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;
import com.alipay.mobile.common.rpc.util.RpcInvokerUtil;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.ext.ProtobufCodec;
import com.alipay.mobile.common.transport.ext.ProtobufCodecImpl;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.framework.service.annotation.OperationType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public class SerializerFactory {
    private RpcFactory rpcFactory;

    public SerializerFactory(RpcFactory rpcFactory2) {
        this.rpcFactory = rpcFactory2;
    }

    private boolean isRpcV2(Object[] args, Method method, InnerRpcInvokeContext rpcInvokeContext) {
        if (rpcInvokeContext.isRpcV2) {
            return true;
        }
        if (rpcInvokeContext.isGetMethod) {
            return false;
        }
        String rpcVersion = RpcInvokerUtil.getRpcVersion();
        if (TextUtils.equals(rpcVersion, "V2")) {
            return true;
        }
        if (TextUtils.equals(rpcVersion, "V1")) {
            return false;
        }
        if (!isSupportRpcV2ForEnv(rpcInvokeContext)) {
            return false;
        }
        if (isPBInParam(args, method)) {
            return true;
        }
        Boolean enableAmnetSetting = ReadSettingServerUrl.getInstance().isEnableAmnetSetting(this.rpcFactory.getContext());
        if (enableAmnetSetting == null || enableAmnetSetting != Boolean.TRUE) {
            return TransportStrategy.isEnabledRpcV2();
        }
        return true;
    }

    private boolean isSupportRpcV2ForEnv(InnerRpcInvokeContext rpcInvokeContext) {
        try {
            if (TransportStrategy.isAlipayUrl(rpcInvokeContext.gwUrl)) {
                LogCatUtil.info("SerializerFactory", "isSupportRpcV2ForEnv is true");
                return true;
            }
            LogCatUtil.info("SerializerFactory", "isSupportRpcV2ForEnv is false");
            return false;
        } catch (MalformedURLException e) {
            throw new RpcException(Integer.valueOf(3002), "Illegal url config, url: " + (rpcInvokeContext != null ? rpcInvokeContext.gwUrl : ""));
        }
    }

    public Serializer getSerializer(int id, String operationType, Method method, Object[] params, String scene, InnerRpcInvokeContext invokeContext, RPCProtoDesc protoDesc) {
        JsonSerializer jsonSerializer;
        if (!isRpcV2(params, method, invokeContext)) {
            if (RpcInvokerUtil.isSimpleRpcAnnotation(((OperationType) method.getAnnotation(OperationType.class)).value())) {
                jsonSerializer = new SimpleRpcJsonSerializer(id, operationType, params);
                protoDesc.protoType = 1;
            } else if (RpcInvokerUtil.isSimpleRpcBytesAnnotation(((OperationType) method.getAnnotation(OperationType.class)).value())) {
                throw new IllegalArgumentException("alipay.client.executerpc.bytes can't use in RpcV1");
            } else {
                JsonSerializer jsonSerializer2 = new JsonSerializer(id, operationType, params);
                jsonSerializer2.setScene(scene);
                jsonSerializer = jsonSerializer2;
                protoDesc.protoType = 0;
            }
            return new SignJsonSerializer(jsonSerializer, this.rpcFactory.getContext(), invokeContext.appKey, isReq2Online(), invokeContext);
        } else if (isPBInParam(params, method)) {
            protoDesc.protoType = 3;
            return new PBSerializer(id, operationType, params);
        } else if (RpcInvokerUtil.isSimpleRpcAnnotation(((OperationType) method.getAnnotation(OperationType.class)).value())) {
            protoDesc.protoType = 2;
            return new SimpleRpcJsonSerializerV2(id, operationType, params);
        } else if (RpcInvokerUtil.isSimpleRpcBytesAnnotation(((OperationType) method.getAnnotation(OperationType.class)).value())) {
            protoDesc.protoType = 4;
            return new SimpleRpcPBSerializer(id, operationType, params);
        } else {
            protoDesc.protoType = 2;
            return new JsonSerializerV2(id, operationType, params);
        }
    }

    public boolean isReq2Online() {
        Config config = this.rpcFactory.getConfig();
        if (TextUtils.isEmpty(config.getUrl())) {
            LogCatUtil.warn((String) "rpc", (String) "handler.getConfig().getUrl() is null");
            return false;
        }
        try {
            if (new URL(config.getUrl()).getHost().lastIndexOf("alipay.com") != -1) {
                return true;
            }
            return false;
        } catch (MalformedURLException e) {
            LogCatUtil.warn((String) "rpc", (Throwable) e);
            return false;
        }
    }

    public Deserializer getDeserializer(Type type, Response response, RPCProtoDesc protoDesc) {
        if (protoDesc.isJsonV2()) {
            return new JsonDeserializerV2(type, response);
        }
        if (protoDesc.isPBV1()) {
            return new PBDeserializer(type, response);
        }
        if (protoDesc.isSimplePBV1()) {
            return new SimpleRpcPBDeserializer(type, response);
        }
        return new JsonDeserializer(type, response.getResData());
    }

    private boolean isPBInParam(Object[] params, Method method) {
        try {
            ProtobufCodec protobufCodec = new ProtobufCodecImpl();
            if ((params == null || params.length != 1 || !protobufCodec.isPBBean(params[0])) && !protobufCodec.isPBBean((Class) method.getReturnType())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            LogCatUtil.error((String) "SerializerFactory", (Throwable) e);
            return false;
        }
    }

    public String getContentType(RPCProtoDesc protoDesc) {
        if (protoDesc.isJsonV2()) {
            return "application/json";
        }
        if (protoDesc.isPBV1()) {
            return "application/protobuf";
        }
        return "application/x-www-form-urlencoded";
    }
}
