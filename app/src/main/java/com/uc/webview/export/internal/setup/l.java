package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Pair;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCElapseTime;
import com.uc.webview.export.cyclone.UCHashMap;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;
import java.io.File;

/* compiled from: ProGuard */
public class l extends UCSubSetupTask<l, l> {
    public static float a = 0.0f;
    private static String b = "l";
    private static boolean c = false;
    private Context d = null;

    public static boolean a(int i) {
        return i == 1003 || i == 1006 || i == 1012 || (i >= 2001 && i <= 2012);
    }

    public void run() {
        float f;
        Object obj;
        float f2;
        String str;
        String str2;
        String valueOf;
        Object obj2;
        Throwable th;
        UCElapseTime uCElapseTime;
        Log.d(b, "run");
        this.d = (Context) getOption(UCCore.OPTION_CONTEXT);
        File a2 = a((String) getOption(UCCore.OPTION_DECOMPRESS_ROOT_DIR), ((File) UCMPackageInfo.invoke(10003, this.d)).getAbsolutePath());
        String str3 = b;
        StringBuilder sb = new StringBuilder("run ");
        sb.append(a2.getAbsolutePath());
        Log.d(str3, sb.toString());
        if (!a(a2, 100000)) {
            Log.d(b, "run not enough");
            try {
                Context context = (Context) getOption(UCCore.OPTION_CONTEXT);
                File dir = context.getDir("core_ucmobile", 0);
                File file = new File(dir, "cache");
                f = (float) (dir.getFreeSpace() / 1024);
                try {
                    uCElapseTime = new UCElapseTime();
                    if (a(new File(file, "httpcache"))) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(b(context));
                        sb2.append("/httpcache");
                        File file2 = new File(sb2.toString());
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(b(context));
                        sb3.append("/httpcache_bad_%s");
                        String sb4 = sb3.toString();
                        Object[] objArr = new Object[1];
                        obj2 = "0";
                        try {
                            objArr[0] = Long.valueOf(SystemClock.uptimeMillis());
                            file2.renameTo(new File(String.format(sb4, objArr)));
                        } catch (Throwable th2) {
                            th = th2;
                            th = th;
                            f2 = 0.0f;
                            try {
                                String valueOf2 = String.valueOf(f);
                                callbackStat(new Pair(IWaStat.SEVENZIP_CLEARHTTPCACHE, new UCHashMap().set("cnt", "1").set("cost", obj2).set(IWaStat.KEY_FREE_DISK_SPACE_BEFORE, valueOf2).set(IWaStat.KEY_FREE_DISK_SPACE, String.valueOf(f2))));
                                Log.e("CheckSpace", "After clean disk space: ".concat(String.valueOf(f2)));
                            } catch (Throwable unused) {
                            }
                            throw th;
                        }
                    } else {
                        obj2 = "0";
                    }
                    if (!a(dir, 100000)) {
                        a(file, (String) "httpcache_bad");
                    }
                    if (!a(dir, 100000)) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(b(context));
                        sb5.append("/httpcache");
                        a(new File(sb5.toString()));
                    }
                    if (!a(dir, 100000)) {
                        a(new File(b(context)), (String) "httpcache_bad");
                    }
                    if (!a(dir, 100000)) {
                        a(new File(dir, "Local Storage"));
                    }
                    if (!a(dir, 100000)) {
                        a(dir, (String) "Local Storage_bad");
                    }
                    if (!a(dir, 100000)) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(Environment.getExternalStorageDirectory().getPath());
                        sb6.append("/UCDownloads/video/.apolloCache");
                        a(new File(sb6.toString()));
                    }
                    if (!a(dir, 100000)) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(Environment.getExternalStorageDirectory().getPath());
                        sb7.append("/UCDownloads/video");
                        a(new File(sb7.toString()), (String) ".apolloCache_bad");
                    }
                    f2 = (float) (dir.getFreeSpace() / 1024);
                } catch (Throwable th3) {
                    th = th3;
                    obj2 = "0";
                    th = th;
                    f2 = 0.0f;
                    String valueOf22 = String.valueOf(f);
                    callbackStat(new Pair(IWaStat.SEVENZIP_CLEARHTTPCACHE, new UCHashMap().set("cnt", "1").set("cost", obj2).set(IWaStat.KEY_FREE_DISK_SPACE_BEFORE, valueOf22).set(IWaStat.KEY_FREE_DISK_SPACE, String.valueOf(f2))));
                    Log.e("CheckSpace", "After clean disk space: ".concat(String.valueOf(f2)));
                    throw th;
                }
                try {
                    String valueOf3 = String.valueOf(uCElapseTime.getMilis());
                    try {
                        String valueOf4 = String.valueOf(f);
                        callbackStat(new Pair(IWaStat.SEVENZIP_CLEARHTTPCACHE, new UCHashMap().set("cnt", "1").set("cost", valueOf3).set(IWaStat.KEY_FREE_DISK_SPACE_BEFORE, valueOf4).set(IWaStat.KEY_FREE_DISK_SPACE, String.valueOf(f2))));
                        str = "CheckSpace";
                        str2 = "After clean disk space: ";
                        valueOf = String.valueOf(f2);
                    } catch (Throwable unused2) {
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String valueOf222 = String.valueOf(f);
                    callbackStat(new Pair(IWaStat.SEVENZIP_CLEARHTTPCACHE, new UCHashMap().set("cnt", "1").set("cost", obj2).set(IWaStat.KEY_FREE_DISK_SPACE_BEFORE, valueOf222).set(IWaStat.KEY_FREE_DISK_SPACE, String.valueOf(f2))));
                    Log.e("CheckSpace", "After clean disk space: ".concat(String.valueOf(f2)));
                    throw th;
                }
            } catch (Throwable th5) {
                obj2 = "0";
                th = th5;
                f2 = 0.0f;
                f = 0.0f;
                String valueOf2222 = String.valueOf(f);
                callbackStat(new Pair(IWaStat.SEVENZIP_CLEARHTTPCACHE, new UCHashMap().set("cnt", "1").set("cost", obj2).set(IWaStat.KEY_FREE_DISK_SPACE_BEFORE, valueOf2222).set(IWaStat.KEY_FREE_DISK_SPACE, String.valueOf(f2))));
                Log.e("CheckSpace", "After clean disk space: ".concat(String.valueOf(f2)));
                throw th;
            }
            Log.e(str, str2.concat(valueOf));
        }
        b(this.d, a2.getAbsolutePath());
    }

    private static File a(String str, String str2) {
        if (!j.a(str)) {
            return new File(str);
        }
        return new File(str2);
    }

    public static boolean a(Context context, String str) {
        if (!c) {
            b(context, str);
        }
        return c;
    }

    private static void b(Context context, String str) {
        File a2 = a(str, ((File) UCMPackageInfo.invoke(10003, context)).getAbsolutePath());
        if (!a(a2, 41000)) {
            c = true;
            a = (float) (a2.getFreeSpace() / 1024);
            return;
        }
        c = false;
    }

    private static boolean a(File file, int i) {
        if (((float) (file.getFreeSpace() / 1024)) < ((float) i)) {
            return false;
        }
        try {
            File file2 = new File(file, "test_dir");
            if (!file2.exists()) {
                if (!file2.mkdirs()) {
                    return false;
                }
            }
            file2.delete();
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean a(File file) {
        StringBuilder sb = new StringBuilder();
        sb.append(file.getAbsolutePath());
        sb.append(String.format("_bad_%s", new Object[]{Long.valueOf(SystemClock.uptimeMillis())}));
        File file2 = new File(sb.toString());
        file.renameTo(file2);
        File[] listFiles = file2.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            UCElapseTime uCElapseTime = new UCElapseTime();
            for (File recursiveDelete : listFiles) {
                UCCyclone.recursiveDelete(recursiveDelete, false, null);
                if (uCElapseTime.getMilisCpu() > 2000) {
                    if (a(file2, 100000)) {
                        return true;
                    }
                    uCElapseTime.reset();
                }
            }
        }
        return false;
    }

    private static void a(File file, String str) {
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file2 : listFiles) {
                if (file2.isDirectory() && file2.getName().startsWith(str)) {
                    File[] listFiles2 = file2.listFiles();
                    if (listFiles2 != null) {
                        UCElapseTime uCElapseTime = new UCElapseTime();
                        for (File recursiveDelete : listFiles2) {
                            UCCyclone.recursiveDelete(recursiveDelete, false, null);
                            if (uCElapseTime.getMilisCpu() > 2000) {
                                if (a(file, 100000)) {
                                    break;
                                }
                                uCElapseTime.reset();
                            }
                        }
                    }
                }
            }
        }
    }

    private static String b(Context context) {
        if (VERSION.SDK_INT >= 8) {
            File externalCacheDir = context.getExternalCacheDir();
            if (externalCacheDir != null) {
                return externalCacheDir.getAbsolutePath();
            }
        }
        StringBuilder sb = new StringBuilder("/Android/data/");
        sb.append(context.getPackageName());
        sb.append("/cache");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(Environment.getExternalStorageDirectory().getPath());
        sb3.append(sb2);
        return sb3.toString();
    }

    public static void a(Context context) {
        b(new File(context.getDir("core_ucmobile", 0), "cache"), (String) "httpcache_bad");
        b(new File(b(context)), (String) "httpcache_bad");
        b(context.getDir("core_ucmobile", 0), (String) "Local Storage_bad");
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append("/UCDownloads/video");
        b(new File(sb.toString()), (String) ".apolloCache_bad");
    }

    private static void b(File file, String str) {
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file2 : listFiles) {
                if (file2.isDirectory() && file2.getName().startsWith(str) && file2.exists()) {
                    try {
                        File[] listFiles2 = file2.listFiles();
                        if (listFiles2 != null) {
                            for (File recursiveDelete : listFiles2) {
                                UCCyclone.recursiveDelete(recursiveDelete, false, null);
                            }
                        }
                        file2.delete();
                    } catch (Throwable unused) {
                    }
                }
            }
        }
    }
}
