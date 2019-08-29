package com.alibaba.baichuan.android.jsbridge.a;

import com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext;
import com.alibaba.baichuan.android.jsbridge.AlibcJsResult;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkClient.NetworkRequestListener;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.baichuan.android.trade.utils.StringUtils;
import java.util.Map;

class e implements NetworkRequestListener {
    final /* synthetic */ AlibcJsCallbackContext a;
    final /* synthetic */ d b;

    e(d dVar, AlibcJsCallbackContext alibcJsCallbackContext) {
        this.b = dVar;
        this.a = alibcJsCallbackContext;
    }

    public void onError(int i, NetworkResponse networkResponse) {
        AlibcJsResult alibcJsResult = new AlibcJsResult();
        alibcJsResult.setResultCode(networkResponse.errorCode);
        alibcJsResult.setResultMsg(networkResponse.errorMsg);
        Map map = networkResponse.data;
        for (String str : map.keySet()) {
            alibcJsResult.addData(str, StringUtils.obj2String(map.get(str)));
        }
        this.a.error(alibcJsResult);
    }

    public void onSuccess(int i, NetworkResponse networkResponse) {
        AlibcJsResult alibcJsResult = new AlibcJsResult();
        Map map = networkResponse.data;
        for (String str : map.keySet()) {
            alibcJsResult.addData(str, StringUtils.obj2String(map.get(str)));
        }
        alibcJsResult.setSuccess();
        this.a.success(alibcJsResult);
    }
}
