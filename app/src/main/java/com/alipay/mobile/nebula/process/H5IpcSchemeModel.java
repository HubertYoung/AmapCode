package com.alipay.mobile.nebula.process;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class H5IpcSchemeModel implements Parcelable {
    public static final Creator<H5IpcSchemeModel> CREATOR = new Creator<H5IpcSchemeModel>() {
        public final H5IpcSchemeModel createFromParcel(Parcel source) {
            return new H5IpcSchemeModel(source);
        }

        public final H5IpcSchemeModel[] newArray(int size) {
            return new H5IpcSchemeModel[size];
        }
    };
    private Bundle params;
    private Uri uri;

    public H5IpcSchemeModel() {
    }

    public H5IpcSchemeModel(Uri uri2, Bundle params2) {
        this.uri = uri2;
        this.params = params2;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setUri(Uri uri2) {
        this.uri = uri2;
    }

    public Bundle getParams() {
        return this.params;
    }

    public void setParams(Bundle params2) {
        this.params = params2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.uri, flags);
        dest.writeBundle(this.params);
    }

    protected H5IpcSchemeModel(Parcel in) {
        this.uri = (Uri) in.readParcelable(Uri.class.getClassLoader());
        this.params = in.readBundle();
    }
}
