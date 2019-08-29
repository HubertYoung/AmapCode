package com.alipay.mobile.beehive.compositeui.multilevel;

import com.alibaba.fastjson.JSONArray;

public interface MultilevelSelectCallBack {
    void onCancel();

    void onSuccess(JSONArray jSONArray);
}
