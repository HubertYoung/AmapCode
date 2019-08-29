package defpackage;

import android.content.Context;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: evt reason: default package */
/* compiled from: ActivityEventProcessor */
public class evt {
    public static final String a = "evt";
    private static String b;
    private static AtomicReference<String> c = new AtomicReference<>("");
    private static AtomicReference<String> d = new AtomicReference<>("");
    private static AtomicReference<Integer> e = new AtomicReference<>(Integer.valueOf(-1));
    private static AtomicBoolean f = new AtomicBoolean(false);
    private static List<evz> g = new ArrayList();
    private List<evv> h = Collections.synchronizedList(new ArrayList());

    public static void a(String str) {
        c.set(str);
    }

    public static String a() {
        return c.get();
    }

    public static void b(String str) {
        d.set(str);
    }

    public static int b() {
        return e.get().intValue();
    }

    public static void c() {
        f.set(false);
    }

    public static boolean d() {
        return f.get();
    }

    private static void a(Context context) {
        ewp.a(context, "hmt_session_id_savetime", "session_save_time", Long.valueOf(System.currentTimeMillis()));
    }

    private static synchronized String b(Context context) throws ParseException {
        synchronized (evt.class) {
            String d2 = euw.d(context);
            if (d2 == null) {
                return "";
            }
            String a2 = euw.a();
            StringBuilder sb = new StringBuilder();
            sb.append(d2);
            sb.append(a2);
            String a3 = ewn.a(sb.toString());
            a(context);
            b = a3;
            return a3;
        }
    }

    public final synchronized void a(Context context, ewe ewe, int i, int i2, String str) {
        a(context, ewe, i, i2, str, (JSONObject) null);
    }

    public final synchronized void a(Context context, ewe ewe, int i, int i2, String str, JSONObject jSONObject) {
        euw.a((String) "Trigger on pause!");
        eve.b();
        a(context);
        int a2 = a(i2, i, false, str);
        if (a2 != -1) {
            JSONObject a3 = g.get(a2).a(context, euw.a());
            g.remove(a2);
            JSONObject a4 = eve.a(a3, ewe);
            if (jSONObject != null) {
                eve.a(a4, jSONObject);
            }
            eve.a(context, a4, "activity_list", evd.p);
            if (this.h != null && this.h.size() > 0) {
                try {
                    JSONObject a5 = evx.a(context, (String) "heats");
                    evv remove = this.h.remove(0);
                    a5.put("_activity", remove.a);
                    JSONObject jSONObject2 = new JSONObject();
                    a(remove.b, jSONObject2);
                    a5.put("heats", jSONObject2);
                    eve.a(context, a5, "heats", evd.p);
                } catch (JSONException e2) {
                    euw.a((String) "An error occured while parsing Heat Point ! ");
                    euw.a(e2.getMessage());
                }
            }
        }
    }

    private static void a(HashMap<String, String[]> hashMap, JSONObject jSONObject) throws JSONException {
        for (String next : hashMap.keySet()) {
            String[] strArr = hashMap.get(next);
            if (strArr != null) {
                JSONArray jSONArray = new JSONArray(strArr);
                String str = "touches_up";
                char c2 = 65535;
                int hashCode = next.hashCode();
                if (hashCode != 115029) {
                    if (hashCode != 3089570) {
                        if (hashCode != 3317767) {
                            if (hashCode == 108511772 && next.equals("right")) {
                                c2 = 3;
                            }
                        } else if (next.equals("left")) {
                            c2 = 2;
                        }
                    } else if (next.equals("down")) {
                        c2 = 1;
                    }
                } else if (next.equals("top")) {
                    c2 = 0;
                }
                switch (c2) {
                    case 0:
                        str = "touches_up";
                        break;
                    case 1:
                        str = "touches_down";
                        break;
                    case 2:
                        str = "touches_left";
                        break;
                    case 3:
                        str = "touches_right";
                        break;
                }
                jSONObject.put(str, jSONArray);
            }
        }
    }

    private static int a(int i, int i2, boolean z, String str) {
        int size = g.size();
        for (int i3 = 0; i3 < size; i3++) {
            if (g.get(i3).a(i, i2, z, str).booleanValue()) {
                return i3;
            }
        }
        return -1;
    }

    public final synchronized void a(Context context, int i, String str, String str2, int i2, String str3) {
        Context context2 = context;
        int i3 = i;
        String str4 = str2;
        String str5 = str3;
        synchronized (this) {
            try {
                euw.a((String) "Trigger on resume!");
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - ((Long) ewp.b(context2, "hmt_session_id_savetime", "session_save_time", Long.valueOf(currentTimeMillis))).longValue() > evd.c) {
                    b(context2);
                }
            } catch (ParseException e2) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(e2.getMessage());
                euw.a(sb.toString());
            } catch (Throwable th) {
                throw th;
            }
            try {
                if (b == null) {
                    b(context2);
                }
            } catch (Exception e3) {
                euw.a(e3.getMessage());
            }
            String a2 = euw.a();
            if (g.size() > evd.z) {
                g = new ArrayList();
            }
            if (!euw.b(euw.j(context2), str4).booleanValue()) {
                int i4 = i2;
                int a3 = a(i4, i3, true, str5);
                if (a3 == -1) {
                    evz evz = new evz(i4, str4, str, b, a2, i3, euw.y(context2), euw.w(context2), euw.p(context2), str5);
                    g.add(evz);
                } else {
                    evz evz2 = g.get(a3);
                    String str6 = b;
                    String y = euw.y(context2);
                    String w = euw.w(context2);
                    String p = euw.p(context2);
                    if (i3 == 1 || evz2.d == 0) {
                        evz2.d = i3;
                        evz2.a = str;
                        evz2.b = str6;
                        evz2.c = a2;
                        evz2.e = str4;
                        evz2.f = y;
                        evz2.g = w;
                        evz2.h = p;
                        evz2.i = str5;
                    }
                }
            }
            if (evd.v) {
                euv.a(context2, 0);
            }
        }
    }
}
