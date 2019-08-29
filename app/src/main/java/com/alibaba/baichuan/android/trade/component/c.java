package com.alibaba.baichuan.android.trade.component;

import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.baichuan.android.trade.callback.AlibcFailureCallback;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;

class c implements Runnable {
    final /* synthetic */ HashMap a;
    final /* synthetic */ AlibcTaokeParams b;
    final /* synthetic */ AlibcFailureCallback c;
    final /* synthetic */ AlibcTaokeComponent d;

    c(AlibcTaokeComponent alibcTaokeComponent, HashMap hashMap, AlibcTaokeParams alibcTaokeParams, AlibcFailureCallback alibcFailureCallback) {
        this.d = alibcTaokeComponent;
        this.a = hashMap;
        this.b = alibcTaokeParams;
        this.c = alibcFailureCallback;
    }

    public void run() {
        String str;
        String str2 = null;
        NetworkResponse synchTaokeTrace = this.d.synchTaokeTrace(this.a, null, this.b, null);
        if (synchTaokeTrace == null || !synchTaokeTrace.isSuccess) {
            if (synchTaokeTrace == null) {
                str = "null taokeTrace response";
            } else {
                StringBuilder sb = new StringBuilder("code: ");
                sb.append(synchTaokeTrace.errorCode);
                sb.append(" msg: ");
                sb.append(synchTaokeTrace.errorMsg);
                str = sb.toString();
            }
            if (str != null) {
                str2 = str;
            }
            AlibcLogger.e("taoke", str2);
            if (this.c != null) {
                this.c.onFailure(0, "淘客打点失败，错误信息:".concat(String.valueOf(str)));
                AlibcLogger.d("taoke", "taoke异步打点失败");
            }
            return;
        }
        this.d.a(UserTrackerConstants.U_TAOKE_TRACE_ASYNC);
        AlibcLogger.d("taoke", "taoke异步打点成功");
    }
}
