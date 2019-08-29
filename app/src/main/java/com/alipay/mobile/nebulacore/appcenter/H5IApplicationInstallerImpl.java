package com.alipay.mobile.nebulacore.appcenter;

import android.content.Intent;
import android.os.Bundle;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.app.IApplicationInstaller;
import com.alipay.mobile.framework.app.IApplicationInstaller.IApplicationInstallCallback;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.ui.H5NebulaAppActivity;

public class H5IApplicationInstallerImpl implements IApplicationInstaller {
    public static String NEBULA_FALLBACK_APP_ID = "NebulaFallBackAppId";

    public void installApplication(final String appId, final IApplicationInstallCallback iApplicationInstallCallback, final Bundle bundle) {
        H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
            public void run() {
                Bundle start = bundle;
                H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                if (h5AppProvider == null) {
                    return;
                }
                if (h5AppProvider.getAppInfo(appId) != null) {
                    H5Log.d("H5IApplicationInstallerImpl", " appId " + appId + " is install");
                    H5IApplicationInstallerImpl.b(appId);
                    iApplicationInstallCallback.installed(true);
                    return;
                }
                iApplicationInstallCallback.installed(false);
                Intent intent = new Intent(H5Utils.getContext(), H5NebulaAppActivity.class);
                if (start == null) {
                    start = new Bundle();
                }
                start.putString(H5IApplicationInstallerImpl.NEBULA_FALLBACK_APP_ID, appId);
                intent.putExtras(start);
                intent.setFlags(268435456);
                H5Utils.getContext().startActivity(intent);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void b(String appId) {
        ApplicationDescription appDescription = new ApplicationDescription();
        appDescription.setAppId(appId);
        appDescription.setEngineType("H5App");
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().addDescription(appDescription);
    }
}
