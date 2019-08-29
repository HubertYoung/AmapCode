package com.amap.bundle.statistics.secondlog;

import java.util.List;

public class AppInfos {
    List<AppInfo> mAppInfoList;
    String mCategory;

    public AppInfos() {
    }

    public AppInfos(String str, List<AppInfo> list) {
        this.mCategory = str;
        this.mAppInfoList = list;
    }
}
