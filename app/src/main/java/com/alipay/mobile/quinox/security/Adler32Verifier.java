package com.alipay.mobile.quinox.security;

import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.utils.StreamUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.Adler32;

public final class Adler32Verifier {
    private static final String TAG = "Adler32Verifier";

    private Adler32Verifier() {
    }

    public static long genInputStreamAdler32Sum(InputStream inputStream) {
        Adler32 adler32 = new Adler32();
        adler32.update(StreamUtil.streamToBytes(inputStream));
        return adler32.getValue();
    }

    public static long genFileAdler32Sum(File file) {
        if (file != null && file.exists()) {
            try {
                return genInputStreamAdler32Sum(new FileInputStream(file));
            } catch (Throwable th) {
                Log.w((String) TAG, th);
            }
        }
        return -1;
    }

    public static boolean checkAdler32(InputStream inputStream, InputStream inputStream2) {
        return genInputStreamAdler32Sum(inputStream) == genInputStreamAdler32Sum(inputStream2);
    }

    public static boolean checkAdler32(InputStream inputStream, File file) {
        return genInputStreamAdler32Sum(inputStream) == genFileAdler32Sum(file);
    }
}
