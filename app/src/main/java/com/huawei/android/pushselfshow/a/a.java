package com.huawei.android.pushselfshow.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.huawei.android.pushagent.a.a.c;
import com.uc.webview.export.WebView;

public class a {
    private static final String[] a = {"phone", "url", NotificationCompat.CATEGORY_EMAIL, "app", "cosa", "rp"};
    private Context b;
    private com.huawei.android.pushselfshow.b.a c;

    public a(Context context, com.huawei.android.pushselfshow.b.a aVar) {
        this.b = context;
        this.c = aVar;
    }

    public static boolean a(String str) {
        for (String equals : a) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void b() {
        com.huawei.android.pushselfshow.b.a aVar;
        String sb;
        c.a("PushSelfShowLog", "enter launchUrl");
        try {
            if (!(this.c.H == 0 || this.c.I == null || this.c.I.length() <= 0)) {
                if (this.c.C.indexOf("?") != -1) {
                    aVar = this.c;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.c.C);
                    sb2.append("&");
                    sb2.append(this.c.I);
                    sb2.append("=");
                    sb2.append(com.huawei.android.pushselfshow.utils.a.a(com.huawei.android.pushselfshow.utils.a.b(this.b)));
                    sb = sb2.toString();
                } else {
                    aVar = this.c;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.c.C);
                    sb3.append("?");
                    sb3.append(this.c.I);
                    sb3.append("=");
                    sb3.append(com.huawei.android.pushselfshow.utils.a.a(com.huawei.android.pushselfshow.utils.a.b(this.b)));
                    sb = sb3.toString();
                }
                aVar.C = sb;
            }
            StringBuilder sb4 = new StringBuilder("url =");
            sb4.append(this.c.C);
            c.a("PushSelfShowLog", sb4.toString());
            if (this.c.G == 0) {
                String str = this.c.C;
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW").setFlags(268435456).setData(Uri.parse(str));
                this.b.startActivity(intent);
                return;
            }
            this.c.D = this.c.C;
            this.c.F = "text/html";
            this.c.E = PoiLayoutTemplate.HTML;
            g();
        } catch (Exception e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private void c() {
        c.a("PushSelfShowLog", "enter launchCall");
        try {
            Intent intent = new Intent();
            Intent action = intent.setAction("android.intent.action.DIAL");
            StringBuilder sb = new StringBuilder(WebView.SCHEME_TEL);
            sb.append(this.c.w);
            action.setData(Uri.parse(sb.toString())).setFlags(268435456);
            this.b.startActivity(intent);
        } catch (Exception e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private void d() {
        c.a("PushSelfShowLog", "enter launchMail");
        try {
            if (this.c.x != null) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SENDTO").setFlags(268435456).setData(Uri.fromParts("mailto", this.c.x, null)).putExtra("android.intent.extra.SUBJECT", this.c.y).putExtra("android.intent.extra.TEXT", this.c.z).setPackage("com.android.email");
                this.b.startActivity(intent);
            }
        } catch (Exception e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private void e() {
        try {
            StringBuilder sb = new StringBuilder("enter launchApp, appPackageName =");
            sb.append(this.c.A);
            c.a("PushSelfShowLog", sb.toString());
            if (com.huawei.android.pushselfshow.utils.a.b(this.b, this.c.A)) {
                f();
                return;
            }
            try {
                StringBuilder sb2 = new StringBuilder("insert into db message.getMsgId() is ");
                sb2.append(this.c.a());
                sb2.append(",message.appPackageName is ");
                sb2.append(this.c.A);
                c.e("PushSelfShowLog", sb2.toString());
                com.huawei.android.pushselfshow.utils.a.a.a(this.b, this.c.a(), this.c.A);
            } catch (Exception e) {
                c.d("PushSelfShowLog", "launchApp not exist ,insertAppinfo error", e);
            }
            Intent intent = null;
            if (com.huawei.android.pushselfshow.utils.a.a(this.b, (String) "com.huawei.appmarket", new Intent("com.huawei.appmarket.intent.action.AppDetail")).booleanValue()) {
                c.a("PushSelfShowLog", "app not exist && appmarkt exist ,so open appmarket");
                intent = new Intent("com.huawei.appmarket.intent.action.AppDetail");
                intent.putExtra("APP_PACKAGENAME", this.c.A);
                intent.setPackage("com.huawei.appmarket");
                intent.setFlags(402653184);
                c.a("PushSelfShowLog", "hwAppmarket only support com.huawei.appmarket.intent.action.AppDetail!");
                com.huawei.android.pushselfshow.utils.a.a(this.b, (String) "7", this.c);
            }
            if (intent != null) {
                StringBuilder sb3 = new StringBuilder("intent is not null ");
                sb3.append(intent.toURI());
                c.a("PushSelfShowLog", sb3.toString());
                this.b.startActivity(intent);
                return;
            }
            c.a("PushSelfShowLog", "intent is null ");
        } catch (Exception e2) {
            StringBuilder sb4 = new StringBuilder("launchApp error:");
            sb4.append(e2.toString());
            c.d("PushSelfShowLog", sb4.toString());
        }
    }

    private void f() {
        c.e("PushSelfShowLog", "run into launchCosaApp ");
        try {
            StringBuilder sb = new StringBuilder("enter launchExistApp cosa, appPackageName =");
            sb.append(this.c.A);
            sb.append(",and msg.intentUri is ");
            sb.append(this.c.g);
            c.a("PushSelfShowLog", sb.toString());
            Intent a2 = com.huawei.android.pushselfshow.utils.a.a(this.b, this.c.A);
            if (this.c.g != null) {
                try {
                    Intent parseUri = Intent.parseUri(this.c.g, 0);
                    StringBuilder sb2 = new StringBuilder("Intent.parseUri(msg.intentUri, 0)，");
                    sb2.append(parseUri.toURI());
                    c.a("PushSelfShowLog", sb2.toString());
                    if (com.huawei.android.pushselfshow.utils.a.a(this.b, this.c.A, parseUri).booleanValue()) {
                        a2 = parseUri;
                    }
                } catch (Exception e) {
                    c.a((String) "PushSelfShowLog", (String) "intentUri error ", (Throwable) e);
                }
            } else {
                if (this.c.B != null) {
                    Intent intent = new Intent(this.c.B);
                    if (com.huawei.android.pushselfshow.utils.a.a(this.b, this.c.A, intent).booleanValue()) {
                        a2 = intent;
                    }
                }
                a2.setPackage(this.c.A);
            }
            if (a2 == null) {
                c.a("PushSelfShowLog", "launchCosaApp,intent == null");
            } else if (!com.huawei.android.pushselfshow.utils.a.a(this.b, a2)) {
                c.c("PushSelfShowLog", "no permission to start Activity");
            } else {
                a2.setFlags(805437440);
                StringBuilder sb3 = new StringBuilder("start ");
                sb3.append(a2.toURI());
                c.a("PushSelfShowLog", sb3.toString());
                this.b.startActivity(a2);
            }
        } catch (Exception e2) {
            c.c("PushSelfShowLog", e2.toString(), e2);
        }
    }

    private void g() {
        try {
            c.e("PushSelfShowLog", "run into launchRichPush ");
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this.b.getPackageName(), "com.huawei.android.pushselfshow.richpush.RichPushActivity"));
            intent.putExtra("type", this.c.E);
            intent.putExtra("selfshow_info", this.c.c());
            intent.putExtra("selfshow_token", this.c.d());
            intent.setFlags(268468240);
            intent.setPackage(this.b.getPackageName());
            this.b.startActivity(intent);
        } catch (Exception e) {
            c.c("PushSelfShowLog", "launchRichPush failed", e);
        }
    }

    public void a() {
        String str;
        String str2;
        c.a("PushSelfShowLog", "enter launchNotify()");
        if (this.b == null || this.c == null) {
            str = "PushSelfShowLog";
            str2 = "launchNotify  context or msg is null";
        } else if ("app".equals(this.c.p)) {
            e();
            return;
        } else if ("cosa".equals(this.c.p)) {
            f();
            return;
        } else if (NotificationCompat.CATEGORY_EMAIL.equals(this.c.p)) {
            d();
            return;
        } else if ("phone".equals(this.c.p)) {
            c();
            return;
        } else if ("rp".equals(this.c.p)) {
            g();
            return;
        } else if ("url".equals(this.c.p)) {
            b();
            return;
        } else {
            str = "PushSelfShowLog";
            StringBuilder sb = new StringBuilder();
            sb.append(this.c.p);
            sb.append(" is not exist in hShowType");
            str2 = sb.toString();
        }
        c.a(str, str2);
    }
}
