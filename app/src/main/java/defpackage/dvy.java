package defpackage;

import android.graphics.Point;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.BusEta;
import com.autonavi.bundle.routecommon.entity.BusEtaLink;
import com.autonavi.bundle.routecommon.entity.BusPathSection.IrregularTime;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.model.Bus;
import com.sina.weibo.sdk.statistic.LogBuilder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dvy reason: default package */
/* compiled from: AosAlterListResponser */
public final class dvy extends AbstractAOSParser {
    public Bus a = new Bus();
    public byte[] b;

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        String[] strArr;
        this.b = bArr;
        JSONObject parseHeader = parseHeader(bArr);
        if (this.errorCode == 1) {
            JSONObject jSONObject = parseHeader.getJSONObject("alterline");
            this.a.id = jSONObject.getString("busid");
            this.a.name = jSONObject.getString("busname");
            if (jSONObject.has("key_name")) {
                this.a.key_name = jSONObject.optString("key_name");
            } else if (jSONObject.has("bus_key_name")) {
                this.a.key_name = jSONObject.optString("bus_key_name");
            } else {
                this.a.key_name = this.a.name;
            }
            this.a.type = jSONObject.getInt("bustype");
            this.a.startName = jSONObject.getString("startname");
            this.a.endName = jSONObject.getString("endname");
            if (jSONObject.has(LogBuilder.KEY_START_TIME)) {
                try {
                    if (jSONObject.optInt(LogBuilder.KEY_START_TIME) == 0) {
                        String[] split = jSONObject.getString(LogBuilder.KEY_START_TIME).split(":");
                        this.a.startTime = (ahh.b(split[0]) * 100) + ahh.b(split[1]);
                    } else {
                        this.a.startTime = jSONObject.optInt(LogBuilder.KEY_START_TIME);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (jSONObject.has(LogBuilder.KEY_END_TIME)) {
                try {
                    if (jSONObject.optInt(LogBuilder.KEY_END_TIME) == 0) {
                        String[] split2 = jSONObject.getString(LogBuilder.KEY_END_TIME).split(":");
                        this.a.endTime = (ahh.b(split2[0]) * 100) + ahh.b(split2[1]);
                    } else {
                        this.a.endTime = jSONObject.optInt(LogBuilder.KEY_END_TIME);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            String string = jSONObject.getString("passdepotid");
            String string2 = jSONObject.getString("passdepotname");
            String string3 = jSONObject.getString("drivercoord");
            String string4 = jSONObject.getString("passdepotcoord");
            if (jSONObject.has("interval_desc")) {
                this.a.interval = jSONObject.getString("interval_desc");
            }
            this.a.irregulartime = a(axr.e(jSONObject, "irregulartime"));
            String[] split3 = string.split(Token.SEPARATOR);
            this.a.stationIds = new String[(split3.length + 2)];
            this.a.stationIds[0] = jSONObject.getString("startid");
            this.a.stationIds[this.a.stationIds.length - 1] = jSONObject.getString("endid");
            int i = 0;
            while (i < split3.length) {
                int i2 = i + 1;
                this.a.stationIds[i2] = split3[i];
                i = i2;
            }
            if (string2 == null || string2.length() == 0) {
                strArr = new String[0];
            } else {
                strArr = string2.split(Token.SEPARATOR);
            }
            this.a.stations = new String[(strArr.length + 2)];
            this.a.stations[0] = this.a.startName;
            this.a.stations[this.a.stations.length - 1] = this.a.endName;
            int i3 = 0;
            while (i3 < strArr.length) {
                int i4 = i3 + 1;
                this.a.stations[i4] = strArr[i3];
                i3 = i4;
            }
            String[] split4 = string4.split(",");
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (int i5 = 0; i5 < split4.length; i5++) {
                String str = split4[i5];
                if (str == null || str.equals("")) {
                    break;
                }
                if ((i5 & 1) == 1) {
                    arrayList.add(Double.valueOf(Double.valueOf(str).doubleValue()));
                } else {
                    arrayList2.add(Double.valueOf(Double.valueOf(str).doubleValue()));
                }
            }
            this.a.stationX = new int[(arrayList.size() + 2)];
            this.a.stationY = new int[(arrayList2.size() + 2)];
            int min = Math.min(arrayList.size(), arrayList2.size());
            int i6 = 0;
            while (i6 < min) {
                Point a2 = cfg.a(((Double) arrayList.get(i6)).doubleValue(), ((Double) arrayList2.get(i6)).doubleValue());
                i6++;
                this.a.stationX[i6] = a2.x;
                this.a.stationY[i6] = a2.y;
            }
            String[] split5 = string3.split(",");
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (int i7 = 0; i7 < split5.length; i7++) {
                String str2 = split5[i7];
                if ((i7 & 1) == 1) {
                    arrayList3.add(Double.valueOf(Double.valueOf(str2).doubleValue()));
                } else {
                    arrayList4.add(Double.valueOf(Double.valueOf(str2).doubleValue()));
                }
            }
            this.a.coordX = new int[arrayList3.size()];
            this.a.coordY = new int[arrayList4.size()];
            int min2 = Math.min(arrayList3.size(), arrayList4.size());
            for (int i8 = 0; i8 < min2; i8++) {
                Point a3 = cfg.a(((Double) arrayList3.get(i8)).doubleValue(), ((Double) arrayList4.get(i8)).doubleValue());
                this.a.coordX[i8] = a3.x;
                this.a.coordY[i8] = a3.y;
            }
            this.a.stationX[0] = this.a.coordX[0];
            this.a.stationY[0] = this.a.coordY[0];
            this.a.stationX[this.a.stationX.length - 1] = this.a.coordX[this.a.coordX.length - 1];
            this.a.stationY[this.a.stationX.length - 1] = this.a.coordY[this.a.coordY.length - 1];
            if (jSONObject.has("eta")) {
                Bus bus = this.a;
                JSONObject jSONObject2 = jSONObject.getJSONObject("eta");
                if (!(jSONObject2 == null || bus == null)) {
                    BusEta busEta = new BusEta();
                    busEta.linestatus = axr.b(jSONObject2, "linestatus");
                    a(busEta, jSONObject2);
                    busEta.etalinks = b(busEta, jSONObject2);
                    bus.eta = busEta;
                }
            }
            if (jSONObject.has("color")) {
                Bus bus2 = this.a;
                StringBuilder sb = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                sb.append(jSONObject.getString("color"));
                bus2.color = sb.toString();
            } else {
                this.a.color = "#42a5ff";
            }
            a(this.a, jSONObject);
        }
    }

    private static IrregularTime a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            IrregularTime irregularTime = new IrregularTime();
            irregularTime.normalday = axr.e(jSONObject, "normalday");
            irregularTime.workday = axr.e(jSONObject, "workday");
            irregularTime.holiday = axr.e(jSONObject, "holiday");
            return irregularTime;
        } catch (JSONException unused) {
            return null;
        }
    }

    private static void a(BusEta busEta, JSONObject jSONObject) {
        double d;
        if (jSONObject != null) {
            String e = axr.e(jSONObject, "etacoords");
            if (e != null) {
                String[] split = e.split(",");
                if (split.length > 0) {
                    int length = split.length / 2;
                    busEta.mXs = new int[length];
                    busEta.mYs = new int[length];
                    for (int i = 0; i < length; i++) {
                        int i2 = i * 2;
                        double d2 = 0.0d;
                        try {
                            d = Double.parseDouble(split[i2]);
                            d2 = Double.parseDouble(split[i2 + 1]);
                        } catch (NumberFormatException unused) {
                            d = 0.0d;
                        }
                        Point a2 = cfg.a(d2, d);
                        busEta.mXs[i] = a2.x;
                        busEta.mYs[i] = a2.y;
                    }
                }
            }
        }
    }

    private static BusEtaLink[] b(BusEta busEta, JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            JSONArray a2 = axr.a(jSONObject, "lnk");
            if (a2 == null || a2.length() <= 0) {
                return null;
            }
            int length = a2.length();
            busEta.etalinks = new BusEtaLink[length];
            for (int i = 0; i < length; i++) {
                BusEtaLink busEtaLink = new BusEtaLink();
                busEtaLink.startindex = axr.b(a2.getJSONObject(i), "sidx");
                busEtaLink.endindex = axr.b(a2.getJSONObject(i), "eidx");
                busEtaLink.etastate = axr.b(a2.getJSONObject(i), "v");
                busEta.etalinks[i] = busEtaLink;
            }
            return busEta.etalinks;
        } catch (Exception unused) {
            return null;
        }
    }

    private static void a(Bus bus, JSONObject jSONObject) {
        if (jSONObject != null && bus != null && bus.stations != null && bus.stations.length > 0) {
            bus.stationdirection = new int[bus.stations.length];
            bus.stationdirection[0] = axr.b(jSONObject, "startdirection");
            bus.stationdirection[bus.stations.length - 1] = axr.b(jSONObject, "enddirection");
            String e = axr.e(jSONObject, "depotdirection");
            if (!TextUtils.isEmpty(e)) {
                String[] split = e.split(Token.SEPARATOR);
                for (int i = 1; i < bus.stations.length - 1; i++) {
                    int i2 = i - 1;
                    if (i2 < split.length) {
                        String str = split[i2];
                        if (!TextUtils.isEmpty(str)) {
                            bus.stationdirection[i] = ahh.b(str);
                        }
                    }
                }
            }
        }
    }

    public final String getErrorDesc(int i) {
        if (i == 0) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.route_unknow_error);
        }
        return this.errorMessage;
    }
}
