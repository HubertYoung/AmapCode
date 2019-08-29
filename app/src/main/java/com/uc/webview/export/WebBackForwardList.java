package com.uc.webview.export;

import android.webkit.WebHistoryItem;
import com.uc.webview.export.annotations.Api;

@Api
/* compiled from: ProGuard */
public class WebBackForwardList {
    public android.webkit.WebBackForwardList mList = null;

    public WebHistoryItem createItem(WebHistoryItem webHistoryItem) {
        return null;
    }

    public synchronized WebHistoryItem getCurrentItem() {
        try {
            WebHistoryItem currentItem = this.mList.getCurrentItem();
            if (currentItem == null) {
                return null;
            }
            return createItem(currentItem);
        }
    }

    public synchronized int getCurrentIndex() {
        return this.mList.getCurrentIndex();
    }

    public synchronized WebHistoryItem getItemAtIndex(int i) {
        WebHistoryItem itemAtIndex = this.mList.getItemAtIndex(i);
        if (itemAtIndex == null) {
            return null;
        }
        return createItem(itemAtIndex);
    }

    public synchronized int getSize() {
        return this.mList.getSize();
    }

    /* access modifiers changed from: protected */
    public synchronized WebBackForwardList clone() {
        return null;
    }
}
