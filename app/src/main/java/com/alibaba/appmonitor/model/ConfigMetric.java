package com.alibaba.appmonitor.model;

import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;

@Deprecated
public class ConfigMetric extends Metric {
    public ConfigMetric(String str, String str2, MeasureSet measureSet) {
        super(str, str2, measureSet, (DimensionSet) null, false);
    }
}
