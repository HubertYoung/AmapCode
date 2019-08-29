package com.alipay.android.phone.inside.api.utils;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import java.lang.reflect.Field;

public class BundleModelImpl implements IBundleModel {
    public Bundle toBundle() {
        try {
            Bundle bundle = new Bundle();
            Class cls = getClass();
            do {
                Field[] declaredFields = cls.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    Object obj = field.get(this);
                    String cls2 = field.getType().toString();
                    TraceLogger f = LoggerFactory.f();
                    StringBuilder sb = new StringBuilder();
                    sb.append(cls2);
                    sb.append(":");
                    sb.append(field.getName());
                    sb.append(":");
                    sb.append(obj);
                    f.a((String) "BundleModelImpl", sb.toString());
                    if (obj != null) {
                        bundle.putString(field.getName(), String.valueOf(obj));
                    }
                }
                cls = cls.getSuperclass();
            } while (cls != Object.class);
            return bundle;
        } catch (Throwable th) {
            LoggerFactory.f().c(BaseModel.class.getName(), th);
            return null;
        }
    }
}
