package com.amap.location.protocol.b;

import android.os.SystemClock;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.amap.location.common.d.a;
import com.amap.location.common.f.h;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.CellState;
import com.amap.location.common.model.CellStatus;
import com.amap.location.common.model.FPS;
import com.amap.location.common.model.HisLocation;
import com.amap.location.common.model.WiFi;
import com.amap.location.common.model.WifiStatus;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.sdk.location.LocationInstrument;
import com.taobao.accs.common.Constants;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: RequestData */
public class b {
    public List<HisLocation> A;
    public String B;
    private long C = System.currentTimeMillis();
    public int a = 0;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public long l = 0;
    public String m;
    public String n;
    public String o;
    public String p;
    public String q = "UNKWN";
    public byte r = 0;
    public byte s = 0;
    public FPS t;
    public int u;
    public List<AmapLoc> v;
    public String w;
    public String x;
    public byte[] y;
    public byte[] z;

    private static int a(int i2) {
        if (i2 < 0) {
            return 0;
        }
        if (i2 > 65535) {
            return 65535;
        }
        return i2;
    }

    public String a() {
        StringBuilder sb = new StringBuilder(1024);
        String str = "0";
        if (!(this.t == null || this.t.cellStatus == null || (this.t.cellStatus.cellType & 2) == 0)) {
            str = "1";
        }
        sb.append("<?xml version=\"1.0\" encoding=\"");
        sb.append("GBK\"?>");
        sb.append("<Cell_Req ver=\"3.0\"><HDR version=\"3.0\" cdma=\"");
        sb.append(str);
        sb.append("\" gtype=\"");
        sb.append(this.s);
        sb.append("\">");
        sb.append("<action>1</action>");
        sb.append("<respctrl>");
        sb.append(this.a);
        sb.append("</respctrl>");
        sb.append("<src></src>");
        sb.append("<license></license>");
        sb.append("<key>");
        sb.append(this.d);
        sb.append("</key>");
        sb.append("<model>");
        sb.append(this.f);
        sb.append("</model>");
        sb.append("<os>");
        sb.append(this.g);
        sb.append("</os>");
        sb.append("<clientid>");
        sb.append(this.i);
        sb.append("</clientid>");
        sb.append("<imei>");
        sb.append(this.j);
        sb.append("</imei>");
        sb.append("<imsi>");
        sb.append(this.k);
        sb.append("</imsi>");
        sb.append("<sn>");
        sb.append(this.B);
        sb.append("</sn>");
        sb.append("<reqid>0</reqid>");
        sb.append("<smac>");
        sb.append(h.a(this.l));
        sb.append("</smac>");
        sb.append("<sdkv>");
        sb.append(this.m);
        sb.append("</sdkv>");
        sb.append("<corv>");
        sb.append(this.n);
        sb.append("</corv>");
        sb.append("<uuid>");
        sb.append(this.o);
        sb.append("</uuid>");
        sb.append("<adiu>");
        sb.append(this.p);
        sb.append("</adiu>");
        sb.append("</HDR><DRR phnum=\"");
        sb.append(this.h);
        sb.append("\" nettype=\"");
        sb.append(this.q);
        sb.append("\" inftype=\"");
        sb.append(this.r);
        sb.append("\">");
        long elapsedRealtime = SystemClock.elapsedRealtime();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        if (!(this.t == null || this.t.cellStatus == null || this.t.cellStatus.cellType == 0)) {
            int i2 = this.t.cellStatus.cellType;
            CellState cellState = this.t.cellStatus.mainCell;
            sb2.append("<cgiflag>");
            sb2.append(this.t.cellStatus.cellType);
            sb2.append("</cgiflag>");
            if ((i2 & 1) != 0) {
                sb2.append("<mcc>");
                sb2.append(cellState.mcc);
                sb2.append("</mcc>");
                sb2.append("<mnc>");
                sb2.append(cellState.mnc);
                sb2.append("</mnc>");
                sb2.append("<lac>");
                sb2.append(cellState.lac);
                sb2.append("</lac>");
                sb2.append("<cellid>");
                sb2.append(cellState.cid);
                sb2.append("</cellid>");
                sb2.append("<signal>");
                sb2.append(cellState.signalStrength);
                sb2.append("</signal>");
                sb2.append("<cage>0</cage>");
                List<CellState> list = this.t.cellStatus.neighbors;
                if (list != null) {
                    for (int i3 = 0; i3 < list.size(); i3++) {
                        CellState cellState2 = list.get(i3);
                        sb3.append(cellState2.lac);
                        sb3.append(",");
                        sb3.append(cellState2.cid);
                        sb3.append(",");
                        sb3.append(cellState2.signalStrength);
                        if (i3 < list.size() - 1) {
                            sb3.append("*");
                        }
                    }
                }
            } else if ((i2 & 2) != 0) {
                sb2.append("<mcc>");
                sb2.append(cellState.mcc);
                sb2.append("</mcc>");
                sb2.append("<sid>");
                sb2.append(cellState.sid);
                sb2.append("</sid>");
                sb2.append("<nid>");
                sb2.append(cellState.nid);
                sb2.append("</nid>");
                sb2.append("<bid>");
                sb2.append(cellState.bid);
                sb2.append("</bid>");
                if (cellState.longitude > 0 && cellState.latitude > 0) {
                    sb2.append("<lon>");
                    sb2.append(cellState.longitude);
                    sb2.append("</lon>");
                    sb2.append("<lat>");
                    sb2.append(cellState.latitude);
                    sb2.append("</lat>");
                }
                sb2.append("<signal>");
                sb2.append(cellState.signalStrength);
                sb2.append("</signal>");
                sb2.append("<cage>0</cage>");
            }
            sb.append(sb2);
            sb.append(String.format(Locale.CHINA, "<nb>%s</nb>", new Object[]{sb3}));
            sb.append("<networkoperator>");
            sb.append(this.t.cellStatus.networkOperator);
            sb.append("</networkoperator>");
            List<CellState> list2 = this.t.cellStatus.cellStateList2;
            if (list2 != null && list2.size() > 0) {
                StringBuilder sb4 = new StringBuilder();
                int min = Math.min(127, list2.size());
                for (int i4 = 0; i4 < min; i4++) {
                    CellState cellState3 = list2.get(i4);
                    switch (cellState3.type) {
                        case 1:
                        case 3:
                        case 4:
                            sb4.append("<cell>");
                            sb4.append("<type>");
                            sb4.append(cellState3.type);
                            sb4.append("</type>");
                            sb4.append("<registered>");
                            sb4.append(cellState3.registered);
                            sb4.append("</registered>");
                            sb4.append("<mcc>");
                            sb4.append(cellState3.mcc);
                            sb4.append("</mcc>");
                            sb4.append("<mnc>");
                            sb4.append(cellState3.mnc);
                            sb4.append("</mnc>");
                            sb4.append("<lac>");
                            sb4.append(cellState3.lac);
                            sb4.append("</lac>");
                            sb4.append("<cellid>");
                            sb4.append(cellState3.cid);
                            sb4.append("</cellid>");
                            sb4.append("<signal>");
                            sb4.append(cellState3.signalStrength);
                            sb4.append("</signal>");
                            sb4.append("<cage>");
                            sb4.append(cellState3.cellAge);
                            sb4.append("</cage>");
                            sb4.append("</cell>");
                            break;
                        case 2:
                            sb4.append("<cell>");
                            sb4.append("<type>");
                            sb4.append(cellState3.type);
                            sb4.append("</type>");
                            sb4.append("<registered>");
                            sb4.append(cellState3.registered);
                            sb4.append("</registered>");
                            sb4.append("<mcc>");
                            sb4.append(cellState3.mcc);
                            sb4.append("</mcc>");
                            sb4.append("<sid>");
                            sb4.append(cellState3.sid);
                            sb4.append("</sid>");
                            sb4.append("<nid>");
                            sb4.append(cellState3.nid);
                            sb4.append("</nid>");
                            sb4.append("<bid>");
                            sb4.append(cellState3.bid);
                            sb4.append("</bid>");
                            sb4.append("<lon>");
                            sb4.append(cellState3.longitude);
                            sb4.append("</lon>");
                            sb4.append("<lat>");
                            sb4.append(cellState3.latitude);
                            sb4.append("</lat>");
                            sb4.append("<signal>");
                            sb4.append(cellState3.signalStrength);
                            sb4.append("</signal>");
                            sb4.append("<cage>");
                            sb4.append(cellState3.cellAge);
                            sb4.append("</cage>");
                            sb4.append("</cell>");
                            break;
                    }
                }
                sb.append("<newcells>");
                sb.append(sb4);
                sb.append("</newcells>");
            }
        }
        StringBuilder sb5 = new StringBuilder();
        StringBuilder sb6 = new StringBuilder();
        if (!(this.t == null || this.t.wifiStatus == null)) {
            WiFi wiFi = this.t.wifiStatus.mainWifi;
            if (wiFi != null) {
                sb6.append(h.a(wiFi.mac));
                sb6.append(",");
                sb6.append(wiFi.rssi);
                sb6.append(",");
                String str2 = wiFi.ssid;
                if (str2 == null) {
                    str2 = "unkwn";
                }
                sb6.append(str2.replace("*", "."));
            }
            List<WiFi> wifiList = this.t.wifiStatus.getWifiList();
            if (wifiList != null) {
                for (WiFi next : wifiList) {
                    sb5.append(h.a(next.mac));
                    sb5.append(",");
                    sb5.append(next.rssi);
                    sb5.append(",");
                    int i5 = (int) (((elapsedRealtime - next.timestamp) / 1000) + 1);
                    if (i5 < 0) {
                        i5 = 0;
                    }
                    sb5.append(i5);
                    sb5.append(",");
                    sb5.append(next.frequency);
                    sb5.append(",");
                    String str3 = next.ssid;
                    if (str3 == null) {
                        str3 = "unkwn";
                    }
                    sb5.append(str3.replace("*", "."));
                    sb5.append("*");
                }
            }
        }
        if (sb5.length() > 0) {
            sb5.deleteCharAt(sb5.length() - 1);
            sb.append("<macs>");
            sb.append(String.format(Locale.CHINA, "<![CDATA[%s]]>", new Object[]{sb5}));
            sb.append("</macs>");
            sb.append("<macsage>");
            sb.append(this.u);
            sb.append("</macsage>");
        }
        sb.append("<mmac>");
        sb.append(String.format(Locale.CHINA, "<![CDATA[%s]]>", new Object[]{sb6}));
        sb.append("</mmac></DRR></Cell_Req>");
        return sb.toString();
    }

    public JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("curTime", this.C);
            jSONObject.put("ver", "5.2");
            jSONObject.put("action", 1);
            jSONObject.put("respctrl", this.a);
            jSONObject.put("src", this.b);
            jSONObject.put("license", this.c);
            jSONObject.put("extrakey", this.d);
            jSONObject.put("srvip", this.e);
            jSONObject.put(Constants.KEY_MODEL, this.f);
            jSONObject.put("os", this.g);
            jSONObject.put("phoneNum", this.h);
            jSONObject.put("appName", this.i);
            jSONObject.put(Constants.KEY_IMEI, this.j);
            jSONObject.put(Constants.KEY_IMSI, this.k);
            jSONObject.put("sn", this.B);
            jSONObject.put("smac", h.a(this.l));
            jSONObject.put(Constants.KEY_SDK_VERSION, this.m);
            jSONObject.put("collectionVersion", this.n);
            jSONObject.put("utdid", this.o);
            jSONObject.put(LocationParams.PARA_COMMON_ADIU, this.p);
            jSONObject.put("nettype", this.q);
            jSONObject.put("inftype", this.r);
            jSONObject.put("gtype", this.s);
            a(jSONObject, this.t);
            jSONObject.put("macsAge", this.u);
            a(jSONObject, this.v);
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, this.w);
            jSONObject.put("context", this.x);
            b(jSONObject, this.A);
        } catch (Exception e2) {
            a.a((Throwable) e2);
        }
        return jSONObject;
    }

    private void a(JSONObject jSONObject, FPS fps) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        CellStatus cellStatus = fps.cellStatus;
        if (cellStatus != null) {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("updateTime", cellStatus.updateTime);
            jSONObject3.put("cellType", cellStatus.cellType);
            jSONObject3.put("networkOperator", cellStatus.networkOperator);
            jSONObject3.put("mainCell", a(cellStatus.mainCell));
            if (cellStatus.neighbors != null && cellStatus.neighbors.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                for (CellState a2 : cellStatus.neighbors) {
                    jSONArray.put(a(a2));
                }
                jSONObject3.put("neighbors", jSONArray);
            }
            jSONObject3.put("mainCell2", a(cellStatus.mainCell2));
            if (cellStatus.cellStateList2 != null && cellStatus.cellStateList2.size() > 0) {
                JSONArray jSONArray2 = new JSONArray();
                for (CellState a3 : cellStatus.cellStateList2) {
                    jSONArray2.put(a(a3));
                }
                jSONObject3.put("cell2", jSONArray2);
            }
            jSONObject2.put("cellStatus", jSONObject3);
        }
        WifiStatus wifiStatus = fps.wifiStatus;
        if (wifiStatus != null) {
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("updateTime", wifiStatus.updateTime);
            jSONObject4.put("mainWifi", a(wifiStatus.mainWifi));
            List<WiFi> wifiList = wifiStatus.getWifiList();
            if (wifiList != null && wifiList.size() > 0) {
                JSONArray jSONArray3 = new JSONArray();
                for (WiFi a4 : wifiList) {
                    jSONArray3.put(a(a4));
                }
                jSONObject4.put("wifiList", jSONArray3);
            }
            jSONObject2.put("wifiStatus", jSONObject4);
        }
        jSONObject.put(LogItem.MM_C13_K4_FPS, jSONObject2);
    }

    private JSONObject a(WiFi wiFi) throws JSONException {
        if (wiFi == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("mac", wiFi.mac);
        jSONObject.put("ssid", wiFi.ssid);
        jSONObject.put("rssi", wiFi.rssi);
        jSONObject.put("frequency", wiFi.frequency);
        jSONObject.put("timestamp", wiFi.timestamp);
        jSONObject.put("lastUpdateUtcMills", wiFi.lastUpdateUtcMills);
        jSONObject.put("age", a((int) ((this.C - wiFi.lastUpdateUtcMills) / 1000)));
        jSONObject.put("type", wiFi.type);
        return jSONObject;
    }

    private JSONObject a(CellState cellState) throws JSONException {
        int i2;
        if (cellState == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", cellState.type);
        jSONObject.put("mcc", cellState.mcc);
        jSONObject.put("mnc", cellState.mnc);
        jSONObject.put("lac", cellState.lac);
        jSONObject.put("cid", cellState.cid);
        jSONObject.put(Constants.KEY_SID, cellState.sid);
        jSONObject.put("nid", cellState.nid);
        jSONObject.put("bid", cellState.bid);
        jSONObject.put("signalStrength", cellState.signalStrength);
        jSONObject.put("latitude", cellState.latitude);
        jSONObject.put("longitude", cellState.longitude);
        jSONObject.put("lastUpdateUtcMills", cellState.lastUpdateUtcMills);
        if (cellState.lastUpdateUtcMills < 0) {
            i2 = 0;
        } else {
            i2 = (int) ((this.C - cellState.lastUpdateUtcMills) / 1000);
        }
        jSONObject.put("age", a(i2));
        jSONObject.put("registered", cellState.registered);
        return jSONObject;
    }

    private void a(JSONObject jSONObject, List<AmapLoc> list) throws JSONException {
        if (list != null && list.size() > 0) {
            JSONArray jSONArray = new JSONArray();
            for (AmapLoc jSONObject2 : list) {
                jSONArray.put(jSONObject2.toJSONObject(1));
            }
            jSONObject.put("recentLocs", jSONArray);
        }
    }

    private void b(JSONObject jSONObject, List<HisLocation> list) throws JSONException {
        if (list != null && list.size() > 0) {
            JSONArray jSONArray = new JSONArray();
            for (HisLocation hisLocation : list) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("hisloc", hisLocation.toString());
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("hisLocs", jSONArray);
        }
    }
}
