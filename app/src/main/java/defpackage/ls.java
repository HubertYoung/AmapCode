package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressFBWarnings({"SBSC_USE_STRINGBUFFER_CONCATENATION"})
/* renamed from: ls reason: default package */
/* compiled from: AppInitResponser */
public final class ls extends lr {
    public Boolean A;
    public mb B;
    private boolean C;
    private String D;
    private JSONObject E;
    private String F = "";
    private Boolean G;
    private String H;
    private lx I;
    public lv a = new lv();
    public lz b = new lz();
    public lw c = new lw();
    public String d;
    public Boolean e;
    public String f = "0";
    Boolean g;
    public long h = -1;
    public long i = -1;
    public JSONObject j;
    String k = "";
    public String l = "";
    public JSONObject m;
    String n;
    public String o = "";
    public String p = "";
    public String q = "";
    public String r = "";
    Boolean s;
    Boolean t;
    String u;
    public String v;
    public lx w;
    public String x;
    public JSONObject y;
    public List<ma> z;

    private void a(JSONObject jSONObject) {
        JSONArray optJSONArray = jSONObject.optJSONArray("three_d_cities");
        if (optJSONArray != null && optJSONArray.length() != 0) {
            this.z = new ArrayList();
            int i2 = 0;
            while (i2 < optJSONArray.length()) {
                try {
                    JSONObject jSONObject2 = (JSONObject) optJSONArray.get(i2);
                    ma maVar = new ma();
                    maVar.a = jSONObject2.optString(AutoJsonUtils.JSON_ADCODE);
                    maVar.b = jSONObject2.optString("name");
                    this.z.add(maVar);
                    i2++;
                } catch (JSONException unused) {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void a(JSONObject jSONObject, boolean z2) {
        String str;
        JSONObject optJSONObject = jSONObject.optJSONObject("realtime_bus");
        new Object[1][0] = optJSONObject != null ? optJSONObject.toString() : "";
        lt.b();
        this.j = optJSONObject;
        JSONObject optJSONObject2 = jSONObject.optJSONObject("map");
        new Object[1][0] = optJSONObject2 != null ? optJSONObject2.toString() : "";
        lt.b();
        if (optJSONObject2 != null) {
            this.k = optJSONObject2.optString("onlineversion");
            this.l = optJSONObject2.optString("downversion");
        }
        JSONObject optJSONObject3 = jSONObject.optJSONObject("taxi_service");
        new Object[1][0] = optJSONObject3 != null ? optJSONObject3.toString() : "";
        lt.b();
        this.m = optJSONObject3;
        JSONArray optJSONArray = jSONObject.optJSONArray("memo");
        new Object[1][0] = optJSONArray != null ? optJSONArray.toString() : "";
        lt.b();
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                JSONObject optJSONObject4 = optJSONArray.optJSONObject(i2);
                if (optJSONObject4 != null) {
                    int optInt = optJSONObject4.optInt("msgid");
                    new Object[1][0] = Integer.valueOf(optInt);
                    lt.b();
                    switch (optInt) {
                        case 1001:
                            this.a.d = true;
                            this.a.a = optJSONObject4.optString("para1");
                            this.a.c = optJSONObject4.optString("para2");
                            this.a.b = optJSONObject4.optString("para3");
                            this.a.e = optJSONObject4.optString(LocationParams.PARA_COMMON_DIV);
                            this.a.f = optJSONObject4.optString("package");
                            this.a.g = optJSONObject4.optString("name");
                            this.a.h = optJSONObject4.optBoolean(LogConstant.SPLASH_SCREEN_DOWNLOADED);
                            this.a.i = optJSONObject4.optString("url");
                            this.a.j = optJSONObject4.optString(H5Param.MENU_ICON);
                            this.a.k = optJSONObject4.optString("scheme");
                            this.a.l = optJSONObject4.optString("days");
                            this.a.n = optJSONObject4.optString("build");
                            this.a.o = optJSONObject4.optString("span");
                            this.a.m = optJSONObject4.optString("appver");
                            this.a.p = optJSONObject4.optInt("force") == 1;
                            break;
                        case 1002:
                            this.b.a = true;
                            this.b.b = optJSONObject4.optString("para1");
                            break;
                        case 1003:
                            this.c.a = optJSONObject4.optString("para1");
                            break;
                    }
                }
            }
        }
        String optString = jSONObject.optString("flash_beginner_days", null);
        new Object[1][0] = optString;
        lt.b();
        if (!TextUtils.isEmpty(optString)) {
            this.D = optString;
        }
        JSONObject optJSONObject5 = jSONObject.optJSONObject("pushservice_runtime");
        new Object[1][0] = optJSONObject5 != null ? optJSONObject5.toString() : "";
        lt.b();
        if (optJSONObject5 != null) {
            this.h = optJSONObject5.optLong(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, -1);
            this.i = optJSONObject5.optLong(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, -1);
        }
        String optString2 = jSONObject.optString("newmap_flag", null);
        new Object[1][0] = optString2;
        lt.b();
        if (!TextUtils.isEmpty(optString2)) {
            this.g = Boolean.valueOf("1".equals(optString2));
        }
        JSONObject optJSONObject6 = jSONObject.optJSONObject("globaldb_info");
        new Object[1][0] = optJSONObject6 != null ? optJSONObject6.toString() : "";
        lt.b();
        if (optJSONObject6 != null) {
            JSONObject optJSONObject7 = optJSONObject6.optJSONObject("globaldb");
            if (optJSONObject7 != null) {
                str = optJSONObject7.toString();
            } else {
                str = "";
            }
            this.x = str;
        }
        JSONObject optJSONObject8 = jSONObject.optJSONObject("guide");
        new Object[1][0] = optJSONObject8 != null ? optJSONObject8.toString() : "";
        lt.b();
        this.E = optJSONObject8;
        String optString3 = jSONObject.optString("splash_screen_source", null);
        new Object[1][0] = optString3;
        lt.b();
        if (!TextUtils.isEmpty(optString3)) {
            this.d = optString3;
        }
        try {
            JSONObject optJSONObject9 = jSONObject.optJSONObject("scheme");
            new Object[1][0] = optJSONObject9 != null ? optJSONObject9.toString() : "";
            lt.b();
            this.y = optJSONObject9;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        JSONArray optJSONArray2 = jSONObject.optJSONArray("flashpic");
        new Object[1][0] = optJSONArray2 != null ? optJSONArray2.toString() : "";
        lt.b();
        if (optJSONArray2 != null) {
            this.n = optJSONArray2.toString();
        }
        String optString4 = jSONObject.optString("bus_collect", null);
        new Object[1][0] = optString4;
        lt.b();
        if (!TextUtils.isEmpty(optString4)) {
            this.e = Boolean.valueOf("1".equals(optString4));
        }
        JSONArray optJSONArray3 = jSONObject.optJSONArray("conf_version");
        new Object[1][0] = optJSONArray3 != null ? optJSONArray3.toString() : "";
        lt.b();
        if (optJSONArray3 != null) {
            for (int i3 = 0; i3 < optJSONArray3.length(); i3++) {
                JSONObject optJSONObject10 = optJSONArray3.optJSONObject(i3);
                if (optJSONObject10 != null && "2048".equals(optJSONObject10.optString("type"))) {
                    this.F = optJSONObject10.optString("version");
                } else if (optJSONObject10 != null && SuperId.BIT_2_REALTIMEBUS_BUSSTATION.equals(optJSONObject10.optString("type"))) {
                    this.o = optJSONObject10.optString("version");
                } else if (optJSONObject10 != null && "1024".equals(optJSONObject10.optString("type"))) {
                    this.p = optJSONObject10.optString("version");
                } else if (optJSONObject10 != null && "4096".equals(optJSONObject10.optString("type"))) {
                    this.q = optJSONObject10.optString("version");
                } else if (optJSONObject10 != null && "8192".equals(optJSONObject10.optString("type"))) {
                    this.r = optJSONObject10.optString("version");
                }
            }
        }
        JSONObject optJSONObject11 = jSONObject.optJSONObject("main_entrance");
        new Object[1][0] = optJSONObject11 != null ? optJSONObject11.toString() : "";
        lt.b();
        if (optJSONObject11 != null) {
            JSONObject optJSONObject12 = optJSONObject11.optJSONObject(FunctionSupportConfiger.SWITCH_TAG);
            JSONObject optJSONObject13 = optJSONObject11.optJSONObject("data");
            if (optJSONObject12 != null) {
                this.G = Boolean.valueOf(optJSONObject12.optString("commuter").equals("1"));
                this.s = Boolean.valueOf(optJSONObject12.optString("realtimebus").equals("1"));
                this.t = Boolean.valueOf("1".equals(optJSONObject12.optString(AmapMessage.TOKEN_REAL3D)));
            }
            String str2 = "";
            String str3 = "";
            String str4 = "";
            if (optJSONObject13 != null) {
                Iterator<String> keys = optJSONObject13.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    JSONArray optJSONArray4 = optJSONObject13.optJSONArray(next);
                    if (optJSONArray4 != null) {
                        int i4 = 0;
                        while (true) {
                            if (i4 < optJSONArray4.length()) {
                                if (optJSONArray4.optString(i4).equals("commuter")) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(str2);
                                    sb.append(next);
                                    sb.append(",");
                                    str2 = sb.toString();
                                } else {
                                    i4++;
                                }
                            }
                        }
                        int i5 = 0;
                        while (true) {
                            if (i5 < optJSONArray4.length()) {
                                if (optJSONArray4.optString(i5).equals("realtimebus")) {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(str3);
                                    sb2.append(next);
                                    sb2.append(",");
                                    str3 = sb2.toString();
                                } else {
                                    i5++;
                                }
                            }
                        }
                        int i6 = 0;
                        while (true) {
                            if (i6 < optJSONArray4.length()) {
                                if (optJSONArray4.optString(i6).equals(AmapMessage.TOKEN_REAL3D)) {
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append(str4);
                                    sb3.append(next);
                                    sb3.append(",");
                                    str4 = sb3.toString();
                                } else {
                                    i6++;
                                }
                            }
                        }
                    }
                }
            }
            this.H = str2;
            this.u = str3;
            this.v = str4;
        }
        JSONObject optJSONObject14 = jSONObject.optJSONObject("share_bicycle_switch");
        new Object[1][0] = optJSONObject14 != null ? optJSONObject14.toString() : "";
        lt.b();
        if (optJSONObject14 != null) {
            this.I = new lx();
            String optString5 = optJSONObject14.optString("basemap_switch");
            new Object[1][0] = optString5;
            lt.b();
            if (!TextUtils.isEmpty(optString5)) {
                this.I.a = Boolean.valueOf(optString5.equals("1"));
            } else {
                this.I.a = null;
            }
            String optString6 = optJSONObject14.optString("bus_switch");
            new Object[1][0] = optString6;
            lt.b();
            if (!TextUtils.isEmpty(optString6)) {
                this.I.b = Boolean.valueOf(optString6.equals("1"));
            } else {
                this.I.b = null;
            }
            if (!z2) {
                this.w = new lx(this.I);
            }
        }
        String optString7 = jSONObject.optString("navigation_abtest", null);
        new Object[1][0] = optString7;
        lt.b();
        if (!TextUtils.isEmpty(optString7)) {
            this.A = Boolean.valueOf("1".equals(optString7));
        }
        JSONObject optJSONObject15 = jSONObject.optJSONObject("use_pay_entrance");
        new Object[1][0] = optJSONObject15 != null ? optJSONObject15.toString() : "";
        lt.b();
        if (optJSONObject15 != null) {
            this.f = optJSONObject15.optString("my_page");
            if (TextUtils.isEmpty(this.f)) {
                this.f = "0";
            }
        }
        a(jSONObject);
        JSONObject optJSONObject16 = jSONObject.optJSONObject("voice_common");
        new Object[1][0] = optJSONObject16 != null ? optJSONObject16.toString() : "";
        lt.b();
        if (optJSONObject16 != null) {
            mb mbVar = new mb();
            String optString8 = optJSONObject16.optString("path");
            new Object[1][0] = optString8;
            lt.b();
            if (!TextUtils.isEmpty(optString8)) {
                mbVar.a = optString8;
            }
            String optString9 = optJSONObject16.optString("md5");
            new Object[1][0] = optString9;
            lt.b();
            if (!TextUtils.isEmpty(optString9)) {
                mbVar.b = optString9;
            }
            this.B = mbVar;
        }
        this.C = !z2;
        if (this.C) {
            ahn.b().execute(new Runnable() {
                public final void run() {
                    ll.a().b();
                }
            });
        }
    }
}
