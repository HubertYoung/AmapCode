package com.alipay.mobile.beehive.rpc.action;

import android.text.TextUtils;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.RpcUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.reflect.Field;

public class DefaultShowTypeProcessor extends ShowTypeProcessor {
    public Integer getShowType(Object result) {
        try {
            Field showTypeField = RpcUtil.getFieldByReflect(result, RpcConstant.SHOW_TYPE);
            if (showTypeField != null) {
                showTypeField.setAccessible(true);
                return (Integer) showTypeField.get(result);
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
        }
        return null;
    }

    public String getResultView(Object result) {
        try {
            Field resultViewField = RpcUtil.getFieldByReflect(result, RpcConstant.RESULT_VIEW);
            if (resultViewField != null) {
                resultViewField.setAccessible(true);
                return (String) resultViewField.get(result);
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
        }
        return "";
    }

    public boolean handleShowType(RpcUiProcessor rr, Object result) {
        try {
            String resultView = getResultView(result);
            if (TextUtils.isEmpty(resultView)) {
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "resultView值为空");
                return false;
            }
            Integer showTypeInteger = getShowType(result);
            if (showTypeInteger != null) {
                int showType = showTypeInteger.intValue();
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "showType=" + showType + ", resultView=" + resultView);
                if (showType == 0) {
                    rr.toast(resultView, 0);
                    LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "toast resultView");
                } else if (showType != 1) {
                    return false;
                } else {
                    rr.alert("", resultView, RpcUtil.getString(rr, R.string.confirm), null, "", null, false);
                    LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "alert resultView");
                }
                return true;
            }
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "showType字段不存在");
            return false;
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
        }
    }
}
