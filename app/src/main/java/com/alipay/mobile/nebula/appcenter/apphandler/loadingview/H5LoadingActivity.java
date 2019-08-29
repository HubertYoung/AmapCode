package com.alipay.mobile.nebula.appcenter.apphandler.loadingview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.appcenter.appsync.H5PageStatues;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5WebLoadingView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.Timer;
import java.util.TimerTask;

public class H5LoadingActivity extends BaseFragmentActivity implements H5LoadingTypeListen {
    private static final String TAG = "H5LoadingActivity";
    /* access modifiers changed from: private */
    public H5WebLoadingView loadingView;
    private H5PageStatues pageStatues;
    private Timer timer;

    private class MyTimeTask extends TimerTask {
        /* access modifiers changed from: private */
        public String appId;
        /* access modifiers changed from: private */
        public int timeout;
        /* access modifiers changed from: private */
        public String type;

        MyTimeTask(String type2, String appId2, int timeout2) {
            this.type = type2;
            this.appId = appId2;
            this.timeout = timeout2;
        }

        public void run() {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    if (H5LoadingActivity.this.isFinishing()) {
                        H5Log.d(H5LoadingActivity.TAG, "isFinishing not handler timeout");
                        return;
                    }
                    H5Log.d(H5LoadingActivity.TAG, "timeout  type " + MyTimeTask.this.type);
                    if (H5AppHandler.isSyncType(MyTimeTask.this.type)) {
                        H5LogUtil.logNebulaTech(H5LogData.seedId("h5_show_nebulaApp_timeout_page").param1().add(MyTimeTask.this.appId, null).param2().add(MyTimeTask.this.type, null).param3().add(MyTimeTask.this.timeout, null));
                        H5LoadingActivity.this.sendToWebFail();
                        return;
                    }
                    H5AppHandler.exitAndStartApp(H5LoadingUtil.getH5StartAppInfo(), H5LoadingUtil.getH5LoadingManager());
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pageStatues = new H5PageStatues();
        this.pageStatues.currentPageStatues = 1;
        H5LoadingUtil.setH5LoadingActivity(this);
        H5LoadingUtil.setH5LoadingTypeListen(this);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        Bundle bundle = intent.getExtras();
        String appName = H5Utils.getString(bundle, (String) "appName");
        String logo = H5Utils.getString(bundle, (String) H5AppHandler.sAppIcon);
        String appId = H5Utils.getString(bundle, (String) "appId");
        int timeout = H5Utils.getInt(bundle, (String) H5LoadingViewImpl.LOADING_TIMEOUT);
        String type = H5Utils.getString(bundle, (String) H5LoadingViewImpl.LOADING_TYPE);
        H5Log.d(TAG, "appName " + appName + " logo:" + logo + " timeout:" + timeout + " type:" + type);
        this.loadingView = new H5WebLoadingView(this);
        this.loadingView.setLoadingInfo(appName, null, null);
        this.loadingView.getBackButton().setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                H5LoadingActivity.this.finish();
            }
        });
        try {
            setContentView((View) this.loadingView);
            if (!TextUtils.isEmpty(logo)) {
                H5ImageUtil.loadImage(logo, new H5ImageListener() {
                    public void onImage(Bitmap bitmap) {
                        if (bitmap != null && !H5LoadingActivity.this.isFinishing() && H5LoadingActivity.this.loadingView != null) {
                            H5LoadingActivity.this.loadingView.setLoadingBitmap(bitmap);
                        }
                    }
                });
            }
            this.loadingView.startLoadingAnimation();
            if (timeout > 0 && this.timer == null) {
                try {
                    this.timer = new Timer();
                    this.timer.schedule(new MyTimeTask(type, appId, timeout), (long) (timeout * 1000));
                } catch (Throwable throwable) {
                    H5Log.e((String) TAG, throwable);
                }
            }
        } catch (Throwable throwable2) {
            H5Log.e((String) TAG, throwable2);
            finish();
        }
    }

    public void sendToWebFail() {
        if (this.loadingView != null) {
            if (this.pageStatues != null) {
                this.pageStatues.currentPageStatues = 2;
            }
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    if (!H5LoadingActivity.this.isFinishing()) {
                        H5LoadingActivity.this.loadingView.stopLoadingAnimation();
                        H5LoadingActivity.this.loadingView.setLoadingInfo(H5Utils.getNebulaResources().getString(R.string.h5_network_poor), null, null);
                    }
                }
            });
        }
    }

    public int getPageStatues() {
        if (this.pageStatues != null) {
            return this.pageStatues.currentPageStatues;
        }
        return 0;
    }

    public void setPageStatue(int statue) {
        if (this.pageStatues != null) {
            this.pageStatues.currentPageStatues = statue;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        H5LoadingUtil.setH5LoadingActivity(null);
        H5LoadingUtil.setH5LoadingManager(null);
        H5LoadingUtil.setH5StartAppInfo(null);
        H5LoadingUtil.setH5LoadingTypeListen(null);
    }

    public void onGetType(String type, int timeout, String appId) {
        H5Log.d(TAG, "onGetType " + type + " timeout:" + timeout + Token.SEPARATOR + this.timer);
        if (this.timer != null && timeout > 0) {
            try {
                this.timer.cancel();
                this.timer = new Timer();
                this.timer.schedule(new MyTimeTask(type, appId, timeout), (long) (timeout * 1000));
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        }
    }
}
