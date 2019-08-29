package com.alibaba.wireless.security.framework;

import android.content.pm.PackageInfo;
import com.alibaba.wireless.security.framework.utils.b;
import java.io.File;
import org.json.JSONObject;

public class a {
    public PackageInfo a = null;
    private JSONObject b = null;
    private String c;

    public a(String str) {
        this.c = str;
    }

    public String a(String str) {
        try {
            return b().getString(str);
        } catch (Exception unused) {
            return "";
        }
    }

    public boolean a() {
        try {
            return new File(this.c).exists();
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x009b A[SYNTHETIC, Splitter:B:19:0x009b] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a8 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.content.pm.PackageInfo r5, java.lang.String r6) {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L_0x00aa
            if (r6 != 0) goto L_0x0006
            return r0
        L_0x0006:
            org.json.JSONObject r6 = new org.json.JSONObject
            r6.<init>()
            java.lang.String r1 = "version"
            java.lang.String r2 = r5.versionName     // Catch:{ Exception -> 0x00a9 }
            r6.put(r1, r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = "hasso"
            android.content.pm.ApplicationInfo r2 = r5.applicationInfo     // Catch:{ Exception -> 0x00a9 }
            android.os.Bundle r2 = r2.metaData     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r3 = "hasso"
            boolean r2 = r2.getBoolean(r3, r0)     // Catch:{ Exception -> 0x00a9 }
            r6.put(r1, r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = "pluginname"
            android.content.pm.ApplicationInfo r2 = r5.applicationInfo     // Catch:{ Exception -> 0x00a9 }
            android.os.Bundle r2 = r2.metaData     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r3 = "pluginname"
            java.lang.String r2 = r2.getString(r3)     // Catch:{ Exception -> 0x00a9 }
            r6.put(r1, r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = "pluginclass"
            android.content.pm.ApplicationInfo r2 = r5.applicationInfo     // Catch:{ Exception -> 0x00a9 }
            android.os.Bundle r2 = r2.metaData     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r3 = "pluginclass"
            java.lang.String r2 = r2.getString(r3)     // Catch:{ Exception -> 0x00a9 }
            r6.put(r1, r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = "dependencies"
            android.content.pm.ApplicationInfo r2 = r5.applicationInfo     // Catch:{ Exception -> 0x00a9 }
            android.os.Bundle r2 = r2.metaData     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r3 = "dependencies"
            java.lang.String r2 = r2.getString(r3)     // Catch:{ Exception -> 0x00a9 }
            r6.put(r1, r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = "weakdependencies"
            android.content.pm.ApplicationInfo r2 = r5.applicationInfo     // Catch:{ Exception -> 0x00a9 }
            android.os.Bundle r2 = r2.metaData     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r3 = "weakdependencies"
            java.lang.String r2 = r2.getString(r3)     // Catch:{ Exception -> 0x00a9 }
            r6.put(r1, r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = "reversedependencies"
            android.content.pm.ApplicationInfo r2 = r5.applicationInfo     // Catch:{ Exception -> 0x00a9 }
            android.os.Bundle r2 = r2.metaData     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r3 = "reversedependencies"
            java.lang.String r2 = r2.getString(r3)     // Catch:{ Exception -> 0x00a9 }
            r6.put(r1, r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = "thirdpartyso"
            android.content.pm.ApplicationInfo r5 = r5.applicationInfo     // Catch:{ Exception -> 0x00a9 }
            android.os.Bundle r5 = r5.metaData     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r2 = "thirdpartyso"
            boolean r5 = r5.getBoolean(r2)     // Catch:{ Exception -> 0x00a9 }
            r6.put(r1, r5)     // Catch:{ Exception -> 0x00a9 }
            r5 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0094 }
            java.lang.String r2 = r4.c     // Catch:{ Exception -> 0x0094 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0094 }
            boolean r5 = r1.exists()     // Catch:{ Exception -> 0x0095 }
            if (r5 != 0) goto L_0x009e
            r1.createNewFile()     // Catch:{ Exception -> 0x0095 }
            goto L_0x009e
        L_0x0094:
            r1 = r5
        L_0x0095:
            boolean r5 = r1.exists()
            if (r5 != 0) goto L_0x009e
            r1.createNewFile()     // Catch:{ Exception -> 0x009e }
        L_0x009e:
            java.lang.String r5 = r6.toString()
            boolean r5 = com.alibaba.wireless.security.framework.utils.b.a(r1, r5)
            if (r5 != 0) goto L_0x00a9
            return r0
        L_0x00a9:
            r0 = 1
        L_0x00aa:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.a.a(android.content.pm.PackageInfo, java.lang.String):boolean");
    }

    public JSONObject b() {
        if (this.b != null) {
            return this.b;
        }
        JSONObject jSONObject = null;
        try {
            String a2 = b.a(new File(this.c));
            if (a2 != null && a2.length() > 0) {
                jSONObject = new JSONObject(a2);
            }
        } catch (Exception unused) {
        }
        this.b = jSONObject;
        return jSONObject;
    }
}
