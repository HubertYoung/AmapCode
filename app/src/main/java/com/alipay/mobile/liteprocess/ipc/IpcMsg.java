package com.alipay.mobile.liteprocess.ipc;

import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Const;

public class IpcMsg implements Parcelable {
    public static final Creator<IpcMsg> CREATOR = new Creator<IpcMsg>() {
        public final IpcMsg createFromParcel(Parcel source) {
            return new IpcMsg(source);
        }

        public final IpcMsg[] newArray(int size) {
            return new IpcMsg[size];
        }
    };
    int a;
    int b;
    String c;
    String d;
    String e;
    Message f;

    static byte[] a(IpcMsg ipcMsg) {
        Parcel out = Parcel.obtain();
        byte[] buf = null;
        try {
            out.writeValue(ipcMsg);
            buf = out.marshall();
        } catch (Exception e2) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcMsg marshall error " + Log.getStackTraceString(e2));
        } finally {
            out.recycle();
        }
        return buf;
    }

    static IpcMsg a(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Parcel in = Parcel.obtain();
        boolean z = false;
        try {
            in.unmarshall(bytes, 0, bytes.length);
            in.setDataPosition(0);
            return (IpcMsg) in.readValue(IpcMsg.class.getClassLoader());
        } catch (Exception e2) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcMsg unmarshall error " + Log.getStackTraceString(e2));
            return z;
        } finally {
            in.recycle();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.a);
        dest.writeInt(this.b);
        dest.writeString(this.c);
        dest.writeString(this.d);
        dest.writeString(this.e);
        dest.writeParcelable(this.f, flags);
    }

    public IpcMsg() {
    }

    protected IpcMsg(Parcel in) {
        this.a = in.readInt();
        this.b = in.readInt();
        this.c = in.readString();
        this.d = in.readString();
        this.e = in.readString();
        this.f = (Message) in.readParcelable(Message.class.getClassLoader());
    }
}
