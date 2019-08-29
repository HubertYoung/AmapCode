package com.autonavi.bundle.entity.common;

import java.io.Serializable;

public class OfflineSearchMode implements Serializable {
    private static final long serialVersionUID = 484907822132474216L;
    public int searchType = 1;
    public String strAdCode;
    public String strDataPath;
    public String strKeyWord;
    public String strLatitude;
    public String strLongitude;
}
