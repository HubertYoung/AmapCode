package com.alipay.android.phone.inside.log.field;

import android.content.Context;
import com.alipay.android.phone.inside.log.biz.ContextManager;
import com.alipay.android.phone.inside.log.util.DeviceEnv;
import java.util.Map;

public class EnvField extends AbstractLogField {
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;

    public final String a() {
        Context context = ContextManager.a().getContext();
        this.b = DeviceEnv.a();
        this.c = DeviceEnv.a(context);
        Map<String, String> b2 = DeviceEnv.b(context);
        this.d = b2.get(DeviceEnv.a);
        this.e = b2.get(DeviceEnv.b);
        this.f = DeviceEnv.c(context);
        return a(this.b, this.c, this.d, this.e, this.f, "-", "-", "-");
    }
}
