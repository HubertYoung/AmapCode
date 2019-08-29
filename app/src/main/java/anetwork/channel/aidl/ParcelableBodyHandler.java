package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ParcelableBodyHandler extends IInterface {

    public static abstract class Stub extends Binder implements ParcelableBodyHandler {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.ParcelableBodyHandler";
        static final int TRANSACTION_isCompleted = 2;
        static final int TRANSACTION_read = 1;

        static class Proxy implements ParcelableBodyHandler {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public int read(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isCompleted() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.a.transact(2, obtain, obtain2, 0);
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

        public static ParcelableBodyHandler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ParcelableBodyHandler)) {
                return new Proxy(iBinder);
            }
            return (ParcelableBodyHandler) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        byte[] createByteArray = parcel.createByteArray();
                        int read = read(createByteArray);
                        parcel2.writeNoException();
                        parcel2.writeInt(read);
                        parcel2.writeByteArray(createByteArray);
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean isCompleted = isCompleted();
                        parcel2.writeNoException();
                        parcel2.writeInt(isCompleted ? 1 : 0);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }
    }

    boolean isCompleted() throws RemoteException;

    int read(byte[] bArr) throws RemoteException;
}
