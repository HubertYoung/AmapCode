package defpackage;

import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.life.inter.IOpenLifeFragment;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.search.inter.ISearchResultController;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: byq reason: default package */
/* compiled from: SearchCategoryAction */
public class byq extends wb {
    public final boolean b() {
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void a(android.content.Intent r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            java.lang.String r0 = "category"
            java.lang.String r0 = r7.getStringExtra(r0)     // Catch:{ all -> 0x006c }
            java.lang.String r1 = "POI"
            java.io.Serializable r7 = r7.getSerializableExtra(r1)     // Catch:{ all -> 0x006c }
            com.autonavi.common.model.POI r7 = (com.autonavi.common.model.POI) r7     // Catch:{ all -> 0x006c }
            if (r7 != 0) goto L_0x0016
            c()     // Catch:{ all -> 0x006c }
            monitor-exit(r6)
            return
        L_0x0016:
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x006c }
            if (r1 == 0) goto L_0x001e
            monitor-exit(r6)
            return
        L_0x001e:
            android.graphics.Rect r1 = new android.graphics.Rect     // Catch:{ all -> 0x006c }
            com.autonavi.common.model.GeoPoint r2 = r7.getPoint()     // Catch:{ all -> 0x006c }
            int r2 = r2.x     // Catch:{ all -> 0x006c }
            int r2 = r2 + -100
            com.autonavi.common.model.GeoPoint r3 = r7.getPoint()     // Catch:{ all -> 0x006c }
            int r3 = r3.y     // Catch:{ all -> 0x006c }
            int r3 = r3 + -100
            com.autonavi.common.model.GeoPoint r4 = r7.getPoint()     // Catch:{ all -> 0x006c }
            int r4 = r4.x     // Catch:{ all -> 0x006c }
            int r4 = r4 + 100
            com.autonavi.common.model.GeoPoint r5 = r7.getPoint()     // Catch:{ all -> 0x006c }
            int r5 = r5.y     // Catch:{ all -> 0x006c }
            int r5 = r5 + 100
            r1.<init>(r2, r3, r4, r5)     // Catch:{ all -> 0x006c }
            com.autonavi.minimap.controller.AppManager r2 = com.autonavi.minimap.controller.AppManager.getInstance()     // Catch:{ all -> 0x006c }
            java.lang.String r2 = r2.getUserLocInfo()     // Catch:{ all -> 0x006c }
            com.autonavi.common.model.GeoPoint r7 = r7.getPoint()     // Catch:{ all -> 0x006c }
            com.autonavi.bl.search.InfoliteParam r7 = defpackage.bbv.a(r2, r0, r7, r1)     // Catch:{ all -> 0x006c }
            java.lang.Class<com.autonavi.minimap.search.inter.ISearchResultController> r2 = com.autonavi.minimap.search.inter.ISearchResultController.class
            java.lang.Object r2 = defpackage.ank.a(r2)     // Catch:{ all -> 0x006c }
            com.autonavi.minimap.search.inter.ISearchResultController r2 = (com.autonavi.minimap.search.inter.ISearchResultController) r2     // Catch:{ all -> 0x006c }
            if (r2 == 0) goto L_0x006a
            ekv r3 = new ekv     // Catch:{ all -> 0x006c }
            r3.<init>()     // Catch:{ all -> 0x006c }
            aea r0 = r2.setSearchRect(r1, r0)     // Catch:{ all -> 0x006c }
            r1 = 1
            r3.a(r7, r1, r0)     // Catch:{ all -> 0x006c }
        L_0x006a:
            monitor-exit(r6)
            return
        L_0x006c:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.byq.a(android.content.Intent):void");
    }

    private static void c() {
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.param_error));
    }

    private synchronized void a(String str, String str2) {
        InfoliteParam a = bbv.a(AppManager.getInstance().getUserLocInfo(), str, (Rect) null);
        a.sugadcode = str2;
        a.search_operate = 0;
        ISearchResultController iSearchResultController = (ISearchResultController) ank.a(ISearchResultController.class);
        if (iSearchResultController != null) {
            new ekv().a(a, 1, iSearchResultController.setSearchResultListener(str, -1, true, false));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0064, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void a(java.lang.String r8, com.autonavi.common.model.POI r9, android.graphics.Rect r10) {
        /*
            r7 = this;
            monitor-enter(r7)
            if (r9 != 0) goto L_0x000a
            c()     // Catch:{ all -> 0x0008 }
            monitor-exit(r7)
            return
        L_0x0008:
            r8 = move-exception
            goto L_0x0065
        L_0x000a:
            boolean r0 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x0008 }
            if (r0 == 0) goto L_0x0012
            monitor-exit(r7)
            return
        L_0x0012:
            com.autonavi.common.SuperId r0 = com.autonavi.common.SuperId.getInstance()     // Catch:{ all -> 0x0008 }
            r0.reset()     // Catch:{ all -> 0x0008 }
            com.autonavi.common.SuperId r0 = com.autonavi.common.SuperId.getInstance()     // Catch:{ all -> 0x0008 }
            java.lang.String r1 = "b"
            r0.setBit1(r1)     // Catch:{ all -> 0x0008 }
            com.autonavi.common.SuperId r0 = com.autonavi.common.SuperId.getInstance()     // Catch:{ all -> 0x0008 }
            java.lang.String r1 = "11"
            r0.setBit2(r1)     // Catch:{ all -> 0x0008 }
            com.autonavi.minimap.controller.AppManager r0 = com.autonavi.minimap.controller.AppManager.getInstance()     // Catch:{ all -> 0x0008 }
            java.lang.String r0 = r0.getUserLocInfo()     // Catch:{ all -> 0x0008 }
            com.autonavi.common.model.GeoPoint r1 = r9.getPoint()     // Catch:{ all -> 0x0008 }
            com.autonavi.bl.search.InfoliteParam r0 = defpackage.bbv.a(r0, r8, r1, r10)     // Catch:{ all -> 0x0008 }
            java.lang.Class<com.autonavi.minimap.search.inter.ISearchResultController> r1 = com.autonavi.minimap.search.inter.ISearchResultController.class
            java.lang.Object r1 = defpackage.ank.a(r1)     // Catch:{ all -> 0x0008 }
            com.autonavi.minimap.search.inter.ISearchResultController r1 = (com.autonavi.minimap.search.inter.ISearchResultController) r1     // Catch:{ all -> 0x0008 }
            if (r1 == 0) goto L_0x0063
            ekv r2 = new ekv     // Catch:{ all -> 0x0008 }
            r2.<init>()     // Catch:{ all -> 0x0008 }
            bvt r3 = new bvt     // Catch:{ all -> 0x0008 }
            r3.<init>()     // Catch:{ all -> 0x0008 }
            bwx r4 = new bwx     // Catch:{ all -> 0x0008 }
            r5 = 2
            r6 = 0
            r4.<init>(r8, r5, r6)     // Catch:{ all -> 0x0008 }
            r4.r = r9     // Catch:{ all -> 0x0008 }
            r3.e = r8     // Catch:{ all -> 0x0008 }
            r3.d = r4     // Catch:{ all -> 0x0008 }
            r1.setSearchRect(r3, r10)     // Catch:{ all -> 0x0008 }
            r8 = 1
            r2.a(r0, r8, r3)     // Catch:{ all -> 0x0008 }
        L_0x0063:
            monitor-exit(r7)
            return
        L_0x0065:
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.byq.a(java.lang.String, com.autonavi.common.model.POI, android.graphics.Rect):void");
    }

    public final void b(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            try {
                avw avw = new avw();
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject optJSONObject = jSONObject.optJSONObject("poiInfo");
                JSONObject optJSONObject2 = jSONObject.optJSONObject("params");
                if (optJSONObject2 != null && optJSONObject2.length() > 0) {
                    int length = optJSONObject2.length();
                    for (int i = 0; i < length; i++) {
                        Iterator<String> keys = optJSONObject2.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            hashMap.put(next, (String) optJSONObject2.get(next));
                        }
                    }
                    avw.a = hashMap;
                }
                POI createPOI = POIFactory.createPOI();
                if (optJSONObject != null) {
                    createPOI = kv.a(optJSONObject.toString());
                }
                String optString = jSONObject.optString("serviceType");
                String optString2 = jSONObject.optString("category");
                String optString3 = jSONObject.optString("keywords");
                String optString4 = jSONObject.optString("listType");
                if (TextUtils.isEmpty(optString)) {
                    String optString5 = jSONObject.optString("queryType", "");
                    String string = jSONObject.getString("category");
                    if (optJSONObject != null) {
                        GeoPoint point = createPOI.getPoint();
                        a(string, createPOI, new Rect(point.x - 100, point.y - 100, point.x + 100, point.y + 100));
                    } else if (hashMap.containsKey(AutoJsonUtils.JSON_ADCODE)) {
                        a(string, hashMap.get(AutoJsonUtils.JSON_ADCODE));
                    } else if (optString5.equals("IDQ")) {
                        InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), optString3, string);
                        a2.search_operate = 0;
                        if (optJSONObject2 != null) {
                            a2.transfer_pdheatmap = optJSONObject2.get("transfer_pdheatmap").toString();
                        }
                        ISearchResultController iSearchResultController = (ISearchResultController) ank.a(ISearchResultController.class);
                        if (iSearchResultController != null) {
                            new ekv().a(a2, 1, iSearchResultController.getSearchCallBackEx(optString3));
                        }
                    } else {
                        TipItem tipItem = new TipItem();
                        tipItem.name = optString2;
                        tipItem.needSearch = false;
                        GeoPoint point2 = createPOI.getPoint();
                        InfoliteParam a3 = bbv.a(AppManager.getInstance().getUserLocInfo(), string, new Rect(point2.x - 100, point2.y - 100, point2.x + 100, point2.y + 100));
                        ISearchResultController iSearchResultController2 = (ISearchResultController) ank.a(ISearchResultController.class);
                        if (iSearchResultController2 != null) {
                            new ekv().a(a3, 1, iSearchResultController2.setSearchResultListener(string, -1, true, false));
                        }
                    }
                } else if (optString.equals("groupbuy")) {
                    if (createPOI == null || createPOI.getPoint() == null) {
                        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.param_error));
                        return;
                    }
                    IOpenLifeFragment iOpenLifeFragment = (IOpenLifeFragment) ank.a(IOpenLifeFragment.class);
                    if (iOpenLifeFragment != null) {
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putSerializable("geoPoint", createPOI.getPoint());
                        pageBundle.putInt("searchType", 136);
                        pageBundle.putString("classifyData", optString2);
                        pageBundle.putString("listType", optString4);
                        pageBundle.putString(WidgetType.ACTIVITY, avw.toString());
                        pageBundle.putObject("params", hashMap);
                        iOpenLifeFragment.a(a.mPageContext, 8, pageBundle);
                    }
                } else if ("brandList".equals(optString)) {
                    String string2 = jSONObject.getString("category");
                    Intent intent = new Intent();
                    intent.putExtra("category", string2);
                    intent.putExtra("POI", createPOI);
                    a(intent);
                } else {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.param_error));
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
