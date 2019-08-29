package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ezo reason: default package */
/* compiled from: PushConfigSettings */
public final class ezo extends ezm<ezs> {
    /* access modifiers changed from: protected */
    public final String a() {
        return "com.vivo.pushservice.other";
    }

    public ezo(Context context) {
        super(context);
    }

    public final List<ezs> a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String trim : str.trim().split("@#")) {
            String trim2 = trim.trim();
            String[] split = trim2.trim().split(",");
            if (split.length >= 2) {
                try {
                    arrayList.add(new ezs(split[0], trim2.substring(split[0].length() + 1)));
                } catch (Exception e) {
                    fat.d("PushConfigSettings", "str2Clients E: ".concat(String.valueOf(e)));
                }
            }
        }
        return arrayList;
    }

    public final String c(String str) {
        synchronized (c) {
            try {
                for (ezs ezs : this.d) {
                    if (!TextUtils.isEmpty(ezs.a) && ezs.a.equals(str)) {
                        String str2 = ezs.b;
                        return str2;
                    }
                }
                return null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final String b(String str) throws Exception {
        return new String(fak.a(fak.a(a), fak.a(b), Base64.decode(str, 2)), "utf-8");
    }
}
