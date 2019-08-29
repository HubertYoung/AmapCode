package com.amap.bundle.drivecommon.model;

import android.graphics.Rect;
import java.io.Serializable;

public class NavigationResult implements Serializable {
    private static final long serialVersionUID = -1623416768941425003L;
    public int erroCode;
    public int mDataLength;
    public int mPathNum;
    public NavigationPath[] mPaths;
    public transient Rect maxBound;
    public int mendX;
    public int mendY;
    public int mstartX;
    public int mstartY;
}
