package defpackage;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.sdk.sys.a;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.taobao.accs.common.Constants;
import com.tencent.open.SocialConstants;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: evx reason: default package */
/* compiled from: ParamCollector */
public class evx {
    private static final String a = "evx";

    public static JSONObject a(Context context, String str) throws JSONException {
        String str2;
        String str3;
        if (!euw.F(context)) {
            context = context.getApplicationContext();
        }
        JSONObject jSONObject = new JSONObject();
        if (!ewo.h().j) {
            ewo.o(context);
        }
        jSONObject.put("os", ewo.h().e());
        jSONObject.put("_ua", ewo.h().d(context));
        jSONObject.put("type", str);
        jSONObject.put("device_id", ewo.h().j(context));
        jSONObject.put("channel_id", ewo.h().i(context));
        jSONObject.put("ts", euw.a());
        jSONObject.put("v", euw.f());
        jSONObject.put("muid", euw.a(context));
        jSONObject.put(H5Param.SHOW_REPORT_BTN, ewo.h().n(context));
        jSONObject.put(a.h, euw.g());
        jSONObject.put(H5Param.SHOW_DOMAIN, euw.h());
        jSONObject.put("char", euw.i());
        String[] i = euw.i(context);
        if (!euw.a(i, (String) Constants.KEY_MODEL).booleanValue()) {
            jSONObject.put(Constants.KEY_MODEL, ewo.h().d());
        }
        if (!euw.a(i, (String) "network").booleanValue()) {
            jSONObject.put("network", euw.o(context));
        }
        if (!euw.a(i, (String) "_openudid").booleanValue()) {
            jSONObject.put("_openudid", ewo.h().c(context));
        }
        if (!euw.a(i, (String) "_imei").booleanValue()) {
            jSONObject.put("_imei", euw.v(context));
        }
        if (!euw.a(i, (String) "_imei1").booleanValue()) {
            String a2 = ewf.a("mobileanalytics", euw.b(context, 0));
            if (TextUtils.isEmpty(a2)) {
                str3 = "";
            } else {
                str3 = a2.toLowerCase();
            }
            jSONObject.put("_imei1", str3);
        }
        if (!euw.a(i, (String) "_imei2").booleanValue()) {
            String a3 = ewf.a("mobileanalytics", euw.b(context, 1));
            if (TextUtils.isEmpty(a3)) {
                str2 = "";
            } else {
                str2 = a3.toLowerCase();
            }
            jSONObject.put("_imei2", str2);
        }
        if (!euw.a(i, (String) "_androidid").booleanValue()) {
            jSONObject.put("_androidid", ewo.h().a(context));
        }
        if (!euw.a(i, (String) "_mac").booleanValue()) {
            jSONObject.put("_mac", euw.x(context));
        }
        if (!euw.a(i, (String) Constants.KEY_IMEI).booleanValue()) {
            jSONObject.put(Constants.KEY_IMEI, ewo.h().k(context));
        }
        if (!euw.a(i, (String) "androidid").booleanValue()) {
            jSONObject.put("androidid", ewo.h().e(context));
        }
        if (!euw.a(i, (String) "androidid1").booleanValue()) {
            ewo h = ewo.h();
            if (TextUtils.isEmpty(h.b)) {
                h.b = euw.p(context);
            }
            jSONObject.put("androidid1", h.b);
        }
        if (!euw.a(i, (String) "aaid").booleanValue()) {
            jSONObject.put("aaid", ewo.h().a());
        }
        if (!euw.a(i, (String) "mac").booleanValue()) {
            jSONObject.put("mac", euw.D(context));
        }
        if (!euw.a(i, (String) "mac1").booleanValue()) {
            jSONObject.put("mac1", euw.E(context));
        }
        if (!euw.a(i, (String) "os_version").booleanValue()) {
            jSONObject.put("os_version", ewo.h().f());
        }
        if (!euw.a(i, (String) "app_name").booleanValue()) {
            jSONObject.put("app_name", ewo.h().g(context));
        }
        if (!euw.a(i, (String) "app_version").booleanValue()) {
            jSONObject.put("app_version", ewo.h().h(context));
        }
        if (!euw.a(i, (String) "app_code").booleanValue()) {
            jSONObject.put("app_code", ewo.h().f(context));
        }
        if (!euw.a(i, (String) "device_name").booleanValue()) {
            jSONObject.put("device_name", ewo.h().b());
        }
        if (!euw.a(i, (String) "lang").booleanValue()) {
            ewo h2 = ewo.h();
            if (TextUtils.isEmpty(h2.c)) {
                h2.c = Locale.getDefault().getLanguage();
            }
            jSONObject.put("lang", h2.c);
        }
        a(jSONObject);
        return jSONObject;
    }

    private static void a(JSONObject jSONObject) throws JSONException {
        if (!TextUtils.isEmpty(evj.a)) {
            jSONObject.put("cust_id", evj.a);
        }
        if (evj.b == null || evj.b.isEmpty()) {
            euw.a((String) "NO USER PARAMETER IS BEEN SETTLED!");
            return;
        }
        for (String next : evj.b.keySet()) {
            String str = evj.b.get(next);
            if (!jSONObject.has(next)) {
                jSONObject.put(next, str);
                euw.a((String) "USER PARAMETER ADDED INTO INFO");
            } else {
                euw.a((String) "USER PARAMETER CONFLICT WITH SYSTEM PARAMETER");
            }
        }
    }

    public static JSONObject a(Context context) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (!euw.F(context)) {
                context = context.getApplicationContext();
            }
            if (!ewo.h().j) {
                ewo.o(context);
            }
            jSONObject = a(context, (String) "client_data");
            try {
                jSONObject.put("producer", ewo.h().g());
                jSONObject.put("manufacturer", ewo.h().c());
                String[] i = euw.i(context);
                if (!euw.a(i, (String) "package_name").booleanValue()) {
                    jSONObject.put("package_name", ewo.h().m(context));
                }
                if (!euw.a(i, (String) "mccmnc").booleanValue()) {
                    jSONObject.put("mccmnc", ewo.h().l(context));
                }
                if (!euw.a(i, (String) "phone_type").booleanValue()) {
                    jSONObject.put("phone_type", euw.z(context));
                }
                if (!euw.a(i, (String) "have_bt").booleanValue()) {
                    ewo h = ewo.h();
                    if (TextUtils.isEmpty(h.d)) {
                        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                        StringBuilder sb = new StringBuilder();
                        sb.append(defaultAdapter != null);
                        h.d = sb.toString();
                    }
                    jSONObject.put("have_bt", h.d);
                }
                if (!euw.a(i, (String) "have_gps").booleanValue()) {
                    ewo h2 = ewo.h();
                    if (TextUtils.isEmpty(h2.e)) {
                        LocationManager locationManager = (LocationManager) context.getSystemService("location");
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(locationManager != null);
                        h2.e = sb2.toString();
                    }
                    jSONObject.put("have_gps", h2.e);
                }
                if (!euw.a(i, (String) "have_gravity").booleanValue()) {
                    ewo h3 = ewo.h();
                    if (TextUtils.isEmpty(h3.f)) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(euw.l(context));
                        h3.f = sb3.toString();
                    }
                    jSONObject.put("have_gravity", h3.f);
                }
                if (!euw.a(i, (String) Constants.KEY_IMSI).booleanValue()) {
                    ewo h4 = ewo.h();
                    if (TextUtils.isEmpty(h4.g)) {
                        h4.g = euw.A(context);
                    }
                    jSONObject.put(Constants.KEY_IMSI, h4.g);
                }
                if (!euw.a(i, (String) "is_mobile_device").booleanValue()) {
                    ewo h5 = ewo.h();
                    if (TextUtils.isEmpty(h5.h)) {
                        h5.h = "true";
                    }
                    jSONObject.put("is_mobile_device", h5.h);
                }
                if (!euw.a(i, (String) "is_jail_break").booleanValue()) {
                    ewo h6 = ewo.h();
                    if (TextUtils.isEmpty(h6.i)) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(euw.e());
                        h6.i = sb4.toString();
                    }
                    jSONObject.put("is_jail_break", h6.i);
                }
                if (((Boolean) ewp.b(context, "location_state", Boolean.FALSE)).booleanValue()) {
                    String str = (String) ewp.b(context, "location_type", "location_type_system");
                    String str2 = "";
                    String str3 = "";
                    if (str.equals("location_type_system")) {
                        if (!eve.a((Long) ewp.b(context, "system_lat_lon_time", Long.valueOf(System.currentTimeMillis())))) {
                            String str4 = (String) ewp.b(context, "system_lat_lon", "-1");
                            if (!TextUtils.isEmpty(str4) && !str4.equals("-1")) {
                                String[] split = str4.split("&");
                                if (split != null && split.length == 2) {
                                    str2 = split[0];
                                    str3 = split[1];
                                }
                            }
                        }
                    } else if (str.equals("location_type_user") && !eve.a((Long) ewp.b(context, "user_lat_lon_time", Long.valueOf(System.currentTimeMillis())))) {
                        String str5 = (String) ewp.b(context, "user_lat_lon", "-1");
                        if (!TextUtils.isEmpty(str5) && !str5.equals("-1")) {
                            String[] split2 = str5.split("&");
                            if (split2 != null && split2.length == 2) {
                                str2 = split2[0];
                                str3 = split2[1];
                            }
                        }
                    }
                    if (!euw.a(i, (String) LocationParams.PARA_FLP_AUTONAVI_LON).booleanValue()) {
                        jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, str3);
                    }
                    if (!euw.a(i, (String) "lat").booleanValue()) {
                        jSONObject.put("lat", str2);
                    }
                }
                if (!euw.a(i, (String) "have_wifi").booleanValue()) {
                    jSONObject.put("have_wifi", euw.b(context));
                }
            } catch (JSONException unused) {
                euw.c();
                return jSONObject;
            } catch (Exception unused2) {
                euw.c();
                return jSONObject;
            } catch (NoSuchMethodError unused3) {
                euw.c();
                return jSONObject;
            }
        } catch (JSONException unused4) {
            jSONObject = jSONObject2;
            euw.c();
            return jSONObject;
        } catch (Exception unused5) {
            jSONObject = jSONObject2;
            euw.c();
            return jSONObject;
        } catch (NoSuchMethodError unused6) {
            jSONObject = jSONObject2;
            euw.c();
            return jSONObject;
        }
        return jSONObject;
    }

    public static JSONObject a(evy evy, Context context) {
        JSONObject jSONObject;
        try {
            jSONObject = a(context, (String) SocialConstants.PARAM_ACT);
            try {
                jSONObject.put("act_name", evy.b);
                jSONObject.put("act_count", evy.c);
                jSONObject.put(WidgetType.ACTIVITY, evy.d);
            } catch (JSONException e) {
                e = e;
            }
        } catch (JSONException e2) {
            e = e2;
            jSONObject = new JSONObject();
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            euw.a(sb.toString());
            return jSONObject;
        }
        return jSONObject;
    }
}
