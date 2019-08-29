package com.alipay.mobile.antui.picker;

import java.util.Comparator;

/* compiled from: AUDatePicker */
final class d implements Comparator<Object> {
    final /* synthetic */ AUDatePicker a;

    d(AUDatePicker this$0) {
        this.a = this$0;
    }

    public final int compare(Object lhs, Object rhs) {
        String lhsStr = lhs.toString();
        String rhsStr = rhs.toString();
        if (lhsStr.startsWith("0")) {
            lhsStr = lhsStr.substring(1);
        }
        if (rhsStr.startsWith("0")) {
            rhsStr = rhsStr.substring(1);
        }
        return Integer.parseInt(lhsStr) - Integer.parseInt(rhsStr);
    }
}
