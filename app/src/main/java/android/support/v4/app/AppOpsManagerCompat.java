package android.support.v4.app;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public class AppOpsManagerCompat {
    private static final AppOpsManagerImpl IMPL;
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_IGNORED = 1;

    static class AppOpsManager23 extends AppOpsManagerImpl {
        private AppOpsManager23() {
            super(0);
        }

        /* synthetic */ AppOpsManager23(byte b) {
            this();
        }

        public final String a(String str) {
            return AppOpsManagerCompat23.permissionToOp(str);
        }

        public final int a(Context context, String str, int i, String str2) {
            return AppOpsManagerCompat23.noteOp(context, str, i, str2);
        }

        public final int a(Context context, String str, String str2) {
            return AppOpsManagerCompat23.noteProxyOp(context, str, str2);
        }
    }

    static class AppOpsManagerImpl {
        public int a(Context context, String str, int i, String str2) {
            return 1;
        }

        public int a(Context context, String str, String str2) {
            return 1;
        }

        public String a(String str) {
            return null;
        }

        private AppOpsManagerImpl() {
        }

        /* synthetic */ AppOpsManagerImpl(byte b) {
            this();
        }
    }

    static {
        if (VERSION.SDK_INT >= 23) {
            IMPL = new AppOpsManager23(0);
        } else {
            IMPL = new AppOpsManagerImpl(0);
        }
    }

    public static String permissionToOp(@NonNull String str) {
        return IMPL.a(str);
    }

    public static int noteOp(@NonNull Context context, @NonNull String str, int i, @NonNull String str2) {
        return IMPL.a(context, str, i, str2);
    }

    public static int noteProxyOp(@NonNull Context context, @NonNull String str, @NonNull String str2) {
        return IMPL.a(context, str, str2);
    }
}
