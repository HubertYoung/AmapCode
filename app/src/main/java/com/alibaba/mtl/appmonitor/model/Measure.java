package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alibaba.analytics.utils.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Measure implements Parcelable {
    public static final Creator<Measure> CREATOR = new Creator<Measure>() {
        public final Measure createFromParcel(Parcel parcel) {
            return Measure.readFromParcel(parcel);
        }

        public final Measure[] newArray(int i) {
            return new Measure[i];
        }
    };
    private static List<Double> nullList;
    private List<Double> bounds;
    protected Double constantValue;
    public String name;

    public int describeContents() {
        return 0;
    }

    Measure() {
        this.constantValue = Double.valueOf(0.0d);
    }

    static {
        ArrayList arrayList = new ArrayList(1);
        nullList = arrayList;
        arrayList.add(null);
    }

    public Measure(String str) {
        this(str, Double.valueOf(0.0d));
    }

    public Measure(String str, Double d, List<Double> list) {
        double d2 = 0.0d;
        this.constantValue = Double.valueOf(0.0d);
        if (list != null) {
            if (list.removeAll(nullList)) {
                Logger.w((String) "bounds entity must not be null", new Object[0]);
            }
            Collections.sort(list);
            this.bounds = list;
        }
        this.name = str;
        this.constantValue = Double.valueOf(d != null ? d.doubleValue() : d2);
    }

    public Measure(String str, Double d) {
        this(str, d, null, null);
    }

    public Measure(String str, Double d, Double d2, Double d3) {
        this(str, d, null);
        if (d2 != null || d3 != null) {
            setRange(d2, d3);
        }
    }

    public void setRange(Double d, Double d2) {
        if (d == null || d2 == null) {
            Logger.w((String) "min or max must not be null", new Object[0]);
            return;
        }
        this.bounds = new ArrayList(2);
        this.bounds.add(d);
        this.bounds.add(d2);
    }

    public Double getMin() {
        if (this.bounds == null || this.bounds.size() <= 0) {
            return null;
        }
        return this.bounds.get(0);
    }

    public Double getMax() {
        if (this.bounds == null || this.bounds.size() < 2) {
            return null;
        }
        return this.bounds.get(this.bounds.size() - 1);
    }

    public List<Double> getBounds() {
        return this.bounds;
    }

    public String getName() {
        return this.name;
    }

    public Double getConstantValue() {
        return this.constantValue;
    }

    public void setConstantValue(Double d) {
        this.constantValue = d;
    }

    public boolean valid(MeasureValue measureValue) {
        Double valueOf = Double.valueOf(measureValue.getValue());
        return valueOf != null && (getMin() == null || valueOf.doubleValue() >= getMin().doubleValue()) && (getMax() == null || valueOf.doubleValue() < getMax().doubleValue());
    }

    public int hashCode() {
        return (this.name == null ? 0 : this.name.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Measure measure = (Measure) obj;
        if (this.name == null) {
            if (measure.name != null) {
                return false;
            }
        } else if (!this.name.equals(measure.name)) {
            return false;
        }
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeList(this.bounds);
            parcel.writeString(this.name);
            parcel.writeInt(this.constantValue == null ? 0 : 1);
            if (this.constantValue != null) {
                parcel.writeDouble(this.constantValue.doubleValue());
            }
        } catch (Throwable th) {
            Logger.e("writeToParcel", th, new Object[0]);
        }
    }

    static Measure readFromParcel(Parcel parcel) {
        try {
            return new Measure(parcel.readString(), !(parcel.readInt() == 0) ? Double.valueOf(parcel.readDouble()) : null, parcel.readArrayList(Measure.class.getClassLoader()));
        } catch (Throwable th) {
            th.printStackTrace();
            Logger.e("readFromParcel", th, new Object[0]);
            return null;
        }
    }
}
