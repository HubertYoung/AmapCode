package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.TokenApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.Token;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DjangoConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

public class TokenManager {
    private static final Logger a = Logger.getLogger((String) "TokenManager");
    private static TokenManager b = new TokenManager();
    private WeakReference<TokenApi> c;
    private SharedPreferences d = AppUtils.getApplicationContext().getSharedPreferences("pref_token_pool", 0);
    private long e;
    /* access modifiers changed from: private */
    public AtomicBoolean f = new AtomicBoolean(false);

    class TokenWrapper {
        long a;
        Token b;

        public TokenWrapper(long fetchTime, Token token) {
            this.a = fetchTime;
            this.b = token;
        }

        public String toString() {
            return "TokenWrapper{fetchTime=" + this.a + ", token=" + this.b + '}';
        }
    }

    private TokenManager() {
    }

    public static TokenManager get() {
        return b;
    }

    public void registerTokenApi(TokenApi tokenApi) {
        this.c = new WeakReference<>(tokenApi);
    }

    public synchronized void updateToken() {
        if (this.c != null) {
            final TokenApi api = (TokenApi) this.c.get();
            if (api != null) {
                DjangoConf conf = ConfigManager.getInstance().djangoConf();
                if (!this.f.get() && System.currentTimeMillis() - this.e > conf.tokenForceRefreshInterval * 60000) {
                    this.f.set(true);
                    a.d("update call api.getToken(true)", new Object[0]);
                    TaskScheduleManager.get().execute(new Runnable() {
                        public void run() {
                            api.getToken(true);
                            TokenManager.this.f.set(false);
                        }
                    });
                }
                TokenWrapper tokenWrapper = a();
                a.d("updateToken getLocalToken: " + tokenWrapper, new Object[0]);
                if (tokenWrapper != null) {
                    api.refreshToken(tokenWrapper.b, tokenWrapper.a);
                }
            }
        }
    }

    public synchronized void saveToken(Token token, long fetchTime) {
        this.e = System.currentTimeMillis();
        List allTokens = a(new TokenWrapper(fetchTime, token));
        while (allTokens.size() > ConfigManager.getInstance().djangoConf().tokenPoolSize) {
            allTokens.remove(allTokens.size() - 1);
        }
        a(allTokens);
    }

    private void a(List<TokenWrapper> allTokens) {
        this.d.edit().clear().apply();
        Editor editor = this.d.edit();
        for (TokenWrapper wrapper : allTokens) {
            editor.putString(String.valueOf(wrapper.a), JSON.toJSONString(wrapper.b));
        }
        editor.apply();
        a.p("saveToken save: " + allTokens.size() + ", " + allTokens, new Object[0]);
    }

    public synchronized void expiredCurrentToken() {
        if (this.c != null) {
            TokenApi api = (TokenApi) this.c.get();
            if (api != null) {
                List all = a(new TokenWrapper[0]);
                Token currentToken = api.getCurrentToken();
                if (currentToken != null && all.size() > 1) {
                    Iterator<TokenWrapper> it = all.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        TokenWrapper wrapper = it.next();
                        if (wrapper.b.getToken().equals(currentToken.getToken()) && all.size() > 1) {
                            a.d("expiredCurrentToken token: " + wrapper, new Object[0]);
                            all.remove(wrapper);
                            break;
                        }
                    }
                    a(all);
                }
            }
        }
    }

    private synchronized TokenWrapper a() {
        List tokenWrappers;
        tokenWrappers = a(new TokenWrapper[0]);
        return tokenWrappers.isEmpty() ? null : tokenWrappers.get(0);
    }

    private List<TokenWrapper> a(TokenWrapper... toAdd) {
        List tokenWrappers = new ArrayList();
        for (Entry entry : this.d.getAll().entrySet()) {
            tokenWrappers.add(new TokenWrapper(Long.valueOf(Long.parseLong((String) entry.getKey())).longValue(), (Token) JSON.parseObject(entry.getValue().toString(), Token.class)));
        }
        if (toAdd.length > 0) {
            Collections.addAll(tokenWrappers, toAdd);
        }
        Collections.sort(tokenWrappers, new Comparator<TokenWrapper>() {
            public int compare(TokenWrapper lhs, TokenWrapper rhs) {
                return (int) (rhs.b.getExpireTime() - lhs.b.getExpireTime());
            }
        });
        a.p("loadAllTokens: " + tokenWrappers, new Object[0]);
        return tokenWrappers;
    }
}
