package defpackage;

import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.ShapeStroke.LineCapType;
import com.airbnb.lottie.model.content.ShapeStroke.LineJoinType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: hq reason: default package */
/* compiled from: GradientStroke */
public final class hq implements hn {
    public final String a;
    public final GradientType b;
    public final gz c;
    public final ha d;
    public final hc e;
    public final hc f;
    public final gy g;
    public final LineCapType h;
    public final LineJoinType i;
    public final List<gy> j;
    @Nullable
    public final gy k;

    /* renamed from: hq$a */
    /* compiled from: GradientStroke */
    static class a {
        static hq a(JSONObject jSONObject, ev evVar) {
            gy gyVar;
            JSONObject jSONObject2 = jSONObject;
            ev evVar2 = evVar;
            String optString = jSONObject2.optString(LogItem.MM_C18_K4_NM);
            JSONObject optJSONObject = jSONObject2.optJSONObject(SuperId.BIT_1_NAVI);
            if (optJSONObject != null && optJSONObject.has(SuperId.BIT_1_REALTIMEBUS_BUSSTATION)) {
                optJSONObject = optJSONObject.optJSONObject(SuperId.BIT_1_REALTIMEBUS_BUSSTATION);
            }
            gz a = optJSONObject != null ? defpackage.gz.a.a(optJSONObject, evVar2) : null;
            JSONObject optJSONObject2 = jSONObject2.optJSONObject("o");
            ha a2 = optJSONObject2 != null ? defpackage.ha.a.a(optJSONObject2, evVar2) : null;
            int i = 1;
            GradientType gradientType = jSONObject2.optInt(LogItem.MM_C15_K4_TIME, 1) == 1 ? GradientType.Linear : GradientType.Radial;
            JSONObject optJSONObject3 = jSONObject2.optJSONObject("s");
            hc a3 = optJSONObject3 != null ? defpackage.hc.a.a(optJSONObject3, evVar2) : null;
            JSONObject optJSONObject4 = jSONObject2.optJSONObject("e");
            hc a4 = optJSONObject4 != null ? defpackage.hc.a.a(optJSONObject4, evVar2) : null;
            gy a5 = defpackage.gy.a.a(jSONObject2.optJSONObject("w"), evVar2, true);
            LineCapType lineCapType = LineCapType.values()[jSONObject2.optInt("lc") - 1];
            LineJoinType lineJoinType = LineJoinType.values()[jSONObject2.optInt("lj") - 1];
            ArrayList arrayList = new ArrayList();
            if (jSONObject2.has("d")) {
                JSONArray optJSONArray = jSONObject2.optJSONArray("d");
                gy gyVar2 = null;
                int i2 = 0;
                while (i2 < optJSONArray.length()) {
                    JSONObject optJSONObject5 = optJSONArray.optJSONObject(i2);
                    String optString2 = optJSONObject5.optString(SuperId.BIT_1_MAIN_BUSSTATION);
                    JSONArray jSONArray = optJSONArray;
                    if (optString2.equals("o")) {
                        i = 1;
                        gyVar2 = defpackage.gy.a.a(optJSONObject5.optJSONObject("v"), evVar2, true);
                    } else if (optString2.equals("d") || optString2.equals(SuperId.BIT_1_NAVI)) {
                        i = 1;
                        arrayList.add(defpackage.gy.a.a(optJSONObject5.optJSONObject("v"), evVar2, true));
                    } else {
                        i = 1;
                    }
                    i2++;
                    optJSONArray = jSONArray;
                }
                if (arrayList.size() == i) {
                    arrayList.add(arrayList.get(0));
                }
                gyVar = gyVar2;
            } else {
                gyVar = null;
            }
            hq hqVar = new hq(optString, gradientType, a, a2, a3, a4, a5, lineCapType, lineJoinType, arrayList, gyVar, 0);
            return hqVar;
        }
    }

    /* synthetic */ hq(String str, GradientType gradientType, gz gzVar, ha haVar, hc hcVar, hc hcVar2, gy gyVar, LineCapType lineCapType, LineJoinType lineJoinType, List list, gy gyVar2, byte b2) {
        this(str, gradientType, gzVar, haVar, hcVar, hcVar2, gyVar, lineCapType, lineJoinType, list, gyVar2);
    }

    private hq(String str, GradientType gradientType, gz gzVar, ha haVar, hc hcVar, hc hcVar2, gy gyVar, LineCapType lineCapType, LineJoinType lineJoinType, List<gy> list, @Nullable gy gyVar2) {
        this.a = str;
        this.b = gradientType;
        this.c = gzVar;
        this.d = haVar;
        this.e = hcVar;
        this.f = hcVar2;
        this.g = gyVar;
        this.h = lineCapType;
        this.i = lineJoinType;
        this.j = list;
        this.k = gyVar2;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new fk(ewVar, hxVar, this);
    }
}
