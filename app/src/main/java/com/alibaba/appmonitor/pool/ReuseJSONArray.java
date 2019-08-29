package com.alibaba.appmonitor.pool;

import com.alibaba.fastjson.JSONArray;
import java.util.Iterator;

public class ReuseJSONArray extends JSONArray implements Reusable {
    private static final long serialVersionUID = -4243576223670082606L;

    public void fill(Object... objArr) {
    }

    public void clean() {
        Iterator<Object> it = iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof Reusable) {
                BalancedPool.getInstance().offer((Reusable) next);
            }
        }
        super.clear();
    }
}
