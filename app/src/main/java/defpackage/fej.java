package defpackage;

import anetwork.network.cache.RpcCache;
import anetwork.network.cache.RpcCache.CacheStatus;
import java.util.List;
import java.util.Map;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.cache.domain.ApiCacheDo;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.global.SDKUtils;
import mtopsdk.network.domain.Request;

/* renamed from: fej reason: default package */
/* compiled from: CacheManagerImpl */
public final class fej implements fei {
    private ep a = null;

    public fej(ep epVar) {
        this.a = epVar;
    }

    public final boolean a(Request request) {
        fff.a();
        if (!fff.i()) {
            TBSdkLog.b((String) "mtopsdk.CacheManagerImpl", request.e, (String) "[isNeedReadCache]GlobalCacheSwitch=false,Don't read local cache.");
            return false;
        } else if (request != null && "GET".equalsIgnoreCase(request.b) && !"no-cache".equalsIgnoreCase(request.c.get("cache-control"))) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean a(Request request, Map<String, List<String>> map) {
        fff.a();
        if (!fff.i()) {
            TBSdkLog.b((String) "mtopsdk.CacheManagerImpl", request.e, (String) "[isNeedWriteCache]GlobalCacheSwitch=false,Don't write local cache.");
            return false;
        } else if (!"GET".equalsIgnoreCase(request.b) || map == null) {
            return false;
        } else {
            String a2 = fcz.a(map, "cache-control");
            if (a2 != null && a2.contains("no-cache")) {
                return false;
            }
            String a3 = fcz.a(map, "last-modified");
            String a4 = fcz.a(map, "MTOP-XETag");
            if (a4 == null) {
                a4 = fcz.a(map, "etag");
            }
            if (a2 == null && a3 == null && a4 == null) {
                return false;
            }
            return true;
        }
    }

    public final RpcCache a(String str) {
        if (this.a == null) {
            return null;
        }
        RpcCache a2 = this.a.a();
        if (!(a2 == null || a2 == null)) {
            if (a2.body == null) {
                a2.cacheStatus = CacheStatus.TIMEOUT;
            } else if (a2.lastModified == null && a2.etag == null) {
                if (a2.offline) {
                    a2.cacheStatus = CacheStatus.NEED_UPDATE;
                } else {
                    a2.cacheStatus = CacheStatus.TIMEOUT;
                }
            } else if (fdd.a(a2.lastModified)) {
                long j = a2.cacheCreateTime;
                long j2 = a2.maxAge;
                long correctionTime = SDKUtils.getCorrectionTime();
                if (correctionTime >= j && correctionTime <= j + j2) {
                    a2.cacheStatus = CacheStatus.FRESH;
                } else if (a2.offline) {
                    a2.cacheStatus = CacheStatus.NEED_UPDATE;
                } else {
                    a2.cacheStatus = CacheStatus.TIMEOUT;
                }
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb = new StringBuilder(128);
                    sb.append("[handleCacheValidation]cacheStatus=");
                    sb.append(a2.cacheStatus);
                    sb.append(";lastModifiedStr=");
                    sb.append(a2.lastModified);
                    sb.append(";lastModified=");
                    sb.append(j);
                    sb.append(";maxAge=");
                    sb.append(j2);
                    sb.append(";currentTime=");
                    sb.append(correctionTime);
                    sb.append(";t_offset=");
                    sb.append(fgy.a());
                    TBSdkLog.b((String) "mtopsdk.CacheManagerImpl", str, sb.toString());
                }
            } else if (fdd.a(a2.etag)) {
                a2.cacheStatus = CacheStatus.NEED_UPDATE;
            }
        }
        return a2;
    }

    public final boolean a(MtopResponse mtopResponse) {
        if (this.a == null) {
            return false;
        }
        RpcCache rpcCache = new RpcCache();
        rpcCache.header = mtopResponse.getHeaderFields();
        rpcCache.body = mtopResponse.getBytedata();
        a(mtopResponse.getMtopStat() != null ? mtopResponse.getMtopStat().E : "", rpcCache);
        return this.a.b();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0053, code lost:
        if (r5.equals("NONE") == false) goto L_0x0074;
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x012e A[FALL_THROUGH] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0134 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0135 A[SYNTHETIC, Splitter:B:67:0x0135] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(defpackage.fdf r14) {
        /*
            r13 = this;
            r0 = 0
            if (r14 != 0) goto L_0x0004
            return r0
        L_0x0004:
            mtopsdk.mtop.domain.MtopRequest r1 = r14.b
            mtopsdk.mtop.common.MtopNetworkProp r2 = r14.d
            java.lang.String r3 = r14.l
            java.util.Map<java.lang.String, java.lang.String> r4 = r14.m
            if (r1 == 0) goto L_0x0168
            if (r2 == 0) goto L_0x0168
            if (r3 == 0) goto L_0x0168
            if (r4 != 0) goto L_0x0016
            goto L_0x0168
        L_0x0016:
            java.lang.String r5 = "ALL"
            fde r6 = defpackage.fde.a.a
            java.lang.String r7 = r1.getKey()
            mtopsdk.mtop.cache.domain.ApiCacheDo r6 = r6.a(r7)
            r7 = 1
            if (r6 == 0) goto L_0x0031
            boolean r5 = r6.privateScope
            java.lang.String r8 = r6.cacheKeyType
            java.util.List<java.lang.String> r6 = r6.cacheKeyItems
            r12 = r8
            r8 = r5
            r5 = r12
            goto L_0x0033
        L_0x0031:
            r6 = r0
            r8 = 1
        L_0x0033:
            r9 = -1
            int r10 = r5.hashCode()
            r11 = 64897(0xfd81, float:9.094E-41)
            if (r10 == r11) goto L_0x006a
            r11 = 69104(0x10df0, float:9.6835E-41)
            if (r10 == r11) goto L_0x0060
            r11 = 72638(0x11bbe, float:1.01788E-40)
            if (r10 == r11) goto L_0x0056
            r11 = 2402104(0x24a738, float:3.366065E-39)
            if (r10 == r11) goto L_0x004d
            goto L_0x0074
        L_0x004d:
            java.lang.String r10 = "NONE"
            boolean r5 = r5.equals(r10)
            if (r5 == 0) goto L_0x0074
            goto L_0x0075
        L_0x0056:
            java.lang.String r7 = "INC"
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L_0x0074
            r7 = 3
            goto L_0x0075
        L_0x0060:
            java.lang.String r7 = "EXC"
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L_0x0074
            r7 = 2
            goto L_0x0075
        L_0x006a:
            java.lang.String r7 = "ALL"
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L_0x0074
            r7 = 0
            goto L_0x0075
        L_0x0074:
            r7 = -1
        L_0x0075:
            switch(r7) {
                case 0: goto L_0x0129;
                case 1: goto L_0x0124;
                case 2: goto L_0x00b2;
                case 3: goto L_0x007a;
                default: goto L_0x0078;
            }
        L_0x0078:
            goto L_0x012e
        L_0x007a:
            if (r6 != 0) goto L_0x0082
            java.net.URL r1 = defpackage.ffu.a(r3, r0)
            goto L_0x0132
        L_0x0082:
            java.util.Map<java.lang.String, java.lang.String> r4 = r1.dataParams
            if (r4 == 0) goto L_0x00ab
            java.util.HashMap r4 = new java.util.HashMap
            java.util.Map<java.lang.String, java.lang.String> r5 = r1.dataParams
            int r5 = r5.size()
            r4.<init>(r5)
            java.util.Iterator r5 = r6.iterator()
        L_0x0095:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x00ac
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            java.util.Map<java.lang.String, java.lang.String> r7 = r1.dataParams
            java.lang.Object r7 = r7.get(r6)
            r4.put(r6, r7)
            goto L_0x0095
        L_0x00ab:
            r4 = r0
        L_0x00ac:
            java.net.URL r1 = defpackage.ffu.a(r3, r4)
            goto L_0x0132
        L_0x00b2:
            if (r6 != 0) goto L_0x00b6
            java.util.List<java.lang.String> r6 = r2.cacheKeyBlackList
        L_0x00b6:
            if (r6 == 0) goto L_0x012e
            java.util.Map<java.lang.String, java.lang.String> r5 = r1.dataParams
            if (r5 == 0) goto L_0x00d2
            java.util.Iterator r5 = r6.iterator()
        L_0x00c0:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x00d2
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            java.util.Map<java.lang.String, java.lang.String> r7 = r1.dataParams
            r7.remove(r6)
            goto L_0x00c0
        L_0x00d2:
            java.util.Map<java.lang.String, java.lang.String> r1 = r1.dataParams
            java.lang.String r1 = defpackage.ffz.a(r1)
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            java.util.Set r4 = r4.entrySet()
            java.util.Iterator r4 = r4.iterator()
        L_0x00e5:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L_0x011f
            java.lang.Object r6 = r4.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.String r7 = "data"
            java.lang.Object r9 = r6.getKey()
            boolean r7 = r7.equals(r9)
            if (r7 == 0) goto L_0x0105
            java.lang.Object r6 = r6.getKey()
            r5.put(r6, r1)
            goto L_0x00e5
        L_0x0105:
            java.lang.String r7 = "wua"
            java.lang.Object r9 = r6.getKey()
            java.lang.String r9 = (java.lang.String) r9
            boolean r7 = r7.equalsIgnoreCase(r9)
            if (r7 != 0) goto L_0x00e5
            java.lang.Object r7 = r6.getKey()
            java.lang.Object r6 = r6.getValue()
            r5.put(r7, r6)
            goto L_0x00e5
        L_0x011f:
            java.net.URL r1 = defpackage.ffu.a(r3, r5)
            goto L_0x0132
        L_0x0124:
            java.net.URL r1 = defpackage.ffu.a(r3, r0)
            goto L_0x0132
        L_0x0129:
            java.net.URL r1 = defpackage.ffu.a(r3, r4)
            goto L_0x0132
        L_0x012e:
            java.net.URL r1 = defpackage.ffu.a(r3, r4)
        L_0x0132:
            if (r1 != 0) goto L_0x0135
            return r0
        L_0x0135:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x015d }
            java.lang.String r1 = r1.getFile()     // Catch:{ Exception -> 0x015d }
            r3.<init>(r1)     // Catch:{ Exception -> 0x015d }
            if (r8 == 0) goto L_0x014d
            mtopsdk.mtop.intf.Mtop r1 = r14.a     // Catch:{ Exception -> 0x015d }
            java.lang.String r4 = r2.userInfo     // Catch:{ Exception -> 0x015d }
            java.lang.String r1 = r1.d(r4)     // Catch:{ Exception -> 0x015d }
            if (r1 == 0) goto L_0x014d
            r3.append(r1)     // Catch:{ Exception -> 0x015d }
        L_0x014d:
            java.lang.String r1 = r2.ttid     // Catch:{ Exception -> 0x015d }
            boolean r2 = defpackage.fdd.a(r1)     // Catch:{ Exception -> 0x015d }
            if (r2 == 0) goto L_0x0158
            r3.append(r1)     // Catch:{ Exception -> 0x015d }
        L_0x0158:
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x015d }
            return r1
        L_0x015d:
            r1 = move-exception
            java.lang.String r2 = "mtopsdk.CacheManagerImpl"
            java.lang.String r14 = r14.h
            java.lang.String r3 = "[getCacheKey] getCacheKey error."
            mtopsdk.common.util.TBSdkLog.b(r2, r14, r3, r1)
            return r0
        L_0x0168:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fej.a(fdf):java.lang.String");
    }

    public final String b(String str) {
        if (fdd.b(str)) {
            return "";
        }
        String str2 = "";
        ApiCacheDo a2 = a.a.a(str);
        if (!(a2 == null || a2.blockName == null)) {
            str2 = a2.blockName;
        }
        return str2;
    }

    private static RpcCache a(String str, RpcCache rpcCache) {
        if (rpcCache.header == null) {
            return rpcCache;
        }
        Map<String, List<String>> map = rpcCache.header;
        String a2 = fcz.a(map, "last-modified");
        String a3 = fcz.a(map, "cache-control");
        String a4 = fcz.a(map, "MTOP-XETag");
        if (a4 == null) {
            a4 = fcz.a(map, "etag");
        }
        if (a3 == null && a2 == null && a4 == null) {
            return rpcCache;
        }
        if (fdd.a(a3) && fdd.a(a2)) {
            rpcCache.lastModified = a2;
            rpcCache.cacheCreateTime = fdb.a(a2);
            String[] split = a3.split(",");
            if (split != null) {
                for (String str2 : split) {
                    try {
                        if (str2.contains("max-age=")) {
                            rpcCache.maxAge = Long.parseLong(str2.substring(8));
                        } else if ("of=on".equalsIgnoreCase(str2)) {
                            rpcCache.offline = true;
                        }
                    } catch (Exception unused) {
                        TBSdkLog.c("mtopsdk.CacheManagerImpl", str, "[handleResponseCacheFlag] parse cacheControlStr error.".concat(String.valueOf(a3)));
                    }
                }
            }
        }
        if (fdd.a(a4)) {
            rpcCache.etag = a4;
        }
        return rpcCache;
    }
}
