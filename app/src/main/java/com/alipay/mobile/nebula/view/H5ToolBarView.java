package com.alipay.mobile.nebula.view;

import android.view.View;
import android.widget.ImageView;
import com.alipay.mobile.h5container.api.H5Page;

public interface H5ToolBarView {
    View getContentView();

    ImageView getMenuView();

    void setH5Page(H5Page h5Page);

    void setMenuImageResource(int i);

    void showClose(boolean z);

    void showImageViewReload(boolean z);

    void showProgressBarReload(boolean z);

    void showToolBar(boolean z);
}
