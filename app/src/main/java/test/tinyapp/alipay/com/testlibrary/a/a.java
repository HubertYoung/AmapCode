package test.tinyapp.alipay.com.testlibrary.a;

import java.util.Collection;
import java.util.Map;

/* compiled from: CollectionsUtil */
public final class a {
    public static <T> boolean a(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T extends Collection> boolean a(T collection) {
        return collection == null || collection.size() == 0;
    }

    public static <T extends Map> boolean a(T map) {
        return map == null || map.size() <= 0;
    }
}
