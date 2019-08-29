package defpackage;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.StatObject;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.IRemoteNetworkGetter;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.aidl.RemoteNetwork;
import anetwork.channel.aidl.adapter.ParcelableNetworkListenerWrapper;
import anetwork.channel.http.HttpNetworkDelegate;
import java.util.concurrent.Future;

/* renamed from: dm reason: default package */
/* compiled from: NetworkProxy */
public class dm implements de {
    protected static String a = "anet.NetworkProxy";
    private volatile RemoteNetwork b = null;
    private int c = 0;
    private Context d;

    protected dm(Context context, int i) {
        this.d = context;
        this.c = i;
    }

    public final dk a(dj djVar) {
        cl.b(a, "networkProxy syncSend", djVar.l(), new Object[0]);
        a(true);
        ParcelableRequest parcelableRequest = new ParcelableRequest(djVar);
        if (parcelableRequest.d == null) {
            return new NetworkResponse(-102);
        }
        try {
            return this.b.syncSend(parcelableRequest);
        } catch (Throwable th) {
            a(th, "[syncSend]call syncSend method failed.");
            return new NetworkResponse(-103);
        }
    }

    private void a(boolean z) {
        if (this.b == null) {
            if (ds.f()) {
                dn.a(this.d, z);
                a(this.c);
                if (this.b != null) {
                    return;
                }
            }
            synchronized (this) {
                if (this.b == null) {
                    if (cl.a(2)) {
                        cl.b(a, "[getLocalNetworkInstance]", null, new Object[0]);
                    }
                    this.b = new HttpNetworkDelegate(this.d);
                }
            }
        }
    }

    public final Future<dk> a(dj djVar, Object obj, dh dhVar) {
        boolean z = false;
        cl.b(a, "networkProxy asyncSend", djVar.l(), new Object[0]);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            z = true;
        }
        a(z);
        ParcelableRequest parcelableRequest = new ParcelableRequest(djVar);
        ParcelableNetworkListenerWrapper parcelableNetworkListenerWrapper = new ParcelableNetworkListenerWrapper(dhVar, null, obj);
        if (parcelableRequest.d == null) {
            try {
                parcelableNetworkListenerWrapper.onFinished(new DefaultFinishEvent(-102));
            } catch (RemoteException unused) {
            }
            return new dl((dk) new NetworkResponse(-102));
        }
        try {
            return new dl(this.b.asyncSend(parcelableRequest, parcelableNetworkListenerWrapper));
        } catch (Throwable th) {
            try {
                parcelableNetworkListenerWrapper.onFinished(new DefaultFinishEvent(-102));
            } catch (RemoteException unused2) {
            }
            a(th, "[asyncSend]call asyncSend exception");
            return new dl((dk) new NetworkResponse(-103));
        }
    }

    private synchronized void a(int i) {
        if (this.b == null) {
            if (cl.a(2)) {
                cl.b(a, "[tryGetRemoteNetworkInstance] type=".concat(String.valueOf(i)), null, new Object[0]);
            }
            IRemoteNetworkGetter a2 = dn.a();
            if (a2 != null) {
                try {
                    this.b = a2.get(i);
                } catch (Throwable th) {
                    a(th, "[tryGetRemoteNetworkInstance]get RemoteNetwork Delegate failed.");
                }
            }
        }
    }

    private static void a(Throwable th, String str) {
        cl.e(a, null, str, new Object[0]);
        ExceptionStatistic exceptionStatistic = new ExceptionStatistic(-103, null, "rt");
        exceptionStatistic.exceptionStack = th.toString();
        x.a().a((StatObject) exceptionStatistic);
    }
}
