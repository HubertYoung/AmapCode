package defpackage;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.sdk.util.h;

/* renamed from: gu reason: default package */
/* compiled from: MutablePair */
public final class gu<T> {
    @Nullable
    public T a;
    @Nullable
    public T b;

    public final boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        if (!a(pair.first, this.a) || !a(pair.second, this.b)) {
            return false;
        }
        return true;
    }

    private static boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = this.a == null ? 0 : this.a.hashCode();
        if (this.b != null) {
            i = this.b.hashCode();
        }
        return hashCode ^ i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Pair{");
        sb.append(String.valueOf(this.a));
        sb.append(Token.SEPARATOR);
        sb.append(String.valueOf(this.b));
        sb.append(h.d);
        return sb.toString();
    }
}
