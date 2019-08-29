package com.alipay.mobile.nebulacore.wallet;

import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.activity.H5BaseActivity;
import com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppInfo;
import com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList;
import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import com.alipay.mobile.nebula.appcenter.apphandler.H5TinyAppDebugMode;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.provider.H5BugMeRpcAuthProvider;
import com.alipay.mobile.nebula.provider.H5BugMeRpcAuthProvider.AuthRpcCallback;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5LoadingDialog;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5DevAppActivity extends H5BaseActivity {
    /* access modifiers changed from: private */
    public String a = "";
    private boolean b = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        if (VERSION.SDK_INT != 26) {
            setRequestedOrientation(1);
        }
        super.onCreate(savedInstanceState);
        H5Log.d(H5BugMeDevApp.TAG, "H5DevAppActivity onCreate " + getIntent().getExtras());
        String token = getIntent().getStringExtra("token");
        String scheme = getIntent().getStringExtra("scheme");
        final Uri schemeUri = H5UrlHelper.parseUrl(scheme);
        if (schemeUri == null) {
            finish();
            return;
        }
        this.b = "true".equals(getIntent().getStringExtra("enableBugme"));
        this.a = getValue(schemeUri, "appId");
        String nbsource = getValue(schemeUri, "nbsource");
        String nbsn = getValue(schemeUri, "nbsn");
        String nbsv = getValue(schemeUri, H5PreferAppList.nbsv);
        final H5DevAppInfo h5DevAppInfo = new H5DevAppInfo();
        h5DevAppInfo.nbsn = nbsn;
        h5DevAppInfo.nbsv = nbsv;
        H5Log.d(H5BaseActivity.TAG, "devAppId " + this.a + " nbsource:" + nbsource + " nbsn:" + nbsn + " nbsv:" + nbsv + " enableBugme:" + this.b);
        final boolean containDebug = TextUtils.equals(nbsource, "debug");
        if (!Nebula.DEBUG || !H5DevConfig.getBooleanConfig(H5DevConfig.h5_read_use_dev_app_config, false) || !containDebug) {
            final H5LoadingDialog h5LoadingDialog = (H5LoadingDialog) Nebula.getProviderManager().getProvider(H5LoadingDialog.class.getName());
            if (h5LoadingDialog != null) {
                h5LoadingDialog.showLoading(this, "请求数据中");
            }
            H5BugMeRpcAuthProvider bugMeRpcAuthProvider = (H5BugMeRpcAuthProvider) Nebula.getProviderManager().getProvider(H5BugMeRpcAuthProvider.class.getName());
            if (bugMeRpcAuthProvider != null) {
                bugMeRpcAuthProvider.rpcAuth(this.a, nbsn, scheme, token, new AuthRpcCallback() {
                    public void onResponse(boolean pass, boolean isSuperUser, String[] domainWhiteList) {
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                if (h5LoadingDialog != null) {
                                    h5LoadingDialog.hide();
                                }
                            }
                        });
                        if (pass) {
                            if (containDebug) {
                                H5DevAppList.getInstance().add(H5DevAppActivity.this.a, h5DevAppInfo);
                            }
                            H5DevAppActivity.this.a(schemeUri, domainWhiteList);
                        } else {
                            if (containDebug) {
                                H5DevAppList.getInstance().remove(H5DevAppActivity.this.a);
                            }
                            H5Utils.runOnMain(new Runnable() {
                                public void run() {
                                    Toast.makeText(H5Utils.getContext(), H5Environment.getResources().getString(R.string.h5_dev_app_toast), 0).show();
                                }
                            });
                        }
                        H5DevAppActivity.this.finish();
                    }
                });
                return;
            }
            return;
        }
        H5DevAppList.getInstance().add(this.a, h5DevAppInfo);
        a(schemeUri, null);
        finish();
    }

    public String getValue(Uri uri, String key) {
        String value = uri.getQueryParameter(key);
        if (value == null || "".equals(value)) {
            return null;
        }
        return value;
    }

    /* access modifiers changed from: private */
    public void a(Uri schemeUri, String[] domainWhiteList) {
        if (schemeUri != null) {
            Uri targetUri = schemeUri;
            if (this.b) {
                String whiteList = "";
                if (domainWhiteList != null) {
                    for (String domain : domainWhiteList) {
                        whiteList = whiteList + H5UrlHelper.encode(domain) + MergeUtil.SEPARATOR_KV;
                    }
                }
                targetUri = targetUri.buildUpon().appendQueryParameter("enableBugme", String.valueOf(this.b)).appendQueryParameter(H5TinyAppDebugMode.KEY_WHITE_LIST, whiteList).build();
            }
            H5EnvProvider h5EnvProvider = (H5EnvProvider) Nebula.getProviderManager().getProvider(H5EnvProvider.class.getName());
            if (h5EnvProvider != null) {
                h5EnvProvider.goToSchemeService(targetUri.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        H5Log.d(H5BugMeDevApp.TAG, "H5DevAppActivity onResume ");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }
}
