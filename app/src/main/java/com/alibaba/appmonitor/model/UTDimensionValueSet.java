package com.alibaba.appmonitor.model;

import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.utils.ParseUtils;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import java.util.Map;

public class UTDimensionValueSet extends DimensionValueSet {
    public Integer getEventId() {
        int i;
        if (this.map != null) {
            String str = (String) this.map.get(LogField.EVENTID.toString());
            if (str != null) {
                try {
                    i = ParseUtils.parseInteger(str);
                } catch (NumberFormatException unused) {
                }
                return Integer.valueOf(i);
            }
        }
        i = 0;
        return Integer.valueOf(i);
    }

    public static UTDimensionValueSet create(Map<String, String> map) {
        return (UTDimensionValueSet) BalancedPool.getInstance().poll(UTDimensionValueSet.class, map);
    }

    public void clean() {
        super.clean();
    }

    public void fill(Object... objArr) {
        super.fill(objArr);
    }
}
