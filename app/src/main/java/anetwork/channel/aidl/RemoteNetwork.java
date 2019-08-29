package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface RemoteNetwork extends IInterface {

    public static abstract class Stub extends Binder implements RemoteNetwork {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.RemoteNetwork";
        static final int TRANSACTION_asyncSend = 2;
        static final int TRANSACTION_getConnection = 3;
        static final int TRANSACTION_syncSend = 1;

        static class Proxy implements RemoteNetwork {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        obtain.writeInt(1);
                        parcelableRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? NetworkResponse.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        obtain.writeInt(1);
                        parcelableRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(parcelableNetworkListener != null ? parcelableNetworkListener.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return anetwork.channel.aidl.ParcelableFuture.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        obtain.writeInt(1);
                        parcelableRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return anetwork.channel.aidl.Connection.Stub.asInterface(obtain2.readStrongBinder());
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

        public static RemoteNetwork asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof RemoteNetwork)) {
                return new Proxy(iBinder);
            }
            return (RemoteNetwork) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [anetwork.channel.aidl.ParcelableRequest] */
        /* JADX WARNING: type inference failed for: r0v4, types: [anetwork.channel.aidl.ParcelableRequest] */
        /* JADX WARNING: type inference failed for: r0v5, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v6, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v7, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v8, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v9 */
        /* JADX WARNING: type inference failed for: r0v10 */
        /* JADX WARNING: type inference failed for: r0v11 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.os.IBinder, anetwork.channel.aidl.ParcelableRequest]
          uses: [anetwork.channel.aidl.ParcelableRequest, android.os.IBinder]
          mth insns count: 52
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 4 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r3, android.os.Parcel r4, android.os.Parcel r5, int r6) throws android.os.RemoteException {
            /*
                r2 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r3 == r0) goto L_0x008c
                r0 = 0
                switch(r3) {
                    case 1: goto L_0x0063;
                    case 2: goto L_0x0035;
                    case 3: goto L_0x000f;
                    default: goto L_0x000a;
                }
            L_0x000a:
                boolean r3 = super.onTransact(r3, r4, r5, r6)
                return r3
            L_0x000f:
                java.lang.String r3 = "anetwork.channel.aidl.RemoteNetwork"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<anetwork.channel.aidl.ParcelableRequest> r3 = anetwork.channel.aidl.ParcelableRequest.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                anetwork.channel.aidl.ParcelableRequest r3 = (anetwork.channel.aidl.ParcelableRequest) r3
                goto L_0x0024
            L_0x0023:
                r3 = r0
            L_0x0024:
                anetwork.channel.aidl.Connection r3 = r2.getConnection(r3)
                r5.writeNoException()
                if (r3 == 0) goto L_0x0031
                android.os.IBinder r0 = r3.asBinder()
            L_0x0031:
                r5.writeStrongBinder(r0)
                return r1
            L_0x0035:
                java.lang.String r3 = "anetwork.channel.aidl.RemoteNetwork"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0049
                android.os.Parcelable$Creator<anetwork.channel.aidl.ParcelableRequest> r3 = anetwork.channel.aidl.ParcelableRequest.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                anetwork.channel.aidl.ParcelableRequest r3 = (anetwork.channel.aidl.ParcelableRequest) r3
                goto L_0x004a
            L_0x0049:
                r3 = r0
            L_0x004a:
                android.os.IBinder r4 = r4.readStrongBinder()
                anetwork.channel.aidl.ParcelableNetworkListener r4 = anetwork.channel.aidl.ParcelableNetworkListener.Stub.asInterface(r4)
                anetwork.channel.aidl.ParcelableFuture r3 = r2.asyncSend(r3, r4)
                r5.writeNoException()
                if (r3 == 0) goto L_0x005f
                android.os.IBinder r0 = r3.asBinder()
            L_0x005f:
                r5.writeStrongBinder(r0)
                return r1
            L_0x0063:
                java.lang.String r3 = "anetwork.channel.aidl.RemoteNetwork"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0077
                android.os.Parcelable$Creator<anetwork.channel.aidl.ParcelableRequest> r3 = anetwork.channel.aidl.ParcelableRequest.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                anetwork.channel.aidl.ParcelableRequest r0 = (anetwork.channel.aidl.ParcelableRequest) r0
            L_0x0077:
                anetwork.channel.aidl.NetworkResponse r3 = r2.syncSend(r0)
                r5.writeNoException()
                if (r3 == 0) goto L_0x0087
                r5.writeInt(r1)
                r3.writeToParcel(r5, r1)
                goto L_0x008b
            L_0x0087:
                r3 = 0
                r5.writeInt(r3)
            L_0x008b:
                return r1
            L_0x008c:
                java.lang.String r3 = "anetwork.channel.aidl.RemoteNetwork"
                r5.writeString(r3)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.aidl.RemoteNetwork.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException;

    Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException;

    NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException;
}
