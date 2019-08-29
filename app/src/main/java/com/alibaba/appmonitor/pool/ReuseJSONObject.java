package com.alibaba.appmonitor.pool;

import com.alibaba.fastjson.JSONObject;

public class ReuseJSONObject extends JSONObject implements Reusable {
    private static final long serialVersionUID = 1465414806753619992L;

    public void fill(Object... objArr) {
    }

    public void clean() {
        for (Object next : values()) {
            if (next instanceof Reusable) {
                BalancedPool.getInstance().offer((Reusable) next);
            }
        }
        super.clear();
    }
}
