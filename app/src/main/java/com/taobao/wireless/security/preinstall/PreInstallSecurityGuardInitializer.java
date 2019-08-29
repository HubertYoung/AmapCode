package com.taobao.wireless.security.preinstall;

import android.content.Context;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.initialize.IInitializeComponent;
import com.alibaba.wireless.security.open.initialize.a;

public final class PreInstallSecurityGuardInitializer {
    private PreInstallSecurityGuardInitializer() {
    }

    public static int Initialize(Context context) {
        return Initialize(context, null);
    }

    public static int Initialize(Context context, String str) {
        try {
            IInitializeComponent initializer = SecurityGuardManager.getInitializer();
            if (initializer == null || !(initializer instanceof a)) {
                return 1;
            }
            return ((a) initializer).a(context, str, false);
        } catch (SecException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
