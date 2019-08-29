package com.alibaba.baichuan.android.trade.config;

import android.content.Context;
import android.os.Handler;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.ut.AlibcUserTracker;
import com.alibaba.baichuan.android.trade.config.a.b;
import com.alibaba.baichuan.android.trade.config.a.c;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.utils.StringUtils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class AlibcConfig {
    public static final int DEFAULT_APPMONITOR_SAMPLING = 10000;
    public static final int DOUBLE_11_OPEN_TYPE_AUTO = 0;
    public static final int DOUBLE_11_OPEN_TYPE_H5 = 2;
    public static final int DOUBLE_11_OPEN_TYPE_NATIVE = 1;
    private static String b = "albbTradeConfig";
    public static String channel;
    private static volatile AlibcConfig f;
    Runnable a = new a(this);
    /* access modifiers changed from: private */
    public b c = new b();
    /* access modifiers changed from: private */
    public b d;
    private Context e = b();
    public long expiredTimeStamp = (System.currentTimeMillis() + 3600000);
    /* access modifiers changed from: private */
    public c g = new c();
    private final long h = 21600000;
    private final long i = 3600000;
    public String isvVersion = "1.0.0";
    /* access modifiers changed from: private */
    public Handler j = new Handler();
    public String taobaoNativeSource;
    public AlibcTaokeParams taokeParams;

    class a implements com.alibaba.baichuan.android.trade.config.b.a {
        a() {
        }

        public void a(com.alibaba.baichuan.android.trade.config.a.a aVar, String str) {
            AlibcConfig.this.c.a(aVar);
            AlibcConfig.this.g.a(aVar);
            AlibcUserTracker instance = AlibcUserTracker.getInstance();
            if (instance != null) {
                instance.setSampling();
            }
            synchronized (AlibcConfig.class) {
                AlibcConfig.this.expiredTimeStamp = System.currentTimeMillis() + 21600000;
                StringBuilder sb = new StringBuilder("当前的时间为过期时间戳为");
                sb.append(AlibcConfig.this.expiredTimeStamp);
                AlibcLogger.d("AlibcConfig", sb.toString());
            }
        }

        public void a(String str) {
        }
    }

    AlibcConfig() {
        a();
        this.d = new b(this.e, new a());
        startRepeatingLoadConfigTask();
    }

    private void a() {
        AlibcLogger.d("AlibcConfig", "config设置默认值开始");
        if (this.g.a() != null) {
            AlibcLogger.d("AlibcConfig", "configspwrapper有值");
            this.c.a(this.g.a());
            return;
        }
        AlibcLogger.d("AlibcConfig", "读取本地配置文件");
        try {
            AlibcLogger.d("AlibcConfig", "本地配置文件的值为".concat(String.valueOf("{\n  \"group0\": {\n    \"dataId\": \"com.alibaba.baichuan.nbcp.meta.default\",\n    \"group\": \"1.0.0\",\n    \"lastUpdate\": \"Jun 14, 2016 2:12:22 PM\",\n    \"sign\": \"\"\n  },\n  \"albbTradeConfig\": {\n    \"isSyncForTaoke\": \"YES\",\n    \"isForceH5\": \"NO\",\n    \"isNeedAlipay\": \"YES\",\n    \"loginDegarade\": \"NO\"\n  },\n  \"group2\": {\n    \"abc1\": \"agc1\",\n    \"abc2\": \"agc2\",\n    \"abc3\": \"agc3\",\n    \"11111\":\"11111\"\n  }\n}")));
            JSONObject jSONObject = new JSONObject("{\n  \"group0\": {\n    \"dataId\": \"com.alibaba.baichuan.nbcp.meta.default\",\n    \"group\": \"1.0.0\",\n    \"lastUpdate\": \"Jun 14, 2016 2:12:22 PM\",\n    \"sign\": \"\"\n  },\n  \"albbTradeConfig\": {\n    \"isSyncForTaoke\": \"YES\",\n    \"isForceH5\": \"NO\",\n    \"isNeedAlipay\": \"YES\",\n    \"loginDegarade\": \"NO\"\n  },\n  \"group2\": {\n    \"abc1\": \"agc1\",\n    \"abc2\": \"agc2\",\n    \"abc3\": \"agc3\",\n    \"11111\":\"11111\"\n  }\n}");
            com.alibaba.baichuan.android.trade.config.a.a aVar = new com.alibaba.baichuan.android.trade.config.a.a();
            aVar.a(jSONObject);
            this.c.a(aVar);
            AlibcLogger.d("AlibcConfig", "读取本地配置文件成功");
        } catch (JSONException e2) {
            StringBuilder sb = new StringBuilder("本地默认配置文件解析错误");
            sb.append(e2.getMessage());
            AlibcLogger.e("AlibcConfig", sb.toString());
        }
    }

    private boolean a(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if ((charAt < '0' || charAt > '9') && ((charAt < 'a' || charAt > 'z') && (charAt < 'A' || charAt > 'Z'))) {
                return false;
            }
        }
        return true;
    }

    private Context b() {
        return AlibcContext.context;
    }

    public static AlibcConfig getInstance() {
        if (f == null) {
            synchronized (AlibcConfig.class) {
                try {
                    if (f == null) {
                        f = new AlibcConfig();
                    }
                }
            }
        }
        return f;
    }

    public int getAppMonitorSampling() {
        Object globalConfig = getGlobalConfig("sampling");
        if (globalConfig instanceof String) {
            try {
                return Integer.parseInt((String) globalConfig);
            } catch (NumberFormatException e2) {
                AlibcLogger.e("AlibcConfig", e2.toString());
            }
        }
        return 10000;
    }

    public String getChannel() {
        String str = (String) getGlobalConfig("channelName");
        if (str == null || str.length() <= 0) {
            if (!a(channel)) {
                StringBuilder sb = new StringBuilder("Channel chars must in [0-9][a-z][A-Z], now : ");
                sb.append(channel);
                AlibcLogger.e("initChannel", sb.toString());
                channel = "0";
            }
            return channel;
        }
        String str2 = (String) getGlobalConfig("channelType");
        if (str2 == null || str2.length() <= 0) {
            return "0".concat(String.valueOf(str));
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(str);
        return sb2.toString();
    }

    public int getDouble11OpenType() {
        Object globalConfig = getGlobalConfig("double11OpenType");
        if (globalConfig instanceof String) {
            try {
                return Integer.parseInt((String) globalConfig);
            } catch (NumberFormatException e2) {
                AlibcLogger.e("AlibcConfig", e2.toString());
            }
        }
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0048 A[SYNTHETIC, Splitter:B:23:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0054 A[SYNTHETIC, Splitter:B:31:0x0054] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getFromAssets(java.lang.String r6) {
        /*
            r5 = this;
            r0 = 0
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            android.content.Context r2 = r5.e     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            android.content.res.Resources r2 = r2.getResources()     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            java.io.InputStream r6 = r2.open(r6)     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            r1.<init>(r6)     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ Exception -> 0x003e }
            r6.<init>(r1)     // Catch:{ Exception -> 0x003e }
            java.lang.String r2 = ""
        L_0x001b:
            java.lang.String r3 = r6.readLine()     // Catch:{ Exception -> 0x003e }
            if (r3 == 0) goto L_0x0031
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003e }
            r4.<init>()     // Catch:{ Exception -> 0x003e }
            r4.append(r2)     // Catch:{ Exception -> 0x003e }
            r4.append(r3)     // Catch:{ Exception -> 0x003e }
            java.lang.String r2 = r4.toString()     // Catch:{ Exception -> 0x003e }
            goto L_0x001b
        L_0x0031:
            r1.close()     // Catch:{ IOException -> 0x0035 }
            return r2
        L_0x0035:
            r6 = move-exception
            r6.printStackTrace()
            return r2
        L_0x003a:
            r6 = move-exception
            r1 = r0
            goto L_0x0052
        L_0x003d:
            r1 = r0
        L_0x003e:
            java.lang.String r6 = "AlibcConfig"
            java.lang.String r2 = "本地默认配置文件读取错误"
            com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.e(r6, r2)     // Catch:{ all -> 0x0051 }
            if (r1 == 0) goto L_0x0050
            r1.close()     // Catch:{ IOException -> 0x004c }
            return r0
        L_0x004c:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0050:
            return r0
        L_0x0051:
            r6 = move-exception
        L_0x0052:
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch:{ IOException -> 0x0058 }
            goto L_0x005c
        L_0x0058:
            r0 = move-exception
            r0.printStackTrace()
        L_0x005c:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.config.AlibcConfig.getFromAssets(java.lang.String):java.lang.String");
    }

    public Object getGlobalConfig(String str) {
        if (str == null) {
            return null;
        }
        return this.c.b(b, str, null);
    }

    public Object getGlobalConfig(String str, Object obj) {
        if (str == null) {
            return null;
        }
        return this.c.b(b, str, obj);
    }

    public Object getGroupConfig(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        return this.c.b(str, str2, null);
    }

    public Object getGroupConfig(String str, String str2, Object obj) {
        if (str == null || str2 == null) {
            return null;
        }
        return this.c.b(str, str2, obj);
    }

    public boolean getIsSyncForTaoke() {
        return "YES".equals(getGlobalConfig("isSyncForTaoke"));
    }

    public String getIsvCode() {
        if (AlibcContext.isvCode != null) {
            return AlibcContext.isvCode;
        }
        if (getGlobalConfig("isvCode") != null) {
            return (String) getGlobalConfig("isvCode");
        }
        return null;
    }

    public String getIsvVersion() {
        return getGlobalConfig("isvVersion") != null ? (String) getGlobalConfig("isvVersion") : this.isvVersion;
    }

    public boolean getLoginDegarade(boolean z) {
        return StringUtils.obj2Boolean(getGlobalConfig("loginDegarade", Boolean.valueOf(z)));
    }

    public AlibcTaokeParams getNBTTradeTaokeParams() {
        return this.taokeParams;
    }

    public AlibcTaokeParams getTaokeParams() {
        return this.taokeParams;
    }

    public String getWebTTID() {
        return String.format("2014_%s_%s@baichuan_android_%s", new Object[]{getChannel(), AlibcContext.getAppKey(), AlibcContext.sdkVersion});
    }

    public boolean isForceH5() {
        return "YES".equals(getGlobalConfig("isForceH5"));
    }

    public boolean needTaoke() {
        return "YES".equals(getGlobalConfig("isTaokeUT"));
    }

    public void setChangeSlickUrl(boolean z) {
        setGlobalConfig("IS_TAOKE_SCLICK", Boolean.valueOf(z));
    }

    public void setChannel(String str, String str2) {
        setGlobalConfig("channelType", str);
        setGlobalConfig("channelName", str2);
    }

    public boolean setGlobalConfig(String str, Object obj) {
        return setGroupConfig(b, str, obj);
    }

    public boolean setGroupConfig(String str, String str2, Object obj) {
        if (str == null || str2 == null || obj == null) {
            return false;
        }
        this.c.a(str, str2, obj);
        return true;
    }

    public boolean setIsForceH5(boolean z) {
        this.c.a((String) "isForceH5");
        return setGlobalConfig("isForceH5", z ? "YES" : "NO");
    }

    public boolean setIsSyncForTaoke(boolean z) {
        this.c.a((String) "isSyncForTaoke");
        return setGlobalConfig("isSyncForTaoke", z ? "YES" : "NO");
    }

    public boolean setIsvCode(String str) {
        this.c.a((String) "isvCode");
        return setGlobalConfig("isvCode", str);
    }

    public boolean setIsvVersion(String str) {
        this.c.a((String) "isvVersion");
        return setGlobalConfig("isvVersion", str);
    }

    public boolean setShouldUseAlipay(boolean z) {
        this.c.a((String) "isNeedAlipay");
        return setGlobalConfig("isNeedAlipay", z ? "YES" : "NO");
    }

    public void setTaokeParams(AlibcTaokeParams alibcTaokeParams) {
        if (alibcTaokeParams != null) {
            setGlobalConfig("pid", alibcTaokeParams.pid);
            setGlobalConfig("subPid", alibcTaokeParams.subPid);
            setGlobalConfig("unionId", alibcTaokeParams.unionId);
            this.taokeParams = alibcTaokeParams;
        }
    }

    public boolean shouldUseAlipay(boolean z) {
        return StringUtils.obj2Boolean(getGlobalConfig("isNeedAlipay", Boolean.valueOf(z)));
    }

    public void startRepeatingLoadConfigTask() {
        this.a.run();
    }
}
