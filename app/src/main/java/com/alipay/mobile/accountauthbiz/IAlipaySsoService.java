package com.alipay.mobile.accountauthbiz;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAlipaySsoService extends IInterface {

    public static abstract class Stub extends Binder implements IAlipaySsoService {

        static class Proxy implements IAlipaySsoService {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public final int a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alipay.mobile.accountauthbiz.IAlipaySsoService");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final AlipaySsoInfo b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alipay.mobile.accountauthbiz.IAlipaySsoService");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? AlipaySsoInfo.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IAlipaySsoService a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.alipay.mobile.accountauthbiz.IAlipaySsoService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAlipaySsoService)) {
                return new Proxy(iBinder);
            }
            return (IAlipaySsoService) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.alipay.mobile.accountauthbiz.IAlipaySsoService");
                        int a = a();
                        parcel2.writeNoException();
                        parcel2.writeInt(a);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.alipay.mobile.accountauthbiz.IAlipaySsoService");
                        AlipaySsoInfo b = b();
                        parcel2.writeNoException();
                        if (b != null) {
                            parcel2.writeInt(1);
                            b.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.alipay.mobile.accountauthbiz.IAlipaySsoService");
                return true;
            }
        }
    }

    int a() throws RemoteException;

    AlipaySsoInfo b() throws RemoteException;
}
