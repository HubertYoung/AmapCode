package com.alipay.android.phone.inside.security.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import java.util.Random;

public class EncryptUtil {
    private static String a;

    public static String a(Context context) {
        if (TextUtils.isEmpty(a)) {
            String str = "";
            try {
                String packageName = context.getApplicationContext().getPackageName();
                try {
                    str = Base64.encodeToString(packageName.getBytes(), 10);
                } catch (Throwable unused) {
                    str = packageName;
                }
            } catch (Throwable unused2) {
            }
            if (TextUtils.isEmpty(str)) {
                str = "unknow";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("0000000000000000000000000000");
            a = sb.toString().substring(0, 24);
        }
        return a;
    }

    public static String a(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            switch (random.nextInt(3)) {
                case 0:
                    stringBuffer.append(String.valueOf((char) ((int) Math.round((Math.random() * 25.0d) + 65.0d))));
                    break;
                case 1:
                    stringBuffer.append(String.valueOf((char) ((int) Math.round((Math.random() * 25.0d) + 97.0d))));
                    break;
                case 2:
                    stringBuffer.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return stringBuffer.toString();
    }
}
