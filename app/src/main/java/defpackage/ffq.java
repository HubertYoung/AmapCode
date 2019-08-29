package defpackage;

import com.autonavi.gdtaojin.camera.CameraControllerManager;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import mtopsdk.common.util.TBSdkLog;

/* renamed from: ffq reason: default package */
/* compiled from: AbstractNetworkConverter */
public abstract class ffq implements ffp {
    /* access modifiers changed from: protected */
    public abstract Map<String, String> a();

    /* JADX WARNING: Removed duplicated region for block: B:82:0x01b5 A[Catch:{ Throwable -> 0x021b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final mtopsdk.network.domain.Request a(defpackage.fdf r19) {
        /*
            r18 = this;
            r1 = r19
            mtopsdk.mtop.common.MtopNetworkProp r2 = r1.d
            mtopsdk.mtop.intf.Mtop r3 = r1.a
            ffd r3 = r3.c
            java.lang.String r4 = r1.h
            mtopsdk.network.domain.Request$a r5 = new mtopsdk.network.domain.Request$a
            r5.<init>()
            r5.e = r4
            java.lang.Object r6 = r2.reqContext
            r5.m = r6
            int r6 = r2.bizId
            r5.i = r6
            int r6 = r2.connTimeout
            if (r6 <= 0) goto L_0x001f
            r5.f = r6
        L_0x001f:
            int r6 = r2.socketTimeout
            if (r6 <= 0) goto L_0x0025
            r5.g = r6
        L_0x0025:
            int r6 = r2.retryTimes
            r5.h = r6
            java.lang.String r6 = r2.reqAppKey
            r5.j = r6
            java.lang.String r6 = r2.authCode
            r5.k = r6
            mtopsdk.mtop.domain.EnvModeEnum r6 = r3.c
            r7 = 0
            if (r6 == 0) goto L_0x004c
            int[] r8 = defpackage.ffq.AnonymousClass1.a
            int r6 = r6.ordinal()
            r6 = r8[r6]
            switch(r6) {
                case 1: goto L_0x004a;
                case 2: goto L_0x0046;
                case 3: goto L_0x0042;
                case 4: goto L_0x0042;
                default: goto L_0x0041;
            }
        L_0x0041:
            goto L_0x004c
        L_0x0042:
            r6 = 2
            r5.l = r6
            goto L_0x004c
        L_0x0046:
            r6 = 1
            r5.l = r6
            goto L_0x004c
        L_0x004a:
            r5.l = r7
        L_0x004c:
            mtopsdk.mtop.domain.MethodEnum r6 = r2.method
            java.util.Map<java.lang.String, java.lang.String> r8 = r1.i
            java.util.Map<java.lang.String, java.lang.String> r9 = r2.requestHeaders
            java.util.Map<java.lang.String, java.lang.String> r10 = r3.F
            boolean r11 = r10.isEmpty()
            if (r11 != 0) goto L_0x0085
            if (r9 == 0) goto L_0x0084
            java.util.Set r10 = r10.entrySet()
            java.util.Iterator r10 = r10.iterator()
        L_0x0064:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x0085
            java.lang.Object r11 = r10.next()
            java.util.Map$Entry r11 = (java.util.Map.Entry) r11
            java.lang.Object r12 = r11.getKey()
            java.lang.String r12 = (java.lang.String) r12
            boolean r13 = r9.containsKey(r12)
            if (r13 != 0) goto L_0x0064
            java.lang.Object r11 = r11.getValue()
            r9.put(r12, r11)
            goto L_0x0064
        L_0x0084:
            r9 = r10
        L_0x0085:
            boolean r10 = r3.C
            r11 = r18
            java.util.Map r9 = r11.a(r8, r9, r10)
            java.lang.String r12 = "api"
            java.lang.Object r12 = r8.remove(r12)     // Catch:{ Throwable -> 0x021b }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Throwable -> 0x021b }
            java.lang.String r13 = "v"
            java.lang.Object r13 = r8.remove(r13)     // Catch:{ Throwable -> 0x021b }
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ Throwable -> 0x021b }
            r5.n = r12     // Catch:{ Throwable -> 0x021b }
            java.lang.String r12 = a(r1, r12, r13)     // Catch:{ Throwable -> 0x021b }
            r1.l = r12     // Catch:{ Throwable -> 0x021b }
            mtopsdk.mtop.intf.Mtop r13 = r1.a     // Catch:{ Throwable -> 0x021b }
            defpackage.fff.a()     // Catch:{ Throwable -> 0x021b }
            boolean r14 = defpackage.fff.j()     // Catch:{ Throwable -> 0x021b }
            if (r14 == 0) goto L_0x011c
            ffd r13 = r13.c     // Catch:{ Throwable -> 0x0117 }
            java.util.Map r13 = r13.a()     // Catch:{ Throwable -> 0x0117 }
            java.util.Set r13 = r13.entrySet()     // Catch:{ Throwable -> 0x0117 }
            java.util.Iterator r13 = r13.iterator()     // Catch:{ Throwable -> 0x0117 }
        L_0x00be:
            boolean r14 = r13.hasNext()     // Catch:{ Throwable -> 0x0117 }
            if (r14 == 0) goto L_0x011c
            java.lang.Object r14 = r13.next()     // Catch:{ Throwable -> 0x0117 }
            java.util.Map$Entry r14 = (java.util.Map.Entry) r14     // Catch:{ Throwable -> 0x0117 }
            java.lang.Object r15 = r14.getKey()     // Catch:{ Exception -> 0x00ef }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ Exception -> 0x00ef }
            boolean r16 = defpackage.fdd.a(r15)     // Catch:{ Exception -> 0x00ef }
            if (r16 == 0) goto L_0x00ed
            java.lang.String r7 = "mtopsdk."
            boolean r7 = r15.startsWith(r7)     // Catch:{ Exception -> 0x00ef }
            if (r7 == 0) goto L_0x00ed
            java.lang.Object r7 = r14.getValue()     // Catch:{ Exception -> 0x00ef }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x00ef }
            r10 = 8
            java.lang.String r10 = r15.substring(r10)     // Catch:{ Exception -> 0x00ef }
            r8.put(r10, r7)     // Catch:{ Exception -> 0x00ef }
        L_0x00ed:
            r7 = 0
            goto L_0x00be
        L_0x00ef:
            java.lang.String r7 = "mtopsdk.AbstractNetworkConverter"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r15 = "[addMtopSdkProperty]get mtopsdk properties error,key="
            r10.<init>(r15)     // Catch:{ Throwable -> 0x0117 }
            java.lang.Object r15 = r14.getKey()     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ Throwable -> 0x0117 }
            r10.append(r15)     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r15 = ",value="
            r10.append(r15)     // Catch:{ Throwable -> 0x0117 }
            java.lang.Object r14 = r14.getValue()     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ Throwable -> 0x0117 }
            r10.append(r14)     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0117 }
            mtopsdk.common.util.TBSdkLog.d(r7, r10)     // Catch:{ Throwable -> 0x0117 }
            goto L_0x00ed
        L_0x0117:
            r0 = move-exception
            r1 = r0
            r3 = 0
            goto L_0x021e
        L_0x011c:
            java.util.Map<java.lang.String, java.lang.String> r7 = r2.queryParameterMap     // Catch:{ Throwable -> 0x021b }
            if (r7 == 0) goto L_0x014a
            java.util.Map<java.lang.String, java.lang.String> r7 = r2.queryParameterMap     // Catch:{ Throwable -> 0x0117 }
            boolean r7 = r7.isEmpty()     // Catch:{ Throwable -> 0x0117 }
            if (r7 != 0) goto L_0x014a
            java.util.Map<java.lang.String, java.lang.String> r7 = r2.queryParameterMap     // Catch:{ Throwable -> 0x0117 }
            java.util.Set r7 = r7.entrySet()     // Catch:{ Throwable -> 0x0117 }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ Throwable -> 0x0117 }
        L_0x0132:
            boolean r10 = r7.hasNext()     // Catch:{ Throwable -> 0x0117 }
            if (r10 == 0) goto L_0x014a
            java.lang.Object r10 = r7.next()     // Catch:{ Throwable -> 0x0117 }
            java.util.Map$Entry r10 = (java.util.Map.Entry) r10     // Catch:{ Throwable -> 0x0117 }
            java.lang.Object r13 = r10.getKey()     // Catch:{ Throwable -> 0x0117 }
            java.lang.Object r10 = r10.getValue()     // Catch:{ Throwable -> 0x0117 }
            r8.put(r13, r10)     // Catch:{ Throwable -> 0x0117 }
            goto L_0x0132
        L_0x014a:
            java.util.Map<java.lang.String, java.lang.String> r3 = r3.G     // Catch:{ Throwable -> 0x021b }
            boolean r7 = r3.isEmpty()     // Catch:{ Throwable -> 0x021b }
            if (r7 != 0) goto L_0x017a
            java.util.Set r3 = r3.entrySet()     // Catch:{ Throwable -> 0x0117 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Throwable -> 0x0117 }
        L_0x015a:
            boolean r7 = r3.hasNext()     // Catch:{ Throwable -> 0x0117 }
            if (r7 == 0) goto L_0x017a
            java.lang.Object r7 = r3.next()     // Catch:{ Throwable -> 0x0117 }
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7     // Catch:{ Throwable -> 0x0117 }
            java.lang.Object r10 = r7.getKey()     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ Throwable -> 0x0117 }
            boolean r13 = r8.containsKey(r10)     // Catch:{ Throwable -> 0x0117 }
            if (r13 != 0) goto L_0x015a
            java.lang.Object r7 = r7.getValue()     // Catch:{ Throwable -> 0x0117 }
            r8.put(r10, r7)     // Catch:{ Throwable -> 0x0117 }
            goto L_0x015a
        L_0x017a:
            java.lang.String r3 = "content-type"
            java.lang.String r7 = "application/x-www-form-urlencoded;charset=UTF-8"
            r9.put(r3, r7)     // Catch:{ Throwable -> 0x021b }
            mtopsdk.mtop.domain.MethodEnum r3 = mtopsdk.mtop.domain.MethodEnum.POST     // Catch:{ Throwable -> 0x021b }
            java.lang.String r3 = r3.getMethod()     // Catch:{ Throwable -> 0x021b }
            java.lang.String r7 = r6.getMethod()     // Catch:{ Throwable -> 0x021b }
            boolean r3 = r3.equals(r7)     // Catch:{ Throwable -> 0x021b }
            if (r3 == 0) goto L_0x01cf
            java.lang.String r2 = "utf-8"
            java.lang.String r2 = defpackage.ffu.a(r8, r2)     // Catch:{ Throwable -> 0x021b }
            if (r2 == 0) goto L_0x01a7
            java.lang.String r3 = "utf-8"
            byte[] r10 = r2.getBytes(r3)     // Catch:{ Exception -> 0x01a0 }
            goto L_0x01a8
        L_0x01a0:
            java.lang.String r2 = "mtopsdk.AbstractNetworkConverter"
            java.lang.String r3 = "[createParamPostData]getPostData error"
            mtopsdk.common.util.TBSdkLog.d(r2, r3)     // Catch:{ Throwable -> 0x0117 }
        L_0x01a7:
            r10 = 0
        L_0x01a8:
            mtopsdk.network.domain.ParcelableRequestBodyImpl r2 = new mtopsdk.network.domain.ParcelableRequestBodyImpl     // Catch:{ Throwable -> 0x021b }
            java.lang.String r3 = "application/x-www-form-urlencoded;charset=UTF-8"
            r2.<init>(r3, r10)     // Catch:{ Throwable -> 0x021b }
            java.lang.String r3 = r6.getMethod()     // Catch:{ Throwable -> 0x021b }
            if (r3 == 0) goto L_0x01c6
            int r6 = r3.length()     // Catch:{ Throwable -> 0x021b }
            if (r6 != 0) goto L_0x01bc
            goto L_0x01c6
        L_0x01bc:
            r5.b = r3     // Catch:{ Throwable -> 0x021b }
            r5.d = r2     // Catch:{ Throwable -> 0x021b }
            r3 = 0
            java.net.URL r2 = defpackage.ffu.a(r12, r3)     // Catch:{ Throwable -> 0x0219 }
            goto L_0x01e7
        L_0x01c6:
            r3 = 0
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r2 = "method == null || method.length() == 0"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0219 }
            throw r1     // Catch:{ Throwable -> 0x0219 }
        L_0x01cf:
            r3 = 0
            few r6 = r1.e     // Catch:{ Throwable -> 0x0219 }
            boolean r6 = r6 instanceof defpackage.fes.a     // Catch:{ Throwable -> 0x0219 }
            if (r6 != 0) goto L_0x01e1
            boolean r2 = r2.useCache     // Catch:{ Throwable -> 0x0219 }
            if (r2 != 0) goto L_0x01e1
            java.lang.String r2 = "cache-control"
            java.lang.String r6 = "no-cache"
            r9.put(r2, r6)     // Catch:{ Throwable -> 0x0219 }
        L_0x01e1:
            r1.m = r8     // Catch:{ Throwable -> 0x0219 }
            java.net.URL r2 = defpackage.ffu.a(r12, r8)     // Catch:{ Throwable -> 0x0219 }
        L_0x01e7:
            if (r2 == 0) goto L_0x0201
            mtopsdk.mtop.util.MtopStatistics r1 = r1.g     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r6 = r2.getHost()     // Catch:{ Throwable -> 0x0219 }
            r1.r = r6     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r1 = r2.toString()     // Catch:{ Throwable -> 0x0219 }
            if (r1 != 0) goto L_0x01ff
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r2 = "url == null"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0219 }
            throw r1     // Catch:{ Throwable -> 0x0219 }
        L_0x01ff:
            r5.a = r1     // Catch:{ Throwable -> 0x0219 }
        L_0x0201:
            if (r9 == 0) goto L_0x0205
            r5.c = r9     // Catch:{ Throwable -> 0x0219 }
        L_0x0205:
            java.lang.String r1 = r5.a     // Catch:{ Throwable -> 0x0219 }
            if (r1 != 0) goto L_0x0211
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r2 = "url == null"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0219 }
            throw r1     // Catch:{ Throwable -> 0x0219 }
        L_0x0211:
            mtopsdk.network.domain.Request r10 = new mtopsdk.network.domain.Request     // Catch:{ Throwable -> 0x0219 }
            r1 = 0
            r10.<init>(r5, r1)     // Catch:{ Throwable -> 0x0219 }
            r3 = r10
            goto L_0x0225
        L_0x0219:
            r0 = move-exception
            goto L_0x021d
        L_0x021b:
            r0 = move-exception
            r3 = 0
        L_0x021d:
            r1 = r0
        L_0x021e:
            java.lang.String r2 = "mtopsdk.AbstractNetworkConverter"
            java.lang.String r5 = "[convert]convert Request failed!"
            mtopsdk.common.util.TBSdkLog.b(r2, r4, r5, r1)
        L_0x0225:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ffq.a(fdf):mtopsdk.network.domain.Request");
    }

    private Map<String, String> a(Map<String, String> map, Map<String, String> map2, boolean z) {
        Map<String, String> a = a();
        if (a == null) {
            TBSdkLog.d("mtopsdk.AbstractNetworkConverter", "[buildRequestHeaders]headerConversionMap is null,buildRequestHeaders error.");
            return map2;
        }
        int size = a.size();
        if (map2 != null) {
            size += map2.size();
        }
        HashMap hashMap = new HashMap(size);
        if (map2 != null) {
            for (Entry next : map2.entrySet()) {
                String str = (String) next.getKey();
                String str2 = (String) next.getValue();
                if (z) {
                    if (str2 != null) {
                        try {
                            str2 = URLEncoder.encode(str2, "utf-8");
                        } catch (Exception unused) {
                            StringBuilder sb = new StringBuilder("[buildRequestHeaders]urlEncode ");
                            sb.append(str);
                            sb.append("=");
                            sb.append(str2);
                            sb.append("error");
                            TBSdkLog.d("mtopsdk.AbstractNetworkConverter", sb.toString());
                        }
                    } else {
                        str2 = null;
                    }
                }
                hashMap.put(str, str2);
            }
        }
        for (Entry next2 : a.entrySet()) {
            String str3 = (String) next2.getKey();
            String remove = map.remove(next2.getValue());
            if (remove != null) {
                try {
                    hashMap.put(str3, URLEncoder.encode(remove, "utf-8"));
                } catch (Exception unused2) {
                    StringBuilder sb2 = new StringBuilder("[buildRequestHeaders]urlEncode ");
                    sb2.append(str3);
                    sb2.append("=");
                    sb2.append(remove);
                    sb2.append("error");
                    TBSdkLog.d("mtopsdk.AbstractNetworkConverter", sb2.toString());
                }
            }
        }
        String remove2 = map.remove(CameraControllerManager.MY_POILOCATION_LNG);
        String remove3 = map.remove("lat");
        if (!(remove2 == null || remove3 == null)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(remove2);
            sb3.append(",");
            sb3.append(remove3);
            try {
                hashMap.put("x-location", URLEncoder.encode(sb3.toString(), "utf-8"));
            } catch (Exception unused3) {
                StringBuilder sb4 = new StringBuilder("[buildRequestHeaders]urlEncode x-location=");
                sb4.append(sb3.toString());
                sb4.append("error");
                TBSdkLog.d("mtopsdk.AbstractNetworkConverter", sb4.toString());
            }
        }
        return hashMap;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(defpackage.fdf r5, java.lang.String r6, java.lang.String r7) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 64
            r0.<init>(r1)
            mtopsdk.mtop.intf.Mtop r1 = r5.a     // Catch:{ Exception -> 0x00b6 }
            ffd r1 = r1.c     // Catch:{ Exception -> 0x00b6 }
            mtopsdk.mtop.common.MtopNetworkProp r2 = r5.d     // Catch:{ Exception -> 0x00b6 }
            mtopsdk.mtop.domain.EnvModeEnum r3 = r1.c     // Catch:{ Exception -> 0x00b6 }
            r2.envMode = r3     // Catch:{ Exception -> 0x00b6 }
            mtopsdk.mtop.domain.ProtocolEnum r2 = r2.protocol     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r2 = r2.getProtocol()     // Catch:{ Exception -> 0x00b6 }
            r0.append(r2)     // Catch:{ Exception -> 0x00b6 }
            mtopsdk.mtop.common.MtopNetworkProp r2 = r5.d     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r3 = r2.customDomain     // Catch:{ Exception -> 0x00b6 }
            boolean r3 = defpackage.fdd.a(r3)     // Catch:{ Exception -> 0x00b6 }
            if (r3 == 0) goto L_0x0027
            java.lang.String r2 = r2.customDomain     // Catch:{ Exception -> 0x00b6 }
            goto L_0x0057
        L_0x0027:
            int[] r3 = defpackage.ffq.AnonymousClass1.a     // Catch:{ Exception -> 0x00b6 }
            mtopsdk.mtop.domain.EnvModeEnum r4 = r2.envMode     // Catch:{ Exception -> 0x00b6 }
            int r4 = r4.ordinal()     // Catch:{ Exception -> 0x00b6 }
            r3 = r3[r4]     // Catch:{ Exception -> 0x00b6 }
            switch(r3) {
                case 1: goto L_0x004b;
                case 2: goto L_0x0040;
                case 3: goto L_0x0035;
                default: goto L_0x0034;
            }     // Catch:{ Exception -> 0x00b6 }
        L_0x0034:
            goto L_0x0056
        L_0x0035:
            java.lang.String r3 = r2.customDailyDomain     // Catch:{ Exception -> 0x00b6 }
            boolean r3 = defpackage.fdd.a(r3)     // Catch:{ Exception -> 0x00b6 }
            if (r3 == 0) goto L_0x0056
            java.lang.String r2 = r2.customDailyDomain     // Catch:{ Exception -> 0x00b6 }
            goto L_0x0057
        L_0x0040:
            java.lang.String r3 = r2.customPreDomain     // Catch:{ Exception -> 0x00b6 }
            boolean r3 = defpackage.fdd.a(r3)     // Catch:{ Exception -> 0x00b6 }
            if (r3 == 0) goto L_0x0056
            java.lang.String r2 = r2.customPreDomain     // Catch:{ Exception -> 0x00b6 }
            goto L_0x0057
        L_0x004b:
            java.lang.String r3 = r2.customOnlineDomain     // Catch:{ Exception -> 0x00b6 }
            boolean r3 = defpackage.fdd.a(r3)     // Catch:{ Exception -> 0x00b6 }
            if (r3 == 0) goto L_0x0056
            java.lang.String r2 = r2.customOnlineDomain     // Catch:{ Exception -> 0x00b6 }
            goto L_0x0057
        L_0x0056:
            r2 = 0
        L_0x0057:
            boolean r3 = defpackage.fdd.a(r2)     // Catch:{ Exception -> 0x00b6 }
            if (r3 == 0) goto L_0x0061
            r0.append(r2)     // Catch:{ Exception -> 0x00b6 }
            goto L_0x0092
        L_0x0061:
            ffd$a r2 = r1.L     // Catch:{ Exception -> 0x00b6 }
            mtopsdk.mtop.common.MtopNetworkProp r3 = r5.d     // Catch:{ Exception -> 0x00b6 }
            mtopsdk.mtop.domain.EnvModeEnum r3 = r3.envMode     // Catch:{ Exception -> 0x00b6 }
            int[] r4 = defpackage.ffd.AnonymousClass1.a     // Catch:{ Exception -> 0x00b6 }
            int r3 = r3.ordinal()     // Catch:{ Exception -> 0x00b6 }
            r3 = r4[r3]     // Catch:{ Exception -> 0x00b6 }
            r4 = 0
            switch(r3) {
                case 1: goto L_0x0088;
                case 2: goto L_0x0082;
                case 3: goto L_0x007c;
                case 4: goto L_0x0076;
                default: goto L_0x0073;
            }     // Catch:{ Exception -> 0x00b6 }
        L_0x0073:
            java.lang.String[] r2 = r2.a     // Catch:{ Exception -> 0x00b6 }
            goto L_0x008d
        L_0x0076:
            java.lang.String[] r2 = r2.a     // Catch:{ Exception -> 0x00b6 }
            r3 = 3
            r2 = r2[r3]     // Catch:{ Exception -> 0x00b6 }
            goto L_0x008f
        L_0x007c:
            java.lang.String[] r2 = r2.a     // Catch:{ Exception -> 0x00b6 }
            r3 = 2
            r2 = r2[r3]     // Catch:{ Exception -> 0x00b6 }
            goto L_0x008f
        L_0x0082:
            java.lang.String[] r2 = r2.a     // Catch:{ Exception -> 0x00b6 }
            r3 = 1
            r2 = r2[r3]     // Catch:{ Exception -> 0x00b6 }
            goto L_0x008f
        L_0x0088:
            java.lang.String[] r2 = r2.a     // Catch:{ Exception -> 0x00b6 }
            r2 = r2[r4]     // Catch:{ Exception -> 0x00b6 }
            goto L_0x008f
        L_0x008d:
            r2 = r2[r4]     // Catch:{ Exception -> 0x00b6 }
        L_0x008f:
            r0.append(r2)     // Catch:{ Exception -> 0x00b6 }
        L_0x0092:
            java.lang.String r2 = "/"
            r0.append(r2)     // Catch:{ Exception -> 0x00b6 }
            mtopsdk.mtop.domain.EntranceEnum r1 = r1.d     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r1 = r1.getEntrance()     // Catch:{ Exception -> 0x00b6 }
            r0.append(r1)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r1 = "/"
            r0.append(r1)     // Catch:{ Exception -> 0x00b6 }
            r0.append(r6)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r6 = "/"
            r0.append(r6)     // Catch:{ Exception -> 0x00b6 }
            r0.append(r7)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r6 = "/"
            r0.append(r6)     // Catch:{ Exception -> 0x00b6 }
            goto L_0x00c0
        L_0x00b6:
            r6 = move-exception
            java.lang.String r7 = "mtopsdk.AbstractNetworkConverter"
            java.lang.String r5 = r5.h
            java.lang.String r1 = "[buildBaseUrl] build mtop baseUrl error."
            mtopsdk.common.util.TBSdkLog.b(r7, r5, r1, r6)
        L_0x00c0:
            java.lang.String r5 = r0.toString()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ffq.a(fdf, java.lang.String, java.lang.String):java.lang.String");
    }
}
