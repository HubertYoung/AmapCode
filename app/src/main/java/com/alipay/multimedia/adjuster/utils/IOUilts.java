package com.alipay.multimedia.adjuster.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUilts {
    public static void close(InputStream input) {
        close((Closeable) input);
    }

    public static void close(OutputStream output) {
        close((Closeable) output);
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
