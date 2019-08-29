package com.alipay.mobile.antui.input.formatter;

import android.text.Editable;
import com.alipay.mobile.antui.input.AUFormatter;

public class AUMoneyFormatter implements AUFormatter {
    public void format(Editable s) {
        String temp = s.toString();
        int posDot = temp.indexOf(".");
        if (posDot != -1 && (temp.length() - posDot) - 1 > 2) {
            s.delete(posDot + 3, s.length());
        }
    }
}
