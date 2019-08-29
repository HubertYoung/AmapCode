package com.sina.weibo.sdk.api;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class StoryMessage implements Parcelable {
    public static final Creator<StoryMessage> CREATOR = new Creator<StoryMessage>() {
        public final StoryMessage createFromParcel(Parcel parcel) {
            return new StoryMessage(parcel);
        }

        public final StoryMessage[] newArray(int i) {
            return new StoryMessage[i];
        }
    };
    private Uri imageUri;
    private Uri videoUri;

    public int describeContents() {
        return 0;
    }

    public Uri getImageUri() {
        return this.imageUri;
    }

    public Uri getVideoUri() {
        return this.videoUri;
    }

    public void setImageUri(Uri uri) {
        this.imageUri = uri;
    }

    public void setVideoUri(Uri uri) {
        this.videoUri = uri;
    }

    public StoryMessage() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.imageUri, i);
        parcel.writeParcelable(this.videoUri, i);
    }

    protected StoryMessage(Parcel parcel) {
        this.imageUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.videoUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
    }

    public boolean checkSource() {
        if (this.imageUri != null && this.videoUri != null) {
            return false;
        }
        if (this.imageUri == null && this.videoUri == null) {
            return false;
        }
        return true;
    }
}
