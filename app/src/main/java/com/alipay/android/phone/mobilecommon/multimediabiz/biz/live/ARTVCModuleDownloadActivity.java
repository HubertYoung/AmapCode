package com.alipay.android.phone.mobilecommon.multimediabiz.biz.live;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.android.phone.mobilecommon.dynamicrelease.DynamicReleaseApi;
import com.alipay.android.phone.mobilecommon.dynamicrelease.callback.DynamicReleaseCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.antui.basic.AULoadingView;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.autonavi.map.core.MapCustomizeManager;

public class ARTVCModuleDownloadActivity extends BaseActivity {
    AULoadingView a = null;
    Bundle b = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.b = getIntent().getExtras();
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(-1358954496);
        this.a = new AULoadingView(this);
        LayoutParams lp = new LayoutParams(-2, -2);
        lp.gravity = 17;
        this.a.setLayoutParams(lp);
        this.a.setCurrentProgress(0);
        layout.addView(this.a);
        setContentView((View) layout);
        if (CommonUtils.isWifiNetwork()) {
            this.a.setVisibility(0);
            a();
            return;
        }
        this.a.setVisibility(8);
        alert(null, "需下载组件（4.0M），非WIFI环境将产生流量费用", "继续", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ARTVCModuleDownloadActivity.this.a.setVisibility(0);
                ARTVCModuleDownloadActivity.this.a();
            }
        }, "取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ARTVCModuleDownloadActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: private */
    public void a() {
        DynamicReleaseApi.getInstance(this).requireBundle("mobile-mrtc-mrtc", new DynamicReleaseCallback(new TransportCallback() {
            public void onCancelled(Request request) {
                ARTVCModuleDownloadActivity.b((String) "onCancelled");
            }

            public void onPreExecute(Request request) {
                ARTVCModuleDownloadActivity.b((String) "onPreExecute");
            }

            public void onPostExecute(Request request, Response response) {
                ARTVCModuleDownloadActivity.b((String) "onPostExecute");
            }

            public void onProgressUpdate(Request request, final double v) {
                ARTVCModuleDownloadActivity.b("onProgressUpdate.v=" + v);
                if (!ARTVCModuleDownloadActivity.this.isFinishing()) {
                    ARTVCModuleDownloadActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ARTVCModuleDownloadActivity.this.a.setCurrentProgress((int) (v * 100.0d));
                        }
                    });
                }
            }

            public void onFailed(Request request, int i, String s) {
                ARTVCModuleDownloadActivity.b((String) "onFailed");
                if (!ARTVCModuleDownloadActivity.this.isFinishing()) {
                    ARTVCModuleDownloadActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ARTVCModuleDownloadActivity.this.alert(null, "组件下载失败", "重试", new OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ARTVCModuleDownloadActivity.this.a.setVisibility(0);
                                    ARTVCModuleDownloadActivity.this.a();
                                }
                            }, "取消", new OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ARTVCModuleDownloadActivity.this.finish();
                                }
                            });
                        }
                    });
                }
            }
        }) {
            public void onFinish() {
                ARTVCModuleDownloadActivity.b((String) "onFinish");
                if (!ARTVCModuleDownloadActivity.this.isFinishing()) {
                    ARTVCModuleDownloadActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ARTVCModuleDownloadActivity.startActivity(ARTVCModuleDownloadActivity.this.mMicroApplicationContext, ARTVCModuleDownloadActivity.this.mApp, ARTVCModuleDownloadActivity.this.b);
                            ARTVCModuleDownloadActivity.this.finish();
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void b(String msg) {
        Logger.D("ARTVCModuleDownloadActivity", msg, new Object[0]);
    }

    public static void startActivity(MicroApplicationContext applicationContext, MicroApplication app, Bundle params) {
        String targetActivity;
        b("startVidoConferenceActivity params=" + params);
        try {
            Intent intent = new Intent();
            if (params != null && "createToyMachineVC".equals(params.getString("actionType"))) {
                targetActivity = "com.ant.phone.ARTVC.activity.ToyMachineActivity";
            } else if (params == null || !LiveLaunchApp.ACTION_CREATE_ARTVCCORE_VC.equals(params.getString("actionType"))) {
                b((String) "can not find the activity");
                return;
            } else {
                targetActivity = "com.ant.phone.ARTVC.activity.ARTVCActivity";
            }
            intent.setComponent(new ComponentName("com.eg.android.AlipayGphone", targetActivity));
            intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            intent.putExtras(params);
            applicationContext.startActivity(app, intent);
        } catch (Throwable e) {
            b("startVidoConferenceActivity exp=" + e.toString());
        }
    }

    public void setRequestedOrientation(int requestedOrientation) {
        if (getResources().getConfiguration().orientation != 1) {
            super.setRequestedOrientation(1);
        }
    }
}
