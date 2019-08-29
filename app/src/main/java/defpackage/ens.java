package defpackage;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/* renamed from: ens reason: default package */
/* compiled from: MD5Util */
public final class ens {
    public static String a(File file) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        instance.update(bArr, 0, read);
                    } else {
                        String a = enq.a(instance.digest());
                        enr.a(fileInputStream);
                        return a;
                    }
                }
            } catch (Exception unused) {
                fileInputStream2 = fileInputStream;
                enr.a(fileInputStream2);
                return "";
            } catch (Throwable th) {
                th = th;
                enr.a(fileInputStream);
                throw th;
            }
        } catch (Exception unused2) {
            enr.a(fileInputStream2);
            return "";
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            enr.a(fileInputStream);
            throw th;
        }
    }
}
