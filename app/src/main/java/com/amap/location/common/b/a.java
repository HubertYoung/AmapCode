package com.amap.location.common.b;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.amap.location.common.b;
import com.google.flatbuffers.FlatBufferBuilder;

/* compiled from: HeaderBuilder */
public class a {
    public static int a(FlatBufferBuilder flatBufferBuilder, Context context) {
        int i;
        int i2;
        a();
        try {
            int createString = flatBufferBuilder.createString((CharSequence) context.getPackageName());
            int createString2 = flatBufferBuilder.createString((CharSequence) b.b());
            int createString3 = TextUtils.isEmpty(com.amap.location.common.a.a()) ? Integer.MIN_VALUE : flatBufferBuilder.createString((CharSequence) com.amap.location.common.a.a());
            String b = com.amap.location.common.a.b(context);
            int createString4 = TextUtils.isEmpty(b) ? Integer.MIN_VALUE : flatBufferBuilder.createString((CharSequence) b);
            String a = com.amap.location.common.a.a(context);
            int createString5 = TextUtils.isEmpty(a) ? Integer.MIN_VALUE : flatBufferBuilder.createString((CharSequence) a);
            String d = com.amap.location.common.a.d(context);
            int createString6 = TextUtils.isEmpty(d) ? Integer.MIN_VALUE : flatBufferBuilder.createString((CharSequence) d);
            String str = Build.BRAND;
            if (str != null && str.length() > 16) {
                str = str.substring(0, 16);
            }
            int createString7 = TextUtils.isEmpty(str) ? Integer.MIN_VALUE : flatBufferBuilder.createString((CharSequence) str);
            String str2 = Build.MODEL;
            if (str2 != null && str2.length() > 16) {
                str2 = str2.substring(0, 16);
            }
            int createString8 = TextUtils.isEmpty(str2) ? Integer.MIN_VALUE : flatBufferBuilder.createString((CharSequence) str2);
            if (TextUtils.isEmpty(b.c())) {
                i = Integer.MIN_VALUE;
            } else {
                i = flatBufferBuilder.createString((CharSequence) b.c());
            }
            if (TextUtils.isEmpty(b.d())) {
                i2 = Integer.MIN_VALUE;
            } else {
                i2 = flatBufferBuilder.createString((CharSequence) b.d());
            }
            b.a(flatBufferBuilder);
            b.a(flatBufferBuilder, b.a());
            b.a(flatBufferBuilder, createString);
            b.b(flatBufferBuilder, createString2);
            b.b(flatBufferBuilder, (byte) com.amap.location.common.a.d());
            b.a(flatBufferBuilder, com.amap.location.common.a.e(context));
            if (createString3 != Integer.MIN_VALUE) {
                b.c(flatBufferBuilder, createString3);
            }
            if (createString4 != Integer.MIN_VALUE) {
                b.d(flatBufferBuilder, createString4);
            }
            if (createString5 != Integer.MIN_VALUE) {
                b.e(flatBufferBuilder, createString5);
            }
            if (createString6 != Integer.MIN_VALUE) {
                b.f(flatBufferBuilder, createString6);
            }
            if (createString7 != Integer.MIN_VALUE) {
                b.g(flatBufferBuilder, createString7);
            }
            if (createString8 != Integer.MIN_VALUE) {
                b.h(flatBufferBuilder, createString8);
            }
            if (i != Integer.MIN_VALUE) {
                b.i(flatBufferBuilder, i);
            }
            if (i2 != Integer.MIN_VALUE) {
                b.j(flatBufferBuilder, i2);
            }
            return b.b(flatBufferBuilder);
        } catch (Error | Exception unused) {
            return 0;
        }
    }

    private static void a() {
        if (b.a() < 0 || TextUtils.isEmpty(b.b())) {
            StringBuilder sb = new StringBuilder("必须在 HeaderBuildre 中，设置好 productId, productVerion");
            sb.append(b.a());
            sb.append(b.b());
            sb.append("， 以及其他的值");
            throw new RuntimeException(sb.toString());
        }
    }
}
