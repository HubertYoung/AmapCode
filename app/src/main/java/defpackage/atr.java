package defpackage;

import java.util.Comparator;
import java.util.Locale;

/* renamed from: atr reason: default package */
/* compiled from: AdCityComparator */
public final class atr implements Comparator<lj> {
    public final /* synthetic */ int compare(Object obj, Object obj2) {
        int i;
        lj ljVar = (lj) obj;
        lj ljVar2 = (lj) obj2;
        if (ljVar.c.length() > ljVar2.c.length()) {
            i = ljVar2.c.length();
        } else {
            i = ljVar.c.length();
        }
        for (int i2 = 0; i2 < i; i2++) {
            String lowerCase = ljVar.c.toLowerCase(Locale.getDefault());
            String lowerCase2 = ljVar2.c.toLowerCase(Locale.getDefault());
            int compareTo = String.valueOf(lowerCase.charAt(i2)).compareTo(String.valueOf(lowerCase2.charAt(i2)));
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }
}
