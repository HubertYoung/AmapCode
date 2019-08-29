package com.alibaba.baichuan.android.trade.adapter.applink;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.ut.AlibcUserTracker;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.constants.AppLinkConstants;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.cache.MemoryCacheUtils;
import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.taobao.applink.TBAppLinkParam;
import com.taobao.applink.TBAppLinkSDK;
import com.taobao.applink.param.TBDetailParam;
import com.taobao.applink.param.TBShopParam;
import com.taobao.applink.param.TBURIParam;
import com.taobao.applink.util.TBAppLinkUtil;
import java.util.HashMap;
import java.util.Map;

public class AlibcApplink implements IAlibcApplink {
    private static final String a = "AlibcApplink";
    private static volatile AlibcApplink d;
    private TBAppLinkSDK b = TBAppLinkSDK.getInstance();
    private boolean c;

    private AlibcApplink() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0078, code lost:
        r2 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.taobao.applink.param.TBBaseParam a(java.util.Map r5, java.lang.String r6) {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L_0x0104
            int r1 = r5.size()
            if (r1 != 0) goto L_0x000a
            return r0
        L_0x000a:
            r1 = -1
            int r2 = r6.hashCode()
            r3 = 2544374(0x26d2f6, float:3.565427E-39)
            if (r2 == r3) goto L_0x0033
            r3 = 79626270(0x4bf001e, float:4.4903992E-36)
            if (r2 == r3) goto L_0x0029
            r3 = 2013072465(0x77fd0c51, float:1.0264851E34)
            if (r2 == r3) goto L_0x001f
            goto L_0x003c
        L_0x001f:
            java.lang.String r2 = "DETAIL"
            boolean r2 = r6.equals(r2)
            if (r2 == 0) goto L_0x003c
            r1 = 1
            goto L_0x003c
        L_0x0029:
            java.lang.String r2 = "TBURI"
            boolean r2 = r6.equals(r2)
            if (r2 == 0) goto L_0x003c
            r1 = 2
            goto L_0x003c
        L_0x0033:
            java.lang.String r2 = "SHOP"
            boolean r2 = r6.equals(r2)
            if (r2 == 0) goto L_0x003c
            r1 = 0
        L_0x003c:
            switch(r1) {
                case 0: goto L_0x0065;
                case 1: goto L_0x0053;
                case 2: goto L_0x0040;
                default: goto L_0x003f;
            }
        L_0x003f:
            return r0
        L_0x0040:
            java.lang.String r1 = "url"
            java.lang.String r1 = r4.getStringValue(r5, r1)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0078
            com.taobao.applink.param.TBURIParam r2 = new com.taobao.applink.param.TBURIParam
            r2.<init>(r1)
            goto L_0x0079
        L_0x0053:
            java.lang.String r1 = "itemId"
            java.lang.String r1 = r4.getStringValue(r5, r1)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0078
            com.taobao.applink.param.TBDetailParam r2 = new com.taobao.applink.param.TBDetailParam
            r2.<init>(r1)
            goto L_0x0079
        L_0x0065:
            java.lang.String r1 = "shopId"
            java.lang.String r1 = r4.getStringValue(r5, r1)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0078
            com.taobao.applink.param.TBShopParam r2 = new com.taobao.applink.param.TBShopParam
            r2.<init>(r1)
            goto L_0x0079
        L_0x0078:
            r2 = r0
        L_0x0079:
            if (r2 != 0) goto L_0x007c
            return r0
        L_0x007c:
            java.lang.String r0 = "backURL"
            java.lang.String r0 = r4.getStringValue(r5, r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x008c
        L_0x0088:
            r2.setBackUrl(r0)
            goto L_0x008f
        L_0x008c:
            java.lang.String r0 = "alisdk://"
            goto L_0x0088
        L_0x008f:
            java.lang.String r0 = "e"
            java.lang.String r0 = r4.getStringValue(r5, r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x009e
            r2.setE(r0)
        L_0x009e:
            java.lang.String r0 = "sign"
            java.lang.String r0 = r4.getStringValue(r5, r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x00ae
            r2.setSign(r0)
        L_0x00ae:
            java.lang.String r0 = "type"
            java.lang.String r0 = r4.getStringValue(r5, r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x00be
            r2.setType(r0)
        L_0x00be:
            java.lang.String r0 = "addParams"
            java.lang.Object r0 = r5.get(r0)
            if (r0 == 0) goto L_0x00d5
            boolean r1 = r0 instanceof java.util.HashMap
            if (r1 == 0) goto L_0x00d5
            java.util.HashMap r0 = (java.util.HashMap) r0
            int r1 = r0.size()
            if (r1 <= 0) goto L_0x00d5
            r2.addExtraParams(r0)
        L_0x00d5:
            java.lang.String r0 = "TBAuth"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x00f4
            java.lang.String r6 = "jsonParams"
            java.lang.Object r6 = r5.get(r6)
            if (r6 == 0) goto L_0x00f4
            boolean r0 = r6 instanceof org.json.JSONObject
            if (r0 == 0) goto L_0x00f4
            org.json.JSONObject r6 = (org.json.JSONObject) r6
            int r0 = r6.length()
            if (r0 <= 0) goto L_0x00f4
            r2.setParams(r6)
        L_0x00f4:
            java.lang.String r6 = "appType"
            java.lang.String r5 = r4.getStringValue(r5, r6)
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x0103
            r2.setAppType(r5)
        L_0x0103:
            return r2
        L_0x0104:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink.a(java.util.Map, java.lang.String):com.taobao.applink.param.TBBaseParam");
    }

    private void a(Object obj, boolean z) {
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(AlibcContext.getAppKey())) {
            hashMap.put("appkey", AlibcContext.getAppKey());
        }
        hashMap.put("ybhpss", (String) MemoryCacheUtils.getGroupProperity(AlibcConstants.TRADE_GROUP, "ybhpss"));
        hashMap.put("param", JSONUtils.objectToJson("param", obj));
        hashMap.put("from", UserTrackerConstants.FROM_VALUE);
        hashMap.put(UserTrackerConstants.IS_SUCCESS, z ? "1" : "0");
        AlibcUserTracker.getInstance().sendCustomHit(UserTrackerConstants.E_SHOW_APPLINK, (String) "", (Map) hashMap);
    }

    public static AlibcApplink getInstance() {
        if (d == null) {
            synchronized (AlibcApplink.class) {
                try {
                    if (d == null) {
                        d = new AlibcApplink();
                    }
                }
            }
        }
        return d;
    }

    public static boolean isApplinkSupported(String str) {
        if (TBAppLinkUtil.isSupportAppLinkSDK(AlibcContext.context, str)) {
            return true;
        }
        AlibcLogger.d("AliTradeApplinkServiceImp", "对不起，请使用最新版的手机淘宝");
        return false;
    }

    public String getStringValue(Map map, String str) {
        Object obj = map.get(str);
        if (obj == null || !(obj instanceof String)) {
            return null;
        }
        return (String) obj;
    }

    public void initApplink() {
        if (!this.c) {
            TBAppLinkParam tBAppLinkParam = new TBAppLinkParam(AlibcContext.getAppKey(), null, "backurl", "");
            tBAppLinkParam.setUtdid(AlibcContext.getUtdid());
            tBAppLinkParam.setTTID(AlibcConfig.getInstance().getWebTTID());
            tBAppLinkParam.setSource(AlibcConfig.getInstance().taobaoNativeSource);
            this.b.init(AlibcContext.context, tBAppLinkParam);
            this.c = true;
        }
    }

    public boolean jumpDetail(Context context, Map map) {
        AlibcLogger.d(a, "调用applink jumpdetail方法,传入参数为:params=".concat(String.valueOf(map)));
        initApplink();
        TBDetailParam tBDetailParam = (TBDetailParam) a(map, (String) AppLinkConstants.DETAIL);
        boolean z = false;
        if (tBDetailParam != null) {
            try {
                this.b.jumpDetail(context, tBDetailParam);
                z = true;
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("Applink調用jumpDetail失败：");
                sb.append(e.getMessage());
                AlibcLogger.e("AliTradeApplinkServiceImp", sb.toString());
            }
        }
        a((Object) map, z);
        StringBuilder sb2 = new StringBuilder("Applink調用jumpDetail");
        sb2.append(z ? "成功" : "失败");
        AlibcLogger.d("AliTradeApplinkServiceImp", sb2.toString());
        return z;
    }

    public boolean jumpShop(Context context, Map map) {
        AlibcLogger.d(a, "调用applink jumpshop方法,传入参数为:params=".concat(String.valueOf(map)));
        initApplink();
        TBShopParam tBShopParam = (TBShopParam) a(map, (String) AppLinkConstants.SHOP);
        boolean z = false;
        if (tBShopParam != null) {
            try {
                this.b.jumpShop(context, tBShopParam);
                z = true;
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("Applink調用jumpShop失败：");
                sb.append(e.getMessage());
                AlibcLogger.e("AliTradeApplinkServiceImp", sb.toString());
            }
        }
        a((Object) map, z);
        StringBuilder sb2 = new StringBuilder("Applink調用jumpShop");
        sb2.append(z ? "成功" : "失败");
        AlibcLogger.d("AliTradeApplinkServiceImp", sb2.toString());
        return z;
    }

    public boolean jumpTBURI(Context context, Map map) {
        AlibcLogger.d(a, "调用applink jumpuri方法,传入参数为:params=".concat(String.valueOf(map)));
        initApplink();
        TBURIParam tBURIParam = (TBURIParam) a(map, (String) AppLinkConstants.TBURI);
        boolean z = false;
        if (tBURIParam != null) {
            try {
                this.b.jumpTBURI(context, tBURIParam);
                z = true;
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("Applink調用jumpTBURI失败：");
                sb.append(e.getMessage());
                AlibcLogger.e("AliTradeApplinkServiceImp", sb.toString());
            }
        }
        a((Object) map, z);
        StringBuilder sb2 = new StringBuilder("Applink調用jumpTBURI");
        sb2.append(z ? "成功" : "失败");
        AlibcLogger.d("AliTradeApplinkServiceImp", sb2.toString());
        return z;
    }

    public void setOpenParam(Map map) {
        initApplink();
        if (this.b != null && map != null) {
            TBAppLinkParam tBAppLinkParam = this.b.sOpenParam;
            if (tBAppLinkParam != null) {
                String str = (String) map.get(AppLinkConstants.BACKURL);
                if (!TextUtils.isEmpty(str)) {
                    tBAppLinkParam.mBackUrl = str;
                }
                String str2 = (String) map.get("pid");
                if (!TextUtils.isEmpty(str2)) {
                    tBAppLinkParam.mPid = str2;
                }
                String str3 = (String) map.get("tag");
                if (!TextUtils.isEmpty(str3)) {
                    tBAppLinkParam.mTag = str3;
                }
                String str4 = (String) map.get("TTID");
                if (!TextUtils.isEmpty(str4)) {
                    tBAppLinkParam.mTtid = str4;
                }
                String str5 = (String) map.get("source");
                if (!TextUtils.isEmpty(str5)) {
                    tBAppLinkParam.mSource = str5;
                }
                String str6 = (String) map.get("utdid");
                if (!TextUtils.isEmpty(str6)) {
                    tBAppLinkParam.mUtdid = str6;
                }
            }
        }
    }
}
