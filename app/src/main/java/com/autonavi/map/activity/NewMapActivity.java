package com.autonavi.map.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.media.MediaMuxer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.appupgrade.AppUpgradeController;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.schoolbus.notifcation.ISchoolbusStatusMangger;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.AEUtil;
import com.autonavi.ae.gmap.AMapSurface;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.jni.ae.bl.map.IPageService;
import com.autonavi.jni.ae.dice.NaviEngine;
import com.autonavi.jni.eyrie.amap.UiThreadWrapper;
import com.autonavi.map.LocatorCallback;
import com.autonavi.map.core.GLMapViewGroup;
import com.autonavi.map.core.LocationMode;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.IPageHost;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.ActivityStateDispatch;
import com.autonavi.mine.page.setting.sysabout.page.ConfigPage;
import com.autonavi.minimap.LeakCanaryUtil;
import com.autonavi.minimap.MapApplication;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.AjxInit;
import com.autonavi.minimap.ajx3.modules.ModuleAMap;
import com.autonavi.minimap.ajx3.modules.ModuleSchemeTest;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;
import com.autonavi.minimap.ajx3.util.AjxDebugUtils;
import com.autonavi.minimap.app.CrashCleanHelper;
import com.autonavi.minimap.base.overlay.OverlayTexureCacheUtil;
import com.autonavi.minimap.bundle.maphome.api.IMapEventListener;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.bundle.setting.api.ISettingService;
import com.autonavi.minimap.bundle.splashscreen.api.ISplashManager;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.nativesupport.platform.NativeSupport;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.wing.WingActivity;
import com.q.Qt;
import com.sijla.callback.QtCallBack;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import defpackage.*;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class NewMapActivity extends WingActivity implements brr, buo, IPageHost, jp, defpackage.kj.a {
    private static boolean y = false;
    private BroadcastReceiver A;
    private BroadcastReceiver B;
    private defpackage.drn.c C;
    /* access modifiers changed from: private */
    public Intent D = null;
    private buw E;
    /* access modifiers changed from: private */
    public GLMapViewGroup atmapsView;
    private brd G = new brd(this);
    /* access modifiers changed from: private */
    public long elapsedRealtime;
    private CrashCleanHelper I = new CrashCleanHelper(this);
    private long J = 0;
    private long K = 0;
    private boolean L = false;
    /* access modifiers changed from: private */
    public Runnable M = new Runnable() {
        public final void run() {
            NewMapActivity.this.finish();
            Process.killProcess(Process.myPid());
        }
    };
    private boolean N = true;
    IPageService b;
    public FrameLayout fl_content_view;
    defpackage.kj.b d = new defpackage.kj.b() {
        public final void run() {
            if (!NewMapActivity.this.q) {
                NewMapActivity.c(NewMapActivity.this);
            }
        }

        public final void reject() {
            if (!NewMapActivity.this.q) {
                aci.AnonymousClass1 r0 = new Runnable() {
                    public final void run() {
                        kj.a((Context) NewMapActivity.this, NewMapActivity.this.d);
                    }
                };
                StringBuilder sb = new StringBuilder();
                sb.append(AMapAppGlobal.getApplication().getString(R.string.permission_tip_write_settings2));
                sb.append(",");
                sb.append(AMapAppGlobal.getApplication().getString(R.string.permission_dialog_tip));
                NewMapActivity.a(NewMapActivity.this, sb.toString(), r0, NewMapActivity.this.M);
            }
        }
    };
    /* access modifiers changed from: private */
    public MapManager f;
    private cde g;
    /* access modifiers changed from: private */
    public dli h;
    private jh i;
    /* access modifiers changed from: private */
    public final Handler j = new c(this, 0);
    private long k = 0;
    private boolean l = false;
    /* access modifiers changed from: private */
    public boolean m = false;
    private boolean n = MapApplication.isDataFreeSpaceLow();
    private defpackage.kj.b o = null;
    private defpackage.kj.b p = null;
    /* access modifiers changed from: private */
    public boolean q = false;
    /* access modifiers changed from: private */
    public boolean r = false;
    private b s;
    private boolean t = false;
    /* access modifiers changed from: private */
    public LocatorCallback u;
    private boolean v;
    private boolean w = false;
    private ActivityStateDispatch x;
    private BroadcastReceiver z;

    static class a implements defpackage.but.a {
        a() {
        }
    }

    class b extends Thread {
        public boolean a;

        private b() {
            this.a = false;
        }

        /* synthetic */ b(NewMapActivity newMapActivity, byte b2) {
            this();
        }

        public final void run() {
            final String str;
            this.a = true;
            try {
                str = akp.a();
            } catch (IOException e) {
                e.printStackTrace();
                str = null;
            }
            this.a = false;
            if (!TextUtils.isEmpty(str)) {
                NewMapActivity.this.runOnUiThread(new Runnable() {
                    public final void run() {
                        Builder builder = new Builder(NewMapActivity.this);
                        builder.setTitle(".So update ");
                        builder.setPositiveButton("重启加载新库", new OnClickListener() {
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                PendingIntent activity = PendingIntent.getActivity(MapApplication.getContext(), 0, new Intent(MapApplication.getContext(), SplashActivity.class), 268435456);
                                AlarmManager alarmManager = (AlarmManager) MapApplication.getContext().getSystemService(NotificationCompat.CATEGORY_ALARM);
                                if (alarmManager != null) {
                                    alarmManager.set(1, System.currentTimeMillis() + 1000, activity);
                                }
                                Process.killProcess(Process.myPid());
                            }
                        });
                        builder.setNegativeButton("Cancle", new OnClickListener() {
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setMessage(str);
                        try {
                            builder.show();
                        } catch (Exception e) {
                            DebugLog.error(e);
                        }
                    }
                });
            }
        }
    }

    static class c extends Handler {
        private WeakReference<NewMapActivity> a;

        /* synthetic */ c(NewMapActivity newMapActivity, byte b) {
            this(newMapActivity);
        }

        private c(NewMapActivity newMapActivity) {
            this.a = new WeakReference<>(newMapActivity);
        }

        public final void handleMessage(Message message) {
            if (!(this.a == null || this.a.get() == null || message.what != 0)) {
                ((NewMapActivity) this.a.get()).startDefaultFragment();
            }
        }
    }

    static class d implements defpackage.but.b {
        d() {
        }

        public final void a(Object obj) {
            LeakCanaryUtil.onDestroy(obj);
        }
    }

    static class e implements defpackage.but.c {
        e() {
        }

        public final void a(Object obj) {
            LocationMode.design(obj);
        }
    }

    static class f implements defpackage.but.d {
        f() {
        }
    }

    static class g implements defpackage.but.e {
        g() {
        }

        public final void a( bid bid) {
            ckg.a(bid);
        }
    }

    static class h implements defpackage.but.f {
        h() {
        }

        public final boolean a() {
            return bim.aa().k((String) "202");
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        AMapAppGlobal.setActivity(this);
    }

    public boolean isHostPaused() {
        return this.v;
    }

    @SuppressFBWarnings({"DM_EXIT"})
    public final void a(Bundle bundle) {
        ckb.a((String) "NewMapActivity#onActivityCreate");
        la.a((String) "CallOnActivityCreate");
        DoNotUseTool.setActivity(this);
        this.h = new dli(this);
        this.i = new jh(this);
        if (!this.I.a()) {
            this.elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.n) {
                afu.b((Activity) this);
                finish();
                System.exit(0);
                return;
            }
            if (buw.a((Activity) this)) {
                init();
                la.a((String) "NewMapActivity-init");
                this.e = true;
            } else {
                this.E = buw.a((Activity) this, ( buu ) new buu() {
                    public final void a() {
                        if (!NewMapActivity.this.q) {
                            NewMapActivity.c(NewMapActivity.this);
                        }
                    }
                });
            }
            ekp a2 = ekp.a();
            FrameLayout frameLayout = this.fl_content_view;
            if (frameLayout != null && VERSION.SDK_INT >= 21) {
                a2.c = this;
                a2.a = frameLayout;
                a2.b = null;
                if (a2.l == null) {
                    a2.l = new MapSharePreference(SharePreferenceName.SharedPreferences);
                }
                if (a2.l.getBooleanValue("lab_screenrecording", false)) {
                    a2.a(true);
                }
            }
            bnu.a().a((String) "lab_screenrecording", (defpackage.bnu.b) new defpackage.bnu.b() {
                public final void a(String str, boolean z) {
                    if ("lab_screenrecording".equals(str)) {
                        ekp.a().a(z);
                    }
                }
            });
            dsk.a();
            dsk.b();
            bnu.a().a((String) "lab_offlinedata_diff", (defpackage.bnu.b) new defpackage.bnu.b() {
                public final void a(String str, boolean z) {
                    if ("lab_offlinedata_diff".equals(str)) {
                        dsk.a();
                        dsk.a(z);
                    }
                }
            });
            la.a((String) "NewMapActivity-onActivityCreated");
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                decorView.setFocusable(true);
                decorView.setFocusableInTouchMode(true);
                decorView.requestFocus();
            }
        }
    }

    public void startActivity(Intent intent) {
        if (intent != null) {
            Uri data = intent.getData();
            if (data != null && ("androidamap".equals(data.getScheme()) || "amapuri".equals(data.getScheme()))) {
                this.h.a(intent);
                return;
            }
        }
        super.startActivity(intent);
    }

    private void n() {
        if (this.C != null) {
            drm.b((defpackage.drn.a) this.C);
        }
    }

    /* access modifiers changed from: private */
    public void startDefaultFragment() {
        if (y) {
            if (this.f != null) {
                this.f.restoreMapStateWithouMapMode();
                ceg ceg = (ceg) ank.a(ceg.class);
                if (ceg != null) {
                    ceg.a(true);
                }
                this.f.getOverlayManager().restoreWhenMapCreate();
                if (this.N) {
                    this.N = false;
                    MapSharePreference mapSharePreference = new MapSharePreference(ISettingService.SharePreferenceName.MapTextSizeSet.toString());
                    String string = mapSharePreference.sharedPrefs().getString("map_text_size", "");
                    if (TextUtils.isEmpty(string)) {
                        mapSharePreference.edit().putString("map_text_size", "std").apply();
                        string = "std";
                    }
                    if (this.f != null) {
                        bty mapView = this.f.getMapView();
                        if (mapView != null) {
                            if (string.contentEquals("small")) {
                                mapView.b(0.9f);
                            } else if (string.contentEquals("std")) {
                                mapView.b(1.0f);
                            } else if (string.contentEquals("large")) {
                                mapView.b(1.2f);
                            } else if (string.contentEquals("extra")) {
                                mapView.b(1.4f);
                            }
                        }
                    }
                }
                cdd.n().c();
                epu.a(this);
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null && (pageContext instanceof IMapPage)) {
                    ((IMapPage) pageContext).onMapSurfaceCreated();
                }
                avv.d();
                avv.a(true);
            }
            c(getIntent());
            this.i.a(true);
            if (!this.t) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.autonavi.minimap");
                registerReceiver(this.B, intentFilter);
                this.t = true;
            }
            if (!this.L) {
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                registerReceiver(this.A, intentFilter2);
                this.L = true;
            }
            r();
            d(getIntent());
            ccm a2 = ccm.a();
            if (ccm.a == null) {
                ccm.a = new ccp();
            }
            ccm.a.a(this, a2.c);
            if (this.h != null) {
                final Intent intent = getIntent();
                if (this.D != null && (intent == null || (intent.getData() == null && intent.getAction() == null))) {
                    intent = this.D;
                }
                IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                if (iOfflineManager == null || !iOfflineManager.isOfflineDataReady()) {
                    this.j.postDelayed(new Runnable() {
                        public final void run() {
                            if (!NewMapActivity.this.isFinishing()) {
                                if (intent == null || !"com.autonavi.bundle.splash.scheme.ACTION".equals(intent.getAction())) {
                                    NewMapActivity.this.h.b(intent);
                                } else {
                                    NewMapActivity.this.h.a(intent);
                                }
                                NewMapActivity.this.D = null;
                            }
                        }
                    }, 150);
                } else {
                    this.h.b(intent);
                    this.D = null;
                }
            }
            esg.a().d();
        }
    }

    private void onStartOver() {
        bim.aa().d(true);
        ccm.a();
        ccm.a(this);
        boolean z2 = !LocationInstrument.getInstance().isLocating();
        if (z2 && this.J != 0 && (System.currentTimeMillis() - this.J) / 1000 > 3600) {
            NetworkParam.clearSession();
            this.J = System.currentTimeMillis();
        }
        if (z2 && this.K != 0 && (System.currentTimeMillis() - this.K) / 1000 > 30) {
            NetworkParam.clearAppstartid();
            this.K = System.currentTimeMillis();
        }
        if (this.r && this.f != null && this.f.getMapView().O() && bry.a().booleanValue()) {
            this.f.getMapView().N();
            this.f.renderPauseDelay();
        }
        yh.a();
        this.x.onActivityStartCallback();
    }

    public final void h() {
        if (!this.I.a) {
            ahm.c( new Runnable() {
                public final void run() {
                    ISplashManager iSplashManager = (ISplashManager) ank.a(ISplashManager.class);
                    if (iSplashManager != null) {
                        iSplashManager.updateSplashData(false);
                    }
                }
            });
        }
    }

    public final void b() {
        if (!this.I.a && this.q) {
            onStartOver();
        }
    }

    private void onResumeOver() {
        ahm.a(new Runnable() {
            public final void run() {
                String str;
                lu luVar = lt.a().d;
                if (luVar.i != null ? luVar.i.booleanValue() : true) {
                    try {
                        if (!TextUtils.isEmpty(NetworkParam.getDiu())) {
                            str = NetworkParam.getDiu();
                        } else {
                            str = NetworkParam.getTaobaoID();
                        }
                        Qt.init(NewMapActivity.this.getApplication(), NetworkParam.getDic(), str, new QtCallBack() {
                            public final void uploadCallBack(JSONObject jSONObject) {
                                String str;
                                if (jSONObject != null) {
                                    try {
                                        str = jSONObject.optString("uploadStatus");
                                        if ( bno.a) {
                                            StringBuilder sb = new StringBuilder("QT-callback:");
                                            sb.append(jSONObject.toString());
                                            AMapLog.debug("paas.main", "NewMapActivity", sb.toString());
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        return;
                                    }
                                } else {
                                    str = UploadDataResult.FAIL_MSG;
                                }
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("type", str);
                                LogManager.actionLogV2(LogConstant.PAGE_THIRDPARTY_SENSUS_EVENT, "B001", jSONObject2);
                            }
                        });
                    } catch (Throwable unused) {
                    }
                }
            }
        });
        AMapPageUtil.setMvpActivityContext(this.a);
        a(this.a);
        if (this.m) {
            LogManager.actionLogV2("LogConstant", "resume");
        }
        this.m = false;
        ccm a2 = ccm.a();
        ccq ccq = ccm.b;
        Application application = a2.c;
        ccq.a();
        Ajx3UpgradeManager.getInstance().checkUpgrade();
        if (this.r) {
            LocationInstrument.getInstance().doStartLocate();
            s();
            cdd.n().c();
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null && (pageContext instanceof IMapPage)) {
                ((IMapPage) pageContext).onMapSurfaceCreated();
            }
        }
        if (AMapPageUtil.getPageContext() instanceof AbstractBaseMapPage) {
            bty mapView = this.f.getMapView();
            if (mapView != null && mapView.s()) {
                mapView.t();
            }
        }
        if (!AjxDebugUtils.isRelease() && new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(ConfigPage.ui_performance_view, false)) {
            emo.a(this).a();
        }
        if (this.w) {
            this.i.a(false);
        }
        this.x.onActivityResumeCallback();
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.f();
        }
    }

    public static void a(Uri uri) {
        if (!AjxDebugUtils.isRelease()) {
            AjxDebugUtils.handleAjxDebugScheme(uri);
        }
    }

    public final void a() {
        AMapAppGlobal.setActivity(this);
        if (!this.I.a) {
            this.v = false;
            if (this.q) {
                onResumeOver();
            }
        }
    }

    private void onPauseOver() {
        this.m = true;
        ccm a2 = ccm.a();
        ccq ccq = ccm.b;
        Application application = a2.c;
        ccq.b();
        if (this.r) {
            this.f.saveMapState();
        }
        try {
            if (this.l) {
                unregisterReceiver(this.z);
            }
            this.l = false;
        } catch (Exception e2) {
            if (bno.a) {
                AMapLog.e("NewMapActivity", Log.getStackTraceString(e2));
            }
        }
        LocationInstrument.getInstance().removeHighFrequencyStatusCallback(this.u);
        LocationInstrument.getInstance().saveCacheLocate();
        if (new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(ConfigPage.ui_performance_view, false)) {
            emo.a(this).a.b();
        }
        this.x.onActivityPauseCallback();
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.g();
        }
        o();
    }

    private static void o() {
        bhz bhz = (bhz) ((IMainMapService) ank.a(IMainMapService.class)).a(bhz.class);
        if (bhz != null) {
            bhz.c();
        }
    }

    public final void c() {
        if (!this.I.a) {
            this.v = true;
            this.w = true;
            if (this.q) {
                onPauseOver();
            }
        }
    }

    private void onStopOver() {
        this.J = System.currentTimeMillis();
        this.K = System.currentTimeMillis();
        ccm.a();
        ccm.b(this);
        if (this.r) {
            bty mapView = this.f.getMapView();
            if (mapView != null) {
                mapView.M();
            }
        }
        this.x.onActivityStopCallback();
    }

    public final void d() {
        if (!this.I.a) {
            cke.h();
            new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents).putLongValue("afp_again_launch_splash_time", System.currentTimeMillis());
            if (this.q) {
                onStopOver();
            }
        }
    }

    private void onNewIntentOver(Intent intent) {
        if (intent != null) {
            c(intent);
            d(intent);
            Uri data = intent.getData();
            if (data != null) {
                String scheme = data.getScheme();
                if (!TextUtils.isEmpty(scheme) && ((scheme.equals("amapuri") || scheme.equals("androidamap")) && this.f != null)) {
                    cdd.n().k();
                }
            }
        }
        if (this.h != null) {
            this.h.b(intent);
        } else if (intent != null && (!TextUtils.isEmpty(intent.getAction()) || intent.getData() != null)) {
            this.D = intent;
        }
        try {
            if (!( cse.a == null || cse.a.get() == null || !((Dialog) cse.a.get()).isShowing())) {
                ((Dialog) cse.a.get()).dismiss();
            }
            if (!(this.f == null || this.f.getOverlayManager() == null)) {
                this.f.getOverlayManager().clearScenicSelectMapPois();
            }
        } catch (Throwable unused) {
        }
    }

    public final void a(Intent intent) {
        if (this.q) {
            onNewIntentOver(intent);
        } else {
            super.a(intent);
        }
    }

    private void onWindowFocusChangedOver(boolean z2) {
        ccm.a();
        if (!z2) {
            o();
        }
    }

    public void onWindowFocusChanged(boolean z2) {
        if (z2) {
            euk.a((Activity) this);
        }
        super.onWindowFocusChanged(z2);
        if (this.q) {
            onWindowFocusChangedOver(z2);
        }
    }

    private void p() {
        ckb.a((String) "NewMapActivity#setupDefaultFragment(launchMapHomePage)");
        la.c();
        cke.a(this.f.getMapView());
        apr apr = (apr) defpackage.esb.a.a.a(apr.class);
        new MapSharePreference((String) "basemap").getStringValue("new_user_guide_is_shown", "");
        bfo bfo = (bfo) defpackage.esb.a.a.a(bfo.class);
        if (bfo != null) {
            bfo.a((ViewGroup) this.fl_content_view );
        }
        if (apr != null) {
            apr.a(( bui ) this.a);
        }
    }

    public final void a(int i2, int i3, Intent intent) {
        ModuleSchemeTest.onActivityResult(getApplicationContext(), i2, i3, intent);
        defpackage.aon.a.a.a(i2, i3, intent);
        defpackage.aon.a.a.a = null;
        defpackage.aoi.a.a.a(i2, i3, intent);
        ddd ddd = defpackage.ddd.a.a;
        if (ddd.a != null) {
            IUiListener iUiListener = (IUiListener) ddd.a.get();
            if (iUiListener != null) {
                Tencent.onActivityResultData(i2, i3, intent, iUiListener);
            }
        }
        defpackage.ddd.a.a.a = null;
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null && (pageContext instanceof AbstractBasePage)) {
            ((AbstractBasePage) pageContext).onActivityResult(i2, i3, intent);
        }
        if (i2 == 12368) {
            r();
        } else {
            boolean z2 = false;
            if (this.p != null && i2 == 1701) {
                if (!kj.a((Context) this)) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.permission_tip_write_settings));
                } else {
                    z2 = true;
                }
                if (this.p != null) {
                    this.p.callback(z2);
                }
                this.p = null;
            } else if (this.p != null && i2 == 1702) {
                if (!kj.c(this)) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.permission_tip_overlay));
                } else {
                    z2 = true;
                }
                if (this.p != null) {
                    this.p.callback(z2);
                }
                this.p = null;
            } else if (i2 == 1000 && pageContext != null && (pageContext instanceof AbstractBasePage)) {
                dtc dtc = (dtc) ank.a(dtc.class);
                if (dtc != null) {
                    dtc.a((AbstractBasePage) pageContext, i3, intent);
                }
            }
        }
        ekp a2 = ekp.a();
        StringBuilder sb = new StringBuilder("开始录屏--》onActivityResult requestCode=");
        sb.append(i2);
        sb.append(", resultCode=");
        sb.append(i3);
        ekp.a(sb.toString());
        if (a2.k != null && 1 == i2 && !a2.e.get()) {
            if (i3 != -1) {
                String str = Build.MODEL;
                if ("YQ601".equals(str) || "SM801".equals(str) || "SM901".equals(str)) {
                    ToastHelper.showToast(a2.c.getApplicationContext().getString(R.string.screen_capture_permissions_closed));
                }
                a2.d();
            } else {
                a2.e.set(true);
                defpackage.ekp.a aVar = a2.k;
                aVar.removeMessages(2);
                aVar.sendEmptyMessageDelayed(2, 120000);
                LogManager.actionLogV2(LogConstant.SCREEN_CAPTURE_PAGE_ID, "B002");
                StringBuilder sb2 = new StringBuilder("record-");
                sb2.append(a2.g);
                sb2.append(DictionaryKeys.CTRLXY_X);
                sb2.append(a2.h);
                sb2.append("-");
                sb2.append(a2.i);
                sb2.append("-");
                sb2.append(System.currentTimeMillis());
                sb2.append(PhotoParam.VIDEO_SUFFIX);
                String sb3 = sb2.toString();
                a2.j = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
                if ("vivo X7".equals(Build.MODEL)) {
                    a2.j = new File(Environment.getExternalStoragePublicDirectory("截屏"), "");
                }
                if (a2.j.exists() || a2.j.mkdirs()) {
                    ahm.a(new Runnable(i3, intent, sb3) {
                        final /* synthetic */ int a;
                        final /* synthetic */ Intent b;
                        final /* synthetic */ String c;

                        {
                            this.a = r2;
                            this.b = r3;
                            this.c = r4;
                        }

                        public final void run() {
                            ekp.this.o = ekp.this.f.getMediaProjection(this.a, this.b);
                            try {
                                ekp.this.v = new File(ekp.this.j, this.c).getAbsolutePath();
                                ekp.f(ekp.this);
                                ekp.this.q = new MediaMuxer(ekp.this.v, 0);
                                ekp.this.t = ekp.this.o.createVirtualDisplay("gaode-display", ekp.this.g, ekp.this.h, ekp.this.i, 1, ekp.this.s, null, null);
                                ekp.m(ekp.this);
                            } catch (Exception e) {
                                ekp.this.d();
                                ToastHelper.showToast(ekp.this.c.getApplicationContext().getString(R.string.screen_capture_system_not_support));
                                e.printStackTrace();
                            } catch (Throwable th) {
                                ekp.p(ekp.this);
                                throw th;
                            }
                            ekp.p(ekp.this);
                        }
                    });
                } else {
                    a2.d();
                    ToastHelper.showToast(a2.c.getApplicationContext().getString(R.string.screen_capture_no_permissions));
                }
            }
        }
        drl.a().a(getClass(), i2, i3, intent);
    }

    private void onBackPressedOver() {
        ModuleAMap.doBack();
        while (true) {
            if (!ModuleAMap.isTimeOut()) {
                if (!"1".equals(ModuleAMap.getIntercept())) {
                    if ("2".equals(ModuleAMap.getIntercept())) {
                        ModuleAMap.setIntercept("0");
                        break;
                    }
                } else {
                    ModuleAMap.setIntercept("0");
                    return;
                }
            } else {
                break;
            }
        }
        if (f() == 0) {
            if (g()) {
                if (q()) {
                    avv.b();
                    but.a = null;
                    super.onBackPressed();
                }
                return;
            }
            bid pageContext = AMapPageUtil.getPageContext();
            if (((apr) defpackage.esb.a.a.a(apr.class)).c(pageContext)) {
                if (q()) {
                    avv.b();
                    but.a = null;
                    try {
                        Field declaredField = FragmentActivity.class.getDeclaredField("mStopped");
                        declaredField.setAccessible(true);
                        declaredField.getBoolean(this);
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    super.onBackPressed();
                }
            } else if (pageContext != null) {
                pageContext.finish();
            }
        }
    }

    public void onBackPressed() {
        try {
            if (this.q) {
                onBackPressedOver();
            }
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        }
    }

    private boolean q() {
        if (this.h != null && this.h.a()) {
            NetworkParam.setSa(null);
            return a_();
        } else if (System.currentTimeMillis() - this.k < 2000) {
            return a_();
        } else {
            int i2 = R.string.exit_application_confirm;
            jg.a().c();
            new MapSharePreference((String) "SharedPreferences").sharedPrefs().getBoolean("isBackgroundDownload", false);
            this.k = System.currentTimeMillis();
            if (AMapAppGlobal.getApplication() != null) {
                ToastHelper.showToast(getString(i2));
            }
            return false;
        }
    }

    public final boolean a_() {
        y = false;
        anm.a(false);
        ToastHelper.cancel();
        this.j.removeCallbacksAndMessages(null);
        NetworkParam.clearSession();
        NetworkParam.clearAppstartid();
        new MapSharePreference(SharePreferenceName.SharedPreferences).putIntValue("last_adcode", LocationInstrument.getInstance().getLatestPosition().getAdCode());
        NetworkParam.setSa(null);
        IMsgboxService iMsgboxService = (IMsgboxService) defpackage.esb.a.a.a(IMsgboxService.class);
        if (iMsgboxService != null) {
            iMsgboxService.reset();
        }
        LocationInstrument.getInstance().setMapRect(null);
        LocationInstrument.getInstance().release();
        dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null) {
            dfm.d();
        }
        cei.a = false;
        cdd.n().e(false);
        return true;
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (!this.q || !a(i2, keyEvent)) {
            return super.onKeyDown(i2, keyEvent);
        }
        return true;
    }

    private void onConfigurationChangedOver(Configuration configuration) {
        cdd.n().a(configuration.orientation == 2);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.q) {
            onConfigurationChangedOver(configuration);
        }
    }

    public final void b(Intent intent) {
        this.h.a(intent);
    }

    public final void a(@NonNull Intent intent, @Nullable Callback<Boolean> callback) {
        this.h.a(intent, callback);
    }

    public final boolean i() {
        return this.m;
    }

    public static boolean j() {
        return y;
    }

    private void onDestroyOver() {
        ckb.c();
        AjxInit.destroy();
        y = false;
        anm.a(false);
        avv.a(false);
        this.j.removeCallbacksAndMessages(null);
        if (this.f != null) {
            this.f.getOverlayManager().removeWhenMapDestroy();
            cdd.n().e(false);
            this.f.release();
            epu.a();
            bty mapView = this.f.getMapView();
            if (mapView != null) {
                mapView.a(( amw ) null);
            }
        }
        bec bec = (bec) ank.a(bec.class);
        if (bec != null) {
            bea a2 = bec.a();
            if (a2 != null) {
                a2.c();
            }
        }
        cdd.n().m();
        Ajx3UpgradeManager.getInstance().cancel();
        n();
        if (this.L) {
            unregisterReceiver(this.A);
        }
        this.L = false;
        if (!this.n) {
            LocationInstrument.getInstance().setMapRect(null);
            LocationInstrument.getInstance().release();
            jh jhVar = this.i;
            if (jhVar.a != null) {
                AppUpgradeController appUpgradeController = jhVar.a;
                try {
                    appUpgradeController.c = true;
                    AMapAppGlobal.getApplication().unregisterReceiver(appUpgradeController.d);
                    jg a3 = jg.a();
                    if (a3.a != null) {
                        for (jk next : a3.a.values()) {
                            if (next.f != null) {
                                yq.a();
                                yq.a(( bph ) next.f);
                                next.d = false;
                            }
                            if (next.g != null) {
                                next.g.c();
                            }
                        }
                        a3.a.clear();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (this.t) {
                unregisterReceiver(this.B);
            }
            this.t = false;
            OverlayTexureCacheUtil.clearTextureCache();
            ccm.b.a((Context) ccm.a().c);
            if (this.b != null) {
                if (this.b.unInit() || !bno.a) {
                    DoNotUseTool.setMapVirtualizationPageService(null);
                    IPageService.destroyS(this.b);
                    this.b = null;
                } else {
                    throw new IllegalStateException("com.autonavi.jni.ae.bl.map.IPageService unInit error");
                }
            }
            this.x = null;
            ceg ceg = (ceg) ank.a(ceg.class);
            if (ceg != null) {
                ceg.a(false);
            }
            StatusBarManager.d().b();
            UiThreadWrapper.getInstance().onUiDestroyed();
            if (this.atmapsView != null) {
                this.atmapsView.destroyMapView();
            }
            if (this.f != null) {
                bty mapView2 = this.f.getMapView();
                if (mapView2 != null) {
                    mapView2.ac();
                }
            }
            NaviEngine.unInit();
            NaviEngine.destroy();
            UiThreadWrapper.uninit();
            dfm dfm = (dfm) ank.a(dfm.class);
            if (dfm != null) {
                dfm.e();
            }
            AEUtil.unInit();
        }
    }

    public final void e() {
        if (!this.I.a) {
            new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents).remove("afp_again_launch_splash_time");
            if (this.q) {
                onDestroyOver();
            }
            la.h();
            ekp.a().c();
            ekp a2 = ekp.a();
            a2.c = null;
            a2.a = null;
            a2.b = null;
            if (this.E != null) {
                this.E.a();
            }
            try {
                cth cth = defpackage.cth.a.a;
                if (AMapAppGlobal.getApplication().getApplicationInfo().targetSdkVersion >= 26) {
                    Application application = MapApplication.getApplication();
                    if (cth.a != null) {
                        application.unregisterReceiver(cth.a);
                    }
                    if (cth.b != null) {
                        application.unregisterReceiver(cth.b);
                    }
                    if (cth.c != null) {
                        application.unregisterReceiver(cth.c);
                    }
                    if (cth.d != null) {
                        application.unregisterReceiver(cth.d);
                    }
                    if (cth.e != null) {
                        application.unregisterReceiver(cth.e);
                    }
                }
            } catch (Error | Exception e2) {
                AMapLog.error("paas.main", "NewMapActivity", "unRegisterReceiver error:".concat(String.valueOf(e2.getMessage())));
            }
        }
    }

    private void r() {
        if ( agq.b(getApplication())) {
            IMapEventListener iMapEventListener = (IMapEventListener) ank.a(IMapEventListener.class);
            if (iMapEventListener != null) {
                iMapEventListener.a(this);
            }
        }
    }

    /* access modifiers changed from: private */
    public void s() {
        try {
            if (!this.l) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                registerReceiver(this.z, intentFilter);
                this.l = true;
            }
        } catch (Exception e2) {
            if (bno.a) {
                AMapLog.e("NewMapActivity", Log.getStackTraceString(e2));
            }
        }
        LocationInstrument.getInstance().addHighFrequencyStatusCallback(this.u, this);
    }

    private static void c(Intent intent) {
        if (intent != null && (intent.getFlags() & 1048576) == 1048576) {
            intent.setData(null);
            intent.setAction("");
            intent.putExtras(new Bundle());
        }
    }

    public final void a(defpackage.kj.b bVar) {
        this.o = bVar;
    }

    public final void b(defpackage.kj.b bVar) {
        this.p = bVar;
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        if (this.E != null) {
            this.E.a(i2, strArr, iArr);
        }
        if (i2 == 1) {
            ArrayList arrayList = new ArrayList();
            boolean z2 = false;
            for (int i3 = 0; i3 < strArr.length; i3++) {
                if (iArr[i3] != 0) {
                    arrayList.add(strArr[i3]);
                }
            }
            if (arrayList.size() > 0) {
                ToastHelper.showLongToast(kj.a((List<String>) arrayList));
            } else if (strArr.length != 0) {
                z2 = true;
            }
            if (this.o != null) {
                defpackage.kj.b bVar = this.o;
                this.o = null;
                bVar.callback(z2);
            }
        }
    }

    private void d(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();
        if ("android.intent.action.SEND".equals(action) && type != null && "text/plain".equals(type)) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                ((apr) defpackage.esb.a.a.a(apr.class)).b(pageContext);
            } else {
                p();
            }
            e(intent);
        }
    }

    private void e(Intent intent) {
        String stringExtra = intent.getStringExtra("android.intent.extra.TEXT");
        if (!TextUtils.isEmpty(stringExtra)) {
            Intent intent2 = new Intent("android.intent.action.VIEW");
            StringBuilder sb = new StringBuilder("amapuri://search/general?keyword=");
            sb.append(stringExtra);
            StringBuilder sb2 = new StringBuilder(sb.toString());
            String stringExtra2 = intent.getStringExtra(DriveUtil.SOURCE_APPLICATION);
            if (!TextUtils.isEmpty(stringExtra2)) {
                sb2.append("&sourceApplication=");
                sb2.append(stringExtra2);
            }
            intent2.setData(Uri.parse(sb2.toString()));
            intent2.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
            b(intent2);
        }
    }

    public final boolean k() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.widget_container);
        if (viewGroup != null && viewGroup.getChildCount() >= 2 && viewGroup.getChildAt(0).getVisibility() == 0) {
            return true;
        }
        return false;
    }

    public void onWindowAttributesChanged(LayoutParams layoutParams) {
        super.onWindowAttributesChanged(layoutParams);
        this.G.onWindowAttributesChanged(layoutParams);
    }

    private void init() {
        this.x = new ActivityStateDispatch();
        this.s = new b(this, 0);
        this.A = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent) {
                if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    NativeSupport.getInstance().getNetworkMonitor().onConnectionChanged(((ConnectivityManager) MapApplication.getContext().getSystemService("connectivity")).getActiveNetworkInfo());
                }
            }
        };
        this.B = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.autonavi.minimap")) {
                    String stringExtra = intent.getStringExtra("NAVI");
                    if (stringExtra != null && "APP_EXIT".equals(stringExtra)) {
                        NewMapActivity.this.a_();
                        NewMapActivity.this.finish();
                    }
                }
            }
        };
        this.z = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("android.intent.action.SCREEN_OFF")) {
                    if (!NewMapActivity.l()) {
                        LocationInstrument.getInstance().doStopLocate();
                    }
                } else if (action.equals("android.intent.action.SCREEN_ON") && ahp.a(NewMapActivity.this.getApplicationContext())) {
                    LocationInstrument.getInstance().doStartLocate();
                }
            }
        };
        n();
        this.C = new defpackage.drn.c() {
            @Override
            public final void c() {
            }

            @Override
            public final void a() {
                Application application = AMapAppGlobal.getApplication();
                if ((application instanceof MapApplication) && ((MapApplication) application).getAliveActivityCount() > 1) {
                    LocationInstrument.getInstance().doStartLocate();
                    epp.a().a(3);
                }
                awh awh = (awh) defpackage.esb.a.a.a(awh.class);
                if (awh != null) {
                    awh.a(false);
                }
            }

            public final void b() {
                if (!NewMapActivity.l()) {
                    LocationInstrument.getInstance().doStopLocate();
                }
                awh awh = (awh) defpackage.esb.a.a.a(awh.class);
                if (awh != null) {
                    awh.a(true);
                }
            }
        };
        drm.a((defpackage.drn.a) this.C);
        euk.b((Activity) this);
        this.q = true;
        y = true;
        anm.a(true);
        StatusBarManager.d();
        ccm a2 = ccm.a();
        if (ccm.a == null) {
            ccm.a = new ccp();
        }
        ccm.a.a(a2.c);
        la.a((String) "startProcessActivityCreateTask");
        akq.a("test".equals(ConfigerHelper.getInstance().getMapNetCondition()));
        akq.a(( alq ) new brk());
        la.a((String) "setGLMapViewMode");
        setContentView(R.layout.new_map_activity);
        this.fl_content_view = (FrameLayout) findViewById(R.id.fl_content_view);
        la.i();
        this.atmapsView = (GLMapViewGroup) findViewById(R.id.atmapsView);
        AMapSurface aMapSurface = this.atmapsView.getAMapSurface();
        akq amapController = this.atmapsView.getAmapController();
        boolean equals = "test".equals(ConfigerHelper.getInstance().getMapNetCondition());
        akq.b("setDebugMode: ".concat(String.valueOf(equals)));
        anb.a = equals;
        this.f = new MapManager(this, amapController, aMapSurface, this.atmapsView.getScreenshotImageView());
        DoNotUseTool.setMapManager(this.f);
        this.atmapsView.setMapView(this.f.getMapView());
        if (this.fl_content_view != null) {
            ViewGroup viewGroup = (ViewGroup) this.fl_content_view.findViewById(R.id.widget_container_root);
            Stub.getMapWidgetManager().initialize(this.fl_content_view, (LinearLayout) viewGroup.findViewById(R.id.msg_state_bar), (ViewGroup) viewGroup.findViewById(R.id.widget_container));
        }
        la.a((String) "initGLMapView");
        this.g = new cde(this, this.f);
        DoNotUseTool.setSuspendManager(this.g);
        la.a((String) "SuspendManager");
        ckc.a(getString(R.string.engine_initialization), SystemClock.elapsedRealtime() - this.elapsedRealtime );
        this.elapsedRealtime = SystemClock.elapsedRealtime();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment next : fragments) {
                if (next != null) {
                    beginTransaction.remove(next);
                }
            }
        }
        beginTransaction.commit();
        but.a = new d();
        but.b = new f();
        but.c = new a();
        but.e = new g();
        but.d = new e();
        but.f = new h();
        a((FrameLayout) findViewById(R.id.fragment_container));
        AMapPageUtil.setMvpActivityContext(this.a);
        a(this.a);
        this.b = IPageService.createS();
        if (this.b == null) {
            if (bno.a) {
                throw new IllegalStateException("com.autonavi.jni.ae.bl.map.IPageService.createS return null");
            }
        } else if (this.b.init()) {
            DoNotUseTool.setMapVirtualizationPageService(new bud(this.b));
        } else if (bno.a) {
            throw new IllegalStateException("com.autonavi.jni.ae.bl.map.IPageService init error");
        }
        la.a((String) "initFragmentContainer");
        p();
        la.a((String) "setupDefaultFragment");
        this.u = new LocatorCallback(this.g.d().h(), this.f);
        this.f.getMapView().a(( amm ) new amm() {
            public final void onRenderDeviceCreated(int i) {
            }

            public final void onRenderDeviceDestroyed(int i) {
            }

            public final void onSurfaceRenderFrame(int i, int i2) {
            }

            public final void onSurfaceCreated(int i) {
                ckb.a((String) "NewMapActivity#onSurfaceCreated");
                if (NewMapActivity.this.atmapsView != null) {
                    NewMapActivity.this.atmapsView.clearMapViewBackground(true);
                }
                emv.d();
                if (NewMapActivity.this.f != null) {
                    if (!NewMapActivity.this.m) {
                        LocationInstrument.getInstance().doStartLocate();
                        NewMapActivity.this.s();
                        NewMapActivity.this.u.execPendingActions();
                    }
                    NewMapActivity.this.f.setMapSurfaceCreated(true);
                }
                NewMapActivity.this.r = true;
                NewMapActivity.this.getWindow().getDecorView().setBackgroundColor(-1182466);
                NewMapActivity.this.getWindow().setBackgroundDrawable(null);
                NewMapActivity.this.f.getMapView().s( brv.a());
            }

            public final void onSurfaceChanged(int i, int i2, int i3, int i4) {
                emv.e();
                bid isInstance = AMapPageUtil.isInstance(IMapPage.class, AMapPageUtil.getPageContext());
                if (isInstance != null && (isInstance instanceof IMapPage)) {
                    ((IMapPage) isInstance).onMapSurfaceChanged(i2, i3);
                }
            }

            public final void onSurfaceDestroy(int i) {
                if (NewMapActivity.this.f != null) {
                    NewMapActivity.this.f.setMapSurfaceCreated(false);
                }
                bid isInstance = AMapPageUtil.isInstance(IMapPage.class, AMapPageUtil.getPageContext());
                if (isInstance != null && (isInstance instanceof IMapPage)) {
                    ((IMapPage) isInstance).onMapSurfaceDestroy();
                }
                OverlayTexureCacheUtil.clearTextureCache();
                NewMapActivity.this.r = false;
                ceg ceg = (ceg) ank.a(ceg.class);
                if (ceg != null) {
                    ceg.a(false);
                }
            }

            public final void onDrawFrameFirst(int i) {
                ckb.a((String) "NewMapActivity#onDrawFrameFirst");
                cke.e();
                if (NewMapActivity.this.atmapsView != null) {
                    NewMapActivity.this.atmapsView.clearMapViewBackground(false);
                }
                Message obtainMessage = NewMapActivity.this.j.obtainMessage(0);
                obtainMessage.what = 0;
                NewMapActivity.this.j.sendMessageDelayed(obtainMessage, 10);
                NewMapActivity.m();
                System.gc();
            }

            public final void onDrawFrameFirstOnGLThread(int i) {
                ckb.a((String) "NewMapActivity#onDrawFrameFirstOnGLThread");
                cke.d();
                la.e();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                ckc.b(NewMapActivity.this.getString(R.string.map_display_time), elapsedRealtime - ckc.a);
                ckc.a(NewMapActivity.this.getString(R.string.map_display), elapsedRealtime - NewMapActivity.this.elapsedRealtime );
            }
        });
        AjxInit.initAjxBLEnvironment(this.f.getMapView());
        la.a((String) "initAjxBLEnvironment");
        if (AEUtil.IS_DEBUG && !this.s.a) {
            this.s = new b(this, 0);
            this.s.start();
        }
        bty mapView = this.f.getMapView();
        if (mapView != null) {
            mapView.a(avv.a());
        }
    }

    static /* synthetic */ void c(NewMapActivity newMapActivity) {
        Intent intent = newMapActivity.getIntent();
        newMapActivity.finish();
        super.startActivity(intent);
    }

    static /* synthetic */ boolean l() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null && (pageContext instanceof com.autonavi.common.impl.Locator.a) && ((com.autonavi.common.impl.Locator.a) pageContext).a()) {
            return true;
        }
        if (pageContext != null) {
            LocationPreference locationPreference = (LocationPreference) pageContext.getClass().getAnnotation(LocationPreference.class);
            if (locationPreference != null && locationPreference.availableOnBackground()) {
                return true;
            }
        }
        ISchoolbusStatusMangger iSchoolbusStatusMangger = (ISchoolbusStatusMangger) ank.a(ISchoolbusStatusMangger.class);
        if (iSchoolbusStatusMangger == null || !iSchoolbusStatusMangger.isNeedOnbackground()) {
            return false;
        }
        return true;
    }

    static /* synthetic */ void m() {
        ISplashManager iSplashManager = (ISplashManager) ank.a(ISplashManager.class);
        if (iSplashManager != null) {
            iSplashManager.updateSplashData(true);
        }
    }

    static /* synthetic */ void a(NewMapActivity newMapActivity, String str, final Runnable runnable, final Runnable runnable2) {
        mi miVar = new mi(newMapActivity);
        miVar.b = str;
        miVar.a(R.string.sure, new defpackage.mj.a() {
            public final void a(mi miVar) {
                miVar.a.dismiss();
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        miVar.b(R.string.cancel, new defpackage.mj.a() {
            public final void a(defpackage.mi miVar) {
                miVar.a.dismiss();
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        miVar.c = false;
        miVar.a();
        try {
            miVar.b();
        } catch (Exception e2) {
            DebugLog.error(e2);
        }
    }
}
