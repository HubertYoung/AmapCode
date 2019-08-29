package com.alipay.mobile.nebulacore.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.widget.Toast;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.appcenter.H5IApplicationInstallerImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.HashMap;
import java.util.Map;

public class H5NebulaAppActivity extends BaseFragmentActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        Bundle bundle = null;
        try {
            bundle = getIntent().getExtras();
        } catch (Throwable throwable) {
            H5Log.e((String) "H5NebulaAppActivity", throwable);
        }
        final String appId = H5Utils.getString(bundle, H5IApplicationInstallerImpl.NEBULA_FALLBACK_APP_ID);
        H5Log.d("H5NebulaAppActivity", "appId " + appId);
        final H5LoadingDialog h5LoadingDialog = new H5LoadingDialog(this);
        h5LoadingDialog.setCancelable(true);
        h5LoadingDialog.setMessage(H5Environment.getResources().getString(R.string.h5_loading_txt));
        h5LoadingDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                if (!H5NebulaAppActivity.this.isFinishing()) {
                    H5NebulaAppActivity.this.finish();
                }
            }
        });
        h5LoadingDialog.show();
        final H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider == null) {
            finish();
            return;
        }
        final Bundle finalBundle = bundle;
        H5Utils.getExecutor("RPC").execute(new Runnable() {
            public void run() {
                Map appMap = new HashMap();
                appMap.put(appId, null);
                h5AppProvider.updateApp(H5UpdateAppParam.newBuilder().setAppMap(appMap).setUpdateCallback(new H5UpdateAppCallback() {
                    public void onResult(final boolean success, boolean isLimit) {
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                if (!H5NebulaAppActivity.this.isFinishing()) {
                                    H5Log.d("H5NebulaAppActivity", "update result:" + success);
                                    if (h5AppProvider.getAppInfo(appId) != null) {
                                        if (h5LoadingDialog != null) {
                                            h5LoadingDialog.dismiss();
                                        }
                                        finalBundle.remove(H5IApplicationInstallerImpl.NEBULA_FALLBACK_APP_ID);
                                        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, appId, finalBundle);
                                        H5NebulaAppActivity.this.finish();
                                        return;
                                    }
                                    H5Utils.runOnMain(new Runnable() {
                                        public void run() {
                                            Toast.makeText(H5Utils.getContext(), H5Environment.getResources().getString(R.string.h5_update_fail), 0).show();
                                            if (h5LoadingDialog != null) {
                                                h5LoadingDialog.dismiss();
                                            }
                                            H5NebulaAppActivity.this.finish();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }).build());
            }
        });
    }
}
