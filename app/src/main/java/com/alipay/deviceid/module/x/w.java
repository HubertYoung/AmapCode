package com.alipay.deviceid.module.x;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class w {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;

    public w(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = str7;
    }

    public final String toString() {
        String str;
        StringBuilder sb;
        String str2;
        StringBuilder sb2;
        String str3;
        StringBuilder sb3;
        StringBuffer stringBuffer = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime()));
        StringBuilder sb4 = new StringBuilder(",");
        sb4.append(this.a);
        stringBuffer.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder(",");
        sb5.append(this.b);
        stringBuffer.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder(",");
        sb6.append(this.c);
        stringBuffer.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder(",");
        sb7.append(this.d);
        stringBuffer.append(sb7.toString());
        if (e.a(this.e) || this.e.length() < 20) {
            sb = new StringBuilder(",");
            str = this.e;
        } else {
            sb = new StringBuilder(",");
            str = this.e.substring(0, 20);
        }
        sb.append(str);
        stringBuffer.append(sb.toString());
        if (e.a(this.f) || this.f.length() < 20) {
            sb2 = new StringBuilder(",");
            str2 = this.f;
        } else {
            sb2 = new StringBuilder(",");
            str2 = this.f.substring(0, 20);
        }
        sb2.append(str2);
        stringBuffer.append(sb2.toString());
        if (e.a(this.g) || this.g.length() < 20) {
            sb3 = new StringBuilder(",");
            str3 = this.g;
        } else {
            sb3 = new StringBuilder(",");
            str3 = this.g.substring(0, 20);
        }
        sb3.append(str3);
        stringBuffer.append(sb3.toString());
        return stringBuffer.toString();
    }
}
