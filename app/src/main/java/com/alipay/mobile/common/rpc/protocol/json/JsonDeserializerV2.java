package com.alipay.mobile.common.rpc.protocol.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.protocol.AbstractDeserializer;
import com.alipay.mobile.common.rpc.util.RpcInvokerUtil;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import java.lang.reflect.Type;
import org.apache.http.util.EncodingUtils;

public class JsonDeserializerV2 extends AbstractDeserializer {
    private static final String TAG = "JsonDeserializerV2";
    private Response response;

    public JsonDeserializerV2(Type type, byte[] data) {
        super(type, data);
    }

    public JsonDeserializerV2(Type type, Response response2) {
        super(type, response2.getResData());
        this.response = response2;
    }

    public Object parser() {
        String message;
        try {
            RpcInvokerUtil.preProcessResponse(this.response);
            if (this.mType == Void.TYPE) {
                return null;
            }
            return JSON.parseObject(EncodingUtils.getString(this.mData, "UTF-8"), this.mType, new Feature[0]);
        } catch (Throwable e) {
            if (e instanceof RpcException) {
                throw ((RpcException) e);
            }
            MonitorErrorLogHelper.log(TAG, MiscUtils.getRootCause(e));
            Integer valueOf = Integer.valueOf(10);
            if (("response  =" + "" + ":" + e) == null) {
                message = "";
            } else {
                message = e.getMessage();
            }
            RpcException rpcException = new RpcException(valueOf, message);
            rpcException.initCause(e);
            logResult("");
            throw rpcException;
        }
    }

    private void logResult(String resData) {
        LogCatUtil.verbose(TAG, "threadid = " + Thread.currentThread().getId() + "; rpc response:  " + resData + " mType=" + (this.mType != null ? this.mType.getClass().getSimpleName() : " is null "));
    }
}
