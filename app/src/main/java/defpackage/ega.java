package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.order.param.BikeCheckOrderRequest;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.model.CheckOrder;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import com.autonavi.minimap.route.sharebike.page.ShareBikePage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;
import com.autonavi.minimap.route.sharebike.utils.ShareBikeLogin;
import com.autonavi.minimap.route.sharebike.utils.ShareBikeLogin.OpenPageType;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"shareBike"})
/* renamed from: ega reason: default package */
/* compiled from: ShareBikeRouter */
public class ega extends esk {
    public boolean start(ese ese) {
        PageBundle pageBundle;
        Uri uri = ese.a;
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() <= 0) {
            return false;
        }
        String str = pathSegments.get(0);
        if (TextUtils.equals(str, "shareBikeWalletView")) {
            String queryParameter = uri.getQueryParameter("source");
            String queryParameter2 = uri.getQueryParameter("name");
            JSONObject jSONObject = new JSONObject();
            try {
                if (!TextUtils.isEmpty(queryParameter)) {
                    jSONObject.put("source", queryParameter);
                }
                if (!TextUtils.isEmpty(queryParameter2)) {
                    jSONObject.put("name", queryParameter2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ShareBikeLogin(jSONObject).a(AMapPageUtil.getPageContext(), OpenPageType.PAGE_WALLET_DETAIL, true);
            return true;
        } else if (TextUtils.equals(str, "shareBikeScanView")) {
            String queryParameter3 = uri.getQueryParameter("shortcutLabel");
            if (!TextUtils.isEmpty(queryParameter3)) {
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("status", queryParameter3);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_3D_TOUCH_SHORTCUT_CLICK, jSONObject2);
            }
            String queryParameter4 = uri.getQueryParameter(LocationParams.PARA_FLP_AUTONAVI_LON);
            String queryParameter5 = uri.getQueryParameter("lat");
            String queryParameter6 = uri.getQueryParameter("name");
            String queryParameter7 = uri.getQueryParameter("firepage");
            boolean booleanQueryParameter = uri.getBooleanQueryParameter("ignorebike", false);
            if (queryParameter7 == null) {
                queryParameter7 = "others";
            }
            JSONObject jSONObject3 = new JSONObject();
            if (TextUtils.isEmpty(queryParameter4) || TextUtils.isEmpty(queryParameter5) || TextUtils.isEmpty(queryParameter6)) {
                pageBundle = null;
            } else {
                pageBundle = new PageBundle();
                pageBundle.putString("end_lat", queryParameter5);
                pageBundle.putString("end_lon", queryParameter4);
                pageBundle.putString("end_name", queryParameter6);
                pageBundle.putString("sharebike_page_from", "gjxq");
            }
            ajw ajw = (ajw) ank.a(ajw.class);
            if (ajw != null) {
                boolean parseBoolean = Boolean.parseBoolean(ehs.b("share_bike_riding_status_id"));
                boolean parseBoolean2 = Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id"));
                if (parseBoolean || parseBoolean2) {
                    if (booleanQueryParameter) {
                        startPage(ShareRidingMapPage.class, (PageBundle) null);
                    } else {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add("amapuri://ajx?path=path://amap_bundle_tripgroup/src/share_bike/ShareBikeScanResult.page.js");
                        arrayList.add("amapuri://shareBike/shareBikeBikingView");
                        if (!ajw.a(arrayList, AMapPageUtil.getPageContext())) {
                            startPage(ShareBikePage.class, pageBundle);
                        }
                    }
                    eao.a((String) "dispatcher-open-scan", (String) "exists native order");
                    return true;
                }
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add("amapuri://qrscan/mainView");
                if (ajw.a(arrayList2, AMapPageUtil.getPageContext(), null, null)) {
                    eao.a((String) "dispatcher-open-scan", (String) "back to scan page success");
                } else if (!booleanQueryParameter) {
                    arrayList2.clear();
                    arrayList2.add("amapuri://shareBike/shareBikeMainView");
                    if (ajw.a(arrayList2, AMapPageUtil.getPageContext(), null, pageBundle)) {
                        try {
                            jSONObject3.put("firepage", queryParameter7);
                            jSONObject3.put("launchBikePage", false);
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                        }
                        new ShareBikeLogin(new dzw(null, jSONObject3)).a(AMapPageUtil.getPageContext(), OpenPageType.PAGE_QRCODE_SCAN, false);
                        eao.a((String) "dispatcher-open-scan", (String) "back to shareBike page success & check permission before launch scan page");
                    }
                }
                return true;
            }
            try {
                if (!TextUtils.isEmpty(queryParameter4) && !TextUtils.isEmpty(queryParameter5) && !TextUtils.isEmpty(queryParameter6)) {
                    jSONObject3.put("end_lat", queryParameter5);
                    jSONObject3.put("end_lon", queryParameter4);
                    jSONObject3.put("end_name", queryParameter6);
                    jSONObject3.put("sharebike_page_from", "gjxq");
                }
                if (!TextUtils.isEmpty(queryParameter7)) {
                    jSONObject3.put("firepage", queryParameter7);
                }
                jSONObject3.put("launchBikePage", !booleanQueryParameter);
            } catch (JSONException e4) {
                e4.printStackTrace();
            }
            new ShareBikeLogin(new dzw(pageBundle, jSONObject3)).a(AMapPageUtil.getPageContext(), OpenPageType.PAGE_QRCODE_SCAN, false);
            return true;
        } else if (TextUtils.equals(str, "shareBikeMainView")) {
            String queryParameter8 = uri.getQueryParameter(LocationParams.PARA_FLP_AUTONAVI_LON);
            String queryParameter9 = uri.getQueryParameter("lat");
            String queryParameter10 = uri.getQueryParameter("firepage");
            PageBundle pageBundle2 = new PageBundle();
            if (!TextUtils.isEmpty(queryParameter8) && !TextUtils.isEmpty(queryParameter9)) {
                pageBundle2.putString(LocationParams.PARA_FLP_AUTONAVI_LON, queryParameter8);
                pageBundle2.putString("lat", queryParameter9);
            }
            if (!TextUtils.isEmpty(queryParameter10)) {
                pageBundle2.putString("sharebike_page_from", queryParameter10);
            }
            startPage(ShareBikePage.class, pageBundle2);
            return true;
        } else if (TextUtils.equals(str, "shareBikeBikingView")) {
            startPage(ShareRidingMapPage.class, (PageBundle) null);
            return true;
        } else if (TextUtils.equals(str, "shareBikeMyTripView")) {
            new ShareBikeLogin(new Object[0]).a(AMapPageUtil.getPageContext(), OpenPageType.PAGE_HISTORY, true);
            return true;
        } else if (TextUtils.equals(str, "shareBikeNotify")) {
            if (Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id"))) {
                startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                PageBundle pageBundle3 = new PageBundle();
                pageBundle3.putString("CpSource", ehs.b("share_bike_cp_source"));
                startPage(ShareRidingMapPage.class, pageBundle3);
            } else {
                PageBundle pageBundle4 = new PageBundle();
                String b = ehs.b("share_bike_cp_source");
                String b2 = ehs.b("share_bike_id");
                String b3 = ehs.b("share_bike_order_id");
                StringBuilder sb = new StringBuilder("Start riding page with notification, cpsource=");
                sb.append(b);
                sb.append("|bikeid=");
                sb.append(b2);
                sb.append("|orderId=");
                sb.append(b3);
                eao.d("wbsww", sb.toString());
                pageBundle4.putString("CpSource", b);
                pageBundle4.putString("OrderId", b3);
                pageBundle4.putString("BikeId", b2);
                pageBundle4.putString("bundle_key_page_from", "5");
                startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                startPage(ShareRidingMapPage.class, pageBundle4);
            }
            return true;
        } else if (!TextUtils.equals(str, "shareBikeTrackView")) {
            return false;
        } else {
            final String b4 = ehs.b("share_bike_order_id");
            final String b5 = ehs.b("share_bike_cp_source");
            StringBuilder sb2 = new StringBuilder("doOpenShareBikeTrackPage uri:");
            sb2.append(uri);
            sb2.append(", orderId:");
            sb2.append(b4);
            sb2.append(", cpSource:");
            sb2.append(b5);
            eao.a((String) AutoConstants.AUTO_FILE_ROUTE, sb2.toString());
            if (TextUtils.isEmpty(b5) || TextUtils.isEmpty(b4)) {
                egu.a(new BikeCheckOrderRequest(), (a) new a() {
                    public final void a(BaseNetResult baseNetResult) {
                        if (baseNetResult == null || !baseNetResult.result) {
                            eht.b(b4, b5);
                            return;
                        }
                        CheckOrder checkOrder = (CheckOrder) baseNetResult;
                        eht.b(checkOrder.orderId, checkOrder.cpSource);
                    }
                });
            } else {
                eht.b(b4, b5);
            }
            return true;
        }
    }
}
