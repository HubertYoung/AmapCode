package com.alipay.mobile.security.bio.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamUtils {
    public static final byte[] input2byte(InputStream inputStream) {
        byte[] bArr;
        OutOfMemoryError e;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr2 = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr2, 0, 100);
                if (read <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr2, 0, read);
            } catch (OutOfMemoryError e2) {
                OutOfMemoryError outOfMemoryError = e2;
                bArr = null;
                e = outOfMemoryError;
            }
        }
        bArr = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (OutOfMemoryError e3) {
            e = e3;
        }
        return bArr;
        BioLog.i(e.toString());
        return bArr;
    }

    public static String input2String(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read();
            if (read == -1) {
                return byteArrayOutputStream.toString();
            }
            byteArrayOutputStream.write(read);
        }
    }

    public static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }
}
