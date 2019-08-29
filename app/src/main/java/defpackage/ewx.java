package defpackage;

import android.os.Bundle;
import java.util.ArrayList;

/* renamed from: ewx reason: default package */
/* compiled from: BundleWapper */
public final class ewx {
    public Bundle a;
    String b;
    private String c;

    public ewx(String str, String str2, Bundle bundle) {
        this.b = str;
        this.c = str2;
        this.a = bundle;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001b, code lost:
        if (android.text.TextUtils.isEmpty(r2) == false) goto L_0x001f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.ewx a(android.content.Intent r5) {
        /*
            r0 = 0
            if (r5 != 0) goto L_0x000b
            java.lang.String r5 = "BundleWapper"
            java.lang.String r1 = "create error : intent is null"
            defpackage.fat.a(r5, r1)
            return r0
        L_0x000b:
            android.os.Bundle r1 = r5.getExtras()
            if (r1 == 0) goto L_0x001e
            java.lang.String r2 = "client_pkgname"
            java.lang.String r2 = r1.getString(r2)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x001e
            goto L_0x001f
        L_0x001e:
            r2 = r0
        L_0x001f:
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L_0x002c
            java.lang.String r3 = "BundleWapper"
            java.lang.String r4 = "create warning: pkgName is null"
            defpackage.fat.b(r3, r4)
        L_0x002c:
            java.lang.String r3 = r5.getPackage()
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 == 0) goto L_0x0054
            android.content.ComponentName r3 = r5.getComponent()
            if (r3 != 0) goto L_0x003e
        L_0x003c:
            r3 = r0
            goto L_0x0047
        L_0x003e:
            android.content.ComponentName r5 = r5.getComponent()
            java.lang.String r0 = r5.getPackageName()
            goto L_0x003c
        L_0x0047:
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            if (r5 == 0) goto L_0x0054
            java.lang.String r5 = "BundleWapper"
            java.lang.String r0 = "create warning: targetPkgName is null"
            defpackage.fat.b(r5, r0)
        L_0x0054:
            ewx r5 = new ewx
            r5.<init>(r2, r3, r1)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ewx.a(android.content.Intent):ewx");
    }

    public final void a(String str, int i) {
        if (this.a == null) {
            this.a = new Bundle();
        }
        this.a.putInt(str, i);
    }

    public final void a(String str, long j) {
        if (this.a == null) {
            this.a = new Bundle();
        }
        this.a.putLong(str, j);
    }

    public final void a(String str, String str2) {
        if (this.a == null) {
            this.a = new Bundle();
        }
        this.a.putString(str, str2);
    }

    public final void a(String str, ArrayList<String> arrayList) {
        if (this.a == null) {
            this.a = new Bundle();
        }
        this.a.putStringArrayList(str, arrayList);
    }

    public final String a(String str) {
        if (this.a == null) {
            return null;
        }
        return this.a.getString(str);
    }

    public final int b(String str, int i) {
        return this.a == null ? i : this.a.getInt(str, i);
    }

    public final ArrayList<String> b(String str) {
        if (this.a == null) {
            return null;
        }
        return this.a.getStringArrayList(str);
    }

    public final long b(String str, long j) {
        return this.a == null ? j : this.a.getLong(str, j);
    }
}
