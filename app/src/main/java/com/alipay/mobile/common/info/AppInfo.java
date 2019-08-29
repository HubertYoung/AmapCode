package com.alipay.mobile.common.info;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.quinox.utils.BuildConfigUtil;
import com.alipay.mobile.quinox.utils.MetaDataUtil;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.UUID;

public class AppInfo {
    public static final String DEF_NAME = "ALIPAY_WALLET";
    public static final String NAME = "product_name";
    public static final String VERSION = "product_version";
    private static AppInfo a;
    private final Context b;
    private ActivityManager c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private boolean j;
    private int k;
    private String l;
    private SharedPreferences m;

    private AppInfo(Context context) {
        this.b = context;
        init();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static AppInfo getInstance() {
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("AppManager must be created by calling createInstance(Context context)");
    }

    public static synchronized AppInfo createInstance(Context context) {
        AppInfo appInfo;
        synchronized (AppInfo.class) {
            try {
                if (a == null) {
                    a = new AppInfo(context);
                }
                appInfo = a;
            }
        }
        return appInfo;
    }

    public String getReleaseType() {
        return this.h;
    }

    public void setReleaseType(String releaseType) {
        this.h = releaseType;
    }

    private static String a(String versionName) {
        if (versionName.contains("ctch1")) {
            return versionName.replace("ctch1", "");
        }
        return versionName;
    }

    public void init() {
        try {
            String tpackageName = this.b.getPackageName();
            Log.d("AppInfo", "getPackageName " + tpackageName);
            this.m = this.b.getSharedPreferences(tpackageName + "_config", 0);
            String version = this.m.getString("product_version", null);
            this.e = a(this.b.getPackageManager().getPackageInfo(tpackageName, 0).versionName);
            if (version != null && a(version, this.e)) {
                this.e = version;
            }
            ApplicationInfo applicationInfo = this.b.getPackageManager().getApplicationInfo(this.b.getPackageName(), 16512);
            if ((applicationInfo.flags & 2) != 0) {
                this.j = true;
            }
            if (applicationInfo.metaData != null) {
                this.f = applicationInfo.metaData.getString(NAME, DEF_NAME);
            }
            this.i = (String) this.b.getPackageManager().getApplicationLabel(applicationInfo);
            this.c = (ActivityManager) this.b.getSystemService(WidgetType.ACTIVITY);
            this.k = Process.myPid();
            this.d = "";
            this.g = BehavorReporter.PROVIDE_BY_ALIPAY;
            this.h = "";
        } catch (NameNotFoundException e2) {
            Log.e("AppManager", new StringBuilder("init: ").append(e2).toString() == null ? "" : e2.getMessage());
        } catch (RuntimeException e3) {
            if (e3.getMessage() != null && e3.getMessage().contains("Package manager has died")) {
                init2(e3);
            }
        }
    }

    public void init2(RuntimeException exception) {
        try {
            ApplicationInfo applicationInfo = this.b.getApplicationInfo();
            String tpackageName = this.b.getPackageName();
            Log.d("AppInfo", "getPackageName " + tpackageName);
            this.m = this.b.getSharedPreferences(tpackageName + "_config", 0);
            String version = this.m.getString("product_version", null);
            this.e = a(BuildConfigUtil.getString(tpackageName, "VERSION_NAME", ""));
            if (version != null && a(version, this.e)) {
                this.e = version;
            }
            if ((applicationInfo.flags & 2) != 0) {
                this.j = true;
            }
            this.f = MetaDataUtil.getString(this.b, NAME, DEF_NAME);
            this.i = this.b.getResources().getString(applicationInfo.labelRes);
            if (TextUtils.isEmpty(this.f) || TextUtils.isEmpty(this.e) || TextUtils.isEmpty(this.i)) {
                throw new RuntimeException("mProductName=" + this.f + ",mProductVersion=" + this.e + ",mAppName=" + this.i);
            }
            this.c = (ActivityManager) this.b.getSystemService(WidgetType.ACTIVITY);
            this.k = Process.myPid();
            this.d = "";
            this.g = BehavorReporter.PROVIDE_BY_ALIPAY;
            this.h = "";
        } catch (Throwable t) {
            throw new RuntimeException(t.getMessage(), exception);
        }
    }

    private static boolean a(String version, String mProductVersion) {
        String[] versions = version.split("\\.");
        String[] productVersions = mProductVersion.split("\\.");
        for (int i2 = 0; i2 < versions.length; i2++) {
            int v1 = Integer.parseInt(versions[i2]);
            int v2 = Integer.parseInt(productVersions[i2]);
            if (v1 > v2) {
                return true;
            }
            if (v1 != v2) {
                return false;
            }
        }
        return false;
    }

    public boolean isDebuggable() {
        return this.j;
    }

    public int getPid() {
        return this.k;
    }

    public void setProductVersion(String version) {
        if (version != null) {
            this.m.edit().putString("product_version", version).apply();
            this.e = version;
        }
    }

    public void recoverProductVersion() {
        this.m.edit().remove("product_version").apply();
    }

    public void setChannels(String channels) {
        this.m.edit().putString("channels", channels).apply();
        this.g = channels;
    }

    public void setProductID(String productId) {
        this.m.edit().putString("productId", productId).apply();
        this.d = productId;
    }

    public String getProductID() {
        if (this.d.equals("")) {
            return "Android-container";
        }
        return this.d;
    }

    @Deprecated
    public String getmProductVersion() {
        return this.e;
    }

    public String getProductVersion() {
        return this.e;
    }

    public String getProductName() {
        return this.f;
    }

    public String getAppName() {
        return this.i;
    }

    public String getmChannels() {
        return this.g;
    }

    public String getmAwid() {
        if (this.l != null) {
            return this.l;
        }
        synchronized (this) {
            if (this.l != null) {
                String str = this.l;
                return str;
            }
            this.l = UUID.randomUUID().toString();
            return this.l;
        }
    }

    public long getTotalMemory() {
        return (long) this.c.getProcessMemoryInfo(new int[]{this.k})[0].getTotalPrivateDirty();
    }

    public String getFilesDirPath() {
        return this.b.getFilesDir().getAbsolutePath();
    }

    public String getCacheDirPath() {
        return this.b.getCacheDir().getAbsolutePath();
    }
}
