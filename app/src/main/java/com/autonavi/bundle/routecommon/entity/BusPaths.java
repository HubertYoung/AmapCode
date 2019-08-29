package com.autonavi.bundle.routecommon.entity;

import java.io.Serializable;

public class BusPaths implements Serializable {
    private static final long serialVersionUID = 1;
    public String mBsid;
    public BusPath[] mBusPaths;
    public int mDataLength;
    public String mEndDes;
    public int mPathNum;
    public String mShowInput_Content;
    public String mShowInput_Title;
    public String mShowInput_Type;
    public String mStartDes;
    public int mTaxiTime;
    public int mTicketShow;
    public int mendX;
    public int mendY;
    public int mstartX;
    public int mstartY;
    public int mtaxiPrice;
}
