package com.alipay.mobile.account.adapter;

import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import java.util.HashMap;
import java.util.UUID;

public class RpcAdapter {
    private static RpcAdapter a;

    private RpcAdapter() {
    }

    public static RpcAdapter a() {
        if (a == null) {
            synchronized (RpcAdapter.class) {
                if (a == null) {
                    a = new RpcAdapter();
                }
            }
        }
        return a;
    }

    public static <T> T a(Class<T> cls) {
        HashMap hashMap = new HashMap();
        hashMap.put("OpenAuthLogin", "YES");
        hashMap.put("needOpenAuth", "NO");
        hashMap.put("bizSource", "aliautologin");
        hashMap.put("cAuthUUID", UUID.randomUUID().toString());
        return CommonServiceFactory.getInstance().getRpcService().getRpcProxy(cls, hashMap);
    }
}
