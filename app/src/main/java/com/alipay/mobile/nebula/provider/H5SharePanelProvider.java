package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.view.H5NavMenuItem;
import java.util.List;

public interface H5SharePanelProvider {
    void addMenuList(int i, List<H5NavMenuItem> list);

    void removeMenuList(int i);

    void showSharePanel(H5Page h5Page, boolean z);
}
