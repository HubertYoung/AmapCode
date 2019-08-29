package defpackage;

import android.support.annotation.Nullable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: hk reason: default package */
/* compiled from: AnimatableValueParser */
public final class hk<T> {
    @Nullable
    private final JSONObject a;
    private final float b;
    private final ev c;
    private final defpackage.hj.a<T> d;

    /* renamed from: hk$a */
    /* compiled from: AnimatableValueParser */
    static class a<T> {
        final List<fc<T>> a;
        @Nullable
        final T b;

        a(List<fc<T>> list, @Nullable T t) {
            this.a = list;
            this.b = t;
        }
    }

    private hk(@Nullable JSONObject jSONObject, float f, ev evVar, defpackage.hj.a<T> aVar) {
        this.a = jSONObject;
        this.b = f;
        this.c = evVar;
        this.d = aVar;
    }

    static <T> hk<T> a(@Nullable JSONObject jSONObject, float f, ev evVar, defpackage.hj.a<T> aVar) {
        return new hk<>(jSONObject, f, evVar, aVar);
    }

    /* access modifiers changed from: 0000 */
    public final a<T> a() {
        List b2 = b();
        return new a<>(b2, a(b2));
    }

    private List<fc<T>> b() {
        if (this.a == null) {
            return Collections.emptyList();
        }
        Object opt = this.a.opt(SuperId.BIT_1_REALTIMEBUS_BUSSTATION);
        if (a(opt)) {
            return defpackage.fc.a.a((JSONArray) opt, this.c, this.b, this.d);
        }
        return Collections.emptyList();
    }

    @Nullable
    private T a(List<fc<T>> list) {
        if (this.a == null) {
            return null;
        }
        if (!list.isEmpty()) {
            return list.get(0).a;
        }
        return this.d.a(this.a.opt(SuperId.BIT_1_REALTIMEBUS_BUSSTATION), this.b);
    }

    private static boolean a(Object obj) {
        if (!(obj instanceof JSONArray)) {
            return false;
        }
        Object opt = ((JSONArray) obj).opt(0);
        if (!(opt instanceof JSONObject) || !((JSONObject) opt).has(LogItem.MM_C15_K4_TIME)) {
            return false;
        }
        return true;
    }
}
