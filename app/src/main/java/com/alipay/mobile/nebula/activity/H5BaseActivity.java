package com.alipay.mobile.nebula.activity;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.framework.BundleContext;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.nebula.provider.H5ReplaceResourceProvider;
import com.alipay.mobile.nebula.providermanager.H5BaseProviderInfo;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5BaseActivity extends BaseFragmentActivity {
    public static final String TAG = "H5BaseActivity";

    /* access modifiers changed from: protected */
    public void replaceResources(Context base) {
        H5Utils.handleTinyAppEnv(this);
        H5Log.d(TAG, "replaceResources");
        BundleContext bundleContext = LauncherApplicationAgent.getInstance().getBundleContext();
        String name = null;
        H5ReplaceResourceProvider h5ReplaceResourceProvider = (H5ReplaceResourceProvider) H5Utils.getProvider(H5ReplaceResourceProvider.class.getName());
        if (h5ReplaceResourceProvider != null) {
            name = h5ReplaceResourceProvider.getReplaceResourcesBundleName();
        }
        if (TextUtils.isEmpty(name)) {
            bundleContext.replaceResources(base, getClass().getName(), H5BaseProviderInfo.nebulabiz, "android-phone-wallet-nebulauc", "android-phone-wallet-nebula");
            return;
        }
        bundleContext.replaceResources(base, getClass().getName(), H5BaseProviderInfo.nebulabiz, "android-phone-wallet-nebulauc", "android-phone-wallet-nebula", name);
    }
}
