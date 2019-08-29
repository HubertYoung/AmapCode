package com.alibaba.baichuan.android.jsbridge.a;

import com.alibaba.baichuan.android.jsbridge.plugin.AlibcApiPlugin;

public class a extends AlibcApiPlugin {
    public static String a = "AliBCAppLink";

    /* JADX WARNING: Removed duplicated region for block: B:18:0x003b A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean execute(java.lang.String r4, java.lang.String r5, com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext r6) {
        /*
            r3 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            r1 = 0
            if (r0 != 0) goto L_0x0096
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x0096
            if (r6 != 0) goto L_0x0011
            goto L_0x0096
        L_0x0011:
            r4 = 0
            java.lang.Object r5 = com.alibaba.fastjson.JSON.parse(r5)     // Catch:{ Exception -> 0x0033 }
            java.util.Map r5 = com.alibaba.baichuan.android.trade.utils.StringUtils.obj2MapObject(r5)     // Catch:{ Exception -> 0x0033 }
            java.lang.String r0 = "type"
            java.lang.Object r0 = r5.get(r0)     // Catch:{ Exception -> 0x0033 }
            java.lang.String r0 = com.alibaba.baichuan.android.trade.utils.StringUtils.obj2String(r0)     // Catch:{ Exception -> 0x0033 }
            java.lang.String r2 = "params"
            java.lang.Object r5 = r5.get(r2)     // Catch:{ Exception -> 0x0031 }
            java.util.Map r5 = com.alibaba.baichuan.android.trade.utils.StringUtils.obj2MapObject(r5)     // Catch:{ Exception -> 0x0031 }
            r4 = r5
            goto L_0x0038
        L_0x0031:
            r5 = move-exception
            goto L_0x0035
        L_0x0033:
            r5 = move-exception
            r0 = r4
        L_0x0035:
            r5.printStackTrace()
        L_0x0038:
            r5 = 1
            if (r0 == 0) goto L_0x0086
            if (r4 != 0) goto L_0x003e
            goto L_0x0086
        L_0x003e:
            java.lang.String r2 = "shop"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0052
            com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink r0 = com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.getInstance()
            android.content.Context r1 = r3.b
            boolean r1 = r0.jumpShop(r1, r4)
            goto L_0x0078
        L_0x0052:
            java.lang.String r2 = "detail"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0065
            com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink r0 = com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.getInstance()
            android.content.Context r1 = r3.b
            boolean r1 = r0.jumpDetail(r1, r4)
            goto L_0x0078
        L_0x0065:
            java.lang.String r2 = "url"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0078
            com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink r0 = com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.getInstance()
            android.content.Context r1 = r3.b
            boolean r1 = r0.jumpTBURI(r1, r4)
        L_0x0078:
            if (r1 == 0) goto L_0x0080
            com.alibaba.baichuan.android.jsbridge.AlibcJsResult r4 = com.alibaba.baichuan.android.jsbridge.AlibcJsResult.RET_SUCCESS
            r6.success(r4)
            return r5
        L_0x0080:
            com.alibaba.baichuan.android.jsbridge.AlibcJsResult r4 = com.alibaba.baichuan.android.jsbridge.AlibcJsResult.RET_FAIL
            r6.error(r4)
            return r5
        L_0x0086:
            com.alibaba.baichuan.android.jsbridge.AlibcJsResult r4 = new com.alibaba.baichuan.android.jsbridge.AlibcJsResult
            java.lang.String r0 = "6"
            r4.<init>(r0)
            java.lang.String r0 = "2"
            r4.setResultCode(r0)
            r6.error(r4)
            return r5
        L_0x0096:
            com.alibaba.baichuan.android.jsbridge.AlibcJsResult r4 = new com.alibaba.baichuan.android.jsbridge.AlibcJsResult
            java.lang.String r5 = "6"
            r4.<init>(r5)
            java.lang.String r5 = "2"
            r4.setResultCode(r5)
            if (r6 == 0) goto L_0x00a7
            r6.error(r4)
        L_0x00a7:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.jsbridge.a.a.execute(java.lang.String, java.lang.String, com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext):boolean");
    }
}
