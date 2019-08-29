package com.alipay.mobile.common.rpc.protocol.protobuf;

import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.util.RpcInvokerUtil;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import java.lang.reflect.Type;

public class SimpleRpcPBDeserializer extends PBDeserializer {
    private static final String TAG = "SimpleRpcPBDeserializer";

    public SimpleRpcPBDeserializer(Type type, Response response) {
        super(type, response);
    }

    public Object parser() {
        try {
            LogCatUtil.debug("SimpleRpc", "====SimpleRpcPBDeserializer====parser");
            RpcInvokerUtil.preProcessResponse(this.response);
            if (this.mType == Void.TYPE) {
                return null;
            }
            return this.mData;
        } catch (Throwable e) {
            if (e instanceof RpcException) {
                throw ((RpcException) e);
            }
            MonitorErrorLogHelper.log(TAG, MiscUtils.getRootCause(e));
            throw new RpcException(Integer.valueOf(10), e);
        }
    }
}
