package com.alipay.mobile.nebula.util;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class H5ZipUtil {
    public static final String TAG = "H5ZipUtil";

    public static boolean unZip(InputStream isUnZip, String destPath) {
        boolean needSecurityCheck = true;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            needSecurityCheck = !TextUtils.equals("NO", h5ConfigProvider.getConfig("h5_amrUnzipSecCheck"));
        }
        ZipInputStream zipIn = null;
        byte[] buf = H5IOUtils.getBuf(2048);
        try {
            ZipInputStream zipIn2 = new ZipInputStream(new BufferedInputStream(isUnZip));
            while (true) {
                try {
                    ZipEntry zipEntry = zipIn2.getNextEntry();
                    if (zipEntry != null) {
                        String entryName = zipEntry.getName();
                        if (!needSecurityCheck || H5SecurityUtil.pathSecurityCheck(entryName)) {
                            File file = new File(destPath + entryName);
                            if (zipEntry.isDirectory()) {
                                file.mkdirs();
                            } else {
                                File parent = file.getParentFile();
                                if (!parent.exists()) {
                                    parent.mkdirs();
                                }
                                FileOutputStream fileOut = null;
                                try {
                                    FileOutputStream fileOut2 = new FileOutputStream(file);
                                    while (true) {
                                        try {
                                            int readedBytes = zipIn2.read(buf);
                                            if (readedBytes <= 0) {
                                                break;
                                            }
                                            fileOut2.write(buf, 0, readedBytes);
                                        } catch (Throwable th) {
                                            th = th;
                                            fileOut = fileOut2;
                                            H5IOUtils.closeQuietly(fileOut);
                                            throw th;
                                        }
                                    }
                                    H5IOUtils.closeQuietly(fileOut2);
                                } catch (Throwable th2) {
                                    th = th2;
                                    H5IOUtils.closeQuietly(fileOut);
                                    throw th;
                                }
                            }
                        } else {
                            H5Log.w(TAG, "zipEntry is illegal");
                        }
                    } else {
                        H5IOUtils.returnBuf(buf);
                        H5IOUtils.closeQuietly(zipIn2);
                        ZipInputStream zipInputStream = zipIn2;
                        return true;
                    }
                } catch (Exception e) {
                    e = e;
                    zipIn = zipIn2;
                    try {
                        H5Log.e((String) TAG, (Throwable) e);
                        H5IOUtils.returnBuf(buf);
                        H5IOUtils.closeQuietly(zipIn);
                        return false;
                    } catch (Throwable th3) {
                        th = th3;
                        H5IOUtils.returnBuf(buf);
                        H5IOUtils.closeQuietly(zipIn);
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    zipIn = zipIn2;
                    H5IOUtils.returnBuf(buf);
                    H5IOUtils.closeQuietly(zipIn);
                    throw th;
                }
            }
        } catch (Exception e2) {
            e = e2;
            H5Log.e((String) TAG, (Throwable) e);
            H5IOUtils.returnBuf(buf);
            H5IOUtils.closeQuietly(zipIn);
            return false;
        }
    }

    public static boolean unZip(String unZipFileName, String destPath) {
        boolean unZipState = false;
        try {
            return unZip((InputStream) new FileInputStream(unZipFileName), destPath);
        } catch (FileNotFoundException e) {
            H5Log.e(TAG, LogCategory.CATEGORY_EXCEPTION, e);
            return unZipState;
        }
    }
}
