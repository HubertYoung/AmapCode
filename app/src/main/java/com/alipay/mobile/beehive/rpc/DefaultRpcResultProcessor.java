package com.alipay.mobile.beehive.rpc;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.reflect.Field;

public class DefaultRpcResultProcessor<ResultType> extends BaseRpcResultProcessor<ResultType> {
    public boolean isSuccess(ResultType result) {
        boolean z = false;
        if (RpcUtil.isRpcSuccess(result)) {
            return true;
        }
        Field baseField = RpcUtil.getFieldByReflect(result, RpcConstant.BASE);
        if (baseField == null) {
            return z;
        }
        try {
            return RpcUtil.isRpcSuccess(baseField.get(result));
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return z;
        }
    }

    public String convertResultText(ResultType result) {
        try {
            Field f = RpcUtil.getFieldByReflect(result, RpcConstant.RESULT_VIEW);
            if (f != null) {
                f.setAccessible(true);
                return (String) f.get(result);
            }
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "resultView字段不存在");
            return "";
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return "";
        }
    }
}
