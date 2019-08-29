package com.alipay.mobile.liteprocess.rpc;

import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.RpcInvocationHandler;
import com.alipay.mobile.common.rpc.protocol.util.RPCProtoDesc;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;
import com.alipay.mobile.common.transport.ext.ProtobufCodecImpl;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.Util;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.apache.http.util.EncodingUtils;

public class RpcCallServerImpl implements RpcCall {
    public CallRet call(CallArgs args) {
        InnerRpcInvokeContext innerRpcInvokeContext;
        LoggerFactory.getTraceLogger().info(Const.TAG, "RpcCallServerImpl call begin " + args.toString());
        Method method = null;
        RPCProtoDesc protoDesc = new RPCProtoDesc();
        protoDesc.protoType = args.protoType;
        try {
            Class clazz = Class.forName(args.className);
            Method[] methods = clazz.getMethods();
            int length = methods.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Method method_ = methods[i];
                if (method_.toGenericString().equals(args.methodName)) {
                    method = method_;
                    break;
                }
                i++;
            }
            Object[] rpcArgs = a(method, args.argsBody, protoDesc);
            Object proxy = ((RpcService) Util.getMicroAppContext().findServiceByInterface(RpcService.class.getName())).getBgRpcProxy(clazz);
            RpcInvocationHandler handler = (RpcInvocationHandler) Proxy.getInvocationHandler(proxy);
            handler.setInnerRpcInvokeContext(args.invokeContext);
            handler.getRpcInvokeContext().setAllowBgLogin(true);
            RpcException rpcException = null;
            Object returnObj = null;
            try {
                LoggerFactory.getTraceLogger().info(Const.TAG, "RpcCallServerImpl invoke begin " + args.toString());
                returnObj = handler.invoke(proxy, method, rpcArgs);
                LoggerFactory.getTraceLogger().info(Const.TAG, "RpcCallServerImpl invoke end " + args.toString());
            } catch (RpcException e) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "RpcCallServerImpl RpcException " + Log.getStackTraceString(e));
                rpcException = e;
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "RpcCallServerImpl  error " + Log.getStackTraceString(t));
            }
            LoggerFactory.getTraceLogger().info(Const.TAG, "RpcCallServerImpl call end " + args.toString());
            if (rpcException != null) {
                return a(rpcException);
            }
            if (handler.getRpcInvokeContext() instanceof InnerRpcInvokeContext) {
                innerRpcInvokeContext = (InnerRpcInvokeContext) handler.getRpcInvokeContext();
            } else {
                innerRpcInvokeContext = args.invokeContext;
            }
            return a(returnObj, protoDesc, innerRpcInvokeContext);
        } catch (ClassNotFoundException e2) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, "RpcCallServerImpl call ClassNotFoundException " + Log.getStackTraceString(e2));
            return null;
        }
    }

    @Nullable
    private static Object[] a(Method method, byte[] argsBody, RPCProtoDesc protoDesc) {
        Type[] types = method.getParameterTypes();
        if (protoDesc.isJsonV2() || protoDesc.isJsonV1() || protoDesc.isSimpleJsonV1() || protoDesc.isSimplePBV1()) {
            JSONArray jsonArray = JSON.parseArray(EncodingUtils.getString(argsBody, "UTF-8"));
            if (jsonArray.size() != types.length) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "jsonArray.size() != types.length");
                return null;
            }
            ArrayList retObjs = new ArrayList();
            for (int i = 0; i < jsonArray.size(); i++) {
                retObjs.add(jsonArray.getObject(i, (Class) types[i]));
            }
            return retObjs.toArray();
        } else if (!protoDesc.isPBV1()) {
            return null;
        } else {
            return new Object[]{new ProtobufCodecImpl().deserialize(types[0], argsBody)};
        }
    }

    private static CallRet a(Object ret, RPCProtoDesc protoDesc, InnerRpcInvokeContext innerRpcInvokeContext) {
        String bodyStr;
        CallRet callRet = new CallRet();
        callRet.isException = false;
        if (protoDesc.isJsonV2() || protoDesc.isJsonV1() || protoDesc.isSimpleJsonV1()) {
            if (ret == null) {
                bodyStr = "[]";
            } else {
                bodyStr = JSON.toJSONString(ret, SerializerFeature.DisableCircularReferenceDetect);
            }
            try {
                callRet.data = bodyStr.getBytes("UTF-8");
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "packetRet error " + Log.getStackTraceString(e));
            }
        } else if (protoDesc.isPBV1() || protoDesc.isSimplePBV1()) {
            callRet.data = new ProtobufCodecImpl().serialize(ret);
        }
        callRet.invokeContext = new LiteRpcInvokeContext(innerRpcInvokeContext);
        return callRet;
    }

    private static CallRet a(RpcException e) {
        LiteRpcException liteRpcException = new LiteRpcException(e);
        CallRet callRet = new CallRet();
        Parcel out = Parcel.obtain();
        try {
            out.writeValue(liteRpcException);
            callRet.data = out.marshall();
        } catch (Exception exp) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, "packetException error " + Log.getStackTraceString(exp));
        } finally {
            out.recycle();
        }
        callRet.isException = true;
        return callRet;
    }
}
