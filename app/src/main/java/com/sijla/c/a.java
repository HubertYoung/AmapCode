package com.sijla.c;

import android.content.Context;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.autonavi.common.SuperId;
import com.sijla.g.a.b;
import com.sijla.g.a.c;
import com.sijla.g.f;
import com.sijla.g.h;
import com.sijla.g.i;
import com.sijla.g.j;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class a {
    public static void a(Context context) {
        try {
            String e = b.e(context);
            StringBuilder sb = new StringBuilder();
            sb.append(e);
            sb.append("config");
            String sb2 = sb.toString();
            File file = new File(sb2);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(".gz");
            File file2 = new File(sb3.toString());
            if (!file.exists() || !file.isFile()) {
                j.a(context, "cfgver", "");
                a(context, file2);
            } else {
                if (Math.abs((System.currentTimeMillis() / 1000) - (file.lastModified() / 1000)) > ((long) c.a.optInt("cfgitl", 28800))) {
                    a(context, file2);
                }
            }
            b(context, file);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static void b(Context context) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(b.e(context));
            sb.append("f7");
            File file = new File(sb.toString());
            if (file.exists()) {
                JSONArray e = com.sijla.g.b.e(c.c(file));
                if (e != null && e.length() > 0) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < e.length(); i++) {
                        JSONObject optJSONObject = e.optJSONObject(i);
                        if (optJSONObject != null) {
                            Iterator<String> keys = optJSONObject.keys();
                            while (keys.hasNext()) {
                                String next = keys.next();
                                if (com.sijla.g.a.a.c(context, next)) {
                                    JSONArray optJSONArray = optJSONObject.optJSONArray(next);
                                    if (optJSONArray != null && optJSONArray.length() > 0) {
                                        for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                                            String optString = optJSONArray.optString(i2);
                                            if (optString != null) {
                                                File file2 = new File(b.a(optString).toString());
                                                if (file2.exists()) {
                                                    com.sijla.bean.a aVar = new com.sijla.bean.a();
                                                    aVar.a(next);
                                                    aVar.a(file2);
                                                    arrayList.add(aVar);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        c.d = arrayList;
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static void c(final Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appid", context.getPackageName());
            jSONObject.put("appver", com.sijla.g.a.a.j(context));
            jSONObject.put("qid", i.b(context));
            jSONObject.put("sdkver", com.sijla.b.a.a);
            List<String> u = com.sijla.g.a.a.u(context.getApplicationContext());
            if (u != null) {
                JSONArray jSONArray = new JSONArray();
                for (String put : u) {
                    jSONArray.put(put);
                }
                jSONObject.put("aps", jSONArray);
                JSONObject a = com.sijla.g.b.a(jSONObject);
                if (a != null) {
                    String optString = c.a.optString("dmcfgurl", null);
                    if (optString != null && com.sijla.g.a.a.g(context)) {
                        h.a(optString, a, (com.sijla.g.h.a) new com.sijla.g.h.a() {
                            public final void a(String str) {
                            }

                            public final void a(String str, JSONObject jSONObject) {
                                if (jSONObject != null) {
                                    JSONArray optJSONArray = jSONObject.optJSONArray("data");
                                    if (optJSONArray != null && optJSONArray.length() > 0) {
                                        String a2 = com.sijla.g.b.a(optJSONArray);
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(b.e(context));
                                        sb.append("f7");
                                        c.a(a2, sb.toString(), false);
                                    }
                                }
                            }
                        }, true);
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(Context context, File file) {
        String optString = c.a.optString("cfgurl", "http://log.qchannel03.cn/n/dpz/");
        if (com.sijla.g.b.a(optString)) {
            optString = "http://log.qchannel03.cn/n/dpz/";
        }
        File a = new b(context, optString, file.getAbsolutePath()).a();
        if (a != null && a.exists() && a.isFile()) {
            try {
                f.a(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void b(Context context, File file) {
        if (file != null && file.exists() && !file.isDirectory()) {
            try {
                String trim = c.c(file).trim();
                if (!com.sijla.g.b.a(trim)) {
                    JSONObject d = com.sijla.g.b.d(trim);
                    if (d != null) {
                        c.c = d.optString("version", "");
                        j.a(context, "cfgver", c.c);
                        JSONObject optJSONObject = d.optJSONObject(RpcConstant.BASE);
                        if (optJSONObject != null) {
                            c.a = optJSONObject;
                        }
                        JSONArray optJSONArray = d.optJSONArray("apk");
                        if (optJSONArray != null) {
                            c.b.clear();
                            for (int i = 0; i < optJSONArray.length(); i++) {
                                JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                                if (optJSONObject2 != null) {
                                    String optString = optJSONObject2.optString(SuperId.BIT_1_REALTIMEBUS_BUSSTATION);
                                    String optString2 = optJSONObject2.optString("v");
                                    String optString3 = optJSONObject2.optString(SuperId.BIT_1_MAIN_BUSSTATION);
                                    if (!(optString2 == null || optString == null)) {
                                        c.b.add(new String[]{optString, optString2, optString3});
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
