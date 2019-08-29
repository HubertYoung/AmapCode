package com.alipay.mobile.rome.longlinkservice.syncmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SyncCommand implements Parcelable {
    public static final String COMMAND_FULL_UPDATE = "fullUpdate";
    public static final String COMMAND_INIT = "init";
    public static final Creator<SyncCommand> CREATOR = new Creator<SyncCommand>() {
        public final SyncCommand createFromParcel(Parcel parcel) {
            return new SyncCommand(parcel);
        }

        public final SyncCommand[] newArray(int i) {
            return new SyncCommand[i];
        }
    };
    public String biz;
    public String command;
    public String commandData;
    public String id;
    public String userId;

    public int describeContents() {
        return 0;
    }

    public SyncCommand() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SyncMsg [userId=");
        sb.append(this.userId);
        sb.append(", biz=");
        sb.append(this.biz);
        sb.append(", command=");
        sb.append(this.command);
        sb.append(", id=");
        sb.append(this.id);
        sb.append(", commandData=");
        sb.append(this.commandData);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.biz);
        parcel.writeString(this.command);
        parcel.writeString(this.id);
        parcel.writeString(this.commandData);
    }

    private SyncCommand(Parcel parcel) {
        this.userId = parcel.readString();
        this.biz = parcel.readString();
        this.command = parcel.readString();
        this.id = parcel.readString();
        this.commandData = parcel.readString();
    }
}
