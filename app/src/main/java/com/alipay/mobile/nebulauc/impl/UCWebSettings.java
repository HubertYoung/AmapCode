package com.alipay.mobile.nebulauc.impl;

import android.content.Context;
import com.alipay.mobile.nebula.webview.APWebSettings;
import com.alipay.mobile.nebula.webview.APWebSettings.LayoutAlgorithm;
import com.alipay.mobile.nebula.webview.APWebSettings.PluginState;
import com.alipay.mobile.nebula.webview.APWebSettings.TextSize;
import com.alipay.mobile.nebula.webview.APWebSettings.ZoomDensity;
import com.uc.webview.export.WebSettings;
import com.uc.webview.export.WebView;
import com.uc.webview.export.extension.UCSettings;

final class UCWebSettings implements APWebSettings {
    private WebSettings mUCImpl;
    private WebView webView;

    UCWebSettings(WebView ucWebView) {
        this.webView = ucWebView;
        this.mUCImpl = ucWebView.getSettings();
    }

    public void setSupportZoom(boolean support) {
        this.mUCImpl.setSupportZoom(support);
    }

    public boolean supportZoom() {
        return this.mUCImpl.supportZoom();
    }

    public boolean getMediaPlaybackRequiresUserGesture() {
        return this.mUCImpl.getMediaPlaybackRequiresUserGesture();
    }

    public void setMediaPlaybackRequiresUserGesture(boolean require) {
        this.mUCImpl.setMediaPlaybackRequiresUserGesture(require);
    }

    public boolean getBuiltInZoomControls() {
        return this.mUCImpl.getBuiltInZoomControls();
    }

    public void setBuiltInZoomControls(boolean enabled) {
        this.mUCImpl.setBuiltInZoomControls(enabled);
    }

    public boolean getDisplayZoomControls() {
        return this.mUCImpl.getDisplayZoomControls();
    }

    public void setDisplayZoomControls(boolean enabled) {
        this.mUCImpl.setDisplayZoomControls(enabled);
    }

    public boolean getAllowFileAccess() {
        return this.mUCImpl.getAllowFileAccess();
    }

    public void setAllowFileAccess(boolean allow) {
        this.mUCImpl.setAllowFileAccess(allow);
    }

    public boolean getAllowContentAccess() {
        return this.mUCImpl.getAllowContentAccess();
    }

    public void setAllowContentAccess(boolean allow) {
        this.mUCImpl.setAllowContentAccess(allow);
    }

    public boolean getLoadWithOverviewMode() {
        return this.mUCImpl.getLoadWithOverviewMode();
    }

    public void setLoadWithOverviewMode(boolean overview) {
        this.mUCImpl.setLoadWithOverviewMode(overview);
    }

    public boolean getSaveFormData() {
        return this.mUCImpl.getSaveFormData();
    }

    public void setSaveFormData(boolean save) {
        this.mUCImpl.setSaveFormData(save);
    }

    public boolean getSavePassword() {
        return this.mUCImpl.getSavePassword();
    }

    public void setSavePassword(boolean save) {
        this.mUCImpl.setSaveFormData(save);
    }

    public int getTextZoom() {
        return this.mUCImpl.getTextZoom();
    }

    public void setTextZoom(int textZoom) {
        this.mUCImpl.setTextZoom(textZoom);
    }

    public TextSize getTextSize() {
        return TextSize.valueOf(this.mUCImpl.getTextSize().name());
    }

    public void setTextSize(TextSize textSize) {
        this.mUCImpl.setTextSize(WebSettings.TextSize.valueOf(textSize.name()));
    }

    public ZoomDensity getDefaultZoom() {
        return ZoomDensity.valueOf(this.mUCImpl.getDefaultZoom().name());
    }

    public boolean getUseWideViewPort() {
        return this.mUCImpl.getUseWideViewPort();
    }

    public void setUseWideViewPort(boolean use) {
        this.mUCImpl.setUseWideViewPort(use);
    }

    public void setSupportMultipleWindows(boolean support) {
        this.mUCImpl.setSupportMultipleWindows(support);
    }

    public boolean supportMultipleWindows() {
        return this.mUCImpl.supportMultipleWindows();
    }

    public LayoutAlgorithm getLayoutAlgorithm() {
        return LayoutAlgorithm.valueOf(this.mUCImpl.getLayoutAlgorithm().name());
    }

    public void setLayoutAlgorithm(LayoutAlgorithm layoutAlgorithm) {
        this.mUCImpl.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.valueOf(layoutAlgorithm.name()));
    }

    public String getStandardFontFamily() {
        return this.mUCImpl.getStandardFontFamily();
    }

    public void setStandardFontFamily(String font) {
        this.mUCImpl.setStandardFontFamily(font);
    }

    public String getFixedFontFamily() {
        return this.mUCImpl.getFixedFontFamily();
    }

    public void setFixedFontFamily(String font) {
        this.mUCImpl.setFixedFontFamily(font);
    }

    public String getSansSerifFontFamily() {
        return this.mUCImpl.getSansSerifFontFamily();
    }

    public void setSansSerifFontFamily(String font) {
        this.mUCImpl.setSansSerifFontFamily(font);
    }

    public String getSerifFontFamily() {
        return this.mUCImpl.getSerifFontFamily();
    }

    public void setSerifFontFamily(String font) {
        this.mUCImpl.setSerifFontFamily(font);
    }

    public String getCursiveFontFamily() {
        return this.mUCImpl.getCursiveFontFamily();
    }

    public void setCursiveFontFamily(String font) {
        this.mUCImpl.setCursiveFontFamily(font);
    }

    public String getFantasyFontFamily() {
        return this.mUCImpl.getFantasyFontFamily();
    }

    public void setFantasyFontFamily(String font) {
        this.mUCImpl.setFantasyFontFamily(font);
    }

    public int getMinimumFontSize() {
        return this.mUCImpl.getMinimumFontSize();
    }

    public void setMinimumFontSize(int size) {
        this.mUCImpl.setMinimumFontSize(size);
    }

    public int getMinimumLogicalFontSize() {
        return this.mUCImpl.getMinimumLogicalFontSize();
    }

    public void setMinimumLogicalFontSize(int size) {
        this.mUCImpl.setMinimumLogicalFontSize(size);
    }

    public int getDefaultFontSize() {
        return this.mUCImpl.getDefaultFontSize();
    }

    public void setDefaultFontSize(int size) {
        this.mUCImpl.setDefaultFontSize(size);
    }

    public int getDefaultFixedFontSize() {
        return this.mUCImpl.getDefaultFixedFontSize();
    }

    public void setDefaultFixedFontSize(int size) {
        this.mUCImpl.setDefaultFixedFontSize(size);
    }

    public boolean getLoadsImagesAutomatically() {
        return this.mUCImpl.getLoadsImagesAutomatically();
    }

    public void setLoadsImagesAutomatically(boolean flag) {
        this.mUCImpl.setLoadsImagesAutomatically(flag);
    }

    public boolean getBlockNetworkImage() {
        return this.mUCImpl.getBlockNetworkImage();
    }

    public void setBlockNetworkImage(boolean flag) {
        this.mUCImpl.setBlockNetworkImage(flag);
    }

    public boolean getJavaScriptEnabled() {
        return this.mUCImpl.getJavaScriptEnabled();
    }

    public void setJavaScriptEnabled(boolean flag) {
        this.mUCImpl.setJavaScriptEnabled(flag);
    }

    public void setGeolocationDatabasePath(String databasePath) {
        this.mUCImpl.setGeolocationDatabasePath(databasePath);
    }

    public void setAppCacheEnabled(boolean flag) {
        this.mUCImpl.setAppCacheEnabled(flag);
    }

    public void setAppCachePath(String appCachePath) {
        this.mUCImpl.setAppCachePath(appCachePath);
    }

    public boolean getDatabaseEnabled() {
        return this.mUCImpl.getDatabaseEnabled();
    }

    public void setDatabaseEnabled(boolean flag) {
        this.mUCImpl.setDatabaseEnabled(flag);
    }

    public boolean getDomStorageEnabled() {
        return this.mUCImpl.getDomStorageEnabled();
    }

    public void setDomStorageEnabled(boolean flag) {
        this.mUCImpl.setDomStorageEnabled(flag);
    }

    public String getDatabasePath() {
        return this.mUCImpl.getDatabasePath();
    }

    public void setDatabasePath(String databasePath) {
        this.mUCImpl.setDatabasePath(databasePath);
    }

    public void setGeolocationEnabled(boolean flag) {
        this.mUCImpl.setGeolocationEnabled(flag);
    }

    public boolean getAllowUniversalAccessFromFileURLs() {
        return this.mUCImpl.getAllowUniversalAccessFromFileURLs();
    }

    public void setAllowUniversalAccessFromFileURLs(boolean flag) {
        this.mUCImpl.setAllowUniversalAccessFromFileURLs(flag);
    }

    public boolean getAllowFileAccessFromFileURLs() {
        return this.mUCImpl.getAllowFileAccessFromFileURLs();
    }

    public void setAllowFileAccessFromFileURLs(boolean flag) {
        this.mUCImpl.setAllowFileAccessFromFileURLs(flag);
    }

    public PluginState getPluginState() {
        return PluginState.valueOf(this.mUCImpl.getPluginState().name());
    }

    public void setPluginState(PluginState pluginState) {
        this.mUCImpl.setPluginState(WebSettings.PluginState.valueOf(pluginState.name()));
    }

    public boolean getJavaScriptCanOpenWindowsAutomatically() {
        return this.mUCImpl.getJavaScriptCanOpenWindowsAutomatically();
    }

    public void setJavaScriptCanOpenWindowsAutomatically(boolean flag) {
        this.mUCImpl.setJavaScriptCanOpenWindowsAutomatically(flag);
    }

    public String getDefaultTextEncodingName() {
        return this.mUCImpl.getDefaultTextEncodingName();
    }

    public void setDefaultTextEncodingName(String encoding) {
        this.mUCImpl.setDefaultTextEncodingName(encoding);
    }

    public String getUserAgentString() {
        return this.mUCImpl.getUserAgentString();
    }

    public void setUserAgentString(String ua) {
        this.mUCImpl.setUserAgentString(ua);
    }

    public String getDefaultUserAgent(Context context) {
        return null;
    }

    public void setNeedInitialFocus(boolean flag) {
        this.mUCImpl.setNeedInitialFocus(flag);
    }

    public int getCacheMode() {
        return this.mUCImpl.getCacheMode();
    }

    public void setCacheMode(int mode) {
        this.mUCImpl.setCacheMode(mode);
    }

    public void setEnableFastScroller(boolean b) {
        if (this.webView.getUCExtension() != null && this.webView.getUCExtension().getUCSettings() != null) {
            this.webView.getUCExtension().getUCSettings().setEnableFastScroller(b);
        }
    }

    public void setPageCacheCapacity(int i) {
        if (this.webView.getUCExtension() != null && this.webView.getUCExtension().getUCSettings() != null) {
            this.webView.getUCExtension().getUCSettings();
            UCSettings.setPageCacheCapacity(i);
        }
    }
}
