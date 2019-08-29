package com.alipay.mobile.antui.model;

import java.io.Serializable;
import java.util.ArrayList;

public class FilterItemData implements Serializable {
    public boolean allowMultipleSelect;
    public String code;
    public int count;
    public String id;
    public boolean isSelect;
    public String key;
    public String name;
    public ArrayList<FilterItemData> subItemData;
}
