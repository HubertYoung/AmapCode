package com.alipay.mobile.framework.app;

import android.os.Bundle;

public interface IApplicationInstaller {

    public interface IApplicationInstallCallback {
        void installed(boolean z);

        void reportEvent(String str, Bundle bundle);
    }

    void installApplication(String str, IApplicationInstallCallback iApplicationInstallCallback, Bundle bundle);
}
