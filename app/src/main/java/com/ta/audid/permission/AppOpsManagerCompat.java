package com.ta.audid.permission;

import android.content.Context;
import android.os.Build.VERSION;

final class AppOpsManagerCompat {
    private static final AppOpsManagerImpl IMPL;
    public static final int MODE_IGNORED = 1;

    static class AppOpsManager23 extends AppOpsManagerImpl {
        private AppOpsManager23() {
            super();
        }

        public String permissionToOp(String str) {
            return AppOpsManagerCompat23.permissionToOp(str);
        }

        public int noteOp(Context context, String str, int i, String str2) {
            return AppOpsManagerCompat23.noteOp(context, str, i, str2);
        }

        public int noteProxyOp(Context context, String str, String str2) {
            return AppOpsManagerCompat23.noteProxyOp(context, str, str2);
        }
    }

    static class AppOpsManagerImpl {
        public int noteOp(Context context, String str, int i, String str2) {
            return 1;
        }

        public int noteProxyOp(Context context, String str, String str2) {
            return 1;
        }

        public String permissionToOp(String str) {
            return null;
        }

        private AppOpsManagerImpl() {
        }
    }

    static {
        if (VERSION.SDK_INT >= 23) {
            IMPL = new AppOpsManager23();
        } else {
            IMPL = new AppOpsManagerImpl();
        }
    }

    private AppOpsManagerCompat() {
    }

    public static String permissionToOp(String str) {
        return IMPL.permissionToOp(str);
    }

    public static int noteOp(Context context, String str, int i, String str2) {
        return IMPL.noteOp(context, str, i, str2);
    }

    public static int noteProxyOp(Context context, String str, String str2) {
        return IMPL.noteProxyOp(context, str, str2);
    }
}
