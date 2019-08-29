package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anetwork.channel.aidl.ParcelableBodyHandler.Stub;

public class ParcelableBodyHandlerWrapper extends Stub {
    private static final String TAG = "anet.ParcelableBodyHandlerWrapper";
    private dd handler;

    public ParcelableBodyHandlerWrapper(dd ddVar) {
        this.handler = ddVar;
    }

    public int read(byte[] bArr) throws RemoteException {
        if (this.handler != null) {
            return this.handler.a();
        }
        return 0;
    }

    public boolean isCompleted() throws RemoteException {
        if (this.handler != null) {
            return this.handler.b();
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" handle:");
        sb.append(this.handler);
        return sb.toString();
    }
}
