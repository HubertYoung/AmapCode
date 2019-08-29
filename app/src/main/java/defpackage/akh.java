package defpackage;

import android.content.Intent;
import java.util.HashMap;

/* renamed from: akh reason: default package */
/* compiled from: PageParams */
public final class akh {
    public int a;
    Intent b = null;
    public HashMap<String, Object> c;
    public Class<?> d;
    String e;

    public akh(HashMap<String, Object> hashMap) {
        this.c = hashMap;
    }

    public akh(HashMap<String, Object> hashMap, Class<?> cls, String str) {
        this.c = hashMap;
        this.d = cls;
        this.e = str;
    }
}
