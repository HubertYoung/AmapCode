package com.alibaba.baichuan.android.trade.adapter.ut.performance.dimension;

import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.utils.a;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;

public class PageDimension extends Dimension {
    private static final String e = "PageDimension";
    public String pageType;
    public String taokeType = "0";

    public static DimensionSet getDimensionSet() {
        DimensionSet dimensionSet = Dimension.getDimensionSet();
        if (dimensionSet != null) {
            dimensionSet.addDimension((String) "pageType");
            dimensionSet.addDimension((String) AlibcConstants.TK_TYPE);
        }
        return dimensionSet;
    }

    public DimensionValueSet getDimensionValues() {
        if (this.pageType == null || this.taokeType == null) {
            a.a(e, "getDimensionValues", "pageType/taokeType is null");
            AlibcLogger.e(e, "getDimensionValues:pageType/taokeType is null");
            return null;
        }
        DimensionValueSet dimensionValues = super.getDimensionValues();
        if (dimensionValues != null) {
            dimensionValues.setValue("pageType", this.pageType);
            dimensionValues.setValue(AlibcConstants.TK_TYPE, this.taokeType);
        }
        return dimensionValues;
    }
}
