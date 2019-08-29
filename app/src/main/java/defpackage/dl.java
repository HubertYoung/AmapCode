package defpackage;

import android.os.RemoteException;
import anetwork.channel.aidl.ParcelableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: dl reason: default package */
/* compiled from: FutureResponse */
public final class dl implements Future<dk> {
    private ParcelableFuture a;
    private dk b;

    public final /* synthetic */ Object get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return a(j);
    }

    public dl(ParcelableFuture parcelableFuture) {
        this.a = parcelableFuture;
    }

    public dl(dk dkVar) {
        this.b = dkVar;
    }

    public final boolean cancel(boolean z) {
        if (this.a == null) {
            return false;
        }
        try {
            return this.a.cancel(z);
        } catch (RemoteException e) {
            cl.a("anet.FutureResponse", "[cancel]", null, e, new Object[0]);
            return false;
        }
    }

    public final boolean isCancelled() {
        try {
            return this.a.isCancelled();
        } catch (RemoteException e) {
            cl.a("anet.FutureResponse", "[isCancelled]", null, e, new Object[0]);
            return false;
        }
    }

    public final boolean isDone() {
        try {
            return this.a.isDone();
        } catch (RemoteException e) {
            cl.a("anet.FutureResponse", "[isDone]", null, e, new Object[0]);
            return true;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public dk get() throws InterruptedException, ExecutionException {
        if (this.b != null) {
            return this.b;
        }
        if (this.a != null) {
            try {
                return this.a.get(20000);
            } catch (RemoteException e) {
                cl.a("anet.FutureResponse", "[get]", null, e, new Object[0]);
            }
        }
        return null;
    }

    private dk a(long j) throws InterruptedException, ExecutionException, TimeoutException {
        if (this.b != null) {
            return this.b;
        }
        if (this.a != null) {
            try {
                return this.a.get(j);
            } catch (RemoteException e) {
                cl.a("anet.FutureResponse", "[get(long timeout, TimeUnit unit)]", null, e, new Object[0]);
            }
        }
        return null;
    }
}
