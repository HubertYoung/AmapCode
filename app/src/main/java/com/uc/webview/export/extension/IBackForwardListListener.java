package com.uc.webview.export.extension;

import com.uc.webview.export.WebHistoryItem;
import com.uc.webview.export.annotations.Api;

@Api
/* compiled from: ProGuard */
public interface IBackForwardListListener {
    void onIndexChanged(WebHistoryItem webHistoryItem, int i);

    void onNewHistoryItem(WebHistoryItem webHistoryItem);
}
