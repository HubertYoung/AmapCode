package defpackage;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.List;
import org.json.JSONObject;

/* renamed from: gy reason: default package */
/* compiled from: AnimatableFloatValue */
public final class gy extends hl<Float, Float> {

    /* renamed from: gy$a */
    /* compiled from: AnimatableFloatValue */
    public static final class a {
        static gy a() {
            return new gy(0);
        }

        public static gy a(JSONObject jSONObject, ev evVar, boolean z) {
            float f;
            if (z) {
                f = evVar.k;
            } else {
                f = 1.0f;
            }
            if (jSONObject != null && jSONObject.has(DictionaryKeys.CTRLXY_X)) {
                evVar.a((String) "Lottie doesn't support expressions.");
            }
            a a = hk.a(jSONObject, f, evVar, b.a).a();
            return new gy(a.a, (Float) a.b, 0);
        }
    }

    /* renamed from: gy$b */
    /* compiled from: AnimatableFloatValue */
    static class b implements defpackage.hj.a<Float> {
        static final b a = new b();

        private b() {
        }

        public final /* synthetic */ Object a(Object obj, float f) {
            return Float.valueOf(JsonUtils.a(obj) * f);
        }
    }

    /* synthetic */ gy(byte b2) {
        this();
    }

    /* synthetic */ gy(List list, Float f, byte b2) {
        this(list, f);
    }

    private gy() {
        super(Float.valueOf(0.0f));
    }

    private gy(List<fc<Float>> list, Float f) {
        super(list, f);
    }

    public final fu<Float, Float> a() {
        if (!d()) {
            return new gh(this.b);
        }
        return new fw(this.a);
    }

    public final /* bridge */ /* synthetic */ Object b() {
        return (Float) this.b;
    }
}
