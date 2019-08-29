package com.alipay.mobile.beehive.plugins.utils;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class Base64Helper {
    public static String encodeFile2String(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return "";
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[512];
        for (int readCount = fis.read(buffer); readCount > 0; readCount = fis.read(buffer)) {
            bos.write(buffer, 0, readCount);
        }
        return Base64.encodeToString(bos.toByteArray(), 2);
    }

    public static String decode(String base64String) {
        return "";
    }
}
