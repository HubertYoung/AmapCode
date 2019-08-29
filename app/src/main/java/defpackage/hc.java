package defpackage;

import android.graphics.PointF;
import java.util.List;
import org.json.JSONObject;

/* renamed from: hc reason: default package */
/* compiled from: AnimatablePointValue */
public final class hc extends hl<PointF, PointF> {

    /* renamed from: hc$a */
    /* compiled from: AnimatablePointValue */
    public static final class a {
        public static hc a(JSONObject jSONObject, ev evVar) {
            a a = hk.a(jSONObject, evVar.k, evVar, gv.a).a();
            return new hc(a.a, (PointF) a.b, 0);
        }
    }

    /* synthetic */ hc(List list, PointF pointF, byte b) {
        this(list, pointF);
    }

    private hc(List<fc<PointF>> list, PointF pointF) {
        super(list, pointF);
    }

    public final fu<PointF, PointF> a() {
        if (!d()) {
            return new gh(this.b);
        }
        return new gd(this.a);
    }
}
