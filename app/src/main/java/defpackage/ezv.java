package defpackage;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ezv reason: default package */
/* compiled from: PushClientManager */
public final class ezv {
    private static final Object j = new Object();
    private static volatile ezv k;
    long a = -1;
    public Context b;
    public boolean c = true;
    public fag d;
    /* access modifiers changed from: 0000 */
    public String e;
    public String f;
    public boolean g;
    public ewv h = new ezu();
    public int i;
    private long l = -1;
    private long m = -1;
    private long n = -1;
    private long o = -1;
    private long p = -1;
    private SparseArray<a> q = new SparseArray<>();
    private int r = 0;
    private Boolean s;
    private Long t;

    /* renamed from: ezv$a */
    /* compiled from: PushClientManager */
    public static class a {
        ewu a;
        Runnable b;
        Object[] c;
        private ewu d;
        private exd e;

        public a(exd exd, ewu ewu) {
            this.e = exd;
            this.d = ewu;
        }

        public final void a(int i, Object... objArr) {
            this.c = objArr;
            if (this.a != null) {
                this.a.a(i);
            }
            if (this.d != null) {
                this.d.a(i);
            }
        }
    }

    private ezv() {
    }

    public static ezv a() {
        if (k == null) {
            synchronized (j) {
                try {
                    if (k == null) {
                        k = new ezv();
                    }
                }
            }
        }
        return k;
    }

    public final synchronized void a(Context context) {
        if (this.b == null) {
            this.b = context.getApplicationContext();
            this.g = faw.a(context, context.getPackageName(), "com.vivo.pushclient.action.RECEIVE");
            fba.b().a(this.b);
            a((fbh) new exh());
            this.d = new fag();
            this.d.a(context, (String) "com.vivo.push_preferences.appconfig_v1");
            this.e = c();
            this.f = this.d.a("APP_ALIAS");
        }
    }

    public final void a(List<String> list) {
        JSONObject jSONObject;
        try {
            if (list.size() > 0) {
                String a2 = this.d.a("APP_TAGS");
                if (TextUtils.isEmpty(a2)) {
                    jSONObject = new JSONObject();
                } else {
                    jSONObject = new JSONObject(a2);
                }
                for (String put : list) {
                    jSONObject.put(put, System.currentTimeMillis());
                }
                String jSONObject2 = jSONObject.toString();
                if (TextUtils.isEmpty(jSONObject2)) {
                    this.d.c("APP_TAGS");
                } else {
                    this.d.a((String) "APP_TAGS", jSONObject2);
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            this.d.c("APP_TAGS");
        }
    }

    public final void b(List<String> list) {
        JSONObject jSONObject;
        try {
            if (list.size() > 0) {
                String a2 = this.d.a("APP_TAGS");
                if (TextUtils.isEmpty(a2)) {
                    jSONObject = new JSONObject();
                } else {
                    jSONObject = new JSONObject(a2);
                }
                for (String remove : list) {
                    jSONObject.remove(remove);
                }
                String jSONObject2 = jSONObject.toString();
                if (TextUtils.isEmpty(jSONObject2)) {
                    this.d.c("APP_TAGS");
                } else {
                    this.d.a((String) "APP_TAGS", jSONObject2);
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            this.d.c("APP_TAGS");
        }
    }

    public final boolean b() {
        if (this.b == null) {
            fat.d("PushClientManager", "support:context is null");
            return false;
        }
        this.s = Boolean.valueOf(d());
        return this.s.booleanValue();
    }

    /* access modifiers changed from: 0000 */
    public final String c() {
        String a2 = this.d.a("APP_TOKEN");
        if (TextUtils.isEmpty(a2) || !fbd.a(this.b, this.b.getPackageName(), a2)) {
            return a2;
        }
        this.d.a();
        return null;
    }

    public final void a(String str) {
        this.e = str;
        this.d.a((String) "APP_TOKEN", this.e);
    }

    public final void a(String str, int i2, Object... objArr) {
        a b2 = b(str);
        if (b2 != null) {
            b2.a(i2, objArr);
        } else {
            fat.d("PushClientManager", "notifyApp token is null");
        }
    }

    static boolean a(long j2) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        return j2 == -1 || elapsedRealtime <= j2 || elapsedRealtime >= j2 + 2000;
    }

    public final void a(String str, int i2) {
        a b2 = b(str);
        if (b2 != null) {
            b2.a(i2, new Object[0]);
        } else {
            fat.d("PushClientManager", "notifyStatusChanged token is null");
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized String a(a aVar) {
        int i2;
        this.q.put(this.r, aVar);
        i2 = this.r;
        this.r = i2 + 1;
        return Integer.toString(i2);
    }

    /* access modifiers changed from: private */
    public synchronized a b(String str) {
        if (str != null) {
            try {
                int parseInt = Integer.parseInt(str);
                a aVar = this.q.get(parseInt);
                this.q.delete(parseInt);
                return aVar;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public final void a(fbh fbh) {
        Context context = a().b;
        if (fbh == null) {
            fat.a((String) "PushClientManager", (String) "sendCommand, null command!");
            if (context != null) {
                fat.c(context, (String) "[执行指令失败]指令空！");
            }
            return;
        }
        fbe a2 = this.h.a(fbh);
        if (a2 == null) {
            fat.a((String) "PushClientManager", "sendCommand, null command task! pushCommand = ".concat(String.valueOf(fbh)));
            if (context != null) {
                StringBuilder sb = new StringBuilder("[执行指令失败]指令");
                sb.append(fbh);
                sb.append("任务空！");
                fat.c(context, sb.toString());
            }
            return;
        }
        fat.d("PushClientManager", "client--sendCommand, command = ".concat(String.valueOf(fbh)));
        fbf.a(a2);
    }

    private long e() {
        if (this.b == null) {
            return -1;
        }
        if (this.t == null) {
            this.t = Long.valueOf(fbd.b(this.b));
        }
        return this.t.longValue();
    }

    /* access modifiers changed from: 0000 */
    public final boolean d() {
        if (this.s == null) {
            this.s = Boolean.valueOf(e() >= 1230 && fbd.d(this.b));
        }
        return this.s.booleanValue();
    }
}
