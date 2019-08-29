package com.autonavi.map.search.manager;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.search.tip.GpsTipView;
import com.autonavi.map.search.tip.SearchPoiTipWrapper;
import com.autonavi.map.search.tip.SearchPoiTipWrapper.Type;
import com.autonavi.map.search.tip.indicator.SearchPoiIndicatorView;
import com.autonavi.map.search.utils.PoiDetailRequester;
import com.autonavi.map.search.view.PoiDetailViewForCQ;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page.AjxPageResultExecutor;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import com.autonavi.minimap.base.overlay.MapPointOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView.OnSetPoiListener;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState;
import java.io.Serializable;
import java.util.HashMap;
import org.json.JSONException;

public final class PoiDetailAjxLayerHandler implements bzn, bzp {
    int a;
    boolean b;
    String c;
    b d;
    InfoliteResult e;
    cbr f;
    com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState g = com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.COLLAPSED;
    bxx h;
    String i;
    int j = Integer.MAX_VALUE;
    bzo k;
    a l = new a() {
    };
    com.autonavi.map.search.tip.SearchPoiTipView.a m = new com.autonavi.map.search.tip.SearchPoiTipView.a() {
    };
    private int n;
    private View o;
    private com.autonavi.bundle.searchresult.ajx.AjxModuleTipDetailPage.a p = new com.autonavi.bundle.searchresult.ajx.AjxModuleTipDetailPage.a() {
        private boolean b;
        private int c = -1;

        public final void a(int i) {
        }

        /* JADX WARNING: Removed duplicated region for block: B:30:0x0084 A[Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }] */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x0086 A[Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }] */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x011a A[Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }] */
        /* JADX WARNING: Removed duplicated region for block: B:47:0x0129 A[Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(java.lang.String r9, com.autonavi.minimap.ajx3.core.JsFunctionCallback r10) {
            /*
                r8 = this;
                org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r10.<init>(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r9 = "from"
                int r9 = r10.optInt(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r0 = "action"
                java.lang.String r1 = ""
                java.lang.String r0 = r10.optString(r0, r1)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r1 = 2
                if (r9 != r1) goto L_0x0314
                boolean r9 = android.text.TextUtils.isEmpty(r0)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x001e
                goto L_0x0314
            L_0x001e:
                r9 = 0
                java.lang.String r2 = "param"
                boolean r2 = r10.has(r2)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r2 == 0) goto L_0x0035
                java.lang.String r9 = "param"
                java.lang.String r2 = ""
                java.lang.String r9 = r10.optString(r9, r2)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r2.<init>(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9 = r2
            L_0x0035:
                java.lang.String r2 = "detailStateDidChange"
                boolean r2 = r0.equals(r2)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r3 = 3560248(0x365338, float:4.98897E-39)
                r4 = 3154575(0x30228f, float:4.420501E-39)
                r5 = -1
                r6 = 1
                r7 = 0
                if (r2 == 0) goto L_0x01c5
                java.lang.String r10 = "state"
                java.lang.String r9 = r9.optString(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r10 == 0) goto L_0x0054
                return
            L_0x0054:
                int r10 = r9.hashCode()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r0 = -1068259250(0xffffffffc053a84e, float:-3.3071475)
                if (r10 == r0) goto L_0x0076
                if (r10 == r4) goto L_0x006c
                if (r10 == r3) goto L_0x0062
                goto L_0x0080
            L_0x0062:
                java.lang.String r10 = "tips"
                boolean r9 = r9.equals(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x0080
                goto L_0x0081
            L_0x006c:
                java.lang.String r10 = "full"
                boolean r9 = r9.equals(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x0080
                r1 = 0
                goto L_0x0081
            L_0x0076:
                java.lang.String r10 = "moving"
                boolean r9 = r9.equals(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x0080
                r1 = 1
                goto L_0x0081
            L_0x0080:
                r1 = -1
            L_0x0081:
                switch(r1) {
                    case 0: goto L_0x0129;
                    case 1: goto L_0x011a;
                    case 2: goto L_0x0086;
                    default: goto L_0x0084;
                }     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x0084:
                goto L_0x01c4
            L_0x0086:
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r10 = "tips"
                r9.c = r10     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.SearchResultTipDetailViewManager$DetailLayerState r10 = com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.COLLAPSED     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.g = r10     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                boolean r9 = defpackage.elc.a     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x00a9
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bqk r9 = r9.c()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzd r9 = r9.d()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                boolean r10 = defpackage.elc.a     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r10 = r10 ^ r6
                r9.a(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x00a9:
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bqk r9 = r9.c()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.a_(r7)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bqk r9 = r9.c()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.b(r7)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$3$1 r9 = new com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$3$1     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.<init>()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r0 = 500(0x1f4, double:2.47E-321)
                defpackage.aho.a(r9, r0)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bxx r9 = r9.h     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x00e1
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r9 = r9.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                int r9 = r9.o     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                boolean r9 = defpackage.bxx.a(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x00e1
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bxx r9 = r9.h     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.b = r6     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x00e1:
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.b(r6)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.a(r6)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.n()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r8.b = r7     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.p()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r9 = r9.p()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x01c4
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r10 = r10.p()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r10 = r10.p     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r9 = r9.p()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x01c4
                if (r10 == 0) goto L_0x01c4
                r10.getId()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                goto L_0x01c4
            L_0x011a:
                r8.b = r6     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.a(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.b(r7)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                return
            L_0x0129:
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bxx r9 = r9.h     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x0143
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r9 = r9.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                int r9 = r9.o     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                boolean r9 = defpackage.bxx.a(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x0143
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bxx r9 = r9.h     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.b = r7     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.a = r7     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x0143:
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.a(r7)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                boolean r9 = r8.b     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 != 0) goto L_0x0153
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.a(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x0153:
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.SearchResultTipDetailViewManager$DetailLayerState r10 = com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.EXPAND     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.g = r10     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r10 = "full"
                r9.c = r10     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.b(r7)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r10 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bqk r10 = r10.c()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.fragmentcontainer.page.MapBasePage r10 = r10.a()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.PageBundle r10 = r10.getArguments()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r10 == 0) goto L_0x01c1
                java.lang.String r0 = "key_detail_main_poi"
                java.lang.Object r10 = r10.getObject(r0)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r10 = (com.autonavi.common.model.POI) r10     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bxf r0 = defpackage.bqk.c     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r0 == 0) goto L_0x019e
                bxf r0 = defpackage.bqk.c     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r0 = r0.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r0 == 0) goto L_0x019e
                bxf r0 = defpackage.bqk.c     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                int r0 = r0.a     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r0 != 0) goto L_0x019e
                java.util.Set<java.lang.String> r10 = defpackage.bqk.b     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bxf r0 = defpackage.bqk.c     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r0 = r0.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r0 = r0.getId()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r10.add(r0)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                goto L_0x01a9
            L_0x019e:
                if (r10 == 0) goto L_0x01a9
                java.util.Set<java.lang.String> r0 = defpackage.bqk.b     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r10 = r10.getId()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r0.add(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x01a9:
                int r10 = r9.a     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r0 = 4
                if (r10 != r0) goto L_0x01c1
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r10 = r9.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r10 = r10.p     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r10 == 0) goto L_0x01c1
                java.util.Set<java.lang.String> r10 = defpackage.bqk.b     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r9 = r9.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r9 = r9.p     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r9 = r9.getId()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r10.add(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x01c1:
                r8.b = r7     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                return
            L_0x01c4:
                return
            L_0x01c5:
                java.lang.String r2 = "detailStateWillChange"
                boolean r2 = r0.equals(r2)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r2 == 0) goto L_0x022d
                java.lang.String r10 = "state"
                java.lang.String r9 = r9.optString(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r10 == 0) goto L_0x01db
                return
            L_0x01db:
                int r10 = r9.hashCode()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r10 == r4) goto L_0x01ef
                if (r10 == r3) goto L_0x01e4
                goto L_0x01f8
            L_0x01e4:
                java.lang.String r10 = "tips"
                boolean r9 = r9.equals(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x01f8
                r5 = 1
                goto L_0x01f8
            L_0x01ef:
                java.lang.String r10 = "full"
                boolean r9 = r9.equals(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r9 == 0) goto L_0x01f8
                r5 = 0
            L_0x01f8:
                switch(r5) {
                    case 0: goto L_0x0214;
                    case 1: goto L_0x01fc;
                    default: goto L_0x01fb;
                }     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x01fb:
                goto L_0x022c
            L_0x01fc:
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.SearchResultTipDetailViewManager$DetailLayerState r10 = com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.COLLAPSED     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.g = r10     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bqk r9 = r9.c()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.GeoPoint r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.b(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                goto L_0x022c
            L_0x0214:
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.SearchResultTipDetailViewManager$DetailLayerState r10 = com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.EXPAND     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.g = r10     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bqk r9 = r9.c()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.GeoPoint r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.b(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                return
            L_0x022c:
                return
            L_0x022d:
                java.lang.String r2 = "tipHeightChange"
                boolean r2 = r0.equals(r2)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r2 == 0) goto L_0x025e
                java.lang.String r10 = "height"
                java.lang.String r9 = r9.optString(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r10 = r10.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bqk r10 = r10.c()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.fragmentcontainer.page.MapBasePage r10 = r10.a()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                android.content.Context r10 = r10.getContext()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                int r9 = r9 / r1
                float r9 = (float) r9     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                int r9 = defpackage.agn.a(r10, r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r10 = r10.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r10.a(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                return
            L_0x025e:
                java.lang.String r2 = "tipPoiDetailData"
                boolean r2 = r0.equals(r2)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r2 == 0) goto L_0x02e8
                java.lang.String r0 = "requestType"
                int r0 = r9.optInt(r0, r7)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r2 = "poiDetailData"
                java.lang.String r3 = ""
                java.lang.String r9 = r9.optString(r2, r3)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r0 == 0) goto L_0x02e7
                boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r2 == 0) goto L_0x027e
                goto L_0x02e7
            L_0x027e:
                org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r2.<init>(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r0 != r6) goto L_0x02b2
                ekq r9 = new ekq     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.<init>()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                ekr r9 = defpackage.ekq.a(r2)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r10 = r10.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.util.List<com.autonavi.common.model.POI> r9 = r9.a     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.Object r9 = r9.get(r7)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r9 = (com.autonavi.common.model.POI) r9     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r10.p = r9     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r9 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r9 = r9.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bqk r9 = r9.c()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzd r9 = r9.d()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r10 = r10.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r10 = r10.p     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.a(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                return
            L_0x02b2:
                if (r0 != r1) goto L_0x02e6
                awf r9 = new awf     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r9.<init>()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                awg r9 = defpackage.awf.a(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r10 = r9.c     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r10 != 0) goto L_0x02d0
                java.lang.String r10 = r9.c     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r0 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r0 = r0.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r0 = r0.p     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r0.setName(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x02d0:
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r10 = r10.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r10 = r10.p     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r0 = r9.f     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r10.setAdCode(r0)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r10 = r10.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r10 = r10.p     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                java.lang.String r9 = r9.a     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r10.setCityCode(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x02e6:
                return
            L_0x02e7:
                return
            L_0x02e8:
                java.lang.String r10 = "tipChildClick"
                boolean r10 = r0.equals(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r10 == 0) goto L_0x0313
                java.lang.String r10 = "childIndex"
                java.lang.String r9 = r9.optString(r10)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                if (r10 == 0) goto L_0x02fe
                return
            L_0x02fe:
                int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r10 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bzo r10 = r10.k     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                bqk r10 = r10.c()     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler r0 = com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.this     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.map.search.manager.PoiDetailAjxLayerHandler$b r0 = r0.d     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                com.autonavi.common.model.POI r0 = r0.p     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
                r10.a(r0, r9)     // Catch:{ JSONException -> 0x031a, NumberFormatException -> 0x0315 }
            L_0x0313:
                return
            L_0x0314:
                return
            L_0x0315:
                r9 = move-exception
                r9.printStackTrace()
                return
            L_0x031a:
                r9 = move-exception
                r9.printStackTrace()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.AnonymousClass3.a(java.lang.String, com.autonavi.minimap.ajx3.core.JsFunctionCallback):void");
        }
    };

    public enum DetailLayerState {
        EXPAND,
        COLLAPSED
    }

    class TipRefreshCallback implements Callback<POI> {
        b a;

        public TipRefreshCallback(b bVar) {
            this.a = bVar;
        }

        public void callback(POI poi) {
            if (poi != null) {
                View view = this.a.b.getView();
                if (view != null && this.a.c != null) {
                    POI poi2 = this.a.p;
                    boolean z = false;
                    if (!(poi2 == null || poi2.getPoiExtra() == null)) {
                        z = poi2.getPoiExtra().containsKey(IOverlayManager.POI_EXTRA_FROM_FAV_ON_MAP);
                    }
                    if (z && poi.getPoiExtra() != null) {
                        poi.getPoiExtra().put(IOverlayManager.POI_EXTRA_FROM_FAV_ON_MAP, Boolean.TRUE);
                    }
                    this.a.g.putObject("favorite_poi", this.a.p);
                    this.a.p = poi;
                    b bVar = this.a;
                    SearchPoi searchPoi = (SearchPoi) bVar.p.as(SearchPoi.class);
                    if (!(bVar.k == null || searchPoi.getTemplateDataMap() == null || searchPoi.getTemplateData() == null)) {
                        searchPoi.getTemplateData().add(bVar.k);
                        searchPoi.getTemplateDataMap().put(Integer.valueOf(bVar.k.getId()), bVar.k);
                    }
                    if (this.a.o != 0) {
                        this.a.b.initData(PoiDetailAjxLayerHandler.this.e, poi, -1);
                        this.a.c.updatePoiData(PoiDetailAjxLayerHandler.this.e, (SearchPoi) poi.as(SearchPoi.class), -1);
                    } else {
                        this.a.b.initData(null, poi, -1);
                        this.a.c.updatePoiData(null, (SearchPoi) poi.as(SearchPoi.class), -1);
                    }
                    if (this.a.b instanceof SearchPoiTipWrapper) {
                        ((SearchPoiTipWrapper) this.a.b).a(PoiDetailAjxLayerHandler.this.f);
                        ((SearchPoiTipWrapper) this.a.b).a(this.a, PoiDetailAjxLayerHandler.this.m);
                    }
                    if (view != null && this.a.c != null) {
                        int a2 = (ccd.a(view) - agn.a(PoiDetailAjxLayerHandler.this.k.c().a().getContext(), 39.0f)) + ccd.a((View) this.a.c);
                        PoiDetailAjxLayerHandler.this.d.d.d().d = a2;
                        PoiDetailAjxLayerHandler.this.k.a(a2);
                        if (PoiDetailAjxLayerHandler.this.e == null) {
                            PoiDetailAjxLayerHandler.this.k.a(poi.getName());
                        }
                        PoiDetailAjxLayerHandler.this.k.c().d().a(poi);
                        refreshAjxViewWithWholeData();
                    }
                }
            }
        }

        public void error(Throwable th, boolean z) {
            if (PoiDetailAjxLayerHandler.this.k.c().a() != null && PoiDetailAjxLayerHandler.this.k.c().a().isAlive()) {
                refreshAjxViewWithWholeData();
            }
        }

        private void refreshAjxViewWithWholeData() {
            PageBundle a2 = bxz.a(this.a.g, SuperId.getInstance(), PoiDetailAjxLayerHandler.this.e, this.a.p, "poitip", PoiDetailAjxLayerHandler.this.k.c().i());
            boolean z = this.a.d.j() == PanelState.EXPANDED;
            a2.putString("is_whole", "1");
            PoiDetailAjxLayerHandler.this.a(a2);
            if (this.a.c != null) {
                PoiDetailAjxLayerHandler.this.d.t.b = this.a.c.getIndicatorInfo();
            }
            this.a.d.b(PoiDetailAjxLayerHandler.this.d.t.a(a2, PoiDetailAjxLayerHandler.this.k.c().a(), z, PoiDetailAjxLayerHandler.this.b));
            this.a.d.i();
        }
    }

    public interface a {
    }

    public static class b extends defpackage.cbl.a {
        public MapPointOverlayItem a;
        public ely<InfoliteResult> b;
        public SearchPoiIndicatorView c;
        public cap d;
        public PoiDetailViewForCQ e;
        public PoiDetailRequester f;
        public PageBundle g;
        public int h = -1;
        public float i = -1.0f;
        public float j = -1.0f;
        public PoiLayoutTemplate k;
        public bzp l;
        public int m = 0;

        public b(bzp bzp) {
            this.l = bzp;
        }

        public final void a(boolean z) {
            super.a(z);
            this.l.a(this);
        }

        public final void b() {
            super.b();
            if (this.d != null) {
                this.d.b();
                this.d.e();
            }
            if (this.f != null) {
                this.f.a();
            }
            bxb.b(this);
        }

        public final boolean a() {
            if (!super.a()) {
                this.l.m();
            }
            return false;
        }
    }

    public final String d() {
        return "invalid";
    }

    public final void g() {
    }

    public PoiDetailAjxLayerHandler(bzo bzo) {
        this.k = bzo;
        this.n = ags.a(bzo.c().a().getContext()).height();
        this.h = new bxx(this.k.c().a().getMapView());
    }

    public final void a(PageBundle pageBundle, InfoliteResult infoliteResult, POI poi) {
        Object object = pageBundle.getObject("tip_view");
        if (object != null) {
            if (object instanceof ely) {
                this.o = ((ely) object).getView();
            } else {
                this.o = (View) object;
            }
        }
        if (this.o != null) {
            this.o.setVisibility(0);
        }
        if (this.o != null && (this.o instanceof AbstractPoiDetailView)) {
            ((AbstractPoiDetailView) this.o).setOnSetPoiListener(null);
        }
        this.e = infoliteResult;
        if (infoliteResult != null) {
            JSONObject parseObject = JSON.parseObject(infoliteResult.originalJson);
            if (parseObject != null) {
                pageBundle.putString("new_detail_switch", parseObject.getString("new_detail_switch"));
            }
        }
        this.a = pageBundle.getInt("poi_detail_page_type", -1);
        boolean z = true;
        if (this.a != 1) {
            z = false;
        }
        this.b = z;
        if (this.a == -1) {
            this.a = b(poi);
        }
        this.k.e_().d();
    }

    public final b a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
        GeoPoint geoPoint;
        SuperId superId;
        String str;
        pageBundle.putString("is_whole", "1");
        pageBundle.putObject("key_tip_poi", a(poi));
        pageBundle.putInt("key_tip_type", 0);
        if (bcy.b(infoliteResult) && infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.R)) {
            pageBundle.putString("key_gsid", infoliteResult.searchInfo.a.R);
        }
        if (bcy.b(infoliteResult) && infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.O)) {
            pageBundle.putString("key_queryIntentCate", infoliteResult.searchInfo.a.O);
        }
        pageBundle.putInt("key_tip_data_source", 1);
        if (poi.getPoiExtra().containsKey("requestPoiData")) {
            if (this.k.c().c() != null) {
                POI c2 = this.k.c().c();
                if (c2 != null) {
                    geoPoint = c2.getPoint();
                    pageBundle.putString("key_centerpoint_longitude", String.valueOf(geoPoint.getLongitude()));
                    pageBundle.putString("key_centerpoint_latitude", String.valueOf(geoPoint.getLatitude()));
                } else {
                    geoPoint = null;
                }
                if (geoPoint == null) {
                    superId = null;
                } else {
                    superId = SuperId.getInstance();
                }
                if (superId == null) {
                    str = "h";
                } else {
                    str = superId.getScenceId();
                }
                pageBundle.putString("key_centerpoint_superid", str);
            }
            pageBundle.putInt("key_tip_request_type", 1);
        } else {
            pageBundle.putInt("key_tip_request_type", 0);
        }
        a(pageBundle, poi, q(), false, (String) "type_search_result");
        this.k.a(true);
        if (poi.getPoiExtra().containsKey("key_result_page_type")) {
            Serializable serializable = poi.getPoiExtra().get("key_result_page_type");
            if (serializable != null) {
                this.d.o = ((Integer) serializable).intValue();
            }
        } else {
            this.d.o = 6;
            pageBundle.putInt("key_tip_data_source", 1);
        }
        this.d.b = new SearchPoiTipWrapper();
        ((SearchPoiTipWrapper) this.d.b).a();
        this.d.b.initData(infoliteResult, this.d.p, 3);
        if (this.d.p.getPoiExtra().containsKey("requestPoiData")) {
            a(this.d, (SearchPoi) poi.as(SearchPoi.class));
        }
        this.d.c.updatePoiData(infoliteResult, (SearchPoi) poi.as(SearchPoi.class), -1);
        if (this.k.c().f()) {
            cbl e_ = this.k.e_();
            if (e_ != null) {
                e_.a((ViewGroup) null, e_.b());
            }
        }
        a(pageBundle, this.d.b.getView(), (View) this.d.c, false, q() != null);
        l();
        this.d.d.a((defpackage.cap.a) new defpackage.cap.a() {
            public final void a() {
                if (bcy.d(PoiDetailAjxLayerHandler.this.e) && PoiDetailAjxLayerHandler.this.d.p != null) {
                    PoiDetailAjxLayerHandler.this.h.a(PoiDetailAjxLayerHandler.this.e.searchInfo.a.O, bcy.l(PoiDetailAjxLayerHandler.this.e), PoiDetailAjxLayerHandler.this.d.p.getId());
                }
            }
        });
        this.k.c().a((SearchPoi) poi.as(SearchPoi.class));
        return this.d;
    }

    public final b a(PageBundle pageBundle, POI poi) {
        pageBundle.putString("is_whole", "1");
        pageBundle.putObject("key_tip_poi", a(poi));
        a(pageBundle, poi, q(), false, this.o instanceof AbstractPoiDetailView ? "type_press_long" : "type_press_short");
        this.k.a(true);
        if (this.o instanceof AbstractPoiDetailView) {
            pageBundle.putString("point_type", "2");
            ((AbstractPoiDetailView) this.o).setOnSetPoiListener(new OnSetPoiListener() {
                public final void onSetPoi(POI poi) {
                    if (poi != null && !DriveUtil.MAP_PLACE_DES_3.equals(poi.getName())) {
                        Serializable serializable = poi.getPoiExtra().get("isWhole");
                        boolean z = false;
                        boolean z2 = serializable != null && ((Boolean) serializable).booleanValue();
                        if (!poi.getPoiExtra().containsKey("isWhole") || !z2) {
                            PageBundle a2 = bxw.a(poi);
                            a2.putString("point_type", "2");
                            a2.putString("is_whole", "1");
                            if (PoiDetailAjxLayerHandler.this.d.d.j() != PanelState.COLLAPSED) {
                                z = true;
                            }
                            PoiDetailAjxLayerHandler.this.d.t.b = PoiDetailAjxLayerHandler.this.d.c.getIndicatorInfo();
                            PoiDetailAjxLayerHandler.this.d.d.b(PoiDetailAjxLayerHandler.this.d.t.a(a2, PoiDetailAjxLayerHandler.this.k.c().a(), z, PoiDetailAjxLayerHandler.this.b));
                            PoiDetailAjxLayerHandler.this.a(a2);
                            PoiDetailAjxLayerHandler.this.d.d.i();
                            poi.getPoiExtra().put("isWhole", Boolean.TRUE);
                            PoiDetailAjxLayerHandler.this.d.p = poi;
                        }
                    }
                }
            });
        }
        if ((poi instanceof GpsPOI) || (this.o instanceof GpsTipView)) {
            this.k.a(this.k.c().a().getContext().getString(R.string.LocationMe));
            pageBundle.putString("point_type", "1");
            this.k.c().d().b();
            poi.setName("我的位置");
        } else {
            this.d.a = new MapPointOverlayItem(this.d.p.getPoint(), R.drawable.b_poi_hl);
            this.k.c().d().a(this.d.a);
        }
        this.h.a(this.o, this.d, this.e == null);
        this.d.o = 0;
        a(pageBundle, this.o, (View) this.d.c, false, q() != null);
        if (!(this.o instanceof AbstractPoiDetailView)) {
            this.k.c().a((GLGeoPoint) poi.getPoint());
        }
        return this.d;
    }

    public final b b(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
        SuperId superId;
        String str;
        this.k.a(true);
        GeoPoint geoPoint = (GeoPoint) pageBundle.getObject("key_tip_schema_center_point");
        pageBundle.putObject("key_tip_poi", a(poi));
        pageBundle.putInt("key_tip_type", 0);
        pageBundle.putInt("key_tip_request_type", 1);
        if (geoPoint != null) {
            pageBundle.putString("key_centerpoint_longitude", String.valueOf(geoPoint.getLongitude()));
            pageBundle.putString("key_centerpoint_latitude", String.valueOf(geoPoint.getLatitude()));
        }
        if (geoPoint == null) {
            superId = null;
        } else {
            superId = SuperId.getInstance();
        }
        if (superId == null) {
            str = "h";
        } else {
            str = superId.getScenceId();
        }
        pageBundle.putString("key_centerpoint_superid", str);
        if (bcy.b(infoliteResult) && infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.R)) {
            pageBundle.putString("key_gsid", infoliteResult.searchInfo.a.R);
        }
        if (bcy.b(infoliteResult) && infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.O)) {
            pageBundle.putString("key_queryIntentCate", infoliteResult.searchInfo.a.O);
        }
        a(pageBundle, poi, (b) null, false, (String) "type_default");
        this.d.o = 4;
        this.d.b = new SearchPoiTipWrapper();
        this.d.b.initData(infoliteResult, this.d.p, 0);
        this.d.c.updatePoiData(infoliteResult, (SearchPoi) poi.as(SearchPoi.class), -1);
        this.i = pageBundle.getString("key_floor");
        this.j = pageBundle.getInt("floor");
        this.d.m = pageBundle.getInt("floor");
        n();
        a(pageBundle, this.d.b.getView(), (View) this.d.c, false, false);
        if (this.e == null) {
            this.k.a(this.d.p.getName());
        }
        this.d.a = new MapPointOverlayItem(this.d.p.getPoint(), R.drawable.b_poi_hl);
        this.k.c().d().a(this.d.a);
        return this.d;
    }

    public final b a(InfoliteResult infoliteResult, PageBundle pageBundle) {
        if (!bcy.b(infoliteResult)) {
            return null;
        }
        POI poi = !infoliteResult.searchInfo.l.isEmpty() ? infoliteResult.searchInfo.l.get(0) : null;
        if (poi == null) {
            return null;
        }
        if (infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.R)) {
            pageBundle.putString("key_gsid", infoliteResult.searchInfo.a.R);
        }
        if (infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.O)) {
            pageBundle.putString("key_queryIntentCate", infoliteResult.searchInfo.a.O);
        }
        pageBundle.putObject("POI", poi);
        pageBundle.putString("is_whole", "1");
        pageBundle.putInt("key_tip_request_type", 0);
        pageBundle.putObject("key_tip_poi", a(poi));
        pageBundle.putInt("key_tip_type", 0);
        pageBundle.putInt("key_is_city_card", 1);
        pageBundle.putInt("key_tip_data_source", 1);
        a(pageBundle, poi, q(), false, (String) "type_default");
        this.d.o = 3;
        poi.getPoiExtra().put("key_result_page_type", Integer.valueOf(this.d.o));
        this.d.b = new SearchPoiTipWrapper(Type.City_Card, this.l);
        ((SearchPoiTipWrapper) this.d.b).a();
        this.d.b.initData(infoliteResult, this.d.p, 1);
        this.d.c.updatePoiData(infoliteResult, (SearchPoi) poi.as(SearchPoi.class), -1);
        View view = this.d.b.getView();
        this.k.a(true);
        a(pageBundle, view, (View) this.d.c, false, q() != null);
        l();
        this.d.d.a((defpackage.cap.a) new defpackage.cap.a() {
            public final void a() {
                if (bcy.d(PoiDetailAjxLayerHandler.this.e)) {
                    PoiDetailAjxLayerHandler.this.h.a(PoiDetailAjxLayerHandler.this.e.searchInfo.a.O, bcy.l(PoiDetailAjxLayerHandler.this.e), PoiDetailAjxLayerHandler.this.d.p.getId());
                }
            }
        });
        return this.d;
    }

    static org.json.JSONObject a(POI poi) {
        if (poi instanceof SearchPoi) {
            SearchPoi searchPoi = (SearchPoi) poi;
            if (!TextUtils.isEmpty((String) searchPoi.getPoiExtra().get("original"))) {
                try {
                    return new org.json.JSONObject((String) searchPoi.getPoiExtra().get("original"));
                } catch (JSONException unused) {
                }
            }
            return bnx.b(poi);
        }
        org.json.JSONObject b2 = bnx.b(poi);
        if (!(poi instanceof ChildStationPoiData)) {
            return b2;
        }
        ChildStationPoiData childStationPoiData = (ChildStationPoiData) poi.as(ChildStationPoiData.class);
        String str = (String) childStationPoiData.getPoiExtra().get("bus_alias");
        String str2 = (String) childStationPoiData.getPoiExtra().get("lineKey");
        try {
            b2.put("bus_alias", str);
            b2.put("lineKey", str2);
            return b2;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return b2;
        }
    }

    private b q() {
        defpackage.cbl.a a2 = this.k.e_().a();
        if (a2 == null || !(a2 instanceof b)) {
            return null;
        }
        return (b) a2;
    }

    /* access modifiers changed from: 0000 */
    public final void a(PageBundle pageBundle, POI poi, b bVar, boolean z, String str) {
        if (bVar == null) {
            bVar = new b(this);
            bVar.d = new cat();
            bVar.t = new bxw(poi);
            pageBundle.putInt("key_tip_from", 2);
            pageBundle.putFloat("key_search_headerb", this.k.i());
            bVar.c = new SearchPoiIndicatorView(DoNotUseTool.getContext(), str);
            bVar.t.b = bVar.c.getIndicatorInfo();
            bVar.d.b(bVar.t.a(pageBundle, this.k.c().a(), z, this.b));
            bVar.d.a(this.p);
            bVar.q = bVar.d.a(this.k.c().a(), this.k.b(), z, pageBundle);
            bVar.w = true;
        } else {
            bVar.w = false;
        }
        this.d = bVar;
        this.d.p = poi;
        if (this.d.p != null && ((SearchPoi) this.d.p.as(SearchPoi.class)).getTemplateDataMap() == null) {
            bxz.a(this.k.c().a().getContext(), this.d.p);
        }
        Object object = pageBundle.getObject("tip_view");
        if (object instanceof ely) {
            this.d.b = (ely) object;
        }
        this.d.g = pageBundle;
        this.d.d.a((defpackage.cap.a) null);
    }

    /* access modifiers changed from: 0000 */
    public final void a(PageBundle pageBundle, View view, View view2, boolean z, boolean z2) {
        if (this.d.p != null) {
            this.c = z ? "full" : ModulePoi.TIPS;
            this.g = z ? com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.EXPAND : com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.COLLAPSED;
            if (view2 instanceof SearchPoiIndicatorView) {
                this.d.t.b = ((SearchPoiIndicatorView) view2).getIndicatorInfo();
            }
            if (!this.d.w) {
                this.d.d.b(this.d.t.a(pageBundle, this.k.c().a(), z, this.b));
            }
            a(pageBundle);
            if (!z2) {
                new AjxPageResultExecutor() {
                    public final void onFragmentResult(AbstractBasePage abstractBasePage, int i, ResultType resultType, PageBundle pageBundle, JsAdapter jsAdapter) {
                        PoiDetailAjxLayerHandler.this.d.t.onFragmentResult(abstractBasePage, i, resultType, pageBundle, jsAdapter);
                    }
                };
                if (z) {
                    view.setVisibility(4);
                }
                a(view, view2);
            } else {
                this.d.d.i();
            }
            this.d.d.a((BackCallback) new BackCallback() {
                public final void back(Object obj, String str) {
                    AMapLog.d("PoiDetail", "js back");
                    if (!PoiDetailAjxLayerHandler.this.o()) {
                        PoiDetailAjxLayerHandler.this.k.c().a().finish();
                    }
                }
            });
        }
    }

    private static void a(b bVar, SearchPoi searchPoi) {
        if (searchPoi.getTemplateDataMap() != null && bVar != null) {
            bVar.k = (PoiLayoutTemplate) searchPoi.getTemplateDataMap().get(Integer.valueOf(2038));
        }
    }

    private void a(View view, View view2) {
        if (view != null && view2 != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            if (this.d.b instanceof SearchPoiTipWrapper) {
                ((SearchPoiTipWrapper) this.d.b).a(this.f);
                ((SearchPoiTipWrapper) this.d.b).a(this.d, this.m);
            }
            this.d.d.a(view, view2);
            this.k.a(this.d.d.d().d);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void l() {
        HashMap<String, Serializable> poiExtra = this.d.p.getPoiExtra();
        if (!(this.a == 0 || this.e == null || poiExtra == null)) {
            if (this.e.searchInfo.w != 1) {
                poiExtra.put("detail_fragment_key_word", this.e.mWrapper.keywords);
                poiExtra.put("detail_data_from_page", Integer.valueOf(this.k.c().i()));
            } else if (poiExtra.containsKey("detail_fragment_key_word")) {
                poiExtra.remove("detail_fragment_key_word");
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(PageBundle pageBundle) {
        if (pageBundle != null && this.d.p != null) {
            POI poi = (POI) pageBundle.getObject("POI");
            if (!(this.a != 0 || poi == null || poi.getPoiExtra() == null)) {
                HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
                if (poiExtra.containsKey("detail_fragment_key_word")) {
                    poiExtra.remove("detail_fragment_key_word");
                }
            }
            if (this.d.d.h() == null) {
                this.d.d.b(pageBundle);
            } else {
                this.d.d.h().setBundle(pageBundle);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void n() {
        cde suspendManager = this.k.c().a().getSuspendManager();
        if (elc.b) {
            if (!(suspendManager == null || suspendManager.c().a() == null)) {
                if (!TextUtils.isEmpty(this.i)) {
                    suspendManager.c().b(this.i);
                } else if (this.j != Integer.MAX_VALUE) {
                    suspendManager.c().b(this.j);
                }
            }
        } else if (!(suspendManager == null || suspendManager.c().a() != null || suspendManager.c().b() == null)) {
            if (!TextUtils.isEmpty(this.i)) {
                suspendManager.c().a((String) "", this.i, true);
            } else if (this.j != Integer.MAX_VALUE) {
                suspendManager.c().a((String) "", this.j, true);
            }
        }
    }

    private void a(float f2) {
        bty mapView = this.k.c().a().getMapManager().getMapView();
        b p2 = p();
        if (p2 != null && p2.o != 0) {
            if (TextUtils.equals(this.c, "full")) {
                p2.j = mapView.v();
                this.k.c().h();
            } else if (TextUtils.equals(this.c, ModulePoi.TIPS)) {
                p2.j = -1.0f;
                this.k.c().h();
            }
            if (f2 != -1.0f) {
                mapView.d(f2);
            }
            GeoPoint geoPoint = null;
            if (this.k.c().e() != null) {
                PointOverlayItem h2 = this.k.c().e().h();
                if (h2 != null) {
                    geoPoint = h2.getGeoPoint();
                }
                if (this.k.c().d().b != null) {
                    geoPoint = this.k.c().d().b.getGeoPoint();
                }
                if (geoPoint != null) {
                    this.k.c().a((GLGeoPoint) geoPoint);
                }
            }
        }
    }

    private int b(POI poi) {
        if (this.o != null) {
            return 0;
        }
        return poi != null ? 1 : -1;
    }

    public final void i() {
        b p2 = p();
        if (p2 != null && p2.d != null && p2.t != null) {
            p2.d.a(p2.t.a);
            p2.t.a = null;
        }
    }

    public final void j() {
        if (!(this.d == null || this.d.d == null)) {
            this.d.d.a();
        }
        this.k.c().a().getMapView().b(ags.a(this.k.c().a().getContext()).width() / 2, ags.a(this.k.c().a().getContext()).height() / 2);
    }

    public final void k() {
        if (this.d != null) {
            this.d.j = -1.0f;
        }
        if (!(this.d == null || this.d.d == null)) {
            this.d.d.a((BackCallback) null);
            this.d.d.b();
            this.d.d.e();
        }
        if (this.o != null) {
            this.o.setVisibility(8);
        }
        if (!(this.d == null || this.d.b == null || !(this.d.b instanceof SearchPoiTipWrapper))) {
            ((SearchPoiTipWrapper) this.d.b).a(null);
        }
        if (this.o != null && (this.o instanceof AbstractPoiDetailView)) {
            ((AbstractPoiDetailView) this.o).setOnSetPoiListener(null);
        }
        this.k.e_().d();
    }

    public final boolean a() {
        b p2 = p();
        if (p2 == null || p2.d == null || p2.d.j() != PanelState.EXPANDED) {
            return o();
        }
        p2.d.c();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final boolean o() {
        defpackage.cbl.a a2 = this.k.e_().a();
        if (a2 == null || a2.o != 0) {
            return this.k.e_().a(this.k.b()) != null;
        }
        this.k.e_().b(this.k.b());
        this.k.a(true);
        this.k.c().b(0);
        this.k.a(-2);
        return false;
    }

    public final void a(b bVar) {
        boolean z = false;
        if (!bVar.r) {
            this.k.a(true);
            this.k.c().b(0);
            this.k.a(-2);
        }
        if (bVar.o == 0) {
            this.k.h_();
            this.c = ModulePoi.TIPS;
            this.g = com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.COLLAPSED;
            return;
        }
        if (bVar.d.j() == PanelState.EXPANDED) {
            this.c = "full";
            this.g = com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.EXPAND;
            org.json.JSONObject jSONObject = new org.json.JSONObject();
            org.json.JSONObject jSONObject2 = new org.json.JSONObject();
            try {
                jSONObject2.put("state", "full");
                jSONObject.put("from", 2);
                jSONObject.put("action", "detailStateDidChange");
                jSONObject.put("param", jSONObject2);
                this.p.a(jSONObject.toString(), null);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        } else if (bVar.d.j() == PanelState.COLLAPSED) {
            this.c = ModulePoi.TIPS;
            this.g = com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState.COLLAPSED;
            org.json.JSONObject jSONObject3 = new org.json.JSONObject();
            org.json.JSONObject jSONObject4 = new org.json.JSONObject();
            try {
                jSONObject4.put("state", ModulePoi.TIPS);
                jSONObject3.put("from", 2);
                jSONObject3.put("action", "detailStateDidChange");
                jSONObject3.put("param", jSONObject4);
                this.p.a(jSONObject3.toString(), null);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        if (bVar.s != null) {
            this.k.c().a(bVar.s);
            if (bVar.d.j() == PanelState.EXPANDED) {
                b p2 = p();
                if (p2 != null) {
                    a(p2.i);
                }
            }
        } else if (bVar.a != null) {
            this.k.c().a(bVar);
        }
        this.k.a(bVar.d.d().d);
        bzo bzo = this.k;
        if (bVar.d.j() != PanelState.EXPANDED) {
            z = true;
        }
        bzo.a(z);
        if (this.k.c().b() && bVar.p != null) {
            this.k.a(bVar.p.getName());
        }
    }

    public final void m() {
        this.k.c().a().finish();
    }

    public final Point b() {
        int i2;
        int height = ags.a(this.k.c().a().getContext()).height();
        int width = ags.a(this.k.c().a().getContext()).width();
        if (height < width) {
            height = ags.a(this.k.c().a().getContext()).width();
            width = ags.a(this.k.c().a().getContext()).height();
        }
        int bottom = this.k.f_().getBottom();
        int top = ((this.k.f().getTop() - bottom) / 2) + bottom;
        if (TextUtils.equals(this.c, "full")) {
            b p2 = p();
            if (p2 != null) {
                String type = this.d.p != null ? this.d.p.getType() : "";
                if (p2.h != -1) {
                    i2 = p2.h;
                } else {
                    i2 = ekt.a(this.k.c().a().getContext(), type);
                }
                p2.h = i2;
                top = (p2.h / 2) + 20;
            }
        } else if (TextUtils.equals(this.c, ModulePoi.TIPS)) {
            top = ((int) (((float) (height - this.d.d.d().d)) + this.k.i())) / 2;
        }
        return new Point(width / 2, top);
    }

    public final void a(AbstractBasePage abstractBasePage, int i2, ResultType resultType, PageBundle pageBundle) {
        b p2 = p();
        if (p2 != null && p2.d != null && p2.t != null) {
            JsAdapter h2 = p2.d.h();
            if (h2 != null) {
                p2.t.onFragmentResult(abstractBasePage, i2, resultType, pageBundle, h2);
            }
        }
    }

    public final com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState c() {
        return this.g;
    }

    public final void a(boolean z) {
        b p2 = p();
        if (p2 != null && p2.q != null) {
            p2.q.setVisibility(z ? 0 : 8);
            p2.r = z;
            if (z) {
                this.k.a(p2.d.d().d);
            }
        }
    }

    public final boolean e() {
        return this.d.d != null && this.d.d.j() == PanelState.EXPANDED;
    }

    public final void f() {
        b p2 = p();
        if (p2 != null) {
            a(p2.j);
        }
    }

    public final void a(cbr cbr) {
        this.f = cbr;
    }

    public final void a(ViewGroup viewGroup) {
        cbl e_ = this.k.e_();
        if (e_ != null && viewGroup != null) {
            b bVar = new b(this);
            bVar.n = 4;
            bVar.o = 0;
            e_.a((defpackage.cbl.a) bVar, viewGroup);
        }
    }

    public final int h() {
        b p2 = p();
        if (p() == null || p2.b == null) {
            return this.n - this.k.f_().getBottom();
        }
        return (this.n - p2.b.getView().getHeight()) - this.k.f_().getBottom();
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final b p() {
        defpackage.cbl.a a2 = this.k.e_().a();
        if (a2 instanceof b) {
            return (b) a2;
        }
        return null;
    }

    static /* synthetic */ void a(PoiDetailAjxLayerHandler poiDetailAjxLayerHandler) {
        poiDetailAjxLayerHandler.k.c().b(8);
        poiDetailAjxLayerHandler.k.c().a_(8);
        poiDetailAjxLayerHandler.k.c().c(8);
        poiDetailAjxLayerHandler.k.c().g();
        if (poiDetailAjxLayerHandler.h != null && bxx.a(poiDetailAjxLayerHandler.d.o)) {
            b p2 = poiDetailAjxLayerHandler.p();
            if (p2 != null && p2.p != null) {
                bxx bxx = poiDetailAjxLayerHandler.h;
                String k2 = bcy.k(poiDetailAjxLayerHandler.e);
                String l2 = bcy.l(poiDetailAjxLayerHandler.e);
                String id = p2.p.getId();
                if (!bxx.a && bxx.b) {
                    org.json.JSONObject jSONObject = new org.json.JSONObject();
                    try {
                        jSONObject.put("text", k2);
                        jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, id);
                        jSONObject.put("gsid", l2);
                        LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, LogConstant.HONGBAO_MAIL_DESTROY, jSONObject);
                    } catch (JSONException unused) {
                    }
                    bxx.b = false;
                }
            }
        }
    }

    static /* synthetic */ GeoPoint b(PoiDetailAjxLayerHandler poiDetailAjxLayerHandler) {
        GeoPoint geoPoint = null;
        if (poiDetailAjxLayerHandler.k.c().e() == null) {
            return null;
        }
        PointOverlayItem h2 = poiDetailAjxLayerHandler.k.c().e().h();
        if (h2 != null) {
            geoPoint = h2.getGeoPoint();
        }
        if (poiDetailAjxLayerHandler.k.c().d().b != null) {
            geoPoint = poiDetailAjxLayerHandler.k.c().d().b.getGeoPoint();
        }
        return geoPoint;
    }
}
