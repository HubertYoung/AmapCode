package com.xiaomi.push.mpcd.job;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.xiaomi.channel.commonutils.android.a;
import com.xiaomi.xmpush.thrift.d;

public class c extends f {
    public c(Context context, int i) {
        super(context, i);
    }

    public int a() {
        return 4;
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        try {
            PackageManager packageManager = this.d.getPackageManager();
            int i = 0;
            for (PackageInfo next : packageManager.getInstalledPackages(128)) {
                if ((next.applicationInfo.flags & 1) == 0) {
                    if (sb.length() > 0) {
                        sb.append(";");
                    }
                    String charSequence = next.applicationInfo.loadLabel(packageManager).toString();
                    String f = a.f(this.d, next.packageName);
                    sb.append(charSequence);
                    sb.append(",");
                    sb.append(next.packageName);
                    sb.append(",");
                    sb.append(next.versionName);
                    sb.append(",");
                    sb.append(next.versionCode);
                    sb.append(",");
                    sb.append(f);
                    i++;
                    if (i >= 200) {
                        break;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    public d d() {
        return d.AppInstallList;
    }
}
