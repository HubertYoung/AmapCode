package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import org.json.JSONObject;

public final class PolystarShape implements hn {
    public final String a;
    public final Type b;
    public final gy c;
    public final hj<PointF, PointF> d;
    public final gy e;
    public final gy f;
    public final gy g;
    public final gy h;
    public final gy i;

    public enum Type {
        Star(1),
        Polygon(2);
        
        private final int value;

        private Type(int i) {
            this.value = i;
        }

        static Type a(int i) {
            Type[] values;
            for (Type type : values()) {
                if (type.value == i) {
                    return type;
                }
            }
            return null;
        }
    }

    public static class a {
        public static PolystarShape a(JSONObject jSONObject, ev evVar) {
            gy gyVar;
            String optString = jSONObject.optString(LogItem.MM_C18_K4_NM);
            Type a = Type.a(jSONObject.optInt("sy"));
            gy a2 = defpackage.gy.a.a(jSONObject.optJSONObject("pt"), evVar, false);
            hj<PointF, PointF> a3 = hb.a(jSONObject.optJSONObject(SuperId.BIT_1_MAIN_VOICE_ASSISTANT), evVar);
            gy a4 = defpackage.gy.a.a(jSONObject.optJSONObject(UploadQueueMgr.MSGTYPE_REALTIME), evVar, false);
            gy a5 = defpackage.gy.a.a(jSONObject.optJSONObject("or"), evVar, true);
            gy a6 = defpackage.gy.a.a(jSONObject.optJSONObject("os"), evVar, false);
            gy gyVar2 = null;
            if (a == Type.Star) {
                gy a7 = defpackage.gy.a.a(jSONObject.optJSONObject("ir"), evVar, true);
                gyVar2 = defpackage.gy.a.a(jSONObject.optJSONObject("is"), evVar, false);
                gyVar = a7;
            } else {
                gyVar = null;
            }
            PolystarShape polystarShape = new PolystarShape(optString, a, a2, a3, a4, gyVar, a5, gyVar2, a6, 0);
            return polystarShape;
        }
    }

    /* synthetic */ PolystarShape(String str, Type type, gy gyVar, hj hjVar, gy gyVar2, gy gyVar3, gy gyVar4, gy gyVar5, gy gyVar6, byte b2) {
        this(str, type, gyVar, hjVar, gyVar2, gyVar3, gyVar4, gyVar5, gyVar6);
    }

    private PolystarShape(String str, Type type, gy gyVar, hj<PointF, PointF> hjVar, gy gyVar2, gy gyVar3, gy gyVar4, gy gyVar5, gy gyVar6) {
        this.a = str;
        this.b = type;
        this.c = gyVar;
        this.d = hjVar;
        this.e = gyVar2;
        this.f = gyVar3;
        this.g = gyVar4;
        this.h = gyVar5;
        this.i = gyVar6;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new fo(ewVar, hxVar, this);
    }
}
