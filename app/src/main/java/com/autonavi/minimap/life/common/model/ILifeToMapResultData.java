package com.autonavi.minimap.life.common.model;

import com.amap.bundle.datamodel.IResultData;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import org.json.JSONObject;

public interface ILifeToMapResultData extends IResultData {
    int getCurPoiPage();

    POI getFocusedPoi();

    int getFocusedPoiIndex();

    ArrayList<POI> getPoiList();

    String getSearchKeyword();

    int getSearchPage();

    boolean parse(JSONObject jSONObject);

    POI searchPoi(POI poi);

    void setCurPoiPage(int i);

    void setFocusedPoiIndex(int i);

    void setSearchKeyword(String str);

    void setSearchPage(int i);
}
