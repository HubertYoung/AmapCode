package com.alibaba.baichuan.android.trade.adapter.ut.performance;

import com.alibaba.baichuan.android.trade.adapter.ut.performance.dimension.Dimension;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.standardar.common.IMUReader;
import java.io.Serializable;

public abstract class PerformancePoint implements Serializable {
    protected long a = IMUReader.NS_PER_SECOND;
    protected Dimension b;

    PerformancePoint() {
    }

    public static DimensionSet getDimensionSet() {
        return Dimension.getDimensionSet();
    }

    public boolean checkData() {
        return true;
    }

    public boolean checkTime(long j) {
        return j >= 0 && j <= this.a;
    }

    public DimensionValueSet getDimensionValues() {
        initDimension();
        return this.b.getDimensionValues();
    }

    public abstract MeasureValueSet getMeasureValues();

    public abstract String getMonitorPoint();

    public void initDimension() {
        if (this.b == null) {
            this.b = new Dimension();
        }
    }

    public abstract void timeBegin(String str);

    public abstract void timeEnd(String str);

    public String toString() {
        StringBuilder sb = new StringBuilder("PerformancePoint{dimension=");
        sb.append(this.b);
        sb.append('}');
        return sb.toString();
    }
}
