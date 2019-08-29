package defpackage;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import org.json.JSONArray;

/* renamed from: gw reason: default package */
/* compiled from: ScaleXY */
public final class gw {
    public final float a;
    public final float b;

    /* renamed from: gw$a */
    /* compiled from: ScaleXY */
    public static class a implements defpackage.hj.a<gw> {
        public static final a a = new a();

        private a() {
        }

        public final /* synthetic */ Object a(Object obj, float f) {
            JSONArray jSONArray = (JSONArray) obj;
            return new gw((((float) jSONArray.optDouble(0, 1.0d)) / 100.0f) * f, (((float) jSONArray.optDouble(1, 1.0d)) / 100.0f) * f);
        }
    }

    public gw(float f, float f2) {
        this.a = f;
        this.b = f2;
    }

    public gw() {
        this(1.0f, 1.0f);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(DictionaryKeys.CTRLXY_X);
        sb.append(this.b);
        return sb.toString();
    }
}
