package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: ezb reason: default package */
/* compiled from: OnNotificationClickTask */
public final class ezb extends eya {
    public ezb(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        exp exp = (exp) fbh;
        ezp ezp = exp.c;
        if (ezp == null) {
            fat.d("OnNotificationClickTask", "current notification item is null");
            return;
        }
        ezq a = fau.a(ezp);
        boolean equals = this.b.getPackageName().equals(exp.a);
        if (equals) {
            fae.a(this.b);
        }
        if (equals) {
            exx exx = new exx(1030);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("type", "2");
            hashMap.put("messageID", String.valueOf(exp.b));
            hashMap.put("platform", this.b.getPackageName());
            String b = fbd.b(this.b, this.b.getPackageName());
            if (!TextUtils.isEmpty(b)) {
                hashMap.put("remoteAppId", b);
            }
            exx.a = hashMap;
            ezv.a().a((fbh) exx);
            StringBuilder sb = new StringBuilder("notification is clicked by skip type[");
            sb.append(a.o);
            sb.append("]");
            fat.d("OnNotificationClickTask", sb.toString());
            boolean z = true;
            switch (a.o) {
                case 1:
                    new Thread(new ezg(this, this.b, a.r)).start();
                    fbf.b(new ezc(this, a));
                    return;
                case 2:
                    String str = a.n;
                    if (!str.startsWith(AjxHttpLoader.DOMAIN_HTTP) && !str.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
                        z = false;
                    }
                    if (z) {
                        Uri parse = Uri.parse(str);
                        Intent intent = new Intent("android.intent.action.VIEW", parse);
                        intent.setFlags(268435456);
                        b(intent, a.r);
                        try {
                            this.b.startActivity(intent);
                        } catch (Exception unused) {
                            fat.a((String) "OnNotificationClickTask", "startActivity error : ".concat(String.valueOf(parse)));
                        }
                    } else {
                        fat.a((String) "OnNotificationClickTask", (String) "url not legal");
                    }
                    fbf.b(new ezd(this, a));
                    return;
                case 3:
                    fbf.b(new eze(this, a));
                    return;
                case 4:
                    String str2 = a.n;
                    try {
                        Intent parseUri = Intent.parseUri(str2, 1);
                        String str3 = parseUri.getPackage();
                        if (TextUtils.isEmpty(str3) || this.b.getPackageName().equals(str3)) {
                            String packageName = parseUri.getComponent() == null ? null : parseUri.getComponent().getPackageName();
                            if (TextUtils.isEmpty(packageName) || this.b.getPackageName().equals(packageName)) {
                                parseUri.setPackage(this.b.getPackageName());
                                parseUri.addFlags(268435456);
                                b(parseUri, a.r);
                                this.b.startActivity(parseUri);
                                fbf.b(new ezf(this, a));
                                return;
                            }
                            StringBuilder sb2 = new StringBuilder("open activity component error : local pkgName is ");
                            sb2.append(this.b.getPackageName());
                            sb2.append("; but remote pkgName is ");
                            sb2.append(parseUri.getPackage());
                            fat.a((String) "OnNotificationClickTask", sb2.toString());
                            return;
                        }
                        StringBuilder sb3 = new StringBuilder("open activity error : local pkgName is ");
                        sb3.append(this.b.getPackageName());
                        sb3.append("; but remote pkgName is ");
                        sb3.append(parseUri.getPackage());
                        fat.a((String) "OnNotificationClickTask", sb3.toString());
                        return;
                    } catch (Exception e) {
                        fat.a("OnNotificationClickTask", "open activity error : ".concat(String.valueOf(str2)), e);
                    }
                    break;
                default:
                    StringBuilder sb4 = new StringBuilder("illegitmacy skip type error : ");
                    sb4.append(a.o);
                    fat.a((String) "OnNotificationClickTask", sb4.toString());
                    return;
            }
        } else {
            StringBuilder sb5 = new StringBuilder("notify is ");
            sb5.append(a);
            sb5.append(" ; isMatch is ");
            sb5.append(equals);
            fat.a((String) "OnNotificationClickTask", sb5.toString());
        }
    }

    /* access modifiers changed from: private */
    public static Intent b(Intent intent, Map<String, String> map) {
        if (map == null || map.entrySet() == null) {
            return intent;
        }
        for (Entry next : map.entrySet()) {
            if (!(next == null || next.getKey() == null)) {
                intent.putExtra((String) next.getKey(), (String) next.getValue());
            }
        }
        return intent;
    }
}
