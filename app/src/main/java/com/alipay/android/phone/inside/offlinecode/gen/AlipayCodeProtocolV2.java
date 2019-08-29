package com.alipay.android.phone.inside.offlinecode.gen;

import com.alipay.offlinepay.usersslwrapper.CryptoJNI;

public class AlipayCodeProtocolV2 extends ICodeProtocol {
    static final String ECC_NAME = "secp192k1";

    public String generateCode(String str, String str2) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(getUserInfo());
        String sb2 = sb.toString();
        String ecdsaSign = ecdsaSign(sha1Digest(sb2), str2);
        String hexDataLength = getHexDataLength(ecdsaSign);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(hexDataLength);
        sb3.append(ecdsaSign);
        return sb3.toString();
    }

    private String ecdsaSign(String str, String str2) throws Exception {
        return getCryptoJNIResult(CryptoJNI.ecdsaSign(str, str2, ECC_NAME));
    }

    private String getUserInfo() {
        StringBuilder sb = new StringBuilder("04");
        sb.append(getHexTime());
        return sb.toString();
    }
}
