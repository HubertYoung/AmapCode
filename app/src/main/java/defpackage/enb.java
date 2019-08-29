package defpackage;

import android.text.TextUtils;
import java.util.HashMap;

/* renamed from: enb reason: default package */
/* compiled from: UrlRewriteDelegateManager */
public final class enb implements enc {
    HashMap<String, ena> a = new HashMap<>();

    public final ena a(String str) {
        return this.a.get(str);
    }

    public final String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        for (ena next : this.a.values()) {
            if (next != null && next.a(str)) {
                return next.b(str);
            }
        }
        return str;
    }
}
