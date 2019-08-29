package com.uc.sandboxExport;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

@Api
/* compiled from: ProGuard */
public interface IChildProcessSetup extends IInterface {

    @Api
    /* compiled from: ProGuard */
    public static abstract class Stub extends Binder implements IChildProcessSetup {

        /* compiled from: ProGuard */
        private static class Proxy implements IChildProcessSetup {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public IBinder preSetupConnection(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.uc.sandboxExport.IChildProcessSetup");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.uc.sandboxExport.IChildProcessSetup");
        }

        public static IChildProcessSetup asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.uc.sandboxExport.IChildProcessSetup");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IChildProcessSetup)) {
                return new Proxy(iBinder);
            }
            return (IChildProcessSetup) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            Bundle bundle;
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.uc.sandboxExport.IChildProcessSetup");
                    if (parcel.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                    } else {
                        bundle = null;
                    }
                    IBinder preSetupConnection = preSetupConnection(bundle);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(preSetupConnection);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.uc.sandboxExport.IChildProcessSetup");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    IBinder preSetupConnection(Bundle bundle);
}
