package com.alibaba.sdk.trade.component.coupon;

import android.text.TextUtils;
import com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext;
import com.alibaba.baichuan.android.jsbridge.AlibcJsResult;
import com.alibaba.baichuan.android.jsbridge.plugin.AlibcApiPlugin;
import com.alibaba.baichuan.android.trade.utils.StringUtils;
import com.alibaba.sdk.trade.container.AlibcComponentCallback;
import com.alibaba.sdk.trade.container.AlibcContainer;
import java.util.Map;

public class AlibcActivityBridge extends AlibcApiPlugin {
    public static String API_NAME = "AlibcActivity";

    public boolean execute(String str, String str2, AlibcJsCallbackContext alibcJsCallbackContext) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str) || alibcJsCallbackContext == null) {
            AlibcJsResult alibcJsResult = new AlibcJsResult("6");
            alibcJsResult.setResultCode("2");
            if (alibcJsCallbackContext != null) {
                alibcJsCallbackContext.error(alibcJsResult);
            }
            return false;
        } else if ("getCoupon".equals(str)) {
            return getCoupon(str2, alibcJsCallbackContext);
        } else {
            if ("queryCoupon".equals(str)) {
                return queryCoupon(str2, alibcJsCallbackContext);
            }
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0067  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean getCoupon(java.lang.String r11, final com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext r12) {
        /*
            r10 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = ""
            r2 = -1
            r3 = -1
            java.lang.Object r11 = com.alibaba.fastjson.JSON.parse(r11)     // Catch:{ Exception -> 0x0057 }
            java.util.Map r11 = com.alibaba.baichuan.android.trade.utils.StringUtils.obj2MapObject(r11)     // Catch:{ Exception -> 0x0057 }
            java.lang.String r5 = "activityId"
            java.lang.Object r5 = r11.get(r5)     // Catch:{ Exception -> 0x0057 }
            java.lang.String r5 = com.alibaba.baichuan.android.trade.utils.StringUtils.obj2String(r5)     // Catch:{ Exception -> 0x0057 }
            java.lang.String r0 = "supplierId"
            java.lang.Object r0 = r11.get(r0)     // Catch:{ Exception -> 0x0055 }
            java.lang.Long r0 = com.alibaba.baichuan.android.trade.utils.StringUtils.obj2Long(r0)     // Catch:{ Exception -> 0x0055 }
            long r6 = r0.longValue()     // Catch:{ Exception -> 0x0055 }
            java.lang.String r0 = "sourceId"
            java.lang.Object r0 = r11.get(r0)     // Catch:{ Exception -> 0x0052 }
            java.lang.Long r0 = com.alibaba.baichuan.android.trade.utils.StringUtils.obj2Long(r0)     // Catch:{ Exception -> 0x0052 }
            int r0 = r0.intValue()     // Catch:{ Exception -> 0x0052 }
            java.lang.String r8 = "asac"
            java.lang.Object r8 = r11.get(r8)     // Catch:{ Exception -> 0x004f }
            java.lang.String r8 = com.alibaba.baichuan.android.trade.utils.StringUtils.obj2String(r8)     // Catch:{ Exception -> 0x004f }
            java.lang.String r1 = "trackParams"
            java.lang.Object r11 = r11.get(r1)     // Catch:{ Exception -> 0x004d }
            java.util.Map r11 = com.alibaba.baichuan.android.trade.utils.StringUtils.obj2MapString(r11)     // Catch:{ Exception -> 0x004d }
            goto L_0x0060
        L_0x004d:
            r11 = move-exception
            goto L_0x005c
        L_0x004f:
            r11 = move-exception
            r8 = r1
            goto L_0x005c
        L_0x0052:
            r11 = move-exception
            r8 = r1
            goto L_0x005b
        L_0x0055:
            r11 = move-exception
            goto L_0x0059
        L_0x0057:
            r11 = move-exception
            r5 = r0
        L_0x0059:
            r8 = r1
            r6 = r3
        L_0x005b:
            r0 = -1
        L_0x005c:
            r11.printStackTrace()
            r11 = 0
        L_0x0060:
            boolean r1 = android.text.TextUtils.isEmpty(r5)
            r9 = 1
            if (r1 != 0) goto L_0x0097
            int r1 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x0097
            if (r0 == r2) goto L_0x0097
            boolean r1 = android.text.TextUtils.isEmpty(r8)
            if (r1 != 0) goto L_0x0097
            if (r11 != 0) goto L_0x0076
            goto L_0x0097
        L_0x0076:
            r1 = 2
            com.alibaba.sdk.trade.container.AlibcBaseComponent r1 = com.alibaba.sdk.trade.container.AlibcContainer.getComponentByType(r1)
            com.alibaba.sdk.trade.component.coupon.AlibcGetCouponParams r2 = new com.alibaba.sdk.trade.component.coupon.AlibcGetCouponParams
            r2.<init>()
            r2.mUUID = r5
            java.lang.Long r3 = java.lang.Long.valueOf(r6)
            r2.mSupplierId = r3
            r2.mCouponInstanceSource = r0
            r2.mASAC = r8
            r2.mYbhpssParams = r11
            com.alibaba.sdk.trade.component.coupon.AlibcActivityBridge$1 r11 = new com.alibaba.sdk.trade.component.coupon.AlibcActivityBridge$1
            r11.<init>(r12)
            r1.execute(r2, r11)
            return r9
        L_0x0097:
            com.alibaba.baichuan.android.jsbridge.AlibcJsResult r11 = new com.alibaba.baichuan.android.jsbridge.AlibcJsResult
            java.lang.String r0 = "6"
            r11.<init>(r0)
            java.lang.String r0 = "2"
            r11.setResultCode(r0)
            r12.error(r11)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.trade.component.coupon.AlibcActivityBridge.getCoupon(java.lang.String, com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext):boolean");
    }

    private boolean queryCoupon(String str, final AlibcJsCallbackContext alibcJsCallbackContext) {
        AlibcContainer.getComponentByType(2).execute(new AlibcQueryCouponParams(), new AlibcComponentCallback() {
            public void onSuccess(Object obj) {
                AlibcJsResult alibcJsResult = new AlibcJsResult("0");
                Map obj2MapObject = StringUtils.obj2MapObject(obj);
                for (String str : obj2MapObject.keySet()) {
                    alibcJsResult.addData(str, StringUtils.obj2String(obj2MapObject.get(str)));
                }
                alibcJsCallbackContext.success(alibcJsResult);
            }

            public void onError(String str, String str2) {
                AlibcJsResult alibcJsResult = new AlibcJsResult("6");
                alibcJsResult.setResultCode(str);
                alibcJsResult.setResultMsg(str2);
                alibcJsCallbackContext.error(alibcJsResult);
            }
        });
        return true;
    }
}
