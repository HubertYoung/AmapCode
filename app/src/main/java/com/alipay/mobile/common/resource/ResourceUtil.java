package com.alipay.mobile.common.resource;

import android.content.Context;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.lang.reflect.Field;

public class ResourceUtil {
    public ResourceUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static final int[] getResourceDeclareStyleableIntArray(Context context, String name) {
        Field[] fields;
        try {
            for (Field f : Class.forName(context.getPackageName() + ".R$styleable").getFields()) {
                if (name.equals(f.getName())) {
                    return (int[]) f.get(null);
                }
            }
            return null;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static final int getResourceInt(Context context, String type, String name) {
        Field[] fields;
        try {
            for (Field f : Class.forName(context.getPackageName() + ".R$" + type).getFields()) {
                if (name.equals(f.getName())) {
                    return ((Integer) f.get(null)).intValue();
                }
            }
            return -1;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e2) {
            throw new RuntimeException(e2);
        } catch (IllegalAccessException e3) {
            throw new RuntimeException(e3);
        }
    }
}
