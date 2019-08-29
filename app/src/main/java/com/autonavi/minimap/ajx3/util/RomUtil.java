package com.autonavi.minimap.ajx3.util;

import android.os.Build;
import android.text.TextUtils;

public class RomUtil {
    private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
    private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
    private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";
    public static final String ROM_EMUI = "EMUI";
    public static final String ROM_FLYME = "FLYME";
    public static final String ROM_MIUI = "MIUI";
    public static final String ROM_OPPO = "OPPO";
    public static final String ROM_QIKU = "QIKU";
    public static final String ROM_SMARTISAN = "SMARTISAN";
    public static final String ROM_VIVO = "VIVO";
    private static final String TAG = "Rom";
    private static String sName;
    private static String sVersion;

    public static boolean isEmui() {
        return check(ROM_EMUI);
    }

    public static boolean isMiui() {
        return check(ROM_MIUI);
    }

    public static boolean isVivo() {
        return check(ROM_VIVO);
    }

    public static boolean isOppo() {
        return check(ROM_OPPO);
    }

    public static boolean isFlyme() {
        return check(ROM_FLYME);
    }

    public static boolean is360() {
        return check(ROM_QIKU) || check("360");
    }

    public static boolean isSmartisan() {
        return check(ROM_SMARTISAN);
    }

    public static String getName() {
        if (sName == null) {
            check("");
        }
        return sName;
    }

    public static String getVersion() {
        if (sVersion == null) {
            check("");
        }
        return sVersion;
    }

    public static boolean check(String str) {
        if (sName != null) {
            return sName.equals(str);
        }
        String systemProperty = getSystemProperty(KEY_VERSION_MIUI, "");
        sVersion = systemProperty;
        if (!TextUtils.isEmpty(systemProperty)) {
            sName = ROM_MIUI;
        } else {
            String systemProperty2 = getSystemProperty(KEY_VERSION_EMUI, "");
            sVersion = systemProperty2;
            if (!TextUtils.isEmpty(systemProperty2)) {
                sName = ROM_EMUI;
            } else {
                String systemProperty3 = getSystemProperty(KEY_VERSION_OPPO, "");
                sVersion = systemProperty3;
                if (!TextUtils.isEmpty(systemProperty3)) {
                    sName = ROM_OPPO;
                } else {
                    String systemProperty4 = getSystemProperty(KEY_VERSION_VIVO, "");
                    sVersion = systemProperty4;
                    if (!TextUtils.isEmpty(systemProperty4)) {
                        sName = ROM_VIVO;
                    } else {
                        String systemProperty5 = getSystemProperty(KEY_VERSION_SMARTISAN, "");
                        sVersion = systemProperty5;
                        if (!TextUtils.isEmpty(systemProperty5)) {
                            sName = ROM_SMARTISAN;
                        } else {
                            String str2 = Build.DISPLAY;
                            sVersion = str2;
                            if (str2.toUpperCase().contains(ROM_FLYME)) {
                                sName = ROM_FLYME;
                            } else {
                                sVersion = "unknown";
                                sName = Build.MANUFACTURER.toUpperCase();
                            }
                        }
                    }
                }
            }
        }
        return sName.equals(str);
    }

    public static String getSystemProperty(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{str, str2});
        } catch (Exception e) {
            e.printStackTrace();
            return str2;
        }
    }
}
