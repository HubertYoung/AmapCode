package defpackage;

import android.content.Context;
import android.content.pm.PackageInfo;

/* renamed from: enj reason: default package */
/* compiled from: SoHotfixContext */
public final class enj {
    public final Context a;
    final String b;
    final String c;
    String d;
    public final String e;
    private final String f = this.a.getPackageName();
    private String g;
    private int h;

    public enj(Context context) {
        this.a = context;
        StringBuilder sb = new StringBuilder("/data/data/");
        sb.append(this.f);
        sb.append("/lib");
        this.b = sb.toString();
        try {
            PackageInfo packageInfo = this.a.getPackageManager().getPackageInfo(this.f, 0);
            this.g = packageInfo.versionName;
            this.h = packageInfo.versionCode;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.g);
        sb2.append(".");
        sb2.append(this.h);
        this.d = sb2.toString();
        StringBuilder sb3 = new StringBuilder("/data/data/");
        sb3.append(this.f);
        sb3.append("/files/lib_hotfix/");
        sb3.append(this.d);
        this.c = sb3.toString();
        StringBuilder sb4 = new StringBuilder("/data/data/");
        sb4.append(this.f);
        sb4.append("/files/lib_hotfix$/");
        sb4.append(this.d);
        this.e = sb4.toString();
    }
}
