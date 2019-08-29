package com.autonavi.minimap.life.order.base.model;

import com.amap.bundle.datamodel.IResultData;
import java.util.ArrayList;
import org.json.JSONObject;

public interface IOrderSearchResult extends IResultData {
    int getPage();

    int getTotalOrderSize();

    ArrayList<dpl> getTotalOrdersList();

    boolean parse(JSONObject jSONObject);

    void resetAll();

    void setPage(int i);
}
