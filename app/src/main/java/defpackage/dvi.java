package defpackage;

import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.bundle.busnavi.api.IBusNaviPage;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.minimap.route.bus.extbus.page.ExtBusMapPage;
import com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailPage;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType;
import org.json.JSONObject;

/* renamed from: dvi reason: default package */
/* compiled from: OpenBusNaviPageImpl */
public final class dvi implements IBusNaviPage {

    /* renamed from: dvi$a */
    /* compiled from: OpenBusNaviPageImpl */
    static class a {
        static dvi a = new dvi();
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        switch (i) {
            case 1:
                return ExtBusMapPage.class;
            case 2:
                return BusNaviDetailPage.class;
            default:
                return null;
        }
    }

    public final void a(int i, PageBundle pageBundle) {
        Class<? extends AbstractBasePage> a2 = a(i);
        if (a2 != null) {
            AMapPageUtil.getPageContext().startPage(a2, pageBundle);
        }
    }

    public final void a(final String str) {
        if (!TextUtils.isEmpty(str)) {
            ebr.a(false).post(new Runnable() {
                public final void run() {
                    IBusRouteResult b2 = dvi.b(str);
                    if (b2 != null) {
                        dvi dvi = dvi.this;
                        String str = str;
                        if (!TextUtils.isEmpty(str) && b2 != null) {
                            aho.a(new Runnable(b2, str) {
                                final /* synthetic */ IBusRouteResult a;
                                final /* synthetic */ String b;

                                {
                                    this.a = r2;
                                    this.b = r3;
                                }

                                public final void run() {
                                    if (AMapPageUtil.getPageContext() != null) {
                                        PageBundle pageBundle = new PageBundle();
                                        pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, this.a);
                                        pageBundle.putString("original_busroute_data", this.b);
                                        pageBundle.putBoolean("key_favorites", false);
                                        pageBundle.putLong("bundle_key_start_time", System.currentTimeMillis());
                                        pageBundle.putString("bundle_key_options", "");
                                        AMapPageUtil.getPageContext().startPage(BusNaviDetailPage.class, pageBundle);
                                        UpLoadOperationDataUtil.a(OperationType.TYPE_BUS_ROUTE_SELECT, 0, 0, 0);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    public final boolean a(bid bid) {
        return bid instanceof BusNaviDetailPage;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0066 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.autonavi.bundle.routecommon.entity.IBusRouteResult b(java.lang.String r6) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            r0 = 0
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x005d }
            r2.<init>(r6)     // Catch:{ JSONException -> 0x005d }
            java.lang.String r3 = "buslist"
            org.json.JSONArray r3 = r2.getJSONArray(r3)     // Catch:{ JSONException -> 0x005d }
            if (r3 == 0) goto L_0x0038
            int r4 = r3.length()     // Catch:{ JSONException -> 0x005d }
            if (r4 <= 0) goto L_0x0038
            org.json.JSONObject r3 = r3.getJSONObject(r0)     // Catch:{ JSONException -> 0x005d }
            java.lang.String r4 = "spoi"
            org.json.JSONObject r4 = r3.getJSONObject(r4)     // Catch:{ JSONException -> 0x005d }
            com.autonavi.common.model.POI r4 = a(r4)     // Catch:{ JSONException -> 0x005d }
            java.lang.String r5 = "epoi"
            org.json.JSONObject r3 = r3.getJSONObject(r5)     // Catch:{ JSONException -> 0x0035 }
            com.autonavi.common.model.POI r3 = a(r3)     // Catch:{ JSONException -> 0x0035 }
            goto L_0x003a
        L_0x0035:
            r2 = move-exception
            r3 = r1
            goto L_0x0060
        L_0x0038:
            r3 = r1
            r4 = r3
        L_0x003a:
            java.lang.String r5 = "res_info"
            org.json.JSONObject r2 = r2.optJSONObject(r5)     // Catch:{ JSONException -> 0x005b }
            if (r2 == 0) goto L_0x0063
            java.lang.String r5 = "start_poi"
            org.json.JSONObject r5 = r2.getJSONObject(r5)     // Catch:{ JSONException -> 0x005b }
            com.autonavi.common.model.POI r5 = b(r5)     // Catch:{ JSONException -> 0x005b }
            java.lang.String r4 = "end_poi"
            org.json.JSONObject r2 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0058 }
            com.autonavi.common.model.POI r2 = b(r2)     // Catch:{ JSONException -> 0x0058 }
            r4 = r5
            goto L_0x0064
        L_0x0058:
            r2 = move-exception
            r4 = r5
            goto L_0x0060
        L_0x005b:
            r2 = move-exception
            goto L_0x0060
        L_0x005d:
            r2 = move-exception
            r3 = r1
            r4 = r3
        L_0x0060:
            r2.printStackTrace()
        L_0x0063:
            r2 = r3
        L_0x0064:
            if (r4 == 0) goto L_0x0089
            if (r2 != 0) goto L_0x0069
            goto L_0x0089
        L_0x0069:
            com.autonavi.minimap.route.bus.localbus.RouteBusResultData r1 = new com.autonavi.minimap.route.bus.localbus.RouteBusResultData
            r1.<init>()
            r1.setFromPOI(r4)
            r1.setToPOI(r2)
            dvz r2 = new dvz
            r2.<init>(r1)
            byte[] r6 = r6.getBytes()
            r2.parser(r6)     // Catch:{ Exception -> 0x0081 }
            goto L_0x0085
        L_0x0081:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0085:
            r1.setFocusBusPathIndex(r0)
            return r1
        L_0x0089:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dvi.b(java.lang.String):com.autonavi.bundle.routecommon.entity.IBusRouteResult");
    }

    private static POI a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        return POIFactory.createPOI(jSONObject.optString("name"), new GeoPoint(jSONObject.optDouble(DictionaryKeys.CTRLXY_X), jSONObject.optDouble(DictionaryKeys.CTRLXY_Y)));
    }

    private static POI b(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        return POIFactory.createPOI(jSONObject.optString("name"), new GeoPoint(jSONObject.optInt(DictionaryKeys.CTRLXY_X), jSONObject.optInt(DictionaryKeys.CTRLXY_Y)));
    }
}
