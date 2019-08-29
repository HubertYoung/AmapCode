package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.network.HostManager;
import com.xiaomi.push.service.module.PushChannelRegion;
import com.xiaomi.xmpush.thrift.af;
import com.xiaomi.xmpush.thrift.aj;
import com.xiaomi.xmpush.thrift.au;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class XMPushService extends Service implements com.xiaomi.smack.d {
    public static int c = 1;
    /* access modifiers changed from: private */
    public static final int i = Process.myPid();
    Messenger a = null;
    final BroadcastReceiver b = new br(this);
    /* access modifiers changed from: private */
    public com.xiaomi.smack.b d;
    private ay e;
    /* access modifiers changed from: private */
    public String f;
    private e g;
    private long h = 0;
    private com.xiaomi.slim.f j;
    /* access modifiers changed from: private */
    public com.xiaomi.smack.a k;
    private e l;
    /* access modifiers changed from: private */
    public PacketSync m = null;
    /* access modifiers changed from: private */
    public l n = null;
    private ArrayList<k> o = new ArrayList<>();
    private com.xiaomi.smack.f p = new bj(this);

    class a extends h {
        com.xiaomi.push.service.aq.b b = null;

        public a(com.xiaomi.push.service.aq.b bVar) {
            super(9);
            this.b = bVar;
        }

        public void a() {
            try {
                if (!XMPushService.this.f()) {
                    com.xiaomi.channel.commonutils.logger.b.d("trying bind while the connection is not created, quit!");
                    return;
                }
                com.xiaomi.push.service.aq.b b2 = aq.a().b(this.b.h, this.b.b);
                if (b2 == null) {
                    StringBuilder sb = new StringBuilder("ignore bind because the channel ");
                    sb.append(this.b.h);
                    sb.append(" is removed ");
                    com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
                } else if (b2.m == com.xiaomi.push.service.aq.c.unbind) {
                    b2.a(com.xiaomi.push.service.aq.c.binding, 0, 0, (String) null, (String) null);
                    XMPushService.this.k.a(b2);
                    com.xiaomi.stats.h.a(XMPushService.this, b2);
                } else {
                    StringBuilder sb2 = new StringBuilder("trying duplicate bind, ingore! ");
                    sb2.append(b2.m);
                    com.xiaomi.channel.commonutils.logger.b.a(sb2.toString());
                }
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                XMPushService.this.a(10, e);
            } catch (Throwable unused) {
            }
        }

        public String b() {
            StringBuilder sb = new StringBuilder("bind the client. ");
            sb.append(this.b.h);
            return sb.toString();
        }
    }

    static class b extends h {
        private final com.xiaomi.push.service.aq.b b;

        public b(com.xiaomi.push.service.aq.b bVar) {
            super(12);
            this.b = bVar;
        }

        public void a() {
            this.b.a(com.xiaomi.push.service.aq.c.unbind, 1, 21, (String) null, (String) null);
        }

        public String b() {
            StringBuilder sb = new StringBuilder("bind time out. chid=");
            sb.append(this.b.h);
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            return TextUtils.equals(((b) obj).b.h, this.b.h);
        }

        public int hashCode() {
            return this.b.h.hashCode();
        }
    }

    class c extends h {
        private com.xiaomi.slim.b c = null;

        public c(com.xiaomi.slim.b bVar) {
            super(8);
            this.c = bVar;
        }

        public void a() {
            XMPushService.this.m.a(this.c);
        }

        public String b() {
            return "receive a message.";
        }
    }

    public class d extends h {
        d() {
            super(1);
        }

        public void a() {
            if (XMPushService.this.b()) {
                XMPushService.this.o();
            } else {
                com.xiaomi.channel.commonutils.logger.b.a((String) "should not connect. quit the job.");
            }
        }

        public String b() {
            return "do reconnect..";
        }
    }

    class e extends BroadcastReceiver {
        e() {
        }

        public void onReceive(Context context, Intent intent) {
            XMPushService.this.onStart(intent, XMPushService.c);
        }
    }

    public class f extends h {
        public int b;
        public Exception c;

        f(int i, Exception exc) {
            super(2);
            this.b = i;
            this.c = exc;
        }

        public void a() {
            XMPushService.this.a(this.b, this.c);
        }

        public String b() {
            return "disconnect the connection.";
        }
    }

    class g extends h {
        private Intent c = null;

        public g(Intent intent) {
            super(15);
            this.c = intent;
        }

        public void a() {
            XMPushService.this.c(this.c);
        }

        public String b() {
            StringBuilder sb = new StringBuilder("Handle intent action = ");
            sb.append(this.c.getAction());
            return sb.toString();
        }
    }

    public static abstract class h extends com.xiaomi.push.service.l.b {
        public h(int i) {
            super(i);
        }

        public abstract void a();

        public abstract String b();

        public void run() {
            if (!(this.a == 4 || this.a == 8)) {
                StringBuilder sb = new StringBuilder("JOB: ");
                sb.append(b());
                com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            }
            a();
        }
    }

    class i extends h {
        public i() {
            super(5);
        }

        public void a() {
            XMPushService.this.n.b();
        }

        public String b() {
            return "ask the job queue to quit";
        }
    }

    class j extends h {
        private com.xiaomi.smack.packet.d c = null;

        public j(com.xiaomi.smack.packet.d dVar) {
            super(8);
            this.c = dVar;
        }

        public void a() {
            XMPushService.this.m.a(this.c);
        }

        public String b() {
            return "receive a message.";
        }
    }

    public interface k {
        void a();
    }

    class l extends h {
        boolean b;

        public l(boolean z) {
            super(4);
            this.b = z;
        }

        public void a() {
            if (XMPushService.this.f()) {
                try {
                    if (!this.b) {
                        com.xiaomi.stats.h.a();
                    }
                    XMPushService.this.k.b(this.b);
                } catch (com.xiaomi.smack.l e) {
                    com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                    XMPushService.this.a(10, (Exception) e);
                }
            }
        }

        public String b() {
            return "send ping..";
        }
    }

    class m extends h {
        com.xiaomi.push.service.aq.b b = null;

        public m(com.xiaomi.push.service.aq.b bVar) {
            super(4);
            this.b = bVar;
        }

        public void a() {
            try {
                this.b.a(com.xiaomi.push.service.aq.c.unbind, 1, 16, (String) null, (String) null);
                XMPushService.this.k.a(this.b.h, this.b.b);
                this.b.a(com.xiaomi.push.service.aq.c.binding, 1, 16, (String) null, (String) null);
                XMPushService.this.k.a(this.b);
            } catch (com.xiaomi.smack.l e) {
                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                XMPushService.this.a(10, (Exception) e);
            }
        }

        public String b() {
            StringBuilder sb = new StringBuilder("rebind the client. ");
            sb.append(this.b.h);
            return sb.toString();
        }
    }

    class n extends h {
        n() {
            super(3);
        }

        public void a() {
            XMPushService.this.a(11, (Exception) null);
            if (XMPushService.this.b()) {
                XMPushService.this.o();
            }
        }

        public String b() {
            return "reset the connection.";
        }
    }

    class o extends h {
        com.xiaomi.push.service.aq.b b = null;
        int c;
        String d;
        String e;

        public o(com.xiaomi.push.service.aq.b bVar, int i, String str, String str2) {
            super(9);
            this.b = bVar;
            this.c = i;
            this.d = str;
            this.e = str2;
        }

        public void a() {
            if (!(this.b.m == com.xiaomi.push.service.aq.c.unbind || XMPushService.this.k == null)) {
                try {
                    XMPushService.this.k.a(this.b.h, this.b.b);
                } catch (com.xiaomi.smack.l e2) {
                    com.xiaomi.channel.commonutils.logger.b.a((Throwable) e2);
                    XMPushService.this.a(10, (Exception) e2);
                }
            }
            this.b.a(com.xiaomi.push.service.aq.c.unbind, this.c, 0, this.e, this.d);
        }

        public String b() {
            StringBuilder sb = new StringBuilder("unbind the channel. ");
            sb.append(this.b.h);
            return sb.toString();
        }
    }

    static {
        HostManager.addReservedHost("app.chat.xiaomi.net", "app.chat.xiaomi.net");
        HostManager.addReservedHost("app.chat.xiaomi.net", "42.62.94.2:443");
        HostManager.addReservedHost("app.chat.xiaomi.net", "114.54.23.2");
        HostManager.addReservedHost("app.chat.xiaomi.net", "111.13.142.2");
        HostManager.addReservedHost("app.chat.xiaomi.net", "111.206.200.2");
    }

    @TargetApi(11)
    public static Notification a(Context context) {
        Intent intent = new Intent(context, XMPushService.class);
        if (VERSION.SDK_INT >= 11) {
            Builder builder = new Builder(context);
            if (VERSION.SDK_INT >= 26) {
                builder.setChannelId("default");
            }
            builder.setSmallIcon(context.getApplicationInfo().icon);
            builder.setContentTitle("Push Service");
            builder.setContentText("Push Service");
            builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
            return builder.getNotification();
        }
        Notification notification = new Notification();
        notification.setLatestEventInfo(context, "Push Service", "Push Service", PendingIntent.getService(context, 0, intent, 0));
        return notification;
    }

    private com.xiaomi.smack.packet.d a(com.xiaomi.smack.packet.d dVar, String str, String str2) {
        String str3;
        String valueOf;
        aq a2 = aq.a();
        List<String> b2 = a2.b(str);
        if (b2.isEmpty()) {
            str3 = "open channel should be called first before sending a packet, pkg=";
        } else {
            dVar.o(str);
            str = dVar.l();
            if (TextUtils.isEmpty(str)) {
                str = b2.get(0);
                dVar.l(str);
            }
            com.xiaomi.push.service.aq.b b3 = a2.b(str, dVar.n());
            if (!f()) {
                str3 = "drop a packet as the channel is not connected, chid=";
            } else if (b3 == null || b3.m != com.xiaomi.push.service.aq.c.binded) {
                str3 = "drop a packet as the channel is not opened, chid=";
            } else if (TextUtils.equals(str2, b3.j)) {
                return dVar;
            } else {
                str3 = "invalid session. ";
                valueOf = String.valueOf(str2);
                com.xiaomi.channel.commonutils.logger.b.a(str3.concat(valueOf));
                return null;
            }
        }
        valueOf = String.valueOf(str);
        com.xiaomi.channel.commonutils.logger.b.a(str3.concat(valueOf));
        return null;
    }

    private void a(Intent intent) {
        String stringExtra = intent.getStringExtra(at.y);
        String stringExtra2 = intent.getStringExtra(at.B);
        Bundle bundleExtra = intent.getBundleExtra("ext_packet");
        intent.getBooleanExtra("ext_encrypt", true);
        com.xiaomi.smack.packet.c cVar = (com.xiaomi.smack.packet.c) a((com.xiaomi.smack.packet.d) new com.xiaomi.smack.packet.c(bundleExtra), stringExtra, stringExtra2);
        if (cVar != null) {
            c((h) new az(this, com.xiaomi.slim.b.a((com.xiaomi.smack.packet.d) cVar, aq.a().b(cVar.l(), cVar.n()).i)));
        }
    }

    private void a(String str, int i2) {
        Collection<com.xiaomi.push.service.aq.b> c2 = aq.a().c(str);
        if (c2 != null) {
            for (com.xiaomi.push.service.aq.b next : c2) {
                if (next != null) {
                    o oVar = new o(next, i2, null, null);
                    a((h) oVar);
                }
            }
        }
        aq.a().a(str);
    }

    public static boolean a(int i2, String str) {
        if (!TextUtils.equals(str, "Enter") || i2 != 1) {
            return TextUtils.equals(str, "Leave") && i2 == 2;
        }
        return true;
    }

    private boolean a(String str, Intent intent) {
        com.xiaomi.push.service.aq.b b2 = aq.a().b(str, intent.getStringExtra(at.p));
        boolean z = false;
        if (b2 == null || str == null) {
            return false;
        }
        String stringExtra = intent.getStringExtra(at.B);
        String stringExtra2 = intent.getStringExtra(at.u);
        if (!TextUtils.isEmpty(b2.j) && !TextUtils.equals(stringExtra, b2.j)) {
            StringBuilder sb = new StringBuilder("session changed. old session=");
            sb.append(b2.j);
            sb.append(", new session=");
            sb.append(stringExtra);
            sb.append(" chid = ");
            sb.append(str);
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            z = true;
        }
        if (stringExtra2.equals(b2.i)) {
            return z;
        }
        StringBuilder sb2 = new StringBuilder("security changed. chid = ");
        sb2.append(str);
        sb2.append(" sechash = ");
        sb2.append(com.xiaomi.channel.commonutils.string.c.a(stringExtra2));
        com.xiaomi.channel.commonutils.logger.b.a(sb2.toString());
        return true;
    }

    /* access modifiers changed from: private */
    public boolean a(String str, String str2, Context context) {
        if (TextUtils.equals("Leave", str) && !TextUtils.equals("Enter", h.a(context).c(str2))) {
            return false;
        }
        if (h.a(context).a(str2, str) != 0) {
            return true;
        }
        com.xiaomi.channel.commonutils.logger.b.a("update geofence statue failed geo_id:".concat(String.valueOf(str2)));
        return false;
    }

    private com.xiaomi.push.service.aq.b b(String str, Intent intent) {
        com.xiaomi.push.service.aq.b b2 = aq.a().b(str, intent.getStringExtra(at.p));
        if (b2 == null) {
            b2 = new com.xiaomi.push.service.aq.b(this);
        }
        b2.h = intent.getStringExtra(at.q);
        b2.b = intent.getStringExtra(at.p);
        b2.c = intent.getStringExtra(at.s);
        b2.a = intent.getStringExtra(at.y);
        b2.f = intent.getStringExtra(at.w);
        b2.g = intent.getStringExtra(at.x);
        b2.e = intent.getBooleanExtra(at.v, false);
        b2.i = intent.getStringExtra(at.u);
        b2.j = intent.getStringExtra(at.B);
        b2.d = intent.getStringExtra(at.t);
        b2.k = this.l;
        b2.a((Messenger) intent.getParcelableExtra(at.F));
        b2.l = getApplicationContext();
        aq.a().a(b2);
        return b2;
    }

    private void b(Intent intent) {
        String stringExtra = intent.getStringExtra(at.y);
        String stringExtra2 = intent.getStringExtra(at.B);
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("ext_packets");
        com.xiaomi.smack.packet.c[] cVarArr = new com.xiaomi.smack.packet.c[parcelableArrayExtra.length];
        intent.getBooleanExtra("ext_encrypt", true);
        int i2 = 0;
        while (i2 < parcelableArrayExtra.length) {
            cVarArr[i2] = new com.xiaomi.smack.packet.c((Bundle) parcelableArrayExtra[i2]);
            cVarArr[i2] = (com.xiaomi.smack.packet.c) a((com.xiaomi.smack.packet.d) cVarArr[i2], stringExtra, stringExtra2);
            if (cVarArr[i2] != null) {
                i2++;
            } else {
                return;
            }
        }
        aq a2 = aq.a();
        com.xiaomi.slim.b[] bVarArr = new com.xiaomi.slim.b[cVarArr.length];
        for (int i3 = 0; i3 < cVarArr.length; i3++) {
            com.xiaomi.smack.packet.c cVar = cVarArr[i3];
            bVarArr[i3] = com.xiaomi.slim.b.a((com.xiaomi.smack.packet.d) cVar, a2.b(cVar.l(), cVar.n()).i);
        }
        c((h) new d(this, bVarArr));
    }

    private void b(boolean z) {
        this.h = System.currentTimeMillis();
        if (!f()) {
            a(true);
        } else if (this.k.p() || this.k.q() || com.xiaomi.channel.commonutils.network.d.e(this)) {
            c((h) new l(z));
        } else {
            c((h) new f(17, null));
            a(true);
        }
    }

    /* access modifiers changed from: private */
    public void c(Intent intent) {
        int i2;
        String str;
        aq a2 = aq.a();
        boolean z = true;
        if (at.d.equalsIgnoreCase(intent.getAction()) || at.j.equalsIgnoreCase(intent.getAction())) {
            String stringExtra = intent.getStringExtra(at.q);
            if (TextUtils.isEmpty(intent.getStringExtra(at.u))) {
                com.xiaomi.channel.commonutils.logger.b.a((String) "security is empty. ignore.");
            } else if (stringExtra != null) {
                boolean a3 = a(stringExtra, intent);
                com.xiaomi.push.service.aq.b b2 = b(stringExtra, intent);
                if (!com.xiaomi.channel.commonutils.network.d.c(this)) {
                    this.l.a(this, b2, false, 2, null);
                    return;
                }
                if (!f()) {
                    a(true);
                } else if (b2.m == com.xiaomi.push.service.aq.c.unbind) {
                    c((h) new a(b2));
                } else if (a3) {
                    c((h) new m(b2));
                } else if (b2.m == com.xiaomi.push.service.aq.c.binding) {
                    com.xiaomi.channel.commonutils.logger.b.a(String.format("the client is binding. %1$s %2$s.", new Object[]{b2.h, b2.b}));
                } else if (b2.m == com.xiaomi.push.service.aq.c.binded) {
                    this.l.a(this, b2, true, 0, null);
                }
            } else {
                com.xiaomi.channel.commonutils.logger.b.d("channel id is empty, do nothing!");
            }
        } else if (at.i.equalsIgnoreCase(intent.getAction())) {
            String stringExtra2 = intent.getStringExtra(at.y);
            String stringExtra3 = intent.getStringExtra(at.q);
            String stringExtra4 = intent.getStringExtra(at.p);
            StringBuilder sb = new StringBuilder("Service called closechannel chid = ");
            sb.append(stringExtra3);
            sb.append(" userId = ");
            sb.append(stringExtra4);
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            if (TextUtils.isEmpty(stringExtra3)) {
                for (String a4 : a2.b(stringExtra2)) {
                    a(a4, 2);
                }
            } else if (TextUtils.isEmpty(stringExtra4)) {
                a(stringExtra3, 2);
            } else {
                a(stringExtra3, stringExtra4, 2, null, null);
            }
        } else if (at.e.equalsIgnoreCase(intent.getAction())) {
            a(intent);
        } else if (at.g.equalsIgnoreCase(intent.getAction())) {
            b(intent);
        } else if (at.f.equalsIgnoreCase(intent.getAction())) {
            com.xiaomi.smack.packet.d a5 = a((com.xiaomi.smack.packet.d) new com.xiaomi.smack.packet.b(intent.getBundleExtra("ext_packet")), intent.getStringExtra(at.y), intent.getStringExtra(at.B));
            if (a5 != null) {
                c((h) new az(this, com.xiaomi.slim.b.a(a5, a2.b(a5.l(), a5.n()).i)));
            }
        } else if (at.h.equalsIgnoreCase(intent.getAction())) {
            com.xiaomi.smack.packet.d a6 = a((com.xiaomi.smack.packet.d) new com.xiaomi.smack.packet.f(intent.getBundleExtra("ext_packet")), intent.getStringExtra(at.y), intent.getStringExtra(at.B));
            if (a6 != null) {
                c((h) new az(this, com.xiaomi.slim.b.a(a6, a2.b(a6.l(), a6.n()).i)));
            }
        } else if (at.k.equals(intent.getAction())) {
            String stringExtra5 = intent.getStringExtra(at.q);
            String stringExtra6 = intent.getStringExtra(at.p);
            if (stringExtra5 != null) {
                com.xiaomi.channel.commonutils.logger.b.a("request reset connection from chid = ".concat(String.valueOf(stringExtra5)));
                com.xiaomi.push.service.aq.b b3 = aq.a().b(stringExtra5, stringExtra6);
                if (b3 != null && b3.i.equals(intent.getStringExtra(at.u)) && b3.m == com.xiaomi.push.service.aq.c.binded) {
                    com.xiaomi.smack.a h2 = h();
                    if (h2 == null || !h2.a(System.currentTimeMillis() - 15000)) {
                        c((h) new n());
                    }
                }
            }
        } else {
            com.xiaomi.push.service.aq.b bVar = null;
            if (at.l.equals(intent.getAction())) {
                String stringExtra7 = intent.getStringExtra(at.y);
                List<String> b4 = a2.b(stringExtra7);
                if (b4.isEmpty()) {
                    com.xiaomi.channel.commonutils.logger.b.a("open channel should be called first before update info, pkg=".concat(String.valueOf(stringExtra7)));
                    return;
                }
                String stringExtra8 = intent.getStringExtra(at.q);
                String stringExtra9 = intent.getStringExtra(at.p);
                if (TextUtils.isEmpty(stringExtra8)) {
                    stringExtra8 = b4.get(0);
                }
                if (TextUtils.isEmpty(stringExtra9)) {
                    Collection<com.xiaomi.push.service.aq.b> c2 = a2.c(stringExtra8);
                    if (c2 != null && !c2.isEmpty()) {
                        bVar = c2.iterator().next();
                    }
                } else {
                    bVar = a2.b(stringExtra8, stringExtra9);
                }
                if (bVar != null) {
                    if (intent.hasExtra(at.w)) {
                        bVar.f = intent.getStringExtra(at.w);
                    }
                    if (intent.hasExtra(at.x)) {
                        bVar.g = intent.getStringExtra(at.x);
                    }
                }
            } else if ("com.xiaomi.mipush.REGISTER_APP".equals(intent.getAction())) {
                if (!av.a(getApplicationContext()).a() || av.a(getApplicationContext()).b() != 0) {
                    byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
                    String stringExtra10 = intent.getStringExtra("mipush_app_package");
                    boolean booleanExtra = intent.getBooleanExtra("mipush_env_chanage", false);
                    int intExtra = intent.getIntExtra("mipush_env_type", 1);
                    u.a((Context) this).g(stringExtra10);
                    if (!booleanExtra || "com.xiaomi.xmsf".equals(getPackageName())) {
                        a(byteArrayExtra, stringExtra10);
                        return;
                    }
                    bt btVar = new bt(this, 14, intExtra, byteArrayExtra, stringExtra10);
                    c((h) btVar);
                    return;
                }
                StringBuilder sb2 = new StringBuilder("register without being provisioned. ");
                sb2.append(intent.getStringExtra("mipush_app_package"));
                com.xiaomi.channel.commonutils.logger.b.a(sb2.toString());
            } else if ("com.xiaomi.mipush.SEND_MESSAGE".equals(intent.getAction()) || "com.xiaomi.mipush.UNREGISTER_APP".equals(intent.getAction())) {
                String stringExtra11 = intent.getStringExtra("mipush_app_package");
                byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
                boolean booleanExtra2 = intent.getBooleanExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
                if ("com.xiaomi.mipush.UNREGISTER_APP".equals(intent.getAction())) {
                    u.a((Context) this).d(stringExtra11);
                }
                a(stringExtra11, byteArrayExtra2, booleanExtra2);
            } else if (aw.a.equals(intent.getAction())) {
                String stringExtra12 = intent.getStringExtra("uninstall_pkg_name");
                if (stringExtra12 != null && !TextUtils.isEmpty(stringExtra12.trim())) {
                    try {
                        getPackageManager().getPackageInfo(stringExtra12, 0);
                        z = false;
                    } catch (NameNotFoundException unused) {
                    }
                    if (!"com.xiaomi.channel".equals(stringExtra12) || aq.a().c("1").isEmpty() || !z) {
                        SharedPreferences sharedPreferences = getSharedPreferences("pref_registered_pkg_names", 0);
                        String string = sharedPreferences.getString(stringExtra12, null);
                        if (!TextUtils.isEmpty(string) && z) {
                            Editor edit = sharedPreferences.edit();
                            edit.remove(stringExtra12);
                            edit.commit();
                            if (ah.e(this, stringExtra12)) {
                                ah.d(this, stringExtra12);
                            }
                            ah.b(this, stringExtra12);
                            if (f() && string != null) {
                                try {
                                    af.a(this, af.a(stringExtra12, string));
                                    StringBuilder sb3 = new StringBuilder("uninstall ");
                                    sb3.append(stringExtra12);
                                    sb3.append(" msg sent");
                                    com.xiaomi.channel.commonutils.logger.b.a(sb3.toString());
                                    return;
                                } catch (com.xiaomi.smack.l e2) {
                                    StringBuilder sb4 = new StringBuilder("Fail to send Message: ");
                                    sb4.append(e2.getMessage());
                                    com.xiaomi.channel.commonutils.logger.b.d(sb4.toString());
                                    a(10, (Exception) e2);
                                }
                            }
                        }
                        return;
                    }
                    a((String) "1", 0);
                    com.xiaomi.channel.commonutils.logger.b.a((String) "close the miliao channel as the app is uninstalled.");
                }
            } else if ("com.xiaomi.mipush.CLEAR_NOTIFICATION".equals(intent.getAction())) {
                String stringExtra13 = intent.getStringExtra(at.y);
                int intExtra2 = intent.getIntExtra(at.z, -2);
                if (!TextUtils.isEmpty(stringExtra13)) {
                    if (intExtra2 >= -1) {
                        ah.a((Context) this, stringExtra13, intExtra2);
                        return;
                    }
                    ah.a((Context) this, stringExtra13, intent.getStringExtra(at.D), intent.getStringExtra(at.E));
                }
            } else if ("com.xiaomi.mipush.SET_NOTIFICATION_TYPE".equals(intent.getAction())) {
                String stringExtra14 = intent.getStringExtra(at.y);
                String stringExtra15 = intent.getStringExtra(at.C);
                if (intent.hasExtra(at.A)) {
                    i2 = intent.getIntExtra(at.A, 0);
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(stringExtra14);
                    sb5.append(i2);
                    str = com.xiaomi.channel.commonutils.string.c.b(sb5.toString());
                    z = false;
                } else {
                    str = com.xiaomi.channel.commonutils.string.c.b(stringExtra14);
                    i2 = 0;
                }
                if (TextUtils.isEmpty(stringExtra14) || !TextUtils.equals(stringExtra15, str)) {
                    com.xiaomi.channel.commonutils.logger.b.d("invalid notification for ".concat(String.valueOf(stringExtra14)));
                } else if (z) {
                    ah.d(this, stringExtra14);
                } else {
                    ah.b((Context) this, stringExtra14, i2);
                }
            } else if ("com.xiaomi.mipush.DISABLE_PUSH".equals(intent.getAction())) {
                String stringExtra16 = intent.getStringExtra("mipush_app_package");
                if (!TextUtils.isEmpty(stringExtra16)) {
                    u.a((Context) this).e(stringExtra16);
                }
                if (!"com.xiaomi.xmsf".equals(getPackageName())) {
                    if (this.g != null) {
                        unregisterReceiver(this.g);
                        this.g = null;
                    }
                    this.n.c();
                    a((h) new bu(this, 2));
                    aq.a().e();
                    aq.a().a((Context) this, 0);
                    aq.a().d();
                    ba.a().b();
                    com.xiaomi.push.service.timers.a.a();
                }
            } else if ("com.xiaomi.mipush.DISABLE_PUSH_MESSAGE".equals(intent.getAction()) || "com.xiaomi.mipush.ENABLE_PUSH_MESSAGE".equals(intent.getAction())) {
                String stringExtra17 = intent.getStringExtra("mipush_app_package");
                byte[] byteArrayExtra3 = intent.getByteArrayExtra("mipush_payload");
                String stringExtra18 = intent.getStringExtra("mipush_app_id");
                String stringExtra19 = intent.getStringExtra("mipush_app_token");
                if ("com.xiaomi.mipush.DISABLE_PUSH_MESSAGE".equals(intent.getAction())) {
                    u.a((Context) this).f(stringExtra17);
                }
                if ("com.xiaomi.mipush.ENABLE_PUSH_MESSAGE".equals(intent.getAction())) {
                    u.a((Context) this).h(stringExtra17);
                    u.a((Context) this).i(stringExtra17);
                }
                if (byteArrayExtra3 == null) {
                    w.a(this, stringExtra17, byteArrayExtra3, ErrorCode.ERROR_INVALID_PAYLOAD, "null payload");
                    return;
                }
                w.b(stringExtra17, byteArrayExtra3);
                v vVar = new v(this, stringExtra17, stringExtra18, stringExtra19, byteArrayExtra3);
                a((h) vVar);
                if ("com.xiaomi.mipush.ENABLE_PUSH_MESSAGE".equals(intent.getAction()) && this.g == null) {
                    this.g = new e();
                    registerReceiver(this.g, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                }
            } else if ("com.xiaomi.mipush.SEND_TINYDATA".equals(intent.getAction())) {
                String stringExtra20 = intent.getStringExtra("mipush_app_package");
                byte[] byteArrayExtra4 = intent.getByteArrayExtra("mipush_payload");
                com.xiaomi.xmpush.thrift.f fVar = new com.xiaomi.xmpush.thrift.f();
                try {
                    au.a(fVar, byteArrayExtra4);
                    com.xiaomi.tinyData.d.a(this).a(fVar, stringExtra20);
                } catch (org.apache.thrift.f e3) {
                    com.xiaomi.channel.commonutils.logger.b.a((Throwable) e3);
                }
            } else {
                if ("com.xiaomi.push.timer".equalsIgnoreCase(intent.getAction())) {
                    com.xiaomi.channel.commonutils.logger.b.a((String) "Service called on timer");
                    if (l()) {
                        b(false);
                    }
                } else if ("com.xiaomi.push.check_alive".equalsIgnoreCase(intent.getAction())) {
                    com.xiaomi.channel.commonutils.logger.b.a((String) "Service called on check alive.");
                    if (l()) {
                        b(false);
                    }
                } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                    k();
                }
            }
        }
    }

    private void c(h hVar) {
        this.n.a((com.xiaomi.push.service.l.b) hVar);
    }

    private void c(boolean z) {
        try {
            if (com.xiaomi.channel.commonutils.android.j.d()) {
                if (z) {
                    sendBroadcast(new Intent("miui.intent.action.NETWORK_CONNECTED"));
                    return;
                }
                sendBroadcast(new Intent("miui.intent.action.NETWORK_BLOCKED"));
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e2);
        }
    }

    private void k() {
        NetworkInfo networkInfo;
        try {
            networkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e2);
            networkInfo = null;
        }
        if (networkInfo != null) {
            StringBuilder sb = new StringBuilder("network changed, ");
            sb.append(networkInfo.toString());
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            State state = networkInfo.getState();
            if (state == State.SUSPENDED || state == State.UNKNOWN) {
                return;
            }
        } else {
            com.xiaomi.channel.commonutils.logger.b.a((String) "network changed, no active network");
        }
        if (com.xiaomi.stats.f.b() != null) {
            com.xiaomi.stats.f.b().b();
        }
        com.xiaomi.smack.util.g.a((Context) this);
        this.j.r();
        if (com.xiaomi.channel.commonutils.network.d.c(this)) {
            if (f() && l()) {
                b(false);
            }
            if (!f() && !g()) {
                this.n.b(1);
                a((h) new d());
            }
            com.xiaomi.push.log.b.a((Context) this).a();
        } else {
            a((h) new f(2, null));
        }
        n();
    }

    private boolean l() {
        if (System.currentTimeMillis() - this.h < StatisticConfig.MIN_UPLOAD_INTERVAL) {
            return false;
        }
        return com.xiaomi.channel.commonutils.network.d.d(this);
    }

    private boolean m() {
        return "com.xiaomi.xmsf".equals(getPackageName()) || !u.a((Context) this).b(getPackageName());
    }

    /* access modifiers changed from: private */
    public void n() {
        if (!b()) {
            com.xiaomi.push.service.timers.a.a();
        } else if (!com.xiaomi.push.service.timers.a.b()) {
            com.xiaomi.push.service.timers.a.a(true);
        }
    }

    /* access modifiers changed from: private */
    public void o() {
        String str;
        if (this.k != null && this.k.k()) {
            str = "try to connect while connecting.";
        } else if (this.k == null || !this.k.l()) {
            this.d.b(com.xiaomi.channel.commonutils.network.d.k(this));
            p();
            if (this.k == null) {
                aq.a().a((Context) this);
                c(false);
            }
            return;
        } else {
            str = "try to connect while is connected.";
        }
        com.xiaomi.channel.commonutils.logger.b.d(str);
    }

    private void p() {
        try {
            this.j.a(this.p, (com.xiaomi.smack.filter.a) new bl(this));
            this.j.t();
            this.k = this.j;
        } catch (com.xiaomi.smack.l e2) {
            com.xiaomi.channel.commonutils.logger.b.a((String) "fail to create Slim connection", (Throwable) e2);
            this.j.b(3, e2);
        }
    }

    private boolean q() {
        if (TextUtils.equals(getPackageName(), "com.xiaomi.xmsf")) {
            return false;
        }
        return an.a((Context) this).a(com.xiaomi.xmpush.thrift.g.ForegroundServiceSwitch.a(), false);
    }

    private void r() {
        if (VERSION.SDK_INT < 18) {
            startForeground(i, new Notification());
        } else {
            bindService(new Intent(this, XMJobService.class), new bm(this), 1);
        }
    }

    private void s() {
        synchronized (this.o) {
            this.o.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (System.currentTimeMillis() - this.h >= ((long) com.xiaomi.smack.g.c()) && com.xiaomi.channel.commonutils.network.d.d(this)) {
            b(true);
        }
    }

    public void a(int i2) {
        this.n.b(i2);
    }

    public void a(int i2, Exception exc) {
        StringBuilder sb = new StringBuilder("disconnect ");
        sb.append(hashCode());
        sb.append(", ");
        sb.append(this.k == null ? null : Integer.valueOf(this.k.hashCode()));
        com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
        if (this.k != null) {
            this.k.b(i2, exc);
            this.k = null;
        }
        a(7);
        a(4);
        aq.a().a((Context) this, i2);
    }

    public void a(BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xiaomi.metoknlp.geofencing.state_change_protected");
        registerReceiver(broadcastReceiver, intentFilter, "com.xiaomi.metoknlp.permission.NOTIFY_FENCE_STATE", null);
    }

    public void a(h hVar) {
        a(hVar, 0);
    }

    public void a(h hVar, long j2) {
        try {
            this.n.a((com.xiaomi.push.service.l.b) hVar, j2);
        } catch (IllegalStateException unused) {
        }
    }

    public void a(k kVar) {
        synchronized (this.o) {
            this.o.add(kVar);
        }
    }

    public void a(com.xiaomi.push.service.aq.b bVar) {
        if (bVar != null) {
            long b2 = bVar.b();
            StringBuilder sb = new StringBuilder("schedule rebind job in ");
            sb.append(b2 / 1000);
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            a((h) new a(bVar), b2);
        }
    }

    public void a(com.xiaomi.slim.b bVar) {
        if (this.k != null) {
            this.k.b(bVar);
            return;
        }
        throw new com.xiaomi.smack.l((String) "try send msg while connection is null.");
    }

    public void a(com.xiaomi.smack.a aVar) {
        com.xiaomi.stats.f.b().a(aVar);
        c(true);
        this.e.a();
        Iterator<com.xiaomi.push.service.aq.b> it = aq.a().b().iterator();
        while (it.hasNext()) {
            a((h) new a(it.next()));
        }
    }

    public void a(com.xiaomi.smack.a aVar, int i2, Exception exc) {
        com.xiaomi.stats.f.b().a(aVar, i2, exc);
        a(false);
    }

    public void a(com.xiaomi.smack.a aVar, Exception exc) {
        com.xiaomi.stats.f.b().a(aVar, exc);
        c(false);
        a(false);
    }

    public void a(String str, String str2, int i2, String str3, String str4) {
        com.xiaomi.push.service.aq.b b2 = aq.a().b(str, str2);
        if (b2 != null) {
            o oVar = new o(b2, i2, str4, str3);
            a((h) oVar);
        }
        aq.a().a(str, str2);
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, byte[] bArr, boolean z) {
        Collection<com.xiaomi.push.service.aq.b> c2 = aq.a().c("5");
        if (c2.isEmpty()) {
            if (z) {
                w.b(str, bArr);
            }
        } else if (c2.iterator().next().m == com.xiaomi.push.service.aq.c.binded) {
            c((h) new bv(this, 4, str, bArr));
        } else if (z) {
            w.b(str, bArr);
        }
    }

    public void a(boolean z) {
        this.e.a(z);
    }

    public void a(byte[] bArr, String str) {
        if (bArr == null) {
            w.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "null payload");
            com.xiaomi.channel.commonutils.logger.b.a((String) "register request without payload");
            return;
        }
        af afVar = new af();
        try {
            au.a(afVar, bArr);
            if (afVar.a == com.xiaomi.xmpush.thrift.a.Registration) {
                aj ajVar = new aj();
                try {
                    au.a(ajVar, afVar.f());
                    w.a(afVar.j(), bArr);
                    v vVar = new v(this, afVar.j(), ajVar.e(), ajVar.i(), bArr);
                    a((h) vVar);
                } catch (org.apache.thrift.f e2) {
                    com.xiaomi.channel.commonutils.logger.b.a((Throwable) e2);
                    w.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data action error.");
                }
            } else {
                w.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " registration action required.");
                com.xiaomi.channel.commonutils.logger.b.a((String) "register request with invalid payload");
            }
        } catch (org.apache.thrift.f e3) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e3);
            w.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data container error.");
        }
    }

    public void a(com.xiaomi.slim.b[] bVarArr) {
        if (this.k != null) {
            this.k.a(bVarArr);
            return;
        }
        throw new com.xiaomi.smack.l((String) "try send msg while connection is null.");
    }

    public void b(h hVar) {
        this.n.a(hVar.a, (com.xiaomi.push.service.l.b) hVar);
    }

    public void b(com.xiaomi.smack.a aVar) {
        com.xiaomi.channel.commonutils.logger.b.c("begin to connect...");
        com.xiaomi.stats.f.b().b(aVar);
    }

    public boolean b() {
        return com.xiaomi.channel.commonutils.network.d.c(this) && aq.a().c() > 0 && !c() && m();
    }

    public boolean b(int i2) {
        return this.n.a(i2);
    }

    public boolean c() {
        boolean z = false;
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            Field field = cls.getField("IS_CM_CUSTOMIZATION_TEST");
            Field field2 = cls.getField("IS_CU_CUSTOMIZATION_TEST");
            Field field3 = cls.getField("IS_CT_CUSTOMIZATION_TEST");
            if (!field.getBoolean(null) && !field2.getBoolean(null) && !field3.getBoolean(null)) {
                return false;
            }
            z = true;
            return z;
        } catch (Throwable unused) {
        }
    }

    public e d() {
        return new e();
    }

    public e e() {
        return this.l;
    }

    public boolean f() {
        return this.k != null && this.k.l();
    }

    public boolean g() {
        return this.k != null && this.k.k();
    }

    public com.xiaomi.smack.a h() {
        return this.k;
    }

    /* access modifiers changed from: 0000 */
    public void i() {
        Iterator it = new ArrayList(this.o).iterator();
        while (it.hasNext()) {
            ((k) it.next()).a();
        }
    }

    public IBinder onBind(Intent intent) {
        return this.a.getBinder();
    }

    public void onCreate() {
        String str;
        super.onCreate();
        com.xiaomi.channel.commonutils.android.j.a(this);
        s a2 = t.a(this);
        if (a2 != null) {
            com.xiaomi.channel.commonutils.misc.a.a(a2.g);
        }
        String a3 = a.a(getApplicationContext()).a();
        if (!TextUtils.isEmpty(a3)) {
            this.f = a3;
            if (PushChannelRegion.Global.name().equals(this.f)) {
                str = "app.chat.global.xiaomi.net";
            } else if (PushChannelRegion.Europe.name().equals(this.f)) {
                str = "fr.app.chat.global.xiaomi.net";
            }
            com.xiaomi.smack.b.a(str);
        } else {
            this.f = PushChannelRegion.China.name();
        }
        this.a = new Messenger(new bn(this));
        au.a(this);
        bo boVar = new bo(this, null, 5222, "xiaomi.com", null);
        this.d = boVar;
        this.d.a(true);
        this.j = new com.xiaomi.slim.f(this, this.d);
        this.l = d();
        try {
            if (com.xiaomi.channel.commonutils.android.j.d()) {
                this.l.a((Context) this);
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e2);
        }
        com.xiaomi.push.service.timers.a.a((Context) this);
        this.j.a((com.xiaomi.smack.d) this);
        this.m = new PacketSync(this);
        this.e = new ay(this);
        new f().a();
        com.xiaomi.stats.f.a().a(this);
        this.n = new l((String) "Connection Controller Thread");
        if (m()) {
            a((h) new bp(this, 11));
        }
        aq a4 = aq.a();
        a4.e();
        a4.a((com.xiaomi.push.service.aq.a) new bq(this));
        if (m()) {
            this.g = new e();
            registerReceiver(this.g, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
        if (q()) {
            r();
        }
        com.xiaomi.tinyData.d.a(this).a((com.xiaomi.tinyData.e) new n(this), (String) "UPLOADER_PUSH_CHANNEL");
        a(this.b);
        com.xiaomi.channel.commonutils.misc.h.a((Context) this).a((com.xiaomi.channel.commonutils.misc.h.a) new g(this), 86400);
        a((k) new com.xiaomi.tinyData.a(this));
        StringBuilder sb = new StringBuilder("XMPushService created pid = ");
        sb.append(i);
        com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
    }

    public void onDestroy() {
        if (this.g != null) {
            unregisterReceiver(this.g);
        }
        unregisterReceiver(this.b);
        this.n.c();
        a((h) new bk(this, 2));
        a((h) new i());
        aq.a().e();
        aq.a().a((Context) this, 15);
        aq.a().d();
        this.j.b((com.xiaomi.smack.d) this);
        ba.a().b();
        com.xiaomi.push.service.timers.a.a();
        s();
        super.onDestroy();
        com.xiaomi.channel.commonutils.logger.b.a((String) "Service destroyed");
    }

    public void onStart(Intent intent, int i2) {
        if (intent == null) {
            com.xiaomi.channel.commonutils.logger.b.d("onStart() with intent NULL");
        } else {
            com.xiaomi.channel.commonutils.logger.b.c(String.format("onStart() with intent.Action = %s, chid = %s", new Object[]{intent.getAction(), intent.getStringExtra(at.q)}));
        }
        if (!(intent == null || intent.getAction() == null)) {
            if ("com.xiaomi.push.timer".equalsIgnoreCase(intent.getAction()) || "com.xiaomi.push.check_alive".equalsIgnoreCase(intent.getAction())) {
                if (this.n.d()) {
                    com.xiaomi.channel.commonutils.logger.b.d("ERROR, the job controller is blocked.");
                    aq.a().a((Context) this, 14);
                    stopSelf();
                    return;
                }
                a((h) new g(intent));
            } else if (!"com.xiaomi.push.network_status_changed".equalsIgnoreCase(intent.getAction())) {
                a((h) new g(intent));
            }
        }
    }

    public int onStartCommand(Intent intent, int i2, int i3) {
        onStart(intent, i3);
        return c;
    }
}
