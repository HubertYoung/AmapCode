package com.alibaba.baichuan.android.trade.a;

import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.baichuan.android.trade.model.AliPayResult;
import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import java.util.HashMap;
import java.util.List;

public class d extends c {
    public d() {
        this.a = "mtop.alibaba.baichuan.nbsdk.pay.query";
    }

    public static AliPayResult a(NetworkResponse networkResponse) {
        if (networkResponse.data == null || !networkResponse.errorCode.equals(GenBusCodeService.CODE_SUCESS)) {
            return null;
        }
        return JSONUtils.parseAliPayResult(networkResponse.jsonData);
    }

    private HashMap b(List list) {
        HashMap hashMap = new HashMap();
        hashMap.put("orderIdList", JSONUtils.toJsonString(list));
        return hashMap;
    }

    public NetworkResponse a(List list) {
        return a(b(list));
    }
}
