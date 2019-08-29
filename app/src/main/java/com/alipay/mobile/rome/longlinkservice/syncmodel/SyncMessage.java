package com.alipay.mobile.rome.longlinkservice.syncmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SyncMessage implements Parcelable {
    public static final Creator<SyncMessage> CREATOR = new Creator<SyncMessage>() {
        public final SyncMessage createFromParcel(Parcel parcel) {
            return new SyncMessage(parcel);
        }

        public final SyncMessage[] newArray(int i) {
            return new SyncMessage[i];
        }
    };
    public String biz;
    public boolean hasMore;
    public String id;
    public String msgData;
    public String userId;

    public int describeContents() {
        return 0;
    }

    public SyncMessage() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SyncMsg [userId=");
        sb.append(this.userId);
        sb.append(", biz=");
        sb.append(this.biz);
        sb.append(", msgData=");
        sb.append(this.msgData);
        sb.append(", id=");
        sb.append(this.id);
        sb.append(", hasMore=");
        sb.append(this.hasMore);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.biz);
        parcel.writeString(this.msgData);
        parcel.writeString(this.id);
        if (this.hasMore) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
    }

    private SyncMessage(Parcel parcel) {
        this.userId = parcel.readString();
        this.biz = parcel.readString();
        this.msgData = parcel.readString();
        this.id = parcel.readString();
        if (parcel.readInt() == 0) {
            this.hasMore = false;
        } else {
            this.hasMore = true;
        }
    }
}
