package com.alipay.mobile.beehive.cityselect.model;

import java.util.List;

public class CityTabModel {
    public static final int AREASHOWMODE_MAINLAND = 1;
    public static final int AREASHOWMODE_NONE = 0;
    public static final int AREASHOWMODE_OVERSEA = 2;
    public static final int TYPE_COUNTRY = 2;
    public static final int TYPE_LINK = 3;
    public static final int TYPE_LOCATE = 1;
    public static final int TYPE_SINGLEITEM = 4;
    public static final int TYPE_STATIC = 0;
    public int areaShowMode;
    public List<CityVO> cityVOs;
    public String name;
    public String navName;
    public boolean needSearch = true;
    public int type = 0;
}
