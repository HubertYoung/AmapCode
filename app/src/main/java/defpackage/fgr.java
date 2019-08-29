package defpackage;

import android.support.annotation.NonNull;
import java.util.HashMap;

/* renamed from: fgr reason: default package */
/* compiled from: ISign */
public interface fgr {

    /* renamed from: fgr$a */
    /* compiled from: ISign */
    public static class a {
        public int a;
        public String b;

        public a(int i, String str) {
            this.a = i;
            this.b = str;
        }
    }

    String a(a aVar);

    String a(String str, String str2, int i);

    String a(String str, String str2, String str3, int i);

    String a(HashMap<String, String> hashMap, String str, String str2);

    void a(@NonNull ffd ffd);
}
