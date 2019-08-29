package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.pool.Reusable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MeasureValueSet implements Parcelable, Reusable, IMerge<MeasureValueSet> {
    public static final Creator<MeasureValueSet> CREATOR = new Creator<MeasureValueSet>() {
        public final MeasureValueSet createFromParcel(Parcel parcel) {
            return MeasureValueSet.readFromParcel(parcel);
        }

        public final MeasureValueSet[] newArray(int i) {
            return new MeasureValueSet[i];
        }
    };
    private Map<String, MeasureValue> map = new LinkedHashMap();

    public int describeContents() {
        return 0;
    }

    public static MeasureValueSet create() {
        return (MeasureValueSet) BalancedPool.getInstance().poll(MeasureValueSet.class, new Object[0]);
    }

    @Deprecated
    public static MeasureValueSet create(int i) {
        return (MeasureValueSet) BalancedPool.getInstance().poll(MeasureValueSet.class, new Object[0]);
    }

    public static MeasureValueSet create(Map<String, Double> map2) {
        MeasureValueSet measureValueSet = (MeasureValueSet) BalancedPool.getInstance().poll(MeasureValueSet.class, new Object[0]);
        if (map2 != null) {
            for (String next : map2.keySet()) {
                Double d = map2.get(next);
                if (d != null) {
                    measureValueSet.map.put(next, BalancedPool.getInstance().poll(MeasureValue.class, d));
                }
            }
        }
        return measureValueSet;
    }

    public static MeasureValueSet fromStringMap(Map<String, String> map2) {
        MeasureValueSet measureValueSet = (MeasureValueSet) BalancedPool.getInstance().poll(MeasureValueSet.class, new Object[0]);
        if (map2 != null) {
            for (Entry next : map2.entrySet()) {
                Double d = toDouble((String) next.getValue());
                if (d != null) {
                    measureValueSet.map.put(next.getKey(), BalancedPool.getInstance().poll(MeasureValue.class, d));
                }
            }
        }
        return measureValueSet;
    }

    private static Double toDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MeasureValueSet setValue(String str, double d) {
        this.map.put(str, BalancedPool.getInstance().poll(MeasureValue.class, Double.valueOf(d)));
        return this;
    }

    public void setValue(String str, MeasureValue measureValue) {
        this.map.put(str, measureValue);
    }

    public MeasureValue getValue(String str) {
        return this.map.get(str);
    }

    public Map<String, MeasureValue> getMap() {
        return this.map;
    }

    public void setMap(Map<String, MeasureValue> map2) {
        this.map = map2;
    }

    public boolean containValue(String str) {
        return this.map.containsKey(str);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void merge(MeasureValueSet measureValueSet) {
        for (String next : this.map.keySet()) {
            this.map.get(next).merge(measureValueSet.getValue(next));
        }
    }

    public void clean() {
        for (MeasureValue offer : this.map.values()) {
            BalancedPool.getInstance().offer(offer);
        }
        this.map.clear();
    }

    public void fill(Object... objArr) {
        if (this.map == null) {
            this.map = new LinkedHashMap();
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeMap(this.map);
    }

    static MeasureValueSet readFromParcel(Parcel parcel) {
        try {
            MeasureValueSet create = create();
            try {
                create.map = parcel.readHashMap(DimensionValueSet.class.getClassLoader());
                return create;
            } catch (Throwable unused) {
                return create;
            }
        } catch (Throwable unused2) {
            return null;
        }
    }

    public void setBuckets(List<Measure> list) {
        if (list != null) {
            for (String next : this.map.keySet()) {
                this.map.get(next).setBuckets(getMeasure(list, next));
            }
        }
    }

    private Measure getMeasure(List<Measure> list, String str) {
        for (Measure next : list) {
            if (str.equalsIgnoreCase(next.getName())) {
                return next;
            }
        }
        return null;
    }
}
