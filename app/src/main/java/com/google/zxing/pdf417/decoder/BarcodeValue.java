package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

final class BarcodeValue {
    private final Map<Integer, Integer> values = new HashMap();

    BarcodeValue() {
    }

    /* access modifiers changed from: 0000 */
    public final void setValue(int i) {
        Integer num = this.values.get(Integer.valueOf(i));
        if (num == null) {
            num = Integer.valueOf(0);
        }
        this.values.put(Integer.valueOf(i), Integer.valueOf(num.intValue() + 1));
    }

    /* access modifiers changed from: 0000 */
    public final int[] getValue() {
        ArrayList arrayList = new ArrayList();
        int i = -1;
        for (Entry next : this.values.entrySet()) {
            if (((Integer) next.getValue()).intValue() > i) {
                i = ((Integer) next.getValue()).intValue();
                arrayList.clear();
                arrayList.add((Integer) next.getKey());
            } else if (((Integer) next.getValue()).intValue() == i) {
                arrayList.add((Integer) next.getKey());
            }
        }
        return PDF417Common.toIntArray(arrayList);
    }

    public final Integer getConfidence(int i) {
        return this.values.get(Integer.valueOf(i));
    }
}
