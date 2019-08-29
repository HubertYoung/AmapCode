package com.autonavi.minimap.offline.util;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;

public final class JsArraysUtils {
    private JsArraysUtils() {
    }

    public static int[] toIntArrays(ArrayList<Integer> arrayList) {
        if (arrayList == null) {
            return new int[0];
        }
        clearNull(arrayList);
        if (arrayList.isEmpty()) {
            return new int[0];
        }
        Integer[] numArr = new Integer[arrayList.size()];
        arrayList.toArray(numArr);
        return toIntArrays(numArr);
    }

    private static int[] toIntArrays(Integer[] numArr) {
        if (numArr == null || numArr.length == 0) {
            return new int[0];
        }
        int[] iArr = new int[numArr.length];
        for (int i = 0; i < numArr.length; i++) {
            Integer num = numArr[i];
            if (num != null) {
                iArr[i] = num.intValue();
            }
        }
        return iArr;
    }

    public static <T> void clearNull(@NonNull ArrayList<T> arrayList) {
        arrayList.removeAll(Collections.singleton(null));
    }
}
