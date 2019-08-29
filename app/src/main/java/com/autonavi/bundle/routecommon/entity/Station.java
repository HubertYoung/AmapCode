package com.autonavi.bundle.routecommon.entity;

import java.io.Serializable;

public class Station implements Serializable {
    private static final long serialVersionUID = 183199811252361637L;
    public String id;
    public boolean isNearestStation;
    public String mName;
    public int mStationdirection;
    public int mX;
    public int mY;
}
