package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DefaultProgressEvent implements Parcelable {
    public static final Creator<DefaultProgressEvent> CREATOR = new Creator<DefaultProgressEvent>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new DefaultProgressEvent[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return DefaultProgressEvent.a(parcel);
        }
    };
    int a;
    int b;
    int c;
    public Object d;
    byte[] e;

    public int describeContents() {
        return 0;
    }

    public DefaultProgressEvent() {
    }

    public DefaultProgressEvent(int i, int i2, int i3, byte[] bArr) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.e = bArr;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DefaultProgressEvent [index=");
        sb.append(this.a);
        sb.append(", size=");
        sb.append(this.b);
        sb.append(", total=");
        sb.append(this.c);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeInt(this.e != null ? this.e.length : 0);
        parcel.writeByteArray(this.e);
    }

    public static DefaultProgressEvent a(Parcel parcel) {
        DefaultProgressEvent defaultProgressEvent = new DefaultProgressEvent();
        try {
            defaultProgressEvent.a = parcel.readInt();
            defaultProgressEvent.b = parcel.readInt();
            defaultProgressEvent.c = parcel.readInt();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                byte[] bArr = new byte[readInt];
                parcel.readByteArray(bArr);
                defaultProgressEvent.e = bArr;
            }
        } catch (Exception unused) {
        }
        return defaultProgressEvent;
    }
}
