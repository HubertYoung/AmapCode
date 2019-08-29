package com.alipay.mobile.nebulacore.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.webkit.WebSettings;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.webview.APWebSettings;
import com.alipay.mobile.nebula.webview.APWebSettings.LayoutAlgorithm;
import com.alipay.mobile.nebula.webview.APWebSettings.PluginState;
import com.alipay.mobile.nebula.webview.APWebSettings.TextSize;
import com.alipay.mobile.nebula.webview.APWebSettings.ZoomDensity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class AndroidWebSettings implements APWebSettings {
    public static final String TAG = "AndroidWebSettings";
    private static Method a = null;
    private WebSettings b;

    AndroidWebSettings(WebSettings androidWebSettings) {
        this.b = androidWebSettings;
        if (this.b != null && VERSION.SDK_INT >= 21) {
            if (a == null) {
                try {
                    a = this.b.getClass().getMethod("setMixedContentMode", new Class[]{Integer.TYPE});
                } catch (NoSuchMethodException e) {
                    H5Log.e(TAG, "Exception", e);
                }
            }
            if (a != null) {
                try {
                    a.invoke(this.b, new Object[]{Integer.valueOf(0)});
                } catch (IllegalAccessException e2) {
                    H5Log.e(TAG, "AndroidWebSettings IllegalAccessException", e2);
                } catch (InvocationTargetException e3) {
                    H5Log.e(TAG, "AndroidWebSettings InvocationTargetException", e3);
                }
            }
        }
    }

    public final void setSupportZoom(boolean support) {
        this.b.setSupportZoom(support);
    }

    public final boolean supportZoom() {
        return this.b.supportZoom();
    }

    @TargetApi(17)
    public final boolean getMediaPlaybackRequiresUserGesture() {
        if (VERSION.SDK_INT >= 17) {
            return this.b.getMediaPlaybackRequiresUserGesture();
        }
        return false;
    }

    @TargetApi(17)
    public final void setMediaPlaybackRequiresUserGesture(boolean require) {
        if (VERSION.SDK_INT >= 17) {
            try {
                this.b.setMediaPlaybackRequiresUserGesture(require);
            } catch (NoSuchMethodError e) {
                H5Log.e(TAG, "There is no method some models.", e);
            }
        }
    }

    public final boolean getBuiltInZoomControls() {
        return this.b.getBuiltInZoomControls();
    }

    @TargetApi(11)
    public final void setBuiltInZoomControls(boolean enabled) {
        if (VERSION.SDK_INT >= 11) {
            this.b.setBuiltInZoomControls(enabled);
        }
    }

    @TargetApi(11)
    public final boolean getDisplayZoomControls() {
        if (VERSION.SDK_INT >= 11) {
            return this.b.getDisplayZoomControls();
        }
        return false;
    }

    @TargetApi(11)
    public final void setDisplayZoomControls(boolean enabled) {
        if (VERSION.SDK_INT >= 11) {
            this.b.setDisplayZoomControls(enabled);
        }
    }

    public final boolean getAllowFileAccess() {
        return this.b.getAllowFileAccess();
    }

    public final void setAllowFileAccess(boolean allow) {
        this.b.setAllowFileAccess(allow);
    }

    @TargetApi(11)
    public final boolean getAllowContentAccess() {
        if (VERSION.SDK_INT >= 11) {
            return this.b.getAllowContentAccess();
        }
        return false;
    }

    @TargetApi(11)
    public final void setAllowContentAccess(boolean allow) {
        if (VERSION.SDK_INT >= 11) {
            this.b.setAllowContentAccess(allow);
        }
    }

    public final boolean getLoadWithOverviewMode() {
        return this.b.getLoadWithOverviewMode();
    }

    public final void setLoadWithOverviewMode(boolean overview) {
        this.b.setLoadWithOverviewMode(overview);
    }

    public final boolean getSaveFormData() {
        return this.b.getSaveFormData();
    }

    public final void setSaveFormData(boolean save) {
        this.b.setSaveFormData(save);
    }

    public final boolean getSavePassword() {
        return this.b.getSavePassword();
    }

    public final void setSavePassword(boolean save) {
        this.b.setSavePassword(save);
    }

    @TargetApi(14)
    public final int getTextZoom() {
        if (VERSION.SDK_INT >= 14) {
            return this.b.getTextZoom();
        }
        return 10;
    }

    @TargetApi(14)
    public final void setTextZoom(int textZoom) {
        if (VERSION.SDK_INT >= 14) {
            this.b.setTextZoom(textZoom);
        }
    }

    public final TextSize getTextSize() {
        return TextSize.valueOf(this.b.getTextSize().name());
    }

    public final void setTextSize(TextSize t) {
        WebSettings.TextSize textSize = null;
        switch (t) {
            case LARGER:
                textSize = WebSettings.TextSize.LARGER;
                break;
            case LARGEST:
                textSize = WebSettings.TextSize.LARGEST;
                break;
            case NORMAL:
                textSize = WebSettings.TextSize.NORMAL;
                break;
            case SMALLER:
                textSize = WebSettings.TextSize.SMALLER;
                break;
            case SMALLEST:
                textSize = WebSettings.TextSize.SMALLEST;
                break;
        }
        if (textSize != null) {
            this.b.setTextSize(textSize);
        }
    }

    public final ZoomDensity getDefaultZoom() {
        return ZoomDensity.valueOf(this.b.getDefaultZoom().name());
    }

    public final boolean getUseWideViewPort() {
        return this.b.getUseWideViewPort();
    }

    public final void setUseWideViewPort(boolean use) {
        this.b.setUseWideViewPort(use);
    }

    public final void setSupportMultipleWindows(boolean support) {
        this.b.setSupportMultipleWindows(support);
    }

    public final boolean supportMultipleWindows() {
        return this.b.supportMultipleWindows();
    }

    public final LayoutAlgorithm getLayoutAlgorithm() {
        return LayoutAlgorithm.valueOf(this.b.getLayoutAlgorithm().name());
    }

    @TargetApi(19)
    public final void setLayoutAlgorithm(LayoutAlgorithm l) {
        WebSettings.LayoutAlgorithm androidL = null;
        switch (l) {
            case NARROW_COLUMNS:
                androidL = WebSettings.LayoutAlgorithm.NARROW_COLUMNS;
                break;
            case NORMAL:
                androidL = WebSettings.LayoutAlgorithm.NORMAL;
                break;
            case SINGLE_COLUMN:
                androidL = WebSettings.LayoutAlgorithm.SINGLE_COLUMN;
                break;
            case TEXT_AUTOSIZING:
                if (VERSION.SDK_INT >= 19) {
                    androidL = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;
                    break;
                }
                break;
        }
        if (androidL != null) {
            this.b.setLayoutAlgorithm(androidL);
        }
    }

    public final String getStandardFontFamily() {
        return this.b.getStandardFontFamily();
    }

    public final void setStandardFontFamily(String font) {
        this.b.setStandardFontFamily(font);
    }

    public final String getFixedFontFamily() {
        return this.b.getFixedFontFamily();
    }

    public final void setFixedFontFamily(String font) {
        this.b.setFixedFontFamily(font);
    }

    public final String getSansSerifFontFamily() {
        return this.b.getSansSerifFontFamily();
    }

    public final void setSansSerifFontFamily(String font) {
        this.b.setSansSerifFontFamily(font);
    }

    public final String getSerifFontFamily() {
        return this.b.getSerifFontFamily();
    }

    public final void setSerifFontFamily(String font) {
        this.b.setSerifFontFamily(font);
    }

    public final String getCursiveFontFamily() {
        return this.b.getCursiveFontFamily();
    }

    public final void setCursiveFontFamily(String font) {
        this.b.setCursiveFontFamily(font);
    }

    public final String getFantasyFontFamily() {
        return this.b.getFantasyFontFamily();
    }

    public final void setFantasyFontFamily(String font) {
        this.b.setFantasyFontFamily(font);
    }

    public final int getMinimumFontSize() {
        return this.b.getMinimumFontSize();
    }

    public final void setMinimumFontSize(int size) {
        this.b.setMinimumFontSize(size);
    }

    public final int getMinimumLogicalFontSize() {
        return this.b.getMinimumLogicalFontSize();
    }

    public final void setMinimumLogicalFontSize(int size) {
        this.b.setMinimumLogicalFontSize(size);
    }

    public final int getDefaultFontSize() {
        return this.b.getDefaultFontSize();
    }

    public final void setDefaultFontSize(int size) {
        this.b.setDefaultFontSize(size);
    }

    public final int getDefaultFixedFontSize() {
        return this.b.getDefaultFixedFontSize();
    }

    public final void setDefaultFixedFontSize(int size) {
        this.b.setDefaultFixedFontSize(size);
    }

    public final boolean getLoadsImagesAutomatically() {
        return this.b.getLoadsImagesAutomatically();
    }

    public final void setLoadsImagesAutomatically(boolean flag) {
        this.b.setLoadsImagesAutomatically(flag);
    }

    public final boolean getBlockNetworkImage() {
        return this.b.getBlockNetworkImage();
    }

    public final void setBlockNetworkImage(boolean flag) {
        this.b.setBlockNetworkImage(flag);
    }

    public final boolean getJavaScriptEnabled() {
        return this.b.getJavaScriptEnabled();
    }

    public final void setJavaScriptEnabled(boolean flag) {
        this.b.setJavaScriptEnabled(flag);
    }

    public final void setGeolocationDatabasePath(String databasePath) {
        this.b.setGeolocationDatabasePath(databasePath);
    }

    public final void setAppCacheEnabled(boolean flag) {
        this.b.setAppCacheEnabled(flag);
    }

    public final void setAppCachePath(String appCachePath) {
        this.b.setAppCachePath(appCachePath);
    }

    public final boolean getDatabaseEnabled() {
        return this.b.getDatabaseEnabled();
    }

    public final void setDatabaseEnabled(boolean flag) {
        this.b.setDatabaseEnabled(flag);
    }

    public final boolean getDomStorageEnabled() {
        return this.b.getDomStorageEnabled();
    }

    public final void setDomStorageEnabled(boolean flag) {
        this.b.setDomStorageEnabled(flag);
    }

    public final String getDatabasePath() {
        return this.b.getDatabasePath();
    }

    public final void setDatabasePath(String databasePath) {
        this.b.setDatabasePath(databasePath);
    }

    public final void setGeolocationEnabled(boolean flag) {
        this.b.setGeolocationEnabled(flag);
    }

    @TargetApi(16)
    public final boolean getAllowUniversalAccessFromFileURLs() {
        if (VERSION.SDK_INT >= 16) {
            return this.b.getAllowUniversalAccessFromFileURLs();
        }
        return false;
    }

    @TargetApi(16)
    public final void setAllowUniversalAccessFromFileURLs(boolean flag) {
        if (VERSION.SDK_INT >= 16) {
            this.b.setAllowUniversalAccessFromFileURLs(flag);
        }
    }

    @TargetApi(16)
    public final boolean getAllowFileAccessFromFileURLs() {
        if (VERSION.SDK_INT >= 16) {
            return this.b.getAllowFileAccessFromFileURLs();
        }
        return false;
    }

    @TargetApi(16)
    public final void setAllowFileAccessFromFileURLs(boolean flag) {
        if (VERSION.SDK_INT >= 16) {
            this.b.setAllowFileAccessFromFileURLs(flag);
        }
    }

    public final PluginState getPluginState() {
        return PluginState.valueOf(this.b.getPluginState().name());
    }

    public final void setPluginState(PluginState state) {
        switch (state) {
            case OFF:
                this.b.setPluginState(WebSettings.PluginState.OFF);
                return;
            case ON:
                this.b.setPluginState(WebSettings.PluginState.ON);
                return;
            case ON_DEMAND:
                this.b.setPluginState(WebSettings.PluginState.ON_DEMAND);
                return;
            default:
                return;
        }
    }

    public final boolean getJavaScriptCanOpenWindowsAutomatically() {
        return this.b.getJavaScriptCanOpenWindowsAutomatically();
    }

    public final void setJavaScriptCanOpenWindowsAutomatically(boolean flag) {
        this.b.setJavaScriptCanOpenWindowsAutomatically(flag);
    }

    public final String getDefaultTextEncodingName() {
        return this.b.getDefaultTextEncodingName();
    }

    public final void setDefaultTextEncodingName(String encoding) {
        this.b.setDefaultTextEncodingName(encoding);
    }

    public final String getUserAgentString() {
        return this.b.getUserAgentString();
    }

    public final void setUserAgentString(String ua) {
        this.b.setUserAgentString(ua);
    }

    public final String getDefaultUserAgent(Context context) {
        return null;
    }

    public final void setNeedInitialFocus(boolean flag) {
        this.b.setNeedInitialFocus(flag);
    }

    public final int getCacheMode() {
        return this.b.getCacheMode();
    }

    public final void setCacheMode(int mode) {
        this.b.setCacheMode(mode);
    }

    public final void setEnableFastScroller(boolean enable) {
    }

    public final void setPageCacheCapacity(int cacheSize) {
    }
}
