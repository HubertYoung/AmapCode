package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alibaba.analytics.utils.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DimensionSet implements Parcelable {
    public static final Creator<DimensionSet> CREATOR = new Creator<DimensionSet>() {
        public final DimensionSet createFromParcel(Parcel parcel) {
            return DimensionSet.readFromParcel(parcel);
        }

        public final DimensionSet[] newArray(int i) {
            return new DimensionSet[i];
        }
    };
    private static final int INIT_SIZE = 3;
    private static final String TAG = "DimensionSet";
    private List<Dimension> dimensions = new ArrayList(3);

    public int describeContents() {
        return 0;
    }

    public static DimensionSet create() {
        return new DimensionSet();
    }

    public static DimensionSet create(Collection<String> collection) {
        DimensionSet dimensionSet = new DimensionSet();
        if (collection != null) {
            for (String dimension : collection) {
                dimensionSet.addDimension(new Dimension(dimension));
            }
        }
        return dimensionSet;
    }

    public static DimensionSet create(String[] strArr) {
        DimensionSet dimensionSet = new DimensionSet();
        if (strArr != null) {
            for (String dimension : strArr) {
                dimensionSet.addDimension(new Dimension(dimension));
            }
        }
        return dimensionSet;
    }

    private DimensionSet() {
    }

    public boolean valid(DimensionValueSet dimensionValueSet) {
        if (this.dimensions != null) {
            if (dimensionValueSet == null) {
                return false;
            }
            for (Dimension name : this.dimensions) {
                if (!dimensionValueSet.containValue(name.getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    public DimensionSet addDimension(Dimension dimension) {
        if (this.dimensions.contains(dimension)) {
            return this;
        }
        this.dimensions.add(dimension);
        return this;
    }

    public DimensionSet addDimension(String str) {
        return addDimension(new Dimension(str));
    }

    public DimensionSet addDimension(String str, String str2) {
        return addDimension(new Dimension(str, str2));
    }

    public Dimension getDimension(String str) {
        for (Dimension next : this.dimensions) {
            if (next.getName().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public void setConstantValue(DimensionValueSet dimensionValueSet) {
        if (this.dimensions != null && dimensionValueSet != null) {
            for (Dimension next : this.dimensions) {
                if (next.getConstantValue() != null && dimensionValueSet.getValue(next.getName()) == null) {
                    dimensionValueSet.setValue(next.getName(), next.getConstantValue());
                }
            }
        }
    }

    public List<Dimension> getDimensions() {
        return this.dimensions;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.dimensions != null) {
            try {
                Object[] array = this.dimensions.toArray();
                Dimension[] dimensionArr = null;
                if (array != null) {
                    dimensionArr = new Dimension[array.length];
                    for (int i2 = 0; i2 < array.length; i2++) {
                        dimensionArr[i2] = (Dimension) array[i2];
                    }
                }
                parcel.writeParcelableArray(dimensionArr, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static DimensionSet readFromParcel(Parcel parcel) {
        DimensionSet create = create();
        try {
            Parcelable[] readParcelableArray = parcel.readParcelableArray(DimensionSet.class.getClassLoader());
            if (readParcelableArray != null) {
                if (create.dimensions == null) {
                    create.dimensions = new ArrayList();
                }
                for (int i = 0; i < readParcelableArray.length; i++) {
                    if (readParcelableArray[i] == null || !(readParcelableArray[i] instanceof Dimension)) {
                        Logger.d((String) TAG, "parcelables[i]:", readParcelableArray[i]);
                    } else {
                        create.dimensions.add((Dimension) readParcelableArray[i]);
                    }
                }
            }
        } catch (Throwable th) {
            Logger.w((String) TAG, "[readFromParcel]", th);
        }
        return create;
    }
}
