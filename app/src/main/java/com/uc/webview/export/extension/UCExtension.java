package com.uc.webview.export.extension;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.webkit.ValueCallback;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.interfaces.IUCExtension;
import com.uc.webview.export.internal.interfaces.IWebView;

@Api
/* compiled from: ProGuard */
public class UCExtension {
    public static final int CORE_STATUS_IS_SUPPORT_HEVC = 0;
    public static final int CORE_STATUS_PROCESS_MODE = 1;
    public static final int CORE_STATUS_SANDBOX_STARTUP_TRACE = 2;
    public static final int LAYOUT_STYLE_ADAPT_SCREEN = 2;
    public static final int LAYOUT_STYLE_MOBILE_OPTIMUM = 4;
    public static final int LAYOUT_STYLE_ZOOM_OPTIMUM = 1;
    public static final String MOVE_CURSOR_KEY_NEXT_ENABLE = "next_enable";
    public static final String MOVE_CURSOR_KEY_PREVIOUS_ENABLE = "previous_enable";
    public static final String MOVE_CURSOR_KEY_SUCCEED = "succeed";
    public static final int MOVE_CURSOR_STEP_CURRENT = 0;
    public static final int MOVE_CURSOR_STEP_NEXT = 1;
    public static final int MOVE_CURSOR_STEP_PREV = -1;
    public static final int TYPE_PAGE_STORAGE_ALL = 2;
    public static final int TYPE_PAGE_STORAGE_MHTML = 3;
    public static final int TYPE_PAGE_STORAGE_ONLY_HTML = 0;
    public static final int TYPE_PAGE_STORAGE_TEXT = 1;
    private IUCExtension a;

    @Api
    /* compiled from: ProGuard */
    public interface InjectJSProvider {
        public static final int TYPE_HEAD_START = 1;
        public static final int TYPE_HEAD_START_NODES = 16;

        String getJS(int i);
    }

    @Api
    /* compiled from: ProGuard */
    public interface OnSoftKeyboardListener {
        boolean displaySoftKeyboard(String str, int i, ValueCallback<String> valueCallback);

        boolean hideSoftKeyboard();

        boolean onFinishComposingText();
    }

    @Api
    /* compiled from: ProGuard */
    public static class TextSelectionClient {
        public boolean onSearchClicked(String str) {
            return false;
        }

        public boolean onShareClicked(String str) {
            return false;
        }

        public boolean shouldShowSearchItem() {
            return true;
        }

        public boolean shouldShowShareItem() {
            return true;
        }
    }

    public UCExtension(IWebView iWebView) {
        this.a = iWebView.getUCExtension();
    }

    public void setClient(UCClient uCClient) {
        this.a.setClient(uCClient);
    }

    public String getBackUrl() {
        return this.a.getBackUrl();
    }

    public String getForwardUrl() {
        return this.a.getForwardUrl();
    }

    public int getPageSize() {
        return this.a.getPageSize();
    }

    public String getPageEncoding() {
        return this.a.getPageEncoding();
    }

    public String getHttpsRemoteCertificate(String str) {
        return this.a.getHttpsRemoteCertificate(str);
    }

    public void setBackForwardListListener(IBackForwardListListener iBackForwardListListener) {
        this.a.setBackForwardListListener(iBackForwardListListener);
    }

    public void moveCursorToTextInput(int i) {
        this.a.moveCursorToTextInput(i);
    }

    public String getFocusedNodeLinkUrl() {
        return this.a.getFocusedNodeLinkUrl();
    }

    public String getFocusedNodeAnchorText() {
        return this.a.getFocusedNodeAnchorText();
    }

    public String getFocusedNodeImageUrl() {
        return this.a.getFocusedNodeImageUrl();
    }

    public int getActiveLayoutStyle() {
        return this.a.getActiveLayoutStyle();
    }

    public UCSettings getUCSettings() {
        return this.a.getUCSettings();
    }

    public void setInjectJSProvider(InjectJSProvider injectJSProvider, int i) {
        this.a.setInjectJSProvider(injectJSProvider, i);
    }

    public void setSoftKeyboardListener(OnSoftKeyboardListener onSoftKeyboardListener) {
        this.a.setSoftKeyboardListener(onSoftKeyboardListener);
    }

    public void setTextSelectionClient(TextSelectionClient textSelectionClient) {
        this.a.setTextSelectionClient(textSelectionClient);
    }

    public boolean isLoadFromCachedPage() {
        Boolean bool = (Boolean) this.a.invoke(4, null);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean ignoreTouchEvent() {
        return this.a.ignoreTouchEvent();
    }

    @Deprecated
    public void setEmbeddedTitleBar(View view) {
        this.a.setEmbeddedTitleBar(view);
    }

    @Deprecated
    public void notifyVisibleRectChanged() {
        this.a.notifyVisibleRectChanged();
    }

    public void savePage(String str, String str2, int i, ValueCallback<Boolean> valueCallback) {
        this.a.savePage(str, str2, i, valueCallback);
    }

    public void getCoreStatus(int i, ValueCallback<Object> valueCallback) {
        this.a.getCoreStatus(i, valueCallback);
    }

    public void setPrivateBrowsing(boolean z) {
        this.a.setPrivateBrowsing(z);
    }

    public boolean injectJavascriptNativeCallback(long j, long j2) {
        Object invoke = this.a.invoke(24, new Object[]{Long.valueOf(j), Long.valueOf(j2)});
        if (invoke == null) {
            return false;
        }
        return ((Boolean) invoke).booleanValue();
    }

    public boolean getCurrentPageSnapshot(Rect rect, Rect rect2, Bitmap bitmap, boolean z, int i) {
        return this.a.getCurrentPageSnapshot(rect, rect2, bitmap, z, i);
    }

    public void getStartupPerformanceStatistics(ValueCallback<String> valueCallback) {
        if (((Boolean) this.a.invoke(26, new Object[]{valueCallback})) == null && valueCallback != null) {
            valueCallback.onReceiveValue(null);
        }
    }
}
