package com.alibaba.appmonitor.model;

import android.text.TextUtils;
import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.Ingore;
import com.alibaba.analytics.core.db.annotation.TableName;
import com.alibaba.appmonitor.pool.Reusable;
import com.alibaba.appmonitor.sample.AMSamplingMgr;
import com.alibaba.fastjson.JSON;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.Measure;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import java.util.List;
import java.util.UUID;

@TableName("stat_register_temp")
public class Metric extends Entity implements Reusable {
    @Ingore
    private static final String SEPERATOR = "$";
    @Ingore
    private DimensionSet dimensionSet;
    @Column("dimensions")
    private String dimensions;
    @Ingore
    private String extraArg;
    @Column("is_commit_detail")
    private boolean isCommitDetail;
    @Ingore
    private MeasureSet measureSet;
    @Column("measures")
    private String measures;
    @Column("module")
    private String module;
    @Column("monitor_point")
    private String monitorPoint;
    @Ingore
    private String transactionId;

    @Deprecated
    public Metric() {
    }

    public Metric(String str, String str2, MeasureSet measureSet2, DimensionSet dimensionSet2, boolean z) {
        this.module = str;
        this.monitorPoint = str2;
        this.dimensionSet = dimensionSet2;
        this.measureSet = measureSet2;
        this.extraArg = null;
        this.isCommitDetail = z;
        if (dimensionSet2 != null) {
            this.dimensions = JSON.toJSONString(dimensionSet2);
        }
        this.measures = JSON.toJSONString(measureSet2);
    }

    @Deprecated
    protected Metric(String str, String str2, String str3, String str4, boolean z) {
        this.module = str;
        this.monitorPoint = str2;
        this.dimensionSet = (DimensionSet) JSON.parseObject(str4, DimensionSet.class);
        this.measureSet = (MeasureSet) JSON.parseObject(str3, MeasureSet.class);
        this.extraArg = null;
        this.isCommitDetail = z;
        this.dimensions = str4;
        this.measures = str3;
    }

    public synchronized String getTransactionId() {
        if (this.transactionId == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(UUID.randomUUID().toString());
            sb.append("$");
            sb.append(this.module);
            sb.append("$");
            sb.append(this.monitorPoint);
            this.transactionId = sb.toString();
        }
        return this.transactionId;
    }

    public void resetTransactionId() {
        this.transactionId = null;
    }

    public boolean valid(DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet) {
        boolean valid = this.dimensionSet != null ? this.dimensionSet.valid(dimensionValueSet) : true;
        if (this.measureSet != null) {
            return valid && this.measureSet.valid(measureValueSet);
        }
        return valid;
    }

    @Deprecated
    private Measure getMeasureByName(String str, List<Measure> list) {
        if (list != null) {
            for (Measure next : list) {
                if (TextUtils.equals(str, next.name)) {
                    return next;
                }
            }
        }
        return null;
    }

    public String getModule() {
        return this.module;
    }

    public String getMonitorPoint() {
        return this.monitorPoint;
    }

    public DimensionSet getDimensionSet() {
        if (this.dimensionSet == null && !TextUtils.isEmpty(this.dimensions)) {
            this.dimensionSet = (DimensionSet) JSON.parseObject(this.dimensions, DimensionSet.class);
        }
        return this.dimensionSet;
    }

    public MeasureSet getMeasureSet() {
        if (this.measureSet == null && !TextUtils.isEmpty(this.measures)) {
            this.measureSet = (MeasureSet) JSON.parseObject(this.measures, MeasureSet.class);
        }
        return this.measureSet;
    }

    public synchronized boolean isCommitDetail() {
        return this.isCommitDetail || AMSamplingMgr.getInstance().isDetail(this.module, this.monitorPoint);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((this.extraArg == null ? 0 : this.extraArg.hashCode()) + 31) * 31) + (this.module == null ? 0 : this.module.hashCode())) * 31;
        if (this.monitorPoint != null) {
            i = this.monitorPoint.hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Metric metric = (Metric) obj;
        if (this.extraArg == null) {
            if (metric.extraArg != null) {
                return false;
            }
        } else if (!this.extraArg.equals(metric.extraArg)) {
            return false;
        }
        if (this.module == null) {
            if (metric.module != null) {
                return false;
            }
        } else if (!this.module.equals(metric.module)) {
            return false;
        }
        if (this.monitorPoint == null) {
            if (metric.monitorPoint != null) {
                return false;
            }
        } else if (!this.monitorPoint.equals(metric.monitorPoint)) {
            return false;
        }
        return true;
    }

    public void clean() {
        this.module = null;
        this.monitorPoint = null;
        this.extraArg = null;
        this.isCommitDetail = false;
        this.dimensionSet = null;
        this.measureSet = null;
        this.transactionId = null;
    }

    public void fill(Object... objArr) {
        this.module = objArr[0];
        this.monitorPoint = objArr[1];
        if (objArr.length > 2) {
            this.extraArg = objArr[2];
        }
    }
}
