package com.ali.user.mobile.util;

import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.rsa.Rsa;

public class RsaUtils {
    public static String a(String str) {
        return Rsa.a(str, Rsa.a(AliUserInit.b()).rsaPK);
    }
}
