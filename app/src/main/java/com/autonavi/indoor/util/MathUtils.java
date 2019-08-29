package com.autonavi.indoor.util;

import java.util.ArrayList;
import java.util.Iterator;

public class MathUtils {
    public static int avg(ArrayList<Integer> arrayList) {
        int i = 0;
        if (arrayList.size() == 0) {
            return 0;
        }
        Iterator<Integer> it = arrayList.iterator();
        while (it.hasNext()) {
            i += it.next().intValue();
        }
        return i / arrayList.size();
    }
}
