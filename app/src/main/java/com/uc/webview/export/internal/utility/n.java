package com.uc.webview.export.internal.utility;

import com.alipay.mobile.quinox.bundle.IBundleOperator;
import com.uc.webview.export.cyclone.UCCyclone;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* compiled from: ProGuard */
abstract class n {
    static void a(ByteBuffer byteBuffer) {
        if (byteBuffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    static long a(ByteBuffer byteBuffer, int i) {
        return ((long) byteBuffer.getInt(i)) & 4294967295L;
    }

    /* JADX INFO: finally extract failed */
    public static boolean a(String str) throws Exception {
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
        int i = 0;
        while (true) {
            try {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    int i2 = i + 1;
                    if (i > 1024) {
                        throw new IOException("Too many files in zip");
                    }
                    String name = nextEntry.getName();
                    if (name.contains("../")) {
                        throw new IOException("unsecurity zipfile!");
                    } else if (!name.contains(IBundleOperator.META_INF) || !name.endsWith(".SF")) {
                        i = i2;
                    } else {
                        byte[] bArr = new byte[16384];
                        StringBuilder sb = new StringBuilder();
                        int i3 = 0;
                        while (true) {
                            int read = zipInputStream.read(bArr, 0, 16384);
                            if (read != -1) {
                                i3 += read;
                                if (i3 > 4194304) {
                                    throw new IOException("Zip contents is too big.");
                                }
                                sb.append(new String(bArr, 0, read));
                            } else {
                                String sb2 = sb.toString();
                                int indexOf = sb2.indexOf("X-Android-APK-Signed");
                                if (indexOf >= 0) {
                                    int indexOf2 = (sb2.indexOf(Integer.toString(2), indexOf + 20) - indexOf) - 20;
                                    if (indexOf2 >= 0 && indexOf2 < 5) {
                                        UCCyclone.close(zipInputStream);
                                        return true;
                                    }
                                }
                                UCCyclone.close(zipInputStream);
                                return false;
                            }
                        }
                    }
                } else {
                    UCCyclone.close(zipInputStream);
                    return false;
                }
            } catch (Throwable th) {
                UCCyclone.close(zipInputStream);
                throw th;
            }
        }
    }
}
