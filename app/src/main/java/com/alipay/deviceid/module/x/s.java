package com.alipay.deviceid.module.x;

import com.alipay.deviceid.module.rpc.report.open.model.ReportRequest;
import com.alipay.deviceid.module.rpc.report.open.model.ReportResult;

final class s implements Runnable {
    final /* synthetic */ ReportRequest a;
    final /* synthetic */ r b;

    s(r rVar, ReportRequest reportRequest) {
        this.b = rVar;
        this.a = reportRequest;
    }

    public final void run() {
        try {
            r.e = this.b.d.reportData(this.a);
        } catch (Throwable th) {
            r.e = new ReportResult();
            r.e.success = false;
            ReportResult a2 = r.e;
            StringBuilder sb = new StringBuilder("static data rpc upload error, ");
            sb.append(e.a(th));
            a2.resultCode = sb.toString();
            aa.a((String) "Rpc failed.");
            aa.a(e.a(th));
        }
    }
}
