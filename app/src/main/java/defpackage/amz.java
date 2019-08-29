package defpackage;

import android.os.Environment;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* renamed from: amz reason: default package */
/* compiled from: GLFileUtil */
public final class amz {
    private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault());
    private static File b;
    private static File c;

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static byte[] a(String str) {
        FileInputStream fileInputStream;
        Throwable th;
        try {
            File file = new File(str);
            if (!file.exists()) {
                a((Closeable) null);
                return null;
            }
            fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[1024];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        byteArrayOutputStream.close();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        a((Closeable) fileInputStream);
                        return byteArray;
                    }
                }
            } catch (Exception unused) {
                a((Closeable) fileInputStream);
                return null;
            } catch (Throwable th2) {
                th = th2;
                a((Closeable) fileInputStream);
                throw th;
            }
        } catch (Exception unused2) {
            fileInputStream = null;
            a((Closeable) fileInputStream);
            return null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            a((Closeable) fileInputStream);
            throw th;
        }
    }

    public static File a() {
        File file;
        if (b == null) {
            String str = null;
            if (Environment.getExternalStorageState().equals("mounted")) {
                file = new File(Environment.getExternalStorageDirectory(), "ae8test");
                if (!file.exists()) {
                    file.mkdir();
                }
            } else {
                file = null;
            }
            if (file != null) {
                str = file.toString();
            }
            if (str == null) {
                str = Environment.getExternalStorageDirectory().toString();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("/saved_images");
            File file2 = new File(sb.toString());
            b = file2;
            if (!file2.exists()) {
                b.mkdirs();
            }
        }
        if (c == null) {
            StringBuilder sb2 = new StringBuilder("screenshot-");
            sb2.append(System.currentTimeMillis());
            sb2.append(".jpg");
            c = new File(b, sb2.toString());
        }
        return c;
    }
}
