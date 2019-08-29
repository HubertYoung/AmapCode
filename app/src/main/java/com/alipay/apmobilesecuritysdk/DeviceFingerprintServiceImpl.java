package com.alipay.apmobilesecuritysdk;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.apdidgen.ApdidManager;
import com.alipay.apmobilesecuritysdk.commonbiz.OnlineHostConfig;
import com.alipay.apmobilesecuritysdk.commonbiz.external.UtdidWrapper;
import com.alipay.apmobilesecuritysdk.commonbiz.monitor.LogAgent;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.InitResultListener;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.apmobilesecuritysdk.face.DeviceTokenBizID;
import com.alipay.apmobilesecuritysdk.storage.ApdidStorage;
import com.alipay.apmobilesecuritysdk.storage.ApdidStorageV4;
import com.alipay.apmobilesecuritysdk.storage.OpenApdidTokenStorage;
import com.alipay.apmobilesecuritysdk.storage.SettingsStorage;
import com.alipay.apmobilesecuritysdk.storage.TokenStorage;
import com.alipay.edge.face.EdgeRiskAnalyzer;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.commonutils.SingleThreadPool;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DeviceFingerprintServiceImpl extends DeviceFingerprintService {
    /* access modifiers changed from: private */
    public TraceLogger logger = LoggerFactory.f();
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public AtomicInteger mDefaultSceneInitializeCnt = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public AtomicBoolean mFirstInitialized = new AtomicBoolean(false);

    public String getSdkName() {
        return "security-sdk-VR";
    }

    public String getSdkVersion() {
        return "3.6.5-20170830";
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void initToken(int i, Map<String, String> map, InitResultListener initResultListener) {
        this.mDefaultSceneInitializeCnt.incrementAndGet();
        if (CommonUtils.isApkDebugable(this.mContext)) {
            clearLocalCacheIfEnvChanges();
        }
        String str = map.get("utdid");
        String str2 = map.get("tid");
        String str3 = map.get("userId");
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        if (str3 == null) {
            str3 = "";
        }
        if (CommonUtils.isBlank(str)) {
            str = UtdidWrapper.getUtdid(this.mContext);
        }
        if (CommonUtils.isBlank(str2)) {
            str2 = "";
        }
        HashMap hashMap = new HashMap();
        hashMap.put("utdid", str);
        hashMap.put("tid", str2);
        hashMap.put("userId", str3);
        hashMap.put("appName", "");
        hashMap.put("appKeyClient", "");
        hashMap.put("appchannel", "");
        hashMap.put(TransportConstants.KEY_RPC_VERSION, "5");
        if (CommonUtils.isApkDebugable(this.mContext)) {
            LoggerFactory.f().b((String) CONST.LOG_TAG, "initToken() utdid = ".concat(String.valueOf(str)));
        }
        runEdge();
        scheduleGenerateApdid(hashMap, initResultListener);
    }

    private void scheduleGenerateApdid(final Map<String, Object> map, final InitResultListener initResultListener) {
        SingleThreadPool.getInstance().execute(new Runnable() {
            public void run() {
                try {
                    TraceLogger access$000 = DeviceFingerprintServiceImpl.this.logger;
                    StringBuilder sb = new StringBuilder("initToken(), 开始时间: ");
                    sb.append(System.currentTimeMillis());
                    access$000.b((String) CONST.LOG_TAG, sb.toString());
                    ApdidManager.a(DeviceFingerprintServiceImpl.this.mContext, map, DeviceFingerprintServiceImpl.this.getVKeySecretIndex(DeviceFingerprintServiceImpl.this.mContext));
                    TokenResult a2 = ApdidManager.a(DeviceFingerprintServiceImpl.this.mContext);
                    TraceLogger access$0002 = DeviceFingerprintServiceImpl.this.logger;
                    StringBuilder sb2 = new StringBuilder("token ");
                    sb2.append(a2.apdidToken);
                    access$0002.b((String) CONST.LOG_TAG, sb2.toString());
                    if (initResultListener != null) {
                        TokenResult a3 = ApdidManager.a(DeviceFingerprintServiceImpl.this.mContext);
                        DeviceFingerprintServiceImpl.this.logger.b((String) CONST.LOG_TAG, (String) "initToken(), 初始化成功,调用回调函数。");
                        initResultListener.onResult(a3);
                    }
                    TraceLogger access$0003 = DeviceFingerprintServiceImpl.this.logger;
                    StringBuilder sb3 = new StringBuilder("initToken(), 结束时间: ");
                    sb3.append(System.currentTimeMillis());
                    access$0003.b((String) CONST.LOG_TAG, sb3.toString());
                } catch (Throwable th) {
                    DeviceFingerprintServiceImpl.this.mDefaultSceneInitializeCnt.decrementAndGet();
                    DeviceFingerprintServiceImpl.this.mFirstInitialized.set(true);
                    throw th;
                }
                DeviceFingerprintServiceImpl.this.mDefaultSceneInitializeCnt.decrementAndGet();
                DeviceFingerprintServiceImpl.this.mFirstInitialized.set(true);
            }
        });
    }

    private void clearLocalCacheIfEnvChanges() {
        String c = SettingsStorage.c(this.mContext);
        OnlineHostConfig.a();
        String c2 = OnlineHostConfig.c();
        this.logger.b((String) CONST.LOG_TAG, "initToken(), LAST DEV = ".concat(String.valueOf(c)));
        this.logger.b((String) CONST.LOG_TAG, "initToken(), NOW  DEV = ".concat(String.valueOf(c2)));
        if (CommonUtils.isNotBlank(c) && !CommonUtils.equals(c, c2)) {
            ApdidStorage.a(this.mContext);
            ApdidStorageV4.a(this.mContext);
            OpenApdidTokenStorage.a(this.mContext);
            TokenStorage.g();
        }
        if (!CommonUtils.equals(c, c2)) {
            SettingsStorage.c(this.mContext, c2);
        }
    }

    public TokenResult getTokenResult() {
        TokenResult a = ApdidManager.a(this.mContext);
        LogAgent.a(DeviceTokenBizID.DEFAULT.getDesc(), a.apdidToken, a.umidToken);
        reGenApdidTokenIfEmpty(a.apdidToken);
        return a;
    }

    public TokenResult getTokenResult(DeviceTokenBizID deviceTokenBizID) {
        TokenResult a = ApdidManager.a(this.mContext);
        LogAgent.a(deviceTokenBizID.getDesc(), a.apdidToken, a.umidToken);
        reGenApdidTokenIfEmpty(a.apdidToken);
        return a;
    }

    public String getApdidToken() {
        String b = TokenStorage.b(this.mContext, "");
        LogAgent.c(DeviceTokenBizID.DEFAULT.getDesc(), b);
        reGenApdidTokenIfEmpty(b);
        return b;
    }

    public String getApdidToken(DeviceTokenBizID deviceTokenBizID) {
        String b = TokenStorage.b(this.mContext, "");
        LogAgent.c(deviceTokenBizID.getDesc(), b);
        reGenApdidTokenIfEmpty(b);
        return b;
    }

    private void reGenApdidTokenIfEmpty(String str) {
        if (str == null || str.length() == 0) {
            if (this.mDefaultSceneInitializeCnt.get() != 0 || !this.mFirstInitialized.get()) {
                this.logger.b((String) CONST.LOG_TAG, (String) "reGenApdidTokenIfEmpty() others working, dont regenerate!");
            } else {
                this.logger.b((String) CONST.LOG_TAG, (String) "reGenApdidTokenIfEmpty() regenerate now!");
                initToken(0, new HashMap(), null);
            }
        }
    }

    public void runEdge() {
        this.logger.b((String) CONST.LOG_TAG, (String) "Edge Sdk Sync Valve start running...");
        final Context context = this.mContext;
        if (CommonUtils.isAlipayWallet(context)) {
            new Thread(new Runnable() {
                public void run() {
                    EdgeRiskAnalyzer.getInstance(context).initialize();
                }
            }).start();
        }
    }

    public String getUtdid() {
        return UtdidWrapper.getUtdid(this.mContext);
    }

    /* access modifiers changed from: private */
    public int getVKeySecretIndex(Context context) {
        if (!CommonUtils.isApkDebugable(context)) {
            return 0;
        }
        OnlineHostConfig.a();
        OnlineHostConfig.b();
        return 0;
    }
}
