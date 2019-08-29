package com.sina.weibo.sdk.web;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sina.weibo.sdk.auth.AuthInfo;
import java.io.Serializable;

public class BaseWebViewRequestData implements Parcelable, Serializable {
    public static final Creator<BaseWebViewRequestData> CREATOR = new Creator<BaseWebViewRequestData>() {
        public final BaseWebViewRequestData createFromParcel(Parcel parcel) {
            return new BaseWebViewRequestData(parcel);
        }

        public final BaseWebViewRequestData[] newArray(int i) {
            return new BaseWebViewRequestData[i];
        }
    };
    private AuthInfo authInfo;
    private String callback;
    private int callbackType;
    private String specifyTitle;
    private WebRequestType type;
    private String url;

    public int describeContents() {
        return 0;
    }

    public void setCallback(String str) {
        this.callback = str;
    }

    public String getCallback() {
        return this.callback;
    }

    public BaseWebViewRequestData(AuthInfo authInfo2, WebRequestType webRequestType, String str, String str2, String str3) {
        this(authInfo2, webRequestType, str, 0, str2, str3);
    }

    public BaseWebViewRequestData(AuthInfo authInfo2, WebRequestType webRequestType, String str, int i, String str2, String str3) {
        this.callbackType = 0;
        this.callback = str;
        this.authInfo = authInfo2;
        this.type = webRequestType;
        this.specifyTitle = str2;
        this.url = str3;
        this.callbackType = i;
    }

    public int getCallbackType() {
        return this.callbackType;
    }

    public void setCallbackType(int i) {
        this.callbackType = i;
    }

    public String getSpecifyTitle() {
        return this.specifyTitle;
    }

    public void setSpecifyTitle(String str) {
        this.specifyTitle = str;
    }

    public String getUrl() {
        return this.url;
    }

    public AuthInfo getAuthInfo() {
        return this.authInfo;
    }

    public WebRequestType getType() {
        return this.type;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setAuthInfo(AuthInfo authInfo2) {
        this.authInfo = authInfo2;
    }

    public void setType(WebRequestType webRequestType) {
        this.type = webRequestType;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.url);
        parcel.writeParcelable(this.authInfo, i);
        parcel.writeInt(this.type == null ? -1 : this.type.ordinal());
        parcel.writeString(this.callback);
        parcel.writeString(this.specifyTitle);
        parcel.writeInt(this.callbackType);
    }

    protected BaseWebViewRequestData(Parcel parcel) {
        WebRequestType webRequestType;
        this.callbackType = 0;
        this.url = parcel.readString();
        this.authInfo = (AuthInfo) parcel.readParcelable(AuthInfo.class.getClassLoader());
        int readInt = parcel.readInt();
        if (readInt == -1) {
            webRequestType = null;
        } else {
            webRequestType = WebRequestType.values()[readInt];
        }
        this.type = webRequestType;
        this.callback = parcel.readString();
        this.specifyTitle = parcel.readString();
        this.callbackType = parcel.readInt();
    }
}
