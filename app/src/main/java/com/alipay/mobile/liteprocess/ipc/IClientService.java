package com.alipay.mobile.liteprocess.ipc;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface IClientService extends IInterface {

    public abstract class Stub extends Binder implements IClientService {

        class Proxy implements IClientService {
            private IBinder a;

            Proxy(IBinder remote) {
                this.a = remote;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public String getInterfaceDescriptor() {
                return "com.alipay.mobile.liteprocess.ipc.IClientService";
            }

            public void destoryClient() {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.alipay.mobile.liteprocess.ipc.IClientService");
                    this.a.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void moveTaskToFront(int runningTaskInfoId, boolean enter, boolean fromForeground, Bundle params) {
                int i = 1;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.alipay.mobile.liteprocess.ipc.IClientService");
                    _data.writeInt(runningTaskInfoId);
                    _data.writeInt(enter ? 1 : 0);
                    if (!fromForeground) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.a.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyLogout() {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.alipay.mobile.liteprocess.ipc.IClientService");
                    this.a.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.alipay.mobile.liteprocess.ipc.IClientService");
        }

        public static IClientService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.alipay.mobile.liteprocess.ipc.IClientService");
            if (iin == null || !(iin instanceof IClientService)) {
                return new Proxy(obj);
            }
            return (IClientService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            boolean _arg1;
            boolean _arg2;
            Bundle _arg3;
            switch (code) {
                case 1:
                    data.enforceInterface("com.alipay.mobile.liteprocess.ipc.IClientService");
                    destoryClient();
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.alipay.mobile.liteprocess.ipc.IClientService");
                    int _arg0 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg1 = true;
                    } else {
                        _arg1 = false;
                    }
                    if (data.readInt() != 0) {
                        _arg2 = true;
                    } else {
                        _arg2 = false;
                    }
                    if (data.readInt() != 0) {
                        _arg3 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg3 = null;
                    }
                    moveTaskToFront(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.alipay.mobile.liteprocess.ipc.IClientService");
                    notifyLogout();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.alipay.mobile.liteprocess.ipc.IClientService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void destoryClient();

    void moveTaskToFront(int i, boolean z, boolean z2, Bundle bundle);

    void notifyLogout();
}
