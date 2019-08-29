package com.huawei.lbs;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHuaweiHiGeoService extends IInterface {

    public static abstract class Stub extends Binder implements IHuaweiHiGeoService {
        private static final String DESCRIPTOR = "com.huawei.lbs.IHuaweiHiGeoService";
        static final int TRANSACTION_sendMapMatchingResult = 1;

        static class Proxy implements IHuaweiHiGeoService {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public boolean sendMapMatchingResult(long j, double d, double d2, double d3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeDouble(d);
                    obtain.writeDouble(d2);
                    obtain.writeDouble(d3);
                    obtain.writeInt(i);
                    boolean z = false;
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
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
            attachInterface(this, DESCRIPTOR);
        }

        public static IHuaweiHiGeoService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IHuaweiHiGeoService)) {
                return new Proxy(iBinder);
            }
            return (IHuaweiHiGeoService) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean sendMapMatchingResult = sendMapMatchingResult(parcel.readLong(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(sendMapMatchingResult ? 1 : 0);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }
    }

    boolean sendMapMatchingResult(long j, double d, double d2, double d3, int i) throws RemoteException;
}
