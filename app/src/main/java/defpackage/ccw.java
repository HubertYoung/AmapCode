package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ccw reason: default package */
/* compiled from: SuspendViewJsHelper */
public final class ccw {

    /* renamed from: ccw$a */
    /* compiled from: SuspendViewJsHelper */
    public static class a {
        public String a;
        public String b;
        public int c;
        public int d;
        public String e;
        public double f;
        public boolean g;
        public String h;
        public boolean i;
        public String j;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(defpackage.ccw.a r7) {
        /*
            r0 = -1
            if (r7 == 0) goto L_0x0063
            java.lang.String r1 = r7.b
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x000c
            goto L_0x0063
        L_0x000c:
            java.lang.String r7 = r7.b
            int r1 = r7.hashCode()
            r2 = 3
            r3 = 5
            r4 = 4
            r5 = 2
            r6 = 1
            switch(r1) {
                case -1436089959: goto L_0x004d;
                case -901823641: goto L_0x0043;
                case -882608751: goto L_0x0039;
                case 55433166: goto L_0x002f;
                case 1626916114: goto L_0x0025;
                case 1646131004: goto L_0x001b;
                default: goto L_0x001a;
            }
        L_0x001a:
            goto L_0x0057
        L_0x001b:
            java.lang.String r1 = "leftCenter"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0057
            r7 = 1
            goto L_0x0058
        L_0x0025:
            java.lang.String r1 = "leftBottom"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0057
            r7 = 2
            goto L_0x0058
        L_0x002f:
            java.lang.String r1 = "leftTop"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0057
            r7 = 0
            goto L_0x0058
        L_0x0039:
            java.lang.String r1 = "rightCenter"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0057
            r7 = 4
            goto L_0x0058
        L_0x0043:
            java.lang.String r1 = "rightBottom"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0057
            r7 = 5
            goto L_0x0058
        L_0x004d:
            java.lang.String r1 = "rightTop"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0057
            r7 = 3
            goto L_0x0058
        L_0x0057:
            r7 = -1
        L_0x0058:
            switch(r7) {
                case 0: goto L_0x0062;
                case 1: goto L_0x0061;
                case 2: goto L_0x0060;
                case 3: goto L_0x005f;
                case 4: goto L_0x005e;
                case 5: goto L_0x005c;
                default: goto L_0x005b;
            }
        L_0x005b:
            return r0
        L_0x005c:
            r7 = 6
            return r7
        L_0x005e:
            return r3
        L_0x005f:
            return r4
        L_0x0060:
            return r2
        L_0x0061:
            return r5
        L_0x0062:
            return r6
        L_0x0063:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ccw.a(ccw$a):int");
    }

    public static List<a> a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            ArrayList arrayList = new ArrayList();
            while (keys.hasNext()) {
                String next = keys.next();
                if (!TextUtils.isEmpty(next)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                    a aVar = new a();
                    aVar.a = next;
                    aVar.b = jSONObject2.optString("position", "");
                    aVar.c = jSONObject2.optInt("width", 0);
                    aVar.d = jSONObject2.optInt("height", 0);
                    aVar.e = jSONObject2.optString(ActionConstant.IMG_URL, "");
                    aVar.j = jSONObject2.optString("description", "");
                    aVar.f = jSONObject2.optDouble("opacity", 0.0d);
                    aVar.g = jSONObject2.optBoolean("hidden", false);
                    aVar.h = jSONObject2.optString("tip", "");
                    arrayList.add(aVar);
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<a> b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            ArrayList arrayList = new ArrayList();
            while (keys.hasNext()) {
                String next = keys.next();
                if (!TextUtils.isEmpty(next)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                    a aVar = new a();
                    aVar.a = next;
                    aVar.g = jSONObject2.optBoolean("hidden", false);
                    aVar.i = jSONObject2.optBoolean("showTip", false);
                    arrayList.add(aVar);
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
