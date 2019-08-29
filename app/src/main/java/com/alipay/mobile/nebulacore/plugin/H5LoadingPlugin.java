package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5LoadingView;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.ui.H5LoadingDialog;
import com.alipay.mobile.nebulacore.util.NebulaUtil;

public class H5LoadingPlugin extends H5SimplePlugin {
    public static final String TAG = "H5LoadingPlugin";
    private Runnable a;
    private Handler b;
    private H5Page c;
    /* access modifiers changed from: private */
    public H5LoadingDialog d;
    /* access modifiers changed from: private */
    public Activity e;
    private boolean f = true;
    private boolean g = false;
    private boolean h = true;

    public H5LoadingPlugin(H5Page page) {
        this.c = page;
        this.b = new Handler();
        Context context = this.c.getContext().getContext();
        if (context instanceof Activity) {
            this.e = (Activity) context;
        }
    }

    public void onRelease() {
        if (this.d != null && this.d.isShowing()) {
            this.d.dismiss();
        }
        this.b.removeCallbacks(this.a);
        this.a = null;
        this.c = null;
    }

    private static boolean a(H5Event event) {
        boolean defaultAutoHide;
        if (event.getId().startsWith("native_")) {
            defaultAutoHide = true;
        } else {
            defaultAutoHide = false;
        }
        if ("yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_showLoading_defaultAutoHide"))) {
            return true;
        }
        return defaultAutoHide;
    }

    public void showLoading(H5Event event) {
        JSONObject param = event.getParam();
        String title = H5Utils.getString(param, (String) "text");
        int delay = H5Utils.getInt(param, (String) "delay");
        this.g = H5Utils.getBoolean(param, (String) "isTinyApp", false);
        this.f = H5Utils.getBoolean(param, (String) "autoHide", a(event));
        boolean cancelable = H5Utils.getBoolean(param, (String) "cancelable", true);
        H5Log.d(TAG, "showLoading [title] " + title + " [delay] " + delay + " autoHide:" + this.f);
        if (this.d == null) {
            this.d = new H5LoadingDialog(this.e);
        }
        hideLoading();
        if (!TextUtils.isEmpty(title) && title.length() > 20) {
            title = title.substring(0, 20);
        }
        if (TextUtils.isEmpty(title)) {
            title = H5Environment.getResources().getString(R.string.h5_loading_txt);
        }
        this.d.setCancelable(cancelable);
        this.d.setCanceledOnTouchOutside(false);
        this.d.setMessage(title);
        this.a = new Runnable() {
            public void run() {
                if (H5LoadingPlugin.this.e != null && !H5LoadingPlugin.this.e.isFinishing()) {
                    try {
                        H5Flag.hasShowLoading = true;
                        H5Log.d(H5LoadingPlugin.TAG, "showLoadingDialog ");
                        H5LoadingPlugin.this.d.show();
                    } catch (RuntimeException e) {
                        H5Flag.hasShowLoading = false;
                        H5Log.e(H5LoadingPlugin.TAG, "exception detail", e);
                    }
                }
            }
        };
        this.b.postDelayed(this.a, (long) delay);
    }

    public void hideLoading() {
        if (this.a != null) {
            this.b.removeCallbacks(this.a);
            this.a = null;
        }
        if (this.d != null) {
            H5Log.d(TAG, "dialog.isShowing():" + this.d.isShowing() + this.d);
        }
        if (this.d != null && this.d.isShowing() && this.e != null && !this.e.isFinishing()) {
            H5Log.d(TAG, CommonEvents.HIDE_LOADING);
            try {
                H5Flag.hasShowLoading = false;
                this.d.dismiss();
            } catch (Throwable th) {
                H5Log.e((String) TAG, (String) "dismiss exception");
            }
        }
    }

    private void b(H5Event event) {
        if (this.c != null && this.c.getH5LoadingView() != null) {
            final H5LoadingView loadingView = this.c.getH5LoadingView();
            JSONObject param = event.getParam();
            final String title = a(H5Utils.getString(param, (String) "text"));
            int delay = H5Utils.getInt(param, (String) "delay");
            this.f = H5Utils.getBoolean(param, (String) "autoHide", a(event));
            this.h = H5Utils.getBoolean(param, (String) "cancelable", true);
            H5Log.d(TAG, "showLoading [title] " + title + " [delay] " + delay);
            a();
            this.a = new Runnable() {
                public void run() {
                    if (loadingView != null) {
                        try {
                            H5Flag.hasShowLoading = true;
                            H5Log.d(H5LoadingPlugin.TAG, "showLoadingView ");
                            loadingView.setText(title);
                            loadingView.setVisibility(0);
                        } catch (RuntimeException e) {
                            H5Flag.hasShowLoading = false;
                            H5Log.e((String) H5LoadingPlugin.TAG, (Throwable) e);
                        }
                    }
                }
            };
            this.b.postDelayed(this.a, (long) delay);
        }
    }

    private void a() {
        if (this.a != null) {
            this.b.removeCallbacks(this.a);
            this.a = null;
        }
        if (this.c != null && this.c.getH5LoadingView() != null && this.e != null && !this.e.isFinishing()) {
            H5Log.d(TAG, CommonEvents.HIDE_LOADING);
            H5Flag.hasShowLoading = false;
            this.c.getH5LoadingView().setVisibility(8);
        }
    }

    private static String a(String title) {
        if (!TextUtils.isEmpty(title) && title.length() > 20) {
            title = title.substring(0, 20);
        }
        if (TextUtils.isEmpty(title)) {
            return H5Environment.getResources().getString(R.string.h5_loading_txt);
        }
        return title;
    }

    public void onPrepare(H5EventFilter filter) {
        if (this.e != null) {
            filter.addAction("showLoading");
            filter.addAction(CommonEvents.HIDE_LOADING);
            filter.addAction(CommonEvents.H5_PAGE_PHYSICAL_BACK);
        }
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (!CommonEvents.H5_PAGE_PHYSICAL_BACK.equals(event.getAction()) || this.c == null || this.c.getH5LoadingView() == null || this.c.getH5LoadingView().getVisibility() != 0 || !NebulaUtil.enableShowLoadingViewConfig()) {
            return super.interceptEvent(event, context);
        }
        if (this.h) {
            a();
        }
        return true;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if ("showLoading".equals(action)) {
            if (b()) {
                b(event);
            } else {
                showLoading(event);
            }
            bridgeContext.sendBridgeResult("success", "true");
        } else if (CommonEvents.HIDE_LOADING.equals(action)) {
            if (event.getId().startsWith("native_") && !this.f) {
                bridgeContext.sendSuccess();
            } else if (this.g) {
                H5Log.d(TAG, "hide autoHide:" + this.f);
                if (this.f) {
                    a(bridgeContext);
                }
            } else {
                a(bridgeContext);
            }
        }
        return true;
    }

    private boolean b() {
        if (this.c == null || this.c.getContext() == null || this.c.getContext().getContext() == null || !(this.c.getContext().getContext() instanceof H5Activity) || !NebulaUtil.enableShowLoadingViewConfig() || a(this.c.getParams()) || Nebula.isTinyWebView(this.c.getParams())) {
            return false;
        }
        return true;
    }

    private static boolean a(Bundle bundle) {
        return H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANSPARENT, false);
    }

    private void a(H5BridgeContext bridgeContext) {
        if (b()) {
            a();
        } else {
            hideLoading();
        }
        bridgeContext.sendBridgeResult("success", "true");
    }
}
