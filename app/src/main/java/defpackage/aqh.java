package defpackage;

import java.util.HashMap;

/* renamed from: aqh reason: default package */
/* compiled from: MainMapServiceFactory */
public final class aqh {
    private static HashMap<Class, Integer> a;

    static {
        HashMap<Class, Integer> hashMap = new HashMap<>();
        a = hashMap;
        hashMap.put(czc.class, Integer.valueOf(1));
        a.put(czl.class, Integer.valueOf(2));
        a.put(czd.class, Integer.valueOf(4));
        a.put(czh.class, Integer.valueOf(6));
        a.put(czg.class, Integer.valueOf(7));
        a.put(cze.class, Integer.valueOf(8));
        a.put(czj.class, Integer.valueOf(9));
        a.put(czf.class, Integer.valueOf(11));
        a.put(czk.class, Integer.valueOf(13));
        a.put(czn.class, Integer.valueOf(14));
        a.put(czm.class, Integer.valueOf(16));
    }

    public static <T extends czi> T a(Class<T> cls, aqf aqf) {
        if (aqf == null || cls == null) {
            return null;
        }
        switch (a.containsKey(cls) ? a.get(cls).intValue() : -1) {
            case 1:
                return new apz();
            case 2:
                return new aql();
            case 4:
                return new aqa();
            case 6:
                return new aqe(aqf.f);
            case 7:
                return new aqd();
            case 8:
                return new aqb();
            case 9:
                return new aqj();
            case 11:
                return new aqc();
            case 13:
                return new aqk();
            case 14:
                return new arw();
            case 16:
                return new aqm();
            default:
                return null;
        }
    }
}
