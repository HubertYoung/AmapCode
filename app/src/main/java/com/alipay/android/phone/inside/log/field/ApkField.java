package com.alipay.android.phone.inside.log.field;

import com.alipay.android.phone.inside.log.biz.ContextManager;
import com.alipay.android.phone.inside.log.util.ApkUtil;

public class ApkField extends AbstractLogField {
    private String b;
    private String c;
    private String d;
    private String e;

    public ApkField() {
        ApkUtil.a(ContextManager.a().getContext());
    }

    public final String a() {
        this.b = ApkUtil.a();
        this.c = ApkUtil.b();
        this.d = ApkUtil.c();
        this.e = ApkUtil.d();
        return a(this.b, this.c, this.d, this.e, "-", "-");
    }
}
