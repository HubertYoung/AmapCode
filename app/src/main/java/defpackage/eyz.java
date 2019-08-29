package defpackage;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.HashMap;

/* renamed from: eyz reason: default package */
/* compiled from: OnNotificationArrivedReceiveTask */
public final class eyz extends eya {
    public eyz(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        boolean a = ezj.a(this.b).a();
        exq exq = (exq) fbh;
        if (!faw.a(this.b, this.b.getPackageName(), "com.vivo.pushservice.action.RECEIVE")) {
            exx exx = new exx(2101);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("messageID", String.valueOf(exq.c));
            String b = fbd.b(this.b, this.b.getPackageName());
            if (!TextUtils.isEmpty(b)) {
                hashMap.put("remoteAppId", b);
            }
            exx.a = hashMap;
            ezv.a().a((fbh) exx);
            return;
        }
        ezv.a().a((fbh) new exi(String.valueOf(exq.c)));
        StringBuilder sb = new StringBuilder("PushMessageReceiver ");
        sb.append(this.b.getPackageName());
        sb.append(" isEnablePush :");
        sb.append(a);
        fat.d("OnNotificationArrivedTask", sb.toString());
        if (!a) {
            exx exx2 = new exx(1020);
            HashMap<String, String> hashMap2 = new HashMap<>();
            hashMap2.put("messageID", String.valueOf(exq.c));
            String b2 = fbd.b(this.b, this.b.getPackageName());
            if (!TextUtils.isEmpty(b2)) {
                hashMap2.put("remoteAppId", b2);
            }
            exx2.a = hashMap2;
            ezv.a().a((fbh) exx2);
        } else if (!ezv.a().c || a(fbd.c(this.b), exq.b(), exq.b)) {
            if (VERSION.SDK_INT >= 24) {
                NotificationManager notificationManager = (NotificationManager) this.b.getSystemService("notification");
                if (notificationManager != null && !notificationManager.areNotificationsEnabled()) {
                    StringBuilder sb2 = new StringBuilder("pkg name : ");
                    sb2.append(this.b.getPackageName());
                    sb2.append(" notify switch is false");
                    fat.b((String) "OnNotificationArrivedTask", sb2.toString());
                    fat.b(this.b, (String) "通知开关关闭，导致通知无法展示，请到设置页打开应用通知开关");
                    exx exx3 = new exx(2104);
                    HashMap<String, String> hashMap3 = new HashMap<>();
                    hashMap3.put("messageID", String.valueOf(exq.c));
                    String b3 = fbd.b(this.b, this.b.getPackageName());
                    if (!TextUtils.isEmpty(b3)) {
                        hashMap3.put("remoteAppId", b3);
                    }
                    exx3.a = hashMap3;
                    ezv.a().a((fbh) exx3);
                    return;
                }
            }
            ezp n_ = exq.n_();
            if (n_ != null) {
                int i = n_.f;
                String str = n_.g;
                StringBuilder sb3 = new StringBuilder("tragetType is ");
                sb3.append(i);
                sb3.append(" ; target is ");
                sb3.append(str);
                fat.d("OnNotificationArrivedTask", sb3.toString());
                fbf.b(new eza(this, n_, exq));
                return;
            }
            fat.a((String) "OnNotificationArrivedTask", (String) "notify is null");
            Context context = this.b;
            StringBuilder sb4 = new StringBuilder("通知内容为空，");
            sb4.append(exq.c);
            fat.c(context, sb4.toString());
            faj.a(this.b, exq.c, 1027);
        } else {
            exx exx4 = new exx(1021);
            HashMap<String, String> hashMap4 = new HashMap<>();
            hashMap4.put("messageID", String.valueOf(exq.c));
            String b4 = fbd.b(this.b, this.b.getPackageName());
            if (!TextUtils.isEmpty(b4)) {
                hashMap4.put("remoteAppId", b4);
            }
            exx4.a = hashMap4;
            ezv.a().a((fbh) exx4);
        }
    }
}
