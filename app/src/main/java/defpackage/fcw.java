package defpackage;

import com.taobao.tlog.adapter.AdapterForTLog;
import mtopsdk.common.log.LogAdapter;

/* renamed from: fcw reason: default package */
/* compiled from: TLogAdapterImpl */
public final class fcw implements LogAdapter {
    public final String a() {
        return AdapterForTLog.getLogLevel();
    }

    public final void a(String str, String str2) {
        AdapterForTLog.traceLog(str, str2);
    }

    public final void a(int i, String str, String str2, Throwable th) {
        if (i == 4) {
            AdapterForTLog.logi(str, str2);
        } else if (i != 8) {
            if (i != 16) {
                switch (i) {
                    case 1:
                        AdapterForTLog.logv(str, str2);
                        return;
                    case 2:
                        AdapterForTLog.logd(str, str2);
                        return;
                }
            } else {
                AdapterForTLog.loge(str, str2, th);
            }
        } else {
            AdapterForTLog.logw(str, str2, th);
        }
    }
}
