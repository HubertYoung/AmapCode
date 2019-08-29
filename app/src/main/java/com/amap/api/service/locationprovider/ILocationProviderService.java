package com.amap.api.service.locationprovider;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILocationProviderService extends IInterface {

    public static abstract class Stub extends Binder implements ILocationProviderService {
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.amap.api.service.locationprovider.ILocationProviderService");
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.amap.api.service.locationprovider.ILocationProviderService");
                Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                int a = a(bundle);
                parcel2.writeNoException();
                parcel2.writeInt(a);
                if (bundle != null) {
                    parcel2.writeInt(1);
                    bundle.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.amap.api.service.locationprovider.ILocationProviderService");
                return true;
            }
        }
    }

    int a(Bundle bundle) throws RemoteException;
}
