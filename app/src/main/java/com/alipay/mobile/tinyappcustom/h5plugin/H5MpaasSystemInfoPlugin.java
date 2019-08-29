package com.alipay.mobile.tinyappcustom.h5plugin;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.info.AppInfo;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SystemInfoPlugin;

public class H5MpaasSystemInfoPlugin extends H5SystemInfoPlugin {
    /* access modifiers changed from: protected */
    public void appendSystemInfo(JSONObject jsonObject) {
        super.appendSystemInfo(jsonObject);
        if (jsonObject != null) {
            jsonObject.put((String) "language", (Object) "zh-Hans");
            jsonObject.put((String) "fontSizeSetting", (Object) Float.valueOf(16.0f));
            jsonObject.put((String) "version", (Object) AppInfo.getInstance().getmProductVersion());
            jsonObject.put((String) "app", (Object) "amap");
        }
    }
}
