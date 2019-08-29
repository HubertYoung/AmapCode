package com.alipay.android.phone.inside.protobuf.wire;

final class Preconditions {
    static void a(Object obj, String str) {
        if (obj == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" == null");
            throw new NullPointerException(sb.toString());
        }
    }
}
