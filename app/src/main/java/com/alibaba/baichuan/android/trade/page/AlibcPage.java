package com.alibaba.baichuan.android.trade.page;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.URLUtil;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.b.a;
import com.alibaba.baichuan.android.trade.callback.AlibcTaokeTraceCallback;
import com.alibaba.baichuan.android.trade.component.AlibcTaokeComponent;
import com.alibaba.baichuan.android.trade.component.b;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.ApplinkOpenType;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlibcPage extends AlibcBasePage {
    public Map additionalHttpHeaders;

    public AlibcPage(String str) {
        this.a = str;
    }

    private boolean a() {
        if (this.a != null) {
            for (String matches : AlibcContext.detailPatterns) {
                if (this.a.trim().matches(matches)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkParams() {
        return (getClass().getSuperclass() != null && getClass().getSuperclass().getName().equals(AlibcPage.class.getName())) || this.a != null;
    }

    public String genOpenUrl() {
        if (URLUtil.isNetworkUrl(this.a)) {
            return this.a.trim();
        }
        return null;
    }

    public Map getAdditionalHttpHeaders() {
        return this.additionalHttpHeaders;
    }

    public String getApi() {
        return UserTrackerConstants.E_SHOWPAGE;
    }

    public boolean needTaoke(AlibcTaokeParams alibcTaokeParams) {
        return a() && alibcTaokeParams != null;
    }

    public void taokeTraceAndGenUrl(AlibcTaokeParams alibcTaokeParams, a aVar, AlibcTaokeTraceCallback alibcTaokeTraceCallback) {
        if (!TextUtils.isEmpty(this.a)) {
            char c = 65535;
            if (!TextUtils.isEmpty(this.a)) {
                for (String matches : AlibcContext.detailPatterns) {
                    if (this.a.matches(matches)) {
                        c = 1;
                    }
                }
                if (1 == c) {
                    Matcher matcher = Pattern.compile("(\\?|&)id=([^&?]*)").matcher(this.a);
                    String str = null;
                    if (matcher.find()) {
                        String group = matcher.group();
                        str = group.substring(group.indexOf(61) + 1);
                    }
                    HashMap hashMap = new HashMap();
                    hashMap.put("itemId", str);
                    if (aVar.e != null) {
                        AlibcTaokeComponent.INSTANCE.genUrlAndTaokeTrace(hashMap, genOpenUrl(), true, alibcTaokeParams, getApi(), aVar, alibcTaokeTraceCallback, aVar.e);
                    }
                }
            }
        }
    }

    public boolean tryNativeJump(AlibcTaokeParams alibcTaokeParams, AlibcShowParams alibcShowParams, Map map, Activity activity) {
        String str;
        if (a()) {
            Matcher matcher = Pattern.compile("(\\?|&)id=([^&?]*)").matcher(this.a);
            String str2 = null;
            if (matcher.find()) {
                String group = matcher.group();
                str = group.substring(group.indexOf(61) + 1);
            } else {
                str = null;
            }
            if (alibcTaokeParams != null) {
                str2 = alibcTaokeParams.pid;
            }
            String str3 = str2;
            String backUrl = (alibcShowParams == null || TextUtils.isEmpty(alibcShowParams.getBackUrl())) ? "alisdk://" : alibcShowParams.getBackUrl();
            String str4 = "";
            if (alibcShowParams != null) {
                str4 = alibcShowParams.getClientType();
            }
            map.put("appType", str4);
            return b.a(activity, ApplinkOpenType.SHOWITEM, null, str, AlibcConfig.getInstance().getIsvCode(), str3, backUrl, map);
        }
        String str5 = "";
        if (alibcShowParams != null) {
            str5 = alibcShowParams.getClientType();
        }
        map.put("appType", str5);
        return super.tryNativeJump(alibcTaokeParams, alibcShowParams, map, activity);
    }
}
