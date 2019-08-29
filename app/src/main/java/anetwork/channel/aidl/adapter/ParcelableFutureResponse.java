package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableFuture.Stub;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ParcelableFutureResponse extends Stub {
    private static final String TAG = "anet.ParcelableFutureResponse";
    Future<dk> future;
    NetworkResponse response;

    public ParcelableFutureResponse(Future<dk> future2) {
        this.future = future2;
    }

    public boolean cancel(boolean z) throws RemoteException {
        if (this.future == null) {
            return true;
        }
        return this.future.cancel(z);
    }

    public boolean isCancelled() throws RemoteException {
        if (this.future == null) {
            return true;
        }
        return this.future.isCancelled();
    }

    public boolean isDone() throws RemoteException {
        if (this.future == null) {
            return true;
        }
        return this.future.isDone();
    }

    public NetworkResponse get(long j) throws RemoteException {
        if (this.future != null) {
            try {
                return (NetworkResponse) this.future.get(j, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                if ("NO SUPPORT".equalsIgnoreCase(e.getMessage())) {
                    cl.e(TAG, "[get]有listener将不支持future.get()方法，如有需要请listener传入null", null, new Object[0]);
                }
                return new NetworkResponse(-201);
            }
        } else if (this.response != null) {
            return this.response;
        } else {
            return new NetworkResponse(-201);
        }
    }
}
