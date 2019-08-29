package com.alipay.mobile.worker;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Page.H5ErrorHandler;
import com.alipay.mobile.h5container.api.H5Page.H5PageHandler;
import com.alipay.mobile.h5container.api.H5Page.H5TitleBarReadyCallback;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.embedviewcommon.H5NewEmbedBaseViewListener;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewProvider;
import com.alipay.mobile.nebula.provider.H5EmbededViewProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5LoadingView;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.config.H5PluginConfigManager;
import com.alipay.mobile.nebulacore.core.H5CoreTarget;
import com.alipay.mobile.nebulacore.plugin.H5ActionSheetPlugin;
import com.alipay.mobile.nebulacore.plugin.H5AlertPlugin;
import com.alipay.mobile.nebulacore.plugin.H5CameraPreviewSizesPlugin;
import com.alipay.mobile.nebulacore.plugin.H5DatePlugin;
import com.alipay.mobile.nebulacore.plugin.H5EmbedViewPlugin;
import com.alipay.mobile.nebulacore.plugin.H5HttpPlugin;
import com.alipay.mobile.nebulacore.plugin.H5JSInjectPlugin;
import com.alipay.mobile.nebulacore.plugin.H5NotifyPlugin;
import com.alipay.mobile.nebulacore.plugin.H5PPDownloadPlugin;
import com.alipay.mobile.nebulacore.plugin.H5PermissionPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ScreenBrightnessPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ShakePlugin;
import com.alipay.mobile.nebulacore.plugin.H5StartParamPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ToastPlugin;
import com.alipay.mobile.nebulacore.plugin.H5UrlInterceptPlugin;
import com.alipay.mobile.nebulacore.util.H5TimeUtil;
import com.alipay.mobile.nebulacore.wallet.H5AutoClickPlugin;
import com.alipay.mobile.nebulacore.wallet.H5LoggerPlugin;

public class MockH5PageImpl extends H5CoreTarget implements H5Page {
    private WorkerBridge a = new WorkerBridge();
    private H5PageData b = new H5PageData();
    private H5AvailablePageData c = new H5AvailablePageData();

    public MockH5PageImpl(Bundle param) {
        a(param);
        a();
    }

    private void a(Bundle param) {
        this.b.setAppId(H5Utils.getString(param, (String) "appId"));
        this.b.setPublicId(H5Utils.getString(param, (String) H5Param.PUBLIC_ID));
        this.b.setAppVersion(H5Utils.getString(param, (String) "appVersion"));
        this.b.setOpenAppId(H5Utils.getString(param, (String) H5Param.OPEN_APP_ID));
        this.b.setShopId(H5Utils.getString(param, (String) "shopId"));
        this.b.setCustomParams(H5Utils.getString(param, (String) H5Param.CUSTOM_PARAMS));
        this.b.setReleaseType(H5Utils.getString(param, (String) "release_type"));
        this.b.setSessionId(H5Utils.getString(param, (String) "sessionId"));
        if (H5Utils.getBoolean(param, (String) "isTinyApp", false)) {
            this.b.setIsTinyApp("YES");
        } else {
            this.b.setIsTinyApp("NO");
        }
        if (H5Utils.getBoolean(param, (String) H5Param.LONG_ISPRERENDER, false)) {
            this.b.setPreRender(1);
        } else {
            this.b.setPreRender(0);
        }
    }

    private void a() {
        long timeMillis = System.currentTimeMillis();
        H5PluginManager pm = getPluginManager();
        pm.register((H5Plugin) new H5AlertPlugin());
        pm.register((H5Plugin) new H5NotifyPlugin());
        pm.register((H5Plugin) new H5ShakePlugin());
        pm.register((H5Plugin) new H5DatePlugin());
        pm.register((H5Plugin) new H5HttpPlugin());
        pm.register((H5Plugin) new H5UrlInterceptPlugin());
        pm.register((H5Plugin) new H5PPDownloadPlugin());
        pm.register((H5Plugin) new H5ActionSheetPlugin());
        pm.register((H5Plugin) new H5PermissionPlugin());
        pm.register((H5Plugin) new H5ScreenBrightnessPlugin());
        pm.register((H5Plugin) new H5LoggerPlugin());
        pm.register((H5Plugin) new H5AutoClickPlugin());
        pm.register((H5Plugin) new H5StartParamPlugin());
        pm.register((H5Plugin) new H5EmbedViewPlugin());
        pm.register((H5Plugin) new H5CameraPreviewSizesPlugin());
        if (!H5Utils.isInWallet()) {
            pm.register((H5Plugin) new H5ToastPlugin());
        }
        H5Plugin extPagePlugin = H5PluginConfigManager.getInstance().createPlugin("page", pm);
        if (extPagePlugin != null) {
            pm.register(extPagePlugin);
        }
        if (Nebula.DEBUG) {
            pm.register((H5Plugin) new H5JSInjectPlugin());
        }
        H5TimeUtil.timeLog(H5TimeUtil.CREATE_PAGE, H5TimeUtil.INIT_PLUGIN, timeMillis);
    }

    public H5Session getSession() {
        return null;
    }

    public H5Context getContext() {
        return null;
    }

    public View getContentView() {
        return null;
    }

    public APWebView getWebView() {
        return null;
    }

    public View getRootView() {
        return null;
    }

    public void setRootView(View view) {
    }

    public void loadUrl(String s) {
    }

    public void execJavaScript4EmbedView(String s, IH5EmbedViewJSCallback ih5EmbedViewJSCallback) {
    }

    public void loadDataWithBaseURL(String s, String s1, String s2, String s3, String s4) {
    }

    public void setTextSize(int i) {
    }

    public String getUrl() {
        return null;
    }

    public String getShareUrl() {
        return null;
    }

    public String getRedirectUrl() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public H5Bridge getBridge() {
        return this.a;
    }

    public Bundle getParams() {
        return null;
    }

    public boolean exitPage() {
        return false;
    }

    public boolean exitTabPage() {
        return false;
    }

    public void setHandler(H5PageHandler h5PageHandler) {
    }

    public void setH5ErrorHandler(H5ErrorHandler h5ErrorHandler) {
    }

    public H5PageData getPageData() {
        return this.b;
    }

    public H5AvailablePageData getAvailablePageData() {
        return this.c;
    }

    public String getVersion() {
        return null;
    }

    public long getLastTouch() {
        return 0;
    }

    public String getPerformance() {
        return null;
    }

    public void setPerformance(String s) {
    }

    public boolean onInterceptError(String s, int i) {
        return false;
    }

    public boolean scriptbizLoadedAndBridgeLoaded() {
        return false;
    }

    public void setTitle(String s) {
    }

    public void setH5TitleBar(H5TitleView h5TitleView) {
    }

    public H5TitleView getH5TitleBar() {
        return null;
    }

    public boolean pageIsClose() {
        return false;
    }

    public ViewGroup getViewGroup() {
        return null;
    }

    public void replace(String s) {
    }

    public H5EmbededViewProvider getEmbededViewProvider() {
        return null;
    }

    public H5NewEmbedViewProvider getNewEmbedViewProvider() {
        return null;
    }

    public void setPageId(int i) {
    }

    public int getPageId() {
        return 0;
    }

    public void setWebViewId(int i) {
    }

    public int getWebViewId() {
        return 0;
    }

    public H5TitleBarReadyCallback getTitleBarReadyCallBack() {
        return null;
    }

    public void setTitleBarReadyCallBack(H5TitleBarReadyCallback h5TitleBarReadyCallback) {
    }

    public boolean ifContainsEmbedView() {
        return false;
    }

    public void setContainsEmbedView(boolean b2) {
    }

    public boolean ifContainsEmbedSurfaceView() {
        return false;
    }

    public void setContainsEmbedSurfaceView(boolean b2) {
    }

    public APWebViewClient getAPWebViewClient() {
        return null;
    }

    public void sendExitEvent() {
    }

    public H5LoadingView getH5LoadingView() {
        return null;
    }

    public boolean isTransparentTitleState() {
        return false;
    }

    public void applyParamsIfNeed() {
    }

    public void setExtra(String s, Object o) {
    }

    public Object getExtra(String s) {
        return null;
    }

    public void setNewEmbedViewRoot(View view) {
    }

    public View getNewEmbedViewRoot(H5NewEmbedBaseViewListener h5NewEmbedBaseViewListener) {
        return null;
    }

    public boolean hasContentBeforeRedirect() {
        return false;
    }

    public void setContentBeforeRedirect(boolean b2) {
    }

    public String getAdvertisementViewTag() {
        return null;
    }

    public boolean isTinyApp() {
        return true;
    }
}
