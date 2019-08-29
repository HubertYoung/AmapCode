package defpackage;

import android.content.Context;
import com.autonavi.bundle.account.api.IAccountService;

/* renamed from: det reason: default package */
/* compiled from: AosSnsBatchErrorReportRequestor */
public final class det {
    private Context a;

    public det(Context context) {
        this.a = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x005e A[Catch:{ Exception -> 0x0068 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00a7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.autonavi.minimap.feedback.param.ReportBatchRequest a(java.lang.String r8, java.lang.String r9, com.autonavi.minimap.feedback.param.ReportBatchRequest r10) {
        /*
            r7 = this;
            r10.r = r8
            r8 = 0
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0023 }
            r1 = 5
            com.autonavi.common.model.GeoPoint r0 = r0.getLatestPosition(r1)     // Catch:{ Exception -> 0x0023 }
            if (r0 == 0) goto L_0x0027
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0023 }
            com.autonavi.common.model.GeoPoint r0 = r0.getLatestPosition()     // Catch:{ Exception -> 0x0023 }
            int r0 = r0.x     // Catch:{ Exception -> 0x0023 }
            com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0023 }
            com.autonavi.common.model.GeoPoint r1 = r1.getLatestPosition()     // Catch:{ Exception -> 0x0023 }
            int r1 = r1.y     // Catch:{ Exception -> 0x0023 }
            goto L_0x0029
        L_0x0023:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0027:
            r0 = 0
            r1 = 0
        L_0x0029:
            r2 = 0
            if (r0 == 0) goto L_0x006f
            if (r1 == 0) goto L_0x006f
            long r3 = (long) r0
            long r5 = (long) r1
            com.autonavi.minimap.map.DPoint r3 = defpackage.cfg.a(r3, r5)     // Catch:{ Exception -> 0x006b }
            li r4 = defpackage.li.a()     // Catch:{ Exception -> 0x006b }
            lj r0 = r4.b(r0, r1)     // Catch:{ Exception -> 0x006b }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0068 }
            r1.<init>()     // Catch:{ Exception -> 0x0068 }
            double r4 = r3.x     // Catch:{ Exception -> 0x0068 }
            r1.append(r4)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0068 }
            r10.s = r1     // Catch:{ Exception -> 0x0068 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0068 }
            r1.<init>()     // Catch:{ Exception -> 0x0068 }
            double r2 = r3.y     // Catch:{ Exception -> 0x0068 }
            r1.append(r2)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0068 }
            r10.t = r1     // Catch:{ Exception -> 0x0068 }
            if (r0 == 0) goto L_0x0066
            int r1 = r0.j     // Catch:{ Exception -> 0x0068 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x0068 }
            r10.u = r1     // Catch:{ Exception -> 0x0068 }
        L_0x0066:
            r2 = r0
            goto L_0x006f
        L_0x0068:
            r1 = move-exception
            r2 = r0
            goto L_0x006c
        L_0x006b:
            r1 = move-exception
        L_0x006c:
            r1.printStackTrace()
        L_0x006f:
            java.lang.String r0 = a()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x007f
            java.lang.String r0 = a()
            r10.v = r0
        L_0x007f:
            java.lang.Class<com.autonavi.minimap.offline.map.inter.IOfflineManager> r0 = com.autonavi.minimap.offline.map.inter.IOfflineManager.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com.autonavi.minimap.offline.map.inter.IOfflineManager r0 = (com.autonavi.minimap.offline.map.inter.IOfflineManager) r0
            if (r0 == 0) goto L_0x00dd
            java.lang.String r1 = "V4|V4|V4"
            r10.w = r1
            if (r2 == 0) goto L_0x00a4
            int r1 = r2.j
            java.lang.String r1 = java.lang.String.valueOf(r1)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x00a4
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x00a0 }
            goto L_0x00a5
        L_0x00a0:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00a4:
            r1 = 0
        L_0x00a5:
            if (r1 <= 0) goto L_0x00dd
            int[] r0 = r0.getCityDownloadedVerByAdcode(r1)
            if (r0 == 0) goto L_0x00dd
            int r1 = r0.length
            r2 = 1
            if (r1 <= r2) goto L_0x00dd
            r1 = r0[r8]
            if (r1 > 0) goto L_0x00b9
            r1 = r0[r2]
            if (r1 <= 0) goto L_0x00dd
        L_0x00b9:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            r3 = r0[r8]
            r1.append(r3)
            java.lang.String r3 = "|"
            r1.append(r3)
            r2 = r0[r2]
            r1.append(r2)
            java.lang.String r2 = "|"
            r1.append(r2)
            r8 = r0[r8]
            r1.append(r8)
            java.lang.String r8 = r1.toString()
            r10.x = r8
        L_0x00dd:
            r10.h = r9
            android.content.Context r8 = r7.a
            java.lang.String r8 = defpackage.aaw.a(r8)
            r10.y = r8
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.det.a(java.lang.String, java.lang.String, com.autonavi.minimap.feedback.param.ReportBatchRequest):com.autonavi.minimap.feedback.param.ReportBatchRequest");
    }

    private static String a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }
}
