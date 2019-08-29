package defpackage;

import android.graphics.Point;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline;
import com.autonavi.minimap.route.bus.realtimebus.model.TripInfo;
import com.autonavi.minimap.route.bus.realtimebus.model.stTrip;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dyl reason: default package */
/* compiled from: RealTimeBusLineParser */
public final class dyl {
    public static dym a(JSONObject jSONObject) {
        dym dym = new dym();
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("buses");
            if (jSONArray == null) {
                return null;
            }
            dym.a = new HashMap<>();
            for (int i = 0; i < jSONArray.length(); i++) {
                RealTimeBusline realTimeBusline = new RealTimeBusline();
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                String string = jSONObject2.getString("line");
                JSONArray jSONArray2 = jSONObject2.getJSONArray("trip");
                int length = jSONArray2.length();
                realTimeBusline.tripInfoMap = new HashMap();
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                    TripInfo tripInfo = new TripInfo();
                    tripInfo.tripid = ahh.b(jSONObject3.getString("i"));
                    Point a = cfg.a(Double.parseDouble(jSONObject3.getString(DictionaryKeys.CTRLXY_Y)), Double.parseDouble(jSONObject3.getString(DictionaryKeys.CTRLXY_X)));
                    tripInfo.gpoi = new GeoPoint(a.x, a.y);
                    tripInfo.arrstid = jSONObject3.getString("stid");
                    tripInfo.state = ahh.b(jSONObject3.getString("s"));
                    if (tripInfo.state == 1) {
                        tripInfo.sitedistance = jSONObject3.getString("d");
                        tripInfo.sitetime = jSONObject3.getString(LogItem.MM_C15_K4_TIME);
                    }
                    realTimeBusline.tripInfoMap.put(Integer.valueOf(tripInfo.tripid), tripInfo);
                }
                JSONArray jSONArray3 = jSONObject2.getJSONArray("st");
                int length2 = jSONArray3.length();
                realTimeBusline.stationMap = new HashMap();
                for (int i3 = 0; i3 < length2; i3++) {
                    JSONObject jSONObject4 = jSONArray3.getJSONObject(i3);
                    stTrip sttrip = new stTrip();
                    sttrip.stid = jSONObject4.getString("id");
                    sttrip.docktripnum = jSONObject4.getString("tw");
                    sttrip.onwaytripnum = jSONObject4.getString("ta");
                    JSONArray jSONArray4 = jSONObject4.getJSONArray("td");
                    if (jSONArray4.length() > 0) {
                        sttrip.tripinfomap = new HashMap();
                    }
                    for (int i4 = 0; i4 < jSONArray4.length(); i4++) {
                        JSONObject jSONObject5 = jSONArray4.getJSONObject(i4);
                        String string2 = jSONObject5.getString("ti");
                        if (string2 != null && !string2.trim().equals("")) {
                            TripInfo tripInfo2 = new TripInfo();
                            tripInfo2.tripid = ahh.b(string2);
                            tripInfo2.sitedistance = jSONObject5.getString("d");
                            tripInfo2.sitetime = jSONObject5.getString(LogItem.MM_C15_K4_TIME);
                            if (jSONObject5.has("ls")) {
                                tripInfo2.stationleft = jSONObject5.getString("ls");
                            }
                            sttrip.tripinfomap.put(Integer.valueOf(tripInfo2.tripid), tripInfo2);
                        }
                    }
                    realTimeBusline.stationMap.put(sttrip.stid, sttrip);
                }
                dym.a.put(string, realTimeBusline);
            }
            return dym;
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
    }
}
