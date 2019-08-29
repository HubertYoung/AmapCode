package com.alipay.camera.util;

import android.text.TextUtils;
import java.util.HashSet;

public class FocusWhiteList {
    private static HashSet<String> a;
    private static HashSet<String> b;

    static {
        HashSet<String> hashSet = new HashSet<>();
        a = hashSet;
        hashSet.add("Xiaomi/Redmi Note 4");
        a.add("Xiaomi/Redmi Note 3");
        a.add("Xiaomi/Redmi Note 2");
        a.add("Xiaomi/Redmi 3S");
        a.add("Xiaomi/MI NOTE Pro");
        a.add("Xiaomi/MI 5");
        a.add("Xiaomi/MI MAX");
        a.add("HUAWEI/HUAWEI P8max");
        a.add("HUAWEI/CUN-AL00");
        a.add("HUAWEI/Nexus 6P");
        a.add("LeEco/Le X620");
        a.add("Meizu/M685C");
        a.add("Meizu/MX6");
        a.add("Meizu/m3");
        a.add("vivo/vivo Xplay6");
        a.add("vivo/vivo Y51A");
        a.add("vivo/vivo X6D");
        a.add("lge/LG-H990");
        a.add("GiONEE/GN5001S");
        a.add("GIONEE/GN5005");
        a.add("nubia/NX531J");
        a.add("samsung/SM-A8000");
        a.add("samsung/SM-G5500");
        a.add("samsung/SM-G5700");
        a.add("samsung/SM-G6000");
        a.add("Letv/Letv X501");
        a.add("LeMobile/Le X620");
        a.add("LeMobile/Le X820");
        a.add("motorola/XT1650-05");
        a.add("google/Pixel");
    }

    public static void refreshConfigList(String config) {
        if (!TextUtils.isEmpty(config) && config.contains(";")) {
            if (b == null) {
                b = new HashSet<>();
            }
            b.clear();
            String[] subConfigs = config.split(";");
            int i = 0;
            while (subConfigs != null && i < subConfigs.length) {
                b.add(subConfigs[i]);
                i++;
            }
        }
    }

    public static boolean inWhiteList(String brand, String model) {
        if (a == null && b == null) {
            return false;
        }
        String modelSign = brand + "/" + model;
        boolean contained = a.contains(modelSign);
        if (contained || b == null) {
            return contained;
        }
        return b.contains(modelSign);
    }
}
