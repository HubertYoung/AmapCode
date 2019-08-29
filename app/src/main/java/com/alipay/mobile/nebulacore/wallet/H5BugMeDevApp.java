package com.alipay.mobile.nebulacore.wallet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.nebula.appcenter.apphandler.H5TinyAppDebugMode;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;

public class H5BugMeDevApp extends ActivityApplication {
    public static final String TAG = "H5BugMeDevApp";
    private Bundle a;

    public String getEntryClassName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.a = bundle;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        H5Log.d(TAG, "onStart: " + this.a);
        String tokenValue = H5Utils.getString(this.a, (String) "token");
        String schemeValue = H5Utils.getString(this.a, (String) "scheme");
        String enableBugme = H5Utils.getString(this.a, (String) "enableBugme");
        if (TextUtils.isEmpty(schemeValue)) {
            destroy(null);
        } else if (H5TinyAppDebugMode.enableTinyAppDebugMode()) {
            a(schemeValue);
            destroy(null);
        } else {
            Intent intent = new Intent(H5Utils.getContext(), H5DevAppActivity.class);
            intent.addFlags(65536);
            intent.putExtra("token", tokenValue);
            intent.putExtra("scheme", schemeValue);
            intent.putExtra("enableBugme", enableBugme);
            getMicroApplicationContext().startActivity((MicroApplication) this, intent);
        }
    }

    private static void a(String scheme) {
        Uri schemeUri = H5UrlHelper.parseUrl(scheme);
        if (schemeUri != null) {
            H5Log.d(TAG, "H5TinyAppDebugMode schemeUri : " + schemeUri);
            H5EnvProvider h5EnvProvider = (H5EnvProvider) Nebula.getProviderManager().getProvider(H5EnvProvider.class.getName());
            if (h5EnvProvider != null) {
                h5EnvProvider.goToSchemeService(schemeUri.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart(Bundle bundle) {
        H5Log.d(TAG, "H5BugMeDevApp onRestart");
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        H5Log.d(TAG, "H5BugMeDevApp onStop");
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        H5Log.d(TAG, "H5BugMeDevApp onDestroy");
    }
}
