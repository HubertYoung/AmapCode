package defpackage;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.List;
import org.json.JSONObject;

/* renamed from: ha reason: default package */
/* compiled from: AnimatableIntegerValue */
public final class ha extends hl<Integer, Integer> {

    /* renamed from: ha$a */
    /* compiled from: AnimatableIntegerValue */
    public static final class a {
        public static ha a(JSONObject jSONObject, ev evVar) {
            if (jSONObject != null && jSONObject.has(DictionaryKeys.CTRLXY_X)) {
                evVar.a((String) "Lottie doesn't support expressions.");
            }
            a a = hk.a(jSONObject, 1.0f, evVar, b.a).a();
            return new ha(a.a, (Integer) a.b);
        }
    }

    /* renamed from: ha$b */
    /* compiled from: AnimatableIntegerValue */
    static class b implements defpackage.hj.a<Integer> {
        /* access modifiers changed from: private */
        public static final b a = new b();

        private b() {
        }

        public final /* synthetic */ Object a(Object obj, float f) {
            return Integer.valueOf(Math.round(JsonUtils.a(obj) * f));
        }
    }

    /* synthetic */ ha(byte b2) {
        this();
    }

    private ha() {
        super(Integer.valueOf(100));
    }

    ha(List<fc<Integer>> list, Integer num) {
        super(list, num);
    }

    public final fu<Integer, Integer> a() {
        if (!d()) {
            return new gh(this.b);
        }
        return new fy(this.a);
    }

    public final /* bridge */ /* synthetic */ Object b() {
        return (Integer) this.b;
    }
}
