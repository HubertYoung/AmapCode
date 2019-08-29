package com.autonavi.minimap.route.bus.navidetail.model.pojo;

import java.io.Serializable;
import java.util.List;

public class BusAlternativeItem implements Serializable {
    private List<String> alter_list;
    private String plan_number;
    private String segment_number;

    public String getPlan_number() {
        return this.plan_number;
    }

    public void setPlan_number(String str) {
        this.plan_number = str;
    }

    public String getSegment_number() {
        return this.segment_number;
    }

    public void setSegment_number(String str) {
        this.segment_number = str;
    }

    public List<String> getAlter_list() {
        return this.alter_list;
    }

    public void setAlter_list(List<String> list) {
        this.alter_list = list;
    }
}
