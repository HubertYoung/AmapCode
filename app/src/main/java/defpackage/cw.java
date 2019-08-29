package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: cw reason: default package */
/* compiled from: RequestPriorityTable */
public final class cw {
    private static Map<String, Integer> a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("tpatch", Integer.valueOf(3));
        a.put(H5Param.SHOW_OPTION_MENU, Integer.valueOf(3));
        a.put("json", Integer.valueOf(3));
        a.put(PoiLayoutTemplate.HTML, Integer.valueOf(4));
        a.put("htm", Integer.valueOf(4));
        a.put("css", Integer.valueOf(5));
        a.put("js", Integer.valueOf(5));
        a.put("webp", Integer.valueOf(6));
        a.put("png", Integer.valueOf(6));
        a.put("jpg", Integer.valueOf(6));
        a.put("do", Integer.valueOf(6));
        a.put("zip", Integer.valueOf(c.c));
        a.put("bin", Integer.valueOf(c.c));
        a.put("apk", Integer.valueOf(c.c));
    }

    public static int a(ay ayVar) {
        if (ayVar == null) {
            throw new NullPointerException("url is null!");
        } else if (Collections.unmodifiableMap(ayVar.c).containsKey("x-pv")) {
            return 1;
        } else {
            String a2 = cq.a(ayVar.a.c);
            if (a2 == null) {
                return 6;
            }
            Integer num = a.get(a2);
            if (num != null) {
                return num.intValue();
            }
            return 6;
        }
    }
}
