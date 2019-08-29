package com.alibaba.sdk.want.ui;

import com.alibaba.sdk.trade.component.cart.AlibcCartParams;
import com.alibaba.sdk.want.widget.IWantWidget;

public interface AlibcIWidgetUI {
    void destroy();

    int getWidgetUIVisible();

    void removeData();

    void setWidget(IWantWidget iWantWidget);

    void updateResult(String str, String str2);

    void updateUI(AlibcCartParams alibcCartParams);
}
