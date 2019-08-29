package defpackage;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.j;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;

@SuppressFBWarnings({"WMI_WRONG_MAP_ITERATOR"})
/* renamed from: fhh reason: default package */
/* compiled from: PayResult */
public final class fhh {
    public String a;
    public String b;
    public String c;

    public fhh(Map<String, String> map) {
        if (map != null) {
            for (String next : map.keySet()) {
                if (TextUtils.equals(next, j.a)) {
                    this.a = map.get(next);
                } else if (TextUtils.equals(next, "result")) {
                    this.b = map.get(next);
                } else if (TextUtils.equals(next, "memo")) {
                    this.c = map.get(next);
                }
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("resultStatus={");
        sb.append(this.a);
        sb.append("};memo={");
        sb.append(this.c);
        sb.append("};result={");
        sb.append(this.b);
        sb.append(h.d);
        return sb.toString();
    }
}
