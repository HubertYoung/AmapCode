package defpackage;

import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.request.param.NetworkParam;
import java.util.LinkedHashMap;
import org.json.JSONObject;

/* renamed from: aaq reason: default package */
/* compiled from: TokenManager */
public final class aaq {
    private volatile a a = c(e());

    /* renamed from: aaq$a */
    /* compiled from: TokenManager */
    static class a {
        String a;
        int b;
        long c;
        String d;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001c, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean a() {
        /*
            r6 = this;
            monitor-enter(r6)
            aaq$a r0 = r6.a     // Catch:{ all -> 0x001f }
            r1 = 0
            if (r0 == 0) goto L_0x001d
            aaq$a r0 = r6.a     // Catch:{ all -> 0x001f }
            if (r0 == 0) goto L_0x001b
            int r2 = r0.b     // Catch:{ all -> 0x001f }
            if (r2 != 0) goto L_0x001b
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x001f }
            long r4 = r0.c     // Catch:{ all -> 0x001f }
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 > 0) goto L_0x001b
            r0 = 1
            monitor-exit(r6)
            return r0
        L_0x001b:
            monitor-exit(r6)
            return r1
        L_0x001d:
            monitor-exit(r6)
            return r1
        L_0x001f:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aaq.a():boolean");
    }

    public final synchronized boolean b() {
        try {
            if (this.a == null) {
                return false;
            }
            String b = aaf.b(ConfigerHelper.AOS_URL_KEY);
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            sb.append("/api/parameter/upload");
            if (!TextUtils.equals(this.a.d, b(sb.toString()))) {
                return true;
            }
            return false;
        }
    }

    public final synchronized int c() {
        int i;
        i = -1;
        try {
            if (this.a != null) {
                i = this.a.b;
            }
        }
        return i;
    }

    public final synchronized boolean a(String str) {
        try {
            AMapLog.e("TokenManager", "updateTokenInfo + ".concat(String.valueOf(str)));
            this.a = c(str);
            d(str);
        }
        return this.a != null;
    }

    public final synchronized boolean a(int i) {
        if (this.a == null) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ver", this.a.a);
            jSONObject.put("status", i);
            jSONObject.put("ex_time", this.a.c);
            jSONObject.put("pub_param", this.a.d);
            this.a.b = i;
            d(jSONObject.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static a c(String str) {
        a aVar;
        AMapLog.e("TokenManager", "parse + tokenInfoStr".concat(String.valueOf(str)));
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            aVar = new a(0);
            aVar.a = jSONObject.getString("ver");
            aVar.b = jSONObject.getInt("status");
            aVar.c = jSONObject.getLong("ex_time");
            aVar.d = jSONObject.getString("pub_param");
        } catch (Exception e) {
            e.printStackTrace();
            aVar = null;
        }
        return aVar;
    }

    private synchronized boolean d(String str) {
        if (aaf.a() == null) {
            return false;
        }
        new MapSharePreference((String) "tag_netparam_token").putStringValue("tag_netparam_token_src_key", str);
        return true;
    }

    public final synchronized boolean d() {
        AMapLog.e("TokenManager", "TokenManager + clear");
        this.a = null;
        if (aaf.a() == null) {
            return false;
        }
        Editor edit = new MapSharePreference((String) "tag_netparam_token").edit();
        edit.clear();
        edit.commit();
        return true;
    }

    private synchronized String e() {
        String str;
        str = "";
        if (aaf.a() != null) {
            str = new MapSharePreference((String) "tag_netparam_token").getStringValue("tag_netparam_token_src_key", "");
        }
        return str;
    }

    public static String b(String str) {
        String b = aao.d().b().b();
        if (TextUtils.isEmpty(b)) {
            return b;
        }
        String[] split = b.split(",");
        LinkedHashMap<String, String> networkParamMap = NetworkParam.getNetworkParamMap(str);
        networkParamMap.remove("ct_id");
        for (String str2 : split) {
            if (!TextUtils.isEmpty(str2)) {
                networkParamMap.remove(str2);
            }
        }
        return networkParamMap.toString();
    }
}
