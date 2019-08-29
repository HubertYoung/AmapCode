package com.alipay.mobile.security.bio.utils;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamUtils {
    public static void close(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                BioLog.e(e.toString());
            }
        }
    }
}
