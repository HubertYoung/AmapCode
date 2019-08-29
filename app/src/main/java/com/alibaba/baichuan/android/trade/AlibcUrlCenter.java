package com.alibaba.baichuan.android.trade;

import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.Map;
import org.json.JSONObject;

public class AlibcUrlCenter {
    public static final String URL_CONFIG = "{\n    \"Release\": {\n        \"trade.tmallTradeItemUrlHost\": \"https://detail.m.tmall.com/item.htm\",\n        \"trade.taobaoTradeItemUrlHost\": \"https://h5.m.taobao.com/cm/snap/index.html\",\n        \"trade.taobaoMobileTradeItemUrlHost\": \"https://h5.m.taobao.com/awp/core/detail.htm\",\n        \"trade.miniTaobaoItemUrlHost\": \"https://h5.m.taobao.com/trade/detail.html\",\n        \"trade.myCardCouponsUrl\": \"https://h5.m.taobao.com/vip/portal.html\",\n        \"trade.myOrdersUrl\": \"https://h5.m.taobao.com/mlapp/olist.html\",\n        \"trade.eTicketDetailUrl\": \"https://bendi.m.taobao.com/coupon/q/eticket_detail.htm?isArchive=false\",\n        \"trade.promotionsUrl\": \"https://ff.win.taobao.com?des=promotions&cc=tae\",\n        \"trade.shopUrlHost\": \"https://shop.m.taobao.com/shop/shop_index.htm\",\n        \"trade.cartUrl\": \"https://h5.m.taobao.com/mlapp/cart.html\"\n    },\n    \"PreRelease\": {\n        \"trade.promotionsUrl\": \"http://ff.win.taobao.com?des=promotions&cc=tae\"\n    },\n    \"Daily\": {\n        \"trade.promotionsUrl\": \"http://ff.win.daily.taobao.net?des=promotions&cc=tae\"\n    }\n}";
    public static final AlibcUrlCenter instance = new AlibcUrlCenter();
    public String TAG = "AlibcUrlCenter";
    Map a;
    Map b;
    Map c;

    private AlibcUrlCenter() {
        a();
    }

    private void a() {
        try {
            JSONObject jSONObject = new JSONObject(URL_CONFIG);
            JSONObject jSONObject2 = jSONObject.getJSONObject("Release");
            JSONObject jSONObject3 = jSONObject.getJSONObject("PreRelease");
            JSONObject jSONObject4 = jSONObject.getJSONObject("Daily");
            this.a = JSONUtils.toMap(jSONObject2);
            this.b = JSONUtils.toMap(jSONObject3);
            this.c = JSONUtils.toMap(jSONObject4);
        } catch (Exception unused) {
            AlibcLogger.e(this.TAG, "URLCenter初始化失败");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        r3 = r3.replace(com.autonavi.minimap.ajx3.loader.AjxHttpLoader.DOMAIN_HTTPS, com.autonavi.minimap.ajx3.loader.AjxHttpLoader.DOMAIN_HTTP);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0058, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getValue(java.lang.String r3, com.alibaba.baichuan.android.trade.AlibcContext.Environment r4) {
        /*
            r2 = this;
            int[] r0 = com.alibaba.baichuan.android.trade.AlibcUrlCenter.AnonymousClass1.a
            int r4 = r4.ordinal()
            r4 = r0[r4]
            r0 = 0
            switch(r4) {
                case 1: goto L_0x005b;
                case 2: goto L_0x002e;
                case 3: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            return r0
        L_0x000d:
            java.util.Map r4 = r2.c
            java.lang.Object r4 = r4.get(r3)
            java.lang.String r4 = (java.lang.String) r4
            if (r4 != 0) goto L_0x0059
            java.util.Map r4 = r2.a
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 != 0) goto L_0x0023
            r3 = r0
            goto L_0x002b
        L_0x0023:
            java.lang.String r4 = ".m.taobao.com"
            java.lang.String r1 = ".waptest.taobao.com"
            java.lang.String r3 = r3.replace(r4, r1)
        L_0x002b:
            if (r3 != 0) goto L_0x004f
            return r0
        L_0x002e:
            java.util.Map r4 = r2.b
            java.lang.Object r4 = r4.get(r3)
            java.lang.String r4 = (java.lang.String) r4
            if (r4 != 0) goto L_0x0059
            java.util.Map r4 = r2.a
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 != 0) goto L_0x0044
            r3 = r0
            goto L_0x004c
        L_0x0044:
            java.lang.String r4 = ".m.taobao.com"
            java.lang.String r1 = ".wapa.taobao.com"
            java.lang.String r3 = r3.replace(r4, r1)
        L_0x004c:
            if (r3 != 0) goto L_0x004f
            return r0
        L_0x004f:
            java.lang.String r4 = "https://"
            java.lang.String r0 = "http://"
            java.lang.String r3 = r3.replace(r4, r0)
        L_0x0057:
            r0 = r3
            return r0
        L_0x0059:
            r0 = r4
            return r0
        L_0x005b:
            java.util.Map r4 = r2.a
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            goto L_0x0057
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.AlibcUrlCenter.getValue(java.lang.String, com.alibaba.baichuan.android.trade.AlibcContext$Environment):java.lang.String");
    }
}
