package com.alipay.android.phone.inside.log.field;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.util.DateUtil;
import com.alipay.android.phone.inside.log.util.storage.PrefUtils;

public class HeaderField extends AbstractLogField {
    private static int b = 0;
    private static String g = "-";
    private String c;
    private String d = DateUtil.a();
    private String e;
    private String f = "{{SUBMIT_TIME}}";

    public HeaderField() {
        int a = PrefUtils.a((String) "sp_inside_log", (String) "log_id", 0) + 1;
        PrefUtils.b((String) "sp_inside_log", (String) "log_id", a);
        LoggerFactory.f().b((String) "inside", "HeaderField::getLogId > ".concat(String.valueOf(a)));
        this.c = Integer.toString(a);
    }

    public final void a(String str) {
        this.d = str;
    }

    public final String a() {
        this.e = DateUtil.a();
        return a(this.c, this.d, this.e, this.f, g, "-", "-");
    }
}
