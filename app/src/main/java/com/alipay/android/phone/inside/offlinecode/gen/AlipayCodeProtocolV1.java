package com.alipay.android.phone.inside.offlinecode.gen;

public class AlipayCodeProtocolV1 extends ICodeProtocol {
    public String generateCode(String str, String str2) throws Exception {
        str.substring(0, 2);
        String concat = "0101".concat(String.valueOf(str.substring(2, 4)));
        String substring = str.substring(4, str.length());
        StringBuilder sb = new StringBuilder();
        sb.append(concat);
        sb.append(substring);
        return new AlipayCodeProtocolV2().generateCode(sb.toString(), str2);
    }
}
