package com.xiaomi.channel.commonutils.file;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class a {
    public static final String[] a = {"jpg", "png", "bmp", "gif", "webp"};

    public static void a(File file, File file2) {
        ZipOutputStream zipOutputStream;
        ZipOutputStream zipOutputStream2 = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(file, false));
            try {
                a(zipOutputStream, file2, null, null);
                a((OutputStream) zipOutputStream);
            } catch (FileNotFoundException unused) {
                a((OutputStream) zipOutputStream);
            } catch (IOException e) {
                e = e;
                zipOutputStream2 = zipOutputStream;
                try {
                    StringBuilder sb = new StringBuilder("zip file failure + ");
                    sb.append(e.getMessage());
                    b.a(sb.toString());
                    a((OutputStream) zipOutputStream2);
                } catch (Throwable th) {
                    th = th;
                    a((OutputStream) zipOutputStream2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                zipOutputStream2 = zipOutputStream;
                a((OutputStream) zipOutputStream2);
                throw th;
            }
        } catch (FileNotFoundException unused2) {
            zipOutputStream = null;
            a((OutputStream) zipOutputStream);
        } catch (IOException e2) {
            e = e2;
            StringBuilder sb2 = new StringBuilder("zip file failure + ");
            sb2.append(e.getMessage());
            b.a(sb2.toString());
            a((OutputStream) zipOutputStream2);
        }
    }

    public static void a(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:1|2|3|4|6) */
    /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0005 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.io.OutputStream r0) {
        /*
            if (r0 == 0) goto L_0x0008
            r0.flush()     // Catch:{ IOException -> 0x0005 }
        L_0x0005:
            r0.close()     // Catch:{ IOException -> 0x0008 }
        L_0x0008:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.file.a.a(java.io.OutputStream):void");
    }

    public static void a(RandomAccessFile randomAccessFile) {
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void a(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void a(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void a(ZipOutputStream zipOutputStream, File file, String str, FileFilter fileFilter) {
        FileInputStream fileInputStream;
        ZipEntry zipEntry;
        String str2;
        if (str == null) {
            str = "";
        }
        FileInputStream fileInputStream2 = null;
        try {
            if (file.isDirectory()) {
                File[] listFiles = fileFilter != null ? file.listFiles(fileFilter) : file.listFiles();
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(File.separator);
                zipOutputStream.putNextEntry(new ZipEntry(sb.toString()));
                if (TextUtils.isEmpty(str)) {
                    str2 = "";
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(File.separator);
                    str2 = sb2.toString();
                }
                for (int i = 0; i < listFiles.length; i++) {
                    File file2 = listFiles[i];
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str2);
                    sb3.append(listFiles[i].getName());
                    a(zipOutputStream, file2, sb3.toString(), null);
                }
                File[] listFiles2 = file.listFiles(new b());
                if (listFiles2 != null) {
                    for (File file3 : listFiles2) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str2);
                        sb4.append(File.separator);
                        sb4.append(file3.getName());
                        a(zipOutputStream, file3, sb4.toString(), fileFilter);
                    }
                }
                fileInputStream = null;
            } else {
                if (!TextUtils.isEmpty(str)) {
                    zipEntry = new ZipEntry(str);
                } else {
                    Date date = new Date();
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(String.valueOf(date.getTime()));
                    sb5.append(".txt");
                    zipEntry = new ZipEntry(sb5.toString());
                }
                zipOutputStream.putNextEntry(zipEntry);
                fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        zipOutputStream.write(bArr, 0, read);
                    }
                } catch (IOException e) {
                    e = e;
                    fileInputStream2 = fileInputStream;
                    try {
                        StringBuilder sb6 = new StringBuilder("zipFiction failed with exception:");
                        sb6.append(e.toString());
                        b.d(sb6.toString());
                        a((InputStream) fileInputStream2);
                    } catch (Throwable th) {
                        th = th;
                        a((InputStream) fileInputStream2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream2 = fileInputStream;
                    a((InputStream) fileInputStream2);
                    throw th;
                }
            }
            a((InputStream) fileInputStream);
        } catch (IOException e2) {
            e = e2;
            StringBuilder sb62 = new StringBuilder("zipFiction failed with exception:");
            sb62.append(e.toString());
            b.d(sb62.toString());
            a((InputStream) fileInputStream2);
        }
    }

    public static boolean a(File file) {
        try {
            if (file.isDirectory()) {
                return false;
            }
            if (file.exists()) {
                return true;
            }
            File parentFile = file.getParentFile();
            if (parentFile.exists() || parentFile.mkdirs()) {
                return file.createNewFile();
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static byte[] a(byte[] bArr) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.finish();
            gZIPOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Exception unused) {
            return bArr;
        }
    }
}
