package com.autonavi.bundle.entity.infolite.internal.template;

import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.ArrayList;

public class PoiSupperAddressTemplate extends PoiLayoutTemplate {
    private ArrayList<String> addrs;
    private String value;

    public int isShown() {
        return 0;
    }

    public ArrayList<String> getAddrs() {
        return this.addrs;
    }

    public void setAddrs(ArrayList<String> arrayList) {
        this.addrs = arrayList;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}
