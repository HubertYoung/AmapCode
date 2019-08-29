package com.ali.auth.third.core.storage.a;

import com.ali.auth.third.core.util.CommonUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class b {
    public static String a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            return CommonUtils.getHashString(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
