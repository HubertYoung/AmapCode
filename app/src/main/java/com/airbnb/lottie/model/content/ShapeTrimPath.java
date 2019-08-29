package com.airbnb.lottie.model.content;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.sdk.util.h;
import org.json.JSONObject;

public final class ShapeTrimPath implements hn {
    public final String a;
    public final Type b;
    public final gy c;
    public final gy d;
    public final gy e;

    public enum Type {
        Simultaneously,
        Individually;

        static Type a(int i) {
            switch (i) {
                case 1:
                    return Simultaneously;
                case 2:
                    return Individually;
                default:
                    throw new IllegalArgumentException("Unknown trim path type ".concat(String.valueOf(i)));
            }
        }
    }

    public static class a {
        public static ShapeTrimPath a(JSONObject jSONObject, ev evVar) {
            ShapeTrimPath shapeTrimPath = new ShapeTrimPath(jSONObject.optString(LogItem.MM_C18_K4_NM), Type.a(jSONObject.optInt("m", 1)), defpackage.gy.a.a(jSONObject.optJSONObject("s"), evVar, false), defpackage.gy.a.a(jSONObject.optJSONObject("e"), evVar, false), defpackage.gy.a.a(jSONObject.optJSONObject("o"), evVar, false), 0);
            return shapeTrimPath;
        }
    }

    /* synthetic */ ShapeTrimPath(String str, Type type, gy gyVar, gy gyVar2, gy gyVar3, byte b2) {
        this(str, type, gyVar, gyVar2, gyVar3);
    }

    private ShapeTrimPath(String str, Type type, gy gyVar, gy gyVar2, gy gyVar3) {
        this.a = str;
        this.b = type;
        this.c = gyVar;
        this.d = gyVar2;
        this.e = gyVar3;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new ft(hxVar, this);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Trim Path: {start: ");
        sb.append(this.c);
        sb.append(", end: ");
        sb.append(this.d);
        sb.append(", offset: ");
        sb.append(this.e);
        sb.append(h.d);
        return sb.toString();
    }
}
