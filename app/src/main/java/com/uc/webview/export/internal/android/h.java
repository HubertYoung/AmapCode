package com.uc.webview.export.internal.android;

import com.uc.webview.export.WebBackForwardList;
import com.uc.webview.export.WebHistoryItem;

/* compiled from: ProGuard */
final class h extends WebBackForwardList {

    /* compiled from: ProGuard */
    class a extends WebHistoryItem {
        a(android.webkit.WebHistoryItem webHistoryItem) {
            this.mItem = webHistoryItem;
        }
    }

    h(android.webkit.WebBackForwardList webBackForwardList) {
        this.mList = webBackForwardList;
    }

    public final WebHistoryItem createItem(android.webkit.WebHistoryItem webHistoryItem) {
        return new a(webHistoryItem);
    }
}
