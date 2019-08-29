package com.autonavi.minimap.offline.utils;

import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class VoiceUtils {
    public static String generateDataPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf("/");
        if (lastIndexOf == -1) {
            return null;
        }
        return str.substring(lastIndexOf);
    }

    public static String readDataFromFile(File file) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        if (file != null && file.isFile() && file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (IOException e) {
                e = e;
                fileInputStream = null;
                try {
                    e.printStackTrace();
                    ahe.a((Closeable) fileInputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream2 = fileInputStream;
                    ahe.a((Closeable) fileInputStream2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                ahe.a((Closeable) fileInputStream2);
                throw th;
            }
            try {
                String b = ahe.b(fileInputStream);
                ahe.a((Closeable) fileInputStream);
                return b;
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                ahe.a((Closeable) fileInputStream);
                return null;
            }
        }
        return null;
    }
}
