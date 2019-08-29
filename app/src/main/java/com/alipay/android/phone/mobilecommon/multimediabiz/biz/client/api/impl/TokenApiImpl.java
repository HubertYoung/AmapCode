package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.TokenManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.TokenApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.TokenApi.OnGotServerTimeListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.TokenApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.EnvSwitcher;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.exception.DjangoClientException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.Token;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.TokenResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DjangoConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ParamChecker;
import com.alipay.mobile.mrtc.api.wwj.StreamerConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TokenApiImpl extends AbstractApiImpl implements TokenApi {
    public static final long TOKEN_EXPIRE_PERIOD_MILLS = 82800000;
    public static final long TOKEN_EXPIRE_PROTECT_INTERVAL = 1800000;
    private static final Logger a = Logger.getLogger((String) "TokenApiImpl");
    private Token b;
    private boolean c;
    private Timer d;
    private OnGotServerTimeListener e;

    private class TokenTask extends TimerTask {
        private TokenTask() {
        }

        /* synthetic */ TokenTask(TokenApiImpl x0, byte b) {
            this();
        }

        public void run() {
            TokenApiImpl.this.a();
        }
    }

    public TokenApiImpl(DjangoClient djangoClient, ConnectionManager<HttpClient> connectionManager, OnGotServerTimeListener onGotServerTimeListener) {
        super(djangoClient, new HttpConnectionManager());
        this.e = onGotServerTimeListener;
        ParamChecker.pmdCheck(connectionManager);
    }

    public synchronized TokenResp getToken(boolean refresh) {
        TokenResp resp;
        resp = new TokenResp();
        resp.setCode(DjangoConstant.DJANGO_OK);
        resp.setToken(this.b);
        if (refresh) {
            this.b = null;
        }
        try {
            if (this.b != null && this.b.getExpireTime() > 0 && this.b.getExpireTime() - this.djangoClient.getCorrectServerTime() < TOKEN_EXPIRE_PROTECT_INTERVAL) {
                this.b = null;
            }
        } catch (DjangoClientException e2) {
            this.b = null;
        }
        if (this.b == null) {
            synchronized (TokenApiImpl.class) {
                if (this.b == null) {
                    resp = a();
                    if (!this.c) {
                        if (this.d != null) {
                            this.d.cancel();
                        }
                        this.d = new Timer();
                        long tokenRefreshPeriodTime = ConfigManager.getInstance().djangoConf().tokenAutoRefreshInterval * 60000;
                        this.d.schedule(new TokenTask(this, 0), tokenRefreshPeriodTime, tokenRefreshPeriodTime);
                        this.c = true;
                    }
                }
            }
        }
        return resp;
    }

    public synchronized String getTokenString() {
        String token;
        if (ConfigManager.getInstance().djangoConf().isUseDjangoTokenPool() && this.b == null) {
            TokenManager.get().updateToken();
        }
        if (this.b == null || this.b.getExpireTime() <= 0 || this.b.getExpireTime() - this.djangoClient.getCorrectServerTime() < TOKEN_EXPIRE_PROTECT_INTERVAL) {
            if (ConfigManager.getInstance().djangoConf().isUseDjangoTokenPool()) {
                TokenManager.get().expiredCurrentToken();
            }
            TokenResp resp = getToken(true);
            if (resp.isSuccess()) {
                this.b = resp.getToken();
                token = this.b.getToken();
            } else {
                throw new DjangoClientException(String.format("code:%s,msg:%s,ti:%s", new Object[]{Integer.valueOf(resp.getCode()), resp.getMsg(), resp.getTraceId()}));
            }
        } else {
            token = this.b.getToken();
        }
        return token;
    }

    /* access modifiers changed from: private */
    public synchronized TokenResp a() {
        TokenResp tokenResp;
        HttpGet method = null;
        HttpResponse response = null;
        String traceId = "";
        try {
            long timestamp = System.currentTimeMillis();
            List params = new ArrayList();
            params.add(new BasicNameValuePair("timestamp", String.valueOf(timestamp)));
            params.add(new BasicNameValuePair("appKey", this.connectionManager.getAppKey()));
            String sign = EnvSwitcher.getSignature(timestamp);
            if (TextUtils.isEmpty(sign)) {
                throw new DjangoClientException((String) "get token error, sign is empty");
            }
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(StreamerConstants.DEFAULT_SIGNATURE, sign);
            params.add(basicNameValuePair);
            traceId = getTokenTraceId();
            BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId);
            params.add(basicNameValuePair2);
            method = createHttpGet(TokenApiInfo.GET_TOKEN, params, false);
            response = ((HttpClient) this.connectionManager.getConnection(false)).execute(method);
            if (response.getStatusLine().getStatusCode() == 200) {
                String resp = EntityUtils.toString(response.getEntity(), "UTF-8");
                tokenResp = (TokenResp) JSON.parseObject(resp, TokenResp.class);
                if (!tokenResp.isSuccess()) {
                    throw new DjangoClientException("get token error, http response:" + resp);
                }
                long fetchTime = System.currentTimeMillis();
                DjangoConf conf = ConfigManager.getInstance().djangoConf();
                if (!conf.isUseDjangoTokenPool()) {
                    fetchTime = tokenResp.getToken().getServerTime();
                }
                if (conf.isUseDjangoTokenPool()) {
                    TokenManager.get().saveToken(tokenResp.getToken(), fetchTime);
                }
                refreshToken(tokenResp.getToken(), fetchTime);
                tokenResp.setTraceId(traceId);
                DjangoUtils.releaseConnection(method, response);
            } else {
                String exp = "get token error, http code:" + response.getStatusLine().getStatusCode() + ";uri=" + method.getURI() + ";host=" + TokenApiInfo.GET_TOKEN.getHost();
                a.d(exp, new Object[0]);
                DjangoClientException djangoClientException = new DjangoClientException(exp);
                throw djangoClientException;
            }
        } catch (Throwable th) {
            DjangoUtils.releaseConnection(method, response);
            throw th;
        }
        return tokenResp;
    }

    public void refreshToken(Token token, long fetchTime) {
        if (token != null && this.e != null) {
            this.b = token;
            this.e.onGotServerTime(fetchTime);
        }
    }

    public Token getCurrentToken() {
        return this.b;
    }
}
