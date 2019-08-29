package com.alipay.mobile.android.verify.sdk.a;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.logger.Logger;
import com.alipay.mobile.beehive.capture.utils.PhotoBehavior;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.taobao.accs.common.Constants;

/* compiled from: BehaviorLogger */
public class a {
    private static String a;

    private static Builder a() {
        Builder appID = new Builder("UC-ZM-171116-1").setAppID("zm_sdk_android");
        if (!TextUtils.isEmpty(a)) {
            appID.addExtParam("zimid", a);
        }
        appID.addExtParam(Constants.KEY_SDK_VERSION, "1.0.1");
        return appID;
    }

    public static void a(String str) {
        a = str;
    }

    public static void b(String str) {
        a().setSeedID(str).click();
    }

    public static void c(String str) {
        a().setSeedID("sdkDetail").addExtParam("sdkDetail", str);
    }

    public static void d(String str) {
        a().setSeedID("errorMessage").addExtParam("errorMessage", str).click();
    }

    public static void a(JSONObject jSONObject) {
        JSONObject jSONObject2;
        if (jSONObject == null) {
            Logger.t("BehaviorLogger").w("null log event data", new Object[0]);
            return;
        }
        String string = jSONObject.getString("seed");
        String string2 = jSONObject.getString(PhotoBehavior.PARAM_1);
        String string3 = jSONObject.getString(PhotoBehavior.PARAM_2);
        String string4 = jSONObject.getString(PhotoBehavior.PARAM_3);
        try {
            jSONObject2 = jSONObject.getJSONObject("param4");
        } catch (Exception e) {
            Logger.t("BehaviorLogger").e(e, "get param4 got error", new Object[0]);
            jSONObject2 = null;
        }
        Builder a2 = a();
        a2.setSeedID(string).setParam1(string2).setParam2(string3).setParam3(string4);
        if (jSONObject2 != null && jSONObject2.size() > 0) {
            for (String next : jSONObject2.keySet()) {
                a2.addExtParam(next, jSONObject2.getString(next));
            }
        }
        a2.click();
    }
}
