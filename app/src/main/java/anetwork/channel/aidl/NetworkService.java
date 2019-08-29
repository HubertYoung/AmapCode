package anetwork.channel.aidl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import anetwork.channel.aidl.IRemoteNetworkGetter.Stub;
import anetwork.channel.degrade.DegradableNetworkDelegate;
import anetwork.channel.http.HttpNetworkDelegate;

public class NetworkService extends Service {
    Stub a = new Stub() {
        public RemoteNetwork get(int i) throws RemoteException {
            return i == 1 ? NetworkService.this.c : NetworkService.this.d;
        }
    };
    private Context b;
    /* access modifiers changed from: private */
    public RemoteNetwork.Stub c = null;
    /* access modifiers changed from: private */
    public RemoteNetwork.Stub d = null;

    public void onDestroy() {
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    public IBinder onBind(Intent intent) {
        this.b = getApplicationContext();
        if (cl.a(2)) {
            StringBuilder sb = new StringBuilder("onBind:");
            sb.append(intent.getAction());
            cl.b("anet.NetworkService", sb.toString(), null, new Object[0]);
        }
        this.c = new DegradableNetworkDelegate(this.b);
        this.d = new HttpNetworkDelegate(this.b);
        if (IRemoteNetworkGetter.class.getName().equals(intent.getAction())) {
            return this.a;
        }
        return null;
    }
}
