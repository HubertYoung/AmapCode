package com.alipay.mobile.liteprocess.rpc;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;

public class CallArgs implements Parcelable {
    public static final Creator<CallArgs> CREATOR = new Creator<CallArgs>() {
        public final CallArgs createFromParcel(Parcel source) {
            return new CallArgs(source);
        }

        public final CallArgs[] newArray(int size) {
            return new CallArgs[size];
        }
    };
    public byte[] argsBody;
    public String className;
    public LiteRpcInvokeContext invokeContext;
    public String methodName;
    public byte protoType;

    public String toString() {
        return "CallArgs{className='" + this.className + '\'' + ", methodName='" + this.methodName + '\'' + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.className);
        dest.writeString(this.methodName);
        dest.writeByteArray(this.argsBody);
        dest.writeByte(this.protoType);
        dest.writeParcelable(this.invokeContext, flags);
    }

    public CallArgs() {
    }

    protected CallArgs(Parcel in) {
        this.className = in.readString();
        this.methodName = in.readString();
        this.argsBody = in.createByteArray();
        this.protoType = in.readByte();
        this.invokeContext = (LiteRpcInvokeContext) in.readParcelable(InnerRpcInvokeContext.class.getClassLoader());
    }
}
