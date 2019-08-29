package com.alipay.inside.android.phone.mrpc.core.utils;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.HttpUrlHeader;
import com.alipay.inside.android.phone.mrpc.core.HttpUrlResponse;
import com.alipay.inside.android.phone.mrpc.core.Response;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import java.lang.reflect.Method;
import java.net.URLDecoder;

public final class RpcInvokerUtil {
    public static final String RPC_V1 = "V1";
    public static final String RPC_V2 = "V2";
    public static final String SIMPLE_RPC_OPERATION_TYPE = "alipay.client.executerpc";
    public static final String SIMPLE_RPC_OPERATION_TYPE_BYTES = "alipay.client.executerpc.bytes";
    private static final String TAG = "RpcInvokerUtil";

    public static final boolean isSimpleRpcAnnotation(String str) {
        return TextUtils.equals(str, "alipay.client.executerpc");
    }

    public static final boolean isSimpleRpcBytesAnnotation(String str) {
        return TextUtils.equals(str, "alipay.client.executerpc.bytes");
    }

    public static final String getOperationTypeValue(Method method, Object[] objArr) {
        OperationType operationType = (OperationType) method.getAnnotation(OperationType.class);
        if (operationType == null) {
            throw new IllegalStateException("OperationType must be set.");
        }
        String value = operationType.value();
        if (isSimpleRpcAnnotation(value) || isSimpleRpcBytesAnnotation(value)) {
            return String.valueOf(objArr[0]);
        }
        return value;
    }

    public static void preProcessResponse(Response response) {
        HttpUrlHeader header = ((HttpUrlResponse) response).getHeader();
        int intValue = Integer.valueOf(header.getHead("Result-Status")).intValue();
        String head = header.getHead("Tips");
        if (intValue != 1000 && intValue != 8001) {
            RpcException rpcException = new RpcException(Integer.valueOf(intValue), decodeMemo(intValue, head));
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("preProcessResponse:rpcException hashcode: ");
            sb.append(rpcException.hashCode());
            sb.append(", errcode: ");
            sb.append(rpcException.getCode());
            sb.append(", errmsg: ");
            sb.append(rpcException.getMsg());
            f.a((String) TAG, sb.toString());
            throw rpcException;
        }
    }

    private static String decodeControlStr(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("control=[");
            sb.append(str);
            sb.append("]");
            f.b(TAG, sb.toString(), e);
            return str;
        }
    }

    private static String decodeMemo(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (Exception e) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("memo=[");
            sb.append(str);
            sb.append("]");
            f.b(TAG, sb.toString(), e);
            StringBuilder sb2 = new StringBuilder("很抱歉，系统错误 [");
            sb2.append(i);
            sb2.append("]。");
            return sb2.toString();
        }
    }
}
