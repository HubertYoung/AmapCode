package com.alipay.mobile.nebula.basebridge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.Rect;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Page.H5ErrorHandler;
import com.alipay.mobile.h5container.api.H5Page.H5PageHandler;
import com.alipay.mobile.h5container.api.H5Page.H5TitleBarReadyCallback;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.embedviewcommon.H5NewEmbedBaseViewListener;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5EmbededViewProvider;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5LoadingView;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.nebula.webview.APDownloadListener;
import com.alipay.mobile.nebula.webview.APHitTestResult;
import com.alipay.mobile.nebula.webview.APWebBackForwardList;
import com.alipay.mobile.nebula.webview.APWebChromeClient;
import com.alipay.mobile.nebula.webview.APWebSettings;
import com.alipay.mobile.nebula.webview.APWebSettings.LayoutAlgorithm;
import com.alipay.mobile.nebula.webview.APWebSettings.PluginState;
import com.alipay.mobile.nebula.webview.APWebSettings.TextSize;
import com.alipay.mobile.nebula.webview.APWebSettings.ZoomDensity;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebula.webview.APWebViewListener;
import com.alipay.mobile.nebula.webview.H5ScrollChangedCallback;
import com.alipay.mobile.nebula.webview.WebViewType;
import java.util.Map;
import junit.framework.Assert;

public class H5BasePage extends H5BaseNebulaService implements H5Page, APWebView {
    private H5Bridge bridge;
    private H5Context mH5Context;
    private final H5PageData mPageData;
    private final Bundle mStartParams;
    private final InternalWebSettings mWebSettings;

    private class InternalWebSettings implements APWebSettings {
        private String mUaString;

        private InternalWebSettings() {
        }

        public void setSupportZoom(boolean b) {
        }

        public boolean supportZoom() {
            return false;
        }

        public boolean getMediaPlaybackRequiresUserGesture() {
            return false;
        }

        public void setMediaPlaybackRequiresUserGesture(boolean b) {
        }

        public boolean getBuiltInZoomControls() {
            return false;
        }

        public void setBuiltInZoomControls(boolean b) {
        }

        public boolean getDisplayZoomControls() {
            return false;
        }

        public void setDisplayZoomControls(boolean b) {
        }

        public boolean getAllowFileAccess() {
            return false;
        }

        public void setAllowFileAccess(boolean b) {
        }

        public boolean getAllowContentAccess() {
            return false;
        }

        public void setAllowContentAccess(boolean b) {
        }

        public boolean getLoadWithOverviewMode() {
            return false;
        }

        public void setLoadWithOverviewMode(boolean b) {
        }

        public boolean getSaveFormData() {
            return false;
        }

        public void setSaveFormData(boolean b) {
        }

        public boolean getSavePassword() {
            return false;
        }

        public void setSavePassword(boolean b) {
        }

        public int getTextZoom() {
            return 0;
        }

        public void setTextZoom(int i) {
        }

        public TextSize getTextSize() {
            return null;
        }

        public void setTextSize(TextSize textSize) {
        }

        public ZoomDensity getDefaultZoom() {
            return null;
        }

        public boolean getUseWideViewPort() {
            return false;
        }

        public void setUseWideViewPort(boolean b) {
        }

        public void setSupportMultipleWindows(boolean b) {
        }

        public boolean supportMultipleWindows() {
            return false;
        }

        public LayoutAlgorithm getLayoutAlgorithm() {
            return null;
        }

        public void setLayoutAlgorithm(LayoutAlgorithm layoutAlgorithm) {
        }

        public String getStandardFontFamily() {
            return null;
        }

        public void setStandardFontFamily(String s) {
        }

        public String getFixedFontFamily() {
            return null;
        }

        public void setFixedFontFamily(String s) {
        }

        public String getSansSerifFontFamily() {
            return null;
        }

        public void setSansSerifFontFamily(String s) {
        }

        public String getSerifFontFamily() {
            return null;
        }

        public void setSerifFontFamily(String s) {
        }

        public String getCursiveFontFamily() {
            return null;
        }

        public void setCursiveFontFamily(String s) {
        }

        public String getFantasyFontFamily() {
            return null;
        }

        public void setFantasyFontFamily(String s) {
        }

        public int getMinimumFontSize() {
            return 0;
        }

        public void setMinimumFontSize(int i) {
        }

        public int getMinimumLogicalFontSize() {
            return 0;
        }

        public void setMinimumLogicalFontSize(int i) {
        }

        public int getDefaultFontSize() {
            return 0;
        }

        public void setDefaultFontSize(int i) {
        }

        public int getDefaultFixedFontSize() {
            return 0;
        }

        public void setDefaultFixedFontSize(int i) {
        }

        public boolean getLoadsImagesAutomatically() {
            return false;
        }

        public void setLoadsImagesAutomatically(boolean b) {
        }

        public boolean getBlockNetworkImage() {
            return false;
        }

        public void setBlockNetworkImage(boolean b) {
        }

        public boolean getJavaScriptEnabled() {
            return false;
        }

        public void setJavaScriptEnabled(boolean b) {
        }

        public void setGeolocationDatabasePath(String s) {
        }

        public void setAppCacheEnabled(boolean b) {
        }

        public void setAppCachePath(String s) {
        }

        public boolean getDatabaseEnabled() {
            return false;
        }

        public void setDatabaseEnabled(boolean b) {
        }

        public boolean getDomStorageEnabled() {
            return false;
        }

        public void setDomStorageEnabled(boolean b) {
        }

        public String getDatabasePath() {
            return null;
        }

        public void setDatabasePath(String s) {
        }

        public void setGeolocationEnabled(boolean b) {
        }

        public boolean getAllowUniversalAccessFromFileURLs() {
            return false;
        }

        public void setAllowUniversalAccessFromFileURLs(boolean b) {
        }

        public boolean getAllowFileAccessFromFileURLs() {
            return false;
        }

        public void setAllowFileAccessFromFileURLs(boolean b) {
        }

        public PluginState getPluginState() {
            return null;
        }

        public void setPluginState(PluginState pluginState) {
        }

        public boolean getJavaScriptCanOpenWindowsAutomatically() {
            return false;
        }

        public void setJavaScriptCanOpenWindowsAutomatically(boolean b) {
        }

        public String getDefaultTextEncodingName() {
            return null;
        }

        public void setDefaultTextEncodingName(String s) {
        }

        public String getUserAgentString() {
            return this.mUaString;
        }

        public void setUserAgentString(String s) {
            this.mUaString = s;
        }

        public String getDefaultUserAgent(Context context) {
            return null;
        }

        public void setNeedInitialFocus(boolean b) {
        }

        public int getCacheMode() {
            return 0;
        }

        public void setCacheMode(int i) {
        }

        public void setEnableFastScroller(boolean b) {
        }

        public void setPageCacheCapacity(int i) {
        }
    }

    public void setH5Context(Context context) {
        if (enableUseOldContext()) {
            this.mH5Context = new H5Context(context);
        }
    }

    private boolean enableUseOldContext() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !"yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableUseOldContext"))) {
            return false;
        }
        return true;
    }

    public H5BasePage(Context context, H5Bridge h5Bridge, Bundle startParams) {
        if (enableUseOldContext()) {
            this.mH5Context = new H5Context(context);
        }
        this.mStartParams = startParams;
        this.bridge = h5Bridge;
        H5Service service = H5ServiceUtils.getH5Service();
        setParent(service.getItsOwnNode());
        getPluginManager().register(service.createPlugin("page", getPluginManager()));
        this.mPageData = new H5PageData();
        this.mPageData.setAppId(startParams.getString("appId", null));
        this.mPageData.setWebViewType("tiny");
        this.mPageData.setPageUrl(startParams.getString("url", null));
        this.mWebSettings = new InternalWebSettings();
        this.mWebSettings.setUserAgentString("");
    }

    public H5Session getSession() {
        return null;
    }

    public H5Context getContext() {
        if (enableUseOldContext()) {
            return this.mH5Context;
        }
        Context context = (Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
        if (context == null) {
            context = H5Utils.getContext();
        }
        return new H5Context(context);
    }

    public View getContentView() {
        return null;
    }

    public APWebView getWebView() {
        return this;
    }

    public View getRootView() {
        return null;
    }

    public void setRootView(View view) {
    }

    public void addJavascriptInterface(Object o, String s) {
    }

    public void removeJavascriptInterface(String s) {
    }

    public void setWebContentsDebuggingEnabled(boolean b) {
    }

    public void flingScroll(int i, int i1) {
    }

    public boolean zoomIn() {
        return false;
    }

    public boolean zoomOut() {
        return false;
    }

    public void setHorizontalScrollbarOverlay(boolean b) {
    }

    public void setVerticalScrollbarOverlay(boolean b) {
    }

    public boolean overlayHorizontalScrollbar() {
        return false;
    }

    public boolean overlayVerticalScrollbar() {
        return false;
    }

    public SslCertificate getCertificate() {
        return null;
    }

    public void savePassword(String s, String s1, String s2) {
    }

    public void setHttpAuthUsernamePassword(String s, String s1, String s2, String s3) {
    }

    public String[] getHttpAuthUsernamePassword(String s, String s1) {
        return new String[0];
    }

    public void destroy() {
    }

    public void setNetworkAvailable(boolean b) {
    }

    public APWebBackForwardList saveState(Bundle bundle) {
        return null;
    }

    public APWebBackForwardList restoreState(Bundle bundle) {
        return null;
    }

    public void loadUrl(String s, Map<String, String> map) {
    }

    public void loadUrl(String s) {
        Assert.assertTrue(false);
    }

    public void execJavaScript4EmbedView(String url, IH5EmbedViewJSCallback jsCallback) {
    }

    public void postUrl(String s, byte[] bytes) {
    }

    public void loadData(String s, String s1, String s2) {
    }

    public void loadDataWithBaseURL(String s, String s1, String s2, String s3, String s4) {
        Assert.assertTrue(false);
    }

    public void evaluateJavascript(String s, ValueCallback<String> valueCallback) {
    }

    public void stopLoading() {
    }

    public void reload() {
    }

    public boolean canGoBack() {
        return false;
    }

    public void goBack() {
    }

    public boolean canGoForward() {
        return false;
    }

    public void goForward() {
    }

    public boolean canGoBackOrForward(int i) {
        return false;
    }

    public void goBackOrForward(int i) {
    }

    public boolean pageUp(boolean b) {
        return false;
    }

    public boolean pageDown(boolean b) {
        return false;
    }

    public void setInitialScale(int i) {
    }

    public void invokeZoomPicker() {
    }

    public void setTextSize(int i) {
    }

    public String getUrl() {
        return this.mPageData.getPageUrl();
    }

    public String getOriginalUrl() {
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

    public Bitmap getFavicon() {
        return null;
    }

    public int getProgress() {
        return 0;
    }

    public int getContentHeight() {
        return 0;
    }

    public int getContentWidth() {
        return 0;
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public boolean isPaused() {
        return false;
    }

    public void freeMemory() {
    }

    public void clearCache(boolean b) {
    }

    public void clearFormData() {
    }

    public void clearHistory() {
    }

    public void clearSslPreferences() {
    }

    public APWebBackForwardList copyBackForwardList() {
        return null;
    }

    public void setWebViewClient(APWebViewClient apWebViewClient) {
    }

    public void setDownloadListener(APDownloadListener apDownloadListener) {
    }

    public void setWebChromeClient(APWebChromeClient apWebChromeClient) {
    }

    public APWebSettings getSettings() {
        return this.mWebSettings;
    }

    public APHitTestResult getHitTestResult() {
        return null;
    }

    public Picture capturePicture() {
        return null;
    }

    public void setAPWebViewListener(APWebViewListener apWebViewListener) {
    }

    public View getView() {
        return null;
    }

    public H5Bridge getBridge() {
        return this.bridge;
    }

    public Bundle getParams() {
        return this.mStartParams;
    }

    public boolean exitPage() {
        if (getParent() != null) {
            getParent().removeChild(this);
            setParent(null);
        }
        return true;
    }

    public boolean exitTabPage() {
        return false;
    }

    public void setHandler(H5PageHandler h5PageHandler) {
    }

    public void setH5ErrorHandler(H5ErrorHandler handler) {
    }

    public H5PageData getPageData() {
        return this.mPageData;
    }

    public H5AvailablePageData getAvailablePageData() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public WebViewType getType() {
        return WebViewType.RN_VIEW;
    }

    public H5Plugin getH5NumInputKeyboard() {
        return null;
    }

    public H5Plugin getH5NativeInput() {
        return null;
    }

    public void setScale(float v) {
    }

    public float getScale() {
        return 0.0f;
    }

    public void setVerticalScrollBarEnabled(boolean b) {
    }

    public void setHorizontalScrollBarEnabled(boolean b) {
    }

    public void setOnScrollChangedCallback(H5ScrollChangedCallback h5ScrollChangedCallback) {
    }

    public int getScrollY() {
        return 0;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public boolean getCurrentPageSnapshot(Rect dstRect, Rect srcRect, Bitmap bitmap, boolean clipByView, int coordinate) {
        return false;
    }

    public long getLastTouch() {
        return 0;
    }

    public String getPerformance() {
        return null;
    }

    public void setPerformance(String s) {
    }

    public boolean onInterceptError(String errorUrl, int statusCode) {
        return false;
    }

    public H5TitleBarReadyCallback getTitleBarReadyCallBack() {
        return null;
    }

    public void setTitleBarReadyCallBack(H5TitleBarReadyCallback h5TitleBarReadyCallback) {
    }

    public boolean ifContainsEmbedView() {
        return false;
    }

    public void setContainsEmbedView(boolean ifContains) {
    }

    public boolean ifContainsEmbedSurfaceView() {
        return false;
    }

    public void setContainsEmbedSurfaceView(boolean ifContains) {
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

    public void setExtra(String biz, Object o) {
    }

    public Object getExtra(String biz) {
        return null;
    }

    public void setNewEmbedViewRoot(View view) {
    }

    public View getNewEmbedViewRoot(H5NewEmbedBaseViewListener listener) {
        return null;
    }

    public boolean hasContentBeforeRedirect() {
        return false;
    }

    public void setContentBeforeRedirect(boolean flag) {
    }

    public String getAdvertisementViewTag() {
        return null;
    }

    public boolean isTinyApp() {
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

    public void setPageId(int pageId) {
    }

    public int getPageId() {
        return 0;
    }

    public void setWebViewId(int pageId) {
    }

    public int getWebViewId() {
        return 0;
    }
}
