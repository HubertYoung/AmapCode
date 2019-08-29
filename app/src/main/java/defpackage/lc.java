package defpackage;

import com.amap.bundle.blutils.FileUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

@Deprecated
/* renamed from: lc reason: default package */
/* compiled from: DBLite */
public final class lc {
    public final File a;

    public lc(String str) {
        File filesDir = FileUtil.getFilesDir();
        if (!filesDir.exists()) {
            filesDir.mkdir();
        }
        this.a = new File(filesDir, str);
    }

    public final byte[] a() {
        FileInputStream fileInputStream;
        if (!this.a.exists()) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            fileInputStream = new FileInputStream(this.a);
            try {
                byte[] bArr = new byte[256];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read < 0) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                if (byteArray.length == 0) {
                    try {
                        fileInputStream.close();
                        byteArrayOutputStream.close();
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                    }
                    return null;
                }
                try {
                    fileInputStream.close();
                    byteArrayOutputStream.close();
                } catch (Exception e2) {
                    kf.a((Throwable) e2);
                }
                return byteArray;
            } catch (Exception e3) {
                e = e3;
                try {
                    kf.a((Throwable) e);
                    try {
                        fileInputStream.close();
                        byteArrayOutputStream.close();
                    } catch (Exception e4) {
                        kf.a((Throwable) e4);
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    try {
                        fileInputStream.close();
                        byteArrayOutputStream.close();
                    } catch (Exception e5) {
                        kf.a((Throwable) e5);
                    }
                    throw th;
                }
            }
        } catch (Exception e6) {
            e = e6;
            fileInputStream = null;
            kf.a((Throwable) e);
            fileInputStream.close();
            byteArrayOutputStream.close();
            return null;
        } catch (Throwable th2) {
            Throwable th3 = th2;
            fileInputStream = null;
            th = th3;
            fileInputStream.close();
            byteArrayOutputStream.close();
            throw th;
        }
    }
}
