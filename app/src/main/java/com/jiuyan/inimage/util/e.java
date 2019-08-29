package com.jiuyan.inimage.util;

import android.text.TextUtils;
import java.io.File;

/* compiled from: FileUtils */
public class e {
    public static boolean a(String str) {
        File file = new File(str);
        return file.exists() && file.length() > 0;
    }

    public static boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        int i = 3;
        while (i > 0) {
            try {
                if (file.delete()) {
                    return true;
                }
                i--;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
