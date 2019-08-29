package com.alipay.mobile.beehive.compositeui.popup;

import com.alipay.mobile.beehive.compositeui.popup.model.FilterItem;

public interface OnFilterChangedListener {
    void onBack();

    void onFilterChanged(FilterItem filterItem);

    void onOtherClick();
}
