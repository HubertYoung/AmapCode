package com.uc.webview.export;

import android.graphics.Bitmap;
import com.uc.webview.export.annotations.Api;

@Api
/* compiled from: ProGuard */
public class WebHistoryItem {
    public android.webkit.WebHistoryItem mItem = null;

    public String getUrl() {
        return this.mItem.getUrl();
    }

    public String getOriginalUrl() {
        return this.mItem.getOriginalUrl();
    }

    public String getTitle() {
        return this.mItem.getTitle();
    }

    public Bitmap getFavicon() {
        return this.mItem.getFavicon();
    }

    /* access modifiers changed from: protected */
    public synchronized WebHistoryItem clone() {
        return null;
    }
}
