package com.autonavi.bundle.uitemplate.util;

import android.support.annotation.Nullable;
import com.amap.bundle.blutils.FileUtil;
import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class LottieDownloadUtil {

    @Target({ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LottieProperty {
        public static final String LOTTIE_IMAGE_PATH_NAME = "/images";
        public static final String LOTTIE_SD_PARENT_FOLDER = "/lottie/ajx";
        public static final String LOTTIE_ZIP_NAME = "/source.zip";
    }

    public interface a {
        void fail();

        void success(String str, String str2);
    }

    @Nullable
    static File a(String str) {
        File[] listFiles;
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }
        for (File file2 : file.listFiles()) {
            if (file2.isDirectory()) {
                File a2 = a(file2.getAbsolutePath());
                if (a2 != null) {
                    return a2;
                }
            } else if (file2.getAbsolutePath().toLowerCase().endsWith(".json") && !file2.getName().toLowerCase().startsWith(".")) {
                return file2;
            }
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r3v2, types: [byte[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(java.lang.String r8) {
        /*
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 0
            r2 = 0
        L_0x0007:
            int r3 = r8.length()
            if (r2 >= r3) goto L_0x0052
            char r3 = r8.charAt(r2)
            if (r3 < 0) goto L_0x001b
            r4 = 255(0xff, float:3.57E-43)
            if (r3 > r4) goto L_0x001b
            r0.append(r3)
            goto L_0x004f
        L_0x001b:
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x0027 }
            java.lang.String r4 = "utf-8"
            byte[] r3 = r3.getBytes(r4)     // Catch:{ Exception -> 0x0027 }
            goto L_0x0029
        L_0x0027:
            byte[] r3 = new byte[r1]
        L_0x0029:
            r4 = 0
        L_0x002a:
            int r5 = r3.length
            if (r4 >= r5) goto L_0x004f
            byte r5 = r3[r4]
            if (r5 >= 0) goto L_0x0033
            int r5 = r5 + 256
        L_0x0033:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "%"
            r6.<init>(r7)
            java.lang.String r5 = java.lang.Integer.toHexString(r5)
            java.lang.String r5 = r5.toUpperCase()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            r0.append(r5)
            int r4 = r4 + 1
            goto L_0x002a
        L_0x004f:
            int r2 = r2 + 1
            goto L_0x0007
        L_0x0052:
            java.lang.String r8 = r0.toString()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.util.LottieDownloadUtil.b(java.lang.String):java.lang.String");
    }

    public static String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getFilesDir());
        sb.append(LottieProperty.LOTTIE_SD_PARENT_FOLDER);
        return sb.toString();
    }
}
