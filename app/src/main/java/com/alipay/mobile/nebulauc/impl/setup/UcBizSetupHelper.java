package com.alipay.mobile.nebulauc.impl.setup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.uc.webview.export.extension.UCSettings;

public class UcBizSetupHelper {
    public static void configure(String value, String key) {
        JSONArray jsonArray = JSON.parseArray(value);
        if (jsonArray != null && !jsonArray.isEmpty()) {
            String[] hostArray = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                hostArray[i] = jsonArray.getString(i);
            }
            UCSettings.updateBussinessInfo(1, 1, key, hostArray);
        }
    }
}
