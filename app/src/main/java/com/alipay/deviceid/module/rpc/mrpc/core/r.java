package com.alipay.deviceid.module.rpc.mrpc.core;

import java.io.Closeable;
import java.io.IOException;

public final class r {
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
