package com.alipay.mobile.nebula.provider;

import android.app.Activity;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;

public interface H5TinyDebugProvider {
    String buildStandardLogInfo(Activity activity, H5Page h5Page, JSONObject jSONObject);

    boolean isRemoteDebugConnected(String str);

    boolean isVConsolePanelOpened();

    void sendMsgToRemoteWorkerOrVConsole(String str, String str2, String str3);
}
