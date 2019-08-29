package com.alipay.mobile.liteprocess.rpc;

import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.rpc.RpcFactory;
import com.alipay.mobile.common.rpc.RpcInvoker;
import com.alipay.mobile.common.rpc.protocol.Serializer;
import com.alipay.mobile.common.rpc.protocol.util.RPCProtoDesc;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;
import com.alipay.mobile.common.rpc.util.RpcInvokerUtil;
import com.alipay.mobile.common.transport.ext.ProtobufCodecImpl;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.Util;
import com.alipay.mobile.liteprocess.ipc.IpcCallClient;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.apache.http.util.EncodingUtils;

public class RpcCallClientInvoker extends RpcInvoker {
    public RpcCallClientInvoker(RpcFactory rpcFactory) {
        super(rpcFactory);
    }

    public Object invoke(Object proxy, Class<?> clazz, Method method, Object[] args, InnerRpcInvokeContext invokeContext) {
        if (!Util.isLiteProcess()) {
            return super.invoke(proxy, clazz, method, args, invokeContext);
        }
        LoggerFactory.getTraceLogger().info(Const.TAG, "RpcCallClientInvoker invoke begin " + Arrays.toString(args));
        int id = this.rpcSequence.incrementAndGet();
        RPCProtoDesc protoDesc = new RPCProtoDesc();
        Serializer serializer = this.serializerFactory.getSerializer(id, RpcInvokerUtil.getOperationTypeValue(method, args), method, args, getScene(), invokeContext, protoDesc);
        if (EXT_PARAM.get() != null) {
            serializer.setExtParam(EXT_PARAM.get());
        }
        byte[] argsBody = a(args, protoDesc);
        Object ret = null;
        try {
            CallArgs callArgs = new CallArgs();
            callArgs.className = clazz.getName();
            callArgs.methodName = method.toGenericString();
            callArgs.argsBody = argsBody;
            callArgs.protoType = protoDesc.protoType;
            callArgs.invokeContext = new LiteRpcInvokeContext(invokeContext);
            LoggerFactory.getTraceLogger().info(Const.TAG, "RpcCallClientInvoker call begin " + Arrays.toString(args));
            CallRet callRet = ((RpcCall) IpcCallClient.getIpcProxy(RpcCall.class)).call(callArgs);
            LoggerFactory.getTraceLogger().info(Const.TAG, "RpcCallClientInvoker call end " + Arrays.toString(args));
            if (callRet.isException) {
                LiteRpcException rpcException = a(callRet.data);
                if (rpcException != null) {
                    throw rpcException;
                }
            } else {
                ret = a(method, callRet.data, protoDesc);
                a(invokeContext, callRet.invokeContext);
            }
        } catch (LiteRpcException e) {
            throw e;
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, "RpcCallClientInvoker invoke error " + Log.getStackTraceString(e2));
        }
        LoggerFactory.getTraceLogger().info(Const.TAG, "RpcCallClientInvoker invoke end " + Arrays.toString(args));
        return ret;
    }

    @Nullable
    private static byte[] a(Object[] args, RPCProtoDesc protoDesc) {
        String bodyStr;
        if (protoDesc.isJsonV2() || protoDesc.isJsonV1() || protoDesc.isSimpleJsonV1() || protoDesc.isSimplePBV1()) {
            if (args == null) {
                bodyStr = "[]";
            } else {
                bodyStr = JSON.toJSONString((Object) args, SerializerFeature.DisableCircularReferenceDetect);
            }
            try {
                return bodyStr.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "packetArgs error " + Log.getStackTraceString(e));
            }
        } else {
            if (protoDesc.isPBV1()) {
                return new ProtobufCodecImpl().serialize(args[0]);
            }
            return null;
        }
    }

    @Nullable
    private static Object a(Method method, byte[] retBody, RPCProtoDesc protoDesc) {
        Type type = method.getGenericReturnType();
        if (protoDesc.isJsonV2() || protoDesc.isJsonV1() || protoDesc.isSimpleJsonV1()) {
            return JSON.parseObject(EncodingUtils.getString(retBody, "UTF-8"), type, new Feature[0]);
        }
        if (protoDesc.isPBV1() || protoDesc.isSimplePBV1()) {
            return new ProtobufCodecImpl().deserialize(type, retBody);
        }
        return null;
    }

    private static void a(InnerRpcInvokeContext origInvokeContext, LiteRpcInvokeContext invokeContext) {
        if (origInvokeContext != null && invokeContext != null) {
            origInvokeContext.timeout = invokeContext.timeout;
            origInvokeContext.gwUrl = invokeContext.gwUrl;
            origInvokeContext.requestHeaders = invokeContext.requestHeaders;
            origInvokeContext.compress = invokeContext.compress;
            origInvokeContext.appKey = invokeContext.appKey;
            origInvokeContext.resetCookie = invokeContext.resetCookie;
            origInvokeContext.bgRpc = invokeContext.bgRpc;
            origInvokeContext.responseHeader = invokeContext.responseHeader;
            origInvokeContext.allowRetry = invokeContext.allowRetry;
            origInvokeContext.isUrgent = invokeContext.isUrgent;
            origInvokeContext.isRpcV2 = invokeContext.isRpcV2;
            origInvokeContext.allowBgLogin = invokeContext.allowBgLogin;
            origInvokeContext.allowNonNet = invokeContext.allowNonNet;
        }
    }

    @Nullable
    private static LiteRpcException a(byte[] retBody) {
        Parcel in = Parcel.obtain();
        LiteRpcException rpcException = null;
        try {
            in.unmarshall(retBody, 0, retBody.length);
            in.setDataPosition(0);
            rpcException = (LiteRpcException) in.readValue(LiteRpcException.class.getClassLoader());
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, "unpacketException error " + Log.getStackTraceString(e));
        } finally {
            in.recycle();
        }
        return rpcException;
    }
}
