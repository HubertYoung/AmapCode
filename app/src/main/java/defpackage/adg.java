package defpackage;

import android.graphics.Point;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.planhome.page.PlanHomePage;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.widget.ConfirmDlg;
import com.autonavi.minimap.widget.ConfirmDlgLifeCircle;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.aos.serverkey;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"routePlan"})
/* renamed from: adg reason: default package */
/* compiled from: PlanHomeRouter */
public class adg extends esk {
    private static final String a = "adg";
    /* access modifiers changed from: private */
    public ConfirmDlg b = null;
    private String c = null;
    private OnClickListener d = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.cancel) {
                if (adg.this.b != null && adg.this.b.isShowing()) {
                    adg.this.b.dismiss();
                    adg.this.b = null;
                }
            } else if (id == R.id.confirm) {
                edr.b((String) "agree_ondest_declare", true);
                adg.this.c();
            }
        }
    };
    private OnClickListener e = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.cancel) {
                if (adg.this.b != null && adg.this.b.isShowing()) {
                    adg.this.b.dismiss();
                    adg.this.b = null;
                }
            } else if (id == R.id.confirm) {
                edr.b((String) "agree_onfoot_declare", true);
                adg.this.d();
            }
        }
    };

    /* JADX WARNING: Code restructure failed: missing block: B:131:0x023b, code lost:
        if (r4.equals("backDefault") != false) goto L_0x025d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0358, code lost:
        if (r4.equals("backDefault") != false) goto L_0x037a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:412:0x08a0 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:413:0x08a1  */
    /* JADX WARNING: Removed duplicated region for block: B:472:0x09c4 A[Catch:{ Exception -> 0x09d1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:479:0x09e8 A[Catch:{ Exception -> 0x0aa6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:507:0x0a50 A[Catch:{ Exception -> 0x0ae3 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.net.Uri r19) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r3 = 0
            if (r2 != 0) goto L_0x0008
            return r3
        L_0x0008:
            java.lang.String r4 = a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "processScheme: "
            r5.<init>(r6)
            java.lang.String r6 = java.lang.String.valueOf(r19)
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            defpackage.eao.a(r4, r5)
            java.util.List r4 = r19.getPathSegments()
            r5 = 0
            if (r4 == 0) goto L_0x0033
            int r6 = r4.size()
            if (r6 <= 0) goto L_0x0033
            java.lang.Object r4 = r4.get(r3)
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x0034
        L_0x0033:
            r4 = r5
        L_0x0034:
            java.lang.String r6 = "params"
            java.lang.String r6 = r2.getQueryParameter(r6)
            bid r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r7 == 0) goto L_0x0045
            android.app.Activity r7 = r7.getActivity()
            goto L_0x0046
        L_0x0045:
            r7 = r5
        L_0x0046:
            boolean r8 = android.text.TextUtils.isEmpty(r4)
            r11 = 7
            r12 = 4
            r13 = 1
            if (r8 != 0) goto L_0x0850
            int r8 = r4.hashCode()
            r9 = 8
            r10 = 5
            r16 = -1
            r15 = 2
            r14 = 3
            switch(r8) {
                case -2129354630: goto L_0x01a3;
                case -2005293913: goto L_0x0198;
                case -1581618112: goto L_0x018d;
                case -1525867330: goto L_0x0182;
                case -1419310402: goto L_0x0178;
                case -1268958287: goto L_0x016d;
                case -1228239952: goto L_0x0162;
                case -1106446972: goto L_0x0157;
                case -1049482625: goto L_0x014d;
                case -1046514932: goto L_0x0141;
                case -1042626835: goto L_0x0135;
                case -1039690024: goto L_0x0129;
                case -891525969: goto L_0x011d;
                case -859198101: goto L_0x0112;
                case -816323261: goto L_0x0106;
                case -680032883: goto L_0x00fa;
                case -664515973: goto L_0x00ee;
                case -353552518: goto L_0x00e2;
                case -292116290: goto L_0x00d6;
                case -53136335: goto L_0x00ca;
                case 3208415: goto L_0x00bf;
                case 3443497: goto L_0x00b4;
                case 110546608: goto L_0x00a7;
                case 240184948: goto L_0x009c;
                case 272721754: goto L_0x0090;
                case 394073396: goto L_0x0084;
                case 1197696195: goto L_0x0078;
                case 1316784841: goto L_0x006b;
                case 2035786481: goto L_0x005f;
                default: goto L_0x005d;
            }
        L_0x005d:
            goto L_0x01af
        L_0x005f:
            java.lang.String r8 = "busDetail"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 27
            goto L_0x01b0
        L_0x006b:
            java.lang.String r8 = "startRun"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 12
            goto L_0x01b0
        L_0x0078:
            java.lang.String r8 = "rideEnd"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 25
            goto L_0x01b0
        L_0x0084:
            java.lang.String r8 = "footNavi"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 18
            goto L_0x01b0
        L_0x0090:
            java.lang.String r8 = "backDefault"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 26
            goto L_0x01b0
        L_0x009c:
            java.lang.String r8 = "busline"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 5
            goto L_0x01b0
        L_0x00a7:
            java.lang.String r8 = "topup"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 8
            goto L_0x01b0
        L_0x00b4:
            java.lang.String r8 = "plan"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 1
            goto L_0x01b0
        L_0x00bf:
            java.lang.String r8 = "home"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 0
            goto L_0x01b0
        L_0x00ca:
            java.lang.String r8 = "rideRoute"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 22
            goto L_0x01b0
        L_0x00d6:
            java.lang.String r8 = "showPoiDetail"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 20
            goto L_0x01b0
        L_0x00e2:
            java.lang.String r8 = "footPreview"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 17
            goto L_0x01b0
        L_0x00ee:
            java.lang.String r8 = "footRoute"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 16
            goto L_0x01b0
        L_0x00fa:
            java.lang.String r8 = "footEnd"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 19
            goto L_0x01b0
        L_0x0106:
            java.lang.String r8 = "vipbus"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 7
            goto L_0x01b0
        L_0x0112:
            java.lang.String r8 = "realtime"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 3
            goto L_0x01b0
        L_0x011d:
            java.lang.String r8 = "subway"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 6
            goto L_0x01b0
        L_0x0129:
            java.lang.String r8 = "notice"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 9
            goto L_0x01b0
        L_0x0135:
            java.lang.String r8 = "recommendRoute"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 14
            goto L_0x01b0
        L_0x0141:
            java.lang.String r8 = "showAllTracks"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 15
            goto L_0x01b0
        L_0x014d:
            java.lang.String r8 = "nearby"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 4
            goto L_0x01b0
        L_0x0157:
            java.lang.String r8 = "fromToRoute"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 28
            goto L_0x01b0
        L_0x0162:
            java.lang.String r8 = "ridePreview"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 23
            goto L_0x01b0
        L_0x016d:
            java.lang.String r8 = "follow"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 11
            goto L_0x01b0
        L_0x0178:
            java.lang.String r8 = "agroup"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 2
            goto L_0x01b0
        L_0x0182:
            java.lang.String r8 = "rideNavi"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 24
            goto L_0x01b0
        L_0x018d:
            java.lang.String r8 = "sharebike"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 10
            goto L_0x01b0
        L_0x0198:
            java.lang.String r8 = "createNewTrack"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 13
            goto L_0x01b0
        L_0x01a3:
            java.lang.String r8 = "startRide"
            boolean r8 = r4.equals(r8)
            if (r8 == 0) goto L_0x01af
            r8 = 21
            goto L_0x01b0
        L_0x01af:
            r8 = -1
        L_0x01b0:
            switch(r8) {
                case 0: goto L_0x084b;
                case 1: goto L_0x0604;
                case 2: goto L_0x05e5;
                case 3: goto L_0x05e1;
                case 4: goto L_0x05dd;
                case 5: goto L_0x05c2;
                case 6: goto L_0x05ae;
                case 7: goto L_0x0574;
                case 8: goto L_0x056d;
                case 9: goto L_0x0530;
                case 10: goto L_0x04f2;
                case 11: goto L_0x04ee;
                case 12: goto L_0x04d8;
                case 13: goto L_0x0493;
                case 14: goto L_0x047d;
                case 15: goto L_0x0428;
                case 16: goto L_0x032a;
                case 17: goto L_0x032a;
                case 18: goto L_0x032a;
                case 19: goto L_0x032a;
                case 20: goto L_0x0311;
                case 21: goto L_0x030b;
                case 22: goto L_0x0213;
                case 23: goto L_0x0213;
                case 24: goto L_0x0213;
                case 25: goto L_0x0213;
                case 26: goto L_0x01f6;
                case 27: goto L_0x01da;
                case 28: goto L_0x01b5;
                default: goto L_0x01b3;
            }
        L_0x01b3:
            goto L_0x0850
        L_0x01b5:
            boolean r3 = android.text.TextUtils.isEmpty(r6)
            if (r3 == 0) goto L_0x01c1
            java.lang.String r3 = "data"
            java.lang.String r6 = r2.getQueryParameter(r3)
        L_0x01c1:
            com.autonavi.common.model.POI r2 = defpackage.bnx.a(r6)
            com.autonavi.common.PageBundle r3 = new com.autonavi.common.PageBundle
            r3.<init>()
            java.lang.String r4 = "bundle_key_poi_end"
            r3.putObject(r4, r2)
            java.lang.String r2 = "bundle_key_auto_route"
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            r3.putObject(r2, r4)
            r1.a(r3)
            return r13
        L_0x01da:
            boolean r2 = android.text.TextUtils.isEmpty(r6)
            if (r2 != 0) goto L_0x01f5
            esb r2 = defpackage.esb.a.a
            java.lang.Class<atb> r3 = defpackage.atb.class
            esc r2 = r2.a(r3)
            atb r2 = (defpackage.atb) r2
            if (r2 == 0) goto L_0x01f5
            com.autonavi.bundle.busnavi.api.IBusNaviPage r2 = r2.a()
            r2.a(r6)
        L_0x01f5:
            return r13
        L_0x01f6:
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            boolean r3 = android.text.TextUtils.isEmpty(r6)
            if (r3 != 0) goto L_0x0206
            java.lang.String r3 = "jsData"
            r2.putString(r3, r6)
        L_0x0206:
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r3.finish()
            java.lang.String r3 = "amap.basemap.action.default_page"
            r1.startPage(r3, r2)
            return r13
        L_0x0213:
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            boolean r5 = android.text.TextUtils.isEmpty(r6)
            if (r5 != 0) goto L_0x0223
            java.lang.String r5 = "jsData"
            r2.putString(r5, r6)
        L_0x0223:
            int r5 = r4.hashCode()
            switch(r5) {
                case -1525867330: goto L_0x0252;
                case -1228239952: goto L_0x0248;
                case -53136335: goto L_0x023e;
                case 272721754: goto L_0x0235;
                case 1197696195: goto L_0x022b;
                default: goto L_0x022a;
            }
        L_0x022a:
            goto L_0x025c
        L_0x022b:
            java.lang.String r5 = "rideEnd"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x025c
            r12 = 3
            goto L_0x025d
        L_0x0235:
            java.lang.String r5 = "backDefault"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x025c
            goto L_0x025d
        L_0x023e:
            java.lang.String r5 = "rideRoute"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x025c
            r12 = 0
            goto L_0x025d
        L_0x0248:
            java.lang.String r5 = "ridePreview"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x025c
            r12 = 1
            goto L_0x025d
        L_0x0252:
            java.lang.String r5 = "rideNavi"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x025c
            r12 = 2
            goto L_0x025d
        L_0x025c:
            r12 = -1
        L_0x025d:
            switch(r12) {
                case 0: goto L_0x02ed;
                case 1: goto L_0x02ca;
                case 2: goto L_0x029d;
                case 3: goto L_0x0270;
                case 4: goto L_0x0262;
                default: goto L_0x0260;
            }
        L_0x0260:
            goto L_0x030a
        L_0x0262:
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r3.finish()
            java.lang.String r3 = "amap.basemap.action.default_page"
            r1.startPage(r3, r2)
            goto L_0x030a
        L_0x0270:
            java.lang.String r3 = "url"
            java.lang.String r4 = "path://amap_bundle_ride/src/end_page/RideEndPage.page.js"
            r2.putString(r3, r4)
            java.lang.String r3 = a(r6)
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x0287
            java.lang.String r4 = "jsData"
            r2.putString(r4, r3)
        L_0x0287:
            esb r3 = defpackage.esb.a.a
            java.lang.Class<aww> r4 = defpackage.aww.class
            esc r3 = r3.a(r4)
            aww r3 = (defpackage.aww) r3
            if (r3 == 0) goto L_0x030a
            com.autonavi.bundle.ridenavi.api.IRideNaviPage r3 = r3.a()
            r3.a(r15, r2)
            goto L_0x030a
        L_0x029d:
            android.app.Activity r2 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            boolean r2 = defpackage.ebm.a(r2)
            if (r2 == 0) goto L_0x030a
            r1.c = r6
            java.lang.String r2 = "agree_ondest_declare"
            boolean r2 = defpackage.edr.a(r2, r3)
            if (r2 == 0) goto L_0x02b5
            r18.c()
            goto L_0x030a
        L_0x02b5:
            com.autonavi.minimap.widget.ConfirmDlg r2 = new com.autonavi.minimap.widget.ConfirmDlg
            android.app.Activity r3 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            android.view.View$OnClickListener r4 = r1.d
            int r5 = com.autonavi.minimap.R.layout.ondest_declare
            r2.<init>(r3, r4, r5)
            r1.b = r2
            com.autonavi.minimap.widget.ConfirmDlg r2 = r1.b
            r2.show()
            goto L_0x030a
        L_0x02ca:
            java.lang.String r3 = "clickRideDetail"
            defpackage.eao.d(r3)
            java.lang.String r3 = "url"
            java.lang.String r4 = "path://amap_bundle_ride/src/preview_page/RidePreviewPage.page.js"
            r2.putString(r3, r4)
            esb r3 = defpackage.esb.a.a
            java.lang.Class<awy> r4 = defpackage.awy.class
            esc r3 = r3.a(r4)
            awy r3 = (defpackage.awy) r3
            if (r3 == 0) goto L_0x030a
            com.autonavi.bundle.rideresult.api.IRideResultPage r3 = r3.a()
            r3.a(r15, r2)
            goto L_0x030a
        L_0x02ed:
            java.lang.String r3 = "url"
            java.lang.String r4 = "path://amap_bundle_ride/src/result_page/RideResultPage.page.js"
            r2.putString(r3, r4)
            esb r3 = defpackage.esb.a.a
            java.lang.Class<awy> r4 = defpackage.awy.class
            esc r3 = r3.a(r4)
            awy r3 = (defpackage.awy) r3
            if (r3 == 0) goto L_0x030a
            com.autonavi.bundle.rideresult.api.IRideResultPage r3 = r3.a()
            r3.a(r13, r2)
        L_0x030a:
            return r13
        L_0x030b:
            java.lang.String r2 = "1"
            b(r2)
            return r13
        L_0x0311:
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            com.autonavi.common.model.POI r3 = defpackage.bnx.a(r6)
            java.lang.String r4 = "POI"
            r2.putObject(r4, r3)
            java.lang.String r3 = "amap.basemap.action.default_page"
            r1.startPage(r3, r5)
            java.lang.String r3 = "amap.search.action.poidetail"
            r1.startPage(r3, r2)
            return r13
        L_0x032a:
            java.lang.String r5 = "params"
            java.lang.String r2 = r2.getQueryParameter(r5)
            com.autonavi.common.PageBundle r5 = new com.autonavi.common.PageBundle
            r5.<init>()
            boolean r6 = android.text.TextUtils.isEmpty(r2)
            if (r6 != 0) goto L_0x0340
            java.lang.String r6 = "jsData"
            r5.putString(r6, r2)
        L_0x0340:
            int r6 = r4.hashCode()
            switch(r6) {
                case -680032883: goto L_0x036f;
                case -664515973: goto L_0x0365;
                case -353552518: goto L_0x035b;
                case 272721754: goto L_0x0352;
                case 394073396: goto L_0x0348;
                default: goto L_0x0347;
            }
        L_0x0347:
            goto L_0x0379
        L_0x0348:
            java.lang.String r6 = "footNavi"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x0379
            r12 = 2
            goto L_0x037a
        L_0x0352:
            java.lang.String r6 = "backDefault"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x0379
            goto L_0x037a
        L_0x035b:
            java.lang.String r6 = "footPreview"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x0379
            r12 = 1
            goto L_0x037a
        L_0x0365:
            java.lang.String r6 = "footRoute"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x0379
            r12 = 0
            goto L_0x037a
        L_0x036f:
            java.lang.String r6 = "footEnd"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x0379
            r12 = 3
            goto L_0x037a
        L_0x0379:
            r12 = -1
        L_0x037a:
            switch(r12) {
                case 0: goto L_0x040a;
                case 1: goto L_0x03e7;
                case 2: goto L_0x03ba;
                case 3: goto L_0x038d;
                case 4: goto L_0x037f;
                default: goto L_0x037d;
            }
        L_0x037d:
            goto L_0x0427
        L_0x037f:
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r2.finish()
            java.lang.String r2 = "amap.basemap.action.default_page"
            r1.startPage(r2, r5)
            goto L_0x0427
        L_0x038d:
            java.lang.String r3 = "url"
            java.lang.String r4 = "path://amap_bundle_foot/src/end_page/FootEndPage.page.js"
            r5.putString(r3, r4)
            java.lang.String r2 = c(r2)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x03a4
            java.lang.String r3 = "jsData"
            r5.putString(r3, r2)
        L_0x03a4:
            esb r2 = defpackage.esb.a.a
            java.lang.Class<avi> r3 = defpackage.avi.class
            esc r2 = r2.a(r3)
            avi r2 = (defpackage.avi) r2
            if (r2 == 0) goto L_0x0427
            com.autonavi.bundle.footnavi.api.IFootNaviPage r2 = r2.c()
            r2.a(r14, r5)
            goto L_0x0427
        L_0x03ba:
            android.app.Activity r4 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            boolean r4 = defpackage.ebm.a(r4)
            if (r4 == 0) goto L_0x0427
            r1.c = r2
            java.lang.String r2 = "agree_onfoot_declare"
            boolean r2 = defpackage.edr.a(r2, r3)
            if (r2 == 0) goto L_0x03d2
            r18.d()
            goto L_0x0427
        L_0x03d2:
            com.autonavi.minimap.widget.ConfirmDlg r2 = new com.autonavi.minimap.widget.ConfirmDlg
            android.app.Activity r3 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            android.view.View$OnClickListener r4 = r1.e
            int r5 = com.autonavi.minimap.R.layout.onfoot_declare
            r2.<init>(r3, r4, r5)
            r1.b = r2
            com.autonavi.minimap.widget.ConfirmDlg r2 = r1.b
            r2.show()
            goto L_0x0427
        L_0x03e7:
            java.lang.String r2 = "clickFootDetail"
            defpackage.eao.d(r2)
            java.lang.String r2 = "url"
            java.lang.String r3 = "path://amap_bundle_foot/src/preview_page/FootPreviewPage.page.js"
            r5.putString(r2, r3)
            esb r2 = defpackage.esb.a.a
            java.lang.Class<avl> r3 = defpackage.avl.class
            esc r2 = r2.a(r3)
            avl r2 = (defpackage.avl) r2
            if (r2 == 0) goto L_0x0427
            com.autonavi.bundle.footresult.api.IFootResultPage r2 = r2.a()
            r2.a(r14, r5)
            goto L_0x0427
        L_0x040a:
            esb r2 = defpackage.esb.a.a
            java.lang.Class<avl> r3 = defpackage.avl.class
            esc r2 = r2.a(r3)
            avl r2 = (defpackage.avl) r2
            if (r2 == 0) goto L_0x0427
            java.lang.String r3 = "url"
            java.lang.String r4 = "path://amap_bundle_foot/src/result_page/FootResultPage.page.js"
            r5.putString(r3, r4)
            com.autonavi.bundle.footresult.api.IFootResultPage r2 = r2.a()
            r2.a(r13, r5)
        L_0x0427:
            return r13
        L_0x0428:
            boolean r2 = android.text.TextUtils.isEmpty(r6)
            if (r2 != 0) goto L_0x047c
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x043b }
            r2.<init>(r6)     // Catch:{ JSONException -> 0x043b }
            java.lang.String r3 = "type"
            java.lang.String r2 = r2.optString(r3)     // Catch:{ JSONException -> 0x043b }
            goto L_0x0441
        L_0x043b:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r2 = r5
        L_0x0441:
            java.lang.String r3 = "foot"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x045f
            esb r2 = defpackage.esb.a.a
            java.lang.Class<avo> r3 = defpackage.avo.class
            esc r2 = r2.a(r3)
            avo r2 = (defpackage.avo) r2
            if (r2 == 0) goto L_0x047c
            com.autonavi.bundle.healthyrun.api.IHRunPage r2 = r2.a()
            r2.a(r14, r5)
            goto L_0x047c
        L_0x045f:
            java.lang.String r3 = "ride"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x047c
            esb r2 = defpackage.esb.a.a
            java.lang.Class<avn> r3 = defpackage.avn.class
            esc r2 = r2.a(r3)
            avn r2 = (defpackage.avn) r2
            if (r2 == 0) goto L_0x047c
            com.autonavi.bundle.healthyride.api.IHRidePage r2 = r2.a()
            r2.a(r14, r5)
        L_0x047c:
            return r13
        L_0x047d:
            esb r2 = defpackage.esb.a.a
            java.lang.Class<bay> r3 = defpackage.bay.class
            esc r2 = r2.a(r3)
            bay r2 = (defpackage.bay) r2
            if (r2 == 0) goto L_0x0492
            java.lang.Class r2 = r2.a()
            r1.startPage(r2, r5)
        L_0x0492:
            return r13
        L_0x0493:
            boolean r2 = android.text.TextUtils.isEmpty(r6)
            if (r2 != 0) goto L_0x04d7
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x04a6 }
            r2.<init>(r6)     // Catch:{ JSONException -> 0x04a6 }
            java.lang.String r3 = "type"
            java.lang.String r2 = r2.optString(r3)     // Catch:{ JSONException -> 0x04a6 }
            goto L_0x04ac
        L_0x04a6:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r2 = r5
        L_0x04ac:
            java.lang.String r3 = "foot"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x04ca
            esb r2 = defpackage.esb.a.a
            java.lang.Class<avo> r3 = defpackage.avo.class
            esc r2 = r2.a(r3)
            avo r2 = (defpackage.avo) r2
            if (r2 == 0) goto L_0x04d7
            com.autonavi.bundle.healthyrun.api.IHRunPage r2 = r2.a()
            r2.a(r13, r5)
            goto L_0x04d7
        L_0x04ca:
            java.lang.String r3 = "ride"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x04d7
            java.lang.String r2 = "2"
            b(r2)
        L_0x04d7:
            return r13
        L_0x04d8:
            esb r2 = defpackage.esb.a.a
            java.lang.Class<avo> r3 = defpackage.avo.class
            esc r2 = r2.a(r3)
            avo r2 = (defpackage.avo) r2
            if (r2 == 0) goto L_0x04ed
            com.autonavi.bundle.healthyrun.api.IHRunPage r2 = r2.a()
            r2.a(r13, r5)
        L_0x04ed:
            return r13
        L_0x04ee:
            a(r13, r13)
            return r13
        L_0x04f2:
            boolean r2 = android.text.TextUtils.isEmpty(r6)
            if (r2 != 0) goto L_0x051a
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0504 }
            r2.<init>(r6)     // Catch:{ JSONException -> 0x0504 }
            java.lang.String r3 = "firepage"
            java.lang.String r2 = r2.optString(r3)     // Catch:{ JSONException -> 0x0504 }
            goto L_0x050a
        L_0x0504:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r2 = r5
        L_0x050a:
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x051a
            com.autonavi.common.PageBundle r5 = new com.autonavi.common.PageBundle
            r5.<init>()
            java.lang.String r3 = "sharebike_page_from"
            r5.putString(r3, r2)
        L_0x051a:
            esb r2 = defpackage.esb.a.a
            java.lang.Class<bdf> r3 = defpackage.bdf.class
            esc r2 = r2.a(r3)
            bdf r2 = (defpackage.bdf) r2
            if (r2 == 0) goto L_0x052f
            com.autonavi.bundle.sharebike.api.IBikePage r2 = r2.b()
            r2.a(r14, r5)
        L_0x052f:
            return r13
        L_0x0530:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            com.amap.bundle.blutils.app.ConfigerHelper r3 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r4 = "busnotice_url"
            java.lang.String r3 = r3.getKeyValue(r4)
            r2.append(r3)
            java.lang.String r3 = "?adcode=110000"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            aja r3 = new aja
            r3.<init>(r2)
            ajf r2 = new ajf
            r2.<init>()
            r3.b = r2
            esb r2 = defpackage.esb.a.a
            java.lang.Class<aix> r4 = defpackage.aix.class
            esc r2 = r2.a(r4)
            aix r2 = (defpackage.aix) r2
            if (r2 == 0) goto L_0x056c
            bid r4 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r2.a(r4, r3)
        L_0x056c:
            return r13
        L_0x056d:
            java.lang.String r2 = "暂未推出此功能"
            com.amap.bundle.utils.ui.ToastHelper.showToast(r2)
            return r13
        L_0x0574:
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            java.lang.String r3 = "url"
            com.amap.bundle.blutils.app.ConfigerHelper r4 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r5 = "dadabus_url"
            java.lang.String r4 = r4.getKeyValue(r5)
            r2.putString(r3, r4)
            java.lang.String r3 = "show_bottom_bar"
            r2.putBoolean(r3, r13)
            java.lang.String r3 = "show_loading_anim"
            r2.putBoolean(r3, r13)
            java.lang.String r3 = "allow_geolocation"
            r2.putBoolean(r3, r13)
            java.lang.String r3 = "thirdpart_name"
            int r4 = com.autonavi.minimap.R.string.dadabus
            android.app.Application r5 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r4 = r5.getString(r4)
            r2.putString(r3, r4)
            java.lang.String r3 = "amap.search.action.thirdpartweb"
            r1.startPage(r3, r2)
            return r13
        L_0x05ae:
            esb r2 = defpackage.esb.a.a
            java.lang.Class<bdk> r3 = defpackage.bdk.class
            esc r2 = r2.a(r3)
            bdk r2 = (defpackage.bdk) r2
            if (r2 == 0) goto L_0x05c1
            java.lang.String r3 = ""
            r2.a(r7, r3)
        L_0x05c1:
            return r13
        L_0x05c2:
            esb r2 = defpackage.esb.a.a
            java.lang.Class<asy> r3 = defpackage.asy.class
            esc r2 = r2.a(r3)
            asy r2 = (defpackage.asy) r2
            if (r2 == 0) goto L_0x05dc
            com.autonavi.bundle.busline.api.IBusLinePage r2 = r2.b()
            com.autonavi.common.PageBundle r3 = new com.autonavi.common.PageBundle
            r3.<init>()
            r2.a(r12, r3)
        L_0x05dc:
            return r13
        L_0x05dd:
            a(r3, r3)
            return r13
        L_0x05e1:
            a(r3, r13)
            return r13
        L_0x05e5:
            java.lang.String r2 = "amapuri://AGroup/joinGroup"
            android.net.Uri r2 = android.net.Uri.parse(r2)
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            android.app.Activity r3 = r3.getActivity()
            boolean r4 = r3 instanceof defpackage.brr
            if (r4 == 0) goto L_0x0603
            android.content.Intent r4 = new android.content.Intent
            java.lang.String r5 = "android.intent.action.VIEW"
            r4.<init>(r5, r2)
            brr r3 = (defpackage.brr) r3
            r3.b(r4)
        L_0x0603:
            return r13
        L_0x0604:
            java.lang.String r4 = "shortcutLabel"
            java.lang.String r4 = r2.getQueryParameter(r4)
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x0628
            org.json.JSONObject r5 = new org.json.JSONObject
            r5.<init>()
            java.lang.String r6 = "status"
            r5.put(r6, r4)     // Catch:{ JSONException -> 0x061c }
            goto L_0x0621
        L_0x061c:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
        L_0x0621:
            java.lang.String r4 = "P00001"
            java.lang.String r6 = "B238"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r6, r5)
        L_0x0628:
            com.autonavi.common.model.POI r4 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            com.autonavi.common.model.POI r5 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            android.net.Uri r2 = e(r19)     // Catch:{ Exception -> 0x084a }
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x084a }
            java.lang.String r6 = "t"
            java.lang.String r6 = r2.getQueryParameter(r6)     // Catch:{ Exception -> 0x084a }
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x084a }
            if (r7 != 0) goto L_0x064a
            int r16 = java.lang.Integer.parseInt(r6)     // Catch:{ Exception -> 0x084a }
            r6 = r16
            goto L_0x064b
        L_0x064a:
            r6 = -1
        L_0x064b:
            if (r6 != 0) goto L_0x0650
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x0650:
            if (r6 != r13) goto L_0x0655
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.BUS     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x0655:
            if (r6 != r15) goto L_0x065a
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x065a:
            if (r6 != r14) goto L_0x065f
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.RIDE     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x065f:
            if (r6 != r12) goto L_0x0664
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.TRAIN     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x0664:
            if (r6 != r10) goto L_0x0669
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.COACH     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x0669:
            if (r6 != r11) goto L_0x066e
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x066e:
            if (r6 != r9) goto L_0x0673
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x0673:
            r7 = 10
            if (r6 != r7) goto L_0x067a
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.AIRTICKET     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x067a:
            r7 = 11
            if (r6 != r7) goto L_0x0681
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.MOTOR     // Catch:{ Exception -> 0x084a }
            goto L_0x0685
        L_0x0681:
            com.autonavi.bundle.routecommon.model.RouteType r6 = defpackage.acr.a()     // Catch:{ Exception -> 0x084a }
        L_0x0685:
            com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x084a }
            if (r6 == r7) goto L_0x0809
            com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK     // Catch:{ Exception -> 0x084a }
            if (r6 == r7) goto L_0x0809
            com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.MOTOR     // Catch:{ Exception -> 0x084a }
            if (r6 != r7) goto L_0x0693
            goto L_0x0809
        L_0x0693:
            com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.RIDE     // Catch:{ Exception -> 0x084a }
            if (r6 != r7) goto L_0x06ae
            java.lang.String r7 = "rideType"
            java.lang.String r7 = r2.getQueryParameter(r7)     // Catch:{ Exception -> 0x084a }
            if (r7 == 0) goto L_0x06ab
            java.lang.String r8 = "elebike"
            boolean r7 = r8.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x084a }
            if (r7 == 0) goto L_0x06ab
            defpackage.edr.a(r13)     // Catch:{ Exception -> 0x084a }
            goto L_0x06ae
        L_0x06ab:
            defpackage.edr.a(r3)     // Catch:{ Exception -> 0x084a }
        L_0x06ae:
            java.lang.String r7 = "dev"
            java.lang.String r7 = r2.getQueryParameter(r7)     // Catch:{ Exception -> 0x084a }
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x084a }
            if (r8 != 0) goto L_0x06bf
            int r7 = java.lang.Integer.parseInt(r7)     // Catch:{ Exception -> 0x084a }
            goto L_0x06c0
        L_0x06bf:
            r7 = 0
        L_0x06c0:
            java.lang.String r8 = "sname"
            java.lang.String r8 = r2.getQueryParameter(r8)     // Catch:{ Exception -> 0x084a }
            java.lang.String r9 = "slat"
            java.lang.String r9 = r2.getQueryParameter(r9)     // Catch:{ Exception -> 0x084a }
            java.lang.String r10 = "slon"
            java.lang.String r10 = r2.getQueryParameter(r10)     // Catch:{ Exception -> 0x084a }
            if (r8 != 0) goto L_0x06d7
            java.lang.String r8 = ""
            goto L_0x06db
        L_0x06d7:
            java.lang.String r8 = r8.trim()     // Catch:{ Exception -> 0x084a }
        L_0x06db:
            boolean r8 = a(r4, r8, r9, r10, r7)     // Catch:{ Exception -> 0x084a }
            java.lang.String r9 = "dname"
            java.lang.String r9 = r2.getQueryParameter(r9)     // Catch:{ Exception -> 0x084a }
            java.lang.String r10 = "dlat"
            java.lang.String r10 = r2.getQueryParameter(r10)     // Catch:{ Exception -> 0x084a }
            java.lang.String r11 = "dlon"
            java.lang.String r2 = r2.getQueryParameter(r11)     // Catch:{ Exception -> 0x084a }
            if (r9 != 0) goto L_0x06f6
            java.lang.String r9 = ""
            goto L_0x06fa
        L_0x06f6:
            java.lang.String r9 = r9.trim()     // Catch:{ Exception -> 0x084a }
        L_0x06fa:
            java.lang.String r11 = "0"
            b(r5, r9, r10, r2, r7)     // Catch:{ Exception -> 0x084a }
            if (r8 == 0) goto L_0x077a
            com.autonavi.common.PageBundle r7 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x084a }
            r7.<init>()     // Catch:{ Exception -> 0x084a }
            java.lang.String r8 = "bundle_key_route_type"
            r7.putObject(r8, r6)     // Catch:{ Exception -> 0x084a }
            java.lang.String r6 = "bundle_key_poi_start"
            r7.putObject(r6, r4)     // Catch:{ Exception -> 0x084a }
            java.lang.String r6 = "bundle_key_poi_end"
            r7.putObject(r6, r5)     // Catch:{ Exception -> 0x084a }
            java.lang.String r6 = "bundle_key_keyword"
            java.lang.String r4 = r4.getName()     // Catch:{ Exception -> 0x084a }
            r7.putString(r6, r4)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_method"
            r7.putString(r4, r11)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_request_code"
            r6 = 1001(0x3e9, float:1.403E-42)
            r7.putInt(r4, r6)     // Catch:{ Exception -> 0x084a }
            boolean r4 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x084a }
            if (r4 != 0) goto L_0x074a
            java.lang.String r4 = "我的位置"
            boolean r4 = r4.equals(r9)     // Catch:{ Exception -> 0x084a }
            if (r4 == 0) goto L_0x074a
            com.autonavi.sdk.location.LocationInstrument r2 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x084a }
            com.autonavi.common.model.GeoPoint r2 = r2.getLatestPosition()     // Catch:{ Exception -> 0x084a }
            r5.setPoint(r2)     // Catch:{ Exception -> 0x084a }
            java.lang.String r2 = "bundle_key_poi_end"
            r7.putObject(r2, r5)     // Catch:{ Exception -> 0x084a }
            goto L_0x0770
        L_0x074a:
            boolean r4 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x084a }
            if (r4 != 0) goto L_0x075c
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x084a }
            if (r2 != 0) goto L_0x075c
            java.lang.String r2 = "bundle_key_poi_end"
            r7.putObject(r2, r5)     // Catch:{ Exception -> 0x084a }
            goto L_0x0770
        L_0x075c:
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x084a }
            if (r2 != 0) goto L_0x0770
            java.lang.String r2 = "我的位置"
            boolean r2 = r2.equals(r9)     // Catch:{ Exception -> 0x084a }
            if (r2 != 0) goto L_0x0770
            java.lang.String r2 = "bundle_key_end_poi_name_passed_in"
            r7.putString(r2, r9)     // Catch:{ Exception -> 0x084a }
        L_0x0770:
            java.lang.String r2 = "bundle_key_auto_route"
            r7.putBoolean(r2, r3)     // Catch:{ Exception -> 0x084a }
            r1.a(r7)     // Catch:{ Exception -> 0x084a }
            goto L_0x084a
        L_0x077a:
            boolean r7 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x084a }
            if (r7 == 0) goto L_0x07e5
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x084a }
            if (r2 == 0) goto L_0x07e5
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x084a }
            if (r2 == 0) goto L_0x07aa
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x084a }
            r2.<init>()     // Catch:{ Exception -> 0x084a }
            java.lang.String r5 = "bundle_key_route_type"
            r2.putObject(r5, r6)     // Catch:{ Exception -> 0x084a }
            java.lang.String r5 = "bundle_key_poi_start"
            r2.putObject(r5, r4)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_method"
            r2.putString(r4, r11)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_auto_route"
            r2.putBoolean(r4, r3)     // Catch:{ Exception -> 0x084a }
            r1.a(r2)     // Catch:{ Exception -> 0x084a }
            goto L_0x084a
        L_0x07aa:
            java.lang.String r2 = "我的位置"
            boolean r2 = r2.equals(r9)     // Catch:{ Exception -> 0x084a }
            if (r2 != 0) goto L_0x07e5
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x084a }
            r2.<init>()     // Catch:{ Exception -> 0x084a }
            java.lang.String r7 = "bundle_key_route_type"
            r2.putObject(r7, r6)     // Catch:{ Exception -> 0x084a }
            java.lang.String r6 = "bundle_key_poi_start"
            r2.putObject(r6, r4)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_poi_end"
            r2.putObject(r4, r5)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_keyword"
            java.lang.String r5 = r5.getName()     // Catch:{ Exception -> 0x084a }
            r2.putString(r4, r5)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_method"
            r2.putString(r4, r11)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_request_code"
            r5 = 1002(0x3ea, float:1.404E-42)
            r2.putInt(r4, r5)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_auto_route"
            r2.putBoolean(r4, r3)     // Catch:{ Exception -> 0x084a }
            r1.a(r2)     // Catch:{ Exception -> 0x084a }
            goto L_0x084a
        L_0x07e5:
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x084a }
            r2.<init>()     // Catch:{ Exception -> 0x084a }
            java.lang.String r7 = "bundle_key_route_type"
            r2.putObject(r7, r6)     // Catch:{ Exception -> 0x084a }
            java.lang.String r6 = "bundle_key_poi_start"
            r2.putObject(r6, r4)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "bundle_key_poi_end"
            r2.putObject(r4, r5)     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "key_savehistory"
            r2.putBoolean(r4, r3)     // Catch:{ Exception -> 0x084a }
            java.lang.String r3 = "bundle_key_auto_route"
            java.lang.Boolean r4 = java.lang.Boolean.TRUE     // Catch:{ Exception -> 0x084a }
            r2.putObject(r3, r4)     // Catch:{ Exception -> 0x084a }
            r1.a(r2)     // Catch:{ Exception -> 0x084a }
            goto L_0x084a
        L_0x0809:
            r18.a()     // Catch:{ Exception -> 0x084a }
            java.lang.Class<djk> r3 = defpackage.djk.class
            java.lang.Object r3 = defpackage.ank.a(r3)     // Catch:{ Exception -> 0x084a }
            djk r3 = (defpackage.djk) r3     // Catch:{ Exception -> 0x084a }
            if (r3 == 0) goto L_0x084a
            com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x084a }
            if (r6 != r4) goto L_0x0828
            android.content.Context r4 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()     // Catch:{ Exception -> 0x084a }
            java.lang.String r5 = "route"
            android.net.Uri r2 = b(r2, r5)     // Catch:{ Exception -> 0x084a }
            r3.a(r4, r2)     // Catch:{ Exception -> 0x084a }
            goto L_0x084a
        L_0x0828:
            com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK     // Catch:{ Exception -> 0x084a }
            if (r6 != r4) goto L_0x083a
            android.content.Context r4 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()     // Catch:{ Exception -> 0x084a }
            java.lang.String r5 = "route"
            android.net.Uri r2 = b(r2, r5)     // Catch:{ Exception -> 0x084a }
            r3.b(r4, r2)     // Catch:{ Exception -> 0x084a }
            goto L_0x084a
        L_0x083a:
            com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.MOTOR     // Catch:{ Exception -> 0x084a }
            if (r6 != r4) goto L_0x084a
            com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()     // Catch:{ Exception -> 0x084a }
            java.lang.String r4 = "route"
            android.net.Uri r2 = b(r2, r4)     // Catch:{ Exception -> 0x084a }
            r3.a(r2)     // Catch:{ Exception -> 0x084a }
        L_0x084a:
            return r13
        L_0x084b:
            boolean r2 = r18.d(r19)
            return r2
        L_0x0850:
            java.lang.String r4 = "featureName"
            java.lang.String r4 = r2.getQueryParameter(r4)
            boolean r6 = android.text.TextUtils.isEmpty(r4)
            if (r6 != 0) goto L_0x089d
            java.lang.String r6 = a
            java.lang.String r7 = "doOpenFeature: "
            java.lang.String r8 = java.lang.String.valueOf(r4)
            java.lang.String r7 = r7.concat(r8)
            defpackage.eao.a(r6, r7)
            java.lang.String r6 = "PathSearch"
            boolean r6 = r4.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x0878
            r1.a(r5)
        L_0x0876:
            r4 = 1
            goto L_0x089e
        L_0x0878:
            java.lang.String r6 = "FromTo"
            boolean r6 = r4.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x0884
            r18.c(r19)
            goto L_0x0876
        L_0x0884:
            java.lang.String r6 = "Travel"
            boolean r6 = r4.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x0890
            r18.c(r19)
            goto L_0x0876
        L_0x0890:
            java.lang.String r6 = "openFromToResult"
            boolean r4 = r4.equalsIgnoreCase(r6)
            if (r4 == 0) goto L_0x089d
            boolean r4 = r18.b(r19)
            goto L_0x089e
        L_0x089d:
            r4 = 0
        L_0x089e:
            if (r4 == 0) goto L_0x08a1
            return r13
        L_0x08a1:
            com.autonavi.common.model.POI r4 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            com.autonavi.common.model.POI r6 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            com.autonavi.common.PageBundle r7 = new com.autonavi.common.PageBundle
            r7.<init>()
            android.net.Uri r2 = e(r19)
            java.lang.String r8 = "t"
            java.lang.String r8 = r2.getQueryParameter(r8)     // Catch:{ Exception -> 0x0ae3 }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ Exception -> 0x0ae3 }
            if (r8 == r12) goto L_0x08d0
            if (r8 == r11) goto L_0x08cd
            switch(r8) {
                case 1: goto L_0x08ca;
                case 2: goto L_0x08c7;
                default: goto L_0x08c4;
            }     // Catch:{ Exception -> 0x0ae3 }
        L_0x08c4:
            com.autonavi.bundle.routecommon.model.RouteType r8 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x08d2
        L_0x08c7:
            com.autonavi.bundle.routecommon.model.RouteType r8 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x08d2
        L_0x08ca:
            com.autonavi.bundle.routecommon.model.RouteType r8 = com.autonavi.bundle.routecommon.model.RouteType.BUS     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x08d2
        L_0x08cd:
            com.autonavi.bundle.routecommon.model.RouteType r8 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x08d2
        L_0x08d0:
            com.autonavi.bundle.routecommon.model.RouteType r8 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT     // Catch:{ Exception -> 0x0ae3 }
        L_0x08d2:
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x0ae3 }
            if (r8 == r9) goto L_0x0aaa
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK     // Catch:{ Exception -> 0x0ae3 }
            if (r8 != r9) goto L_0x08dc
            goto L_0x0aaa
        L_0x08dc:
            java.lang.String r9 = "showResult"
            java.lang.String r9 = r2.getQueryParameter(r9)     // Catch:{ Exception -> 0x0ae3 }
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x0ae3 }
            if (r10 != 0) goto L_0x08ed
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x08ee
        L_0x08ed:
            r9 = 1
        L_0x08ee:
            java.lang.String r10 = "bundle_key_auto_route"
            if (r9 == 0) goto L_0x08f4
            r9 = 1
            goto L_0x08f5
        L_0x08f4:
            r9 = 0
        L_0x08f5:
            r7.putBoolean(r10, r9)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r9 = "dev"
            java.lang.String r9 = r2.getQueryParameter(r9)     // Catch:{ Exception -> 0x0ae3 }
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r10 = "sname"
            java.lang.String r10 = r2.getQueryParameter(r10)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r11 = "slat"
            java.lang.String r11 = r2.getQueryParameter(r11)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r12 = "slon"
            java.lang.String r12 = r2.getQueryParameter(r12)     // Catch:{ Exception -> 0x0ae3 }
            if (r10 != 0) goto L_0x0919
            java.lang.String r10 = ""
            goto L_0x091d
        L_0x0919:
            java.lang.String r10 = r10.trim()     // Catch:{ Exception -> 0x0ae3 }
        L_0x091d:
            java.lang.String r14 = "sid"
            java.lang.String r14 = r2.getQueryParameter(r14)     // Catch:{ Exception -> 0x0ae3 }
            boolean r15 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Exception -> 0x0ae3 }
            if (r15 != 0) goto L_0x092c
            r4.setId(r14)     // Catch:{ Exception -> 0x0ae3 }
        L_0x092c:
            boolean r10 = a(r4, r10, r11, r12, r9)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r11 = "dname"
            java.lang.String r11 = r2.getQueryParameter(r11)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r12 = "dlat"
            java.lang.String r12 = r2.getQueryParameter(r12)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r14 = "dlon"
            java.lang.String r14 = r2.getQueryParameter(r14)     // Catch:{ Exception -> 0x0ae3 }
            if (r11 != 0) goto L_0x0947
            java.lang.String r11 = ""
            goto L_0x094b
        L_0x0947:
            java.lang.String r11 = r11.trim()     // Catch:{ Exception -> 0x0ae3 }
        L_0x094b:
            java.lang.String r15 = "did"
            java.lang.String r15 = r2.getQueryParameter(r15)     // Catch:{ Exception -> 0x0ae3 }
            boolean r16 = android.text.TextUtils.isEmpty(r15)     // Catch:{ Exception -> 0x0ae3 }
            if (r16 != 0) goto L_0x095a
            r6.setId(r15)     // Catch:{ Exception -> 0x0ae3 }
        L_0x095a:
            b(r6, r11, r12, r14, r9)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r9 = "0"
            java.lang.String r15 = "sourceApplication"
            java.lang.String r15 = r2.getQueryParameter(r15)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r5 = "bundle_key_source_app"
            r7.putString(r5, r15)     // Catch:{ Exception -> 0x0ae3 }
            if (r2 == 0) goto L_0x09c1
            java.lang.String r5 = "backScheme"
            java.lang.String r5 = r2.getQueryParameter(r5)     // Catch:{ Exception -> 0x0ae3 }
            boolean r15 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0ae3 }
            if (r15 != 0) goto L_0x097d
            android.net.Uri r5 = android.net.Uri.parse(r5)     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x097e
        L_0x097d:
            r5 = 0
        L_0x097e:
            java.lang.String r15 = "sourceApplication"
            java.lang.String r15 = r2.getQueryParameter(r15)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r3 = "backCategory"
            java.lang.String r3 = r2.getQueryParameter(r3)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r13 = "backAction"
            java.lang.String r2 = r2.getQueryParameter(r13)     // Catch:{ Exception -> 0x0ae3 }
            boolean r13 = android.text.TextUtils.isEmpty(r15)     // Catch:{ Exception -> 0x0ae3 }
            if (r13 == 0) goto L_0x09a0
            android.app.Application r13 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0ae3 }
            int r15 = com.autonavi.minimap.R.string.third_part     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r15 = r13.getString(r15)     // Catch:{ Exception -> 0x0ae3 }
        L_0x09a0:
            boolean r13 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0ae3 }
            if (r13 == 0) goto L_0x09a8
            java.lang.String r3 = "android.intent.category.DEFAULT"
        L_0x09a8:
            if (r5 == 0) goto L_0x09c1
            boolean r13 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0ae3 }
            if (r13 != 0) goto L_0x09c1
            dlg r13 = new dlg     // Catch:{ Exception -> 0x0ae3 }
            r13.<init>()     // Catch:{ Exception -> 0x0ae3 }
            r1 = 1
            r13.a = r1     // Catch:{ Exception -> 0x09d1 }
            r13.b = r5     // Catch:{ Exception -> 0x09d1 }
            r13.c = r15     // Catch:{ Exception -> 0x09d1 }
            r13.d = r3     // Catch:{ Exception -> 0x09d1 }
            r13.e = r2     // Catch:{ Exception -> 0x09d1 }
            goto L_0x09c2
        L_0x09c1:
            r13 = 0
        L_0x09c2:
            if (r13 == 0) goto L_0x09d7
            java.lang.String r1 = "key_action"
            java.lang.String r2 = "actiono_back_scheme"
            r7.putObject(r1, r2)     // Catch:{ Exception -> 0x09d1 }
            java.lang.String r1 = "key_back_scheme_param"
            r7.putObject(r1, r13)     // Catch:{ Exception -> 0x09d1 }
            goto L_0x09d7
        L_0x09d1:
            r0 = move-exception
            r2 = r0
            r1 = r18
            goto L_0x0ae5
        L_0x09d7:
            java.lang.String r1 = "bundle_key_route_type"
            r7.putObject(r1, r8)     // Catch:{ Exception -> 0x0aa6 }
            java.lang.String r1 = "bundle_key_poi_start"
            r7.putObject(r1, r4)     // Catch:{ Exception -> 0x0aa6 }
            java.lang.String r1 = "bundle_key_method"
            r7.putString(r1, r9)     // Catch:{ Exception -> 0x0aa6 }
            if (r10 == 0) goto L_0x0a50
            java.lang.String r1 = "bundle_key_poi_end"
            r7.putObject(r1, r6)     // Catch:{ Exception -> 0x0aa6 }
            java.lang.String r1 = "bundle_key_keyword"
            java.lang.String r2 = r4.getName()     // Catch:{ Exception -> 0x0aa6 }
            r7.putString(r1, r2)     // Catch:{ Exception -> 0x0aa6 }
            java.lang.String r1 = "bundle_key_request_code"
            r2 = 1001(0x3e9, float:1.403E-42)
            r7.putInt(r1, r2)     // Catch:{ Exception -> 0x0aa6 }
            boolean r1 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Exception -> 0x0aa6 }
            if (r1 != 0) goto L_0x0a1d
            java.lang.String r1 = "我的位置"
            boolean r1 = r1.equals(r11)     // Catch:{ Exception -> 0x09d1 }
            if (r1 == 0) goto L_0x0a1d
            com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x09d1 }
            com.autonavi.common.model.GeoPoint r1 = r1.getLatestPosition()     // Catch:{ Exception -> 0x09d1 }
            r6.setPoint(r1)     // Catch:{ Exception -> 0x09d1 }
            java.lang.String r1 = "bundle_key_poi_end"
            r7.putObject(r1, r6)     // Catch:{ Exception -> 0x09d1 }
            goto L_0x0a43
        L_0x0a1d:
            boolean r1 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x0aa6 }
            if (r1 != 0) goto L_0x0a2f
            boolean r1 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Exception -> 0x09d1 }
            if (r1 != 0) goto L_0x0a2f
            java.lang.String r1 = "bundle_key_poi_end"
            r7.putObject(r1, r6)     // Catch:{ Exception -> 0x09d1 }
            goto L_0x0a43
        L_0x0a2f:
            boolean r1 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Exception -> 0x0aa6 }
            if (r1 != 0) goto L_0x0a43
            java.lang.String r1 = "我的位置"
            boolean r1 = r1.equals(r11)     // Catch:{ Exception -> 0x09d1 }
            if (r1 != 0) goto L_0x0a43
            java.lang.String r1 = "bundle_key_end_poi_name_passed_in"
            r7.putString(r1, r11)     // Catch:{ Exception -> 0x09d1 }
        L_0x0a43:
            java.lang.String r1 = "bundle_key_auto_route"
            r2 = 0
            r7.putBoolean(r1, r2)     // Catch:{ Exception -> 0x0aa6 }
            r1 = r18
            r1.a(r7)     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x0ae8
        L_0x0a50:
            r1 = r18
            boolean r2 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x0ae3 }
            if (r2 == 0) goto L_0x0a97
            boolean r2 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Exception -> 0x0ae3 }
            if (r2 == 0) goto L_0x0a97
            boolean r2 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Exception -> 0x0ae3 }
            if (r2 == 0) goto L_0x0a6f
            java.lang.String r2 = "bundle_key_auto_route"
            r3 = 0
            r7.putBoolean(r2, r3)     // Catch:{ Exception -> 0x0ae3 }
            r1.a(r7)     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x0ae8
        L_0x0a6f:
            java.lang.String r2 = "我的位置"
            boolean r2 = r2.equals(r11)     // Catch:{ Exception -> 0x0ae3 }
            if (r2 != 0) goto L_0x0a97
            java.lang.String r2 = "bundle_key_poi_end"
            r7.putObject(r2, r6)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r2 = "bundle_key_keyword"
            java.lang.String r3 = r6.getName()     // Catch:{ Exception -> 0x0ae3 }
            r7.putString(r2, r3)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r2 = "bundle_key_request_code"
            r3 = 1002(0x3ea, float:1.404E-42)
            r7.putInt(r2, r3)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r2 = "bundle_key_auto_route"
            r3 = 0
            r7.putBoolean(r2, r3)     // Catch:{ Exception -> 0x0ae3 }
            r1.a(r7)     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x0ae8
        L_0x0a97:
            java.lang.String r2 = "bundle_key_auto_route"
            r3 = 1
            r7.putBoolean(r2, r3)     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r2 = "bundle_key_poi_end"
            r7.putObject(r2, r6)     // Catch:{ Exception -> 0x0ae3 }
            r1.a(r7)     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x0ae8
        L_0x0aa6:
            r0 = move-exception
            r1 = r18
            goto L_0x0ae4
        L_0x0aaa:
            r18.a()     // Catch:{ Exception -> 0x0ae3 }
            java.lang.Class<djk> r3 = defpackage.djk.class
            java.lang.Object r3 = defpackage.ank.a(r3)     // Catch:{ Exception -> 0x0ae3 }
            djk r3 = (defpackage.djk) r3     // Catch:{ Exception -> 0x0ae3 }
            if (r3 == 0) goto L_0x0ae8
            com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x0ae3 }
            if (r8 != r4) goto L_0x0acd
            android.app.Application r4 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0ae3 }
            android.content.Context r4 = r4.getApplicationContext()     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r5 = "route"
            android.net.Uri r2 = b(r2, r5)     // Catch:{ Exception -> 0x0ae3 }
            r3.a(r4, r2)     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x0ae8
        L_0x0acd:
            com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK     // Catch:{ Exception -> 0x0ae3 }
            if (r8 != r4) goto L_0x0ae8
            android.app.Application r4 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0ae3 }
            android.content.Context r4 = r4.getApplicationContext()     // Catch:{ Exception -> 0x0ae3 }
            java.lang.String r5 = "route"
            android.net.Uri r2 = b(r2, r5)     // Catch:{ Exception -> 0x0ae3 }
            r3.b(r4, r2)     // Catch:{ Exception -> 0x0ae3 }
            goto L_0x0ae8
        L_0x0ae3:
            r0 = move-exception
        L_0x0ae4:
            r2 = r0
        L_0x0ae5:
            r2.printStackTrace()
        L_0x0ae8:
            r2 = 1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.adg.a(android.net.Uri):boolean");
    }

    private boolean b(Uri uri) {
        GeoPoint geoPoint;
        GeoPoint geoPoint2;
        try {
            Uri e2 = e(uri);
            RouteType routeType = RouteType.CAR;
            int parseInt = Integer.parseInt(e2.getQueryParameter(LogItem.MM_C15_K4_TIME));
            if (parseInt == 1) {
                routeType = RouteType.BUS;
            } else if (parseInt == 2) {
                routeType = RouteType.CAR;
            } else if (parseInt == 4) {
                routeType = RouteType.ONFOOT;
            } else if (parseInt == 7) {
                routeType = RouteType.TRUCK;
            }
            if (routeType != RouteType.CAR) {
                if (routeType != RouteType.TRUCK) {
                    POI createPOI = POIFactory.createPOI();
                    POI createPOI2 = POIFactory.createPOI();
                    int parseInt2 = Integer.parseInt(e2.getQueryParameter("dev"));
                    String queryParameter = e2.getQueryParameter("sname");
                    String queryParameter2 = e2.getQueryParameter("slat");
                    String queryParameter3 = e2.getQueryParameter("slon");
                    if (TextUtils.isEmpty(queryParameter) || TextUtils.isEmpty(queryParameter2) || TextUtils.isEmpty(queryParameter3)) {
                        createPOI.setName("我的位置");
                        createPOI.setPoint(LocationInstrument.getInstance().getLatestPosition());
                    } else {
                        Point a2 = cfg.a(Double.valueOf(e2.getQueryParameter("slat")).doubleValue(), Double.valueOf(e2.getQueryParameter("slon")).doubleValue());
                        if (parseInt2 == 1) {
                            geoPoint2 = cff.a(a2.x, a2.y);
                        } else {
                            geoPoint2 = new GeoPoint(a2.x, a2.y);
                        }
                        createPOI.setName(queryParameter);
                        createPOI.setPoint(geoPoint2);
                    }
                    String queryParameter4 = e2.getQueryParameter("dname");
                    String queryParameter5 = e2.getQueryParameter("dlat");
                    String queryParameter6 = e2.getQueryParameter("dlon");
                    if (!TextUtils.isEmpty(queryParameter4) && !TextUtils.isEmpty(queryParameter5) && !TextUtils.isEmpty(queryParameter6)) {
                        Point a3 = cfg.a(Double.valueOf(e2.getQueryParameter("dlat")).doubleValue(), Double.valueOf(e2.getQueryParameter("dlon")).doubleValue());
                        if (parseInt2 == 1) {
                            geoPoint = cff.a(a3.x, a3.y);
                        } else {
                            geoPoint = new GeoPoint(a3.x, a3.y);
                        }
                        createPOI2.setName(queryParameter4);
                        createPOI2.setPoint(geoPoint);
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("bundle_key_route_type", routeType);
                    pageBundle.putObject("bundle_key_poi_start", createPOI);
                    pageBundle.putObject("bundle_key_poi_end", createPOI2);
                    pageBundle.putBoolean(DriveUtil.BUNDLE_KEY_BOOL_SAVECOOKIE, false);
                    pageBundle.putObject("bundle_key_auto_route", Boolean.TRUE);
                    a(pageBundle);
                    return true;
                }
            }
            a();
            djk djk = (djk) ank.a(djk.class);
            if (djk != null) {
                if (routeType == RouteType.CAR) {
                    djk.a(AMapAppGlobal.getApplication().getApplicationContext(), b(e2, BaseIntentDispatcher.HOST_OPENFEATURE));
                } else if (routeType == RouteType.TRUCK) {
                    djk.b(AMapAppGlobal.getApplication().getApplicationContext(), b(e2, BaseIntentDispatcher.HOST_OPENFEATURE));
                }
            }
            return true;
        } catch (Exception e3) {
            kf.a((Throwable) e3);
            return false;
        }
    }

    private void c(Uri uri) {
        PageBundle pageBundle = new PageBundle();
        String queryParameter = uri.getQueryParameter("page");
        Uri e2 = e(uri);
        if (!TextUtils.isEmpty(queryParameter)) {
            if (queryParameter.equals(ShowRouteActionProcessor.SEARCH_TYPE_BUS)) {
                pageBundle.putObject("bundle_key_route_type", RouteType.BUS);
            } else if (queryParameter.equals("car")) {
                a();
                djk djk = (djk) ank.a(djk.class);
                if (djk != null) {
                    djk.a(AMapAppGlobal.getApplication().getApplicationContext(), b(e2, BaseIntentDispatcher.HOST_OPENFEATURE));
                }
                return;
            } else {
                pageBundle.putObject("bundle_key_route_type", RouteType.ONFOOT);
            }
            pageBundle.putString("bundle_key_source_app", e2.getQueryParameter(DriveUtil.SOURCE_APPLICATION));
            pageBundle.putBoolean("bundle_key_auto_route", false);
            a(pageBundle);
            return;
        }
        a();
        djk djk2 = (djk) ank.a(djk.class);
        if (djk2 != null) {
            djk2.a(AMapAppGlobal.getApplication().getApplicationContext(), b(e2, BaseIntentDispatcher.HOST_OPENFEATURE));
        }
    }

    private boolean d(Uri uri) {
        try {
            String queryParameter = uri.getQueryParameter(LogItem.MM_C15_K4_TIME);
            if (!TextUtils.isEmpty(queryParameter)) {
                RouteType type = RouteType.getType(Integer.parseInt(queryParameter));
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("bundle_key_route_type", type);
                startPage(PlanHomePage.class, pageBundle);
                return true;
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    private void a() {
        Class<?> topPageClass = AMapPageUtil.getTopPageClass();
        if (topPageClass != null && topPageClass == PlanHomePage.class) {
            b();
        }
    }

    private void b() {
        try {
            startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
            ConfirmDlgLifeCircle.removeAll();
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append(e2.getMessage());
            AMapLog.e("BaseIntentDispatcher", sb.toString());
        }
    }

    private static int a(Uri uri, String str) {
        try {
            return Integer.parseInt(uri.getQueryParameter(str));
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    private static Uri e(Uri uri) {
        if (a(uri, (String) DriveUtil.SCHEME_PARAM_ENCRYPT) != 1) {
            return uri;
        }
        String amapDecodeV2 = serverkey.amapDecodeV2(uri.getQueryParameter("slat"));
        String amapDecodeV22 = serverkey.amapDecodeV2(uri.getQueryParameter("slon"));
        String amapDecodeV23 = serverkey.amapDecodeV2(uri.getQueryParameter("dlat"));
        String amapDecodeV24 = serverkey.amapDecodeV2(uri.getQueryParameter("dlon"));
        HashMap hashMap = new HashMap();
        hashMap.put(DriveUtil.SCHEME_PARAM_ENCRYPT, Integer.toString(0));
        hashMap.put("slat", amapDecodeV2);
        hashMap.put("slon", amapDecodeV22);
        hashMap.put("dlat", amapDecodeV23);
        hashMap.put("dlon", amapDecodeV24);
        return a(uri, (Map<String, String>) hashMap);
    }

    private static Uri a(Uri uri, Map<String, String> map) {
        Set<String> queryParameterNames = uri.getQueryParameterNames();
        Builder clearQuery = uri.buildUpon().clearQuery();
        for (String next : queryParameterNames) {
            clearQuery.appendQueryParameter(next, map.containsKey(next) ? map.get(next) : uri.getQueryParameter(next));
        }
        return clearQuery.build();
    }

    private void a(PageBundle pageBundle) {
        if (pageBundle != null) {
            pageBundle.putBoolean("bundle_key_from_scheme", true);
            pageBundle.putString("bundle_key_source", "scheme");
        }
        startPage(PlanHomePage.class, pageBundle);
    }

    private static boolean a(POI poi, String str, String str2, String str3, int i) {
        GeoPoint geoPoint;
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            if (!TextUtils.isEmpty(str)) {
                poi.setName(str);
            } else {
                poi.setName("地图选定位置");
            }
            Point a2 = cfg.a(Double.valueOf(str2).doubleValue(), Double.valueOf(str3).doubleValue());
            if (i == 1) {
                geoPoint = cff.a(a2.x, a2.y);
            } else {
                geoPoint = new GeoPoint(a2.x, a2.y);
            }
            poi.setPoint(geoPoint);
        } else if (TextUtils.isEmpty(str) || "我的位置".equals(str)) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            poi.setName("我的位置");
            poi.setPoint(latestPosition);
        } else {
            poi.setName(str);
            return true;
        }
        return false;
    }

    private static void b(POI poi, String str, String str2, String str3, int i) {
        GeoPoint geoPoint;
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            Point a2 = cfg.a(Double.valueOf(str2).doubleValue(), Double.valueOf(str3).doubleValue());
            if (i == 1) {
                geoPoint = cff.a(a2.x, a2.y);
            } else {
                geoPoint = new GeoPoint(a2.x, a2.y);
            }
            poi.setPoint(geoPoint);
            if (TextUtils.isEmpty(str)) {
                poi.setName("地图选定位置");
            }
        }
        if (!TextUtils.isEmpty(str)) {
            poi.setName(str);
            if ("我的位置".equals(str)) {
                poi.setPoint(LocationInstrument.getInstance().getLatestPosition());
            }
        }
    }

    private static void a(int i, int i2) {
        PageBundle pageBundle = new PageBundle();
        StringBuilder sb = new StringBuilder();
        sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
        pageBundle.putString("real_time_bus_adcode", sb.toString());
        pageBundle.putInt("where_click_real_time_bus", i);
        pageBundle.putInt("is_support_real_time_bus", i2);
        awv awv = (awv) a.a.a(awv.class);
        if (awv != null) {
            awv.a().a();
        }
    }

    private static String a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.put("ride_type", edr.a());
            String a2 = edn.a();
            if (!TextUtils.isEmpty(a2)) {
                jSONObject.put("trackStorageFolder", a2);
            }
            return JsonUtil.toString(jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private static void b(String str) {
        if (ebm.a(AMapAppGlobal.getTopActivity())) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("bundle_key_page_from", str);
            avn avn = (avn) a.a.a(avn.class);
            if (avn != null) {
                avn.a().a(1, pageBundle);
            }
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        eao.d("clickRideNavigationBtn");
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(this.c)) {
            pageBundle.putString("bundle_key_obj_result", this.c);
        }
        aww aww = (aww) a.a.a(aww.class);
        if (aww != null) {
            aww.a().a(1, pageBundle);
        }
    }

    private static String c(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String a2 = edn.a();
            if (!TextUtils.isEmpty(a2)) {
                jSONObject.put("trackStorageFolder", a2);
            }
            return JsonUtil.toString(jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        eao.d("clickFootNavigationBtn");
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(this.c)) {
            pageBundle.putString("bundle_key_obj_result", this.c);
        }
        avi avi = (avi) a.a.a(avi.class);
        if (avi != null) {
            avi.c().a(1, pageBundle);
        }
    }

    private static Uri b(Uri uri, String str) {
        if (uri == null || TextUtils.isEmpty(uri.getHost())) {
            return null;
        }
        new StringBuilder("redirectSchemeUri: ").append(String.valueOf(uri));
        Set<String> queryParameterNames = uri.getQueryParameterNames();
        Builder clearQuery = uri.buildUpon().clearQuery();
        for (String next : queryParameterNames) {
            clearQuery.appendQueryParameter(next, uri.getQueryParameter(next));
        }
        clearQuery.authority(str);
        new StringBuilder("redirectSchemeUri result: ").append(String.valueOf(clearQuery.build()));
        return clearQuery.build();
    }

    public boolean start(ese ese) {
        return a(ese.a);
    }
}
