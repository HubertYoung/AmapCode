package defpackage;

import java.util.ArrayList;
import java.util.List;

@Deprecated
/* renamed from: agj reason: default package */
/* compiled from: CollectionUtil */
public final class agj {
    public static <T> ArrayList<T> a(List<T> list) {
        if (list == null) {
            return null;
        }
        if (list instanceof ArrayList) {
            return (ArrayList) list;
        }
        return new ArrayList<>(list);
    }
}
