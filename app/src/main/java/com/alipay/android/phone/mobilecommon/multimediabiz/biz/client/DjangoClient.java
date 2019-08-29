package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client;

import android.os.SystemClock;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.ChunkApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.FileApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.ImageApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.TokenApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.exception.DjangoClientException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;

public abstract class DjangoClient {
    public static boolean DEBUG = true;
    public static final String LOG_TAG = "DjangoClient";
    protected static long correctLocalElapsedRealtimeAtPoint = 0;
    protected static long correctServerTimeAtPoint = 0;

    public abstract ChunkApi getChunkApi();

    public abstract ConnectionManager<?> getConnectionManager();

    public abstract FileApi getFileApi();

    public abstract ImageApi getImageApi();

    public abstract TokenApi getTokenApi();

    public abstract void release(BaseDownResp baseDownResp);

    public synchronized long getCorrectServerTime() {
        long elapsedRealtime;
        try {
            if (ConfigManager.getInstance().djangoConf().isUseDjangoTokenPool()) {
                elapsedRealtime = System.currentTimeMillis();
            } else if (correctServerTimeAtPoint == 0 || correctLocalElapsedRealtimeAtPoint == 0) {
                throw new DjangoClientException((String) "take it easy, this will not cause crash. Please set variable 'correctServerTimeAtPoint' and 'correctLocalElapsedRealtimeAtPoint' in TokenApi.getToken(boolean)");
            } else {
                elapsedRealtime = correctServerTimeAtPoint + (SystemClock.elapsedRealtime() - correctLocalElapsedRealtimeAtPoint);
            }
        }
        return elapsedRealtime;
    }
}
