package com.alipay.mobile.nebula.provider;

import android.app.Activity;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;

public interface H5OpenAuthProvider {

    public interface OnGetAuthListener {
        void onResult(JSONObject jSONObject);
    }

    void getAuthCode(Activity activity, String str, String str2, String str3, List<String> list, String str4, Map<String, Object> map, boolean z, OnGetAuthListener onGetAuthListener);

    void getAuthCodeLocal(Activity activity, String str, List<String> list, List<String> list2, List<String> list3, OnGetAuthListener onGetAuthListener);
}
