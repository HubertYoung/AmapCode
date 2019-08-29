package com.huawei.android.pushselfshow.utils;

import android.content.Context;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.huawei.android.pushagent.a.a.c;
import java.lang.reflect.Field;

public class d {
    public static int a(Context context, String str) {
        return a(context, ResUtils.STRING, str);
    }

    public static int a(Context context, String str, String str2) {
        StringBuilder sb;
        String str3;
        try {
            int identifier = context.getResources().getIdentifier(str2, str, context.getPackageName());
            if (identifier == 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(context.getPackageName());
                sb2.append(".R$");
                sb2.append(str);
                Field field = Class.forName(sb2.toString()).getField(str2);
                identifier = Integer.parseInt(field.get(field.getName()).toString());
                if (identifier == 0) {
                    StringBuilder sb3 = new StringBuilder("Error-resourceType=");
                    sb3.append(str);
                    sb3.append("--resourceName=");
                    sb3.append(str2);
                    sb3.append("--resourceId =");
                    sb3.append(identifier);
                    c.b("ResourceLoader", sb3.toString());
                }
            }
            return identifier;
        } catch (ClassNotFoundException e) {
            e = e;
            str3 = "ResourceLoader";
            sb = new StringBuilder("!!!! ResourceLoader: ClassNotFoundException-resourceType=");
            sb.append(str);
            sb.append("--resourceName=");
            sb.append(str2);
            c.c(str3, sb.toString(), e);
            return 0;
        } catch (NoSuchFieldException e2) {
            e = e2;
            str3 = "ResourceLoader";
            sb = new StringBuilder("!!!! ResourceLoader: NoSuchFieldException-resourceType=");
            sb.append(str);
            sb.append("--resourceName=");
            sb.append(str2);
            c.c(str3, sb.toString(), e);
            return 0;
        } catch (NumberFormatException e3) {
            e = e3;
            str3 = "ResourceLoader";
            sb = new StringBuilder("!!!! ResourceLoader: NumberFormatException-resourceType=");
            sb.append(str);
            sb.append("--resourceName=");
            sb.append(str2);
            c.c(str3, sb.toString(), e);
            return 0;
        } catch (IllegalAccessException e4) {
            e = e4;
            str3 = "ResourceLoader";
            sb = new StringBuilder("!!!! ResourceLoader: IllegalAccessException-resourceType=");
            sb.append(str);
            sb.append("--resourceName=");
            sb.append(str2);
            c.c(str3, sb.toString(), e);
            return 0;
        } catch (IllegalArgumentException e5) {
            e = e5;
            str3 = "ResourceLoader";
            sb = new StringBuilder("!!!! ResourceLoader: IllegalArgumentException-resourceType=");
            sb.append(str);
            sb.append("--resourceName=");
            sb.append(str2);
            c.c(str3, sb.toString(), e);
            return 0;
        }
    }

    public static int b(Context context, String str) {
        return a(context, "plurals", str);
    }

    public static int c(Context context, String str) {
        return a(context, ResUtils.LAYOUT, str);
    }

    public static int d(Context context, String str) {
        return a(context, "menu", str);
    }

    public static int e(Context context, String str) {
        return a(context, "id", str);
    }

    public static int f(Context context, String str) {
        return a(context, "color", str);
    }

    public static int g(Context context, String str) {
        return a(context, ResUtils.DRAWABLE, str);
    }
}
