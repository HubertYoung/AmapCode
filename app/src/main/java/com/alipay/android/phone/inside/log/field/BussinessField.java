package com.alipay.android.phone.inside.log.field;

import com.alipay.android.phone.inside.log.biz.ContextManager;

public class BussinessField extends AbstractLogField {
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
    private String l;
    private String m;

    public final String a() {
        this.b = ContextManager.a().getInfo("log_inner_ver");
        this.c = ContextManager.a().getInfo("log_channel");
        this.d = ContextManager.a().getInfo("log_inside_mode");
        this.e = ContextManager.a().getInfo("log_product_id");
        this.f = ContextManager.a().getInfo("log_product_ver");
        this.g = ContextManager.a().getInfo("log_bussiness_id");
        this.h = ContextManager.a().getInfo("log_session_id");
        this.i = ContextManager.a().getInfo("log_user_id");
        this.j = ContextManager.a().getInfo("log_tid");
        this.k = ContextManager.a().getInfo("log_utdid");
        this.l = ContextManager.a().getInfo("log_biz_tid");
        this.m = ContextManager.a().getInfo("log_pid_token");
        return a(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, "-");
    }
}
