package com.autonavi.bundle.entity.infolite.internal.template;

import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class PoiTextTemplate extends PoiLayoutTemplate {
    private static final long serialVersionUID = 4550569605404265120L;
    private String color;
    private String value;

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String str) {
        this.color = str;
    }

    public int isShown() {
        return (this.value == null || "".equals(this.value)) ? 8 : 0;
    }
}
