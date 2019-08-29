package com.ali.user.mobile.service;

import android.content.Context;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseBizService<T> {
    protected Context a;
    protected T b;

    public BaseBizService(Context context) {
        this.a = context.getApplicationContext();
        Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            throw new IllegalArgumentException("only and must set one type interface for the this rpc class");
        }
        this.b = CommonServiceFactory.getInstance().getRpcService().getRpcProxy((Class) actualTypeArguments[0]);
    }
}
