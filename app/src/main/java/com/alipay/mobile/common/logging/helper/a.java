package com.alipay.mobile.common.logging.helper;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportName;
import java.util.HashMap;

/* compiled from: BugReportAnalyzer */
final class a implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ boolean b;
    final /* synthetic */ BugReportAnalyzer c;

    a(BugReportAnalyzer this$0, int i, boolean z) {
        this.c = this$0;
        this.a = i;
        this.b = z;
    }

    public final void run() {
        try {
            String logcat = BugReportAnalyzer.b(this.a);
            HashMap params = new HashMap();
            params.put("logcat", logcat);
            LoggerFactory.getMonitorLogger().mtBizReport(MTBizReportName.MTBIZ_FRAME, "FRAME_LOGCAT_REPORT", "0", params);
            LoggerFactory.getLogContext().flush(null, this.b);
            LoggerFactory.getLogContext().uploadAfterSync(null);
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "BugReportAnalyzer", tr);
        }
    }
}
