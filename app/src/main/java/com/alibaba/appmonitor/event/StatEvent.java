package com.alibaba.appmonitor.event;

import com.alibaba.appmonitor.model.Metric;
import com.alibaba.appmonitor.model.MetricRepo;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.pool.ReuseJSONArray;
import com.alibaba.appmonitor.pool.ReuseJSONObject;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.Measure;
import com.alibaba.mtl.appmonitor.model.MeasureValue;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StatEvent extends Event {
    /* access modifiers changed from: private */
    public Metric metric;
    private Map<DimensionValueSet, Entity> values;

    public class Entity {
        /* access modifiers changed from: private */
        public int count = 0;
        private List<MeasureValueSet> measureValueList = new ArrayList();
        /* access modifiers changed from: private */
        public int noise = 0;

        public Entity() {
        }

        public void commit(MeasureValueSet measureValueSet) {
            if (measureValueSet != null) {
                if (StatEvent.this.metric != null && StatEvent.this.metric.isCommitDetail()) {
                    this.measureValueList.add(formatMeasureValueSet(measureValueSet));
                } else if (this.measureValueList.isEmpty()) {
                    MeasureValueSet formatMeasureValueSet = formatMeasureValueSet(measureValueSet);
                    if (!(StatEvent.this.metric == null || StatEvent.this.metric.getMeasureSet() == null)) {
                        formatMeasureValueSet.setBuckets(StatEvent.this.metric.getMeasureSet().getMeasures());
                    }
                    this.measureValueList.add(formatMeasureValueSet);
                } else {
                    this.measureValueList.get(0).merge(measureValueSet);
                }
            }
        }

        private MeasureValueSet formatMeasureValueSet(MeasureValueSet measureValueSet) {
            MeasureValueSet measureValueSet2 = (MeasureValueSet) BalancedPool.getInstance().poll(MeasureValueSet.class, new Object[0]);
            if (!(StatEvent.this.metric == null || StatEvent.this.metric.getMeasureSet() == null)) {
                List<Measure> measures = StatEvent.this.metric.getMeasureSet().getMeasures();
                if (measures != null) {
                    int size = measures.size();
                    for (int i = 0; i < size; i++) {
                        Measure measure = measures.get(i);
                        if (measure != null) {
                            MeasureValue measureValue = (MeasureValue) BalancedPool.getInstance().poll(MeasureValue.class, new Object[0]);
                            MeasureValue value = measureValueSet.getValue(measure.getName());
                            if (value.getOffset() != null) {
                                measureValue.setOffset(value.getOffset().doubleValue());
                            }
                            measureValue.setValue(value.getValue());
                            measureValueSet2.setValue(measure.getName(), measureValue);
                        }
                    }
                }
            }
            return measureValueSet2;
        }

        public List<Map<String, Map<String, Object>>> getValues() {
            if (this.measureValueList == null || this.measureValueList.isEmpty()) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            int size = this.measureValueList.size();
            for (int i = 0; i < size; i++) {
                MeasureValueSet measureValueSet = this.measureValueList.get(i);
                if (measureValueSet != null) {
                    Map<String, MeasureValue> map = measureValueSet.getMap();
                    if (map != null && !map.isEmpty()) {
                        HashMap hashMap = new HashMap();
                        for (Entry next : map.entrySet()) {
                            HashMap hashMap2 = new HashMap();
                            String str = (String) next.getKey();
                            MeasureValue measureValue = (MeasureValue) next.getValue();
                            hashMap2.put("value", Double.valueOf(measureValue.getValue()));
                            if (measureValue.getOffset() != null) {
                                hashMap2.put("offset", measureValue.getOffset());
                            }
                            Map<String, Double> buckets = measureValue.getBuckets();
                            if (buckets != null) {
                                hashMap2.put("buckets", buckets);
                            }
                            hashMap.put(str, hashMap2);
                        }
                        arrayList.add(hashMap);
                    }
                }
            }
            return arrayList;
        }

        public void incrCount() {
            this.count++;
        }

        public void incrNoise() {
            this.noise++;
        }
    }

    public synchronized void commit(DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet) {
        Entity entity;
        boolean z = false;
        if (dimensionValueSet == null) {
            try {
                dimensionValueSet = (DimensionValueSet) BalancedPool.getInstance().poll(DimensionValueSet.class, new Object[0]);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.values.containsKey(dimensionValueSet)) {
            entity = this.values.get(dimensionValueSet);
        } else {
            DimensionValueSet dimensionValueSet2 = (DimensionValueSet) BalancedPool.getInstance().poll(DimensionValueSet.class, new Object[0]);
            dimensionValueSet2.addValues(dimensionValueSet);
            Entity entity2 = new Entity();
            this.values.put(dimensionValueSet2, entity2);
            entity = entity2;
        }
        if (this.metric != null) {
            z = this.metric.valid(dimensionValueSet, measureValueSet);
        }
        if (z) {
            entity.incrCount();
            entity.commit(measureValueSet);
        } else {
            entity.incrNoise();
            if (this.metric != null && this.metric.isCommitDetail()) {
                entity.commit(measureValueSet);
            }
        }
        super.commit(null);
    }

    public synchronized JSONObject dumpToJSONObject() {
        JSONObject dumpToJSONObject;
        dumpToJSONObject = super.dumpToJSONObject();
        if (this.metric != null) {
            dumpToJSONObject.put((String) "isCommitDetail", (Object) String.valueOf(this.metric.isCommitDetail()));
        }
        JSONArray jSONArray = (JSONArray) BalancedPool.getInstance().poll(ReuseJSONArray.class, new Object[0]);
        if (this.values != null) {
            for (Entry next : this.values.entrySet()) {
                JSONObject jSONObject = (JSONObject) BalancedPool.getInstance().poll(ReuseJSONObject.class, new Object[0]);
                DimensionValueSet dimensionValueSet = (DimensionValueSet) next.getKey();
                Entity entity = (Entity) next.getValue();
                Integer valueOf = Integer.valueOf(entity.count);
                Integer valueOf2 = Integer.valueOf(entity.noise);
                jSONObject.put((String) NewHtcHomeBadger.COUNT, (Object) valueOf);
                jSONObject.put((String) "noise", (Object) valueOf2);
                jSONObject.put((String) "dimensions", (Object) dimensionValueSet != null ? new HashMap(dimensionValueSet.getMap()) : null);
                jSONObject.put((String) "measures", (Object) entity.getValues());
                jSONArray.add(jSONObject);
            }
        }
        dumpToJSONObject.put((String) "values", (Object) jSONArray);
        return dumpToJSONObject;
    }

    public Metric getMetric() {
        return this.metric;
    }

    public void setMetric(Metric metric2) {
        this.metric = metric2;
    }

    public synchronized void clean() {
        super.clean();
        this.metric = null;
        for (DimensionValueSet offer : this.values.keySet()) {
            BalancedPool.getInstance().offer(offer);
        }
        this.values.clear();
    }

    public void fill(Object... objArr) {
        super.fill(objArr);
        if (this.values == null) {
            this.values = new HashMap();
        }
        this.metric = MetricRepo.getRepo().getMetric(this.module, this.monitorPoint);
    }
}
