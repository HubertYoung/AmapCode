package defpackage;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: fau reason: default package */
/* compiled from: MessageConvertUtil */
public final class fau {
    public static ezq a(ezp ezp) {
        ezq ezq = new ezq();
        ezq.f = ezp.f;
        ezq.g = ezp.g;
        ezq.h = ezp.h;
        ezq.i = ezp.i;
        ezq.j = ezp.j;
        ezq.k = ezp.k;
        ezq.l = ezp.l;
        ezq.m = ezp.m;
        ezq.n = ezp.n;
        ezq.o = ezp.o;
        ezq.p = ezp.p;
        ezq.q = ezp.q;
        ezq.r = ezp.r;
        return ezq;
    }

    public static String b(ezp ezp) {
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(ezp.f);
        jSONArray.put(ezp.g);
        jSONArray.put(ezp.h);
        jSONArray.put(ezp.i);
        jSONArray.put(ezp.j);
        jSONArray.put(ezp.k);
        jSONArray.put(ezp.l);
        jSONArray.put(ezp.m);
        jSONArray.put(ezp.n);
        jSONArray.put(ezp.o);
        jSONArray.put(ezp.p);
        if (ezp.r != null) {
            jSONArray.put(new JSONObject(ezp.r));
        } else {
            jSONArray.put(bny.c);
        }
        jSONArray.put(ezp.a);
        jSONArray.put(ezp.b);
        jSONArray.put(ezp.c);
        jSONArray.put(ezp.d);
        jSONArray.put(ezp.e);
        return jSONArray.toString();
    }

    public static ezp a(String str) {
        ezp ezp = new ezp();
        try {
            if (TextUtils.isEmpty(str)) {
                fat.a((String) "MessageConvertUtil", (String) "notify msg pack to obj is null");
                return null;
            }
            JSONArray jSONArray = new JSONArray(str);
            ezp.f = jSONArray.getInt(0);
            ezp.g = jSONArray.getString(1);
            ezp.h = jSONArray.getString(2);
            ezp.i = jSONArray.getString(3);
            ezp.j = jSONArray.getInt(4);
            ezp.k = jSONArray.getString(5);
            ezp.l = jSONArray.getString(6);
            ezp.m = jSONArray.getString(7);
            ezp.n = jSONArray.getString(8);
            ezp.o = jSONArray.getInt(9);
            ezp.p = jSONArray.getBoolean(10);
            if (jSONArray.length() > 11) {
                ezp.r = faq.a(new JSONObject(jSONArray.getString(11)));
            }
            if (jSONArray.length() > 15) {
                ezp.a = jSONArray.getInt(12);
                ezp.b = jSONArray.getString(13);
                ezp.c = jSONArray.getBoolean(14);
                ezp.d = jSONArray.getString(15);
            }
            if (jSONArray.length() > 16) {
                ezp.e = jSONArray.getInt(16);
            }
            return ezp;
        } catch (JSONException e) {
            fat.a("MessageConvertUtil", "notify msg pack to obj error", e);
        }
    }
}
