package anetwork.channel.entity;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import anet.channel.request.BodyEntry;
import anetwork.channel.aidl.ParcelableBodyHandler;
import anetwork.channel.aidl.adapter.ParcelableBodyHandlerWrapper;
import java.io.IOException;
import java.io.OutputStream;

public class BodyHandlerEntry implements BodyEntry {
    public static final Creator<BodyHandlerEntry> CREATOR = new Creator<BodyHandlerEntry>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new BodyHandlerEntry[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            BodyHandlerEntry bodyHandlerEntry = new BodyHandlerEntry(0);
            bodyHandlerEntry.a = ParcelableBodyHandlerWrapper.asInterface(parcel.readStrongBinder());
            return bodyHandlerEntry;
        }
    };
    ParcelableBodyHandler a;

    public final String a() {
        return null;
    }

    public int describeContents() {
        return 0;
    }

    /* synthetic */ BodyHandlerEntry(byte b) {
        this();
    }

    private BodyHandlerEntry() {
        this.a = null;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongInterface(this.a);
    }

    public final int a(OutputStream outputStream) throws IOException {
        try {
            aa a2 = a.a.a(2048);
            int i = 0;
            while (!this.a.isCompleted()) {
                int read = this.a.read(a2.a);
                outputStream.write(a2.a, 0, read);
                i += read;
            }
            a2.a();
            return i;
        } catch (RemoteException e) {
            throw new IOException("RemoteException", e);
        }
    }
}
