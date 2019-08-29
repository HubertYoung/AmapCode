package com.taobao.accs.utl;

public class AppMonitorAdapter {
    public static void commitAlarmSuccess(String str, String str2, String str3) {
        bj bjVar = new bj();
        bjVar.e = str;
        bjVar.f = str2;
        bjVar.b = str3;
        bjVar.a = true;
        x.a().a(bjVar);
    }

    public static void commitAlarmFail(String str, String str2, String str3, String str4, String str5) {
        bj bjVar = new bj();
        bjVar.e = str;
        bjVar.f = str2;
        bjVar.b = str3;
        bjVar.c = str4;
        bjVar.d = str5;
        bjVar.a = false;
        x.a().a(bjVar);
    }

    public static void commitCount(String str, String str2, String str3, double d) {
        bk bkVar = new bk();
        bkVar.c = str;
        bkVar.d = str2;
        bkVar.a = str3;
        bkVar.b = d;
        x.a().a(bkVar);
    }
}
