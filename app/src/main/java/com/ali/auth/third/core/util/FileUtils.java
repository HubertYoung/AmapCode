package com.ali.auth.third.core.util;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils {
    private static void a(File file) {
        File[] listFiles;
        for (File file2 : file.listFiles()) {
            if (file2.isDirectory()) {
                a(file2);
            } else if (file2.isFile()) {
                file2.delete();
            }
        }
    }

    public static void delete(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else {
                a(file);
            }
        }
    }

    public static String readFileData(Context context, String str) {
        String str2 = "";
        try {
            FileInputStream openFileInput = context.openFileInput(str);
            int available = openFileInput.available();
            if (available > 0) {
                byte[] bArr = new byte[available];
                openFileInput.read(bArr);
                String str3 = new String(bArr, "UTF-8");
                try {
                    openFileInput.close();
                    return str3;
                } catch (Exception e) {
                    e = e;
                    str2 = str3;
                    e.printStackTrace();
                    return str2;
                }
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return str2;
        }
        return str2;
    }

    public static void writeFileData(Context context, String str, String str2) {
        try {
            FileOutputStream openFileOutput = context.openFileOutput(str, 0);
            openFileOutput.write(str2.getBytes());
            openFileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
