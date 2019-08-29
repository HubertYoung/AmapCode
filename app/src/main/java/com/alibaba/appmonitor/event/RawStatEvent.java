package com.alibaba.appmonitor.event;

import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;

@Deprecated
public class RawStatEvent extends Event implements IRawEvent {
    public UTEvent dumpToUTEvent() {
        return null;
    }

    public DimensionValueSet getDimensionValues() {
        return null;
    }

    public MeasureValueSet getMeasureValues() {
        return null;
    }

    public void setDimensionValues(DimensionValueSet dimensionValueSet) {
    }

    public void setMeasureValues(MeasureValueSet measureValueSet) {
    }

    public void clean() {
        super.clean();
    }
}
