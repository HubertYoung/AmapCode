package com.airbnb.lottie.model.content;

import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.support.annotation.Nullable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public final class ShapeStroke implements hn {
    public final String a;
    @Nullable
    public final gy b;
    public final List<gy> c;
    public final gx d;
    public final ha e;
    public final gy f;
    public final LineCapType g;
    public final LineJoinType h;

    public enum LineCapType {
        Butt,
        Round,
        Unknown;

        public final Cap toPaintCap() {
            switch (this) {
                case Butt:
                    return Cap.BUTT;
                case Round:
                    return Cap.ROUND;
                default:
                    return Cap.SQUARE;
            }
        }
    }

    public enum LineJoinType {
        Miter,
        Round,
        Bevel;

        public final Join toPaintJoin() {
            switch (this) {
                case Bevel:
                    return Join.BEVEL;
                case Miter:
                    return Join.MITER;
                case Round:
                    return Join.ROUND;
                default:
                    return null;
            }
        }
    }

    public static class a {
        public static ShapeStroke a(JSONObject jSONObject, ev evVar) {
            String optString = jSONObject.optString(LogItem.MM_C18_K4_NM);
            ArrayList arrayList = new ArrayList();
            gx a = defpackage.gx.a.a(jSONObject.optJSONObject(SuperId.BIT_1_NEARBY_SEARCH), evVar);
            gy a2 = defpackage.gy.a.a(jSONObject.optJSONObject("w"), evVar, true);
            ha a3 = defpackage.ha.a.a(jSONObject.optJSONObject("o"), evVar);
            LineCapType lineCapType = LineCapType.values()[jSONObject.optInt("lc") - 1];
            LineJoinType lineJoinType = LineJoinType.values()[jSONObject.optInt("lj") - 1];
            gy gyVar = null;
            if (jSONObject.has("d")) {
                JSONArray optJSONArray = jSONObject.optJSONArray("d");
                gy gyVar2 = null;
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    String optString2 = optJSONObject.optString(SuperId.BIT_1_MAIN_BUSSTATION);
                    if (optString2.equals("o")) {
                        gyVar2 = defpackage.gy.a.a(optJSONObject.optJSONObject("v"), evVar, true);
                    } else if (optString2.equals("d") || optString2.equals(SuperId.BIT_1_NAVI)) {
                        arrayList.add(defpackage.gy.a.a(optJSONObject.optJSONObject("v"), evVar, true));
                    }
                }
                if (arrayList.size() == 1) {
                    arrayList.add(arrayList.get(0));
                }
                gyVar = gyVar2;
            }
            ShapeStroke shapeStroke = new ShapeStroke(optString, gyVar, arrayList, a, a3, a2, lineCapType, lineJoinType, 0);
            return shapeStroke;
        }
    }

    /* synthetic */ ShapeStroke(String str, gy gyVar, List list, gx gxVar, ha haVar, gy gyVar2, LineCapType lineCapType, LineJoinType lineJoinType, byte b2) {
        this(str, gyVar, list, gxVar, haVar, gyVar2, lineCapType, lineJoinType);
    }

    private ShapeStroke(String str, @Nullable gy gyVar, List<gy> list, gx gxVar, ha haVar, gy gyVar2, LineCapType lineCapType, LineJoinType lineJoinType) {
        this.a = str;
        this.b = gyVar;
        this.c = list;
        this.d = gxVar;
        this.e = haVar;
        this.f = gyVar2;
        this.g = lineCapType;
        this.h = lineJoinType;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new fs(ewVar, hxVar, this);
    }
}
