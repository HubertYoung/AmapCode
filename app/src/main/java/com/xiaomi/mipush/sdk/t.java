package com.xiaomi.mipush.sdk;

import android.text.TextUtils;

class t {
    int a = 0;
    String b = "";

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof t)) {
            t tVar = (t) obj;
            if (!TextUtils.isEmpty(tVar.b) && tVar.b.equals(this.b)) {
                return true;
            }
        }
        return false;
    }
}
