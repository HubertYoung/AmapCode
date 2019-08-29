package com.alipay.mobile.nebula.search;

public interface H5InputCallback {
    void disable();

    void enable();

    void focusInNavSearchBar();

    void focusOutNavSearchBar();

    String getNavSearchBarValue();

    void setNavSearchBarValue(String str);
}
