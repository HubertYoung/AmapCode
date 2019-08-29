package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.util.Pair;
import com.uc.webview.export.cyclone.UCElapseTime;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat;
import java.util.List;

/* compiled from: ProGuard */
public final class bt extends q {
    public final void run() {
        long j;
        setup(UCCore.OPTION_DEX_FILE_PATH, null);
        Context context = (Context) this.mOptions.get(UCCore.OPTION_CONTEXT);
        List<UCMPackageInfo> a = UCMPackageInfo.a(context, this.mOptions);
        if (a.size() != 1) {
            throw new UCSetupException(3015, (String) "1 UCMPackage expected for thick mode.");
        }
        Integer num = (Integer) this.mOptions.get(UCCore.OPTION_VERIFY_POLICY);
        UCMPackageInfo uCMPackageInfo = a.get(0);
        this.mUCM = uCMPackageInfo;
        long j2 = 0;
        if (num == null || (num.intValue() & 8) == 0) {
            j = 0;
        } else {
            UCElapseTime uCElapseTime = new UCElapseTime();
            a(uCMPackageInfo, context, UCMPackageInfo.class.getClassLoader(), num.intValue());
            j2 = uCElapseTime.getMilisCpu() + 0;
            j = 0 + uCElapseTime.getMilis();
        }
        if (!(num == null || (num.intValue() & 32) == 0)) {
            UCElapseTime uCElapseTime2 = new UCElapseTime();
            b(uCMPackageInfo, context, UCMPackageInfo.class.getClassLoader(), num.intValue());
            j2 += uCElapseTime2.getMilisCpu();
            j += uCElapseTime2.getMilis();
        }
        long j3 = j2;
        a(IWaStat.SETUP_TASK_VERIFY, false, num, "thick", j, j3);
        this.mCL = UCMPackageInfo.class.getClassLoader();
        this.a = false;
        this.b = false;
        callbackStat(new Pair(IWaStat.SETUP_SUCCESS_SETUPED, null));
    }
}
