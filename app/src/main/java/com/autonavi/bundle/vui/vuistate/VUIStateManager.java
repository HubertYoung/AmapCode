package com.autonavi.bundle.vui.vuistate;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.telephony.PhoneStateListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewTreeObserver;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.amap.bundle.drive.api.ICarTruckInfoManager;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.network.util.NetworkReachability.NetworkType;
import com.amap.bundle.network.util.NetworkReachability.a;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.vui.entity.VSysStateResultMap;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.vcs.Constants.VUIStatus;
import com.autonavi.vcs.NativeVcsManager;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class VUIStateManager extends PhoneStateListener implements a, b, a, bfc, a, lp {
    private static final Object D = new Object();
    private static VUIStateManager x;
    private volatile boolean A;
    private boolean B;
    private boolean C = false;
    private aft E;
    private VideoRecv F;
    private a G = new a() {
        public final void a() {
            if (bno.a) {
                bfh.a("VUI_TAG", "VUIStateManager  >----- keyBoardShow -----< ");
            }
            if (VUIStateManager.this.o) {
                if (bno.a) {
                    bfh.a("VUI_TAG", "VUIStateManager  >----- keyBoardShow (Navi) -----< ");
                }
                return;
            }
            VUIStateManager.this.z = true;
            VUIStateManager.this.C();
            VUIStateManager.this.b(8);
            if (VUIStateManager.this.v != null) {
                VSysStateResultMap vSysStateResultMap = new VSysStateResultMap();
                vSysStateResultMap.put((String) "isKeyboardVisible", Integer.valueOf(1));
                vSysStateResultMap.put((String) "isRecordPermissionGranted", Integer.valueOf(VUIStateManager.this.n() ? 1 : 0));
                vSysStateResultMap.put((String) "isVUICardVisible", Integer.valueOf(VUIStateManager.f().g ? 1 : 0));
                VUIStateManager.this.v.a(vSysStateResultMap);
            }
        }

        public final void b() {
            if (bno.a) {
                bfh.a("VUI_TAG", "VUIStateManager  >----- keyBoardHide -----< ");
            }
            if (VUIStateManager.this.o) {
                if (bno.a) {
                    bfh.a("VUI_TAG", "VUIStateManager  >----- keyBoardHide (Navi) -----< ");
                }
                return;
            }
            VUIStateManager.this.z = false;
            VUIStateManager.this.D();
            VUIStateManager.this.b(8);
            if (VUIStateManager.this.v != null) {
                VSysStateResultMap vSysStateResultMap = new VSysStateResultMap();
                vSysStateResultMap.put((String) "isKeyboardVisible", Integer.valueOf(0));
                vSysStateResultMap.put((String) "isRecordPermissionGranted", Integer.valueOf(VUIStateManager.this.n() ? 1 : 0));
                vSysStateResultMap.put((String) "isVUICardVisible", Integer.valueOf(VUIStateManager.f().g ? 1 : 0));
                VUIStateManager.this.v.a(vSysStateResultMap);
            }
        }
    };
    public volatile boolean a;
    public volatile boolean b;
    public List<bgv> c = new ArrayList();
    public List<bgu> d = new CopyOnWriteArrayList();
    public List<bgw> e = new ArrayList();
    public Context f;
    public boolean g;
    public boolean h;
    public int i = -1;
    public int j = 0;
    public int k = 5000;
    public int l = 5000;
    public MapSharePreference m;
    public volatile boolean n;
    public volatile boolean o;
    public volatile boolean p;
    public volatile boolean q;
    public volatile boolean r;
    public volatile boolean s;
    public volatile boolean t;
    public volatile boolean u;
    public bfn v;
    public volatile boolean w = false;
    private boolean y;
    /* access modifiers changed from: private */
    public volatile boolean z;

    static class VideoRecv extends BroadcastReceiver {
        private VideoRecv() {
        }

        /* synthetic */ VideoRecv(byte b) {
            this();
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int intExtra = intent.getIntExtra("STATE", 0);
                bfj.a().a(intExtra);
                if (1 == intExtra) {
                    VUIStateManager.f().s();
                }
            }
        }
    }

    public static VUIStateManager f() {
        if (x == null) {
            synchronized (VUIStateManager.class) {
                try {
                    if (x == null) {
                        x = new VUIStateManager();
                    }
                }
            }
        }
        return x;
    }

    private VUIStateManager() {
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager >> >>  (constructor) ");
        }
        this.f = AMapAppGlobal.getApplication().getApplicationContext();
        this.m = new MapSharePreference((String) "SharedPreferences");
        this.B = true;
        this.F = new VideoRecv(0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.autonavi.minimap.ajx3.action.VIDEO_FOCUS");
        this.f.registerReceiver(this.F, intentFilter);
    }

    public final void b() {
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager >> >>  (onCreate) ");
        }
        lo.a().a((String) "vui_navi", (lp) this);
        if (m()) {
            if (bno.a) {
                bfh.a("VUI_TAG", "VUIStateManager >> >>  (init) ");
            }
            afq.a(this.f).a((b) this);
            if (F()) {
                afq.a(this.f).a((a) this);
            }
            afr a2 = afr.a(this.f);
            synchronized (a2.c) {
                if (a2.c.isEmpty()) {
                    a2.c.add(this);
                    IntentFilter intentFilter = new IntentFilter("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
                    intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
                    try {
                        a2.a.registerReceiver(a2.d, intentFilter);
                    } catch (Exception e2) {
                        AMapLog.warning("paas.tools", "AmapBluetoothAdapter", Log.getStackTraceString(e2));
                    }
                } else if (!a2.c.contains(this)) {
                    a2.c.add(this);
                }
            }
            NetworkReachability.a((a) this);
            afs.a(this.f).a.listen(this, 32);
            this.E = new aft(DoNotUseTool.getActivity());
            a aVar = this.G;
            aft aft = this.E;
            aft.c = aVar;
            if (aft.a != null) {
                ViewTreeObserver viewTreeObserver = aft.a.getViewTreeObserver();
                if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                    viewTreeObserver.addOnGlobalLayoutListener(aft.e);
                }
            }
            NativeVcsManager.getInstance().doInit();
        }
        if (this.B) {
            if (m() && bfj.a().b()) {
                if (bno.a) {
                    bfh.a("VUI_TAG", "VUIStateManager (addEventTracking)P00462-B001");
                }
                LogManager.actionLogV2("P00462", "B001");
                if (l() && p()) {
                    if (bno.a) {
                        bfh.a("VUI_TAG", "VUIStateManager (addEventTracking)P00462-B002");
                    }
                    LogManager.actionLogV2("P00462", "B002");
                }
            }
            this.B = false;
        }
    }

    public final void c() {
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager >> >>  (onResume): ");
        }
        if (!p()) {
            if (bno.a) {
                bfh.a("VUI_TAG", "VUIStateManager >> >>  (onResume) NoPermission");
            }
            return;
        }
        D();
        this.w = false;
    }

    public final void d() {
        C();
    }

    public final void e() {
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager >> >>  (onDestroy) ");
        }
        lo.a().b("vui_navi", this);
        NativeVcsManager.getInstance().release();
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager >> >>  (close) ");
        }
        afq a2 = afq.a(this.f);
        synchronized (a2.b) {
            if (a2.b.contains(this)) {
                a2.b.remove(this);
            }
            if (a2.b.isEmpty()) {
                try {
                    a2.a.unregisterReceiver(a2.c);
                } catch (Exception e2) {
                    AMapLog.warning("paas.tools", "AmapBatteryManager", Log.getStackTraceString(e2));
                }
            }
        }
        if (F()) {
            afq.a(this.f).b((a) this);
        }
        afr a3 = afr.a(this.f);
        synchronized (a3.c) {
            if (a3.c.contains(this)) {
                a3.c.remove(this);
            }
            if (a3.c.isEmpty()) {
                try {
                    a3.a.unregisterReceiver(a3.d);
                } catch (Exception e3) {
                    AMapLog.warning("paas.tools", "AmapBluetoothAdapter", Log.getStackTraceString(e3));
                }
            }
        }
        NetworkReachability.b((a) this);
        afs.a(this.f).a.listen(this, 0);
        if (this.E != null) {
            aft aft = this.E;
            aft.c = null;
            if (aft.a != null) {
                ViewTreeObserver viewTreeObserver = aft.a.getViewTreeObserver();
                if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeGlobalOnLayoutListener(aft.e);
                }
            }
        }
        this.n = false;
        this.o = false;
        this.i = -1;
        this.j = 0;
        this.k = 5000;
        this.l = 5000;
        if (this.c != null) {
            this.c.clear();
        }
        if (this.d != null) {
            this.d.clear();
        }
        if (this.e != null) {
            this.e.clear();
        }
        this.w = true;
    }

    public void onConfigResultCallBack(int i2, String str) {
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (this.m == null) {
                    this.m = new MapSharePreference((String) "SharedPreferences");
                }
                boolean z2 = true;
                if (jSONObject.has("vui_navi_key")) {
                    int optInt = jSONObject.optInt("vui_navi_key", 1);
                    this.m.putIntValue("vui_navi_key", optInt);
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder("VUIStateManager  (onConfigResultCallBack) result: ");
                        sb.append(str);
                        sb.append(" vKey: ");
                        sb.append(optInt);
                        bfh.a("VUI_TAG", sb.toString());
                    }
                }
                if (jSONObject.has("wwv_enable")) {
                    int optInt2 = jSONObject.optInt("wwv_enable", 0);
                    NativeVcsManager instance = NativeVcsManager.getInstance();
                    if (optInt2 <= 0) {
                        z2 = false;
                    }
                    instance.setWWVEnable(z2);
                    if (bno.a) {
                        StringBuilder sb2 = new StringBuilder("VUIStateManager  (onConfigResultCallBack) result: ");
                        sb2.append(str);
                        sb2.append(" wKey: ");
                        sb2.append(optInt2);
                        bfh.a("VUI_TAG", sb2.toString());
                    }
                } else {
                    NativeVcsManager.getInstance().setWWVEnable(false);
                }
                if (jSONObject.has(RPCDataItems.VALUE_DT_HTTPDNS)) {
                    int optInt3 = jSONObject.optInt(RPCDataItems.VALUE_DT_HTTPDNS, 0);
                    this.j = optInt3;
                    this.m.putIntValue(RPCDataItems.VALUE_DT_HTTPDNS, optInt3);
                    if (bno.a) {
                        StringBuilder sb3 = new StringBuilder("VUIStateManager  (onConfigResultCallBack) result: ");
                        sb3.append(str);
                        sb3.append(" httpdns enable: ");
                        sb3.append(optInt3);
                        bfh.a("VUI_TAG", sb3.toString());
                    }
                }
                if (jSONObject.has("httpDNSTimeout")) {
                    int optInt4 = jSONObject.optInt("httpDNSTimeout", 5000);
                    this.m.putIntValue("httpDNSTimeout", optInt4);
                    this.k = optInt4;
                    NativeVcsManager.getInstance().setHttpdnsTimeout(optInt4);
                    if (bno.a) {
                        StringBuilder sb4 = new StringBuilder("VUIStateManager  (onConfigResultCallBack) result: ");
                        sb4.append(str);
                        sb4.append(" httpdns timeout: ");
                        sb4.append(optInt4);
                        bfh.a("VUI_TAG", sb4.toString());
                    }
                }
                if (jSONObject.has("localDNSTimeout")) {
                    int optInt5 = jSONObject.optInt("localDNSTimeout", 5000);
                    this.m.putIntValue("localDNSTimeout", optInt5);
                    this.l = optInt5;
                    NativeVcsManager.getInstance().setLocaldnsTimeout(optInt5);
                    if (bno.a) {
                        StringBuilder sb5 = new StringBuilder("VUIStateManager  (onConfigResultCallBack) result: ");
                        sb5.append(str);
                        sb5.append(" localpdns timeout: ");
                        sb5.append(optInt5);
                        bfh.a("VUI_TAG", sb5.toString());
                    }
                }
            } catch (JSONException unused) {
                NativeVcsManager.getInstance().setWWVEnable(false);
            }
        }
    }

    public void onConfigCallBack(int i2) {
        String a2 = lo.a().a((String) "vui_navi");
        if (!TextUtils.isEmpty(a2)) {
            try {
                JSONObject jSONObject = new JSONObject(a2);
                if (jSONObject.has("wwv_enable")) {
                    int optInt = jSONObject.optInt("wwv_enable", 0);
                    NativeVcsManager.getInstance().setWWVEnable(optInt > 0);
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder("VUIStateManager  (onConfigCallBack) result: ");
                        sb.append(a2);
                        sb.append(" wKey: ");
                        sb.append(optInt);
                        bfh.a("VUI_TAG", sb.toString());
                    }
                } else {
                    NativeVcsManager.getInstance().setWWVEnable(false);
                }
                if (jSONObject.has(RPCDataItems.VALUE_DT_HTTPDNS)) {
                    int optInt2 = jSONObject.optInt(RPCDataItems.VALUE_DT_HTTPDNS, 0);
                    this.m.putIntValue(RPCDataItems.VALUE_DT_HTTPDNS, optInt2);
                    this.j = optInt2;
                    if (bno.a) {
                        StringBuilder sb2 = new StringBuilder("VUIStateManager  (onConfigCallBack) result: ");
                        sb2.append(a2);
                        sb2.append(" httpdns enable: ");
                        sb2.append(optInt2);
                        bfh.a("VUI_TAG", sb2.toString());
                    }
                }
                if (jSONObject.has("httpDNSTimeout")) {
                    int optInt3 = jSONObject.optInt("httpDNSTimeout", 5000);
                    this.m.putIntValue("httpDNSTimeout", optInt3);
                    this.k = optInt3;
                    NativeVcsManager.getInstance().setHttpdnsTimeout(optInt3);
                    if (bno.a) {
                        StringBuilder sb3 = new StringBuilder("VUIStateManager  (onConfigCallBack) result: ");
                        sb3.append(a2);
                        sb3.append(" httpdns timeout: ");
                        sb3.append(optInt3);
                        bfh.a("VUI_TAG", sb3.toString());
                    }
                }
                if (jSONObject.has("localDNSTimeout")) {
                    int optInt4 = jSONObject.optInt("localDNSTimeout", 5000);
                    this.m.putIntValue("localDNSTimeout", optInt4);
                    this.l = optInt4;
                    NativeVcsManager.getInstance().setLocaldnsTimeout(optInt4);
                    if (bno.a) {
                        StringBuilder sb4 = new StringBuilder("VUIStateManager  (onConfigCallBack) result: ");
                        sb4.append(a2);
                        sb4.append(" localpdns timeout: ");
                        sb4.append(optInt4);
                        bfh.a("VUI_TAG", sb4.toString());
                    }
                }
                return;
            } catch (JSONException unused) {
            }
        }
        NativeVcsManager.getInstance().setWWVEnable(false);
    }

    public final void a() {
        k();
        E();
    }

    private void E() {
        if (this.h) {
            NativeVcsManager.getInstance().tryStartListening();
            if (bno.a) {
                bfh.a("VUI_TAG", "VUIStateManager  notify(onBatteryChange) isBatteryLower: false");
            }
            a(1);
        }
        this.h = false;
    }

    public final void a(NetworkType networkType) {
        boolean b2 = NetworkReachability.b();
        StringBuilder sb = new StringBuilder("VUIStateManager  bConnected: ");
        sb.append(b2);
        sb.append(" mIsNetworkConnect: ");
        sb.append(this.y);
        bfh.a("VUI_TAG", sb.toString());
        if (this.y != b2) {
            a(4);
        }
        this.y = b2;
    }

    public final void b(boolean z2) {
        String str;
        String str2;
        afr a2 = afr.a(this.f);
        String name = a2.b == null ? null : a2.b.getName();
        if (bfg.a()) {
            if (!TextUtils.isEmpty(name)) {
                if (z2) {
                    str2 = this.f.getString(R.string.bluetooth_connected, new Object[]{name});
                } else {
                    str2 = this.f.getString(R.string.bluetooth_disconnected, new Object[]{name});
                }
                ToastHelper.showToast(str2);
            } else {
                if (z2) {
                    str = this.f.getString(R.string.bluetooth_connected_device_undefined);
                } else {
                    str = this.f.getString(R.string.bluetooth_disconnected_device_undefined);
                }
                ToastHelper.showToast(str);
            }
        }
        a(2);
    }

    public void onCallStateChanged(int i2, String str) {
        if (!h()) {
            NativeVcsManager.getInstance().tryStartListening();
        } else {
            if (v()) {
                s();
                c(true);
            }
            NativeVcsManager.getInstance().tryStopListening();
        }
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager  notify(onCallStateChanged) isCallOffHook: ");
            sb.append(h());
            bfh.a("VUI_TAG", sb.toString());
        }
        a(5);
    }

    public final void a(bgu bgu) {
        if (this.d != null) {
            this.d.remove(bgu);
        }
    }

    /* access modifiers changed from: private */
    public void b(int i2) {
        if (this.c != null) {
            for (bgv a2 : this.c) {
                VUIState vUIState = new VUIState();
                vUIState.type = i2;
                a2.a(vUIState);
            }
        }
    }

    private void c(int i2) {
        if (this.d != null) {
            for (bgu onCardStateChanged : this.d) {
                VUIState vUIState = new VUIState();
                vUIState.type = i2;
                onCardStateChanged.onCardStateChanged(vUIState);
            }
        }
    }

    private void d(int i2) {
        if (this.e != null) {
            for (bgw a2 : this.e) {
                VUIState vUIState = new VUIState();
                vUIState.type = i2;
                a2.a(vUIState);
            }
        }
    }

    public final void a(int i2) {
        b(i2);
        c(i2);
        d(i2);
    }

    private static boolean F() {
        return VERSION.SDK_INT >= 21;
    }

    @SuppressLint({"MissingPermission"})
    public final boolean g() {
        boolean z2 = false;
        if ((this.f.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.f.getPackageName()) == 0) && NetworkReachability.b()) {
            z2 = true;
        }
        this.y = z2;
        StringBuilder sb = new StringBuilder("VUIStateManager isNetworkConnected: ");
        sb.append(this.y);
        bfh.a("VUI_TAG", sb.toString());
        return this.y;
    }

    public final boolean h() {
        int callState = afs.a(this.f).a.getCallState();
        boolean z2 = true;
        if (!(callState == 2 || callState == 1)) {
            z2 = false;
        }
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager (isCallOffHook): ".concat(String.valueOf(z2)));
        }
        return z2;
    }

    public final boolean i() {
        long currentTimeMillis = bno.a ? System.currentTimeMillis() : 0;
        boolean isMusicActive = ka.a(this.f).a.isMusicActive();
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager (isBackgroundMusicPlaying): ");
            sb.append(isMusicActive);
            sb.append(" (time): ");
            sb.append(System.currentTimeMillis() - currentTimeMillis);
            bfh.a("VUI_TAG", sb.toString());
        }
        return isMusicActive;
    }

    public final float j() {
        float b2 = afq.a(this.f).b();
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager (getBatteryScale): ".concat(String.valueOf(b2)));
        }
        return b2;
    }

    public final boolean k() {
        boolean a2 = afq.a(this.f).a();
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager (isBatteryCharging): ".concat(String.valueOf(a2)));
        }
        return a2;
    }

    public final boolean l() {
        return !h();
    }

    public final boolean m() {
        if (this.i != -1) {
            return this.i > 0;
        }
        if (this.m == null) {
            this.m = new MapSharePreference((String) "SharedPreferences");
        }
        this.i = this.m.getIntValue("vui_navi_key", 1);
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager (isCloudOpen) vKey: ");
            sb.append(this.i);
            bfh.a("VUI_TAG", sb.toString());
        }
        return this.i > 0;
    }

    public final boolean n() {
        boolean a2 = kj.a(this.f, new String[]{"android.permission.RECORD_AUDIO"});
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager (hasAudioPermission) >> ".concat(String.valueOf(a2)));
        }
        return a2;
    }

    public static boolean o() {
        if (NativeVcsManager.getInstance().isRecorderApplied()) {
            return false;
        }
        if (bno.a) {
            bfh.a("VUI_TAG", "VUIStateManager (recordOccupy) the record is occupied");
        }
        return true;
    }

    public final boolean p() {
        if (NativeVcsManager.getInstance().isRecordFileERROR()) {
            if (bno.a) {
                bfh.a("VUI_TAG", "VUIStateManager (recordReady) isRecordFileERROR");
            }
            return false;
        } else if (o()) {
            return false;
        } else {
            return n();
        }
    }

    public final void q() {
        if (!this.C) {
            this.C = true;
            String str = NativeVcsManager.getInstance().isRecordFileERROR() ? "isRecordFileERROR!" : !NativeVcsManager.getInstance().isRecorderApplied() ? "isRecorderNotApplied!" : "do not have permission!";
            bfp.b(d.a, 3, str);
        }
    }

    public static void a(b bVar) {
        kj.a(DoNotUseTool.getActivity(), new String[]{"android.permission.RECORD_AUDIO"}, bVar);
    }

    public static long r() {
        return NativeVcsManager.getInstance().getCurrentScene();
    }

    public final void s() {
        if (!this.o) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("VUIStateManager =====>>  (stopTTS) ");
                sb.append(Log.getStackTraceString(new Throwable()));
                bfh.a("VUI_TAG", sb.toString());
            }
            if (PlaySoundUtils.getInstance().isPlaying()) {
                PlaySoundUtils.getInstance().clear();
            }
        }
    }

    public final void c(boolean z2) {
        if (this.o) {
            if (bno.a) {
                bfh.a("VUI_TAG", "VUIStateManager  (playWarningSound NO(navi)");
            }
        } else if (!z2 || bfg.a()) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("VUIStateManager =====>>  playWarningSound(DUDU-) ");
                sb.append(Log.getStackTraceString(new Throwable()));
                bfh.a("VUI_TAG", sb.toString());
            }
            PlaySoundUtils.getInstance().playNaviWarningSound(this.f, R.raw.vui_failure_ring);
        }
    }

    public final void t() {
        synchronized (D) {
            if (bno.a) {
                bfh.a("VUI_TAG", "------------------------------ >>>>> (tryResumeMusic)");
                StringBuilder sb = new StringBuilder(" hasPause: ");
                sb.append(this.n);
                bfh.a("VUI_TAG", sb.toString());
            }
            if (this.n) {
                int abandonAudioFocus = ka.a(this.f).a.abandonAudioFocus(null);
                if (bno.a) {
                    StringBuilder sb2 = new StringBuilder("VUIStateManager  ret: ");
                    sb2.append(abandonAudioFocus > 0);
                    bfh.a("VUI_TAG", sb2.toString());
                }
                if (abandonAudioFocus > 0) {
                    this.n = false;
                }
            }
        }
    }

    public final void u() {
        synchronized (D) {
            if (bno.a) {
                bfh.a("VUI_TAG", "------------------------------ >>>>> (tryPauseMusic)");
                StringBuilder sb = new StringBuilder(" hasPause: ");
                sb.append(this.n);
                sb.append(" isPlaying: ");
                sb.append(i());
                bfh.a("VUI_TAG", sb.toString());
            }
            if (i()) {
                int requestAudioFocus = ka.a(this.f).a.requestAudioFocus(null, 3, 2);
                if (bno.a) {
                    StringBuilder sb2 = new StringBuilder("VUIStateManager  ret: ");
                    sb2.append(requestAudioFocus > 0);
                    bfh.a("VUI_TAG", sb2.toString());
                }
                if (requestAudioFocus > 0) {
                    this.n = true;
                }
            }
        }
    }

    public static boolean v() {
        boolean z2 = NativeVcsManager.getInstance().getCurrentVCSStatus() == VUIStatus.VUIStatus_SpeechRecognizing || NativeVcsManager.getInstance().getCurrentVCSStatus() == VUIStatus.VUIStatus_RecognizingWaiting || NativeVcsManager.getInstance().getCurrentVCSStatus() == VUIStatus.VUIStatus_SpeechTranslating || NativeVcsManager.getInstance().getCurrentVCSStatus() == VUIStatus.VUIStatus_ExecuteCommand || "ringState".equals(NativeVcsManager.getInstance().getCurrentVCSState());
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager  (isSession) isHandleState: ");
            sb.append(z2);
            sb.append(" isPlaying: ");
            sb.append(PlaySoundUtils.getInstance().isPlaying());
            bfh.a("VUI_TAG", sb.toString());
        }
        return z2 || PlaySoundUtils.getInstance().isPlaying();
    }

    public static boolean w() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            aww aww = (aww) a.a.a(aww.class);
            if (!(aww == null || aww.a() == null || !aww.a().a(pageContext))) {
                return true;
            }
        }
        return false;
    }

    public static boolean x() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            avi avi = (avi) a.a.a(avi.class);
            if (!(avi == null || avi.c() == null || !avi.c().a(pageContext))) {
                return true;
            }
        }
        return false;
    }

    public static boolean y() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            atb atb = (atb) a.a.a(atb.class);
            if (!(atb == null || atb.a() == null || !atb.a().a(pageContext))) {
                return true;
            }
        }
        return false;
    }

    private static boolean f(String str) {
        ICarTruckInfoManager iCarTruckInfoManager = (ICarTruckInfoManager) ank.a(ICarTruckInfoManager.class);
        if (iCarTruckInfoManager != null) {
            return str.equals(iCarTruckInfoManager.getSimNaviPage().getName());
        }
        return false;
    }

    public static boolean b(String str) {
        ICarTruckInfoManager iCarTruckInfoManager = (ICarTruckInfoManager) ank.a(ICarTruckInfoManager.class);
        if (iCarTruckInfoManager != null) {
            return iCarTruckInfoManager.isTruckNaviPage(str);
        }
        return false;
    }

    private static boolean g(String str) {
        ICarTruckInfoManager iCarTruckInfoManager = (ICarTruckInfoManager) ank.a(ICarTruckInfoManager.class);
        if (iCarTruckInfoManager != null) {
            return iCarTruckInfoManager.isSimTruckNaviPage(str);
        }
        return false;
    }

    public final void a(String str, boolean z2) {
        if (d(str)) {
            this.t = z2;
        } else if (e(str)) {
            this.u = z2;
        } else if (f(str)) {
            this.p = z2;
        } else if (b(str)) {
            this.r = z2;
        } else {
            if (g(str)) {
                this.s = z2;
            }
        }
    }

    public final boolean A() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager  (isSoftKeyboardShow) ");
            sb.append(this.z);
            bfh.a("VUI_TAG", sb.toString());
        }
        return this.z;
    }

    public final boolean B() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager  (isAudioRelease) ");
            sb.append(this.A);
            bfh.a("VUI_TAG", sb.toString());
        }
        return this.A;
    }

    public final void C() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager =====>>  (releaseAudio) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        this.A = true;
        NativeVcsManager.getInstance().resetRecordFileERRORFlag();
        NativeVcsManager.getInstance().stopListening(true);
        NativeVcsManager.getInstance().releaseAudioRecord();
    }

    public final void D() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager =====>>  (recoverAudio) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        this.A = false;
        if (!bfg.a()) {
            bfh.a("1");
        }
        NativeVcsManager.getInstance().resetRecordFileERRORFlag();
        NativeVcsManager.getInstance().tryStartListening();
    }

    public final void a(boolean z2) {
        j();
        E();
    }

    private static boolean d(String str) {
        String str2 = "";
        String str3 = "";
        avn avn = (avn) a.a.a(avn.class);
        if (avn != null) {
            str2 = avn.a().a(1).getName();
            str3 = avn.a().a(2).getName();
        }
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager  (isHealthyRideNaviPage) pageIdentifier: ");
            sb.append(str);
            sb.append(" -healthyRideClsName: ");
            sb.append(str2);
            sb.append(" -healthyRideFinishClsName: ");
            sb.append(str3);
            bfh.a("VUI_TAG", sb.toString());
        }
        if (str.equals(str2) || str.equals(str3)) {
            return true;
        }
        return false;
    }

    private static boolean e(String str) {
        String str2 = "";
        String str3 = "";
        avo avo = (avo) a.a.a(avo.class);
        if (avo != null) {
            str2 = avo.a().a(1).getName();
            str3 = avo.a().a(2).getName();
        }
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUIStateManager  (isHealtyhRunNaviPage) pageIdentifier: ");
            sb.append(str);
            sb.append(" -healthyRunClsName: ");
            sb.append(str2);
            sb.append(" -healthyRunFinishClsName: ");
            sb.append(str3);
            bfh.a("VUI_TAG", sb.toString());
        }
        if (str.equals(str2) || str.equals(str3)) {
            return true;
        }
        return false;
    }

    public static boolean a(String str) {
        bci bci = (bci) a.a.a(bci.class);
        if (bci != null) {
            return bci.a(str);
        }
        return false;
    }

    public static boolean z() {
        axw axw = (axw) a.a.a(axw.class);
        if (axw != null) {
            return axw.a();
        }
        return false;
    }

    public static boolean c(String str) {
        String str2 = "";
        aww aww = (aww) a.a.a(aww.class);
        if (aww != null) {
            str2 = aww.a().a(1).getName();
        }
        if (!str.equals(str2)) {
            String str3 = "";
            avi avi = (avi) a.a.a(avi.class);
            if (avi != null) {
                str3 = avi.c().a(1).getName();
            }
            if (!str.equals(str3) && !e(str) && !d(str)) {
                ICarTruckInfoManager iCarTruckInfoManager = (ICarTruckInfoManager) ank.a(ICarTruckInfoManager.class);
                if ((iCarTruckInfoManager != null ? str.equals(iCarTruckInfoManager.getMotorNaviPage().getName()) : false) || b(str) || f(str) || g(str) || z()) {
                    return true;
                }
                return false;
            }
        }
        return true;
    }
}
