package com.alipay.mobile.nebulacore.wallet;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.BundleContext;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.RnService;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5WalletWrapper {
    public static final String TAG = "H5WalletWrapper";

    public static Resources getResources() {
        return LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-nebula");
    }

    public static RnService getRnService() {
        return (RnService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(RnService.class.getName());
    }

    public static Class<?> getClass(String bundleName, String className) {
        H5Log.d("H5WalletWrapper", "getClass " + bundleName + ":" + className);
        try {
            BundleContext bundleContext = LauncherApplicationAgent.getInstance().getBundleContext();
            bundleContext.loadBundle(bundleName);
            ClassLoader classLoader = bundleContext.findClassLoaderByBundleName(bundleName);
            if (classLoader != null) {
                return classLoader.loadClass(className);
            }
            return null;
        } catch (Throwable t) {
            if (!H5Environment.isInWallet()) {
                return null;
            }
            H5Log.e("H5WalletWrapper", "failed to load class bundle.", t);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0050 A[Catch:{ Exception -> 0x0023 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void startActivity(com.alipay.mobile.h5container.api.H5Context r6, android.content.Intent r7) {
        /*
            if (r7 != 0) goto L_0x000a
            java.lang.String r3 = "H5WalletWrapper"
            java.lang.String r4 = "invalid event parameter"
            com.alipay.mobile.nebula.util.H5Log.w(r3, r4)
        L_0x0009:
            return
        L_0x000a:
            com.alipay.mobile.framework.app.MicroApplication r2 = a(r6)
            if (r2 == 0) goto L_0x002c
            java.lang.String r3 = "H5WalletWrapper"
            java.lang.String r4 = "microApp != null getMicroApplicationContext().startActivity"
            com.alipay.mobile.nebula.util.H5Log.debug(r3, r4)     // Catch:{ Exception -> 0x0023 }
            com.alipay.mobile.framework.LauncherApplicationAgent r3 = com.alipay.mobile.framework.LauncherApplicationAgent.getInstance()     // Catch:{ Exception -> 0x0023 }
            com.alipay.mobile.framework.MicroApplicationContext r3 = r3.getMicroApplicationContext()     // Catch:{ Exception -> 0x0023 }
            r3.startActivity(r2, r7)     // Catch:{ Exception -> 0x0023 }
            goto L_0x0009
        L_0x0023:
            r1 = move-exception
            java.lang.String r3 = "H5WalletWrapper"
            java.lang.String r4 = "startActivity exception"
            com.alipay.mobile.nebula.util.H5Log.e(r3, r4, r1)
            goto L_0x0009
        L_0x002c:
            if (r6 == 0) goto L_0x0059
            android.content.Context r3 = r6.getContext()     // Catch:{ Exception -> 0x0023 }
            if (r3 == 0) goto L_0x0059
            android.content.Context r0 = r6.getContext()     // Catch:{ Exception -> 0x0023 }
        L_0x0038:
            java.lang.String r3 = "H5WalletWrapper"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0023 }
            java.lang.String r5 = "context "
            r4.<init>(r5)     // Catch:{ Exception -> 0x0023 }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0023 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0023 }
            com.alipay.mobile.nebula.util.H5Log.debug(r3, r4)     // Catch:{ Exception -> 0x0023 }
            boolean r3 = r0 instanceof android.app.Activity     // Catch:{ Exception -> 0x0023 }
            if (r3 != 0) goto L_0x0055
            r3 = 268435456(0x10000000, float:2.5243549E-29)
            r7.setFlags(r3)     // Catch:{ Exception -> 0x0023 }
        L_0x0055:
            r0.startActivity(r7)     // Catch:{ Exception -> 0x0023 }
            goto L_0x0009
        L_0x0059:
            android.content.Context r0 = com.alipay.mobile.nebulacore.env.H5Environment.getContext()     // Catch:{ Exception -> 0x0023 }
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.wallet.H5WalletWrapper.startActivity(com.alipay.mobile.h5container.api.H5Context, android.content.Intent):void");
    }

    public static String getSessionId(H5Context h5Context, Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String sessionId = H5Utils.getString(bundle, (String) "sessionId");
        if (!TextUtils.isEmpty(sessionId)) {
            return sessionId;
        }
        if (h5Context instanceof WalletContext) {
            MicroApplication app = ((WalletContext) h5Context).getMicroApplication();
            if (app != null && !TextUtils.isEmpty(app.getAppId())) {
                String appId = app.getAppId();
                if (!(app instanceof H5Application) || !TextUtils.equals("H5Activity", H5Utils.getString(bundle, (String) H5Param.CREATEPAGESENCE)) || BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_createPageSence_sessionId"))) {
                    bundle.putString("appId", appId);
                } else {
                    H5Log.d("H5WalletWrapper", app + " is createPage in H5App not merge appId");
                }
                sessionId = "h5session_" + appId;
            }
        }
        if (TextUtils.isEmpty(sessionId)) {
            return "h5session_default";
        }
        return sessionId;
    }

    static MicroApplication a(H5Context h5Context) {
        if (h5Context == null) {
            return null;
        }
        Context context = h5Context.getContext();
        MicroApplication app = null;
        if (context instanceof BaseActivity) {
            app = ((BaseActivity) context).getActivityApplication();
        } else if (context instanceof BaseFragmentActivity) {
            app = ((BaseFragmentActivity) context).getActivityApplication();
        }
        if (app != null || !(h5Context instanceof WalletContext)) {
            return app;
        }
        return ((WalletContext) h5Context).getMicroApplication();
    }
}
