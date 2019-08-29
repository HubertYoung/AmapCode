package defpackage;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.statistics.secondlog.AppInfo;
import com.amap.bundle.statistics.secondlog.AppInfos;
import com.amap.bundle.statistics.secondlog.ScanInfo;
import com.amap.bundle.statistics.secondlog.StorageInfo;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.xidea.el.json.JSONEncoder;

/* renamed from: afm reason: default package */
/* compiled from: SecondLogUtils */
public final class afm {
    public static afm a;

    @SuppressLint({"WrongConstant"})
    static List<AppInfo> a(Context context) {
        List list;
        AppInfo appInfo;
        boolean z;
        ArrayList arrayList = new ArrayList();
        try {
            list = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRecentTasks(Integer.MAX_VALUE, 1);
        } catch (SecurityException unused) {
            list = null;
        }
        if (list == null) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        ActivityInfo resolveActivityInfo = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").resolveActivityInfo(packageManager, 0);
        for (int i = 0; i < list.size(); i++) {
            RecentTaskInfo recentTaskInfo = (RecentTaskInfo) list.get(i);
            Intent intent = new Intent(recentTaskInfo.baseIntent);
            if (((RecentTaskInfo) list.get(i)).origActivity != null) {
                intent.setComponent(recentTaskInfo.origActivity);
            }
            if (resolveActivityInfo == null || !resolveActivityInfo.packageName.equals(intent.getComponent().getPackageName()) || !resolveActivityInfo.name.equals(intent.getComponent().getClassName())) {
                intent.setFlags((intent.getFlags() & -2097153) | 268435456);
                ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
                if (resolveActivity != null) {
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(resolveActivity.activityInfo.packageName, 8192);
                        if (packageInfo != null) {
                            if (packageInfo == null) {
                                appInfo = null;
                            } else {
                                appInfo = new AppInfo();
                                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                                appInfo.mPackageName = packageInfo.packageName;
                                appInfo.mAppName = applicationInfo.loadLabel(packageManager).toString();
                                appInfo.mVersionCode = packageInfo.versionCode;
                                appInfo.mVersionName = packageInfo.versionName;
                                int i2 = applicationInfo.flags;
                                if ((i2 & 128) == 0) {
                                    if ((i2 & 1) != 0) {
                                        z = false;
                                        appInfo.mIsSystemApp = !z;
                                    }
                                }
                                z = true;
                                appInfo.mIsSystemApp = !z;
                            }
                            arrayList.add(appInfo);
                        }
                    } catch (NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return arrayList;
    }

    public final void b(final Context context) {
        new Thread("SecondLogUtilsThread") {
            public final void run() {
                Throwable th;
                Exception exc;
                Context context = context;
                String diu = NetworkParam.getDiu();
                String mac = NetworkParam.getMac();
                String isn = NetworkParam.getIsn();
                String dic = NetworkParam.getDic();
                String div = NetworkParam.getDiv();
                String dip = NetworkParam.getDip();
                String str = Build.MODEL;
                context.getPackageManager();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                arrayList.add(new AppInfos("UserInstalledApp", arrayList2));
                arrayList.add(new AppInfos("SystemApp", arrayList3));
                arrayList.add(new AppInfos("RecentlyUsedApp", afm.a(context)));
                ArrayList arrayList4 = new ArrayList();
                StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
                String str2 = dip;
                String a2 = afm.a(Formatter.formatFileSize(context, ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount())));
                StatFs statFs2 = new StatFs(Environment.getDataDirectory().getPath());
                arrayList4.add(new StorageInfo("Rom", a2, afm.a(Formatter.formatFileSize(context, ((long) statFs2.getBlockSize()) * ((long) statFs2.getAvailableBlocks())))));
                arrayList4.add(new StorageInfo("SdCard", afm.c(context), afm.d(context)));
                ScanInfo scanInfo = new ScanInfo(diu, mac, isn, dic, div, str2, str, arrayList, arrayList4, ahw.a(new Date(), (String) "yyyy-MM-dd HH:mm:ss.SSS"));
                try {
                    byte[] a3 = ahj.a(JSONEncoder.encode(scanInfo));
                    if (a3 != null && a3.length > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(ahk.a(AMapAppGlobal.getApplication()).getAbsolutePath());
                        sb.append("/log2_file_name.txt");
                        File file = new File(sb.toString());
                        FileOutputStream fileOutputStream = null;
                        try {
                            if (file.exists()) {
                                file.delete();
                            }
                            file.createNewFile();
                            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                            try {
                                fileOutputStream2.write(a3);
                                fileOutputStream2.flush();
                                ahe.a((Closeable) fileOutputStream2);
                            } catch (Exception e) {
                                exc = e;
                                fileOutputStream = fileOutputStream2;
                                try {
                                    exc.printStackTrace();
                                    ahe.a((Closeable) fileOutputStream);
                                    Editor edit = context.getSharedPreferences("log2_sp", 0).edit();
                                    edit.putLong("key_pre_last_update_time", System.currentTimeMillis());
                                    edit.commit();
                                } catch (Throwable th2) {
                                    th = th2;
                                    ahe.a((Closeable) fileOutputStream);
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                fileOutputStream = fileOutputStream2;
                                ahe.a((Closeable) fileOutputStream);
                                throw th;
                            }
                        } catch (Exception e2) {
                            exc = e2;
                            exc.printStackTrace();
                            ahe.a((Closeable) fileOutputStream);
                            Editor edit2 = context.getSharedPreferences("log2_sp", 0).edit();
                            edit2.putLong("key_pre_last_update_time", System.currentTimeMillis());
                            edit2.commit();
                        }
                        Editor edit22 = context.getSharedPreferences("log2_sp", 0).edit();
                        edit22.putLong("key_pre_last_update_time", System.currentTimeMillis());
                        edit22.commit();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }.start();
    }

    static String c(Context context) {
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return a(Formatter.formatFileSize(context, ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount())));
        } catch (Exception unused) {
            return "0";
        }
    }

    static String d(Context context) {
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return a(Formatter.formatFileSize(context, ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())));
        } catch (Exception unused) {
            return "0";
        }
    }

    static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        return str.replace("千字节", "KB").replace("兆字节", "MB").replace("吉字节", "GB");
    }
}
