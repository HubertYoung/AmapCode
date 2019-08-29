package com.sina.weibo.sdk.api;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;

public class VideoSourceObject extends BaseMediaObject {
    public static final Creator<VideoSourceObject> CREATOR = new Creator<VideoSourceObject>() {
        public final VideoSourceObject createFromParcel(Parcel parcel) {
            return new VideoSourceObject(parcel);
        }

        public final VideoSourceObject[] newArray(int i) {
            return new VideoSourceObject[i];
        }
    };
    public Uri coverPath;
    public long during;
    public Uri videoPath;

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

    public VideoSourceObject() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.coverPath, i);
        parcel.writeParcelable(this.videoPath, i);
        parcel.writeLong(this.during);
    }

    protected VideoSourceObject(Parcel parcel) {
        super(parcel);
        this.coverPath = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.videoPath = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.during = parcel.readLong();
    }
}
