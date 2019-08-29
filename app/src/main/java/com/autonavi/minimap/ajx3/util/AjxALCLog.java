package com.autonavi.minimap.ajx3.util;

public class AjxALCLog {
    public static final String GROUP_DEFAULT = "ajx3.native";
    public static final String TAG_PERFORMANCE = "track.performance";
    private static ALCInterface mALCInterface;

    public interface ALCInterface {
        void debug(String str, String str2, String str3);

        void error(String str, String str2, String str3);

        void fatal(String str, String str2, String str3);

        void info(String str, String str2, String str3);

        void performance(String str, String str2, String str3);

        void trace(String str, String str2, String str3);

        void warning(String str, String str2, String str3);
    }

    public static void setALCLog(ALCInterface aLCInterface) {
        mALCInterface = aLCInterface;
    }

    public static void fatal(String str, String str2) {
        fatal(GROUP_DEFAULT, str, str2);
    }

    public static void error(String str, String str2) {
        error(GROUP_DEFAULT, str, str2);
    }

    public static void warning(String str, String str2) {
        warning(GROUP_DEFAULT, str, str2);
    }

    public static void info(String str, String str2) {
        info(GROUP_DEFAULT, str, str2);
    }

    public static void debug(String str, String str2) {
        debug(GROUP_DEFAULT, str, str2);
    }

    public static void trace(String str, String str2) {
        trace(GROUP_DEFAULT, str, str2);
    }

    public static void performance(String str, String str2) {
        performance(GROUP_DEFAULT, str, str2);
    }

    public static void fatal(String str, String str2, String str3) {
        if (mALCInterface != null) {
            mALCInterface.fatal(str, str2, str3);
        }
    }

    public static void error(String str, String str2, String str3) {
        if (mALCInterface != null) {
            mALCInterface.error(str, str2, str3);
        }
    }

    public static void warning(String str, String str2, String str3) {
        if (mALCInterface != null) {
            mALCInterface.warning(str, str2, str3);
        }
    }

    public static void info(String str, String str2, String str3) {
        if (mALCInterface != null) {
            mALCInterface.info(str, str2, str3);
        }
    }

    public static void debug(String str, String str2, String str3) {
        if (mALCInterface != null) {
            mALCInterface.debug(str, str2, str3);
        }
    }

    public static void trace(String str, String str2, String str3) {
        if (mALCInterface != null) {
            mALCInterface.trace(str, str2, str3);
        }
    }

    public static void performance(String str, String str2, String str3) {
        if (mALCInterface != null) {
            mALCInterface.performance(str, str2, str3);
        }
    }
}
