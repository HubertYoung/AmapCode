package com.mpaas.nebula.util;

import com.alipay.mobile.framework.BuildConfig;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.config.H5EmbedViewConfig;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewConfig;
import com.alipay.mobile.nebula.providermanager.H5BaseProviderInfo;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import java.util.ArrayList;
import java.util.List;

public class H5EmbedViewConfigList {
    public static final String TAG = "H5EmbedViewConfigList";
    public static List<H5EmbedViewConfig> embedViewList = new ArrayList<H5EmbedViewConfig>() {
        {
            String PACKAGE_NAME = LauncherApplicationAgent.getInstance().getApplicationContext().getPackageName();
            H5Log.d(H5EmbedViewConfigList.TAG, "PACKAGE_NAME: " + PACKAGE_NAME);
            if ("com.autonavi.minimap".equals(PACKAGE_NAME)) {
                add(new H5EmbedViewConfig(BuildConfig.BUNDLE_NAME, "com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView", "map"));
            } else {
                add(new H5EmbedViewConfig("com-mpaas-mpaasadapter-commonbiz", "com.alipay.mobile.embedview.H5EmbedMapView", "map"));
            }
            add(new H5EmbedViewConfig(H5BaseProviderInfo.nebulabiz, "com.alipay.mobile.nebulabiz.embedview.H5EmbedLottieView", "lottie"));
            add(new H5EmbedViewConfig("android-phone-wallet-tinyappcommon", "com.alipay.mobile.tinyappcommon.embedview.H5EmbedWebView", "web-view"));
            add(new H5EmbedViewConfig("android-phone-wallet-nebula", "com.alipay.mobile.nebula.embedviewcommon.H5NewEmbedBaseView", "newembedbase"));
            add(new H5EmbedViewConfig("android-phone-wallet-canvas", "com.alipay.mobile.canvas.tinyapp.CanvasEmbedViewController", "canvas"));
            add(new H5EmbedViewConfig("android-phone-wallet-beehive", "com.alipay.mobile.beehive.lottie.H5BeeLottieView", "lottieview"));
            add(new H5EmbedViewConfig("android-phone-wallet-beehive", "com.alipay.mobile.beehive.plugins.capture.H5CaptureView", WalletTinyappUtils.CONST_SCOPE_CAMERA));
            add(new H5EmbedViewConfig(H5BaseProviderInfo.nebulabiz, "com.alipay.mobile.nebulabiz.embedview.input.H5EmbedEditText", "input"));
        }
    };
    public static List<H5NewEmbedViewConfig> newEmbedViewList = new ArrayList<H5NewEmbedViewConfig>() {
        {
            add(new H5NewEmbedViewConfig(com.mpaas.nebula.provider.H5BaseProviderInfo.tinyappcustom, "com.alipay.mobile.tinyappcustom.embedview.H5NewEmbedImageView", "image"));
            add(new H5NewEmbedViewConfig(com.mpaas.nebula.provider.H5BaseProviderInfo.tinyappcustom, "com.alipay.mobile.tinyappcustom.embedview.H5NewEmbedTextView", "text"));
            add(new H5NewEmbedViewConfig(com.mpaas.nebula.provider.H5BaseProviderInfo.tinyappcustom, "com.alipay.mobile.tinyappcustom.embedview.H5NewEmbedFrameLayout", "container"));
            add(new H5NewEmbedViewConfig(com.mpaas.nebula.provider.H5BaseProviderInfo.tinyappcustom, "com.alipay.mobile.tinyappcustom.embedview.H5NewEmbedScrollView", "scrollview"));
        }
    };
}
