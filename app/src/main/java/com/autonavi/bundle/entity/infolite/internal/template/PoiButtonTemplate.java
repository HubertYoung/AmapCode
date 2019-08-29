package com.autonavi.bundle.entity.infolite.internal.template;

import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class PoiButtonTemplate extends PoiLayoutTemplate {
    private static final long serialVersionUID = 216038044127935183L;
    private String action;
    private String value;

    public int isShown() {
        return (this.action == null || "".equals(this.action)) ? 8 : 0;
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
