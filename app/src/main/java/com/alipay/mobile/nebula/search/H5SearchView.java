package com.alipay.mobile.nebula.search;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

public interface H5SearchView {
    public static final float alpha = 0.15f;

    void setSearchBarColor(int i);

    void showSearch(Context context, LinearLayout linearLayout, Bundle bundle);

    void switchToOriginal();

    void switchToWhiteTheme();
}
