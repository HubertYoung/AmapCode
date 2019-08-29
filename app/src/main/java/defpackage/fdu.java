package defpackage;

import android.content.Context;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.cache.domain.ApiCacheDo;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.domain.ResponseSource;

/* renamed from: fdu reason: default package */
/* compiled from: CacheDuplexFilter */
public final class fdu implements fdg, fdh {
    private static final Map<ep, fei> a = new ConcurrentHashMap(2);

    public final String a() {
        return "mtopsdk.CacheDuplexFilter";
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x010c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String b(defpackage.fdf r8) {
        /*
            r7 = this;
            java.lang.String r0 = "CONTINUE"
            fff r1 = defpackage.fff.a()
            java.util.Set<java.lang.String> r1 = r1.b
            if (r1 == 0) goto L_0x0036
            mtopsdk.mtop.domain.MtopRequest r1 = r8.b
            java.lang.String r1 = r1.getKey()
            fff r2 = defpackage.fff.a()
            java.util.Set<java.lang.String> r2 = r2.b
            boolean r2 = r2.contains(r1)
            if (r2 == 0) goto L_0x0036
            mtopsdk.common.util.TBSdkLog$LogEnable r2 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable
            boolean r2 = mtopsdk.common.util.TBSdkLog.a(r2)
            if (r2 == 0) goto L_0x0035
            java.lang.String r2 = "mtopsdk.CacheDuplexFilter"
            java.lang.String r8 = r8.h
            java.lang.String r3 = "apiKey in degradeApiCacheList,apiKey="
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r1 = r3.concat(r1)
            mtopsdk.common.util.TBSdkLog.b(r2, r8, r1)
        L_0x0035:
            return r0
        L_0x0036:
            mtopsdk.mtop.util.MtopStatistics r1 = r8.g
            r2 = 1
            r1.l = r2
            mtopsdk.mtop.intf.Mtop r1 = r8.a
            ffd r1 = r1.c
            ep r1 = r1.u
            if (r1 != 0) goto L_0x0065
            mtopsdk.common.util.TBSdkLog$LogEnable r1 = mtopsdk.common.util.TBSdkLog.LogEnable.DebugEnable
            boolean r1 = mtopsdk.common.util.TBSdkLog.a(r1)
            if (r1 == 0) goto L_0x0064
            java.lang.String r1 = "mtopsdk.CacheDuplexFilter"
            java.lang.String r2 = r8.h
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = " CacheImpl is null. instanceId="
            r3.<init>(r4)
            mtopsdk.mtop.intf.Mtop r8 = r8.a
            java.lang.String r8 = r8.b
            r3.append(r8)
            java.lang.String r8 = r3.toString()
            mtopsdk.common.util.TBSdkLog.a(r1, r2, r8)
        L_0x0064:
            return r0
        L_0x0065:
            java.util.Map<ep, fei> r2 = a
            java.lang.Object r2 = r2.get(r1)
            fei r2 = (defpackage.fei) r2
            if (r2 != 0) goto L_0x008b
            java.util.Map<ep, fei> r3 = a
            monitor-enter(r3)
            java.util.Map<ep, fei> r2 = a     // Catch:{ all -> 0x0088 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0088 }
            fei r2 = (defpackage.fei) r2     // Catch:{ all -> 0x0088 }
            if (r2 != 0) goto L_0x0086
            fej r2 = new fej     // Catch:{ all -> 0x0088 }
            r2.<init>(r1)     // Catch:{ all -> 0x0088 }
            java.util.Map<ep, fei> r4 = a     // Catch:{ all -> 0x0088 }
            r4.put(r1, r2)     // Catch:{ all -> 0x0088 }
        L_0x0086:
            monitor-exit(r3)     // Catch:{ all -> 0x0088 }
            goto L_0x008b
        L_0x0088:
            r8 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0088 }
            throw r8
        L_0x008b:
            r1 = 0
            mtopsdk.network.domain.Request r3 = r8.k     // Catch:{ Exception -> 0x00ec }
            boolean r3 = r2.a(r3)     // Catch:{ Exception -> 0x00ec }
            if (r3 == 0) goto L_0x00ea
            mtopsdk.mtop.domain.ResponseSource r3 = new mtopsdk.mtop.domain.ResponseSource     // Catch:{ Exception -> 0x00ec }
            r3.<init>(r8, r2)     // Catch:{ Exception -> 0x00ec }
            r8.j = r3     // Catch:{ Exception -> 0x00e8 }
            r3.getCacheKey()     // Catch:{ Exception -> 0x00e8 }
            r3.getCacheBlock()     // Catch:{ Exception -> 0x00e8 }
            java.lang.String r1 = r8.h     // Catch:{ Exception -> 0x00e8 }
            anetwork.network.cache.RpcCache r1 = r2.a(r1)     // Catch:{ Exception -> 0x00e8 }
            r3.rpcCache = r1     // Catch:{ Exception -> 0x00e8 }
            mtopsdk.mtop.common.MtopNetworkProp r1 = r8.d     // Catch:{ Exception -> 0x00e8 }
            android.os.Handler r1 = r1.handler     // Catch:{ Exception -> 0x00e8 }
            anetwork.network.cache.RpcCache r2 = r3.rpcCache     // Catch:{ Exception -> 0x00e8 }
            if (r2 == 0) goto L_0x00de
            anetwork.network.cache.RpcCache r2 = r3.rpcCache     // Catch:{ Exception -> 0x00e8 }
            anetwork.network.cache.RpcCache$CacheStatus r2 = r2.cacheStatus     // Catch:{ Exception -> 0x00e8 }
            if (r2 != 0) goto L_0x00bd
            fem r2 = new fem     // Catch:{ Exception -> 0x00e8 }
            r2.<init>()     // Catch:{ Exception -> 0x00e8 }
            goto L_0x00da
        L_0x00bd:
            int[] r4 = defpackage.fek.AnonymousClass1.a     // Catch:{ Exception -> 0x00e8 }
            int r2 = r2.ordinal()     // Catch:{ Exception -> 0x00e8 }
            r2 = r4[r2]     // Catch:{ Exception -> 0x00e8 }
            switch(r2) {
                case 1: goto L_0x00d1;
                case 2: goto L_0x00cb;
                default: goto L_0x00c8;
            }     // Catch:{ Exception -> 0x00e8 }
        L_0x00c8:
            fem r2 = new fem     // Catch:{ Exception -> 0x00e8 }
            goto L_0x00d7
        L_0x00cb:
            fen r2 = new fen     // Catch:{ Exception -> 0x00e8 }
            r2.<init>()     // Catch:{ Exception -> 0x00e8 }
            goto L_0x00da
        L_0x00d1:
            feo r2 = new feo     // Catch:{ Exception -> 0x00e8 }
            r2.<init>()     // Catch:{ Exception -> 0x00e8 }
            goto L_0x00da
        L_0x00d7:
            r2.<init>()     // Catch:{ Exception -> 0x00e8 }
        L_0x00da:
            r2.a(r3, r1)     // Catch:{ Exception -> 0x00e8 }
            goto L_0x010a
        L_0x00de:
            java.lang.String r1 = "mtopsdk.CacheStatusHandler"
            java.lang.String r2 = r3.seqNo     // Catch:{ Exception -> 0x00e8 }
            java.lang.String r4 = "[handleCacheStatus]Didn't  hit local cache "
            mtopsdk.common.util.TBSdkLog.b(r1, r2, r4)     // Catch:{ Exception -> 0x00e8 }
            goto L_0x010a
        L_0x00e8:
            r1 = move-exception
            goto L_0x00ef
        L_0x00ea:
            r3 = r1
            goto L_0x010a
        L_0x00ec:
            r2 = move-exception
            r3 = r1
            r1 = r2
        L_0x00ef:
            java.lang.String r2 = "mtopsdk.CacheDuplexFilter"
            java.lang.String r4 = r8.h
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "[initResponseSource] initResponseSource error,apiKey="
            r5.<init>(r6)
            mtopsdk.mtop.domain.MtopRequest r6 = r8.b
            java.lang.String r6 = r6.getKey()
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            mtopsdk.common.util.TBSdkLog.b(r2, r4, r5, r1)
        L_0x010a:
            if (r3 == 0) goto L_0x011a
            boolean r1 = r3.requireConnection
            if (r1 != 0) goto L_0x011a
            mtopsdk.mtop.domain.MtopResponse r0 = r3.cacheResponse
            r8.c = r0
            defpackage.fed.a(r8)
            java.lang.String r8 = "STOP"
            return r8
        L_0x011a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fdu.b(fdf):java.lang.String");
    }

    public final String a(fdf fdf) {
        if (fff.a().b != null) {
            String key = fdf.b.getKey();
            if (fff.a().b.contains(key)) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b((String) "mtopsdk.CacheDuplexFilter", fdf.h, "apiKey in degradeApiCacheList,apiKey=".concat(String.valueOf(key)));
                }
                return "CONTINUE";
            }
        }
        MtopResponse mtopResponse = fdf.c;
        ResponseSource responseSource = fdf.j;
        if (mtopResponse.isApiSuccess() && responseSource != null) {
            Map<String, List<String>> headerFields = mtopResponse.getHeaderFields();
            fei fei = responseSource.cacheManager;
            if (fei.a(fdf.k, headerFields)) {
                responseSource.getCacheKey();
                responseSource.getCacheBlock();
                fei.a(mtopResponse);
                String cacheBlock = responseSource.getCacheBlock();
                String a2 = fcz.a(headerFields, "cache-control");
                if (!fdd.b(a2)) {
                    fde a3 = a.a;
                    String api = mtopResponse.getApi();
                    String v = mtopResponse.getV();
                    String b = fdd.b(api, v);
                    ApiCacheDo a4 = a3.a(b);
                    Context context = fdf.a.c.e;
                    if (a4 == null) {
                        ApiCacheDo apiCacheDo = new ApiCacheDo(api, v, cacheBlock);
                        fde.b(a2, apiCacheDo);
                        a3.a(b, apiCacheDo);
                        a3.a(context, fdf.h);
                    } else if (!a2.equals(a4.cacheControlHeader)) {
                        fde.b(a2, a4);
                        a3.a(context, fdf.h);
                    }
                }
            }
        }
        return "CONTINUE";
    }
}
