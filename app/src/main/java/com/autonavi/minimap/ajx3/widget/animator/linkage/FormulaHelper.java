package com.autonavi.minimap.ajx3.widget.animator.linkage;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.minimap.ajx3.muparser.Parser;
import com.autonavi.minimap.ajx3.muparser.ParserException;

class FormulaHelper {
    FormulaHelper() {
    }

    static float getResultFromFormula(String str, float f) {
        double d;
        try {
            d = Parser.eval(str, DictionaryKeys.CTRLXY_X, (double) f);
        } catch (ParserException e) {
            e.printStackTrace();
            d = 0.0d;
        }
        return (float) d;
    }

    static boolean checkExpression(String str) {
        if (bny.c.equals(str)) {
            return false;
        }
        boolean z = true;
        try {
            Parser.eval(str, DictionaryKeys.CTRLXY_X, 1.0d);
        } catch (Exception unused) {
            z = false;
        }
        return z;
    }
}
