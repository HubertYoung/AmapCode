package defpackage;

import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import java.util.ArrayList;

/* renamed from: cbz reason: default package */
/* compiled from: CitySuggestionUtils */
public final class cbz {
    public static ArrayList<CitySuggestion> a(int i, ArrayList<CitySuggestion> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        int size = arrayList.size();
        if (i <= 0 || i > a(arrayList)) {
            return null;
        }
        int i2 = (i - 1) * 10;
        if (i2 >= size) {
            return null;
        }
        int i3 = (i2 + 10) - 1;
        int i4 = size - 1;
        if (i3 > i4) {
            i3 = i4;
        }
        int i5 = (i3 - i2) + 1;
        ArrayList<CitySuggestion> arrayList2 = new ArrayList<>();
        for (int i6 = 0; i6 < i5; i6++) {
            arrayList2.add(arrayList.get(i2 + i6));
        }
        return arrayList2;
    }

    public static int a(ArrayList<CitySuggestion> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return 0;
        }
        int size = arrayList.size() / 10;
        if (arrayList.size() % 10 != 0) {
            size++;
        }
        return size;
    }
}
