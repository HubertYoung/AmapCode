package com.alipay.mobile.android.verify.sdk.interfaces;

import android.app.Activity;
import android.content.Context;
import com.alibaba.fastjson.JSONObject;

public interface IService {
    String getMetaInfo(Context context);

    void startService(Activity activity, JSONObject jSONObject, ICallback iCallback);

    @Deprecated
    void startService(Activity activity, String str, ICallback iCallback);
}
