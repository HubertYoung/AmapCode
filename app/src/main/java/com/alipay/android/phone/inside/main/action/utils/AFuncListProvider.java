package com.alipay.android.phone.inside.main.action.utils;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.config.plugin.ConfigPlugin;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import org.json.JSONArray;
import org.json.JSONObject;

public class AFuncListProvider {
    public static String a(String str) throws Exception {
        String a = a(b(), str);
        if (TextUtils.isEmpty(a)) {
            a = a("{\"funcs\":[{\"func\":\"transfer\",\"name\":\"转账\",\"image\":\"https://gw.alicdn.com/tfs/TB1.32hrFuWBuNjSspnXXX1NVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=09999988\"},{\"func\":\"rechargeCenter\",\"name\":\"充值中心\",\"image\":\"https://gw.alicdn.com/tfs/TB1njS2rFOWBuNjy0FiXXXFxVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=20000987\"},{\"func\":\"collectMoney\",\"name\":\"收钱码\",\"image\":\"https://img.alicdn.com/tfs/TB1QmsWA_tYBeNjy1XdXXXXyVXa-81-81.png\",\"link\":\"alipays://platformapi/startapp?appId=20000123\"},{\"func\":\"livingPayment\",\"name\":\"生活缴费\",\"image\":\"https://gw.alicdn.com/tfs/TB1uGvprL5TBuNjSspmXXaDRVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=20000193\"},{\"func\":\"creditCardPayment\",\"name\":\"信用卡还款\",\"image\":\"https://gw.alicdn.com/tfs/TB1tDzLrNGYBuNjy0FnXXX5lpXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=09999999\"},{\"func\":\"healthcare\",\"name\":\"医疗健康\",\"image\":\"https://gw.alicdn.com/tfs/TB1j7Vfr_tYBeNjy1XdXXXXyVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=60000141\"},{\"func\":\"carOwnerService\",\"name\":\"车主服务\",\"image\":\"https://gw.alicdn.com/tfs/TB1iveUrHGYBuNjy0FoXXciBFXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=20000919\"},{\"func\":\"cityService\",\"name\":\"城市服务\",\"image\":\"https://gw.alicdn.com/tfs/TB1mZ05jvuSBuNkHFqDXXXfhVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=20000178\"}]}", str);
        }
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        throw new Exception("not found: ".concat(String.valueOf(str)));
    }

    private static String a(String str, String str2) throws Exception {
        JSONArray optJSONArray = new JSONObject(str).optJSONArray("funcs");
        for (int i = 0; i < optJSONArray.length(); i++) {
            if (TextUtils.equals(str2, optJSONArray.optJSONObject(i).optString("func"))) {
                return optJSONArray.optJSONObject(i).optString("link");
            }
        }
        return "";
    }

    public static String a() throws Exception {
        JSONObject jSONObject = new JSONObject(b());
        JSONArray optJSONArray = jSONObject.optJSONArray("funcs");
        for (int i = 0; i < optJSONArray.length(); i++) {
            optJSONArray.optJSONObject(i).remove("link");
        }
        return jSONObject.toString();
    }

    private static String b() {
        String str = "";
        try {
            Bundle bundle = new Bundle();
            bundle.putString("configName", "ALIPAY_INSIDE_SDK_OPPO_AFUNC");
            String string = ((Bundle) ServiceExecutor.b(ConfigPlugin.SERVICE_DYNAMI_CCONFIG_LOAD, bundle)).getString("configValue", "");
            if (!TextUtils.isEmpty(string) && new JSONObject(string).optJSONArray("funcs") != null) {
                str = string;
            }
        } catch (Exception e) {
            LoggerFactory.e().a((String) "main", (String) "GetAFuncListDynamicConfigEx", (Throwable) e);
        }
        String str2 = "dynamic";
        if (TextUtils.isEmpty(str)) {
            str2 = "default";
            str = "{\"funcs\":[{\"func\":\"transfer\",\"name\":\"转账\",\"image\":\"https://gw.alicdn.com/tfs/TB1.32hrFuWBuNjSspnXXX1NVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=09999988\"},{\"func\":\"rechargeCenter\",\"name\":\"充值中心\",\"image\":\"https://gw.alicdn.com/tfs/TB1njS2rFOWBuNjy0FiXXXFxVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=20000987\"},{\"func\":\"collectMoney\",\"name\":\"收钱码\",\"image\":\"https://img.alicdn.com/tfs/TB1QmsWA_tYBeNjy1XdXXXXyVXa-81-81.png\",\"link\":\"alipays://platformapi/startapp?appId=20000123\"},{\"func\":\"livingPayment\",\"name\":\"生活缴费\",\"image\":\"https://gw.alicdn.com/tfs/TB1uGvprL5TBuNjSspmXXaDRVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=20000193\"},{\"func\":\"creditCardPayment\",\"name\":\"信用卡还款\",\"image\":\"https://gw.alicdn.com/tfs/TB1tDzLrNGYBuNjy0FnXXX5lpXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=09999999\"},{\"func\":\"healthcare\",\"name\":\"医疗健康\",\"image\":\"https://gw.alicdn.com/tfs/TB1j7Vfr_tYBeNjy1XdXXXXyVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=60000141\"},{\"func\":\"carOwnerService\",\"name\":\"车主服务\",\"image\":\"https://gw.alicdn.com/tfs/TB1iveUrHGYBuNjy0FoXXciBFXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=20000919\"},{\"func\":\"cityService\",\"name\":\"城市服务\",\"image\":\"https://gw.alicdn.com/tfs/TB1mZ05jvuSBuNkHFqDXXXfhVXa-84-84.png\",\"link\":\"alipays://platformapi/startapp?appId=20000178\"}]}";
        }
        LoggerFactory.d().a("afunc", BehaviorType.EVENT, "GetAFuncList").g = str2;
        return str;
    }
}
