package com.ali.user.mobile.register.model;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.HashMap;
import java.util.Map;

public class SimpleRequest {
    public long a = 0;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public boolean g = true;
    public Map<String, String> h = new HashMap();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(Token.SEPARATOR);
        sb.append(this.b);
        return sb.toString();
    }
}
