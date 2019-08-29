package com.xiaomi.smack.packet;

public class g {
    private String a;

    public g(String str) {
        this.a = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("stream:error (");
        sb.append(this.a);
        sb.append(")");
        return sb.toString();
    }
}
