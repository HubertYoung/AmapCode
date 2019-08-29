package com.ali.user.mobile.log;

import android.text.TextUtils;

public class LogSb {
    private StringBuffer a = new StringBuffer();

    public final void a(String str) {
        if (this.a != null && !TextUtils.isEmpty(str)) {
            StringBuffer stringBuffer = this.a;
            stringBuffer.append(str);
            stringBuffer.append("###");
        }
    }

    public String toString() {
        return this.a != null ? this.a.toString() : "";
    }
}
