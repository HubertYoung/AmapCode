package com.amap.bundle.drivecommon.model;

import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupNavigationSection implements Serializable {
    private static final long serialVersionUID = -5780174178500197553L;
    public int index;
    public transient List<NavigationSection> mSectionList = new ArrayList();
    public String m_GroupName;
    public boolean m_bArrivePass;
    public boolean m_bIsSrucial;
    public int m_nDistance;
    public int m_nSegCount;
    public int m_nSpeed;
    public int m_nStartSegId;
    public int m_nStatus;
    public int m_nToll;
    public int m_nTrafficLights;
    public GeoPoint posPoint;
}
