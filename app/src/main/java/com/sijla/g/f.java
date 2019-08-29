package com.sijla.g;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class f {
    public static String a(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".gz");
        return a(str, sb.toString(), z);
    }

    public static String a(String str, String str2, boolean z) {
        GZIPOutputStream gZIPOutputStream;
        FileInputStream fileInputStream;
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                gZIPOutputStream = new GZIPOutputStream(new FileOutputStream(str2));
            } catch (Exception e) {
                e = e;
                gZIPOutputStream = null;
                try {
                    e.printStackTrace();
                    b.a(fileInputStream);
                    b.a(gZIPOutputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    b.a(fileInputStream);
                    b.a(gZIPOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                gZIPOutputStream = null;
                b.a(fileInputStream);
                b.a(gZIPOutputStream);
                throw th;
            }
            try {
                byte[] bArr = new byte[10240];
                while (fileInputStream.available() > 10240 && fileInputStream.read(bArr) > 0) {
                    gZIPOutputStream.write(bArr);
                }
                int available = fileInputStream.available();
                fileInputStream.read(bArr, 0, available);
                gZIPOutputStream.write(bArr, 0, available);
                gZIPOutputStream.flush();
                if (z && file.exists()) {
                    file.delete();
                }
                b.a(fileInputStream);
                b.a(gZIPOutputStream);
                return str2;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                b.a(fileInputStream);
                b.a(gZIPOutputStream);
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
            gZIPOutputStream = null;
            e.printStackTrace();
            b.a(fileInputStream);
            b.a(gZIPOutputStream);
            return null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            gZIPOutputStream = null;
            b.a(fileInputStream);
            b.a(gZIPOutputStream);
            throw th;
        }
    }

    public static boolean a(File file, boolean z) {
        Object obj;
        Object obj2;
        Exception e;
        Object obj3;
        Object obj4;
        Object obj5 = 0;
        boolean z2 = false;
        try {
            InputStream fileInputStream = new FileInputStream(file);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file.getPath().replace(".gz", ""));
                try {
                    a(fileInputStream, (OutputStream) fileOutputStream);
                    fileOutputStream.flush();
                    b.a(fileInputStream, fileOutputStream);
                    z2 = true;
                } catch (Exception e2) {
                    Object obj6 = fileOutputStream;
                    e = e2;
                    obj4 = fileInputStream;
                    obj3 = obj6;
                    try {
                        e.printStackTrace();
                        b.a(obj4, obj3);
                        file.delete();
                        return z2;
                    } catch (Throwable th) {
                        th = th;
                        Object obj7 = obj3;
                        obj = obj4;
                        obj2 = obj7;
                        b.a(obj, obj2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    obj2 = fileOutputStream;
                    obj = fileInputStream;
                    b.a(obj, obj2);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Object obj8 = fileInputStream;
                obj3 = 0;
                obj4 = obj8;
                e.printStackTrace();
                b.a(obj4, obj3);
                file.delete();
                return z2;
            } catch (Throwable th3) {
                th = th3;
                obj2 = obj5;
                obj = fileInputStream;
                b.a(obj, obj2);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            obj3 = 0;
            obj4 = obj5;
            e.printStackTrace();
            b.a(obj4, obj3);
            file.delete();
            return z2;
        } catch (Throwable th4) {
            th = th4;
            obj = 0;
            obj2 = obj5;
            b.a(obj, obj2);
            throw th;
        }
        if (!z2 || z) {
            file.delete();
        }
        return z2;
    }

    public static boolean a(File file) {
        return a(file, true);
    }

    public static void a(InputStream inputStream, OutputStream outputStream) {
        GZIPInputStream gZIPInputStream = new GZIPInputStream(inputStream);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = gZIPInputStream.read(bArr, 0, 1024);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                gZIPInputStream.close();
                return;
            }
        }
    }
}
