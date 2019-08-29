package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: ahe reason: default package */
/* compiled from: IOUtil */
public final class ahe {
    public static void a(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }

    public static byte[] a(@NonNull InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                a(inputStream, byteArrayOutputStream2);
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                a((Closeable) byteArrayOutputStream2);
                return byteArray;
            } catch (Throwable th) {
                th = th;
                byteArrayOutputStream = byteArrayOutputStream2;
                a((Closeable) byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            a((Closeable) byteArrayOutputStream);
            throw th;
        }
    }

    public static int a(@NonNull InputStream inputStream, @NonNull OutputStream outputStream) throws IOException {
        long b = b(inputStream, outputStream);
        if (b > 2147483647L) {
            return -1;
        }
        return (int) b;
    }

    private static long b(@NonNull InputStream inputStream, @NonNull OutputStream outputStream) throws IOException {
        return a(inputStream, outputStream, new byte[4096]);
    }

    private static long a(@NonNull InputStream inputStream, @NonNull OutputStream outputStream, @NonNull byte[] bArr) throws IOException {
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    public static String b(InputStream inputStream) throws IOException {
        return new String(a(inputStream));
    }
}
