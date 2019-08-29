package com.uc.webview.export;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.utility.ReflectionUtil;

@Api
/* compiled from: ProGuard */
public abstract class WebSettings {
    public static final int COOKIE_TYPE_SYSTEM = 1;
    public static final int COOKIE_TYPE_UC = 2;
    public static final int COOKIE_TYPE_UC_ENCRYPT = 3;
    public static final int LOAD_CACHE_ELSE_NETWORK = 1;
    public static final int LOAD_CACHE_ONLY = 3;
    public static final int LOAD_DEFAULT = -1;
    @Deprecated
    public static final int LOAD_NORMAL = 0;
    public static final int LOAD_NO_CACHE = 2;
    public static final int MENU_ITEM_NONE = 0;
    public static final int MENU_ITEM_PROCESS_TEXT = 4;
    public static final int MENU_ITEM_SHARE = 1;
    public static final int MENU_ITEM_WEB_SEARCH = 2;
    public static final int MIXED_CONTENT_ALWAYS_ALLOW = 0;
    public static final int MIXED_CONTENT_COMPATIBILITY_MODE = 2;
    public static final int MIXED_CONTENT_NEVER_ALLOW = 1;
    private String a = "";
    public android.webkit.WebSettings mSettings = null;

    @Api
    /* compiled from: ProGuard */
    public enum LayoutAlgorithm {
        NORMAL,
        SINGLE_COLUMN,
        NARROW_COLUMNS,
        TEXT_AUTOSIZING
    }

    @Api
    /* compiled from: ProGuard */
    public enum PluginState {
        ON,
        ON_DEMAND,
        OFF
    }

    @Api
    /* compiled from: ProGuard */
    public enum RenderPriority {
        NORMAL,
        HIGH,
        LOW
    }

    @Api
    /* compiled from: ProGuard */
    public enum TextSize {
        SMALLEST(50),
        SMALLER(75),
        NORMAL(100),
        LARGER(150),
        LARGEST(200);
        
        public int value;

        private TextSize(int i) {
            this.value = i;
        }
    }

    @Api
    /* compiled from: ProGuard */
    public enum ZoomDensity {
        FAR(150),
        MEDIUM(100),
        CLOSE(75);
        
        int a;

        private ZoomDensity(int i) {
            this.a = i;
        }

        public final int getValue() {
            return this.a;
        }
    }

    @Deprecated
    public void setNavDump(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setNavDump", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
    }

    @Deprecated
    public boolean getNavDump() {
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getNavDump");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public void setSupportZoom(boolean z) {
        this.mSettings.setSupportZoom(z);
    }

    public boolean supportZoom() {
        return this.mSettings.supportZoom();
    }

    public void setMediaPlaybackRequiresUserGesture(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setMediaPlaybackRequiresUserGesture", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
    }

    public boolean getMediaPlaybackRequiresUserGesture() {
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getMediaPlaybackRequiresUserGesture");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public void setBuiltInZoomControls(boolean z) {
        this.mSettings.setBuiltInZoomControls(z);
    }

    public boolean getBuiltInZoomControls() {
        return this.mSettings.getBuiltInZoomControls();
    }

    @TargetApi(11)
    public void setDisplayZoomControls(boolean z) {
        if (VERSION.SDK_INT >= 11) {
            this.mSettings.setDisplayZoomControls(z);
        }
    }

    @TargetApi(11)
    public boolean getDisplayZoomControls() {
        if (VERSION.SDK_INT >= 11) {
            return this.mSettings.getDisplayZoomControls();
        }
        return false;
    }

    public void setAllowFileAccess(boolean z) {
        this.mSettings.setAllowFileAccess(z);
    }

    public boolean getAllowFileAccess() {
        return this.mSettings.getAllowFileAccess();
    }

    @TargetApi(11)
    public void setAllowContentAccess(boolean z) {
        if (VERSION.SDK_INT >= 11) {
            this.mSettings.setAllowContentAccess(z);
        }
    }

    @TargetApi(11)
    public boolean getAllowContentAccess() {
        return VERSION.SDK_INT >= 11 && this.mSettings.getAllowContentAccess();
    }

    public void setLoadWithOverviewMode(boolean z) {
        this.mSettings.setLoadWithOverviewMode(z);
    }

    public boolean getLoadWithOverviewMode() {
        return this.mSettings.getLoadWithOverviewMode();
    }

    @Deprecated
    public void setEnableSmoothTransition(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setEnableSmoothTransition", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
    }

    @Deprecated
    public boolean enableSmoothTransition() {
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "enableSmoothTransition");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Deprecated
    public void setUseWebViewBackgroundForOverscrollBackground(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setUseWebViewBackgroundForOverscrollBackground", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
    }

    @Deprecated
    public boolean getUseWebViewBackgroundForOverscrollBackground() {
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getUseWebViewBackgroundForOverscrollBackground");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public void setSaveFormData(boolean z) {
        this.mSettings.setSaveFormData(z);
    }

    public boolean getSaveFormData() {
        return this.mSettings.getSaveFormData();
    }

    @Deprecated
    public void setSavePassword(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setSavePassword", new Class[]{Boolean.class}, new Object[]{Boolean.valueOf(z)});
    }

    @Deprecated
    public boolean getSavePassword() {
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getSavePassword");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @TargetApi(14)
    public synchronized void setTextZoom(int i) {
        if (VERSION.SDK_INT >= 14) {
            this.mSettings.setTextZoom(i);
        }
    }

    @TargetApi(14)
    public synchronized int getTextZoom() {
        if (VERSION.SDK_INT < 14) {
            return 0;
        }
        return this.mSettings.getTextZoom();
    }

    public synchronized void setTextSize(TextSize textSize) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setTextSize", new Class[]{android.webkit.WebSettings.TextSize.class}, new Object[]{android.webkit.WebSettings.TextSize.valueOf(textSize.name())});
    }

    public synchronized TextSize getTextSize() {
        android.webkit.WebSettings.TextSize textSize = (android.webkit.WebSettings.TextSize) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getTextSize");
        if (textSize == null) {
            return null;
        }
        return TextSize.valueOf(textSize.name());
    }

    @Deprecated
    public void setDefaultZoom(ZoomDensity zoomDensity) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setDefaultZoom", new Class[]{android.webkit.WebSettings.ZoomDensity.class}, new Object[]{android.webkit.WebSettings.ZoomDensity.valueOf(zoomDensity.name())});
    }

    public ZoomDensity getDefaultZoom() {
        android.webkit.WebSettings.ZoomDensity zoomDensity = (android.webkit.WebSettings.ZoomDensity) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getDefaultZoom");
        if (zoomDensity == null) {
            return null;
        }
        return ZoomDensity.valueOf(zoomDensity.name());
    }

    @Deprecated
    public void setLightTouchEnabled(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setLightTouchEnabled", new Class[]{Boolean.class}, new Object[]{Boolean.valueOf(z)});
    }

    @Deprecated
    public boolean getLightTouchEnabled() {
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getLightTouchEnabled");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Deprecated
    public synchronized void setUseDoubleTree(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setUseDoubleTree", new Class[]{Boolean.class}, new Object[]{Boolean.valueOf(z)});
    }

    @Deprecated
    public synchronized boolean getUseDoubleTree() {
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getUseDoubleTree");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Deprecated
    public synchronized void setUserAgent(int i) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setUserAgent", new Class[]{Integer.class}, new Object[]{Integer.valueOf(i)});
    }

    @Deprecated
    public synchronized int getUserAgent() {
        Integer num = (Integer) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getUserAgent");
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public synchronized void setUseWideViewPort(boolean z) {
        this.mSettings.setUseWideViewPort(z);
    }

    public synchronized boolean getUseWideViewPort() {
        return this.mSettings.getUseWideViewPort();
    }

    public synchronized void setSupportMultipleWindows(boolean z) {
        this.mSettings.setSupportMultipleWindows(z);
    }

    public synchronized boolean supportMultipleWindows() {
        return this.mSettings.supportMultipleWindows();
    }

    public synchronized void setLayoutAlgorithm(LayoutAlgorithm layoutAlgorithm) {
        this.mSettings.setLayoutAlgorithm(android.webkit.WebSettings.LayoutAlgorithm.valueOf(layoutAlgorithm.name()));
    }

    public synchronized LayoutAlgorithm getLayoutAlgorithm() {
        return LayoutAlgorithm.valueOf(this.mSettings.getLayoutAlgorithm().name());
    }

    public synchronized void setStandardFontFamily(String str) {
        this.mSettings.setStandardFontFamily(str);
    }

    public synchronized String getStandardFontFamily() {
        return this.mSettings.getStandardFontFamily();
    }

    public synchronized void setFixedFontFamily(String str) {
        this.mSettings.setFixedFontFamily(str);
    }

    public synchronized String getFixedFontFamily() {
        return this.mSettings.getFixedFontFamily();
    }

    public synchronized void setSansSerifFontFamily(String str) {
        this.mSettings.setSansSerifFontFamily(str);
    }

    public synchronized String getSansSerifFontFamily() {
        return this.mSettings.getSansSerifFontFamily();
    }

    public synchronized void setSerifFontFamily(String str) {
        this.mSettings.setSerifFontFamily(str);
    }

    public synchronized String getSerifFontFamily() {
        return this.mSettings.getSerifFontFamily();
    }

    public synchronized void setCursiveFontFamily(String str) {
        this.mSettings.setCursiveFontFamily(str);
    }

    public synchronized String getCursiveFontFamily() {
        return this.mSettings.getCursiveFontFamily();
    }

    public synchronized void setFantasyFontFamily(String str) {
        this.mSettings.setFantasyFontFamily(str);
    }

    public synchronized String getFantasyFontFamily() {
        return this.mSettings.getFantasyFontFamily();
    }

    public synchronized void setMinimumFontSize(int i) {
        this.mSettings.setMinimumFontSize(i);
    }

    public synchronized int getMinimumFontSize() {
        return this.mSettings.getMinimumFontSize();
    }

    public synchronized void setMinimumLogicalFontSize(int i) {
        this.mSettings.setMinimumLogicalFontSize(i);
    }

    public synchronized int getMinimumLogicalFontSize() {
        return this.mSettings.getMinimumLogicalFontSize();
    }

    public synchronized void setDefaultFontSize(int i) {
        this.mSettings.setDefaultFontSize(i);
    }

    public synchronized int getDefaultFontSize() {
        return this.mSettings.getDefaultFontSize();
    }

    public synchronized void setDefaultFixedFontSize(int i) {
        this.mSettings.setDefaultFixedFontSize(i);
    }

    public synchronized int getDefaultFixedFontSize() {
        return this.mSettings.getDefaultFixedFontSize();
    }

    public synchronized void setLoadsImagesAutomatically(boolean z) {
        this.mSettings.setLoadsImagesAutomatically(z);
    }

    public synchronized boolean getLoadsImagesAutomatically() {
        return this.mSettings.getLoadsImagesAutomatically();
    }

    public synchronized void setBlockNetworkImage(boolean z) {
        this.mSettings.setBlockNetworkImage(z);
    }

    public synchronized boolean getBlockNetworkImage() {
        return this.mSettings.getBlockNetworkImage();
    }

    public synchronized void setBlockNetworkLoads(boolean z) {
        this.mSettings.setBlockNetworkLoads(z);
    }

    public synchronized boolean getBlockNetworkLoads() {
        return this.mSettings.getBlockNetworkLoads();
    }

    public synchronized void setJavaScriptEnabled(boolean z) {
        this.mSettings.setJavaScriptEnabled(z);
    }

    public void setAllowUniversalAccessFromFileURLs(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setAllowUniversalAccessFromFileURLs", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
    }

    public void setAllowFileAccessFromFileURLs(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setAllowFileAccessFromFileURLs", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
    }

    @Deprecated
    public synchronized void setPluginsEnabled(boolean z) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setPluginsEnabled", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
    }

    @Deprecated
    public synchronized void setPluginState(PluginState pluginState) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setPluginState", new Class[]{android.webkit.WebSettings.PluginState.class}, new Object[]{android.webkit.WebSettings.PluginState.valueOf(pluginState.name())});
    }

    @Deprecated
    public synchronized void setPluginsPath(String str) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setPluginsPath", new Class[]{String.class}, new Object[]{str});
    }

    public synchronized void setDatabasePath(String str) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setDatabasePath", new Class[]{String.class}, new Object[]{str});
    }

    public synchronized void setGeolocationDatabasePath(String str) {
        this.mSettings.setGeolocationDatabasePath(str);
    }

    public synchronized void setAppCacheEnabled(boolean z) {
        this.mSettings.setAppCacheEnabled(z);
    }

    public synchronized void setAppCachePath(String str) {
        this.mSettings.setAppCachePath(str);
    }

    @Deprecated
    public synchronized void setAppCacheMaxSize(long j) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setAppCacheMaxSize", new Class[]{Long.class}, new Object[]{Long.valueOf(j)});
    }

    public synchronized void setDatabaseEnabled(boolean z) {
        this.mSettings.setDatabaseEnabled(z);
    }

    public synchronized void setDomStorageEnabled(boolean z) {
        this.mSettings.setDomStorageEnabled(z);
    }

    public synchronized boolean getDomStorageEnabled() {
        return this.mSettings.getDomStorageEnabled();
    }

    public synchronized String getDatabasePath() {
        String str = (String) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getDatabasePath");
        if (str == null) {
            return null;
        }
        return str;
    }

    public synchronized boolean getDatabaseEnabled() {
        return this.mSettings.getDatabaseEnabled();
    }

    public synchronized void setGeolocationEnabled(boolean z) {
        this.mSettings.setGeolocationEnabled(z);
    }

    public synchronized boolean getJavaScriptEnabled() {
        return this.mSettings.getJavaScriptEnabled();
    }

    public boolean getAllowUniversalAccessFromFileURLs() {
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getAllowUniversalAccessFromFileURLs");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean getAllowFileAccessFromFileURLs() {
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getAllowFileAccessFromFileURLs");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002f, code lost:
        return r2;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean getPluginsEnabled() {
        /*
            r3 = this;
            monitor-enter(r3)
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0030 }
            r1 = 17
            r2 = 0
            if (r0 > r1) goto L_0x001c
            android.webkit.WebSettings r0 = r3.mSettings     // Catch:{ all -> 0x0030 }
            java.lang.String r1 = "getPluginsEnabled"
            java.lang.Object r0 = com.uc.webview.export.internal.utility.ReflectionUtil.invokeNoThrow(r0, r1)     // Catch:{ all -> 0x0030 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x0030 }
            if (r0 != 0) goto L_0x0016
            monitor-exit(r3)
            return r2
        L_0x0016:
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x0030 }
            monitor-exit(r3)
            return r0
        L_0x001c:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0030 }
            r1 = 18
            if (r0 != r1) goto L_0x002e
            android.webkit.WebSettings r0 = r3.mSettings     // Catch:{ all -> 0x0030 }
            android.webkit.WebSettings$PluginState r0 = r0.getPluginState()     // Catch:{ all -> 0x0030 }
            android.webkit.WebSettings$PluginState r1 = android.webkit.WebSettings.PluginState.ON     // Catch:{ all -> 0x0030 }
            if (r1 != r0) goto L_0x002e
            r0 = 1
            r2 = 1
        L_0x002e:
            monitor-exit(r3)
            return r2
        L_0x0030:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.WebSettings.getPluginsEnabled():boolean");
    }

    @Deprecated
    public synchronized PluginState getPluginState() {
        android.webkit.WebSettings.PluginState pluginState = (android.webkit.WebSettings.PluginState) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getPluginState");
        if (pluginState == null) {
            return null;
        }
        return PluginState.valueOf(pluginState.name());
    }

    @Deprecated
    public synchronized String getPluginsPath() {
        String str = (String) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getPluginsPath");
        if (str == null) {
            return null;
        }
        return str;
    }

    public synchronized void setJavaScriptCanOpenWindowsAutomatically(boolean z) {
        this.mSettings.setJavaScriptCanOpenWindowsAutomatically(z);
    }

    public synchronized boolean getJavaScriptCanOpenWindowsAutomatically() {
        return this.mSettings.getJavaScriptCanOpenWindowsAutomatically();
    }

    public synchronized void setDefaultTextEncodingName(String str) {
        this.mSettings.setDefaultTextEncodingName(str);
    }

    public synchronized String getDefaultTextEncodingName() {
        return this.mSettings.getDefaultTextEncodingName();
    }

    public synchronized void setUserAgentString(String str) {
        this.mSettings.setUserAgentString(str);
    }

    public synchronized String getUserAgentString() {
        try {
        }
        return this.mSettings.getUserAgentString();
    }

    public static String getDefaultUserAgent(Context context) {
        return (String) SDKFactory.invoke(10056, context);
    }

    public void setNeedInitialFocus(boolean z) {
        this.mSettings.setNeedInitialFocus(z);
    }

    @Deprecated
    public synchronized void setRenderPriority(RenderPriority renderPriority) {
        ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setRenderPriority", new Class[]{android.webkit.WebSettings.RenderPriority.class}, new Object[]{android.webkit.WebSettings.RenderPriority.valueOf(renderPriority.name())});
    }

    public void setCacheMode(int i) {
        this.mSettings.setCacheMode(i);
    }

    public int getCacheMode() {
        return this.mSettings.getCacheMode();
    }

    public synchronized void setPreCacheScope(String str) {
        this.a = str;
    }

    public synchronized String getPreCacheScope() {
        return this.a;
    }

    public void setMixedContentMode(int i) {
        if (VERSION.SDK_INT >= 21) {
            ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setMixedContentMode", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
        }
    }

    public int getMixedContentMode() {
        if (VERSION.SDK_INT < 21) {
            return 0;
        }
        Integer num = (Integer) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getMixedContentMode");
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    @TargetApi(23)
    public void setOffscreenPreRaster(boolean z) {
        if (VERSION.SDK_INT >= 23) {
            ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setOffscreenPreRaster", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
        }
    }

    @TargetApi(23)
    public boolean getOffscreenPreRaster() {
        if (VERSION.SDK_INT < 23) {
            return false;
        }
        Boolean bool = (Boolean) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getOffscreenPreRaster");
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @TargetApi(24)
    public void setDisabledActionModeMenuItems(int i) {
        if (VERSION.SDK_INT >= 24) {
            ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "setDisabledActionModeMenuItems", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
        }
    }

    @TargetApi(24)
    public int getDisabledActionModeMenuItems() {
        if (VERSION.SDK_INT < 24) {
            return 0;
        }
        Integer num = (Integer) ReflectionUtil.invokeNoThrow((Object) this.mSettings, (String) "getDisabledActionModeMenuItems");
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }
}
