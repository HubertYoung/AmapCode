package com.alipay.mobile.common.rpc.protocol.protobuf;

import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.protocol.AbstractDeserializer;
import com.alipay.mobile.common.rpc.util.RpcInvokerUtil;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.ext.ProtobufCodecImpl;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import java.lang.reflect.Type;

public class PBDeserializer extends AbstractDeserializer {
    private static final String TAG = "PBDeserializer";
    public Response response;

    public PBDeserializer(Type type, Response response2) {
        super(type, response2.getResData());
        this.response = response2;
    }

    public Object parser() {
        try {
            RpcInvokerUtil.preProcessResponse(this.response);
            if (this.mType == Void.TYPE) {
                return null;
            }
            return new ProtobufCodecImpl().deserialize(this.mType, this.mData);
        } catch (Throwable e) {
            if (e instanceof RpcException) {
                throw ((RpcException) e);
            }
            MonitorErrorLogHelper.log(TAG, MiscUtils.getRootCause(e));
            throw new RpcException(Integer.valueOf(10), e);
        }
    }
}
