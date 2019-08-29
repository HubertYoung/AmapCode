package com.alipay.mobile.nebulauc.impl;

import com.alipay.mobile.nebula.webview.APWebBackForwardList;
import com.alipay.mobile.nebula.webview.APWebHistoryItem;
import com.uc.webview.export.WebBackForwardList;

public class UCWebBackForwardList implements APWebBackForwardList {
    private WebBackForwardList mList;

    public UCWebBackForwardList(WebBackForwardList list) {
        this.mList = list;
    }

    public int getCurrentIndex() {
        if (this.mList == null) {
            return -1;
        }
        return this.mList.getCurrentIndex();
    }

    public APWebHistoryItem getCurrentItem() {
        if (this.mList == null || this.mList.getCurrentItem() == null) {
            return null;
        }
        return (APWebHistoryItem) DynamicProxy.dynamicProxy(APWebHistoryItem.class, this.mList.getCurrentItem());
    }

    public APWebHistoryItem getItemAtIndex(int i) {
        if (this.mList == null || this.mList.getItemAtIndex(i) == null) {
            return null;
        }
        return (APWebHistoryItem) DynamicProxy.dynamicProxy(APWebHistoryItem.class, this.mList.getItemAtIndex(i));
    }

    public int getSize() {
        if (this.mList == null) {
            return 0;
        }
        return this.mList.getSize();
    }
}
