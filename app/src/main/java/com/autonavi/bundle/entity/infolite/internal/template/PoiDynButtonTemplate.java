package com.autonavi.bundle.entity.infolite.internal.template;

import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class PoiDynButtonTemplate extends PoiLayoutTemplate {
    private static final long serialVersionUID = 8256574104552371052L;
    private String action;
    private String value;

    public int isShown() {
        return (this.action == null || "".equals(this.action)) ? 8 : 0;
    }

    public boolean isEnable() {
        return isShown() == 0 && this.value != null && !this.value.equals("");
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
