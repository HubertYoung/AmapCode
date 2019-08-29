package com.alipay.multimedia.io;

import com.alipay.alipaylogger.Log;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class IOUtils {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable t) {
                Log.w("IOUtils", "closeQuietly error, " + t);
            }
        }
    }

    public static byte[] getBytes(File file) {
        FileInputStream fis = null;
        try {
            FileInputStream fis2 = new FileInputStream(file);
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream((int) file.length());
                byte[] buf = new byte[4096];
                while (true) {
                    int read = fis2.read(buf);
                    if (read != -1) {
                        baos.write(buf, 0, read);
                    } else {
                        closeQuietly(fis2);
                        return baos.toByteArray();
                    }
                }
            } catch (Throwable th) {
                th = th;
                fis = fis2;
                closeQuietly(fis);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            closeQuietly(fis);
            throw th;
        }
    }

    public static byte[] getBytes(InputStream in) {
        BufferedInputStream bis = null;
        try {
            BufferedInputStream bis2 = new BufferedInputStream(in);
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                while (true) {
                    int read = bis2.read(buf);
                    if (read != -1) {
                        baos.write(buf, 0, read);
                    } else {
                        closeQuietly(bis2);
                        return baos.toByteArray();
                    }
                }
            } catch (Throwable th) {
                th = th;
                bis = bis2;
                closeQuietly(bis);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            closeQuietly(bis);
            throw th;
        }
    }
}
