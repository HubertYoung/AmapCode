package com.huawei.android.pushselfshow.c;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.b.a;
import com.huawei.android.pushselfshow.richpush.tools.b;

public class d extends Thread {
    private Context a;
    private a b;

    public d(Context context, a aVar) {
        this.a = context;
        this.b = aVar;
    }

    private static Intent b(Context context, a aVar) {
        if (aVar == null) {
            return null;
        }
        Intent a2 = com.huawei.android.pushselfshow.utils.a.a(context, aVar.A);
        if (aVar.g != null) {
            try {
                Intent parseUri = Intent.parseUri(aVar.g, 0);
                StringBuilder sb = new StringBuilder("Intent.parseUri(msg.intentUri, 0)，");
                sb.append(parseUri.toURI());
                c.a("PushSelfShowLog", sb.toString());
                if (com.huawei.android.pushselfshow.utils.a.a(context, aVar.A, parseUri).booleanValue()) {
                    return parseUri;
                }
            } catch (Exception e) {
                c.a((String) "PushSelfShowLog", (String) "intentUri error ", (Throwable) e);
                return a2;
            }
        } else {
            if (aVar.B != null) {
                Intent intent = new Intent(aVar.B);
                if (com.huawei.android.pushselfshow.utils.a.a(context, aVar.A, intent).booleanValue()) {
                    a2 = intent;
                }
            }
            a2.setPackage(aVar.A);
        }
        return a2;
    }

    public boolean a(Context context) {
        if ("cosa".equals(this.b.p)) {
            return b(context);
        }
        if (NotificationCompat.CATEGORY_EMAIL.equals(this.b.p)) {
            return c(context);
        }
        if ("rp".equals(this.b.p)) {
            return d(context);
        }
        return true;
    }

    public boolean a(Context context, a aVar) {
        if (!"app".equals(aVar.p) && !"cosa".equals(aVar.p)) {
            return false;
        }
        Intent b2 = b(context, aVar);
        if (b2 == null) {
            c.a("PushSelfShowLog", "launchCosaApp,intent == null");
            return true;
        } else if (com.huawei.android.pushselfshow.utils.a.a(context, b2)) {
            return false;
        } else {
            c.b("PushSelfShowLog", "no permission to start activity");
            return true;
        }
    }

    public boolean b(Context context) {
        if (com.huawei.android.pushselfshow.utils.a.b(context, this.b.A)) {
            return true;
        }
        com.huawei.android.pushselfshow.utils.a.a(context, (String) "4", this.b);
        return false;
    }

    public boolean c(Context context) {
        if (com.huawei.android.pushselfshow.utils.a.d(context)) {
            return true;
        }
        com.huawei.android.pushselfshow.utils.a.a(context, (String) "15", this.b);
        return false;
    }

    public boolean d(Context context) {
        if (this.b.D == null || this.b.D.length() == 0) {
            com.huawei.android.pushselfshow.utils.a.a(context, (String) "6", this.b);
            c.a("PushSelfShowLog", "ilegle richpush param ,rpl is null");
            return false;
        }
        StringBuilder sb = new StringBuilder("enter checkRichPush, rpl is ");
        sb.append(this.b.D);
        sb.append(",psMsg.rpct:");
        sb.append(this.b.F);
        c.a("PushSelfShowLog", sb.toString());
        if ("application/zip".equals(this.b.F) || this.b.D.endsWith(FilePathHelper.SUFFIX_DOT_ZIP)) {
            this.b.F = "application/zip";
            if (this.b.j == 1) {
                String a2 = new com.huawei.android.pushselfshow.richpush.tools.d().a(context, this.b.D, this.b.k, b.a("application/zip"));
                if (a2 != null && a2.length() > 0) {
                    this.b.D = a2;
                    this.b.F = "application/zip_local";
                }
                c.a("PushSelfShowLog", "Download first ,the localfile".concat(String.valueOf(a2)));
            }
            return true;
        } else if ("text/html".equals(this.b.F) || this.b.D.endsWith(".html")) {
            this.b.F = "text/html";
            return true;
        } else {
            c.a("PushSelfShowLog", "unknow rpl type");
            com.huawei.android.pushselfshow.utils.a.a(context, (String) "6", this.b);
            return false;
        }
    }

    public void run() {
        c.a("PushSelfShowLog", "enter run()");
        try {
            if (a(this.a)) {
                if (a(this.a, this.b)) {
                    com.huawei.android.pushselfshow.utils.a.a(this.a, (String) "17", this.b);
                    return;
                }
                b.a(this.a, this.b);
            }
        } catch (Exception unused) {
        }
        super.run();
    }
}
