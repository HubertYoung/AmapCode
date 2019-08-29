package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: la reason: default package */
/* compiled from: DailyPerfAppInitTimeRecorder */
public final class la {
    /* access modifiers changed from: private */
    public static JSONArray a = new JSONArray();
    private static long b = 0;
    private static long c = 0;
    /* access modifiers changed from: private */
    public static boolean d = false;
    /* access modifiers changed from: private */
    public static JSONArray e = new JSONArray();
    /* access modifiers changed from: private */
    public static a f;
    /* access modifiers changed from: private */
    public static a g;
    private static a h;
    /* access modifiers changed from: private */
    public static a i;
    /* access modifiers changed from: private */
    public static a j;
    /* access modifiers changed from: private */
    public static final Object k = new Object();

    /* renamed from: la$a */
    /* compiled from: DailyPerfAppInitTimeRecorder */
    static class a {
        ArrayList<Long> a;
        ArrayList<String> b;
        private String c;
        private String d;
        private boolean e = true;

        public a(String str, String str2) {
            this.c = str;
            this.d = str2;
            a();
        }

        public final void a() {
            this.e = !bny.b;
            if (!this.e) {
                if (this.a == null) {
                    this.a = new ArrayList<>();
                    this.b = new ArrayList<>();
                    return;
                }
                this.a.clear();
                this.b.clear();
            }
        }

        public final void a(String str) {
            if (!this.e) {
                this.a.add(Long.valueOf(System.currentTimeMillis()));
                this.b.add(str);
            }
        }

        public final void b() {
            if (!this.e) {
                if (bno.a) {
                    String str = this.c;
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.d);
                    sb.append(": begin");
                    AMapLog.d(str, sb.toString());
                }
                long longValue = this.a.get(0).longValue();
                HashMap hashMap = new HashMap();
                long j = longValue;
                for (int i = 1; i < this.a.size(); i++) {
                    j = this.a.get(i).longValue();
                    String str2 = this.b.get(i);
                    long longValue2 = this.a.get(i - 1).longValue();
                    if (bno.a) {
                        String str3 = this.c;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(this.d);
                        sb2.append(":      ");
                        sb2.append(j - longValue2);
                        sb2.append(" ms, ");
                        sb2.append(str2);
                        AMapLog.d(str3, sb2.toString());
                    }
                    hashMap.put(str2, Long.valueOf(j - longValue2));
                }
                for (Entry next : a(hashMap)) {
                    a((String) next.getKey(), ((Long) next.getValue()).longValue());
                }
                if (bno.a) {
                    String str4 = this.c;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.d);
                    sb3.append(": end, ");
                    sb3.append(j - longValue);
                    sb3.append(" ms");
                    AMapLog.d(str4, sb3.toString());
                }
            }
        }

        private static void a(String str, long j) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("task", str);
                jSONObject.put("cost", j);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            la.e.put(jSONObject);
        }

        private List<Entry<String, Long>> a(HashMap hashMap) {
            ArrayList arrayList = new ArrayList(hashMap.entrySet());
            Collections.sort(arrayList, new Comparator<Entry<String, Long>>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return ((Long) ((Entry) obj2).getValue()).compareTo((Long) ((Entry) obj).getValue());
                }
            });
            return arrayList;
        }
    }

    public static void i() {
    }

    public static void a(String str) {
        if (g == null) {
            g = new a("Performance", "Activity");
        }
        g.a(str);
    }

    public static void b(String str) {
        if (f == null) {
            f = new a("Performance", "Application");
        }
        f.a(str);
    }

    public static void c(String str) {
        if (h == null) {
            h = new a("Performance", "VApp");
        }
        h.a(str);
    }

    public static void d(String str) {
        if (i == null) {
            i = new a("Performance", "Launcher");
        }
        i.a(str);
    }

    public static void e(String str) {
        if (j == null) {
            j = new a("Performance", "MainPage");
        }
        j.a(str);
    }

    public static void a() {
        if (bny.a) {
            if (!d) {
                long currentTimeMillis = System.currentTimeMillis();
                b = currentTimeMillis;
                c = currentTimeMillis;
            }
            d = true;
        }
    }

    public static void b() {
        f(AMapAppGlobal.getApplication().getString(R.string.init_log_create_engine_frame));
    }

    public static void c() {
        f(AMapAppGlobal.getApplication().getString(R.string.init_log_enter_default_page));
    }

    public static void d() {
        if (bny.a) {
            f(AMapAppGlobal.getApplication().getString(R.string.init_log_page_pre_draw));
        }
    }

    public static void e() {
        f(AMapAppGlobal.getApplication().getString(R.string.init_log_render_first_frame));
    }

    public static void f() {
        r();
        if (bny.a) {
            f(AMapAppGlobal.getApplication().getString(R.string.init_log_render_complete));
            s();
            t();
        }
    }

    public static void a(int i2) {
        r();
        if (bny.a) {
            f(AMapAppGlobal.getApplication().getString(R.string.init_log_render_complete));
            s();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("scenerio", "总帧数");
                jSONObject.put("time", i2);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            a.put(jSONObject);
            t();
        }
    }

    private static void r() {
        ahm.a(new Runnable() {
            public final void run() {
                try {
                    synchronized (la.k) {
                        if (la.f != null) {
                            la.f.b();
                            la.f.a();
                        }
                        if (la.i != null) {
                            la.i.b();
                            la.i.a();
                        }
                        if (la.g != null) {
                            la.g.b();
                            la.g.a();
                        }
                        if (la.j != null) {
                            la.j.b();
                            la.j.a();
                        }
                        if (bny.a || la.d || bno.a) {
                            la.a("init_time_detail_log.txt", la.e);
                        }
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    @NonNull
    private static void s() {
        long currentTimeMillis = System.currentTimeMillis() - c;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("scenerio", AMapAppGlobal.getApplication().getString(R.string.init_log_time_all));
            jSONObject.put("time", currentTimeMillis);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        a.put(jSONObject);
    }

    public static JSONArray g() {
        JSONArray jSONArray;
        synchronized (k) {
            jSONArray = null;
            if (e != null) {
                try {
                    JSONArray jSONArray2 = new JSONArray(e.toString());
                    try {
                        e = new JSONArray();
                        jSONArray = jSONArray2;
                    } catch (JSONException e2) {
                        JSONArray jSONArray3 = jSONArray2;
                        e = e2;
                        jSONArray = jSONArray3;
                        e.printStackTrace();
                        return jSONArray;
                    }
                } catch (JSONException e3) {
                    e = e3;
                    e.printStackTrace();
                    return jSONArray;
                }
            }
        }
        return jSONArray;
    }

    public static void h() {
        d = false;
        a = new JSONArray();
    }

    private static void f(String str) {
        if (bny.a && d) {
            long currentTimeMillis = System.currentTimeMillis();
            long j2 = currentTimeMillis - b;
            b = currentTimeMillis;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("scenerio", str);
                jSONObject.put("time", j2);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            a.put(jSONObject);
        }
    }

    private static void t() {
        if (d) {
            d = false;
            ahm.a(new Runnable() {
                public final void run() {
                    la.a("init_time_log.txt", la.a);
                    la.a = new JSONArray();
                }
            });
        }
    }

    static /* synthetic */ void a(String str, JSONArray jSONArray) {
        JSONArray jSONArray2;
        if (!TextUtils.isEmpty(str) && jSONArray != null && jSONArray.length() != 0) {
            String b2 = PathManager.a().b();
            if (FileUtil.checkPathIsCanUse(b2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(b2);
                sb.append("/autonavi/");
                File file = new File(sb.toString(), str);
                try {
                    if (file.exists()) {
                        jSONArray2 = new JSONArray(FileUtil.readData(file.getAbsolutePath()));
                    } else {
                        jSONArray2 = new JSONArray();
                    }
                    jSONArray2.put(jSONArray);
                    FileUtil.writeTextFile(file, jSONArray2.toString());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    static {
        if (a == null) {
        }
    }
}
