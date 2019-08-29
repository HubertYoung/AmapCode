package com.alipay.mobile.common.apkutil;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.quinox.bundle.IBundleOperator;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class ApkUtil {
    public ApkUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static AppInfoData getApkFileInfo(Context ctx, String apkPath) {
        File apkFile = new File(apkPath);
        if (!apkFile.exists() || (!apkPath.toLowerCase().endsWith(".apk") && !apkPath.toLowerCase().endsWith(IBundleOperator.EXTENSION))) {
            LoggerFactory.getTraceLogger().error((String) "ApkUtil", (String) "file path is not corrent");
            return null;
        }
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo packageInfo = pm.getPackageArchiveInfo(apkPath, 1);
            if (packageInfo == null) {
                return null;
            }
            AppInfoData appInfoData = new AppInfoData();
            appInfoData.setApppackage(packageInfo.packageName);
            appInfoData.setAppversion(packageInfo.versionName);
            appInfoData.setAppversionCode(packageInfo.versionCode);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (applicationInfo != null) {
                applicationInfo.publicSourceDir = apkPath;
                applicationInfo.sourceDir = apkPath;
                appInfoData.setAppicon(applicationInfo.loadIcon(pm));
            }
            Resources res = getResourcesForApplication(apkPath, (Application) ctx.getApplicationContext());
            if (applicationInfo.labelRes != 0) {
                appInfoData.setAppname((String) res.getText(applicationInfo.labelRes));
                return appInfoData;
            }
            String apkName = apkFile.getName();
            appInfoData.setAppname(apkName.substring(0, apkName.lastIndexOf(".")));
            return appInfoData;
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) "ApkUtil", (Throwable) e);
            return null;
        }
    }

    public static Resources getResourcesForApplication(String archiveFilePath, Application refApplication) {
        try {
            AssetManager assets = AssetManager.class.newInstance();
            if (((Integer) assets.getClass().getMethod("addAssetPath", new Class[]{String.class}).invoke(assets, new Object[]{archiveFilePath})).intValue() == 0) {
                return null;
            }
            return new Resources(assets, refApplication.getResources().getDisplayMetrics(), refApplication.getResources().getConfiguration());
        } catch (IllegalArgumentException e) {
            LoggerFactory.getTraceLogger().warn((String) "ApkUtil", (Throwable) e);
            return null;
        } catch (IllegalAccessException e2) {
            LoggerFactory.getTraceLogger().warn((String) "ApkUtil", (Throwable) e2);
            return null;
        } catch (InvocationTargetException e3) {
            LoggerFactory.getTraceLogger().warn((String) "ApkUtil", (Throwable) e3);
            return null;
        } catch (InstantiationException e4) {
            LoggerFactory.getTraceLogger().warn((String) "ApkUtil", (Throwable) e4);
            return null;
        } catch (NoSuchMethodException e5) {
            LoggerFactory.getTraceLogger().warn((String) "ApkUtil", (Throwable) e5);
            return null;
        }
    }
}
