package com.alipay.mobile.nebula.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public interface H5ToolMenuView {
    View getItemView(int i, ViewGroup viewGroup);

    void setList(List<H5NavMenuItem> list);
}
