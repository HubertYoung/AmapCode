package com.alipay.apmobilesecuritysdk.apdidgen;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.loggers.LoggerUtil;
import com.alipay.apmobilesecuritysdk.storage.TokenStorage;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.Map;

public class ApdidFinalizeProcessor implements ApdidProcessor {
    private TraceLogger a = LoggerFactory.f();

    public final boolean a(Context context, Map<String, Object> map) {
        long currentTimeMillis = System.currentTimeMillis();
        this.a.b((String) CONST.LOG_TAG, "ApdidFinalizeProcessor() start :".concat(String.valueOf(currentTimeMillis)));
        LoggerUtil.a(context);
        if (CommonUtils.getIntegerFromMap(map, "resultcode", -1) != 1) {
            String stringFromMap = CommonUtils.getStringFromMap(map, "appName", "");
            if (CommonUtils.isBlank(TokenStorage.a()) || CommonUtils.isBlank(TokenStorage.a(stringFromMap))) {
                map.put("resultcode", Integer.valueOf(4));
            } else {
                map.put("resultcode", Integer.valueOf(0));
            }
        }
        TraceLogger traceLogger = this.a;
        StringBuilder sb = new StringBuilder("ApdidFinalizeProcessor() cost ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        sb.append(" ms.");
        traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        return false;
    }
}
