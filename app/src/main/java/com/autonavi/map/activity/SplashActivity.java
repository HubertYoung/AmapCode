package com.autonavi.map.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.amap.app.AMapBaseActivity;
import com.autonavi.minimap.MapApplication;
import com.autonavi.minimap.R;
import com.autonavi.minimap.app.CrashCleanHelper;
import com.autonavi.minimap.basemap.maphome.IVerifyUserService;
import com.autonavi.minimap.bundle.featureguide.api.GuideStartType;
import com.autonavi.minimap.bundle.splashscreen.api.ISplashManager;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

public class SplashActivity extends AMapBaseActivity implements ddp, defpackage.kj.a {
    cex a;
    b b = new b() {
        public final void run() {
            if (!SplashActivity.this.c) {
                SplashActivity.this.b();
            }
        }

        public final void reject() {
            if (!SplashActivity.this.c) {
                AnonymousClass1 r0 = new Runnable() {
                    public final void run() {
                        kj.a((Context) SplashActivity.this, SplashActivity.this.b);
                    }
                };
                StringBuilder sb = new StringBuilder();
                sb.append(AMapAppGlobal.getApplication().getString(R.string.permission_tip_write_settings2));
                sb.append(",");
                sb.append(AMapAppGlobal.getApplication().getString(R.string.permission_dialog_tip));
                SplashActivity.a(SplashActivity.this, sb.toString(), r0, SplashActivity.this.l);
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean c = false;
    private b d = null;
    private final Handler e = new a(this, 0);
    private ISplashManager f;
    private long g;
    private BroadcastReceiver h;
    private CrashCleanHelper i = new CrashCleanHelper(this);
    private buw j;
    private Runnable k = new Runnable() {
        public final void run() {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", SplashActivity.this.getPackageName(), null));
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
            Process.killProcess(Process.myPid());
        }
    };
    /* access modifiers changed from: private */
    public Runnable l = new Runnable() {
        public final void run() {
            SplashActivity.this.finish();
            Process.killProcess(Process.myPid());
        }
    };

    static class MultiInstReceiver extends BroadcastReceiver {
        private WeakReference<SplashActivity> a;

        public MultiInstReceiver(SplashActivity splashActivity) {
            this.a = new WeakReference<>(splashActivity);
        }

        public void onReceive(Context context, Intent intent) {
            SplashActivity splashActivity = (SplashActivity) this.a.get();
            if (splashActivity != null) {
                SplashActivity.a(splashActivity, intent);
            }
        }
    }

    static class a extends Handler {
        private WeakReference<SplashActivity> a;

        /* synthetic */ a(SplashActivity splashActivity, byte b) {
            this(splashActivity);
        }

        private a(SplashActivity splashActivity) {
            this.a = new WeakReference<>(splashActivity);
        }

        public final void handleMessage(Message message) {
            if (this.a != null) {
                SplashActivity splashActivity = (SplashActivity) this.a.get();
                if (splashActivity != null && !SplashActivity.b((Activity) splashActivity) && message.what == 0) {
                    MapApplication mapApplication = (MapApplication) splashActivity.getApplication();
                    if (mapApplication.isNewMapActivityFinished() || NewMapActivity.j()) {
                        splashActivity.c();
                        return;
                    }
                    mapApplication.registerHandler(this);
                }
            }
        }
    }

    public final void a(b bVar) {
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e2  */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"DM_EXIT"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r7) {
        /*
            r6 = this;
            java.lang.String r0 = "SplashActivity#onCreate"
            defpackage.ckb.a(r0)
            defpackage.la.i()
            defpackage.cke.b()
            boolean r0 = com.autonavi.minimap.MapApplication.isLaunchStartApp
            defpackage.cke.a(r0)
            boolean r0 = com.autonavi.minimap.MapApplication.isLaunchStartApp
            defpackage.zw.a(r0)
            super.onCreate(r7)
            com.autonavi.minimap.app.CrashCleanHelper r7 = r6.i
            boolean r7 = r7.a()
            if (r7 == 0) goto L_0x0021
            return
        L_0x0021:
            long r0 = android.os.SystemClock.elapsedRealtime()
            long r2 = java.lang.System.currentTimeMillis()
            r6.g = r2
            android.content.Intent r7 = new android.content.Intent
            java.lang.String r2 = "com.autonavi.minimap.action.splash_multi_inst"
            r7.<init>(r2)
            java.lang.String r2 = "startTime"
            long r3 = r6.g
            r7.putExtra(r2, r3)
            android.content.Context r2 = r6.getApplicationContext()
            android.support.v4.content.LocalBroadcastManager r2 = android.support.v4.content.LocalBroadcastManager.getInstance(r2)
            r2.sendBroadcast(r7)
            com.autonavi.map.activity.SplashActivity$MultiInstReceiver r7 = new com.autonavi.map.activity.SplashActivity$MultiInstReceiver
            r7.<init>(r6)
            r6.h = r7
            android.content.Context r7 = r6.getApplicationContext()
            android.support.v4.content.LocalBroadcastManager r7 = android.support.v4.content.LocalBroadcastManager.getInstance(r7)
            android.content.BroadcastReceiver r2 = r6.h
            android.content.IntentFilter r3 = new android.content.IntentFilter
            java.lang.String r4 = "com.autonavi.minimap.action.splash_multi_inst"
            r3.<init>(r4)
            r7.registerReceiver(r2, r3)
            boolean r7 = r6.isTaskRoot()
            if (r7 != 0) goto L_0x0083
            android.content.Intent r7 = r6.getIntent()
            if (r7 == 0) goto L_0x0083
            android.content.Intent r7 = r6.getIntent()
            int r7 = r7.getFlags()
            r2 = 4194304(0x400000, float:5.877472E-39)
            r7 = r7 & r2
            boolean r3 = r6.d()
            if (r3 == 0) goto L_0x0083
            if (r7 != r2) goto L_0x0083
            r6.finish()
            return
        L_0x0083:
            boolean r7 = defpackage.afu.a(r6)
            r2 = 0
            if (r7 == 0) goto L_0x0091
            r6.finish()
            java.lang.System.exit(r2)
            return
        L_0x0091:
            boolean r7 = defpackage.buw.a(r6)
            r3 = 1
            if (r7 == 0) goto L_0x00af
            int r7 = android.os.Build.VERSION.SDK_INT
            r4 = 23
            if (r7 < r4) goto L_0x00a6
            java.lang.String r7 = "android.permission.RECORD_AUDIO"
            int r7 = r6.checkSelfPermission(r7)
            if (r7 != 0) goto L_0x00a7
        L_0x00a6:
            r2 = 1
        L_0x00a7:
            if (r2 == 0) goto L_0x00af
            defpackage.kj.a = r3
            r6.b()
            goto L_0x00ba
        L_0x00af:
            com.autonavi.map.activity.SplashActivity$1 r7 = new com.autonavi.map.activity.SplashActivity$1
            r7.<init>()
            buw r7 = defpackage.buw.a(r6, r7)
            r6.j = r7
        L_0x00ba:
            com.amap.bundle.mapstorage.MapSharePreference r7 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r2 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r7.<init>(r2)
            java.lang.String r2 = "hardware_log_version_name"
            java.lang.String r4 = ""
            java.lang.String r2 = r7.getStringValue(r2, r4)
            java.lang.String r4 = "hardware_log_version_code"
            r5 = -1
            r7.getIntValue(r4, r5)
            ahp$a r4 = defpackage.ahp.a.a()
            java.lang.String r4 = r4.a
            ahp$a r5 = defpackage.ahp.a.a()
            int r5 = r5.b
            boolean r2 = r2.equals(r4)
            r2 = r2 ^ r3
            if (r2 == 0) goto L_0x00f8
            java.lang.String r2 = "hardware_log_version_name"
            r7.putStringValue(r2, r4)
            java.lang.String r2 = "hardware_log_version_code"
            r7.putIntValue(r2, r5)
            bns r7 = new bns
            android.content.Context r2 = r6.getApplicationContext()
            r7.<init>(r2)
            r7.a(r3)
        L_0x00f8:
            long r2 = defpackage.ckc.a()
            long r4 = android.os.SystemClock.elapsedRealtime()
            long r4 = r4 - r0
            long r2 = r2 + r4
            int r7 = com.autonavi.minimap.R.string.entry_application
            java.lang.String r7 = r6.getString(r7)
            defpackage.ckc.a(r7, r2)
            android.app.Application r7 = r6.getApplication()
            com.autonavi.common.tool.CrashLogUtil.initAppLunchForeground(r7)
            defpackage.la.i()
            defpackage.euk.b(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.activity.SplashActivity.onCreate(android.os.Bundle):void");
    }

    public void onPause() {
        if (this.i.a) {
            super.onPause();
            return;
        }
        if (this.f != null) {
            this.f.setSplashOnPause(true);
        }
        super.onPause();
    }

    public void onResume() {
        if (this.i.a) {
            super.onResume();
            return;
        }
        super.onResume();
        MapSharePreference mapSharePreference = new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents);
        if (mapSharePreference.getIntValue("afp_splash_first_launch_time", -1) == -1 && mapSharePreference.getIntValue("afp_splash_first_launch_wait_time_of_year", -1) == -1) {
            Calendar instance = Calendar.getInstance(Locale.CHINA);
            int parseInt = Integer.parseInt(String.format("%02d", new Object[]{Integer.valueOf(instance.get(6))}));
            int parseInt2 = Integer.parseInt(String.format("%02d", new Object[]{Integer.valueOf(instance.get(1))}));
            mapSharePreference.putIntValue("afp_splash_first_launch_time", parseInt);
            mapSharePreference.putIntValue("afp_splash_first_launch_wait_time_of_year", parseInt2);
        }
        la.i();
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        if (this.i.a) {
            super.onRestart();
            return;
        }
        super.onRestart();
        if (this.f != null) {
            this.f.setSplashOnRestart(true);
        }
    }

    public void onStop() {
        super.onStop();
        if (b((Activity) this)) {
            this.e.removeCallbacksAndMessages(null);
        }
    }

    public void onDestroy() {
        if (this.i.a) {
            super.onDestroy();
            return;
        }
        if (this.h != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.h);
        }
        if (this.f != null) {
            this.f.setSplashOnDestroy(true);
        }
        try {
            ahx.a(getWindow().peekDecorView());
        } catch (Throwable unused) {
        }
        this.e.removeCallbacksAndMessages(null);
        if (this.j != null) {
            this.j.a();
        }
        super.onDestroy();
    }

    public final void b(b bVar) {
        this.d = bVar;
    }

    public final void a() {
        e();
    }

    /* access modifiers changed from: private */
    public void b() {
        if (!this.c) {
            this.c = true;
            emy.a(getIntent());
            MapSharePreference mapSharePreference = new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents);
            boolean booleanValue = mapSharePreference.getBooleanValue("afp_splash_again_show", false);
            if (booleanValue || !NewMapActivity.j()) {
                if (this.f == null) {
                    this.f = (ISplashManager) ank.a(ISplashManager.class);
                }
                if (this.f != null) {
                    this.f.setSplashActivity(this);
                    this.f.setSplashActivityFragmentManager(getSupportFragmentManager());
                    this.f.setMapStartListener(this);
                    if (this.f.getIsGuideViewCreated()) {
                        this.f.initUserGuideSplash(GuideStartType.DEFAULT);
                        cke.a((String) "1");
                        return;
                    }
                    cey cey = new cey();
                    if (!cey.a()) {
                        if (this.a == null) {
                            this.a = new cex(this, cey);
                        }
                        this.a.a();
                        cex.a = new defpackage.cex.a() {
                            public final void a() {
                                SplashActivity.this.e();
                            }
                        };
                        a(false);
                    } else if (!a(bno.f)) {
                        getSharedPreferences("data", 0).edit().putString("date", "null").apply();
                        this.f.initUserGuideSplash(GuideStartType.DEFAULT);
                        cke.a((String) "1");
                    } else if (mapSharePreference.getIntValue(FunctionSupportConfiger.splash_show_source, 1) != 1) {
                        e();
                    } else if (d() || booleanValue) {
                        this.f.initAfpSplash();
                    } else {
                        e();
                    }
                } else {
                    e();
                }
            } else {
                c();
            }
        }
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (this.d != null && i2 == 1701) {
            boolean z = true;
            if (!kj.a((Context) this)) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.permission_tip_write_settings));
                z = false;
            }
            if (this.d != null) {
                this.d.callback(z);
            }
            this.d = null;
        }
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 4) {
            return super.onKeyDown(i2, keyEvent);
        }
        Process.killProcess(Process.myPid());
        return true;
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, int[] iArr) {
        if (this.j != null) {
            this.j.a(i2, strArr, iArr);
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        IVerifyUserService iVerifyUserService;
        Intent intent;
        if (f()) {
            awh awh = (awh) defpackage.esb.a.a.a(awh.class);
            if (awh != null && awh.b(g())) {
                new dli(this).b(getIntent());
                finish();
                return;
            }
        }
        if (bno.a) {
            iVerifyUserService = new bqz();
        } else if (bno.c) {
            iVerifyUserService = new bqy();
        } else {
            iVerifyUserService = new bqz();
        }
        getApplicationContext();
        int a2 = iVerifyUserService.a();
        if (a2 == 1 || a2 == 2) {
            if (d()) {
                intent = new Intent(getApplicationContext(), NewMapActivity.class);
            } else {
                intent = new Intent(getIntent());
                intent.setClass(getApplicationContext(), NewMapActivity.class);
                if (getIntent() != null && (getIntent().getFlags() & 1048576) == 1048576) {
                    intent.setData(null);
                    intent.setAction("");
                    intent.putExtras(new Bundle());
                }
            }
            try {
                getWindow().setFlags(2048, 2048);
                la.i();
                startActivity(intent);
                ckb.a((String) "SplashActivity#startActivity");
            } catch (ActivityNotFoundException unused) {
            }
            ckb.a((String) "SplashActivity#finish");
            finish();
        }
    }

    private boolean d() {
        Intent intent = getIntent();
        if (intent == null) {
            return false;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action) || !"android.intent.action.MAIN".equals(action)) {
            return false;
        }
        Set<String> categories = intent.getCategories();
        if (categories == null || categories.isEmpty()) {
            return false;
        }
        for (String equals : categories) {
            if ("android.intent.category.LAUNCHER".equals(equals)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void e() {
        ckb.a((String) "SplashActivity#intoNewMap");
        this.e.removeMessages(0);
        Message obtainMessage = this.e.obtainMessage();
        obtainMessage.what = 0;
        this.e.sendMessage(obtainMessage);
    }

    /* access modifiers changed from: private */
    public static boolean b(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return true;
        }
        if (VERSION.SDK_INT >= 17) {
            return activity.isDestroyed();
        }
        return false;
    }

    private static boolean a(boolean z) {
        boolean z2;
        if (z) {
            SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
            boolean z3 = sharedPrefs.getBoolean("isSplashNeedShow", false);
            if (!z3) {
                return z3;
            }
            int i2 = sharedPrefs.getInt("versionCode", -1);
            String string = sharedPrefs.getString("versionName", "");
            if (bno.a) {
                z2 = sharedPrefs.getBoolean("lottieDebug", false);
            } else {
                z2 = false;
            }
            if (z2 || i2 != defpackage.ahp.a.a().b || a(string, defpackage.ahp.a.a().a)) {
                return false;
            }
            return z3;
        }
        SharedPreferences sharedPrefs2 = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
        String string2 = sharedPrefs2.getString("versionName", "");
        int i3 = sharedPrefs2.getInt("versionCode", -1);
        if (sharedPrefs2.getBoolean("isSplashNeedShow", false) && i3 == defpackage.ahp.a.a().b && string2.equals(defpackage.ahp.a.a().a)) {
            return true;
        }
        Editor edit = sharedPrefs2.edit();
        edit.putBoolean("isSplashNeedShow", true);
        edit.putInt("versionCode", defpackage.ahp.a.a().b);
        edit.putString("versionName", defpackage.ahp.a.a().a);
        edit.apply();
        return true;
    }

    private static boolean a(String str, String str2) {
        int lastIndexOf = str.lastIndexOf(".");
        return lastIndexOf < 0 || !str.substring(0, lastIndexOf).equals(str2.substring(0, lastIndexOf));
    }

    private boolean f() {
        Intent intent = getIntent();
        if (intent == null) {
            return false;
        }
        String dataString = intent.getDataString();
        if (TextUtils.isEmpty(dataString)) {
            return false;
        }
        return dataString.startsWith("amapuri://applets/platformapi/apserviceresult");
    }

    private String g() {
        Intent intent = getIntent();
        if (intent == null) {
            return "";
        }
        Uri data = intent.getData();
        if (data == null) {
            return "";
        }
        return data.getQueryParameter("amapAppId");
    }

    static /* synthetic */ void a(SplashActivity splashActivity, String str, final Runnable runnable, final Runnable runnable2) {
        mi miVar = new mi(splashActivity);
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
            public final void a(mi miVar) {
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

    static /* synthetic */ void a(SplashActivity splashActivity, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equals("com.autonavi.minimap.action.splash_multi_inst")) {
                long longExtra = intent.getLongExtra("startTime", 0);
                if (longExtra > 0 && longExtra != splashActivity.g) {
                    splashActivity.finish();
                }
            }
        }
    }
}
