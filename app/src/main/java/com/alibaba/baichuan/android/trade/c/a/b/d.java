package com.alibaba.baichuan.android.trade.c.a.b;

import android.app.Activity;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.adapter.ut.AlibcUserTracker;
import com.alibaba.baichuan.android.trade.c.a.a.b.b;
import com.alibaba.baichuan.android.trade.c.a.a.b.c;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.cache.MemoryCacheUtils;
import java.util.HashMap;
import java.util.Map;

public class d implements b {
    /* access modifiers changed from: private */
    public void a() {
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(AlibcContext.getAppKey())) {
            hashMap.put("appkey", AlibcContext.getAppKey());
            hashMap.put("userId", AlibcLogin.INSTANCE.getSession().openId);
            hashMap.put("utdid", AlibcContext.getUtdid());
            hashMap.put("ttid", AlibcConfig.getInstance().getWebTTID());
            hashMap.put("ybhpss", (String) MemoryCacheUtils.getGroupProperity(AlibcConstants.TRADE_GROUP, "ybhpss"));
        }
        AlibcUserTracker.getInstance().sendCustomHit(UserTrackerConstants.E_SHOWLOGIN, (String) "", (Map) hashMap);
    }

    public boolean a(c cVar) {
        if (AlibcConfig.getInstance().getLoginDegarade(false)) {
            return false;
        }
        AlibcLogin.INSTANCE.showLogin((Activity) cVar.d.getContext(), new e(this, cVar));
        return true;
    }
}
