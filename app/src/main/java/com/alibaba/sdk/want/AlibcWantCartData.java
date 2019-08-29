package com.alibaba.sdk.want;

import com.alibaba.sdk.want.widget.WantBaseData;
import com.alibaba.sdk.want.widget.WantWidgetFactory;

public class AlibcWantCartData implements WantBaseData {
    public String itemID;
    public String tips;

    public String getType() {
        return WantWidgetFactory.ADDWANTWIDGET_TYPE;
    }

    private AlibcWantCartData() {
    }

    public static AlibcWantCartData getCartData(String str, String str2) {
        AlibcWantCartData alibcWantCartData = new AlibcWantCartData();
        alibcWantCartData.itemID = str;
        alibcWantCartData.tips = str2;
        return alibcWantCartData;
    }

    public String getKey() {
        return this.itemID;
    }

    public String getValue() {
        return this.tips;
    }
}
