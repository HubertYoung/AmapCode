package com.autonavi.bundle.entity.infolite.internal.template;

import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class PoiImageTemplate extends PoiLayoutTemplate {
    private static final long serialVersionUID = -5163421485364126017L;
    private String src;
    private String value;

    public int isShown() {
        return (this.src == null || "".equals(this.src)) ? 8 : 0;
    }

    public String[] getSrc() {
        if (this.src.indexOf(PoiLayoutTemplate.SPLITER) < 0) {
            String[] strArr = new String[1];
            int indexOf = this.src.indexOf(58);
            if (indexOf < 0) {
                return null;
            }
            strArr[0] = this.src.substring(indexOf + 1);
            return strArr;
        }
        String[] split = this.src.split(PoiLayoutTemplate.SPLITER_REG);
        for (int i = 0; i < split.length; i++) {
            int indexOf2 = split[i].indexOf(58);
            if (indexOf2 < 0) {
                return null;
            }
            split[i] = split[i].substring(indexOf2 + 1);
        }
        return split;
    }

    public String getSrcValue() {
        return this.src;
    }

    public void setSrc(String str) {
        this.src = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
