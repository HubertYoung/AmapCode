package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.amap.bundle.appupgrade.AppUpgradeController;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.mapstorage.MapSharePreference;
import java.util.Date;

/* renamed from: jh reason: default package */
/* compiled from: AppUpgradeChecker */
public final class jh {
    public final AppUpgradeController a;
    private Context b;

    public jh(Activity activity) {
        this.a = new AppUpgradeController(activity);
        this.b = activity;
    }

    public final void a(boolean z) {
        boolean z2;
        AppUpgradeController appUpgradeController = this.a;
        SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
        boolean z3 = sharedPrefs.getBoolean("isForceUpdateApp", false);
        String string = sharedPrefs.getString("needForceUpdateVersion", "");
        boolean z4 = true;
        if (z3) {
            if (string.equals(a.a().a)) {
                try {
                    appUpgradeController.a = jt.b();
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                if (appUpgradeController.a != null) {
                    appUpgradeController.b = AppUpgradeController.a(appUpgradeController.a);
                    appUpgradeController.a(appUpgradeController.a.d);
                    if (!(!TextUtils.isEmpty(appUpgradeController.a.d) ? appUpgradeController.a(appUpgradeController.a, true, false) : false)) {
                        appUpgradeController.b(appUpgradeController.a, true, false);
                    }
                }
            } else {
                appUpgradeController.b();
            }
            z2 = true;
        } else {
            z2 = false;
        }
        long j = this.b.getSharedPreferences("appinit", 0).getLong("appinit", -1);
        if (j > 0) {
            Date date = new Date();
            Date date2 = new Date(j);
            if (date.getYear() == date2.getYear() && date.getMonth() == date2.getMonth() && date.getDay() == date2.getDay()) {
                z4 = false;
            }
        }
        if ((z || z4 || !FunctionSupportConfiger.getInst().isLoaded()) && !z2) {
            if (z4) {
                try {
                    AppUpgradeController.a();
                    this.a.e = false;
                } catch (Exception e2) {
                    kf.a((Throwable) e2);
                    return;
                }
            }
            this.a.b();
        }
    }
}
