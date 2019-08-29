package com.alipay.mobile.liteprocess.rpc;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.mobile.common.rpc.RpcException;

public class LiteRpcException extends RpcException implements Parcelable {
    public static final Creator<LiteRpcException> CREATOR = new Creator<LiteRpcException>() {
        public final LiteRpcException createFromParcel(Parcel source) {
            return new LiteRpcException(source);
        }

        public final LiteRpcException[] newArray(int size) {
            return new LiteRpcException[size];
        }
    };

    public LiteRpcException(Integer code, String msg) {
        super(code, msg);
    }

    public LiteRpcException(Integer code, Throwable cause) {
        super(code, cause);
    }

    public LiteRpcException(String msg) {
        super(msg);
    }

    public LiteRpcException(RpcException rpcException) {
        super("");
        this.mOperationType = rpcException.getOperationType();
        this.mCode = rpcException.getCode();
        this.mMsg = rpcException.getMsg();
        this.mControl = rpcException.getControl();
        this.mIsControlOwn = rpcException.isControlOwn();
        this.errorSource = rpcException.getErrorSource();
        this.isBgRpc = rpcException.isBgRpc();
        this.alert = rpcException.getAlert();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2 = 1;
        dest.writeString(this.mOperationType);
        dest.writeInt(this.mCode);
        dest.writeString(this.mMsg);
        dest.writeString(this.mControl);
        if (this.mIsControlOwn) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        dest.writeInt(this.errorSource);
        if (!this.isBgRpc) {
            b2 = 0;
        }
        dest.writeByte(b2);
        dest.writeInt(this.alert);
    }

    public LiteRpcException() {
        super("");
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    protected LiteRpcException(Parcel in) {
        boolean z;
        // boolean z2 = true;
        super("");
        this.mOperationType = in.readString();
        this.mCode = in.readInt();
        this.mMsg = in.readString();
        this.mControl = in.readString();
        if (in.readByte() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsControlOwn = z;
        this.errorSource = in.readInt();
        this.isBgRpc = in.readByte() == 0 ? false : z2;
        this.alert = in.readInt();
    }
}
