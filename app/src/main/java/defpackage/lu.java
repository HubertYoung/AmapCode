package defpackage;

import android.text.TextUtils;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import org.json.JSONObject;

/* renamed from: lu reason: default package */
/* compiled from: AppSwitchResponser */
public final class lu extends lr {
    public Boolean a;
    public Boolean b;
    public Boolean c;
    public Boolean d;
    Boolean e;
    Boolean f;
    public Boolean g;
    String h;
    public Boolean i;
    public Boolean j;
    public ly k = new ly();
    public Boolean l;
    public Boolean m;
    public Boolean n;
    public Boolean o;
    public Boolean p;
    public Boolean q;
    private Boolean r;
    private Boolean s;
    private Boolean t;
    private Boolean u;

    /* access modifiers changed from: protected */
    public final void a(JSONObject jSONObject, boolean z) {
        String optString = jSONObject.optString("localapp_flag");
        new Object[1][0] = optString;
        lt.b();
        if (!TextUtils.isEmpty(optString)) {
            this.a = Boolean.valueOf("1".equals(optString));
        }
        String optString2 = jSONObject.optString("traffic_alarm");
        new Object[1][0] = optString2;
        lt.b();
        if (!TextUtils.isEmpty(optString2)) {
            this.c = Boolean.valueOf("1".equals(optString2));
        }
        String optString3 = jSONObject.optString("smart_scenic");
        new Object[1][0] = optString3;
        lt.b();
        if (!TextUtils.isEmpty(optString3)) {
            this.b = Boolean.valueOf("1".equals(optString3));
        }
        String optString4 = jSONObject.optString("ride_switch", null);
        new Object[1][0] = optString4;
        lt.b();
        if (!TextUtils.isEmpty(optString4)) {
            this.f = Boolean.valueOf("0".equals(optString4));
        }
        String optString5 = jSONObject.optString("foot_navigation_switch", null);
        new Object[1][0] = optString5;
        lt.b();
        if (!TextUtils.isEmpty(optString5)) {
            this.s = Boolean.valueOf("1".equals(optString5));
        }
        String optString6 = jSONObject.optString("bus_ticket_switch", null);
        new Object[1][0] = optString6;
        lt.b();
        if (!TextUtils.isEmpty(optString6)) {
            this.e = Boolean.valueOf("1".equals(optString6));
        }
        String optString7 = jSONObject.optString("operation_module_switch", null);
        new Object[1][0] = optString7;
        lt.b();
        if (!TextUtils.isEmpty(optString7)) {
            this.d = Boolean.valueOf("1".equals(optString7));
        }
        String optString8 = jSONObject.optString("shenma_sdk_close_watchdog_android", null);
        new Object[1][0] = optString8;
        lt.b();
        if (!TextUtils.isEmpty(optString8)) {
            this.u = Boolean.valueOf("0".equals(optString8));
        }
        String optString9 = jSONObject.optString("qm_switch", null);
        new Object[1][0] = optString9;
        lt.b();
        if (!TextUtils.isEmpty(optString9)) {
            this.i = Boolean.valueOf("1".equals(optString9));
        }
        String optString10 = jSONObject.optString("iResearch_switch", null);
        new Object[1][0] = optString10;
        lt.b();
        if (!TextUtils.isEmpty(optString10)) {
            this.j = Boolean.valueOf("1".equals(optString10));
        }
        String optString11 = jSONObject.optString(FunctionSupportConfiger.LOCAL_LOG);
        new Object[1][0] = optString11;
        lt.b();
        if (!TextUtils.isEmpty(optString11)) {
            this.h = optString11;
        }
        String optString12 = jSONObject.optString("user_center", null);
        new Object[1][0] = optString12;
        lt.b();
        if (!TextUtils.isEmpty(optString12)) {
            this.t = Boolean.valueOf("1".equals(optString12));
        }
        String optString13 = jSONObject.optString("private_car_switch");
        new Object[1][0] = optString13;
        lt.b();
        if (!TextUtils.isEmpty(optString13)) {
            this.k.a = Boolean.valueOf("1".equals(optString13));
        } else {
            this.k.a = null;
        }
        String optString14 = jSONObject.optString("private_car_tab_switch");
        new Object[1][0] = optString14;
        lt.b();
        if (!TextUtils.isEmpty(optString14)) {
            this.k.b = Boolean.valueOf("1".equals(optString14));
        } else {
            this.k.b = null;
        }
        String optString15 = jSONObject.optString("shenma_sdk_use_http", null);
        new Object[1][0] = optString15;
        lt.b();
        if (!TextUtils.isEmpty(optString15)) {
            this.r = Boolean.valueOf("1".equals(optString15));
        }
        String optString16 = jSONObject.optString("share_state");
        new Object[1][0] = optString16;
        lt.b();
        if (!TextUtils.isEmpty(optString16)) {
            this.l = Boolean.valueOf("1".equals(optString16));
        }
        String optString17 = jSONObject.optString("share_function");
        new Object[1][0] = optString17;
        lt.b();
        if (!TextUtils.isEmpty(optString17)) {
            this.m = Boolean.valueOf("1".equals(optString17));
        }
        String optString18 = jSONObject.optString("share_popup_over100km");
        new Object[1][0] = optString18;
        lt.b();
        if (!TextUtils.isEmpty(optString18)) {
            this.n = Boolean.valueOf("1".equals(optString18));
        }
        String optString19 = jSONObject.optString("share_popup_all");
        new Object[1][0] = optString19;
        lt.b();
        if (!TextUtils.isEmpty(optString19)) {
            this.o = Boolean.valueOf("1".equals(optString19));
        }
        String optString20 = jSONObject.optString("event_report_popup");
        new Object[1][0] = optString20;
        lt.b();
        if (!TextUtils.isEmpty(optString20)) {
            this.p = Boolean.valueOf("1".equals(optString20));
        }
        String optString21 = jSONObject.optString("event_report_button");
        new Object[1][0] = optString21;
        lt.b();
        if (!TextUtils.isEmpty(optString21)) {
            this.q = Boolean.valueOf("1".equals(optString21));
        }
        String optString22 = jSONObject.optString("default_voice_lzl", null);
        new Object[1][0] = optString22;
        lt.b();
        if (!TextUtils.isEmpty(optString22)) {
            this.g = Boolean.valueOf("1".equals(optString22));
        }
    }
}
