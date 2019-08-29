package com.alibaba.baichuan.android.jsbridge.a;

import android.text.TextUtils;
import com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext;
import com.alibaba.baichuan.android.jsbridge.AlibcJsResult;
import com.alibaba.baichuan.android.jsbridge.plugin.AlibcApiPlugin;
import com.alibaba.baichuan.android.trade.utils.AlibcTradeHelper;
import com.alibaba.baichuan.android.trade.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import java.util.Map;

public class f extends AlibcApiPlugin {
    public static String a = "AliBCWebView";

    public void a(AlibcJsCallbackContext alibcJsCallbackContext, String str) {
        Map obj2MapString = StringUtils.obj2MapString(JSON.parseObject(str));
        if (obj2MapString != null) {
            this.c.b.put("ui_contextParams", AlibcTradeHelper.createUrlParams(obj2MapString));
        }
        AlibcJsResult alibcJsResult = new AlibcJsResult();
        alibcJsResult.setResultCode("0");
        alibcJsCallbackContext.success(alibcJsResult);
    }

    public boolean execute(String str, String str2, AlibcJsCallbackContext alibcJsCallbackContext) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str) || alibcJsCallbackContext == null) {
            AlibcJsResult alibcJsResult = new AlibcJsResult("6");
            alibcJsResult.setResultCode("2");
            if (alibcJsCallbackContext != null) {
                alibcJsCallbackContext.error(alibcJsResult);
            }
            return false;
        }
        if ("setYbhpss".equals(str)) {
            a(alibcJsCallbackContext, str2);
        }
        return true;
    }
}
