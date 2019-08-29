package com.alibaba.baichuan.android.trade.component;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.baichuan.android.trade.adapter.ut.UserTrackerCompoment;
import com.alibaba.baichuan.android.trade.b.a;
import com.alibaba.baichuan.android.trade.callback.AlibcFailureCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTaokeTraceCallback;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.utils.g;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;
import java.util.Map;

public class AlibcTaokeComponent {
    public static final AlibcTaokeComponent INSTANCE = new AlibcTaokeComponent();

    private AlibcTaokeComponent() {
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        UserTrackerCompoment.sendUseabilitySuccess(str);
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2, String str3) {
        UserTrackerCompoment.sendUseabilityFailureForOtherErrmsg(str, str2, str3);
    }

    private void a(HashMap hashMap, String str, AlibcTaokeParams alibcTaokeParams, String str2, a aVar, AlibcFailureCallback alibcFailureCallback) {
        StringBuilder sb = new StringBuilder("taoke异步打点开始，参数为：");
        sb.append(hashMap != null ? hashMap.toString() : null);
        sb.append("url=");
        sb.append(str);
        sb.append("rpcReferer=");
        sb.append(str2);
        AlibcLogger.d("taoke", sb.toString());
        g gVar = AlibcContext.executorService;
        e eVar = new e(this, hashMap, str, alibcTaokeParams, str2, alibcFailureCallback);
        gVar.a(eVar);
    }

    private void a(Map map, AlibcTaokeParams alibcTaokeParams, String str) {
        AlibcLogger.d("taoke", "添加淘客参数");
        if (map != null && str != null) {
            map.put("url", str);
            map.put("appKey", AlibcContext.getAppKey());
            map.put("utdid", AlibcContext.getUtdid());
            String str2 = null;
            if (!TextUtils.isEmpty(alibcTaokeParams.subPid)) {
                map.put("subPid", alibcTaokeParams.subPid);
            } else {
                map.put("subPid", null);
            }
            if (!TextUtils.isEmpty(alibcTaokeParams.unionId)) {
                map.put("unionId", alibcTaokeParams.unionId);
            } else {
                map.put("unionId", null);
            }
            map.put("pid", alibcTaokeParams.pid);
            StringBuilder sb = new StringBuilder("淘客参数:");
            if (map != null) {
                str2 = map.toString();
            }
            sb.append(str2);
            AlibcLogger.d("taoke", sb.toString());
        }
    }

    public void asyncTaokeTrace(HashMap hashMap, AlibcTaokeParams alibcTaokeParams, AlibcFailureCallback alibcFailureCallback) {
        if (hashMap != null && alibcTaokeParams != null) {
            String str = null;
            a((Map) hashMap, alibcTaokeParams, (String) null);
            StringBuilder sb = new StringBuilder("taoke异步打点开始，参数为：");
            if (hashMap != null) {
                str = hashMap.toString();
            }
            sb.append(str);
            AlibcLogger.d("taoke", sb.toString());
            AlibcContext.executorService.a(new c(this, hashMap, alibcTaokeParams, alibcFailureCallback));
        }
    }

    public void genUrlAndTaokeTrace(HashMap hashMap, String str, boolean z, AlibcTaokeParams alibcTaokeParams, String str2, a aVar, AlibcTaokeTraceCallback alibcTaokeTraceCallback, AlibcFailureCallback alibcFailureCallback) {
        HashMap hashMap2 = hashMap;
        AlibcTaokeParams alibcTaokeParams2 = alibcTaokeParams;
        AlibcTaokeTraceCallback alibcTaokeTraceCallback2 = alibcTaokeTraceCallback;
        if (!com.alibaba.baichuan.android.trade.utils.b.a.a(AlibcContext.context)) {
            AlibcLogger.e("taoke", "网络无连接，请连接网络后再试");
            alibcTaokeTraceCallback2.genTaokeUrl(null);
        } else if (hashMap2 == null || alibcTaokeParams2 == null) {
            AlibcLogger.e("taoke", "淘客参数不存在");
        } else {
            String str3 = str;
            a((Map) hashMap2, alibcTaokeParams2, str3);
            a aVar2 = aVar;
            aVar2.d("async");
            if (!z) {
                taokeTrace(hashMap2, str3, alibcTaokeParams2, str2, aVar2, alibcFailureCallback);
                alibcTaokeTraceCallback2.genTaokeUrl(null);
                return;
            }
            d dVar = new d(this, aVar2, hashMap2, alibcTaokeTraceCallback2, str3, alibcTaokeParams2, str2, alibcFailureCallback);
            AlibcContext.executorService.a(dVar);
        }
    }

    public NetworkResponse synchTaokeTrace(HashMap hashMap, String str, AlibcTaokeParams alibcTaokeParams, String str2) {
        if (alibcTaokeParams == null) {
            return null;
        }
        com.alibaba.baichuan.android.trade.a.a aVar = new com.alibaba.baichuan.android.trade.a.a();
        if (alibcTaokeParams.pid == null || !alibcTaokeParams.pid.startsWith(AlibcTaokeParams.PID_PREFIX)) {
            AlibcLogger.d("taoke", "淘客pid参数错误");
        }
        NetworkResponse a = aVar.a(new HashMap(hashMap));
        if (a != null && !a.isSuccess()) {
            String str3 = a.errorMsg;
            StringBuilder sb = new StringBuilder(UserTrackerConstants.ERRCODE_TAOKE_ASYNC);
            sb.append(a.errorCode);
            a((String) UserTrackerConstants.U_TAOKE_TRACE_ASYNC, str3, sb.toString());
        }
        if (AlibcContext.isDebuggable()) {
            StringBuilder sb2 = new StringBuilder("tktrace result json: ");
            sb2.append(a.data);
            AlibcLogger.d("taoke", sb2.toString());
        }
        return a;
    }

    public void taokeTrace(HashMap hashMap, String str, AlibcTaokeParams alibcTaokeParams, String str2, a aVar, AlibcFailureCallback alibcFailureCallback) {
        a(hashMap, str, alibcTaokeParams, str2, aVar, alibcFailureCallback);
    }
}
