package defpackage;

import android.text.TextUtils;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.util.List;

/* renamed from: tc reason: default package */
/* compiled from: AosCarRouteTmcResponsor */
public final class tc extends tb {
    private List<ICarRouteResult> d;

    public tc(List<ICarRouteResult> list) {
        this.d = list;
        if (list.size() > 0) {
            this.b = list.get(0).getCarPlate();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0134, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void parser(byte[] r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            if (r10 != 0) goto L_0x0016
            r0 = 13
            r9.errorCode = r0     // Catch:{ Exception -> 0x0013 }
            int r0 = r9.errorCode     // Catch:{ Exception -> 0x0013 }
            java.lang.String r0 = a(r0)     // Catch:{ Exception -> 0x0013 }
            r9.errorMessage = r0     // Catch:{ Exception -> 0x0013 }
            goto L_0x0016
        L_0x0010:
            r10 = move-exception
            goto L_0x013d
        L_0x0013:
            r10 = move-exception
            goto L_0x0135
        L_0x0016:
            java.util.List<com.amap.bundle.drivecommon.model.ICarRouteResult> r0 = r9.d     // Catch:{ Exception -> 0x0013 }
            if (r0 == 0) goto L_0x0133
            if (r10 == 0) goto L_0x0123
            int r0 = r10.length     // Catch:{ Exception -> 0x0013 }
            r1 = 4
            if (r0 < r1) goto L_0x0123
            r0 = 0
            byte r2 = r10[r0]     // Catch:{ Exception -> 0x0013 }
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << 24
            r3 = 1
            byte r4 = r10[r3]     // Catch:{ Exception -> 0x0013 }
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << 16
            int r2 = r2 + r4
            r4 = 2
            byte r5 = r10[r4]     // Catch:{ Exception -> 0x0013 }
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r5 = r5 << 8
            int r2 = r2 + r5
            r5 = 3
            byte r6 = r10[r5]     // Catch:{ Exception -> 0x0013 }
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r2 = r2 + r6
            int r6 = r10.length     // Catch:{ Exception -> 0x0013 }
            int r6 = r6 - r1
            if (r2 <= r6) goto L_0x008a
            boolean r1 = defpackage.bno.a     // Catch:{ Exception -> 0x0013 }
            if (r1 == 0) goto L_0x0078
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0013 }
            r1.<init>()     // Catch:{ Exception -> 0x0013 }
            byte r0 = r10[r0]     // Catch:{ Exception -> 0x0013 }
            r1.append(r0)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r0 = ","
            r1.append(r0)     // Catch:{ Exception -> 0x0013 }
            byte r0 = r10[r3]     // Catch:{ Exception -> 0x0013 }
            r1.append(r0)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r0 = ","
            r1.append(r0)     // Catch:{ Exception -> 0x0013 }
            byte r0 = r10[r4]     // Catch:{ Exception -> 0x0013 }
            r1.append(r0)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r0 = ","
            r1.append(r0)     // Catch:{ Exception -> 0x0013 }
            byte r10 = r10[r5]     // Catch:{ Exception -> 0x0013 }
            r1.append(r10)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r10 = "AosCarRouteTmcResponsor"
            java.lang.String r0 = "AosCarRouteTmcResponsor#parser"
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0013 }
            com.amap.bundle.logs.AMapLog.debug(r10, r0, r1)     // Catch:{ Exception -> 0x0013 }
        L_0x0078:
            android.app.Application r10 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0013 }
            android.content.res.Resources r10 = r10.getResources()     // Catch:{ Exception -> 0x0013 }
            int r0 = com.autonavi.minimap.R.string.autonavi_route_response_retry_failed     // Catch:{ Exception -> 0x0013 }
            java.lang.String r10 = r10.getString(r0)     // Catch:{ Exception -> 0x0013 }
            r9.errorMessage = r10     // Catch:{ Exception -> 0x0013 }
            monitor-exit(r9)
            return
        L_0x008a:
            byte[] r3 = new byte[r2]     // Catch:{ Exception -> 0x0013 }
            java.lang.System.arraycopy(r10, r1, r3, r0, r2)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x0013 }
            java.lang.String r5 = "UTF-8"
            r4.<init>(r3, r5)     // Catch:{ Exception -> 0x0013 }
            int r2 = r2 + r1
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0013 }
            r1.<init>(r4)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r3 = "bucket_desc"
            org.json.JSONArray r1 = r1.optJSONArray(r3)     // Catch:{ Exception -> 0x0013 }
            r3 = r2
            r2 = 0
        L_0x00a4:
            int r4 = r1.length()     // Catch:{ Exception -> 0x0013 }
            if (r2 >= r4) goto L_0x0119
            org.json.JSONObject r4 = r1.optJSONObject(r2)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r5 = "route_key"
            java.lang.String r5 = r4.optString(r5)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r6 = "length"
            int r6 = r4.optInt(r6)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r7 = "index"
            r4.optInt(r7)     // Catch:{ Exception -> 0x0013 }
            java.lang.String r4 = "nav_home"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0013 }
            if (r4 == 0) goto L_0x00eb
            java.util.List<com.amap.bundle.drivecommon.model.ICarRouteResult> r4 = r9.d     // Catch:{ Exception -> 0x0013 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ Exception -> 0x0013 }
        L_0x00cd:
            boolean r5 = r4.hasNext()     // Catch:{ Exception -> 0x0013 }
            if (r5 == 0) goto L_0x0116
            java.lang.Object r5 = r4.next()     // Catch:{ Exception -> 0x0013 }
            com.amap.bundle.drivecommon.model.ICarRouteResult r5 = (com.amap.bundle.drivecommon.model.ICarRouteResult) r5     // Catch:{ Exception -> 0x0013 }
            com.autonavi.minimap.drive.route.CalcRouteScene r7 = r5.getCalcRouteScene()     // Catch:{ Exception -> 0x0013 }
            com.autonavi.minimap.drive.route.CalcRouteScene r8 = com.autonavi.minimap.drive.route.CalcRouteScene.SCENE_HOME_TMC     // Catch:{ Exception -> 0x0013 }
            if (r8 != r7) goto L_0x00cd
            byte[] r4 = new byte[r6]     // Catch:{ Exception -> 0x0013 }
            java.lang.System.arraycopy(r10, r3, r4, r0, r6)     // Catch:{ Exception -> 0x0013 }
            r9.a(r5, r4)     // Catch:{ Exception -> 0x0013 }
            int r3 = r3 + r6
            goto L_0x0116
        L_0x00eb:
            java.lang.String r4 = "nav_company"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0013 }
            if (r4 == 0) goto L_0x0116
            java.util.List<com.amap.bundle.drivecommon.model.ICarRouteResult> r4 = r9.d     // Catch:{ Exception -> 0x0013 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ Exception -> 0x0013 }
        L_0x00f9:
            boolean r5 = r4.hasNext()     // Catch:{ Exception -> 0x0013 }
            if (r5 == 0) goto L_0x0116
            java.lang.Object r5 = r4.next()     // Catch:{ Exception -> 0x0013 }
            com.amap.bundle.drivecommon.model.ICarRouteResult r5 = (com.amap.bundle.drivecommon.model.ICarRouteResult) r5     // Catch:{ Exception -> 0x0013 }
            com.autonavi.minimap.drive.route.CalcRouteScene r7 = r5.getCalcRouteScene()     // Catch:{ Exception -> 0x0013 }
            com.autonavi.minimap.drive.route.CalcRouteScene r8 = com.autonavi.minimap.drive.route.CalcRouteScene.SCENE_COMPANY_TMC     // Catch:{ Exception -> 0x0013 }
            if (r8 != r7) goto L_0x00f9
            byte[] r4 = new byte[r6]     // Catch:{ Exception -> 0x0013 }
            java.lang.System.arraycopy(r10, r3, r4, r0, r6)     // Catch:{ Exception -> 0x0013 }
            r9.a(r5, r4)     // Catch:{ Exception -> 0x0013 }
            int r3 = r3 + r6
        L_0x0116:
            int r2 = r2 + 1
            goto L_0x00a4
        L_0x0119:
            int r10 = r9.errorCode     // Catch:{ Exception -> 0x0013 }
            java.lang.String r10 = a(r10)     // Catch:{ Exception -> 0x0013 }
            r9.errorMessage = r10     // Catch:{ Exception -> 0x0013 }
            monitor-exit(r9)
            return
        L_0x0123:
            android.app.Application r10 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0013 }
            android.content.res.Resources r10 = r10.getResources()     // Catch:{ Exception -> 0x0013 }
            int r0 = com.autonavi.minimap.R.string.autonavi_route_response_retry_failed     // Catch:{ Exception -> 0x0013 }
            java.lang.String r10 = r10.getString(r0)     // Catch:{ Exception -> 0x0013 }
            r9.errorMessage = r10     // Catch:{ Exception -> 0x0013 }
        L_0x0133:
            monitor-exit(r9)
            return
        L_0x0135:
            r0 = 0
            r9.d = r0     // Catch:{ all -> 0x0010 }
            defpackage.kf.a(r10)     // Catch:{ all -> 0x0010 }
            monitor-exit(r9)
            return
        L_0x013d:
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.tc.parser(byte[]):void");
    }

    private void a(ICarRouteResult iCarRouteResult, byte[] bArr) {
        if (bArr.length < 8) {
            this.errorMessage = AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_response_retry_failed);
        } else if ((bArr[0] & 255) + ((bArr[1] & 255) << 8) == 200) {
            byte[] bArr2 = new byte[(bArr.length - 10)];
            System.arraycopy(bArr, 10, bArr2, 0, bArr2.length);
            byte[] bArr3 = new byte[8];
            System.arraycopy(bArr, 2, bArr3, 0, 8);
            this.errorCode = iCarRouteResult.parseData(bArr2, 0, (int) ahg.b(bArr3));
            this.errorMessage = a(this.errorCode);
        } else {
            this.errorMessage = AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_response_retry_failed);
        }
    }

    public final synchronized List<ICarRouteResult> c() {
        try {
        }
        return this.d;
    }

    public final String getErrorDesc(int i) {
        return this.errorMessage;
    }

    public final synchronized boolean a() {
        try {
            if (this.d != null) {
                for (ICarRouteResult next : this.d) {
                    if (next != null && next.hasData()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public final String b() {
        if (TextUtils.isEmpty(this.errorMessage)) {
            return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_response_retry_failed);
        }
        return this.errorMessage;
    }
}
