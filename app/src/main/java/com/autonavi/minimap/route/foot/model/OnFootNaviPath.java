package com.autonavi.minimap.route.foot.model;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.wtbt.IWMilestone;
import java.io.Serializable;
import java.util.ArrayList;

public class OnFootNaviPath implements Serializable {
    private static final long serialVersionUID = -4796517638111297656L;
    public int crossingCount;
    public ArrayList<GeoPoint> mAllPoints;
    public int mDataLength;
    public POI mEndPOI;
    public ArrayList<IWMilestone> mMileStones;
    public int mPathTime;
    public int mPathlength;
    public ArrayList<GeoPoint> mRarefyPoints;
    public int mStartDirection;
    public POI mStartPOI;
    public int mTaxiFee = -1;
    public int mendX;
    public int mendY;
    public int mstartX;
    public int mstartY;
    public String tabName;
}
