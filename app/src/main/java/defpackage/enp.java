package defpackage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* renamed from: enp reason: default package */
/* compiled from: FileUtil */
public final class enp {
    public static String a(File file) {
        ByteArrayOutputStream byteArrayOutputStream;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Exception e) {
                e = e;
                byteArrayOutputStream = null;
                try {
                    e.printStackTrace();
                    enr.a(byteArrayOutputStream);
                    enr.a(fileInputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    enr.a(byteArrayOutputStream);
                    enr.a(fileInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
                enr.a(byteArrayOutputStream);
                enr.a(fileInputStream);
                throw th;
            }
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        String byteArrayOutputStream2 = byteArrayOutputStream.toString("UTF-8");
                        enr.a(byteArrayOutputStream);
                        enr.a(fileInputStream);
                        return byteArrayOutputStream2;
                    }
                }
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                enr.a(byteArrayOutputStream);
                enr.a(fileInputStream);
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            byteArrayOutputStream = null;
            fileInputStream = null;
            e.printStackTrace();
            enr.a(byteArrayOutputStream);
            enr.a(fileInputStream);
            return null;
        } catch (Throwable th3) {
            fileInputStream = null;
            th = th3;
            byteArrayOutputStream = null;
            enr.a(byteArrayOutputStream);
            enr.a(fileInputStream);
            throw th;
        }
    }

    public static boolean a(File file, String str) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(str.getBytes("UTF-8"));
                fileOutputStream.flush();
                enr.a(fileOutputStream);
                return true;
            } catch (Exception unused) {
                fileOutputStream2 = fileOutputStream;
                enr.a(fileOutputStream2);
                return false;
            } catch (Throwable th) {
                th = th;
                enr.a(fileOutputStream);
                throw th;
            }
        } catch (Exception unused2) {
            enr.a(fileOutputStream2);
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            enr.a(fileOutputStream);
            throw th;
        }
    }

    public static boolean b(File file) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File b : listFiles) {
                    b(b);
                }
            }
        }
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
