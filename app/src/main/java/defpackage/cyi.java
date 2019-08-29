package defpackage;

import android.support.annotation.NonNull;
import java.util.List;

/* renamed from: cyi reason: default package */
/* compiled from: ArrayUtils */
public final class cyi {
    @NonNull
    public static String[] a(@NonNull List<String> list) {
        int size = list.size();
        if (size <= 0) {
            return new String[0];
        }
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = list.get(i);
        }
        return strArr;
    }
}
