package defpackage;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: aau reason: default package */
/* compiled from: HttpDnsUtils */
public final class aau {
    public static String a(String str) {
        ArrayList arrayList;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        List<bo> a = bu.a().a(str);
        if (a.isEmpty()) {
            arrayList = null;
        } else {
            ArrayList arrayList2 = new ArrayList(a.size());
            for (bo next : a) {
                if (next.c() != 1) {
                    arrayList2.add(new a(next));
                }
            }
            arrayList = arrayList2;
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            a aVar = (a) arrayList.get(0);
            if (aVar != null) {
                String a2 = aVar.a.a();
                if (!TextUtils.isEmpty(a2)) {
                    return a2;
                }
            }
        }
        return "";
    }
}
