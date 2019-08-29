package com.uc.webview.export.internal.interfaces;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.webkit.ValueCallback;
import com.uc.webview.export.annotations.Interface;
import com.uc.webview.export.extension.IBackForwardListListener;
import com.uc.webview.export.extension.UCClient;
import com.uc.webview.export.extension.UCExtension.InjectJSProvider;
import com.uc.webview.export.extension.UCExtension.OnSoftKeyboardListener;
import com.uc.webview.export.extension.UCExtension.TextSelectionClient;
import com.uc.webview.export.extension.UCSettings;

@Interface
/* compiled from: ProGuard */
public interface IUCExtension extends InvokeObject {
    int getActiveLayoutStyle();

    String getBackUrl();

    void getCoreStatus(int i, ValueCallback<Object> valueCallback);

    boolean getCurrentPageSnapshot(Rect rect, Rect rect2, Bitmap bitmap, boolean z, int i);

    String getFocusedNodeAnchorText();

    String getFocusedNodeImageUrl();

    String getFocusedNodeLinkUrl();

    String getForwardUrl();

    String getHttpsRemoteCertificate(String str);

    String getPageEncoding();

    int getPageSize();

    UCSettings getUCSettings();

    boolean ignoreTouchEvent();

    void moveCursorToTextInput(int i);

    @Deprecated
    void notifyVisibleRectChanged();

    void savePage(String str, String str2, int i, ValueCallback<Boolean> valueCallback);

    void setBackForwardListListener(IBackForwardListListener iBackForwardListListener);

    void setClient(UCClient uCClient);

    @Deprecated
    void setEmbeddedTitleBar(View view);

    void setInjectJSProvider(InjectJSProvider injectJSProvider, int i);

    void setPrivateBrowsing(boolean z);

    void setSoftKeyboardListener(OnSoftKeyboardListener onSoftKeyboardListener);

    void setTextSelectionClient(TextSelectionClient textSelectionClient);
}
