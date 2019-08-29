package defpackage;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.amap.bundle.appupgrade.InstallListener;
import com.amap.bundle.blutils.device.DeviceInfo;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.device.ConnectivityMonitor;
import com.autonavi.ae.AEUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.tool.CrashLogUtil;
import com.autonavi.inter.IMultipleServiceLoader;
import com.autonavi.map.mapinterface.IMapRequestManager;
import com.autonavi.minimap.MapApplication;
import com.autonavi.minimap.app.WakeupReceiver;
import com.autonavi.minimap.autosec.UTAnalyticsUtils;
import com.autonavi.minimap.nativesupport.platform.NativeSupport;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import com.autonavi.minimap.widget.ConfirmDlg;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.taobao.accs.EventReceiver;
import com.taobao.accs.ServiceReceiver;
import com.taobao.accs.common.Constants;
import com.taobao.agoo.AgooCommondReceiver;
import java.io.File;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
/* renamed from: ccp reason: default package */
/* compiled from: StartProcessImpl */
public final class ccp implements ccr {
    private static volatile boolean a = false;

    /* renamed from: ccp$a */
    /* compiled from: StartProcessImpl */
    static class a implements lp {
        private Context a;

        public final void onConfigCallBack(int i) {
        }

        public a(Context context) {
            this.a = context.getApplicationContext();
        }

        public final void onConfigResultCallBack(int i, String str) {
            lo.a().b("update_imei", this);
            ccp.b(this.a, str);
        }
    }

    public final void a(Context context) {
        la.i();
        if (bno.a && Environment.getExternalStorageState().equals("mounted")) {
            File file = new File(Environment.getExternalStorageDirectory(), "asan");
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
        }
        la.i();
        la.i();
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory != null && externalStorageDirectory.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(externalStorageDirectory.toString());
            sb.append("/autonavi/data/mapcache/vmap4tiles/");
            ahd.a(new File(sb.toString()));
        }
        la.i();
        AEUtil.init();
        la.i();
        LocationInstrument.getInstance().init();
        la.i();
        dll.a();
        la.i();
        ConnectivityMonitor.a().a(context);
        la.i();
        epw.a(context);
        la.i();
        bnu a2 = bnu.a();
        if (bnu.a == null) {
            defpackage.bnu.AnonymousClass2 r0 = new c() {
                public final void b() {
                }

                public final void c() {
                }

                public final void a() {
                    bnu.this.b();
                }
            };
            bnu.a = r0;
            drm.a((defpackage.drn.a) r0);
        }
        bnu.a().b();
        la.i();
        UTAnalyticsUtils.getInstance().initUT(AMapAppGlobal.getApplication());
        la.i();
        la.i();
    }

    public final void a(Activity activity, Application application) {
        BroadcastReceiver broadcastReceiver;
        BroadcastReceiver broadcastReceiver2;
        BroadcastReceiver broadcastReceiver3;
        BroadcastReceiver broadcastReceiver4;
        Activity activity2 = activity;
        final Application application2 = application;
        List<Class<? extends T>> loadServices = ((IMultipleServiceLoader) bqn.a(IMultipleServiceLoader.class)).loadServices(awa.class);
        if (loadServices != null) {
            for (Class newInstance : loadServices) {
                try {
                    ((awa) newInstance.newInstance()).a();
                } catch (Exception e) {
                    Exception exc = e;
                    if (bno.a) {
                        throw new IllegalArgumentException(exc);
                    }
                    exc.printStackTrace();
                }
            }
        }
        List<Class<? extends T>> loadServices2 = ((IMultipleServiceLoader) bqn.a(IMultipleServiceLoader.class)).loadServices(emb.class);
        if (loadServices2 != null) {
            for (Class newInstance2 : loadServices2) {
                try {
                    newInstance2.newInstance();
                } catch (Exception e2) {
                    if (bno.a) {
                        throw new IllegalArgumentException(e2);
                    }
                    e2.printStackTrace();
                }
            }
        }
        ahm.a(new Runnable() {
            public final void run() {
                LogManager.startInitLogTask();
                DeviceInfo.getInstance(application2).setStartTime();
                IMapRequestManager iMapRequestManager = (IMapRequestManager) ank.a(IMapRequestManager.class);
                if (iMapRequestManager != null) {
                    iMapRequestManager.authDevice(null, null, 0, 0, null);
                }
            }
        });
        ahm.a(new Runnable() {
            public final void run() {
                lu luVar = lt.a().d;
                if (luVar.j != null ? luVar.j.booleanValue() : true) {
                    try {
                        euv.a(application2, new String[]{"_imei", "_mac"});
                        euv.a((evp) new evp() {
                            public final void a() {
                                JSONObject jSONObject = new JSONObject();
                                try {
                                    jSONObject.put("type", "success");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogManager.actionLogV2(LogConstant.PAGE_THIRDPARTY_SENSUS_EVENT, "B002", jSONObject);
                            }

                            public final void b() {
                                JSONObject jSONObject = new JSONObject();
                                try {
                                    jSONObject.put("type", UploadDataResult.FAIL_MSG);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogManager.actionLogV2(LogConstant.PAGE_THIRDPARTY_SENSUS_EVENT, "B002", jSONObject);
                            }
                        });
                    } catch (Exception e) {
                        new StringBuilder("irsearch init fail+").append(e.getMessage());
                    }
                }
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) application2.getSystemService("connectivity");
        AgooCommondReceiver agooCommondReceiver = null;
        NativeSupport.getInstance().getNetworkMonitor().setNetworkStatus(connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null);
        if (afm.a == null) {
            afm.a = new afm();
        }
        afm afm = afm.a;
        boolean z = false;
        SharedPreferences sharedPreferences = activity2.getSharedPreferences("log2_sp", 0);
        if (!sharedPreferences.getBoolean("key_first_update", false)) {
            afm.b(activity2);
            Editor edit = sharedPreferences.edit();
            edit.putBoolean("key_first_update", true);
            edit.commit();
        } else {
            long j = sharedPreferences.getLong("key_pre_last_update_time", System.currentTimeMillis());
            if (!((System.currentTimeMillis() - j) / 86400000 >= 7) || !FunctionSupportConfiger.getInst().isLocalAppInfoActive()) {
                if ((System.currentTimeMillis() - j) / 86400000 >= 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ahk.a(AMapAppGlobal.getApplication()).getAbsolutePath());
                    sb.append("/log2_file_name.txt");
                    File file = new File(sb.toString());
                    if (file.exists() && NetworkReachability.b()) {
                        afn.a().a(file);
                    }
                }
            } else {
                afm.b(activity2);
            }
        }
        LogConstant.isLogOn = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(ConfirmDlg.SP_KEY_log_state, true);
        File file2 = new File(application.getFilesDir(), "locLog");
        if (file2.exists()) {
            new Thread(new Runnable(file2) {
                final /* synthetic */ File a;

                {
                    this.a = r1;
                }

                /* JADX WARNING: Removed duplicated region for block: B:47:0x00ad A[SYNTHETIC, Splitter:B:47:0x00ad] */
                /* JADX WARNING: Removed duplicated region for block: B:50:0x00b2 A[Catch:{ IOException -> 0x00bb }] */
                /* JADX WARNING: Removed duplicated region for block: B:57:0x00bf A[SYNTHETIC, Splitter:B:57:0x00bf] */
                /* JADX WARNING: Removed duplicated region for block: B:60:0x00c4 A[Catch:{ IOException -> 0x00cc }] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r12 = this;
                        r0 = 0
                        java.io.FileReader r1 = new java.io.FileReader     // Catch:{ Exception -> 0x00a4, all -> 0x009f }
                        java.io.File r2 = r12.a     // Catch:{ Exception -> 0x00a4, all -> 0x009f }
                        r1.<init>(r2)     // Catch:{ Exception -> 0x00a4, all -> 0x009f }
                        java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
                        r2.<init>(r1)     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
                    L_0x000d:
                        java.lang.String r0 = r2.readLine()     // Catch:{ Exception -> 0x0093 }
                        if (r0 == 0) goto L_0x0086
                        java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x0093 }
                        byte[] r0 = defpackage.agv.a(r0)     // Catch:{ Exception -> 0x0093 }
                        r3.<init>(r0)     // Catch:{ Exception -> 0x0093 }
                        java.lang.String r0 = "&"
                        java.lang.String[] r0 = r3.split(r0)     // Catch:{ Exception -> 0x0093 }
                        if (r0 == 0) goto L_0x000d
                        int r3 = r0.length     // Catch:{ Exception -> 0x0093 }
                        r4 = 3
                        if (r3 != r4) goto L_0x000d
                        org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0093 }
                        r3.<init>()     // Catch:{ Exception -> 0x0093 }
                        java.lang.String r4 = "type"
                        java.lang.String r5 = "1"
                        r3.put(r4, r5)     // Catch:{ Exception -> 0x0093 }
                        int r4 = r0.length     // Catch:{ Exception -> 0x0093 }
                        r5 = 0
                        r6 = 0
                    L_0x0037:
                        if (r6 >= r4) goto L_0x0080
                        r7 = r0[r6]     // Catch:{ Exception -> 0x0093 }
                        java.lang.String r8 = "="
                        java.lang.String[] r7 = r7.split(r8)     // Catch:{ Exception -> 0x0093 }
                        if (r7 == 0) goto L_0x007d
                        int r8 = r7.length     // Catch:{ Exception -> 0x0093 }
                        r9 = 2
                        if (r8 != r9) goto L_0x007d
                        r8 = r7[r5]     // Catch:{ Exception -> 0x0093 }
                        java.lang.String r9 = "time"
                        boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x0093 }
                        r9 = 1
                        if (r8 == 0) goto L_0x005a
                        java.lang.String r8 = "locateTime"
                        r7 = r7[r9]     // Catch:{ Exception -> 0x0093 }
                        r3.put(r8, r7)     // Catch:{ Exception -> 0x0093 }
                        goto L_0x007d
                    L_0x005a:
                        r8 = r7[r5]     // Catch:{ Exception -> 0x0093 }
                        java.lang.String r10 = "sourceApplication"
                        boolean r8 = r8.equals(r10)     // Catch:{ Exception -> 0x0093 }
                        if (r8 == 0) goto L_0x006c
                        java.lang.String r8 = "sourceApplication"
                        r7 = r7[r9]     // Catch:{ Exception -> 0x0093 }
                        r3.put(r8, r7)     // Catch:{ Exception -> 0x0093 }
                        goto L_0x007d
                    L_0x006c:
                        r8 = r7[r5]     // Catch:{ Exception -> 0x0093 }
                        java.lang.String r10 = "reqType"
                        boolean r8 = r8.equals(r10)     // Catch:{ Exception -> 0x0093 }
                        if (r8 == 0) goto L_0x007d
                        java.lang.String r8 = "locateType"
                        r7 = r7[r9]     // Catch:{ Exception -> 0x0093 }
                        r3.put(r8, r7)     // Catch:{ Exception -> 0x0093 }
                    L_0x007d:
                        int r6 = r6 + 1
                        goto L_0x0037
                    L_0x0080:
                        r0 = 4003(0xfa3, float:5.61E-42)
                        com.amap.bundle.statistics.LogManager.actionLog(r0, r5, r3)     // Catch:{ Exception -> 0x0093 }
                        goto L_0x000d
                    L_0x0086:
                        r1.close()     // Catch:{ Exception -> 0x0093 }
                        r2.close()     // Catch:{ Exception -> 0x0093 }
                        java.io.File r0 = r12.a     // Catch:{ IOException -> 0x0092 }
                        r0.delete()     // Catch:{ IOException -> 0x0092 }
                        return
                    L_0x0092:
                        return
                    L_0x0093:
                        r0 = move-exception
                        goto L_0x00a8
                    L_0x0095:
                        r2 = move-exception
                        r11 = r2
                        r2 = r0
                        r0 = r11
                        goto L_0x00bd
                    L_0x009a:
                        r2 = move-exception
                        r11 = r2
                        r2 = r0
                        r0 = r11
                        goto L_0x00a8
                    L_0x009f:
                        r1 = move-exception
                        r2 = r0
                        r0 = r1
                        r1 = r2
                        goto L_0x00bd
                    L_0x00a4:
                        r1 = move-exception
                        r2 = r0
                        r0 = r1
                        r1 = r2
                    L_0x00a8:
                        r0.printStackTrace()     // Catch:{ all -> 0x00bc }
                        if (r1 == 0) goto L_0x00b0
                        r1.close()     // Catch:{ IOException -> 0x00bb }
                    L_0x00b0:
                        if (r2 == 0) goto L_0x00b5
                        r2.close()     // Catch:{ IOException -> 0x00bb }
                    L_0x00b5:
                        java.io.File r0 = r12.a     // Catch:{ IOException -> 0x00bb }
                        r0.delete()     // Catch:{ IOException -> 0x00bb }
                        return
                    L_0x00bb:
                        return
                    L_0x00bc:
                        r0 = move-exception
                    L_0x00bd:
                        if (r1 == 0) goto L_0x00c2
                        r1.close()     // Catch:{ IOException -> 0x00cc }
                    L_0x00c2:
                        if (r2 == 0) goto L_0x00c7
                        r2.close()     // Catch:{ IOException -> 0x00cc }
                    L_0x00c7:
                        java.io.File r1 = r12.a     // Catch:{ IOException -> 0x00cc }
                        r1.delete()     // Catch:{ IOException -> 0x00cc }
                    L_0x00cc:
                        throw r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.ccn.AnonymousClass1.run():void");
                }
            }).start();
        }
        if (bim.aa().k((String) "202")) {
            activity.getWindow().addFlags(128);
        } else {
            activity.getWindow().clearFlags(128);
        }
        ahm.a(new Runnable() {
            public final void run() {
                ccp.a(application2);
                ccp.a();
            }
        });
        if (MapApplication.isLaunchStartApp) {
            MapApplication.isLaunchStartApp = false;
        }
        cth cth = defpackage.cth.a.a;
        if (AMapAppGlobal.getApplication().getApplicationInfo().targetSdkVersion >= 26) {
            int i = VERSION.SDK_INT;
            if (i >= 26) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                intentFilter.addDataScheme("package");
                broadcastReceiver = new InstallListener();
                MapApplication.getApplication().registerReceiver(broadcastReceiver, intentFilter);
            } else {
                broadcastReceiver = null;
            }
            cth.a = broadcastReceiver;
            if (i >= 24) {
                IntentFilter intentFilter2 = new IntentFilter();
                broadcastReceiver2 = new WakeupReceiver();
                intentFilter2.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                if (i >= 26) {
                    intentFilter2.addAction("android.intent.action.PHONE_STATE");
                }
                MapApplication.getApplication().registerReceiver(broadcastReceiver2, intentFilter2);
            } else {
                broadcastReceiver2 = null;
            }
            cth.b = broadcastReceiver2;
            if (i >= 24) {
                IntentFilter intentFilter3 = new IntentFilter();
                broadcastReceiver3 = new EventReceiver();
                intentFilter3.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                MapApplication.getApplication().registerReceiver(broadcastReceiver3, intentFilter3);
                if (i >= 26) {
                    IntentFilter intentFilter4 = new IntentFilter();
                    intentFilter4.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter4.addDataScheme("package");
                    MapApplication.getApplication().registerReceiver(broadcastReceiver3, intentFilter4);
                    IntentFilter intentFilter5 = new IntentFilter();
                    intentFilter5.addAction("android.intent.action.USER_PRESENT");
                    MapApplication.getApplication().registerReceiver(broadcastReceiver3, intentFilter5);
                }
            } else {
                broadcastReceiver3 = null;
            }
            cth.c = broadcastReceiver3;
            if (i >= 26) {
                broadcastReceiver4 = new ServiceReceiver();
                IntentFilter intentFilter6 = new IntentFilter();
                intentFilter6.addAction(Constants.ACTION_COMMAND);
                MapApplication.getApplication().registerReceiver(broadcastReceiver4, intentFilter6);
                IntentFilter intentFilter7 = new IntentFilter();
                intentFilter7.addAction(Constants.ACTION_START_FROM_AGOO);
                MapApplication.getApplication().registerReceiver(broadcastReceiver4, intentFilter7);
            } else {
                broadcastReceiver4 = null;
            }
            cth.d = broadcastReceiver4;
            if (i >= 26) {
                agooCommondReceiver = new AgooCommondReceiver();
                IntentFilter intentFilter8 = new IntentFilter();
                intentFilter8.addAction("com.autonavi.minimap.intent.action.COMMAND");
                MapApplication.getApplication().registerReceiver(agooCommondReceiver, intentFilter8);
                IntentFilter intentFilter9 = new IntentFilter();
                intentFilter9.addAction("android.intent.action.PACKAGE_REMOVED");
                intentFilter9.addDataScheme("package");
                MapApplication.getApplication().registerReceiver(agooCommondReceiver, intentFilter9);
            }
            cth.e = agooCommondReceiver;
        }
        aho.a(new Runnable() {
            public final void run() {
                fhb fhb = (fhb) defpackage.esb.a.a.a(fhb.class);
                if (fhb != null) {
                    fhb.a(MapApplication.getContext());
                }
            }
        }, 5000);
        CrashLogUtil.initAppLunchForeground(application);
        try {
            if (ContextCompat.checkSelfPermission(application2, "android.permission.READ_PHONE_STATE") == 0) {
                z = true;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (z) {
            String a2 = lo.a().a((String) "update_imei");
            if (TextUtils.isEmpty(a2)) {
                lo.a().a((String) "update_imei", (lp) new a(application2));
            } else {
                b(application2, a2);
            }
        }
        ajl a3 = ajl.a();
        if (a3.a == null) {
            a3.a = new lp() {
                public final void onConfigCallBack(int i) {
                }

                public final void onConfigResultCallBack(int i, String str) {
                    switch (i) {
                        case 0:
                        case 1:
                        case 4:
                            ajl.a(str);
                            break;
                        case 3:
                            ajl.c();
                            return;
                    }
                }
            };
            lo.a().a((String) "uc_options", a3.a);
            String a4 = lo.a().a((String) "uc_options");
            if (!TextUtils.isEmpty(a4)) {
                ajl.a(a4);
            }
        }
        new cll();
        SecurityGuardManager.getInitializer().initializeAsync(AMapAppGlobal.getApplication());
    }

    /* access modifiers changed from: private */
    public static void b(final Context context, final String str) {
        ahn.b().execute(new Runnable() {
            public final void run() {
                if (bno.a) {
                    TextUtils.isEmpty(str);
                }
                long j = 86400000;
                if (!TextUtils.isEmpty(str)) {
                    try {
                        j = new JSONObject(str).optLong("update_imei", 86400000);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                long j2 = new MapSharePreference((String) "SharedPreferences").sharedPrefs().getLong("last_update_imei_time", 0);
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - j2 > j) {
                    String a2 = kn.a(context);
                    String a3 = kn.a(context, true);
                    String a4 = agm.a(context);
                    String b2 = kn.b(context);
                    String a5 = kn.a(true);
                    kn.b(context, Build.MODEL, false);
                    if (!TextUtils.isEmpty(a2) && !TextUtils.isEmpty(a4) && !a2.equalsIgnoreCase(a4)) {
                        kn.a(context, a4, false);
                        kn.a(context, a4, true);
                        ccp.a(true);
                        if (bno.a) {
                            StringBuilder sb = new StringBuilder("newIMEI:");
                            sb.append(a4);
                            sb.append(",oldIMEI:");
                            sb.append(a2);
                            sb.append(",updateAfter:");
                            sb.append(kn.a(context));
                        }
                        if (!TextUtils.isEmpty(b2) && b2.equals(a5)) {
                            ccp.b();
                        }
                    } else if (TextUtils.isEmpty(a3) || TextUtils.isEmpty(a4) || a3.equalsIgnoreCase(a4)) {
                        ccp.a(false);
                        if (bno.a) {
                            StringBuilder sb2 = new StringBuilder("newIMEI:");
                            sb2.append(a4);
                            sb2.append(",oldIMEI:");
                            sb2.append(a2);
                        }
                    } else {
                        kn.a(context, a4, true);
                        ccp.a(true);
                        if (bno.a) {
                            StringBuilder sb3 = new StringBuilder("newIMEI:");
                            sb3.append(a4);
                            sb3.append(",oldIMEI:");
                            sb3.append(a3);
                            sb3.append(",updateAfter:");
                            sb3.append(kn.a(context));
                        }
                        if (!TextUtils.isEmpty(b2) && b2.equals(a5)) {
                            ccp.b();
                        }
                    }
                    Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
                    edit.putLong("last_update_imei_time", currentTimeMillis);
                    edit.apply();
                    return;
                }
                if (bno.a) {
                    new StringBuilder("checkTime-FAIL,fileIMEI:").append(kn.a(context));
                }
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0076, code lost:
        if (android.os.Build.VERSION.SDK_INT < 26) goto L_0x008a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0043 A[Catch:{ all -> 0x0092 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(android.app.Application r7) {
        /*
            boolean r0 = a
            if (r0 != 0) goto L_0x0096
            r0 = 1
            r1 = 0
            a = r0     // Catch:{ all -> 0x0092 }
            com.amap.bundle.mapstorage.MapSharePreference r2 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x0092 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r3 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ all -> 0x0092 }
            r2.<init>(r3)     // Catch:{ all -> 0x0092 }
            java.lang.String r3 = "ShortCut_Create"
            boolean r3 = r2.getBooleanValue(r3, r1)     // Catch:{ all -> 0x0092 }
            int r4 = com.autonavi.minimap.R.string.amap_app_name     // Catch:{ all -> 0x0092 }
            java.lang.String r4 = r7.getString(r4)     // Catch:{ all -> 0x0092 }
            boolean r4 = com.amap.bundle.blutils.platform.ShortCutUtil.hasShortCutCompat(r7, r4)     // Catch:{ all -> 0x0092 }
            int r5 = com.autonavi.minimap.R.string.old_app_name     // Catch:{ all -> 0x0092 }
            java.lang.String r5 = r7.getString(r5)     // Catch:{ all -> 0x0092 }
            boolean r5 = com.amap.bundle.blutils.platform.ShortCutUtil.hasShortCutCompat(r7, r5)     // Catch:{ all -> 0x0092 }
            java.lang.String r6 = "高德地图"
            boolean r6 = com.amap.bundle.blutils.platform.ShortCutUtil.hasShortCutCompat(r7, r6)     // Catch:{ all -> 0x0092 }
            if (r3 != 0) goto L_0x003a
            if (r4 != 0) goto L_0x003a
            if (r5 != 0) goto L_0x003a
            if (r6 == 0) goto L_0x0038
            goto L_0x003a
        L_0x0038:
            r3 = 0
            goto L_0x003b
        L_0x003a:
            r3 = 1
        L_0x003b:
            java.lang.String r4 = "first"
            boolean r4 = r2.getBooleanValue(r4, r0)     // Catch:{ all -> 0x0092 }
            if (r3 != 0) goto L_0x008a
            if (r4 == 0) goto L_0x0078
            java.lang.String r3 = "Huawei"
            java.lang.String r4 = android.os.Build.MANUFACTURER     // Catch:{ all -> 0x0092 }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x0092 }
            if (r3 == 0) goto L_0x0059
            java.lang.String r3 = "Nexus 6P"
            java.lang.String r4 = android.os.Build.MODEL     // Catch:{ all -> 0x0092 }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x0092 }
            if (r3 != 0) goto L_0x006d
        L_0x0059:
            java.lang.String r3 = "motorola"
            java.lang.String r4 = android.os.Build.MANUFACTURER     // Catch:{ all -> 0x0092 }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x0092 }
            if (r3 == 0) goto L_0x006f
            java.lang.String r3 = "Nexus 6"
            java.lang.String r4 = android.os.Build.MODEL     // Catch:{ all -> 0x0092 }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x0092 }
            if (r3 == 0) goto L_0x006f
        L_0x006d:
            r3 = 1
            goto L_0x0070
        L_0x006f:
            r3 = 0
        L_0x0070:
            if (r3 == 0) goto L_0x0078
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0092 }
            r4 = 26
            if (r3 < r4) goto L_0x008a
        L_0x0078:
            int r3 = com.autonavi.minimap.R.string.amap_app_name     // Catch:{ all -> 0x0092 }
            java.lang.String r3 = r7.getString(r3)     // Catch:{ all -> 0x0092 }
            int r4 = com.autonavi.minimap.R.drawable.v3_icon     // Catch:{ all -> 0x0092 }
            java.lang.Class<com.autonavi.map.activity.SplashActivity> r5 = com.autonavi.map.activity.SplashActivity.class
            com.amap.bundle.blutils.platform.ShortCutUtil.addMainShortCut(r7, r3, r4, r5)     // Catch:{ all -> 0x0092 }
            java.lang.String r7 = "ShortCut_Create"
            r2.putBooleanValue(r7, r0)     // Catch:{ all -> 0x0092 }
        L_0x008a:
            java.lang.String r7 = "first"
            r2.putBooleanValue(r7, r1)     // Catch:{ all -> 0x0092 }
            a = r1
            return
        L_0x0092:
            r7 = move-exception
            a = r1
            throw r7
        L_0x0096:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ccp.a(android.app.Application):void");
    }

    static /* synthetic */ void a() {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences.toString());
        if (!mapSharePreference.getBooleanValue("has_log_cpu_abi", false)) {
            String[] strArr = Build.SUPPORTED_ABIS;
            if (strArr != null && strArr.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < strArr.length; i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(strArr[i]);
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("cpu_abi", sb.toString());
                    LogManager.actionLogV2("P00534", "B001", jSONObject);
                } catch (JSONException unused) {
                }
            }
            mapSharePreference.putBooleanValue("has_log_cpu_abi", true);
        }
    }

    static /* synthetic */ void a(boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", z ? 1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LogConstant.UPDATE_IMEI_CACHE, jSONObject);
    }

    static /* synthetic */ void b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", 99);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LogConstant.UPDATE_IMEI_CACHE, jSONObject);
    }
}
