package com.amap.bundle.appupgrade;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.autonavi.minimap.app.param.AppUpdateRequest;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;
import org.json.JSONObject;

public final class AppUpgradeController implements jm, js {
    /* access modifiers changed from: private */
    public static HashMap<String, Integer> t = new HashMap<>();
    private static List<String> u = new ArrayList();
    private static Object v = new Object();
    public ji a;
    public String b;
    public volatile boolean c = false;
    public final BroadcastReceiver d = new BroadcastReceiver() {
        public void onReceive(final Context context, Intent intent) {
            ahm.a(new Runnable() {
                public final void run() {
                    if (aaw.d(context) == 1 && ju.c()) {
                        AppUpgradeController.this.o = AppUpgradeController.this.i.getBoolean("isBackgroundDownload", false);
                        if (AppUpgradeController.this.o) {
                            jg.a().b();
                        }
                    }
                }
            });
        }
    };
    public volatile boolean e = false;
    AosRequest f;
    b g;
    /* access modifiers changed from: private */
    public Activity h;
    /* access modifiers changed from: private */
    public final SharedPreferences i;
    private ProgressDlg j;
    private String k;
    private String l;
    /* access modifiers changed from: private */
    public String m;
    /* access modifiers changed from: private */
    public boolean n;
    /* access modifiers changed from: private */
    public boolean o = false;
    /* access modifiers changed from: private */
    public jk p;
    private boolean q = false;
    private final Runnable r = new Runnable() {
        public final void run() {
            if (!AppUpgradeController.this.c) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                intentFilter.setPriority(1000);
                try {
                    AMapAppGlobal.getApplication().registerReceiver(AppUpgradeController.this.d, intentFilter);
                } catch (AssertionError | Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public jq s;

    class AppUpdateCallBack extends FalconAosPrepareResponseCallback<ji> {
        private AppUpdateCallBack() {
        }

        /* synthetic */ AppUpdateCallBack(AppUpgradeController appUpgradeController, byte b) {
            this();
        }

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            return b(aosByteResponse);
        }

        public final /* synthetic */ void a(Object obj) {
            ji jiVar = (ji) obj;
            if (jiVar == null) {
                if (AppUpgradeController.this.m != null && AppUpgradeController.this.m.equals("check")) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.act_update_noupdate));
                    jx.a().a(jiVar.s);
                }
                AppUpgradeController.this.q();
                return;
            }
            String str = jiVar.u;
            Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("appupdategatedlaunch", 0).edit();
            edit.putString("gray_res", str);
            edit.apply();
            if (!jiVar.q || ((TextUtils.isEmpty(jiVar.b) || TextUtils.isEmpty(jiVar.d)) && jiVar.x.size() <= 0)) {
                AppUpgradeController.this.q();
                if (AppUpgradeController.this.m != null && AppUpgradeController.this.m.equals("check")) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.act_update_noupdate));
                }
                jx.a().a(jiVar.s);
            } else {
                AppUpgradeController.this.a = jiVar;
                if (jiVar.x.size() > 0) {
                    jx a2 = jx.a();
                    List<defpackage.ji.b> list = jiVar.x;
                    String str2 = jiVar.s;
                    String str3 = jiVar.v;
                    c cVar = new c(AppUpgradeController.this);
                    synchronized (jx.class) {
                        if (list.size() != 0) {
                            ahn b = ahn.b();
                            defpackage.jx.AnonymousClass1 r4 = new Runnable(str2, list, str3, cVar) {
                                final /* synthetic */ String a;
                                final /* synthetic */ List b;
                                final /* synthetic */ String c;
                                final /* synthetic */ a d;

                                {
                                    this.a = r2;
                                    this.b = r3;
                                    this.c = r4;
                                    this.d = r5;
                                }

                                public final void run() {
                                    jx.this.b.a(this.b, this.c, jw.a(this.a), this.d);
                                }
                            };
                            b.execute(r4);
                        }
                    }
                } else {
                    String str4 = jiVar.v;
                    Editor edit2 = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
                    edit2.putString("update_hint_config_file_download_complete_version", str4);
                    edit2.apply();
                }
                try {
                    Editor edit3 = AMapAppGlobal.getApplication().getSharedPreferences("appUpdateInfo", 0).edit();
                    edit3.putString("appInfo", jiVar.a().toString());
                    edit3.apply();
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                String str5 = jiVar.s;
                Editor edit4 = AMapAppGlobal.getApplication().getSharedPreferences("appupdatemd5info", 0).edit();
                edit4.putString("update_md5_param", str5);
                edit4.apply();
                if (!TextUtils.isEmpty(AppUpgradeController.this.m)) {
                    if (AppUpgradeController.this.m.equals("auto")) {
                        AppUpgradeController.b(AppUpgradeController.this, jiVar);
                    } else if (AppUpgradeController.this.m.equals("check")) {
                        AppUpgradeController.c(AppUpgradeController.this, jiVar);
                    }
                }
            }
        }

        private static ji b(AosByteResponse aosByteResponse) {
            ji jiVar = new ji();
            try {
                jiVar.parser((byte[]) aosByteResponse.getResult());
            } catch (UnsupportedEncodingException e) {
                kf.a((Throwable) e);
            } catch (JSONException e2) {
                kf.a((Throwable) e2);
            }
            return jiVar;
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            AppUpgradeController.this.q();
            if (!NetworkReachability.b() && AMapPageUtil.getPageContext() != null && AMapPageUtil.isHomePage()) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.ic_net_error_check));
            }
        }
    }

    public class a implements defpackage.ju.a {
        private String b;

        public a(String str) {
            this.b = str;
        }

        public final void a(boolean z, String str, File file, String str2) {
            String str3;
            Integer num;
            long j;
            if (!z) {
                if (file != null) {
                    try {
                        str3 = AppUpgradeController.this.b;
                    } catch (Throwable th) {
                        th.printStackTrace();
                        return;
                    }
                } else {
                    str3 = "";
                }
                Integer valueOf = Integer.valueOf(0);
                if (!TextUtils.isEmpty(str3)) {
                    Integer num2 = (Integer) AppUpgradeController.t.get(str3);
                    if (num2 == null) {
                        num2 = Integer.valueOf(0);
                    }
                    valueOf = Integer.valueOf(num2.intValue() + 1);
                    AppUpgradeController.t.put(str3, valueOf);
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("time", simpleDateFormat.format(new Date()));
                jSONObject.put("isdebug", bno.a);
                jSONObject.put(LocationParams.PARA_COMMON_DIV, NetworkParam.getDiv());
                jSONObject.put(LocationParams.PARA_COMMON_DIBV, NetworkParam.getDibv());
                jSONObject.put(LocationParams.PARA_COMMON_DIC, NetworkParam.getDic());
                jSONObject.put("tag", this.b != null ? this.b : "");
                jSONObject.put("md5", str);
                if (file != null) {
                    num = valueOf;
                    jSONObject.put("file_info", String.format("file: %s, exsits: %s, fileMd5: %s, len: %s, r: %s, w: %s, e: %s, tm: %s", new Object[]{file.getAbsolutePath(), Boolean.valueOf(file.exists()), str2, Long.valueOf(file.length()), Boolean.valueOf(file.canRead()), Boolean.valueOf(file.canWrite()), Boolean.valueOf(file.canExecute()), simpleDateFormat.format(new Date(file.lastModified()))}));
                } else {
                    num = valueOf;
                    jSONObject.put("file_info", "null");
                }
                jSONObject.put("file_key", AppUpgradeController.this.b);
                jSONObject.put("network_env", ConfigerHelper.getInstance().getNetCondition());
                jSONObject.put("network_type", aaw.b(AMapAppGlobal.getApplication()));
                jSONObject.put("url", AppUpgradeController.n());
                jSONObject.put("fail_count", num);
                jSONObject.put("other_log", AppUpgradeController.g());
                jSONObject.put("update_info", jt.a());
                jSONObject.put("last_install", new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue("UPGRADE_LAST_INSTALL_INFO", ""));
                if (file != null) {
                    File parentFile = file.getParentFile();
                    if (parentFile != null) {
                        jSONObject.put("file_parent_info", String.format("file: %s, r: %s, w: %s, e: %s", new Object[]{parentFile.getAbsolutePath(), Boolean.valueOf(parentFile.canRead()), Boolean.valueOf(parentFile.canWrite()), Boolean.valueOf(parentFile.canExecute())}));
                        File[] listFiles = parentFile.listFiles();
                        if (listFiles != null) {
                            StringBuilder sb = new StringBuilder();
                            int i = 0;
                            for (int length = listFiles.length; i < length; length = length) {
                                File file2 = listFiles[i];
                                sb.append(String.format("cld: %s, len: %s, tm: %s; ", new Object[]{file2.getName(), Long.valueOf(file2.length()), simpleDateFormat.format(new Date(file2.lastModified()))}));
                                i++;
                            }
                            jSONObject.put("child_info", sb.toString());
                        }
                    }
                }
                coe.a("P0006", ALCTtsConstant.EVENT_ID_TTS_JNI_ERROR, jSONObject.toString());
                cjy.a(ALCLogLevel.P6, (String) AMapLog.GROUP_BASEMAP, (String) "D1", (String) "P0006", (String) "E102", jSONObject.toString());
                coe.b("P0006", "E202", jSONObject.toString());
                if (AppUpgradeController.this.a != null) {
                    if (file == null) {
                        j = 0;
                    } else {
                        j = file.length();
                    }
                    AMapLog.error("paas.appupgrade", "UpdateMapTotalVersion", String.format("install package md5 error,fileLength:%s,file md5:%s,serviceData:%s", new Object[]{Long.valueOf(j), str2, AppUpgradeController.this.a.a()}));
                }
            }
        }
    }

    class b {
        volatile boolean a;

        private b() {
            this.a = false;
        }

        /* synthetic */ b(AppUpgradeController appUpgradeController, byte b2) {
            this();
        }
    }

    static class c implements defpackage.jv.a {
        SoftReference<AppUpgradeController> a;

        public c(AppUpgradeController appUpgradeController) {
            this.a = new SoftReference<>(appUpgradeController);
        }

        public final void a() {
            aho.a(new Runnable() {
                public final void run() {
                    if (c.this.a != null) {
                        AppUpgradeController appUpgradeController = c.this.a.get();
                        if (appUpgradeController != null) {
                            StringBuilder sb = new StringBuilder("updateConfigCheckType:");
                            sb.append(appUpgradeController.m);
                            AMapLog.i("UpdateMapTotalVersion", sb.toString());
                            appUpgradeController.q();
                            boolean z = true;
                            boolean a2 = (!TextUtils.equals(appUpgradeController.m, "auto") || appUpgradeController.e()) ? true : appUpgradeController.a(ju.b().longValue(), appUpgradeController.a.k, appUpgradeController.a.t);
                            if (!ju.a(appUpgradeController.a.e, appUpgradeController.a.g, appUpgradeController.a.f) || !TextUtils.equals(appUpgradeController.m, "auto")) {
                                z = false;
                            }
                            if (a2 && AppUpgradeController.b(appUpgradeController.a.k, TextUtils.equals(appUpgradeController.m, "check")) && !AppUpgradeController.o(appUpgradeController) && !z) {
                                AppUpgradeController.a(appUpgradeController, appUpgradeController.a, TextUtils.equals(appUpgradeController.m, "check"), (File) null);
                            }
                        }
                    }
                }
            });
        }

        public final void b() {
            if (this.a != null) {
                AppUpgradeController appUpgradeController = this.a.get();
                if (appUpgradeController != null) {
                    appUpgradeController.q();
                }
            }
        }
    }

    public AppUpgradeController(Activity activity) {
        this.h = (Activity) new WeakReference(activity).get();
        aho.a(this.r, 2000);
        this.i = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
    }

    public static void a() {
        Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("appinit", 0).edit();
        edit.putLong("appinit", System.currentTimeMillis());
        edit.apply();
    }

    public final void b() {
        if (!this.e) {
            this.e = true;
            aho.a(new Runnable() {
                public final void run() {
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(false);
                    if (latestPosition != null) {
                        double longitude = latestPosition.getLongitude();
                        double latitude = latestPosition.getLatitude();
                        if (longitude > 0.0d && latitude > 0.0d) {
                            lt.a();
                            lt.a(String.valueOf(latitude), String.valueOf(longitude), lb.a(), defpackage.boe.a.a.a.a());
                        }
                    }
                    AppUpgradeController.e(AppUpgradeController.this);
                }
            });
        }
    }

    public final void c() {
        if (!bno.b) {
            if (!aaw.c(AMapAppGlobal.getApplication())) {
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_message));
            }
            b((String) "check");
        }
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        this.g = new b(this, 0);
        if ("check".equals(str)) {
            a(this.g);
        }
        c(str);
    }

    private void c(final String str) {
        ahm.a(new Runnable() {
            public final void run() {
                PackageInfo packageInfo;
                AppUpdateRequest appUpdateRequest = new AppUpdateRequest();
                appUpdateRequest.b = str;
                AppUpgradeController.this.m = appUpdateRequest.b;
                appUpdateRequest.c = lb.a();
                StringBuilder sb = new StringBuilder();
                sb.append(defpackage.ahp.a.a().b);
                appUpdateRequest.d = sb.toString();
                appUpdateRequest.e = ju.e();
                String string = AMapAppGlobal.getApplication().getSharedPreferences("appupdategatedlaunch", 0).getString("gray_res", "");
                if (!TextUtils.isEmpty(string)) {
                    appUpdateRequest.g = string;
                    appUpdateRequest.f = ju.a();
                }
                Application application = AMapAppGlobal.getApplication();
                try {
                    packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                    packageInfo = null;
                }
                if (packageInfo != null) {
                    appUpdateRequest.addReqParam("last_update_time", String.valueOf(packageInfo.lastUpdateTime));
                }
                appUpdateRequest.setUrl(AppUpdateRequest.a);
                appUpdateRequest.addSignParam("channel");
                appUpdateRequest.addSignParam(LocationParams.PARA_COMMON_DIC);
                appUpdateRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
                appUpdateRequest.addReqParam("type", appUpdateRequest.b);
                appUpdateRequest.addReqParam("build", appUpdateRequest.c);
                appUpdateRequest.addReqParam("appver", appUpdateRequest.d);
                appUpdateRequest.addReqParam("amap_ae8_params", appUpdateRequest.e);
                appUpdateRequest.addReqParam("md5", appUpdateRequest.f);
                appUpdateRequest.addReqParam("gray_res", appUpdateRequest.g);
                appUpdateRequest.addReqParam("incremental", appUpdateRequest.h);
                AppUpgradeController.this.f = appUpdateRequest;
                if (AppUpgradeController.this.g == null || AppUpgradeController.this.g.a) {
                    AppUpgradeController.this.f = null;
                    AppUpgradeController.this.q();
                    return;
                }
                StringBuilder sb2 = new StringBuilder("CheckType:");
                sb2.append(str);
                AMapLog.i("UpdateMapTotalVersion", sb2.toString());
                yq.a();
                yq.a((AosRequest) appUpdateRequest, (AosResponseCallback<T>) new AppUpdateCallBack<T>(AppUpgradeController.this, 0));
            }
        });
    }

    public static String a(ji jiVar) {
        StringBuilder sb = new StringBuilder();
        sb.append(jiVar.f);
        sb.append(jiVar.e);
        sb.append(jiVar.g);
        return sb.toString();
    }

    private void l() {
        if (this.h != null && (this.h instanceof jp)) {
            ((jp) this.h).a_();
        }
        System.exit(0);
    }

    private void a(String str, boolean z, int i2) {
        if (TextUtils.isEmpty(str) || (!str.contains(AjxHttpLoader.DOMAIN_HTTP) && !str.contains(AjxHttpLoader.DOMAIN_HTTPS))) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("time", simpleDateFormat.format(new Date()));
                jSONObject.put("isdebug", bno.a);
                jSONObject.put(LocationParams.PARA_COMMON_DIV, NetworkParam.getDiv());
                jSONObject.put(LocationParams.PARA_COMMON_DIBV, NetworkParam.getDibv());
                jSONObject.put(LocationParams.PARA_COMMON_DIC, NetworkParam.getDic());
                jSONObject.put("url", str);
                jSONObject.put("update_info", jt.a());
                coe.a("P0006", ALCTtsConstant.EVENT_ID_TTS_DEVICE_INFO, jSONObject.toString());
                cjy.a(ALCLogLevel.P6, (String) AMapLog.GROUP_BASEMAP, (String) "D1", (String) "P0006", (String) "E104", jSONObject.toString());
                coe.b("P0006", "E204", jSONObject.toString());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            if (ju.c() && !z && this.m != null && this.m.equals("auto")) {
                this.o = true;
            }
            a(str);
            this.n = z;
            p();
            a((String) "startDownload. url: %s", str);
            new MapSharePreference(SharePreferenceName.SharedPreferences).putStringValue("UPGRADE_LAST_DOWNLOAD_URL", str);
            if (!new File(m()).exists()) {
                jw.a(this.a.s, 0);
            }
            this.p = jg.a().a(str, m(), AMapAppGlobal.getApplication().getString(R.string.amap_app_name), 1, this.n, i2, this.h, this);
        }
    }

    private String m() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.k);
        sb.append(this.l);
        sb.append(FilePathHelper.SUFFIX_DOT_TMP);
        return sb.toString();
    }

    private static void a(String str, String str2) {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        mapSharePreference.putStringValue("UPGRADE_LAST_SAVED_FILE_NAME", str);
        mapSharePreference.putStringValue("UPGRADE_LAST_SAVED_FILE_NAME_KEY", str2);
    }

    private static String a(AtomicReference<String> atomicReference) {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        atomicReference.set(mapSharePreference.getStringValue("UPGRADE_LAST_SAVED_FILE_NAME_KEY", null));
        return mapSharePreference.getStringValue("UPGRADE_LAST_SAVED_FILE_NAME", null);
    }

    /* access modifiers changed from: private */
    public static String n() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue("UPGRADE_LAST_DOWNLOAD_URL", "");
    }

    public final boolean a(ji jiVar, boolean z, boolean z2) {
        AtomicReference atomicReference = new AtomicReference();
        String a2 = a(atomicReference);
        if (TextUtils.isEmpty(a2)) {
            return false;
        }
        if (TextUtils.isEmpty((CharSequence) atomicReference.get()) || !((String) atomicReference.get()).equals(this.b)) {
            p();
            return false;
        }
        final File file = new File(a2);
        if (!file.exists()) {
            return false;
        }
        if (!b(z, z2)) {
            return true;
        }
        if (jiVar.x.size() > 0 && !jw.a(jw.a(jiVar.s), jiVar.x)) {
            return false;
        }
        final ji jiVar2 = jiVar;
        final boolean z3 = z2;
        final boolean z4 = z;
        AnonymousClass15 r4 = new Runnable() {
            public final void run() {
                if (AppUpgradeController.this.h != null && !AppUpgradeController.this.h.isFinishing()) {
                    if (jiVar2.x.size() > 0) {
                        AppUpgradeController.a(AppUpgradeController.this, jiVar2, z3, file);
                    } else {
                        String string = AMapAppGlobal.getApplication().getString(z4 ? R.string.exit_application : R.string.ignore);
                        String string2 = AMapAppGlobal.getApplication().getString(R.string.app_download_install_now);
                        String string3 = AMapAppGlobal.getApplication().getString(R.string.app_download_install_msg);
                        defpackage.ji.a aVar = jiVar2.w;
                        if (aVar != null) {
                            C0090a aVar2 = aVar.c;
                            if (aVar2 != null) {
                                if (!TextUtils.isEmpty(aVar2.c)) {
                                    string2 = aVar2.c;
                                }
                                if (!TextUtils.isEmpty(aVar.b)) {
                                    string3 = aVar.b;
                                }
                                if (z4) {
                                    if (!TextUtils.isEmpty(aVar2.d)) {
                                        string = aVar2.d;
                                    }
                                } else if (!TextUtils.isEmpty(aVar2.a)) {
                                    string = aVar2.a;
                                }
                            }
                        }
                        final bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null) {
                            com.autonavi.widget.ui.AlertView.a a2 = ju.a((Context) AppUpgradeController.this.h, jiVar2, string3);
                            a2.a(R.string.app_download_install_title);
                            a2.a(!z4);
                            a2.a((CharSequence) string2, (defpackage.ern.a) new defpackage.ern.a() {
                                public final void onClick(AlertView alertView, int i) {
                                    AppUpgradeController.a(AppUpgradeController.this, pageContext, alertView, z3, z4, file);
                                }
                            });
                            a2.b((CharSequence) string, (defpackage.ern.a) new defpackage.ern.a() {
                                public final void onClick(AlertView alertView, int i) {
                                    AppUpgradeController.a(AppUpgradeController.this, pageContext, alertView, z4);
                                }
                            });
                            a2.b = new defpackage.ern.a() {
                                public final void onClick(AlertView alertView, int i) {
                                }
                            };
                            a2.c = new defpackage.ern.a() {
                                public final void onClick(AlertView alertView, int i) {
                                }
                            };
                            a2.a(false);
                            AlertView a3 = a2.a();
                            pageContext.showViewLayer(a3);
                            a3.startAnimation();
                        } else {
                            return;
                        }
                    }
                    ju.a(0);
                }
            }
        };
        aho.a(r4);
        return true;
    }

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.l = str.substring(str.lastIndexOf("/") + 1);
            this.k = FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication());
            if (!TextUtils.isEmpty(this.k) && this.k.indexOf("data/data") == -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.k);
                sb.append("/autonavi/apk/");
                this.k = sb.toString();
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(File file) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("time", simpleDateFormat.format(new Date()));
            jSONObject.put("file", file != null ? file.getName() : "");
            jSONObject.put("path", a(new AtomicReference<>()));
            jSONObject.put("url", n());
            jSONObject.put("md5", ju.a());
            new MapSharePreference(SharePreferenceName.SharedPreferences).putStringValue("UPGRADE_LAST_INSTALL_INFO", jSONObject.toString());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        kl.a(this.h, file);
    }

    /* access modifiers changed from: private */
    public File o() {
        boolean z;
        if (this.h == null) {
            return null;
        }
        String format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(this.k);
        sb.append(this.b);
        sb.append("_");
        sb.append(format);
        String sb2 = sb.toString();
        String m2 = m();
        File file = new File(sb2);
        File file2 = new File(m2);
        if (file.exists()) {
            file.delete();
        }
        try {
            z = ahd.a(m2, sb2);
        } catch (Throwable th) {
            th.printStackTrace();
            z = false;
        }
        if (!z) {
            a((String) "prepareInstallApk. rename failed. file: %s, exists: %s, fileTmp: %s, exists: %s", file.getAbsolutePath(), Boolean.valueOf(file.exists()), file2.getAbsolutePath(), Boolean.valueOf(file2.exists()));
        }
        if (file2.exists() && !ju.a(ju.a(), file2, (defpackage.ju.a) null)) {
            a((String) "prepareInstallApk. fileTmp md5 is wrong. fileTmp: %s, length: %s", file2.getAbsolutePath(), Long.valueOf(file2.length()));
            file2.delete();
        }
        if (!file.exists()) {
            return null;
        }
        a(sb2, this.b);
        if (TextUtils.isEmpty(this.k) || this.k.indexOf("data/data") == -1 || ahd.b(file) == 0) {
            return file;
        }
        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.app_download_not_allow_install));
        return null;
    }

    public final void b(final ji jiVar, final boolean z, final boolean z2) {
        if (this.h == null || jiVar == null) {
            q();
        } else if (!b(z, z2)) {
            q();
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    if (jiVar.x.size() == 0) {
                        AppUpgradeController.a(AppUpgradeController.this, jiVar, z, z2);
                        return;
                    }
                    if (jw.a(jw.a(jiVar.s), jiVar.x)) {
                        AppUpgradeController.a(AppUpgradeController.this, jiVar, z2, (File) null);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public boolean a(long j2, boolean z, int i2) {
        if (z || !TextUtils.equals(this.m, "auto") || ji.y) {
            return true;
        }
        long max = Math.max(System.currentTimeMillis() - j2, 0);
        if (i2 > 0) {
            if (max > ((long) i2) * 3600000) {
                return true;
            }
            return false;
        } else if (max > FileCache.EXPIRE_TIME) {
            return true;
        } else {
            return false;
        }
    }

    private void a(final b bVar) {
        if (this.h != null) {
            this.j = new ProgressDlg(this.h, AMapAppGlobal.getApplication().getString(R.string.app_download_check_version), "");
            this.j.setCancelable(true);
            this.j.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface dialogInterface) {
                    if (bVar != null) {
                        b bVar = bVar;
                        bVar.a = true;
                        if (AppUpgradeController.this.f != null) {
                            yq.a();
                            yq.a(AppUpgradeController.this.f);
                            AppUpgradeController.this.f = null;
                        }
                    }
                }
            });
            this.j.show();
        }
    }

    public final void d() {
        jw.a(this.a.s, 1);
        LogManager.actionLogV2(LogConstant.PAGE_ID_UPDATE_DIALOG, "B007");
        new AsyncTask<Object, Object, File>() {
            /* access modifiers changed from: protected */
            public final /* synthetic */ void onPostExecute(Object obj) {
                File file = (File) obj;
                super.onPostExecute(file);
                if (file != null && !ju.d()) {
                    boolean a2 = ju.a(ju.a(), file, (defpackage.ju.a) new a("startInstallAsyncOnDownloadFinish"));
                    boolean z = true;
                    if (!a2) {
                        AppUpgradeController.this.a(!TextUtils.equals(AppUpgradeController.this.m, "check"));
                        return;
                    }
                    if (AppUpgradeController.this.p.j != 2) {
                        z = false;
                    }
                    if (z) {
                        AppUpgradeController.a(AppUpgradeController.this, file);
                    } else if (AppUpgradeController.this.o || AppUpgradeController.this.n) {
                        AppUpgradeController.this.a(AppUpgradeController.this.a, AppUpgradeController.this.n, false);
                    } else {
                        AppUpgradeController.this.a(file);
                    }
                }
            }

            /* access modifiers changed from: protected */
            public final void onPreExecute() {
                super.onPreExecute();
            }

            /* access modifiers changed from: protected */
            public final /* synthetic */ Object doInBackground(Object[] objArr) {
                return AppUpgradeController.this.o();
            }
        }.execute(new Object[0]);
    }

    public final void a(Throwable th) {
        LogManager.actionLogV2(LogConstant.PAGE_ID_UPDATE_DIALOG, "B008");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("time", simpleDateFormat.format(new Date()));
            jSONObject.put("isdebug", bno.a);
            jSONObject.put(LocationParams.PARA_COMMON_DIV, NetworkParam.getDiv());
            jSONObject.put(LocationParams.PARA_COMMON_DIBV, NetworkParam.getDibv());
            jSONObject.put(LocationParams.PARA_COMMON_DIC, NetworkParam.getDic());
            jSONObject.put("error", th.toString());
            jSONObject.put("url", n());
            jSONObject.put("other_log", g());
            jSONObject.put("update_info", jt.a());
            jSONObject.put("network", aaw.c(AMapAppGlobal.getApplication()));
            jSONObject.put("network_env", ConfigerHelper.getInstance().getNetCondition());
            jSONObject.put("network_type", aaw.b(AMapAppGlobal.getApplication()));
            coe.a("P0006", ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, jSONObject.toString());
            cjy.a(ALCLogLevel.P6, (String) AMapLog.GROUP_BASEMAP, (String) "D1", (String) "P0006", (String) "E103", jSONObject.toString());
            coe.b("P0006", "E203", jSONObject.toString());
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public final boolean e() {
        AtomicReference atomicReference = new AtomicReference();
        String a2 = a(atomicReference);
        if (!TextUtils.isEmpty(a2) && !TextUtils.isEmpty((CharSequence) atomicReference.get()) && ((String) atomicReference.get()).equals(this.b)) {
            return new File(a2).exists();
        }
        return false;
    }

    public final void f() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (!(pageContext == null || this.s == null)) {
            pageContext.dismissViewLayer(this.s.a());
        }
    }

    /* access modifiers changed from: private */
    public static boolean p() {
        String a2 = a(new AtomicReference<>());
        if (!TextUtils.isEmpty(a2)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            File file = new File(a2);
            if (file.exists()) {
                file.delete();
            }
            if (file.exists()) {
                a((String) "delete downloaded file failed. file: %s, canRead: %s, canWrite: %s, canExecute: %s, lastModifiedTime: %s", file.getAbsolutePath(), Boolean.valueOf(file.canRead()), Boolean.valueOf(file.canWrite()), Boolean.valueOf(file.canExecute()), simpleDateFormat.format(new Date(file.lastModified())));
            }
            a((String) "", (String) "");
            if (!file.exists()) {
                return true;
            }
            return false;
        }
        a((String) "", (String) "");
        return true;
    }

    /* access modifiers changed from: private */
    public void a(final boolean z) {
        if (this.h != null) {
            if (!z || AMapPageUtil.isHomePage()) {
                final bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(this.h);
                    if (z) {
                        aVar.a(R.string.md5_error_auto);
                    } else {
                        aVar.a(R.string.md5_error_check);
                    }
                    aVar.a(R.string.sure_830, (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            pageContext.dismissViewLayer(alertView);
                            AppUpgradeController.p();
                            JSONObject jSONObject = new JSONObject();
                            try {
                                jSONObject.put("paras", 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (z) {
                                LogManager.actionLogV2("P00001", LogConstant.MAIN_PAGE_MD5_ERROR_BUTTONID, jSONObject);
                                AppUpgradeController.this.c();
                                return;
                            }
                            LogManager.actionLogV2(LogConstant.PAGE_MORE, "B031", jSONObject);
                            AppUpgradeController.this.c();
                        }
                    });
                    aVar.b(R.string.ignore, (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            pageContext.dismissViewLayer(alertView);
                            JSONObject jSONObject = new JSONObject();
                            try {
                                jSONObject.put("paras", 2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (z) {
                                LogManager.actionLogV2("P00001", LogConstant.MAIN_PAGE_MD5_ERROR_BUTTONID, jSONObject);
                            } else {
                                LogManager.actionLogV2(LogConstant.PAGE_MORE, "B031", jSONObject);
                            }
                            AppUpgradeController.p();
                        }
                    });
                    aVar.b = new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                        }
                    };
                    aVar.c = new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                        }
                    };
                    aVar.a(false);
                    AlertView a2 = aVar.a();
                    pageContext.showViewLayer(a2);
                    a2.startAnimation();
                    LogManager.actionLogV2(LogConstant.PAGE_ID_UPDATE_DIALOG, "B006");
                }
            }
        }
    }

    private static void a(String str, Object... objArr) {
        try {
            String format = String.format("[%s]---%s\n", new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()), String.format(str, objArr)});
            synchronized (v) {
                u.add(format);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static String g() {
        StringBuilder sb = new StringBuilder();
        try {
            if (u.size() > 0) {
                synchronized (v) {
                    if (u.size() > 0) {
                        for (String append : u) {
                            sb.append(append);
                            sb.append("\n");
                        }
                    }
                    u.clear();
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public void q() {
        if (this.j != null && this.j.isShowing()) {
            this.j.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(boolean z, boolean z2) {
        return z || z2 || AMapPageUtil.isHomePage();
    }

    static /* synthetic */ void e(AppUpgradeController appUpgradeController) {
        if (!bno.b) {
            aho.a(new Runnable() {
                public final void run() {
                    new Thread(new Runnable() {
                        public final void run() {
                            AppUpgradeController.this.b((String) "auto");
                        }
                    }).start();
                }
            }, 1000);
        }
    }

    static /* synthetic */ void a(AppUpgradeController appUpgradeController, ji jiVar, boolean z, File file) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null && jw.a(jw.a(jiVar.s), jiVar.x)) {
            appUpgradeController.q();
            if (appUpgradeController.s == null || !pageContext.isViewLayerShowing(appUpgradeController.s.a())) {
                appUpgradeController.s = (jq) ank.a(jq.class);
                if (appUpgradeController.s != null) {
                    jq jqVar = appUpgradeController.s;
                    Context applicationContext = appUpgradeController.h.getApplicationContext();
                    final File file2 = file;
                    final bid bid = pageContext;
                    final boolean z2 = z;
                    final ji jiVar2 = jiVar;
                    AnonymousClass18 r0 = new defpackage.jq.a() {
                        public final void a() {
                            if (file2 != null) {
                                AppUpgradeController.a(AppUpgradeController.this, bid, AppUpgradeController.this.s.a(), z2, jiVar2.k, file2);
                            } else {
                                AppUpgradeController.a(AppUpgradeController.this, bid, AppUpgradeController.this.s.a(), jiVar2, jiVar2.k);
                            }
                            if (!jiVar2.k) {
                                bid.dismissViewLayer(AppUpgradeController.this.s.a());
                            }
                            LogManager.actionLogV2(LogConstant.PAGE_ID_UPDATE_DIALOG, "B004");
                        }

                        public final void b() {
                            if (file2 != null) {
                                AppUpgradeController.a(AppUpgradeController.this, bid, AppUpgradeController.this.s.a(), jiVar2.k);
                            } else {
                                AppUpgradeController.a(AppUpgradeController.this, bid, AppUpgradeController.this.s.a(), jiVar2.k, aaw.d(bid.getContext()));
                            }
                            bid.dismissViewLayer(AppUpgradeController.this.s.a());
                            LogManager.actionLogV2(LogConstant.PAGE_ID_UPDATE_DIALOG, "B002");
                        }
                    };
                    jqVar.a(applicationContext, jiVar, appUpgradeController, r0);
                    pageContext.showViewLayer(appUpgradeController.s.a());
                    LogManager.actionLogV2(LogConstant.PAGE_ID_UPDATE_DIALOG, "B001");
                    if (!z) {
                        ju.a(System.currentTimeMillis());
                    }
                }
            }
        }
    }

    static /* synthetic */ void a(AppUpgradeController appUpgradeController, bid bid, AlertView alertView, boolean z, boolean z2, File file) {
        ju.a(1);
        if (!ju.a(ju.a(), file, (defpackage.ju.a) new a("onPosInstallClick"))) {
            bid.dismissViewLayer(alertView);
            appUpgradeController.a(!z);
            return;
        }
        appUpgradeController.a(file);
        if (!z2) {
            bid.dismissViewLayer(alertView);
        }
    }

    static /* synthetic */ void a(AppUpgradeController appUpgradeController, bid bid, AlertView alertView, boolean z) {
        ju.a(2);
        bid.dismissViewLayer(alertView);
        if (z) {
            appUpgradeController.l();
            return;
        }
        if (AMapPageUtil.isHomePage()) {
            SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
            sharedPrefs.edit().putInt("app_uct", sharedPrefs.getInt("app_uct", 0) + 1).apply();
        }
    }

    static /* synthetic */ void a(AppUpgradeController appUpgradeController, File file) {
        Notification notification;
        Notification notification2;
        Notification notification3;
        Builder builder = new Builder(appUpgradeController.h);
        int i2 = R.drawable.downapp;
        String string = appUpgradeController.h.getString(R.string.amap_app_name);
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.app_download_finish_download));
        String sb2 = sb.toString();
        builder.setSmallIcon(i2).setTicker(sb2).setWhen(System.currentTimeMillis());
        ky.a(builder, NotificationChannelIds.b);
        if (VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }
        notification.flags = 16;
        notification.contentView = null;
        if (!ju.a(ju.a(), file, (defpackage.ju.a) new a("setupInstallNotification"))) {
            file.delete();
            builder.setContentTitle(string).setContentText(appUpgradeController.h.getString(R.string.md5_error_notification)).setContentIntent(PendingIntent.getActivity(appUpgradeController.h, 1, new Intent(), 134217728));
            if (VERSION.SDK_INT >= 16) {
                notification3 = builder.build();
            } else {
                notification3 = builder.getNotification();
            }
            ((NotificationManager) appUpgradeController.h.getSystemService("notification")).notify(1, notification3);
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        if (VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
            intent.setDataAndType(FileProvider.getUriForFile(AMapAppGlobal.getApplication(), FileUtil.FILE_PROVIDER, file), "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        builder.setContentTitle(string).setContentText(appUpgradeController.h.getString(R.string.app_download_click_install)).setContentIntent(PendingIntent.getActivity(appUpgradeController.h, 1, intent, 134217728));
        if (VERSION.SDK_INT >= 16) {
            notification2 = builder.build();
        } else {
            notification2 = builder.getNotification();
        }
        ((NotificationManager) appUpgradeController.h.getSystemService("notification")).notify(1, notification2);
    }

    static /* synthetic */ void a(AppUpgradeController appUpgradeController, final ji jiVar, final boolean z, boolean z2) {
        String str;
        appUpgradeController.q();
        if (appUpgradeController.h != null) {
            Application application = AMapAppGlobal.getApplication();
            if (application != null) {
                final int d2 = aaw.d(application);
                String string = application.getString(z ? R.string.download_now : R.string.app_download_update_now);
                String string2 = application.getString(z ? R.string.exit_application : R.string.ignore);
                if (z && d2 != 1) {
                    string2 = application.getString(R.string.app_download_open_wifi_auto);
                }
                String string3 = application.getString(R.string.app_download_hint_msg);
                defpackage.ji.a aVar = jiVar.w;
                if (aVar != null) {
                    if (!TextUtils.isEmpty(aVar.a)) {
                        string3 = aVar.a;
                    }
                    C0090a aVar2 = aVar.c;
                    if (aVar2 != null) {
                        if (!TextUtils.isEmpty(aVar2.b)) {
                            string = aVar2.b;
                        }
                        if (z) {
                            if (d2 == 1) {
                                if (!TextUtils.isEmpty(aVar2.d)) {
                                    string2 = aVar2.d;
                                }
                            } else if (!TextUtils.isEmpty(aVar2.e)) {
                                string2 = aVar2.e;
                            }
                        } else if (!TextUtils.isEmpty(aVar2.a)) {
                            string2 = aVar2.a;
                        }
                    }
                }
                if (d2 == 1) {
                    string3 = null;
                }
                if (TextUtils.isEmpty(jiVar.a)) {
                    str = application.getResources().getString(R.string.app_download_version_title);
                } else if (jiVar.a.length() > 10) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(jiVar.a.substring(0, 10));
                    sb.append("...");
                    str = sb.toString();
                } else {
                    str = jiVar.a;
                }
                final bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    com.autonavi.widget.ui.AlertView.a a2 = ju.a((Context) appUpgradeController.h, jiVar, string3);
                    a2.a((CharSequence) str);
                    a2.a((CharSequence) string, (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            AppUpgradeController.a(AppUpgradeController.this, pageContext, alertView, jiVar, z);
                        }
                    });
                    a2.b((CharSequence) string2, (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            AppUpgradeController.a(AppUpgradeController.this, pageContext, alertView, z, d2);
                        }
                    });
                    if (b(z, z2)) {
                        a2.b = new defpackage.ern.a() {
                            public final void onClick(AlertView alertView, int i) {
                            }
                        };
                        a2.c = new defpackage.ern.a() {
                            public final void onClick(AlertView alertView, int i) {
                            }
                        };
                        if (!appUpgradeController.q) {
                            a2.a(false);
                            AlertView a3 = a2.a();
                            pageContext.showViewLayer(a3);
                            a3.startAnimation();
                            appUpgradeController.q = true;
                        }
                        if (!z2) {
                            ju.a(System.currentTimeMillis());
                        }
                    }
                }
            }
        }
    }

    static /* synthetic */ void a(AppUpgradeController appUpgradeController, bid bid, AlertView alertView, ji jiVar, boolean z) {
        ju.b(1);
        bid.dismissViewLayer(alertView);
        appUpgradeController.q = false;
        appUpgradeController.o = false;
        Editor edit = appUpgradeController.i.edit();
        edit.putInt("app_uct", 0);
        edit.putBoolean("isBackgroundDownload", appUpgradeController.o);
        edit.apply();
        if (!jiVar.o) {
            appUpgradeController.a(jiVar.d, z, 1);
        } else if (!ahp.a(jiVar.p)) {
            appUpgradeController.a(jiVar.n, z, 1);
        } else {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(jiVar.r));
            if (appUpgradeController.h != null) {
                try {
                    appUpgradeController.h.startActivity(intent);
                } catch (ActivityNotFoundException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    static /* synthetic */ void a(AppUpgradeController appUpgradeController, bid bid, AlertView alertView, boolean z, int i2) {
        ju.b(2);
        bid.dismissViewLayer(alertView);
        appUpgradeController.q = false;
        if (z) {
            if (i2 == 1) {
                appUpgradeController.l();
            } else {
                bin.a.d((String) UploadConstants.STATUS_PUSH_NOTIFIED, 1);
                bin.a.Q();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0094  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void b(com.amap.bundle.appupgrade.AppUpgradeController r9, defpackage.ji r10) {
        /*
            r9.q()
            if (r10 != 0) goto L_0x0006
            return
        L_0x0006:
            java.lang.String r0 = r10.d
            java.lang.String r1 = r10.b
            boolean r2 = r10.k
            java.lang.String r3 = r10.g
            com.amap.bundle.mapstorage.MapSharePreference r4 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r5 = "SharedPreferences"
            r4.<init>(r5)
            android.content.SharedPreferences r4 = r4.sharedPrefs()
            android.content.SharedPreferences$Editor r4 = r4.edit()
            java.lang.String r5 = a(r10)
            r9.b = r5
            java.lang.String r5 = r10.d
            r9.a(r5)
            boolean r5 = android.text.TextUtils.isEmpty(r0)
            r6 = 0
            if (r5 != 0) goto L_0x0046
            java.lang.String r5 = r10.e
            java.lang.String r7 = r10.f
            boolean r5 = defpackage.ju.a(r5, r3, r7)
            if (r2 != 0) goto L_0x003b
            if (r5 != 0) goto L_0x0046
        L_0x003b:
            boolean r5 = defpackage.ju.d()
            if (r5 != 0) goto L_0x0046
            boolean r5 = r9.a(r10, r2, r6)
            goto L_0x0047
        L_0x0046:
            r5 = 0
        L_0x0047:
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            r7 = 1
            if (r1 != 0) goto L_0x0056
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0056
            r1 = 1
            goto L_0x0057
        L_0x0056:
            r1 = 0
        L_0x0057:
            java.util.List<ji$b> r8 = r10.x
            int r8 = r8.size()
            if (r8 <= 0) goto L_0x0061
            r8 = 1
            goto L_0x0062
        L_0x0061:
            r8 = 0
        L_0x0062:
            if (r5 != 0) goto L_0x00f5
            if (r1 != 0) goto L_0x0068
            if (r8 == 0) goto L_0x00f5
        L_0x0068:
            java.lang.String r1 = "updateUrl"
            r4.putString(r1, r0)
            java.lang.String r0 = r10.e
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0078
            java.lang.String r0 = ""
        L_0x0078:
            java.lang.String r1 = "updateAmapAppVersion"
            r4.putString(r1, r0)
            java.lang.String r0 = "isForceUpdateApp"
            r4.putBoolean(r0, r6)
            if (r2 == 0) goto L_0x0094
            java.lang.String r0 = "needForceUpdateVersion"
            ahp$a r1 = defpackage.ahp.a.a()
            java.lang.String r1 = r1.a
            r4.putString(r0, r1)
            r9.b(r10, r7, r6)
            goto L_0x00f5
        L_0x0094:
            java.lang.String r0 = r10.e
            java.lang.String r1 = r10.f
            boolean r0 = defpackage.ju.a(r0, r3, r1)
            if (r0 != 0) goto L_0x00f5
            boolean r0 = defpackage.ju.d()
            if (r0 != 0) goto L_0x00f5
            android.app.Activity r0 = r9.h
            int r0 = defpackage.aaw.d(r0)
            r9.o = r6
            switch(r0) {
                case 0: goto L_0x00f5;
                case 1: goto L_0x00c4;
                case 2: goto L_0x00b0;
                case 3: goto L_0x00b0;
                case 4: goto L_0x00b0;
                default: goto L_0x00af;
            }
        L_0x00af:
            goto L_0x00f5
        L_0x00b0:
            java.lang.Long r0 = defpackage.ju.b()
            long r0 = r0.longValue()
            int r3 = r10.t
            boolean r0 = r9.a(r0, r2, r3)
            if (r0 == 0) goto L_0x00f5
            r9.b(r10, r6, r6)
            goto L_0x00f5
        L_0x00c4:
            boolean r0 = defpackage.ju.c()
            if (r0 == 0) goto L_0x00e2
            r9.o = r7
            java.lang.String r10 = r10.d
            r9.a(r10, r6, r6)
            android.content.SharedPreferences r10 = r9.i
            android.content.SharedPreferences$Editor r10 = r10.edit()
            java.lang.String r0 = "isBackgroundDownload"
            boolean r9 = r9.o
            r10.putBoolean(r0, r9)
            r10.apply()
            goto L_0x00f5
        L_0x00e2:
            java.lang.Long r0 = defpackage.ju.b()
            long r0 = r0.longValue()
            int r3 = r10.t
            boolean r0 = r9.a(r0, r2, r3)
            if (r0 == 0) goto L_0x00f5
            r9.b(r10, r6, r6)
        L_0x00f5:
            r4.apply()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.appupgrade.AppUpgradeController.b(com.amap.bundle.appupgrade.AppUpgradeController, ji):void");
    }

    static /* synthetic */ void c(AppUpgradeController appUpgradeController, ji jiVar) {
        if (jiVar == null) {
            appUpgradeController.q();
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.act_update_noupdate));
            jx.a().a((String) "");
        } else if (appUpgradeController.h == null || appUpgradeController.i == null) {
            appUpgradeController.q();
        } else {
            String str = jiVar.d;
            String str2 = jiVar.b;
            boolean z = jiVar.k;
            Editor edit = appUpgradeController.i.edit();
            appUpgradeController.b = a(jiVar);
            appUpgradeController.a(jiVar.d);
            boolean a2 = !TextUtils.isEmpty(str) ? appUpgradeController.a(jiVar, z, true) : false;
            if (!a2 && ((!TextUtils.isEmpty(str2) || jiVar.x.size() > 0) && !TextUtils.isEmpty(str))) {
                edit.putString("updateUrl", str);
                String str3 = jiVar.e;
                if (TextUtils.isEmpty(str3)) {
                    str3 = "";
                }
                edit.putString("updateAmapAppVersion", str3);
                edit.putBoolean("isForceUpdateApp", false);
                if (!z) {
                    switch (aaw.d(AMapAppGlobal.getApplication())) {
                        case 0:
                            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.act_update_nonet));
                            break;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            appUpgradeController.b(jiVar, false, true);
                            break;
                    }
                } else {
                    edit.putString("needForceUpdateVersion", defpackage.ahp.a.a().a);
                    appUpgradeController.b(jiVar, true, true);
                }
            } else {
                appUpgradeController.q();
                if (!a2) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.act_update_noupdate));
                    jx.a().a(jiVar.s);
                }
            }
            edit.apply();
        }
    }

    static /* synthetic */ boolean o(AppUpgradeController appUpgradeController) {
        int d2 = aaw.d(appUpgradeController.h);
        if (!ju.c() || d2 != 1 || !TextUtils.equals(appUpgradeController.m, "auto") || appUpgradeController.e()) {
            return false;
        }
        return true;
    }
}
