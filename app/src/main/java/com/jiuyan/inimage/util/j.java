package com.jiuyan.inimage.util;

import com.alibaba.wireless.security.SecExceptionCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* compiled from: NumberUtil */
public class j {
    static final int[] a = {9, 99, SecExceptionCode.SEC_ERROR_UMID_UNKNOWN_ERR, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};

    public static List<Integer> a(int i, int i2, int i3) {
        if (i2 < i || i3 > (i2 - i) + 1) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        while (i <= i2) {
            linkedList.add(Integer.valueOf(i));
            i++;
        }
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(linkedList.remove((int) (Math.random() * ((double) linkedList.size()))));
        }
        Collections.sort(arrayList);
        return arrayList;
    }
}
