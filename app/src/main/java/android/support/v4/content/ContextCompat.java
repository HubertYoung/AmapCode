package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.NonNull;
import java.io.File;

public class ContextCompat {
    private static final String DIR_ANDROID = "Android";
    private static final String DIR_CACHE = "cache";
    private static final String DIR_DATA = "data";
    private static final String DIR_FILES = "files";
    private static final String DIR_OBB = "obb";
    private static final String TAG = "ContextCompat";

    public static boolean startActivities(Context context, Intent[] intentArr) {
        return startActivities(context, intentArr, null);
    }

    public static boolean startActivities(Context context, Intent[] intentArr, Bundle bundle) {
        int i = VERSION.SDK_INT;
        if (i >= 16) {
            ContextCompatJellybean.a(context, intentArr, bundle);
            return true;
        } else if (i < 11) {
            return false;
        } else {
            ContextCompatHoneycomb.a(context, intentArr);
            return true;
        }
    }

    public static File[] getObbDirs(Context context) {
        File file;
        int i = VERSION.SDK_INT;
        if (i >= 19) {
            return ContextCompatKitKat.b(context);
        }
        if (i >= 11) {
            file = ContextCompatHoneycomb.a(context);
        } else {
            file = buildPath(Environment.getExternalStorageDirectory(), "Android", DIR_OBB, context.getPackageName());
        }
        return new File[]{file};
    }

    public static File[] getExternalFilesDirs(Context context, String str) {
        File file;
        int i = VERSION.SDK_INT;
        if (i >= 19) {
            return ContextCompatKitKat.a(context, str);
        }
        if (i >= 8) {
            file = ContextCompatFroyo.a(context, str);
        } else {
            file = buildPath(Environment.getExternalStorageDirectory(), "Android", "data", context.getPackageName(), "files", str);
        }
        return new File[]{file};
    }

    public static File[] getExternalCacheDirs(Context context) {
        File file;
        int i = VERSION.SDK_INT;
        if (i >= 19) {
            return ContextCompatKitKat.a(context);
        }
        if (i >= 8) {
            file = ContextCompatFroyo.a(context);
        } else {
            file = buildPath(Environment.getExternalStorageDirectory(), "Android", "data", context.getPackageName(), DIR_CACHE);
        }
        return new File[]{file};
    }

    private static File buildPath(File file, String... strArr) {
        for (String str : strArr) {
            if (file == null) {
                file = new File(str);
            } else if (str != null) {
                file = new File(file, str);
            }
        }
        return file;
    }

    public static final Drawable getDrawable(Context context, int i) {
        if (VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.a(context, i);
        }
        return context.getResources().getDrawable(i);
    }

    public static final ColorStateList getColorStateList(Context context, int i) {
        if (VERSION.SDK_INT >= 23) {
            return ContextCompatApi23.a(context, i);
        }
        return context.getResources().getColorStateList(i);
    }

    public static final int getColor(Context context, int i) {
        if (VERSION.SDK_INT >= 23) {
            return ContextCompatApi23.b(context, i);
        }
        return context.getResources().getColor(i);
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String str) {
        if (str != null) {
            return context.checkPermission(str, Process.myPid(), Process.myUid());
        }
        throw new IllegalArgumentException("permission is null");
    }

    public final File getNoBackupFilesDir(Context context) {
        if (VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.a(context);
        }
        return createFilesDir(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    public final File getCodeCacheDir(Context context) {
        if (VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.b(context);
        }
        return createFilesDir(new File(context.getApplicationInfo().dataDir, "code_cache"));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0029, code lost:
        return r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized java.io.File createFilesDir(java.io.File r3) {
        /*
            java.lang.Class<android.support.v4.content.ContextCompat> r0 = android.support.v4.content.ContextCompat.class
            monitor-enter(r0)
            boolean r1 = r3.exists()     // Catch:{ all -> 0x002a }
            if (r1 != 0) goto L_0x0028
            boolean r1 = r3.mkdirs()     // Catch:{ all -> 0x002a }
            if (r1 != 0) goto L_0x0028
            boolean r1 = r3.exists()     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x0017
            monitor-exit(r0)
            return r3
        L_0x0017:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x002a }
            java.lang.String r2 = "Unable to create files subdir "
            r1.<init>(r2)     // Catch:{ all -> 0x002a }
            java.lang.String r3 = r3.getPath()     // Catch:{ all -> 0x002a }
            r1.append(r3)     // Catch:{ all -> 0x002a }
            r3 = 0
            monitor-exit(r0)
            return r3
        L_0x0028:
            monitor-exit(r0)
            return r3
        L_0x002a:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.ContextCompat.createFilesDir(java.io.File):java.io.File");
    }
}
