package com.alipay.android.phone.inside.log.field;

import com.alipay.android.phone.inside.log.util.DateUtil;

public class PerfField extends AbstractLogField {
    public String b;
    public String c;
    public Float d;
    public String e;
    public String f;

    private PerfField(String str, String str2, Float f2) {
        this.b = str;
        this.c = str2;
        this.d = f2;
        this.e = "-";
        this.f = DateUtil.a();
    }

    private PerfField(String str, String str2, Float f2, String... strArr) {
        this(str, str2, f2);
        this.e = b(strArr);
    }

    public PerfField(String str, String str2, Long l) {
        this(str, str2, Float.valueOf((float) l.longValue()));
    }

    public PerfField(String str, String str2, Long l, String... strArr) {
        this(str, str2, Float.valueOf((float) l.longValue()), strArr);
    }

    public final String a() {
        return a(this.b, this.c, Float.toString(this.d.floatValue()), this.e, this.f, "-");
    }
}
