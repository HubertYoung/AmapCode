package com.mpaas.nebula.provider;

import com.alipay.mobile.common.transport.utils.ZURLEncodedUtil;
import com.alipay.mobile.nebula.provider.NebulaAdapterProvider;

public class NebulaAdapterProviderImpl implements NebulaAdapterProvider {
    private String a = "NebulaAdapterProviderImpl";

    /* JADX WARNING: type inference failed for: r25v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r21v1 */
    /* JADX WARNING: type inference failed for: r29v0 */
    /* JADX WARNING: type inference failed for: r29v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r29v2 */
    /* JADX WARNING: type inference failed for: r1v4, types: [java.io.InputStream] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.nebula.process.H5HttpRequestResult httpRequest(java.lang.String r36, java.lang.String r37, java.util.Map<java.lang.String, java.lang.String> r38, byte[] r39, long r40, java.lang.String r42, java.lang.String r43, boolean r44, com.alipay.mobile.h5container.api.H5Page r45) {
        /*
            r35 = this;
            com.alipay.mobile.common.transport.h5.H5HttpUrlRequest r23 = new com.alipay.mobile.common.transport.h5.H5HttpUrlRequest     // Catch:{ Throwable -> 0x0060 }
            r0 = r23
            r1 = r36
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0060 }
            r0 = r23
            r1 = r37
            r0.setRequestMethod(r1)     // Catch:{ Throwable -> 0x0060 }
            java.util.Set r5 = r38.keySet()     // Catch:{ Throwable -> 0x0060 }
            java.util.Iterator r6 = r5.iterator()     // Catch:{ Throwable -> 0x0060 }
        L_0x0018:
            boolean r5 = r6.hasNext()     // Catch:{ Throwable -> 0x0060 }
            if (r5 == 0) goto L_0x00a7
            java.lang.Object r28 = r6.next()     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r28 = (java.lang.String) r28     // Catch:{ Throwable -> 0x0060 }
            r0 = r38
            r1 = r28
            java.lang.Object r5 = r0.get(r1)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r31 = a(r5)     // Catch:{ Throwable -> 0x0060 }
            r0 = r23
            r1 = r28
            r2 = r31
            r0.addHeader(r1, r2)     // Catch:{ Throwable -> 0x0060 }
            r0 = r35
            java.lang.String r5 = r0.a     // Catch:{ Throwable -> 0x0060 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r8 = "request headers "
            r7.<init>(r8)     // Catch:{ Throwable -> 0x0060 }
            r0 = r28
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r8 = " "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x0060 }
            r0 = r31
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0060 }
            com.alipay.mobile.nebula.util.H5Log.d(r5, r7)     // Catch:{ Throwable -> 0x0060 }
            goto L_0x0018
        L_0x0060:
            r18 = move-exception
            java.lang.Class<com.alipay.mobile.nebula.provider.H5LogProvider> r5 = com.alipay.mobile.nebula.provider.H5LogProvider.class
            java.lang.String r5 = r5.getName()
            java.lang.Object r4 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r5)
            com.alipay.mobile.nebula.provider.H5LogProvider r4 = (com.alipay.mobile.nebula.provider.H5LogProvider) r4
            if (r4 == 0) goto L_0x007d
            java.lang.String r5 = "h5_httpRequest_exception"
            java.lang.String r7 = "httpRequest请求异常"
            r8 = 0
            java.lang.String r9 = r18.toString()
            r6 = r36
            r4.log(r5, r6, r7, r8, r9)
        L_0x007d:
            r0 = r35
            java.lang.String r5 = r0.a
            r0 = r18
            com.alipay.mobile.nebula.util.H5Log.e(r5, r0)
            com.alipay.mobile.nebula.process.H5HttpRequestResult r22 = new com.alipay.mobile.nebula.process.H5HttpRequestResult
            r22.<init>()
            r5 = 12
            r0 = r22
            r0.responseStatues = r5
            com.alibaba.fastjson.JSONArray r5 = new com.alibaba.fastjson.JSONArray
            r5.<init>()
            r0 = r22
            r0.responseHeader = r5
            java.lang.String r5 = ""
            r0 = r22
            r0.responseContext = r5
            r5 = 12
            r0 = r22
            r0.error = r5
        L_0x00a6:
            return r22
        L_0x00a7:
            java.lang.String r5 = "su584channelapplet"
            java.lang.String r6 = "Y"
            r0 = r23
            r0.addHeader(r5, r6)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r5 = "h5_app_type"
            java.lang.String r6 = "mini_app"
            r0 = r23
            r0.addTags(r5, r6)     // Catch:{ Throwable -> 0x0060 }
            r0 = r23
            r1 = r39
            r0.setReqData(r1)     // Catch:{ Throwable -> 0x0060 }
            r5 = 2
            r0 = r23
            r0.linkType = r5     // Catch:{ Throwable -> 0x0060 }
            long r32 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0060 }
            com.alipay.mobile.common.transport.h5.H5NetworkManager r5 = new com.alipay.mobile.common.transport.h5.H5NetworkManager     // Catch:{ Throwable -> 0x0060 }
            android.content.Context r6 = com.alipay.mobile.nebula.util.H5Utils.getContext()     // Catch:{ Throwable -> 0x0060 }
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0060 }
            r0 = r23
            java.util.concurrent.Future r19 = r5.enqueue(r0)     // Catch:{ Throwable -> 0x0060 }
            r24 = 0
            r6 = 0
            int r5 = (r40 > r6 ? 1 : (r40 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x0249
            r6 = 30000(0x7530, double:1.4822E-319)
            int r5 = (r40 > r6 ? 1 : (r40 == r6 ? 0 : -1))
            if (r5 == 0) goto L_0x0249
            r0 = r35
            java.lang.String r5 = r0.a     // Catch:{ Throwable -> 0x023f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x023f }
            java.lang.String r7 = "timeout "
            r6.<init>(r7)     // Catch:{ Throwable -> 0x023f }
            r0 = r40
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Throwable -> 0x023f }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x023f }
            com.alipay.mobile.nebula.util.H5Log.d(r5, r6)     // Catch:{ Throwable -> 0x023f }
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x023f }
            r0 = r19
            r1 = r40
            java.lang.Object r5 = r0.get(r1, r5)     // Catch:{ Throwable -> 0x023f }
            r0 = r5
            com.alipay.mobile.common.transport.h5.H5HttpUrlResponse r0 = (com.alipay.mobile.common.transport.h5.H5HttpUrlResponse) r0     // Catch:{ Throwable -> 0x023f }
            r24 = r0
        L_0x010d:
            r0 = r35
            java.lang.String r5 = r0.a     // Catch:{ Throwable -> 0x0060 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r7 = "httpRequest timeCost h5HttpUrlRequest "
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0060 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0060 }
            long r8 = r8 - r32
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r7 = " "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x0060 }
            r0 = r36
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0060 }
            com.alipay.mobile.nebula.util.H5Log.d(r5, r6)     // Catch:{ Throwable -> 0x0060 }
            com.alibaba.fastjson.JSONArray r26 = new com.alibaba.fastjson.JSONArray     // Catch:{ Throwable -> 0x0060 }
            r26.<init>()     // Catch:{ Throwable -> 0x0060 }
            r20 = 0
            if (r24 == 0) goto L_0x0251
            com.alipay.mobile.common.transport.http.HttpUrlHeader r5 = r24.getHeader()     // Catch:{ Throwable -> 0x0060 }
            if (r5 == 0) goto L_0x0251
            com.alipay.mobile.common.transport.http.HttpUrlHeader r5 = r24.getHeader()     // Catch:{ Throwable -> 0x0060 }
            java.util.Map r30 = r5.toMultimap()     // Catch:{ Throwable -> 0x0060 }
            java.util.Set r5 = r30.keySet()     // Catch:{ Throwable -> 0x0060 }
            java.util.Iterator r6 = r5.iterator()     // Catch:{ Throwable -> 0x0060 }
        L_0x0154:
            boolean r5 = r6.hasNext()     // Catch:{ Throwable -> 0x0060 }
            if (r5 == 0) goto L_0x0251
            java.lang.Object r28 = r6.next()     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r28 = (java.lang.String) r28     // Catch:{ Throwable -> 0x0060 }
            r0 = r30
            r1 = r28
            java.lang.Object r34 = r0.get(r1)     // Catch:{ Throwable -> 0x0060 }
            java.util.List r34 = (java.util.List) r34     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r5 = "Content-Encoding"
            r0 = r28
            boolean r13 = r5.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x0060 }
            java.util.Iterator r7 = r34.iterator()     // Catch:{ Throwable -> 0x0060 }
        L_0x0176:
            boolean r5 = r7.hasNext()     // Catch:{ Throwable -> 0x0060 }
            if (r5 == 0) goto L_0x0154
            java.lang.Object r31 = r7.next()     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r31 = (java.lang.String) r31     // Catch:{ Throwable -> 0x0060 }
            r0 = r35
            java.lang.String r5 = r0.a     // Catch:{ Throwable -> 0x0060 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r9 = "handleResponse headers "
            r8.<init>(r9)     // Catch:{ Throwable -> 0x0060 }
            r0 = r28
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r9 = " "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0060 }
            r0 = r31
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0060 }
            com.alipay.mobile.nebula.util.H5Log.d(r5, r8)     // Catch:{ Throwable -> 0x0060 }
            if (r13 == 0) goto L_0x01b4
            java.lang.String r5 = "gzip"
            r0 = r31
            boolean r5 = r5.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x0060 }
            if (r5 == 0) goto L_0x01b4
            r20 = 1
        L_0x01b4:
            com.alibaba.fastjson.JSONObject r27 = new com.alibaba.fastjson.JSONObject     // Catch:{ Throwable -> 0x0060 }
            r5 = 1
            r0 = r27
            r0.<init>(r5)     // Catch:{ Throwable -> 0x0060 }
            r0 = r27
            r1 = r28
            r2 = r31
            r0.put(r1, r2)     // Catch:{ Throwable -> 0x0060 }
            r26.add(r27)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r5 = "set-cookie"
            r0 = r28
            boolean r5 = r0.equalsIgnoreCase(r5)     // Catch:{ Throwable -> 0x0231 }
            if (r5 == 0) goto L_0x0176
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0231 }
            r0 = r36
            r1 = r31
            com.alipay.mobile.nebula.util.H5CookieUtil.setCookie(r0, r1)     // Catch:{ Throwable -> 0x0231 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0231 }
            long r16 = r8 - r14
            r0 = r35
            java.lang.String r5 = r0.a     // Catch:{ Throwable -> 0x0231 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0231 }
            java.lang.String r9 = "httpRequest timeCost setCookie "
            r8.<init>(r9)     // Catch:{ Throwable -> 0x0231 }
            r0 = r16
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x0231 }
            java.lang.String r9 = " "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0231 }
            r0 = r36
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x0231 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0231 }
            com.alipay.mobile.nebula.util.H5Log.d(r5, r8)     // Catch:{ Throwable -> 0x0231 }
            java.lang.Class<com.alipay.mobile.nebula.provider.H5LogProvider> r5 = com.alipay.mobile.nebula.provider.H5LogProvider.class
            java.lang.String r5 = r5.getName()     // Catch:{ Throwable -> 0x0231 }
            java.lang.Object r5 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r5)     // Catch:{ Throwable -> 0x0231 }
            com.alipay.mobile.nebula.provider.H5LogProvider r5 = (com.alipay.mobile.nebula.provider.H5LogProvider) r5     // Catch:{ Throwable -> 0x0231 }
            if (r5 == 0) goto L_0x0176
            if (r45 == 0) goto L_0x0176
            com.alipay.mobile.h5container.api.H5PageData r5 = r45.getPageData()     // Catch:{ Throwable -> 0x0231 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0231 }
            java.lang.String r9 = "^setCookie="
            r8.<init>(r9)     // Catch:{ Throwable -> 0x0231 }
            r0 = r16
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x0231 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0231 }
            r5.appendJsApiPerExtra(r8)     // Catch:{ Throwable -> 0x0231 }
            goto L_0x0176
        L_0x0231:
            r18 = move-exception
            r0 = r35
            java.lang.String r5 = r0.a     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r8 = "exception detail"
            r0 = r18
            com.alipay.mobile.nebula.util.H5Log.e(r5, r8, r0)     // Catch:{ Throwable -> 0x0060 }
            goto L_0x0176
        L_0x023f:
            r5 = move-exception
            java.lang.String r5 = "TimeoutException"
            r0 = r23
            r0.cancel(r5)     // Catch:{ Throwable -> 0x0060 }
            goto L_0x010d
        L_0x0249:
            java.lang.Object r24 = r19.get()     // Catch:{ Throwable -> 0x0060 }
            com.alipay.mobile.common.transport.h5.H5HttpUrlResponse r24 = (com.alipay.mobile.common.transport.h5.H5HttpUrlResponse) r24     // Catch:{ Throwable -> 0x0060 }
            goto L_0x010d
        L_0x0251:
            r21 = 0
            java.io.InputStream r25 = r24.getInputStream()     // Catch:{ Throwable -> 0x0060 }
            if (r20 == 0) goto L_0x0262
            java.util.zip.GZIPInputStream r21 = new java.util.zip.GZIPInputStream     // Catch:{ Throwable -> 0x0060 }
            r0 = r21
            r1 = r25
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0060 }
        L_0x0262:
            if (r21 == 0) goto L_0x0297
            r29 = r21
        L_0x0266:
            int r11 = r24.getCode()     // Catch:{ Throwable -> 0x0060 }
            byte[] r10 = com.alipay.mobile.nebula.util.H5Utils.readBytes(r29)     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r5 = "base64"
            r0 = r42
            boolean r5 = r5.equals(r0)     // Catch:{ Throwable -> 0x0060 }
            if (r5 == 0) goto L_0x029a
            r5 = 2
            java.lang.String r12 = android.util.Base64.encodeToString(r10, r5)     // Catch:{ Throwable -> 0x0060 }
        L_0x027d:
            com.alipay.mobile.nebula.process.H5HttpRequestResult r22 = new com.alipay.mobile.nebula.process.H5HttpRequestResult     // Catch:{ Throwable -> 0x0060 }
            r22.<init>()     // Catch:{ Throwable -> 0x0060 }
            r0 = r22
            r0.responseStatues = r11     // Catch:{ Throwable -> 0x0060 }
            r0 = r26
            r1 = r22
            r1.responseHeader = r0     // Catch:{ Throwable -> 0x0060 }
            r0 = r22
            r0.responseContext = r12     // Catch:{ Throwable -> 0x0060 }
            r5 = 0
            r0 = r22
            r0.error = r5     // Catch:{ Throwable -> 0x0060 }
            goto L_0x00a6
        L_0x0297:
            r29 = r25
            goto L_0x0266
        L_0x029a:
            boolean r5 = android.text.TextUtils.isEmpty(r43)     // Catch:{ Throwable -> 0x0060 }
            if (r5 != 0) goto L_0x02a8
            java.lang.String r12 = new java.lang.String     // Catch:{ Throwable -> 0x0060 }
            r0 = r43
            r12.<init>(r10, r0)     // Catch:{ Throwable -> 0x0060 }
            goto L_0x027d
        L_0x02a8:
            java.lang.String r12 = new java.lang.String     // Catch:{ Throwable -> 0x0060 }
            r12.<init>(r10)     // Catch:{ Throwable -> 0x0060 }
            goto L_0x027d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mpaas.nebula.provider.NebulaAdapterProviderImpl.httpRequest(java.lang.String, java.lang.String, java.util.Map, byte[], long, java.lang.String, java.lang.String, boolean, com.alipay.mobile.h5container.api.H5Page):com.alipay.mobile.nebula.process.H5HttpRequestResult");
    }

    private static String a(String netValue) {
        return ZURLEncodedUtil.urlEncode(netValue);
    }
}
