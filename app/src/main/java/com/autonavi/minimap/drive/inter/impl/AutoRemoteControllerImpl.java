package com.autonavi.minimap.drive.inter.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.amap.bundle.drive.ajx.module.ModuleHeadunit;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.tripgroup.api.AlinkConnectionListener;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.tripgroup.api.IAutoRemoteController.ConnectionType;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.link.LinkSDK;
import com.autonavi.link.connect.listener.Connection.OnBtStateChangeListener;
import com.autonavi.link.connect.listener.Connection.OnDiscoverHostListener;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientHelper;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.drive.auto.AutoConnectionTypeEnum;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@BundleInterface(IAutoRemoteController.class)
public class AutoRemoteControllerImpl implements IAutoRemoteController {
    public String a = "127.0.0.1";
    /* access modifiers changed from: private */
    public boolean b = false;
    private boolean c = false;
    /* access modifiers changed from: private */
    public DiscoverInfo d;
    /* access modifiers changed from: private */
    public String e = "";
    /* access modifiers changed from: private */
    public String f;
    /* access modifiers changed from: private */
    public boolean g = false;
    /* access modifiers changed from: private */
    public boolean h = false;
    /* access modifiers changed from: private */
    public String i;
    /* access modifiers changed from: private */
    public WeakReference<afw> j;
    /* access modifiers changed from: private */
    public List<aga> k;
    /* access modifiers changed from: private */
    public ArrayList<AlinkConnectionListener> l = new ArrayList<>();
    private BroadcastReceiver m;
    private fbl n;
    private boolean o;
    private afv p;
    /* access modifiers changed from: private */
    public final OnDiscoverHostListener q = new OnDiscoverHostListener() {
        public final void onDiscoverHost(DiscoverInfo discoverInfo) {
            fhw.a();
            AutoRemoteControllerImpl autoRemoteControllerImpl = AutoRemoteControllerImpl.this;
            StringBuilder sb = new StringBuilder();
            sb.append(discoverInfo.IP);
            sb.append(":");
            sb.append(discoverInfo.httpPort);
            autoRemoteControllerImpl.a = sb.toString();
            AutoRemoteControllerImpl.this.e = discoverInfo.sdkVersion;
            AutoRemoteControllerImpl.this.d = discoverInfo;
            AutoRemoteControllerImpl.a(AutoRemoteControllerImpl.this, AutoRemoteControllerImpl.this.a, ConnectionType.WIFI);
            if (AutoRemoteControllerImpl.this.isNewAmapSDK()) {
                agb.a((String) "amap_wifi_20");
            } else {
                agb.a((String) "amap_wifi");
            }
            if (!(AutoRemoteControllerImpl.this.j == null || AutoRemoteControllerImpl.this.j.get() == null)) {
                ((afw) AutoRemoteControllerImpl.this.j.get()).a(true);
            }
            Iterator it = AutoRemoteControllerImpl.this.l.iterator();
            while (it.hasNext()) {
                AlinkConnectionListener alinkConnectionListener = (AlinkConnectionListener) it.next();
                if (alinkConnectionListener != null) {
                    alinkConnectionListener.onConnected();
                }
            }
        }

        public final void onFindDevice(List<DiscoverInfo> list) {
            if (list != null && !list.isEmpty()) {
                DiscoverInfo discoverInfo = list.get(0);
                if (!TextUtils.isEmpty(discoverInfo.IP)) {
                    LinkSDK.getInstance().getWifiInstance().startLink(discoverInfo.IP);
                }
            }
        }

        public final void onDisconnect() {
            AutoRemoteControllerImpl.this.a = "127.0.0.1";
            AutoRemoteControllerImpl.this.d();
            Iterator it = AutoRemoteControllerImpl.this.l.iterator();
            while (it.hasNext()) {
                AlinkConnectionListener alinkConnectionListener = (AlinkConnectionListener) it.next();
                if (alinkConnectionListener != null) {
                    alinkConnectionListener.onDisconnected();
                }
            }
            if (!(AutoRemoteControllerImpl.this.j == null || AutoRemoteControllerImpl.this.j.get() == null)) {
                ((afw) AutoRemoteControllerImpl.this.j.get()).a(false);
            }
            if (AutoRemoteControllerImpl.this.k != null) {
                for (aga b : AutoRemoteControllerImpl.this.k) {
                    b.b(ConnectionType.WIFI);
                }
            }
            AutoRemoteControllerImpl.this.d = null;
            try {
                LinkSDK.getInstance().getWifiInstance().startDiscoverHost(AutoRemoteControllerImpl.this.q);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    };
    private OnBtStateChangeListener r = new OnBtStateChangeListener() {
        public final void onStateChange(int i, DiscoverInfo discoverInfo) {
            fhw.a();
            if (i != -5) {
                boolean z = false;
                if (i != 1) {
                    switch (i) {
                        case -2:
                            String d = agb.d();
                            if (!AutoRemoteControllerImpl.this.isNewAmapSDK() ? !"amap_bluetooth".equals(d) : !"amap_bluetooth_20".equals(d)) {
                                z = true;
                            }
                            ToastHelper.showToast("连接失败..");
                            fhw.a();
                            if (!z) {
                                AutoRemoteControllerImpl.this.d();
                                return;
                            }
                            break;
                        case -1:
                            AutoRemoteControllerImpl.this.d();
                            if (AutoRemoteControllerImpl.this.k != null) {
                                for (aga b : AutoRemoteControllerImpl.this.k) {
                                    b.b(ConnectionType.BLUETOOTH);
                                }
                                return;
                            }
                            break;
                    }
                } else {
                    AutoRemoteControllerImpl.this.e = discoverInfo.sdkVersion;
                    AutoRemoteControllerImpl.this.b = true;
                    String d2 = agb.d();
                    if ("amap_bluetooth".equals(d2) || "amap_bluetooth_20".equals(d2)) {
                        AutoRemoteControllerImpl.this.b = false;
                    } else {
                        AutoRemoteControllerImpl.this.b = true;
                    }
                    if (AutoRemoteControllerImpl.this.isNewAmapSDK()) {
                        agb.a((String) "amap_bluetooth_20");
                    } else {
                        agb.a((String) "amap_bluetooth");
                    }
                    AutoRemoteControllerImpl.this.stopALinkWifi();
                    AutoRemoteControllerImpl.f(AutoRemoteControllerImpl.this);
                    AutoRemoteControllerImpl.a(true);
                    if (!TextUtils.isEmpty(AutoRemoteControllerImpl.this.f)) {
                        AutoRemoteControllerImpl.b(AutoRemoteControllerImpl.this.f);
                    }
                    AutoRemoteControllerImpl.a(AutoRemoteControllerImpl.this, "127.0.0.1", ConnectionType.BLUETOOTH);
                    ToastHelper.showToast("连接成功！\n可以在首页使用遥控器了");
                    fhw.a();
                    if (!(AutoRemoteControllerImpl.this.j == null || AutoRemoteControllerImpl.this.j.get() == null)) {
                        ((afw) AutoRemoteControllerImpl.this.j.get()).a();
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putBoolean("firstConnected", AutoRemoteControllerImpl.this.b);
                    pageBundle.putObject("connectionType", AutoRemoteControllerImpl.this.isNewAmapSDK() ? AutoConnectionTypeEnum.AMAP_BLUETOOTH_20 : AutoConnectionTypeEnum.AMAP_BLUETOOTH_10);
                    bid pageContext = AMapPageUtil.getPageContext();
                    if (pageContext != null) {
                        pageContext.startPage((String) "amap.drive.action.alicar.manage", pageBundle);
                    }
                }
            } else {
                AutoRemoteControllerImpl.this.d();
            }
        }
    };

    class BluetoothBroadcastReceiverAgent extends BroadcastReceiver {
        BluetoothBroadcastReceiverAgent() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                switch (intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", 10)) {
                    case 10:
                        if (AutoRemoteControllerImpl.this.g) {
                            fhw.a();
                            new StringBuilder("配对失败: ").append(bluetoothDevice.getName());
                        }
                        AutoRemoteControllerImpl.this.g = false;
                        break;
                    case 11:
                        fhw.a();
                        new StringBuilder("正在配对设备: ").append(bluetoothDevice.getName());
                        return;
                    case 12:
                        if (AutoRemoteControllerImpl.this.g) {
                            fhw.a();
                            new StringBuilder("完成配对: ").append(bluetoothDevice.getName());
                            AutoRemoteControllerImpl.this.doConnectBt(bluetoothDevice.getAddress());
                        }
                        AutoRemoteControllerImpl.this.g = false;
                        return;
                }
                return;
            }
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                if (intExtra != 10) {
                    if (intExtra == 12 && AutoRemoteControllerImpl.this.h) {
                        if (!TextUtils.isEmpty(AutoRemoteControllerImpl.this.i)) {
                            if (AutoRemoteControllerImpl.this.isParied(AutoRemoteControllerImpl.this.i)) {
                                AutoRemoteControllerImpl.this.doConnectBt(AutoRemoteControllerImpl.this.i);
                            } else {
                                AutoRemoteControllerImpl.this.pairDevice(AutoRemoteControllerImpl.this.i);
                            }
                        }
                        AutoRemoteControllerImpl.this.h = false;
                    }
                } else if (AutoRemoteControllerImpl.this.h) {
                    AutoRemoteControllerImpl.this.h = false;
                }
            }
        }
    }

    public void setAutoRemoteViewUpdateListener(afv afv) {
        this.p = afv;
    }

    public void init() {
        this.n = fbl.a(AMapPageUtil.getAppContext());
        LinkSDK.getInstance().init(AMapAppGlobal.getApplication().getApplicationContext());
        LinkSDK.getInstance().getBtInstance().setOnBtStateChangeListener(this.r);
        String d2 = agb.d();
        if ("amap_bluetooth".equals(d2) || "amap_bluetooth_20".equals(d2)) {
            checkNeedStartBluetoothServer();
        }
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        if (this.m == null) {
            this.m = new BluetoothBroadcastReceiverAgent();
        }
        AMapAppGlobal.getApplication().getApplicationContext().registerReceiver(this.m, intentFilter);
        this.c = true;
        if ("amap_wifi".equals(d2) || "amap_wifi_20".equals(d2)) {
            startAlinkWifi(null);
        }
    }

    public void startAlinkWifi(afw afw) {
        if (afw != null) {
            this.j = new WeakReference<>(afw);
        }
        fhw.a();
        System.currentTimeMillis();
        if (b()) {
            try {
                LinkSDK.getInstance().getWifiInstance().startDiscoverHost(this.q);
            } catch (SocketException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void stopALinkWifi() {
        LinkSDK.getInstance().getWifiInstance().stopDiscoverHost();
        d();
    }

    public void release() {
        if (this.m != null) {
            try {
                if (this.c) {
                    AMapAppGlobal.getApplication().getApplicationContext().unregisterReceiver(this.m);
                    this.c = false;
                }
            } catch (Exception unused) {
            }
            this.m = null;
        }
        LinkSDK.getInstance().getBtInstance().stopBt();
        LinkSDK.getInstance().getWifiInstance().stopDiscoverHost();
        LinkSDK.getInstance().release();
        this.g = false;
        this.l.clear();
    }

    public void checkNeedStartBluetoothServer() {
        System.currentTimeMillis();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null && defaultAdapter.isEnabled()) {
            BluetoothAdapter defaultAdapter2 = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter2 != null && defaultAdapter2.getProfileConnectionState(2) == 2) {
                LinkSDK.getInstance().getBtInstance().startBtServer();
                fhw.a();
                return;
            }
            if (new MapSharePreference((String) "AUTO_REMOTE").getBooleanValue("BLUETOOTH_PAIRED", false)) {
                String stringValue = new MapSharePreference((String) "AUTO_REMOTE").getStringValue("LAST_CONNECT_BLUETOOTH", null);
                if (!TextUtils.isEmpty(stringValue) && isParied(stringValue)) {
                    LinkSDK.getInstance().getBtInstance().startBtServer();
                    fhw.a();
                }
            }
        }
    }

    public void doConnectBt(String str) {
        fhw.a();
        String d2 = agb.d();
        if ("amap_wifi".equals(d2) || "amap_wifi_20".equals(d2) || "ali_auto_wifi".equals(d2)) {
            a(false);
        }
        b(str);
        this.f = str;
        LinkSDK.getInstance().getBtInstance().doBtConnect(str);
    }

    public void reconnectBt() {
        if (!TextUtils.isEmpty(this.f)) {
            LinkSDK.getInstance().getBtInstance().doBtConnect(this.f);
        }
    }

    public DiscoverInfo getWifiDiscoverInfo() {
        return this.d;
    }

    private static boolean b() {
        Method[] declaredMethods;
        WifiManager wifiManager = (WifiManager) AMapAppGlobal.getApplication().getApplicationContext().getSystemService("wifi");
        boolean z = false;
        for (Method method : wifiManager.getClass().getDeclaredMethods()) {
            if ("isWifiApEnabled".equals(method.getName())) {
                try {
                    z = ((Boolean) method.invoke(wifiManager, new Object[0])).booleanValue();
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                } catch (IllegalAccessException e3) {
                    e3.printStackTrace();
                } catch (InvocationTargetException e4) {
                    e4.printStackTrace();
                }
            }
        }
        return z;
    }

    public boolean isNewAmapSDK() {
        if (this.e != null && this.e.startsWith(SecureSignatureDefine.SG_KEY_SIGN_VERSION)) {
            try {
                if (this.e.length() <= 3 || Integer.parseInt(this.e.substring(1, 2)) < 2) {
                    return false;
                }
                return true;
            } catch (NumberFormatException unused) {
                return false;
            }
        }
        return false;
    }

    private void c() {
        if (this.p != null) {
            this.p.a();
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        synchronized (this) {
            agb.b(false);
            c();
            this.o = false;
            this.a = "127.0.0.1";
        }
    }

    public synchronized void restoreViewByConnectionState() {
        c();
    }

    public void stopALinkBt() {
        LinkSDK.getInstance().getBtInstance().stopBt();
    }

    /* access modifiers changed from: private */
    public static void a(boolean z) {
        new MapSharePreference((String) "AUTO_REMOTE").putBooleanValue("BLUETOOTH_PAIRED", z);
    }

    /* access modifiers changed from: private */
    public static void b(String str) {
        new MapSharePreference((String) "AUTO_REMOTE").putStringValue("LAST_CONNECT_BLUETOOTH", str);
    }

    public byte[] getBytes(String str, Map<String, String> map) throws IOException {
        return HttpClientHelper.getInstance().getBytes(this.a, str, map);
    }

    public boolean isParied(String str) {
        Object[] array = BluetoothAdapter.getDefaultAdapter().getBondedDevices().toArray();
        for (Object obj : array) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
            if (bluetoothDevice != null && bluetoothDevice.getAddress() != null && bluetoothDevice.getAddress().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean pairDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        ToastHelper.showToast("请求与车机配对...");
        fhw.a();
        BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(str);
        try {
            this.g = true;
            return ((Boolean) remoteDevice.getClass().getMethod("createBond", new Class[0]).invoke(remoteDevice, new Object[0])).booleanValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void promptToEnableBluetoothBeforePairing(String str) {
        this.h = true;
        this.i = str;
        DoNotUseTool.startActivity(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"));
    }

    public byte[] postBytes(String str, Map<String, String> map, byte[] bArr) throws IOException {
        StringBuilder sb = new StringBuilder("linksdk postBytes        Host:");
        sb.append(this.a);
        c(sb.toString());
        c("linksdk postBytes         url:".concat(String.valueOf(str)));
        c("linksdk postBytes queryParams:".concat(String.valueOf(map)));
        StringBuilder sb2 = new StringBuilder("linksdk postBytes      buffer:");
        sb2.append(bArr.toString());
        c(sb2.toString());
        return HttpClientHelper.getInstance().postBytes(this.a, str, map, bArr);
    }

    public void setRemoteControlConnectListener(aga aga) {
        if (this.k == null) {
            this.k = new CopyOnWriteArrayList();
        }
        if (!this.k.contains(aga)) {
            this.k.add(aga);
        }
    }

    public void removeRemoteControlConnectListener(aga aga) {
        if (this.k != null && this.k.contains(aga)) {
            this.k.remove(aga);
        }
    }

    public void addAlinkWifiConnectionListener(AlinkConnectionListener alinkConnectionListener) {
        if (alinkConnectionListener != null && !this.l.contains(alinkConnectionListener)) {
            this.l.add(alinkConnectionListener);
        }
    }

    public void removeAlinkWifiConnectionListener(AlinkConnectionListener alinkConnectionListener) {
        if (alinkConnectionListener != null && this.l.contains(alinkConnectionListener)) {
            this.l.remove(alinkConnectionListener);
        }
    }

    public boolean IsWifiConnected() {
        return LinkSDK.getInstance().getWifiInstance().getIsConnect();
    }

    public boolean IsBtConnected() {
        return LinkSDK.getInstance().getBtInstance().getIsConnect();
    }

    public boolean lowVersionAutoConnected() {
        return this.o;
    }

    public boolean hasBoundToAuto() {
        String d2 = agb.d();
        return d2 != null && !TextUtils.isEmpty(d2.trim());
    }

    private static void c(String str) {
        AMapLog.d(ModuleHeadunit.MODULE_NAME, str);
        if (bno.a) {
            ku.a().c(ModuleHeadunit.MODULE_NAME, str);
        }
    }

    static /* synthetic */ void a(AutoRemoteControllerImpl autoRemoteControllerImpl, String str, ConnectionType connectionType) {
        synchronized (autoRemoteControllerImpl) {
            agb.b(true);
            autoRemoteControllerImpl.c();
            autoRemoteControllerImpl.o = true;
            autoRemoteControllerImpl.a = str;
            if (autoRemoteControllerImpl.k != null) {
                for (aga next : autoRemoteControllerImpl.k) {
                    if (next != null) {
                        next.a(connectionType);
                    }
                }
            }
        }
    }

    static /* synthetic */ void f(AutoRemoteControllerImpl autoRemoteControllerImpl) {
        agb.a(false);
        agb.c(false);
        autoRemoteControllerImpl.n.c.clear();
        autoRemoteControllerImpl.n.d.clear();
        autoRemoteControllerImpl.n.e.clear();
        autoRemoteControllerImpl.n.g();
    }
}
