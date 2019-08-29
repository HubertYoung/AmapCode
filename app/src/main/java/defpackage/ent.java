package defpackage;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/* renamed from: ent reason: default package */
/* compiled from: RsaUtil */
public final class ent {
    public static boolean a(File file, String str, String str2) {
        if (file == null || str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return false;
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(eno.a(str2)));
                Signature instance = Signature.getInstance("SHA1WithRSA");
                instance.initVerify(generatePublic);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream2.read(bArr);
                    if (read != -1) {
                        instance.update(bArr, 0, read);
                    } else {
                        boolean verify = instance.verify(eno.a(str));
                        enr.a(fileInputStream2);
                        return verify;
                    }
                }
            } catch (Exception e) {
                e = e;
                fileInputStream = fileInputStream2;
                try {
                    e.printStackTrace();
                    enr.a(fileInputStream);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream2 = fileInputStream;
                    enr.a(fileInputStream2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                enr.a(fileInputStream2);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            enr.a(fileInputStream);
            return false;
        }
    }
}
