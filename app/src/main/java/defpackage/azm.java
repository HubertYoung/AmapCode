package defpackage;

import android.os.Looper;
import com.amap.bundle.webview.page.WebViewPage;
import com.autonavi.bundle.routecommute.bus.details.BusCommuteDetailsPage;
import com.autonavi.bundle.routecommute.bus.details.BusCommuteListPage;
import com.autonavi.bundle.routecommute.common.CommuteHelper;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.bundle.routecommute.drive.page.AjxDriveCommuteEndPage;
import com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.ArrayList;

/* renamed from: azm reason: default package */
/* compiled from: SchemeRulesHandler */
public final class azm {
    private final CommuteHelper a;
    private boolean b;

    public azm(CommuteHelper commuteHelper) {
        this.a = commuteHelper;
    }

    public final void a(final String str, final String str2, final String str3) {
        String str4 = CommuteHelper.a;
        StringBuilder sb = new StringBuilder("SchemeRulesHandler handle type = ");
        sb.append(str);
        sb.append(", distance = ");
        sb.append(str2);
        sb.append(",   from:");
        sb.append(str3);
        azb.a(str4, sb.toString());
        if ("3".equals(str3)) {
            ArrayList<bid> pageContextStacks = AMapPageUtil.getPageContextStacks();
            if (!(pageContextStacks == null || pageContextStacks.size() == 0)) {
                boolean z = false;
                for (int size = pageContextStacks.size() - 1; size >= 0; size--) {
                    bid bid = pageContextStacks.get(size);
                    if (bid != null) {
                        if ((bid instanceof AjxDriveCommutePage) || (bid instanceof AjxDriveCommuteEndPage) || (bid instanceof BusCommuteListPage) || (bid instanceof BusCommuteDetailsPage)) {
                            bid.finish();
                        } else if (bid instanceof WebViewPage) {
                            if (!z) {
                                z = true;
                            } else {
                                bid.finish();
                            }
                        }
                    }
                }
            }
        } else if (!"4".equals(str3) && !"5".equals(str3)) {
            CommuteHelper commuteHelper = this.a;
            if (commuteHelper.c != null) {
                commuteHelper.c.startPage((String) "amap.basemap.action.default_page", new PageBundle());
            }
        }
        if (CommuteHelper.n() != null && this.a.j()) {
            azf.a((a) new a() {
                public final void a(final NaviAddress naviAddress) {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        azm.a(azm.this, str, str2, str3, naviAddress);
                    } else {
                        aho.a(new Runnable() {
                            public final void run() {
                                azm.a(azm.this, str, str2, str3, naviAddress);
                            }
                        });
                    }
                }
            });
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d7, code lost:
        r11 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d8, code lost:
        switch(r11) {
            case 0: goto L_0x00de;
            case 1: goto L_0x00dc;
            default: goto L_0x00db;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00dc, code lost:
        r10 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00de, code lost:
        r10 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(defpackage.azm r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, com.autonavi.bundle.routecommute.common.bean.NaviAddress r13) {
        /*
            java.lang.String r0 = com.autonavi.bundle.routecommute.common.CommuteHelper.a
            java.lang.String r1 = "SchemeRulesHandler dealAddress = "
            java.lang.String r2 = java.lang.String.valueOf(r13)
            java.lang.String r1 = r1.concat(r2)
            defpackage.azb.a(r0, r1)
            if (r13 == 0) goto L_0x01da
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r0 = r13.home
            if (r0 == 0) goto L_0x01da
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r0 = r13.company
            if (r0 != 0) goto L_0x001b
            goto L_0x01da
        L_0x001b:
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r0 = r13.home
            com.autonavi.common.model.POI r0 = r0.getHome()
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r1 = r13.company
            com.autonavi.common.model.POI r1 = r1.getCompany()
            boolean r2 = defpackage.bnx.a(r0)
            if (r2 == 0) goto L_0x01d6
            boolean r2 = defpackage.bnx.a(r1)
            if (r2 != 0) goto L_0x0035
            goto L_0x01d6
        L_0x0035:
            boolean r2 = android.text.TextUtils.isEmpty(r10)
            if (r2 == 0) goto L_0x003f
            java.lang.String r10 = defpackage.azi.g()
        L_0x003f:
            java.lang.String r2 = com.autonavi.bundle.routecommute.common.CommuteHelper.a
            java.lang.String r3 = "SchemeRulesHandler type = "
            java.lang.String r4 = java.lang.String.valueOf(r10)
            java.lang.String r3 = r3.concat(r4)
            defpackage.azb.a(r2, r3)
            java.lang.String r2 = "0"
            boolean r10 = android.text.TextUtils.equals(r10, r2)
            r2 = 0
            r3 = 1
            if (r10 == 0) goto L_0x019a
            com.autonavi.sdk.location.LocationInstrument r10 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0195 }
            r0 = 5
            com.autonavi.common.model.GeoPoint r10 = r10.getLatestPosition(r0)     // Catch:{ Exception -> 0x0195 }
            if (r10 != 0) goto L_0x0064
            return
        L_0x0064:
            org.json.JSONObject r0 = defpackage.azk.a(r10)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r1 = com.autonavi.bundle.routecommute.common.CommuteHelper.a     // Catch:{ Exception -> 0x0195 }
            java.lang.String r4 = "SchemeRulesHandler doDriveScheme start = "
            java.lang.String r5 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ Exception -> 0x0195 }
            defpackage.azb.a(r1, r4)     // Catch:{ Exception -> 0x0195 }
            if (r0 != 0) goto L_0x007a
            return
        L_0x007a:
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r1 = r13.home     // Catch:{ Exception -> 0x0195 }
            com.autonavi.common.model.POI r1 = r1.getHome()     // Catch:{ Exception -> 0x0195 }
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r4 = r13.company     // Catch:{ Exception -> 0x0195 }
            com.autonavi.common.model.POI r4 = r4.getCompany()     // Catch:{ Exception -> 0x0195 }
            com.autonavi.common.model.GeoPoint r5 = r1.getPoint()     // Catch:{ Exception -> 0x0195 }
            float r5 = defpackage.cfe.a(r10, r5)     // Catch:{ Exception -> 0x0195 }
            com.autonavi.common.model.GeoPoint r6 = r4.getPoint()     // Catch:{ Exception -> 0x0195 }
            float r10 = defpackage.cfe.a(r10, r6)     // Catch:{ Exception -> 0x0195 }
            com.autonavi.bundle.routecommute.common.bean.CommuteControlBean r6 = com.autonavi.bundle.routecommute.common.CommuteHelper.n()     // Catch:{ Exception -> 0x0195 }
            if (r6 == 0) goto L_0x009f
            java.lang.String r6 = r6.carBubbleRule     // Catch:{ Exception -> 0x0195 }
            goto L_0x00a0
        L_0x009f:
            r6 = r2
        L_0x00a0:
            boolean r6 = defpackage.azk.a(r6, r1, r4)     // Catch:{ Exception -> 0x0195 }
            r7 = -1
            r8 = 0
            if (r6 == 0) goto L_0x00b0
            int r10 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r10 <= 0) goto L_0x00ae
            r10 = 0
            goto L_0x00b3
        L_0x00ae:
            r10 = 1
            goto L_0x00b3
        L_0x00b0:
            r9.b = r3     // Catch:{ Exception -> 0x0195 }
            r10 = -1
        L_0x00b3:
            if (r10 != r7) goto L_0x00df
            boolean r5 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Exception -> 0x0195 }
            if (r5 != 0) goto L_0x00df
            int r5 = r11.hashCode()     // Catch:{ Exception -> 0x0195 }
            switch(r5) {
                case 48: goto L_0x00cd;
                case 49: goto L_0x00c3;
                default: goto L_0x00c2;
            }     // Catch:{ Exception -> 0x0195 }
        L_0x00c2:
            goto L_0x00d7
        L_0x00c3:
            java.lang.String r5 = "1"
            boolean r11 = r11.equals(r5)     // Catch:{ Exception -> 0x0195 }
            if (r11 == 0) goto L_0x00d7
            r11 = 1
            goto L_0x00d8
        L_0x00cd:
            java.lang.String r5 = "0"
            boolean r11 = r11.equals(r5)     // Catch:{ Exception -> 0x0195 }
            if (r11 == 0) goto L_0x00d7
            r11 = 0
            goto L_0x00d8
        L_0x00d7:
            r11 = -1
        L_0x00d8:
            switch(r11) {
                case 0: goto L_0x00de;
                case 1: goto L_0x00dc;
                default: goto L_0x00db;
            }     // Catch:{ Exception -> 0x0195 }
        L_0x00db:
            goto L_0x00df
        L_0x00dc:
            r10 = 1
            goto L_0x00df
        L_0x00de:
            r10 = 0
        L_0x00df:
            if (r10 != r7) goto L_0x00f2
            java.util.Calendar r11 = java.util.Calendar.getInstance()     // Catch:{ Exception -> 0x0195 }
            r5 = 9
            int r11 = r11.get(r5)     // Catch:{ Exception -> 0x0195 }
            switch(r11) {
                case 0: goto L_0x00f1;
                case 1: goto L_0x00ef;
                default: goto L_0x00ee;
            }     // Catch:{ Exception -> 0x0195 }
        L_0x00ee:
            goto L_0x00f2
        L_0x00ef:
            r10 = 0
            goto L_0x00f2
        L_0x00f1:
            r10 = 1
        L_0x00f2:
            java.lang.String r11 = com.autonavi.bundle.routecommute.common.CommuteHelper.a     // Catch:{ Exception -> 0x0195 }
            java.lang.String r5 = "SchemeRulesHandler getDriveSchemeEndType resultPoi =  "
            java.lang.String r6 = java.lang.String.valueOf(r10)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r5 = r5.concat(r6)     // Catch:{ Exception -> 0x0195 }
            defpackage.azb.a(r11, r5)     // Catch:{ Exception -> 0x0195 }
            if (r10 != 0) goto L_0x0105
            r11 = r1
            goto L_0x0106
        L_0x0105:
            r11 = r4
        L_0x0106:
            boolean r5 = defpackage.bnx.a(r11)     // Catch:{ Exception -> 0x0195 }
            if (r5 != 0) goto L_0x010d
            return
        L_0x010d:
            java.lang.String r5 = com.autonavi.bundle.routecommute.common.CommuteHelper.a     // Catch:{ Exception -> 0x0195 }
            java.lang.String r6 = "SchemeRulesHandler doDriveScheme end = "
            java.lang.String r7 = java.lang.String.valueOf(r11)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ Exception -> 0x0195 }
            defpackage.azb.a(r5, r6)     // Catch:{ Exception -> 0x0195 }
            org.json.JSONObject r11 = defpackage.azk.a(r11)     // Catch:{ Exception -> 0x0195 }
            if (r10 != 0) goto L_0x012e
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r10 = r13.home     // Catch:{ Exception -> 0x0195 }
            int r10 = r10.source     // Catch:{ Exception -> 0x0195 }
            if (r10 != r3) goto L_0x012b
            java.lang.String r10 = "0"
            goto L_0x0139
        L_0x012b:
            java.lang.String r10 = "2"
            goto L_0x0139
        L_0x012e:
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r10 = r13.company     // Catch:{ Exception -> 0x0195 }
            int r10 = r10.source     // Catch:{ Exception -> 0x0195 }
            if (r10 != r3) goto L_0x0137
            java.lang.String r10 = "1"
            goto L_0x0139
        L_0x0137:
            java.lang.String r10 = "3"
        L_0x0139:
            java.lang.String r13 = com.autonavi.bundle.routecommute.common.CommuteHelper.a     // Catch:{ Exception -> 0x0195 }
            java.lang.String r5 = "SchemeRulesHandler getEndTypeSource result =  "
            java.lang.String r6 = java.lang.String.valueOf(r10)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r5 = r5.concat(r6)     // Catch:{ Exception -> 0x0195 }
            defpackage.azb.a(r13, r5)     // Catch:{ Exception -> 0x0195 }
            org.json.JSONObject r13 = new org.json.JSONObject     // Catch:{ Exception -> 0x0195 }
            r13.<init>()     // Catch:{ Exception -> 0x0195 }
            java.lang.String r5 = "endPointType"
            r13.put(r5, r10)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r10 = "startPoi"
            r13.put(r10, r0)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r10 = "endPoi"
            r13.put(r10, r11)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r10 = "from"
            int r11 = java.lang.Integer.parseInt(r12)     // Catch:{ Exception -> 0x0195 }
            r13.put(r10, r11)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r10 = "isCpoint"
            boolean r11 = r9.b     // Catch:{ Exception -> 0x0195 }
            r13.put(r10, r11)     // Catch:{ Exception -> 0x0195 }
            defpackage.azi.a(r3)     // Catch:{ Exception -> 0x0195 }
            com.autonavi.bundle.routecommute.common.CommuteHelper r10 = r9.a     // Catch:{ Exception -> 0x0195 }
            r10.t()     // Catch:{ Exception -> 0x0195 }
            int r10 = defpackage.ayq.a(r2, r1, r4)     // Catch:{ Exception -> 0x0195 }
            com.autonavi.bundle.routecommute.common.CommuteHelper r11 = r9.a     // Catch:{ Exception -> 0x0195 }
            bao r11 = r11.o()     // Catch:{ Exception -> 0x0195 }
            r11.a(r10, r12)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r10 = r13.toString()     // Catch:{ Exception -> 0x0195 }
            com.autonavi.bundle.routecommute.common.CommuteHelper.c(r10)     // Catch:{ Exception -> 0x0195 }
            java.lang.String r10 = com.autonavi.bundle.routecommute.common.CommuteHelper.a     // Catch:{ Exception -> 0x0195 }
            java.lang.String r11 = "SchemeRulesHandler doDriveScheme startDriveCommutePage "
            defpackage.azb.a(r10, r11)     // Catch:{ Exception -> 0x0195 }
            defpackage.azi.h()     // Catch:{ Exception -> 0x0195 }
            r9.b = r8     // Catch:{ Exception -> 0x0195 }
            return
        L_0x0195:
            r9 = move-exception
            r9.printStackTrace()
            return
        L_0x019a:
            com.autonavi.bundle.routecommute.common.bean.CommuteControlBean r10 = com.autonavi.bundle.routecommute.common.CommuteHelper.n()
            if (r10 == 0) goto L_0x01a3
            java.lang.String r10 = r10.busBubbleRule
            goto L_0x01a4
        L_0x01a3:
            r10 = r2
        L_0x01a4:
            boolean r10 = defpackage.ayq.a(r10, r0, r1, r2, r3)
            java.lang.String r11 = com.autonavi.bundle.routecommute.common.CommuteHelper.a
            java.lang.String r12 = "SchemeRulesHandler doBusScheme  isBusDistanceRule = "
            java.lang.String r13 = java.lang.String.valueOf(r10)
            java.lang.String r12 = r12.concat(r13)
            defpackage.azb.a(r11, r12)
            if (r10 == 0) goto L_0x01d5
            defpackage.azi.a(r3)
            com.autonavi.bundle.routecommute.common.CommuteHelper r10 = r9.a
            r10.q()
            int r10 = defpackage.ayq.a(r2, r0, r1)
            com.autonavi.bundle.routecommute.common.CommuteHelper r11 = r9.a
            baq r11 = r11.r()
            com.autonavi.bundle.routecommute.common.CommuteHelper r9 = r9.a
            java.lang.String r9 = r9.d
            r11.a(r10, r9)
            defpackage.azi.h()
        L_0x01d5:
            return
        L_0x01d6:
            defpackage.azl.a(r10, r11)
            return
        L_0x01da:
            defpackage.azl.a(r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.azm.a(azm, java.lang.String, java.lang.String, java.lang.String, com.autonavi.bundle.routecommute.common.bean.NaviAddress):void");
    }
}
