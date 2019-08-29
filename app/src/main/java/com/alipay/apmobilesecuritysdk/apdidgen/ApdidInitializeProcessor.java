package com.alipay.apmobilesecuritysdk.apdidgen;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.commonbiz.AppLaunchTimeUtil;
import com.alipay.apmobilesecuritysdk.loggers.LoggerUtil;
import com.alipay.apmobilesecuritysdk.storage.TokenStorage;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.Map;

public class ApdidInitializeProcessor implements ApdidProcessor {
    private TraceLogger a = LoggerFactory.f();

    public final boolean a(Context context, Map<String, Object> map) {
        long currentTimeMillis = System.currentTimeMillis();
        this.a.b((String) CONST.LOG_TAG, "ApdidInitializeProcessor() start:".concat(String.valueOf(currentTimeMillis)));
        String stringFromMap = CommonUtils.getStringFromMap(map, "appName", "");
        String stringFromMap2 = CommonUtils.getStringFromMap(map, "tid", "");
        String stringFromMap3 = CommonUtils.getStringFromMap(map, "utdid", "");
        String b = TokenStorage.b(context);
        this.a.b((String) CONST.LOG_TAG, "preLoadCache(), appName = ".concat(String.valueOf(stringFromMap)));
        TokenStorage.a(context);
        TokenStorage.b(context, stringFromMap);
        this.a.b((String) CONST.LOG_TAG, (String) "preLoadCache(), TokenStorage缓存加载完毕");
        TokenStorage.h();
        LoggerUtil.a(context, stringFromMap2, stringFromMap3, b);
        if (CommonUtils.isAlipayWallet(context)) {
            AppLaunchTimeUtil.b(context);
        }
        TraceLogger traceLogger = this.a;
        StringBuilder sb = new StringBuilder("ApdidInitializeProcessor() cost ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        sb.append(" ms.");
        traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        return true;
    }
}
