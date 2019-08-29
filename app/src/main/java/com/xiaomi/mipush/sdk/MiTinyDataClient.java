package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.bf;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.f;
import com.xiaomi.xmpush.thrift.u;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MiTinyDataClient {
    public static final String PENDING_REASON_APPID = "com.xiaomi.xmpushsdk.tinydataPending.appId";
    public static final String PENDING_REASON_CHANNEL = "com.xiaomi.xmpushsdk.tinydataPending.channel";
    public static final String PENDING_REASON_INIT = "com.xiaomi.xmpushsdk.tinydataPending.init";

    public static class a {
        private static a a;
        /* access modifiers changed from: private */
        public Context b;
        private String c;
        private Boolean d;
        private C0079a e = new C0079a();
        private final ArrayList<f> f = new ArrayList<>();

        /* renamed from: com.xiaomi.mipush.sdk.MiTinyDataClient$a$a reason: collision with other inner class name */
        public class C0079a {
            public final ArrayList<f> a = new ArrayList<>();
            private ScheduledThreadPoolExecutor c = new ScheduledThreadPoolExecutor(1);
            /* access modifiers changed from: private */
            public ScheduledFuture<?> d;
            private final Runnable e = new z(this);

            public C0079a() {
            }

            /* access modifiers changed from: private */
            public void a() {
                if (this.d == null) {
                    this.d = this.c.scheduleAtFixedRate(this.e, 1000, 1000, TimeUnit.MILLISECONDS);
                }
            }

            /* access modifiers changed from: private */
            public void b() {
                f remove = this.a.remove(0);
                for (ai a2 : bf.a(Arrays.asList(new f[]{remove}), a.this.b.getPackageName(), c.a(a.this.b).c(), 30720)) {
                    StringBuilder sb = new StringBuilder("MiTinyDataClient Send item by PushServiceClient.sendMessage(XmActionNotification).");
                    sb.append(remove.m());
                    b.c(sb.toString());
                    aj.a(a.this.b).a(a2, com.xiaomi.xmpush.thrift.a.Notification, true, (u) null);
                }
            }

            public void a(f fVar) {
                this.c.execute(new y(this, fVar));
            }
        }

        public static a a() {
            if (a == null) {
                synchronized (a.class) {
                    if (a == null) {
                        a = new a();
                    }
                }
            }
            return a;
        }

        private void b(f fVar) {
            synchronized (this.f) {
                if (!this.f.contains(fVar)) {
                    this.f.add(fVar);
                    if (this.f.size() > 100) {
                        this.f.remove(0);
                    }
                }
            }
        }

        private boolean b(Context context) {
            if (!aj.a(context).c()) {
                return true;
            }
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
                return packageInfo != null && packageInfo.versionCode >= 108;
            } catch (Exception unused) {
                return false;
            }
        }

        private boolean c(Context context) {
            return c.a(context).c() == null && !b(this.b);
        }

        private boolean c(f fVar) {
            if (bf.a(fVar, false)) {
                return false;
            }
            if (this.d.booleanValue()) {
                StringBuilder sb = new StringBuilder("MiTinyDataClient Send item by PushServiceClient.sendTinyData(ClientUploadDataItem).");
                sb.append(fVar.m());
                b.c(sb.toString());
                aj.a(this.b).a(fVar);
            } else {
                this.e.a(fVar);
            }
            return true;
        }

        public void a(Context context) {
            if (context == null) {
                b.a((String) "context is null, MiTinyDataClientImp.init() failed.");
                return;
            }
            this.b = context;
            this.d = Boolean.valueOf(b(context));
            b((String) MiTinyDataClient.PENDING_REASON_INIT);
        }

        public synchronized void a(String str) {
            if (TextUtils.isEmpty(str)) {
                b.a((String) "channel is null, MiTinyDataClientImp.setChannel(String) failed.");
                return;
            }
            this.c = str;
            b((String) MiTinyDataClient.PENDING_REASON_CHANNEL);
        }

        public synchronized boolean a(f fVar) {
            String sb;
            boolean z = false;
            if (fVar == null) {
                return false;
            }
            if (bf.a(fVar, true)) {
                return false;
            }
            boolean z2 = TextUtils.isEmpty(fVar.a()) && TextUtils.isEmpty(this.c);
            boolean z3 = !b();
            if (this.b == null || c(this.b)) {
                z = true;
            }
            if (!z3 && !z2) {
                if (!z) {
                    StringBuilder sb2 = new StringBuilder("MiTinyDataClient Send item immediately.");
                    sb2.append(fVar.m());
                    b.c(sb2.toString());
                    if (TextUtils.isEmpty(fVar.m())) {
                        fVar.f(MiPushClient.generatePacketID());
                    }
                    if (TextUtils.isEmpty(fVar.a())) {
                        fVar.a(this.c);
                    }
                    if (TextUtils.isEmpty(fVar.k())) {
                        fVar.e(this.b.getPackageName());
                    }
                    if (fVar.g() <= 0) {
                        fVar.b(System.currentTimeMillis());
                    }
                    return c(fVar);
                }
            }
            if (z2) {
                StringBuilder sb3 = new StringBuilder("MiTinyDataClient Pending ");
                sb3.append(fVar.d());
                sb3.append(" reason is com.xiaomi.xmpushsdk.tinydataPending.channel");
                sb = sb3.toString();
            } else if (z3) {
                StringBuilder sb4 = new StringBuilder("MiTinyDataClient Pending ");
                sb4.append(fVar.d());
                sb4.append(" reason is com.xiaomi.xmpushsdk.tinydataPending.init");
                sb = sb4.toString();
            } else {
                if (z) {
                    StringBuilder sb5 = new StringBuilder("MiTinyDataClient Pending ");
                    sb5.append(fVar.d());
                    sb5.append(" reason is com.xiaomi.xmpushsdk.tinydataPending.appId");
                    sb = sb5.toString();
                }
                b(fVar);
                return true;
            }
            b.c(sb);
            b(fVar);
            return true;
        }

        public void b(String str) {
            StringBuilder sb = new StringBuilder("MiTinyDataClient.processPendingList(");
            sb.append(str);
            sb.append(")");
            b.c(sb.toString());
            ArrayList arrayList = new ArrayList();
            synchronized (this.f) {
                arrayList.addAll(this.f);
                this.f.clear();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                a((f) it.next());
            }
        }

        public boolean b() {
            return this.b != null;
        }
    }

    public static void init(Context context, String str) {
        if (context == null) {
            b.a((String) "context is null, MiTinyDataClient.init(Context, String) failed.");
            return;
        }
        a.a().a(context);
        if (TextUtils.isEmpty(str)) {
            b.a((String) "channel is null or empty, MiTinyDataClient.init(Context, String) failed.");
        } else {
            a.a().a(str);
        }
    }

    public static boolean upload(Context context, f fVar) {
        StringBuilder sb = new StringBuilder("MiTinyDataClient.upload ");
        sb.append(fVar.m());
        b.c(sb.toString());
        if (!a.a().b()) {
            a.a().a(context);
        }
        return a.a().a(fVar);
    }

    public static boolean upload(Context context, String str, String str2, long j, String str3) {
        f fVar = new f();
        fVar.d(str);
        fVar.c(str2);
        fVar.a(j);
        fVar.b(str3);
        fVar.c(true);
        fVar.a((String) "push_sdk_channel");
        return upload(context, fVar);
    }

    public static boolean upload(String str, String str2, long j, String str3) {
        f fVar = new f();
        fVar.d(str);
        fVar.c(str2);
        fVar.a(j);
        fVar.b(str3);
        return a.a().a(fVar);
    }
}
