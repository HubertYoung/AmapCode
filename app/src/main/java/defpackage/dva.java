package defpackage;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.route.bus.model.Bus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dva reason: default package */
/* compiled from: RouteFeedbackUtil */
public final class dva {
    public static void a(Bus bus, int i, boolean z) {
        if (bus != null) {
            IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
            if (iErrorReportStarter != null) {
                iErrorReportStarter.startFeedback(b(bus, i, z));
            }
        }
    }

    private static PageBundle b(Bus bus, int i, boolean z) {
        int i2;
        int i3;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("page_action", "com.basemap.action.feedback_entry_list");
        pageBundle.putInt("page_id", 20);
        pageBundle.putInt("sourcepage", z ? 56 : 7);
        pageBundle.putString("bus_id", bus.id);
        pageBundle.putStringArray("bus_stations", bus.stations);
        pageBundle.putStringArray("bus_station_ids", bus.stationIds);
        pageBundle.putIntArray("bus_stations_x", bus.stationX);
        pageBundle.putIntArray("bus_stations_y", bus.stationY);
        pageBundle.putStringArray("station_poi_id_1s", bus.stationpoiid1);
        if (!(bus.stationX == null || bus.stationY == null)) {
            GeoPoint geoPoint = new GeoPoint(bus.stationX[0], bus.stationY[0]);
            POI createPOI = POIFactory.createPOI("", geoPoint);
            createPOI.setName(bus.startName);
            pageBundle.putSerializable("startpoint", createPOI);
            int length = bus.stationX.length - 1;
            pageBundle.putSerializable("endpoint", POIFactory.createPOI(bus.endName, new GeoPoint(bus.stationX[length], bus.stationY[length])));
            pageBundle.putString(AutoJsonUtils.JSON_ADCODE, String.valueOf(geoPoint.getAdCode()));
        }
        pageBundle.putString("name", bus.key_name);
        pageBundle.putString("lineid", bus.id);
        String str = "";
        String str2 = "";
        if (i >= 0) {
            str = bus.stations[i];
            str2 = bus.stationIds[i];
            i3 = bus.stationX[i];
            i2 = bus.stationY[i];
        } else {
            i3 = 0;
            i2 = 0;
        }
        if (bno.a) {
            StringBuilder sb = new StringBuilder("(BusDetail) ReportError -pos: ");
            sb.append(i);
            sb.append(" -sName: ");
            sb.append(str);
            sb.append(" -sId: ");
            sb.append(str2);
            sb.append(" -keyName: ");
            sb.append(bus.key_name);
            sb.append(" -keyX: ");
            sb.append(i3);
            sb.append(" -keyY: ");
            sb.append(i2);
            eao.a((String) "Daniel-27", sb.toString());
        }
        pageBundle.putString("linename", bus.key_name);
        pageBundle.putString("stationid", str2);
        pageBundle.putString("stationname", str);
        pageBundle.putInt("stationX", i3);
        pageBundle.putInt("stationY", i2);
        pageBundle.putBoolean("isRealTime", bus.isRealTime);
        JSONArray jSONArray = new JSONArray();
        for (int i4 = 0; i4 < bus.stationIds.length; i4++) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("id", bus.stationIds[i4]);
                jSONObject.put("name", bus.stations[i4]);
                jSONObject.put(DictionaryKeys.CTRLXY_X, bus.stationX[i4]);
                jSONObject.put(DictionaryKeys.CTRLXY_Y, bus.stationY[i4]);
                jSONArray.put(i4, jSONObject);
            } catch (JSONException unused) {
            }
        }
        pageBundle.putObject("bus_all_stations", jSONArray);
        pageBundle.putBoolean("bundle_key_boolean_default", false);
        return pageBundle;
    }
}
