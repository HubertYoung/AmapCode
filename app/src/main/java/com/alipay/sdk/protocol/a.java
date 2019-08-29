package com.alipay.sdk.protocol;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.util.Constants;

public enum a {
    None(Constants.ANIMATOR_NONE),
    WapPay("js://wappay"),
    Update("js://update");
    
    private String d;

    private a(String str) {
        this.d = str;
    }

    public static a a(String str) {
        if (TextUtils.isEmpty(str)) {
            return None;
        }
        a aVar = None;
        a[] values = values();
        int length = values.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            a aVar2 = values[i];
            if (str.startsWith(aVar2.d)) {
                aVar = aVar2;
                break;
            }
            i++;
        }
        return aVar;
    }
}
