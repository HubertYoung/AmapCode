package com.alipay.mobile.liteprocess.rpc;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CallRet implements Parcelable {
    public static final Creator<CallRet> CREATOR = new Creator<CallRet>() {
        public final CallRet createFromParcel(Parcel source) {
            return new CallRet(source);
        }

        public final CallRet[] newArray(int size) {
            return new CallRet[size];
        }
    };
    public byte[] data;
    public LiteRpcInvokeContext invokeContext;
    public boolean isException;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(this.data);
        dest.writeParcelable(this.invokeContext, flags);
        dest.writeByte(this.isException ? (byte) 1 : 0);
    }

    public CallRet() {
    }

    protected CallRet(Parcel in) {
        this.data = in.createByteArray();
        this.invokeContext = (LiteRpcInvokeContext) in.readParcelable(LiteRpcInvokeContext.class.getClassLoader());
        this.isException = in.readByte() != 0;
    }
}
