package com.alipay.mobile.tinyappcommon.c;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.log.TinyReportDataHandler;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.api.TinyAppStartupInterceptor;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

/* compiled from: TinyAppStartupInterceptorImpl */
public final class b implements TinyAppStartupInterceptor {
    public static final TinyAppStartupInterceptor a = new b();
    private boolean b = false;

    private b() {
    }

    public final Bundle handlerAppResume(H5Page h5Page, Bundle bundle) {
        Bundle pageParams = h5Page.getParams();
        if (!(h5Page == null || pageParams == null || bundle == null)) {
            boolean sureTinyApp = true;
            if (!bundle.containsKey("isTinyApp")) {
                sureTinyApp = false;
            }
            H5Log.d("TinyAppStartupInterceptor", "handler startup params for app resume");
            a.a(h5Page, bundle, sureTinyApp);
            a.a(bundle);
            this.b = true;
        }
        return bundle;
    }

    public final Bundle handlerStartupParams(H5Page h5Page, Bundle bundle) {
        if (bundle != null && !TextUtils.isEmpty(H5Utils.getString(bundle, (String) "MINI-PROGRAM-WEB-VIEW-TAG"))) {
            bundle.putBoolean("isTinyApp", true);
        }
        Bundle pageParams = h5Page.getParams();
        if (!(h5Page == null || pageParams == null)) {
            H5Log.d("TinyAppStartupInterceptor", "handler startup params for js bridge");
            a.a(h5Page, bundle, true);
            if (!this.b) {
                a.a(bundle);
            }
            a(bundle);
        }
        return bundle;
    }

    private static void a(Bundle startupParams) {
        TemplateTinyApp.getInstance().addStartupParamsForTemplateApp(TinyappUtils.getAppId(startupParams), startupParams);
    }

    public final void handlerStartParamsReady(Context context, Bundle bundle) {
        if (context != null && bundle != null && !TextUtils.isEmpty(H5Utils.getString(bundle, (String) "appId")) && (context instanceof Activity)) {
            final Activity h5Activity = (Activity) context;
            if (TextUtils.equals(H5Utils.getString(bundle, (String) "deviceOrientation"), H5Param.LONG_LANDSCAPE) && h5Activity.getRequestedOrientation() != 0) {
                H5Utils.runOnMain(new Runnable() {
                    public final void run() {
                        if (h5Activity.getRequestedOrientation() != 0) {
                            h5Activity.setRequestedOrientation(0);
                        }
                    }
                }, 500);
            }
            b(bundle);
        }
    }

    private static void b(Bundle bundle) {
        e(bundle);
        try {
            d(bundle);
        } catch (Exception e) {
            H5Log.e((String) "TinyAppStartupInterceptor", (Throwable) e);
        }
        c(bundle);
    }

    private static void c(Bundle bundle) {
        boolean inject = TinyReportDataHandler.shouldUseTinyTracker(H5Utils.getString(bundle, (String) "appId"));
        H5Log.d("TinyAppStartupInterceptor", "injectTinyTrackerStartParams inject: " + inject);
        if (inject) {
            bundle.putBoolean("tinyTrackerReportDataSwitch", true);
        }
    }

    private static void d(Bundle bundle) {
        String cfg = null;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            cfg = h5ConfigProvider.getConfig("ta_appx_map_optimize");
        }
        bundle.putBoolean("appXMapOptimize", TextUtils.equals(cfg, "1"));
    }

    private static void e(Bundle bundle) {
        bundle.putBoolean("hasNativeCanvas", false);
        bundle.putString("nativeCanvasVersion", "");
        LoggerFactory.getTraceLogger().info("TinyAppStartupInterceptor", String.format("injectCanvasStartParams:hasNativeCanvas=%s,nativeCanvasVersion=%s", new Object[]{Boolean.valueOf(false), ""}));
    }
}
