package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: hv reason: default package */
/* compiled from: ShapeGroup */
public final class hv implements hn {
    public final String a;
    public final List<hn> b;

    /* renamed from: hv$a */
    /* compiled from: ShapeGroup */
    static class a {
        static hv a(JSONObject jSONObject, ev evVar) {
            JSONArray optJSONArray = jSONObject.optJSONArray("it");
            String optString = jSONObject.optString(LogItem.MM_C18_K4_NM);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                hn a = hv.a(optJSONArray.optJSONObject(i), evVar);
                if (a != null) {
                    arrayList.add(a);
                }
            }
            return new hv(optString, arrayList);
        }
    }

    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.hn a(org.json.JSONObject r2, defpackage.ev r3) {
        /*
            java.lang.String r0 = "ty"
            java.lang.String r0 = r2.optString(r0)
            int r1 = r0.hashCode()
            switch(r1) {
                case 3239: goto L_0x0092;
                case 3270: goto L_0x0088;
                case 3295: goto L_0x007e;
                case 3307: goto L_0x0074;
                case 3308: goto L_0x006a;
                case 3488: goto L_0x005f;
                case 3633: goto L_0x0054;
                case 3646: goto L_0x0049;
                case 3669: goto L_0x003f;
                case 3679: goto L_0x0034;
                case 3681: goto L_0x0029;
                case 3705: goto L_0x001c;
                case 3710: goto L_0x0010;
                default: goto L_0x000e;
            }
        L_0x000e:
            goto L_0x009c
        L_0x0010:
            java.lang.String r1 = "tr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 5
            goto L_0x009d
        L_0x001c:
            java.lang.String r1 = "tm"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 9
            goto L_0x009d
        L_0x0029:
            java.lang.String r1 = "st"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 1
            goto L_0x009d
        L_0x0034:
            java.lang.String r1 = "sr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 10
            goto L_0x009d
        L_0x003f:
            java.lang.String r1 = "sh"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 6
            goto L_0x009d
        L_0x0049:
            java.lang.String r1 = "rp"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 12
            goto L_0x009d
        L_0x0054:
            java.lang.String r1 = "rc"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 8
            goto L_0x009d
        L_0x005f:
            java.lang.String r1 = "mm"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 11
            goto L_0x009d
        L_0x006a:
            java.lang.String r1 = "gs"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 2
            goto L_0x009d
        L_0x0074:
            java.lang.String r1 = "gr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 0
            goto L_0x009d
        L_0x007e:
            java.lang.String r1 = "gf"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 4
            goto L_0x009d
        L_0x0088:
            java.lang.String r1 = "fl"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 3
            goto L_0x009d
        L_0x0092:
            java.lang.String r1 = "el"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009c
            r0 = 7
            goto L_0x009d
        L_0x009c:
            r0 = -1
        L_0x009d:
            switch(r0) {
                case 0: goto L_0x00de;
                case 1: goto L_0x00d9;
                case 2: goto L_0x00d4;
                case 3: goto L_0x00cf;
                case 4: goto L_0x00ca;
                case 5: goto L_0x00c5;
                case 6: goto L_0x00c0;
                case 7: goto L_0x00bb;
                case 8: goto L_0x00b6;
                case 9: goto L_0x00b1;
                case 10: goto L_0x00ac;
                case 11: goto L_0x00a7;
                case 12: goto L_0x00a2;
                default: goto L_0x00a0;
            }
        L_0x00a0:
            r2 = 0
            return r2
        L_0x00a2:
            hs r2 = defpackage.hs.a.a(r2, r3)
            return r2
        L_0x00a7:
            com.airbnb.lottie.model.content.MergePaths r2 = com.airbnb.lottie.model.content.MergePaths.a.a(r2)
            return r2
        L_0x00ac:
            com.airbnb.lottie.model.content.PolystarShape r2 = com.airbnb.lottie.model.content.PolystarShape.a.a(r2, r3)
            return r2
        L_0x00b1:
            com.airbnb.lottie.model.content.ShapeTrimPath r2 = com.airbnb.lottie.model.content.ShapeTrimPath.a.a(r2, r3)
            return r2
        L_0x00b6:
            hr r2 = defpackage.hr.a.a(r2, r3)
            return r2
        L_0x00bb:
            hm r2 = defpackage.hm.a.a(r2, r3)
            return r2
        L_0x00c0:
            hw r2 = defpackage.hw.a.a(r2, r3)
            return r2
        L_0x00c5:
            hi r2 = defpackage.hi.a.a(r2, r3)
            return r2
        L_0x00ca:
            hp r2 = defpackage.hp.a.a(r2, r3)
            return r2
        L_0x00cf:
            hu r2 = defpackage.hu.a.a(r2, r3)
            return r2
        L_0x00d4:
            hq r2 = defpackage.hq.a.a(r2, r3)
            return r2
        L_0x00d9:
            com.airbnb.lottie.model.content.ShapeStroke r2 = com.airbnb.lottie.model.content.ShapeStroke.a.a(r2, r3)
            return r2
        L_0x00de:
            hv r2 = defpackage.hv.a.a(r2, r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hv.a(org.json.JSONObject, ev):hn");
    }

    public hv(String str, List<hn> list) {
        this.a = str;
        this.b = list;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new ff(ewVar, hxVar, this);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ShapeGroup{name='");
        sb.append(this.a);
        sb.append("' Shapes: ");
        sb.append(Arrays.toString(this.b.toArray()));
        sb.append('}');
        return sb.toString();
    }
}
