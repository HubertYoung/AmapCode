package defpackage;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/* renamed from: agy reason: default package */
/* compiled from: MD5Util */
public final class agy {
    public static String a(File file, String str, boolean z) {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                String a = agx.a(a((InputStream) fileInputStream2), z);
                ahe.a((Closeable) fileInputStream2);
                return a;
            } catch (Exception unused) {
                fileInputStream = fileInputStream2;
                ahe.a((Closeable) fileInputStream);
                return str;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                ahe.a((Closeable) fileInputStream);
                throw th;
            }
        } catch (Exception unused2) {
            ahe.a((Closeable) fileInputStream);
            return str;
        } catch (Throwable th2) {
            th = th2;
            ahe.a((Closeable) fileInputStream);
            throw th;
        }
    }

    public static String a(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return agx.a(instance.digest(), true);
        } catch (Exception unused) {
            return null;
        }
    }

    private static byte[] a(InputStream inputStream) throws Exception {
        MessageDigest instance = MessageDigest.getInstance("MD5");
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return instance.digest();
            }
            instance.update(bArr, 0, read);
        }
    }

    public static String a(String str) {
        try {
            return a(str.getBytes("UTF-8"));
        } catch (Exception unused) {
            return null;
        }
    }
}
