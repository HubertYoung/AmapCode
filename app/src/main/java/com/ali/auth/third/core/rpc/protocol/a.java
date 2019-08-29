package com.ali.auth.third.core.rpc.protocol;

import java.util.Random;

public class a {
    public static String a(int i) {
        String str;
        double d;
        double d2;
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            switch (random.nextInt(3)) {
                case 0:
                    d = Math.random() * 25.0d;
                    d2 = 65.0d;
                    break;
                case 1:
                    d = Math.random() * 25.0d;
                    d2 = 97.0d;
                    break;
                case 2:
                    str = String.valueOf(new Random().nextInt(10));
                    break;
            }
            str = String.valueOf((char) ((int) Math.round(d + d2)));
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }
}
