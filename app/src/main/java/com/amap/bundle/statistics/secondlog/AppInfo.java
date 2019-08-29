package com.amap.bundle.statistics.secondlog;

public class AppInfo {
    public String mAppName;
    public boolean mIsSystemApp;
    public String mPackageName;
    public int mVersionCode;
    public String mVersionName;

    public AppInfo() {
    }

    public AppInfo(String str, String str2, String str3, int i, boolean z) {
        this.mAppName = str;
        this.mPackageName = str2;
        this.mVersionName = str3;
        this.mVersionCode = i;
        this.mIsSystemApp = z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mAppName);
        sb.append(",");
        sb.append(this.mPackageName);
        sb.append(",");
        sb.append(this.mVersionName);
        sb.append(",");
        sb.append(this.mVersionCode);
        sb.append(",");
        sb.append(this.mIsSystemApp);
        return sb.toString();
    }
}
