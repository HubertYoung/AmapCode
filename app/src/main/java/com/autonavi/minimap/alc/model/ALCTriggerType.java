package com.autonavi.minimap.alc.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public enum ALCTriggerType implements Parcelable {
    appLaunch(0),
    appEnterForeground(1);
    
    public static final Creator<ALCTriggerType> CREATOR = null;
    private int num;

    public final int describeContents() {
        return 0;
    }

    static {
        CREATOR = new Creator<ALCTriggerType>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new ALCTriggerType[0];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                switch (parcel.readInt()) {
                    case 0:
                        return ALCTriggerType.appLaunch;
                    case 1:
                        return ALCTriggerType.appEnterForeground;
                    default:
                        return ALCTriggerType.appLaunch;
                }
            }
        };
    }

    private ALCTriggerType(int i) {
        this.num = i;
    }

    public final int getNum() {
        return this.num;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.num);
    }
}
