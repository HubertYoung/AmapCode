package com.alipay.mobile.common.nbnet.biz.token;

import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.transport.concurrent.TaskExecutorManager;
import com.alipay.mobile.common.transport.concurrent.ZFutureTask;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

public class TokenManager {
    public static int a = 7200000;
    public static int b = 60000;
    private static TokenModel c;
    private static TokenManager e;
    private Lock d = new ReentrantLock(true);

    class RequestTokenCallale implements Callable<String> {
        RequestTokenCallale() {
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public String call() {
            NBNetLogCat.a((String) "TokenManager", (String) "RequestTokenCallale#call");
            return TokenManager.this.b();
        }
    }

    public class TokenModel {
        public long a;
        public String b;
        public long c;
        public long d;

        public TokenModel() {
        }

        public final boolean a() {
            return System.currentTimeMillis() <= this.d;
        }

        public final String b() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", this.b);
            jsonObject.put("token_up_time", this.a);
            jsonObject.put("token_expired_time", this.c);
            jsonObject.put("token_min_interval", this.d);
            return jsonObject.toString();
        }
    }

    public static final TokenManager a() {
        if (e != null) {
            return e;
        }
        synchronized (TokenManager.class) {
            if (e != null) {
                TokenManager tokenManager = e;
                return tokenManager;
            }
            e = new TokenManager();
            return e;
        }
    }

    private TokenManager() {
    }

    public final String b() {
        int i;
        if (c == null || !c.a()) {
            NBNetLogCat.a((String) "TokenManager", (String) "Waiting execute request token.");
            this.d.lock();
            Throwable lastThrowable = null;
            try {
                if (c == null || !c.a()) {
                    i = 0;
                    while (i < 4) {
                        NBNetLogCat.a((String) "TokenManager", (String) "Execute request token.");
                        String f = f();
                        this.d.unlock();
                        return f;
                    }
                    this.d.unlock();
                    if (lastThrowable != null) {
                    }
                    return "";
                }
                NBNetLogCat.a((String) "TokenManager", (String) "2. In request min interval");
                String str = c.b;
                this.d.unlock();
                return str;
            } catch (Throwable th) {
                this.d.unlock();
                throw th;
            }
        } else {
            NBNetLogCat.a((String) "TokenManager", (String) "1. In request min interval");
            return c.b;
        }
    }

    private String f() {
        TokenTransport tokenTransport = new TokenTransport();
        tokenTransport.a();
        Pair pair = tokenTransport.b();
        Map header = (Map) pair.first;
        String responseCode = (String) header.get("responseCode");
        if (!TextUtils.equals(responseCode, "200")) {
            throw new NBNetException("requestToken server error. responseCode=" + responseCode + ", responseMessage=" + ((String) header.get("responseMessage")), -3);
        } else if (TextUtils.isEmpty((CharSequence) pair.second)) {
            throw new NBNetException((String) "requestToken server error. response body may be null", -3);
        } else {
            TokenModel tokenModel = b((String) pair.second);
            a(tokenModel.b());
            c = tokenModel;
            return tokenModel.b;
        }
    }

    private static void a(String json) {
        SharedPreUtils.putData(NBNetEnvUtils.a(), (String) "token_key", json);
    }

    private TokenModel a(String token, long expiredTime) {
        TokenModel tokenModel = new TokenModel();
        tokenModel.a = System.currentTimeMillis();
        tokenModel.c = expiredTime;
        tokenModel.b = token;
        tokenModel.d = tokenModel.a + ((long) b);
        return tokenModel;
    }

    public final FutureTask<String> c() {
        return TaskExecutorManager.getInstance(NBNetEnvUtils.a()).execute(new ZFutureTask(new RequestTokenCallale(), 5));
    }

    private TokenModel b(String body) {
        try {
            NBNetLogCat.a((String) "TokenManager", "parseResponseToken. response body: " + body.toString());
            JSONObject rootJsonObject = new JSONObject(body);
            JSONObject resJsonObject = rootJsonObject.optJSONObject("ret");
            int code = Integer.parseInt(resJsonObject.optString("code"));
            if (code != 0) {
                throw new NBNetException(resJsonObject.optString("msg"), code);
            }
            JSONObject bizTypeJsonObject = rootJsonObject.optJSONObject("data").optJSONObject(NBNetEnvUtils.h());
            String token = bizTypeJsonObject.optString("token");
            if (TextUtils.isEmpty(token)) {
                throw new NBNetException("Token may be null, illegal response body： " + body, -3);
            }
            long expires = bizTypeJsonObject.optLong("expires");
            if (expires <= 0) {
                expires = System.currentTimeMillis() + 828000000;
            }
            return a(token, expires);
        } catch (NBNetException e2) {
            throw e2;
        } catch (Throwable e3) {
            NBNetException nbNetException = new NBNetException("Illegal response body： " + body, -3);
            nbNetException.initCause(e3);
            throw nbNetException;
        }
    }

    public static void d() {
        String logInfo = "remove token: ";
        if (c != null) {
            try {
                logInfo = logInfo + c.b();
            } catch (Throwable e2) {
                NBNetLogCat.d("TokenManager", "mTokenModel.toJsonString exception: " + e2.toString());
            }
        } else {
            logInfo = logInfo + " empty.";
        }
        NBNetLogCat.a((String) "TokenManager", logInfo);
        SharedPreUtils.putData(NBNetEnvUtils.a(), (String) "token_key", (String) "");
        c = null;
    }

    public static void e() {
        if (c == null || c.a()) {
            NBNetLogCat.a((String) "TokenManager", (String) "softRemoveToken missed");
            return;
        }
        NBNetLogCat.a((String) "TokenManager", (String) "softRemoveToken hit");
        d();
    }
}
