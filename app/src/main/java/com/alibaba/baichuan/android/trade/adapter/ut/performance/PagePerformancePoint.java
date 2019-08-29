package com.alibaba.baichuan.android.trade.adapter.ut.performance;

import com.alibaba.baichuan.android.trade.adapter.ut.performance.dimension.PageDimension;
import com.alibaba.mtl.appmonitor.model.DimensionSet;

public abstract class PagePerformancePoint extends PerformancePoint {
    public static DimensionSet getDimensionSet() {
        return PageDimension.getDimensionSet();
    }

    public void initDimension() {
        if (this.b == null) {
            this.b = new PageDimension();
        }
    }

    public void setPageType(String str) {
        initDimension();
        ((PageDimension) this.b).pageType = str;
    }

    public void setTaokeType(String str) {
        initDimension();
        ((PageDimension) this.b).taokeType = str;
    }
}
