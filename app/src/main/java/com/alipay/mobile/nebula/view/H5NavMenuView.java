package com.alipay.mobile.nebula.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public interface H5NavMenuView {
    View getItemView(int i, ViewGroup viewGroup);

    int getListCount();

    void setList(List<H5NavMenuItem> list);
}
