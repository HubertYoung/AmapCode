package com.amap.bundle.network.request.param.builder;

import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AosRequestBuilder extends URLBuilder {
    private String a;
    private List<String> b;
    private Map<String, Object> c = new HashMap();

    public void parse(Path path, Map<String, Field> map, ParamEntity paramEntity, boolean z) throws IllegalAccessException {
        this.a = aai.a(path.host(), path.url());
        if (map != null) {
            for (Entry next : map.entrySet()) {
                Object obj = ((Field) next.getValue()).get(paramEntity);
                if (obj != null) {
                    this.c.put(next.getKey(), obj);
                }
            }
        }
        String[] sign = path.sign();
        if (sign != null) {
            this.b = Arrays.asList(sign);
        }
        addCombinParam(path, this.c);
        this.a = aai.a(this.a, this.c);
    }

    public String getUrl() {
        return this.a;
    }

    public Map<String, Object> getParams() {
        return this.c;
    }

    public List<String> getSignParams() {
        return this.b;
    }
}
