package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import org.json.JSONObject;

/* renamed from: ave reason: default package */
/* compiled from: FeedbackUtils */
public final class ave {
    public static PageBundle a(PageBundle pageBundle) {
        JSONObject jSONObject;
        PageBundle pageBundle2 = new PageBundle();
        String string = pageBundle.getString("error_pic_path");
        int i = pageBundle.getInt("sourcepage", 0);
        Object object = pageBundle.getObject("realtime_bus_param");
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("sourcepage", i);
            jSONObject2.put("snapshotPath", string);
            if (object != null) {
                if (!(object instanceof JSONObject)) {
                    jSONObject = new JSONObject(object.toString());
                } else {
                    jSONObject = (JSONObject) object;
                }
                jSONObject2.put("param", jSONObject);
            }
        } catch (Exception unused) {
        }
        pageBundle2.putString("url", "path://amap_bundle_bus_feedback/src/pages/RealBusError/RealBusError.page.js");
        pageBundle2.putObject("jsData", jSONObject2.toString());
        return pageBundle2;
    }

    public static SelectPoiFromMapBean a(POI poi) {
        SelectPoiFromMapBean selectPoiFromMapBean = new SelectPoiFromMapBean();
        selectPoiFromMapBean.setOldPOI(poi);
        selectPoiFromMapBean.setHideOldPoi(true);
        selectPoiFromMapBean.setLevel(18);
        return selectPoiFromMapBean;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0039  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a() {
        /*
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            android.location.Location r0 = r0.getLatestLocation()
            java.lang.String r0 = r0.getProvider()
            int r1 = r0.hashCode()
            r2 = -1184229805(0xffffffffb96a1653, float:-2.2324295E-4)
            if (r1 == r2) goto L_0x0025
            r2 = 102570(0x190aa, float:1.43731E-40)
            if (r1 == r2) goto L_0x001b
            goto L_0x002f
        L_0x001b:
            java.lang.String r1 = "gps"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002f
            r0 = 0
            goto L_0x0030
        L_0x0025:
            java.lang.String r1 = "indoor"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002f
            r0 = 1
            goto L_0x0030
        L_0x002f:
            r0 = -1
        L_0x0030:
            switch(r0) {
                case 0: goto L_0x0039;
                case 1: goto L_0x0036;
                default: goto L_0x0033;
            }
        L_0x0033:
            java.lang.String r0 = "7000"
            goto L_0x003b
        L_0x0036:
            java.lang.String r0 = "7001"
            goto L_0x003b
        L_0x0039:
            java.lang.String r0 = "7002"
        L_0x003b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ave.a():java.lang.String");
    }
}
