package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl;

import android.content.Context;
import android.os.SystemClock;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.TokenManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.ChunkApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.FileApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.ImageApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.TokenApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.TokenApi.OnGotServerTimeListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.ChunkApiImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.EnhanceFileApiImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.ImageApiImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.Env;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.EnvSwitcher;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.LiteStringUtils;
import org.apache.http.client.HttpClient;

public class HttpDjangoClient extends DjangoClient implements OnGotServerTimeListener {
    private static TokenApi a;
    private FileApi b;
    private ChunkApi c;
    private ImageApi d;
    private Context e;
    private ConnectionManager<HttpClient> f;

    public static DjangoClient regeisterClient(Context context, String appKey, String acl, String uid) {
        return regeisterClient(context, appKey, acl, uid, EnvSwitcher.getCurrentEnv());
    }

    public static DjangoClient regeisterClient(Context context, String appKey, String acl, String uid, Env env) {
        if (context == null || LiteStringUtils.isBlank(appKey) || LiteStringUtils.isBlank(acl) || LiteStringUtils.isBlank(uid)) {
            throw new IllegalArgumentException("Parameter can not be null !");
        }
        ConnectionManager conMgr = new HttpConnectionManager();
        conMgr.setAppKey(appKey);
        conMgr.setAcl(acl);
        conMgr.setUid(uid);
        return new HttpDjangoClient(context, conMgr);
    }

    public HttpDjangoClient(Context context, ConnectionManager<HttpClient> conn) {
        this.e = context;
        this.f = conn;
    }

    public ConnectionManager<?> getConnectionManager() {
        return this.f;
    }

    public void release(BaseDownResp resp) {
        DjangoUtils.releaseDownloadResponse(resp);
    }

    public TokenApi getTokenApi() {
        if (a == null) {
            synchronized (HttpDjangoClient.class) {
                if (a == null) {
                    a = new TokenApiImpl(this, this.f, this);
                    TokenManager.get().registerTokenApi(a);
                }
            }
        }
        return a;
    }

    public synchronized FileApi getFileApi() {
        if (this.b == null) {
            this.b = new EnhanceFileApiImpl(this, this.f);
        }
        return this.b;
    }

    public synchronized ChunkApi getChunkApi() {
        if (this.c == null) {
            this.c = new ChunkApiImpl(this, this.f);
        }
        return this.c;
    }

    public synchronized ImageApi getImageApi() {
        if (this.d == null) {
            this.d = new ImageApiImpl(this, this.f);
        }
        return this.d;
    }

    public void onGotServerTime(long serverTime) {
        correctServerTimeAtPoint = serverTime;
        correctLocalElapsedRealtimeAtPoint = SystemClock.elapsedRealtime();
    }
}
