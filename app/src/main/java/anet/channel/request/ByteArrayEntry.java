package anet.channel.request;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayEntry implements BodyEntry {
    public static final Creator<ByteArrayEntry> CREATOR = new Creator<ByteArrayEntry>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ByteArrayEntry[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            ByteArrayEntry byteArrayEntry = new ByteArrayEntry(0);
            byteArrayEntry.a = new byte[parcel.readInt()];
            parcel.readByteArray(byteArrayEntry.a);
            byteArrayEntry.b = parcel.readInt();
            byteArrayEntry.c = parcel.readInt();
            return byteArrayEntry;
        }
    };
    /* access modifiers changed from: private */
    public byte[] a;
    /* access modifiers changed from: private */
    public int b;
    /* access modifiers changed from: private */
    public int c;
    private String d;

    public int describeContents() {
        return 0;
    }

    /* synthetic */ ByteArrayEntry(byte b2) {
        this();
    }

    public ByteArrayEntry(byte[] bArr) {
        this(bArr, bArr.length);
    }

    private ByteArrayEntry(byte[] bArr, int i) {
        this.b = 0;
        this.c = 0;
        this.d = null;
        this.a = bArr;
        this.b = 0;
        this.c = i;
    }

    private ByteArrayEntry() {
        this.b = 0;
        this.c = 0;
        this.d = null;
    }

    public final String a() {
        return this.d;
    }

    public final int a(OutputStream outputStream) throws IOException {
        outputStream.write(this.a, this.b, this.c);
        return this.c;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a.length);
        parcel.writeByteArray(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
    }
}
