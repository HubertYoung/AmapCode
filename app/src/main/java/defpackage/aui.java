package defpackage;

import android.text.TextUtils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"EI_EXPOSE_REP"})
/* renamed from: aui reason: default package */
/* compiled from: FilterTagInfo */
public final class aui {
    public final a a;
    public final String b;
    public final String c;
    public final a[] d;

    /* renamed from: aui$a */
    /* compiled from: FilterTagInfo */
    public static class a {
        public final String a;
        public final String b;

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public final int hashCode() {
            return this.b == null ? super.hashCode() : this.b.hashCode();
        }

        public final boolean equals(Object obj) {
            if (obj != null && (obj instanceof a)) {
                return TextUtils.equals(((a) obj).b, this.b);
            }
            return false;
        }
    }

    public aui(String str, String str2, String str3, a[] aVarArr) {
        this.b = str2;
        this.c = str3;
        this.d = aVarArr;
        this.a = a(aVarArr, str);
    }

    private static a a(a[] aVarArr, String str) {
        if (aVarArr != null && aVarArr.length > 0) {
            for (a aVar : aVarArr) {
                if (TextUtils.equals(aVar.b, str)) {
                    return aVar;
                }
            }
        }
        return null;
    }
}
