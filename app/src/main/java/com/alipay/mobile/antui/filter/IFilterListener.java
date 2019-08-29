package com.alipay.mobile.antui.filter;

import com.alipay.mobile.antui.model.FilterCategoryData;
import java.util.Map;

public interface IFilterListener {
    void onFilterSelected(FilterCategoryData filterCategoryData, Map<String, String> map, boolean z, boolean z2);
}
