package com.alipay.android.phone.mobilecommon.multimediabiz.biz.live;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import com.alipay.mobile.framework.app.ui.BaseActivity;

public class LiveDownloadActivity extends BaseActivity {
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
        alert(null, "开始直播需下载直播组件（0.35M），非WIFI环境将产生流量费用", "继续", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                LiveDownloadActivity.this.a.setVisibility(0);
                LiveDownloadActivity.this.a();
            }
        }, "取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                LiveDownloadActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: private */
    public void a() {
        DynamicReleaseApi.getInstance(this).requireBundle("multimedia-live", new DynamicReleaseCallback(new TransportCallback() {
            public void onCancelled(Request request) {
                Logger.D("LiveDownloadActivity", "onCancelled", new Object[0]);
            }

            public void onPreExecute(Request request) {
                Logger.D("LiveDownloadActivity", "onPreExecute", new Object[0]);
            }

            public void onPostExecute(Request request, Response response) {
                Logger.D("LiveDownloadActivity", "onPostExecute", new Object[0]);
            }

            public void onProgressUpdate(Request request, final double v) {
                Logger.D("LiveDownloadActivity", "onProgressUpdate.v=" + v, new Object[0]);
                if (!LiveDownloadActivity.this.isFinishing()) {
                    LiveDownloadActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            LiveDownloadActivity.this.a.setCurrentProgress((int) (v * 100.0d));
                        }
                    });
                }
            }

            public void onFailed(Request request, int i, String s) {
                Logger.D("LiveDownloadActivity", "onFailed", new Object[0]);
                if (!LiveDownloadActivity.this.isFinishing()) {
                    LiveDownloadActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            LiveDownloadActivity.this.alert(null, "直播组件下载失败", "重试", new OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    LiveDownloadActivity.this.a.setVisibility(0);
                                    LiveDownloadActivity.this.a();
                                }
                            }, "取消", new OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    LiveDownloadActivity.this.finish();
                                }
                            });
                        }
                    });
                }
            }
        }) {
            public void onFinish() {
                Logger.D("LiveDownloadActivity", "onFinish", new Object[0]);
                if (!LiveDownloadActivity.this.isFinishing()) {
                    LiveDownloadActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            LiveLaunchApp.startLiveBundle(LiveDownloadActivity.this.mMicroApplicationContext, LiveDownloadActivity.this.mApp, LiveDownloadActivity.this, LiveDownloadActivity.this.b);
                            LiveDownloadActivity.this.finish();
                        }
                    });
                }
            }
        });
    }

    public void setRequestedOrientation(int requestedOrientation) {
        if (getResources().getConfiguration().orientation != 1) {
            super.setRequestedOrientation(1);
        }
    }
}
