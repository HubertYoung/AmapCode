package com.alipay.android.phone.inside.bizadapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.bizadapter.rpc.CommonInterceptor;
import com.alipay.android.phone.inside.bizadapter.rpc.LoginInterceptor;
import com.alipay.android.phone.inside.bizadapter.securitysdk.SecuritySdkInit;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.commonservice.RpcService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.security.api.SecurityGuardInit;
import com.alipay.inside.mobile.framework.service.annotation.CheckLogin;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import java.lang.reflect.Field;

public class InsideBizAdapter {
    public static void a(Context context) {
        SecurityGuardInit.a(StaticConfig.a());
        SecuritySdkInit.a();
        if ("true".equals(StaticConfig.a((String) "removeInsideRpc"))) {
            LoggerFactory.f().b((String) "inside", (String) "skip rpc initialize");
        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean(CommonServiceFactory.KEY_IS_ALIPAY, StaticConfig.j());
            CommonServiceFactory.getInstance().init(bundle);
            RpcService rpcService = CommonServiceFactory.getInstance().getRpcService();
            rpcService.addRpcInterceptor(CheckLogin.class, new LoginInterceptor());
            rpcService.addRpcInterceptor(OperationType.class, new CommonInterceptor());
        }
        if (TextUtils.equals(StaticConfig.a((String) "supportNonAAR"), "true")) {
            b(context);
        }
    }

    private static void b(Context context) {
        Class[] declaredClasses;
        Field[] declaredFields;
        try {
            Class<?> cls = Class.forName("com.alipay.android.phone.inside.portal.R");
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            for (Class cls2 : cls.getDeclaredClasses()) {
                for (Field field : cls2.getDeclaredFields()) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append(context.getPackageName());
                        sb.append(".R$");
                        sb.append(cls2.getSimpleName());
                        Class<?> cls3 = Class.forName(sb.toString());
                        if (cls3 != null) {
                            Object obj = cls3.getField(field.getName()).get(cls3);
                            field.setAccessible(true);
                            field.set(cls2, obj);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    } catch (NoSuchFieldException e3) {
                        e3.printStackTrace();
                    }
                }
            }
            LoggerFactory.f().b((String) "inside", "R construction time cost: ".concat(String.valueOf(Long.valueOf(System.currentTimeMillis() - valueOf.longValue()))));
        } catch (ClassNotFoundException e4) {
            e4.printStackTrace();
        }
    }
}
