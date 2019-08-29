package com.alibaba.baichuan.android.trade.component;

public class b {
    private static final String a = "b";

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00d7, code lost:
        if (r7 == null) goto L_0x00e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00d9, code lost:
        r8.put("shopId", r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00e7, code lost:
        return com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.getInstance().jumpShop(r4, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00e8, code lost:
        com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.e(a, "startNativeTaobao: shopid is null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00f0, code lost:
        if (r6 == null) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00f2, code lost:
        r8.put("url", r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0100, code lost:
        return com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.getInstance().jumpTBURI(r4, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0101, code lost:
        com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.e(a, "startNativeTaobao: shwourl is null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x010a, code lost:
        return false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00c7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.content.Context r4, com.alibaba.baichuan.android.trade.model.ApplinkOpenType r5, java.lang.String r6, java.lang.String r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, java.util.Map r11) {
        /*
            if (r11 != 0) goto L_0x0007
            java.util.HashMap r11 = new java.util.HashMap
            r11.<init>()
        L_0x0007:
            r0 = 0
            java.util.Set r1 = r11.entrySet()     // Catch:{ JSONException -> 0x0040 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ JSONException -> 0x0040 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0040 }
            r2.<init>()     // Catch:{ JSONException -> 0x0040 }
        L_0x0015:
            boolean r0 = r1.hasNext()     // Catch:{ JSONException -> 0x003e }
            if (r0 == 0) goto L_0x002f
            java.lang.Object r0 = r1.next()     // Catch:{ JSONException -> 0x003e }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ JSONException -> 0x003e }
            java.lang.Object r3 = r0.getKey()     // Catch:{ JSONException -> 0x003e }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ JSONException -> 0x003e }
            java.lang.Object r0 = r0.getValue()     // Catch:{ JSONException -> 0x003e }
            r2.put(r3, r0)     // Catch:{ JSONException -> 0x003e }
            goto L_0x0015
        L_0x002f:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x003e }
            r0.<init>()     // Catch:{ JSONException -> 0x003e }
            java.lang.String r1 = "params"
            java.lang.String r3 = r2.toString()     // Catch:{ JSONException -> 0x003e }
            r0.put(r1, r3)     // Catch:{ JSONException -> 0x003e }
            goto L_0x0046
        L_0x003e:
            r0 = move-exception
            goto L_0x0043
        L_0x0040:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0043:
            r0.printStackTrace()
        L_0x0046:
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.lang.String r1 = "backURL"
            r0.put(r1, r10)
            java.lang.String r1 = "pid"
            r0.put(r1, r9)
            java.lang.String r9 = "tag"
            r0.put(r9, r8)
            java.lang.String r8 = "TTID"
            com.alibaba.baichuan.android.trade.config.AlibcConfig r9 = com.alibaba.baichuan.android.trade.config.AlibcConfig.getInstance()
            java.lang.String r9 = r9.getWebTTID()
            r0.put(r8, r9)
            java.lang.String r8 = "source"
            java.lang.String r9 = "bc"
            r0.put(r8, r9)
            java.lang.String r8 = "utdid"
            java.lang.String r9 = com.alibaba.baichuan.android.trade.AlibcContext.getUtdid()
            r0.put(r8, r9)
            com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink r8 = com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.getInstance()
            r8.setOpenParam(r0)
            java.util.HashMap r8 = new java.util.HashMap
            r8.<init>()
            java.lang.String r9 = "backURL"
            r8.put(r9, r10)
            java.lang.String r9 = "addParams"
            r8.put(r9, r11)
            java.lang.String r9 = "jsonParams"
            r8.put(r9, r2)
            java.lang.String r9 = a
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r0 = "appType is "
            r10.<init>(r0)
            java.lang.String r0 = "appType"
            java.lang.Object r0 = r11.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            r10.append(r0)
            java.lang.String r10 = r10.toString()
            com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.e(r9, r10)
            java.lang.String r9 = "appType"
            java.lang.String r10 = "appType"
            java.lang.Object r10 = r11.get(r10)
            r8.put(r9, r10)
            int[] r9 = com.alibaba.baichuan.android.trade.component.b.AnonymousClass1.a
            int r5 = r5.ordinal()
            r5 = r9[r5]
            switch(r5) {
                case 1: goto L_0x00c7;
                case 2: goto L_0x00d7;
                case 3: goto L_0x00f0;
                default: goto L_0x00c6;
            }
        L_0x00c6:
            goto L_0x0109
        L_0x00c7:
            if (r7 == 0) goto L_0x00d7
            java.lang.String r5 = "itemId"
            r8.put(r5, r7)
            com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink r5 = com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.getInstance()
            boolean r4 = r5.jumpDetail(r4, r8)
            return r4
        L_0x00d7:
            if (r7 == 0) goto L_0x00e8
            java.lang.String r5 = "shopId"
            r8.put(r5, r7)
            com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink r5 = com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.getInstance()
            boolean r4 = r5.jumpShop(r4, r8)
            return r4
        L_0x00e8:
            java.lang.String r5 = a
            java.lang.String r7 = "startNativeTaobao: shopid is null"
            com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.e(r5, r7)
        L_0x00f0:
            if (r6 == 0) goto L_0x0101
            java.lang.String r5 = "url"
            r8.put(r5, r6)
            com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink r5 = com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.getInstance()
            boolean r4 = r5.jumpTBURI(r4, r8)
            return r4
        L_0x0101:
            java.lang.String r4 = a
            java.lang.String r5 = "startNativeTaobao: shwourl is null"
            com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.e(r4, r5)
        L_0x0109:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.component.b.a(android.content.Context, com.alibaba.baichuan.android.trade.model.ApplinkOpenType, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map):boolean");
    }
}
