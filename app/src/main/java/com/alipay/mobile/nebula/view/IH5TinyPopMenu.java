package com.alipay.mobile.nebula.view;

import android.content.Context;
import android.view.ViewGroup;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.callback.H5SimpleRpcListener;
import java.util.List;

public interface IH5TinyPopMenu {
    void init(H5Page h5Page, String str, Context context, ViewGroup viewGroup);

    void onRelease();

    void onSwitchToBlueTheme();

    void onSwitchToWhiteTheme();

    void requestRpc(H5SimpleRpcListener h5SimpleRpcListener, H5Page h5Page, Context context);

    void setH5MenuList(List<H5NavMenuItem> list, boolean z);

    void setH5OptionMenuTextFlag();

    void setH5ShowOptionMenuFlag();

    void setTitleBarRightButtonViewShow(boolean z);

    void showMenu();
}
