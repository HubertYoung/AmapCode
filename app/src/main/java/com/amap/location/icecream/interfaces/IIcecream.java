package com.amap.location.icecream.interfaces;

import android.content.Context;
import org.json.JSONObject;

public interface IIcecream {
    void start(Context context, JSONObject jSONObject);

    void stop();
}
