package com.autonavi.minimap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.autonavi.amap.app.BaseMapApplication;
import com.autonavi.map.activity.NewMapActivity;
import com.autonavi.minimap.multidexload.MdLoadingActivity;

import defpackage.afu;
import defpackage.ckz;
import defpackage.drl;
import defpackage.drn;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import proguard.annotation.KeepName;

public class MapApplication extends BaseMapApplication {
    static final int TargetApi_Lifecycle = 14;
    private static Application app = null;
    @SuppressFBWarnings({"MS_CANNOT_BE_FINAL"})
    public static boolean isLaunchStartApp = false;
    private a mActivityLifecycleListener = new a();
    /* access modifiers changed from: private */
    public Handler mHandler;
    private ckz mLauncher;
    /* access modifiers changed from: private */
    public boolean mNewMapActivityDestroyed = true;

    @TargetApi(14)
    class a implements ActivityLifecycleCallbacks {
        int a;
        private int c;
        private final drn d = drl.a();

        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public a() {
        }

        private static boolean a(Activity activity) {
            return activity.getClass().equals(MdLoadingActivity.class);
        }

        public final void onActivityCreated(Activity activity, Bundle bundle) {
            if (activity instanceof NewMapActivity) {
                MapApplication.this.mNewMapActivityDestroyed = false;
            }
            if (!a(activity)) {
                this.a++;
                this.d.a(activity.getClass());
            }
        }

        public final void onActivityDestroyed(Activity activity) {
            if (activity instanceof NewMapActivity) {
                MapApplication.this.mNewMapActivityDestroyed = true;
                if (MapApplication.this.mHandler != null) {
                    Message obtainMessage = MapApplication.this.mHandler.obtainMessage();
                    obtainMessage.what = 0;
                    MapApplication.this.mHandler.sendMessage(obtainMessage);
                }
                MapApplication.this.mHandler = null;
            }
            if (!a(activity)) {
                this.a--;
                this.d.f(activity.getClass());
                if (this.a == 0) {
                    this.d.i(activity.getClass());
                }
            }
        }

        public final void onActivityResumed(Activity activity) {
            if (!a(activity)) {
                this.d.c(activity.getClass());
            }
        }

        public final void onActivityPaused(Activity activity) {
            if (!a(activity)) {
                this.d.d(activity.getClass());
            }
        }

        public final void onActivityStarted(Activity activity) {
            if (!a(activity)) {
                this.c++;
                this.d.b(activity.getClass());
                if (this.c == 1) {
                    this.d.g(activity.getClass());
                }
            }
        }

        public final void onActivityStopped(Activity activity) {
            if (!a(activity)) {
                this.c--;
                this.d.e(activity.getClass());
                if (this.c == 0 && !activity.isFinishing()) {
                    this.d.h(activity.getClass());
                }
            }
        }
    }

    public boolean isMainAppReady() {
        return true;
    }

    @KeepName
    public int mzNightModeUseOf() {
        return 0;
    }

    static {
        initAsyncTask();
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0126  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0128  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onApplicationCreate() {
        /*
            r13 = this;
            super.onApplicationCreate()
            r0 = 1
            isLaunchStartApp = r0
            app = r13
            ckz r1 = new ckz
            r1.<init>(r13)
            r13.mLauncher = r1
            defpackage.ckb.a(r13)
            r1 = 0
            defpackage.la.d(r1)
            ckz r1 = r13.mLauncher
            ckx r2 = new ckx
            r2.<init>()
            clb r3 = new clb
            r3.<init>()
            com.autonavi.minimap.app.init.Process[] r4 = new com.autonavi.minimap.app.init.Process[r0]
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.MAIN
            r6 = 0
            r4[r6] = r5
            ckx r2 = r2.a(r3, r4)
            clq r3 = new clq
            r3.<init>()
            com.autonavi.minimap.app.init.Process[] r4 = new com.autonavi.minimap.app.init.Process[r0]
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.MAIN
            r4[r6] = r5
            ckx r2 = r2.a(r3, r4)
            ckq r3 = new ckq
            r3.<init>()
            com.autonavi.minimap.app.init.Process[] r4 = new com.autonavi.minimap.app.init.Process[r0]
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.MAIN
            r4[r6] = r5
            ckx r2 = r2.a(r3, r4)
            ckp r3 = new ckp
            r3.<init>()
            com.autonavi.minimap.app.init.Process[] r4 = new com.autonavi.minimap.app.init.Process[r0]
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.MAIN
            r4[r6] = r5
            ckx r2 = r2.a(r3, r4)
            clk r3 = new clk
            r3.<init>()
            com.autonavi.minimap.app.init.Process[] r4 = new com.autonavi.minimap.app.init.Process[r0]
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.MAIN
            r4[r6] = r5
            ckx r2 = r2.a(r3, r4)
            cki r3 = new cki
            r3.<init>()
            com.autonavi.minimap.app.init.Process[] r4 = new com.autonavi.minimap.app.init.Process[r0]
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.MAIN
            r4[r6] = r5
            ckx r2 = r2.a(r3, r4)
            clf$d r3 = new clf$d
            r3.<init>()
            r4 = 5
            com.autonavi.minimap.app.init.Process[] r4 = new com.autonavi.minimap.app.init.Process[r4]
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.MAIN
            r4[r6] = r5
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.LOCATION
            r4[r0] = r5
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.PUSH
            r7 = 2
            r4[r7] = r5
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.LOTUSPOOL
            r7 = 3
            r4[r7] = r5
            com.autonavi.minimap.app.init.Process r5 = com.autonavi.minimap.app.init.Process.INSTALLERROR
            r8 = 4
            r4[r8] = r5
            ckx r2 = r2.a(r3, r4)
            com.autonavi.minimap.MapApplication r3 = r1.a
            r2.a(r3)
            com.autonavi.minimap.MapApplication r2 = r1.a
            boolean r3 = r2.isMainProcess()
            if (r3 == 0) goto L_0x0123
            java.lang.String r3 = "crash_record"
            android.content.SharedPreferences r2 = r2.getSharedPreferences(r3, r6)
            java.lang.String r3 = "crash_count"
            int r3 = r2.getInt(r3, r6)
            java.lang.String r4 = "launch_time"
            r8 = 0
            long r4 = r2.getLong(r4, r8)
            com.amap.bundle.mapstorage.MapSharePreference r10 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r11 = "dumpcrash_pref"
            r10.<init>(r11)
            java.lang.String r11 = "dumpcrash_starttime"
            java.lang.String r12 = "0"
            java.lang.String r10 = r10.getStringValue(r11, r12)
            long r10 = java.lang.Long.parseLong(r10)
            int r12 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r12 == 0) goto L_0x0105
            long r10 = r10 - r4
            r4 = 15000(0x3a98, double:7.411E-320)
            int r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r4 > 0) goto L_0x0105
            int r5 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r5 < 0) goto L_0x0105
            if (r4 > 0) goto L_0x0105
            int r3 = r3 + r0
            if (r3 < r7) goto L_0x00e5
            r4 = 1
            goto L_0x00e6
        L_0x00e5:
            r4 = 0
        L_0x00e6:
            android.content.SharedPreferences$Editor r5 = r2.edit()
            java.lang.String r7 = "crash_count"
            android.content.SharedPreferences$Editor r3 = r5.putInt(r7, r3)
            r3.apply()
            android.content.SharedPreferences$Editor r2 = r2.edit()
            java.lang.String r3 = "launch_time"
            long r7 = java.lang.System.currentTimeMillis()
            android.content.SharedPreferences$Editor r2 = r2.putLong(r3, r7)
            r2.apply()
            goto L_0x0124
        L_0x0105:
            android.content.SharedPreferences$Editor r3 = r2.edit()
            java.lang.String r4 = "crash_count"
            android.content.SharedPreferences$Editor r3 = r3.putInt(r4, r6)
            r3.apply()
            android.content.SharedPreferences$Editor r2 = r2.edit()
            java.lang.String r3 = "launch_time"
            long r4 = java.lang.System.currentTimeMillis()
            android.content.SharedPreferences$Editor r2 = r2.putLong(r3, r4)
            r2.apply()
        L_0x0123:
            r4 = 0
        L_0x0124:
            if (r4 == 0) goto L_0x0128
            r0 = 0
            goto L_0x012b
        L_0x0128:
            r1.a()
        L_0x012b:
            java.lang.String r1 = "end"
            defpackage.la.d(r1)
            if (r0 == 0) goto L_0x013b
            boolean r0 = r13.isMainProcess()
            if (r0 == 0) goto L_0x013b
            r13.registerActivityLifecycleListener()
        L_0x013b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.MapApplication.onApplicationCreate():void");
    }

    public static void initAsyncTask() {
        new AsyncTask<Void, Void, Void>() {
            /* access modifiers changed from: protected */
            public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
                return null;
            }
        };
    }

    public final void onTerminate() {
        super.onTerminate();
        onTerminateOver();
    }

    private void onTerminateOver() {
        unregisterActivityLifecycleListener();
        AlibcTradeSDK.destory();
    }

    public static boolean isDataFreeSpaceLow() {
        return afu.a((Context) getApplication()).a;
    }

    public boolean restartApp() {
        if (this.mLauncher == null) {
            this.mLauncher = new ckz(this);
        }
        this.mLauncher.a();
        super.restart();
        return true;
    }

    @KeepName
    public static Application getApplication() {
        return app;
    }

    @KeepName
    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public void registerHandler(Handler handler) {
        this.mHandler = handler;
    }

    public boolean isNewMapActivityFinished() {
        return this.mNewMapActivityDestroyed;
    }

    @TargetApi(14)
    public void registerActivityLifecycleListener() {
        if (VERSION.SDK_INT >= 14 && this.mActivityLifecycleListener != null) {
            registerActivityLifecycleCallbacks(this.mActivityLifecycleListener);
        }
    }

    @TargetApi(14)
    public void unregisterActivityLifecycleListener() {
        if (VERSION.SDK_INT >= 14 && this.mActivityLifecycleListener != null) {
            unregisterActivityLifecycleCallbacks(this.mActivityLifecycleListener);
        }
    }

    public int getAliveActivityCount() {
        return this.mActivityLifecycleListener.a;
    }
}
