package com.amap.location.sdk.fusion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILocationService extends IInterface {

    public static abstract class Stub extends Binder implements ILocationService {

        static class a implements ILocationService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void a(int i, long j, float f, boolean z, ILocationCallback iLocationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.amap.location.sdk.fusion.ILocationService");
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeFloat(f);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeStrongBinder(iLocationCallback != null ? iLocationCallback.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(ILocationCallback iLocationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.amap.location.sdk.fusion.ILocationService");
                    obtain.writeStrongBinder(iLocationCallback != null ? iLocationCallback.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(IStatusCallback iStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.amap.location.sdk.fusion.ILocationService");
                    obtain.writeStrongBinder(iStatusCallback != null ? iStatusCallback.asBinder() : null);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.amap.location.sdk.fusion.ILocationService");
                    obtain.writeString(str);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(String str, int i, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.amap.location.sdk.fusion.ILocationService");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String b(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.amap.location.sdk.fusion.ILocationService");
                    obtain.writeString(str);
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.amap.location.sdk.fusion.ILocationService");
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.amap.location.sdk.fusion.ILocationService");
        }

        public static ILocationService a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.amap.location.sdk.fusion.ILocationService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ILocationService)) {
                return new a(iBinder);
            }
            return (ILocationService) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.amap.location.sdk.fusion.ILocationService");
                        a(parcel.readInt(), parcel.readLong(), parcel.readFloat(), parcel.readInt() != 0, com.amap.location.sdk.fusion.ILocationCallback.Stub.a(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.amap.location.sdk.fusion.ILocationService");
                        a(com.amap.location.sdk.fusion.ILocationCallback.Stub.a(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.amap.location.sdk.fusion.ILocationService");
                        a(com.amap.location.sdk.fusion.IStatusCallback.Stub.a(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.amap.location.sdk.fusion.ILocationService");
                        a(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface("com.amap.location.sdk.fusion.ILocationService");
                        a(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface("com.amap.location.sdk.fusion.ILocationService");
                        String b = b(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(b);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.amap.location.sdk.fusion.ILocationService");
                        a();
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.amap.location.sdk.fusion.ILocationService");
                return true;
            }
        }
    }

    void a() throws RemoteException;

    void a(int i, long j, float f, boolean z, ILocationCallback iLocationCallback) throws RemoteException;

    void a(ILocationCallback iLocationCallback) throws RemoteException;

    void a(IStatusCallback iStatusCallback) throws RemoteException;

    void a(String str) throws RemoteException;

    void a(String str, int i, int i2, String str2) throws RemoteException;

    String b(String str) throws RemoteException;
}
