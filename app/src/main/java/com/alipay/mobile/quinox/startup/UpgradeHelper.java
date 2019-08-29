package com.alipay.mobile.quinox.startup;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.alipay.mobile.quinox.utils.BuildConfigUtil;
import com.alipay.mobile.quinox.utils.FileUtil;
import com.alipay.mobile.quinox.utils.MonitorLogger;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.io.File;
import java.util.Set;

public class UpgradeHelper {
    public static final String CODE_PATH = "code_path";
    public static final String OLD_VERSION = "version";
    private static final String TAG = "UpgradeHelper";
    public static final String VERSION = "product_version";
    private static UpgradeHelper sInstance;
    private Context mContext;
    private String mLastCodePath;
    private String mLastProductVersion;
    private String mProductVersion;
    private SharedPreferences mSharedPreferences;
    private UpgradeEnum mUpgrade = UpgradeEnum.UPGRADE;

    public enum UpgradeEnum {
        NONE,
        NEW,
        UPGRADE,
        DOWNGRADE
    }

    public static UpgradeHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (UpgradeHelper.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new UpgradeHelper(context);
                    }
                }
            }
        }
        return sInstance;
    }

    private UpgradeHelper(Context context) {
        this.mContext = context;
    }

    public void init() {
        String packageName = this.mContext.getPackageName();
        TraceLogger.d((String) TAG, "UpgradeHelper : getPackageName=".concat(String.valueOf(packageName)));
        Context context = this.mContext;
        StringBuilder sb = new StringBuilder();
        sb.append(packageName);
        sb.append("_config");
        this.mSharedPreferences = context.getSharedPreferences(sb.toString(), 0);
        String str = null;
        if (this.mSharedPreferences.contains("version")) {
            str = this.mSharedPreferences.getString("version", null);
            this.mSharedPreferences.edit().remove("version").apply();
        }
        this.mUpgrade = upgrade(str);
        if (this.mSharedPreferences.contains(CODE_PATH)) {
            this.mLastCodePath = this.mSharedPreferences.getString(CODE_PATH, "");
        }
    }

    private UpgradeEnum upgrade(String str) {
        UpgradeEnum upgradeEnum = UpgradeEnum.NEW;
        String string = this.mSharedPreferences.getString("product_version", null);
        try {
            this.mProductVersion = getVersionFromPackageInfo();
            String versionFromBuildConfig = getVersionFromBuildConfig();
            if (!TextUtils.isEmpty(versionFromBuildConfig) && !TextUtils.equals(this.mProductVersion, versionFromBuildConfig)) {
                StringBuilder sb = new StringBuilder("packageInfo version=");
                sb.append(this.mProductVersion);
                sb.append(", buildConfig version=");
                sb.append(versionFromBuildConfig);
                MonitorLogger.exception((String) "packageInfoMismatch", (Throwable) null, sb.toString());
                this.mProductVersion = versionFromBuildConfig;
            }
            if (!TextUtils.isEmpty(string)) {
                this.mLastProductVersion = string;
                UpgradeEnum compareVersion = compareVersion(this.mProductVersion, string, 4);
                try {
                    StringBuilder sb2 = new StringBuilder("UpgradeHelper(oldVersion=");
                    sb2.append(string);
                    sb2.append(" vs newVersion=");
                    sb2.append(this.mProductVersion);
                    sb2.append(") : ");
                    sb2.append(compareVersion);
                    TraceLogger.d((String) TAG, sb2.toString());
                    return compareVersion;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    upgradeEnum = compareVersion;
                    th = th2;
                    TraceLogger.w(TAG, "upgrade(Exception) upgradeEnum=".concat(String.valueOf(upgradeEnum)), th);
                    return upgradeEnum;
                }
            } else if (!TextUtils.isEmpty(str)) {
                this.mLastProductVersion = string;
                UpgradeEnum compareVersion2 = compareVersion(this.mProductVersion, str, 4);
                try {
                    StringBuilder sb3 = new StringBuilder("UpgradeHelper(oldVersion=");
                    sb3.append(str);
                    sb3.append(" vs newVersion=");
                    sb3.append(this.mProductVersion);
                    sb3.append(") : ");
                    sb3.append(compareVersion2);
                    TraceLogger.d((String) TAG, sb3.toString());
                    return compareVersion2;
                } catch (Throwable th3) {
                    th = th3;
                    upgradeEnum = compareVersion2;
                    TraceLogger.w(TAG, "upgrade(Exception) upgradeEnum=".concat(String.valueOf(upgradeEnum)), th);
                    return upgradeEnum;
                }
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(upgradeEnum);
                sb4.append(" : version=");
                sb4.append(this.mProductVersion);
                TraceLogger.i((String) TAG, sb4.toString());
                return upgradeEnum;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    private String getVersionFromPackageInfo() throws NameNotFoundException {
        return clearVersionName(this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionName);
    }

    private String getVersionFromBuildConfig() {
        return clearVersionName(BuildConfigUtil.getString(this.mContext.getPackageName(), "VERSION_NAME", ""));
    }

    public void setUpgrade(UpgradeEnum upgradeEnum) {
        this.mUpgrade = upgradeEnum;
    }

    public UpgradeEnum getUpgrade() {
        return this.mUpgrade;
    }

    @Deprecated
    public boolean isUpgrade() {
        return UpgradeEnum.NONE != this.mUpgrade;
    }

    private static String clearVersionName(String str) {
        return str.contains("ctch1") ? str.replace("ctch1", "") : str;
    }

    private UpgradeEnum compareVersion(String str, String str2, int i) {
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int min = Math.min(Math.min(split.length, split2.length), i);
        for (int i2 = 0; i2 < min; i2++) {
            int i3 = (Long.parseLong(split[i2]) > Long.parseLong(split2[i2]) ? 1 : (Long.parseLong(split[i2]) == Long.parseLong(split2[i2]) ? 0 : -1));
            if (i3 > 0) {
                return UpgradeEnum.UPGRADE;
            }
            if (i3 < 0) {
                return UpgradeEnum.DOWNGRADE;
            }
        }
        if (min < i && split.length != split2.length) {
            if (split.length > split2.length) {
                return UpgradeEnum.UPGRADE;
            }
            if (split.length < split2.length) {
                return UpgradeEnum.DOWNGRADE;
            }
        }
        return UpgradeEnum.NONE;
    }

    @Deprecated
    public void clearOldPluginLibs() {
        clearOldPluginLibs(null);
    }

    public void clearOldPluginLibs(Set<String> set) {
        long currentTimeMillis = System.currentTimeMillis();
        TraceLogger.d((String) TAG, (String) "clearOldPluginLibs start.");
        File dir = this.mContext.getDir("plugins_lib", 0);
        FileUtil.deleteFiles(dir, null, set);
        dir.mkdirs();
        StringBuilder sb = new StringBuilder("clearOldPluginLibs end. cost ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        sb.append(" ms.");
        TraceLogger.d((String) TAG, sb.toString());
    }

    @Deprecated
    public void clearOldPluginOpts() {
        clearOldPluginOpts(null);
    }

    public void clearOldPluginOpts(Set<String> set) {
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("clearOldPluginOpts start. excludePrefixes=");
        sb.append(StringUtil.collection2String(set));
        TraceLogger.d((String) TAG, sb.toString());
        File dir = this.mContext.getDir("plugins_opt", 0);
        FileUtil.deleteFiles(dir, null, set);
        dir.mkdirs();
        StringBuilder sb2 = new StringBuilder("clearOldPluginOpts end. cost ");
        sb2.append(System.currentTimeMillis() - currentTimeMillis);
        sb2.append(" ms.");
        TraceLogger.d((String) TAG, sb2.toString());
    }

    @Deprecated
    public void clearOldPluginFiles() {
        clearOldPluginFiles(null);
    }

    public void clearOldPluginFiles(Set<String> set) {
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("clearOldPluginFiles start. excludePrefixes=");
        sb.append(StringUtil.collection2String(set));
        TraceLogger.w(TAG, sb.toString(), new RuntimeException("just print stack"));
        File dir = this.mContext.getDir("plugins", 0);
        FileUtil.deleteFiles(dir, null, set);
        dir.mkdirs();
        File dir2 = this.mContext.getDir("plugins_patch", 0);
        FileUtil.deleteFiles(dir2);
        dir2.mkdirs();
        StringBuilder sb2 = new StringBuilder("clearOldPluginFiles end. cost ");
        sb2.append(System.currentTimeMillis() - currentTimeMillis);
        sb2.append(" ms.");
        TraceLogger.d((String) TAG, sb2.toString());
    }

    public String getProductVersion() {
        return this.mProductVersion;
    }

    public String getLastProductVersion() {
        return this.mLastProductVersion;
    }

    public String getLastCodePath() {
        return this.mLastCodePath;
    }

    public void setProductVersion() {
        if (UpgradeEnum.NONE == this.mUpgrade || this.mProductVersion == null) {
            StringBuilder sb = new StringBuilder("Ignore setProductVersion(mUpgrade=");
            sb.append(this.mUpgrade);
            sb.append(", mProductVersion=");
            sb.append(this.mProductVersion);
            sb.append(")");
            TraceLogger.d((String) TAG, sb.toString());
        } else {
            this.mSharedPreferences.edit().putString("product_version", this.mProductVersion).commit();
            StringBuilder sb2 = new StringBuilder("setProductVersion(mUpgrade=");
            sb2.append(this.mUpgrade);
            sb2.append(", mProductVersion=");
            sb2.append(this.mProductVersion);
            sb2.append(")");
            TraceLogger.d((String) TAG, sb2.toString());
        }
        String packageCodePath = this.mContext.getPackageCodePath();
        if (!TextUtils.isEmpty(packageCodePath) && !TextUtils.equals(packageCodePath, this.mLastCodePath)) {
            this.mSharedPreferences.edit().putString(CODE_PATH, packageCodePath).commit();
        }
    }
}
