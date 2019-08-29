package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.iflytek.tts.TtsService.Tts;
import com.yunos.carkitsdk.CarKitManager$1;
import com.yunos.carkitsdk.ConnectionStatusInfo;
import com.yunos.carkitsdk.IAliTransferService;
import com.yunos.carkitsdk.IServiceStatusCallBack.Stub;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* renamed from: fbl reason: default package */
/* compiled from: CarKitManager */
public final class fbl {
    private static fbl n;
    IAliTransferService a;
    long b;
    public ArrayList<fbm> c = new ArrayList<>();
    public ArrayList<fbo> d = new ArrayList<>();
    public ArrayList<fbn> e = new ArrayList<>();
    public Set<Long> f = new TreeSet();
    public Set<Long> g = new TreeSet();
    public boolean h;
    public boolean i;
    public int j = 2;
    public String k = "";
    Stub l = new CarKitManager$1(this);
    public Handler m = new Handler() {
        public final void handleMessage(Message message) {
            new StringBuilder("message what = ").append(message.what);
            switch (message.what) {
                case 256:
                    fbl fbl = fbl.this;
                    Object obj = message.obj;
                    fbl.a(fbl);
                    return;
                case Tts.TTS_STATE_INVALID_DATA /*258*/:
                    fbl fbl2 = fbl.this;
                    ConnectionStatusInfo connectionStatusInfo = (ConnectionStatusInfo) message.obj;
                    StringBuilder sb = new StringBuilder("handleConnectionStatus ");
                    sb.append(connectionStatusInfo.b);
                    sb.append(Token.SEPARATOR);
                    sb.append(connectionStatusInfo.c);
                    sb.append(Token.SEPARATOR);
                    sb.append(connectionStatusInfo.d);
                    new StringBuilder("mConnectionStatusListeners.size()=").append(fbl2.c.size());
                    if (fbl2.b != 0) {
                        fbl2.a(connectionStatusInfo.a, connectionStatusInfo.b);
                    }
                    return;
                case Tts.TTS_STATE_CREATED /*259*/:
                    fbl fbl3 = fbl.this;
                    a aVar = (a) message.obj;
                    new StringBuilder("mTransferStatusListeners.size()=").append(fbl3.d.size());
                    if (fbl3.b == 0) {
                        new StringBuilder("not register component, discard ").append(fbl3.d.size());
                        return;
                    } else {
                        fbl3.a(aVar.b, aVar.c);
                        return;
                    }
                case Tts.TTS_STATE_DESTROY /*260*/:
                    fbl fbl4 = fbl.this;
                    Object obj2 = message.obj;
                    if (fbl4.b == 0) {
                        new StringBuilder("not register component, discard ").append(fbl4.d.size());
                        return;
                    } else {
                        fbl4.e();
                        return;
                    }
                case 261:
                    fbl fbl5 = fbl.this;
                    Object obj3 = message.obj;
                    if (fbl5.b == 0) {
                        new StringBuilder("not register component, discard ").append(fbl5.d.size());
                        return;
                    } else {
                        fbl5.d();
                        return;
                    }
                case 262:
                    fbl.b(fbl.this);
                    break;
            }
        }
    };
    private Context o;
    private ServiceConnection p = new ServiceConnection() {
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            fbl.this.a = IAliTransferService.Stub.asInterface(iBinder);
            if (fbl.this.b > 0) {
                try {
                    fbl.this.a.registerComponent(fbl.this.b, fbl.this.l);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            Iterator<fbn> it = fbl.this.e.iterator();
            while (it.hasNext()) {
                it.next().c();
            }
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            fbl.this.a = null;
            fbl.this.a((String) "", 2);
            fbl fbl = fbl.this;
            fbl.a = null;
            fbl.b = 0;
            fbl.f.clear();
            fbl.g.clear();
            fbl.h = false;
            fbl.i = false;
            fbl.k = "";
            fbl.j = 2;
            Iterator<fbn> it = fbl.this.e.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        }
    };

    /* renamed from: fbl$a */
    /* compiled from: CarKitManager */
    public static class a {
        long a;
        int b;
        String c;

        public a(long j, int i, String str) {
            this.a = j;
            this.b = i;
            this.c = str;
        }
    }

    public static fbl a(Context context) {
        if (n == null) {
            n = new fbl(context);
        }
        return n;
    }

    private fbl(Context context) {
        this.o = context;
    }

    public final boolean a() {
        return this.a != null;
    }

    public final boolean b() {
        return this.j == 1;
    }

    public final void c() {
        this.c.clear();
        this.d.clear();
        this.e.clear();
        try {
            if (this.a != null) {
                this.a = null;
                this.o.unbindService(this.p);
            }
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        }
    }

    private static Intent a(Context context, Intent intent) {
        String str;
        int i2 = 0;
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
        if (queryIntentServices == null || queryIntentServices.size() == 0) {
            return null;
        }
        String packageName = context.getPackageName();
        List<RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningServices(200);
        if (runningServices.size() > 0) {
            int i3 = 0;
            while (true) {
                if (i3 >= runningServices.size()) {
                    break;
                } else if (runningServices.get(i3).process.equals("com.yunos.carkitservice.CarKitService")) {
                    str = runningServices.get(i3).service.getPackageName();
                    break;
                } else {
                    i3++;
                }
            }
        }
        str = null;
        "runing service package is ".concat(String.valueOf(str));
        "this APK package is ".concat(String.valueOf(packageName));
        if (str == null) {
            for (ResolveInfo next : queryIntentServices) {
                i2++;
                new StringBuilder("packageName=").append(next.serviceInfo.packageName);
                new StringBuilder("className=").append(next.serviceInfo.name);
                new StringBuilder("processName=").append(next.serviceInfo.processName);
                if (packageName.equals(next.serviceInfo.packageName)) {
                    break;
                }
            }
        } else {
            for (ResolveInfo next2 : queryIntentServices) {
                i2++;
                new StringBuilder("packageName=").append(next2.serviceInfo.packageName);
                new StringBuilder("className=").append(next2.serviceInfo.name);
                new StringBuilder("processName=").append(next2.serviceInfo.processName);
                if (str.equals(next2.serviceInfo.packageName)) {
                    break;
                }
            }
        }
        int i4 = i2 - 1;
        if (i4 < 0) {
            return null;
        }
        ResolveInfo resolveInfo = queryIntentServices.get(i4);
        String str2 = resolveInfo.serviceInfo.packageName;
        ComponentName componentName = new ComponentName(str2, resolveInfo.serviceInfo.name);
        "connect to service package ".concat(String.valueOf(str2));
        Intent intent2 = new Intent(intent);
        intent2.setComponent(componentName);
        return intent2;
    }

    private void h() {
        Iterator<fbn> it = this.e.iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str, int i2) {
        Iterator<fbm> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().a(str, i2);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void d() {
        Iterator<fbo> it = this.d.iterator();
        while (it.hasNext()) {
            it.next();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        Iterator<fbo> it = this.d.iterator();
        while (it.hasNext()) {
            it.next();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i2, String str) {
        Iterator<fbo> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().a(i2, str);
        }
    }

    public final void a(fbn fbn) {
        if (this.a != null) {
            fbn.c();
            return;
        }
        if (!this.e.contains(fbn)) {
            this.e.add(fbn);
        }
        Intent intent = new Intent();
        intent.setAction("com.yunos.carkitservice.remoteservice");
        Intent a2 = a(this.o, intent);
        if (a2 != null) {
            this.o.bindService(a2, this.p, 1);
        }
    }

    public final int f() {
        if (this.b == 3) {
            return 0;
        }
        if (this.g.contains(Long.valueOf(3))) {
            this.b = 3;
        }
        int i2 = 1;
        if (this.a != null) {
            try {
                this.b = 3;
                i2 = this.a.registerComponent(3, this.l);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
        return i2;
    }

    public final int g() {
        int i2;
        this.g.remove(Long.valueOf(3));
        this.b = 0;
        if (this.a != null) {
            i2 = 0;
            try {
                this.a.unRegisterComponent(3);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        } else {
            i2 = 1;
        }
        h();
        return i2;
    }

    public final void a(fbm fbm) {
        if (!this.c.contains(fbm)) {
            this.c.add(fbm);
        }
    }

    public final void b(fbm fbm) {
        this.c.remove(fbm);
    }

    public final void a(fbo fbo) {
        if (!this.d.contains(fbo)) {
            this.d.add(fbo);
        }
    }

    public final void b(fbn fbn) {
        this.e.remove(fbn);
    }

    public final int b(int i2, String str) {
        if (!i()) {
            return 1;
        }
        if (this.j != 1) {
            return 7;
        }
        return a(this.b, i2, str);
    }

    private int a(long j2, int i2, String str) {
        try {
            return this.a.sendMessage(j2, 3, i2, str);
        } catch (RemoteException e2) {
            e2.printStackTrace();
            return 1;
        }
    }

    private boolean i() {
        if (this.b == 0 || this.a == null) {
            return false;
        }
        return true;
    }

    static /* synthetic */ void a(fbl fbl) {
        Iterator<fbm> it = fbl.c.iterator();
        while (it.hasNext()) {
            it.next();
        }
    }

    static /* synthetic */ void b(fbl fbl) {
        fbl.h();
        fbl.b = 0;
    }
}
