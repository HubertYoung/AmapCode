package com.alipay.mobile.beehive.cityselect.model;

import java.util.ArrayList;
import java.util.List;

public class CityPageModel {
    public List<CityTabModel> cityTabModels = new ArrayList();
    public int fillMainLand = 0;
    public boolean hasNavigation = true;
    public String name;
    public boolean needDefaultList = true;
    public int type;
}
