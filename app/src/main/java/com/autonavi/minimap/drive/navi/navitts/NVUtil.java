package com.autonavi.minimap.drive.navi.navitts;

import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;

public final class NVUtil {

    public enum SavingNewPackageName {
        SNPN_PACKAGE_NAME_ALREADY_EXISTS,
        SNPN_PACKAGE_NAME_NOT_CHANGED,
        SNPN_RENAME_CURRENT_PACKAGE_NAME,
        SNPN_PACKAGE_NAME_NOT_FOUND
    }

    public static String a(int i) {
        switch (i) {
            case 0:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_MINE;
            case 1:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_GLOBAL;
            case 101:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_GUIDE;
            case 102:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_GUIDE;
            case 1001:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_NAVI;
            case 1002:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_NAVI;
            case 1003:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_NAVI;
            default:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_OTHER;
        }
    }

    public static String a() {
        if (NetworkReachability.b()) {
            return NetworkReachability.a() ? "wifi" : "moblie";
        }
        return "network not connected";
    }
}
