package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import anet.channel.statist.RequestStatistic;
import anetwork.channel.statist.StatisticData;

public class DefaultFinishEvent implements Parcelable, a {
    public static final Creator<DefaultFinishEvent> CREATOR = new Creator<DefaultFinishEvent>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new DefaultFinishEvent[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return DefaultFinishEvent.a(parcel);
        }
    };
    public Object a;
    int b;
    String c;
    public StatisticData d;
    public final RequestStatistic e;

    public int describeContents() {
        return 0;
    }

    public final int a() {
        return this.b;
    }

    public final String b() {
        return this.c;
    }

    public final StatisticData c() {
        return this.d;
    }

    public DefaultFinishEvent(int i) {
        this(i, null, null);
    }

    public DefaultFinishEvent(int i, String str, RequestStatistic requestStatistic) {
        this.d = new StatisticData();
        this.b = i;
        this.c = str == null ? co.a(i) : str;
        this.e = requestStatistic;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DefaultFinishEvent [");
        sb.append("code=");
        sb.append(this.b);
        sb.append(", desc=");
        sb.append(this.c);
        sb.append(", context=");
        sb.append(this.a);
        sb.append(", statisticData=");
        sb.append(this.d);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.b);
        parcel.writeString(this.c);
        if (this.d != null) {
            parcel.writeSerializable(this.d);
        }
    }

    static DefaultFinishEvent a(Parcel parcel) {
        DefaultFinishEvent defaultFinishEvent = new DefaultFinishEvent(0);
        try {
            defaultFinishEvent.b = parcel.readInt();
            defaultFinishEvent.c = parcel.readString();
            defaultFinishEvent.d = (StatisticData) parcel.readSerializable();
        } catch (Throwable unused) {
        }
        return defaultFinishEvent;
    }
}
