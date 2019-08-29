package com.alipay.mobile.nebulacore.env;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.service.RnService;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ToastProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.wallet.H5WalletWrapper;

public class H5Environment {
    public static final String TAG = "H5Environment";
    private static Context a;

    public static String getSessionId(H5Context h5Context, Bundle bundle) {
        return H5WalletWrapper.getSessionId(h5Context, bundle);
    }

    public static void startActivity(H5Context h5Context, Intent intent) {
        H5WalletWrapper.startActivity(h5Context, intent);
    }

    public static Class<?> getClass(String bundleName, String className) {
        return H5WalletWrapper.getClass(bundleName, className);
    }

    public static Context getContext() {
        if (a == null) {
            a = H5Utils.getContext();
        }
        return a;
    }

    public static void setContext(Context ctx) {
        if (a == null && ctx != null) {
            a = ctx.getApplicationContext();
        }
    }

    public static Resources getResources() {
        return H5WalletWrapper.getResources();
    }

    public static RnService getRnService() {
        return H5WalletWrapper.getRnService();
    }

    public static String getConfig(String configName) {
        H5ConfigProvider provider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (provider != null) {
            return provider.getConfig(configName);
        }
        H5Log.w(TAG, "provider == null");
        return null;
    }

    public static JSONObject getConfigJSONObject(String configName) {
        H5ConfigProvider provider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (provider != null) {
            return provider.getConfigJSONObject(configName);
        }
        H5Log.w(TAG, "provider == null");
        return null;
    }

    public static String getConfigWithProcessCache(String configName) {
        H5ConfigProvider provider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (provider != null) {
            return provider.getConfigWithProcessCache(configName);
        }
        H5Log.w(TAG, "provider == null");
        return null;
    }

    public static void showToast(Context context, String text, int duration) {
        H5ToastProvider h5ToastProvider = (H5ToastProvider) H5ProviderManagerImpl.getInstance().getProvider(H5ToastProvider.class.getName());
        if (h5ToastProvider != null) {
            h5ToastProvider.makeToast(context, text, duration);
        } else {
            Toast.makeText(context, text, duration).show();
        }
    }

    public static boolean isInWallet() {
        return H5Utils.isInWallet();
    }
}
