package com.alipay.zoloz.toyger.blob;

import java.util.Map;

public class Meta {
    public Map<String, Object> collectInfo;
    public Map<String, Object> score;
    public int serialize;
    public String type;

    public Meta() {
    }

    public Meta(String str, Map<String, Object> map, Map<String, Object> map2, int i) {
        this.type = str;
        this.score = map;
        this.collectInfo = map2;
        this.serialize = i;
    }
}
