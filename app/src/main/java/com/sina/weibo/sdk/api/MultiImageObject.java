package com.sina.weibo.sdk.api;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class MultiImageObject extends BaseMediaObject {
    public static final Creator<MultiImageObject> CREATOR = new Creator<MultiImageObject>() {
        public final MultiImageObject createFromParcel(Parcel parcel) {
            return new MultiImageObject(parcel);
        }

        public final MultiImageObject[] newArray(int i) {
            return new MultiImageObject[i];
        }
    };
    public ArrayList<Uri> imageList;

    public int describeContents() {
        return 0;
    }

    public int getObjType() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public BaseMediaObject toExtraMediaObject(String str) {
        return null;
    }

    /* access modifiers changed from: protected */
    public String toExtraMediaString() {
        return null;
    }

    public void setImageList(ArrayList<Uri> arrayList) {
        this.imageList = arrayList;
    }

    public ArrayList<Uri> getImageList() {
        return this.imageList;
    }

    public MultiImageObject() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.imageList);
    }

    protected MultiImageObject(Parcel parcel) {
        super(parcel);
        this.imageList = parcel.createTypedArrayList(Uri.CREATOR);
    }
}
