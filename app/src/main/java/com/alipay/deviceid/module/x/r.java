package com.alipay.deviceid.module.x;

import android.content.Context;
import com.alipay.deviceid.module.rpc.deviceFp.BugTrackMessageService;
import com.alipay.deviceid.module.rpc.mrpc.core.aa;
import com.alipay.deviceid.module.rpc.mrpc.core.h;
import com.alipay.deviceid.module.rpc.mrpc.core.w;
import com.alipay.deviceid.module.rpc.report.open.OpenReportService;
import com.alipay.deviceid.module.rpc.report.open.model.ReportRequest;
import com.alipay.deviceid.module.rpc.report.open.model.ReportResult;
import org.json.JSONObject;

public final class r {
    private static r a;
    /* access modifiers changed from: private */
    public static ReportResult e;
    private w b = null;
    private BugTrackMessageService c = null;
    /* access modifiers changed from: private */
    public OpenReportService d = null;

    private r(Context context, String str) {
        aa aaVar = new aa();
        aaVar.a(str);
        this.b = new h(context);
        this.c = (BugTrackMessageService) this.b.a(BugTrackMessageService.class, aaVar);
        this.d = (OpenReportService) this.b.a(OpenReportService.class, aaVar);
    }

    public static r a(Context context, String str) {
        if (context == null || e.a(str)) {
            return null;
        }
        if (a == null) {
            a = new r(context, str);
        }
        return a;
    }

    public final p a(Context context, q qVar) {
        ReportRequest a2 = o.a(context, qVar);
        if (this.d == null) {
            return null;
        }
        e = null;
        new Thread(new s(this, a2)).start();
        int i = 300000;
        while (e == null && i >= 0) {
            Thread.sleep(50);
            i -= 50;
        }
        return o.a(e);
    }

    public final boolean a(String str) {
        String str2;
        boolean z = false;
        if (e.a(str)) {
            return false;
        }
        if (this.c != null) {
            try {
                str2 = this.c.logCollect(e.e(str));
            } catch (Exception unused) {
                str2 = null;
            }
            if (!e.a(str2)) {
                z = ((Boolean) new JSONObject(str2).get("success")).booleanValue();
            }
        }
        return z;
    }
}
