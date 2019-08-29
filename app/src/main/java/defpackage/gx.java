package defpackage;

import java.util.List;
import org.json.JSONObject;

/* renamed from: gx reason: default package */
/* compiled from: AnimatableColorValue */
public final class gx extends hl<Integer, Integer> {

    /* renamed from: gx$a */
    /* compiled from: AnimatableColorValue */
    public static final class a {
        public static gx a(JSONObject jSONObject, ev evVar) {
            a a = hk.a(jSONObject, 1.0f, evVar, gm.a).a();
            return new gx(a.a, (Integer) a.b, 0);
        }
    }

    /* synthetic */ gx(List list, Integer num, byte b) {
        this(list, num);
    }

    private gx(List<fc<Integer>> list, Integer num) {
        super(list, num);
    }

    public final fu<Integer, Integer> a() {
        if (!d()) {
            return new gh(this.b);
        }
        return new fv(this.a);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AnimatableColorValue{initialValue=");
        sb.append(this.b);
        sb.append('}');
        return sb.toString();
    }
}
