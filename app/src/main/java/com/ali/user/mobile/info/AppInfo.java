package com.ali.user.mobile.info;

import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.dataprovider.AppDataProvider;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.util.HttpUtil;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.security.StaticDataStore;
import com.alipay.android.phone.inside.security.encryption.DataContext;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.InitResultListener;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

public final class AppInfo {
    private static final int GET_RESULT_TIMEOUT = 5;
    private static final String TAG = "AppInfo";
    public static final String TAOBAO_SSO_MTOP_APP_KEY_ONLINE = "23699722";
    private static AppInfo sInstance;
    Callable<String> callable = new Callable<String>() {
        /* access modifiers changed from: private */
        /* renamed from: a */
        public String call() {
            try {
                AppInfo.getInstance().initUmidToken(AppInfo.this.mContext);
                AppInfo.this.mSemaphore.acquire();
                return AppInfo.this.mApdidToken;
            } catch (Exception unused) {
                LoggerFactory.f().a((String) AppInfo.TAG, (String) "acquire exception");
                return AppInfo.this.mApdidToken;
            }
        }
    };
    private APSecuritySdk mApSecuritySdk;
    /* access modifiers changed from: private */
    public String mApdid = "";
    /* access modifiers changed from: private */
    public String mApdidToken = "";
    private AppDataProvider mAppDataProvider;
    /* access modifiers changed from: private */
    public Context mContext = LauncherApplication.a();
    private String mCurrentApdidToken = "";
    private final AtomicBoolean mInited = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public Semaphore mSemaphore = new Semaphore(0);
    private TidInfo mTidInfo;
    /* access modifiers changed from: private */
    public String mUmidToken = "";

    public final String getAppKey(Context context) {
        return "23699722";
    }

    public final String getSdkId() {
        return "aliusersdk";
    }

    public final String getSdkVersion() {
        return "2.0.0.0";
    }

    @Deprecated
    public final void setChannel(String str) {
    }

    private AppInfo() {
    }

    public static AppInfo getInstance() {
        synchronized (AppInfo.class) {
            try {
                if (sInstance == null) {
                    sInstance = new AppInfo();
                }
            }
        }
        return sInstance;
    }

    public final void init(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
        if (this.mContext == null) {
            this.mContext = LauncherApplication.a();
        }
        com.alipay.android.phone.inside.common.info.AppInfo.b();
        if (!this.mInited.get()) {
            getAPSecuritySdk(context);
            initUmidToken(context);
            this.mInited.set(true);
        }
    }

    private APSecuritySdk getAPSecuritySdk(Context context) {
        if (this.mApSecuritySdk == null) {
            this.mApSecuritySdk = APSecuritySdk.getInstance(context);
        }
        return this.mApSecuritySdk;
    }

    /* access modifiers changed from: private */
    public void initUmidToken(final Context context) {
        AnonymousClass1 r0 = new Runnable() {
            public void run() {
                AliUserLog.c(AppInfo.TAG, "start to initUmidToken");
                HashMap hashMap = new HashMap();
                DeviceInfo.b();
                hashMap.put("utdid", DeviceInfo.g());
                TidInfo tidInfo = AppInfo.this.getTidInfo();
                if (tidInfo != null) {
                    hashMap.put("tid", tidInfo.a());
                }
                hashMap.put("userId", AliUserInit.a());
                boolean c = AliUserInit.c();
                APSecuritySdk.getInstance(context).initToken(c ? 1 : 0, hashMap, new InitResultListener() {
                    public void onResult(TokenResult tokenResult) {
                        AliUserLog.c(AppInfo.TAG, String.format("apdid:%s, apdidToken:%s, umidToken:%s", new Object[]{tokenResult.apdid, tokenResult.apdidToken, tokenResult.umidToken}));
                        AppInfo.this.mApdid = tokenResult.apdid;
                        AppInfo.this.mApdidToken = tokenResult.apdidToken;
                        AppInfo.this.mUmidToken = tokenResult.umidToken;
                        AppInfo.this.mSemaphore.release();
                        AppInfo.this.setCookieDelayed();
                    }
                });
            }
        };
        AliUserLog.c(TAG, "initUmidToken in thread");
        SecurityUtil.a((Runnable) r0);
    }

    public final String getChannel() {
        return com.alipay.android.phone.inside.common.info.AppInfo.a().h();
    }

    public final String getProductId() {
        if (this.mAppDataProvider != null) {
            return this.mAppDataProvider.c();
        }
        return com.alipay.android.phone.inside.common.info.AppInfo.a().e();
    }

    public final String getProductVersion() {
        if (this.mAppDataProvider != null) {
            return this.mAppDataProvider.b();
        }
        return com.alipay.android.phone.inside.common.info.AppInfo.a().f();
    }

    public final String getAppName() {
        return this.mAppDataProvider != null ? this.mAppDataProvider.e() : "";
    }

    public final String getDeviceId() {
        return this.mAppDataProvider != null ? this.mAppDataProvider.d() : "";
    }

    @Deprecated
    public final boolean isAlipayApp() {
        return this.mAppDataProvider != null;
    }

    public final TokenResult getTokenResult() {
        return getAPSecuritySdk(this.mContext).getTokenResult();
    }

    public final String getApdid() {
        TokenResult tokenResult = getAPSecuritySdk(this.mContext).getTokenResult();
        if (tokenResult != null) {
            return tokenResult.apdid;
        }
        StringBuilder sb = new StringBuilder("tokenResult == null, return mApdid:");
        sb.append(this.mApdid);
        AliUserLog.c(TAG, sb.toString());
        return this.mApdid;
    }

    public final String getApdidToken() {
        TokenResult tokenResult = getAPSecuritySdk(this.mContext).getTokenResult();
        if (tokenResult != null) {
            return tokenResult.apdidToken;
        }
        StringBuilder sb = new StringBuilder("tokenResult == null, return mApdidToken:");
        sb.append(this.mApdidToken);
        AliUserLog.c(TAG, sb.toString());
        return this.mApdidToken;
    }

    /* JADX INFO: finally extract failed */
    public final String getApdidtokenResultTimeout() {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        FutureTask futureTask = new FutureTask(this.callable);
        newSingleThreadExecutor.execute(futureTask);
        LoggerFactory.f().a((String) TAG, (String) "getResultTimeout executed");
        try {
            String str = (String) futureTask.get(5, TimeUnit.SECONDS);
            newSingleThreadExecutor.shutdown();
            return str;
        } catch (Throwable th) {
            newSingleThreadExecutor.shutdown();
            throw th;
        }
    }

    public final String getDeviceKeySet() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(DictionaryKeys.V2_APDID, getApdidToken());
            return jSONObject.toString();
        } catch (Exception e) {
            AliUserLog.b((String) TAG, (Throwable) e);
            return e.getMessage();
        }
    }

    public final String getUmid() {
        TokenResult tokenResult = getAPSecuritySdk(this.mContext).getTokenResult();
        if (tokenResult != null) {
            return tokenResult.umidToken;
        }
        StringBuilder sb = new StringBuilder("tokenResult == null, return mUmidToken:");
        sb.append(this.mUmidToken);
        AliUserLog.c(TAG, sb.toString());
        return this.mUmidToken;
    }

    private String getAppKeyInternal() {
        DataContext dataContext = new DataContext();
        int i = 0;
        dataContext.a = 0;
        StaticDataStore staticDataStore = new StaticDataStore(new ContextWrapper(this.mContext));
        if (dataContext.a >= 0) {
            i = dataContext.a;
        }
        String a = staticDataStore.a(i);
        AliUserLog.c(TAG, "getAppKeyInternal:".concat(String.valueOf(a)));
        return a;
    }

    private TidInfo defaultTidInfo() {
        if (this.mTidInfo == null) {
            this.mTidInfo = new TidInfo();
        }
        return this.mTidInfo;
    }

    public final TidInfo getTidInfo() {
        if (this.mAppDataProvider != null) {
            return this.mAppDataProvider.a();
        }
        return defaultTidInfo();
    }

    public final boolean isUseSso() {
        return this.mAppDataProvider != null ? false : false;
    }

    public final void setDataProvider(AppDataProvider appDataProvider) {
        this.mAppDataProvider = appDataProvider;
    }

    /* access modifiers changed from: private */
    public void setCookieDelayed() {
        SecurityUtil.a(new Runnable() {
            public void run() {
                if (!TextUtils.isEmpty(AppInfo.this.mApdidToken)) {
                    StringBuilder sb = new StringBuilder("devKeySet=");
                    sb.append(AppInfo.this.getDeviceKeySet());
                    HttpUtil.a(".alipay.com", sb.toString());
                }
            }
        }, 5, TimeUnit.SECONDS);
    }

    public final void setAuthApdidToken(String str) {
        this.mCurrentApdidToken = str;
    }

    public final String getAuthApdidToken() {
        return this.mCurrentApdidToken;
    }
}
