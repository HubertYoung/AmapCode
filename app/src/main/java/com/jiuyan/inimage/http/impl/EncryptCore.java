package com.jiuyan.inimage.http.impl;

import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inalipay.ilisya.Ilisya;
import com.jiuyan.inimage.http.interfaces.IEncryptAction;
import com.xiaomi.mipush.sdk.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

public class EncryptCore implements IEncryptAction {
    private HashMap<String, String> mEncryptGet = new HashMap<>();
    private HashMap<String, String> mEncryptPost = new HashMap<>();
    private int mMethod;

    public EncryptCore(int i) {
        this.mMethod = i;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void putEncrypt(String str, String str2) {
        switch (this.mMethod) {
            case 0:
                putEncryptGet(str, str2);
                return;
            case 1:
                putEncryptPost(str, str2);
                return;
            default:
                return;
        }
    }

    public void putEncryptGet(String str, String str2) {
        this.mEncryptGet.put(str, str2);
    }

    public void putEncryptPost(String str, String str2) {
        this.mEncryptPost.put(str, str2);
    }

    public String fetchDcarg() {
        return encrypt(order(this.mEncryptGet), order(this.mEncryptPost));
    }

    public String encrypt(String str, String str2) {
        return Ilisya.instance().createHttpRequestString(str, str2);
    }

    private static String order(HashMap<String, String> hashMap) {
        StringBuilder sb = new StringBuilder();
        for (Entry next : hashMap.entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            if (!TextUtils.isEmpty(str2)) {
                sb.append(str).append("=").append(urlEncode(str2)).append("&");
            }
        }
        if (sb.toString().length() > 0) {
            return sb.toString().substring(0, sb.toString().lastIndexOf("&"));
        }
        return "";
    }

    private static String urlEncode(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                return URLEncoder.encode(str, "UTF-8").replace("*", "%2A").replace("+", "%20").replace("%7E", Constants.WAVE_SEPARATOR);
            }
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
