package com.alipay.mobile.common.transportext.biz.spdy.longlink;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.ConnectionPool;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import java.net.URL;

public final class SpdyLongLinkUtils {
    public static final String SPDY_LONG_LINK_OPER_TYPE = "spdy_long_link_connect";

    public static final Connection getConnectionFromPool(Context context) {
        try {
            URL url = new URL(ExtTransportStrategy.getSpdyUrl(context));
            return ConnectionPool.getDefault().getSpdyConnection(url.getHost(), Util.getEffectivePort(url));
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error((String) "SpdyLongLinkUtils", (Throwable) e);
            return null;
        }
    }

    public static final boolean isSpdyLongLinkOperType(String operationType) {
        if (operationType == null || TextUtils.equals(operationType.trim(), "")) {
            return false;
        }
        return TextUtils.equals(SPDY_LONG_LINK_OPER_TYPE, operationType);
    }

    public static final String getSpdyLongLinkOperType() {
        return SPDY_LONG_LINK_OPER_TYPE;
    }
}
