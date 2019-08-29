package com.uc.webview.export.internal.utility;

import android.content.Context;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.internal.setup.UCSetupException;
import java.io.File;

/* compiled from: ProGuard */
public final class m {
    private static String a(String str) {
        File file = new File(str);
        return UCCyclone.getSourceHash(UCCyclone.getDecompressSourceHash(str, file.length(), file.lastModified(), false));
    }

    public static boolean a(String str, Context context) {
        File file = (File) UCMPackageInfo.invoke(10005, context);
        String a = a(str);
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append("_n");
        if (new File(file, sb.toString()).exists()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("_y");
            if (new File(file, sb2.toString()).exists()) {
                return false;
            }
            StringBuilder sb3 = new StringBuilder("快速校验 Quick Failed [");
            sb3.append(str);
            sb3.append("]");
            Log.d("VerifyUtils", sb3.toString());
            throw new UCSetupException(3005, String.format("[%s] verifyQuick failed", new Object[]{str}));
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(a);
        sb4.append("_y");
        if (!new File(file, sb4.toString()).exists()) {
            return false;
        }
        StringBuilder sb5 = new StringBuilder("快速校验 Quick Success [");
        sb5.append(str);
        sb5.append("]");
        Log.d("VerifyUtils", sb5.toString());
        return true;
    }

    public static void a(String str, Context context, boolean z) {
        File file = (File) UCMPackageInfo.invoke(10005, context);
        String a = a(str);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("_y");
            File file2 = new File(file, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("_n");
            File file3 = new File(file, sb2.toString());
            if (z) {
                file2.createNewFile();
                file3.delete();
                return;
            }
            file2.delete();
            file3.createNewFile();
        } catch (Exception unused) {
        }
    }
}
