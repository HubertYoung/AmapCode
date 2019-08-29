package com.alipay.mobile.tinyappcommon.provider;

import android.app.Activity;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.provider.H5TinyDebugProvider;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;

public class H5TinyAppDebugProviderImpl implements H5TinyDebugProvider {
    public void sendMsgToRemoteWorkerOrVConsole(String s, String s1, String s2) {
        TinyappUtils.sendMsgToRemoteWorkerOrVConsole(s, s1, s2);
    }

    public boolean isRemoteDebugConnected(String s) {
        return TinyappUtils.isRemoteDebugConnected(s);
    }

    public boolean isVConsolePanelOpened() {
        return TinyappUtils.isVConsolePanelOpened();
    }

    public String buildStandardLogInfo(Activity activity, H5Page h5Page, JSONObject jsonObject) {
        return H5TinyAppLogUtil.buildStandardLogInfo(activity, h5Page, jsonObject);
    }
}
