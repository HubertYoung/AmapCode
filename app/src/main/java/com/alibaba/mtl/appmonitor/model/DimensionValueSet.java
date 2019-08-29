package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.pool.Reusable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DimensionValueSet implements Parcelable, Reusable {
    public static final Creator<DimensionValueSet> CREATOR = new Creator<DimensionValueSet>() {
        public final DimensionValueSet createFromParcel(Parcel parcel) {
            return DimensionValueSet.readFromParcel(parcel);
        }

        public final DimensionValueSet[] newArray(int i) {
            return new DimensionValueSet[i];
        }
    };
    protected Map<String, String> map;

    public int describeContents() {
        return 0;
    }

    public static DimensionValueSet create() {
        return (DimensionValueSet) BalancedPool.getInstance().poll(DimensionValueSet.class, new Object[0]);
    }

    @Deprecated
    public static DimensionValueSet create(int i) {
        return (DimensionValueSet) BalancedPool.getInstance().poll(DimensionValueSet.class, new Object[0]);
    }

    @Deprecated
    public DimensionValueSet() {
        if (this.map == null) {
            this.map = new LinkedHashMap();
        }
    }

    public static DimensionValueSet fromStringMap(Map<String, String> map2) {
        DimensionValueSet dimensionValueSet = (DimensionValueSet) BalancedPool.getInstance().poll(DimensionValueSet.class, new Object[0]);
        for (Entry next : map2.entrySet()) {
            dimensionValueSet.map.put(next.getKey(), next.getValue() != null ? (String) next.getValue() : "null");
        }
        return dimensionValueSet;
    }

    public DimensionValueSet setValue(String str, String str2) {
        Map<String, String> map2 = this.map;
        if (str2 == null) {
            str2 = "null";
        }
        map2.put(str, str2);
        return this;
    }

    public DimensionValueSet addValues(DimensionValueSet dimensionValueSet) {
        if (dimensionValueSet != null) {
            Map<String, String> map2 = dimensionValueSet.getMap();
            if (map2 != null) {
                for (Entry next : map2.entrySet()) {
                    this.map.put(next.getKey(), next.getValue() != null ? (String) next.getValue() : "null");
                }
            }
        }
        return this;
    }

    public boolean containValue(String str) {
        return this.map.containsKey(str);
    }

    public String getValue(String str) {
        return this.map.get(str);
    }

    public Map<String, String> getMap() {
        return this.map;
    }

    public void setMap(Map<String, String> map2) {
        for (Entry next : map2.entrySet()) {
            this.map.put(next.getKey(), next.getValue() != null ? (String) next.getValue() : "null");
        }
    }

    public int hashCode() {
        return (this.map == null ? 0 : this.map.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DimensionValueSet dimensionValueSet = (DimensionValueSet) obj;
        if (this.map == null) {
            if (dimensionValueSet.map != null) {
                return false;
            }
        } else if (!this.map.equals(dimensionValueSet.map)) {
            return false;
        }
        return true;
    }

    public void clean() {
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

    static DimensionValueSet readFromParcel(Parcel parcel) {
        DimensionValueSet dimensionValueSet;
        try {
            dimensionValueSet = create();
            try {
                dimensionValueSet.map = parcel.readHashMap(DimensionValueSet.class.getClassLoader());
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            dimensionValueSet = null;
            th.printStackTrace();
            return dimensionValueSet;
        }
        return dimensionValueSet;
    }
}
