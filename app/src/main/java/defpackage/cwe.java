package defpackage;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* renamed from: cwe reason: default package */
/* compiled from: FileUtils */
public final class cwe {
    public static byte[] a(File file) throws IOException {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
        byte[] bArr = new byte[1024];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read > 0) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                fileInputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static void b(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File b : listFiles) {
                    b(b);
                }
            }
        }
        file.delete();
    }

    public static String a(Context context, String str, boolean z) {
        if (str == null) {
            str = "";
        }
        File file = null;
        try {
            if (TextUtils.equals("mounted", Environment.getExternalStorageState())) {
                File externalCacheDir = z ? context.getExternalCacheDir() : context.getExternalFilesDir(null);
                if (externalCacheDir != null) {
                    file = new File(externalCacheDir, "Telescope/".concat(String.valueOf(str)));
                }
            }
        } catch (NullPointerException unused) {
        }
        if (file == null) {
            file = new File(z ? context.getCacheDir() : context.getFilesDir(), "Telescope/".concat(String.valueOf(str)));
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
