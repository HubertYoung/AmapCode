package defpackage;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* renamed from: ede reason: default package */
/* compiled from: RequestParamBuilder */
public final class ede {
    public String a;
    public String b;
    public List<String> c = new ArrayList();
    public String d;
    public HashMap<String, String> e = new HashMap<>();
    public boolean f = false;

    public ede(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public final ede a(String str, String str2) {
        if (TextUtils.isEmpty(str) || str2 == null) {
            return this;
        }
        this.e.put(str, str2);
        this.f = false;
        return this;
    }

    public final ede b(String str, String str2) {
        a(str, str2);
        if (!TextUtils.isEmpty(str2)) {
            this.c.add(str);
        }
        return this;
    }
}
