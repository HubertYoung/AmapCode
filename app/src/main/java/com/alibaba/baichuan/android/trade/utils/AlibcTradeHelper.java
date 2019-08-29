package com.alibaba.baichuan.android.trade.utils;

import android.app.Activity;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.b.a;
import com.alibaba.baichuan.android.trade.callback.AlibcFailureCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.utils.cache.MemoryCacheUtils;
import com.alibaba.baichuan.android.trade.utils.code.Base64Utils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class AlibcTradeHelper {
    private static String a(Map map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        map.put("ttid", AlibcConfig.getInstance().getWebTTID());
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String str : map.keySet()) {
            String str2 = (String) map.get(str);
            if (i != 0) {
                sb.append("&");
            }
            try {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("=");
                sb2.append(URLEncoder.encode(new String(str2), "utf-8"));
                sb.append(sb2.toString());
                i++;
            } catch (Exception e) {
                StringBuilder sb3 = new StringBuilder("构建Ybhpss参数错误：");
                sb3.append(e.getMessage());
                AlibcLogger.e("AlibcTradeHelper", sb3.toString());
                return null;
            }
        }
        if (TextUtils.isEmpty(sb)) {
            return sb.toString();
        }
        String base64 = Base64Utils.getBase64(new String(sb));
        MemoryCacheUtils.setGroupProperity(AlibcConstants.TRADE_GROUP, "ybhpss", base64);
        return base64;
    }

    public static boolean checkParams(AlibcBasePage alibcBasePage, Activity activity, a aVar, AlibcTradeCallback alibcTradeCallback) {
        Object[] objArr;
        if (alibcTradeCallback == null) {
            objArr = new Object[]{"tradeProcessCallback"};
        } else if (activity == null) {
            objArr = new Object[]{"Activity"};
        } else if (alibcBasePage != null && alibcBasePage.checkParams()) {
            return true;
        } else {
            objArr = new Object[]{"AlibcPage"};
        }
        d.a((AlibcFailureCallback) alibcTradeCallback, com.alibaba.baichuan.android.trade.utils.a.a.a(14, objArr));
        return false;
    }

    public static Map createUrlParams(Map map) {
        HashMap hashMap = new HashMap();
        hashMap.put("ttid", AlibcConfig.getInstance().getWebTTID());
        StringBuilder sb = new StringBuilder("1-");
        sb.append(AlibcContext.getAppKey());
        hashMap.put(AlibcConstants.UMP_CHANNEL, sb.toString());
        StringBuilder sb2 = new StringBuilder("1-");
        sb2.append(AlibcContext.getAppKey());
        hashMap.put(AlibcConstants.U_CHANNEL, sb2.toString());
        if (map == null || map.size() == 0) {
            return hashMap;
        }
        if (map.get(AlibcConstants.ISV_CODE) == null && AlibcConfig.getInstance().getIsvCode() != null) {
            hashMap.put(AlibcConstants.ISV_CODE, AlibcConfig.getInstance().getIsvCode());
        }
        HashMap hashMap2 = new HashMap();
        for (String str : map.keySet()) {
            String str2 = (String) map.get(str);
            if (str2 != null && !TextUtils.isEmpty(str2)) {
                if (AlibcContext.firstLevelKeys.contains(str)) {
                    hashMap.put(str, str2);
                } else {
                    hashMap2.put(str, str2);
                }
            }
        }
        hashMap2.put("ttid", AlibcConfig.getInstance().getWebTTID());
        String a = a(hashMap2);
        if (a != null && !TextUtils.isEmpty(a)) {
            hashMap.put("ybhpss", a);
        }
        return hashMap;
    }
}
