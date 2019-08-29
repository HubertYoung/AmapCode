package com.amap.bundle.statistics.secondlog;

import java.util.List;

public class ScanInfo {
    public String createDate;
    public List<AppInfos> deviceAppInfos;
    public List<StorageInfo> deviceStorageInfos;
    public String dic;
    public String dip;
    public String diu1;
    public String diu2;
    public String diu3;
    public String div;
    public String modelNumber;

    public ScanInfo() {
    }

    public ScanInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, List<AppInfos> list, List<StorageInfo> list2, String str8) {
        this.diu1 = str;
        this.diu2 = str2;
        this.diu3 = str3;
        this.dic = str4;
        this.div = str5;
        this.dip = str6;
        this.modelNumber = str7;
        this.deviceAppInfos = list;
        this.deviceStorageInfos = list2;
        this.createDate = str8;
    }
}
