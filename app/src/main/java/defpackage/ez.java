package defpackage;

import android.support.v4.util.Pair;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: ez reason: default package */
/* compiled from: PerformanceTracker */
public final class ez {
    boolean a = false;
    private final List<Object> b = new ArrayList();
    private Map<String, ih> c = new HashMap();
    private final Comparator<Pair<String, Float>> d = new Comparator<Pair<String, Float>>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            float floatValue = ((Float) ((Pair) obj).second).floatValue();
            float floatValue2 = ((Float) ((Pair) obj2).second).floatValue();
            if (floatValue2 > floatValue) {
                return 1;
            }
            return floatValue > floatValue2 ? -1 : 0;
        }
    };

    public final void a(String str, float f) {
        if (this.a) {
            ih ihVar = this.c.get(str);
            if (ihVar == null) {
                ihVar = new ih();
                this.c.put(str, ihVar);
            }
            ihVar.a += f;
            ihVar.b++;
            if (ihVar.b == Integer.MAX_VALUE) {
                ihVar.a /= 2.0f;
                ihVar.b /= 2;
            }
            if (str.equals(DictionaryKeys.ENV_ROOT)) {
                Iterator<Object> it = this.b.iterator();
                while (it.hasNext()) {
                    it.next();
                }
            }
        }
    }
}
