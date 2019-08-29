package com.alipay.mobile.common.security;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Des {
    public Des() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static String encrypt(String content, String strkey) {
        return TextUtils.isEmpty(content) ? content : doFinal(1, content, strkey);
    }

    public static String decrypt(String content, String strkey) {
        return TextUtils.isEmpty(content) ? content : doFinal(2, content, strkey);
    }

    public static String doFinal(int opmode, String content, String strkey) {
        byte[] plaintext;
        try {
            Key key = new SecretKeySpec(strkey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(opmode, key);
            if (opmode == 2) {
                plaintext = Base64.decode(content, 0);
            } else {
                plaintext = content.getBytes("UTF-8");
            }
            byte[] output = cipher.doFinal(plaintext);
            if (opmode == 2) {
                return new String(output);
            }
            return Base64.encodeToString(output, 0);
        } catch (Exception e) {
            Log.w("Des", "opmode=" + opmode, e);
            return null;
        }
    }

    public static byte[] encrypt(byte[] content, String strkey) {
        if (content == null || content.length == 0) {
            return null;
        }
        return doFinal(1, content, strkey);
    }

    public static byte[] decrypt(byte[] content, String strkey) {
        if (content == null || content.length == 0) {
            return null;
        }
        return doFinal(2, content, strkey);
    }

    public static byte[] doFinal(int opmode, byte[] content, String strkey) {
        byte[] plaintext;
        try {
            Key key = new SecretKeySpec(strkey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(opmode, key);
            if (opmode == 2) {
                plaintext = Base64.decode(content, 0);
            } else {
                plaintext = content;
            }
            byte[] output = cipher.doFinal(plaintext);
            if (opmode == 2) {
                return output;
            }
            return Base64.encode(output, 0);
        } catch (Exception e) {
            Log.w("Des", "opmode=" + opmode, e);
            return null;
        }
    }
}
