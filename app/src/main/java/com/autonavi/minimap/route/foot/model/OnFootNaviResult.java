package com.autonavi.minimap.route.foot.model;

import java.io.Serializable;

public class OnFootNaviResult implements Serializable {
    public int mDataLength;
    public String mNaviId;
    public OnFootNaviPath[] mOnFootNaviPath;
    public int mPathNum;
}
