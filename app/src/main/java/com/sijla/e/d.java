package com.sijla.e;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.sijla.a.a;
import com.sijla.g.a.c;
import com.sijla.g.b;
import com.sijla.g.h;
import com.sijla.g.i;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends b {
    private static boolean d;
    /* access modifiers changed from: private */
    public Context b;
    private boolean c;

    public void a(Intent intent) {
    }

    public d(Context context) {
        this(context, false);
    }

    public d(Context context, boolean z) {
        this.b = context;
        this.c = z;
        this.a = "UDFER";
    }

    public void h() {
        a.a(new Runnable() {
            public void run() {
                d.this.a((String) "onScreenOff");
            }
        });
    }

    public List<File> a(Context context, String str) {
        ArrayList arrayList = new ArrayList();
        if (!b.a(str)) {
            File file = new File(str);
            if (file.exists()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    HashMap hashMap = new HashMap();
                    for (File file2 : listFiles) {
                        if (file2 != null && file2.exists()) {
                            String name = file2.getName();
                            if (file2.isDirectory() || !name.contains(".csv")) {
                                c.a(file2);
                            } else if (!name.endsWith(".lock")) {
                                try {
                                    String substring = name.substring(0, name.indexOf(".csv"));
                                    StringBuilder sb = new StringBuilder();
                                    if (hashMap.containsKey(substring)) {
                                        sb.append((StringBuilder) hashMap.get(substring));
                                    }
                                    String b2 = b(b.b(b.a(file2)));
                                    if (!TextUtils.isEmpty(b2)) {
                                        sb.append(b2);
                                        hashMap.put(substring, sb);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                file2.delete();
                            }
                        }
                    }
                    for (String str2 : hashMap.keySet()) {
                        String sb2 = ((StringBuilder) hashMap.get(str2)).toString();
                        if (!TextUtils.isEmpty(sb2)) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str2);
                            sb3.append(".csv-");
                            sb3.append(System.currentTimeMillis());
                            String sb4 = sb3.toString();
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str);
                            sb5.append(sb4);
                            File a = c.a(sb5.toString(), b.g(sb2));
                            if (a != null && a.exists()) {
                                arrayList.add(a);
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private static String b(String str) {
        return b.c(str);
    }

    public void a(Context context) {
        String[] strArr = {com.sijla.g.a.b.c(context), com.sijla.g.a.b.d(context)};
        for (int i = 0; i < 2; i++) {
            String str = strArr[i];
            try {
                a(context, str);
                a(context, str, true);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private static void a(Context context, String str, boolean z) {
        String str2;
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null && listFiles.length != 0) {
            HashMap hashMap = new HashMap();
            for (File file : listFiles) {
                if (file != null && file.exists() && file.isFile()) {
                    hashMap.put(file.getName(), file);
                }
            }
            try {
                JSONArray optJSONArray = com.sijla.c.c.a.optJSONArray("truthurls");
                if (optJSONArray == null || optJSONArray.length() == 0) {
                    optJSONArray = new JSONArray();
                    optJSONArray.put("https://www.qchannel01.cn/center/ard");
                    optJSONArray.put("https://b.qchannel03.cn/n/ard");
                }
                String a = a(context, z);
                ArrayList arrayList = new ArrayList();
                int i = com.sijla.c.c.a.optInt("repeatReportTruth", 0) == 0 ? 1 : 0;
                int i2 = 0;
                boolean z2 = false;
                while (true) {
                    if (i2 >= optJSONArray.length()) {
                        break;
                    }
                    String optString = optJSONArray.optString(i2, "https://www.qchannel01.cn/center/ard");
                    new com.sijla.b.c();
                    if (i2 > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(a);
                        sb.append("&r=");
                        sb.append(i);
                        str2 = sb.toString();
                    } else {
                        str2 = a;
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(optString);
                    sb2.append(str2);
                    com.sijla.b.c a2 = h.a(sb2.toString(), new JSONObject(), (Map<String, File>) hashMap);
                    new StringBuilder("STATUS:").append(a2.b());
                    if (a2.b()) {
                        if (!z2) {
                            z2 = true;
                        }
                        if (i != 0) {
                            break;
                        }
                    } else {
                        arrayList.add(new String[]{optString, a2.a().toString(), com.sijla.g.d.a()});
                    }
                    i2++;
                }
                a(str, z2);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private static String a(Context context, boolean z) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("appkey", b.g(context));
            jSONObject2.put(Oauth2AccessToken.KEY_UID, i.b(context));
            StringBuilder sb = new StringBuilder();
            sb.append(com.sijla.b.a.a);
            jSONObject2.put(GlobalConstants.EXCEPTIONTYPE, sb.toString());
            if ("".equals(com.sijla.c.c.a.optString("rootdir", ""))) {
                jSONObject2.put(LogItem.MM_C02_K4_SM, "1");
            }
            jSONObject = b.a(jSONObject2);
            try {
                jSONObject.put("m", "1");
                if (z) {
                    jSONObject.put("type", "3");
                }
            } catch (JSONException e) {
                jSONObject2 = jSONObject;
                e = e;
                e.printStackTrace();
                jSONObject = jSONObject2;
                return "?".concat(String.valueOf(com.sijla.g.b.a.a().a(jSONObject).toString()));
            }
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            jSONObject = jSONObject2;
            return "?".concat(String.valueOf(com.sijla.g.b.a.a().a(jSONObject).toString()));
        }
        return "?".concat(String.valueOf(com.sijla.g.b.a.a().a(jSONObject).toString()));
    }

    protected static void a(String str, boolean z) {
        try {
            File[] listFiles = new File(str).listFiles();
            if (listFiles != null) {
                int i = 0;
                if (!z) {
                    if (listFiles.length <= 0) {
                        int length = listFiles.length;
                        while (i < length) {
                            File file = listFiles[i];
                            if (file != null && file.exists() && !file.getName().endsWith(".lock")) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(file.getAbsolutePath());
                                sb.append(".lock");
                                file.renameTo(new File(sb.toString()));
                            }
                            i++;
                        }
                    }
                }
                int length2 = listFiles.length;
                while (i < length2) {
                    File file2 = listFiles[i];
                    if (file2 != null && file2.exists()) {
                        c.a(file2);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(String str) {
        if (d) {
            return;
        }
        if (this.c || b()) {
            d = true;
            a(this.b);
            d = false;
        }
    }

    private boolean b() {
        return b.a(this.b, (String) "lastPostTime", com.sijla.c.c.a.optLong("timepost", 3600)) && com.sijla.g.a.a.g(this.b);
    }

    public void e() {
        a.a(new Runnable() {
            public void run() {
                d.this.a((String) "onPowerConnected");
            }
        });
    }

    public void g() {
        a.a(new Runnable() {
            public void run() {
                b.b(d.this.b);
                d.this.a((String) "onKeyGuardGone");
            }
        });
    }

    public void a() {
        super.a();
        a.a(new Runnable() {
            public void run() {
                b.b(d.this.b);
                d.this.a((String) "onCurrentAppForeground");
            }
        });
    }
}
