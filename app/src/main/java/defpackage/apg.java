package defpackage;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: apg reason: default package */
/* compiled from: UserInfoUtil */
public final class apg {
    public static String a(ant ant) {
        if (ant == null) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Oauth2AccessToken.KEY_UID, ant.a);
            jSONObject.put("avatar", ant.b);
            jSONObject.put("userName", ant.c);
            jSONObject.put("birthday", ant.d);
            jSONObject.put("nick", ant.e);
            jSONObject.put("gender", ant.f);
            jSONObject.put(NotificationCompat.CATEGORY_EMAIL, ant.g);
            jSONObject.put("mobile", ant.h);
            jSONObject.put("sinaNick", ant.i);
            jSONObject.put("taobaoNick", ant.l);
            jSONObject.put("qqNick", ant.n);
            jSONObject.put("weixinNick", ant.p);
            jSONObject.put("alipayNick", ant.s);
            jSONObject.put("repwd", ant.w);
            jSONObject.put("sinaID", ant.j);
            jSONObject.put("taobaoID", ant.m);
            jSONObject.put("qqID", ant.o);
            jSONObject.put("weixinID", ant.q);
            jSONObject.put("alipayID", ant.t);
            jSONObject.put("carLogoID", ant.x);
            jSONObject.put("userLevel", ant.y);
            jSONObject.put("userCheckinCount", ant.z);
            jSONObject.put("userAchievementLevel", ant.A);
            jSONObject.put("emblemNum", ant.B);
            jSONObject.put("userCarLoginFlag", ant.C);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static String b(ant ant) {
        if (ant == null) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Oauth2AccessToken.KEY_UID, ant.a);
            jSONObject.put("avatar", ant.b);
            jSONObject.put("userName", ant.c);
            jSONObject.put("birthday", ant.d);
            jSONObject.put("nick", ant.e);
            jSONObject.put("gender", ant.f);
            jSONObject.put(NotificationCompat.CATEGORY_EMAIL, ant.g);
            jSONObject.put("mobile", ant.h);
            jSONObject.put("sinaNick", ant.i);
            jSONObject.put("sinaID", ant.j);
            jSONObject.put("taobaoNick", ant.l);
            jSONObject.put("taobaoID", ant.m);
            jSONObject.put("taobaoToken", ant.k);
            jSONObject.put("qqNick", ant.n);
            jSONObject.put("qqID", ant.o);
            jSONObject.put("weixinNick", ant.p);
            jSONObject.put("weixinID", ant.q);
            jSONObject.put("alipayNick", ant.s);
            jSONObject.put("alipayID", ant.t);
            jSONObject.put("alipayScopeToken", a(ant.r));
            jSONObject.put("alipayUID", ant.u);
            jSONObject.put("source", ant.v);
            jSONObject.put("repwd", ant.w);
            jSONObject.put("carLogoID", ant.x);
            jSONObject.put("userLevel", ant.y);
            jSONObject.put("userCheckinCount", ant.z);
            jSONObject.put("userAchievementLevel", ant.A);
            jSONObject.put("emblemNum", ant.B);
            jSONObject.put("userCarLoginFlag", ant.C);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static ant a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ant ant = new ant();
        try {
            JSONObject jSONObject = new JSONObject(str);
            ant.a = jSONObject.optString(Oauth2AccessToken.KEY_UID);
            ant.b = jSONObject.optString("avatar");
            ant.c = jSONObject.optString("userName");
            ant.d = jSONObject.optString("birthday");
            ant.e = jSONObject.optString("nick");
            ant.f = jSONObject.optString("gender");
            ant.g = jSONObject.optString(NotificationCompat.CATEGORY_EMAIL);
            ant.h = jSONObject.optString("mobile");
            ant.i = jSONObject.optString("sinaNick");
            ant.j = jSONObject.optString("sinaID");
            ant.m = jSONObject.optString("taobaoID");
            ant.l = jSONObject.optString("taobaoNick");
            ant.k = jSONObject.optString("taobaoToken");
            ant.o = jSONObject.optString("qqID");
            ant.n = jSONObject.optString("qqNick");
            ant.q = jSONObject.optString("weixinID");
            ant.p = jSONObject.optString("weixinNick");
            ant.t = jSONObject.optString("alipayID");
            ant.s = jSONObject.optString("alipayNick");
            String optString = jSONObject.optString("alipayToken");
            String a = apa.a(optString);
            if (a != null) {
                ant.r.put(a, optString);
            } else {
                ant.r.putAll(b(jSONObject.optString("alipayScopeToken")));
            }
            ant.u = jSONObject.optString("alipayUID");
            ant.v = jSONObject.optString("source");
            ant.w = jSONObject.optString("repwd");
            ant.x = jSONObject.optString("carLogoID");
            ant.y = jSONObject.optString("userLevel");
            ant.z = jSONObject.optString("userCheckinCount");
            ant.A = jSONObject.optString("userAchievementLevel");
            ant.B = jSONObject.optString("emblemNum");
            ant.C = jSONObject.optString("userCarLoginFlag");
        } catch (JSONException e) {
            e.printStackTrace();
            ant = null;
        }
        return ant;
    }

    public static void a(dkm dkm, dkm dkm2) {
        dkm2.result = dkm.result;
        dkm2.code = dkm.code;
        dkm2.message = dkm.message;
        dkm2.errmsg = dkm.errmsg;
        dkm2.timestamp = dkm.timestamp;
        dkm2.version = dkm.version;
        dkm2.err_order_id = dkm.err_order_id;
    }

    private static String a(HashMap<String, String> hashMap) {
        JSONObject jSONObject = new JSONObject();
        if (hashMap == null || hashMap.size() == 0) {
            return jSONObject.toString();
        }
        for (Entry next : hashMap.entrySet()) {
            try {
                jSONObject.put((String) next.getKey(), next.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    private static HashMap<String, String> b(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (TextUtils.isEmpty(str)) {
            return hashMap;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, jSONObject.getString(next));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
