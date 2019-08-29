package com.uc.webview.export.internal.interfaces;

import com.uc.webview.export.annotations.Interface;

@Interface
/* compiled from: ProGuard */
public interface IWebViewDatabase {
    void clearFormData();

    void clearHttpAuthUsernamePassword();

    @Deprecated
    void clearUsernamePassword();

    boolean hasFormData();

    boolean hasHttpAuthUsernamePassword();

    @Deprecated
    boolean hasUsernamePassword();
}
