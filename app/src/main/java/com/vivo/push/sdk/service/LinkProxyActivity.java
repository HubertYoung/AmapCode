package com.vivo.push.sdk.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import java.util.List;

public class LinkProxyActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null) {
            fat.d("LinkProxyActivity", "enter RequestPermissionsActivity onCreate, intent is null, finish");
            finish();
            return;
        }
        boolean z = false;
        try {
            Window window = getWindow();
            window.setGravity(8388659);
            LayoutParams attributes = window.getAttributes();
            attributes.x = 0;
            attributes.y = 0;
            attributes.height = 1;
            attributes.width = 1;
            window.setAttributes(attributes);
        } catch (Throwable th) {
            fat.b("LinkProxyActivity", "enter onCreate error ", th);
        }
        String packageName = getPackageName();
        StringBuilder sb = new StringBuilder();
        sb.append(hashCode());
        sb.append(" enter onCreate ");
        sb.append(packageName);
        fat.d("LinkProxyActivity", sb.toString());
        if (!"com.vivo.abe".equals(packageName)) {
            try {
                if (intent.getExtras() != null) {
                    Intent intent2 = (Intent) intent.getExtras().get("previous_intent");
                    if (intent2 != null) {
                        PackageManager packageManager = getPackageManager();
                        if (packageManager != null) {
                            List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent2, 576);
                            if (queryIntentServices != null) {
                                if (!queryIntentServices.isEmpty()) {
                                    ResolveInfo resolveInfo = queryIntentServices.get(0);
                                    if (!(resolveInfo == null || resolveInfo.serviceInfo == null || !resolveInfo.serviceInfo.exported)) {
                                        z = true;
                                    }
                                }
                            }
                        }
                    }
                    if (z) {
                        startService(intent2);
                    } else {
                        fat.b((String) "LinkProxyActivity", "service's exported is ".concat(String.valueOf(z)));
                    }
                }
            } catch (Exception e) {
                fat.a("LinkProxyActivity", e.toString(), e);
            }
        } else if (intent == null) {
            try {
                fat.d("LinkProxyActivity", "adapterToService intent is null");
            } catch (Exception e2) {
                fat.a("LinkProxyActivity", e2.toString(), e2);
            }
        } else if (intent.getExtras() == null) {
            fat.d("LinkProxyActivity", "adapterToService getExtras() is null");
        } else {
            Intent intent3 = (Intent) intent.getExtras().get("previous_intent");
            if (intent3 == null) {
                fat.d("LinkProxyActivity", "adapterToService proxyIntent is null");
            } else {
                fbd.a((Context) this, intent3);
            }
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        StringBuilder sb = new StringBuilder();
        sb.append(hashCode());
        sb.append(" onDestory ");
        sb.append(getPackageName());
        fat.d("LinkProxyActivity", sb.toString());
    }
}
