package defpackage;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import com.hmt.analytics.util.j;
import com.hmt.analytics.util.l;
import com.hmt.analytics.util.l.a;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: euv reason: default package */
/* compiled from: HMTAgent */
public class euv {
    public static boolean a = false;
    public static Handler b = null;
    private static final String c = "euv";
    /* access modifiers changed from: private */
    public static Context d = null;
    private static euv e = new euv();
    private static AtomicBoolean f = new AtomicBoolean(true);
    private static AtomicBoolean g = new AtomicBoolean(true);
    private static evu h = new evu();
    /* access modifiers changed from: private */
    public static evt i = new evt();
    private static l j = new l(a.HMT);
    private static boolean k = false;
    private static j l;

    static {
        try {
            b = new Handler();
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e2.getMessage());
            euw.a(sb.toString());
        }
    }

    private euv() {
    }

    public static void a(Context context, String str, int i2, ewe ewe, String str2, JSONObject jSONObject) {
        evy evy;
        if (!TextUtils.isEmpty(str2)) {
            evy = new evy(str, String.valueOf(i2), euw.a(), str2, euw.n(context), euw.d(context));
        } else {
            evy = new evy(str, String.valueOf(i2), context);
        }
        evs.a(context, evy, ewe, jSONObject);
    }

    public static void a(Context context) {
        if (VERSION.SDK_INT >= 14) {
            final int hashCode = context.hashCode();
            final Context applicationContext = context.getApplicationContext();
            ewq.a().execute(new Runnable() {
                final /* synthetic */ ewe b = null;
                final /* synthetic */ int c = 0;
                final /* synthetic */ evo d = null;
                final /* synthetic */ String f;

                {
                    this.f = null;
                }

                public final void run() {
                    JSONObject jSONObject;
                    evt a2 = euv.i;
                    Context context = applicationContext;
                    ewe ewe = this.b;
                    int i = hashCode;
                    String a3 = evt.a();
                    boolean d2 = evt.d();
                    int b2 = evt.b();
                    if (!TextUtils.isEmpty(a3) && b2 == i) {
                        try {
                            jSONObject = new JSONObject("{\"source\": \"h5\"}");
                        } catch (JSONException e2) {
                            String str = evt.a;
                            StringBuilder sb = new StringBuilder("Collected:");
                            sb.append(e2.getMessage());
                            euw.a(sb.toString());
                            jSONObject = null;
                        }
                        a2.a(context.getApplicationContext(), ewe, 1, a3.hashCode(), (String) null, jSONObject);
                    }
                    if (!d2) {
                        evt.c();
                        evt.b((String) "");
                        evt.a((String) "");
                    }
                    euv.i.a(applicationContext, this.b, this.c, hashCode, this.f);
                }
            });
        }
    }

    public static void b(Context context) {
        if (evt.b() == context.hashCode() && l != null) {
            j jVar = l;
            String str = j.a;
            euw.a((String) "stopWatch");
            if (jVar.c != null) {
                jVar.b.unregisterReceiver(jVar.c);
            }
        }
    }

    public static void c(Context context) {
        if (VERSION.SDK_INT >= 14) {
            final String str = null;
            if (TextUtils.isEmpty(null)) {
                str = euw.a(context, 1);
            }
            final String a2 = euw.a(context, 0);
            final int hashCode = context.hashCode();
            final Context applicationContext = context.getApplicationContext();
            ewq.a().execute(new Runnable() {
                final /* synthetic */ int b = 0;
                final /* synthetic */ String f;

                {
                    this.f = null;
                }

                public final void run() {
                    evt a2 = euv.i;
                    Context context = applicationContext;
                    String str = a2;
                    int i = hashCode;
                    String a3 = evt.a();
                    boolean d2 = evt.d();
                    int b2 = evt.b();
                    if (!TextUtils.isEmpty(a3) && d2 && b2 == i) {
                        a2.a(context.getApplicationContext(), 1, a3, str, a3.hashCode(), (String) null);
                    }
                    evt.c();
                    euv.i.a(applicationContext, this.b, str, a2, hashCode, this.f);
                }
            });
        }
    }

    public static void a(Context context, final String[] strArr) {
        if (g.compareAndSet(true, false)) {
            d = context.getApplicationContext();
            ewq.a().execute(new Runnable() {
                final /* synthetic */ int a = 0;

                public final void run() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(evd.h);
                    sb.append(evd.A);
                    evd.h = sb.toString();
                    euw.I(euv.d);
                    euv.a(euv.d, this.a, strArr);
                }
            });
            Context context2 = d;
            if (evd.w && VERSION.SDK_INT >= 14) {
                if (context2 instanceof Activity) {
                    ((Activity) context2).getApplication().registerActivityLifecycleCallbacks(new evc());
                } else if (context2 instanceof Application) {
                    ((Application) context2).registerActivityLifecycleCallbacks(new evc());
                }
            }
            ewi.a(context);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0061, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c7, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(final android.content.Context r11, int r12) {
        /*
            java.lang.Class<euv> r0 = defpackage.euv.class
            monitor-enter(r0)
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00c8 }
            java.lang.String r3 = "hmt_init_savetime"
            java.lang.String r4 = "init_save_time"
            java.lang.Long r5 = java.lang.Long.valueOf(r1)     // Catch:{ all -> 0x00c8 }
            java.lang.Object r3 = defpackage.ewp.b(r11, r3, r4, r5)     // Catch:{ all -> 0x00c8 }
            java.lang.Long r3 = (java.lang.Long) r3     // Catch:{ all -> 0x00c8 }
            long r3 = r3.longValue()     // Catch:{ all -> 0x00c8 }
            java.lang.String r5 = "hmt_init_savetime"
            java.lang.String r6 = "upload_save_time"
            r7 = 0
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x00c8 }
            java.lang.Object r5 = defpackage.ewp.b(r11, r5, r6, r7)     // Catch:{ all -> 0x00c8 }
            java.lang.Long r5 = (java.lang.Long) r5     // Catch:{ all -> 0x00c8 }
            long r5 = r5.longValue()     // Catch:{ all -> 0x00c8 }
            r7 = 0
            long r7 = r1 - r3
            long r9 = defpackage.evd.e     // Catch:{ all -> 0x00c8 }
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            r8 = 0
            r9 = 1
            if (r7 <= 0) goto L_0x003a
            r7 = 1
            goto L_0x003b
        L_0x003a:
            r7 = 0
        L_0x003b:
            boolean r3 = defpackage.eve.a(r1, r3)     // Catch:{ all -> 0x00c8 }
            if (r7 != 0) goto L_0x0062
            boolean r4 = k     // Catch:{ all -> 0x00c8 }
            if (r4 == 0) goto L_0x0062
            if (r3 != 0) goto L_0x0048
            goto L_0x0062
        L_0x0048:
            boolean r12 = defpackage.eve.a(r1, r5)     // Catch:{ all -> 0x00c8 }
            if (r12 != 0) goto L_0x0060
            boolean r12 = defpackage.euw.c(r11)     // Catch:{ all -> 0x00c8 }
            if (r12 == 0) goto L_0x0060
            java.util.concurrent.ExecutorService r12 = defpackage.ewq.a()     // Catch:{ all -> 0x00c8 }
            evb r1 = new evb     // Catch:{ all -> 0x00c8 }
            r1.<init>(r11, r8)     // Catch:{ all -> 0x00c8 }
            r12.execute(r1)     // Catch:{ all -> 0x00c8 }
        L_0x0060:
            monitor-exit(r0)
            return
        L_0x0062:
            defpackage.eve.b()     // Catch:{ all -> 0x00c8 }
            java.lang.String r1 = defpackage.euw.g(r11)     // Catch:{ all -> 0x00c8 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x00c8 }
            if (r1 != 0) goto L_0x008d
            k = r9     // Catch:{ all -> 0x00c8 }
            java.lang.String r1 = "hmt_init_savetime"
            java.lang.String r2 = "init_save_time"
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00c8 }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x00c8 }
            defpackage.ewp.a(r11, r1, r2, r3)     // Catch:{ all -> 0x00c8 }
            org.json.JSONObject r1 = defpackage.evx.a(r11)     // Catch:{ all -> 0x00c8 }
            java.lang.String r2 = "client_data_list"
            java.lang.String r3 = defpackage.evd.p     // Catch:{ all -> 0x00c8 }
            defpackage.eve.a(r11, r1, r2, r3)     // Catch:{ all -> 0x00c8 }
            r8 = 1
            goto L_0x0090
        L_0x008d:
            defpackage.eve.b()     // Catch:{ all -> 0x00c8 }
        L_0x0090:
            if (r12 != r9) goto L_0x00b2
            boolean r12 = defpackage.euw.c(r11)     // Catch:{ all -> 0x00c8 }
            if (r12 == 0) goto L_0x00a4
            java.util.concurrent.ExecutorService r12 = defpackage.ewq.a()     // Catch:{ all -> 0x00c8 }
            evb r1 = new evb     // Catch:{ all -> 0x00c8 }
            r1.<init>(r11)     // Catch:{ all -> 0x00c8 }
            r12.execute(r1)     // Catch:{ all -> 0x00c8 }
        L_0x00a4:
            euv$5 r12 = new euv$5     // Catch:{ all -> 0x00c8 }
            r12.<init>(r11)     // Catch:{ all -> 0x00c8 }
            java.util.concurrent.ExecutorService r11 = defpackage.ewq.a()     // Catch:{ all -> 0x00c8 }
            r11.execute(r12)     // Catch:{ all -> 0x00c8 }
            monitor-exit(r0)
            return
        L_0x00b2:
            if (r8 == 0) goto L_0x00c6
            boolean r12 = defpackage.euw.c(r11)     // Catch:{ all -> 0x00c8 }
            if (r12 == 0) goto L_0x00c6
            java.util.concurrent.ExecutorService r12 = defpackage.ewq.a()     // Catch:{ all -> 0x00c8 }
            evb r1 = new evb     // Catch:{ all -> 0x00c8 }
            r1.<init>(r11)     // Catch:{ all -> 0x00c8 }
            r12.execute(r1)     // Catch:{ all -> 0x00c8 }
        L_0x00c6:
            monitor-exit(r0)
            return
        L_0x00c8:
            r11 = move-exception
            monitor-exit(r0)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.euv.a(android.content.Context, int):void");
    }

    public static void a(evp evp) {
        evd.x = evp;
    }

    public static void a(final Context context, final String str, final ewe ewe) {
        ewq.a().execute(new Runnable() {
            final /* synthetic */ int c = 1;
            final /* synthetic */ evo e;
            final /* synthetic */ String f;
            final /* synthetic */ JSONObject g;

            {
                this.e = null;
                this.f = null;
                this.g = null;
            }

            public final void run() {
                euv.a(context, str, this.c, ewe, this.f, this.g);
            }
        });
    }

    static /* synthetic */ void a(Context context, int i2, String[] strArr) {
        euw.G(context);
        euw.a(context, strArr, (String) "client");
        euw.a(context, i2, (String) "client");
        ewo.o(context);
        if (f.compareAndSet(true, false)) {
            eve.a(context);
            if (euw.c(context)) {
                l lVar = j;
                String H = euw.H(context);
                if (euw.c(context)) {
                    evw a2 = evh.a(H);
                    try {
                        if (a2.a) {
                            lVar.a(context, new JSONObject(a2.b));
                        } else {
                            String str = l.a;
                            euw.a((String) "updateOnlineConfigs failed");
                        }
                    } catch (JSONException e2) {
                        String str2 = l.a;
                        StringBuilder sb = new StringBuilder("Collected:");
                        sb.append(e2.getMessage());
                        euw.a(sb.toString());
                    }
                } else {
                    String str3 = l.a;
                    euw.a((String) " updateOnlineConfigs : network error");
                }
            }
            if (evd.v) {
                a(context, 1);
            }
        }
    }

    static /* synthetic */ void d(Context context) {
        if (eve.d(context)) {
            eve.c(context);
            eve.c(context);
            eve.b(context);
        }
    }
}
