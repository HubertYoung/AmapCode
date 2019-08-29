package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.util.Pair;
import android.webkit.CookieSyncManager;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.autonavi.ae.bl.map.IMapPageConstant;
import com.uc.webview.export.Build;
import com.uc.webview.export.cyclone.UCElapseTime;
import com.uc.webview.export.cyclone.UCHashMap;
import com.uc.webview.export.extension.ILocationManager;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.d;
import com.uc.webview.export.internal.interfaces.IGlobalSettings;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.UCMobileWebKit;
import com.uc.webview.export.internal.uc.CoreFactory;
import com.uc.webview.export.internal.uc.startup.StartupStats;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;
import com.uc.webview.export.utility.Utils;

/* compiled from: ProGuard */
public final class r extends UCSubSetupTask<r, r> {
    private static final boolean a = (VERSION.SDK_INT >= 14);
    private boolean b = false;

    /* compiled from: ProGuard */
    class a implements ILocationManager {
        private LocationManager b;

        public a(Context context) {
            this.b = (LocationManager) context.getSystemService("location");
        }

        public final void requestLocationUpdates(String str, long j, float f, LocationListener locationListener) {
            if (this.b != null) {
                this.b.requestLocationUpdates(str, j, f, locationListener);
            }
        }

        public final void requestLocationUpdatesWithUrl(String str, long j, float f, LocationListener locationListener, String str2) {
            if (this.b != null) {
                this.b.requestLocationUpdates(str, j, f, locationListener);
            }
        }

        public final void removeUpdates(LocationListener locationListener) {
            if (this.b != null) {
                this.b.removeUpdates(locationListener);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void a() {
        String str;
        boolean z;
        Log.d("InitTask", "runImpl");
        StartupTrace.a();
        StartupStats.a(13);
        UCElapseTime uCElapseTime = new UCElapseTime();
        Context context = (Context) this.mOptions.get(UCCore.OPTION_CONTEXT);
        boolean b2 = j.b((Boolean) this.mOptions.get(UCCore.OPTION_USE_SDK_SETUP));
        if (!b2) {
            CoreFactory.a(context);
            try {
                StartupTrace.a();
                CookieSyncManager.createInstance(context);
                StartupTrace.traceEventEnd("CookieSyncManager.createInstance");
            } catch (RuntimeException unused) {
            }
        }
        StartupTrace.a();
        boolean z2 = SDKFactory.i != -1 ? SDKFactory.i == 1 : !(!a || !Utils.checkSupportSamplerExternalOES());
        UCMobileWebKit initUCMobileWebKit = CoreFactory.initUCMobileWebKit(context, z2, false);
        d.a(CoreFactory.getUCMobileWebKit());
        StartupTrace.traceEventEnd("CoreFactory.initUCMobileWebKit");
        if (!b2) {
            boolean z3 = ((Integer) SDKFactory.invoke(UCMPackageInfo.makeDirDeleteFlg, new Object[0])).intValue() != -1 && ((Integer) SDKFactory.invoke(UCMPackageInfo.makeDirDeleteFlg, new Object[0])).intValue() == 1;
            if (!z2 && z3) {
                Log.e("InitTask", "UC Core not support Hardware accelerated.");
                z3 = false;
            }
            if (VERSION.SDK_INT < 14) {
                if (z3) {
                    StringBuilder sb = new StringBuilder("Video Hardware accelerated is supported start at api level 14 and now is ");
                    sb.append(VERSION.SDK_INT);
                    Log.e("InitTask", sb.toString());
                }
                CoreFactory.l().setBoolValue("video_hardward_accelerate", false);
            } else {
                CoreFactory.l().setBoolValue("video_hardward_accelerate", z3);
                if (z3) {
                    SDKFactory.invoke(10001, Long.valueOf(1048576));
                }
            }
            initUCMobileWebKit.setLocationManagerUC(new a(context));
            initUCMobileWebKit.invoke(401, new Object[]{j.b(this.mShellCL)});
            if (((Boolean) SDKFactory.invoke(10010, new Object[0])).booleanValue() && !SDKFactory.l && ((Integer) SDKFactory.invoke(SDKFactory.getCoreType, new Object[0])).intValue() == 2) {
                throw new UCSetupException(3016, String.format("Init success but disallow switch from android to [%d].", new Object[]{CoreFactory.getCoreType()}));
            }
        }
        int i = ((Boolean) UCMPackageInfo.invoke(10011, new Object[0])).booleanValue() ? 1 : this.mUCM.isSpecified ? 2 : 3;
        SDKFactory.o = i;
        SDKFactory.d = initUCMobileWebKit;
        SDKFactory.invoke(10021, Integer.valueOf(CoreFactory.getCoreType().intValue()));
        if (!b2) {
            SDKFactory.invoke(UCMPackageInfo.expectDirFile1S, new Object[0]);
            if (!this.mUCM.isSpecified) {
                if (((Boolean) SDKFactory.invoke(UCMPackageInfo.expectCreateDirFile2P, context, this.mUCM.pkgName)).booleanValue()) {
                    SDKFactory.invoke(10001, Long.valueOf(IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE));
                }
            }
            SDKFactory.invoke(UCMPackageInfo.deleteUCMSDKDir, this.mOptions);
            SDKFactory.invoke(10034, context);
            IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
            if (iGlobalSettings != null) {
                if (this.mOptions.containsKey(UCCore.OPTION_WAP_DENY)) {
                    Object obj = this.mOptions.get(UCCore.OPTION_WAP_DENY);
                    iGlobalSettings.setStringValue("SDKWapDeny", obj == null ? "" : obj.toString());
                    StringBuilder sb2 = new StringBuilder("initProxySettings: setStringValue: SDKWapDeny = ");
                    sb2.append(obj.toString());
                    Log.d("InitTask", sb2.toString());
                }
                if (this.mOptions.containsKey(UCCore.OPTION_UC_PROXY_ADBLOCK)) {
                    Object obj2 = this.mOptions.get(UCCore.OPTION_UC_PROXY_ADBLOCK);
                    if (obj2 instanceof String) {
                        str = "SDKAdBlock";
                        z = Boolean.parseBoolean((String) obj2);
                    } else {
                        str = "SDKAdBlock";
                        z = obj2 == null ? false : ((Boolean) obj2).booleanValue();
                    }
                    iGlobalSettings.setBoolValue(str, z);
                }
                iGlobalSettings.setStringValue("UBISiProfileId", Build.SDK_PROFILE_ID);
                iGlobalSettings.setStringValue("UBISiPrd", Build.SDK_PRD);
            }
        }
        if (((Boolean) SDKFactory.invoke(10052, new Object[0])).booleanValue()) {
            SDKFactory.a(false);
        }
        callbackStat(new Pair(IWaStat.SETUP_TASK_INIT, new UCHashMap().set("cnt", "1").set("data", b2 ? "ucm" : GlobalConstants.EXCEPTIONTYPE).set("cost_cpu", String.valueOf(uCElapseTime.getMilisCpu())).set("cost", String.valueOf(uCElapseTime.getMilis()))));
        callbackStat(new Pair(IWaStat.SETUP_SUCCESS_INITED, null));
        StartupStats.a(14);
        StartupTrace.traceEventEnd("InitTask.runImpl");
    }

    public final void run() {
        Pair<Integer, Object> a2;
        StartupTrace.traceEvent("InitTask.run");
        Boolean bool = (Boolean) getOption(UCCore.OPTION_INIT_IN_SETUP_THREAD);
        if (bool != null) {
            this.b = !bool.booleanValue();
        }
        if (!this.b) {
            a();
            return;
        }
        bx bxVar = new bx();
        synchronized (bxVar) {
            SDKFactory.invoke(UCMPackageInfo.compareVersion, new s(this, bxVar));
            a2 = bxVar.a(60000);
        }
        if (((Integer) a2.first).intValue() == 3) {
            setException(a2.second instanceof UCSetupException ? (UCSetupException) a2.second : new UCSetupException(4003, (Throwable) a2.second));
            return;
        }
        if (((Integer) a2.first).intValue() == 1) {
            setException(new UCSetupException(4024, (String) "Init timeout(60000ms)"));
        }
    }
}
