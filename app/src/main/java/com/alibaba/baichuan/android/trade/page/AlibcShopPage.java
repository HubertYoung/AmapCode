package com.alibaba.baichuan.android.trade.page;

import android.app.Activity;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.b.a;
import com.alibaba.baichuan.android.trade.callback.AlibcTaokeTraceCallback;
import com.alibaba.baichuan.android.trade.component.AlibcTaokeComponent;
import com.alibaba.baichuan.android.trade.component.b;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.ApplinkOpenType;
import java.util.HashMap;
import java.util.Map;

public class AlibcShopPage extends AlibcBasePage {
    private String b;
    private String c;

    public AlibcShopPage(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.b = str;
        }
    }

    public boolean checkParams() {
        return this.b != null && !TextUtils.isEmpty(this.b);
    }

    public String genOpenUrl() {
        if (this.c != null && !TextUtils.isEmpty(this.c)) {
            return this.c;
        }
        String str = AlibcContext.TB_SHOP_URL;
        int indexOf = str.indexOf(63);
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("?shop_id=%s");
        this.c = String.format(sb.toString(), new Object[]{this.b});
        return this.c;
    }

    public String getApi() {
        return UserTrackerConstants.E_SHOWSHOP;
    }

    public String getPerformancePageType() {
        return AlibcConstants.SHOP;
    }

    public String getUsabilityPageType() {
        return UserTrackerConstants.U_SHOP_PAGE;
    }

    public boolean needTaoke(AlibcTaokeParams alibcTaokeParams) {
        return false;
    }

    public void taokeTraceAndGenUrl(AlibcTaokeParams alibcTaokeParams, a aVar, AlibcTaokeTraceCallback alibcTaokeTraceCallback) {
        if (this.b != null && !TextUtils.isEmpty(this.b)) {
            HashMap hashMap = new HashMap();
            hashMap.put("shopId", this.b);
            if (aVar.e != null) {
                AlibcTaokeComponent.INSTANCE.genUrlAndTaokeTrace(hashMap, genOpenUrl(), true, alibcTaokeParams, getApi(), aVar, alibcTaokeTraceCallback, aVar.e);
            }
        }
    }

    public boolean tryNativeJump(AlibcTaokeParams alibcTaokeParams, AlibcShowParams alibcShowParams, Map map, Activity activity) {
        String str = alibcTaokeParams != null ? alibcTaokeParams.pid : null;
        String backUrl = (alibcShowParams == null || TextUtils.isEmpty(alibcShowParams.getBackUrl())) ? "alisdk://" : alibcShowParams.getBackUrl();
        String str2 = "";
        if (alibcShowParams != null) {
            str2 = alibcShowParams.getClientType();
        }
        map.put("appType", str2);
        return b.a(activity, ApplinkOpenType.SHOWSHOP, null, this.b, AlibcConfig.getInstance().getIsvCode(), str, backUrl, map);
    }
}
