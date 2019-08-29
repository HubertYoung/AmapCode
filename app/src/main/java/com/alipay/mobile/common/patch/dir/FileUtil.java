package com.alipay.mobile.common.patch.dir;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.util.ByteArrayBuffer;

public class FileUtil {
    public static final String TAG = "FileUtil";

    public FileUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

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
            return z;
        }
    }

    public static boolean delete(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return false;
        }
        return delete(new File(absPath));
    }

    public static boolean delete(File file) {
        if (!exists(file)) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        boolean result = true;
        for (File delete : file.listFiles()) {
            result |= delete(delete);
        }
        return file.delete() | result;
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
        if (cleanPath(childPath).startsWith(cleanPath(parentPath) + File.separator)) {
            return true;
        }
        return false;
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
            try {
                byte[] buffer = new byte[16384];
                while (true) {
                    int len = in.read(buffer);
                    if (len != -1) {
                        out.write(buffer, 0, len);
                    } else {
                        out.flush();
                        try {
                            return true;
                        } catch (Exception e) {
                            return true;
                        }
                    }
                }
            } catch (Exception e2) {
                return false;
            } finally {
                try {
                    in.close();
                    out.close();
                } catch (Exception e3) {
                }
            }
        } catch (Exception e4) {
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

    public static String getMimeType(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "*/*";
        }
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getExtension(fileName));
        if (TextUtils.isEmpty(type)) {
            return "*/*";
        }
        return type;
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

    public static String getText(String absPath) {
        if (!exists(absPath)) {
            return null;
        }
        File file = new File(absPath);
        int length = (int) file.length();
        if (length > 20480) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(length);
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            while (true) {
                int len = in.read(buffer, 0, 1024);
                if (-1 != len) {
                    bos.write(buffer, 0, len);
                } else {
                    in.close();
                    bos.close();
                    return bos.toString();
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x001f A[SYNTHETIC, Splitter:B:18:0x001f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String read(java.lang.String r5) {
        /*
            r2 = 0
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0013, all -> 0x001c }
            r1.<init>(r5)     // Catch:{ Exception -> 0x0013, all -> 0x001c }
            java.lang.String r2 = read(r1)     // Catch:{ Exception -> 0x0028, all -> 0x0025 }
            r1.close()     // Catch:{ Exception -> 0x0010 }
            r0 = r1
        L_0x000f:
            return r2
        L_0x0010:
            r3 = move-exception
            r0 = r1
            goto L_0x000f
        L_0x0013:
            r3 = move-exception
        L_0x0014:
            if (r0 == 0) goto L_0x000f
            r0.close()     // Catch:{ Exception -> 0x001a }
            goto L_0x000f
        L_0x001a:
            r3 = move-exception
            goto L_0x000f
        L_0x001c:
            r3 = move-exception
        L_0x001d:
            if (r0 == 0) goto L_0x0022
            r0.close()     // Catch:{ Exception -> 0x0023 }
        L_0x0022:
            throw r3
        L_0x0023:
            r4 = move-exception
            goto L_0x0022
        L_0x0025:
            r3 = move-exception
            r0 = r1
            goto L_0x001d
        L_0x0028:
            r3 = move-exception
            r0 = r1
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.patch.dir.FileUtil.read(java.lang.String):java.lang.String");
    }

    public static final String read(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        ByteArrayBuffer baf = new ByteArrayBuffer(16384);
        byte[] buffer = new byte[16384];
        try {
            int count = inputStream.read(buffer);
            while (count != -1) {
                baf.append(buffer, 0, count);
                count = inputStream.read(buffer);
            }
        } catch (Exception e) {
        }
        return new String(baf.toByteArray());
    }

    public static String getLastModified(String absPath) {
        if (!exists(absPath)) {
            return null;
        }
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(new File(absPath).lastModified()));
    }
}
