package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import anetwork.channel.statist.StatisticData;
import java.util.List;
import java.util.Map;

public class NetworkResponse implements Parcelable, dk {
    public static final Creator<NetworkResponse> CREATOR = new Creator<NetworkResponse>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NetworkResponse[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return NetworkResponse.a(parcel);
        }
    };
    int a;
    public String b;
    public byte[] c;
    public Map<String, List<String>> d;
    public StatisticData e;
    private Throwable f;

    public int describeContents() {
        return 0;
    }

    public final void a(int i) {
        this.a = i;
        this.b = co.a(i);
    }

    public final byte[] b() {
        return this.c;
    }

    public final Map<String, List<String>> c() {
        return this.d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NetworkResponse [");
        sb.append("statusCode=");
        sb.append(this.a);
        sb.append(", desc=");
        sb.append(this.b);
        sb.append(", connHeadFields=");
        sb.append(this.d);
        sb.append(", bytedata=");
        sb.append(this.c != null ? new String(this.c) : "");
        sb.append(", error=");
        sb.append(this.f);
        sb.append(", statisticData=");
        sb.append(this.e);
        sb.append("]");
        return sb.toString();
    }

    public NetworkResponse() {
    }

    public NetworkResponse(int i) {
        this.a = i;
        this.b = co.a(i);
    }

    public final int a() {
        return this.a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeString(this.b);
        int length = this.c != null ? this.c.length : 0;
        parcel.writeInt(length);
        if (length > 0) {
            parcel.writeByteArray(this.c);
        }
        parcel.writeMap(this.d);
        if (this.e != null) {
            parcel.writeSerializable(this.e);
        }
    }

    public static NetworkResponse a(Parcel parcel) {
        NetworkResponse networkResponse = new NetworkResponse();
        try {
            networkResponse.a = parcel.readInt();
            networkResponse.b = parcel.readString();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                networkResponse.c = new byte[readInt];
                parcel.readByteArray(networkResponse.c);
            }
            networkResponse.d = parcel.readHashMap(NetworkResponse.class.getClassLoader());
            try {
                networkResponse.e = (StatisticData) parcel.readSerializable();
            } catch (Throwable unused) {
                cl.b("anet.NetworkResponse", "[readFromParcel] source.readSerializable() error", null, new Object[0]);
            }
        } catch (Exception e2) {
            cl.a("anet.NetworkResponse", "[readFromParcel]", null, e2, new Object[0]);
        }
        return networkResponse;
    }

    public final StatisticData d() {
        return this.e;
    }
}
