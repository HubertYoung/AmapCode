package com.standardar.service.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SharedMemory;

public interface IDataFlowInterface extends IInterface {

    public static abstract class Stub extends Binder implements IDataFlowInterface {
        private static final String DESCRIPTOR = "com.standardar.service.aidl.IDataFlowInterface";
        static final int TRANSACTION_processFrame = 1;
        static final int TRANSACTION_processFrameShareMemory = 2;
        static final int TRANSACTION_processFrameShareMemoryV27 = 4;
        static final int TRANSACTION_sendCommand = 5;
        static final int TRANSACTION_setupSharedMemory = 3;

        static class Proxy implements IDataFlowInterface {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public byte[] processFrame(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public byte[] processFrameShareMemory(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        obtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setupSharedMemory(SharedMemory sharedMemory, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sharedMemory != null) {
                        obtain.writeInt(1);
                        sharedMemory.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public byte[] processFrameShareMemoryV27() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int sendCommand(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
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

        public static IDataFlowInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDataFlowInterface)) {
                return new Proxy(iBinder);
            }
            return (IDataFlowInterface) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v4, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v5, types: [android.os.SharedMemory] */
        /* JADX WARNING: type inference failed for: r0v7, types: [android.os.SharedMemory] */
        /* JADX WARNING: type inference failed for: r0v8 */
        /* JADX WARNING: type inference failed for: r0v9 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.os.SharedMemory, android.os.ParcelFileDescriptor]
          uses: [android.os.ParcelFileDescriptor, android.os.SharedMemory]
          mth insns count: 50
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
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r3, android.os.Parcel r4, android.os.Parcel r5, int r6) throws android.os.RemoteException {
            /*
                r2 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r3 == r0) goto L_0x008d
                r0 = 0
                switch(r3) {
                    case 1: goto L_0x0079;
                    case 2: goto L_0x0056;
                    case 3: goto L_0x0037;
                    case 4: goto L_0x0027;
                    case 5: goto L_0x000f;
                    default: goto L_0x000a;
                }
            L_0x000a:
                boolean r3 = super.onTransact(r3, r4, r5, r6)
                return r3
            L_0x000f:
                java.lang.String r3 = "com.standardar.service.aidl.IDataFlowInterface"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                byte[] r4 = r4.createByteArray()
                int r3 = r2.sendCommand(r3, r4)
                r5.writeNoException()
                r5.writeInt(r3)
                return r1
            L_0x0027:
                java.lang.String r3 = "com.standardar.service.aidl.IDataFlowInterface"
                r4.enforceInterface(r3)
                byte[] r3 = r2.processFrameShareMemoryV27()
                r5.writeNoException()
                r5.writeByteArray(r3)
                return r1
            L_0x0037:
                java.lang.String r3 = "com.standardar.service.aidl.IDataFlowInterface"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x004b
                android.os.Parcelable$Creator r3 = android.os.SharedMemory.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                android.os.SharedMemory r0 = (android.os.SharedMemory) r0
            L_0x004b:
                int r3 = r4.readInt()
                r2.setupSharedMemory(r0, r3)
                r5.writeNoException()
                return r1
            L_0x0056:
                java.lang.String r3 = "com.standardar.service.aidl.IDataFlowInterface"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x006a
                android.os.Parcelable$Creator r3 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
            L_0x006a:
                int r3 = r4.readInt()
                byte[] r3 = r2.processFrameShareMemory(r0, r3)
                r5.writeNoException()
                r5.writeByteArray(r3)
                return r1
            L_0x0079:
                java.lang.String r3 = "com.standardar.service.aidl.IDataFlowInterface"
                r4.enforceInterface(r3)
                byte[] r3 = r4.createByteArray()
                byte[] r3 = r2.processFrame(r3)
                r5.writeNoException()
                r5.writeByteArray(r3)
                return r1
            L_0x008d:
                java.lang.String r3 = "com.standardar.service.aidl.IDataFlowInterface"
                r5.writeString(r3)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.standardar.service.aidl.IDataFlowInterface.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    byte[] processFrame(byte[] bArr) throws RemoteException;

    byte[] processFrameShareMemory(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    byte[] processFrameShareMemoryV27() throws RemoteException;

    int sendCommand(int i, byte[] bArr) throws RemoteException;

    void setupSharedMemory(SharedMemory sharedMemory, int i) throws RemoteException;
}
