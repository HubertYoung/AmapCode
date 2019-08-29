package com.alipay.mobile.nebula.permission;

import java.util.ArrayList;
import java.util.List;

public class H5PermissionInfo {
    private String level = "";
    private List<String> whiteList = new ArrayList();

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level2) {
        this.level = level2;
    }

    public List<String> getWhiteList() {
        return this.whiteList;
    }

    public void setWhiteList(List<String> whiteList2) {
        this.whiteList = whiteList2;
    }
}
