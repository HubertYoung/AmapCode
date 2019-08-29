package com.alibaba.appmonitor.offline;

import android.text.TextUtils;
import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.TableName;
import com.alibaba.fastjson.JSON;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;

@TableName("stat_temp")
public class TempStat extends TempEvent {
    @Column("dimension_values")
    private String dimension_values;
    @Column("measure_values")
    private String measure_values;

    public TempStat() {
    }

    public TempStat(String str, String str2, DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet, String str3, String str4) {
        super(str, str2, str3, str4);
        this.dimension_values = JSON.toJSONString(dimensionValueSet);
        this.measure_values = JSON.toJSONString(measureValueSet);
    }

    public MeasureValueSet getMeasureVauleSet() {
        if (!TextUtils.isEmpty(this.measure_values)) {
            return (MeasureValueSet) JSON.parseObject(this.measure_values, MeasureValueSet.class);
        }
        return null;
    }

    public DimensionValueSet getDimensionValue() {
        if (!TextUtils.isEmpty(this.dimension_values)) {
            return (DimensionValueSet) JSON.parseObject(this.dimension_values, DimensionValueSet.class);
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TempStat{");
        sb.append("module='");
        sb.append(this.module);
        sb.append('\'');
        sb.append("monitorPoint='");
        sb.append(this.monitorPoint);
        sb.append('\'');
        sb.append("dimension_values='");
        sb.append(this.dimension_values);
        sb.append('\'');
        sb.append(", measure_values='");
        sb.append(this.measure_values);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
