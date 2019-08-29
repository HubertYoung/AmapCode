package defpackage;

import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.accs.utl.BaseMonitor;
import org.json.JSONArray;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

/* renamed from: bw reason: default package */
/* compiled from: StrategyResultParser */
public final class bw {

    /* renamed from: bw$a */
    /* compiled from: StrategyResultParser */
    public static class a {
        public final int a;
        public final String b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final String g;
        public final String h;

        public a(JSONObject jSONObject) {
            this.a = jSONObject.optInt("port");
            this.b = jSONObject.optString("protocol");
            this.c = jSONObject.optInt("cto");
            this.d = jSONObject.optInt("rto");
            this.e = jSONObject.optInt(ActionConstant.TYPE_RETRY);
            this.f = jSONObject.optInt("heartbeat");
            this.g = jSONObject.optString("rtt", "");
            this.h = jSONObject.optString("publickey");
        }
    }

    /* renamed from: bw$b */
    /* compiled from: StrategyResultParser */
    public static class b {
        public final String a;
        public final int b;
        public final String c;
        public final String d;
        public final String e;
        public final String[] f;
        public final String[] g;
        public final a[] h;
        public final e[] i;
        public final boolean j;
        public final boolean k;

        public b(JSONObject jSONObject) {
            this.a = jSONObject.optString("host");
            this.b = jSONObject.optInt("ttl");
            this.c = jSONObject.optString("safeAisles");
            this.d = jSONObject.optString("cname", null);
            this.e = jSONObject.optString("unit", null);
            boolean z = true;
            this.j = jSONObject.optInt("clear") != 1 ? false : z;
            this.k = jSONObject.optBoolean("effectNow");
            JSONArray optJSONArray = jSONObject.optJSONArray("ips");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                this.f = new String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    this.f[i2] = optJSONArray.optString(i2);
                }
            } else {
                this.f = null;
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("sips");
            if (optJSONArray2 == null || optJSONArray2.length() <= 0) {
                this.g = null;
            } else {
                int length2 = optJSONArray2.length();
                this.g = new String[length2];
                for (int i3 = 0; i3 < length2; i3++) {
                    this.g[i3] = optJSONArray2.optString(i3);
                }
            }
            JSONArray optJSONArray3 = jSONObject.optJSONArray("aisles");
            if (optJSONArray3 != null) {
                int length3 = optJSONArray3.length();
                this.h = new a[length3];
                for (int i4 = 0; i4 < length3; i4++) {
                    this.h[i4] = new a(optJSONArray3.optJSONObject(i4));
                }
            } else {
                this.h = null;
            }
            JSONArray optJSONArray4 = jSONObject.optJSONArray("strategies");
            if (optJSONArray4 == null || optJSONArray4.length() <= 0) {
                this.i = null;
                return;
            }
            int length4 = optJSONArray4.length();
            this.i = new e[length4];
            for (int i5 = 0; i5 < length4; i5++) {
                this.i[i5] = new e(optJSONArray4.optJSONObject(i5));
            }
        }
    }

    /* renamed from: bw$c */
    /* compiled from: StrategyResultParser */
    public static class c {
        public final String a;
        public final e[] b;

        public c(JSONObject jSONObject) {
            this.a = jSONObject.optString("host");
            JSONArray optJSONArray = jSONObject.optJSONArray("strategies");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                this.b = new e[length];
                for (int i = 0; i < length; i++) {
                    this.b[i] = new e(optJSONArray.optJSONObject(i));
                }
                return;
            }
            this.b = null;
        }
    }

    /* renamed from: bw$d */
    /* compiled from: StrategyResultParser */
    public static class d {
        public final String a;
        public final b[] b;
        public final c[] c;
        public final String d;
        public final String e;
        public final int f;
        public final int g;
        public final int h;

        public d(JSONObject jSONObject) {
            this.a = jSONObject.optString(OnNativeInvokeListener.ARG_IP);
            this.d = jSONObject.optString(Oauth2AccessToken.KEY_UID, null);
            this.e = jSONObject.optString("utdid", null);
            this.f = jSONObject.optInt("cv");
            this.g = jSONObject.optInt("fcl");
            this.h = jSONObject.optInt("fct");
            JSONArray optJSONArray = jSONObject.optJSONArray(BaseMonitor.COUNT_POINT_DNS);
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                this.b = new b[length];
                for (int i = 0; i < length; i++) {
                    this.b[i] = new b(optJSONArray.optJSONObject(i));
                }
            } else {
                this.b = null;
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("hrTask");
            if (optJSONArray2 != null) {
                int length2 = optJSONArray2.length();
                this.c = new c[length2];
                for (int i2 = 0; i2 < length2; i2++) {
                    this.c[i2] = new c(optJSONArray2.optJSONObject(i2));
                }
                return;
            }
            this.c = null;
        }
    }

    /* renamed from: bw$e */
    /* compiled from: StrategyResultParser */
    public static class e {
        public final String a;
        public final a b;
        public final String c;

        public e(JSONObject jSONObject) {
            this.a = jSONObject.optString(OnNativeInvokeListener.ARG_IP);
            this.c = jSONObject.optString("path");
            this.b = new a(jSONObject);
        }
    }

    public static d a(JSONObject jSONObject) {
        try {
            return new d(jSONObject);
        } catch (Exception unused) {
            cl.e("StrategyResultParser", "Parse HttpDns response failed.", null, "JSON Content", jSONObject.toString());
            return null;
        }
    }
}
