package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: clz reason: default package */
/* compiled from: ApmConfigProvider */
class clz implements c, lp {
    private boolean a = true;
    private volatile a b = new a();

    /* renamed from: clz$a */
    /* compiled from: ApmConfigProvider */
    static class a {
        Map<String, Boolean> a = new HashMap();

        public final void a(String str, boolean z) {
            if (!TextUtils.isEmpty(str)) {
                this.a.put(str, Boolean.valueOf(z));
            }
        }
    }

    public void onConfigCallBack(int i) {
    }

    public clz() {
        b(b());
    }

    public final boolean a() {
        return this.a;
    }

    public final boolean a(String str) {
        boolean z = false;
        if (this.b != null) {
            a aVar = this.b;
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split("://", 2);
                String[] split2 = split[split.length - 1].split("\\?")[0].split("/", 2);
                Boolean bool = aVar.a.get(split2[split2.length - 1]);
                if (bool != null && bool.booleanValue()) {
                    z = true;
                }
            }
        }
        StringBuilder sb = new StringBuilder("[");
        sb.append(z);
        sb.append("]canPassFilter[");
        sb.append(str);
        sb.append("]");
        AMapLog.e("ApmConfig", sb.toString());
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0040, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void b(java.lang.String r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "ApmConfig"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0047 }
            java.lang.String r2 = "parseConfig["
            r1.<init>(r2)     // Catch:{ all -> 0x0047 }
            r1.append(r4)     // Catch:{ all -> 0x0047 }
            java.lang.String r2 = "]"
            r1.append(r2)     // Catch:{ all -> 0x0047 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0047 }
            com.amap.bundle.logs.AMapLog.e(r0, r1)     // Catch:{ all -> 0x0047 }
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0047 }
            if (r0 != 0) goto L_0x0045
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0041 }
            r0.<init>(r4)     // Catch:{ Exception -> 0x0041 }
            java.lang.String r4 = "apm_enable"
            r1 = 0
            int r4 = r0.optInt(r4, r1)     // Catch:{ Exception -> 0x0041 }
            r2 = 1
            if (r4 != r2) goto L_0x002f
            r1 = 1
        L_0x002f:
            r3.a = r1     // Catch:{ Exception -> 0x0041 }
            java.lang.String r4 = "apm_filter_config"
            java.lang.String r4 = r0.optString(r4)     // Catch:{ Exception -> 0x0041 }
            clz$a r4 = c(r4)     // Catch:{ Exception -> 0x0041 }
            if (r4 == 0) goto L_0x003f
            r3.b = r4     // Catch:{ Exception -> 0x0041 }
        L_0x003f:
            monitor-exit(r3)
            return
        L_0x0041:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x0047 }
        L_0x0045:
            monitor-exit(r3)
            return
        L_0x0047:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.clz.b(java.lang.String):void");
    }

    private static a c(String str) {
        a aVar;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            aVar = new a();
            JSONArray optJSONArray = jSONObject.optJSONArray("whiteList");
            JSONArray optJSONArray2 = jSONObject.optJSONArray("blackList");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                for (int i = 0; i < length; i++) {
                    String string = optJSONArray.getString(i);
                    if (!TextUtils.isEmpty(string)) {
                        String[] split = string.split("://", 2);
                        aVar.a(split[split.length - 1].split("\\?")[0], true);
                    }
                }
            }
            if (optJSONArray2 != null) {
                int length2 = optJSONArray2.length();
                for (int i2 = 0; i2 < length2; i2++) {
                    String string2 = optJSONArray2.getString(i2);
                    if (!TextUtils.isEmpty(string2)) {
                        aVar.a(string2, false);
                    }
                }
            }
        } catch (Exception unused) {
            aVar = null;
        }
        return aVar;
    }

    public void onConfigResultCallBack(int i, String str) {
        StringBuilder sb = new StringBuilder("status[");
        sb.append(i);
        sb.append("]result : ");
        sb.append(str);
        AMapLog.e("ApmConfig", sb.toString());
        if (i != 3) {
            switch (i) {
                case 0:
                case 1:
                    b(str);
                    d(str);
                    return;
            }
        } else {
            c();
        }
    }

    private synchronized boolean d(String str) {
        if (AMapPageUtil.getAppContext() == null) {
            return false;
        }
        new MapSharePreference((String) "net_apm_log_config").putStringValue("net_apm_log_config_sp_key", str);
        return true;
    }

    private synchronized String b() {
        String str;
        str = "";
        if (AMapPageUtil.getAppContext() != null) {
            str = new MapSharePreference((String) "net_apm_log_config").getStringValue("net_apm_log_config_sp_key", "");
        }
        return str;
    }

    private synchronized boolean c() {
        if (AMapPageUtil.getAppContext() == null) {
            return false;
        }
        new MapSharePreference((String) "net_apm_log_config").edit().clear().apply();
        return true;
    }
}
