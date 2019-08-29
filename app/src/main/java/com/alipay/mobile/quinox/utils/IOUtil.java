package com.alipay.mobile.quinox.utils;

import com.alipay.mobile.quinox.log.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public final class IOUtil {
    private static final String TAG = "IOUtil";

    private IOUtil() {
    }

    public static String readStringFromFile(String str) {
        return readStringFromFile(new File(str));
    }

    public static String readStringFromFile(File file) {
        FileInputStream fileInputStream;
        if (file == null || !file.exists()) {
            StringBuilder sb = new StringBuilder("readStringFromFile(File[");
            sb.append(file);
            sb.append("]): null == file || !file.exists()");
            Log.d((String) TAG, sb.toString());
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                return StreamUtil.streamToString(fileInputStream);
            } catch (Exception e) {
                e = e;
                StreamUtil.closeSafely(fileInputStream);
                StringBuilder sb2 = new StringBuilder("readStringFromFile(File[");
                sb2.append(file);
                sb2.append("]): Exception occur.");
                Log.w(TAG, sb2.toString(), e);
                return null;
            }
        } catch (Exception e2) {
            e = e2;
            fileInputStream = null;
            StreamUtil.closeSafely(fileInputStream);
            StringBuilder sb22 = new StringBuilder("readStringFromFile(File[");
            sb22.append(file);
            sb22.append("]): Exception occur.");
            Log.w(TAG, sb22.toString(), e);
            return null;
        }
    }

    public static byte[] readBytesFromFile(String str) {
        return readBytesFromFile(new File(str));
    }

    public static byte[] readBytesFromFile(File file) {
        FileInputStream fileInputStream;
        if (file == null || !file.exists()) {
            StringBuilder sb = new StringBuilder("readBytesFromFile(File[");
            sb.append(file);
            sb.append("]): null == file || !file.exists()");
            Log.d((String) TAG, sb.toString());
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                return StreamUtil.streamToBytes(fileInputStream);
            } catch (Exception e) {
                e = e;
                StreamUtil.closeSafely(fileInputStream);
                StringBuilder sb2 = new StringBuilder("readBytesFromFile(File[");
                sb2.append(file);
                sb2.append("]): Exception occur.");
                Log.w(TAG, sb2.toString(), e);
                return null;
            }
        } catch (Exception e2) {
            e = e2;
            fileInputStream = null;
            StreamUtil.closeSafely(fileInputStream);
            StringBuilder sb22 = new StringBuilder("readBytesFromFile(File[");
            sb22.append(file);
            sb22.append("]): Exception occur.");
            Log.w(TAG, sb22.toString(), e);
            return null;
        }
    }

    public static boolean writeDateToFile(byte[] bArr, String str) {
        return writeDateToFile(new String(bArr), new File(str), false);
    }

    public static boolean writeDateToFile(byte[] bArr, File file) {
        return writeDateToFile(new String(bArr), file, false);
    }

    public static boolean writeDateToFile(String str, String str2) {
        return writeDateToFile(str, new File(str2), false);
    }

    public static boolean writeDateToFile(String str, File file) {
        return writeDateToFile(str, file, false);
    }

    public static boolean writeDateToFile(byte[] bArr, String str, boolean z) {
        return bArr != null && bArr.length > 0 && writeDateToFile(new String(bArr), new File(str), z);
    }

    public static boolean writeDateToFile(byte[] bArr, File file, boolean z) {
        return bArr != null && bArr.length > 0 && writeDateToFile(new String(bArr), file, z);
    }

    public static boolean writeDateToFile(String str, String str2, boolean z) {
        return writeDateToFile(str, new File(str2), z);
    }

    public static boolean writeDateToFile(String str, File file, boolean z) {
        FileWriter fileWriter = null;
        try {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FileWriter fileWriter2 = new FileWriter(file, z);
            try {
                fileWriter2.write(str);
                StreamUtil.closeSafely(fileWriter2);
                return true;
            } catch (Exception e) {
                e = e;
                fileWriter = fileWriter2;
                try {
                    StringBuilder sb = new StringBuilder("writeDateToFile(String[");
                    sb.append(str);
                    sb.append("], String[");
                    sb.append(file);
                    sb.append("]) Exception occur.");
                    Log.w(TAG, sb.toString(), e);
                    StreamUtil.closeSafely(fileWriter);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    StreamUtil.closeSafely(fileWriter);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileWriter = fileWriter2;
                StreamUtil.closeSafely(fileWriter);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            StringBuilder sb2 = new StringBuilder("writeDateToFile(String[");
            sb2.append(str);
            sb2.append("], String[");
            sb2.append(file);
            sb2.append("]) Exception occur.");
            Log.w(TAG, sb2.toString(), e);
            StreamUtil.closeSafely(fileWriter);
            return false;
        }
    }
}
