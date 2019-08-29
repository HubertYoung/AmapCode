package com.autonavi.minimap.route.sharebike.model;

import java.io.Serializable;
import java.util.List;

public class UserTagDataInfo implements Serializable {
    private a icon;
    private List<String> tags;

    public static class a {
        public String a = null;
        public String b = null;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> list) {
        this.tags = list;
    }

    public a getIcon() {
        return this.icon;
    }

    public void setIcon(a aVar) {
        this.icon = aVar;
    }
}
