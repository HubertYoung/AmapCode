package com.autonavi.map.fragmentcontainer.page;

import android.view.View;
import com.autonavi.common.model.POI;

public interface IPoiDetailPage {
    boolean isGpsTipDisable();

    boolean isUsePoiDelegate();

    void onPageGpsBtnClicked();

    void onStartDetail(POI poi, View view);

    void onStartDetail(POI poi, ely<?> ely);
}
