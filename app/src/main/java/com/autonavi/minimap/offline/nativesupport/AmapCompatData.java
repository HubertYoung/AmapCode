package com.autonavi.minimap.offline.nativesupport;

import com.autonavi.jni.ae.bl.Parcel;
import com.autonavi.jni.ae.bl.Parcelable;

public class AmapCompatData implements Parcelable {
    public int itemLength;
    public CompatDataItem[] items;
    public int result;

    public boolean writeToParcel(Parcel parcel) {
        parcel.reset();
        parcel.writeInt(this.itemLength);
        parcel.writeInt(this.result);
        writeArray(parcel);
        return true;
    }

    public boolean readFromParcel(Parcel parcel) {
        parcel.reset();
        this.itemLength = parcel.readInt();
        this.result = parcel.readInt();
        readArray(parcel);
        return true;
    }

    private boolean writeArray(Parcel parcel) {
        for (int i = 0; i < this.itemLength; i++) {
            parcel.writeInt(this.items[i].cityId);
            parcel.writeInt(this.items[i].packageType);
            parcel.writeInt(this.items[i].status);
            parcel.writeBoolean(this.items[i].autoMode);
            int length = this.items[i].dataContent.length();
            parcel.writeInt(length);
            if (length > 0) {
                parcel.writeString(this.items[i].dataContent);
            }
        }
        return true;
    }

    private boolean readArray(Parcel parcel) {
        if (this.itemLength > 0) {
            this.items = new CompatDataItem[this.itemLength];
            for (int i = 0; i < this.itemLength; i++) {
                this.items[i] = new CompatDataItem();
                this.items[i].cityId = parcel.readInt();
                this.items[i].packageType = parcel.readInt();
                this.items[i].status = parcel.readInt();
                this.items[i].autoMode = parcel.readBoolean();
                if (parcel.readInt() > 0) {
                    this.items[i].dataContent = parcel.readString();
                }
            }
        }
        return true;
    }
}
