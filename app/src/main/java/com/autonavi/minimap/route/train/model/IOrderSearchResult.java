package com.autonavi.minimap.route.train.model;

import com.amap.bundle.datamodel.IResultData;
import java.util.ArrayList;
import org.json.JSONObject;

public interface IOrderSearchResult extends IResultData {
    int getPage();

    int getTotalOrderSize();

    ArrayList<eit> getTotalOrdersList();

    boolean parse(JSONObject jSONObject);

    void resetAll();

    void setPage(int i);
}
