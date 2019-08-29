package com.alipay.android.phone.inside.offlinecode.engine;

import android.content.Context;
import org.json.JSONObject;

public interface IJSEngine {
    void callJSMethod(String str, JSONObject jSONObject, JSEngineCallback jSEngineCallback);

    void destroy();

    boolean hasPrepared();

    void init(Context context);

    void registerPlugin(DynamicCodePlugin dynamicCodePlugin);
}
