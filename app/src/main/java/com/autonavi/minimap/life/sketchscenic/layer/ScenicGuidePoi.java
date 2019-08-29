package com.autonavi.minimap.life.sketchscenic.layer;

import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;

public interface ScenicGuidePoi extends ISearchPoiData {
    String getIdentityId();

    void setClearFocus(String str);

    void setIconType(String str);

    void setIdentityId(String str);

    void setIsScenicGuidePoi(boolean z);
}
