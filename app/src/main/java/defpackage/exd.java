package defpackage;

import android.text.TextUtils;

/* renamed from: exd reason: default package */
/* compiled from: BaseAppCommand */
public class exd extends fbh {
    private String a;
    private long b = -1;
    public String c = null;
    public int d;
    public String e;
    private int h = -1;

    public String toString() {
        return "BaseAppCommand";
    }

    public exd(String str) {
        super(2006);
        this.a = str;
    }

    public void a(ewx ewx) {
        ewx.a((String) "req_id", this.c);
        ewx.a((String) "package_name", this.a);
        ewx.a((String) "sdk_version", 280);
        ewx.a((String) "PUSH_APP_STATUS", this.h);
        if (!TextUtils.isEmpty(this.e)) {
            ewx.a((String) "BaseAppCommand.EXTRA__HYBRIDVERSION", this.e);
        }
    }

    public void b(ewx ewx) {
        this.c = ewx.a((String) "req_id");
        this.a = ewx.a((String) "package_name");
        this.b = ewx.b((String) "sdk_version", 0);
        this.h = ewx.b((String) "PUSH_APP_STATUS", 0);
        this.e = ewx.a((String) "BaseAppCommand.EXTRA__HYBRIDVERSION");
    }
}
