package com.alipay.mobile.rome.longlinkservice.syncmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.HashMap;

public class SyncInitInfo implements Parcelable {
    public static final Creator<SyncInitInfo> CREATOR = new Creator<SyncInitInfo>() {
        public final SyncInitInfo createFromParcel(Parcel parcel) {
            return new SyncInitInfo(parcel);
        }

        public final SyncInitInfo[] newArray(int i) {
            return new SyncInitInfo[i];
        }
    };
    public String appName;
    public String[] baseDeviceBiz;
    public String[] baseUserBiz;
    public String deviceId;
    public HashMap<String, String> extParaMap;
    public String host;
    public boolean isUseSSL;
    public String port;
    public String productId;
    public String productVersion;
    public String tid;
    public String verifyInfo;

    public int describeContents() {
        return 0;
    }

    public SyncInitInfo() {
        this.isUseSSL = false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SyncMsg [deviceId=");
        sb.append(this.deviceId);
        sb.append(", productId=");
        sb.append(this.productId);
        sb.append(", productVersion=");
        sb.append(this.productVersion);
        sb.append(", appName=");
        sb.append(this.appName);
        sb.append(", host=");
        sb.append(this.host);
        sb.append("], port=");
        sb.append(this.port);
        sb.append("], isUseSSL=");
        sb.append(this.isUseSSL);
        sb.append("], verifyInfo=");
        sb.append(this.verifyInfo);
        sb.append("], tid=");
        sb.append(this.tid);
        sb.append("], extParaMap=");
        sb.append(this.extParaMap);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceId);
        parcel.writeString(this.productId);
        parcel.writeString(this.productVersion);
        parcel.writeString(this.appName);
        parcel.writeString(this.host);
        parcel.writeString(this.port);
        parcel.writeString(this.verifyInfo);
        parcel.writeString(this.tid);
        parcel.writeMap(this.extParaMap);
        if (this.isUseSSL) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeStringArray(this.baseUserBiz);
        parcel.writeStringArray(this.baseDeviceBiz);
    }

    private SyncInitInfo(Parcel parcel) {
        this.isUseSSL = false;
        this.deviceId = parcel.readString();
        this.productId = parcel.readString();
        this.productVersion = parcel.readString();
        this.appName = parcel.readString();
        this.host = parcel.readString();
        this.port = parcel.readString();
        this.verifyInfo = parcel.readString();
        this.tid = parcel.readString();
        this.extParaMap = parcel.readHashMap(HashMap.class.getClassLoader());
        if (parcel.readInt() == 0) {
            this.isUseSSL = false;
        } else {
            this.isUseSSL = true;
        }
    }
}
