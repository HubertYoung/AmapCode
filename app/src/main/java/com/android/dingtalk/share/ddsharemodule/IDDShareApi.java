package com.android.dingtalk.share.ddsharemodule;

import android.content.Intent;
import com.android.dingtalk.share.ddsharemodule.message.BaseReq;

public interface IDDShareApi {
    int getDDSupportAPI();

    boolean handleIntent(Intent intent, IDDAPIEventHandler iDDAPIEventHandler);

    boolean isDDAppInstalled();

    boolean isDDSupportAPI();

    boolean isDDSupportDingAPI();

    boolean openDDApp();

    boolean registerApp(String str);

    boolean sendReq(BaseReq baseReq);

    boolean sendReqToDing(BaseReq baseReq);

    void unregisterApp();
}
