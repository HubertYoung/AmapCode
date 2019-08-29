package com.amap.bundle.planhome.page;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.amap.bundle.planhome.ajx.ModuleHome;
import com.amap.bundle.planhome.ajx.ModulePlanHome;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.IPageHost;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.BatAjxPageInterface;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.modules.ModuleJsBridge;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;
import com.autonavi.minimap.route.train.stationlist.StationRequestManger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public final class AjxPlanHomePage extends Ajx3Page implements axe, axi, bgm, bgo, IVoiceCmdResponder, BatAjxPageInterface {
    public static final String a = "AjxPlanHomePage";
    public static RouteType b;
    private Object c;
    private int d;
    private String e = "path://amap_bundle_basemap_route/src/index.page.js";
    private IRouteUI f;
    private ModuleHome g;
    private boolean h = true;
    /* access modifiers changed from: private */
    public boolean i = false;
    private Map<RouteType, dic> j = new HashMap(6);
    private Map<RouteType, axe> k = new HashMap(6);
    private int l = 0;
    private PageBundle m = null;
    private int n = -1;
    private ResultType o = null;
    private a p;

    public interface a {
    }

    public final void finishSelf() {
    }

    @Nullable
    public final String getAjx3Url() {
        return "path://amap_bundle_basemap_route/src/index.page.js";
    }

    public final bgo getPresenter() {
        return this;
    }

    public final JSONObject getScenesData() {
        return null;
    }

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public final boolean isInnerPage() {
        return false;
    }

    public final boolean needKeepSessionAlive() {
        return false;
    }

    public final void pageCreated() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0030, code lost:
        r0 = r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r4, com.autonavi.common.PageBundle r5) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            int[] r1 = com.amap.bundle.planhome.page.AjxPlanHomePage.AnonymousClass3.a
            int r2 = r4.ordinal()
            r1 = r1[r2]
            switch(r1) {
                case 1: goto L_0x021a;
                case 2: goto L_0x01c9;
                case 3: goto L_0x018f;
                case 4: goto L_0x0149;
                case 5: goto L_0x0107;
                case 6: goto L_0x00c5;
                case 7: goto L_0x008c;
                case 8: goto L_0x0055;
                case 9: goto L_0x001c;
                case 10: goto L_0x0011;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x0260
        L_0x0011:
            com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r4 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.PREPARE_SWITCH_TAB
            java.lang.String r4 = r4.name()
            r5.getObject(r4)
            goto L_0x0260
        L_0x001c:
            com.autonavi.bundle.routecommon.model.RouteType r1 = b()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            if (r1 != r2) goto L_0x0033
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
        L_0x0030:
            r0 = r4
            goto L_0x0260
        L_0x0033:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            if (r1 != r2) goto L_0x0044
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0044:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r1 != r2) goto L_0x0260
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0055:
            com.autonavi.bundle.routecommon.model.RouteType r1 = b()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            if (r1 != r2) goto L_0x006a
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x006a:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            if (r1 != r2) goto L_0x007b
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x007b:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r1 != r2) goto L_0x0260
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x008c:
            com.autonavi.bundle.routecommon.model.RouteType r1 = b()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            if (r1 != r2) goto L_0x00a1
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x00a1:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            if (r1 != r2) goto L_0x00b3
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x00b3:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r1 != r2) goto L_0x0260
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x00c5:
            com.autonavi.bundle.routecommon.model.RouteType r1 = b()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            if (r1 != r2) goto L_0x00db
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x00db:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            if (r1 != r2) goto L_0x00f1
            java.util.Map<com.autonavi.bundle.routecommon.model.RouteType, axe> r1 = r3.k
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            java.lang.Object r1 = r1.get(r2)
            axe r1 = (defpackage.axe) r1
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x00f1:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r1 != r2) goto L_0x0260
            java.util.Map<com.autonavi.bundle.routecommon.model.RouteType, axe> r1 = r3.k
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            java.lang.Object r1 = r1.get(r2)
            axe r1 = (defpackage.axe) r1
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0107:
            com.autonavi.bundle.routecommon.model.RouteType r1 = b()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            if (r1 != r2) goto L_0x011d
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x011d:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            if (r1 != r2) goto L_0x0133
            java.util.Map<com.autonavi.bundle.routecommon.model.RouteType, axe> r1 = r3.k
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            java.lang.Object r1 = r1.get(r2)
            axe r1 = (defpackage.axe) r1
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0133:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r1 != r2) goto L_0x0260
            java.util.Map<com.autonavi.bundle.routecommon.model.RouteType, axe> r1 = r3.k
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            java.lang.Object r1 = r1.get(r2)
            axe r1 = (defpackage.axe) r1
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0149:
            com.autonavi.bundle.routecommon.model.RouteType r1 = b()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.COACH
            if (r1 == r2) goto L_0x0260
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRAIN
            if (r1 == r2) goto L_0x0260
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            if (r1 != r2) goto L_0x0167
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0167:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            if (r1 != r2) goto L_0x0179
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0179:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r1 != r2) goto L_0x018b
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x018b:
            com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.AIRTICKET
            goto L_0x0260
        L_0x018f:
            com.autonavi.bundle.routecommon.model.RouteType r1 = b()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            if (r1 != r2) goto L_0x01a5
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x01a5:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            if (r1 != r2) goto L_0x01b7
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x01b7:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r1 != r2) goto L_0x0260
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x01c9:
            com.autonavi.bundle.routecommon.model.RouteType r1 = b()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.COACH
            if (r1 == r2) goto L_0x0260
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRAIN
            if (r1 == r2) goto L_0x0260
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            if (r1 != r2) goto L_0x01eb
            java.util.Map<com.autonavi.bundle.routecommon.model.RouteType, axe> r1 = r3.k
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            java.lang.Object r1 = r1.get(r2)
            axe r1 = (defpackage.axe) r1
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x01eb:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            if (r1 != r2) goto L_0x0201
            java.util.Map<com.autonavi.bundle.routecommon.model.RouteType, axe> r1 = r3.k
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            java.lang.Object r1 = r1.get(r2)
            axe r1 = (defpackage.axe) r1
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0201:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r1 != r2) goto L_0x0217
            java.util.Map<com.autonavi.bundle.routecommon.model.RouteType, axe> r1 = r3.k
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            java.lang.Object r1 = r1.get(r2)
            axe r1 = (defpackage.axe) r1
            if (r1 == 0) goto L_0x0260
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0217:
            com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.AIRTICKET
            goto L_0x0260
        L_0x021a:
            com.autonavi.bundle.routecommon.model.RouteType r1 = b()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            if (r1 != r2) goto L_0x0230
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0254
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0230:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            if (r1 != r2) goto L_0x0242
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0254
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0242:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r1 != r2) goto L_0x0254
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            axe r1 = r3.a(r1)
            if (r1 == 0) goto L_0x0254
            boolean r4 = r1.a(r4, r5)
            goto L_0x0030
        L_0x0254:
            com.autonavi.minimap.ajx3.views.AmapAjxView r4 = r3.mAjxView
            if (r4 == 0) goto L_0x0260
            com.autonavi.minimap.ajx3.views.AmapAjxView r4 = r3.mAjxView
            boolean r4 = r4.backPressed()
            goto L_0x0030
        L_0x0260:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.planhome.page.AjxPlanHomePage.a(com.autonavi.bundle.routecommon.model.IRouteHeaderEvent, com.autonavi.common.PageBundle):boolean");
    }

    private axe a(RouteType routeType) {
        if (routeType == null) {
            return null;
        }
        return this.k.get(routeType);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" current tab: ");
        sb.append(b());
        return sb.toString();
    }

    public final void onJsBack(Object obj, String str) {
        StringBuilder sb = new StringBuilder("jsBack: object ");
        sb.append(obj);
        sb.append(" pageID ");
        sb.append(str);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, obj);
        setResult(ResultType.OK, pageBundle);
        if (this.f instanceof AbstractBasePresenter) {
            ((AbstractBasePresenter) this.f).onBackPressed();
        } else {
            finish();
        }
    }

    public final long getScene() {
        if (b != null) {
            switch (b) {
                case BUS:
                    return 32;
                case CAR:
                    return 8;
                case ONFOOT:
                    return 128;
                case TRAIN:
                    return 256;
                case RIDE:
                    return 64;
                case COACH:
                    return 512;
                case TAXI:
                    return 4;
                case TRUCK:
                    return 16;
            }
        }
        return 0;
    }

    public final Ajx3PagePresenter createPresenter() {
        return new adc(this);
    }

    public final void onCreate(Context context) {
        super.onCreate(context);
        this.d = getActivity().getWindow().getAttributes().softInputMode;
        requestScreenOn(false);
        requestScreenOrientation(1);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("url");
            if (!TextUtils.isEmpty(string)) {
                this.e = string;
            }
            this.p = (a) arguments.get("resultExecutor");
            this.c = arguments.getObject("jsData");
        }
        AMapPageFramework.setPageStateListener(this, new IPageStateListener() {
            public final void onCover() {
                AjxPlanHomePage.this.i = true;
            }

            public final void onAppear() {
                if (AjxPlanHomePage.b == RouteType.TAXI || AjxPlanHomePage.b == RouteType.FREERIDE) {
                    AjxPlanHomePage.this.a();
                }
            }
        });
    }

    public final void loadJs() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        this.mAjxView.load(this.e, this.c, a, displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public final void resume() {
        super.resume();
        setSoftInputMode(18);
        if (this.h) {
            this.h = false;
        } else if (this.i) {
            this.i = false;
        }
        if (this.f == null) {
            this.f = ((axd) getContentView().getParent()).getRouteInputUI();
            this.f.a((axe) this);
        } else if (this.f.o()) {
            this.f.a((axe) this);
        }
        RouteType b2 = b();
        dic dic = this.j.get(b2);
        if (dic != null) {
            dic.b();
        }
        if (b2 == RouteType.COACH) {
            StationRequestManger.a().a(1);
            return;
        }
        if (b2 == RouteType.TRAIN) {
            StationRequestManger.a().a(0);
        }
    }

    public final void pause() {
        super.pause();
        if (this.d != 0) {
            setSoftInputMode(this.d);
        }
        dic dic = this.j.get(b());
        if (dic != null) {
            dic.c();
        }
    }

    public final void start() {
        super.start();
        dic dic = this.j.get(b());
        if (dic != null) {
            dic.a();
        }
    }

    public final void stop() {
        super.stop();
        dic dic = this.j.get(b());
        if (dic != null) {
            dic.d();
        }
    }

    public final void destroy() {
        super.destroy();
        if (this.mAjxView != null) {
            ModulePlanHome modulePlanHome = (ModulePlanHome) this.mAjxView.getJsModule(ModulePlanHome.MODULE_NAME);
            if (modulePlanHome != null) {
                modulePlanHome.setHistoryItemClickListener(null);
            }
        }
        atb atb = (atb) defpackage.esb.a.a.a(atb.class);
        if (atb != null) {
            atb.b().a((AmapAjxViewInterface) this.mAjxView, (axi) null);
        }
        avl avl = (avl) defpackage.esb.a.a.a(avl.class);
        if (avl != null) {
            avl.b().a((AmapAjxViewInterface) this.mAjxView, (axi) null);
        }
        awy awy = (awy) defpackage.esb.a.a.a(awy.class);
        if (awy != null) {
            awy.b().a(this.mAjxView, (axi) null);
        }
        atw atw = (atw) defpackage.esb.a.a.a(atw.class);
        if (atw != null) {
            atw.c().a(this.mAjxView, null);
        }
        bdo bdo = (bdo) defpackage.esb.a.a.a(bdo.class);
        if (bdo != null) {
            bdo.a().a(this.mAjxView, null);
        }
        if (this.p != null) {
            this.p = null;
        }
        for (Entry<RouteType, dic> value : this.j.entrySet()) {
            dic dic = (dic) value.getValue();
            if (dic != null) {
                dic.e();
            }
        }
        b = null;
    }

    public final void result(int i2, ResultType resultType, PageBundle pageBundle) {
        if (pageBundle == null || resultType != ResultType.OK) {
            if (this.ajxPageStateInvoker != null) {
                this.ajxPageStateInvoker.setResumeData(null);
            }
        } else if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.setResumeData(pageBundle.get(ModuleHistory.AJX_BACK_RETURN_DATA_KEY));
        }
        if ((i2 == 1004 || i2 == 1005) && b() != RouteType.CAR) {
            dic dic = this.j.get(RouteType.CAR);
            if (dic != null) {
                dic.a(i2, resultType, pageBundle);
            }
        }
        if (this.p != null) {
            if (this.mAjxView.getAjxContext() != null) {
                ModuleJsBridge moduleJsBridge = (ModuleJsBridge) this.mAjxView.getJsModule("js");
                if (moduleJsBridge != null) {
                    moduleJsBridge.getJsMethod();
                }
            } else {
                return;
            }
        }
        dic dic2 = this.j.get(b());
        if (dic2 != null) {
            dic2.a(i2, resultType, pageBundle);
        } else {
            this.m = pageBundle;
            this.n = i2;
            this.o = resultType;
        }
        RouteType b2 = b();
        if (b2 == RouteType.BUS) {
            if (i2 == 1007 && ResultType.OK == resultType && pageBundle.containsKey("SubwayCityListFragment.adCode") && !TextUtils.isEmpty(pageBundle.getString("SubwayCityListFragment.adCode"))) {
                bdk bdk = (bdk) defpackage.esb.a.a.a(bdk.class);
                if (bdk != null) {
                    bdk.a(getActivity(), "");
                }
            }
            if (i2 == 1001) {
                POI a2 = eal.a(resultType, pageBundle);
                if (a2 != null) {
                    this.f.a(a2);
                }
                eal.a(this.f, getArguments());
                if (eal.a(this.f)) {
                    PageBundle pageBundle2 = new PageBundle();
                    pageBundle2.putString("bundle_key_source", "busRoute");
                    a(AjxPlanResultPage.class, RouteType.BUS, pageBundle2);
                }
            } else if (i2 == 1002) {
                POI a3 = eal.a(resultType, pageBundle);
                if (a3 != null) {
                    this.f.b(a3);
                }
                if (eal.a(this.f)) {
                    PageBundle pageBundle3 = new PageBundle();
                    pageBundle3.putString("bundle_key_source", "busRoute");
                    a(AjxPlanResultPage.class, RouteType.BUS, pageBundle3);
                }
            }
        }
        if (b2 == RouteType.ETRIP) {
            dhz dhz = (dhz) ank.a(dhz.class);
            if (dhz != null) {
                Class d2 = dhz.d();
                if (i2 == 1007 && ResultType.OK == resultType && pageBundle.containsKey("SubwayCityListFragment.adCode") && !TextUtils.isEmpty(pageBundle.getString("SubwayCityListFragment.adCode"))) {
                    bdk bdk2 = (bdk) defpackage.esb.a.a.a(bdk.class);
                    if (bdk2 != null) {
                        bdk2.a(getActivity(), "");
                    }
                }
                if (i2 == 1001) {
                    POI a4 = eal.a(resultType, pageBundle);
                    if (a4 != null) {
                        this.f.a(a4);
                    }
                    eal.a(this.f, getArguments());
                    if (eal.a(this.f)) {
                        a(d2, RouteType.ETRIP, null);
                    }
                } else if (i2 == 1002) {
                    POI a5 = eal.a(resultType, pageBundle);
                    if (a5 != null) {
                        this.f.b(a5);
                    }
                    if (eal.a(this.f)) {
                        a(d2, RouteType.ETRIP, null);
                    }
                }
            } else {
                return;
            }
        }
        if (b2 == RouteType.ONFOOT) {
            if (i2 == 1001) {
                POI a6 = eal.a(resultType, pageBundle);
                if (a6 != null) {
                    this.f.a(a6);
                }
                eal.a(this.f, getArguments());
                avl avl = (avl) defpackage.esb.a.a.a(avl.class);
                if (avl != null && eal.a(this.f)) {
                    a(avl.a().a(1), RouteType.ONFOOT, null);
                }
            } else if (i2 == 1002) {
                POI a7 = eal.a(resultType, pageBundle);
                if (a7 != null) {
                    this.f.b(a7);
                }
                avl avl2 = (avl) defpackage.esb.a.a.a(avl.class);
                if (avl2 != null && eal.a(this.f)) {
                    a(avl2.a().a(1), RouteType.ONFOOT, null);
                }
            }
        }
        if (b2 == RouteType.RIDE) {
            if (i2 == 1001) {
                POI a8 = eal.a(resultType, pageBundle);
                if (a8 != null) {
                    this.f.a(a8);
                }
                eal.a(this.f, getArguments());
                if (eal.a(this.f)) {
                    awy awy = (awy) defpackage.esb.a.a.a(awy.class);
                    if (awy != null) {
                        a(awy.a().a(1), RouteType.RIDE, null);
                    }
                }
            } else if (i2 == 1002) {
                POI a9 = eal.a(resultType, pageBundle);
                if (a9 != null) {
                    this.f.b(a9);
                }
                if (eal.a(this.f)) {
                    awy awy2 = (awy) defpackage.esb.a.a.a(awy.class);
                    if (awy2 != null) {
                        a(awy2.a().a(1), RouteType.RIDE, null);
                    }
                }
            }
        } else if (!(b2 == RouteType.TRAIN || b2 == RouteType.COACH)) {
            RouteType routeType = RouteType.AIRTICKET;
        }
    }

    public final boolean backPressed() {
        dic dic = this.j.get(b());
        if (dic != null && dic.f() == ResultType.CANCEL) {
            return true;
        }
        if (this.mAjxView != null) {
            return this.mAjxView.backPressed();
        }
        return false;
    }

    public final void a() {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("url");
            if (!TextUtils.isEmpty(string)) {
                this.e = string;
            }
            this.p = (a) arguments.get("resultExecutor");
            this.c = arguments.getObject("jsData");
            Object object = arguments.getObject("route_type");
            if (!(object == null || !(object instanceof RouteType) || this.g == null)) {
                RouteType routeType = (RouteType) object;
                if (routeType == RouteType.ONFOOT || routeType == RouteType.BUS) {
                    bdp bdp = (bdp) defpackage.esb.a.a.a(bdp.class);
                    if (bdp != null) {
                        bdp.b();
                    }
                }
            }
        }
        dic dic = this.j.get(b());
        if (dic != null) {
            dic.b(arguments);
        }
    }

    private static RouteType b() {
        if (adf.a() != null) {
            return adf.a().b();
        }
        return null;
    }

    public final void a(final chk chk) {
        getContentView().post(new Runnable() {
            public final void run() {
                bid pageContext = AMapPageUtil.getPageContext();
                IPageHost iPageHost = (pageContext == null || pageContext.getActivity() == null) ? null : (IPageHost) pageContext.getActivity();
                if (iPageHost == null || !iPageHost.isHostPaused()) {
                    AjxPlanHomePage.a(AjxPlanHomePage.this, chk);
                }
            }
        });
    }

    private void a(Class cls, RouteType routeType, PageBundle pageBundle) {
        if (this.f != null) {
            this.f.a(cls, routeType, pageBundle);
        }
    }

    public final long getScenesID() {
        if (b() == null) {
            return 2;
        }
        switch (b()) {
            case BUS:
                return 32;
            case CAR:
                return 8;
            case ONFOOT:
                return 128;
            case TRAIN:
                return 256;
            case RIDE:
                return 64;
            case COACH:
                return 512;
            case TAXI:
                return 4;
            case TRUCK:
                return 16;
            case ETRIP:
                return 137438953472L;
            case MOTOR:
                return 1125899906842624L;
            default:
                return 2;
        }
    }

    public final void onAjxContxtCreated(IAjxContext iAjxContext) {
        this.g = (ModuleHome) this.mAjxView.getJsModule(ModuleHome.MODULE_NAME);
        ModulePlanHome modulePlanHome = (ModulePlanHome) this.mAjxView.getJsModule(ModulePlanHome.MODULE_NAME);
        if (modulePlanHome != null) {
            modulePlanHome.setHistoryItemClickListener(this);
        }
        atb atb = (atb) defpackage.esb.a.a.a(atb.class);
        if (atb != null) {
            atb.b().a((AmapAjxViewInterface) this.mAjxView, (axi) this);
        }
        avl avl = (avl) defpackage.esb.a.a.a(avl.class);
        if (avl != null) {
            avl.b().a((AmapAjxViewInterface) this.mAjxView, (axi) this);
        }
        awy awy = (awy) defpackage.esb.a.a.a(awy.class);
        if (awy != null) {
            awy.b().a(this.mAjxView, (axi) this);
        }
        atw atw = (atw) defpackage.esb.a.a.a(atw.class);
        if (atw != null) {
            atw.c().a(this.mAjxView, this);
        }
        bdo bdo = (bdo) defpackage.esb.a.a.a(bdo.class);
        if (bdo != null) {
            bdo.a().a(this.mAjxView, this);
        }
        apl apl = (apl) defpackage.esb.a.a.a(apl.class);
        if (apl != null) {
            apl.b().a(this.mAjxView, this);
        }
        djk djk = (djk) ank.a(djk.class);
        if (djk != null) {
            try {
                dic a2 = djk.a((AbstractBasePage) this, (Object) this.mAjxView.getAjxContext());
                this.j.put(RouteType.CAR, a2);
                if (a2 instanceof axe) {
                    this.k.put(RouteType.CAR, (axe) a2);
                }
                if (b() == RouteType.CAR) {
                    a2.a(getArguments());
                    a2.b();
                }
                dic d2 = djk.d(this, this.mAjxView.getAjxContext());
                this.j.put(RouteType.MOTOR, d2);
                if (d2 instanceof axe) {
                    this.k.put(RouteType.MOTOR, (axe) d2);
                }
                if (b() == RouteType.MOTOR) {
                    d2.a(getArguments());
                    d2.b();
                    if (this.m != null) {
                        d2.a(this.n, this.o, this.m);
                        this.n = -1;
                        this.o = null;
                        this.m = null;
                    }
                }
                dic b2 = djk.b((AbstractBasePage) this, (Object) this.mAjxView.getAjxContext());
                this.j.put(RouteType.TRUCK, b2);
                if (b2 instanceof axe) {
                    this.k.put(RouteType.TRUCK, (axe) b2);
                }
                if (b() == RouteType.TRUCK) {
                    b2.a(getArguments());
                    b2.b();
                }
                dic c2 = djk.c(this, this.mAjxView.getAjxContext());
                this.j.put(RouteType.ETRIP, c2);
                if (c2 instanceof axe) {
                    this.k.put(RouteType.ETRIP, (axe) c2);
                }
                if (b() == RouteType.ETRIP) {
                    c2.a(getArguments());
                    c2.b();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    static /* synthetic */ void a(AjxPlanHomePage ajxPlanHomePage, chk chk) {
        RouteType b2 = b();
        ajxPlanHomePage.f.a((POI) null);
        ajxPlanHomePage.f.b((POI) null);
        if (b2 == RouteType.CAR) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("bundle_key_from_page", "plan_record");
            ajxPlanHomePage.f.a(chk.d(), (List<POI>) chk.f(), chk.e());
            ajxPlanHomePage.f.a(b2, pageBundle);
        } else if (b2 == RouteType.TRUCK) {
            PageBundle pageBundle2 = new PageBundle();
            pageBundle2.putString("bundle_key_from_page", "plan_record");
            ajxPlanHomePage.f.a(chk.d(), (List<POI>) chk.f(), chk.e());
            ajxPlanHomePage.f.a(b2, pageBundle2);
        } else if (b2 == RouteType.ETRIP) {
            PageBundle pageBundle3 = new PageBundle();
            pageBundle3.putString("bundle_key_from_page", "plan_record");
            ajxPlanHomePage.f.a(chk.d(), (List<POI>) chk.f(), chk.e());
            ajxPlanHomePage.f.a(b2, pageBundle3);
        } else if (b2 == RouteType.BUS) {
            ajxPlanHomePage.f.a(chk.d(), (List<POI>) null, chk.e());
            PageBundle pageBundle4 = new PageBundle();
            pageBundle4.putString("bundle_key_source", "history");
            ajxPlanHomePage.f.a(AjxPlanResultPage.class, b2, pageBundle4);
        } else if (b2 == RouteType.ONFOOT) {
            ajxPlanHomePage.f.a(chk.d(), (List<POI>) null, chk.e());
            avl avl = (avl) defpackage.esb.a.a.a(avl.class);
            if (avl != null) {
                ajxPlanHomePage.f.a((Class) avl.a().a(1), b2, (PageBundle) null);
            }
        } else if (b2 == RouteType.RIDE) {
            ajxPlanHomePage.f.a(chk.d(), (List<POI>) null, chk.e());
            awy awy = (awy) defpackage.esb.a.a.a(awy.class);
            if (awy != null) {
                ajxPlanHomePage.f.a(awy.a().a(1), (PageBundle) null);
            }
        } else if (b2 == RouteType.COACH) {
            ajxPlanHomePage.f.a(chk.d(), (List<POI>) null, chk.e());
        } else if (b2 == RouteType.TRAIN) {
            ajxPlanHomePage.f.a(chk.d(), (List<POI>) null, chk.e());
        } else {
            if (b2 == RouteType.AIRTICKET) {
                ajxPlanHomePage.f.a(chk.d(), (List<POI>) null, chk.e());
            }
        }
    }
}
