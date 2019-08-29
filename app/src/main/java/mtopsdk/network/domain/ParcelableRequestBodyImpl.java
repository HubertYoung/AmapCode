package mtopsdk.network.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.IOException;
import java.io.OutputStream;

public class ParcelableRequestBodyImpl extends fgh implements Parcelable {
    public static final Creator<ParcelableRequestBodyImpl> CREATOR = new Creator<ParcelableRequestBodyImpl>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ParcelableRequestBodyImpl[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ParcelableRequestBodyImpl(parcel);
        }
    };
    public String a;
    private byte[] b;

    public int describeContents() {
        return 0;
    }

    public ParcelableRequestBodyImpl(String str, byte[] bArr) {
        this.b = bArr;
        this.a = str;
    }

    public final String a() {
        return this.a;
    }

    public final long b() {
        return this.b != null ? (long) this.b.length : super.b();
    }

    public final void a(OutputStream outputStream) throws IOException {
        outputStream.write(this.b);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeByteArray(this.b);
    }

    protected ParcelableRequestBodyImpl(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.createByteArray();
    }
}
