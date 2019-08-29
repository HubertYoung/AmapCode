package com.alipay.android.phone.inside.offlinecode.gen;

import com.alipay.offlinepay.usersslwrapper.CryptoJNI;

public class TransportDepCodeProtocol extends ICodeProtocol {
    static final int SIGNMODE = 1;

    public String generateCode(String str, String str2) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(getHexTime());
        String sb2 = sb.toString();
        String sm2Sign = sm2Sign(sb2, str2);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("15");
        sb3.append(sm2Sign);
        return sb3.toString();
    }

    private String sm2Sign(String str, String str2) throws Exception {
        return getCryptoJNIResult(CryptoJNI.sm2Sign(str, str2, 1));
    }
}
