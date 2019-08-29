package com.alibaba.mtl.appmonitor;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.alibaba.analytics.AnalyticsMgr;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import java.util.UUID;

public class Transaction implements Parcelable {
    public static Creator<Transaction> CREATOR = new Creator<Transaction>() {
        public final Transaction createFromParcel(Parcel parcel) {
            return Transaction.readFromParcel(parcel);
        }

        public final Transaction[] newArray(int i) {
            return new Transaction[i];
        }
    };
    public DimensionValueSet dimensionValues;
    public Integer eventId;
    private Object lock;
    public String module;
    public String monitorPoint;
    public String transactionId;

    public int describeContents() {
        return 0;
    }

    public Transaction(Integer num, String str, String str2, DimensionValueSet dimensionValueSet) {
        this.eventId = num;
        this.module = str;
        this.monitorPoint = str2;
        this.transactionId = UUID.randomUUID().toString();
        this.dimensionValues = dimensionValueSet;
        this.lock = new Object();
    }

    public Transaction() {
    }

    public void begin(String str) {
        Logger.d();
        if (AnalyticsMgr.iAnalytics != null) {
            try {
                AnalyticsMgr.iAnalytics.transaction_begin(this, str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void end(String str) {
        Logger.d();
        if (AnalyticsMgr.iAnalytics != null) {
            try {
                AnalyticsMgr.iAnalytics.transaction_end(this, str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void addDimensionValues(DimensionValueSet dimensionValueSet) {
        synchronized (this.lock) {
            if (this.dimensionValues == null) {
                this.dimensionValues = dimensionValueSet;
            } else {
                this.dimensionValues.addValues(dimensionValueSet);
            }
        }
    }

    public void addDimensionValues(String str, String str2) {
        synchronized (this.lock) {
            if (this.dimensionValues == null) {
                this.dimensionValues = (DimensionValueSet) BalancedPool.getInstance().poll(DimensionValueSet.class, new Object[0]);
            }
            this.dimensionValues.setValue(str, str2);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.dimensionValues, i);
        parcel.writeInt(this.eventId.intValue());
        parcel.writeString(this.module);
        parcel.writeString(this.monitorPoint);
        parcel.writeString(this.transactionId);
    }

    static Transaction readFromParcel(Parcel parcel) {
        Transaction transaction = new Transaction();
        try {
            transaction.dimensionValues = (DimensionValueSet) parcel.readParcelable(Transaction.class.getClassLoader());
            transaction.eventId = Integer.valueOf(parcel.readInt());
            transaction.module = parcel.readString();
            transaction.monitorPoint = parcel.readString();
            transaction.transactionId = parcel.readString();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return transaction;
    }
}
