package com.autonavi.bundle.entity.infolite.internal.template;

import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class PoiLinkTemplate extends PoiLayoutTemplate {
    private static final long serialVersionUID = 5181375238233257700L;
    private String action;

    public String getValue() {
        return "";
    }

    public int isShown() {
        return (this.action == null || "".equals(this.action)) ? 8 : 0;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }
}
