package defpackage;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import com.amap.bundle.drivecommon.inter.NetConstant;
import com.autonavi.widget.ui.BalloonLayout;
import com.yunos.carkitsdk.ConnectionStatusInfo;
import com.yunos.carkitsdk.IServiceStatusCallBack;
import com.yunos.carkitsdk.TransferInfo;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/* renamed from: fbu reason: default package */
/* compiled from: CarKitHandler */
public final class fbu implements fbs, fce {
    private int A = 30000;
    public TreeMap<Long, IServiceStatusCallBack> a = new TreeMap<>();
    public TreeSet<Long> b = new TreeSet<>();
    public TreeSet<Long> c = new TreeSet<>();
    public int d = 1;
    public fcs e;
    public fbv f;
    public String g;
    public String h;
    String i;
    boolean j;
    boolean k;
    public fby l;
    public fcd m;
    String n;
    String o;
    public String p = "davidwu";
    public String q;
    public Context r;
    public boolean s = false;
    boolean t = false;
    BluetoothDevice u;
    boolean v = false;
    public Handler w = new Handler() {
        public final void handleMessage(Message message) {
            String str;
            Message message2 = message;
            new StringBuilder("message what = ").append(message2.what);
            boolean z = true;
            switch (message2.what) {
                case 4096:
                    Object obj = message2.obj;
                    return;
                case 4097:
                    fbu fbu = fbu.this;
                    fcu fcu = (fcu) message2.obj;
                    new StringBuilder("handleCarMsg=").append(fcu.T);
                    fbu.c();
                    int i = fcu.T;
                    if (i != 100) {
                        switch (i) {
                            case 2:
                                fbu.a((fbx) fcu);
                                return;
                            case 3:
                                if (fbu.f != null) {
                                    fbu.f();
                                    fbu.f.a();
                                    fbu.f = null;
                                    if (fbu.d != 1) {
                                        fbu.d = 3;
                                    }
                                    fbu.g = null;
                                }
                                return;
                            case 4:
                                fcj fcj = (fcj) fcu;
                                new StringBuilder("handleRegisterMsg ").append(fcj.a);
                                fbu.b.addAll(fcj.a);
                                fbu.a((Set<Long>) fbu.b);
                                new StringBuilder("current peer component= ").append(fbu.b);
                                return;
                            case 5:
                                fcq fcq = (fcq) fcu;
                                new StringBuilder("handleUnRegisterMsg ").append(fcq.a);
                                fbu.b.remove(Long.valueOf(fcq.a));
                                fbu.a((Set<Long>) fbu.b);
                                new StringBuilder("current peer component= ").append(fbu.b);
                                return;
                            default:
                                switch (i) {
                                    case 7:
                                        fcl fcl = (fcl) fcu;
                                        StringBuilder sb = new StringBuilder("/sdcard/test/received/");
                                        sb.append(fcl.a);
                                        String sb2 = sb.toString();
                                        fcd fcd = fbu.m;
                                        long j = fcl.b;
                                        long j2 = fcl.c;
                                        long j3 = fcl.e;
                                        String str2 = fbu.g;
                                        int i2 = fcl.d;
                                        StringBuilder sb3 = new StringBuilder("getNextTransferIdForReceiver src=");
                                        sb3.append(j);
                                        sb3.append(" dst=");
                                        sb3.append(j2);
                                        sb3.append(" path=");
                                        sb3.append(sb2);
                                        sb3.append(" peerName=");
                                        sb3.append(str2);
                                        sb3.append(" peerTransferId=");
                                        sb3.append(i2);
                                        fcd.h++;
                                        fcc fcc = new fcc();
                                        fcc.c = j;
                                        fcc.d = j2;
                                        fcc.g = sb2;
                                        fcc.h = j3;
                                        fcc.a = str2;
                                        fcc.i = fcd.h;
                                        fcc.b = false;
                                        fcc.j = i2;
                                        fcd.b.put(Integer.valueOf(fcd.h), fcc);
                                        int i3 = fcd.h;
                                        TransferInfo transferInfo = new TransferInfo(fbu.g, sb2, false, fcl.e, fcl.b, fcl.c, i3, fcl.f);
                                        transferInfo.h = 1;
                                        fbu.a(transferInfo);
                                        return;
                                    case 8:
                                        fbp fbp = (fbp) fcu;
                                        fcd fcd2 = fbu.m;
                                        int i4 = fbp.a;
                                        String str3 = fbp.c;
                                        int i5 = fbp.b;
                                        long j4 = fbp.e;
                                        StringBuilder sb4 = new StringBuilder("createFileSender transferId=");
                                        sb4.append(i4);
                                        sb4.append(" ip=");
                                        sb4.append(str3);
                                        sb4.append(" port=");
                                        sb4.append(i5);
                                        fcc remove = fcd2.a.remove(Integer.valueOf(i4));
                                        if (remove != null) {
                                            remove.e = str3;
                                            remove.f = i5;
                                            remove.l = j4;
                                            remove.m = false;
                                            fcb fcb = new fcb(remove, fcd2.f, fcd2);
                                            if (fcd2.g > 0) {
                                                fcd2.e.add(fcb);
                                                return;
                                            }
                                            fcd2.c.put(Integer.valueOf(remove.i), fcb);
                                            new StringBuilder("mFileSender.size()=").append(fcd2.c.size());
                                            new Thread(fcb).start();
                                            fcd2.g++;
                                            new StringBuilder("createFileSender concurrent=").append(fcd2.g);
                                        }
                                        return;
                                    case 9:
                                        if (fbu.f != null) {
                                            fbu.f.a((fcu) new fcf());
                                        }
                                        fbu.c();
                                        return;
                                    case 10:
                                        fbt fbt = (fbt) fcu;
                                        new StringBuilder("handleCancelFileMsg transferId=").append(fbt.a);
                                        if (fbt.b) {
                                            fbu.m.a(fbt.a, fbt.c);
                                            return;
                                        } else {
                                            fbu.m.b(fbt.a, fbt.c);
                                            return;
                                        }
                                    case 11:
                                        fcp fcp = (fcp) fcu;
                                        new StringBuilder("handleSetCarOwnerMsg owner= ").append(fcp.a);
                                        if (fbu.j == fcp.a && fbu.k == fcp.c) {
                                            z = false;
                                        }
                                        if (z) {
                                            fbu.j = fcp.a;
                                            fbu.k = fcp.c;
                                            fbu.g();
                                        }
                                        return;
                                }
                        }
                    } else {
                        fbu.a((fcr) fcu);
                    }
                    return;
                case 4098:
                    fbu fbu2 = fbu.this;
                    fbu2.d = 5;
                    if (fbu2.f != null) {
                        fbv fbv = fbu2.f;
                        String str4 = fbu2.p;
                        String str5 = fbu2.h;
                        String str6 = Build.BRAND;
                        String str7 = fbv.a.a;
                        if (str7 == null || str7.equals("")) {
                            "set name to bluetooth name=".concat(String.valueOf(str6));
                            str = str6;
                        } else {
                            str = str7;
                        }
                        if (str4 == null) {
                            fbw fbw = new fbw(str, fbv.a.b, fbv.a.c, false, "", str5, str6);
                            fbv.a((fcu) fbw);
                            return;
                        }
                        fbw fbw2 = new fbw(str, fbv.a.b, fbv.a.c, true, str4, str5, str6);
                        fbv.a((fcu) fbw2);
                    }
                    return;
                case 4099:
                    fbu.this.h();
                    return;
                case 4102:
                    fbu fbu3 = fbu.this;
                    String str8 = (String) message2.obj;
                    "handleWifiConnected wifi=".concat(String.valueOf(str8));
                    if (!fbu3.v) {
                        fbu3.v = true;
                        fbu3.n = str8;
                        fbu3.d = 3;
                        fbu3.a();
                    }
                    return;
                case 4103:
                    "handleBluetoothConnected: ".concat(String.valueOf((String) message2.obj));
                    return;
                case 4104:
                    fbu fbu4 = fbu.this;
                    "handleBluetoothDisconnected: ".concat(String.valueOf((String) message2.obj));
                    fbu4.o = null;
                    fbu4.i = null;
                    if (fbu4.n == null) {
                        fbu4.d = 1;
                    }
                    if (fbu4.t) {
                        fbu4.e();
                    }
                    return;
                case 4105:
                    fbu.this.a((fcr) message2.obj);
                    return;
                case 4112:
                    fbu.this.h();
                    return;
                case 4113:
                    fbu.this.i();
                    return;
                case 4114:
                    fbu fbu5 = fbu.this;
                    fbu5.n = null;
                    fbu5.d = 1;
                    return;
                case 4115:
                    fbu.b(fbu.this);
                    return;
                case 4117:
                    fbu.this.a();
                    return;
                case 4118:
                    fbu fbu6 = fbu.this;
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) message2.obj;
                    fbu6.u = bluetoothDevice;
                    fbu6.o = bluetoothDevice.getName();
                    fbu6.i = bluetoothDevice.getAddress();
                    fbu6.b();
                    fbu6.w.sendMessageDelayed(fbu6.w.obtainMessage(4119), BalloonLayout.DEFAULT_DISPLAY_DURATION);
                    fbu6.w.sendMessageDelayed(fbu6.w.obtainMessage(4119), 6000);
                    break;
                case 4119:
                    fbu.this.b();
                    return;
            }
        }
    };
    private long x;
    private Map<String, fby> y;
    private int z = 10;

    public final String d() {
        try {
            String deviceId = ((TelephonyManager) this.r.getSystemService("phone")).getDeviceId();
            if (deviceId == null) {
                deviceId = "";
            }
            return deviceId;
        } catch (Exception unused) {
            return "";
        }
    }

    public final void a(long j2, long j3, String str, String str2) {
        long j4 = j2;
        long j5 = j3;
        String str3 = str;
        "sendFile ".concat(String.valueOf(str));
        if (this.d == 6 && this.f != null) {
            fcd fcd = this.m;
            Iterator<fcb> it = fcd.c.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    Iterator<fcc> it2 = fcd.a.values().iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (str3.equals(it2.next().g)) {
                                "there is already a to be send file ".concat(String.valueOf(str));
                                break;
                            }
                        } else {
                            Iterator<fcb> it3 = fcd.e.iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    if (str3.equals(it3.next().a.b)) {
                                        "there is already a to be start file ".concat(String.valueOf(str));
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                } else if (str3.equals(it.next().a.b)) {
                    "there is already a transfer of same file ".concat(String.valueOf(str));
                    break;
                } else {
                    j4 = j2;
                    j5 = j3;
                }
            }
            File file = new File(str3);
            fcd fcd2 = this.m;
            String str4 = this.g;
            StringBuilder sb = new StringBuilder("getNextTransferIdForSender src=");
            sb.append(j4);
            sb.append(" dst=");
            sb.append(j5);
            sb.append(" path=");
            sb.append(str3);
            sb.append(" peerName");
            sb.append(str4);
            fcd2.h++;
            fcc fcc = new fcc();
            fcc.c = j4;
            fcc.d = j5;
            fcc.g = str3;
            fcc.a = str4;
            fcc.b = true;
            fcc.i = fcd2.h;
            fcc.k = str2;
            fcd2.a.put(Integer.valueOf(fcd2.h), fcc);
            int i2 = fcd2.h;
            fbv fbv = this.f;
            long j6 = j4;
            fcl fcl = r1;
            fcl fcl2 = new fcl(j6, j5, i2, file.getName(), file.length(), str2);
            fbv.a((fcu) fcl);
            String str5 = str3;
            TransferInfo transferInfo = new TransferInfo(this.g, str5, true, file.length(), j2, j3, i2, str2);
            transferInfo.h = 1;
            d(transferInfo);
        }
    }

    public final int a(long j2, IServiceStatusCallBack iServiceStatusCallBack) {
        int i2;
        "registerComponent: ".concat(String.valueOf(j2));
        if (this.a.get(Long.valueOf(j2)) == null) {
            this.c.add(Long.valueOf(j2));
            i2 = 0;
            this.a.put(Long.valueOf(j2), iServiceStatusCallBack);
        } else {
            "already registerComponent=".concat(String.valueOf(j2));
            IServiceStatusCallBack iServiceStatusCallBack2 = this.a.get(Long.valueOf(j2));
            if (iServiceStatusCallBack2 != null) {
                try {
                    "already registered component is replace, notify it com=".concat(String.valueOf(j2));
                    iServiceStatusCallBack2.onUnregisteredByOther();
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
            this.a.remove(Long.valueOf(j2));
            this.a.put(Long.valueOf(j2), iServiceStatusCallBack);
            i2 = 2;
        }
        b((Set<Long>) this.c);
        if (this.d == 6 && this.g != null) {
            new StringBuilder("notifyCurrentStatus mConnectedCar=").append(this.g);
            ConnectionStatusInfo connectionStatusInfo = new ConnectionStatusInfo(this.g, 1, this.x, this.j, this.k);
            try {
                iServiceStatusCallBack.onConnectionStatusNotify(connectionStatusInfo);
            } catch (RemoteException e3) {
                e3.printStackTrace();
            }
        }
        if (this.y != null && this.y.size() > 0) {
            try {
                iServiceStatusCallBack.onFoundCar(new ArrayList(this.y.keySet()));
            } catch (RemoteException e4) {
                e4.printStackTrace();
            }
        }
        new StringBuilder("current local component= ").append(this.c);
        if (this.f != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Long.valueOf(j2));
            this.f.a((List<Long>) arrayList);
        }
        return i2;
    }

    public final void a() {
        new StringBuilder("wifiIsConnected=").append(this.v);
        if (this.d >= 3) {
            if (this.d == 6) {
                new StringBuilder("already connected to ").append(this.g);
            } else if (this.d <= 3) {
                if (this.n != null && fct.a(this.n)) {
                    new StringBuilder("try to use tcp to create protocol channel wifiName=").append(this.n);
                    if (this.f != null) {
                        this.s = true;
                        e();
                    } else if (!k().equals("0.0.0.1")) {
                        this.d = 4;
                        this.g = this.n;
                        this.t = false;
                        String a2 = this.e.a();
                        this.l.d = b(a2);
                        this.f = new fbv(k(), this.w, this.l);
                        new Thread(this.f).start();
                    } else {
                        new StringBuilder("ip is invalid:").append(this.e.a());
                        i();
                    }
                } else if (this.o != null) {
                    this.g = this.o;
                    this.t = true;
                } else {
                    new StringBuilder("mConnectedBluetoothName=").append(this.o);
                    new StringBuilder("mConnectedWifiName=").append(this.n);
                    new StringBuilder("mConnectedCar=").append(this.g);
                    new StringBuilder("mState=").append(this.d);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(fbx fbx) {
        if (this.f != null) {
            this.j = fbx.d;
            this.d = 6;
            this.g = fbx.a;
            this.s = true;
            this.z = 10;
            this.k = fbx.f;
            g();
            ArrayList arrayList = new ArrayList();
            Iterator<Long> it = this.c.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
            this.f.a((List<Long>) arrayList);
            if (fbx.g > 0) {
                this.A = fbx.g + 5000;
            }
            new StringBuilder("keep alive interval=").append(this.A);
            c();
        }
    }

    /* access modifiers changed from: private */
    public void h() {
        f();
        e();
    }

    /* access modifiers changed from: private */
    public void i() {
        this.n = null;
        this.v = false;
        if (this.o == null) {
            this.d = 1;
        }
        if (!this.t) {
            e();
            f();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        if (this.d == 6 && this.f != null && this.i != null) {
            this.f.a((fcu) new fcm(this.i));
        }
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        this.w.removeMessages(4112);
        this.w.sendMessageDelayed(this.w.obtainMessage(4112), (long) this.A);
    }

    public final void a(String str, String str2) {
        Editor edit = this.r.getSharedPreferences("account_status", 0).edit();
        if (str == null) {
            edit.putBoolean("islogin", false);
        } else {
            edit.putString(NetConstant.KEY_MONEY_ACCOUNT, str);
            edit.putString("token", str2);
            edit.putBoolean("islogin", true);
        }
        edit.commit();
    }

    public final void e() {
        new StringBuilder("resetState()#mState=").append(this.d);
        this.m.b();
        if (this.d != 1) {
            this.d = 3;
        }
        this.k = false;
        this.j = false;
        if (this.f != null) {
            this.f.a();
        }
        this.f = null;
        this.w.removeMessages(4096);
        this.w.removeMessages(4097);
        this.w.removeMessages(4098);
        this.w.removeMessages(4099);
        this.w.removeMessages(4102);
        this.w.removeMessages(4113);
        this.w.removeMessages(4114);
        this.w.removeMessages(4115);
        this.w.removeMessages(4103);
        this.w.removeMessages(4104);
        this.w.removeMessages(4105);
        this.w.removeMessages(4112);
        this.w.removeMessages(4116);
        this.w.removeMessages(4117);
        if (this.d != 1) {
            j();
        }
    }

    private void j() {
        StringBuilder sb = new StringBuilder("doReconnect()#mDoReconnect=");
        sb.append(this.s);
        sb.append("#mRetryCount=");
        sb.append(this.z);
        if (this.s) {
            int i2 = this.z;
            this.z = i2 - 1;
            if (i2 > 0) {
                this.w.sendMessageDelayed(this.w.obtainMessage(4117), 1000);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Set<Long> set) {
        ArrayList arrayList = new ArrayList();
        for (Long longValue : set) {
            arrayList.add(String.valueOf(longValue.longValue()));
        }
        for (IServiceStatusCallBack onRemoteComponents : this.a.values()) {
            try {
                onRemoteComponents.onRemoteComponents(arrayList);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final void b(Set<Long> set) {
        ArrayList arrayList = new ArrayList();
        for (Long longValue : set) {
            arrayList.add(String.valueOf(longValue.longValue()));
        }
        for (IServiceStatusCallBack onRegisteredComponents : this.a.values()) {
            try {
                onRegisteredComponents.onRegisteredComponents(arrayList);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final void f() {
        ConnectionStatusInfo connectionStatusInfo = new ConnectionStatusInfo(this.g, 2, this.x, this.j, this.k);
        for (IServiceStatusCallBack onConnectionStatusNotify : this.a.values()) {
            try {
                onConnectionStatusNotify.onConnectionStatusNotify(connectionStatusInfo);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void g() {
        new StringBuilder("mConnectedCar= ").append(this.g);
        if (this.g != null) {
            ConnectionStatusInfo connectionStatusInfo = new ConnectionStatusInfo(this.g, 1, this.x, this.j, this.k);
            new StringBuilder("current local component= ").append(this.a);
            for (IServiceStatusCallBack onConnectionStatusNotify : this.a.values()) {
                try {
                    onConnectionStatusNotify.onConnectionStatusNotify(connectionStatusInfo);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private void d(TransferInfo transferInfo) {
        if (this.g != null) {
            try {
                IServiceStatusCallBack iServiceStatusCallBack = this.a.get(Long.valueOf(transferInfo.f));
                if (iServiceStatusCallBack != null) {
                    iServiceStatusCallBack.onSendFileNotify(transferInfo);
                } else {
                    new StringBuilder("sender not find the src com=").append(transferInfo.f);
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(TransferInfo transferInfo) {
        if (this.g != null) {
            try {
                IServiceStatusCallBack iServiceStatusCallBack = this.a.get(Long.valueOf(transferInfo.g));
                if (iServiceStatusCallBack != null) {
                    iServiceStatusCallBack.onReceiveFileNotify(transferInfo);
                } else {
                    new StringBuilder("receiver not find the dst com=").append(transferInfo.g);
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    private String k() {
        String a2 = this.e.a();
        StringBuilder sb = new StringBuilder(String.valueOf(a2.substring(0, a2.lastIndexOf(46))));
        sb.append(".1");
        return sb.toString();
    }

    private static String b(String str) {
        String str2;
        try {
            "ipaddr=".concat(String.valueOf(str));
            new StringBuilder("interface name=").append(InetAddress.getByName(str));
            str2 = a(NetworkInterface.getByInetAddress(InetAddress.getByName(str)).getHardwareAddress());
        } catch (Exception e2) {
            e2.printStackTrace();
            str2 = "";
        }
        "wifi mac=".concat(String.valueOf(str2));
        return str2;
    }

    private static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length);
        int length = bArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            String hexString = Integer.toHexString(bArr[i2] & 255);
            if (hexString.length() == 1) {
                stringBuffer.append("0");
                stringBuffer.append(hexString);
            } else {
                stringBuffer.append(hexString);
            }
            if (i2 < length - 1) {
                stringBuffer.append(":");
            }
        }
        return String.valueOf(stringBuffer);
    }

    public final void a(String str) {
        this.w.obtainMessage(4104, str).sendToTarget();
    }

    public final void b(TransferInfo transferInfo) {
        d(transferInfo);
    }

    public final void c(TransferInfo transferInfo) {
        a(transferInfo);
    }

    public final void a(BluetoothDevice bluetoothDevice) {
        this.w.obtainMessage(4118, bluetoothDevice).sendToTarget();
    }

    /* access modifiers changed from: 0000 */
    public final void a(fcr fcr) {
        StringBuilder sb = new StringBuilder("notifyUserMsg src=");
        sb.append(fcr.a);
        sb.append(" dst=");
        sb.append(fcr.b);
        sb.append(" content=");
        sb.append(fcr.c);
        if (this.g != null) {
            try {
                IServiceStatusCallBack iServiceStatusCallBack = this.a.get(Long.valueOf(fcr.b));
                if (iServiceStatusCallBack != null) {
                    iServiceStatusCallBack.onReceiveMsgNotify(fcr.a, fcr.d, fcr.c);
                } else {
                    new StringBuilder("receive msg, not find the dst com=").append(fcr.b);
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    static /* synthetic */ void b(fbu fbu) {
        fbu.f();
        fbu.e();
    }
}
