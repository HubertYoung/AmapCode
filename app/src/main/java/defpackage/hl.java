package defpackage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* renamed from: hl reason: default package */
/* compiled from: BaseAnimatableValue */
public abstract class hl<V, O> implements hj<V, O> {
    final List<fc<V>> a;
    public final V b;

    /* JADX WARNING: type inference failed for: r1v0, types: [V, O] */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public O a(V r1) {
        /*
            r0 = this;
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hl.a(java.lang.Object):java.lang.Object");
    }

    hl(V v) {
        this(Collections.emptyList(), v);
    }

    hl(List<fc<V>> list, V v) {
        this.a = list;
        this.b = v;
    }

    public final boolean d() {
        return !this.a.isEmpty();
    }

    public O b() {
        return a(this.b);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("parseInitialValue=");
        sb.append(this.b);
        if (!this.a.isEmpty()) {
            sb.append(", values=");
            sb.append(Arrays.toString(this.a.toArray()));
        }
        return sb.toString();
    }
}
