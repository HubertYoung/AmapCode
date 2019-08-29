package com.alipay.android.phone.inside.log.field;

import android.content.Context;
import com.alipay.android.phone.inside.log.biz.ContextManager;
import com.alipay.android.phone.inside.log.util.DeviceUtil;

public class DeviceField extends AbstractLogField {
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;

    public DeviceField() {
        Context context = ContextManager.a().getContext();
        if (context != null) {
            DeviceUtil.a(context);
        }
    }

    public final String a() {
        this.b = DeviceUtil.a();
        this.c = DeviceUtil.b();
        this.d = DeviceUtil.c();
        this.e = DeviceUtil.d();
        this.f = DeviceUtil.e();
        this.g = DeviceUtil.i();
        this.h = DeviceUtil.h();
        this.i = DeviceUtil.f();
        this.j = DeviceUtil.g();
        this.k = DeviceUtil.j();
        return a(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, "-", "-");
    }
}
