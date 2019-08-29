package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;

/* renamed from: aha reason: default package */
/* compiled from: BitmapUtil */
public final class aha {
    public static boolean a(Bitmap bitmap, String str, CompressFormat compressFormat) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            try {
                bitmap.compress(compressFormat, 100, fileOutputStream);
                fileOutputStream.flush();
                ahe.a((Closeable) fileOutputStream);
                return true;
            } catch (Exception unused) {
                ahe.a((Closeable) fileOutputStream);
                return false;
            } catch (Throwable th) {
                th = th;
                fileOutputStream2 = fileOutputStream;
                ahe.a((Closeable) fileOutputStream2);
                throw th;
            }
        } catch (Exception unused2) {
            fileOutputStream = null;
            ahe.a((Closeable) fileOutputStream);
            return false;
        } catch (Throwable th2) {
            th = th2;
            ahe.a((Closeable) fileOutputStream2);
            throw th;
        }
    }
}
