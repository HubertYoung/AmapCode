package defpackage;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"realtimeBus"})
/* renamed from: dxv reason: default package */
/* compiled from: RealTimeBusRouter */
public class dxv extends esk {
    private static String a(Uri uri) {
        if (uri == null) {
            return "";
        }
        String queryParameter = uri.getQueryParameter("param");
        String queryParameter2 = uri.getQueryParameter("from");
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(queryParameter2)) {
                jSONObject.put("from", queryParameter2);
            }
            if (!TextUtils.isEmpty(queryParameter)) {
                jSONObject.put("param", new JSONObject(queryParameter));
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String b(Uri uri) {
        String queryParameter = uri.getQueryParameter("param");
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(queryParameter)) {
                JSONObject jSONObject2 = new JSONObject(queryParameter);
                Iterator<String> keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    jSONObject.put(next, jSONObject2.get(next));
                }
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0046 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0068  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.lang.String r4, android.net.Uri r5) {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = -1335224239(0xffffffffb06a1851, float:-8.516326E-10)
            r2 = 0
            if (r0 == r1) goto L_0x0038
            r1 = -191501435(0xfffffffff495eb85, float:-9.502309E31)
            if (r0 == r1) goto L_0x002e
            r1 = 107868(0x1a55c, float:1.51155E-40)
            if (r0 == r1) goto L_0x0024
            r1 = 3208415(0x30f4df, float:4.495947E-39)
            if (r0 == r1) goto L_0x001a
            goto L_0x0042
        L_0x001a:
            java.lang.String r0 = "home"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0042
            r4 = 0
            goto L_0x0043
        L_0x0024:
            java.lang.String r0 = "map"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0042
            r4 = 2
            goto L_0x0043
        L_0x002e:
            java.lang.String r0 = "feedback"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0042
            r4 = 3
            goto L_0x0043
        L_0x0038:
            java.lang.String r0 = "detail"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0042
            r4 = 1
            goto L_0x0043
        L_0x0042:
            r4 = -1
        L_0x0043:
            switch(r4) {
                case 0: goto L_0x0068;
                case 1: goto L_0x005d;
                case 2: goto L_0x0052;
                case 3: goto L_0x0047;
                default: goto L_0x0046;
            }
        L_0x0046:
            return r2
        L_0x0047:
            java.lang.String r4 = "param"
            java.lang.String r4 = r5.getQueryParameter(r4)
            boolean r4 = b(r4)
            return r4
        L_0x0052:
            java.lang.String r4 = "param"
            java.lang.String r4 = r5.getQueryParameter(r4)
            boolean r4 = a(r4)
            return r4
        L_0x005d:
            java.lang.String r4 = "path://amap_bundle_realbus/src/pages/realtime_detail/realtime_detail.page.js"
            java.lang.String r5 = b(r5)
            boolean r4 = r3.a(r4, r5)
            return r4
        L_0x0068:
            java.lang.String r4 = "path://amap_bundle_realbus/src/pages/realtime_index/realtime_index.page.js"
            java.lang.String r5 = a(r5)
            boolean r4 = r3.a(r4, r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dxv.a(java.lang.String, android.net.Uri):boolean");
    }

    private boolean a(@NonNull String str, @Nullable String str2) {
        try {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", str);
            if (!TextUtils.isEmpty(str2)) {
                pageBundle.putString("jsData", str2);
            }
            startPage(Ajx3Page.class, pageBundle);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments != null && pathSegments.size() > 0) {
            return a(pathSegments.get(0), uri);
        }
        if ("RealTimeBusPosition".equalsIgnoreCase(uri.getQueryParameter("SCHEME_PARAM_KEY_FEATURE_NAME"))) {
            return a((String) "home", uri);
        }
        return false;
    }

    private static boolean a(String str) {
        asy asy = (asy) a.a.a(asy.class);
        if (asy != null) {
            asy.a(str);
        }
        return true;
    }

    private static boolean b(String str) {
        asy asy = (asy) a.a.a(asy.class);
        if (asy != null) {
            asy.b(str);
        }
        return true;
    }
}
