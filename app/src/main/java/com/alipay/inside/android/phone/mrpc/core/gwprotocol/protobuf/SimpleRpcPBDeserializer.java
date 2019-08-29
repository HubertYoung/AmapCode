package com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.inside.android.phone.mrpc.core.Response;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.utils.RpcInvokerUtil;
import java.lang.reflect.Type;

public class SimpleRpcPBDeserializer extends PBDeserializer {
    public SimpleRpcPBDeserializer(Type type, Response response) {
        super(type, response);
    }

    public Object parser() throws RpcException {
        try {
            LoggerFactory.f().a((String) "SimpleRpc", (String) "====SimpleRpcPBDeserializer====parser");
            RpcInvokerUtil.preProcessResponse(this.response);
            if (this.mType == Void.TYPE) {
                return null;
            }
            return this.mData;
        } catch (Exception e) {
            if (e instanceof RpcException) {
                throw ((RpcException) e);
            }
            throw new RpcException(Integer.valueOf(10), (Throwable) e);
        }
    }
}
