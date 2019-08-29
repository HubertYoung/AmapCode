package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;

/* renamed from: edn reason: default package */
/* compiled from: AjxShareBitmapHelper */
public final class edn {
    public static Bitmap a(Bitmap bitmap, String str) {
        FileOutputStream fileOutputStream;
        if (TextUtils.isEmpty(str) || bitmap == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists() && !file.delete()) {
                ahe.a((Closeable) null);
                return null;
            } else if (!file.createNewFile()) {
                ahe.a((Closeable) null);
                return null;
            } else {
                fileOutputStream = new FileOutputStream(str);
                try {
                    String parent = file.getParent();
                    StringBuilder sb = new StringBuilder();
                    sb.append(parent);
                    sb.append("/.nomedia");
                    File file2 = new File(sb.toString());
                    if (file2.exists() || file2.createNewFile()) {
                        bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
                        bitmap.recycle();
                        ahe.a((Closeable) fileOutputStream);
                        return bitmap;
                    }
                    ahe.a((Closeable) fileOutputStream);
                    return null;
                } catch (Exception e) {
                    e = e;
                    try {
                        e.printStackTrace();
                        ahe.a((Closeable) fileOutputStream);
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        ahe.a((Closeable) fileOutputStream);
                        throw th;
                    }
                }
            }
        } catch (Exception e2) {
            e = e2;
            fileOutputStream = null;
            e.printStackTrace();
            ahe.a((Closeable) fileOutputStream);
            return null;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            ahe.a((Closeable) fileOutputStream);
            throw th;
        }
    }

    public static String a(String str) {
        String str2 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb = new StringBuilder("autonavi");
            sb.append(File.separator);
            sb.append("navishare");
            File file = new File(externalStorageDirectory, sb.toString());
            if (!file.exists() && !file.mkdir()) {
                return null;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/");
            sb2.append(str);
            str2 = sb2.toString();
        }
        return str2;
    }

    public static String a() {
        String str = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb = new StringBuilder("autonavi");
            sb.append(File.separator);
            sb.append("navishare");
            File file = new File(externalStorageDirectory, sb.toString());
            if (!file.exists() && !file.mkdir()) {
                return null;
            }
            str = file.getAbsolutePath();
        }
        return str;
    }
}
