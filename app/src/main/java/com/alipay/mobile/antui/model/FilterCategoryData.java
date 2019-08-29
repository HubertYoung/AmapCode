package com.alipay.mobile.antui.model;

import java.io.Serializable;
import java.util.ArrayList;

public class FilterCategoryData implements Serializable {
    public String code;
    public String groupId;
    public ArrayList<FilterItemData> itemDatas;
    public String name;
    public ArrayList<FilterItemData> selectItems;
}
