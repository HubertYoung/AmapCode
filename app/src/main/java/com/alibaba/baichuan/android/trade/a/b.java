package com.alibaba.baichuan.android.trade.a;

import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;

public class b extends c {
    public b() {
        this.a = "mtop.alibaba.baichuan.nbsdk.sclick.create";
    }

    public static String a(NetworkResponse networkResponse) {
        if (networkResponse.data == null || !networkResponse.isSuccess || !networkResponse.errorCode.equals(GenBusCodeService.CODE_SUCESS)) {
            return null;
        }
        return networkResponse.data.get("data").toString();
    }
}
