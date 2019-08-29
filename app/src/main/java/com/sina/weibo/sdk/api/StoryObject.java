package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class StoryObject implements Parcelable {
    public static final Creator<StoryObject> CREATOR = new Creator<StoryObject>() {
        public final StoryObject createFromParcel(Parcel parcel) {
            return new StoryObject(parcel);
        }

        public final StoryObject[] newArray(int i) {
            return new StoryObject[i];
        }
    };
    public String appId;
    public String appPackage;
    public String callbackAction = "com.sina.weibo.sdk.action.ACTION_SDK_REQ_STORY";
    public String sourcePath;
    public int sourceType;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sourcePath);
        parcel.writeInt(this.sourceType);
        parcel.writeString(this.appId);
        parcel.writeString(this.appPackage);
        parcel.writeString(this.callbackAction);
    }

    public StoryObject() {
    }

    protected StoryObject(Parcel parcel) {
        this.sourcePath = parcel.readString();
        this.sourceType = parcel.readInt();
        this.appId = parcel.readString();
        this.appPackage = parcel.readString();
        this.callbackAction = parcel.readString();
    }
}
