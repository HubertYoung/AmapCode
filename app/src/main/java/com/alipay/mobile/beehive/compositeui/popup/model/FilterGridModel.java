package com.alipay.mobile.beehive.compositeui.popup.model;

import java.util.List;

public class FilterGridModel {
    public static final String STYLE_DIVIDER = "divider";
    public static final String STYLE_NORMAL = "normal";
    public List<FilterItem> filters;
    public int numColumns;
    public String styleType;

    public FilterGridModel(int numColumns2, String styleType2, List<FilterItem> filters2) {
        this.numColumns = numColumns2;
        this.styleType = styleType2;
        this.filters = filters2;
    }
}
