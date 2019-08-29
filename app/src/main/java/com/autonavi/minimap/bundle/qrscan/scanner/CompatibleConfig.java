package com.autonavi.minimap.bundle.qrscan.scanner;

import android.os.Build;
import java.util.HashSet;
import java.util.Set;

public class CompatibleConfig {
    private Set<String> torchBlackSet = new HashSet();
    private Set<String> zoomBlackSet;

    public CompatibleConfig() {
        this.torchBlackSet.add("samsung/SCH-I739");
        this.torchBlackSet.add("LENOVO/Lenovo A820t");
        this.zoomBlackSet = new HashSet();
    }

    public boolean checkSupportTorch(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/");
        sb.append(str2);
        return !this.torchBlackSet.contains(sb.toString());
    }

    public boolean checkSupportAutoZoom() {
        StringBuilder sb = new StringBuilder();
        sb.append(Build.MANUFACTURER);
        sb.append("/");
        sb.append(Build.MODEL);
        return !this.zoomBlackSet.contains(sb.toString());
    }
}
