package com.autonavi.minimap.route.train.inter;

import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem;
import java.util.ArrayList;

public interface ITrainRouteResult extends IRouteResultData {
    int getMinPrice();

    byte[] getRouteData();

    ArrayList<TrainPlanBaseInfoItem> getTrainPlanInfoResult();

    boolean isNeedServiceSwitch();

    boolean isParseOK();

    boolean parseData(byte[] bArr);
}
