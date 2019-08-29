package com.sijla.f;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import com.q.Qt;
import com.sijla.bean.b;
import com.sijla.c.c;
import com.sijla.callback.QtCallBack;
import com.sijla.g.d;
import com.sijla.g.g;
import com.sijla.g.h;
import com.sijla.g.i;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;

public class a {
    public static b a(Context context) {
        return a(context, c.a, (String) "0");
    }

    public static b a(Context context, JSONObject jSONObject, String str) {
        b bVar = new b();
        try {
            String packageName = context.getPackageName();
            boolean z = true;
            if (1 != jSONObject.optInt("phnum", 1)) {
                z = false;
            }
            String l = z ? com.sijla.g.a.a.l(context) : "";
            bVar.a("00", com.sijla.g.b.g(context));
            bVar.a("01", com.sijla.g.a.a.k(context));
            bVar.a("02", packageName);
            bVar.a("03", com.sijla.g.a.a.a(packageName, context));
            bVar.a("04", com.sijla.g.b.e(context));
            bVar.a("05", "");
            bVar.a("06", com.sijla.g.b.f(context));
            bVar.a("07", String.valueOf(com.sijla.g.a.a.j()));
            bVar.a("08", com.sijla.g.a.a.g());
            bVar.a("09", com.sijla.g.a.a.q(context));
            bVar.a("10", com.sijla.g.a.a.r(context));
            bVar.a("11", com.sijla.g.a.a.c());
            bVar.a("12", VERSION.RELEASE);
            bVar.a("13", l);
            bVar.a("14", i.d(context));
            bVar.a("15", com.sijla.g.a.a.h());
            bVar.a("16", com.sijla.g.a.a.n(context));
            bVar.a("17", com.sijla.g.a.a.w(context));
            bVar.a("18", com.sijla.g.a.a.b() ? "1" : "0");
            bVar.a("19", com.sijla.g.a.a.x(context));
            bVar.a("20", com.sijla.g.a.a.o(context));
            bVar.a("21", com.sijla.g.a.a.t(context));
            bVar.a("22", str);
            bVar.a("23", String.valueOf(System.currentTimeMillis() / 1000));
            bVar.a("24", i.b(context));
            bVar.a("25", com.sijla.g.a.a.s(context));
            bVar.a("26", com.sijla.g.a.a.m(context));
            bVar.a(FFmpegSessionConfig.CRF_27, com.sijla.g.b.d(context));
            bVar.a("28", i.e(context));
            bVar.a("29", com.sijla.b.b.b());
            bVar.a("30", String.valueOf(com.sijla.g.a.a.y(context)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bVar;
    }

    public static String a(Context context, String str, String str2) {
        String str3;
        try {
            if (!TextUtils.isEmpty(str)) {
                String f = com.sijla.g.a.b.f(context);
                StringBuilder sb = new StringBuilder();
                sb.append(f);
                sb.append(str2);
                str3 = sb.toString();
                try {
                    byte[] a = a(str);
                    if (a == null || a.length <= 0) {
                        return str3;
                    }
                    com.sijla.g.a.c.a(str3, a);
                    return str3;
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    return str3;
                }
            } else {
                r2 = "";
                return "";
            }
        } catch (Exception e2) {
            e = e2;
            str3 = "";
            e.printStackTrace();
            return str3;
        }
    }

    private static byte[] a(String str) {
        return com.sijla.g.b.g(str);
    }

    private static String b(Context context) {
        StringBuilder sb = new StringBuilder();
        String packageName = context.getPackageName();
        sb.append(packageName);
        sb.append("\t");
        sb.append(com.sijla.g.a.a.a(packageName, context));
        sb.append("\t");
        sb.append(com.sijla.g.b.f(context));
        sb.append("\t");
        sb.append(i.b(context));
        sb.append("\t");
        sb.append(i.a(context));
        sb.append("\t");
        sb.append(com.sijla.g.a.a.q(context));
        sb.append("\t");
        sb.append(com.sijla.g.a.a.r(context));
        sb.append("\t");
        sb.append(com.sijla.g.a.a.l(context));
        sb.append("\t");
        sb.append(com.sijla.g.a.a.t(context));
        sb.append("\t");
        sb.append(com.sijla.g.a.a.g());
        sb.append("\t");
        sb.append(String.valueOf(com.sijla.g.a.a.j()));
        sb.append("\t");
        sb.append(com.sijla.g.a.a.x(context));
        sb.append("\t");
        sb.append(com.sijla.g.a.a.s(context));
        sb.append("\t");
        sb.append("-\t");
        sb.append(Build.MANUFACTURER);
        sb.append("\t");
        sb.append(com.sijla.g.a.a.c());
        sb.append("\t");
        sb.append(com.sijla.g.a.a.h());
        sb.append("\t");
        sb.append(com.sijla.g.a.a.w(context));
        sb.append("\t");
        sb.append(VERSION.RELEASE);
        sb.append("\t");
        sb.append(com.sijla.g.a.a.o(context));
        sb.append("\t");
        sb.append(com.sijla.g.a.a.n(context));
        sb.append("\t");
        sb.append(com.sijla.g.a.a.b() ? "1" : "0");
        sb.append("\t");
        sb.append(Secure.getString(context.getContentResolver(), "android_id"));
        sb.append("\t");
        sb.append(System.currentTimeMillis() / 1000);
        sb.append("\t");
        sb.append(com.sijla.g.a.a.m(context));
        return sb.toString();
    }

    public static void a(Context context, String str) {
        if (com.sijla.g.a.a.g(context)) {
            StringBuilder sb = new StringBuilder("qt.csv.");
            sb.append(System.currentTimeMillis());
            sb.append(".txt");
            String sb2 = sb.toString();
            String b = b(context);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("\t");
            sb3.append(b);
            String sb4 = sb3.toString();
            g.a("reportTruthinfo2:".concat(String.valueOf(sb4)));
            if (sb4 != null) {
                a(context, sb4, sb2);
                File file = new File(com.sijla.g.a.b.f(context));
                if (file.isDirectory()) {
                    File[] listFiles = file.listFiles(new FileFilter() {
                        public final boolean accept(File file) {
                            return file.isFile() && file.getName().startsWith("qt.csv.");
                        }
                    });
                    if (listFiles != null) {
                        HashMap hashMap = new HashMap();
                        for (File file2 : listFiles) {
                            hashMap.put(file2.getName(), file2);
                        }
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("appkey", com.sijla.g.b.g(context));
                            jSONObject.put(Oauth2AccessToken.KEY_UID, i.b(context));
                            jSONObject.put(GlobalConstants.EXCEPTIONTYPE, com.sijla.b.a.a);
                            jSONObject.put("type", 3);
                            jSONObject.put(LogItem.MM_C43_K4_FINISH_TIME, "2");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String concat = "?".concat(String.valueOf(com.sijla.g.b.a.a().a(jSONObject).toString()));
                        JSONArray jSONArray = null;
                        try {
                            jSONArray = c.a.optJSONArray("trinfo2urls");
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                        if (jSONArray == null || jSONArray.length() == 0) {
                            jSONArray = new JSONArray();
                            jSONArray.put("https://truth.qchannel03.cn/truth");
                        }
                        boolean z = c.a.optInt("repeatReportGrowth", 0) == 0;
                        boolean z2 = false;
                        for (int i = 0; i < jSONArray.length(); i++) {
                            String optString = jSONArray.optString(i, "https://truth.qchannel03.cn/truth");
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(concat);
                            sb5.append("&r=");
                            sb5.append(i);
                            String sb6 = sb5.toString();
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append(optString);
                            sb7.append(sb6);
                            if (h.a(sb7.toString(), new JSONObject(), (Map<String, File>) hashMap).b()) {
                                if (!z2) {
                                    z2 = true;
                                }
                                if (z) {
                                    break;
                                }
                            }
                        }
                        if (z2 || listFiles.length > 3) {
                            com.sijla.g.a.c.a(file);
                        }
                    }
                }
            }
        }
    }

    public static void a(Context context, JSONObject jSONObject, QtCallBack qtCallBack) {
        a(context, jSONObject, qtCallBack, false);
    }

    public static void a(Context context, JSONObject jSONObject, QtCallBack qtCallBack, boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        a(context, qtCallBack, (String) "startUpload", currentTimeMillis);
        if (jSONObject == null || !com.sijla.g.a.a.g(context)) {
            a(context, qtCallBack, (String) UploadDataResult.FAIL_MSG, currentTimeMillis);
            return;
        }
        JSONArray jSONArray = null;
        try {
            jSONArray = c.a.optJSONArray("growthurls");
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (jSONArray == null || jSONArray.length() == 0) {
            jSONArray = new JSONArray();
            jSONArray.put("https://www.qchannel01.cn/center/adj");
            jSONArray.put("https://b.qchannel03.cn/n/qts");
        }
        boolean z2 = c.a.optInt("repeatReportGrowth", 0) == 0;
        String a = a(context, z);
        boolean z3 = false;
        for (int i = 0; i < jSONArray.length(); i++) {
            String optString = jSONArray.optString(i, "https://www.qchannel01.cn/center/adj");
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("&r=");
            sb.append(i);
            sb.append("&sv=");
            sb.append(com.sijla.b.a.a);
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(optString);
            sb3.append(sb2);
            if (h.a(sb3.toString(), jSONObject)) {
                if (!z3) {
                    com.sijla.b.b.b((String) "Upload Data Success");
                    a(context, qtCallBack, (String) "success", currentTimeMillis);
                    z3 = true;
                }
                if (z2) {
                    break;
                }
            }
        }
        if (!z3) {
            com.sijla.b.b.a((String) "Upload Data Fail", 0);
            a(context, qtCallBack, (String) UploadDataResult.FAIL_MSG, currentTimeMillis);
            a(context, jSONObject);
        }
    }

    private static String a(Context context, boolean z) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("ak", com.sijla.g.b.g(context));
            jSONObject2.put(Oauth2AccessToken.KEY_UID, i.a(context));
            jSONObject2.put("offline", z ? "1" : "0");
            jSONObject = com.sijla.g.b.a(jSONObject2);
            try {
                jSONObject.put("m", "1");
            } catch (JSONException e) {
                jSONObject2 = jSONObject;
                e = e;
            }
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            jSONObject = jSONObject2;
            return "?".concat(String.valueOf(com.sijla.g.b.a.a().a(jSONObject).toString()));
        }
        return "?".concat(String.valueOf(com.sijla.g.b.a.a().a(jSONObject).toString()));
    }

    private static void a(Context context, QtCallBack qtCallBack, String str, long j) {
        if (qtCallBack != null) {
            try {
                String a = com.sijla.g.a.a.a(context.getPackageName(), context);
                com.sijla.g.b.f(context);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("qmuid", i.a(context));
                jSONObject.put("selfuid", Qt._3uid);
                jSONObject.put("appver", a);
                jSONObject.put("currentChannel", Qt._channel);
                jSONObject.put("installChannel", Qt._channel);
                jSONObject.put("uploadStatus", str);
                jSONObject.put("sessionid", j);
                qtCallBack.uploadCallBack(jSONObject);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private static void a(Context context, JSONObject jSONObject) {
        new com.sijla.e.c(context).a(jSONObject);
    }

    public static void a(Context context, long j, QtCallBack qtCallBack) {
        a(context, a(context, j), qtCallBack);
    }

    private static JSONObject a(Context context, long j) {
        JSONObject jSONObject;
        try {
            String valueOf = String.valueOf(d.d());
            String substring = com.sijla.g.a.d.a(valueOf).substring(0, 8);
            jSONObject = new JSONObject();
            try {
                jSONObject.put("dur", com.sijla.g.b.a(substring, String.valueOf(j)));
                jSONObject.put("pv", com.sijla.g.b.a(substring, String.valueOf(com.sijla.b.a.a)));
                jSONObject.put("dtype", com.sijla.g.b.a(substring, (String) "qt"));
                jSONObject.put("ts", com.sijla.g.b.a(substring, String.valueOf(d.d())));
                jSONObject.put("appkey", com.sijla.g.b.a(substring, com.sijla.g.b.g(context)));
                jSONObject.put("channel", com.sijla.g.b.a(substring, Qt._channel == null ? "null" : Qt._channel));
                jSONObject.put("uuid", com.sijla.g.b.a(substring, i.a(context)));
                jSONObject.put("nt", com.sijla.g.b.a(substring, com.sijla.g.a.a.i(context)));
                jSONObject.put("nuid", com.sijla.g.b.a(substring, i.e(context)));
                jSONObject.put("adr", com.sijla.g.b.a(substring, com.sijla.g.b.d(context)));
                jSONObject.put(LocationParams.PARA_COMMON_DID, com.sijla.g.b.a(substring, com.sijla.g.a.a.q(context)));
                jSONObject.put("mid", com.sijla.g.b.a(substring, i.b(context)));
                jSONObject.put("uid3", com.sijla.g.b.a(substring, Qt._3uid == null ? "null" : Qt._3uid));
                jSONObject.put("inschannel", com.sijla.g.b.a(substring, Qt._channel == null ? "null" : Qt._channel));
                jSONObject.put("ks", com.sijla.g.b.a(substring, com.sijla.g.a.a.A(context)));
                jSONObject.put("mf", com.sijla.g.b.a(substring, com.sijla.g.a.a.d()));
                jSONObject.put("bd", com.sijla.g.b.a(substring, com.sijla.g.a.a.e()));
                jSONObject.put("md", com.sijla.g.b.a(substring, com.sijla.g.a.a.f()));
                jSONObject.put("ov", com.sijla.g.b.a(substring, VERSION.RELEASE));
                JSONObject a = com.sijla.g.a.a.a(context.getPackageManager(), context.getPackageName());
                StringBuilder sb = new StringBuilder();
                sb.append(a.optLong("instime"));
                jSONObject.put("inst", com.sijla.g.b.a(substring, sb.toString()));
                StringBuilder sb2 = new StringBuilder();
                sb2.append(a.optLong("uptime"));
                jSONObject.put("upst", com.sijla.g.b.a(substring, sb2.toString()));
                jSONObject.put("insid", com.sijla.g.b.a(substring, com.sijla.b.b.b(context)));
                jSONObject.put("clientkey", com.sijla.g.b.a(substring, com.sijla.b.b.a()));
                jSONObject.put("aid", com.sijla.g.b.a(substring, com.sijla.g.a.a.k(context)));
                jSONObject.put("e", "1");
                jSONObject.put("s1", valueOf);
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return jSONObject;
            }
        } catch (Exception e2) {
            e = e2;
            jSONObject = null;
            e.printStackTrace();
            return jSONObject;
        }
        return jSONObject;
    }
}
