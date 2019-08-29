package com.yunos.carkitsdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IServiceStatusCallBack extends IInterface {

    public static abstract class Stub extends Binder implements IServiceStatusCallBack {
        private static final String DESCRIPTOR = "com.yunos.carkitsdk.IServiceStatusCallBack";
        static final int TRANSACTION_onConnectionStatusNotify = 4;
        static final int TRANSACTION_onFoundCar = 1;
        static final int TRANSACTION_onReceiveFileNotify = 6;
        static final int TRANSACTION_onReceiveMsgNotify = 5;
        static final int TRANSACTION_onRegisteredComponents = 2;
        static final int TRANSACTION_onRemoteComponents = 3;
        static final int TRANSACTION_onSendFileNotify = 7;
        static final int TRANSACTION_onUnregisteredByOther = 8;

        static class Proxy implements IServiceStatusCallBack {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void onFoundCar(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onRegisteredComponents(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onRemoteComponents(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onConnectionStatusNotify(ConnectionStatusInfo connectionStatusInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (connectionStatusInfo != null) {
                        obtain.writeInt(1);
                        connectionStatusInfo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onReceiveMsgNotify(long j, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onReceiveFileNotify(TransferInfo transferInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (transferInfo != null) {
                        obtain.writeInt(1);
                        transferInfo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onSendFileNotify(TransferInfo transferInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (transferInfo != null) {
                        obtain.writeInt(1);
                        transferInfo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onUnregisteredByOther() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.a.transact(8, obtain, obtain2, 0);
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
            attachInterface(this, DESCRIPTOR);
        }

        public static IServiceStatusCallBack asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IServiceStatusCallBack)) {
                return new Proxy(iBinder);
            }
            return (IServiceStatusCallBack) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [com.yunos.carkitsdk.ConnectionStatusInfo] */
        /* JADX WARNING: type inference failed for: r0v4, types: [com.yunos.carkitsdk.ConnectionStatusInfo] */
        /* JADX WARNING: type inference failed for: r0v5, types: [com.yunos.carkitsdk.TransferInfo] */
        /* JADX WARNING: type inference failed for: r0v7, types: [com.yunos.carkitsdk.TransferInfo] */
        /* JADX WARNING: type inference failed for: r0v8, types: [com.yunos.carkitsdk.TransferInfo] */
        /* JADX WARNING: type inference failed for: r0v10, types: [com.yunos.carkitsdk.TransferInfo] */
        /* JADX WARNING: type inference failed for: r0v11 */
        /* JADX WARNING: type inference failed for: r0v12 */
        /* JADX WARNING: type inference failed for: r0v13 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], com.yunos.carkitsdk.TransferInfo, com.yunos.carkitsdk.ConnectionStatusInfo]
          uses: [com.yunos.carkitsdk.ConnectionStatusInfo, com.yunos.carkitsdk.TransferInfo]
          mth insns count: 66
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
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r5 == r0) goto L_0x00b4
                r0 = 0
                switch(r5) {
                    case 1: goto L_0x00a4;
                    case 2: goto L_0x0094;
                    case 3: goto L_0x0084;
                    case 4: goto L_0x0069;
                    case 5: goto L_0x0051;
                    case 6: goto L_0x0036;
                    case 7: goto L_0x001b;
                    case 8: goto L_0x000f;
                    default: goto L_0x000a;
                }
            L_0x000a:
                boolean r5 = super.onTransact(r5, r6, r7, r8)
                return r5
            L_0x000f:
                java.lang.String r5 = "com.yunos.carkitsdk.IServiceStatusCallBack"
                r6.enforceInterface(r5)
                r4.onUnregisteredByOther()
                r7.writeNoException()
                return r1
            L_0x001b:
                java.lang.String r5 = "com.yunos.carkitsdk.IServiceStatusCallBack"
                r6.enforceInterface(r5)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x002f
                android.os.Parcelable$Creator<com.yunos.carkitsdk.TransferInfo> r5 = com.yunos.carkitsdk.TransferInfo.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r0 = r5
                com.yunos.carkitsdk.TransferInfo r0 = (com.yunos.carkitsdk.TransferInfo) r0
            L_0x002f:
                r4.onSendFileNotify(r0)
                r7.writeNoException()
                return r1
            L_0x0036:
                java.lang.String r5 = "com.yunos.carkitsdk.IServiceStatusCallBack"
                r6.enforceInterface(r5)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x004a
                android.os.Parcelable$Creator<com.yunos.carkitsdk.TransferInfo> r5 = com.yunos.carkitsdk.TransferInfo.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r0 = r5
                com.yunos.carkitsdk.TransferInfo r0 = (com.yunos.carkitsdk.TransferInfo) r0
            L_0x004a:
                r4.onReceiveFileNotify(r0)
                r7.writeNoException()
                return r1
            L_0x0051:
                java.lang.String r5 = "com.yunos.carkitsdk.IServiceStatusCallBack"
                r6.enforceInterface(r5)
                long r2 = r6.readLong()
                int r5 = r6.readInt()
                java.lang.String r6 = r6.readString()
                r4.onReceiveMsgNotify(r2, r5, r6)
                r7.writeNoException()
                return r1
            L_0x0069:
                java.lang.String r5 = "com.yunos.carkitsdk.IServiceStatusCallBack"
                r6.enforceInterface(r5)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x007d
                android.os.Parcelable$Creator<com.yunos.carkitsdk.ConnectionStatusInfo> r5 = com.yunos.carkitsdk.ConnectionStatusInfo.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r0 = r5
                com.yunos.carkitsdk.ConnectionStatusInfo r0 = (com.yunos.carkitsdk.ConnectionStatusInfo) r0
            L_0x007d:
                r4.onConnectionStatusNotify(r0)
                r7.writeNoException()
                return r1
            L_0x0084:
                java.lang.String r5 = "com.yunos.carkitsdk.IServiceStatusCallBack"
                r6.enforceInterface(r5)
                java.util.ArrayList r5 = r6.createStringArrayList()
                r4.onRemoteComponents(r5)
                r7.writeNoException()
                return r1
            L_0x0094:
                java.lang.String r5 = "com.yunos.carkitsdk.IServiceStatusCallBack"
                r6.enforceInterface(r5)
                java.util.ArrayList r5 = r6.createStringArrayList()
                r4.onRegisteredComponents(r5)
                r7.writeNoException()
                return r1
            L_0x00a4:
                java.lang.String r5 = "com.yunos.carkitsdk.IServiceStatusCallBack"
                r6.enforceInterface(r5)
                java.util.ArrayList r5 = r6.createStringArrayList()
                r4.onFoundCar(r5)
                r7.writeNoException()
                return r1
            L_0x00b4:
                java.lang.String r5 = "com.yunos.carkitsdk.IServiceStatusCallBack"
                r7.writeString(r5)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yunos.carkitsdk.IServiceStatusCallBack.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void onConnectionStatusNotify(ConnectionStatusInfo connectionStatusInfo) throws RemoteException;

    void onFoundCar(List<String> list) throws RemoteException;

    void onReceiveFileNotify(TransferInfo transferInfo) throws RemoteException;

    void onReceiveMsgNotify(long j, int i, String str) throws RemoteException;

    void onRegisteredComponents(List<String> list) throws RemoteException;

    void onRemoteComponents(List<String> list) throws RemoteException;

    void onSendFileNotify(TransferInfo transferInfo) throws RemoteException;

    void onUnregisteredByOther() throws RemoteException;
}
