package com.alipay.mobile.nebulacore.android;

import android.webkit.WebBackForwardList;
import com.alipay.mobile.nebula.webview.APWebBackForwardList;
import com.alipay.mobile.nebula.webview.APWebHistoryItem;

public class AndroidWebBackForwardList implements APWebBackForwardList {
    private WebBackForwardList a;

    public AndroidWebBackForwardList(WebBackForwardList list) {
        this.a = list;
    }

    public int getCurrentIndex() {
        if (this.a == null) {
            return -1;
        }
        return this.a.getCurrentIndex();
    }

    public APWebHistoryItem getCurrentItem() {
        if (this.a == null || this.a.getCurrentItem() == null) {
            return null;
        }
        return (APWebHistoryItem) DynamicProxy.dynamicProxy(APWebHistoryItem.class, this.a.getCurrentItem());
    }

    public APWebHistoryItem getItemAtIndex(int i) {
        if (this.a == null || this.a.getItemAtIndex(i) == null) {
            return null;
        }
        return (APWebHistoryItem) DynamicProxy.dynamicProxy(APWebHistoryItem.class, this.a.getItemAtIndex(i));
    }

    public int getSize() {
        if (this.a == null) {
            return 0;
        }
        return this.a.getSize();
    }

    public String toString() {
        return super.toString();
    }
}
