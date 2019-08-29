package com.sijla.f;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.autonavi.widget.ui.BalloonLayout;
import com.sijla.a.a;
import com.sijla.g.d;
import com.sijla.g.g;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;

public class b {
    private static b a = new b();
    private static long b;

    public static b a() {
        if (a == null) {
            synchronized (b.class) {
                try {
                    if (a == null) {
                        a = new b();
                    }
                }
            }
        }
        return a;
    }

    public List<String> b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("06");
        arrayList.add("22");
        arrayList.add("23");
        arrayList.add("25");
        arrayList.add(FFmpegSessionConfig.CRF_27);
        arrayList.add("29");
        arrayList.add("30");
        return arrayList;
    }

    public void a(final Context context) {
        if (b == 0 || System.currentTimeMillis() - b > BalloonLayout.DEFAULT_DISPLAY_DURATION) {
            b = System.currentTimeMillis();
            a.a(new Runnable() {
                public void run() {
                    byte[] bArr;
                    JSONObject jSONObject;
                    Exception e;
                    try {
                        Context applicationContext = context.getApplicationContext();
                        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("QT", 0);
                        Editor edit = sharedPreferences.edit();
                        String str = "1";
                        com.sijla.bean.b a2 = a.a(applicationContext);
                        List<String> a3 = com.sijla.g.b.a(applicationContext);
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < a3.size(); i++) {
                            String str2 = a3.get(i);
                            if (str2 != null) {
                                File file = new File(str2);
                                if (file.exists()) {
                                    arrayList.add(file);
                                }
                            }
                        }
                        JSONObject jSONObject2 = new JSONObject();
                        if (!arrayList.isEmpty()) {
                            Iterator it = arrayList.iterator();
                            boolean z = true;
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                try {
                                    bArr = com.sijla.g.b.a((File) it.next());
                                } catch (Exception unused) {
                                    bArr = null;
                                }
                                if (bArr != null) {
                                    try {
                                        String b2 = com.sijla.g.b.b(bArr);
                                        if (!TextUtils.isEmpty(b2)) {
                                            jSONObject = new JSONObject(b2);
                                            try {
                                                List<String> b3 = b.this.b();
                                                Iterator<String> keys = jSONObject.keys();
                                                while (keys.hasNext()) {
                                                    String next = keys.next();
                                                    if (!b3.contains(next)) {
                                                        String optString = jSONObject.optString(next);
                                                        String optString2 = a2.optString(next);
                                                        z &= optString.equals(optString2) || com.sijla.g.b.a(optString2);
                                                    }
                                                }
                                                if (z) {
                                                    jSONObject2 = jSONObject;
                                                    break;
                                                }
                                            } catch (Exception e2) {
                                                e = e2;
                                                e.printStackTrace();
                                                jSONObject2 = jSONObject;
                                            }
                                            jSONObject2 = jSONObject;
                                        } else {
                                            continue;
                                        }
                                    } catch (Exception e3) {
                                        jSONObject = jSONObject2;
                                        e = e3;
                                        e.printStackTrace();
                                        jSONObject2 = jSONObject;
                                    }
                                }
                            }
                            String str3 = z ? "1" : "3";
                            String string = sharedPreferences.getString("appver", "");
                            str = (string.equals(com.sijla.g.a.a.a(applicationContext.getPackageName(), applicationContext)) || "".equals(string)) ? (!jSONObject2.optString("03").equals(a2.optString("03")) || !"".equals(string)) ? str3 : "5" : "2";
                        } else {
                            String string2 = sharedPreferences.getString("appver", "");
                            if (TextUtils.isEmpty(string2)) {
                                str = "4";
                            } else if (!TextUtils.isEmpty(string2) && sharedPreferences.getString("QTIME", "").equals("")) {
                                str = "2";
                            }
                        }
                        g.a("truth", a2, "currentTruthJsonObj");
                        com.sijla.g.b.a(applicationContext, (JSONObject) a2);
                        String a4 = com.sijla.g.a.a.a(applicationContext.getPackageName(), applicationContext);
                        edit.putString("QTIME", String.valueOf(d.d()));
                        edit.putString("appver", a4);
                        edit.apply();
                        if (!str.equals("1")) {
                            a.a(applicationContext, str);
                        }
                    } catch (Throwable th) {
                        PrintStream printStream = System.err;
                        th.printStackTrace();
                    }
                }
            });
        }
    }
}
