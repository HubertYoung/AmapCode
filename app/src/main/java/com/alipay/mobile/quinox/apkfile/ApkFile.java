package com.alipay.mobile.quinox.apkfile;

import android.content.Context;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.zip.ZipFile;

public final class ApkFile {
    private static final String TAG = "ApkFile";
    private static ApkFile sApkFile;
    private final String mApkFilePath;
    private ZipFile mZipFile;
    private boolean mZipFileInited = false;

    private ApkFile(Context context) {
        this.mApkFilePath = context.getApplicationInfo().sourceDir;
    }

    public static ApkFile getInstance(Context context) {
        if (sApkFile == null) {
            synchronized (ApkFile.class) {
                if (sApkFile == null) {
                    sApkFile = new ApkFile(context);
                }
            }
        }
        return sApkFile;
    }

    public final String getApkFilePath() {
        return this.mApkFilePath;
    }

    public final ZipFile getZipFile() {
        if (!this.mZipFileInited) {
            synchronized (ApkFile.class) {
                if (!this.mZipFileInited) {
                    this.mZipFileInited = true;
                    try {
                        this.mZipFile = new ZipFile(this.mApkFilePath);
                        TraceLogger.e((String) TAG, (String) "success to create java.zip.ZipFile");
                    } catch (Throwable th) {
                        TraceLogger.e(TAG, "failed to access the apk file.", th);
                    }
                }
            }
        }
        return this.mZipFile;
    }
}
