package defpackage;

import android.text.TextUtils;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: tb reason: default package */
/* compiled from: AosCarRouteResponsor */
public class tb extends AbstractAOSParser {
    public ICarRouteResult a;
    public String b = "";
    public int c;

    public tb(ICarRouteResult iCarRouteResult) {
        this.a = iCarRouteResult;
        this.b = iCarRouteResult.getCarPlate();
    }

    public tb() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0079, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void parser(byte[] r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 != 0) goto L_0x0014
            r0 = 13
            r5.errorCode = r0     // Catch:{ Exception -> 0x0012 }
            int r0 = r5.errorCode     // Catch:{ Exception -> 0x0012 }
            java.lang.String r0 = a(r0)     // Catch:{ Exception -> 0x0012 }
            r5.errorMessage = r0     // Catch:{ Exception -> 0x0012 }
            goto L_0x0014
        L_0x0010:
            r6 = move-exception
            goto L_0x0082
        L_0x0012:
            r6 = move-exception
            goto L_0x007a
        L_0x0014:
            com.amap.bundle.drivecommon.model.ICarRouteResult r0 = r5.a     // Catch:{ Exception -> 0x0012 }
            if (r0 == 0) goto L_0x0078
            if (r6 == 0) goto L_0x0068
            int r0 = r6.length     // Catch:{ Exception -> 0x0012 }
            r1 = 8
            if (r0 < r1) goto L_0x0068
            r0 = 0
            byte r2 = r6[r0]     // Catch:{ Exception -> 0x0012 }
            r2 = r2 & 255(0xff, float:3.57E-43)
            r3 = 1
            byte r3 = r6[r3]     // Catch:{ Exception -> 0x0012 }
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << r1
            int r2 = r2 + r3
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 != r3) goto L_0x0056
            int r2 = r6.length     // Catch:{ Exception -> 0x0012 }
            r3 = 10
            int r2 = r2 - r3
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x0012 }
            int r4 = r2.length     // Catch:{ Exception -> 0x0012 }
            java.lang.System.arraycopy(r6, r3, r2, r0, r4)     // Catch:{ Exception -> 0x0012 }
            byte[] r3 = new byte[r1]     // Catch:{ Exception -> 0x0012 }
            r4 = 2
            java.lang.System.arraycopy(r6, r4, r3, r0, r1)     // Catch:{ Exception -> 0x0012 }
            long r3 = defpackage.ahg.b(r3)     // Catch:{ Exception -> 0x0012 }
            int r6 = (int) r3     // Catch:{ Exception -> 0x0012 }
            com.amap.bundle.drivecommon.model.ICarRouteResult r1 = r5.a     // Catch:{ Exception -> 0x0012 }
            int r6 = r1.parseData(r2, r0, r6)     // Catch:{ Exception -> 0x0012 }
            r5.errorCode = r6     // Catch:{ Exception -> 0x0012 }
            int r6 = r5.errorCode     // Catch:{ Exception -> 0x0012 }
            java.lang.String r6 = a(r6)     // Catch:{ Exception -> 0x0012 }
            r5.errorMessage = r6     // Catch:{ Exception -> 0x0012 }
            monitor-exit(r5)
            return
        L_0x0056:
            android.app.Application r6 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0012 }
            android.content.res.Resources r6 = r6.getResources()     // Catch:{ Exception -> 0x0012 }
            int r0 = com.autonavi.minimap.R.string.autonavi_route_response_retry_failed     // Catch:{ Exception -> 0x0012 }
            java.lang.String r6 = r6.getString(r0)     // Catch:{ Exception -> 0x0012 }
            r5.errorMessage = r6     // Catch:{ Exception -> 0x0012 }
            monitor-exit(r5)
            return
        L_0x0068:
            android.app.Application r6 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0012 }
            android.content.res.Resources r6 = r6.getResources()     // Catch:{ Exception -> 0x0012 }
            int r0 = com.autonavi.minimap.R.string.autonavi_route_response_retry_failed     // Catch:{ Exception -> 0x0012 }
            java.lang.String r6 = r6.getString(r0)     // Catch:{ Exception -> 0x0012 }
            r5.errorMessage = r6     // Catch:{ Exception -> 0x0012 }
        L_0x0078:
            monitor-exit(r5)
            return
        L_0x007a:
            r0 = 0
            r5.a = r0     // Catch:{ all -> 0x0010 }
            defpackage.kf.a(r6)     // Catch:{ all -> 0x0010 }
            monitor-exit(r5)
            return
        L_0x0082:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.tb.parser(byte[]):void");
    }

    public String getErrorDesc(int i) {
        return this.errorMessage;
    }

    public boolean a() {
        return this.a != null && this.a.hasData();
    }

    public static String a(int i) {
        String string = AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_response_retry_failed);
        if (i == 21) {
            return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_via_not_supported);
        }
        switch (i) {
            case 0:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 13:
                return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_response_retry_failed);
            case 1:
                return string;
            case 2:
                break;
            case 3:
                return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_start_not_supported);
            case 6:
                return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_end_not_supported);
            case 10:
                return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_change_start_with_not_found);
            case 11:
                return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_change_end_with_not_found);
            case 12:
                return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_change_via_with_not_found);
            default:
                switch (i) {
                    case 16:
                    case 17:
                        break;
                    default:
                        return string;
                }
        }
        return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_net_error);
    }

    public String b() {
        if (TextUtils.isEmpty(this.errorMessage)) {
            return AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_route_response_retry_failed);
        }
        return this.errorMessage;
    }
}
