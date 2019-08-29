package com.alibaba.baichuan.android.trade.page;

import android.app.Activity;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink;
import com.alibaba.baichuan.android.trade.b.a;
import com.alibaba.baichuan.android.trade.c.a.a.e;
import com.alibaba.baichuan.android.trade.callback.AlibcTaokeTraceCallback;
import com.alibaba.baichuan.android.trade.component.b;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.ApplinkOpenType;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;
import java.util.Map;

public class AlibcBasePage {
    public static final int NOT_REQUIRE = 0;
    public static final int REQUIRE_H5 = 1;
    public static final int REQUIRE_NATIVE = 2;
    private static final String b = "AlibcBasePage";
    protected String a;

    public boolean checkParams() {
        return true;
    }

    public String genOpenUrl() {
        if (!TextUtils.isEmpty(this.a)) {
            return this.a;
        }
        return null;
    }

    public String getAddParamsUrl(String str, Map map, a aVar) {
        String str2 = b;
        StringBuilder sb = new StringBuilder("首次进入加参，参数： urlParams = ");
        sb.append(map);
        sb.append("  url=");
        sb.append(str);
        AlibcLogger.d(str2, sb.toString());
        if (map == null) {
            return genOpenUrl();
        }
        aVar.a((String) UserTrackerConstants.PM_URL_HANDLE_TIME);
        com.alibaba.baichuan.android.trade.c.a.a.c.a aVar2 = new com.alibaba.baichuan.android.trade.c.a.a.c.a();
        aVar2.e = 1;
        if (str == null) {
            str = genOpenUrl();
        }
        aVar2.d = str;
        aVar2.i = new HashMap();
        aVar2.i.put("ui_contextParams", map);
        String a2 = e.a().a(aVar2);
        aVar.b(UserTrackerConstants.PM_URL_HANDLE_TIME);
        AlibcLogger.d(b, "首次加参后结果为 url=".concat(String.valueOf(a2)));
        if (a2 == null) {
            a2 = genOpenUrl();
        }
        return a2;
    }

    public String getAddParamsUrl(Map map, a aVar) {
        return getAddParamsUrl(null, map, aVar);
    }

    public Map getAdditionalHttpHeaders() {
        return null;
    }

    public String getApi() {
        return UserTrackerConstants.E_SHOW;
    }

    public String getPerformancePageType() {
        return "url";
    }

    public String getUsabilityPageType() {
        return UserTrackerConstants.U_OTHER_PAGE;
    }

    public boolean isBackWhenLoginFail() {
        return false;
    }

    public boolean isNativeOpen(AlibcShowParams alibcShowParams) {
        if (!AlibcApplink.isApplinkSupported(alibcShowParams.getClientType())) {
            return false;
        }
        if (requireOpenType() != 0) {
            return 2 == requireOpenType();
        }
        int double11OpenType = AlibcConfig.getInstance().getDouble11OpenType();
        if (double11OpenType == 1) {
            return true;
        }
        if (double11OpenType == 2) {
            return false;
        }
        switch (alibcShowParams.getOpenType()) {
            case H5:
                return false;
            case Native:
                return true;
            default:
                String genOpenUrl = genOpenUrl();
                if (genOpenUrl != null) {
                    for (String matches : AlibcContext.nativeOpenPatterns) {
                        if (genOpenUrl.matches(matches) && !AlibcConfig.getInstance().isForceH5()) {
                            return true;
                        }
                    }
                }
                return false;
        }
    }

    public boolean needTaoke(AlibcTaokeParams alibcTaokeParams) {
        return alibcTaokeParams != null;
    }

    public int requireOpenType() {
        return 0;
    }

    public void taokeTraceAndGenUrl(AlibcTaokeParams alibcTaokeParams, a aVar, AlibcTaokeTraceCallback alibcTaokeTraceCallback) {
    }

    public boolean tryNativeJump(AlibcTaokeParams alibcTaokeParams, AlibcShowParams alibcShowParams, Map map, Activity activity) {
        String str = "alisdk://";
        String str2 = "";
        String str3 = alibcTaokeParams != null ? alibcTaokeParams.pid : null;
        if (alibcShowParams != null) {
            str2 = alibcShowParams.getClientType();
        }
        if (alibcShowParams != null && !TextUtils.isEmpty(alibcShowParams.getBackUrl())) {
            str = alibcShowParams.getBackUrl();
        }
        map.put("appType", str2);
        map.put("url", genOpenUrl());
        return b.a(activity, ApplinkOpenType.SHOWURL, genOpenUrl(), null, AlibcConfig.getInstance().getIsvCode(), str3, str, map);
    }
}
