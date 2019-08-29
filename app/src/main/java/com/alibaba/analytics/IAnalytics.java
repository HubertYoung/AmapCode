package com.alibaba.analytics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.alibaba.mtl.appmonitor.Transaction;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import java.util.Map;

public interface IAnalytics extends IInterface {

    public static abstract class Stub extends Binder implements IAnalytics {
        private static final String DESCRIPTOR = "com.alibaba.analytics.IAnalytics";
        static final int TRANSACTION_alarm_checkSampled = 39;
        static final int TRANSACTION_alarm_commitFail1 = 42;
        static final int TRANSACTION_alarm_commitFail2 = 43;
        static final int TRANSACTION_alarm_commitSuccess1 = 40;
        static final int TRANSACTION_alarm_commitSuccess2 = 41;
        static final int TRANSACTION_alarm_enableOfflineAgg = 54;
        static final int TRANSACTION_alarm_setSampling = 38;
        static final int TRANSACTION_alarm_setStatisticsInterval = 37;
        static final int TRANSACTION_counter_checkSampled = 30;
        static final int TRANSACTION_counter_commit1 = 31;
        static final int TRANSACTION_counter_commit2 = 32;
        static final int TRANSACTION_counter_setSampling = 29;
        static final int TRANSACTION_counter_setStatisticsInterval = 28;
        static final int TRANSACTION_destroy = 18;
        static final int TRANSACTION_disableNetworkStatusChecker = 9;
        static final int TRANSACTION_dispatchLocalHits = 10;
        static final int TRANSACTION_enableLog = 14;
        static final int TRANSACTION_getValue = 12;
        static final int TRANSACTION_init = 13;
        static final int TRANSACTION_initUT = 1;
        static final int TRANSACTION_offlinecounter_checkSampled = 35;
        static final int TRANSACTION_offlinecounter_commit = 36;
        static final int TRANSACTION_offlinecounter_setSampling = 34;
        static final int TRANSACTION_offlinecounter_setStatisticsInterval = 33;
        static final int TRANSACTION_register1 = 23;
        static final int TRANSACTION_register2 = 24;
        static final int TRANSACTION_register3 = 25;
        static final int TRANSACTION_register4 = 26;
        static final int TRANSACTION_removeGlobalProperty = 57;
        static final int TRANSACTION_saveCacheDataToLocal = 11;
        static final int TRANSACTION_selfCheck = 55;
        static final int TRANSACTION_setAppVersion = 3;
        static final int TRANSACTION_setChannel = 4;
        static final int TRANSACTION_setGlobalProperty = 56;
        static final int TRANSACTION_setRequestAuthInfo = 15;
        static final int TRANSACTION_setSampling = 20;
        static final int TRANSACTION_setSessionProperties = 6;
        static final int TRANSACTION_setStatisticsInterval1 = 21;
        static final int TRANSACTION_setStatisticsInterval2 = 22;
        static final int TRANSACTION_stat_begin = 44;
        static final int TRANSACTION_stat_checkSampled = 48;
        static final int TRANSACTION_stat_commit1 = 49;
        static final int TRANSACTION_stat_commit2 = 50;
        static final int TRANSACTION_stat_commit3 = 51;
        static final int TRANSACTION_stat_end = 45;
        static final int TRANSACTION_stat_setSampling = 47;
        static final int TRANSACTION_stat_setStatisticsInterval = 46;
        static final int TRANSACTION_transaction_begin = 52;
        static final int TRANSACTION_transaction_end = 53;
        static final int TRANSACTION_transferLog = 8;
        static final int TRANSACTION_triggerUpload = 19;
        static final int TRANSACTION_turnOffRealTimeDebug = 17;
        static final int TRANSACTION_turnOnDebug = 7;
        static final int TRANSACTION_turnOnRealTimeDebug = 16;
        static final int TRANSACTION_updateMeasure = 27;
        static final int TRANSACTION_updateSessionProperties = 5;
        static final int TRANSACTION_updateUserAccount = 2;

        static class Proxy implements IAnalytics {
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

            public void initUT() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void updateUserAccount(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setAppVersion(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setChannel(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void updateSessionProperties(Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeMap(map);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setSessionProperties(Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeMap(map);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void turnOnDebug() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void transferLog(Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeMap(map);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void dispatchLocalHits() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void saveCacheDataToLocal() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getValue(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void init() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void enableLog(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String selfCheck(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setRequestAuthInfo(boolean z, boolean z2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeInt(z2 ? 1 : 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void turnOnRealTimeDebug(Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeMap(map);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void turnOffRealTimeDebug() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void destroy() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void triggerUpload() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setStatisticsInterval1(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setStatisticsInterval2(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void register1(String str, String str2, MeasureSet measureSet) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (measureSet != null) {
                        obtain.writeInt(1);
                        measureSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void register2(String str, String str2, MeasureSet measureSet, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (measureSet != null) {
                        obtain.writeInt(1);
                        measureSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void register3(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (measureSet != null) {
                        obtain.writeInt(1);
                        measureSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (dimensionSet != null) {
                        obtain.writeInt(1);
                        dimensionSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void register4(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (measureSet != null) {
                        obtain.writeInt(1);
                        measureSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (dimensionSet != null) {
                        obtain.writeInt(1);
                        dimensionSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void updateMeasure(String str, String str2, String str3, double d, double d2, double d3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeDouble(d);
                    obtain.writeDouble(d2);
                    obtain.writeDouble(d3);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void counter_setStatisticsInterval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void counter_setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean counter_checkSampled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    boolean z = false;
                    this.mRemote.transact(30, obtain, obtain2, 0);
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

            public void counter_commit1(String str, String str2, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeDouble(d);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void counter_commit2(String str, String str2, String str3, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeDouble(d);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void offlinecounter_setStatisticsInterval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void offlinecounter_setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean offlinecounter_checkSampled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    boolean z = false;
                    this.mRemote.transact(35, obtain, obtain2, 0);
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

            public void offlinecounter_commit(String str, String str2, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeDouble(d);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void alarm_setStatisticsInterval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void alarm_setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean alarm_checkSampled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    boolean z = false;
                    this.mRemote.transact(39, obtain, obtain2, 0);
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

            public void alarm_commitSuccess1(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void alarm_commitSuccess2(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void alarm_commitFail1(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void alarm_commitFail2(String str, String str2, String str3, String str4, String str5) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void stat_begin(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void stat_end(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void stat_setStatisticsInterval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void stat_setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean stat_checkSampled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    boolean z = false;
                    this.mRemote.transact(48, obtain, obtain2, 0);
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

            public void stat_commit1(String str, String str2, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeDouble(d);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void stat_commit2(String str, String str2, DimensionValueSet dimensionValueSet, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (dimensionValueSet != null) {
                        obtain.writeInt(1);
                        dimensionValueSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeDouble(d);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void stat_commit3(String str, String str2, DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (dimensionValueSet != null) {
                        obtain.writeInt(1);
                        dimensionValueSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (measureValueSet != null) {
                        obtain.writeInt(1);
                        measureValueSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void transaction_begin(Transaction transaction, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (transaction != null) {
                        obtain.writeInt(1);
                        transaction.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void transaction_end(Transaction transaction, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (transaction != null) {
                        obtain.writeInt(1);
                        transaction.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setGlobalProperty(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeGlobalProperty(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
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

        public static IAnalytics asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAnalytics)) {
                return new Proxy(iBinder);
            }
            return (IAnalytics) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v3, types: [com.alibaba.mtl.appmonitor.model.MeasureSet] */
        /* JADX WARNING: type inference failed for: r3v6, types: [com.alibaba.mtl.appmonitor.model.MeasureSet] */
        /* JADX WARNING: type inference failed for: r3v7, types: [com.alibaba.mtl.appmonitor.model.MeasureSet] */
        /* JADX WARNING: type inference failed for: r3v10, types: [com.alibaba.mtl.appmonitor.model.MeasureSet] */
        /* JADX WARNING: type inference failed for: r3v11, types: [com.alibaba.mtl.appmonitor.model.DimensionSet] */
        /* JADX WARNING: type inference failed for: r3v14, types: [com.alibaba.mtl.appmonitor.model.DimensionSet] */
        /* JADX WARNING: type inference failed for: r3v26, types: [com.alibaba.mtl.appmonitor.model.DimensionValueSet] */
        /* JADX WARNING: type inference failed for: r0v120, types: [com.alibaba.mtl.appmonitor.model.DimensionValueSet] */
        /* JADX WARNING: type inference failed for: r3v27 */
        /* JADX WARNING: type inference failed for: r3v28, types: [com.alibaba.mtl.appmonitor.model.MeasureValueSet] */
        /* JADX WARNING: type inference failed for: r3v31, types: [com.alibaba.mtl.appmonitor.model.MeasureValueSet] */
        /* JADX WARNING: type inference failed for: r3v32, types: [com.alibaba.mtl.appmonitor.Transaction] */
        /* JADX WARNING: type inference failed for: r3v34, types: [com.alibaba.mtl.appmonitor.Transaction] */
        /* JADX WARNING: type inference failed for: r3v35, types: [com.alibaba.mtl.appmonitor.Transaction] */
        /* JADX WARNING: type inference failed for: r3v37, types: [com.alibaba.mtl.appmonitor.Transaction] */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v40 */
        /* JADX WARNING: type inference failed for: r3v41 */
        /* JADX WARNING: type inference failed for: r3v42 */
        /* JADX WARNING: type inference failed for: r3v43 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v0
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], com.alibaba.mtl.appmonitor.model.MeasureSet, com.alibaba.mtl.appmonitor.model.DimensionSet, ?[OBJECT, ARRAY], com.alibaba.mtl.appmonitor.model.MeasureValueSet, com.alibaba.mtl.appmonitor.Transaction]
          uses: [com.alibaba.mtl.appmonitor.model.MeasureSet, com.alibaba.mtl.appmonitor.model.DimensionSet, com.alibaba.mtl.appmonitor.model.DimensionValueSet, com.alibaba.mtl.appmonitor.model.MeasureValueSet, com.alibaba.mtl.appmonitor.Transaction]
          mth insns count: 456
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
        /* JADX WARNING: Unknown variable types count: 8 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r10 = r15
                r0 = r16
                r1 = r17
                r11 = r18
                r2 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r0 == r2) goto L_0x053e
                switch(r0) {
                    case 1: goto L_0x0532;
                    case 2: goto L_0x051a;
                    case 3: goto L_0x050a;
                    case 4: goto L_0x04fa;
                    case 5: goto L_0x04e2;
                    case 6: goto L_0x04ca;
                    case 7: goto L_0x04be;
                    case 8: goto L_0x04a6;
                    default: goto L_0x0010;
                }
            L_0x0010:
                r2 = 0
                r3 = 0
                switch(r0) {
                    case 10: goto L_0x049a;
                    case 11: goto L_0x048e;
                    case 12: goto L_0x047a;
                    case 13: goto L_0x046e;
                    case 14: goto L_0x045b;
                    case 15: goto L_0x0437;
                    case 16: goto L_0x041f;
                    case 17: goto L_0x0413;
                    case 18: goto L_0x0407;
                    case 19: goto L_0x03fb;
                    case 20: goto L_0x03eb;
                    case 21: goto L_0x03db;
                    case 22: goto L_0x03c7;
                    case 23: goto L_0x03a4;
                    case 24: goto L_0x037b;
                    case 25: goto L_0x0348;
                    case 26: goto L_0x0303;
                    case 27: goto L_0x02d8;
                    case 28: goto L_0x02c8;
                    case 29: goto L_0x02b8;
                    case 30: goto L_0x02a0;
                    case 31: goto L_0x0288;
                    case 32: goto L_0x0267;
                    case 33: goto L_0x0257;
                    case 34: goto L_0x0247;
                    case 35: goto L_0x022f;
                    case 36: goto L_0x0217;
                    case 37: goto L_0x0207;
                    case 38: goto L_0x01f7;
                    case 39: goto L_0x01df;
                    case 40: goto L_0x01cb;
                    case 41: goto L_0x01b3;
                    case 42: goto L_0x0197;
                    case 43: goto L_0x0171;
                    case 44: goto L_0x0159;
                    case 45: goto L_0x0141;
                    case 46: goto L_0x0131;
                    case 47: goto L_0x0121;
                    case 48: goto L_0x0109;
                    case 49: goto L_0x00f1;
                    case 50: goto L_0x00c6;
                    case 51: goto L_0x0093;
                    case 52: goto L_0x0074;
                    case 53: goto L_0x0055;
                    default: goto L_0x0015;
                }
            L_0x0015:
                switch(r0) {
                    case 55: goto L_0x0041;
                    case 56: goto L_0x002d;
                    case 57: goto L_0x001d;
                    default: goto L_0x0018;
                }
            L_0x0018:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x001d:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                r10.removeGlobalProperty(r0)
                r18.writeNoException()
                return r12
            L_0x002d:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r1 = r17.readString()
                r10.setGlobalProperty(r0, r1)
                r18.writeNoException()
                return r12
            L_0x0041:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r0 = r10.selfCheck(r0)
                r18.writeNoException()
                r11.writeString(r0)
                return r12
            L_0x0055:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0069
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.Transaction> r0 = com.alibaba.mtl.appmonitor.Transaction.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r1)
                r3 = r0
                com.alibaba.mtl.appmonitor.Transaction r3 = (com.alibaba.mtl.appmonitor.Transaction) r3
            L_0x0069:
                java.lang.String r0 = r17.readString()
                r10.transaction_end(r3, r0)
                r18.writeNoException()
                return r12
            L_0x0074:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0088
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.Transaction> r0 = com.alibaba.mtl.appmonitor.Transaction.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r1)
                r3 = r0
                com.alibaba.mtl.appmonitor.Transaction r3 = (com.alibaba.mtl.appmonitor.Transaction) r3
            L_0x0088:
                java.lang.String r0 = r17.readString()
                r10.transaction_begin(r3, r0)
                r18.writeNoException()
                return r12
            L_0x0093:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x00af
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.model.DimensionValueSet> r4 = com.alibaba.mtl.appmonitor.model.DimensionValueSet.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r1)
                com.alibaba.mtl.appmonitor.model.DimensionValueSet r4 = (com.alibaba.mtl.appmonitor.model.DimensionValueSet) r4
                goto L_0x00b0
            L_0x00af:
                r4 = r3
            L_0x00b0:
                int r5 = r17.readInt()
                if (r5 == 0) goto L_0x00bf
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.model.MeasureValueSet> r3 = com.alibaba.mtl.appmonitor.model.MeasureValueSet.CREATOR
                java.lang.Object r1 = r3.createFromParcel(r1)
                r3 = r1
                com.alibaba.mtl.appmonitor.model.MeasureValueSet r3 = (com.alibaba.mtl.appmonitor.model.MeasureValueSet) r3
            L_0x00bf:
                r10.stat_commit3(r0, r2, r4, r3)
                r18.writeNoException()
                return r12
            L_0x00c6:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r2 = r17.readString()
                java.lang.String r4 = r17.readString()
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x00e2
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.model.DimensionValueSet> r0 = com.alibaba.mtl.appmonitor.model.DimensionValueSet.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r1)
                com.alibaba.mtl.appmonitor.model.DimensionValueSet r0 = (com.alibaba.mtl.appmonitor.model.DimensionValueSet) r0
                r3 = r0
            L_0x00e2:
                double r5 = r17.readDouble()
                r0 = r10
                r1 = r2
                r2 = r4
                r4 = r5
                r0.stat_commit2(r1, r2, r3, r4)
                r18.writeNoException()
                return r12
            L_0x00f1:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                double r3 = r17.readDouble()
                r10.stat_commit1(r0, r2, r3)
                r18.writeNoException()
                return r12
            L_0x0109:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r1 = r17.readString()
                boolean r0 = r10.stat_checkSampled(r0, r1)
                r18.writeNoException()
                r11.writeInt(r0)
                return r12
            L_0x0121:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.stat_setSampling(r0)
                r18.writeNoException()
                return r12
            L_0x0131:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.stat_setStatisticsInterval(r0)
                r18.writeNoException()
                return r12
            L_0x0141:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                java.lang.String r1 = r17.readString()
                r10.stat_end(r0, r2, r1)
                r18.writeNoException()
                return r12
            L_0x0159:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                java.lang.String r1 = r17.readString()
                r10.stat_begin(r0, r2, r1)
                r18.writeNoException()
                return r12
            L_0x0171:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r2 = r17.readString()
                java.lang.String r3 = r17.readString()
                java.lang.String r4 = r17.readString()
                java.lang.String r5 = r17.readString()
                java.lang.String r6 = r17.readString()
                r0 = r10
                r1 = r2
                r2 = r3
                r3 = r4
                r4 = r5
                r5 = r6
                r0.alarm_commitFail2(r1, r2, r3, r4, r5)
                r18.writeNoException()
                return r12
            L_0x0197:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                java.lang.String r3 = r17.readString()
                java.lang.String r1 = r17.readString()
                r10.alarm_commitFail1(r0, r2, r3, r1)
                r18.writeNoException()
                return r12
            L_0x01b3:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                java.lang.String r1 = r17.readString()
                r10.alarm_commitSuccess2(r0, r2, r1)
                r18.writeNoException()
                return r12
            L_0x01cb:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r1 = r17.readString()
                r10.alarm_commitSuccess1(r0, r1)
                r18.writeNoException()
                return r12
            L_0x01df:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r1 = r17.readString()
                boolean r0 = r10.alarm_checkSampled(r0, r1)
                r18.writeNoException()
                r11.writeInt(r0)
                return r12
            L_0x01f7:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.alarm_setSampling(r0)
                r18.writeNoException()
                return r12
            L_0x0207:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.alarm_setStatisticsInterval(r0)
                r18.writeNoException()
                return r12
            L_0x0217:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                double r3 = r17.readDouble()
                r10.offlinecounter_commit(r0, r2, r3)
                r18.writeNoException()
                return r12
            L_0x022f:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r1 = r17.readString()
                boolean r0 = r10.offlinecounter_checkSampled(r0, r1)
                r18.writeNoException()
                r11.writeInt(r0)
                return r12
            L_0x0247:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.offlinecounter_setSampling(r0)
                r18.writeNoException()
                return r12
            L_0x0257:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.offlinecounter_setStatisticsInterval(r0)
                r18.writeNoException()
                return r12
            L_0x0267:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r2 = r17.readString()
                java.lang.String r3 = r17.readString()
                java.lang.String r4 = r17.readString()
                double r5 = r17.readDouble()
                r0 = r10
                r1 = r2
                r2 = r3
                r3 = r4
                r4 = r5
                r0.counter_commit2(r1, r2, r3, r4)
                r18.writeNoException()
                return r12
            L_0x0288:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                double r3 = r17.readDouble()
                r10.counter_commit1(r0, r2, r3)
                r18.writeNoException()
                return r12
            L_0x02a0:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r1 = r17.readString()
                boolean r0 = r10.counter_checkSampled(r0, r1)
                r18.writeNoException()
                r11.writeInt(r0)
                return r12
            L_0x02b8:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.counter_setSampling(r0)
                r18.writeNoException()
                return r12
            L_0x02c8:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.counter_setStatisticsInterval(r0)
                r18.writeNoException()
                return r12
            L_0x02d8:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r2 = r17.readString()
                java.lang.String r3 = r17.readString()
                java.lang.String r4 = r17.readString()
                double r5 = r17.readDouble()
                double r7 = r17.readDouble()
                double r13 = r17.readDouble()
                r0 = r10
                r1 = r2
                r2 = r3
                r3 = r4
                r4 = r5
                r6 = r7
                r8 = r13
                r0.updateMeasure(r1, r2, r3, r4, r6, r8)
                r18.writeNoException()
                return r12
            L_0x0303:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r4 = r17.readString()
                java.lang.String r5 = r17.readString()
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0320
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.model.MeasureSet> r0 = com.alibaba.mtl.appmonitor.model.MeasureSet.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r1)
                com.alibaba.mtl.appmonitor.model.MeasureSet r0 = (com.alibaba.mtl.appmonitor.model.MeasureSet) r0
                r6 = r0
                goto L_0x0321
            L_0x0320:
                r6 = r3
            L_0x0321:
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0331
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.model.DimensionSet> r0 = com.alibaba.mtl.appmonitor.model.DimensionSet.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r1)
                com.alibaba.mtl.appmonitor.model.DimensionSet r0 = (com.alibaba.mtl.appmonitor.model.DimensionSet) r0
                r7 = r0
                goto L_0x0332
            L_0x0331:
                r7 = r3
            L_0x0332:
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x033a
                r8 = 1
                goto L_0x033b
            L_0x033a:
                r8 = 0
            L_0x033b:
                r0 = r10
                r1 = r4
                r2 = r5
                r3 = r6
                r4 = r7
                r5 = r8
                r0.register4(r1, r2, r3, r4, r5)
                r18.writeNoException()
                return r12
            L_0x0348:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x0364
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.model.MeasureSet> r4 = com.alibaba.mtl.appmonitor.model.MeasureSet.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r1)
                com.alibaba.mtl.appmonitor.model.MeasureSet r4 = (com.alibaba.mtl.appmonitor.model.MeasureSet) r4
                goto L_0x0365
            L_0x0364:
                r4 = r3
            L_0x0365:
                int r5 = r17.readInt()
                if (r5 == 0) goto L_0x0374
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.model.DimensionSet> r3 = com.alibaba.mtl.appmonitor.model.DimensionSet.CREATOR
                java.lang.Object r1 = r3.createFromParcel(r1)
                r3 = r1
                com.alibaba.mtl.appmonitor.model.DimensionSet r3 = (com.alibaba.mtl.appmonitor.model.DimensionSet) r3
            L_0x0374:
                r10.register3(r0, r2, r4, r3)
                r18.writeNoException()
                return r12
            L_0x037b:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r4 = r17.readString()
                int r5 = r17.readInt()
                if (r5 == 0) goto L_0x0396
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.model.MeasureSet> r3 = com.alibaba.mtl.appmonitor.model.MeasureSet.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r1)
                com.alibaba.mtl.appmonitor.model.MeasureSet r3 = (com.alibaba.mtl.appmonitor.model.MeasureSet) r3
            L_0x0396:
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x039d
                r2 = 1
            L_0x039d:
                r10.register2(r0, r4, r3, r2)
                r18.writeNoException()
                return r12
            L_0x03a4:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x03c0
                android.os.Parcelable$Creator<com.alibaba.mtl.appmonitor.model.MeasureSet> r3 = com.alibaba.mtl.appmonitor.model.MeasureSet.CREATOR
                java.lang.Object r1 = r3.createFromParcel(r1)
                r3 = r1
                com.alibaba.mtl.appmonitor.model.MeasureSet r3 = (com.alibaba.mtl.appmonitor.model.MeasureSet) r3
            L_0x03c0:
                r10.register1(r0, r2, r3)
                r18.writeNoException()
                return r12
            L_0x03c7:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                int r1 = r17.readInt()
                r10.setStatisticsInterval2(r0, r1)
                r18.writeNoException()
                return r12
            L_0x03db:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.setStatisticsInterval1(r0)
                r18.writeNoException()
                return r12
            L_0x03eb:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                r10.setSampling(r0)
                r18.writeNoException()
                return r12
            L_0x03fb:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                r10.triggerUpload()
                r18.writeNoException()
                return r12
            L_0x0407:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                r10.destroy()
                r18.writeNoException()
                return r12
            L_0x0413:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                r10.turnOffRealTimeDebug()
                r18.writeNoException()
                return r12
            L_0x041f:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.Class r0 = r10.getClass()
                java.lang.ClassLoader r0 = r0.getClassLoader()
                java.util.HashMap r0 = r1.readHashMap(r0)
                r10.turnOnRealTimeDebug(r0)
                r18.writeNoException()
                return r12
            L_0x0437:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0444
                r0 = 1
                goto L_0x0445
            L_0x0444:
                r0 = 0
            L_0x0445:
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x044c
                r2 = 1
            L_0x044c:
                java.lang.String r3 = r17.readString()
                java.lang.String r1 = r17.readString()
                r10.setRequestAuthInfo(r0, r2, r3, r1)
                r18.writeNoException()
                return r12
            L_0x045b:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0467
                r2 = 1
            L_0x0467:
                r10.enableLog(r2)
                r18.writeNoException()
                return r12
            L_0x046e:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                r10.init()
                r18.writeNoException()
                return r12
            L_0x047a:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r0 = r10.getValue(r0)
                r18.writeNoException()
                r11.writeString(r0)
                return r12
            L_0x048e:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                r10.saveCacheDataToLocal()
                r18.writeNoException()
                return r12
            L_0x049a:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                r10.dispatchLocalHits()
                r18.writeNoException()
                return r12
            L_0x04a6:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.Class r0 = r10.getClass()
                java.lang.ClassLoader r0 = r0.getClassLoader()
                java.util.HashMap r0 = r1.readHashMap(r0)
                r10.transferLog(r0)
                r18.writeNoException()
                return r12
            L_0x04be:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                r10.turnOnDebug()
                r18.writeNoException()
                return r12
            L_0x04ca:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.Class r0 = r10.getClass()
                java.lang.ClassLoader r0 = r0.getClassLoader()
                java.util.HashMap r0 = r1.readHashMap(r0)
                r10.setSessionProperties(r0)
                r18.writeNoException()
                return r12
            L_0x04e2:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.Class r0 = r10.getClass()
                java.lang.ClassLoader r0 = r0.getClassLoader()
                java.util.HashMap r0 = r1.readHashMap(r0)
                r10.updateSessionProperties(r0)
                r18.writeNoException()
                return r12
            L_0x04fa:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                r10.setChannel(r0)
                r18.writeNoException()
                return r12
            L_0x050a:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                r10.setAppVersion(r0)
                r18.writeNoException()
                return r12
            L_0x051a:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                java.lang.String r0 = r17.readString()
                java.lang.String r2 = r17.readString()
                java.lang.String r1 = r17.readString()
                r10.updateUserAccount(r0, r2, r1)
                r18.writeNoException()
                return r12
            L_0x0532:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r1.enforceInterface(r0)
                r10.initUT()
                r18.writeNoException()
                return r12
            L_0x053e:
                java.lang.String r0 = "com.alibaba.analytics.IAnalytics"
                r11.writeString(r0)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.IAnalytics.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    boolean alarm_checkSampled(String str, String str2) throws RemoteException;

    void alarm_commitFail1(String str, String str2, String str3, String str4) throws RemoteException;

    void alarm_commitFail2(String str, String str2, String str3, String str4, String str5) throws RemoteException;

    void alarm_commitSuccess1(String str, String str2) throws RemoteException;

    void alarm_commitSuccess2(String str, String str2, String str3) throws RemoteException;

    void alarm_setSampling(int i) throws RemoteException;

    void alarm_setStatisticsInterval(int i) throws RemoteException;

    boolean counter_checkSampled(String str, String str2) throws RemoteException;

    void counter_commit1(String str, String str2, double d) throws RemoteException;

    void counter_commit2(String str, String str2, String str3, double d) throws RemoteException;

    void counter_setSampling(int i) throws RemoteException;

    void counter_setStatisticsInterval(int i) throws RemoteException;

    void destroy() throws RemoteException;

    void dispatchLocalHits() throws RemoteException;

    void enableLog(boolean z) throws RemoteException;

    String getValue(String str) throws RemoteException;

    void init() throws RemoteException;

    void initUT() throws RemoteException;

    boolean offlinecounter_checkSampled(String str, String str2) throws RemoteException;

    void offlinecounter_commit(String str, String str2, double d) throws RemoteException;

    void offlinecounter_setSampling(int i) throws RemoteException;

    void offlinecounter_setStatisticsInterval(int i) throws RemoteException;

    void register1(String str, String str2, MeasureSet measureSet) throws RemoteException;

    void register2(String str, String str2, MeasureSet measureSet, boolean z) throws RemoteException;

    void register3(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet) throws RemoteException;

    void register4(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) throws RemoteException;

    void removeGlobalProperty(String str) throws RemoteException;

    void saveCacheDataToLocal() throws RemoteException;

    String selfCheck(String str) throws RemoteException;

    void setAppVersion(String str) throws RemoteException;

    void setChannel(String str) throws RemoteException;

    void setGlobalProperty(String str, String str2) throws RemoteException;

    void setRequestAuthInfo(boolean z, boolean z2, String str, String str2) throws RemoteException;

    void setSampling(int i) throws RemoteException;

    void setSessionProperties(Map map) throws RemoteException;

    void setStatisticsInterval1(int i) throws RemoteException;

    void setStatisticsInterval2(int i, int i2) throws RemoteException;

    void stat_begin(String str, String str2, String str3) throws RemoteException;

    boolean stat_checkSampled(String str, String str2) throws RemoteException;

    void stat_commit1(String str, String str2, double d) throws RemoteException;

    void stat_commit2(String str, String str2, DimensionValueSet dimensionValueSet, double d) throws RemoteException;

    void stat_commit3(String str, String str2, DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet) throws RemoteException;

    void stat_end(String str, String str2, String str3) throws RemoteException;

    void stat_setSampling(int i) throws RemoteException;

    void stat_setStatisticsInterval(int i) throws RemoteException;

    void transaction_begin(Transaction transaction, String str) throws RemoteException;

    void transaction_end(Transaction transaction, String str) throws RemoteException;

    void transferLog(Map map) throws RemoteException;

    void triggerUpload() throws RemoteException;

    void turnOffRealTimeDebug() throws RemoteException;

    void turnOnDebug() throws RemoteException;

    void turnOnRealTimeDebug(Map map) throws RemoteException;

    void updateMeasure(String str, String str2, String str3, double d, double d2, double d3) throws RemoteException;

    void updateSessionProperties(Map map) throws RemoteException;

    void updateUserAccount(String str, String str2, String str3) throws RemoteException;
}
