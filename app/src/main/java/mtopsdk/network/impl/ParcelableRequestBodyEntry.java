package mtopsdk.network.impl;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import anet.channel.request.BodyEntry;
import java.io.IOException;
import java.io.OutputStream;
import mtopsdk.network.domain.ParcelableRequestBodyImpl;

public class ParcelableRequestBodyEntry implements BodyEntry {
    public static final Creator<ParcelableRequestBodyEntry> CREATOR = new Creator<ParcelableRequestBodyEntry>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ParcelableRequestBodyEntry[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ParcelableRequestBodyEntry(parcel);
        }
    };
    ParcelableRequestBodyImpl a;

    public int describeContents() {
        return 0;
    }

    public ParcelableRequestBodyEntry(ParcelableRequestBodyImpl parcelableRequestBodyImpl) {
        this.a = parcelableRequestBodyImpl;
    }

    public final String a() {
        return this.a.a;
    }

    public final int a(OutputStream outputStream) throws IOException {
        this.a.a(outputStream);
        return (int) this.a.b();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.a, i);
    }

    protected ParcelableRequestBodyEntry(Parcel parcel) {
        this.a = (ParcelableRequestBodyImpl) parcel.readParcelable(ParcelableRequestBodyImpl.class.getClassLoader());
    }
}
