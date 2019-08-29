package com.alipay.android.phone.inside.log.field;

import com.alipay.android.phone.inside.log.util.DateUtil;
import com.alipay.android.phone.inside.log.util.LoggingUtil;

public class ExceptionField extends AbstractLogField {
    public String b;
    public String c;
    public String d;
    public String e;
    private String f;

    public ExceptionField(String str, String str2) {
        this.b = str;
        this.c = str2;
        this.d = "-";
        this.e = "-";
        this.f = DateUtil.a();
    }

    public ExceptionField(String str, String str2, String... strArr) {
        this(str, str2);
        this.e = b(strArr);
    }

    public ExceptionField(String str, String str2, Throwable th) {
        this(str, str2);
        this.d = LoggingUtil.a(th);
    }

    public ExceptionField(String str, String str2, Throwable th, String... strArr) {
        this(str, str2, th);
        this.e = b(strArr);
    }

    public final String a() {
        return a(this.b, this.c, this.d, this.e, this.f, "-");
    }
}
