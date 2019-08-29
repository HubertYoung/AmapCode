package com.sina.weibo.sdk.sso;

import android.content.Context;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.LogUtil;
import com.weibo.ssosdk.WeiboSsoSdk;

public final class WeiboSsoManager {
    private static final String TAG = "WeiboSsoManager";
    private String aid;

    static class Instance {
        /* access modifiers changed from: private */
        public static final WeiboSsoManager instance = new WeiboSsoManager();

        private Instance() {
        }
    }

    private WeiboSsoManager() {
    }

    public static synchronized WeiboSsoManager getInstance() {
        WeiboSsoManager access$100;
        synchronized (WeiboSsoManager.class) {
            try {
                access$100 = Instance.instance;
            }
        }
        return access$100;
    }

    public final void init(Context context, String str) {
        LogUtil.d(TAG, "init config");
        fbk fbk = new fbk();
        fbk.a = context.getApplicationContext();
        fbk.b = str;
        fbk.d = "1478195010";
        fbk.e = "1000_0001";
        WeiboSsoSdk.a(fbk);
        initAid();
    }

    private void initAid() {
        try {
            WeiboSsoSdk a = WeiboSsoSdk.a();
            String b = WeiboSsoSdk.b();
            if (TextUtils.isEmpty(b)) {
                if (a.a == null || TextUtils.isEmpty(a.a.a)) {
                    Thread thread = new Thread(new Runnable() {
                        public final void run() {
                            try {
                                WeiboSsoSdk.a(WeiboSsoSdk.this, "", 1);
                            } catch (Exception unused) {
                            }
                        }
                    });
                    thread.start();
                    thread.join();
                }
                if (a.a == null) {
                    throw new Exception("visitor login failed");
                }
                b = a.a.a;
            }
            this.aid = b;
            if (TextUtils.isEmpty(this.aid)) {
                WeiboSsoSdk a2 = WeiboSsoSdk.a();
                if (a2.a == null) {
                    Thread thread2 = new Thread(new Runnable() {
                        public final void run() {
                            try {
                                WeiboSsoSdk.a(WeiboSsoSdk.this, "", 1);
                            } catch (Exception unused) {
                            }
                        }
                    });
                    thread2.start();
                    thread2.join();
                }
                if (a2.a == null) {
                    throw new Exception("visitor login failed");
                }
                this.aid = a2.a.a;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
    }

    public final String getAid(Context context, String str) {
        LogUtil.d(TAG, "getAid()");
        if (TextUtils.isEmpty(this.aid)) {
            init(context, str);
        }
        return this.aid;
    }

    public final String getMfp(Context context) {
        return fbj.a(context);
    }
}
