package com.alipay.mobile.nebula.webview;

public class Version {
    private static final int STORE_OFFSET = 16;

    public static class Major {
        public int compare(int v1, int v2) {
            int v1Major = Version.getMajor(v1);
            int v2Major = Version.getMajor(v2);
            if (v1Major == v2Major) {
                return 0;
            }
            if (v1Major > v2Major) {
                return 1;
            }
            return -1;
        }
    }

    public static class Minor {
        public int compare(int v1, int v2) {
            int v1Minor = Version.getMinor(v1);
            int v2Minor = Version.getMinor(v2);
            if (v1Minor == v2Minor) {
                return 0;
            }
            if (v1Minor > v2Minor) {
                return 1;
            }
            return -1;
        }
    }

    public static int getMajor(int v) {
        return v >> 16;
    }

    public static int getMinor(int v) {
        return (v << 16) >> 16;
    }

    public static String toString(int v) {
        return "Version(major: " + getMajor(v) + ", minor: " + getMinor(v) + ")";
    }

    public static int build(int major, int minor) {
        return (major << 16) | minor;
    }
}
