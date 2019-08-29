package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import java.util.Set;

@Deprecated
/* renamed from: dlx reason: default package */
/* compiled from: CompatIntentInterceptor */
public final class dlx implements dlh {
    private static void a(Intent intent, String str) {
        Uri data = intent.getData();
        if (data != null && !TextUtils.isEmpty(data.getHost())) {
            new StringBuilder("redirectSchemeUri: ").append(String.valueOf(data));
            Set<String> queryParameterNames = data.getQueryParameterNames();
            Builder clearQuery = data.buildUpon().clearQuery();
            for (String next : queryParameterNames) {
                clearQuery.appendQueryParameter(next, data.getQueryParameter(next));
            }
            if (!TextUtils.isEmpty(str)) {
                clearQuery.authority(str);
            }
            new StringBuilder("redirectSchemeUri result: ").append(String.valueOf(clearQuery.build()));
            intent.setData(clearQuery.build());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0172, code lost:
        if (r4.equals("FromTo") != false) goto L_0x0202;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(android.content.Intent r8) {
        /*
            r7 = this;
            r0 = 1
            r1 = 0
            if (r8 == 0) goto L_0x00c6
            android.net.Uri r2 = r8.getData()
            if (r2 == 0) goto L_0x00c6
            java.lang.String r3 = r2.getScheme()
            java.lang.String r4 = "amapuri"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x005d
            defpackage.dmh.a(r2)
            defpackage.dmh.b(r2)
            java.lang.String r3 = "clearStack"
            java.lang.String r3 = r2.getQueryParameter(r3)
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x003b
            java.lang.String r3 = "clearStack"
            java.lang.String r3 = r2.getQueryParameter(r3)
            java.lang.String r4 = "1"
            boolean r3 = r3.contentEquals(r4)
            if (r3 == 0) goto L_0x003b
            defpackage.dmg.a()
            r3 = 1
            goto L_0x003c
        L_0x003b:
            r3 = 0
        L_0x003c:
            if (r3 != 0) goto L_0x0053
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r3 == 0) goto L_0x0053
            java.lang.String r3 = "ajxdebug"
            java.lang.String r4 = r2.getHost()
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0053
            defpackage.dmi.a()
        L_0x0053:
            java.lang.String r3 = "sourceApplication"
            java.lang.String r2 = r2.getQueryParameter(r3)
            com.amap.bundle.network.request.param.NetworkParam.setSa(r2)
            goto L_0x00c6
        L_0x005d:
            java.lang.String r4 = "androidamap"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x00c6
            defpackage.dmh.b(r2)
            java.lang.String r3 = r2.getHost()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x00c6
            java.lang.String r3 = "clearStack"
            java.lang.String r3 = r2.getQueryParameter(r3)
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0091
            java.lang.String r3 = "clearStack"
            java.lang.String r3 = r2.getQueryParameter(r3)
            java.lang.String r4 = "1"
            boolean r3 = r3.contentEquals(r4)
            if (r3 == 0) goto L_0x0091
            defpackage.dmg.a()
            r3 = 1
            goto L_0x0092
        L_0x0091:
            r3 = 0
        L_0x0092:
            if (r3 != 0) goto L_0x00c3
            java.lang.String r3 = "sinaweibo"
            java.lang.String r4 = com.amap.bundle.network.request.param.NetworkParam.getSa()
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x00c3
            java.lang.String r3 = "isFromTheSameSchemeWithIOS"
            java.lang.String r3 = r2.getQueryParameter(r3)
            java.lang.String r4 = "true"
            boolean r3 = android.text.TextUtils.equals(r3, r4)
            if (r3 != 0) goto L_0x00c3
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r3 == 0) goto L_0x00c3
            java.lang.String r3 = "ajxdebug"
            java.lang.String r4 = r2.getHost()
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x00c3
            defpackage.dmi.a()
        L_0x00c3:
            defpackage.dmh.a(r2)
        L_0x00c6:
            if (r8 == 0) goto L_0x0308
            android.net.Uri r2 = r8.getData()
            if (r2 == 0) goto L_0x0308
            java.lang.String r3 = r2.getHost()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 == 0) goto L_0x00da
            goto L_0x0308
        L_0x00da:
            java.lang.String r3 = r2.getHost()
            java.lang.String r4 = "featureName"
            java.lang.String r4 = r2.getQueryParameter(r4)
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            if (r5 != 0) goto L_0x0308
            java.util.List r5 = r2.getPathSegments()
            java.lang.String r6 = "bus"
            boolean r6 = r3.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x00fd
            java.lang.String r0 = "busline"
            a(r8, r0)
            goto L_0x0308
        L_0x00fd:
            java.lang.String r6 = "route_toolbox"
            boolean r6 = r3.equalsIgnoreCase(r6)
            if (r6 != 0) goto L_0x0303
            java.lang.String r6 = "route"
            boolean r6 = r3.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x010f
            goto L_0x0303
        L_0x010f:
            java.lang.String r6 = "health"
            boolean r6 = r3.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x0154
            if (r5 == 0) goto L_0x0308
            int r0 = r5.size()
            if (r0 > 0) goto L_0x0121
            goto L_0x0308
        L_0x0121:
            java.lang.Object r0 = r5.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r2 = "run"
            boolean r2 = android.text.TextUtils.equals(r0, r2)
            if (r2 == 0) goto L_0x0136
            java.lang.String r0 = "healthyRun"
            a(r8, r0)
            goto L_0x0308
        L_0x0136:
            java.lang.String r2 = "ride"
            boolean r2 = android.text.TextUtils.equals(r0, r2)
            if (r2 == 0) goto L_0x0145
            java.lang.String r0 = "healthyRide"
            a(r8, r0)
            goto L_0x0308
        L_0x0145:
            java.lang.String r2 = "runRoute"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x0308
            java.lang.String r0 = "runRecommend"
            a(r8, r0)
            goto L_0x0308
        L_0x0154:
            java.lang.String r5 = "openFeature"
            boolean r5 = r3.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x02cf
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x02cf
            r3 = -1
            int r5 = r4.hashCode()
            switch(r5) {
                case -1821939270: goto L_0x01f6;
                case -1781830854: goto L_0x01ec;
                case -1691992770: goto L_0x01e1;
                case -1479466419: goto L_0x01d7;
                case -1299155682: goto L_0x01cd;
                case -1152039504: goto L_0x01c2;
                case -1057416227: goto L_0x01b8;
                case -1009104980: goto L_0x01ae;
                case -659044068: goto L_0x01a4;
                case -645512440: goto L_0x0199;
                case 2398323: goto L_0x018d;
                case 862524499: goto L_0x0182;
                case 1499275331: goto L_0x0176;
                case 2112736229: goto L_0x016c;
                default: goto L_0x016a;
            }
        L_0x016a:
            goto L_0x0201
        L_0x016c:
            java.lang.String r5 = "FromTo"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0201
            goto L_0x0202
        L_0x0176:
            java.lang.String r0 = "Settings"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 11
            goto L_0x0202
        L_0x0182:
            java.lang.String r0 = "OnFootNavi"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 6
            goto L_0x0202
        L_0x018d:
            java.lang.String r0 = "Mine"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 9
            goto L_0x0202
        L_0x0199:
            java.lang.String r0 = "SetNavi"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 13
            goto L_0x0202
        L_0x01a4:
            java.lang.String r0 = "BuslineSearch"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 4
            goto L_0x0202
        L_0x01ae:
            java.lang.String r0 = "openFromToResult"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 3
            goto L_0x0202
        L_0x01b8:
            java.lang.String r0 = "OnRideNavi"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 7
            goto L_0x0202
        L_0x01c2:
            java.lang.String r0 = "TrainSearch"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 8
            goto L_0x0202
        L_0x01cd:
            java.lang.String r0 = "RealTimeBusPosition"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 5
            goto L_0x0202
        L_0x01d7:
            java.lang.String r0 = "PathSearch"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 0
            goto L_0x0202
        L_0x01e1:
            java.lang.String r0 = "Measure"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 10
            goto L_0x0202
        L_0x01ec:
            java.lang.String r0 = "Travel"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 2
            goto L_0x0202
        L_0x01f6:
            java.lang.String r0 = "SetMap"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0201
            r0 = 12
            goto L_0x0202
        L_0x0201:
            r0 = -1
        L_0x0202:
            switch(r0) {
                case 0: goto L_0x02c9;
                case 1: goto L_0x02c9;
                case 2: goto L_0x02c9;
                case 3: goto L_0x02c9;
                case 4: goto L_0x02c3;
                case 5: goto L_0x02bd;
                case 6: goto L_0x02b7;
                case 7: goto L_0x02b1;
                case 8: goto L_0x02ab;
                case 9: goto L_0x0215;
                case 10: goto L_0x020e;
                case 11: goto L_0x0207;
                case 12: goto L_0x0207;
                case 13: goto L_0x0207;
                default: goto L_0x0205;
            }
        L_0x0205:
            goto L_0x0308
        L_0x0207:
            java.lang.String r0 = "settings"
            a(r8, r0)
            goto L_0x0308
        L_0x020e:
            java.lang.String r0 = "measure"
            a(r8, r0)
            goto L_0x0308
        L_0x0215:
            java.lang.String r0 = "page"
            java.lang.String r0 = r2.getQueryParameter(r0)
            java.lang.String r3 = "ToolBox"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x026b
            java.lang.String r0 = "item"
            java.lang.String r0 = r2.getQueryParameter(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x0308
            java.lang.String r2 = "BusLine"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x023e
            java.lang.String r0 = "busline"
            a(r8, r0)
            goto L_0x0308
        L_0x023e:
            java.lang.String r2 = "Measure"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x024d
            java.lang.String r0 = "measure"
            a(r8, r0)
            goto L_0x0308
        L_0x024d:
            java.lang.String r2 = "DriveHelp"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x025c
            java.lang.String r0 = "toolbox"
            a(r8, r0)
            goto L_0x0308
        L_0x025c:
            java.lang.String r2 = "QRCode"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0308
            java.lang.String r0 = "toolbox"
            a(r8, r0)
            goto L_0x0308
        L_0x026b:
            java.lang.String r3 = "Fortune"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0308
            java.lang.String r0 = "item"
            java.lang.String r0 = r2.getQueryParameter(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x0308
            java.lang.String r2 = "Wallet"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x028e
            java.lang.String r0 = "wallet"
            a(r8, r0)
            goto L_0x0308
        L_0x028e:
            java.lang.String r2 = "GoldCoin"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x029d
            java.lang.String r0 = "toolbox"
            a(r8, r0)
            goto L_0x0308
        L_0x029d:
            java.lang.String r2 = "Lottery"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0308
            java.lang.String r0 = "toolbox"
            a(r8, r0)
            goto L_0x0308
        L_0x02ab:
            java.lang.String r0 = "train"
            a(r8, r0)
            goto L_0x0308
        L_0x02b1:
            java.lang.String r0 = "rideNavi"
            a(r8, r0)
            goto L_0x0308
        L_0x02b7:
            java.lang.String r0 = "footNavi"
            a(r8, r0)
            goto L_0x0308
        L_0x02bd:
            java.lang.String r0 = "realtimeBus"
            a(r8, r0)
            goto L_0x0308
        L_0x02c3:
            java.lang.String r0 = "busline"
            a(r8, r0)
            goto L_0x0308
        L_0x02c9:
            java.lang.String r0 = "routePlan"
            a(r8, r0)
            goto L_0x0308
        L_0x02cf:
            java.lang.String r0 = "thirdpartyservice"
            boolean r0 = r3.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x02e9
            java.lang.String r0 = r2.getPath()
            java.lang.String r2 = "drivehelp"
            boolean r0 = r0.contains(r2)
            if (r0 == 0) goto L_0x0308
            java.lang.String r0 = "toolbox"
            a(r8, r0)
            goto L_0x0308
        L_0x02e9:
            java.lang.String r0 = "mine"
            boolean r0 = r3.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x0308
            java.lang.String r0 = "/activityzone"
            java.lang.String r2 = r2.getPath()
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0308
            java.lang.String r0 = "activities"
            a(r8, r0)
            goto L_0x0308
        L_0x0303:
            java.lang.String r0 = "routePlan"
            a(r8, r0)
        L_0x0308:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dlx.a(android.content.Intent):boolean");
    }
}
