package com.alipay.mobile.framework.service;

import com.alipay.mobile.map.model.geocode.PoiItem;
import java.util.List;

public interface OnSearchListener {
    void onPoiSearched(List<PoiItem> list, int i);
}
