package com.autonavi.bundle.entity.common.searchpoi;

import com.autonavi.common.model.POI;

public interface SearchRecommendPoi extends POI {
    String getShortName();

    int getShowType();

    String getTpIcon();

    void setShortName(String str);

    void setShowType(int i);

    void setTpIcon(String str);
}
