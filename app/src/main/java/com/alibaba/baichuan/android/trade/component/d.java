package com.alibaba.baichuan.android.trade.component;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.a.b;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.baichuan.android.trade.b.a;
import com.alibaba.baichuan.android.trade.callback.AlibcFailureCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTaokeTraceCallback;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;

class d implements Runnable {
    final /* synthetic */ a a;
    final /* synthetic */ HashMap b;
    final /* synthetic */ AlibcTaokeTraceCallback c;
    final /* synthetic */ String d;
    final /* synthetic */ AlibcTaokeParams e;
    final /* synthetic */ String f;
    final /* synthetic */ AlibcFailureCallback g;
    final /* synthetic */ AlibcTaokeComponent h;

    d(AlibcTaokeComponent alibcTaokeComponent, a aVar, HashMap hashMap, AlibcTaokeTraceCallback alibcTaokeTraceCallback, String str, AlibcTaokeParams alibcTaokeParams, String str2, AlibcFailureCallback alibcFailureCallback) {
        this.h = alibcTaokeComponent;
        this.a = aVar;
        this.b = hashMap;
        this.c = alibcTaokeTraceCallback;
        this.d = str;
        this.e = alibcTaokeParams;
        this.f = str2;
        this.g = alibcFailureCallback;
    }

    public void run() {
        AlibcTaokeTraceCallback alibcTaokeTraceCallback;
        String str;
        this.a.d("sync");
        if (AlibcConfig.getInstance().getIsSyncForTaoke()) {
            AlibcLogger.i("taoke", "taoke同步打点");
            this.a.a((String) UserTrackerConstants.PM_TAOKE_TIME);
            NetworkResponse a2 = new b().a(new HashMap(this.b));
            str = b.a(a2);
            this.a.b(UserTrackerConstants.PM_TAOKE_TIME);
            if (!TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder("taoke同步打点成,sclickUrl：");
                sb.append(a2.data);
                AlibcLogger.i("taoke", sb.toString());
                this.h.a(UserTrackerConstants.U_TAOKE_TRACE_SYNC);
                alibcTaokeTraceCallback = this.c;
                alibcTaokeTraceCallback.genTaokeUrl(str);
            }
            if (a2 != null) {
                AlibcTaokeComponent alibcTaokeComponent = this.h;
                String str2 = a2.errorMsg;
                StringBuilder sb2 = new StringBuilder(UserTrackerConstants.ERRCODE_TAOKE_SYNC);
                sb2.append(a2.errorCode);
                alibcTaokeComponent.a((String) UserTrackerConstants.U_TAOKE_TRACE_SYNC, str2, sb2.toString());
            }
            AlibcLogger.e("taoke", "taoke同步打点失败");
        }
        this.h.taokeTrace(this.b, this.d, this.e, this.f, this.a, this.g);
        alibcTaokeTraceCallback = this.c;
        str = null;
        alibcTaokeTraceCallback.genTaokeUrl(str);
    }
}
