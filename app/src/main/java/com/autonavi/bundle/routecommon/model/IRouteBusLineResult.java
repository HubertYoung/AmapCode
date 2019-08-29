package com.autonavi.bundle.routecommon.model;

import com.amap.bundle.datamodel.IResultData;

public interface IRouteBusLineResult extends IResultData {
    int getResultType();

    int getTotalPoiPage();

    int getTotalPoiSize();

    boolean hasBuslineData();
}
