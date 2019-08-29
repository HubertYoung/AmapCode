package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.List;
import java.util.Map;

public class ParcelableHeader implements Parcelable {
    public static Creator<ParcelableHeader> c = new Creator<ParcelableHeader>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ParcelableHeader[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return ParcelableHeader.a(parcel);
        }
    };
    public int a;
    public Map<String, List<String>> b;

    public int describeContents() {
        return 0;
    }

    public ParcelableHeader(int i, Map<String, List<String>> map) {
        this.b = map;
        this.a = i;
    }

    ParcelableHeader() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.b != null) {
            parcel.writeInt(1);
            parcel.writeMap(this.b);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.a);
    }

    static ParcelableHeader a(Parcel parcel) {
        ParcelableHeader parcelableHeader = new ParcelableHeader();
        try {
            if (parcel.readInt() == 1) {
                parcelableHeader.b = parcel.readHashMap(ParcelableHeader.class.getClassLoader());
            }
            parcelableHeader.a = parcel.readInt();
        } catch (Throwable unused) {
            cl.e("anet.ParcelableHeader", "[readFromParcel]", null, new Object[0]);
        }
        return parcelableHeader;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ParcelableResponseHeader [responseCode=");
        sb.append(this.a);
        sb.append(", header=");
        sb.append(this.b);
        sb.append("]");
        return sb.toString();
    }
}
