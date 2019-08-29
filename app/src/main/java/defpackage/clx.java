package defpackage;

import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: clx reason: default package */
/* compiled from: AccsConfigProvider */
class clx implements b {
    static lp b = new lp() {
        public final void onConfigCallBack(int i) {
        }

        public final void onConfigResultCallBack(int i, String str) {
            StringBuilder sb = new StringBuilder("[Manager] configCallback status = ");
            sb.append(i);
            sb.append(",result = ");
            sb.append(str);
            AMapLog.i("AccsConfig", sb.toString());
            clx.c(i, str);
            j.a(yr.a());
        }
    };
    lp a = new lp() {
        public final void onConfigCallBack(int i) {
        }

        public final void onConfigResultCallBack(int i, String str) {
            StringBuilder sb = new StringBuilder("[Manager] configCallback status = ");
            sb.append(i);
            sb.append(",result = ");
            sb.append(str);
            AMapLog.i("AccsConfig", sb.toString());
            clx.this.b(i, str);
        }
    };
    private List<String> c = new CopyOnWriteArrayList();
    private List<String> d = new CopyOnWriteArrayList();

    clx() {
    }

    public final String b() {
        return ConfigerHelper.getInstance().getAccsMode();
    }

    public final String a() {
        return ConfigerHelper.getInstance().getAccsAppkey();
    }

    public final List<String> d() {
        return this.c;
    }

    public final List<String> e() {
        return this.d;
    }

    public final boolean c() {
        return new MapSharePreference((String) "accs_network").getBooleanValue("ipv6Switch", false);
    }

    /* access modifiers changed from: private */
    public synchronized void b(int i, String str) {
        if (i == 3) {
            this.c.clear();
            this.d.clear();
        } else if ((i == 4 || i != 0 || i == 1) && str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONArray jSONArray = (JSONArray) jSONObject.get("urlBlackList");
                if (jSONArray != null) {
                    this.c.clear();
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        this.c.add((String) jSONArray.get(i2));
                    }
                    JSONArray jSONArray2 = (JSONArray) jSONObject.get("hostWhiteList");
                    if (jSONArray2 != null) {
                        this.d.clear();
                        for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                            this.d.add((String) jSONArray2.get(i3));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void c(int r2, java.lang.String r3) {
        /*
            java.lang.Class<clx> r0 = defpackage.clx.class
            monitor-enter(r0)
            r1 = 3
            if (r2 != r1) goto L_0x001c
            com.amap.bundle.mapstorage.MapSharePreference r2 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x001a }
            java.lang.String r3 = "accs_network"
            r2.<init>(r3)     // Catch:{ all -> 0x001a }
            android.content.SharedPreferences$Editor r2 = r2.edit()     // Catch:{ all -> 0x001a }
            android.content.SharedPreferences$Editor r2 = r2.clear()     // Catch:{ all -> 0x001a }
            r2.commit()     // Catch:{ all -> 0x001a }
            monitor-exit(r0)
            return
        L_0x001a:
            r2 = move-exception
            goto L_0x0052
        L_0x001c:
            r1 = 4
            if (r2 == r1) goto L_0x0024
            if (r2 == 0) goto L_0x0024
            r1 = 1
            if (r2 != r1) goto L_0x0054
        L_0x0024:
            if (r3 == 0) goto L_0x0054
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x004d }
            r2.<init>(r3)     // Catch:{ JSONException -> 0x004d }
            java.lang.String r3 = "ipv6Switch"
            boolean r3 = r2.has(r3)     // Catch:{ JSONException -> 0x004d }
            if (r3 == 0) goto L_0x004b
            java.lang.String r3 = "1"
            java.lang.String r1 = "ipv6Switch"
            java.lang.String r2 = r2.optString(r1)     // Catch:{ JSONException -> 0x004d }
            boolean r2 = r3.equals(r2)     // Catch:{ JSONException -> 0x004d }
            com.amap.bundle.mapstorage.MapSharePreference r3 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ JSONException -> 0x004d }
            java.lang.String r1 = "accs_network"
            r3.<init>(r1)     // Catch:{ JSONException -> 0x004d }
            java.lang.String r1 = "ipv6Switch"
            r3.putBooleanValue(r1, r2)     // Catch:{ JSONException -> 0x004d }
        L_0x004b:
            monitor-exit(r0)
            return
        L_0x004d:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x001a }
            goto L_0x0054
        L_0x0052:
            monitor-exit(r0)
            throw r2
        L_0x0054:
            monitor-exit(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.clx.c(int, java.lang.String):void");
    }
}
