package com.alibaba.baichuan.android.jsbridge.a;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext;
import com.alibaba.baichuan.android.jsbridge.AlibcJsResult;
import com.alibaba.baichuan.android.jsbridge.plugin.AlibcApiPlugin;
import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.taobao.accs.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends AlibcApiPlugin {
    public static String a = "AliBCBase";

    public static boolean a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            return (packageManager == null || packageManager.getPackageInfo(str, 0) == null) ? false : true;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void a(AlibcJsCallbackContext alibcJsCallbackContext, String str) {
        AlibcJsResult alibcJsResult = new AlibcJsResult();
        alibcJsResult.setSuccess();
        alibcJsResult.addData(Constants.KEY_MODEL, Build.MODEL);
        alibcJsResult.addData("brand", Build.BRAND);
        alibcJsCallbackContext.success(alibcJsResult);
    }

    public void b(AlibcJsCallbackContext alibcJsCallbackContext, String str) {
        String[] strArr;
        try {
            strArr = JSONUtils.toStringArray(new JSONObject(str).getJSONArray("apps"));
        } catch (JSONException unused) {
            AlibcLogger.e("Base", "isInstall parse params error, params: ".concat(String.valueOf(str)));
            strArr = null;
        }
        AlibcJsResult alibcJsResult = new AlibcJsResult();
        if (strArr == null) {
            alibcJsCallbackContext.error(alibcJsResult);
            return;
        }
        for (int i = 0; i < strArr.length; i++) {
            alibcJsResult.addData(strArr[i], Boolean.valueOf(a(this.b, strArr[i])));
        }
        alibcJsResult.setSuccess();
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
        if ("getDeviceInfo".equals(str)) {
            a(alibcJsCallbackContext, str2);
        } else if ("isInstalled".equals(str)) {
            b(alibcJsCallbackContext, str2);
        }
        return true;
    }
}
