package com.autonavi.minimap.route.sharebike.model;

import java.io.Serializable;
import java.util.List;

public class UserTagDataTips implements Serializable {
    private List<a> tips;

    public static class a {
        public String a = null;
        public String b = null;
        public String c = null;
    }

    public List<a> getTips() {
        return this.tips;
    }

    public void setTips(List<a> list) {
        this.tips = list;
    }
}
