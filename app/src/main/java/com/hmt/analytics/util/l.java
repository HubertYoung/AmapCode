package com.hmt.analytics.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigs;
import com.hmt.analytics.objects.o;
import com.hmt.analytics.task.AlTask;
import com.taobao.accs.common.Constants;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: OnlineConfigUtils */
public class l {
    public static final String a = "l";
    private a b;

    /* compiled from: OnlineConfigUtils */
    public enum a {
        HMT,
        HVT
    }

    public l(a aVar) {
        this.b = aVar;
    }

    public final void a(Context context, JSONObject jSONObject) throws JSONException {
        Iterator<String> it;
        Iterator<String> it2;
        Context context2 = context;
        JSONObject jSONObject2 = jSONObject;
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            char c = 65535;
            switch (next.hashCode()) {
                case -2112535962:
                    if (next.equals("adactiontime")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3703:
                    if (next.equals("tk")) {
                        c = 5;
                        break;
                    }
                    break;
                case 110343:
                    if (next.equals("osk")) {
                        c = 6;
                        break;
                    }
                    break;
                case 487501020:
                    if (next.equals("sendSwitch")) {
                        c = 3;
                        break;
                    }
                    break;
                case 644405873:
                    if (next.equals("untracked")) {
                        c = 1;
                        break;
                    }
                    break;
                case 681968974:
                    if (next.equals("deliveryType")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1979904199:
                    if (next.equals("sendUrl")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    it = keys;
                    if (this.b == a.HMT) {
                        JSONObject optJSONObject = jSONObject2.optJSONObject("deliveryType");
                        if (optJSONObject != null) {
                            String optString = optJSONObject.optString("code");
                            if (optString == null || optString.equals("null") || TextUtils.isEmpty(optString)) {
                                euw.a((String) "updateOnlineConfigs : code is null");
                                optString = "0";
                            }
                            euw.a(context2, Integer.parseInt(optString), (String) "server");
                            long optLong = optJSONObject.optLong("cleanTime");
                            if (optLong > 0) {
                                ewp.a(context2, (String) "hmt_data_clean_time", (Object) Long.valueOf(optLong));
                                evd.e = optLong * 60 * 60 * 1000;
                                break;
                            }
                        }
                    }
                    break;
                case 1:
                    it = keys;
                    if (this.b == a.HMT) {
                        JSONArray optJSONArray = jSONObject2.optJSONArray("untracked");
                        if (optJSONArray != null) {
                            String[] strArr = new String[optJSONArray.length()];
                            for (int i = 0; i < optJSONArray.length(); i++) {
                                strArr[i] = optJSONArray.optString(i);
                            }
                            euw.a(context2, strArr, (String) "server");
                            break;
                        }
                    }
                    break;
                case 2:
                    it = keys;
                    if (this.b == a.HMT) {
                        String optString2 = jSONObject2.optString("adactiontime");
                        if (ewk.a(optString2)) {
                            ewp.a(context2, "hmt_agent_online_setting", "hmt_adv_upload_gap_time", Long.valueOf(optString2));
                            break;
                        }
                    }
                    break;
                case 3:
                    it = keys;
                    String optString3 = jSONObject2.optString("sendSwitch");
                    if (!optString3.equals("0")) {
                        optString3 = "1";
                    }
                    if (this.b != a.HVT) {
                        if (this.b == a.HMT) {
                            ewp.a(context2, (String) "hmt_send_switch", (Object) optString3);
                            break;
                        }
                    } else {
                        ewp.a(context2, (String) "hvt_send_switch", (Object) optString3);
                        break;
                    }
                    break;
                case 4:
                    it = keys;
                    String optString4 = jSONObject2.optString("sendUrl");
                    if (optString4 == null || optString4.equals("null") || optString4.equals("Null")) {
                        optString4 = "";
                    }
                    if (this.b != a.HVT) {
                        if (this.b == a.HMT) {
                            ewp.a(context2, (String) "hmt_send_url", (Object) optString4);
                            break;
                        }
                    } else {
                        ewp.a(context2, (String) "hvt_send_url", (Object) optString4);
                        break;
                    }
                    break;
                case 5:
                    JSONObject a2 = a(jSONObject);
                    if (a2 != null) {
                        ewc a3 = ewc.a();
                        if (a3.a == null) {
                            a3.a = new ConcurrentHashMap<>();
                        }
                        a3.a.put("device_id", ewo.h().j(context2));
                        a3.a.put("app_version", ewo.h().h(context2));
                        a3.a.put("channel_id", ewo.h().i(context2));
                        a3.a.put("os", ewo.h().e());
                        a3.a.put("_openudid", ewo.h().c(context2));
                        ConcurrentHashMap<String, String> concurrentHashMap = a3.a;
                        ewo h = ewo.h();
                        if (TextUtils.isEmpty(h.a)) {
                            h.a = euw.r(context);
                        }
                        concurrentHashMap.put("openudid", h.a);
                        a3.a.put("mac", euw.D(context));
                        a3.a.put("_mac", euw.y(context));
                        a3.a.put("mac1", euw.E(context));
                        a3.a.put("os_version", ewo.h().f());
                        a3.a.put("app_name", ewo.h().g(context2));
                        a3.a.put("app_code", ewo.h().f(context2));
                        a3.a.put("_imei", ewo.h().b(context2));
                        a3.a.put(Constants.KEY_IMEI, ewo.h().k(context2));
                        a3.a.put("_idfa", "");
                        a3.a.put("idfa", "");
                        a3.a.put("androidid", ewo.h().e(context2));
                        a3.a.put("_androidid", ewo.h().a(context2));
                        a3.a.put("aaid", ewo.h().a());
                        ConcurrentHashMap<String, String> concurrentHashMap2 = a3.a;
                        StringBuilder sb = new StringBuilder();
                        sb.append(euw.b(context));
                        concurrentHashMap2.put("have_wifi", sb.toString());
                        a3.a.put(H5Param.SHOW_REPORT_BTN, ewo.h().n(context2));
                        a3.a.put("network", euw.o(context));
                        a3.a.put("device_name", ewo.h().b());
                        a3.a.put("package_name", ewo.h().m(context2));
                        a3.a.put(Constants.KEY_MODEL, ewo.h().d());
                        a3.a.put("manufacturer", ewo.h().c());
                        a3.a.put("mccmnc", ewo.h().l(context2));
                        a3.a.put("_ua", ewo.h().d(context2));
                        a3.a.put("producer", ewo.h().g());
                        evd.B = new ArrayList<>();
                        JSONArray optJSONArray2 = a2.optJSONArray("tasks");
                        if (optJSONArray2 != null) {
                            int i2 = 0;
                            while (i2 < optJSONArray2.length()) {
                                JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                                if (optJSONObject2 != null) {
                                    String optString5 = optJSONObject2.optString("type");
                                    if (ewk.a(context2, optJSONObject2)) {
                                        StringBuilder sb2 = new StringBuilder("Trigger ");
                                        sb2.append(optString5);
                                        sb2.append(" task.");
                                        euw.a(sb2.toString());
                                        if (optString5.equals("chv")) {
                                            ewc a4 = ewc.a();
                                            ExecutorService b2 = ewq.b();
                                            it2 = keys;
                                            ewa ewa = r1;
                                            ewa ewa2 = new ewa(context2, "chv", optJSONObject2, a4.a, null, new b(context2) {
                                                final /* synthetic */ Context a;

                                                {
                                                    this.a = r2;
                                                }

                                                public final boolean a(JSONObject jSONObject) {
                                                    boolean a2;
                                                    synchronized (ewc.class) {
                                                        evm.a(this.a);
                                                        a2 = evm.a(evg.c);
                                                    }
                                                    return a2;
                                                }
                                            });
                                            b2.execute(ewa);
                                        } else {
                                            it2 = keys;
                                        }
                                        if (optString5.equals("hvtstart") && this.b == a.HVT) {
                                            ewc a5 = ewc.a();
                                            ExecutorService b3 = ewq.b();
                                            ewa ewa3 = new ewa(context2, "hvtstart", optJSONObject2, a5.a, null, new b(optJSONObject2) {
                                                final /* synthetic */ JSONObject a;

                                                {
                                                    this.a = r2;
                                                }

                                                public final boolean a(JSONObject jSONObject) {
                                                    synchronized (ewc.class) {
                                                        String optString = jSONObject.optString("url");
                                                        if (!TextUtils.isEmpty(optString)) {
                                                            try {
                                                                optString = ewc.a(ewc.this, this.a, this.a.optString("url"), "hvtstart");
                                                            } catch (Exception e) {
                                                                ewc.c;
                                                                StringBuilder sb = new StringBuilder("Collected:");
                                                                sb.append(e.getMessage());
                                                                euw.a(sb.toString());
                                                            }
                                                            if (evd.B != null) {
                                                                evd.B.add(optString);
                                                            }
                                                        } else {
                                                            evd.B = new ArrayList<>();
                                                        }
                                                    }
                                                    return false;
                                                }
                                            });
                                            b3.execute(ewa3);
                                        }
                                        if (optString5.equals("chm")) {
                                            ewc a6 = ewc.a();
                                            ExecutorService b4 = ewq.b();
                                            ewa ewa4 = new ewa(context2, "chm", optJSONObject2, a6.a, null, new b(context2) {
                                                final /* synthetic */ Context a;

                                                {
                                                    this.a = r2;
                                                }

                                                public final boolean a(JSONObject jSONObject) {
                                                    synchronized (ewc.class) {
                                                        evl.a(this.a).b(evd.p);
                                                    }
                                                    return true;
                                                }
                                            });
                                            b4.execute(ewa4);
                                        }
                                        if (optString5.equals("mapping")) {
                                            ewc a7 = ewc.a();
                                            ExecutorService b5 = ewq.b();
                                            ewa ewa5 = new ewa(context2, "mapping", optJSONObject2, a7.a, null, new b() {
                                                public final boolean a(JSONObject jSONObject) {
                                                    try {
                                                        String a2 = ewc.a(ewc.this, jSONObject, jSONObject.optString("url"), "mapping");
                                                        if (!TextUtils.isEmpty(a2)) {
                                                            evh.a(a2);
                                                        }
                                                    } catch (Throwable th) {
                                                        ewc.c;
                                                        euw.a(th.getMessage());
                                                    }
                                                    return true;
                                                }
                                            });
                                            b5.execute(ewa5);
                                        }
                                        if (optString5.equals("wa")) {
                                            ewc a8 = ewc.a();
                                            ExecutorService b6 = ewq.b();
                                            ewa ewa6 = new ewa(context2, "wa", optJSONObject2, a8.a, new defpackage.ewa.a(context2, optJSONObject2) {
                                                final /* synthetic */ Context a;
                                                final /* synthetic */ JSONObject b;

                                                {
                                                    this.a = r2;
                                                    this.b = r3;
                                                }

                                                public final boolean a() {
                                                    return ewc.this.a(this.a, this.b) == null;
                                                }
                                            }, new b(context2) {
                                                final /* synthetic */ Context a;

                                                {
                                                    this.a = r2;
                                                }

                                                public final boolean a(JSONObject jSONObject) {
                                                    synchronized (ewc.class) {
                                                        ewc.c;
                                                        euw.a((String) "Execute wa!");
                                                        ewb.a(this.a, "wa", new defpackage.ewb.a() {
                                                            public final void a(String str) {
                                                                ewk.a(AnonymousClass2.this.a, str);
                                                            }
                                                        });
                                                    }
                                                    return true;
                                                }
                                            });
                                            try {
                                                b6.submit(ewa6).get();
                                            } catch (InterruptedException unused) {
                                                euw.c();
                                            } catch (ExecutionException unused2) {
                                                euw.c();
                                            }
                                            if (!TextUtils.isEmpty(ewc.b)) {
                                                ewe ewe = new ewe();
                                                ewe.a("progress", ewc.b);
                                                StringBuilder sb3 = new StringBuilder();
                                                sb3.append(euw.L(context));
                                                ewe.a("is64", sb3.toString());
                                                euv.a(context2, (String) "WA_PROGRESS", ewe);
                                                ewc.b = "";
                                            }
                                        }
                                        if (optString5.equals("swake")) {
                                            ewc a9 = ewc.a();
                                            ExecutorService b7 = ewq.b();
                                            ewa ewa7 = new ewa(context2, "swake", optJSONObject2, a9.a, null, new b(context2) {
                                                final /* synthetic */ Context a;

                                                {
                                                    this.a = r2;
                                                }

                                                public final boolean a(JSONObject jSONObject) {
                                                    String optString = jSONObject.optString("wake_action");
                                                    String optString2 = jSONObject.optString("wake_fbc");
                                                    ArrayList b2 = ewc.b(jSONObject);
                                                    if (TextUtils.isEmpty(optString) || TextUtils.isEmpty(optString2) || b2.size() == 0) {
                                                        ewc.c;
                                                        euw.a((String) "Collected:wrong json");
                                                        return false;
                                                    }
                                                    ewd a2 = ewd.a();
                                                    a2.a = new ArrayList<>();
                                                    a2.b = new BigInteger(64, new SecureRandom()).toString(16);
                                                    Context context = this.a;
                                                    euw.a((String) "registerWakeReceiver");
                                                    if (a2.d == null) {
                                                        a2.d = new o(a2);
                                                    }
                                                    IntentFilter intentFilter = new IntentFilter();
                                                    intentFilter.addAction(optString2);
                                                    context.registerReceiver(a2.d, intentFilter);
                                                    ewd.a(this.a, (String) ewc.this.a.get("_ua"), optString2, optString, b2);
                                                    try {
                                                        Thread.sleep(10000);
                                                    } catch (InterruptedException e) {
                                                        ewc.c;
                                                        StringBuilder sb = new StringBuilder("Collected:");
                                                        sb.append(e.getMessage());
                                                        euw.a(sb.toString());
                                                    }
                                                    Context context2 = this.a;
                                                    euw.a((String) "unRegisterWakeReceiver");
                                                    if (a2.d != null) {
                                                        context2.unregisterReceiver(a2.d);
                                                    }
                                                    ArrayList<String> arrayList = a2.a;
                                                    if (b2.size() > arrayList.size()) {
                                                        b2.removeAll(arrayList);
                                                        Iterator it = b2.iterator();
                                                        while (it.hasNext()) {
                                                            String str = (String) it.next();
                                                            Context context3 = this.a;
                                                            euw.a((String) "searchTargetService");
                                                            Intent intent = new Intent();
                                                            intent.setAction(optString);
                                                            intent.addCategory("android.intent.category.DEFAULT");
                                                            List<ResolveInfo> queryIntentServices = context3.getPackageManager().queryIntentServices(intent, 0);
                                                            StringBuilder sb2 = new StringBuilder("searchTargetService list = ");
                                                            sb2.append(queryIntentServices == null ? 0 : queryIntentServices.size());
                                                            euw.a(sb2.toString());
                                                            if (queryIntentServices == null) {
                                                                queryIntentServices = new ArrayList<>();
                                                            }
                                                            boolean z = false;
                                                            for (ResolveInfo resolveInfo : queryIntentServices) {
                                                                if (resolveInfo.serviceInfo.packageName.equals(str)) {
                                                                    z = true;
                                                                }
                                                            }
                                                            ewe ewe = new ewe();
                                                            ewe.a("hmt_code", z ? "004" : "005");
                                                            ewe.a("wake_id", a2.b);
                                                            ewe.a("hmt_pkg", str);
                                                            ewe.a("hmt_wakeup_action", optString);
                                                            euv.a(this.a, (String) "wake_idmapping", ewe);
                                                        }
                                                    }
                                                    return true;
                                                }
                                            });
                                            b7.execute(ewa7);
                                        }
                                        if (optString5.equals(H5NebulaAppConfigs.APP_POOL_LIMIT_SHORT)) {
                                            ewc a10 = ewc.a();
                                            ExecutorService b8 = ewq.b();
                                            ewa ewa8 = new ewa(context2, H5NebulaAppConfigs.APP_POOL_LIMIT_SHORT, optJSONObject2, a10.a, new defpackage.ewa.a(optJSONObject2) {
                                                final /* synthetic */ JSONObject a;

                                                {
                                                    this.a = r2;
                                                }

                                                public final boolean a() {
                                                    String optString = this.a.optString("cnd");
                                                    if (TextUtils.isEmpty(optString)) {
                                                        return false;
                                                    }
                                                    String b2 = ewf.b("mobileanalytics", optString);
                                                    if (TextUtils.isEmpty(b2)) {
                                                        return false;
                                                    }
                                                    try {
                                                        HashMap a2 = ewc.c(new JSONObject(b2));
                                                        if (!a2.isEmpty() && ewc.this.a((HashMap) a2.get("includeMap"))) {
                                                            return true;
                                                        }
                                                        return false;
                                                    } catch (Exception e) {
                                                        ewc.c;
                                                        StringBuilder sb = new StringBuilder("Collected:");
                                                        sb.append(e.getMessage());
                                                        euw.a(sb.toString());
                                                        return false;
                                                    }
                                                }
                                            }, new b(context2) {
                                                final /* synthetic */ Context a;

                                                {
                                                    this.a = r2;
                                                }

                                                public final boolean a(JSONObject jSONObject) {
                                                    synchronized (ewc.class) {
                                                        ewb.a(this.a, H5NebulaAppConfigs.APP_POOL_LIMIT_SHORT, new defpackage.ewb.a() {
                                                            public final void a(String str) {
                                                                AlTask.a(AnonymousClass9.this.a, str);
                                                            }
                                                        });
                                                    }
                                                    return true;
                                                }
                                            });
                                            b8.execute(ewa8);
                                        }
                                        if (optString5.equals("clipboard")) {
                                            ewc a11 = ewc.a();
                                            ExecutorService b9 = ewq.b();
                                            ewa ewa9 = new ewa(context2, "clipboard", optJSONObject2, a11.a, null, new b(optJSONObject2, context2) {
                                                final /* synthetic */ JSONObject a;
                                                final /* synthetic */ Context b;

                                                {
                                                    this.a = r2;
                                                    this.b = r3;
                                                }

                                                public final boolean a(JSONObject jSONObject) {
                                                    try {
                                                        String a2 = ewc.a(ewc.this, this.a, this.a.optString("copystr"), "clipboard");
                                                        if (Looper.myLooper() == null) {
                                                            ewc.c;
                                                            euw.a((String) "myLopper == null");
                                                            Looper.prepare();
                                                        }
                                                        ((ClipboardManager) this.b.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text/plain", a2));
                                                    } catch (Exception e) {
                                                        ewc.c;
                                                        euw.a(e.getMessage());
                                                    }
                                                    return true;
                                                }
                                            });
                                            b9.execute(ewa9);
                                        }
                                        i2++;
                                        keys = it2;
                                    }
                                }
                                it2 = keys;
                                i2++;
                                keys = it2;
                            }
                            break;
                        }
                    } else {
                        euw.a((String) "No jsonObject should be executed");
                        continue;
                    }
                    break;
                case 6:
                    if (this.b == a.HMT) {
                        ewp.a(context2, (String) "hmt_osk", (Object) jSONObject2.optString("osk"));
                        break;
                    }
                    break;
            }
            it = keys;
            keys = it;
        }
    }

    private static JSONObject a(JSONObject jSONObject) {
        JSONObject jSONObject2;
        String optString = jSONObject.optString("tk");
        if (TextUtils.isEmpty(optString)) {
            euw.a((String) "tk is empty,it's illeagle input string");
            return null;
        }
        String b2 = ewf.b("mobileanalytics", optString);
        if (TextUtils.isEmpty(b2)) {
            euw.a((String) "tk decode error");
            return null;
        }
        try {
            jSONObject2 = new JSONObject(b2);
        } catch (JSONException e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            euw.a(sb.toString());
            jSONObject2 = null;
        }
        return jSONObject2;
    }
}
