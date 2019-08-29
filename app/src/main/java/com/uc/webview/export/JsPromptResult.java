package com.uc.webview.export;

import com.uc.webview.export.annotations.Api;

@Api
/* compiled from: ProGuard */
public interface JsPromptResult extends JsResult {
    void cancel();

    void confirm();

    void confirm(String str);
}
