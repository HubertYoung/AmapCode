package com.yunos.carkitservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import com.amap.bundle.drivecommon.inter.NetConstant;
import com.yunos.carkitsdk.IAliTransferService.Stub;
import com.yunos.carkitsdk.IServiceStatusCallBack;
import com.yunos.carkitsdk.TransferInfo;
import java.util.Set;

public class CarKitService extends Service {
    private Stub a = new Stub() {
        public int connectToCarWithPasswd(String str, String str2) throws RemoteException {
            return 0;
        }

        public boolean isCarCached(String str) throws RemoteException {
            return false;
        }

        public void setCarPasswd(String str, String str2) throws RemoteException {
        }

        public int connectToCar(String str) throws RemoteException {
            fbu a = CarKitService.this.c;
            "connectToCar car=".concat(String.valueOf(str));
            a.d = 3;
            a.a();
            return 0;
        }

        public void disConnectToCar(String str) throws RemoteException {
            fbu a = CarKitService.this.c;
            "disConnectToCar:".concat(String.valueOf(str));
            a.s = false;
            if (a.f != null) {
                a.f.a((fcu) new fbz());
            }
            a.f();
            a.e();
        }

        public int registerComponent(long j, IServiceStatusCallBack iServiceStatusCallBack) throws RemoteException {
            return CarKitService.this.c.a(j, iServiceStatusCallBack);
        }

        public int sendMessage(long j, long j2, int i, String str) throws RemoteException {
            c cVar = new c(j, j2, i, str);
            CarKitService.this.b.obtainMessage(4097, cVar).sendToTarget();
            return 0;
        }

        public void unRegisterComponent(long j) throws RemoteException {
            fbu a = CarKitService.this.c;
            "unRegisterComponent: ".concat(String.valueOf(j));
            a.a.remove(Long.valueOf(j));
            a.c.remove(Long.valueOf(j));
            a.b((Set<Long>) a.c);
            new StringBuilder("current local component= ").append(a.c);
            if (a.f != null) {
                a.f.a((fcu) new fcq(j));
            }
        }

        public String getConnectCar() throws RemoteException {
            fbu a = CarKitService.this.c;
            if (a.d == 6) {
                return a.g;
            }
            return null;
        }

        public void cancelTransfer(TransferInfo transferInfo) throws RemoteException {
            CarKitService.this.b.obtainMessage(4101, transferInfo).sendToTarget();
        }

        public void sendSmsMessage(String str, String str2, String str3, String str4) throws RemoteException {
            CarKitService.this.b.obtainMessage(4098, new d(str, str2, str3, str4)).sendToTarget();
        }

        public void sendWeiXinMessage(String str, String str2, String str3) throws RemoteException {
            fbu a = CarKitService.this.c;
            "sendWeiXinMessage: ".concat(String.valueOf(str));
            if (a.f != null) {
                a.f.a((fcu) new fco(str, str2, str3));
            }
        }

        public void setPhoneName(String str) throws RemoteException {
            fbu a = CarKitService.this.c;
            a.l.a = str;
            Editor edit = a.r.getSharedPreferences("phoneinfo", 0).edit();
            edit.putString("phoneName", str);
            edit.commit();
        }

        public void test() throws RemoteException {
            CarKitService.this.c;
        }

        public void acceptFile(int i, String str, int i2, long j, boolean z) throws RemoteException {
            CarKitService.this.b.obtainMessage(4099, new a(i, str, i2)).sendToTarget();
        }

        public int sendFile(long j, long j2, String str, String str2) throws RemoteException {
            b bVar = new b(j, j2, str, str2);
            CarKitService.this.b.obtainMessage(4100, bVar).sendToTarget();
            return 0;
        }

        public void accountLogin(String str, String str2) throws RemoteException {
            CarKitService.a(CarKitService.this.getApplicationContext());
            fbu a = CarKitService.this.c;
            a.p = str;
            a.a(a.p, str2);
            if (a.f != null) {
                a.f.a(true, a.p);
            }
        }

        public void accountLogout() throws RemoteException {
            fbu a = CarKitService.this.c;
            a.p = null;
            a.a(a.p, (String) null);
            if (a.f != null) {
                a.f.a(false, "");
            }
        }

        public void startDiscovery() throws RemoteException {
            CarKitService.this.c;
        }

        public void stopDiscovery() throws RemoteException {
            CarKitService.this.c;
        }
    };
    /* access modifiers changed from: private */
    public Handler b = new Handler() {
        public final void handleMessage(Message message) {
            fca fca;
            switch (message.what) {
                case 4097:
                    c cVar = (c) message.obj;
                    fbu a2 = CarKitService.this.c;
                    long j = cVar.a;
                    long j2 = cVar.b;
                    int i = cVar.d;
                    String str = cVar.c;
                    StringBuilder sb = new StringBuilder("sendMessage: ");
                    sb.append(j);
                    sb.append(" dst:");
                    sb.append(j2);
                    if (a2.d == 6) {
                        if (a2.a.get(Long.valueOf(j)) == null) {
                            "local not register this component=".concat(String.valueOf(j));
                        } else if (!a2.b.contains(Long.valueOf(j2))) {
                            "peer not register this component=".concat(String.valueOf(j2));
                        }
                        if (a2.f != null) {
                            fbv fbv = a2.f;
                            fcr fcr = new fcr(j, j2, i, str);
                            fbv.a((fcu) fcr);
                        }
                    }
                    return;
                case 4098:
                    d dVar = (d) message.obj;
                    fbu a3 = CarKitService.this.c;
                    String str2 = dVar.a;
                    String str3 = dVar.b;
                    String str4 = dVar.c;
                    String str5 = dVar.d;
                    "sendSmsMessage: ".concat(String.valueOf(str2));
                    if (a3.f != null) {
                        a3.f.a((fcu) new fcn(str2, str3, str4, str5));
                    }
                    return;
                case 4099:
                    a aVar = (a) message.obj;
                    fbu a4 = CarKitService.this.c;
                    int i2 = aVar.a;
                    String str6 = aVar.b;
                    int i3 = aVar.c;
                    fcd fcd = a4.m;
                    StringBuilder sb2 = new StringBuilder("createFileReceiver transferId=");
                    sb2.append(i2);
                    sb2.append(" path=");
                    sb2.append(str6);
                    fcc remove = fcd.b.remove(Integer.valueOf(i2));
                    if (remove != null) {
                        if (str6 != null) {
                            remove.g = str6;
                        }
                        remove.m = false;
                        remove.l = 0;
                        fca = new fca(remove, fcd.f, fcd);
                        fcd.d.put(Integer.valueOf(i2), fca);
                        new StringBuilder("mFileReceiver.size()=").append(fcd.d.size());
                        new Thread(fca).start();
                    } else {
                        fca = null;
                    }
                    if (!(fca == null || a4.d != 6 || a4.f == null)) {
                        a4.f.a((fcu) new fbp(fca.a.j, a4.e.a(), fca.b, i3));
                        break;
                    }
                case 4100:
                    b bVar = (b) message.obj;
                    CarKitService.this.c.a(bVar.a, bVar.b, bVar.c, bVar.d);
                    return;
                case 4101:
                    TransferInfo transferInfo = (TransferInfo) message.obj;
                    fbu a5 = CarKitService.this.c;
                    new StringBuilder("cancelTransfer: ").append(transferInfo.i);
                    a5.m.c(transferInfo.i, transferInfo.l);
                    if (a5.f != null) {
                        if (transferInfo.d) {
                            a5.f.a(transferInfo.i, transferInfo.d, transferInfo.l);
                        } else {
                            a5.f.a(transferInfo.j, transferInfo.d, transferInfo.l);
                        }
                    }
                    fcd fcd2 = a5.m;
                    int i4 = transferInfo.i;
                    fcb fcb = fcd2.c.get(Integer.valueOf(i4));
                    if (fcb != null) {
                        fcb.a();
                    }
                    fca fca2 = fcd2.d.get(Integer.valueOf(i4));
                    if (fca2 != null) {
                        fca2.a();
                    }
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public fbu c;

    static class a {
        int a;
        String b;
        int c;
        long d = 0;
        boolean e = false;

        public a(int i, String str, int i2) {
            this.a = i;
            this.b = str;
            this.c = i2;
        }
    }

    static class b {
        long a;
        long b;
        String c;
        String d;

        public b(long j, long j2, String str, String str2) {
            this.a = j;
            this.b = j2;
            this.c = str;
            this.d = str2;
        }
    }

    static class c {
        long a;
        long b;
        String c;
        int d;

        public c(long j, long j2, int i, String str) {
            this.a = j;
            this.b = j2;
            this.d = i;
            this.c = str;
        }
    }

    static class d {
        String a;
        String b;
        String c;
        String d;

        public d(String str, String str2, String str3, String str4) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
        }
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public IBinder onBind(Intent intent) {
        return this.a;
    }

    public void onCreate() {
        super.onCreate();
        new StringBuilder("onCreate thread id= ").append(Process.myPid());
        this.c = new fbu();
        fbu fbu = this.c;
        Context applicationContext = getApplicationContext();
        fbu.r = applicationContext;
        fbu.e = new fcs(applicationContext, fbu.w);
        fbu.h = "000000000";
        fbu.l = new fby(fbu.r.getSharedPreferences("phoneinfo", 0).getString("phoneName", null), "1.0", fbu.d());
        fbu.m = new fcd(fbu);
        SharedPreferences sharedPreferences = fbu.r.getSharedPreferences("account_status", 0);
        boolean z = sharedPreferences.getBoolean("islogin", false);
        if (z) {
            fbu.p = sharedPreferences.getString(NetConstant.KEY_MONEY_ACCOUNT, null);
            fbu.q = sharedPreferences.getString("token", null);
        } else {
            fbu.p = null;
            fbu.q = null;
        }
        StringBuilder sb = new StringBuilder("mAccount=");
        sb.append(fbu.p);
        sb.append("#isLogin=");
        sb.append(z);
    }

    public void onDestroy() {
        new StringBuilder("onDestroy thread id= ").append(Process.myPid());
        super.onDestroy();
        this.b.removeCallbacksAndMessages(null);
        if (this.c != null) {
            fbu fbu = this.c;
            fcs fcs = fbu.e;
            fcs.b.unregisterReceiver(fcs.d);
            fbu.e = null;
            fbu.s = false;
            fbu.a.clear();
            fbu.e();
            fbu.m = null;
            fbu.w = null;
            this.c = null;
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, 1, i2);
    }

    static /* synthetic */ String a(Context context) {
        String nameForUid = context.getPackageManager().getNameForUid(Binder.getCallingUid());
        "calling app is ".concat(String.valueOf(nameForUid));
        return nameForUid;
    }
}
