package com.alipay.mobile.securitycommon.taobaobind.util;

import android.os.Bundle;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.inside.h5.insideh5adapter.InsideH5ServiceManager;

public class H5Wrapper {
    public static final String CALLBACK = "https://www.alipay.com/webviewbridge";

    public static void startPage(String str) {
        startPage(str, null);
    }

    public static void startPage(String str, AUH5Plugin aUH5Plugin) {
        if (str.indexOf("https://www.alipay.com/webviewbridge") < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str.indexOf(63) >= 0 ? "&callback=" : "?callback=");
            sb.append("https://www.alipay.com/webviewbridge");
            str = sb.toString();
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", str);
        bundle.putString("st", "YES");
        bundle.putString(H5Param.LONG_SHOW_TITLEBAR, "YES");
        bundle.putString(H5Param.SHOW_TOOLBAR, "NO");
        bundle.putString(H5Param.LONG_SHOW_TOOLBAR, "NO");
        bundle.putString(H5Param.SHOW_LOADING, "NO");
        bundle.putString("showLoading", "NO");
        bundle.putBoolean("sp", true);
        bundle.putBoolean(H5Param.LONG_SHOW_PROGRESS, true);
        bundle.putBoolean(H5Param.SHOW_OPTION_MENU, false);
        bundle.putBoolean("showOptionMenu", false);
        if (aUH5Plugin != null && aUH5Plugin.isBackable()) {
            bundle.putString(H5Param.BACK_BEHAVIOR, H5Param.DEFAULT_LONG_BACK_BEHAVIOR);
        }
        H5Bundle h5Bundle = new H5Bundle();
        h5Bundle.setParams(bundle);
        if (aUH5Plugin != null) {
            h5Bundle.addListener(new AUH5Listener(aUH5Plugin));
        }
        InsideH5ServiceManager.getInstance().getInsideH5Service().getH5Service().startPageFromActivity(null, h5Bundle);
    }
}
