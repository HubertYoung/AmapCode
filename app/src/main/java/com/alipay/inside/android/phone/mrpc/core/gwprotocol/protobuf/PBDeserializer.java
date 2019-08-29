package com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.Response;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.AbstractDeserializer;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.util.ProtobufCodecUtil;
import com.alipay.inside.android.phone.mrpc.core.utils.RpcInvokerUtil;
import java.lang.reflect.Type;

public class PBDeserializer extends AbstractDeserializer {
    private static final String TAG = "PBDeserializer";
    public Response response;

    public PBDeserializer(Type type, Response response2) {
        super(type, response2.getResData());
        this.response = response2;
    }

    public Object parser() throws RpcException {
        try {
            RpcInvokerUtil.preProcessResponse(this.response);
            if (this.mType == Void.TYPE) {
                return null;
            }
            ProtobufCodec protobufCodec = ProtobufCodecUtil.getProtobufCodec(this.mType);
            if (protobufCodec != null) {
                return protobufCodec.deserialize(this.mType, this.mData);
            }
            throw new RuntimeException("protobufCodec == null");
        } catch (Throwable th) {
            if (th instanceof RpcException) {
                throw ((RpcException) th);
            }
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("parser ex:");
            sb.append(th.toString());
            f.d(TAG, sb.toString());
            throw new RpcException(Integer.valueOf(10), th);
        }
    }
}
