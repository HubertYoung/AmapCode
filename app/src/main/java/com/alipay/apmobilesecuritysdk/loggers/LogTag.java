package com.alipay.apmobilesecuritysdk.loggers;

import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogTag {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;

    public LogTag(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = str7;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime()));
        StringBuilder sb = new StringBuilder(",");
        sb.append(this.a);
        stringBuffer.append(sb.toString());
        StringBuilder sb2 = new StringBuilder(",");
        sb2.append(this.b);
        stringBuffer.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder(",");
        sb3.append(this.c);
        stringBuffer.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder(",");
        sb4.append(this.d);
        stringBuffer.append(sb4.toString());
        if (CommonUtils.isBlank(this.e) || this.e.length() < 20) {
            StringBuilder sb5 = new StringBuilder(",");
            sb5.append(this.e);
            stringBuffer.append(sb5.toString());
        } else {
            StringBuilder sb6 = new StringBuilder(",");
            sb6.append(this.e.substring(0, 20));
            stringBuffer.append(sb6.toString());
        }
        if (CommonUtils.isBlank(this.f) || this.f.length() < 20) {
            StringBuilder sb7 = new StringBuilder(",");
            sb7.append(this.f);
            stringBuffer.append(sb7.toString());
        } else {
            StringBuilder sb8 = new StringBuilder(",");
            sb8.append(this.f.substring(0, 20));
            stringBuffer.append(sb8.toString());
        }
        if (CommonUtils.isBlank(this.g) || this.g.length() < 20) {
            StringBuilder sb9 = new StringBuilder(",");
            sb9.append(this.g);
            stringBuffer.append(sb9.toString());
        } else {
            StringBuilder sb10 = new StringBuilder(",");
            sb10.append(this.g.substring(0, 20));
            stringBuffer.append(sb10.toString());
        }
        return stringBuffer.toString();
    }
}
