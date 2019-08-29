package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: cmf reason: default package */
/* compiled from: ParamOptConfigProvider */
class cmf implements h, lp {
    private volatile a a;
    private volatile boolean b;
    private volatile String c;
    private volatile Set<String> d = Collections.synchronizedSet(new HashSet());

    /* renamed from: cmf$a */
    /* compiled from: ParamOptConfigProvider */
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

    public cmf() {
        c("channel,sign,ent,tid,adiu,dip,div,diu,diu2,diu3,dic,uid,csid,stepid,session,cifa,spm,appstartid");
        b(d());
    }

    public final boolean a(String str) {
        if (this.a == null) {
            return false;
        }
        a aVar = this.a;
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("://", 2);
            String[] split2 = split[split.length - 1].split("\\?")[0].split("/", 2);
            Boolean bool = aVar.a.get(split2[split2.length - 1]);
            if (bool == null || !bool.booleanValue()) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final boolean a() {
        return this.b;
    }

    public final String b() {
        return this.c;
    }

    public final Set<String> c() {
        return this.d;
    }

    public void onConfigResultCallBack(int i, String str) {
        StringBuilder sb = new StringBuilder("status[");
        sb.append(i);
        sb.append("]result : ");
        sb.append(str);
        AMapLog.e("OptConfigManager", sb.toString());
        if (i != 3) {
            switch (i) {
                case 0:
                case 1:
                    b(str);
                    e(str);
                    return;
            }
        } else {
            e();
        }
    }

    private void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                boolean z = false;
                if (jSONObject.optInt("opt_param_enable", 0) == 1) {
                    z = true;
                }
                this.b = z;
                String optString = jSONObject.optString("filter_config");
                c(jSONObject.optString("opt_param_keys"));
                a d2 = d(optString);
                if (d2 != null) {
                    this.a = d2;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void c(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.c = str;
            this.d.clear();
            this.d.addAll(Arrays.asList(str.split(",")));
        }
    }

    private static a d(String str) {
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

    private synchronized boolean e(String str) {
        if (AMapPageUtil.getAppContext() == null) {
            return false;
        }
        new MapSharePreference((String) "tag_netparam_url_filter").putStringValue("tag_netparam_url_filter_src_key", str);
        return true;
    }

    private synchronized String d() {
        String str;
        str = "";
        if (AMapPageUtil.getAppContext() != null) {
            str = new MapSharePreference((String) "tag_netparam_url_filter").getStringValue("tag_netparam_url_filter_src_key", "");
        }
        return str;
    }

    private synchronized boolean e() {
        this.a = null;
        this.b = false;
        if (AMapPageUtil.getAppContext() == null) {
            return false;
        }
        new MapSharePreference((String) "tag_netparam_url_filter").edit().clear().apply();
        return true;
    }
}
