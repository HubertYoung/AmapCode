package com.alipay.mobile.nebula.util;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.monitor.util.FileUtils.PathType;
import com.alipay.mobile.monitor.util.FileUtils.Result;
import com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class H5FileUtil {
    private static final int IO_BUFFER_SIZE = 2048;
    public static final String TAG = "H5FileUtil";

    public static boolean create(String absPath) {
        return create(absPath, false);
    }

    public static boolean create(String absPath, boolean force) {
        boolean z = false;
        if (TextUtils.isEmpty(absPath)) {
            return z;
        }
        if (exists(absPath)) {
            return true;
        }
        mkdirs(getParent(absPath), force);
        try {
            return new File(absPath).createNewFile();
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
            return z;
        }
    }

    public static boolean mkdirs(String absPath) {
        return mkdirs(absPath, false);
    }

    public static boolean mkdirs(String absPath, boolean force) {
        File file = new File(absPath);
        if (exists(absPath) && !isFolder(absPath)) {
            if (!force) {
                return false;
            }
            delete(file);
        }
        try {
            file.mkdirs();
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
        }
        return exists(file);
    }

    public static boolean move(String srcPath, String dstPath) {
        return move(srcPath, dstPath, false);
    }

    public static boolean move(String srcPath, String dstPath, boolean force) {
        boolean z = false;
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath) || !exists(srcPath)) {
            return z;
        }
        if (exists(dstPath)) {
            if (!force) {
                return z;
            }
            delete(dstPath);
        }
        try {
            return new File(srcPath).renameTo(new File(dstPath));
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
            return z;
        }
    }

    public static boolean checkPathValid(String path) {
        if (TextUtils.isEmpty(path) || (!TextUtils.equals(path, "/") && !path.contains("../") && !path.contains("/.."))) {
            return true;
        }
        return false;
    }

    public static boolean delete(String absPath) {
        if (TextUtils.isEmpty(absPath) || absPath.equals("/")) {
            return false;
        }
        return delete(new File(absPath));
    }

    private static boolean useSafeDelete() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_useSafeDelete"))) {
            return true;
        }
        return false;
    }

    public static boolean delete(File file) {
        if (!exists(file) || TextUtils.equals("/", file.getAbsolutePath())) {
            return true;
        }
        if (useSafeDelete()) {
            Result result = PathType.checkPathValid(H5Utils.getContext(), file, PathType.PATH_TYPE_ANY);
            if (result == null) {
                return true;
            }
            if (!result.success) {
                H5LogUtil.logNebulaTech(H5LogData.seedId("h5_delete_inValid_file").param4().add("filePath", file.getAbsolutePath()).add("errMessage", result.errMessage).add("errCode", Integer.valueOf(result.errCode)));
                return false;
            }
        }
        if (file.isFile()) {
            H5Log.d(TAG, "deleteFile:" + file.getAbsolutePath());
            return file.delete();
        }
        boolean result2 = true;
        File[] files = file.listFiles();
        if (files == null) {
            return true;
        }
        for (File delete : files) {
            result2 |= delete(delete);
        }
        boolean result3 = result2 | file.delete();
        H5Log.d(TAG, "deleteFile:" + file.getAbsolutePath() + " result:" + result3);
        return result3;
    }

    public static boolean exists(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return false;
        }
        return exists(new File(absPath));
    }

    public static boolean exists(File file) {
        return file != null && file.exists();
    }

    public static String getParent(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return null;
        }
        return getParent(new File(cleanPath(absPath)));
    }

    public static String getParent(File file) {
        if (file == null) {
            return null;
        }
        return file.getParent();
    }

    public static boolean childOf(String childPath, String parentPath) {
        if (TextUtils.isEmpty(childPath) || TextUtils.isEmpty(parentPath)) {
            return false;
        }
        return cleanPath(childPath).startsWith(cleanPath(parentPath) + File.separator);
    }

    public static int childCount(String absPath) {
        if (!exists(absPath)) {
            return 0;
        }
        File[] children = new File(absPath).listFiles();
        if (children == null || children.length == 0) {
            return 0;
        }
        return children.length;
    }

    public static String cleanPath(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return absPath;
        }
        try {
            return new File(absPath).getCanonicalPath();
        } catch (Exception e) {
            H5Log.e(TAG, "Exception", e);
            return absPath;
        }
    }

    public static long size(String absPath) {
        if (absPath == null) {
            return 0;
        }
        return size(new File(absPath));
    }

    public static long size(File file) {
        if (!exists(file)) {
            return 0;
        }
        long length = 0;
        if (isFile(file)) {
            return file.length();
        }
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return 0;
        }
        for (File child : files) {
            length += size(child);
        }
        return length;
    }

    public static boolean copy(String srcPath, String dstPath) {
        return copy(srcPath, dstPath, false);
    }

    /* JADX INFO: finally extract failed */
    public static boolean copy(String srcPath, String dstPath, boolean force) {
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath)) {
            return false;
        }
        if (srcPath.equals(dstPath)) {
            return true;
        }
        if (!exists(srcPath) || !isFile(srcPath)) {
            return false;
        }
        if (exists(dstPath)) {
            if (!force) {
                return false;
            }
            delete(dstPath);
        }
        if (!create(dstPath)) {
            return false;
        }
        try {
            FileInputStream in = new FileInputStream(srcPath);
            FileOutputStream out = new FileOutputStream(dstPath);
            byte[] buffer = H5IOUtils.getBuf(2048);
            while (true) {
                try {
                    int len = in.read(buffer);
                    if (len != -1) {
                        out.write(buffer, 0, len);
                    } else {
                        out.flush();
                        H5IOUtils.returnBuf(buffer);
                        H5IOUtils.closeQuietly(in);
                        H5IOUtils.closeQuietly(out);
                        return true;
                    }
                } catch (Exception e) {
                    H5Log.e(TAG, "exception detail", e);
                    H5IOUtils.returnBuf(buffer);
                    H5IOUtils.closeQuietly(in);
                    H5IOUtils.closeQuietly(out);
                    return false;
                } catch (Throwable th) {
                    H5IOUtils.returnBuf(buffer);
                    H5IOUtils.closeQuietly(in);
                    H5IOUtils.closeQuietly(out);
                    throw th;
                }
            }
        } catch (Exception e2) {
            H5Log.e(TAG, "exception detail", e2);
            return false;
        }
    }

    public static String fileName(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return null;
        }
        return new File(cleanPath(absPath)).getName();
    }

    public static String getExtension(File file) {
        if (file == null) {
            return null;
        }
        return getExtension(file.getName());
    }

    public static String getFileExtensionFromUrl(String url) {
        String type = MimeTypeMap.getFileExtensionFromUrl(url);
        if (!TextUtils.isEmpty(type)) {
            return type;
        }
        String type2 = getExtension(url);
        if (TextUtils.isEmpty(type2) || !MimeTypeMap.getSingleton().hasExtension(type2.toLowerCase())) {
            return null;
        }
        return type2;
    }

    @Deprecated
    public static String getExtension(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int index = fileName.lastIndexOf(46);
        if (index < 0 || index >= fileName.length() - 1) {
            return "";
        }
        return fileName.substring(index + 1);
    }

    public static String getMimeType(File file) {
        if (file == null) {
            return "*/*";
        }
        return getMimeType(file.getName());
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = getFileExtensionFromUrl(url);
        if ("js".equalsIgnoreCase(extension)) {
            return "application/javascript";
        }
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @Nullable
    public static String getMimeTypeFromContentType(String contentType) {
        try {
            String[] splits = contentType.split(";");
            if (splits[0] != null) {
                return splits[0].trim();
            }
        } catch (Throwable t) {
            H5Log.e(TAG, "getMimeTypeFromContentType", t);
        }
        return null;
    }

    public static String getExtensionFromMimeType(String mimeType) {
        if (TextUtils.isEmpty(mimeType)) {
            return null;
        }
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
    }

    public static boolean isFile(String absPath) {
        if (!exists(absPath)) {
            return false;
        }
        return isFile(new File(absPath));
    }

    public static boolean isFile(File file) {
        return file != null && file.isFile();
    }

    public static boolean isFolder(String absPath) {
        if (!exists(absPath)) {
            return false;
        }
        return new File(absPath).isDirectory();
    }

    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int index = filePath.lastIndexOf("/");
        if (index <= 0 || index >= filePath.length() - 1) {
            return filePath;
        }
        return filePath.substring(index + 1, filePath.length());
    }

    public static String getFileStem(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            return fileName.substring(0, index);
        }
        return "";
    }

    public static String getFileSHA1(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return null;
        }
        File file = new File(absPath);
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        String fileHash = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            H5Log.e(TAG, "exception detail.", e1);
        }
        if (fis == null) {
            return null;
        }
        byte[] buffer = H5IOUtils.getBuf(2048);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            while (true) {
                int length = fis.read(buffer);
                if (length <= 0) {
                    break;
                }
                messageDigest.update(buffer, 0, length);
            }
            fileHash = H5SecurityUtil.bytes2Hex(messageDigest.digest());
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
        } finally {
            H5IOUtils.returnBuf(buffer);
            H5IOUtils.closeQuietly(fis);
        }
        if (!TextUtils.isEmpty(fileHash)) {
            return fileHash.trim();
        }
        return fileHash;
    }

    public static String getFileMD5(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return null;
        }
        File file = new File(absPath);
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        String fileHash = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            H5Log.e(TAG, "exception detail.", e1);
        }
        if (fis == null) {
            return null;
        }
        byte[] buffer = H5IOUtils.getBuf(2048);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            while (true) {
                int length = fis.read(buffer);
                if (length <= 0) {
                    break;
                }
                messageDigest.update(buffer, 0, length);
            }
            fileHash = H5SecurityUtil.bytes2Hex(messageDigest.digest());
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
            try {
                fis.close();
            } catch (IOException ioe) {
                H5Log.e(TAG, "IOException", ioe);
            }
        } finally {
            H5IOUtils.returnBuf(buffer);
            H5IOUtils.closeQuietly(fis);
        }
        if (!TextUtils.isEmpty(fileHash)) {
            return fileHash.trim();
        }
        return fileHash;
    }

    public static String getText(String absPath) {
        String str = null;
        if (exists(absPath)) {
            File file = new File(absPath);
            int length = (int) file.length();
            if (length <= 20480) {
                ByteArrayOutputStream bos = null;
                BufferedInputStream in = null;
                byte[] buffer = H5IOUtils.getBuf(1024);
                try {
                    ByteArrayOutputStream bos2 = new PoolingByteArrayOutputStream(length);
                    try {
                        BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(file));
                        while (true) {
                            try {
                                int len = in2.read(buffer);
                                if (-1 == len) {
                                    break;
                                }
                                bos2.write(buffer, 0, len);
                            } catch (Exception e) {
                                e = e;
                                in = in2;
                                bos = bos2;
                                try {
                                    H5Log.e(TAG, "exception detail", e);
                                    H5IOUtils.getByteArrayPool().returnBuf(buffer);
                                    H5IOUtils.closeQuietly(bos);
                                    H5IOUtils.closeQuietly(in);
                                    return str;
                                } catch (Throwable th) {
                                    th = th;
                                    H5IOUtils.getByteArrayPool().returnBuf(buffer);
                                    H5IOUtils.closeQuietly(bos);
                                    H5IOUtils.closeQuietly(in);
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                in = in2;
                                bos = bos2;
                                H5IOUtils.getByteArrayPool().returnBuf(buffer);
                                H5IOUtils.closeQuietly(bos);
                                H5IOUtils.closeQuietly(in);
                                throw th;
                            }
                        }
                        str = bos2.toString();
                        H5IOUtils.getByteArrayPool().returnBuf(buffer);
                        H5IOUtils.closeQuietly(bos2);
                        H5IOUtils.closeQuietly(in2);
                    } catch (Exception e2) {
                        e = e2;
                        bos = bos2;
                        H5Log.e(TAG, "exception detail", e);
                        H5IOUtils.getByteArrayPool().returnBuf(buffer);
                        H5IOUtils.closeQuietly(bos);
                        H5IOUtils.closeQuietly(in);
                        return str;
                    } catch (Throwable th3) {
                        th = th3;
                        bos = bos2;
                        H5IOUtils.getByteArrayPool().returnBuf(buffer);
                        H5IOUtils.closeQuietly(bos);
                        H5IOUtils.closeQuietly(in);
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    H5Log.e(TAG, "exception detail", e);
                    H5IOUtils.getByteArrayPool().returnBuf(buffer);
                    H5IOUtils.closeQuietly(bos);
                    H5IOUtils.closeQuietly(in);
                    return str;
                }
            }
        }
        return str;
    }

    public static final String read(String absPath) {
        String text = null;
        InputStream ips = null;
        try {
            InputStream ips2 = new FileInputStream(absPath);
            try {
                text = read(ips2);
                H5IOUtils.closeQuietly(ips2);
                FileInputStream fileInputStream = ips2;
            } catch (Exception e) {
                e = e;
                ips = ips2;
                try {
                    H5Log.e(TAG, "Exception", e);
                    H5IOUtils.closeQuietly(ips);
                    return text;
                } catch (Throwable th) {
                    th = th;
                    H5IOUtils.closeQuietly(ips);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                ips = ips2;
                H5IOUtils.closeQuietly(ips);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            H5Log.e(TAG, "Exception", e);
            H5IOUtils.closeQuietly(ips);
            return text;
        }
        return text;
    }

    public static final String read(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[2048];
        try {
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            while (true) {
                int size = reader.read(buffer, 0, 2048);
                if (size < 0) {
                    break;
                }
                builder.append(buffer, 0, size);
            }
        } catch (Exception e) {
            H5Log.e(TAG, "Exception", e);
        }
        return builder.toString();
    }

    public static String getLastModified(String absPath) {
        if (!exists(absPath)) {
            return null;
        }
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(new File(absPath).lastModified()));
    }

    public static long getCreateTime(String absPath) {
        if (!exists(absPath)) {
            return 0;
        }
        return new Date(new File(absPath).lastModified()).getTime();
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyFile(java.io.File r4, java.io.File r5) {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0012 }
            r0.<init>(r4)     // Catch:{ IOException -> 0x0012 }
            boolean r1 = copyToFile(r0, r5)     // Catch:{ all -> 0x000d }
            r0.close()     // Catch:{ IOException -> 0x0012 }
        L_0x000c:
            return r1
        L_0x000d:
            r3 = move-exception
            r0.close()     // Catch:{ IOException -> 0x0012 }
            throw r3     // Catch:{ IOException -> 0x0012 }
        L_0x0012:
            r2 = move-exception
            java.lang.String r3 = "H5FileUtil"
            com.alipay.mobile.nebula.util.H5Log.e(r3, r2)
            r1 = 0
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.util.H5FileUtil.copyFile(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x004d A[SYNTHETIC, Splitter:B:29:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0055 A[SYNTHETIC, Splitter:B:34:0x0055] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyToFile(java.io.FileInputStream r10, java.io.File r11) {
        /*
            boolean r2 = r11.exists()
            if (r2 == 0) goto L_0x002c
            r11.delete()
        L_0x0009:
            r7 = 0
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0062 }
            r8.<init>(r11)     // Catch:{ Exception -> 0x0062 }
            java.nio.channels.FileChannel r6 = r8.getChannel()     // Catch:{ Exception -> 0x0042, all -> 0x005f }
            java.nio.channels.FileChannel r1 = r10.getChannel()     // Catch:{ Exception -> 0x0042, all -> 0x005f }
            r2 = 0
            long r4 = r1.size()     // Catch:{ all -> 0x003a }
            r1.transferTo(r2, r4, r6)     // Catch:{ all -> 0x003a }
            r6.close()     // Catch:{ Exception -> 0x0042, all -> 0x005f }
            r1.close()     // Catch:{ Exception -> 0x0042, all -> 0x005f }
            r8.close()     // Catch:{ Exception -> 0x0059 }
        L_0x0029:
            r2 = 1
            r7 = r8
        L_0x002b:
            return r2
        L_0x002c:
            java.io.File r0 = r11.getParentFile()
            boolean r2 = r0.exists()
            if (r2 != 0) goto L_0x0009
            r0.mkdirs()
            goto L_0x0009
        L_0x003a:
            r2 = move-exception
            r6.close()     // Catch:{ Exception -> 0x0042, all -> 0x005f }
            r1.close()     // Catch:{ Exception -> 0x0042, all -> 0x005f }
            throw r2     // Catch:{ Exception -> 0x0042, all -> 0x005f }
        L_0x0042:
            r9 = move-exception
            r7 = r8
        L_0x0044:
            java.lang.String r2 = "FileUtils"
            java.lang.String r3 = "copy file error!"
            com.alipay.mobile.nebula.util.H5Log.e(r2, r3, r9)     // Catch:{ all -> 0x0052 }
            if (r7 == 0) goto L_0x0050
            r7.close()     // Catch:{ Exception -> 0x005b }
        L_0x0050:
            r2 = 0
            goto L_0x002b
        L_0x0052:
            r2 = move-exception
        L_0x0053:
            if (r7 == 0) goto L_0x0058
            r7.close()     // Catch:{ Exception -> 0x005d }
        L_0x0058:
            throw r2
        L_0x0059:
            r2 = move-exception
            goto L_0x0029
        L_0x005b:
            r2 = move-exception
            goto L_0x0050
        L_0x005d:
            r3 = move-exception
            goto L_0x0058
        L_0x005f:
            r2 = move-exception
            r7 = r8
            goto L_0x0053
        L_0x0062:
            r9 = move-exception
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.util.H5FileUtil.copyToFile(java.io.FileInputStream, java.io.File):boolean");
    }

    public static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            FileOutputStream fos = null;
            byte[] buffer = H5IOUtils.getBuf(4096);
            try {
                FileOutputStream fos2 = new FileOutputStream(destFile);
                while (true) {
                    try {
                        int bytesRead = inputStream.read(buffer);
                        if (bytesRead >= 0) {
                            fos2.write(buffer, 0, bytesRead);
                        } else {
                            H5IOUtils.returnBuf(buffer);
                            H5IOUtils.closeQuietly(fos2);
                            return true;
                        }
                    } catch (Throwable th) {
                        th = th;
                        fos = fos2;
                        H5IOUtils.returnBuf(buffer);
                        H5IOUtils.closeQuietly(fos);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                H5IOUtils.returnBuf(buffer);
                H5IOUtils.closeQuietly(fos);
                throw th;
            }
        } catch (IOException var9) {
            H5Log.e((String) TAG, (Throwable) var9);
            return false;
        }
    }

    public static String getSDPath() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                return Environment.getExternalStorageDirectory().getPath();
            }
            return null;
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return null;
        }
    }
}
