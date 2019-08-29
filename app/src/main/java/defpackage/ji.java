package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ji reason: default package */
/* compiled from: AppUpgradeInfo */
public final class ji extends AbstractAOSParser {
    public static boolean y = false;
    public String a = "";
    public String b = "";
    public String c = "";
    public String d = "";
    public String e = "";
    public String f = "";
    public String g = "";
    public String h = "";
    public String i = "";
    public String j = "";
    public boolean k;
    public String l = "";
    public String m = "";
    public String n = "";
    public boolean o;
    public String p = "";
    public boolean q;
    public String r = "";
    public String s = "";
    public int t;
    public String u;
    public String v;
    public a w;
    public List<b> x = new ArrayList(5);

    /* renamed from: ji$a */
    /* compiled from: AppUpgradeInfo */
    public static class a {
        public String a;
        public String b;
        public C0090a c;

        /* renamed from: ji$a$a reason: collision with other inner class name */
        /* compiled from: AppUpgradeInfo */
        public static class C0090a {
            public String a;
            public String b;
            public String c;
            public String d;
            public String e;

            public final JSONObject a() {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("cancel", this.a);
                    jSONObject.put("download_now", this.b);
                    jSONObject.put("install_now", this.c);
                    jSONObject.put("quit", this.d);
                    jSONObject.put("download_auto", this.e);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                return jSONObject;
            }

            static C0090a a(JSONObject jSONObject) {
                if (jSONObject == null) {
                    return null;
                }
                C0090a aVar = new C0090a();
                aVar.a = jSONObject.optString("cancel");
                aVar.b = jSONObject.optString("download_now");
                aVar.c = jSONObject.optString("install_now");
                aVar.d = jSONObject.optString("quit");
                aVar.e = jSONObject.optString("download_auto");
                return aVar;
            }
        }

        public final JSONObject a() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("mobile_remind", this.a);
                jSONObject.put("finish", this.b);
                jSONObject.put("buttons", this.c.a());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jSONObject;
        }

        static a a(JSONObject jSONObject) {
            if (jSONObject == null) {
                return null;
            }
            a aVar = new a();
            aVar.a = jSONObject.optString("mobile_remind");
            aVar.b = jSONObject.optString("finish");
            aVar.c = C0090a.a(jSONObject.optJSONObject("buttons"));
            return aVar;
        }
    }

    /* renamed from: ji$b */
    /* compiled from: AppUpgradeInfo */
    public static class b {
        public String a;
        public String b;
        public String c;
        public String d;
        public int e;
        public String f;

        public final JSONObject a() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("title", this.a);
                jSONObject.put("desc", this.b);
                jSONObject.put("url", this.c);
                jSONObject.put("md5", this.d);
                jSONObject.put("orderNum", this.e);
                jSONObject.put("fileType", this.f);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            return jSONObject;
        }
    }

    public final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("para1", this.b);
            jSONObject.put("para2", this.c);
            jSONObject.put("title", this.a);
            jSONObject.put("para3", this.d);
            jSONObject.put(LocationParams.PARA_COMMON_DIV, this.e);
            jSONObject.put("appver", this.f);
            jSONObject.put("build", this.g);
            jSONObject.put("beta", this.i);
            jSONObject.put("span", this.j);
            jSONObject.put("force", this.k ? 1 : 0);
            jSONObject.put(H5Param.MENU_ICON, this.l);
            jSONObject.put("name", this.m);
            jSONObject.put("url", this.n);
            jSONObject.put(LogConstant.SPLASH_SCREEN_DOWNLOADED, this.o);
            jSONObject.put("package", this.p);
            jSONObject.put("days", this.h);
            jSONObject.put("md5", this.s);
            jSONObject.put("gray_res", this.u);
            jSONObject.put("versionNo", this.v);
            if (this.w != null) {
                jSONObject.put("doc", this.w.a());
            }
            if (this.x != null && this.x.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                for (b a2 : this.x) {
                    jSONArray.put(a2.a());
                }
                jSONObject.put("fileDesc", jSONArray.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public final void a(JSONObject jSONObject) {
        b(jSONObject);
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        JSONObject parseHeader = parseHeader(bArr);
        if (parseHeader != null) {
            JSONArray optJSONArray = parseHeader.optJSONArray("memo");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                JSONObject jSONObject = optJSONArray.getJSONObject(0);
                if (jSONObject != null) {
                    b(jSONObject);
                }
            }
        }
    }

    private void b(JSONObject jSONObject) {
        if (jSONObject != null) {
            boolean z = true;
            this.q = true;
            this.b = jSONObject.optString("para1");
            this.a = jSONObject.optString("title");
            this.c = jSONObject.optString("para2");
            this.d = jSONObject.optString("para3");
            this.e = jSONObject.optString(LocationParams.PARA_COMMON_DIV);
            this.f = jSONObject.optString("appver");
            this.g = jSONObject.optString("build");
            this.i = jSONObject.optString("beta");
            this.j = jSONObject.optString("span");
            if (jSONObject.optInt("force") != 1) {
                z = false;
            }
            this.k = z;
            this.l = jSONObject.optString(H5Param.MENU_ICON);
            this.m = jSONObject.optString("name");
            this.n = jSONObject.optString("url");
            this.o = jSONObject.optBoolean(LogConstant.SPLASH_SCREEN_DOWNLOADED);
            this.p = jSONObject.optString("package");
            this.h = jSONObject.optString("days");
            this.s = jSONObject.optString("md5");
            this.t = jSONObject.optInt(H5SensorPlugin.PARAM_INTERVAL, -1);
            this.u = jSONObject.optString("gray_res", "0");
            this.v = jSONObject.optString("versionNo");
            this.w = a.a(jSONObject.optJSONObject("doc"));
            JSONArray optJSONArray = jSONObject.optJSONArray("fileDesc");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i2);
                    if (optJSONObject != null) {
                        b bVar = new b();
                        bVar.a = optJSONObject.optString("title", "");
                        bVar.b = optJSONObject.optString("desc", "");
                        bVar.c = optJSONObject.optString("url", "");
                        bVar.d = optJSONObject.optString("md5", "");
                        bVar.e = optJSONObject.optInt("orderNum", -1);
                        bVar.f = optJSONObject.optString("type", "");
                        this.x.add(bVar);
                    }
                }
            }
        }
    }

    public final String getErrorDesc(int i2) {
        switch (i2) {
            case 0:
                return AMapAppGlobal.getApplication().getString(R.string.app_download_fail_unknown);
            case 1:
                return AMapAppGlobal.getApplication().getString(R.string.app_download_success);
            case 3:
                return AMapAppGlobal.getApplication().getString(R.string.app_download_fail_param_error);
            case 4:
                return AMapAppGlobal.getApplication().getString(R.string.app_download_fail_sign_error);
            case 5:
                return AMapAppGlobal.getApplication().getString(R.string.app_download_fail_verify_error);
            default:
                return "";
        }
    }
}
