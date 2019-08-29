package com.autonavi.minimap.offline.koala.intf;

import com.autonavi.minimap.offline.koala.exception.KoalaDownloadSizeException;

public interface IKoalaDownloader {

    public interface Callback {
        void onCancel(int i, String str);

        void onComplete(int i, String str);

        void onConnect(int i, String str);

        void onError(int i, String str, Throwable th);

        void onProgress(int i, String str, long j, long j2);
    }

    public static class Config {
        public int bufferSize;
        public int connectTimeoutMillis;
        public int maxAutoRetryTimes;
        public int readTimeoutMillis;
    }

    void cancel();

    long getTotalBytes() throws KoalaDownloadSizeException;

    boolean isCanceled();

    boolean isRunning();

    void run(Callback callback);

    void setConfig(Config config);

    void setDownloadData(int i, String str, String str2);
}
