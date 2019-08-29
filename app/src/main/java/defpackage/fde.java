package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.cache.domain.ApiCacheDo;
import mtopsdk.mtop.cache.domain.AppConfigDo;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: fde reason: default package */
/* compiled from: AppConfigManager */
public final class fde {
    ConcurrentHashMap<String, ApiCacheDo> a;
    public Set<String> b;

    /* renamed from: fde$a */
    /* compiled from: AppConfigManager */
    public static class a {
        /* access modifiers changed from: private */
        public static fde a = new fde(0);
    }

    /* synthetic */ fde(byte b2) {
        this();
    }

    private fde() {
        this.a = new ConcurrentHashMap<>();
        this.b = new HashSet();
    }

    public final ApiCacheDo a(String str) {
        if (fdd.b(str)) {
            return null;
        }
        return this.a.get(str);
    }

    public final void a(String str, ApiCacheDo apiCacheDo) {
        if (!fdd.b(str)) {
            if (TBSdkLog.a(LogEnable.DebugEnable)) {
                TBSdkLog.a((String) "mtopsdk.AppConfigManager", "[addApiCacheDoToGroup] apiCacheDo:".concat(String.valueOf(apiCacheDo)));
            }
            this.a.put(str, apiCacheDo);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x007f A[Catch:{ Exception -> 0x00af }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0080 A[Catch:{ Exception -> 0x00af }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0085 A[Catch:{ Exception -> 0x00af }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x008a A[Catch:{ Exception -> 0x00af }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x008f A[Catch:{ Exception -> 0x00af }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(@android.support.annotation.NonNull java.lang.String r11, @android.support.annotation.NonNull mtopsdk.mtop.cache.domain.ApiCacheDo r12) {
        /*
            if (r11 == 0) goto L_0x00cf
            if (r12 != 0) goto L_0x0006
            goto L_0x00cf
        L_0x0006:
            java.lang.String r0 = ","
            java.lang.String[] r0 = r11.split(r0)
            int r1 = r0.length
            r2 = 0
            r3 = 0
        L_0x000f:
            if (r3 >= r1) goto L_0x00ce
            r4 = r0[r3]
            java.lang.String r5 = "of=on"
            boolean r5 = r5.equalsIgnoreCase(r4)     // Catch:{ Exception -> 0x00af }
            r6 = 1
            if (r5 == 0) goto L_0x0020
            r12.offline = r6     // Catch:{ Exception -> 0x00af }
            goto L_0x00ca
        L_0x0020:
            java.lang.String r5 = "private=false"
            boolean r5 = r5.equalsIgnoreCase(r4)     // Catch:{ Exception -> 0x00af }
            if (r5 == 0) goto L_0x002c
            r12.privateScope = r2     // Catch:{ Exception -> 0x00af }
            goto L_0x00ca
        L_0x002c:
            java.lang.String r5 = "kt="
            boolean r5 = r4.contains(r5)     // Catch:{ Exception -> 0x00af }
            r7 = 3
            if (r5 == 0) goto L_0x0094
            java.lang.String r5 = r4.substring(r7)     // Catch:{ Exception -> 0x00af }
            r8 = -1
            int r9 = r5.hashCode()     // Catch:{ Exception -> 0x00af }
            r10 = 64897(0xfd81, float:9.094E-41)
            if (r9 == r10) goto L_0x0071
            r10 = 69104(0x10df0, float:9.6835E-41)
            if (r9 == r10) goto L_0x0067
            r7 = 72638(0x11bbe, float:1.01788E-40)
            if (r9 == r7) goto L_0x005d
            r7 = 2402104(0x24a738, float:3.366065E-39)
            if (r9 == r7) goto L_0x0053
            goto L_0x007b
        L_0x0053:
            java.lang.String r7 = "NONE"
            boolean r5 = r5.equals(r7)     // Catch:{ Exception -> 0x00af }
            if (r5 == 0) goto L_0x007b
            r5 = 1
            goto L_0x007c
        L_0x005d:
            java.lang.String r6 = "INC"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x00af }
            if (r5 == 0) goto L_0x007b
            r5 = 2
            goto L_0x007c
        L_0x0067:
            java.lang.String r6 = "EXC"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x00af }
            if (r5 == 0) goto L_0x007b
            r5 = 3
            goto L_0x007c
        L_0x0071:
            java.lang.String r6 = "ALL"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x00af }
            if (r5 == 0) goto L_0x007b
            r5 = 0
            goto L_0x007c
        L_0x007b:
            r5 = -1
        L_0x007c:
            switch(r5) {
                case 0: goto L_0x008f;
                case 1: goto L_0x008a;
                case 2: goto L_0x0085;
                case 3: goto L_0x0080;
                default: goto L_0x007f;
            }     // Catch:{ Exception -> 0x00af }
        L_0x007f:
            goto L_0x00ca
        L_0x0080:
            java.lang.String r5 = "EXC"
            r12.cacheKeyType = r5     // Catch:{ Exception -> 0x00af }
            goto L_0x00ca
        L_0x0085:
            java.lang.String r5 = "INC"
            r12.cacheKeyType = r5     // Catch:{ Exception -> 0x00af }
            goto L_0x00ca
        L_0x008a:
            java.lang.String r5 = "NONE"
            r12.cacheKeyType = r5     // Catch:{ Exception -> 0x00af }
            goto L_0x00ca
        L_0x008f:
            java.lang.String r5 = "ALL"
            r12.cacheKeyType = r5     // Catch:{ Exception -> 0x00af }
            goto L_0x00ca
        L_0x0094:
            java.lang.String r5 = "ks="
            boolean r5 = r4.contains(r5)     // Catch:{ Exception -> 0x00af }
            if (r5 == 0) goto L_0x00ac
            java.lang.String r5 = r4.substring(r7)     // Catch:{ Exception -> 0x00af }
            java.lang.String r6 = "\\|"
            java.lang.String[] r5 = r5.split(r6)     // Catch:{ Exception -> 0x00af }
            java.util.List r5 = java.util.Arrays.asList(r5)     // Catch:{ Exception -> 0x00af }
            r12.cacheKeyItems = r5     // Catch:{ Exception -> 0x00af }
        L_0x00ac:
            r12.cacheControlHeader = r11     // Catch:{ Exception -> 0x00af }
            goto L_0x00ca
        L_0x00af:
            java.lang.String r5 = "mtopsdk.AppConfigManager"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "[parseCacheControlHeader] parse item in CacheControlHeader error.item ="
            r6.<init>(r7)
            r6.append(r4)
            java.lang.String r4 = ",CacheControlHeader="
            r6.append(r4)
            r6.append(r11)
            java.lang.String r4 = r6.toString()
            mtopsdk.common.util.TBSdkLog.c(r5, r4)
        L_0x00ca:
            int r3 = r3 + 1
            goto L_0x000f
        L_0x00ce:
            return
        L_0x00cf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fde.b(java.lang.String, mtopsdk.mtop.cache.domain.ApiCacheDo):void");
    }

    public final boolean a(@NonNull String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject("clientCache");
            if (optJSONObject == null) {
                return false;
            }
            JSONArray optJSONArray = optJSONObject.optJSONArray("clientCacheAppConfList");
            if (optJSONArray == null) {
                return false;
            }
            for (int length = optJSONArray.length() - 1; length >= 0; length--) {
                JSONObject optJSONObject2 = optJSONArray.optJSONObject(length);
                if (optJSONObject2 != null) {
                    String optString = optJSONObject2.optString(MtopJSParam.API);
                    String optString2 = optJSONObject2.optString("v");
                    String optString3 = optJSONObject2.optString("block");
                    String b2 = fdd.b(optString, optString2);
                    ApiCacheDo a2 = a.a.a(b2);
                    if (a2 != null) {
                        a2.blockName = optString3;
                    } else {
                        a.a.a(b2, new ApiCacheDo(optString, optString2, optString3));
                    }
                }
            }
            JSONObject optJSONObject3 = jSONObject.optJSONObject("unit");
            if (optJSONObject3 != null) {
                JSONArray optJSONArray2 = optJSONObject3.optJSONArray("tradeUnitApiList");
                if (optJSONArray2 != null) {
                    HashSet hashSet = new HashSet();
                    for (int length2 = optJSONArray2.length() - 1; length2 >= 0; length2--) {
                        JSONObject optJSONObject4 = optJSONArray2.optJSONObject(length2);
                        if (optJSONObject4 != null) {
                            hashSet.add(fdd.b(optJSONObject4.optString(MtopJSParam.API), optJSONObject4.optString("v")));
                        }
                    }
                    this.b = hashSet;
                }
            }
            return true;
        } catch (Exception e) {
            TBSdkLog.b("mtopsdk.AppConfigManager", str2, "[parseAppConfig]parse appConf node error.", e);
            return false;
        }
    }

    public final void a(ffd ffd) {
        if (ffd != null) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(ffd.e.getExternalFilesDir(null).getAbsoluteFile());
                sb.append("/mtop");
                File file = new File(sb.toString());
                AppConfigDo appConfigDo = (AppConfigDo) fdb.a(file, "appConf");
                if (appConfigDo != null && fdd.a(appConfigDo.appConf) && appConfigDo.appConfigVersion > ffd.s) {
                    synchronized (ffd.t) {
                        if (appConfigDo.appConfigVersion > ffd.s && a.a.a(appConfigDo.appConf, (String) "")) {
                            ffd.s = appConfigDo.appConfigVersion;
                            StringBuilder sb2 = new StringBuilder("[reloadAppConfig] reload appConf succeed. appConfVersion=");
                            sb2.append(ffd.s);
                            TBSdkLog.b("mtopsdk.AppConfigManager", sb2.toString());
                        }
                    }
                }
                Map map = (Map) fdb.a(file, "apiCacheConf");
                if (map != null) {
                    for (Entry entry : map.entrySet()) {
                        String str = (String) entry.getKey();
                        ApiCacheDo apiCacheDo = (ApiCacheDo) entry.getValue();
                        ApiCacheDo apiCacheDo2 = this.a.get(str);
                        if (apiCacheDo2 == null) {
                            this.a.put(str, apiCacheDo);
                            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                                TBSdkLog.b("mtopsdk.AppConfigManager", "[reloadAppConfig] add apiCacheDo config,apiKey=".concat(String.valueOf(str)));
                            }
                        } else if (!apiCacheDo2.equals(apiCacheDo)) {
                            apiCacheDo2.cacheControlHeader = apiCacheDo.cacheControlHeader;
                            apiCacheDo2.privateScope = apiCacheDo.privateScope;
                            apiCacheDo2.offline = apiCacheDo.offline;
                            apiCacheDo2.cacheKeyType = apiCacheDo.cacheKeyType;
                            apiCacheDo2.cacheKeyItems = apiCacheDo.cacheKeyItems;
                            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                                TBSdkLog.b("mtopsdk.AppConfigManager", "[reloadAppConfig] update apiCacheDo config,apiKey=".concat(String.valueOf(str)));
                            }
                        }
                    }
                }
            } catch (Exception unused) {
                TBSdkLog.d("mtopsdk.AppConfigManager", "[reloadAppConfig] reload appConf file error.");
            }
        }
    }

    public final void a(final Context context, final String str) {
        ffy.a(new Runnable() {
            public final void run() {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(context.getExternalFilesDir(null).getAbsoluteFile());
                    sb.append("/mtop");
                    fdb.a(fde.this.a, new File(sb.toString()), "apiCacheConf");
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        TBSdkLog.b((String) "mtopsdk.AppConfigManager", str, (String) "[storeApiCacheDoMap] save apiCacheConf succeed.");
                    }
                } catch (Exception e) {
                    TBSdkLog.b("mtopsdk.AppConfigManager", str, "[storeApiCacheDoMap] save apiCacheConf error.", e);
                }
            }
        });
    }
}
