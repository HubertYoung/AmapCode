package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ezk reason: default package */
/* compiled from: AppConfigSettings */
public final class ezk extends ezm<ezs> {
    /* access modifiers changed from: protected */
    public final String a() {
        return "com.vivo.pushservice.back_up";
    }

    public ezk(Context context) {
        super(context);
    }

    public final List<ezs> a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str)) {
            for (String trim : str.trim().split("@#")) {
                String trim2 = trim.trim();
                String[] split = trim2.trim().split(",");
                if (split.length >= 2) {
                    try {
                        arrayList.add(new ezs(split[0], trim2.substring(split[0].length() + 1)));
                    } catch (Exception e) {
                        fat.d("AppConfigSettings", "str2Clients E: ".concat(String.valueOf(e)));
                    }
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public final String b(String str) throws Exception {
        return new String(fak.a(fak.a(a), fak.a(b), Base64.decode(str, 2)), "utf-8");
    }

    public final ezs c(String str) {
        synchronized (c) {
            try {
                for (ezs ezs : this.d) {
                    if (!TextUtils.isEmpty(ezs.a) && ezs.a.equals(str)) {
                        return ezs;
                    }
                }
                return null;
            }
        }
    }

    public final int b() {
        ezs c = c("push_mode");
        if (c == null || TextUtils.isEmpty(c.b)) {
            return -1;
        }
        try {
            return Integer.parseInt(c.b);
        } catch (Exception unused) {
            return -1;
        }
    }

    public static boolean a(int i) {
        if (i != -1) {
            return (i & 1) != 0;
        }
        return fbd.b((String) "persist.sys.log.ctrl", (String) BQCCameraParam.VALUE_NO).equals("yes");
    }
}
