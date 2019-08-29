package com.autonavi.bundle.routecommon.entity;

import java.io.Serializable;

public class BusEta implements Serializable {
    private static final long serialVersionUID = -6273944259327672283L;
    public BusEtaLink[] etalinks = null;
    public int linestatus;
    public int[] mXs;
    public int[] mYs;
}
