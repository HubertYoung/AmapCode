package defpackage;

import android.graphics.Point;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.bundle.routecommon.entity.Trip;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dyk reason: default package */
/* compiled from: AosRealTimeBuslineMatchupParser */
public final class dyk extends AbstractAOSParser {
    public HashMap<String, RealTimeBusAndStationMatchup> a;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        JSONArray jSONArray;
        String[] strArr;
        parseHeader(bArr);
        if (this.errorCode == 1) {
            try {
                JSONArray b = b(new JSONObject(new String(bArr, "UTF-8")), "buses");
                if (b != null) {
                    if (b.length() != 0) {
                        if (this.a == null) {
                            this.a = new HashMap<>();
                        }
                        this.a.clear();
                        int i = 0;
                        int i2 = 0;
                        while (i2 < b.length()) {
                            JSONObject jSONObject = b.getJSONObject(i2);
                            int a2 = a(jSONObject, "status");
                            String c = c(jSONObject, "line");
                            String c2 = c(jSONObject, "station");
                            JSONArray jSONArray2 = jSONObject.getJSONArray("trip");
                            RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = new RealTimeBusAndStationMatchup();
                            realTimeBusAndStationMatchup.mStatus = a2;
                            realTimeBusAndStationMatchup.mBuslineID = c;
                            realTimeBusAndStationMatchup.mStationID = c2;
                            if (jSONArray2 == null || jSONArray2.length() <= 0) {
                                jSONArray = b;
                            } else {
                                JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
                                Trip trip = new Trip();
                                trip.lindID = c;
                                trip.stationID = c2;
                                trip.arrival = a(jSONObject2, "arrival");
                                trip.arrival_old = a(jSONObject2, "arrival_old");
                                trip.speed = a(jSONObject2, "speed");
                                trip.station_left = a(jSONObject2, "station_left");
                                trip.speed_avg = a(jSONObject2, "speed_avg");
                                trip.dis = a(jSONObject2, "dis");
                                JSONObject optJSONObject = jSONObject2.optJSONObject("track");
                                if (optJSONObject != null) {
                                    trip.track = new ArrayList<>();
                                    String optString = optJSONObject.optString("xs");
                                    String optString2 = optJSONObject.optString("ys");
                                    String[] strArr2 = null;
                                    if (optString == null) {
                                        strArr = null;
                                    } else {
                                        strArr = optString.split(",");
                                    }
                                    if (optString2 != null) {
                                        strArr2 = optString2.split(",");
                                    }
                                    if (!(strArr == null || strArr2 == null || strArr.length != strArr2.length)) {
                                        int i3 = 0;
                                        while (i3 < strArr.length) {
                                            trip.track.add(new GeoPoint(Double.parseDouble(strArr[i3]), Double.parseDouble(strArr2[i3])));
                                            i3++;
                                            b = b;
                                        }
                                    }
                                }
                                jSONArray = b;
                                Double valueOf = Double.valueOf(jSONObject2.getDouble(DictionaryKeys.CTRLXY_X));
                                Double valueOf2 = Double.valueOf(jSONObject2.getDouble(DictionaryKeys.CTRLXY_Y));
                                Point a3 = cfg.a(valueOf2.doubleValue(), valueOf.doubleValue());
                                trip.x = a3.x;
                                trip.y = a3.y;
                                trip.lon = valueOf.doubleValue();
                                trip.lat = valueOf2.doubleValue();
                                realTimeBusAndStationMatchup.mTrip = trip;
                            }
                            this.a.put(c2, realTimeBusAndStationMatchup);
                            i2++;
                            b = jSONArray;
                            i = 0;
                        }
                    }
                }
            } catch (JSONException e) {
                kf.a((Throwable) e);
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
    }

    private static int a(JSONObject jSONObject, String str) {
        try {
            String optString = jSONObject.optString(str);
            if (optString.equals("")) {
                return 0;
            }
            return ahh.b(optString);
        } catch (Exception unused) {
            return 0;
        }
    }

    private static JSONArray b(JSONObject jSONObject, String str) {
        try {
            JSONArray optJSONArray = jSONObject.optJSONArray(str);
            if (optJSONArray == null) {
                JSONObject optJSONObject = jSONObject.optJSONObject(str);
                if (optJSONObject != null) {
                    optJSONArray = new JSONArray().put(optJSONObject);
                }
            }
            return optJSONArray;
        } catch (Exception unused) {
            return null;
        }
    }

    private static String c(JSONObject jSONObject, String str) {
        String str2;
        if (jSONObject == null || str.length() == 0) {
            return null;
        }
        try {
            str2 = jSONObject.getString(str);
            try {
                return str2.equals("null") ? "" : str2;
            } catch (JSONException unused) {
                return str2;
            }
        } catch (JSONException unused2) {
            str2 = "";
            return str2;
        }
    }
}
