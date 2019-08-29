package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MeasureSet implements Parcelable {
    public static final Creator<MeasureSet> CREATOR = new Creator<MeasureSet>() {
        public final MeasureSet createFromParcel(Parcel parcel) {
            return MeasureSet.readFromParcel(parcel);
        }

        public final MeasureSet[] newArray(int i) {
            return new MeasureSet[i];
        }
    };
    private static final int INIT_SIZE = 3;
    private List<Measure> measures = new ArrayList(3);

    public int describeContents() {
        return 0;
    }

    public static MeasureSet create() {
        return new MeasureSet();
    }

    public static MeasureSet create(Collection<String> collection) {
        MeasureSet measureSet = new MeasureSet();
        if (collection != null) {
            for (String addMeasure : collection) {
                measureSet.addMeasure(addMeasure);
            }
        }
        return measureSet;
    }

    public static MeasureSet create(String[] strArr) {
        MeasureSet measureSet = new MeasureSet();
        if (strArr != null) {
            for (String addMeasure : strArr) {
                measureSet.addMeasure(addMeasure);
            }
        }
        return measureSet;
    }

    private MeasureSet() {
    }

    public boolean valid(MeasureValueSet measureValueSet) {
        if (this.measures != null) {
            if (measureValueSet == null) {
                return false;
            }
            for (int i = 0; i < this.measures.size(); i++) {
                Measure measure = this.measures.get(i);
                if (measure != null) {
                    String name = measure.getName();
                    if (!measureValueSet.containValue(name) || !measure.valid(measureValueSet.getValue(name))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public MeasureSet addMeasure(Measure measure) {
        if (!this.measures.contains(measure)) {
            this.measures.add(measure);
        }
        return this;
    }

    public MeasureSet addMeasure(String str) {
        return addMeasure(new Measure(str));
    }

    public Measure getMeasure(String str) {
        for (Measure next : this.measures) {
            if (next.getName().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public List<Measure> getMeasures() {
        return this.measures;
    }

    public void setConstantValue(MeasureValueSet measureValueSet) {
        if (this.measures != null && measureValueSet != null) {
            for (Measure next : this.measures) {
                if (next.getConstantValue() != null && measureValueSet.getValue(next.getName()) == null) {
                    measureValueSet.setValue(next.getName(), next.getConstantValue().doubleValue());
                }
            }
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.measures != null) {
            try {
                Object[] array = this.measures.toArray();
                Measure[] measureArr = null;
                if (array != null) {
                    measureArr = new Measure[array.length];
                    for (int i2 = 0; i2 < array.length; i2++) {
                        measureArr[i2] = (Measure) array[i2];
                    }
                }
                parcel.writeParcelableArray(measureArr, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static MeasureSet readFromParcel(Parcel parcel) {
        MeasureSet create = create();
        try {
            Parcelable[] readParcelableArray = parcel.readParcelableArray(MeasureSet.class.getClassLoader());
            if (readParcelableArray != null) {
                ArrayList arrayList = new ArrayList(readParcelableArray.length);
                for (Parcelable parcelable : readParcelableArray) {
                    arrayList.add((Measure) parcelable);
                }
                create.measures = arrayList;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return create;
    }

    public void upateMeasures(List<Measure> list) {
        int size = this.measures.size();
        int size2 = list.size();
        for (int i = 0; i < size; i++) {
            for (int i2 = 0; i2 < size2; i2++) {
                if (TextUtils.equals(this.measures.get(i).name, list.get(i2).name)) {
                    this.measures.get(i).setRange(list.get(i2).getMin(), list.get(i2).getMax());
                }
            }
        }
    }

    public void upateMeasure(Measure measure) {
        int size = this.measures.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(this.measures.get(i).name, measure.name)) {
                this.measures.get(i).setRange(measure.getMin(), measure.getMax());
                this.measures.get(i).setConstantValue(measure.getConstantValue());
            }
        }
    }
}
