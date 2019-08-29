package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5Page;

public interface H5WarningTipProvider {
    boolean getWarningTipVisible();

    void hideWarningTip(H5Page h5Page);

    void showWarningTip(H5Page h5Page, int i, String str);
}
