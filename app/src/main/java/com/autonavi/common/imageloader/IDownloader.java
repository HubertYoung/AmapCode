package com.autonavi.common.imageloader;

import android.net.Uri;
import java.io.IOException;

public interface IDownloader {

    public static class ResponseException extends IOException {
        public final boolean a;
        public final int b;

        public ResponseException(String str, int i, int i2) {
            super(str);
            this.a = NetworkPolicy.isOfflineOnly(i);
            this.b = i2;
        }
    }

    a a(Uri uri) throws Exception;

    void a(boy boy);
}
